package org.eclipse.n4js.ts.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.n4js.ts.services.TypesGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
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
@SuppressWarnings("all")
public class InternalTypesParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AssignmnentCompatible", "ProtectedInternal", "ProvidedByRuntime", "PublicInternal", "AutoboxedType", "Intersection", "Constructor", "VirtualBase", "Implements", "Instanceof", "Promisify", "Interface", "Primitive", "Protected", "Undefined", "Abstract", "Continue", "Debugger", "External", "Function", "Default", "Extends", "Finally", "Indexed", "Private", "Project", "Delete", "Export", "Import", "Object", "Public", "Return", "Static", "Switch", "Target", "Typeof", "Async", "Await", "Break", "Catch", "Class", "Const", "False", "Final", "Super", "Throw", "Union", "While", "Yield", "This", "Case", "Else", "Enum", "From", "Null", "This_1", "True", "Type", "Void", "With", "FullStopFullStopFullStop", "Any", "For", "Get", "Let", "New", "Out", "Set", "Try", "Var", "EqualsSignGreaterThanSign", "As", "Do", "If", "In", "Of", "Ampersand", "LeftParenthesis", "RightParenthesis", "PlusSign", "Comma", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "QuestionMark", "CommercialAt", "LeftSquareBracket", "RightSquareBracket", "LeftCurlyBracket", "RightCurlyBracket", "Tilde", "RULE_SINGLE_STRING_CHAR", "RULE_STRING", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_STRUCTMODSUFFIX", "RULE_IDENTIFIER_START", "RULE_IDENTIFIER_PART", "RULE_IDENTIFIER", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_INT", "RULE_ML_COMMENT_FRAGMENT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_EOL", "RULE_WHITESPACE_FRAGMENT", "RULE_WS", "RULE_HEX_DIGIT", "RULE_UNICODE_ESCAPE_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_ZWNJ", "RULE_ZWJ", "RULE_DOT_DOT", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ANY_OTHER"
    };
    public static final int Delete=30;
    public static final int Enum=56;
    public static final int Import=32;
    public static final int EqualsSignGreaterThanSign=74;
    public static final int Var=73;
    public static final int Break=42;
    public static final int False=46;
    public static final int LessThanSign=89;
    public static final int LeftParenthesis=81;
    public static final int Throw=49;
    public static final int VirtualBase=11;
    public static final int Private=28;
    public static final int Extends=25;
    public static final int GreaterThanSign=91;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=102;
    public static final int RULE_STRUCTMODSUFFIX=103;
    public static final int RULE_EOL=112;
    public static final int ProtectedInternal=5;
    public static final int Out=70;
    public static final int RULE_ZWNJ=121;
    public static final int Project=29;
    public static final int Switch=37;
    public static final int PlusSign=83;
    public static final int RULE_INT=108;
    public static final int Get=67;
    public static final int RULE_ML_COMMENT=110;
    public static final int Object=33;
    public static final int LeftSquareBracket=94;
    public static final int If=77;
    public static final int Finally=26;
    public static final int Intersection=9;
    public static final int Set=71;
    public static final int RULE_UNICODE_ESCAPE_FRAGMENT=116;
    public static final int In=78;
    public static final int Catch=43;
    public static final int Union=50;
    public static final int Case=54;
    public static final int Comma=84;
    public static final int RULE_SL_COMMENT_FRAGMENT=127;
    public static final int Target=38;
    public static final int As=75;
    public static final int RULE_IDENTIFIER_PART=105;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=126;
    public static final int Export=31;
    public static final int Solidus=86;
    public static final int RightCurlyBracket=97;
    public static final int Final=47;
    public static final int FullStop=85;
    public static final int Constructor=10;
    public static final int Abstract=19;
    public static final int Promisify=14;
    public static final int Default=24;
    public static final int CommercialAt=93;
    public static final int Semicolon=88;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=101;
    public static final int Type=61;
    public static final int QuestionMark=92;
    public static final int PublicInternal=7;
    public static final int Else=55;
    public static final int RULE_HEX_DIGIT=115;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=120;
    public static final int Yield=52;
    public static final int Interface=15;
    public static final int New=69;
    public static final int Null=58;
    public static final int Typeof=39;
    public static final int True=60;
    public static final int ProvidedByRuntime=6;
    public static final int FullStopFullStopFullStop=64;
    public static final int RULE_IDENTIFIER_START=104;
    public static final int Implements=12;
    public static final int RULE_WHITESPACE_FRAGMENT=113;
    public static final int Super=48;
    public static final int Async=40;
    public static final int This=53;
    public static final int Try=72;
    public static final int Ampersand=80;
    public static final int Void=62;
    public static final int RightSquareBracket=95;
    public static final int Undefined=18;
    public static final int Protected=17;
    public static final int AutoboxedType=8;
    public static final int Const=45;
    public static final int For=66;
    public static final int RightParenthesis=82;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=118;
    public static final int Public=34;
    public static final int Do=76;
    public static final int This_1=59;
    public static final int RULE_DOT_DOT=123;
    public static final int External=22;
    public static final int Class=44;
    public static final int Static=36;
    public static final int Debugger=21;
    public static final int RULE_SINGLE_STRING_CHAR=99;
    public static final int AssignmnentCompatible=4;
    public static final int RULE_IDENTIFIER=106;
    public static final int RULE_ML_COMMENT_FRAGMENT=109;
    public static final int RULE_STRING=100;
    public static final int Continue=20;
    public static final int Any=65;
    public static final int With=63;
    public static final int RULE_SL_COMMENT=111;
    public static final int Function=23;
    public static final int EqualsSign=90;
    public static final int RULE_ZWJ=122;
    public static final int Primitive=16;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=119;
    public static final int Instanceof=13;
    public static final int Colon=87;
    public static final int EOF=-1;
    public static final int Indexed=27;
    public static final int Return=35;
    public static final int RULE_WS=114;
    public static final int RULE_BOM=125;
    public static final int LeftCurlyBracket=96;
    public static final int Tilde=98;
    public static final int While=51;
    public static final int From=57;
    public static final int RULE_ANY_OTHER=128;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=117;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=124;
    public static final int Of=79;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=107;
    public static final int Let=68;
    public static final int Await=41;

    // delegates
    // delegators


        public InternalTypesParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalTypesParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalTypesParser.tokenNames; }
    public String getGrammarFileName() { return "InternalTypesParser.g"; }



     	private TypesGrammarAccess grammarAccess;

        public InternalTypesParser(TokenStream input, TypesGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "TypeDefs";
       	}

       	@Override
       	protected TypesGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleTypeDefs"
    // InternalTypesParser.g:65:1: entryRuleTypeDefs returns [EObject current=null] : iv_ruleTypeDefs= ruleTypeDefs EOF ;
    public final EObject entryRuleTypeDefs() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeDefs = null;


        try {
            // InternalTypesParser.g:65:49: (iv_ruleTypeDefs= ruleTypeDefs EOF )
            // InternalTypesParser.g:66:2: iv_ruleTypeDefs= ruleTypeDefs EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeDefsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeDefs=ruleTypeDefs();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeDefs; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeDefs"


    // $ANTLR start "ruleTypeDefs"
    // InternalTypesParser.g:72:1: ruleTypeDefs returns [EObject current=null] : ( (lv_types_0_0= ruleType ) )* ;
    public final EObject ruleTypeDefs() throws RecognitionException {
        EObject current = null;

        EObject lv_types_0_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:78:2: ( ( (lv_types_0_0= ruleType ) )* )
            // InternalTypesParser.g:79:2: ( (lv_types_0_0= ruleType ) )*
            {
            // InternalTypesParser.g:79:2: ( (lv_types_0_0= ruleType ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==PublicInternal||LA1_0==VirtualBase||LA1_0==Primitive||LA1_0==Undefined||LA1_0==Project||LA1_0==Public||LA1_0==Null||LA1_0==Void||LA1_0==Any||LA1_0==RULE_IDENTIFIER) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalTypesParser.g:80:3: (lv_types_0_0= ruleType )
            	    {
            	    // InternalTypesParser.g:80:3: (lv_types_0_0= ruleType )
            	    // InternalTypesParser.g:81:4: lv_types_0_0= ruleType
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getTypeDefsAccess().getTypesTypeParserRuleCall_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_types_0_0=ruleType();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				if (current==null) {
            	      					current = createModelElementForParent(grammarAccess.getTypeDefsRule());
            	      				}
            	      				add(
            	      					current,
            	      					"types",
            	      					lv_types_0_0,
            	      					"org.eclipse.n4js.ts.Types.Type");
            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeDefs"


    // $ANTLR start "entryRuleTAnnotation"
    // InternalTypesParser.g:101:1: entryRuleTAnnotation returns [EObject current=null] : iv_ruleTAnnotation= ruleTAnnotation EOF ;
    public final EObject entryRuleTAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTAnnotation = null;


        try {
            // InternalTypesParser.g:101:52: (iv_ruleTAnnotation= ruleTAnnotation EOF )
            // InternalTypesParser.g:102:2: iv_ruleTAnnotation= ruleTAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTAnnotationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTAnnotation=ruleTAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTAnnotation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTAnnotation"


    // $ANTLR start "ruleTAnnotation"
    // InternalTypesParser.g:108:1: ruleTAnnotation returns [EObject current=null] : ( ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )? otherlv_6= RightParenthesis )? ) ;
    public final EObject ruleTAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_args_3_0 = null;

        EObject lv_args_5_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:114:2: ( ( ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )? otherlv_6= RightParenthesis )? ) )
            // InternalTypesParser.g:115:2: ( ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            {
            // InternalTypesParser.g:115:2: ( ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )? otherlv_6= RightParenthesis )? )
            // InternalTypesParser.g:116:3: ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) ) ) ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )? otherlv_6= RightParenthesis )?
            {
            // InternalTypesParser.g:116:3: ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) ) )
            // InternalTypesParser.g:117:4: ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) )
            {
            // InternalTypesParser.g:126:4: (otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) ) )
            // InternalTypesParser.g:127:5: otherlv_0= CommercialAt ( (lv_name_1_0= RULE_IDENTIFIER ) )
            {
            otherlv_0=(Token)match(input,CommercialAt,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_0, grammarAccess.getTAnnotationAccess().getCommercialAtKeyword_0_0_0());
              				
            }
            // InternalTypesParser.g:131:5: ( (lv_name_1_0= RULE_IDENTIFIER ) )
            // InternalTypesParser.g:132:6: (lv_name_1_0= RULE_IDENTIFIER )
            {
            // InternalTypesParser.g:132:6: (lv_name_1_0= RULE_IDENTIFIER )
            // InternalTypesParser.g:133:7: lv_name_1_0= RULE_IDENTIFIER
            {
            lv_name_1_0=(Token)match(input,RULE_IDENTIFIER,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							newLeafNode(lv_name_1_0, grammarAccess.getTAnnotationAccess().getNameIDENTIFIERTerminalRuleCall_0_0_1_0());
              						
            }
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElement(grammarAccess.getTAnnotationRule());
              							}
              							setWithLastConsumed(
              								current,
              								"name",
              								lv_name_1_0,
              								"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
              						
            }

            }


            }


            }


            }

            // InternalTypesParser.g:151:3: ( ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )? otherlv_6= RightParenthesis )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LeftParenthesis) && (synpred2_InternalTypesParser())) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalTypesParser.g:152:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis ) ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )? otherlv_6= RightParenthesis
                    {
                    // InternalTypesParser.g:152:4: ( ( LeftParenthesis )=>otherlv_2= LeftParenthesis )
                    // InternalTypesParser.g:153:5: ( LeftParenthesis )=>otherlv_2= LeftParenthesis
                    {
                    otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getTAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      				
                    }

                    }

                    // InternalTypesParser.g:159:4: ( ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )* )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( ((LA3_0>=Intersection && LA3_0<=Constructor)||LA3_0==Undefined||LA3_0==Indexed||LA3_0==Union||(LA3_0>=Null && LA3_0<=This_1)||(LA3_0>=Type && LA3_0<=Void)||LA3_0==Any||LA3_0==LeftCurlyBracket||LA3_0==Tilde||LA3_0==RULE_STRING||LA3_0==RULE_IDENTIFIER) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalTypesParser.g:160:5: ( (lv_args_3_0= ruleTAnnotationArgument ) ) (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )*
                            {
                            // InternalTypesParser.g:160:5: ( (lv_args_3_0= ruleTAnnotationArgument ) )
                            // InternalTypesParser.g:161:6: (lv_args_3_0= ruleTAnnotationArgument )
                            {
                            // InternalTypesParser.g:161:6: (lv_args_3_0= ruleTAnnotationArgument )
                            // InternalTypesParser.g:162:7: lv_args_3_0= ruleTAnnotationArgument
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_7);
                            lv_args_3_0=ruleTAnnotationArgument();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getTAnnotationRule());
                              							}
                              							add(
                              								current,
                              								"args",
                              								lv_args_3_0,
                              								"org.eclipse.n4js.ts.Types.TAnnotationArgument");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalTypesParser.g:179:5: (otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) ) )*
                            loop2:
                            do {
                                int alt2=2;
                                int LA2_0 = input.LA(1);

                                if ( (LA2_0==Comma) ) {
                                    alt2=1;
                                }


                                switch (alt2) {
                            	case 1 :
                            	    // InternalTypesParser.g:180:6: otherlv_4= Comma ( (lv_args_5_0= ruleTAnnotationArgument ) )
                            	    {
                            	    otherlv_4=(Token)match(input,Comma,FOLLOW_8); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_4, grammarAccess.getTAnnotationAccess().getCommaKeyword_1_1_1_0());
                            	      					
                            	    }
                            	    // InternalTypesParser.g:184:6: ( (lv_args_5_0= ruleTAnnotationArgument ) )
                            	    // InternalTypesParser.g:185:7: (lv_args_5_0= ruleTAnnotationArgument )
                            	    {
                            	    // InternalTypesParser.g:185:7: (lv_args_5_0= ruleTAnnotationArgument )
                            	    // InternalTypesParser.g:186:8: lv_args_5_0= ruleTAnnotationArgument
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_7);
                            	    lv_args_5_0=ruleTAnnotationArgument();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getTAnnotationRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"args",
                            	      									lv_args_5_0,
                            	      									"org.eclipse.n4js.ts.Types.TAnnotationArgument");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop2;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getTAnnotationAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTAnnotation"


    // $ANTLR start "entryRuleTAnnotationArgument"
    // InternalTypesParser.g:214:1: entryRuleTAnnotationArgument returns [EObject current=null] : iv_ruleTAnnotationArgument= ruleTAnnotationArgument EOF ;
    public final EObject entryRuleTAnnotationArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTAnnotationArgument = null;


        try {
            // InternalTypesParser.g:214:60: (iv_ruleTAnnotationArgument= ruleTAnnotationArgument EOF )
            // InternalTypesParser.g:215:2: iv_ruleTAnnotationArgument= ruleTAnnotationArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTAnnotationArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTAnnotationArgument=ruleTAnnotationArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTAnnotationArgument; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTAnnotationArgument"


    // $ANTLR start "ruleTAnnotationArgument"
    // InternalTypesParser.g:221:1: ruleTAnnotationArgument returns [EObject current=null] : (this_TAnnotationStringArgument_0= ruleTAnnotationStringArgument | this_TAnnotationTypeRefArgument_1= ruleTAnnotationTypeRefArgument ) ;
    public final EObject ruleTAnnotationArgument() throws RecognitionException {
        EObject current = null;

        EObject this_TAnnotationStringArgument_0 = null;

        EObject this_TAnnotationTypeRefArgument_1 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:227:2: ( (this_TAnnotationStringArgument_0= ruleTAnnotationStringArgument | this_TAnnotationTypeRefArgument_1= ruleTAnnotationTypeRefArgument ) )
            // InternalTypesParser.g:228:2: (this_TAnnotationStringArgument_0= ruleTAnnotationStringArgument | this_TAnnotationTypeRefArgument_1= ruleTAnnotationTypeRefArgument )
            {
            // InternalTypesParser.g:228:2: (this_TAnnotationStringArgument_0= ruleTAnnotationStringArgument | this_TAnnotationTypeRefArgument_1= ruleTAnnotationTypeRefArgument )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_STRING) ) {
                alt5=1;
            }
            else if ( ((LA5_0>=Intersection && LA5_0<=Constructor)||LA5_0==Undefined||LA5_0==Indexed||LA5_0==Union||(LA5_0>=Null && LA5_0<=This_1)||(LA5_0>=Type && LA5_0<=Void)||LA5_0==Any||LA5_0==LeftCurlyBracket||LA5_0==Tilde||LA5_0==RULE_IDENTIFIER) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalTypesParser.g:229:3: this_TAnnotationStringArgument_0= ruleTAnnotationStringArgument
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationStringArgumentParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TAnnotationStringArgument_0=ruleTAnnotationStringArgument();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TAnnotationStringArgument_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:238:3: this_TAnnotationTypeRefArgument_1= ruleTAnnotationTypeRefArgument
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationTypeRefArgumentParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TAnnotationTypeRefArgument_1=ruleTAnnotationTypeRefArgument();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TAnnotationTypeRefArgument_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTAnnotationArgument"


    // $ANTLR start "entryRuleTAnnotationStringArgument"
    // InternalTypesParser.g:250:1: entryRuleTAnnotationStringArgument returns [EObject current=null] : iv_ruleTAnnotationStringArgument= ruleTAnnotationStringArgument EOF ;
    public final EObject entryRuleTAnnotationStringArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTAnnotationStringArgument = null;


        try {
            // InternalTypesParser.g:250:66: (iv_ruleTAnnotationStringArgument= ruleTAnnotationStringArgument EOF )
            // InternalTypesParser.g:251:2: iv_ruleTAnnotationStringArgument= ruleTAnnotationStringArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTAnnotationStringArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTAnnotationStringArgument=ruleTAnnotationStringArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTAnnotationStringArgument; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTAnnotationStringArgument"


    // $ANTLR start "ruleTAnnotationStringArgument"
    // InternalTypesParser.g:257:1: ruleTAnnotationStringArgument returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleTAnnotationStringArgument() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;


        	enterRule();

        try {
            // InternalTypesParser.g:263:2: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // InternalTypesParser.g:264:2: ( (lv_value_0_0= RULE_STRING ) )
            {
            // InternalTypesParser.g:264:2: ( (lv_value_0_0= RULE_STRING ) )
            // InternalTypesParser.g:265:3: (lv_value_0_0= RULE_STRING )
            {
            // InternalTypesParser.g:265:3: (lv_value_0_0= RULE_STRING )
            // InternalTypesParser.g:266:4: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_value_0_0, grammarAccess.getTAnnotationStringArgumentAccess().getValueSTRINGTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getTAnnotationStringArgumentRule());
              				}
              				setWithLastConsumed(
              					current,
              					"value",
              					lv_value_0_0,
              					"org.eclipse.n4js.ts.Types.STRING");
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTAnnotationStringArgument"


    // $ANTLR start "entryRuleTAnnotationTypeRefArgument"
    // InternalTypesParser.g:285:1: entryRuleTAnnotationTypeRefArgument returns [EObject current=null] : iv_ruleTAnnotationTypeRefArgument= ruleTAnnotationTypeRefArgument EOF ;
    public final EObject entryRuleTAnnotationTypeRefArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTAnnotationTypeRefArgument = null;


        try {
            // InternalTypesParser.g:285:67: (iv_ruleTAnnotationTypeRefArgument= ruleTAnnotationTypeRefArgument EOF )
            // InternalTypesParser.g:286:2: iv_ruleTAnnotationTypeRefArgument= ruleTAnnotationTypeRefArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTAnnotationTypeRefArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTAnnotationTypeRefArgument=ruleTAnnotationTypeRefArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTAnnotationTypeRefArgument; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTAnnotationTypeRefArgument"


    // $ANTLR start "ruleTAnnotationTypeRefArgument"
    // InternalTypesParser.g:292:1: ruleTAnnotationTypeRefArgument returns [EObject current=null] : ( (lv_typeRef_0_0= ruleTypeRef ) ) ;
    public final EObject ruleTAnnotationTypeRefArgument() throws RecognitionException {
        EObject current = null;

        EObject lv_typeRef_0_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:298:2: ( ( (lv_typeRef_0_0= ruleTypeRef ) ) )
            // InternalTypesParser.g:299:2: ( (lv_typeRef_0_0= ruleTypeRef ) )
            {
            // InternalTypesParser.g:299:2: ( (lv_typeRef_0_0= ruleTypeRef ) )
            // InternalTypesParser.g:300:3: (lv_typeRef_0_0= ruleTypeRef )
            {
            // InternalTypesParser.g:300:3: (lv_typeRef_0_0= ruleTypeRef )
            // InternalTypesParser.g:301:4: lv_typeRef_0_0= ruleTypeRef
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefTypeRefParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_typeRef_0_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getTAnnotationTypeRefArgumentRule());
              				}
              				set(
              					current,
              					"typeRef",
              					lv_typeRef_0_0,
              					"org.eclipse.n4js.ts.Types.TypeRef");
              				afterParserOrEnumRuleCall();
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTAnnotationTypeRefArgument"


    // $ANTLR start "entryRuleType"
    // InternalTypesParser.g:321:1: entryRuleType returns [EObject current=null] : iv_ruleType= ruleType EOF ;
    public final EObject entryRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleType = null;


        try {
            // InternalTypesParser.g:321:45: (iv_ruleType= ruleType EOF )
            // InternalTypesParser.g:322:2: iv_ruleType= ruleType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleType=ruleType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleType"


    // $ANTLR start "ruleType"
    // InternalTypesParser.g:328:1: ruleType returns [EObject current=null] : (this_TObjectPrototype_0= ruleTObjectPrototype | this_TClass_1= ruleTClass | this_TInterface_2= ruleTInterface | this_TEnum_3= ruleTEnum | this_AnyType_4= ruleAnyType | this_VoidType_5= ruleVoidType | this_UndefinedType_6= ruleUndefinedType | this_NullType_7= ruleNullType | this_PrimitiveType_8= rulePrimitiveType | this_TFunction_9= ruleTFunction | this_TypeVariable_10= ruleTypeVariable | this_VirtualBaseType_11= ruleVirtualBaseType ) ;
    public final EObject ruleType() throws RecognitionException {
        EObject current = null;

        EObject this_TObjectPrototype_0 = null;

        EObject this_TClass_1 = null;

        EObject this_TInterface_2 = null;

        EObject this_TEnum_3 = null;

        EObject this_AnyType_4 = null;

        EObject this_VoidType_5 = null;

        EObject this_UndefinedType_6 = null;

        EObject this_NullType_7 = null;

        EObject this_PrimitiveType_8 = null;

        EObject this_TFunction_9 = null;

        EObject this_TypeVariable_10 = null;

        EObject this_VirtualBaseType_11 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:334:2: ( (this_TObjectPrototype_0= ruleTObjectPrototype | this_TClass_1= ruleTClass | this_TInterface_2= ruleTInterface | this_TEnum_3= ruleTEnum | this_AnyType_4= ruleAnyType | this_VoidType_5= ruleVoidType | this_UndefinedType_6= ruleUndefinedType | this_NullType_7= ruleNullType | this_PrimitiveType_8= rulePrimitiveType | this_TFunction_9= ruleTFunction | this_TypeVariable_10= ruleTypeVariable | this_VirtualBaseType_11= ruleVirtualBaseType ) )
            // InternalTypesParser.g:335:2: (this_TObjectPrototype_0= ruleTObjectPrototype | this_TClass_1= ruleTClass | this_TInterface_2= ruleTInterface | this_TEnum_3= ruleTEnum | this_AnyType_4= ruleAnyType | this_VoidType_5= ruleVoidType | this_UndefinedType_6= ruleUndefinedType | this_NullType_7= ruleNullType | this_PrimitiveType_8= rulePrimitiveType | this_TFunction_9= ruleTFunction | this_TypeVariable_10= ruleTypeVariable | this_VirtualBaseType_11= ruleVirtualBaseType )
            {
            // InternalTypesParser.g:335:2: (this_TObjectPrototype_0= ruleTObjectPrototype | this_TClass_1= ruleTClass | this_TInterface_2= ruleTInterface | this_TEnum_3= ruleTEnum | this_AnyType_4= ruleAnyType | this_VoidType_5= ruleVoidType | this_UndefinedType_6= ruleUndefinedType | this_NullType_7= ruleNullType | this_PrimitiveType_8= rulePrimitiveType | this_TFunction_9= ruleTFunction | this_TypeVariable_10= ruleTypeVariable | this_VirtualBaseType_11= ruleVirtualBaseType )
            int alt6=12;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // InternalTypesParser.g:336:3: this_TObjectPrototype_0= ruleTObjectPrototype
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getTObjectPrototypeParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TObjectPrototype_0=ruleTObjectPrototype();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TObjectPrototype_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:345:3: this_TClass_1= ruleTClass
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getTClassParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TClass_1=ruleTClass();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TClass_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:354:3: this_TInterface_2= ruleTInterface
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getTInterfaceParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TInterface_2=ruleTInterface();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TInterface_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:363:3: this_TEnum_3= ruleTEnum
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getTEnumParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TEnum_3=ruleTEnum();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TEnum_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:372:3: this_AnyType_4= ruleAnyType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getAnyTypeParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_AnyType_4=ruleAnyType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_AnyType_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalTypesParser.g:381:3: this_VoidType_5= ruleVoidType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getVoidTypeParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_VoidType_5=ruleVoidType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_VoidType_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalTypesParser.g:390:3: this_UndefinedType_6= ruleUndefinedType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getUndefinedTypeParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_UndefinedType_6=ruleUndefinedType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_UndefinedType_6;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalTypesParser.g:399:3: this_NullType_7= ruleNullType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getNullTypeParserRuleCall_7());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_NullType_7=ruleNullType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_NullType_7;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalTypesParser.g:408:3: this_PrimitiveType_8= rulePrimitiveType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_8());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_PrimitiveType_8=rulePrimitiveType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_PrimitiveType_8;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalTypesParser.g:417:3: this_TFunction_9= ruleTFunction
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getTFunctionParserRuleCall_9());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TFunction_9=ruleTFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TFunction_9;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalTypesParser.g:426:3: this_TypeVariable_10= ruleTypeVariable
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getTypeVariableParserRuleCall_10());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeVariable_10=ruleTypeVariable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TypeVariable_10;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalTypesParser.g:435:3: this_VirtualBaseType_11= ruleVirtualBaseType
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeAccess().getVirtualBaseTypeParserRuleCall_11());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_VirtualBaseType_11=ruleVirtualBaseType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_VirtualBaseType_11;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleType"


    // $ANTLR start "entryRuleTypeRef"
    // InternalTypesParser.g:447:1: entryRuleTypeRef returns [EObject current=null] : iv_ruleTypeRef= ruleTypeRef EOF ;
    public final EObject entryRuleTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRef = null;


        try {
            // InternalTypesParser.g:447:48: (iv_ruleTypeRef= ruleTypeRef EOF )
            // InternalTypesParser.g:448:2: iv_ruleTypeRef= ruleTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeRef=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeRef"


    // $ANTLR start "ruleTypeRef"
    // InternalTypesParser.g:454:1: ruleTypeRef returns [EObject current=null] : (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( (lv_followedByQuestionMark_1_0= QuestionMark ) )? ) ;
    public final EObject ruleTypeRef() throws RecognitionException {
        EObject current = null;

        Token lv_followedByQuestionMark_1_0=null;
        EObject this_TypeRefWithoutModifiers_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:460:2: ( (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( (lv_followedByQuestionMark_1_0= QuestionMark ) )? ) )
            // InternalTypesParser.g:461:2: (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( (lv_followedByQuestionMark_1_0= QuestionMark ) )? )
            {
            // InternalTypesParser.g:461:2: (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( (lv_followedByQuestionMark_1_0= QuestionMark ) )? )
            // InternalTypesParser.g:462:3: this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( (lv_followedByQuestionMark_1_0= QuestionMark ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTypeRefAccess().getTypeRefWithoutModifiersParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_9);
            this_TypeRefWithoutModifiers_0=ruleTypeRefWithoutModifiers();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TypeRefWithoutModifiers_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:470:3: ( (lv_followedByQuestionMark_1_0= QuestionMark ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==QuestionMark) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalTypesParser.g:471:4: (lv_followedByQuestionMark_1_0= QuestionMark )
                    {
                    // InternalTypesParser.g:471:4: (lv_followedByQuestionMark_1_0= QuestionMark )
                    // InternalTypesParser.g:472:5: lv_followedByQuestionMark_1_0= QuestionMark
                    {
                    lv_followedByQuestionMark_1_0=(Token)match(input,QuestionMark,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_followedByQuestionMark_1_0, grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTypeRefRule());
                      					}
                      					setWithLastConsumed(current, "followedByQuestionMark", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeRef"


    // $ANTLR start "entryRulePrimitiveType"
    // InternalTypesParser.g:488:1: entryRulePrimitiveType returns [EObject current=null] : iv_rulePrimitiveType= rulePrimitiveType EOF ;
    public final EObject entryRulePrimitiveType() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimitiveType = null;


        try {
            // InternalTypesParser.g:488:54: (iv_rulePrimitiveType= rulePrimitiveType EOF )
            // InternalTypesParser.g:489:2: iv_rulePrimitiveType= rulePrimitiveType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimitiveTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePrimitiveType=rulePrimitiveType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimitiveType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimitiveType"


    // $ANTLR start "rulePrimitiveType"
    // InternalTypesParser.g:495:1: rulePrimitiveType returns [EObject current=null] : (otherlv_0= Primitive ( (lv_name_1_0= ruleVoidOrBindingIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= ruleTypeVariable ) ) otherlv_4= GreaterThanSign )? (otherlv_5= Indexed ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_7= LeftCurlyBracket (otherlv_8= AutoboxedType ( ( ruleTypeReferenceName ) ) )? (otherlv_10= AssignmnentCompatible ( ( ruleTypeReferenceName ) ) )? otherlv_12= RightCurlyBracket ) ;
    public final EObject rulePrimitiveType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_typeVars_3_0 = null;

        EObject lv_declaredElementType_6_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:501:2: ( (otherlv_0= Primitive ( (lv_name_1_0= ruleVoidOrBindingIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= ruleTypeVariable ) ) otherlv_4= GreaterThanSign )? (otherlv_5= Indexed ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_7= LeftCurlyBracket (otherlv_8= AutoboxedType ( ( ruleTypeReferenceName ) ) )? (otherlv_10= AssignmnentCompatible ( ( ruleTypeReferenceName ) ) )? otherlv_12= RightCurlyBracket ) )
            // InternalTypesParser.g:502:2: (otherlv_0= Primitive ( (lv_name_1_0= ruleVoidOrBindingIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= ruleTypeVariable ) ) otherlv_4= GreaterThanSign )? (otherlv_5= Indexed ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_7= LeftCurlyBracket (otherlv_8= AutoboxedType ( ( ruleTypeReferenceName ) ) )? (otherlv_10= AssignmnentCompatible ( ( ruleTypeReferenceName ) ) )? otherlv_12= RightCurlyBracket )
            {
            // InternalTypesParser.g:502:2: (otherlv_0= Primitive ( (lv_name_1_0= ruleVoidOrBindingIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= ruleTypeVariable ) ) otherlv_4= GreaterThanSign )? (otherlv_5= Indexed ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_7= LeftCurlyBracket (otherlv_8= AutoboxedType ( ( ruleTypeReferenceName ) ) )? (otherlv_10= AssignmnentCompatible ( ( ruleTypeReferenceName ) ) )? otherlv_12= RightCurlyBracket )
            // InternalTypesParser.g:503:3: otherlv_0= Primitive ( (lv_name_1_0= ruleVoidOrBindingIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= ruleTypeVariable ) ) otherlv_4= GreaterThanSign )? (otherlv_5= Indexed ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_7= LeftCurlyBracket (otherlv_8= AutoboxedType ( ( ruleTypeReferenceName ) ) )? (otherlv_10= AssignmnentCompatible ( ( ruleTypeReferenceName ) ) )? otherlv_12= RightCurlyBracket
            {
            otherlv_0=(Token)match(input,Primitive,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getPrimitiveTypeAccess().getPrimitiveKeyword_0());
              		
            }
            // InternalTypesParser.g:507:3: ( (lv_name_1_0= ruleVoidOrBindingIdentifier ) )
            // InternalTypesParser.g:508:4: (lv_name_1_0= ruleVoidOrBindingIdentifier )
            {
            // InternalTypesParser.g:508:4: (lv_name_1_0= ruleVoidOrBindingIdentifier )
            // InternalTypesParser.g:509:5: lv_name_1_0= ruleVoidOrBindingIdentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getNameVoidOrBindingIdentifierParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_11);
            lv_name_1_0=ruleVoidOrBindingIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.n4js.ts.Types.VoidOrBindingIdentifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:526:3: (otherlv_2= LessThanSign ( (lv_typeVars_3_0= ruleTypeVariable ) ) otherlv_4= GreaterThanSign )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==LessThanSign) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalTypesParser.g:527:4: otherlv_2= LessThanSign ( (lv_typeVars_3_0= ruleTypeVariable ) ) otherlv_4= GreaterThanSign
                    {
                    otherlv_2=(Token)match(input,LessThanSign,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2_0());
                      			
                    }
                    // InternalTypesParser.g:531:4: ( (lv_typeVars_3_0= ruleTypeVariable ) )
                    // InternalTypesParser.g:532:5: (lv_typeVars_3_0= ruleTypeVariable )
                    {
                    // InternalTypesParser.g:532:5: (lv_typeVars_3_0= ruleTypeVariable )
                    // InternalTypesParser.g:533:6: lv_typeVars_3_0= ruleTypeVariable
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_12);
                    lv_typeVars_3_0=ruleTypeVariable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
                      						}
                      						add(
                      							current,
                      							"typeVars",
                      							lv_typeVars_3_0,
                      							"org.eclipse.n4js.ts.Types.TypeVariable");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_4=(Token)match(input,GreaterThanSign,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_2_2());
                      			
                    }

                    }
                    break;

            }

            // InternalTypesParser.g:555:3: (otherlv_5= Indexed ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==Indexed) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalTypesParser.g:556:4: otherlv_5= Indexed ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) )
                    {
                    otherlv_5=(Token)match(input,Indexed,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getPrimitiveTypeAccess().getIndexedKeyword_3_0());
                      			
                    }
                    // InternalTypesParser.g:560:4: ( (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal ) )
                    // InternalTypesParser.g:561:5: (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal )
                    {
                    // InternalTypesParser.g:561:5: (lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal )
                    // InternalTypesParser.g:562:6: lv_declaredElementType_6_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
                    lv_declaredElementType_6_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
                      						}
                      						set(
                      							current,
                      							"declaredElementType",
                      							lv_declaredElementType_6_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,LeftCurlyBracket,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getPrimitiveTypeAccess().getLeftCurlyBracketKeyword_4());
              		
            }
            // InternalTypesParser.g:584:3: (otherlv_8= AutoboxedType ( ( ruleTypeReferenceName ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==AutoboxedType) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalTypesParser.g:585:4: otherlv_8= AutoboxedType ( ( ruleTypeReferenceName ) )
                    {
                    otherlv_8=(Token)match(input,AutoboxedType,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeKeyword_5_0());
                      			
                    }
                    // InternalTypesParser.g:589:4: ( ( ruleTypeReferenceName ) )
                    // InternalTypesParser.g:590:5: ( ruleTypeReferenceName )
                    {
                    // InternalTypesParser.g:590:5: ( ruleTypeReferenceName )
                    // InternalTypesParser.g:591:6: ruleTypeReferenceName
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPrimitiveTypeRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeTClassifierCrossReference_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    ruleTypeReferenceName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:606:3: (otherlv_10= AssignmnentCompatible ( ( ruleTypeReferenceName ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==AssignmnentCompatible) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalTypesParser.g:607:4: otherlv_10= AssignmnentCompatible ( ( ruleTypeReferenceName ) )
                    {
                    otherlv_10=(Token)match(input,AssignmnentCompatible,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getPrimitiveTypeAccess().getAssignmnentCompatibleKeyword_6_0());
                      			
                    }
                    // InternalTypesParser.g:611:4: ( ( ruleTypeReferenceName ) )
                    // InternalTypesParser.g:612:5: ( ruleTypeReferenceName )
                    {
                    // InternalTypesParser.g:612:5: ( ruleTypeReferenceName )
                    // InternalTypesParser.g:613:6: ruleTypeReferenceName
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPrimitiveTypeRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
                    ruleTypeReferenceName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_12=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_12, grammarAccess.getPrimitiveTypeAccess().getRightCurlyBracketKeyword_7());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimitiveType"


    // $ANTLR start "entryRuleTypeReferenceName"
    // InternalTypesParser.g:636:1: entryRuleTypeReferenceName returns [String current=null] : iv_ruleTypeReferenceName= ruleTypeReferenceName EOF ;
    public final String entryRuleTypeReferenceName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypeReferenceName = null;


        try {
            // InternalTypesParser.g:636:57: (iv_ruleTypeReferenceName= ruleTypeReferenceName EOF )
            // InternalTypesParser.g:637:2: iv_ruleTypeReferenceName= ruleTypeReferenceName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeReferenceNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeReferenceName=ruleTypeReferenceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeReferenceName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeReferenceName"


    // $ANTLR start "ruleTypeReferenceName"
    // InternalTypesParser.g:643:1: ruleTypeReferenceName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= Void | kw= Any | kw= Undefined | kw= Null | kw= Indexed | (this_IDENTIFIER_5= RULE_IDENTIFIER (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )* ) ) ;
    public final AntlrDatatypeRuleToken ruleTypeReferenceName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_IDENTIFIER_5=null;
        Token this_IDENTIFIER_7=null;


        	enterRule();

        try {
            // InternalTypesParser.g:649:2: ( (kw= Void | kw= Any | kw= Undefined | kw= Null | kw= Indexed | (this_IDENTIFIER_5= RULE_IDENTIFIER (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )* ) ) )
            // InternalTypesParser.g:650:2: (kw= Void | kw= Any | kw= Undefined | kw= Null | kw= Indexed | (this_IDENTIFIER_5= RULE_IDENTIFIER (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )* ) )
            {
            // InternalTypesParser.g:650:2: (kw= Void | kw= Any | kw= Undefined | kw= Null | kw= Indexed | (this_IDENTIFIER_5= RULE_IDENTIFIER (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )* ) )
            int alt13=6;
            switch ( input.LA(1) ) {
            case Void:
                {
                alt13=1;
                }
                break;
            case Any:
                {
                alt13=2;
                }
                break;
            case Undefined:
                {
                alt13=3;
                }
                break;
            case Null:
                {
                alt13=4;
                }
                break;
            case Indexed:
                {
                alt13=5;
                }
                break;
            case RULE_IDENTIFIER:
                {
                alt13=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // InternalTypesParser.g:651:3: kw= Void
                    {
                    kw=(Token)match(input,Void,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getVoidKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:657:3: kw= Any
                    {
                    kw=(Token)match(input,Any,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getAnyKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:663:3: kw= Undefined
                    {
                    kw=(Token)match(input,Undefined,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getUndefinedKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:669:3: kw= Null
                    {
                    kw=(Token)match(input,Null,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getNullKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:675:3: kw= Indexed
                    {
                    kw=(Token)match(input,Indexed,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getIndexedKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalTypesParser.g:681:3: (this_IDENTIFIER_5= RULE_IDENTIFIER (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )* )
                    {
                    // InternalTypesParser.g:681:3: (this_IDENTIFIER_5= RULE_IDENTIFIER (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )* )
                    // InternalTypesParser.g:682:4: this_IDENTIFIER_5= RULE_IDENTIFIER (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )*
                    {
                    this_IDENTIFIER_5=(Token)match(input,RULE_IDENTIFIER,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_IDENTIFIER_5);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_IDENTIFIER_5, grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_0());
                      			
                    }
                    // InternalTypesParser.g:689:4: (kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==Solidus) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalTypesParser.g:690:5: kw= Solidus this_IDENTIFIER_7= RULE_IDENTIFIER
                    	    {
                    	    kw=(Token)match(input,Solidus,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getSolidusKeyword_5_1_0());
                    	      				
                    	    }
                    	    this_IDENTIFIER_7=(Token)match(input,RULE_IDENTIFIER,FOLLOW_19); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_IDENTIFIER_7);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_IDENTIFIER_7, grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_1_1());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeReferenceName"


    // $ANTLR start "entryRuleAnyType"
    // InternalTypesParser.g:708:1: entryRuleAnyType returns [EObject current=null] : iv_ruleAnyType= ruleAnyType EOF ;
    public final EObject entryRuleAnyType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnyType = null;


        try {
            // InternalTypesParser.g:708:48: (iv_ruleAnyType= ruleAnyType EOF )
            // InternalTypesParser.g:709:2: iv_ruleAnyType= ruleAnyType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnyTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnyType=ruleAnyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnyType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnyType"


    // $ANTLR start "ruleAnyType"
    // InternalTypesParser.g:715:1: ruleAnyType returns [EObject current=null] : ( () ( (lv_name_1_0= Any ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) ;
    public final EObject ruleAnyType() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalTypesParser.g:721:2: ( ( () ( (lv_name_1_0= Any ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) )
            // InternalTypesParser.g:722:2: ( () ( (lv_name_1_0= Any ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            {
            // InternalTypesParser.g:722:2: ( () ( (lv_name_1_0= Any ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            // InternalTypesParser.g:723:3: () ( (lv_name_1_0= Any ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket
            {
            // InternalTypesParser.g:723:3: ()
            // InternalTypesParser.g:724:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getAnyTypeAccess().getAnyTypeAction_0(),
              					current);
              			
            }

            }

            // InternalTypesParser.g:730:3: ( (lv_name_1_0= Any ) )
            // InternalTypesParser.g:731:4: (lv_name_1_0= Any )
            {
            // InternalTypesParser.g:731:4: (lv_name_1_0= Any )
            // InternalTypesParser.g:732:5: lv_name_1_0= Any
            {
            lv_name_1_0=(Token)match(input,Any,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getAnyTypeAccess().getNameAnyKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getAnyTypeRule());
              					}
              					setWithLastConsumed(current, "name", lv_name_1_0, "any");
              				
            }

            }


            }

            otherlv_2=(Token)match(input,LeftCurlyBracket,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getAnyTypeAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            otherlv_3=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getAnyTypeAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnyType"


    // $ANTLR start "entryRuleVoidType"
    // InternalTypesParser.g:756:1: entryRuleVoidType returns [EObject current=null] : iv_ruleVoidType= ruleVoidType EOF ;
    public final EObject entryRuleVoidType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVoidType = null;


        try {
            // InternalTypesParser.g:756:49: (iv_ruleVoidType= ruleVoidType EOF )
            // InternalTypesParser.g:757:2: iv_ruleVoidType= ruleVoidType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVoidTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVoidType=ruleVoidType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVoidType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVoidType"


    // $ANTLR start "ruleVoidType"
    // InternalTypesParser.g:763:1: ruleVoidType returns [EObject current=null] : ( () ( (lv_name_1_0= Void ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) ;
    public final EObject ruleVoidType() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalTypesParser.g:769:2: ( ( () ( (lv_name_1_0= Void ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) )
            // InternalTypesParser.g:770:2: ( () ( (lv_name_1_0= Void ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            {
            // InternalTypesParser.g:770:2: ( () ( (lv_name_1_0= Void ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            // InternalTypesParser.g:771:3: () ( (lv_name_1_0= Void ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket
            {
            // InternalTypesParser.g:771:3: ()
            // InternalTypesParser.g:772:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVoidTypeAccess().getVoidTypeAction_0(),
              					current);
              			
            }

            }

            // InternalTypesParser.g:778:3: ( (lv_name_1_0= Void ) )
            // InternalTypesParser.g:779:4: (lv_name_1_0= Void )
            {
            // InternalTypesParser.g:779:4: (lv_name_1_0= Void )
            // InternalTypesParser.g:780:5: lv_name_1_0= Void
            {
            lv_name_1_0=(Token)match(input,Void,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getVoidTypeAccess().getNameVoidKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVoidTypeRule());
              					}
              					setWithLastConsumed(current, "name", lv_name_1_0, "void");
              				
            }

            }


            }

            otherlv_2=(Token)match(input,LeftCurlyBracket,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getVoidTypeAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            otherlv_3=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getVoidTypeAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVoidType"


    // $ANTLR start "entryRuleUndefinedType"
    // InternalTypesParser.g:804:1: entryRuleUndefinedType returns [EObject current=null] : iv_ruleUndefinedType= ruleUndefinedType EOF ;
    public final EObject entryRuleUndefinedType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUndefinedType = null;


        try {
            // InternalTypesParser.g:804:54: (iv_ruleUndefinedType= ruleUndefinedType EOF )
            // InternalTypesParser.g:805:2: iv_ruleUndefinedType= ruleUndefinedType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUndefinedTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUndefinedType=ruleUndefinedType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUndefinedType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUndefinedType"


    // $ANTLR start "ruleUndefinedType"
    // InternalTypesParser.g:811:1: ruleUndefinedType returns [EObject current=null] : ( () ( (lv_name_1_0= Undefined ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) ;
    public final EObject ruleUndefinedType() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalTypesParser.g:817:2: ( ( () ( (lv_name_1_0= Undefined ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) )
            // InternalTypesParser.g:818:2: ( () ( (lv_name_1_0= Undefined ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            {
            // InternalTypesParser.g:818:2: ( () ( (lv_name_1_0= Undefined ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            // InternalTypesParser.g:819:3: () ( (lv_name_1_0= Undefined ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket
            {
            // InternalTypesParser.g:819:3: ()
            // InternalTypesParser.g:820:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getUndefinedTypeAccess().getUndefinedTypeAction_0(),
              					current);
              			
            }

            }

            // InternalTypesParser.g:826:3: ( (lv_name_1_0= Undefined ) )
            // InternalTypesParser.g:827:4: (lv_name_1_0= Undefined )
            {
            // InternalTypesParser.g:827:4: (lv_name_1_0= Undefined )
            // InternalTypesParser.g:828:5: lv_name_1_0= Undefined
            {
            lv_name_1_0=(Token)match(input,Undefined,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getUndefinedTypeAccess().getNameUndefinedKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getUndefinedTypeRule());
              					}
              					setWithLastConsumed(current, "name", lv_name_1_0, "undefined");
              				
            }

            }


            }

            otherlv_2=(Token)match(input,LeftCurlyBracket,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getUndefinedTypeAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            otherlv_3=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getUndefinedTypeAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUndefinedType"


    // $ANTLR start "entryRuleNullType"
    // InternalTypesParser.g:852:1: entryRuleNullType returns [EObject current=null] : iv_ruleNullType= ruleNullType EOF ;
    public final EObject entryRuleNullType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNullType = null;


        try {
            // InternalTypesParser.g:852:49: (iv_ruleNullType= ruleNullType EOF )
            // InternalTypesParser.g:853:2: iv_ruleNullType= ruleNullType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNullTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNullType=ruleNullType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNullType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNullType"


    // $ANTLR start "ruleNullType"
    // InternalTypesParser.g:859:1: ruleNullType returns [EObject current=null] : ( () ( (lv_name_1_0= Null ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) ;
    public final EObject ruleNullType() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalTypesParser.g:865:2: ( ( () ( (lv_name_1_0= Null ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket ) )
            // InternalTypesParser.g:866:2: ( () ( (lv_name_1_0= Null ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            {
            // InternalTypesParser.g:866:2: ( () ( (lv_name_1_0= Null ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket )
            // InternalTypesParser.g:867:3: () ( (lv_name_1_0= Null ) ) otherlv_2= LeftCurlyBracket otherlv_3= RightCurlyBracket
            {
            // InternalTypesParser.g:867:3: ()
            // InternalTypesParser.g:868:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getNullTypeAccess().getNullTypeAction_0(),
              					current);
              			
            }

            }

            // InternalTypesParser.g:874:3: ( (lv_name_1_0= Null ) )
            // InternalTypesParser.g:875:4: (lv_name_1_0= Null )
            {
            // InternalTypesParser.g:875:4: (lv_name_1_0= Null )
            // InternalTypesParser.g:876:5: lv_name_1_0= Null
            {
            lv_name_1_0=(Token)match(input,Null,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getNullTypeAccess().getNameNullKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getNullTypeRule());
              					}
              					setWithLastConsumed(current, "name", lv_name_1_0, "null");
              				
            }

            }


            }

            otherlv_2=(Token)match(input,LeftCurlyBracket,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getNullTypeAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            otherlv_3=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getNullTypeAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNullType"


    // $ANTLR start "entryRuleTypesIdentifier"
    // InternalTypesParser.g:900:1: entryRuleTypesIdentifier returns [String current=null] : iv_ruleTypesIdentifier= ruleTypesIdentifier EOF ;
    public final String entryRuleTypesIdentifier() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypesIdentifier = null;


        try {
            // InternalTypesParser.g:900:55: (iv_ruleTypesIdentifier= ruleTypesIdentifier EOF )
            // InternalTypesParser.g:901:2: iv_ruleTypesIdentifier= ruleTypesIdentifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypesIdentifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypesIdentifier=ruleTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypesIdentifier.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypesIdentifier"


    // $ANTLR start "ruleTypesIdentifier"
    // InternalTypesParser.g:907:1: ruleTypesIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_IdentifierName_1= ruleIdentifierName ) ;
    public final AntlrDatatypeRuleToken ruleTypesIdentifier() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_TypesSpecificKeywords_0 = null;

        AntlrDatatypeRuleToken this_IdentifierName_1 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:913:2: ( (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_IdentifierName_1= ruleIdentifierName ) )
            // InternalTypesParser.g:914:2: (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_IdentifierName_1= ruleIdentifierName )
            {
            // InternalTypesParser.g:914:2: (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_IdentifierName_1= ruleIdentifierName )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==AssignmnentCompatible||LA14_0==AutoboxedType||LA14_0==VirtualBase||LA14_0==Primitive||LA14_0==Undefined||LA14_0==Object||LA14_0==Any) ) {
                alt14=1;
            }
            else if ( ((LA14_0>=Intersection && LA14_0<=Constructor)||(LA14_0>=Implements && LA14_0<=Interface)||LA14_0==Protected||(LA14_0>=Abstract && LA14_0<=Finally)||(LA14_0>=Private && LA14_0<=Import)||(LA14_0>=Public && LA14_0<=False)||(LA14_0>=Super && LA14_0<=With)||(LA14_0>=For && LA14_0<=Var)||(LA14_0>=As && LA14_0<=Of)||LA14_0==RULE_IDENTIFIER) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalTypesParser.g:915:3: this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypesSpecificKeywords_0=ruleTypesSpecificKeywords();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_TypesSpecificKeywords_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:926:3: this_IdentifierName_1= ruleIdentifierName
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypesIdentifierAccess().getIdentifierNameParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_IdentifierName_1=ruleIdentifierName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_IdentifierName_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypesIdentifier"


    // $ANTLR start "entryRuleBindingTypesIdentifier"
    // InternalTypesParser.g:940:1: entryRuleBindingTypesIdentifier returns [String current=null] : iv_ruleBindingTypesIdentifier= ruleBindingTypesIdentifier EOF ;
    public final String entryRuleBindingTypesIdentifier() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBindingTypesIdentifier = null;


        try {
            // InternalTypesParser.g:940:62: (iv_ruleBindingTypesIdentifier= ruleBindingTypesIdentifier EOF )
            // InternalTypesParser.g:941:2: iv_ruleBindingTypesIdentifier= ruleBindingTypesIdentifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBindingTypesIdentifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBindingTypesIdentifier=ruleBindingTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBindingTypesIdentifier.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBindingTypesIdentifier"


    // $ANTLR start "ruleBindingTypesIdentifier"
    // InternalTypesParser.g:947:1: ruleBindingTypesIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_BindingIdentifier_1= ruleBindingIdentifier ) ;
    public final AntlrDatatypeRuleToken ruleBindingTypesIdentifier() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_TypesSpecificKeywords_0 = null;

        AntlrDatatypeRuleToken this_BindingIdentifier_1 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:953:2: ( (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_BindingIdentifier_1= ruleBindingIdentifier ) )
            // InternalTypesParser.g:954:2: (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_BindingIdentifier_1= ruleBindingIdentifier )
            {
            // InternalTypesParser.g:954:2: (this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords | this_BindingIdentifier_1= ruleBindingIdentifier )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==AssignmnentCompatible||LA15_0==AutoboxedType||LA15_0==VirtualBase||LA15_0==Primitive||LA15_0==Undefined||LA15_0==Object||LA15_0==Any) ) {
                alt15=1;
            }
            else if ( ((LA15_0>=Intersection && LA15_0<=Constructor)||LA15_0==Implements||(LA15_0>=Promisify && LA15_0<=Interface)||LA15_0==Protected||LA15_0==Abstract||LA15_0==External||(LA15_0>=Private && LA15_0<=Project)||LA15_0==Public||LA15_0==Static||LA15_0==Target||(LA15_0>=Async && LA15_0<=Await)||LA15_0==Union||(LA15_0>=Yield && LA15_0<=This)||LA15_0==From||LA15_0==Type||(LA15_0>=Get && LA15_0<=Let)||(LA15_0>=Out && LA15_0<=Set)||LA15_0==As||LA15_0==Of||LA15_0==RULE_IDENTIFIER) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalTypesParser.g:955:3: this_TypesSpecificKeywords_0= ruleTypesSpecificKeywords
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getBindingTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypesSpecificKeywords_0=ruleTypesSpecificKeywords();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_TypesSpecificKeywords_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:966:3: this_BindingIdentifier_1= ruleBindingIdentifier
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getBindingTypesIdentifierAccess().getBindingIdentifierParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_BindingIdentifier_1=ruleBindingIdentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_BindingIdentifier_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBindingTypesIdentifier"


    // $ANTLR start "entryRuleVoidOrBindingIdentifier"
    // InternalTypesParser.g:980:1: entryRuleVoidOrBindingIdentifier returns [String current=null] : iv_ruleVoidOrBindingIdentifier= ruleVoidOrBindingIdentifier EOF ;
    public final String entryRuleVoidOrBindingIdentifier() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVoidOrBindingIdentifier = null;


        try {
            // InternalTypesParser.g:980:63: (iv_ruleVoidOrBindingIdentifier= ruleVoidOrBindingIdentifier EOF )
            // InternalTypesParser.g:981:2: iv_ruleVoidOrBindingIdentifier= ruleVoidOrBindingIdentifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVoidOrBindingIdentifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVoidOrBindingIdentifier=ruleVoidOrBindingIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVoidOrBindingIdentifier.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVoidOrBindingIdentifier"


    // $ANTLR start "ruleVoidOrBindingIdentifier"
    // InternalTypesParser.g:987:1: ruleVoidOrBindingIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= Void | this_BindingTypesIdentifier_1= ruleBindingTypesIdentifier ) ;
    public final AntlrDatatypeRuleToken ruleVoidOrBindingIdentifier() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_BindingTypesIdentifier_1 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:993:2: ( (kw= Void | this_BindingTypesIdentifier_1= ruleBindingTypesIdentifier ) )
            // InternalTypesParser.g:994:2: (kw= Void | this_BindingTypesIdentifier_1= ruleBindingTypesIdentifier )
            {
            // InternalTypesParser.g:994:2: (kw= Void | this_BindingTypesIdentifier_1= ruleBindingTypesIdentifier )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==Void) ) {
                alt16=1;
            }
            else if ( (LA16_0==AssignmnentCompatible||(LA16_0>=AutoboxedType && LA16_0<=Implements)||(LA16_0>=Promisify && LA16_0<=Abstract)||LA16_0==External||(LA16_0>=Private && LA16_0<=Project)||(LA16_0>=Object && LA16_0<=Public)||LA16_0==Static||LA16_0==Target||(LA16_0>=Async && LA16_0<=Await)||LA16_0==Union||(LA16_0>=Yield && LA16_0<=This)||LA16_0==From||LA16_0==Type||LA16_0==Any||(LA16_0>=Get && LA16_0<=Let)||(LA16_0>=Out && LA16_0<=Set)||LA16_0==As||LA16_0==Of||LA16_0==RULE_IDENTIFIER) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalTypesParser.g:995:3: kw= Void
                    {
                    kw=(Token)match(input,Void,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getVoidOrBindingIdentifierAccess().getVoidKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:1001:3: this_BindingTypesIdentifier_1= ruleBindingTypesIdentifier
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getVoidOrBindingIdentifierAccess().getBindingTypesIdentifierParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_BindingTypesIdentifier_1=ruleBindingTypesIdentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_BindingTypesIdentifier_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVoidOrBindingIdentifier"


    // $ANTLR start "entryRuleTypesSpecificKeywords"
    // InternalTypesParser.g:1015:1: entryRuleTypesSpecificKeywords returns [String current=null] : iv_ruleTypesSpecificKeywords= ruleTypesSpecificKeywords EOF ;
    public final String entryRuleTypesSpecificKeywords() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypesSpecificKeywords = null;


        try {
            // InternalTypesParser.g:1015:61: (iv_ruleTypesSpecificKeywords= ruleTypesSpecificKeywords EOF )
            // InternalTypesParser.g:1016:2: iv_ruleTypesSpecificKeywords= ruleTypesSpecificKeywords EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypesSpecificKeywordsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypesSpecificKeywords=ruleTypesSpecificKeywords();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypesSpecificKeywords.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypesSpecificKeywords"


    // $ANTLR start "ruleTypesSpecificKeywords"
    // InternalTypesParser.g:1022:1: ruleTypesSpecificKeywords returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= Any | kw= Undefined | kw= Object | kw= VirtualBase | kw= Primitive | kw= AutoboxedType | kw= AssignmnentCompatible ) ;
    public final AntlrDatatypeRuleToken ruleTypesSpecificKeywords() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalTypesParser.g:1028:2: ( (kw= Any | kw= Undefined | kw= Object | kw= VirtualBase | kw= Primitive | kw= AutoboxedType | kw= AssignmnentCompatible ) )
            // InternalTypesParser.g:1029:2: (kw= Any | kw= Undefined | kw= Object | kw= VirtualBase | kw= Primitive | kw= AutoboxedType | kw= AssignmnentCompatible )
            {
            // InternalTypesParser.g:1029:2: (kw= Any | kw= Undefined | kw= Object | kw= VirtualBase | kw= Primitive | kw= AutoboxedType | kw= AssignmnentCompatible )
            int alt17=7;
            switch ( input.LA(1) ) {
            case Any:
                {
                alt17=1;
                }
                break;
            case Undefined:
                {
                alt17=2;
                }
                break;
            case Object:
                {
                alt17=3;
                }
                break;
            case VirtualBase:
                {
                alt17=4;
                }
                break;
            case Primitive:
                {
                alt17=5;
                }
                break;
            case AutoboxedType:
                {
                alt17=6;
                }
                break;
            case AssignmnentCompatible:
                {
                alt17=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalTypesParser.g:1030:3: kw= Any
                    {
                    kw=(Token)match(input,Any,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getAnyKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:1036:3: kw= Undefined
                    {
                    kw=(Token)match(input,Undefined,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getUndefinedKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:1042:3: kw= Object
                    {
                    kw=(Token)match(input,Object,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getObjectKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:1048:3: kw= VirtualBase
                    {
                    kw=(Token)match(input,VirtualBase,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getVirtualBaseKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:1054:3: kw= Primitive
                    {
                    kw=(Token)match(input,Primitive,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getPrimitiveKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalTypesParser.g:1060:3: kw= AutoboxedType
                    {
                    kw=(Token)match(input,AutoboxedType,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getAutoboxedTypeKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalTypesParser.g:1066:3: kw= AssignmnentCompatible
                    {
                    kw=(Token)match(input,AssignmnentCompatible,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getAssignmnentCompatibleKeyword_6());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypesSpecificKeywords"


    // $ANTLR start "entryRuleTypesComputedPropertyName"
    // InternalTypesParser.g:1075:1: entryRuleTypesComputedPropertyName returns [String current=null] : iv_ruleTypesComputedPropertyName= ruleTypesComputedPropertyName EOF ;
    public final String entryRuleTypesComputedPropertyName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypesComputedPropertyName = null;


        try {
            // InternalTypesParser.g:1075:65: (iv_ruleTypesComputedPropertyName= ruleTypesComputedPropertyName EOF )
            // InternalTypesParser.g:1076:2: iv_ruleTypesComputedPropertyName= ruleTypesComputedPropertyName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypesComputedPropertyNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypesComputedPropertyName=ruleTypesComputedPropertyName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypesComputedPropertyName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypesComputedPropertyName"


    // $ANTLR start "ruleTypesComputedPropertyName"
    // InternalTypesParser.g:1082:1: ruleTypesComputedPropertyName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= LeftSquareBracket (this_TypesSymbolLiteralComputedName_1= ruleTypesSymbolLiteralComputedName | this_TypesStringLiteralComputedName_2= ruleTypesStringLiteralComputedName ) kw= RightSquareBracket ) ;
    public final AntlrDatatypeRuleToken ruleTypesComputedPropertyName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_TypesSymbolLiteralComputedName_1 = null;

        AntlrDatatypeRuleToken this_TypesStringLiteralComputedName_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:1088:2: ( (kw= LeftSquareBracket (this_TypesSymbolLiteralComputedName_1= ruleTypesSymbolLiteralComputedName | this_TypesStringLiteralComputedName_2= ruleTypesStringLiteralComputedName ) kw= RightSquareBracket ) )
            // InternalTypesParser.g:1089:2: (kw= LeftSquareBracket (this_TypesSymbolLiteralComputedName_1= ruleTypesSymbolLiteralComputedName | this_TypesStringLiteralComputedName_2= ruleTypesStringLiteralComputedName ) kw= RightSquareBracket )
            {
            // InternalTypesParser.g:1089:2: (kw= LeftSquareBracket (this_TypesSymbolLiteralComputedName_1= ruleTypesSymbolLiteralComputedName | this_TypesStringLiteralComputedName_2= ruleTypesStringLiteralComputedName ) kw= RightSquareBracket )
            // InternalTypesParser.g:1090:3: kw= LeftSquareBracket (this_TypesSymbolLiteralComputedName_1= ruleTypesSymbolLiteralComputedName | this_TypesStringLiteralComputedName_2= ruleTypesStringLiteralComputedName ) kw= RightSquareBracket
            {
            kw=(Token)match(input,LeftSquareBracket,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTypesComputedPropertyNameAccess().getLeftSquareBracketKeyword_0());
              		
            }
            // InternalTypesParser.g:1095:3: (this_TypesSymbolLiteralComputedName_1= ruleTypesSymbolLiteralComputedName | this_TypesStringLiteralComputedName_2= ruleTypesStringLiteralComputedName )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==AssignmnentCompatible||(LA18_0>=AutoboxedType && LA18_0<=Finally)||(LA18_0>=Private && LA18_0<=False)||(LA18_0>=Super && LA18_0<=With)||(LA18_0>=Any && LA18_0<=Var)||(LA18_0>=As && LA18_0<=Of)||LA18_0==RULE_IDENTIFIER) ) {
                alt18=1;
            }
            else if ( (LA18_0==RULE_STRING) ) {
                alt18=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalTypesParser.g:1096:4: this_TypesSymbolLiteralComputedName_1= ruleTypesSymbolLiteralComputedName
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypesComputedPropertyNameAccess().getTypesSymbolLiteralComputedNameParserRuleCall_1_0());
                      			
                    }
                    pushFollow(FOLLOW_21);
                    this_TypesSymbolLiteralComputedName_1=ruleTypesSymbolLiteralComputedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_TypesSymbolLiteralComputedName_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:1107:4: this_TypesStringLiteralComputedName_2= ruleTypesStringLiteralComputedName
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypesComputedPropertyNameAccess().getTypesStringLiteralComputedNameParserRuleCall_1_1());
                      			
                    }
                    pushFollow(FOLLOW_21);
                    this_TypesStringLiteralComputedName_2=ruleTypesStringLiteralComputedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_TypesStringLiteralComputedName_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            kw=(Token)match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTypesComputedPropertyNameAccess().getRightSquareBracketKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypesComputedPropertyName"


    // $ANTLR start "entryRuleTypesSymbolLiteralComputedName"
    // InternalTypesParser.g:1127:1: entryRuleTypesSymbolLiteralComputedName returns [String current=null] : iv_ruleTypesSymbolLiteralComputedName= ruleTypesSymbolLiteralComputedName EOF ;
    public final String entryRuleTypesSymbolLiteralComputedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypesSymbolLiteralComputedName = null;


        try {
            // InternalTypesParser.g:1127:70: (iv_ruleTypesSymbolLiteralComputedName= ruleTypesSymbolLiteralComputedName EOF )
            // InternalTypesParser.g:1128:2: iv_ruleTypesSymbolLiteralComputedName= ruleTypesSymbolLiteralComputedName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypesSymbolLiteralComputedNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypesSymbolLiteralComputedName=ruleTypesSymbolLiteralComputedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypesSymbolLiteralComputedName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypesSymbolLiteralComputedName"


    // $ANTLR start "ruleTypesSymbolLiteralComputedName"
    // InternalTypesParser.g:1134:1: ruleTypesSymbolLiteralComputedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_TypesIdentifier_0= ruleTypesIdentifier kw= FullStop this_TypesIdentifier_2= ruleTypesIdentifier ) ;
    public final AntlrDatatypeRuleToken ruleTypesSymbolLiteralComputedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_TypesIdentifier_0 = null;

        AntlrDatatypeRuleToken this_TypesIdentifier_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:1140:2: ( (this_TypesIdentifier_0= ruleTypesIdentifier kw= FullStop this_TypesIdentifier_2= ruleTypesIdentifier ) )
            // InternalTypesParser.g:1141:2: (this_TypesIdentifier_0= ruleTypesIdentifier kw= FullStop this_TypesIdentifier_2= ruleTypesIdentifier )
            {
            // InternalTypesParser.g:1141:2: (this_TypesIdentifier_0= ruleTypesIdentifier kw= FullStop this_TypesIdentifier_2= ruleTypesIdentifier )
            // InternalTypesParser.g:1142:3: this_TypesIdentifier_0= ruleTypesIdentifier kw= FullStop this_TypesIdentifier_2= ruleTypesIdentifier
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_22);
            this_TypesIdentifier_0=ruleTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_TypesIdentifier_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,FullStop,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTypesSymbolLiteralComputedNameAccess().getFullStopKeyword_1());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_2);
            this_TypesIdentifier_2=ruleTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_TypesIdentifier_2);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypesSymbolLiteralComputedName"


    // $ANTLR start "entryRuleTypesStringLiteralComputedName"
    // InternalTypesParser.g:1171:1: entryRuleTypesStringLiteralComputedName returns [String current=null] : iv_ruleTypesStringLiteralComputedName= ruleTypesStringLiteralComputedName EOF ;
    public final String entryRuleTypesStringLiteralComputedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypesStringLiteralComputedName = null;


        try {
            // InternalTypesParser.g:1171:70: (iv_ruleTypesStringLiteralComputedName= ruleTypesStringLiteralComputedName EOF )
            // InternalTypesParser.g:1172:2: iv_ruleTypesStringLiteralComputedName= ruleTypesStringLiteralComputedName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypesStringLiteralComputedNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypesStringLiteralComputedName=ruleTypesStringLiteralComputedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypesStringLiteralComputedName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypesStringLiteralComputedName"


    // $ANTLR start "ruleTypesStringLiteralComputedName"
    // InternalTypesParser.g:1178:1: ruleTypesStringLiteralComputedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_STRING_0= RULE_STRING ;
    public final AntlrDatatypeRuleToken ruleTypesStringLiteralComputedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;


        	enterRule();

        try {
            // InternalTypesParser.g:1184:2: (this_STRING_0= RULE_STRING )
            // InternalTypesParser.g:1185:2: this_STRING_0= RULE_STRING
            {
            this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_STRING_0);
              	
            }
            if ( state.backtracking==0 ) {

              		newLeafNode(this_STRING_0, grammarAccess.getTypesStringLiteralComputedNameAccess().getSTRINGTerminalRuleCall());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypesStringLiteralComputedName"


    // $ANTLR start "entryRuleTObjectPrototype"
    // InternalTypesParser.g:1195:1: entryRuleTObjectPrototype returns [EObject current=null] : iv_ruleTObjectPrototype= ruleTObjectPrototype EOF ;
    public final EObject entryRuleTObjectPrototype() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTObjectPrototype = null;


        try {
            // InternalTypesParser.g:1195:57: (iv_ruleTObjectPrototype= ruleTObjectPrototype EOF )
            // InternalTypesParser.g:1196:2: iv_ruleTObjectPrototype= ruleTObjectPrototype EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTObjectPrototypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTObjectPrototype=ruleTObjectPrototype();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTObjectPrototype; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTObjectPrototype"


    // $ANTLR start "ruleTObjectPrototype"
    // InternalTypesParser.g:1202:1: ruleTObjectPrototype returns [EObject current=null] : ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredFinal_2_0= Final ) )? otherlv_3= Object ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) (this_TypeVariables_5= ruleTypeVariables[$current] )? (otherlv_6= Extends ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Indexed ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) ) )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_10_0= ruleTAnnotation ) )* otherlv_11= LeftCurlyBracket ( (lv_ownedMembers_12_0= ruleTMember ) )* ( ( (lv_callableCtor_13_0= ruleCallableCtor ) ) ( (lv_ownedMembers_14_0= ruleTMember ) )* )? otherlv_15= RightCurlyBracket ) ;
    public final EObject ruleTObjectPrototype() throws RecognitionException {
        EObject current = null;

        Token lv_declaredProvidedByRuntime_1_0=null;
        Token lv_declaredFinal_2_0=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_11=null;
        Token otherlv_15=null;
        Enumerator lv_declaredTypeAccessModifier_0_0 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject this_TypeVariables_5 = null;

        EObject lv_superType_7_0 = null;

        EObject lv_declaredElementType_9_0 = null;

        EObject lv_annotations_10_0 = null;

        EObject lv_ownedMembers_12_0 = null;

        EObject lv_callableCtor_13_0 = null;

        EObject lv_ownedMembers_14_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:1208:2: ( ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredFinal_2_0= Final ) )? otherlv_3= Object ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) (this_TypeVariables_5= ruleTypeVariables[$current] )? (otherlv_6= Extends ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Indexed ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) ) )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_10_0= ruleTAnnotation ) )* otherlv_11= LeftCurlyBracket ( (lv_ownedMembers_12_0= ruleTMember ) )* ( ( (lv_callableCtor_13_0= ruleCallableCtor ) ) ( (lv_ownedMembers_14_0= ruleTMember ) )* )? otherlv_15= RightCurlyBracket ) )
            // InternalTypesParser.g:1209:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredFinal_2_0= Final ) )? otherlv_3= Object ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) (this_TypeVariables_5= ruleTypeVariables[$current] )? (otherlv_6= Extends ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Indexed ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) ) )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_10_0= ruleTAnnotation ) )* otherlv_11= LeftCurlyBracket ( (lv_ownedMembers_12_0= ruleTMember ) )* ( ( (lv_callableCtor_13_0= ruleCallableCtor ) ) ( (lv_ownedMembers_14_0= ruleTMember ) )* )? otherlv_15= RightCurlyBracket )
            {
            // InternalTypesParser.g:1209:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredFinal_2_0= Final ) )? otherlv_3= Object ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) (this_TypeVariables_5= ruleTypeVariables[$current] )? (otherlv_6= Extends ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Indexed ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) ) )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_10_0= ruleTAnnotation ) )* otherlv_11= LeftCurlyBracket ( (lv_ownedMembers_12_0= ruleTMember ) )* ( ( (lv_callableCtor_13_0= ruleCallableCtor ) ) ( (lv_ownedMembers_14_0= ruleTMember ) )* )? otherlv_15= RightCurlyBracket )
            // InternalTypesParser.g:1210:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredFinal_2_0= Final ) )? otherlv_3= Object ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) (this_TypeVariables_5= ruleTypeVariables[$current] )? (otherlv_6= Extends ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Indexed ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) ) )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_10_0= ruleTAnnotation ) )* otherlv_11= LeftCurlyBracket ( (lv_ownedMembers_12_0= ruleTMember ) )* ( ( (lv_callableCtor_13_0= ruleCallableCtor ) ) ( (lv_ownedMembers_14_0= ruleTMember ) )* )? otherlv_15= RightCurlyBracket
            {
            // InternalTypesParser.g:1210:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) )
            // InternalTypesParser.g:1211:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            {
            // InternalTypesParser.g:1211:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            // InternalTypesParser.g:1212:5: lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
            lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
              					}
              					set(
              						current,
              						"declaredTypeAccessModifier",
              						lv_declaredTypeAccessModifier_0_0,
              						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:1229:3: ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ProvidedByRuntime) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalTypesParser.g:1230:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    {
                    // InternalTypesParser.g:1230:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    // InternalTypesParser.g:1231:5: lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime
                    {
                    lv_declaredProvidedByRuntime_1_0=(Token)match(input,ProvidedByRuntime,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTObjectPrototypeRule());
                      					}
                      					setWithLastConsumed(current, "declaredProvidedByRuntime", true, "providedByRuntime");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalTypesParser.g:1243:3: ( (lv_declaredFinal_2_0= Final ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==Final) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalTypesParser.g:1244:4: (lv_declaredFinal_2_0= Final )
                    {
                    // InternalTypesParser.g:1244:4: (lv_declaredFinal_2_0= Final )
                    // InternalTypesParser.g:1245:5: lv_declaredFinal_2_0= Final
                    {
                    lv_declaredFinal_2_0=(Token)match(input,Final,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredFinal_2_0, grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalFinalKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTObjectPrototypeRule());
                      					}
                      					setWithLastConsumed(current, "declaredFinal", true, "final");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,Object,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getTObjectPrototypeAccess().getObjectKeyword_3());
              		
            }
            // InternalTypesParser.g:1261:3: ( (lv_name_4_0= ruleBindingTypesIdentifier ) )
            // InternalTypesParser.g:1262:4: (lv_name_4_0= ruleBindingTypesIdentifier )
            {
            // InternalTypesParser.g:1262:4: (lv_name_4_0= ruleBindingTypesIdentifier )
            // InternalTypesParser.g:1263:5: lv_name_4_0= ruleBindingTypesIdentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getNameBindingTypesIdentifierParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_27);
            lv_name_4_0=ruleBindingTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_4_0,
              						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:1280:3: (this_TypeVariables_5= ruleTypeVariables[$current] )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==LessThanSign) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalTypesParser.g:1281:4: this_TypeVariables_5= ruleTypeVariables[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getTObjectPrototypeRule());
                      				}
                      				newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getTypeVariablesParserRuleCall_5());
                      			
                    }
                    pushFollow(FOLLOW_28);
                    this_TypeVariables_5=ruleTypeVariables(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TypeVariables_5;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalTypesParser.g:1293:3: (otherlv_6= Extends ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==Extends) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalTypesParser.g:1294:4: otherlv_6= Extends ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) )
                    {
                    otherlv_6=(Token)match(input,Extends,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getTObjectPrototypeAccess().getExtendsKeyword_6_0());
                      			
                    }
                    // InternalTypesParser.g:1298:4: ( (lv_superType_7_0= ruleParameterizedTypeRefNominal ) )
                    // InternalTypesParser.g:1299:5: (lv_superType_7_0= ruleParameterizedTypeRefNominal )
                    {
                    // InternalTypesParser.g:1299:5: (lv_superType_7_0= ruleParameterizedTypeRefNominal )
                    // InternalTypesParser.g:1300:6: lv_superType_7_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getSuperTypeParameterizedTypeRefNominalParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
                    lv_superType_7_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
                      						}
                      						set(
                      							current,
                      							"superType",
                      							lv_superType_7_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:1318:3: (otherlv_8= Indexed ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==Indexed) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalTypesParser.g:1319:4: otherlv_8= Indexed ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) )
                    {
                    otherlv_8=(Token)match(input,Indexed,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getTObjectPrototypeAccess().getIndexedKeyword_7_0());
                      			
                    }
                    // InternalTypesParser.g:1323:4: ( (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal ) )
                    // InternalTypesParser.g:1324:5: (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal )
                    {
                    // InternalTypesParser.g:1324:5: (lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal )
                    // InternalTypesParser.g:1325:6: lv_declaredElementType_9_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_30);
                    lv_declaredElementType_9_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
                      						}
                      						set(
                      							current,
                      							"declaredElementType",
                      							lv_declaredElementType_9_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:1343:3: ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_10_0= ruleTAnnotation ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==CommercialAt) && (synpred3_InternalTypesParser())) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalTypesParser.g:1344:4: ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_10_0= ruleTAnnotation )
            	    {
            	    // InternalTypesParser.g:1353:4: (lv_annotations_10_0= ruleTAnnotation )
            	    // InternalTypesParser.g:1354:5: lv_annotations_10_0= ruleTAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getAnnotationsTAnnotationParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_30);
            	    lv_annotations_10_0=ruleTAnnotation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
            	      					}
            	      					add(
            	      						current,
            	      						"annotations",
            	      						lv_annotations_10_0,
            	      						"org.eclipse.n4js.ts.Types.TAnnotation");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            otherlv_11=(Token)match(input,LeftCurlyBracket,FOLLOW_31); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_11, grammarAccess.getTObjectPrototypeAccess().getLeftCurlyBracketKeyword_9());
              		
            }
            // InternalTypesParser.g:1375:3: ( (lv_ownedMembers_12_0= ruleTMember ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==ProtectedInternal||LA25_0==PublicInternal||LA25_0==Protected||(LA25_0>=Private && LA25_0<=Project)||LA25_0==Public) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalTypesParser.g:1376:4: (lv_ownedMembers_12_0= ruleTMember )
            	    {
            	    // InternalTypesParser.g:1376:4: (lv_ownedMembers_12_0= ruleTMember )
            	    // InternalTypesParser.g:1377:5: lv_ownedMembers_12_0= ruleTMember
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_10_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_31);
            	    lv_ownedMembers_12_0=ruleTMember();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
            	      					}
            	      					add(
            	      						current,
            	      						"ownedMembers",
            	      						lv_ownedMembers_12_0,
            	      						"org.eclipse.n4js.ts.Types.TMember");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

            // InternalTypesParser.g:1394:3: ( ( (lv_callableCtor_13_0= ruleCallableCtor ) ) ( (lv_ownedMembers_14_0= ruleTMember ) )* )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==LeftParenthesis) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalTypesParser.g:1395:4: ( (lv_callableCtor_13_0= ruleCallableCtor ) ) ( (lv_ownedMembers_14_0= ruleTMember ) )*
                    {
                    // InternalTypesParser.g:1395:4: ( (lv_callableCtor_13_0= ruleCallableCtor ) )
                    // InternalTypesParser.g:1396:5: (lv_callableCtor_13_0= ruleCallableCtor )
                    {
                    // InternalTypesParser.g:1396:5: (lv_callableCtor_13_0= ruleCallableCtor )
                    // InternalTypesParser.g:1397:6: lv_callableCtor_13_0= ruleCallableCtor
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_callableCtor_13_0=ruleCallableCtor();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
                      						}
                      						set(
                      							current,
                      							"callableCtor",
                      							lv_callableCtor_13_0,
                      							"org.eclipse.n4js.ts.Types.CallableCtor");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:1414:4: ( (lv_ownedMembers_14_0= ruleTMember ) )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==ProtectedInternal||LA26_0==PublicInternal||LA26_0==Protected||(LA26_0>=Private && LA26_0<=Project)||LA26_0==Public) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // InternalTypesParser.g:1415:5: (lv_ownedMembers_14_0= ruleTMember )
                    	    {
                    	    // InternalTypesParser.g:1415:5: (lv_ownedMembers_14_0= ruleTMember )
                    	    // InternalTypesParser.g:1416:6: lv_ownedMembers_14_0= ruleTMember
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_11_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_32);
                    	    lv_ownedMembers_14_0=ruleTMember();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"ownedMembers",
                    	      							lv_ownedMembers_14_0,
                    	      							"org.eclipse.n4js.ts.Types.TMember");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_15=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_15, grammarAccess.getTObjectPrototypeAccess().getRightCurlyBracketKeyword_12());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTObjectPrototype"


    // $ANTLR start "entryRuleVirtualBaseType"
    // InternalTypesParser.g:1442:1: entryRuleVirtualBaseType returns [EObject current=null] : iv_ruleVirtualBaseType= ruleVirtualBaseType EOF ;
    public final EObject entryRuleVirtualBaseType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVirtualBaseType = null;


        try {
            // InternalTypesParser.g:1442:56: (iv_ruleVirtualBaseType= ruleVirtualBaseType EOF )
            // InternalTypesParser.g:1443:2: iv_ruleVirtualBaseType= ruleVirtualBaseType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVirtualBaseTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVirtualBaseType=ruleVirtualBaseType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVirtualBaseType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVirtualBaseType"


    // $ANTLR start "ruleVirtualBaseType"
    // InternalTypesParser.g:1449:1: ruleVirtualBaseType returns [EObject current=null] : ( () otherlv_1= VirtualBase ( (lv_name_2_0= ruleBindingTypesIdentifier ) ) (otherlv_3= Indexed ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_5= LeftCurlyBracket ( (lv_ownedMembers_6_0= ruleTMember ) )* otherlv_7= RightCurlyBracket ) ;
    public final EObject ruleVirtualBaseType() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_declaredElementType_4_0 = null;

        EObject lv_ownedMembers_6_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:1455:2: ( ( () otherlv_1= VirtualBase ( (lv_name_2_0= ruleBindingTypesIdentifier ) ) (otherlv_3= Indexed ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_5= LeftCurlyBracket ( (lv_ownedMembers_6_0= ruleTMember ) )* otherlv_7= RightCurlyBracket ) )
            // InternalTypesParser.g:1456:2: ( () otherlv_1= VirtualBase ( (lv_name_2_0= ruleBindingTypesIdentifier ) ) (otherlv_3= Indexed ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_5= LeftCurlyBracket ( (lv_ownedMembers_6_0= ruleTMember ) )* otherlv_7= RightCurlyBracket )
            {
            // InternalTypesParser.g:1456:2: ( () otherlv_1= VirtualBase ( (lv_name_2_0= ruleBindingTypesIdentifier ) ) (otherlv_3= Indexed ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_5= LeftCurlyBracket ( (lv_ownedMembers_6_0= ruleTMember ) )* otherlv_7= RightCurlyBracket )
            // InternalTypesParser.g:1457:3: () otherlv_1= VirtualBase ( (lv_name_2_0= ruleBindingTypesIdentifier ) ) (otherlv_3= Indexed ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) ) )? otherlv_5= LeftCurlyBracket ( (lv_ownedMembers_6_0= ruleTMember ) )* otherlv_7= RightCurlyBracket
            {
            // InternalTypesParser.g:1457:3: ()
            // InternalTypesParser.g:1458:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseTypeAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,VirtualBase,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseKeyword_1());
              		
            }
            // InternalTypesParser.g:1468:3: ( (lv_name_2_0= ruleBindingTypesIdentifier ) )
            // InternalTypesParser.g:1469:4: (lv_name_2_0= ruleBindingTypesIdentifier )
            {
            // InternalTypesParser.g:1469:4: (lv_name_2_0= ruleBindingTypesIdentifier )
            // InternalTypesParser.g:1470:5: lv_name_2_0= ruleBindingTypesIdentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVirtualBaseTypeAccess().getNameBindingTypesIdentifierParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_name_2_0=ruleBindingTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getVirtualBaseTypeRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_2_0,
              						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:1487:3: (otherlv_3= Indexed ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==Indexed) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalTypesParser.g:1488:4: otherlv_3= Indexed ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) )
                    {
                    otherlv_3=(Token)match(input,Indexed,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getVirtualBaseTypeAccess().getIndexedKeyword_3_0());
                      			
                    }
                    // InternalTypesParser.g:1492:4: ( (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal ) )
                    // InternalTypesParser.g:1493:5: (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal )
                    {
                    // InternalTypesParser.g:1493:5: (lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal )
                    // InternalTypesParser.g:1494:6: lv_declaredElementType_4_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVirtualBaseTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
                    lv_declaredElementType_4_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVirtualBaseTypeRule());
                      						}
                      						set(
                      							current,
                      							"declaredElementType",
                      							lv_declaredElementType_4_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,LeftCurlyBracket,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getVirtualBaseTypeAccess().getLeftCurlyBracketKeyword_4());
              		
            }
            // InternalTypesParser.g:1516:3: ( (lv_ownedMembers_6_0= ruleTMember ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==ProtectedInternal||LA29_0==PublicInternal||LA29_0==Protected||(LA29_0>=Private && LA29_0<=Project)||LA29_0==Public) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalTypesParser.g:1517:4: (lv_ownedMembers_6_0= ruleTMember )
            	    {
            	    // InternalTypesParser.g:1517:4: (lv_ownedMembers_6_0= ruleTMember )
            	    // InternalTypesParser.g:1518:5: lv_ownedMembers_6_0= ruleTMember
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersTMemberParserRuleCall_5_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
            	    lv_ownedMembers_6_0=ruleTMember();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getVirtualBaseTypeRule());
            	      					}
            	      					add(
            	      						current,
            	      						"ownedMembers",
            	      						lv_ownedMembers_6_0,
            	      						"org.eclipse.n4js.ts.Types.TMember");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            otherlv_7=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getVirtualBaseTypeAccess().getRightCurlyBracketKeyword_6());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVirtualBaseType"


    // $ANTLR start "entryRuleTClass"
    // InternalTypesParser.g:1543:1: entryRuleTClass returns [EObject current=null] : iv_ruleTClass= ruleTClass EOF ;
    public final EObject entryRuleTClass() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTClass = null;


        try {
            // InternalTypesParser.g:1543:47: (iv_ruleTClass= ruleTClass EOF )
            // InternalTypesParser.g:1544:2: iv_ruleTClass= ruleTClass EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTClassRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTClass=ruleTClass();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTClass; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTClass"


    // $ANTLR start "ruleTClass"
    // InternalTypesParser.g:1550:1: ruleTClass returns [EObject current=null] : ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredAbstract_2_0= Abstract ) )? ( (lv_declaredFinal_3_0= Final ) )? otherlv_4= Class this_TClassOrInterfaceHeader_5= ruleTClassOrInterfaceHeader[$current] (otherlv_6= Extends ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Implements ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) ) (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_12_0= ruleTAnnotation ) )* otherlv_13= LeftCurlyBracket ( (lv_ownedMembers_14_0= ruleTMember ) )* ( ( (lv_callableCtor_15_0= ruleCallableCtor ) ) ( (lv_ownedMembers_16_0= ruleTMember ) )* )? otherlv_17= RightCurlyBracket ) ;
    public final EObject ruleTClass() throws RecognitionException {
        EObject current = null;

        Token lv_declaredProvidedByRuntime_1_0=null;
        Token lv_declaredAbstract_2_0=null;
        Token lv_declaredFinal_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_13=null;
        Token otherlv_17=null;
        Enumerator lv_declaredTypeAccessModifier_0_0 = null;

        EObject this_TClassOrInterfaceHeader_5 = null;

        EObject lv_superClassRef_7_0 = null;

        EObject lv_implementedInterfaceRefs_9_0 = null;

        EObject lv_implementedInterfaceRefs_11_0 = null;

        EObject lv_annotations_12_0 = null;

        EObject lv_ownedMembers_14_0 = null;

        EObject lv_callableCtor_15_0 = null;

        EObject lv_ownedMembers_16_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:1556:2: ( ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredAbstract_2_0= Abstract ) )? ( (lv_declaredFinal_3_0= Final ) )? otherlv_4= Class this_TClassOrInterfaceHeader_5= ruleTClassOrInterfaceHeader[$current] (otherlv_6= Extends ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Implements ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) ) (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_12_0= ruleTAnnotation ) )* otherlv_13= LeftCurlyBracket ( (lv_ownedMembers_14_0= ruleTMember ) )* ( ( (lv_callableCtor_15_0= ruleCallableCtor ) ) ( (lv_ownedMembers_16_0= ruleTMember ) )* )? otherlv_17= RightCurlyBracket ) )
            // InternalTypesParser.g:1557:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredAbstract_2_0= Abstract ) )? ( (lv_declaredFinal_3_0= Final ) )? otherlv_4= Class this_TClassOrInterfaceHeader_5= ruleTClassOrInterfaceHeader[$current] (otherlv_6= Extends ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Implements ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) ) (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_12_0= ruleTAnnotation ) )* otherlv_13= LeftCurlyBracket ( (lv_ownedMembers_14_0= ruleTMember ) )* ( ( (lv_callableCtor_15_0= ruleCallableCtor ) ) ( (lv_ownedMembers_16_0= ruleTMember ) )* )? otherlv_17= RightCurlyBracket )
            {
            // InternalTypesParser.g:1557:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredAbstract_2_0= Abstract ) )? ( (lv_declaredFinal_3_0= Final ) )? otherlv_4= Class this_TClassOrInterfaceHeader_5= ruleTClassOrInterfaceHeader[$current] (otherlv_6= Extends ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Implements ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) ) (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_12_0= ruleTAnnotation ) )* otherlv_13= LeftCurlyBracket ( (lv_ownedMembers_14_0= ruleTMember ) )* ( ( (lv_callableCtor_15_0= ruleCallableCtor ) ) ( (lv_ownedMembers_16_0= ruleTMember ) )* )? otherlv_17= RightCurlyBracket )
            // InternalTypesParser.g:1558:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? ( (lv_declaredAbstract_2_0= Abstract ) )? ( (lv_declaredFinal_3_0= Final ) )? otherlv_4= Class this_TClassOrInterfaceHeader_5= ruleTClassOrInterfaceHeader[$current] (otherlv_6= Extends ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) ) )? (otherlv_8= Implements ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) ) (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_12_0= ruleTAnnotation ) )* otherlv_13= LeftCurlyBracket ( (lv_ownedMembers_14_0= ruleTMember ) )* ( ( (lv_callableCtor_15_0= ruleCallableCtor ) ) ( (lv_ownedMembers_16_0= ruleTMember ) )* )? otherlv_17= RightCurlyBracket
            {
            // InternalTypesParser.g:1558:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) )
            // InternalTypesParser.g:1559:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            {
            // InternalTypesParser.g:1559:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            // InternalTypesParser.g:1560:5: lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_33);
            lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTClassRule());
              					}
              					set(
              						current,
              						"declaredTypeAccessModifier",
              						lv_declaredTypeAccessModifier_0_0,
              						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:1577:3: ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ProvidedByRuntime) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalTypesParser.g:1578:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    {
                    // InternalTypesParser.g:1578:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    // InternalTypesParser.g:1579:5: lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime
                    {
                    lv_declaredProvidedByRuntime_1_0=(Token)match(input,ProvidedByRuntime,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTClassRule());
                      					}
                      					setWithLastConsumed(current, "declaredProvidedByRuntime", true, "providedByRuntime");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalTypesParser.g:1591:3: ( (lv_declaredAbstract_2_0= Abstract ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==Abstract) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalTypesParser.g:1592:4: (lv_declaredAbstract_2_0= Abstract )
                    {
                    // InternalTypesParser.g:1592:4: (lv_declaredAbstract_2_0= Abstract )
                    // InternalTypesParser.g:1593:5: lv_declaredAbstract_2_0= Abstract
                    {
                    lv_declaredAbstract_2_0=(Token)match(input,Abstract,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredAbstract_2_0, grammarAccess.getTClassAccess().getDeclaredAbstractAbstractKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTClassRule());
                      					}
                      					setWithLastConsumed(current, "declaredAbstract", true, "abstract");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalTypesParser.g:1605:3: ( (lv_declaredFinal_3_0= Final ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==Final) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalTypesParser.g:1606:4: (lv_declaredFinal_3_0= Final )
                    {
                    // InternalTypesParser.g:1606:4: (lv_declaredFinal_3_0= Final )
                    // InternalTypesParser.g:1607:5: lv_declaredFinal_3_0= Final
                    {
                    lv_declaredFinal_3_0=(Token)match(input,Final,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredFinal_3_0, grammarAccess.getTClassAccess().getDeclaredFinalFinalKeyword_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTClassRule());
                      					}
                      					setWithLastConsumed(current, "declaredFinal", true, "final");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,Class,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTClassAccess().getClassKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTClassRule());
              			}
              			newCompositeNode(grammarAccess.getTClassAccess().getTClassOrInterfaceHeaderParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_38);
            this_TClassOrInterfaceHeader_5=ruleTClassOrInterfaceHeader(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TClassOrInterfaceHeader_5;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:1634:3: (otherlv_6= Extends ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==Extends) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalTypesParser.g:1635:4: otherlv_6= Extends ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) )
                    {
                    otherlv_6=(Token)match(input,Extends,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getTClassAccess().getExtendsKeyword_6_0());
                      			
                    }
                    // InternalTypesParser.g:1639:4: ( (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal ) )
                    // InternalTypesParser.g:1640:5: (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal )
                    {
                    // InternalTypesParser.g:1640:5: (lv_superClassRef_7_0= ruleParameterizedTypeRefNominal )
                    // InternalTypesParser.g:1641:6: lv_superClassRef_7_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTClassAccess().getSuperClassRefParameterizedTypeRefNominalParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_39);
                    lv_superClassRef_7_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTClassRule());
                      						}
                      						set(
                      							current,
                      							"superClassRef",
                      							lv_superClassRef_7_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:1659:3: (otherlv_8= Implements ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) ) (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )* )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==Implements) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalTypesParser.g:1660:4: otherlv_8= Implements ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) ) (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )*
                    {
                    otherlv_8=(Token)match(input,Implements,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getTClassAccess().getImplementsKeyword_7_0());
                      			
                    }
                    // InternalTypesParser.g:1664:4: ( (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal ) )
                    // InternalTypesParser.g:1665:5: (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal )
                    {
                    // InternalTypesParser.g:1665:5: (lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal )
                    // InternalTypesParser.g:1666:6: lv_implementedInterfaceRefs_9_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_40);
                    lv_implementedInterfaceRefs_9_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTClassRule());
                      						}
                      						add(
                      							current,
                      							"implementedInterfaceRefs",
                      							lv_implementedInterfaceRefs_9_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:1683:4: (otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) ) )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==Comma) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // InternalTypesParser.g:1684:5: otherlv_10= Comma ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) )
                    	    {
                    	    otherlv_10=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_10, grammarAccess.getTClassAccess().getCommaKeyword_7_2_0());
                    	      				
                    	    }
                    	    // InternalTypesParser.g:1688:5: ( (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal ) )
                    	    // InternalTypesParser.g:1689:6: (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal )
                    	    {
                    	    // InternalTypesParser.g:1689:6: (lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal )
                    	    // InternalTypesParser.g:1690:7: lv_implementedInterfaceRefs_11_0= ruleParameterizedTypeRefNominal
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_40);
                    	    lv_implementedInterfaceRefs_11_0=ruleParameterizedTypeRefNominal();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getTClassRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"implementedInterfaceRefs",
                    	      								lv_implementedInterfaceRefs_11_0,
                    	      								"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalTypesParser.g:1709:3: ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_12_0= ruleTAnnotation ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==CommercialAt) && (synpred4_InternalTypesParser())) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalTypesParser.g:1710:4: ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_12_0= ruleTAnnotation )
            	    {
            	    // InternalTypesParser.g:1719:4: (lv_annotations_12_0= ruleTAnnotation )
            	    // InternalTypesParser.g:1720:5: lv_annotations_12_0= ruleTAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getTClassAccess().getAnnotationsTAnnotationParserRuleCall_8_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_30);
            	    lv_annotations_12_0=ruleTAnnotation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getTClassRule());
            	      					}
            	      					add(
            	      						current,
            	      						"annotations",
            	      						lv_annotations_12_0,
            	      						"org.eclipse.n4js.ts.Types.TAnnotation");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            otherlv_13=(Token)match(input,LeftCurlyBracket,FOLLOW_31); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_13, grammarAccess.getTClassAccess().getLeftCurlyBracketKeyword_9());
              		
            }
            // InternalTypesParser.g:1741:3: ( (lv_ownedMembers_14_0= ruleTMember ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==ProtectedInternal||LA37_0==PublicInternal||LA37_0==Protected||(LA37_0>=Private && LA37_0<=Project)||LA37_0==Public) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalTypesParser.g:1742:4: (lv_ownedMembers_14_0= ruleTMember )
            	    {
            	    // InternalTypesParser.g:1742:4: (lv_ownedMembers_14_0= ruleTMember )
            	    // InternalTypesParser.g:1743:5: lv_ownedMembers_14_0= ruleTMember
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_10_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_31);
            	    lv_ownedMembers_14_0=ruleTMember();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getTClassRule());
            	      					}
            	      					add(
            	      						current,
            	      						"ownedMembers",
            	      						lv_ownedMembers_14_0,
            	      						"org.eclipse.n4js.ts.Types.TMember");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);

            // InternalTypesParser.g:1760:3: ( ( (lv_callableCtor_15_0= ruleCallableCtor ) ) ( (lv_ownedMembers_16_0= ruleTMember ) )* )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==LeftParenthesis) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalTypesParser.g:1761:4: ( (lv_callableCtor_15_0= ruleCallableCtor ) ) ( (lv_ownedMembers_16_0= ruleTMember ) )*
                    {
                    // InternalTypesParser.g:1761:4: ( (lv_callableCtor_15_0= ruleCallableCtor ) )
                    // InternalTypesParser.g:1762:5: (lv_callableCtor_15_0= ruleCallableCtor )
                    {
                    // InternalTypesParser.g:1762:5: (lv_callableCtor_15_0= ruleCallableCtor )
                    // InternalTypesParser.g:1763:6: lv_callableCtor_15_0= ruleCallableCtor
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTClassAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_callableCtor_15_0=ruleCallableCtor();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTClassRule());
                      						}
                      						set(
                      							current,
                      							"callableCtor",
                      							lv_callableCtor_15_0,
                      							"org.eclipse.n4js.ts.Types.CallableCtor");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:1780:4: ( (lv_ownedMembers_16_0= ruleTMember ) )*
                    loop38:
                    do {
                        int alt38=2;
                        int LA38_0 = input.LA(1);

                        if ( (LA38_0==ProtectedInternal||LA38_0==PublicInternal||LA38_0==Protected||(LA38_0>=Private && LA38_0<=Project)||LA38_0==Public) ) {
                            alt38=1;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // InternalTypesParser.g:1781:5: (lv_ownedMembers_16_0= ruleTMember )
                    	    {
                    	    // InternalTypesParser.g:1781:5: (lv_ownedMembers_16_0= ruleTMember )
                    	    // InternalTypesParser.g:1782:6: lv_ownedMembers_16_0= ruleTMember
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_11_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_32);
                    	    lv_ownedMembers_16_0=ruleTMember();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getTClassRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"ownedMembers",
                    	      							lv_ownedMembers_16_0,
                    	      							"org.eclipse.n4js.ts.Types.TMember");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_17=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_17, grammarAccess.getTClassAccess().getRightCurlyBracketKeyword_12());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTClass"


    // $ANTLR start "entryRuleTInterface"
    // InternalTypesParser.g:1808:1: entryRuleTInterface returns [EObject current=null] : iv_ruleTInterface= ruleTInterface EOF ;
    public final EObject entryRuleTInterface() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTInterface = null;


        try {
            // InternalTypesParser.g:1808:51: (iv_ruleTInterface= ruleTInterface EOF )
            // InternalTypesParser.g:1809:2: iv_ruleTInterface= ruleTInterface EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTInterfaceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTInterface=ruleTInterface();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTInterface; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTInterface"


    // $ANTLR start "ruleTInterface"
    // InternalTypesParser.g:1815:1: ruleTInterface returns [EObject current=null] : ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Interface this_TClassOrInterfaceHeader_3= ruleTClassOrInterfaceHeader[$current] (otherlv_4= Extends ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) ) (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_8_0= ruleTAnnotation ) )* otherlv_9= LeftCurlyBracket ( (lv_ownedMembers_10_0= ruleTMember ) )* otherlv_11= RightCurlyBracket ) ;
    public final EObject ruleTInterface() throws RecognitionException {
        EObject current = null;

        Token lv_declaredProvidedByRuntime_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Enumerator lv_declaredTypeAccessModifier_0_0 = null;

        EObject this_TClassOrInterfaceHeader_3 = null;

        EObject lv_superInterfaceRefs_5_0 = null;

        EObject lv_superInterfaceRefs_7_0 = null;

        EObject lv_annotations_8_0 = null;

        EObject lv_ownedMembers_10_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:1821:2: ( ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Interface this_TClassOrInterfaceHeader_3= ruleTClassOrInterfaceHeader[$current] (otherlv_4= Extends ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) ) (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_8_0= ruleTAnnotation ) )* otherlv_9= LeftCurlyBracket ( (lv_ownedMembers_10_0= ruleTMember ) )* otherlv_11= RightCurlyBracket ) )
            // InternalTypesParser.g:1822:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Interface this_TClassOrInterfaceHeader_3= ruleTClassOrInterfaceHeader[$current] (otherlv_4= Extends ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) ) (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_8_0= ruleTAnnotation ) )* otherlv_9= LeftCurlyBracket ( (lv_ownedMembers_10_0= ruleTMember ) )* otherlv_11= RightCurlyBracket )
            {
            // InternalTypesParser.g:1822:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Interface this_TClassOrInterfaceHeader_3= ruleTClassOrInterfaceHeader[$current] (otherlv_4= Extends ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) ) (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_8_0= ruleTAnnotation ) )* otherlv_9= LeftCurlyBracket ( (lv_ownedMembers_10_0= ruleTMember ) )* otherlv_11= RightCurlyBracket )
            // InternalTypesParser.g:1823:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Interface this_TClassOrInterfaceHeader_3= ruleTClassOrInterfaceHeader[$current] (otherlv_4= Extends ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) ) (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )* )? ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_8_0= ruleTAnnotation ) )* otherlv_9= LeftCurlyBracket ( (lv_ownedMembers_10_0= ruleTMember ) )* otherlv_11= RightCurlyBracket
            {
            // InternalTypesParser.g:1823:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) )
            // InternalTypesParser.g:1824:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            {
            // InternalTypesParser.g:1824:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            // InternalTypesParser.g:1825:5: lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_41);
            lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTInterfaceRule());
              					}
              					set(
              						current,
              						"declaredTypeAccessModifier",
              						lv_declaredTypeAccessModifier_0_0,
              						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:1842:3: ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==ProvidedByRuntime) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalTypesParser.g:1843:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    {
                    // InternalTypesParser.g:1843:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    // InternalTypesParser.g:1844:5: lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime
                    {
                    lv_declaredProvidedByRuntime_1_0=(Token)match(input,ProvidedByRuntime,FOLLOW_42); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTInterfaceRule());
                      					}
                      					setWithLastConsumed(current, "declaredProvidedByRuntime", true, "providedByRuntime");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,Interface,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getTInterfaceAccess().getInterfaceKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTInterfaceRule());
              			}
              			newCompositeNode(grammarAccess.getTInterfaceAccess().getTClassOrInterfaceHeaderParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_43);
            this_TClassOrInterfaceHeader_3=ruleTClassOrInterfaceHeader(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TClassOrInterfaceHeader_3;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:1871:3: (otherlv_4= Extends ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) ) (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )* )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==Extends) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalTypesParser.g:1872:4: otherlv_4= Extends ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) ) (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )*
                    {
                    otherlv_4=(Token)match(input,Extends,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getTInterfaceAccess().getExtendsKeyword_4_0());
                      			
                    }
                    // InternalTypesParser.g:1876:4: ( (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal ) )
                    // InternalTypesParser.g:1877:5: (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal )
                    {
                    // InternalTypesParser.g:1877:5: (lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal )
                    // InternalTypesParser.g:1878:6: lv_superInterfaceRefs_5_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_40);
                    lv_superInterfaceRefs_5_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTInterfaceRule());
                      						}
                      						add(
                      							current,
                      							"superInterfaceRefs",
                      							lv_superInterfaceRefs_5_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:1895:4: (otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) ) )*
                    loop41:
                    do {
                        int alt41=2;
                        int LA41_0 = input.LA(1);

                        if ( (LA41_0==Comma) ) {
                            alt41=1;
                        }


                        switch (alt41) {
                    	case 1 :
                    	    // InternalTypesParser.g:1896:5: otherlv_6= Comma ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) )
                    	    {
                    	    otherlv_6=(Token)match(input,Comma,FOLLOW_14); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_6, grammarAccess.getTInterfaceAccess().getCommaKeyword_4_2_0());
                    	      				
                    	    }
                    	    // InternalTypesParser.g:1900:5: ( (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal ) )
                    	    // InternalTypesParser.g:1901:6: (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal )
                    	    {
                    	    // InternalTypesParser.g:1901:6: (lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal )
                    	    // InternalTypesParser.g:1902:7: lv_superInterfaceRefs_7_0= ruleParameterizedTypeRefNominal
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_40);
                    	    lv_superInterfaceRefs_7_0=ruleParameterizedTypeRefNominal();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getTInterfaceRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"superInterfaceRefs",
                    	      								lv_superInterfaceRefs_7_0,
                    	      								"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop41;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalTypesParser.g:1921:3: ( ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_8_0= ruleTAnnotation ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==CommercialAt) && (synpred5_InternalTypesParser())) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalTypesParser.g:1922:4: ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )=> (lv_annotations_8_0= ruleTAnnotation )
            	    {
            	    // InternalTypesParser.g:1931:4: (lv_annotations_8_0= ruleTAnnotation )
            	    // InternalTypesParser.g:1932:5: lv_annotations_8_0= ruleTAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getTInterfaceAccess().getAnnotationsTAnnotationParserRuleCall_5_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_30);
            	    lv_annotations_8_0=ruleTAnnotation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getTInterfaceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"annotations",
            	      						lv_annotations_8_0,
            	      						"org.eclipse.n4js.ts.Types.TAnnotation");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            otherlv_9=(Token)match(input,LeftCurlyBracket,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_9, grammarAccess.getTInterfaceAccess().getLeftCurlyBracketKeyword_6());
              		
            }
            // InternalTypesParser.g:1953:3: ( (lv_ownedMembers_10_0= ruleTMember ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==ProtectedInternal||LA44_0==PublicInternal||LA44_0==Protected||(LA44_0>=Private && LA44_0<=Project)||LA44_0==Public) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalTypesParser.g:1954:4: (lv_ownedMembers_10_0= ruleTMember )
            	    {
            	    // InternalTypesParser.g:1954:4: (lv_ownedMembers_10_0= ruleTMember )
            	    // InternalTypesParser.g:1955:5: lv_ownedMembers_10_0= ruleTMember
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getTInterfaceAccess().getOwnedMembersTMemberParserRuleCall_7_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
            	    lv_ownedMembers_10_0=ruleTMember();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getTInterfaceRule());
            	      					}
            	      					add(
            	      						current,
            	      						"ownedMembers",
            	      						lv_ownedMembers_10_0,
            	      						"org.eclipse.n4js.ts.Types.TMember");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            otherlv_11=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_11, grammarAccess.getTInterfaceAccess().getRightCurlyBracketKeyword_8());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTInterface"


    // $ANTLR start "entryRuleTypeVariable"
    // InternalTypesParser.g:1980:1: entryRuleTypeVariable returns [EObject current=null] : iv_ruleTypeVariable= ruleTypeVariable EOF ;
    public final EObject entryRuleTypeVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeVariable = null;


        try {
            // InternalTypesParser.g:1980:53: (iv_ruleTypeVariable= ruleTypeVariable EOF )
            // InternalTypesParser.g:1981:2: iv_ruleTypeVariable= ruleTypeVariable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeVariableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeVariable=ruleTypeVariable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeVariable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeVariable"


    // $ANTLR start "ruleTypeVariable"
    // InternalTypesParser.g:1987:1: ruleTypeVariable returns [EObject current=null] : ( ( (lv_name_0_0= RULE_IDENTIFIER ) ) (otherlv_1= Extends ( (lv_declaredUpperBound_2_0= ruleTypeRef ) ) )? ) ;
    public final EObject ruleTypeVariable() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_declaredUpperBound_2_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:1993:2: ( ( ( (lv_name_0_0= RULE_IDENTIFIER ) ) (otherlv_1= Extends ( (lv_declaredUpperBound_2_0= ruleTypeRef ) ) )? ) )
            // InternalTypesParser.g:1994:2: ( ( (lv_name_0_0= RULE_IDENTIFIER ) ) (otherlv_1= Extends ( (lv_declaredUpperBound_2_0= ruleTypeRef ) ) )? )
            {
            // InternalTypesParser.g:1994:2: ( ( (lv_name_0_0= RULE_IDENTIFIER ) ) (otherlv_1= Extends ( (lv_declaredUpperBound_2_0= ruleTypeRef ) ) )? )
            // InternalTypesParser.g:1995:3: ( (lv_name_0_0= RULE_IDENTIFIER ) ) (otherlv_1= Extends ( (lv_declaredUpperBound_2_0= ruleTypeRef ) ) )?
            {
            // InternalTypesParser.g:1995:3: ( (lv_name_0_0= RULE_IDENTIFIER ) )
            // InternalTypesParser.g:1996:4: (lv_name_0_0= RULE_IDENTIFIER )
            {
            // InternalTypesParser.g:1996:4: (lv_name_0_0= RULE_IDENTIFIER )
            // InternalTypesParser.g:1997:5: lv_name_0_0= RULE_IDENTIFIER
            {
            lv_name_0_0=(Token)match(input,RULE_IDENTIFIER,FOLLOW_44); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getTypeVariableRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
              				
            }

            }


            }

            // InternalTypesParser.g:2013:3: (otherlv_1= Extends ( (lv_declaredUpperBound_2_0= ruleTypeRef ) ) )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==Extends) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalTypesParser.g:2014:4: otherlv_1= Extends ( (lv_declaredUpperBound_2_0= ruleTypeRef ) )
                    {
                    otherlv_1=(Token)match(input,Extends,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getTypeVariableAccess().getExtendsKeyword_1_0());
                      			
                    }
                    // InternalTypesParser.g:2018:4: ( (lv_declaredUpperBound_2_0= ruleTypeRef ) )
                    // InternalTypesParser.g:2019:5: (lv_declaredUpperBound_2_0= ruleTypeRef )
                    {
                    // InternalTypesParser.g:2019:5: (lv_declaredUpperBound_2_0= ruleTypeRef )
                    // InternalTypesParser.g:2020:6: lv_declaredUpperBound_2_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_declaredUpperBound_2_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTypeVariableRule());
                      						}
                      						set(
                      							current,
                      							"declaredUpperBound",
                      							lv_declaredUpperBound_2_0,
                      							"org.eclipse.n4js.ts.Types.TypeRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeVariable"


    // $ANTLR start "ruleTClassOrInterfaceHeader"
    // InternalTypesParser.g:2043:1: ruleTClassOrInterfaceHeader[EObject in_current] returns [EObject current=in_current] : ( ( (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator ) )? ( (lv_name_1_0= ruleBindingTypesIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= superTypeVariable ) ) (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )* otherlv_6= GreaterThanSign )? ) ;
    public final EObject ruleTClassOrInterfaceHeader(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_typingStrategy_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_typeVars_3_0 = null;

        EObject lv_typeVars_5_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2049:2: ( ( ( (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator ) )? ( (lv_name_1_0= ruleBindingTypesIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= superTypeVariable ) ) (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )* otherlv_6= GreaterThanSign )? ) )
            // InternalTypesParser.g:2050:2: ( ( (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator ) )? ( (lv_name_1_0= ruleBindingTypesIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= superTypeVariable ) ) (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )* otherlv_6= GreaterThanSign )? )
            {
            // InternalTypesParser.g:2050:2: ( ( (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator ) )? ( (lv_name_1_0= ruleBindingTypesIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= superTypeVariable ) ) (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )* otherlv_6= GreaterThanSign )? )
            // InternalTypesParser.g:2051:3: ( (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator ) )? ( (lv_name_1_0= ruleBindingTypesIdentifier ) ) (otherlv_2= LessThanSign ( (lv_typeVars_3_0= superTypeVariable ) ) (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )* otherlv_6= GreaterThanSign )?
            {
            // InternalTypesParser.g:2051:3: ( (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator ) )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==Tilde) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalTypesParser.g:2052:4: (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator )
                    {
                    // InternalTypesParser.g:2052:4: (lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator )
                    // InternalTypesParser.g:2053:5: lv_typingStrategy_0_0= ruleTypingStrategyDefSiteOperator
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_10);
                    lv_typingStrategy_0_0=ruleTypingStrategyDefSiteOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
                      					}
                      					set(
                      						current,
                      						"typingStrategy",
                      						lv_typingStrategy_0_0,
                      						"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalTypesParser.g:2070:3: ( (lv_name_1_0= ruleBindingTypesIdentifier ) )
            // InternalTypesParser.g:2071:4: (lv_name_1_0= ruleBindingTypesIdentifier )
            {
            // InternalTypesParser.g:2071:4: (lv_name_1_0= ruleBindingTypesIdentifier )
            // InternalTypesParser.g:2072:5: lv_name_1_0= ruleBindingTypesIdentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getNameBindingTypesIdentifierParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_45);
            lv_name_1_0=ruleBindingTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:2089:3: (otherlv_2= LessThanSign ( (lv_typeVars_3_0= superTypeVariable ) ) (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )* otherlv_6= GreaterThanSign )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==LessThanSign) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalTypesParser.g:2090:4: otherlv_2= LessThanSign ( (lv_typeVars_3_0= superTypeVariable ) ) (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )* otherlv_6= GreaterThanSign
                    {
                    otherlv_2=(Token)match(input,LessThanSign,FOLLOW_46); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getTClassOrInterfaceHeaderAccess().getLessThanSignKeyword_2_0());
                      			
                    }
                    // InternalTypesParser.g:2094:4: ( (lv_typeVars_3_0= superTypeVariable ) )
                    // InternalTypesParser.g:2095:5: (lv_typeVars_3_0= superTypeVariable )
                    {
                    // InternalTypesParser.g:2095:5: (lv_typeVars_3_0= superTypeVariable )
                    // InternalTypesParser.g:2096:6: lv_typeVars_3_0= superTypeVariable
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
                    lv_typeVars_3_0=superTypeVariable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
                      						}
                      						add(
                      							current,
                      							"typeVars",
                      							lv_typeVars_3_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.TypeVariable");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:2113:4: (otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) ) )*
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==Comma) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // InternalTypesParser.g:2114:5: otherlv_4= Comma ( (lv_typeVars_5_0= superTypeVariable ) )
                    	    {
                    	    otherlv_4=(Token)match(input,Comma,FOLLOW_46); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_4, grammarAccess.getTClassOrInterfaceHeaderAccess().getCommaKeyword_2_2_0());
                    	      				
                    	    }
                    	    // InternalTypesParser.g:2118:5: ( (lv_typeVars_5_0= superTypeVariable ) )
                    	    // InternalTypesParser.g:2119:6: (lv_typeVars_5_0= superTypeVariable )
                    	    {
                    	    // InternalTypesParser.g:2119:6: (lv_typeVars_5_0= superTypeVariable )
                    	    // InternalTypesParser.g:2120:7: lv_typeVars_5_0= superTypeVariable
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_47);
                    	    lv_typeVars_5_0=superTypeVariable();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"typeVars",
                    	      								lv_typeVars_5_0,
                    	      								"org.eclipse.n4js.ts.TypeExpressions.TypeVariable");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getTClassOrInterfaceHeaderAccess().getGreaterThanSignKeyword_2_3());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTClassOrInterfaceHeader"


    // $ANTLR start "entryRuleCallableCtor"
    // InternalTypesParser.g:2147:1: entryRuleCallableCtor returns [EObject current=null] : iv_ruleCallableCtor= ruleCallableCtor EOF ;
    public final EObject entryRuleCallableCtor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCallableCtor = null;


        try {
            // InternalTypesParser.g:2147:53: (iv_ruleCallableCtor= ruleCallableCtor EOF )
            // InternalTypesParser.g:2148:2: iv_ruleCallableCtor= ruleCallableCtor EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCallableCtorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCallableCtor=ruleCallableCtor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCallableCtor; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCallableCtor"


    // $ANTLR start "ruleCallableCtor"
    // InternalTypesParser.g:2154:1: ruleCallableCtor returns [EObject current=null] : ( () this_TFormalParameters_1= ruleTFormalParameters[$current] (this_ColonSepReturnTypeRef_2= ruleColonSepReturnTypeRef[$current] )? (otherlv_3= Semicolon )? ) ;
    public final EObject ruleCallableCtor() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        EObject this_TFormalParameters_1 = null;

        EObject this_ColonSepReturnTypeRef_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2160:2: ( ( () this_TFormalParameters_1= ruleTFormalParameters[$current] (this_ColonSepReturnTypeRef_2= ruleColonSepReturnTypeRef[$current] )? (otherlv_3= Semicolon )? ) )
            // InternalTypesParser.g:2161:2: ( () this_TFormalParameters_1= ruleTFormalParameters[$current] (this_ColonSepReturnTypeRef_2= ruleColonSepReturnTypeRef[$current] )? (otherlv_3= Semicolon )? )
            {
            // InternalTypesParser.g:2161:2: ( () this_TFormalParameters_1= ruleTFormalParameters[$current] (this_ColonSepReturnTypeRef_2= ruleColonSepReturnTypeRef[$current] )? (otherlv_3= Semicolon )? )
            // InternalTypesParser.g:2162:3: () this_TFormalParameters_1= ruleTFormalParameters[$current] (this_ColonSepReturnTypeRef_2= ruleColonSepReturnTypeRef[$current] )? (otherlv_3= Semicolon )?
            {
            // InternalTypesParser.g:2162:3: ()
            // InternalTypesParser.g:2163:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getCallableCtorAccess().getTMethodAction_0(),
              					current);
              			
            }

            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getCallableCtorRule());
              			}
              			newCompositeNode(grammarAccess.getCallableCtorAccess().getTFormalParametersParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_48);
            this_TFormalParameters_1=ruleTFormalParameters(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TFormalParameters_1;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:2180:3: (this_ColonSepReturnTypeRef_2= ruleColonSepReturnTypeRef[$current] )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==Colon) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalTypesParser.g:2181:4: this_ColonSepReturnTypeRef_2= ruleColonSepReturnTypeRef[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getCallableCtorRule());
                      				}
                      				newCompositeNode(grammarAccess.getCallableCtorAccess().getColonSepReturnTypeRefParserRuleCall_2());
                      			
                    }
                    pushFollow(FOLLOW_49);
                    this_ColonSepReturnTypeRef_2=ruleColonSepReturnTypeRef(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ColonSepReturnTypeRef_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalTypesParser.g:2193:3: (otherlv_3= Semicolon )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==Semicolon) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalTypesParser.g:2194:4: otherlv_3= Semicolon
                    {
                    otherlv_3=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getCallableCtorAccess().getSemicolonKeyword_3());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCallableCtor"


    // $ANTLR start "ruleTFormalParameters"
    // InternalTypesParser.g:2204:1: ruleTFormalParameters[EObject in_current] returns [EObject current=in_current] : (otherlv_0= LeftParenthesis ( ( (lv_fpars_1_0= ruleTFormalParameter ) ) (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )* )? otherlv_4= RightParenthesis ) ;
    public final EObject ruleTFormalParameters(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_fpars_1_0 = null;

        EObject lv_fpars_3_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2210:2: ( (otherlv_0= LeftParenthesis ( ( (lv_fpars_1_0= ruleTFormalParameter ) ) (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )* )? otherlv_4= RightParenthesis ) )
            // InternalTypesParser.g:2211:2: (otherlv_0= LeftParenthesis ( ( (lv_fpars_1_0= ruleTFormalParameter ) ) (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )* )? otherlv_4= RightParenthesis )
            {
            // InternalTypesParser.g:2211:2: (otherlv_0= LeftParenthesis ( ( (lv_fpars_1_0= ruleTFormalParameter ) ) (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )* )? otherlv_4= RightParenthesis )
            // InternalTypesParser.g:2212:3: otherlv_0= LeftParenthesis ( ( (lv_fpars_1_0= ruleTFormalParameter ) ) (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )* )? otherlv_4= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_50); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getTFormalParametersAccess().getLeftParenthesisKeyword_0());
              		
            }
            // InternalTypesParser.g:2216:3: ( ( (lv_fpars_1_0= ruleTFormalParameter ) ) (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )* )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=Intersection && LA52_0<=Constructor)||LA52_0==Implements||(LA52_0>=Promisify && LA52_0<=Interface)||LA52_0==Protected||LA52_0==Abstract||LA52_0==External||(LA52_0>=Private && LA52_0<=Project)||LA52_0==Public||LA52_0==Static||LA52_0==Target||(LA52_0>=Async && LA52_0<=Await)||LA52_0==Union||(LA52_0>=Yield && LA52_0<=This)||LA52_0==From||LA52_0==Type||LA52_0==FullStopFullStopFullStop||(LA52_0>=Get && LA52_0<=Let)||(LA52_0>=Out && LA52_0<=Set)||LA52_0==As||LA52_0==Of||LA52_0==RULE_IDENTIFIER) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalTypesParser.g:2217:4: ( (lv_fpars_1_0= ruleTFormalParameter ) ) (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )*
                    {
                    // InternalTypesParser.g:2217:4: ( (lv_fpars_1_0= ruleTFormalParameter ) )
                    // InternalTypesParser.g:2218:5: (lv_fpars_1_0= ruleTFormalParameter )
                    {
                    // InternalTypesParser.g:2218:5: (lv_fpars_1_0= ruleTFormalParameter )
                    // InternalTypesParser.g:2219:6: lv_fpars_1_0= ruleTFormalParameter
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_7);
                    lv_fpars_1_0=ruleTFormalParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTFormalParametersRule());
                      						}
                      						add(
                      							current,
                      							"fpars",
                      							lv_fpars_1_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.TFormalParameter");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:2236:4: (otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) ) )*
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( (LA51_0==Comma) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // InternalTypesParser.g:2237:5: otherlv_2= Comma ( (lv_fpars_3_0= ruleTFormalParameter ) )
                    	    {
                    	    otherlv_2=(Token)match(input,Comma,FOLLOW_51); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_2, grammarAccess.getTFormalParametersAccess().getCommaKeyword_1_1_0());
                    	      				
                    	    }
                    	    // InternalTypesParser.g:2241:5: ( (lv_fpars_3_0= ruleTFormalParameter ) )
                    	    // InternalTypesParser.g:2242:6: (lv_fpars_3_0= ruleTFormalParameter )
                    	    {
                    	    // InternalTypesParser.g:2242:6: (lv_fpars_3_0= ruleTFormalParameter )
                    	    // InternalTypesParser.g:2243:7: lv_fpars_3_0= ruleTFormalParameter
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_7);
                    	    lv_fpars_3_0=ruleTFormalParameter();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getTFormalParametersRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"fpars",
                    	      								lv_fpars_3_0,
                    	      								"org.eclipse.n4js.ts.TypeExpressions.TFormalParameter");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop51;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTFormalParametersAccess().getRightParenthesisKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTFormalParameters"


    // $ANTLR start "entryRuleTMember"
    // InternalTypesParser.g:2270:1: entryRuleTMember returns [EObject current=null] : iv_ruleTMember= ruleTMember EOF ;
    public final EObject entryRuleTMember() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTMember = null;


        try {
            // InternalTypesParser.g:2270:48: (iv_ruleTMember= ruleTMember EOF )
            // InternalTypesParser.g:2271:2: iv_ruleTMember= ruleTMember EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTMemberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTMember=ruleTMember();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTMember; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTMember"


    // $ANTLR start "ruleTMember"
    // InternalTypesParser.g:2277:1: ruleTMember returns [EObject current=null] : ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod ) | this_TField_3= ruleTField ) ;
    public final EObject ruleTMember() throws RecognitionException {
        EObject current = null;

        EObject this_TGetter_0 = null;

        EObject this_TSetter_1 = null;

        EObject this_TMethod_2 = null;

        EObject this_TField_3 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2283:2: ( ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod ) | this_TField_3= ruleTField ) )
            // InternalTypesParser.g:2284:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod ) | this_TField_3= ruleTField )
            {
            // InternalTypesParser.g:2284:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod ) | this_TField_3= ruleTField )
            int alt53=4;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalTypesParser.g:2285:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter )
                    {
                    // InternalTypesParser.g:2285:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter )
                    // InternalTypesParser.g:2286:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTMemberAccess().getTGetterParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TGetter_0=ruleTGetter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TGetter_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:2331:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter )
                    {
                    // InternalTypesParser.g:2331:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter )
                    // InternalTypesParser.g:2332:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTMemberAccess().getTSetterParserRuleCall_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TSetter_1=ruleTSetter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TSetter_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:2377:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod )
                    {
                    // InternalTypesParser.g:2377:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod )
                    // InternalTypesParser.g:2378:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTMemberAccess().getTMethodParserRuleCall_2());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TMethod_2=ruleTMethod();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TMethod_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:2426:3: this_TField_3= ruleTField
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTMemberAccess().getTFieldParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TField_3=ruleTField();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TField_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTMember"


    // $ANTLR start "entryRuleTMethod"
    // InternalTypesParser.g:2438:1: entryRuleTMethod returns [EObject current=null] : iv_ruleTMethod= ruleTMethod EOF ;
    public final EObject entryRuleTMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTMethod = null;


        try {
            // InternalTypesParser.g:2438:48: (iv_ruleTMethod= ruleTMethod EOF )
            // InternalTypesParser.g:2439:2: iv_ruleTMethod= ruleTMethod EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTMethodRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTMethod=ruleTMethod();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTMethod; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTMethod"


    // $ANTLR start "ruleTMethod"
    // InternalTypesParser.g:2445:1: ruleTMethod returns [EObject current=null] : ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) ) ) this_ColonSepReturnTypeRef_7= ruleColonSepReturnTypeRef[$current] (otherlv_8= Semicolon )? ) ;
    public final EObject ruleTMethod() throws RecognitionException {
        EObject current = null;

        Token lv_declaredAbstract_1_0=null;
        Token lv_declaredStatic_2_0=null;
        Token otherlv_8=null;
        Enumerator lv_declaredMemberAccessModifier_0_0 = null;

        EObject this_TypeVariables_3 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        AntlrDatatypeRuleToken lv_name_5_0 = null;

        EObject this_TFormalParameters_6 = null;

        EObject this_ColonSepReturnTypeRef_7 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2451:2: ( ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) ) ) this_ColonSepReturnTypeRef_7= ruleColonSepReturnTypeRef[$current] (otherlv_8= Semicolon )? ) )
            // InternalTypesParser.g:2452:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) ) ) this_ColonSepReturnTypeRef_7= ruleColonSepReturnTypeRef[$current] (otherlv_8= Semicolon )? )
            {
            // InternalTypesParser.g:2452:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) ) ) this_ColonSepReturnTypeRef_7= ruleColonSepReturnTypeRef[$current] (otherlv_8= Semicolon )? )
            // InternalTypesParser.g:2453:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) ) ) this_ColonSepReturnTypeRef_7= ruleColonSepReturnTypeRef[$current] (otherlv_8= Semicolon )?
            {
            // InternalTypesParser.g:2453:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) ) )
            // InternalTypesParser.g:2454:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) )
            {
            // InternalTypesParser.g:2492:4: ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] ) )
            // InternalTypesParser.g:2493:5: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? (this_TypeVariables_3= ruleTypeVariables[$current] )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] )
            {
            // InternalTypesParser.g:2493:5: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) )
            // InternalTypesParser.g:2494:6: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            {
            // InternalTypesParser.g:2494:6: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            // InternalTypesParser.g:2495:7: lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier
            {
            if ( state.backtracking==0 ) {

              							newCompositeNode(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0());
              						
            }
            pushFollow(FOLLOW_52);
            lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElementForParent(grammarAccess.getTMethodRule());
              							}
              							set(
              								current,
              								"declaredMemberAccessModifier",
              								lv_declaredMemberAccessModifier_0_0,
              								"org.eclipse.n4js.ts.Types.MemberAccessModifier");
              							afterParserOrEnumRuleCall();
              						
            }

            }


            }

            // InternalTypesParser.g:2512:5: ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )?
            int alt54=3;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==Abstract) ) {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==AssignmnentCompatible||(LA54_1>=AutoboxedType && LA54_1<=Finally)||(LA54_1>=Private && LA54_1<=False)||(LA54_1>=Super && LA54_1<=With)||(LA54_1>=Any && LA54_1<=Var)||(LA54_1>=As && LA54_1<=Of)||LA54_1==LessThanSign||LA54_1==LeftSquareBracket||LA54_1==RULE_IDENTIFIER) ) {
                    alt54=1;
                }
            }
            else if ( (LA54_0==Static) ) {
                int LA54_2 = input.LA(2);

                if ( (LA54_2==AssignmnentCompatible||(LA54_2>=AutoboxedType && LA54_2<=Finally)||(LA54_2>=Private && LA54_2<=False)||(LA54_2>=Super && LA54_2<=With)||(LA54_2>=Any && LA54_2<=Var)||(LA54_2>=As && LA54_2<=Of)||LA54_2==LessThanSign||LA54_2==LeftSquareBracket||LA54_2==RULE_IDENTIFIER) ) {
                    alt54=2;
                }
            }
            switch (alt54) {
                case 1 :
                    // InternalTypesParser.g:2513:6: ( (lv_declaredAbstract_1_0= Abstract ) )
                    {
                    // InternalTypesParser.g:2513:6: ( (lv_declaredAbstract_1_0= Abstract ) )
                    // InternalTypesParser.g:2514:7: (lv_declaredAbstract_1_0= Abstract )
                    {
                    // InternalTypesParser.g:2514:7: (lv_declaredAbstract_1_0= Abstract )
                    // InternalTypesParser.g:2515:8: lv_declaredAbstract_1_0= Abstract
                    {
                    lv_declaredAbstract_1_0=(Token)match(input,Abstract,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								newLeafNode(lv_declaredAbstract_1_0, grammarAccess.getTMethodAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0());
                      							
                    }
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElement(grammarAccess.getTMethodRule());
                      								}
                      								setWithLastConsumed(current, "declaredAbstract", true, "abstract");
                      							
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:2528:6: ( (lv_declaredStatic_2_0= Static ) )
                    {
                    // InternalTypesParser.g:2528:6: ( (lv_declaredStatic_2_0= Static ) )
                    // InternalTypesParser.g:2529:7: (lv_declaredStatic_2_0= Static )
                    {
                    // InternalTypesParser.g:2529:7: (lv_declaredStatic_2_0= Static )
                    // InternalTypesParser.g:2530:8: lv_declaredStatic_2_0= Static
                    {
                    lv_declaredStatic_2_0=(Token)match(input,Static,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								newLeafNode(lv_declaredStatic_2_0, grammarAccess.getTMethodAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0());
                      							
                    }
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElement(grammarAccess.getTMethodRule());
                      								}
                      								setWithLastConsumed(current, "declaredStatic", true, "static");
                      							
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:2543:5: (this_TypeVariables_3= ruleTypeVariables[$current] )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==LessThanSign) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalTypesParser.g:2544:6: this_TypeVariables_3= ruleTypeVariables[$current]
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTMethodRule());
                      						}
                      						newCompositeNode(grammarAccess.getTMethodAccess().getTypeVariablesParserRuleCall_0_0_2());
                      					
                    }
                    pushFollow(FOLLOW_52);
                    this_TypeVariables_3=ruleTypeVariables(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						current = this_TypeVariables_3;
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;

            }

            // InternalTypesParser.g:2556:5: ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==AssignmnentCompatible||(LA56_0>=AutoboxedType && LA56_0<=Finally)||(LA56_0>=Private && LA56_0<=False)||(LA56_0>=Super && LA56_0<=With)||(LA56_0>=Any && LA56_0<=Var)||(LA56_0>=As && LA56_0<=Of)||LA56_0==RULE_IDENTIFIER) ) {
                alt56=1;
            }
            else if ( (LA56_0==LeftSquareBracket) ) {
                alt56=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // InternalTypesParser.g:2557:6: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    {
                    // InternalTypesParser.g:2557:6: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    // InternalTypesParser.g:2558:7: (lv_name_4_0= ruleTypesIdentifier )
                    {
                    // InternalTypesParser.g:2558:7: (lv_name_4_0= ruleTypesIdentifier )
                    // InternalTypesParser.g:2559:8: lv_name_4_0= ruleTypesIdentifier
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getTMethodAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0());
                      							
                    }
                    pushFollow(FOLLOW_53);
                    lv_name_4_0=ruleTypesIdentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getTMethodRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_4_0,
                      									"org.eclipse.n4js.ts.Types.TypesIdentifier");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:2577:6: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    {
                    // InternalTypesParser.g:2577:6: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    // InternalTypesParser.g:2578:7: (lv_name_5_0= ruleTypesComputedPropertyName )
                    {
                    // InternalTypesParser.g:2578:7: (lv_name_5_0= ruleTypesComputedPropertyName )
                    // InternalTypesParser.g:2579:8: lv_name_5_0= ruleTypesComputedPropertyName
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getTMethodAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0());
                      							
                    }
                    pushFollow(FOLLOW_53);
                    lv_name_5_0=ruleTypesComputedPropertyName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getTMethodRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_5_0,
                      									"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:2597:5: ( ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current] )
            // InternalTypesParser.g:2598:6: ( LeftParenthesis )=>this_TFormalParameters_6= ruleTFormalParameters[$current]
            {
            if ( state.backtracking==0 ) {

              						if (current==null) {
              							current = createModelElement(grammarAccess.getTMethodRule());
              						}
              						newCompositeNode(grammarAccess.getTMethodAccess().getTFormalParametersParserRuleCall_0_0_4());
              					
            }
            pushFollow(FOLLOW_54);
            this_TFormalParameters_6=ruleTFormalParameters(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              						current = this_TFormalParameters_6;
              						afterParserOrEnumRuleCall();
              					
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTMethodRule());
              			}
              			newCompositeNode(grammarAccess.getTMethodAccess().getColonSepReturnTypeRefParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_49);
            this_ColonSepReturnTypeRef_7=ruleColonSepReturnTypeRef(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_ColonSepReturnTypeRef_7;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:2624:3: (otherlv_8= Semicolon )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==Semicolon) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalTypesParser.g:2625:4: otherlv_8= Semicolon
                    {
                    otherlv_8=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getTMethodAccess().getSemicolonKeyword_2());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTMethod"


    // $ANTLR start "entryRuleTField"
    // InternalTypesParser.g:2634:1: entryRuleTField returns [EObject current=null] : iv_ruleTField= ruleTField EOF ;
    public final EObject entryRuleTField() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTField = null;


        try {
            // InternalTypesParser.g:2634:47: (iv_ruleTField= ruleTField EOF )
            // InternalTypesParser.g:2635:2: iv_ruleTField= ruleTField EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTFieldRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTField=ruleTField();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTField; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTField"


    // $ANTLR start "ruleTField"
    // InternalTypesParser.g:2641:1: ruleTField returns [EObject current=null] : ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredStatic_1_0= Static ) ) | ( (lv_const_2_0= Const ) ) | ( (lv_declaredFinal_3_0= Final ) ) )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( (lv_optional_6_0= QuestionMark ) )? this_ColonSepTypeRef_7= ruleColonSepTypeRef[$current] (otherlv_8= Semicolon )? ) ;
    public final EObject ruleTField() throws RecognitionException {
        EObject current = null;

        Token lv_declaredStatic_1_0=null;
        Token lv_const_2_0=null;
        Token lv_declaredFinal_3_0=null;
        Token lv_optional_6_0=null;
        Token otherlv_8=null;
        Enumerator lv_declaredMemberAccessModifier_0_0 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        AntlrDatatypeRuleToken lv_name_5_0 = null;

        EObject this_ColonSepTypeRef_7 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2647:2: ( ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredStatic_1_0= Static ) ) | ( (lv_const_2_0= Const ) ) | ( (lv_declaredFinal_3_0= Final ) ) )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( (lv_optional_6_0= QuestionMark ) )? this_ColonSepTypeRef_7= ruleColonSepTypeRef[$current] (otherlv_8= Semicolon )? ) )
            // InternalTypesParser.g:2648:2: ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredStatic_1_0= Static ) ) | ( (lv_const_2_0= Const ) ) | ( (lv_declaredFinal_3_0= Final ) ) )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( (lv_optional_6_0= QuestionMark ) )? this_ColonSepTypeRef_7= ruleColonSepTypeRef[$current] (otherlv_8= Semicolon )? )
            {
            // InternalTypesParser.g:2648:2: ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredStatic_1_0= Static ) ) | ( (lv_const_2_0= Const ) ) | ( (lv_declaredFinal_3_0= Final ) ) )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( (lv_optional_6_0= QuestionMark ) )? this_ColonSepTypeRef_7= ruleColonSepTypeRef[$current] (otherlv_8= Semicolon )? )
            // InternalTypesParser.g:2649:3: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredStatic_1_0= Static ) ) | ( (lv_const_2_0= Const ) ) | ( (lv_declaredFinal_3_0= Final ) ) )? ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ( (lv_optional_6_0= QuestionMark ) )? this_ColonSepTypeRef_7= ruleColonSepTypeRef[$current] (otherlv_8= Semicolon )?
            {
            // InternalTypesParser.g:2649:3: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) )
            // InternalTypesParser.g:2650:4: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            {
            // InternalTypesParser.g:2650:4: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            // InternalTypesParser.g:2651:5: lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_55);
            lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTFieldRule());
              					}
              					set(
              						current,
              						"declaredMemberAccessModifier",
              						lv_declaredMemberAccessModifier_0_0,
              						"org.eclipse.n4js.ts.Types.MemberAccessModifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:2668:3: ( ( (lv_declaredStatic_1_0= Static ) ) | ( (lv_const_2_0= Const ) ) | ( (lv_declaredFinal_3_0= Final ) ) )?
            int alt58=4;
            switch ( input.LA(1) ) {
                case Static:
                    {
                    int LA58_1 = input.LA(2);

                    if ( (LA58_1==AssignmnentCompatible||(LA58_1>=AutoboxedType && LA58_1<=Finally)||(LA58_1>=Private && LA58_1<=False)||(LA58_1>=Super && LA58_1<=With)||(LA58_1>=Any && LA58_1<=Var)||(LA58_1>=As && LA58_1<=Of)||LA58_1==LeftSquareBracket||LA58_1==RULE_IDENTIFIER) ) {
                        alt58=1;
                    }
                    }
                    break;
                case Const:
                    {
                    int LA58_2 = input.LA(2);

                    if ( (LA58_2==AssignmnentCompatible||(LA58_2>=AutoboxedType && LA58_2<=Finally)||(LA58_2>=Private && LA58_2<=False)||(LA58_2>=Super && LA58_2<=With)||(LA58_2>=Any && LA58_2<=Var)||(LA58_2>=As && LA58_2<=Of)||LA58_2==LeftSquareBracket||LA58_2==RULE_IDENTIFIER) ) {
                        alt58=2;
                    }
                    }
                    break;
                case Final:
                    {
                    alt58=3;
                    }
                    break;
            }

            switch (alt58) {
                case 1 :
                    // InternalTypesParser.g:2669:4: ( (lv_declaredStatic_1_0= Static ) )
                    {
                    // InternalTypesParser.g:2669:4: ( (lv_declaredStatic_1_0= Static ) )
                    // InternalTypesParser.g:2670:5: (lv_declaredStatic_1_0= Static )
                    {
                    // InternalTypesParser.g:2670:5: (lv_declaredStatic_1_0= Static )
                    // InternalTypesParser.g:2671:6: lv_declaredStatic_1_0= Static
                    {
                    lv_declaredStatic_1_0=(Token)match(input,Static,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_declaredStatic_1_0, grammarAccess.getTFieldAccess().getDeclaredStaticStaticKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTFieldRule());
                      						}
                      						setWithLastConsumed(current, "declaredStatic", true, "static");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:2684:4: ( (lv_const_2_0= Const ) )
                    {
                    // InternalTypesParser.g:2684:4: ( (lv_const_2_0= Const ) )
                    // InternalTypesParser.g:2685:5: (lv_const_2_0= Const )
                    {
                    // InternalTypesParser.g:2685:5: (lv_const_2_0= Const )
                    // InternalTypesParser.g:2686:6: lv_const_2_0= Const
                    {
                    lv_const_2_0=(Token)match(input,Const,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_const_2_0, grammarAccess.getTFieldAccess().getConstConstKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTFieldRule());
                      						}
                      						setWithLastConsumed(current, "const", true, "const");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:2699:4: ( (lv_declaredFinal_3_0= Final ) )
                    {
                    // InternalTypesParser.g:2699:4: ( (lv_declaredFinal_3_0= Final ) )
                    // InternalTypesParser.g:2700:5: (lv_declaredFinal_3_0= Final )
                    {
                    // InternalTypesParser.g:2700:5: (lv_declaredFinal_3_0= Final )
                    // InternalTypesParser.g:2701:6: lv_declaredFinal_3_0= Final
                    {
                    lv_declaredFinal_3_0=(Token)match(input,Final,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_declaredFinal_3_0, grammarAccess.getTFieldAccess().getDeclaredFinalFinalKeyword_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTFieldRule());
                      						}
                      						setWithLastConsumed(current, "declaredFinal", true, "final");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:2714:3: ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==AssignmnentCompatible||(LA59_0>=AutoboxedType && LA59_0<=Finally)||(LA59_0>=Private && LA59_0<=False)||(LA59_0>=Super && LA59_0<=With)||(LA59_0>=Any && LA59_0<=Var)||(LA59_0>=As && LA59_0<=Of)||LA59_0==RULE_IDENTIFIER) ) {
                alt59=1;
            }
            else if ( (LA59_0==LeftSquareBracket) ) {
                alt59=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // InternalTypesParser.g:2715:4: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    {
                    // InternalTypesParser.g:2715:4: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    // InternalTypesParser.g:2716:5: (lv_name_4_0= ruleTypesIdentifier )
                    {
                    // InternalTypesParser.g:2716:5: (lv_name_4_0= ruleTypesIdentifier )
                    // InternalTypesParser.g:2717:6: lv_name_4_0= ruleTypesIdentifier
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTFieldAccess().getNameTypesIdentifierParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_56);
                    lv_name_4_0=ruleTypesIdentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTFieldRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_4_0,
                      							"org.eclipse.n4js.ts.Types.TypesIdentifier");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:2735:4: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    {
                    // InternalTypesParser.g:2735:4: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    // InternalTypesParser.g:2736:5: (lv_name_5_0= ruleTypesComputedPropertyName )
                    {
                    // InternalTypesParser.g:2736:5: (lv_name_5_0= ruleTypesComputedPropertyName )
                    // InternalTypesParser.g:2737:6: lv_name_5_0= ruleTypesComputedPropertyName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTFieldAccess().getNameTypesComputedPropertyNameParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_56);
                    lv_name_5_0=ruleTypesComputedPropertyName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTFieldRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_5_0,
                      							"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:2755:3: ( (lv_optional_6_0= QuestionMark ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==QuestionMark) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalTypesParser.g:2756:4: (lv_optional_6_0= QuestionMark )
                    {
                    // InternalTypesParser.g:2756:4: (lv_optional_6_0= QuestionMark )
                    // InternalTypesParser.g:2757:5: lv_optional_6_0= QuestionMark
                    {
                    lv_optional_6_0=(Token)match(input,QuestionMark,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_optional_6_0, grammarAccess.getTFieldAccess().getOptionalQuestionMarkKeyword_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTFieldRule());
                      					}
                      					setWithLastConsumed(current, "optional", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTFieldRule());
              			}
              			newCompositeNode(grammarAccess.getTFieldAccess().getColonSepTypeRefParserRuleCall_4());
              		
            }
            pushFollow(FOLLOW_49);
            this_ColonSepTypeRef_7=ruleColonSepTypeRef(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_ColonSepTypeRef_7;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:2780:3: (otherlv_8= Semicolon )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==Semicolon) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalTypesParser.g:2781:4: otherlv_8= Semicolon
                    {
                    otherlv_8=(Token)match(input,Semicolon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getTFieldAccess().getSemicolonKeyword_5());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTField"


    // $ANTLR start "entryRuleTGetter"
    // InternalTypesParser.g:2790:1: entryRuleTGetter returns [EObject current=null] : iv_ruleTGetter= ruleTGetter EOF ;
    public final EObject entryRuleTGetter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTGetter = null;


        try {
            // InternalTypesParser.g:2790:48: (iv_ruleTGetter= ruleTGetter EOF )
            // InternalTypesParser.g:2791:2: iv_ruleTGetter= ruleTGetter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTGetterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTGetter=ruleTGetter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTGetter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTGetter"


    // $ANTLR start "ruleTGetter"
    // InternalTypesParser.g:2797:1: ruleTGetter returns [EObject current=null] : ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis otherlv_8= RightParenthesis this_ColonSepDeclaredTypeRef_9= ruleColonSepDeclaredTypeRef[$current] ) ;
    public final EObject ruleTGetter() throws RecognitionException {
        EObject current = null;

        Token lv_declaredAbstract_1_0=null;
        Token lv_declaredStatic_2_0=null;
        Token otherlv_3=null;
        Token lv_optional_6_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Enumerator lv_declaredMemberAccessModifier_0_0 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        AntlrDatatypeRuleToken lv_name_5_0 = null;

        EObject this_ColonSepDeclaredTypeRef_9 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2803:2: ( ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis otherlv_8= RightParenthesis this_ColonSepDeclaredTypeRef_9= ruleColonSepDeclaredTypeRef[$current] ) )
            // InternalTypesParser.g:2804:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis otherlv_8= RightParenthesis this_ColonSepDeclaredTypeRef_9= ruleColonSepDeclaredTypeRef[$current] )
            {
            // InternalTypesParser.g:2804:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis otherlv_8= RightParenthesis this_ColonSepDeclaredTypeRef_9= ruleColonSepDeclaredTypeRef[$current] )
            // InternalTypesParser.g:2805:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis otherlv_8= RightParenthesis this_ColonSepDeclaredTypeRef_9= ruleColonSepDeclaredTypeRef[$current]
            {
            // InternalTypesParser.g:2805:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) )
            // InternalTypesParser.g:2806:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) )
            {
            // InternalTypesParser.g:2841:4: ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) )
            // InternalTypesParser.g:2842:5: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Get ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) )
            {
            // InternalTypesParser.g:2842:5: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) )
            // InternalTypesParser.g:2843:6: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            {
            // InternalTypesParser.g:2843:6: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            // InternalTypesParser.g:2844:7: lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier
            {
            if ( state.backtracking==0 ) {

              							newCompositeNode(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0());
              						
            }
            pushFollow(FOLLOW_57);
            lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElementForParent(grammarAccess.getTGetterRule());
              							}
              							set(
              								current,
              								"declaredMemberAccessModifier",
              								lv_declaredMemberAccessModifier_0_0,
              								"org.eclipse.n4js.ts.Types.MemberAccessModifier");
              							afterParserOrEnumRuleCall();
              						
            }

            }


            }

            // InternalTypesParser.g:2861:5: ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )?
            int alt62=3;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==Abstract) ) {
                alt62=1;
            }
            else if ( (LA62_0==Static) ) {
                alt62=2;
            }
            switch (alt62) {
                case 1 :
                    // InternalTypesParser.g:2862:6: ( (lv_declaredAbstract_1_0= Abstract ) )
                    {
                    // InternalTypesParser.g:2862:6: ( (lv_declaredAbstract_1_0= Abstract ) )
                    // InternalTypesParser.g:2863:7: (lv_declaredAbstract_1_0= Abstract )
                    {
                    // InternalTypesParser.g:2863:7: (lv_declaredAbstract_1_0= Abstract )
                    // InternalTypesParser.g:2864:8: lv_declaredAbstract_1_0= Abstract
                    {
                    lv_declaredAbstract_1_0=(Token)match(input,Abstract,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								newLeafNode(lv_declaredAbstract_1_0, grammarAccess.getTGetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0());
                      							
                    }
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElement(grammarAccess.getTGetterRule());
                      								}
                      								setWithLastConsumed(current, "declaredAbstract", true, "abstract");
                      							
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:2877:6: ( (lv_declaredStatic_2_0= Static ) )
                    {
                    // InternalTypesParser.g:2877:6: ( (lv_declaredStatic_2_0= Static ) )
                    // InternalTypesParser.g:2878:7: (lv_declaredStatic_2_0= Static )
                    {
                    // InternalTypesParser.g:2878:7: (lv_declaredStatic_2_0= Static )
                    // InternalTypesParser.g:2879:8: lv_declaredStatic_2_0= Static
                    {
                    lv_declaredStatic_2_0=(Token)match(input,Static,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								newLeafNode(lv_declaredStatic_2_0, grammarAccess.getTGetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0());
                      							
                    }
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElement(grammarAccess.getTGetterRule());
                      								}
                      								setWithLastConsumed(current, "declaredStatic", true, "static");
                      							
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,Get,FOLLOW_52); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_3, grammarAccess.getTGetterAccess().getGetKeyword_0_0_2());
              				
            }
            // InternalTypesParser.g:2896:5: ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==AssignmnentCompatible||(LA63_0>=AutoboxedType && LA63_0<=Finally)||(LA63_0>=Private && LA63_0<=False)||(LA63_0>=Super && LA63_0<=With)||(LA63_0>=Any && LA63_0<=Var)||(LA63_0>=As && LA63_0<=Of)||LA63_0==RULE_IDENTIFIER) ) {
                alt63=1;
            }
            else if ( (LA63_0==LeftSquareBracket) ) {
                alt63=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // InternalTypesParser.g:2897:6: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    {
                    // InternalTypesParser.g:2897:6: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    // InternalTypesParser.g:2898:7: (lv_name_4_0= ruleTypesIdentifier )
                    {
                    // InternalTypesParser.g:2898:7: (lv_name_4_0= ruleTypesIdentifier )
                    // InternalTypesParser.g:2899:8: lv_name_4_0= ruleTypesIdentifier
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getTGetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0());
                      							
                    }
                    pushFollow(FOLLOW_59);
                    lv_name_4_0=ruleTypesIdentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getTGetterRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_4_0,
                      									"org.eclipse.n4js.ts.Types.TypesIdentifier");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:2917:6: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    {
                    // InternalTypesParser.g:2917:6: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    // InternalTypesParser.g:2918:7: (lv_name_5_0= ruleTypesComputedPropertyName )
                    {
                    // InternalTypesParser.g:2918:7: (lv_name_5_0= ruleTypesComputedPropertyName )
                    // InternalTypesParser.g:2919:8: lv_name_5_0= ruleTypesComputedPropertyName
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getTGetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0());
                      							
                    }
                    pushFollow(FOLLOW_59);
                    lv_name_5_0=ruleTypesComputedPropertyName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getTGetterRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_5_0,
                      									"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            // InternalTypesParser.g:2939:3: ( (lv_optional_6_0= QuestionMark ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==QuestionMark) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalTypesParser.g:2940:4: (lv_optional_6_0= QuestionMark )
                    {
                    // InternalTypesParser.g:2940:4: (lv_optional_6_0= QuestionMark )
                    // InternalTypesParser.g:2941:5: lv_optional_6_0= QuestionMark
                    {
                    lv_optional_6_0=(Token)match(input,QuestionMark,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_optional_6_0, grammarAccess.getTGetterAccess().getOptionalQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTGetterRule());
                      					}
                      					setWithLastConsumed(current, "optional", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,LeftParenthesis,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getTGetterAccess().getLeftParenthesisKeyword_2());
              		
            }
            otherlv_8=(Token)match(input,RightParenthesis,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getTGetterAccess().getRightParenthesisKeyword_3());
              		
            }
            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTGetterRule());
              			}
              			newCompositeNode(grammarAccess.getTGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4());
              		
            }
            pushFollow(FOLLOW_2);
            this_ColonSepDeclaredTypeRef_9=ruleColonSepDeclaredTypeRef(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_ColonSepDeclaredTypeRef_9;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTGetter"


    // $ANTLR start "entryRuleTSetter"
    // InternalTypesParser.g:2976:1: entryRuleTSetter returns [EObject current=null] : iv_ruleTSetter= ruleTSetter EOF ;
    public final EObject entryRuleTSetter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTSetter = null;


        try {
            // InternalTypesParser.g:2976:48: (iv_ruleTSetter= ruleTSetter EOF )
            // InternalTypesParser.g:2977:2: iv_ruleTSetter= ruleTSetter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTSetterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTSetter=ruleTSetter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTSetter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTSetter"


    // $ANTLR start "ruleTSetter"
    // InternalTypesParser.g:2983:1: ruleTSetter returns [EObject current=null] : ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis ( (lv_fpar_8_0= ruleTFormalParameter ) ) otherlv_9= RightParenthesis ) ;
    public final EObject ruleTSetter() throws RecognitionException {
        EObject current = null;

        Token lv_declaredAbstract_1_0=null;
        Token lv_declaredStatic_2_0=null;
        Token otherlv_3=null;
        Token lv_optional_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Enumerator lv_declaredMemberAccessModifier_0_0 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        AntlrDatatypeRuleToken lv_name_5_0 = null;

        EObject lv_fpar_8_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:2989:2: ( ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis ( (lv_fpar_8_0= ruleTFormalParameter ) ) otherlv_9= RightParenthesis ) )
            // InternalTypesParser.g:2990:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis ( (lv_fpar_8_0= ruleTFormalParameter ) ) otherlv_9= RightParenthesis )
            {
            // InternalTypesParser.g:2990:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis ( (lv_fpar_8_0= ruleTFormalParameter ) ) otherlv_9= RightParenthesis )
            // InternalTypesParser.g:2991:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) ) ( (lv_optional_6_0= QuestionMark ) )? otherlv_7= LeftParenthesis ( (lv_fpar_8_0= ruleTFormalParameter ) ) otherlv_9= RightParenthesis
            {
            // InternalTypesParser.g:2991:3: ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) ) )
            // InternalTypesParser.g:2992:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=> ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) )
            {
            // InternalTypesParser.g:3027:4: ( ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) ) )
            // InternalTypesParser.g:3028:5: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) ) ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )? otherlv_3= Set ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) )
            {
            // InternalTypesParser.g:3028:5: ( (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier ) )
            // InternalTypesParser.g:3029:6: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            {
            // InternalTypesParser.g:3029:6: (lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier )
            // InternalTypesParser.g:3030:7: lv_declaredMemberAccessModifier_0_0= ruleMemberAccessModifier
            {
            if ( state.backtracking==0 ) {

              							newCompositeNode(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0());
              						
            }
            pushFollow(FOLLOW_61);
            lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElementForParent(grammarAccess.getTSetterRule());
              							}
              							set(
              								current,
              								"declaredMemberAccessModifier",
              								lv_declaredMemberAccessModifier_0_0,
              								"org.eclipse.n4js.ts.Types.MemberAccessModifier");
              							afterParserOrEnumRuleCall();
              						
            }

            }


            }

            // InternalTypesParser.g:3047:5: ( ( (lv_declaredAbstract_1_0= Abstract ) ) | ( (lv_declaredStatic_2_0= Static ) ) )?
            int alt65=3;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==Abstract) ) {
                alt65=1;
            }
            else if ( (LA65_0==Static) ) {
                alt65=2;
            }
            switch (alt65) {
                case 1 :
                    // InternalTypesParser.g:3048:6: ( (lv_declaredAbstract_1_0= Abstract ) )
                    {
                    // InternalTypesParser.g:3048:6: ( (lv_declaredAbstract_1_0= Abstract ) )
                    // InternalTypesParser.g:3049:7: (lv_declaredAbstract_1_0= Abstract )
                    {
                    // InternalTypesParser.g:3049:7: (lv_declaredAbstract_1_0= Abstract )
                    // InternalTypesParser.g:3050:8: lv_declaredAbstract_1_0= Abstract
                    {
                    lv_declaredAbstract_1_0=(Token)match(input,Abstract,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								newLeafNode(lv_declaredAbstract_1_0, grammarAccess.getTSetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0());
                      							
                    }
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElement(grammarAccess.getTSetterRule());
                      								}
                      								setWithLastConsumed(current, "declaredAbstract", true, "abstract");
                      							
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:3063:6: ( (lv_declaredStatic_2_0= Static ) )
                    {
                    // InternalTypesParser.g:3063:6: ( (lv_declaredStatic_2_0= Static ) )
                    // InternalTypesParser.g:3064:7: (lv_declaredStatic_2_0= Static )
                    {
                    // InternalTypesParser.g:3064:7: (lv_declaredStatic_2_0= Static )
                    // InternalTypesParser.g:3065:8: lv_declaredStatic_2_0= Static
                    {
                    lv_declaredStatic_2_0=(Token)match(input,Static,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								newLeafNode(lv_declaredStatic_2_0, grammarAccess.getTSetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0());
                      							
                    }
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElement(grammarAccess.getTSetterRule());
                      								}
                      								setWithLastConsumed(current, "declaredStatic", true, "static");
                      							
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,Set,FOLLOW_52); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_3, grammarAccess.getTSetterAccess().getSetKeyword_0_0_2());
              				
            }
            // InternalTypesParser.g:3082:5: ( ( (lv_name_4_0= ruleTypesIdentifier ) ) | ( (lv_name_5_0= ruleTypesComputedPropertyName ) ) )
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==AssignmnentCompatible||(LA66_0>=AutoboxedType && LA66_0<=Finally)||(LA66_0>=Private && LA66_0<=False)||(LA66_0>=Super && LA66_0<=With)||(LA66_0>=Any && LA66_0<=Var)||(LA66_0>=As && LA66_0<=Of)||LA66_0==RULE_IDENTIFIER) ) {
                alt66=1;
            }
            else if ( (LA66_0==LeftSquareBracket) ) {
                alt66=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }
            switch (alt66) {
                case 1 :
                    // InternalTypesParser.g:3083:6: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    {
                    // InternalTypesParser.g:3083:6: ( (lv_name_4_0= ruleTypesIdentifier ) )
                    // InternalTypesParser.g:3084:7: (lv_name_4_0= ruleTypesIdentifier )
                    {
                    // InternalTypesParser.g:3084:7: (lv_name_4_0= ruleTypesIdentifier )
                    // InternalTypesParser.g:3085:8: lv_name_4_0= ruleTypesIdentifier
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getTSetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0());
                      							
                    }
                    pushFollow(FOLLOW_59);
                    lv_name_4_0=ruleTypesIdentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getTSetterRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_4_0,
                      									"org.eclipse.n4js.ts.Types.TypesIdentifier");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:3103:6: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    {
                    // InternalTypesParser.g:3103:6: ( (lv_name_5_0= ruleTypesComputedPropertyName ) )
                    // InternalTypesParser.g:3104:7: (lv_name_5_0= ruleTypesComputedPropertyName )
                    {
                    // InternalTypesParser.g:3104:7: (lv_name_5_0= ruleTypesComputedPropertyName )
                    // InternalTypesParser.g:3105:8: lv_name_5_0= ruleTypesComputedPropertyName
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getTSetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0());
                      							
                    }
                    pushFollow(FOLLOW_59);
                    lv_name_5_0=ruleTypesComputedPropertyName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getTSetterRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_5_0,
                      									"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            // InternalTypesParser.g:3125:3: ( (lv_optional_6_0= QuestionMark ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==QuestionMark) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalTypesParser.g:3126:4: (lv_optional_6_0= QuestionMark )
                    {
                    // InternalTypesParser.g:3126:4: (lv_optional_6_0= QuestionMark )
                    // InternalTypesParser.g:3127:5: lv_optional_6_0= QuestionMark
                    {
                    lv_optional_6_0=(Token)match(input,QuestionMark,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_optional_6_0, grammarAccess.getTSetterAccess().getOptionalQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTSetterRule());
                      					}
                      					setWithLastConsumed(current, "optional", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,LeftParenthesis,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getTSetterAccess().getLeftParenthesisKeyword_2());
              		
            }
            // InternalTypesParser.g:3143:3: ( (lv_fpar_8_0= ruleTFormalParameter ) )
            // InternalTypesParser.g:3144:4: (lv_fpar_8_0= ruleTFormalParameter )
            {
            // InternalTypesParser.g:3144:4: (lv_fpar_8_0= ruleTFormalParameter )
            // InternalTypesParser.g:3145:5: lv_fpar_8_0= ruleTFormalParameter
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTSetterAccess().getFparTFormalParameterParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_60);
            lv_fpar_8_0=ruleTFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTSetterRule());
              					}
              					set(
              						current,
              						"fpar",
              						lv_fpar_8_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TFormalParameter");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_9=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_9, grammarAccess.getTSetterAccess().getRightParenthesisKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTSetter"


    // $ANTLR start "entryRuleTFunction"
    // InternalTypesParser.g:3170:1: entryRuleTFunction returns [EObject current=null] : iv_ruleTFunction= ruleTFunction EOF ;
    public final EObject entryRuleTFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTFunction = null;


        try {
            // InternalTypesParser.g:3170:50: (iv_ruleTFunction= ruleTFunction EOF )
            // InternalTypesParser.g:3171:2: iv_ruleTFunction= ruleTFunction EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTFunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTFunction=ruleTFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTFunction; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTFunction"


    // $ANTLR start "ruleTFunction"
    // InternalTypesParser.g:3177:1: ruleTFunction returns [EObject current=null] : ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Function (this_TypeVariables_3= ruleTypeVariables[$current] )? ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) this_TFormalParameters_5= ruleTFormalParameters[$current] this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] ) ;
    public final EObject ruleTFunction() throws RecognitionException {
        EObject current = null;

        Token lv_declaredProvidedByRuntime_1_0=null;
        Token otherlv_2=null;
        Enumerator lv_declaredTypeAccessModifier_0_0 = null;

        EObject this_TypeVariables_3 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject this_TFormalParameters_5 = null;

        EObject this_ColonSepReturnTypeRef_6 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3183:2: ( ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Function (this_TypeVariables_3= ruleTypeVariables[$current] )? ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) this_TFormalParameters_5= ruleTFormalParameters[$current] this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] ) )
            // InternalTypesParser.g:3184:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Function (this_TypeVariables_3= ruleTypeVariables[$current] )? ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) this_TFormalParameters_5= ruleTFormalParameters[$current] this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )
            {
            // InternalTypesParser.g:3184:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Function (this_TypeVariables_3= ruleTypeVariables[$current] )? ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) this_TFormalParameters_5= ruleTFormalParameters[$current] this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )
            // InternalTypesParser.g:3185:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Function (this_TypeVariables_3= ruleTypeVariables[$current] )? ( (lv_name_4_0= ruleBindingTypesIdentifier ) ) this_TFormalParameters_5= ruleTFormalParameters[$current] this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current]
            {
            // InternalTypesParser.g:3185:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) )
            // InternalTypesParser.g:3186:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            {
            // InternalTypesParser.g:3186:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            // InternalTypesParser.g:3187:5: lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_63);
            lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTFunctionRule());
              					}
              					set(
              						current,
              						"declaredTypeAccessModifier",
              						lv_declaredTypeAccessModifier_0_0,
              						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:3204:3: ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==ProvidedByRuntime) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalTypesParser.g:3205:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    {
                    // InternalTypesParser.g:3205:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    // InternalTypesParser.g:3206:5: lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime
                    {
                    lv_declaredProvidedByRuntime_1_0=(Token)match(input,ProvidedByRuntime,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTFunctionRule());
                      					}
                      					setWithLastConsumed(current, "declaredProvidedByRuntime", true, "providedByRuntime");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,Function,FOLLOW_65); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getTFunctionAccess().getFunctionKeyword_2());
              		
            }
            // InternalTypesParser.g:3222:3: (this_TypeVariables_3= ruleTypeVariables[$current] )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==LessThanSign) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalTypesParser.g:3223:4: this_TypeVariables_3= ruleTypeVariables[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getTFunctionRule());
                      				}
                      				newCompositeNode(grammarAccess.getTFunctionAccess().getTypeVariablesParserRuleCall_3());
                      			
                    }
                    pushFollow(FOLLOW_10);
                    this_TypeVariables_3=ruleTypeVariables(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TypeVariables_3;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalTypesParser.g:3235:3: ( (lv_name_4_0= ruleBindingTypesIdentifier ) )
            // InternalTypesParser.g:3236:4: (lv_name_4_0= ruleBindingTypesIdentifier )
            {
            // InternalTypesParser.g:3236:4: (lv_name_4_0= ruleBindingTypesIdentifier )
            // InternalTypesParser.g:3237:5: lv_name_4_0= ruleBindingTypesIdentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTFunctionAccess().getNameBindingTypesIdentifierParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_53);
            lv_name_4_0=ruleBindingTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTFunctionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_4_0,
              						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTFunctionRule());
              			}
              			newCompositeNode(grammarAccess.getTFunctionAccess().getTFormalParametersParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_54);
            this_TFormalParameters_5=ruleTFormalParameters(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TFormalParameters_5;
              			afterParserOrEnumRuleCall();
              		
            }
            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTFunctionRule());
              			}
              			newCompositeNode(grammarAccess.getTFunctionAccess().getColonSepReturnTypeRefParserRuleCall_6());
              		
            }
            pushFollow(FOLLOW_2);
            this_ColonSepReturnTypeRef_6=ruleColonSepReturnTypeRef(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_ColonSepReturnTypeRef_6;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTFunction"


    // $ANTLR start "entryRuleTEnum"
    // InternalTypesParser.g:3280:1: entryRuleTEnum returns [EObject current=null] : iv_ruleTEnum= ruleTEnum EOF ;
    public final EObject entryRuleTEnum() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTEnum = null;


        try {
            // InternalTypesParser.g:3280:46: (iv_ruleTEnum= ruleTEnum EOF )
            // InternalTypesParser.g:3281:2: iv_ruleTEnum= ruleTEnum EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTEnumRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTEnum=ruleTEnum();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTEnum; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTEnum"


    // $ANTLR start "ruleTEnum"
    // InternalTypesParser.g:3287:1: ruleTEnum returns [EObject current=null] : ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Enum ( (lv_name_3_0= ruleBindingTypesIdentifier ) ) otherlv_4= LeftCurlyBracket ( (lv_literals_5_0= ruleTEnumLiteral ) ) (otherlv_6= Comma ( (lv_literals_7_0= ruleTEnumLiteral ) ) )* otherlv_8= RightCurlyBracket ) ;
    public final EObject ruleTEnum() throws RecognitionException {
        EObject current = null;

        Token lv_declaredProvidedByRuntime_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Enumerator lv_declaredTypeAccessModifier_0_0 = null;

        AntlrDatatypeRuleToken lv_name_3_0 = null;

        EObject lv_literals_5_0 = null;

        EObject lv_literals_7_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3293:2: ( ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Enum ( (lv_name_3_0= ruleBindingTypesIdentifier ) ) otherlv_4= LeftCurlyBracket ( (lv_literals_5_0= ruleTEnumLiteral ) ) (otherlv_6= Comma ( (lv_literals_7_0= ruleTEnumLiteral ) ) )* otherlv_8= RightCurlyBracket ) )
            // InternalTypesParser.g:3294:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Enum ( (lv_name_3_0= ruleBindingTypesIdentifier ) ) otherlv_4= LeftCurlyBracket ( (lv_literals_5_0= ruleTEnumLiteral ) ) (otherlv_6= Comma ( (lv_literals_7_0= ruleTEnumLiteral ) ) )* otherlv_8= RightCurlyBracket )
            {
            // InternalTypesParser.g:3294:2: ( ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Enum ( (lv_name_3_0= ruleBindingTypesIdentifier ) ) otherlv_4= LeftCurlyBracket ( (lv_literals_5_0= ruleTEnumLiteral ) ) (otherlv_6= Comma ( (lv_literals_7_0= ruleTEnumLiteral ) ) )* otherlv_8= RightCurlyBracket )
            // InternalTypesParser.g:3295:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) ) ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )? otherlv_2= Enum ( (lv_name_3_0= ruleBindingTypesIdentifier ) ) otherlv_4= LeftCurlyBracket ( (lv_literals_5_0= ruleTEnumLiteral ) ) (otherlv_6= Comma ( (lv_literals_7_0= ruleTEnumLiteral ) ) )* otherlv_8= RightCurlyBracket
            {
            // InternalTypesParser.g:3295:3: ( (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier ) )
            // InternalTypesParser.g:3296:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            {
            // InternalTypesParser.g:3296:4: (lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier )
            // InternalTypesParser.g:3297:5: lv_declaredTypeAccessModifier_0_0= ruleTypeAccessModifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_66);
            lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTEnumRule());
              					}
              					set(
              						current,
              						"declaredTypeAccessModifier",
              						lv_declaredTypeAccessModifier_0_0,
              						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:3314:3: ( (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==ProvidedByRuntime) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalTypesParser.g:3315:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    {
                    // InternalTypesParser.g:3315:4: (lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime )
                    // InternalTypesParser.g:3316:5: lv_declaredProvidedByRuntime_1_0= ProvidedByRuntime
                    {
                    lv_declaredProvidedByRuntime_1_0=(Token)match(input,ProvidedByRuntime,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTEnumRule());
                      					}
                      					setWithLastConsumed(current, "declaredProvidedByRuntime", true, "providedByRuntime");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,Enum,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getTEnumAccess().getEnumKeyword_2());
              		
            }
            // InternalTypesParser.g:3332:3: ( (lv_name_3_0= ruleBindingTypesIdentifier ) )
            // InternalTypesParser.g:3333:4: (lv_name_3_0= ruleBindingTypesIdentifier )
            {
            // InternalTypesParser.g:3333:4: (lv_name_3_0= ruleBindingTypesIdentifier )
            // InternalTypesParser.g:3334:5: lv_name_3_0= ruleBindingTypesIdentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTEnumAccess().getNameBindingTypesIdentifierParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_name_3_0=ruleBindingTypesIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTEnumRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_3_0,
              						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_4=(Token)match(input,LeftCurlyBracket,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTEnumAccess().getLeftCurlyBracketKeyword_4());
              		
            }
            // InternalTypesParser.g:3355:3: ( (lv_literals_5_0= ruleTEnumLiteral ) )
            // InternalTypesParser.g:3356:4: (lv_literals_5_0= ruleTEnumLiteral )
            {
            // InternalTypesParser.g:3356:4: (lv_literals_5_0= ruleTEnumLiteral )
            // InternalTypesParser.g:3357:5: lv_literals_5_0= ruleTEnumLiteral
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_68);
            lv_literals_5_0=ruleTEnumLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTEnumRule());
              					}
              					add(
              						current,
              						"literals",
              						lv_literals_5_0,
              						"org.eclipse.n4js.ts.Types.TEnumLiteral");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:3374:3: (otherlv_6= Comma ( (lv_literals_7_0= ruleTEnumLiteral ) ) )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==Comma) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // InternalTypesParser.g:3375:4: otherlv_6= Comma ( (lv_literals_7_0= ruleTEnumLiteral ) )
            	    {
            	    otherlv_6=(Token)match(input,Comma,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_6, grammarAccess.getTEnumAccess().getCommaKeyword_6_0());
            	      			
            	    }
            	    // InternalTypesParser.g:3379:4: ( (lv_literals_7_0= ruleTEnumLiteral ) )
            	    // InternalTypesParser.g:3380:5: (lv_literals_7_0= ruleTEnumLiteral )
            	    {
            	    // InternalTypesParser.g:3380:5: (lv_literals_7_0= ruleTEnumLiteral )
            	    // InternalTypesParser.g:3381:6: lv_literals_7_0= ruleTEnumLiteral
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_6_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_68);
            	    lv_literals_7_0=ruleTEnumLiteral();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTEnumRule());
            	      						}
            	      						add(
            	      							current,
            	      							"literals",
            	      							lv_literals_7_0,
            	      							"org.eclipse.n4js.ts.Types.TEnumLiteral");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);

            otherlv_8=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getTEnumAccess().getRightCurlyBracketKeyword_7());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTEnum"


    // $ANTLR start "entryRuleTEnumLiteral"
    // InternalTypesParser.g:3407:1: entryRuleTEnumLiteral returns [EObject current=null] : iv_ruleTEnumLiteral= ruleTEnumLiteral EOF ;
    public final EObject entryRuleTEnumLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTEnumLiteral = null;


        try {
            // InternalTypesParser.g:3407:53: (iv_ruleTEnumLiteral= ruleTEnumLiteral EOF )
            // InternalTypesParser.g:3408:2: iv_ruleTEnumLiteral= ruleTEnumLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTEnumLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTEnumLiteral=ruleTEnumLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTEnumLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTEnumLiteral"


    // $ANTLR start "ruleTEnumLiteral"
    // InternalTypesParser.g:3414:1: ruleTEnumLiteral returns [EObject current=null] : ( (lv_name_0_0= RULE_IDENTIFIER ) ) ;
    public final EObject ruleTEnumLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalTypesParser.g:3420:2: ( ( (lv_name_0_0= RULE_IDENTIFIER ) ) )
            // InternalTypesParser.g:3421:2: ( (lv_name_0_0= RULE_IDENTIFIER ) )
            {
            // InternalTypesParser.g:3421:2: ( (lv_name_0_0= RULE_IDENTIFIER ) )
            // InternalTypesParser.g:3422:3: (lv_name_0_0= RULE_IDENTIFIER )
            {
            // InternalTypesParser.g:3422:3: (lv_name_0_0= RULE_IDENTIFIER )
            // InternalTypesParser.g:3423:4: lv_name_0_0= RULE_IDENTIFIER
            {
            lv_name_0_0=(Token)match(input,RULE_IDENTIFIER,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_name_0_0, grammarAccess.getTEnumLiteralAccess().getNameIDENTIFIERTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getTEnumLiteralRule());
              				}
              				setWithLastConsumed(
              					current,
              					"name",
              					lv_name_0_0,
              					"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTEnumLiteral"


    // $ANTLR start "entryRulePrimaryTypeExpression"
    // InternalTypesParser.g:3442:1: entryRulePrimaryTypeExpression returns [EObject current=null] : iv_rulePrimaryTypeExpression= rulePrimaryTypeExpression EOF ;
    public final EObject entryRulePrimaryTypeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryTypeExpression = null;


        try {
            // InternalTypesParser.g:3442:62: (iv_rulePrimaryTypeExpression= rulePrimaryTypeExpression EOF )
            // InternalTypesParser.g:3443:2: iv_rulePrimaryTypeExpression= rulePrimaryTypeExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryTypeExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePrimaryTypeExpression=rulePrimaryTypeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimaryTypeExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimaryTypeExpression"


    // $ANTLR start "rulePrimaryTypeExpression"
    // InternalTypesParser.g:3449:1: rulePrimaryTypeExpression returns [EObject current=null] : ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression ) | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeRefWithModifiers_2= ruleTypeRefWithModifiers | (otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis ) ) ;
    public final EObject rulePrimaryTypeExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_ArrowFunctionTypeExpression_0 = null;

        EObject this_ArrayTypeRef_1 = null;

        EObject this_TypeRefWithModifiers_2 = null;

        EObject this_TypeRef_4 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3455:2: ( ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression ) | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeRefWithModifiers_2= ruleTypeRefWithModifiers | (otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis ) ) )
            // InternalTypesParser.g:3456:2: ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression ) | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeRefWithModifiers_2= ruleTypeRefWithModifiers | (otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis ) )
            {
            // InternalTypesParser.g:3456:2: ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression ) | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeRefWithModifiers_2= ruleTypeRefWithModifiers | (otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis ) )
            int alt72=4;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // InternalTypesParser.g:3457:3: ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression )
                    {
                    // InternalTypesParser.g:3457:3: ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression )
                    // InternalTypesParser.g:3458:4: ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getArrowFunctionTypeExpressionParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_ArrowFunctionTypeExpression_0=ruleArrowFunctionTypeExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ArrowFunctionTypeExpression_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:3477:3: this_ArrayTypeRef_1= ruleArrayTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getArrayTypeRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ArrayTypeRef_1=ruleArrayTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ArrayTypeRef_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:3486:3: this_TypeRefWithModifiers_2= ruleTypeRefWithModifiers
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefWithModifiersParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeRefWithModifiers_2=ruleTypeRefWithModifiers();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TypeRefWithModifiers_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:3495:3: (otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis )
                    {
                    // InternalTypesParser.g:3495:3: (otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis )
                    // InternalTypesParser.g:3496:4: otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefParserRuleCall_3_1());
                      			
                    }
                    pushFollow(FOLLOW_60);
                    this_TypeRef_4=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TypeRef_4;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getPrimaryTypeExpressionAccess().getRightParenthesisKeyword_3_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimaryTypeExpression"


    // $ANTLR start "entryRuleTypeRefWithModifiers"
    // InternalTypesParser.g:3517:1: entryRuleTypeRefWithModifiers returns [EObject current=null] : iv_ruleTypeRefWithModifiers= ruleTypeRefWithModifiers EOF ;
    public final EObject entryRuleTypeRefWithModifiers() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRefWithModifiers = null;


        try {
            // InternalTypesParser.g:3517:61: (iv_ruleTypeRefWithModifiers= ruleTypeRefWithModifiers EOF )
            // InternalTypesParser.g:3518:2: iv_ruleTypeRefWithModifiers= ruleTypeRefWithModifiers EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRefWithModifiersRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeRefWithModifiers=ruleTypeRefWithModifiers();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeRefWithModifiers; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeRefWithModifiers"


    // $ANTLR start "ruleTypeRefWithModifiers"
    // InternalTypesParser.g:3524:1: ruleTypeRefWithModifiers returns [EObject current=null] : (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( ( ( QuestionMark ) )=> (lv_followedByQuestionMark_1_0= QuestionMark ) )? ) ;
    public final EObject ruleTypeRefWithModifiers() throws RecognitionException {
        EObject current = null;

        Token lv_followedByQuestionMark_1_0=null;
        EObject this_TypeRefWithoutModifiers_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3530:2: ( (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( ( ( QuestionMark ) )=> (lv_followedByQuestionMark_1_0= QuestionMark ) )? ) )
            // InternalTypesParser.g:3531:2: (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( ( ( QuestionMark ) )=> (lv_followedByQuestionMark_1_0= QuestionMark ) )? )
            {
            // InternalTypesParser.g:3531:2: (this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( ( ( QuestionMark ) )=> (lv_followedByQuestionMark_1_0= QuestionMark ) )? )
            // InternalTypesParser.g:3532:3: this_TypeRefWithoutModifiers_0= ruleTypeRefWithoutModifiers ( ( ( QuestionMark ) )=> (lv_followedByQuestionMark_1_0= QuestionMark ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTypeRefWithModifiersAccess().getTypeRefWithoutModifiersParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_9);
            this_TypeRefWithoutModifiers_0=ruleTypeRefWithoutModifiers();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TypeRefWithoutModifiers_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:3540:3: ( ( ( QuestionMark ) )=> (lv_followedByQuestionMark_1_0= QuestionMark ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==QuestionMark) && (synpred14_InternalTypesParser())) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalTypesParser.g:3541:4: ( ( QuestionMark ) )=> (lv_followedByQuestionMark_1_0= QuestionMark )
                    {
                    // InternalTypesParser.g:3545:4: (lv_followedByQuestionMark_1_0= QuestionMark )
                    // InternalTypesParser.g:3546:5: lv_followedByQuestionMark_1_0= QuestionMark
                    {
                    lv_followedByQuestionMark_1_0=(Token)match(input,QuestionMark,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_followedByQuestionMark_1_0, grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTypeRefWithModifiersRule());
                      					}
                      					setWithLastConsumed(current, "followedByQuestionMark", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeRefWithModifiers"


    // $ANTLR start "entryRuleTypeRefWithoutModifiers"
    // InternalTypesParser.g:3562:1: entryRuleTypeRefWithoutModifiers returns [EObject current=null] : iv_ruleTypeRefWithoutModifiers= ruleTypeRefWithoutModifiers EOF ;
    public final EObject entryRuleTypeRefWithoutModifiers() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRefWithoutModifiers = null;


        try {
            // InternalTypesParser.g:3562:64: (iv_ruleTypeRefWithoutModifiers= ruleTypeRefWithoutModifiers EOF )
            // InternalTypesParser.g:3563:2: iv_ruleTypeRefWithoutModifiers= ruleTypeRefWithoutModifiers EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRefWithoutModifiersRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeRefWithoutModifiers=ruleTypeRefWithoutModifiers();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeRefWithoutModifiers; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeRefWithoutModifiers"


    // $ANTLR start "ruleTypeRefWithoutModifiers"
    // InternalTypesParser.g:3569:1: ruleTypeRefWithoutModifiers returns [EObject current=null] : ( ( (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef ) ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )? ) | this_TypeTypeRef_3= ruleTypeTypeRef | this_FunctionTypeExpressionOLD_4= ruleFunctionTypeExpressionOLD | this_UnionTypeExpressionOLD_5= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_6= ruleIntersectionTypeExpressionOLD ) ;
    public final EObject ruleTypeRefWithoutModifiers() throws RecognitionException {
        EObject current = null;

        Token lv_dynamic_2_0=null;
        EObject this_ParameterizedTypeRef_0 = null;

        EObject this_ThisTypeRef_1 = null;

        EObject this_TypeTypeRef_3 = null;

        EObject this_FunctionTypeExpressionOLD_4 = null;

        EObject this_UnionTypeExpressionOLD_5 = null;

        EObject this_IntersectionTypeExpressionOLD_6 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3575:2: ( ( ( (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef ) ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )? ) | this_TypeTypeRef_3= ruleTypeTypeRef | this_FunctionTypeExpressionOLD_4= ruleFunctionTypeExpressionOLD | this_UnionTypeExpressionOLD_5= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_6= ruleIntersectionTypeExpressionOLD ) )
            // InternalTypesParser.g:3576:2: ( ( (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef ) ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )? ) | this_TypeTypeRef_3= ruleTypeTypeRef | this_FunctionTypeExpressionOLD_4= ruleFunctionTypeExpressionOLD | this_UnionTypeExpressionOLD_5= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_6= ruleIntersectionTypeExpressionOLD )
            {
            // InternalTypesParser.g:3576:2: ( ( (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef ) ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )? ) | this_TypeTypeRef_3= ruleTypeTypeRef | this_FunctionTypeExpressionOLD_4= ruleFunctionTypeExpressionOLD | this_UnionTypeExpressionOLD_5= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_6= ruleIntersectionTypeExpressionOLD )
            int alt76=5;
            switch ( input.LA(1) ) {
            case Undefined:
            case Indexed:
            case Null:
            case This_1:
            case Void:
            case Any:
            case Tilde:
            case RULE_IDENTIFIER:
                {
                alt76=1;
                }
                break;
            case Constructor:
            case Type:
                {
                alt76=2;
                }
                break;
            case LeftCurlyBracket:
                {
                alt76=3;
                }
                break;
            case Union:
                {
                alt76=4;
                }
                break;
            case Intersection:
                {
                alt76=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // InternalTypesParser.g:3577:3: ( (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef ) ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )? )
                    {
                    // InternalTypesParser.g:3577:3: ( (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef ) ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )? )
                    // InternalTypesParser.g:3578:4: (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef ) ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )?
                    {
                    // InternalTypesParser.g:3578:4: (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ThisTypeRef_1= ruleThisTypeRef )
                    int alt74=2;
                    switch ( input.LA(1) ) {
                    case Undefined:
                    case Indexed:
                    case Null:
                    case Void:
                    case Any:
                    case RULE_IDENTIFIER:
                        {
                        alt74=1;
                        }
                        break;
                    case Tilde:
                        {
                        switch ( input.LA(2) ) {
                        case Tilde:
                            {
                            int LA74_4 = input.LA(3);

                            if ( (LA74_4==Undefined||LA74_4==Indexed||LA74_4==Null||LA74_4==Void||LA74_4==Any||LA74_4==RULE_IDENTIFIER) ) {
                                alt74=1;
                            }
                            else if ( (LA74_4==This_1) ) {
                                alt74=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 74, 4, input);

                                throw nvae;
                            }
                            }
                            break;
                        case RULE_STRUCTMODSUFFIX:
                            {
                            int LA74_5 = input.LA(3);

                            if ( (LA74_5==This_1) ) {
                                alt74=2;
                            }
                            else if ( (LA74_5==Undefined||LA74_5==Indexed||LA74_5==Null||LA74_5==Void||LA74_5==Any||LA74_5==RULE_IDENTIFIER) ) {
                                alt74=1;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 74, 5, input);

                                throw nvae;
                            }
                            }
                            break;
                        case Undefined:
                        case Indexed:
                        case Null:
                        case Void:
                        case Any:
                        case RULE_IDENTIFIER:
                            {
                            alt74=1;
                            }
                            break;
                        case This_1:
                            {
                            alt74=2;
                            }
                            break;
                        default:
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 74, 2, input);

                            throw nvae;
                        }

                        }
                        break;
                    case This_1:
                        {
                        alt74=2;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 74, 0, input);

                        throw nvae;
                    }

                    switch (alt74) {
                        case 1 :
                            // InternalTypesParser.g:3579:5: this_ParameterizedTypeRef_0= ruleParameterizedTypeRef
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getParameterizedTypeRefParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_69);
                            this_ParameterizedTypeRef_0=ruleParameterizedTypeRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_ParameterizedTypeRef_0;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalTypesParser.g:3588:5: this_ThisTypeRef_1= ruleThisTypeRef
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getThisTypeRefParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_69);
                            this_ThisTypeRef_1=ruleThisTypeRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_ThisTypeRef_1;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;

                    }

                    // InternalTypesParser.g:3597:4: ( ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign ) )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==PlusSign) && (synpred15_InternalTypesParser())) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // InternalTypesParser.g:3598:5: ( ( PlusSign ) )=> (lv_dynamic_2_0= PlusSign )
                            {
                            // InternalTypesParser.g:3602:5: (lv_dynamic_2_0= PlusSign )
                            // InternalTypesParser.g:3603:6: lv_dynamic_2_0= PlusSign
                            {
                            lv_dynamic_2_0=(Token)match(input,PlusSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_dynamic_2_0, grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicPlusSignKeyword_0_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getTypeRefWithoutModifiersRule());
                              						}
                              						setWithLastConsumed(current, "dynamic", true, "+");
                              					
                            }

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:3617:3: this_TypeTypeRef_3= ruleTypeTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getTypeTypeRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeTypeRef_3=ruleTypeTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TypeTypeRef_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:3626:3: this_FunctionTypeExpressionOLD_4= ruleFunctionTypeExpressionOLD
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getFunctionTypeExpressionOLDParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_FunctionTypeExpressionOLD_4=ruleFunctionTypeExpressionOLD();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_FunctionTypeExpressionOLD_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:3635:3: this_UnionTypeExpressionOLD_5= ruleUnionTypeExpressionOLD
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getUnionTypeExpressionOLDParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_UnionTypeExpressionOLD_5=ruleUnionTypeExpressionOLD();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_UnionTypeExpressionOLD_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:3644:3: this_IntersectionTypeExpressionOLD_6= ruleIntersectionTypeExpressionOLD
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getIntersectionTypeExpressionOLDParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_IntersectionTypeExpressionOLD_6=ruleIntersectionTypeExpressionOLD();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_IntersectionTypeExpressionOLD_6;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeRefWithoutModifiers"


    // $ANTLR start "entryRuleTypeRefFunctionTypeExpression"
    // InternalTypesParser.g:3656:1: entryRuleTypeRefFunctionTypeExpression returns [EObject current=null] : iv_ruleTypeRefFunctionTypeExpression= ruleTypeRefFunctionTypeExpression EOF ;
    public final EObject entryRuleTypeRefFunctionTypeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRefFunctionTypeExpression = null;


        try {
            // InternalTypesParser.g:3656:70: (iv_ruleTypeRefFunctionTypeExpression= ruleTypeRefFunctionTypeExpression EOF )
            // InternalTypesParser.g:3657:2: iv_ruleTypeRefFunctionTypeExpression= ruleTypeRefFunctionTypeExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeRefFunctionTypeExpression=ruleTypeRefFunctionTypeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeRefFunctionTypeExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeRefFunctionTypeExpression"


    // $ANTLR start "ruleTypeRefFunctionTypeExpression"
    // InternalTypesParser.g:3663:1: ruleTypeRefFunctionTypeExpression returns [EObject current=null] : (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeTypeRef_2= ruleTypeTypeRef | this_UnionTypeExpressionOLD_3= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_4= ruleIntersectionTypeExpressionOLD ) ;
    public final EObject ruleTypeRefFunctionTypeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ParameterizedTypeRef_0 = null;

        EObject this_ArrayTypeRef_1 = null;

        EObject this_TypeTypeRef_2 = null;

        EObject this_UnionTypeExpressionOLD_3 = null;

        EObject this_IntersectionTypeExpressionOLD_4 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3669:2: ( (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeTypeRef_2= ruleTypeTypeRef | this_UnionTypeExpressionOLD_3= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_4= ruleIntersectionTypeExpressionOLD ) )
            // InternalTypesParser.g:3670:2: (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeTypeRef_2= ruleTypeTypeRef | this_UnionTypeExpressionOLD_3= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_4= ruleIntersectionTypeExpressionOLD )
            {
            // InternalTypesParser.g:3670:2: (this_ParameterizedTypeRef_0= ruleParameterizedTypeRef | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeTypeRef_2= ruleTypeTypeRef | this_UnionTypeExpressionOLD_3= ruleUnionTypeExpressionOLD | this_IntersectionTypeExpressionOLD_4= ruleIntersectionTypeExpressionOLD )
            int alt77=5;
            switch ( input.LA(1) ) {
            case Undefined:
            case Indexed:
            case Null:
            case Void:
            case Any:
            case Tilde:
            case RULE_IDENTIFIER:
                {
                alt77=1;
                }
                break;
            case LeftSquareBracket:
                {
                alt77=2;
                }
                break;
            case Constructor:
            case Type:
                {
                alt77=3;
                }
                break;
            case Union:
                {
                alt77=4;
                }
                break;
            case Intersection:
                {
                alt77=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }

            switch (alt77) {
                case 1 :
                    // InternalTypesParser.g:3671:3: this_ParameterizedTypeRef_0= ruleParameterizedTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getParameterizedTypeRefParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ParameterizedTypeRef_0=ruleParameterizedTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ParameterizedTypeRef_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:3680:3: this_ArrayTypeRef_1= ruleArrayTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getArrayTypeRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ArrayTypeRef_1=ruleArrayTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ArrayTypeRef_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:3689:3: this_TypeTypeRef_2= ruleTypeTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getTypeTypeRefParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeTypeRef_2=ruleTypeTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TypeTypeRef_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:3698:3: this_UnionTypeExpressionOLD_3= ruleUnionTypeExpressionOLD
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getUnionTypeExpressionOLDParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_UnionTypeExpressionOLD_3=ruleUnionTypeExpressionOLD();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_UnionTypeExpressionOLD_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:3707:3: this_IntersectionTypeExpressionOLD_4= ruleIntersectionTypeExpressionOLD
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIntersectionTypeExpressionOLDParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_IntersectionTypeExpressionOLD_4=ruleIntersectionTypeExpressionOLD();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_IntersectionTypeExpressionOLD_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeRefFunctionTypeExpression"


    // $ANTLR start "entryRuleTypeArgInTypeTypeRef"
    // InternalTypesParser.g:3719:1: entryRuleTypeArgInTypeTypeRef returns [EObject current=null] : iv_ruleTypeArgInTypeTypeRef= ruleTypeArgInTypeTypeRef EOF ;
    public final EObject entryRuleTypeArgInTypeTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeArgInTypeTypeRef = null;


        try {
            // InternalTypesParser.g:3719:61: (iv_ruleTypeArgInTypeTypeRef= ruleTypeArgInTypeTypeRef EOF )
            // InternalTypesParser.g:3720:2: iv_ruleTypeArgInTypeTypeRef= ruleTypeArgInTypeTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeArgInTypeTypeRef=ruleTypeArgInTypeTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeArgInTypeTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeArgInTypeTypeRef"


    // $ANTLR start "ruleTypeArgInTypeTypeRef"
    // InternalTypesParser.g:3726:1: ruleTypeArgInTypeTypeRef returns [EObject current=null] : (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ThisTypeRefNominal_1= ruleThisTypeRefNominal | ( ( ( () QuestionMark ) )=>this_Wildcard_2= ruleWildcard ) ) ;
    public final EObject ruleTypeArgInTypeTypeRef() throws RecognitionException {
        EObject current = null;

        EObject this_ParameterizedTypeRefNominal_0 = null;

        EObject this_ThisTypeRefNominal_1 = null;

        EObject this_Wildcard_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3732:2: ( (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ThisTypeRefNominal_1= ruleThisTypeRefNominal | ( ( ( () QuestionMark ) )=>this_Wildcard_2= ruleWildcard ) ) )
            // InternalTypesParser.g:3733:2: (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ThisTypeRefNominal_1= ruleThisTypeRefNominal | ( ( ( () QuestionMark ) )=>this_Wildcard_2= ruleWildcard ) )
            {
            // InternalTypesParser.g:3733:2: (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ThisTypeRefNominal_1= ruleThisTypeRefNominal | ( ( ( () QuestionMark ) )=>this_Wildcard_2= ruleWildcard ) )
            int alt78=3;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==Undefined||LA78_0==Indexed||LA78_0==Null||LA78_0==Void||LA78_0==Any||LA78_0==RULE_IDENTIFIER) ) {
                alt78=1;
            }
            else if ( (LA78_0==This_1) ) {
                alt78=2;
            }
            else if ( (LA78_0==QuestionMark) && (synpred16_InternalTypesParser())) {
                alt78=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // InternalTypesParser.g:3734:3: this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ParameterizedTypeRefNominal_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ParameterizedTypeRefNominal_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:3743:3: this_ThisTypeRefNominal_1= ruleThisTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefAccess().getThisTypeRefNominalParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ThisTypeRefNominal_1=ruleThisTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ThisTypeRefNominal_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:3752:3: ( ( ( () QuestionMark ) )=>this_Wildcard_2= ruleWildcard )
                    {
                    // InternalTypesParser.g:3752:3: ( ( ( () QuestionMark ) )=>this_Wildcard_2= ruleWildcard )
                    // InternalTypesParser.g:3753:4: ( ( () QuestionMark ) )=>this_Wildcard_2= ruleWildcard
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefAccess().getWildcardParserRuleCall_2());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Wildcard_2=ruleWildcard();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Wildcard_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeArgInTypeTypeRef"


    // $ANTLR start "entryRuleThisTypeRef"
    // InternalTypesParser.g:3772:1: entryRuleThisTypeRef returns [EObject current=null] : iv_ruleThisTypeRef= ruleThisTypeRef EOF ;
    public final EObject entryRuleThisTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThisTypeRef = null;


        try {
            // InternalTypesParser.g:3772:52: (iv_ruleThisTypeRef= ruleThisTypeRef EOF )
            // InternalTypesParser.g:3773:2: iv_ruleThisTypeRef= ruleThisTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getThisTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleThisTypeRef=ruleThisTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleThisTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleThisTypeRef"


    // $ANTLR start "ruleThisTypeRef"
    // InternalTypesParser.g:3779:1: ruleThisTypeRef returns [EObject current=null] : (this_ThisTypeRefNominal_0= ruleThisTypeRefNominal | this_ThisTypeRefStructural_1= ruleThisTypeRefStructural ) ;
    public final EObject ruleThisTypeRef() throws RecognitionException {
        EObject current = null;

        EObject this_ThisTypeRefNominal_0 = null;

        EObject this_ThisTypeRefStructural_1 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3785:2: ( (this_ThisTypeRefNominal_0= ruleThisTypeRefNominal | this_ThisTypeRefStructural_1= ruleThisTypeRefStructural ) )
            // InternalTypesParser.g:3786:2: (this_ThisTypeRefNominal_0= ruleThisTypeRefNominal | this_ThisTypeRefStructural_1= ruleThisTypeRefStructural )
            {
            // InternalTypesParser.g:3786:2: (this_ThisTypeRefNominal_0= ruleThisTypeRefNominal | this_ThisTypeRefStructural_1= ruleThisTypeRefStructural )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==This_1) ) {
                alt79=1;
            }
            else if ( (LA79_0==Tilde) ) {
                alt79=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }
            switch (alt79) {
                case 1 :
                    // InternalTypesParser.g:3787:3: this_ThisTypeRefNominal_0= ruleThisTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getThisTypeRefAccess().getThisTypeRefNominalParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ThisTypeRefNominal_0=ruleThisTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ThisTypeRefNominal_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:3796:3: this_ThisTypeRefStructural_1= ruleThisTypeRefStructural
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getThisTypeRefAccess().getThisTypeRefStructuralParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ThisTypeRefStructural_1=ruleThisTypeRefStructural();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ThisTypeRefStructural_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleThisTypeRef"


    // $ANTLR start "entryRuleThisTypeRefNominal"
    // InternalTypesParser.g:3808:1: entryRuleThisTypeRefNominal returns [EObject current=null] : iv_ruleThisTypeRefNominal= ruleThisTypeRefNominal EOF ;
    public final EObject entryRuleThisTypeRefNominal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThisTypeRefNominal = null;


        try {
            // InternalTypesParser.g:3808:59: (iv_ruleThisTypeRefNominal= ruleThisTypeRefNominal EOF )
            // InternalTypesParser.g:3809:2: iv_ruleThisTypeRefNominal= ruleThisTypeRefNominal EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getThisTypeRefNominalRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleThisTypeRefNominal=ruleThisTypeRefNominal();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleThisTypeRefNominal; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleThisTypeRefNominal"


    // $ANTLR start "ruleThisTypeRefNominal"
    // InternalTypesParser.g:3815:1: ruleThisTypeRefNominal returns [EObject current=null] : ( () otherlv_1= This_1 ) ;
    public final EObject ruleThisTypeRefNominal() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalTypesParser.g:3821:2: ( ( () otherlv_1= This_1 ) )
            // InternalTypesParser.g:3822:2: ( () otherlv_1= This_1 )
            {
            // InternalTypesParser.g:3822:2: ( () otherlv_1= This_1 )
            // InternalTypesParser.g:3823:3: () otherlv_1= This_1
            {
            // InternalTypesParser.g:3823:3: ()
            // InternalTypesParser.g:3824:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getThisTypeRefNominalAccess().getThisTypeRefNominalAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,This_1,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getThisTypeRefNominalAccess().getThisKeyword_1());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleThisTypeRefNominal"


    // $ANTLR start "entryRuleThisTypeRefStructural"
    // InternalTypesParser.g:3838:1: entryRuleThisTypeRefStructural returns [EObject current=null] : iv_ruleThisTypeRefStructural= ruleThisTypeRefStructural EOF ;
    public final EObject entryRuleThisTypeRefStructural() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThisTypeRefStructural = null;


        try {
            // InternalTypesParser.g:3838:62: (iv_ruleThisTypeRefStructural= ruleThisTypeRefStructural EOF )
            // InternalTypesParser.g:3839:2: iv_ruleThisTypeRefStructural= ruleThisTypeRefStructural EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getThisTypeRefStructuralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleThisTypeRefStructural=ruleThisTypeRefStructural();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleThisTypeRefStructural; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleThisTypeRefStructural"


    // $ANTLR start "ruleThisTypeRefStructural"
    // InternalTypesParser.g:3845:1: ruleThisTypeRefStructural returns [EObject current=null] : ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) otherlv_1= This_1 (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? ) ;
    public final EObject ruleThisTypeRefStructural() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_definedTypingStrategy_0_0 = null;

        EObject this_TStructMemberList_3 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3851:2: ( ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) otherlv_1= This_1 (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? ) )
            // InternalTypesParser.g:3852:2: ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) otherlv_1= This_1 (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? )
            {
            // InternalTypesParser.g:3852:2: ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) otherlv_1= This_1 (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? )
            // InternalTypesParser.g:3853:3: ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) otherlv_1= This_1 (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )?
            {
            // InternalTypesParser.g:3853:3: ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) )
            // InternalTypesParser.g:3854:4: (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator )
            {
            // InternalTypesParser.g:3854:4: (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator )
            // InternalTypesParser.g:3855:5: lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_70);
            lv_definedTypingStrategy_0_0=ruleTypingStrategyUseSiteOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getThisTypeRefStructuralRule());
              					}
              					set(
              						current,
              						"definedTypingStrategy",
              						lv_definedTypingStrategy_0_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyUseSiteOperator");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,This_1,FOLLOW_71); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getThisTypeRefStructuralAccess().getThisKeyword_1());
              		
            }
            // InternalTypesParser.g:3876:3: (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==With) ) {
                int LA80_1 = input.LA(2);

                if ( (LA80_1==LeftCurlyBracket) ) {
                    alt80=1;
                }
            }
            switch (alt80) {
                case 1 :
                    // InternalTypesParser.g:3877:4: otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current]
                    {
                    otherlv_2=(Token)match(input,With,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getThisTypeRefStructuralAccess().getWithKeyword_2_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getThisTypeRefStructuralRule());
                      				}
                      				newCompositeNode(grammarAccess.getThisTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TStructMemberList_3=ruleTStructMemberList(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TStructMemberList_3;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleThisTypeRefStructural"


    // $ANTLR start "entryRuleFunctionTypeExpressionOLD"
    // InternalTypesParser.g:3897:1: entryRuleFunctionTypeExpressionOLD returns [EObject current=null] : iv_ruleFunctionTypeExpressionOLD= ruleFunctionTypeExpressionOLD EOF ;
    public final EObject entryRuleFunctionTypeExpressionOLD() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionTypeExpressionOLD = null;


        try {
            // InternalTypesParser.g:3897:66: (iv_ruleFunctionTypeExpressionOLD= ruleFunctionTypeExpressionOLD EOF )
            // InternalTypesParser.g:3898:2: iv_ruleFunctionTypeExpressionOLD= ruleFunctionTypeExpressionOLD EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFunctionTypeExpressionOLD=ruleFunctionTypeExpressionOLD();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFunctionTypeExpressionOLD; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionTypeExpressionOLD"


    // $ANTLR start "ruleFunctionTypeExpressionOLD"
    // InternalTypesParser.g:3904:1: ruleFunctionTypeExpressionOLD returns [EObject current=null] : ( () otherlv_1= LeftCurlyBracket (otherlv_2= CommercialAt otherlv_3= This otherlv_4= LeftParenthesis ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) ) otherlv_6= RightParenthesis )? otherlv_7= Function (otherlv_8= LessThanSign ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) ) (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )* otherlv_12= GreaterThanSign )? otherlv_13= LeftParenthesis this_TAnonymousFormalParameterList_14= ruleTAnonymousFormalParameterList[$current] otherlv_15= RightParenthesis (this_ColonSepReturnTypeRef_16= ruleColonSepReturnTypeRef[$current] )? otherlv_17= RightCurlyBracket ) ;
    public final EObject ruleFunctionTypeExpressionOLD() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        EObject lv_declaredThisType_5_0 = null;

        EObject lv_ownedTypeVars_9_0 = null;

        EObject lv_ownedTypeVars_11_0 = null;

        EObject this_TAnonymousFormalParameterList_14 = null;

        EObject this_ColonSepReturnTypeRef_16 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:3910:2: ( ( () otherlv_1= LeftCurlyBracket (otherlv_2= CommercialAt otherlv_3= This otherlv_4= LeftParenthesis ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) ) otherlv_6= RightParenthesis )? otherlv_7= Function (otherlv_8= LessThanSign ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) ) (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )* otherlv_12= GreaterThanSign )? otherlv_13= LeftParenthesis this_TAnonymousFormalParameterList_14= ruleTAnonymousFormalParameterList[$current] otherlv_15= RightParenthesis (this_ColonSepReturnTypeRef_16= ruleColonSepReturnTypeRef[$current] )? otherlv_17= RightCurlyBracket ) )
            // InternalTypesParser.g:3911:2: ( () otherlv_1= LeftCurlyBracket (otherlv_2= CommercialAt otherlv_3= This otherlv_4= LeftParenthesis ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) ) otherlv_6= RightParenthesis )? otherlv_7= Function (otherlv_8= LessThanSign ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) ) (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )* otherlv_12= GreaterThanSign )? otherlv_13= LeftParenthesis this_TAnonymousFormalParameterList_14= ruleTAnonymousFormalParameterList[$current] otherlv_15= RightParenthesis (this_ColonSepReturnTypeRef_16= ruleColonSepReturnTypeRef[$current] )? otherlv_17= RightCurlyBracket )
            {
            // InternalTypesParser.g:3911:2: ( () otherlv_1= LeftCurlyBracket (otherlv_2= CommercialAt otherlv_3= This otherlv_4= LeftParenthesis ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) ) otherlv_6= RightParenthesis )? otherlv_7= Function (otherlv_8= LessThanSign ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) ) (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )* otherlv_12= GreaterThanSign )? otherlv_13= LeftParenthesis this_TAnonymousFormalParameterList_14= ruleTAnonymousFormalParameterList[$current] otherlv_15= RightParenthesis (this_ColonSepReturnTypeRef_16= ruleColonSepReturnTypeRef[$current] )? otherlv_17= RightCurlyBracket )
            // InternalTypesParser.g:3912:3: () otherlv_1= LeftCurlyBracket (otherlv_2= CommercialAt otherlv_3= This otherlv_4= LeftParenthesis ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) ) otherlv_6= RightParenthesis )? otherlv_7= Function (otherlv_8= LessThanSign ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) ) (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )* otherlv_12= GreaterThanSign )? otherlv_13= LeftParenthesis this_TAnonymousFormalParameterList_14= ruleTAnonymousFormalParameterList[$current] otherlv_15= RightParenthesis (this_ColonSepReturnTypeRef_16= ruleColonSepReturnTypeRef[$current] )? otherlv_17= RightCurlyBracket
            {
            // InternalTypesParser.g:3912:3: ()
            // InternalTypesParser.g:3913:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionTypeExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftCurlyBracket,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalTypesParser.g:3923:3: (otherlv_2= CommercialAt otherlv_3= This otherlv_4= LeftParenthesis ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) ) otherlv_6= RightParenthesis )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==CommercialAt) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalTypesParser.g:3924:4: otherlv_2= CommercialAt otherlv_3= This otherlv_4= LeftParenthesis ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) ) otherlv_6= RightParenthesis
                    {
                    otherlv_2=(Token)match(input,CommercialAt,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getFunctionTypeExpressionOLDAccess().getCommercialAtKeyword_2_0());
                      			
                    }
                    otherlv_3=(Token)match(input,This,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getFunctionTypeExpressionOLDAccess().getThisKeyword_2_1());
                      			
                    }
                    otherlv_4=(Token)match(input,LeftParenthesis,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_2_2());
                      			
                    }
                    // InternalTypesParser.g:3936:4: ( (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression ) )
                    // InternalTypesParser.g:3937:5: (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression )
                    {
                    // InternalTypesParser.g:3937:5: (lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression )
                    // InternalTypesParser.g:3938:6: lv_declaredThisType_5_0= ruleTypeRefFunctionTypeExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0());
                      					
                    }
                    pushFollow(FOLLOW_60);
                    lv_declaredThisType_5_0=ruleTypeRefFunctionTypeExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getFunctionTypeExpressionOLDRule());
                      						}
                      						set(
                      							current,
                      							"declaredThisType",
                      							lv_declaredThisType_5_0,
                      							"org.eclipse.n4js.ts.TypeExpressions.TypeRefFunctionTypeExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_2_4());
                      			
                    }

                    }
                    break;

            }

            otherlv_7=(Token)match(input,Function,FOLLOW_75); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionKeyword_3());
              		
            }
            // InternalTypesParser.g:3964:3: (otherlv_8= LessThanSign ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) ) (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )* otherlv_12= GreaterThanSign )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==LessThanSign) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalTypesParser.g:3965:4: otherlv_8= LessThanSign ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) ) (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )* otherlv_12= GreaterThanSign
                    {
                    otherlv_8=(Token)match(input,LessThanSign,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getFunctionTypeExpressionOLDAccess().getLessThanSignKeyword_4_0());
                      			
                    }
                    // InternalTypesParser.g:3969:4: ( (lv_ownedTypeVars_9_0= ruleTypeVariable ) )
                    // InternalTypesParser.g:3970:5: (lv_ownedTypeVars_9_0= ruleTypeVariable )
                    {
                    // InternalTypesParser.g:3970:5: (lv_ownedTypeVars_9_0= ruleTypeVariable )
                    // InternalTypesParser.g:3971:6: lv_ownedTypeVars_9_0= ruleTypeVariable
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
                    lv_ownedTypeVars_9_0=ruleTypeVariable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getFunctionTypeExpressionOLDRule());
                      						}
                      						add(
                      							current,
                      							"ownedTypeVars",
                      							lv_ownedTypeVars_9_0,
                      							"org.eclipse.n4js.ts.Types.TypeVariable");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:3988:4: (otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) ) )*
                    loop82:
                    do {
                        int alt82=2;
                        int LA82_0 = input.LA(1);

                        if ( (LA82_0==Comma) ) {
                            alt82=1;
                        }


                        switch (alt82) {
                    	case 1 :
                    	    // InternalTypesParser.g:3989:5: otherlv_10= Comma ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) )
                    	    {
                    	    otherlv_10=(Token)match(input,Comma,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_10, grammarAccess.getFunctionTypeExpressionOLDAccess().getCommaKeyword_4_2_0());
                    	      				
                    	    }
                    	    // InternalTypesParser.g:3993:5: ( (lv_ownedTypeVars_11_0= ruleTypeVariable ) )
                    	    // InternalTypesParser.g:3994:6: (lv_ownedTypeVars_11_0= ruleTypeVariable )
                    	    {
                    	    // InternalTypesParser.g:3994:6: (lv_ownedTypeVars_11_0= ruleTypeVariable )
                    	    // InternalTypesParser.g:3995:7: lv_ownedTypeVars_11_0= ruleTypeVariable
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_47);
                    	    lv_ownedTypeVars_11_0=ruleTypeVariable();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getFunctionTypeExpressionOLDRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"ownedTypeVars",
                    	      								lv_ownedTypeVars_11_0,
                    	      								"org.eclipse.n4js.ts.Types.TypeVariable");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop82;
                        }
                    } while (true);

                    otherlv_12=(Token)match(input,GreaterThanSign,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getFunctionTypeExpressionOLDAccess().getGreaterThanSignKeyword_4_3());
                      			
                    }

                    }
                    break;

            }

            otherlv_13=(Token)match(input,LeftParenthesis,FOLLOW_76); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_13, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_5());
              		
            }
            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getFunctionTypeExpressionOLDRule());
              			}
              			newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getTAnonymousFormalParameterListParserRuleCall_6());
              		
            }
            pushFollow(FOLLOW_60);
            this_TAnonymousFormalParameterList_14=ruleTAnonymousFormalParameterList(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TAnonymousFormalParameterList_14;
              			afterParserOrEnumRuleCall();
              		
            }
            otherlv_15=(Token)match(input,RightParenthesis,FOLLOW_77); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_15, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_7());
              		
            }
            // InternalTypesParser.g:4037:3: (this_ColonSepReturnTypeRef_16= ruleColonSepReturnTypeRef[$current] )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==Colon) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalTypesParser.g:4038:4: this_ColonSepReturnTypeRef_16= ruleColonSepReturnTypeRef[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getFunctionTypeExpressionOLDRule());
                      				}
                      				newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getColonSepReturnTypeRefParserRuleCall_8());
                      			
                    }
                    pushFollow(FOLLOW_18);
                    this_ColonSepReturnTypeRef_16=ruleColonSepReturnTypeRef(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ColonSepReturnTypeRef_16;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            otherlv_17=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_17, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_9());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunctionTypeExpressionOLD"


    // $ANTLR start "entryRuleArrowFunctionTypeExpression"
    // InternalTypesParser.g:4058:1: entryRuleArrowFunctionTypeExpression returns [EObject current=null] : iv_ruleArrowFunctionTypeExpression= ruleArrowFunctionTypeExpression EOF ;
    public final EObject entryRuleArrowFunctionTypeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrowFunctionTypeExpression = null;


        try {
            // InternalTypesParser.g:4058:68: (iv_ruleArrowFunctionTypeExpression= ruleArrowFunctionTypeExpression EOF )
            // InternalTypesParser.g:4059:2: iv_ruleArrowFunctionTypeExpression= ruleArrowFunctionTypeExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrowFunctionTypeExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArrowFunctionTypeExpression=ruleArrowFunctionTypeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrowFunctionTypeExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrowFunctionTypeExpression"


    // $ANTLR start "ruleArrowFunctionTypeExpression"
    // InternalTypesParser.g:4065:1: ruleArrowFunctionTypeExpression returns [EObject current=null] : ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=> ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign ) ) ( (lv_returnTypeRef_5_0= rulePrimaryTypeExpression ) ) ) ;
    public final EObject ruleArrowFunctionTypeExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject this_TAnonymousFormalParameterList_2 = null;

        EObject lv_returnTypeRef_5_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4071:2: ( ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=> ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign ) ) ( (lv_returnTypeRef_5_0= rulePrimaryTypeExpression ) ) ) )
            // InternalTypesParser.g:4072:2: ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=> ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign ) ) ( (lv_returnTypeRef_5_0= rulePrimaryTypeExpression ) ) )
            {
            // InternalTypesParser.g:4072:2: ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=> ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign ) ) ( (lv_returnTypeRef_5_0= rulePrimaryTypeExpression ) ) )
            // InternalTypesParser.g:4073:3: ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=> ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign ) ) ( (lv_returnTypeRef_5_0= rulePrimaryTypeExpression ) )
            {
            // InternalTypesParser.g:4073:3: ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=> ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign ) )
            // InternalTypesParser.g:4074:4: ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=> ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign )
            {
            // InternalTypesParser.g:4083:4: ( () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign )
            // InternalTypesParser.g:4084:5: () otherlv_1= LeftParenthesis this_TAnonymousFormalParameterList_2= ruleTAnonymousFormalParameterList[$current] otherlv_3= RightParenthesis otherlv_4= EqualsSignGreaterThanSign
            {
            // InternalTypesParser.g:4084:5: ()
            // InternalTypesParser.g:4085:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getArrowFunctionTypeExpressionAccess().getFunctionTypeExpressionAction_0_0_0(),
              							current);
              					
            }

            }

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_76); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getArrowFunctionTypeExpressionAccess().getLeftParenthesisKeyword_0_0_1());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getArrowFunctionTypeExpressionRule());
              					}
              					newCompositeNode(grammarAccess.getArrowFunctionTypeExpressionAccess().getTAnonymousFormalParameterListParserRuleCall_0_0_2());
              				
            }
            pushFollow(FOLLOW_60);
            this_TAnonymousFormalParameterList_2=ruleTAnonymousFormalParameterList(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					current = this_TAnonymousFormalParameterList_2;
              					afterParserOrEnumRuleCall();
              				
            }
            otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_78); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_3, grammarAccess.getArrowFunctionTypeExpressionAccess().getRightParenthesisKeyword_0_0_3());
              				
            }
            otherlv_4=(Token)match(input,EqualsSignGreaterThanSign,FOLLOW_79); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_4, grammarAccess.getArrowFunctionTypeExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_4());
              				
            }

            }


            }

            // InternalTypesParser.g:4116:3: ( (lv_returnTypeRef_5_0= rulePrimaryTypeExpression ) )
            // InternalTypesParser.g:4117:4: (lv_returnTypeRef_5_0= rulePrimaryTypeExpression )
            {
            // InternalTypesParser.g:4117:4: (lv_returnTypeRef_5_0= rulePrimaryTypeExpression )
            // InternalTypesParser.g:4118:5: lv_returnTypeRef_5_0= rulePrimaryTypeExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_returnTypeRef_5_0=rulePrimaryTypeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArrowFunctionTypeExpressionRule());
              					}
              					set(
              						current,
              						"returnTypeRef",
              						lv_returnTypeRef_5_0,
              						"org.eclipse.n4js.ts.TypeExpressions.PrimaryTypeExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrowFunctionTypeExpression"


    // $ANTLR start "ruleTAnonymousFormalParameterList"
    // InternalTypesParser.g:4140:1: ruleTAnonymousFormalParameterList[EObject in_current] returns [EObject current=in_current] : ( ( (lv_fpars_0_0= ruleTAnonymousFormalParameter ) ) (otherlv_1= Comma ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) ) )* )? ;
    public final EObject ruleTAnonymousFormalParameterList(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_1=null;
        EObject lv_fpars_0_0 = null;

        EObject lv_fpars_2_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4146:2: ( ( ( (lv_fpars_0_0= ruleTAnonymousFormalParameter ) ) (otherlv_1= Comma ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) ) )* )? )
            // InternalTypesParser.g:4147:2: ( ( (lv_fpars_0_0= ruleTAnonymousFormalParameter ) ) (otherlv_1= Comma ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) ) )* )?
            {
            // InternalTypesParser.g:4147:2: ( ( (lv_fpars_0_0= ruleTAnonymousFormalParameter ) ) (otherlv_1= Comma ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) ) )* )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( ((LA86_0>=Intersection && LA86_0<=Constructor)||LA86_0==Implements||(LA86_0>=Promisify && LA86_0<=Interface)||(LA86_0>=Protected && LA86_0<=Abstract)||LA86_0==External||(LA86_0>=Indexed && LA86_0<=Project)||LA86_0==Public||LA86_0==Static||LA86_0==Target||(LA86_0>=Async && LA86_0<=Await)||LA86_0==Union||(LA86_0>=Yield && LA86_0<=This)||(LA86_0>=From && LA86_0<=This_1)||(LA86_0>=Type && LA86_0<=Void)||(LA86_0>=FullStopFullStopFullStop && LA86_0<=Any)||(LA86_0>=Get && LA86_0<=Let)||(LA86_0>=Out && LA86_0<=Set)||LA86_0==As||LA86_0==Of||LA86_0==LeftCurlyBracket||LA86_0==Tilde||LA86_0==RULE_IDENTIFIER) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalTypesParser.g:4148:3: ( (lv_fpars_0_0= ruleTAnonymousFormalParameter ) ) (otherlv_1= Comma ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) ) )*
                    {
                    // InternalTypesParser.g:4148:3: ( (lv_fpars_0_0= ruleTAnonymousFormalParameter ) )
                    // InternalTypesParser.g:4149:4: (lv_fpars_0_0= ruleTAnonymousFormalParameter )
                    {
                    // InternalTypesParser.g:4149:4: (lv_fpars_0_0= ruleTAnonymousFormalParameter )
                    // InternalTypesParser.g:4150:5: lv_fpars_0_0= ruleTAnonymousFormalParameter
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_80);
                    lv_fpars_0_0=ruleTAnonymousFormalParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterListRule());
                      					}
                      					add(
                      						current,
                      						"fpars",
                      						lv_fpars_0_0,
                      						"org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameter");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }

                    // InternalTypesParser.g:4167:3: (otherlv_1= Comma ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) ) )*
                    loop85:
                    do {
                        int alt85=2;
                        int LA85_0 = input.LA(1);

                        if ( (LA85_0==Comma) ) {
                            alt85=1;
                        }


                        switch (alt85) {
                    	case 1 :
                    	    // InternalTypesParser.g:4168:4: otherlv_1= Comma ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) )
                    	    {
                    	    otherlv_1=(Token)match(input,Comma,FOLLOW_81); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      				newLeafNode(otherlv_1, grammarAccess.getTAnonymousFormalParameterListAccess().getCommaKeyword_1_0());
                    	      			
                    	    }
                    	    // InternalTypesParser.g:4172:4: ( (lv_fpars_2_0= ruleTAnonymousFormalParameter ) )
                    	    // InternalTypesParser.g:4173:5: (lv_fpars_2_0= ruleTAnonymousFormalParameter )
                    	    {
                    	    // InternalTypesParser.g:4173:5: (lv_fpars_2_0= ruleTAnonymousFormalParameter )
                    	    // InternalTypesParser.g:4174:6: lv_fpars_2_0= ruleTAnonymousFormalParameter
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_1_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_80);
                    	    lv_fpars_2_0=ruleTAnonymousFormalParameter();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterListRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"fpars",
                    	      							lv_fpars_2_0,
                    	      							"org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameter");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop85;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTAnonymousFormalParameterList"


    // $ANTLR start "entryRuleTAnonymousFormalParameter"
    // InternalTypesParser.g:4196:1: entryRuleTAnonymousFormalParameter returns [EObject current=null] : iv_ruleTAnonymousFormalParameter= ruleTAnonymousFormalParameter EOF ;
    public final EObject entryRuleTAnonymousFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTAnonymousFormalParameter = null;


        try {
            // InternalTypesParser.g:4196:66: (iv_ruleTAnonymousFormalParameter= ruleTAnonymousFormalParameter EOF )
            // InternalTypesParser.g:4197:2: iv_ruleTAnonymousFormalParameter= ruleTAnonymousFormalParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTAnonymousFormalParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTAnonymousFormalParameter=ruleTAnonymousFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTAnonymousFormalParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTAnonymousFormalParameter"


    // $ANTLR start "ruleTAnonymousFormalParameter"
    // InternalTypesParser.g:4203:1: ruleTAnonymousFormalParameter returns [EObject current=null] : ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) ) | ( (lv_typeRef_3_0= ruleTypeRef ) ) ) this_DefaultFormalParameter_4= ruleDefaultFormalParameter[$current] ) ;
    public final EObject ruleTAnonymousFormalParameter() throws RecognitionException {
        EObject current = null;

        Token lv_variadic_0_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject this_ColonSepTypeRef_2 = null;

        EObject lv_typeRef_3_0 = null;

        EObject this_DefaultFormalParameter_4 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4209:2: ( ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) ) | ( (lv_typeRef_3_0= ruleTypeRef ) ) ) this_DefaultFormalParameter_4= ruleDefaultFormalParameter[$current] ) )
            // InternalTypesParser.g:4210:2: ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) ) | ( (lv_typeRef_3_0= ruleTypeRef ) ) ) this_DefaultFormalParameter_4= ruleDefaultFormalParameter[$current] )
            {
            // InternalTypesParser.g:4210:2: ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) ) | ( (lv_typeRef_3_0= ruleTypeRef ) ) ) this_DefaultFormalParameter_4= ruleDefaultFormalParameter[$current] )
            // InternalTypesParser.g:4211:3: ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) ) | ( (lv_typeRef_3_0= ruleTypeRef ) ) ) this_DefaultFormalParameter_4= ruleDefaultFormalParameter[$current]
            {
            // InternalTypesParser.g:4211:3: ( (lv_variadic_0_0= FullStopFullStopFullStop ) )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==FullStopFullStopFullStop) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalTypesParser.g:4212:4: (lv_variadic_0_0= FullStopFullStopFullStop )
                    {
                    // InternalTypesParser.g:4212:4: (lv_variadic_0_0= FullStopFullStopFullStop )
                    // InternalTypesParser.g:4213:5: lv_variadic_0_0= FullStopFullStopFullStop
                    {
                    lv_variadic_0_0=(Token)match(input,FullStopFullStopFullStop,FOLLOW_82); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_variadic_0_0, grammarAccess.getTAnonymousFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTAnonymousFormalParameterRule());
                      					}
                      					setWithLastConsumed(current, "variadic", true, "...");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalTypesParser.g:4225:3: ( ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) ) | ( (lv_typeRef_3_0= ruleTypeRef ) ) )
            int alt88=2;
            alt88 = dfa88.predict(input);
            switch (alt88) {
                case 1 :
                    // InternalTypesParser.g:4226:4: ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) )
                    {
                    // InternalTypesParser.g:4226:4: ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) )
                    // InternalTypesParser.g:4227:5: ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) )
                    {
                    // InternalTypesParser.g:4236:5: ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) )
                    // InternalTypesParser.g:4237:6: ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )
                    {
                    // InternalTypesParser.g:4237:6: ( (lv_name_1_0= ruleBindingIdentifier ) )
                    // InternalTypesParser.g:4238:7: (lv_name_1_0= ruleBindingIdentifier )
                    {
                    // InternalTypesParser.g:4238:7: (lv_name_1_0= ruleBindingIdentifier )
                    // InternalTypesParser.g:4239:8: lv_name_1_0= ruleBindingIdentifier
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0());
                      							
                    }
                    pushFollow(FOLLOW_56);
                    lv_name_1_0=ruleBindingIdentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_1_0,
                      									"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }

                    // InternalTypesParser.g:4256:6: ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )
                    // InternalTypesParser.g:4257:7: ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current]
                    {
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getTAnonymousFormalParameterRule());
                      							}
                      							newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getColonSepTypeRefParserRuleCall_1_0_0_1());
                      						
                    }
                    pushFollow(FOLLOW_83);
                    this_ColonSepTypeRef_2=ruleColonSepTypeRef(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							current = this_ColonSepTypeRef_2;
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:4273:4: ( (lv_typeRef_3_0= ruleTypeRef ) )
                    {
                    // InternalTypesParser.g:4273:4: ( (lv_typeRef_3_0= ruleTypeRef ) )
                    // InternalTypesParser.g:4274:5: (lv_typeRef_3_0= ruleTypeRef )
                    {
                    // InternalTypesParser.g:4274:5: (lv_typeRef_3_0= ruleTypeRef )
                    // InternalTypesParser.g:4275:6: lv_typeRef_3_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefTypeRefParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_83);
                    lv_typeRef_3_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterRule());
                      						}
                      						set(
                      							current,
                      							"typeRef",
                      							lv_typeRef_3_0,
                      							"org.eclipse.n4js.ts.Types.TypeRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTAnonymousFormalParameterRule());
              			}
              			newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getDefaultFormalParameterParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_2);
            this_DefaultFormalParameter_4=ruleDefaultFormalParameter(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_DefaultFormalParameter_4;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTAnonymousFormalParameter"


    // $ANTLR start "entryRuleTFormalParameter"
    // InternalTypesParser.g:4308:1: entryRuleTFormalParameter returns [EObject current=null] : iv_ruleTFormalParameter= ruleTFormalParameter EOF ;
    public final EObject entryRuleTFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTFormalParameter = null;


        try {
            // InternalTypesParser.g:4308:57: (iv_ruleTFormalParameter= ruleTFormalParameter EOF )
            // InternalTypesParser.g:4309:2: iv_ruleTFormalParameter= ruleTFormalParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTFormalParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTFormalParameter=ruleTFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTFormalParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTFormalParameter"


    // $ANTLR start "ruleTFormalParameter"
    // InternalTypesParser.g:4315:1: ruleTFormalParameter returns [EObject current=null] : ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( (lv_name_1_0= ruleBindingIdentifier ) ) this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] this_DefaultFormalParameter_3= ruleDefaultFormalParameter[$current] ) ;
    public final EObject ruleTFormalParameter() throws RecognitionException {
        EObject current = null;

        Token lv_variadic_0_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject this_ColonSepTypeRef_2 = null;

        EObject this_DefaultFormalParameter_3 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4321:2: ( ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( (lv_name_1_0= ruleBindingIdentifier ) ) this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] this_DefaultFormalParameter_3= ruleDefaultFormalParameter[$current] ) )
            // InternalTypesParser.g:4322:2: ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( (lv_name_1_0= ruleBindingIdentifier ) ) this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] this_DefaultFormalParameter_3= ruleDefaultFormalParameter[$current] )
            {
            // InternalTypesParser.g:4322:2: ( ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( (lv_name_1_0= ruleBindingIdentifier ) ) this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] this_DefaultFormalParameter_3= ruleDefaultFormalParameter[$current] )
            // InternalTypesParser.g:4323:3: ( (lv_variadic_0_0= FullStopFullStopFullStop ) )? ( (lv_name_1_0= ruleBindingIdentifier ) ) this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] this_DefaultFormalParameter_3= ruleDefaultFormalParameter[$current]
            {
            // InternalTypesParser.g:4323:3: ( (lv_variadic_0_0= FullStopFullStopFullStop ) )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==FullStopFullStopFullStop) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // InternalTypesParser.g:4324:4: (lv_variadic_0_0= FullStopFullStopFullStop )
                    {
                    // InternalTypesParser.g:4324:4: (lv_variadic_0_0= FullStopFullStopFullStop )
                    // InternalTypesParser.g:4325:5: lv_variadic_0_0= FullStopFullStopFullStop
                    {
                    lv_variadic_0_0=(Token)match(input,FullStopFullStopFullStop,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_variadic_0_0, grammarAccess.getTFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTFormalParameterRule());
                      					}
                      					setWithLastConsumed(current, "variadic", true, "...");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalTypesParser.g:4337:3: ( (lv_name_1_0= ruleBindingIdentifier ) )
            // InternalTypesParser.g:4338:4: (lv_name_1_0= ruleBindingIdentifier )
            {
            // InternalTypesParser.g:4338:4: (lv_name_1_0= ruleBindingIdentifier )
            // InternalTypesParser.g:4339:5: lv_name_1_0= ruleBindingIdentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_56);
            lv_name_1_0=ruleBindingIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTFormalParameterRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTFormalParameterRule());
              			}
              			newCompositeNode(grammarAccess.getTFormalParameterAccess().getColonSepTypeRefParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_83);
            this_ColonSepTypeRef_2=ruleColonSepTypeRef(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_ColonSepTypeRef_2;
              			afterParserOrEnumRuleCall();
              		
            }
            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTFormalParameterRule());
              			}
              			newCompositeNode(grammarAccess.getTFormalParameterAccess().getDefaultFormalParameterParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_DefaultFormalParameter_3=ruleDefaultFormalParameter(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_DefaultFormalParameter_3;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTFormalParameter"


    // $ANTLR start "ruleDefaultFormalParameter"
    // InternalTypesParser.g:4383:1: ruleDefaultFormalParameter[EObject in_current] returns [EObject current=in_current] : ( ( (lv_hasInitializerAssignment_0_0= EqualsSign ) ) ( (lv_astInitializer_1_0= ruleTypeReferenceName ) )? )? ;
    public final EObject ruleDefaultFormalParameter(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token lv_hasInitializerAssignment_0_0=null;
        AntlrDatatypeRuleToken lv_astInitializer_1_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4389:2: ( ( ( (lv_hasInitializerAssignment_0_0= EqualsSign ) ) ( (lv_astInitializer_1_0= ruleTypeReferenceName ) )? )? )
            // InternalTypesParser.g:4390:2: ( ( (lv_hasInitializerAssignment_0_0= EqualsSign ) ) ( (lv_astInitializer_1_0= ruleTypeReferenceName ) )? )?
            {
            // InternalTypesParser.g:4390:2: ( ( (lv_hasInitializerAssignment_0_0= EqualsSign ) ) ( (lv_astInitializer_1_0= ruleTypeReferenceName ) )? )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==EqualsSign) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalTypesParser.g:4391:3: ( (lv_hasInitializerAssignment_0_0= EqualsSign ) ) ( (lv_astInitializer_1_0= ruleTypeReferenceName ) )?
                    {
                    // InternalTypesParser.g:4391:3: ( (lv_hasInitializerAssignment_0_0= EqualsSign ) )
                    // InternalTypesParser.g:4392:4: (lv_hasInitializerAssignment_0_0= EqualsSign )
                    {
                    // InternalTypesParser.g:4392:4: (lv_hasInitializerAssignment_0_0= EqualsSign )
                    // InternalTypesParser.g:4393:5: lv_hasInitializerAssignment_0_0= EqualsSign
                    {
                    lv_hasInitializerAssignment_0_0=(Token)match(input,EqualsSign,FOLLOW_84); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_hasInitializerAssignment_0_0, grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentEqualsSignKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getDefaultFormalParameterRule());
                      					}
                      					setWithLastConsumed(current, "hasInitializerAssignment", true, "=");
                      				
                    }

                    }


                    }

                    // InternalTypesParser.g:4405:3: ( (lv_astInitializer_1_0= ruleTypeReferenceName ) )?
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==Undefined||LA90_0==Indexed||LA90_0==Null||LA90_0==Void||LA90_0==Any||LA90_0==RULE_IDENTIFIER) ) {
                        alt90=1;
                    }
                    switch (alt90) {
                        case 1 :
                            // InternalTypesParser.g:4406:4: (lv_astInitializer_1_0= ruleTypeReferenceName )
                            {
                            // InternalTypesParser.g:4406:4: (lv_astInitializer_1_0= ruleTypeReferenceName )
                            // InternalTypesParser.g:4407:5: lv_astInitializer_1_0= ruleTypeReferenceName
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getDefaultFormalParameterAccess().getAstInitializerTypeReferenceNameParserRuleCall_1_0());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            lv_astInitializer_1_0=ruleTypeReferenceName();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					if (current==null) {
                              						current = createModelElementForParent(grammarAccess.getDefaultFormalParameterRule());
                              					}
                              					set(
                              						current,
                              						"astInitializer",
                              						lv_astInitializer_1_0,
                              						"org.eclipse.n4js.ts.Types.TypeReferenceName");
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDefaultFormalParameter"


    // $ANTLR start "entryRuleUnionTypeExpressionOLD"
    // InternalTypesParser.g:4428:1: entryRuleUnionTypeExpressionOLD returns [EObject current=null] : iv_ruleUnionTypeExpressionOLD= ruleUnionTypeExpressionOLD EOF ;
    public final EObject entryRuleUnionTypeExpressionOLD() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnionTypeExpressionOLD = null;


        try {
            // InternalTypesParser.g:4428:63: (iv_ruleUnionTypeExpressionOLD= ruleUnionTypeExpressionOLD EOF )
            // InternalTypesParser.g:4429:2: iv_ruleUnionTypeExpressionOLD= ruleUnionTypeExpressionOLD EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnionTypeExpressionOLDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnionTypeExpressionOLD=ruleUnionTypeExpressionOLD();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnionTypeExpressionOLD; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnionTypeExpressionOLD"


    // $ANTLR start "ruleUnionTypeExpressionOLD"
    // InternalTypesParser.g:4435:1: ruleUnionTypeExpressionOLD returns [EObject current=null] : ( () otherlv_1= Union otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket ) ;
    public final EObject ruleUnionTypeExpressionOLD() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_typeRefs_3_0 = null;

        EObject lv_typeRefs_5_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4441:2: ( ( () otherlv_1= Union otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket ) )
            // InternalTypesParser.g:4442:2: ( () otherlv_1= Union otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket )
            {
            // InternalTypesParser.g:4442:2: ( () otherlv_1= Union otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket )
            // InternalTypesParser.g:4443:3: () otherlv_1= Union otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket
            {
            // InternalTypesParser.g:4443:3: ()
            // InternalTypesParser.g:4444:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getUnionTypeExpressionOLDAccess().getUnionTypeExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,Union,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getUnionTypeExpressionOLDAccess().getUnionKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,LeftCurlyBracket,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getUnionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            // InternalTypesParser.g:4458:3: ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) )
            // InternalTypesParser.g:4459:4: (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers )
            {
            // InternalTypesParser.g:4459:4: (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers )
            // InternalTypesParser.g:4460:5: lv_typeRefs_3_0= ruleTypeRefWithoutModifiers
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_68);
            lv_typeRefs_3_0=ruleTypeRefWithoutModifiers();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getUnionTypeExpressionOLDRule());
              					}
              					add(
              						current,
              						"typeRefs",
              						lv_typeRefs_3_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithoutModifiers");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:4477:3: (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==Comma) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // InternalTypesParser.g:4478:4: otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) )
            	    {
            	    otherlv_4=(Token)match(input,Comma,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getUnionTypeExpressionOLDAccess().getCommaKeyword_4_0());
            	      			
            	    }
            	    // InternalTypesParser.g:4482:4: ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) )
            	    // InternalTypesParser.g:4483:5: (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers )
            	    {
            	    // InternalTypesParser.g:4483:5: (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers )
            	    // InternalTypesParser.g:4484:6: lv_typeRefs_5_0= ruleTypeRefWithoutModifiers
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_68);
            	    lv_typeRefs_5_0=ruleTypeRefWithoutModifiers();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getUnionTypeExpressionOLDRule());
            	      						}
            	      						add(
            	      							current,
            	      							"typeRefs",
            	      							lv_typeRefs_5_0,
            	      							"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithoutModifiers");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop92;
                }
            } while (true);

            otherlv_6=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getUnionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnionTypeExpressionOLD"


    // $ANTLR start "entryRuleIntersectionTypeExpressionOLD"
    // InternalTypesParser.g:4510:1: entryRuleIntersectionTypeExpressionOLD returns [EObject current=null] : iv_ruleIntersectionTypeExpressionOLD= ruleIntersectionTypeExpressionOLD EOF ;
    public final EObject entryRuleIntersectionTypeExpressionOLD() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntersectionTypeExpressionOLD = null;


        try {
            // InternalTypesParser.g:4510:70: (iv_ruleIntersectionTypeExpressionOLD= ruleIntersectionTypeExpressionOLD EOF )
            // InternalTypesParser.g:4511:2: iv_ruleIntersectionTypeExpressionOLD= ruleIntersectionTypeExpressionOLD EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIntersectionTypeExpressionOLDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIntersectionTypeExpressionOLD=ruleIntersectionTypeExpressionOLD();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIntersectionTypeExpressionOLD; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIntersectionTypeExpressionOLD"


    // $ANTLR start "ruleIntersectionTypeExpressionOLD"
    // InternalTypesParser.g:4517:1: ruleIntersectionTypeExpressionOLD returns [EObject current=null] : ( () otherlv_1= Intersection otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket ) ;
    public final EObject ruleIntersectionTypeExpressionOLD() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_typeRefs_3_0 = null;

        EObject lv_typeRefs_5_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4523:2: ( ( () otherlv_1= Intersection otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket ) )
            // InternalTypesParser.g:4524:2: ( () otherlv_1= Intersection otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket )
            {
            // InternalTypesParser.g:4524:2: ( () otherlv_1= Intersection otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket )
            // InternalTypesParser.g:4525:3: () otherlv_1= Intersection otherlv_2= LeftCurlyBracket ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) ) (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )* otherlv_6= RightCurlyBracket
            {
            // InternalTypesParser.g:4525:3: ()
            // InternalTypesParser.g:4526:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionTypeExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,Intersection,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,LeftCurlyBracket,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getIntersectionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            // InternalTypesParser.g:4540:3: ( (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers ) )
            // InternalTypesParser.g:4541:4: (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers )
            {
            // InternalTypesParser.g:4541:4: (lv_typeRefs_3_0= ruleTypeRefWithoutModifiers )
            // InternalTypesParser.g:4542:5: lv_typeRefs_3_0= ruleTypeRefWithoutModifiers
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefWithoutModifiersParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_68);
            lv_typeRefs_3_0=ruleTypeRefWithoutModifiers();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getIntersectionTypeExpressionOLDRule());
              					}
              					add(
              						current,
              						"typeRefs",
              						lv_typeRefs_3_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithoutModifiers");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:4559:3: (otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) ) )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( (LA93_0==Comma) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // InternalTypesParser.g:4560:4: otherlv_4= Comma ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) )
            	    {
            	    otherlv_4=(Token)match(input,Comma,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIntersectionTypeExpressionOLDAccess().getCommaKeyword_4_0());
            	      			
            	    }
            	    // InternalTypesParser.g:4564:4: ( (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers ) )
            	    // InternalTypesParser.g:4565:5: (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers )
            	    {
            	    // InternalTypesParser.g:4565:5: (lv_typeRefs_5_0= ruleTypeRefWithoutModifiers )
            	    // InternalTypesParser.g:4566:6: lv_typeRefs_5_0= ruleTypeRefWithoutModifiers
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefWithoutModifiersParserRuleCall_4_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_68);
            	    lv_typeRefs_5_0=ruleTypeRefWithoutModifiers();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getIntersectionTypeExpressionOLDRule());
            	      						}
            	      						add(
            	      							current,
            	      							"typeRefs",
            	      							lv_typeRefs_5_0,
            	      							"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithoutModifiers");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);

            otherlv_6=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getIntersectionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIntersectionTypeExpressionOLD"


    // $ANTLR start "entryRuleParameterizedTypeRef"
    // InternalTypesParser.g:4592:1: entryRuleParameterizedTypeRef returns [EObject current=null] : iv_ruleParameterizedTypeRef= ruleParameterizedTypeRef EOF ;
    public final EObject entryRuleParameterizedTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterizedTypeRef = null;


        try {
            // InternalTypesParser.g:4592:61: (iv_ruleParameterizedTypeRef= ruleParameterizedTypeRef EOF )
            // InternalTypesParser.g:4593:2: iv_ruleParameterizedTypeRef= ruleParameterizedTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterizedTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameterizedTypeRef=ruleParameterizedTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameterizedTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterizedTypeRef"


    // $ANTLR start "ruleParameterizedTypeRef"
    // InternalTypesParser.g:4599:1: ruleParameterizedTypeRef returns [EObject current=null] : (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ParameterizedTypeRefStructural_1= ruleParameterizedTypeRefStructural ) ;
    public final EObject ruleParameterizedTypeRef() throws RecognitionException {
        EObject current = null;

        EObject this_ParameterizedTypeRefNominal_0 = null;

        EObject this_ParameterizedTypeRefStructural_1 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4605:2: ( (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ParameterizedTypeRefStructural_1= ruleParameterizedTypeRefStructural ) )
            // InternalTypesParser.g:4606:2: (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ParameterizedTypeRefStructural_1= ruleParameterizedTypeRefStructural )
            {
            // InternalTypesParser.g:4606:2: (this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal | this_ParameterizedTypeRefStructural_1= ruleParameterizedTypeRefStructural )
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==Undefined||LA94_0==Indexed||LA94_0==Null||LA94_0==Void||LA94_0==Any||LA94_0==RULE_IDENTIFIER) ) {
                alt94=1;
            }
            else if ( (LA94_0==Tilde) ) {
                alt94=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }
            switch (alt94) {
                case 1 :
                    // InternalTypesParser.g:4607:3: this_ParameterizedTypeRefNominal_0= ruleParameterizedTypeRefNominal
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ParameterizedTypeRefNominal_0=ruleParameterizedTypeRefNominal();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ParameterizedTypeRefNominal_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:4616:3: this_ParameterizedTypeRefStructural_1= ruleParameterizedTypeRefStructural
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefStructuralParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ParameterizedTypeRefStructural_1=ruleParameterizedTypeRefStructural();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ParameterizedTypeRefStructural_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterizedTypeRef"


    // $ANTLR start "entryRuleParameterizedTypeRefNominal"
    // InternalTypesParser.g:4628:1: entryRuleParameterizedTypeRefNominal returns [EObject current=null] : iv_ruleParameterizedTypeRefNominal= ruleParameterizedTypeRefNominal EOF ;
    public final EObject entryRuleParameterizedTypeRefNominal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterizedTypeRefNominal = null;


        try {
            // InternalTypesParser.g:4628:68: (iv_ruleParameterizedTypeRefNominal= ruleParameterizedTypeRefNominal EOF )
            // InternalTypesParser.g:4629:2: iv_ruleParameterizedTypeRefNominal= ruleParameterizedTypeRefNominal EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterizedTypeRefNominalRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameterizedTypeRefNominal=ruleParameterizedTypeRefNominal();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameterizedTypeRefNominal; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterizedTypeRefNominal"


    // $ANTLR start "ruleParameterizedTypeRefNominal"
    // InternalTypesParser.g:4635:1: ruleParameterizedTypeRefNominal returns [EObject current=null] : this_TypeAndTypeArguments_0= ruleTypeAndTypeArguments[$current] ;
    public final EObject ruleParameterizedTypeRefNominal() throws RecognitionException {
        EObject current = null;

        EObject this_TypeAndTypeArguments_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4641:2: (this_TypeAndTypeArguments_0= ruleTypeAndTypeArguments[$current] )
            // InternalTypesParser.g:4642:2: this_TypeAndTypeArguments_0= ruleTypeAndTypeArguments[$current]
            {
            if ( state.backtracking==0 ) {

              		if (current==null) {
              			current = createModelElement(grammarAccess.getParameterizedTypeRefNominalRule());
              		}
              		newCompositeNode(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeAndTypeArgumentsParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_TypeAndTypeArguments_0=ruleTypeAndTypeArguments(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_TypeAndTypeArguments_0;
              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterizedTypeRefNominal"


    // $ANTLR start "entryRuleArrayTypeRef"
    // InternalTypesParser.g:4656:1: entryRuleArrayTypeRef returns [EObject current=null] : iv_ruleArrayTypeRef= ruleArrayTypeRef EOF ;
    public final EObject entryRuleArrayTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayTypeRef = null;


        try {
            // InternalTypesParser.g:4656:53: (iv_ruleArrayTypeRef= ruleArrayTypeRef EOF )
            // InternalTypesParser.g:4657:2: iv_ruleArrayTypeRef= ruleArrayTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArrayTypeRef=ruleArrayTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayTypeRef"


    // $ANTLR start "ruleArrayTypeRef"
    // InternalTypesParser.g:4663:1: ruleArrayTypeRef returns [EObject current=null] : ( ( (lv_arrayTypeLiteral_0_0= LeftSquareBracket ) ) ( (lv_typeArgs_1_0= ruleTypeArgument ) ) otherlv_2= RightSquareBracket ) ;
    public final EObject ruleArrayTypeRef() throws RecognitionException {
        EObject current = null;

        Token lv_arrayTypeLiteral_0_0=null;
        Token otherlv_2=null;
        EObject lv_typeArgs_1_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4669:2: ( ( ( (lv_arrayTypeLiteral_0_0= LeftSquareBracket ) ) ( (lv_typeArgs_1_0= ruleTypeArgument ) ) otherlv_2= RightSquareBracket ) )
            // InternalTypesParser.g:4670:2: ( ( (lv_arrayTypeLiteral_0_0= LeftSquareBracket ) ) ( (lv_typeArgs_1_0= ruleTypeArgument ) ) otherlv_2= RightSquareBracket )
            {
            // InternalTypesParser.g:4670:2: ( ( (lv_arrayTypeLiteral_0_0= LeftSquareBracket ) ) ( (lv_typeArgs_1_0= ruleTypeArgument ) ) otherlv_2= RightSquareBracket )
            // InternalTypesParser.g:4671:3: ( (lv_arrayTypeLiteral_0_0= LeftSquareBracket ) ) ( (lv_typeArgs_1_0= ruleTypeArgument ) ) otherlv_2= RightSquareBracket
            {
            // InternalTypesParser.g:4671:3: ( (lv_arrayTypeLiteral_0_0= LeftSquareBracket ) )
            // InternalTypesParser.g:4672:4: (lv_arrayTypeLiteral_0_0= LeftSquareBracket )
            {
            // InternalTypesParser.g:4672:4: (lv_arrayTypeLiteral_0_0= LeftSquareBracket )
            // InternalTypesParser.g:4673:5: lv_arrayTypeLiteral_0_0= LeftSquareBracket
            {
            lv_arrayTypeLiteral_0_0=(Token)match(input,LeftSquareBracket,FOLLOW_85); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_arrayTypeLiteral_0_0, grammarAccess.getArrayTypeRefAccess().getArrayTypeLiteralLeftSquareBracketKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getArrayTypeRefRule());
              					}
              					setWithLastConsumed(current, "arrayTypeLiteral", true, "[");
              				
            }

            }


            }

            // InternalTypesParser.g:4685:3: ( (lv_typeArgs_1_0= ruleTypeArgument ) )
            // InternalTypesParser.g:4686:4: (lv_typeArgs_1_0= ruleTypeArgument )
            {
            // InternalTypesParser.g:4686:4: (lv_typeArgs_1_0= ruleTypeArgument )
            // InternalTypesParser.g:4687:5: lv_typeArgs_1_0= ruleTypeArgument
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayTypeRefAccess().getTypeArgsTypeArgumentParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_21);
            lv_typeArgs_1_0=ruleTypeArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArrayTypeRefRule());
              					}
              					add(
              						current,
              						"typeArgs",
              						lv_typeArgs_1_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getArrayTypeRefAccess().getRightSquareBracketKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayTypeRef"


    // $ANTLR start "entryRuleParameterizedTypeRefStructural"
    // InternalTypesParser.g:4712:1: entryRuleParameterizedTypeRefStructural returns [EObject current=null] : iv_ruleParameterizedTypeRefStructural= ruleParameterizedTypeRefStructural EOF ;
    public final EObject entryRuleParameterizedTypeRefStructural() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterizedTypeRefStructural = null;


        try {
            // InternalTypesParser.g:4712:71: (iv_ruleParameterizedTypeRefStructural= ruleParameterizedTypeRefStructural EOF )
            // InternalTypesParser.g:4713:2: iv_ruleParameterizedTypeRefStructural= ruleParameterizedTypeRefStructural EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameterizedTypeRefStructural=ruleParameterizedTypeRefStructural();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameterizedTypeRefStructural; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterizedTypeRefStructural"


    // $ANTLR start "ruleParameterizedTypeRefStructural"
    // InternalTypesParser.g:4719:1: ruleParameterizedTypeRefStructural returns [EObject current=null] : ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) this_TypeAndTypeArguments_1= ruleTypeAndTypeArguments[$current] (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? ) ;
    public final EObject ruleParameterizedTypeRefStructural() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_definedTypingStrategy_0_0 = null;

        EObject this_TypeAndTypeArguments_1 = null;

        EObject this_TStructMemberList_3 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4725:2: ( ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) this_TypeAndTypeArguments_1= ruleTypeAndTypeArguments[$current] (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? ) )
            // InternalTypesParser.g:4726:2: ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) this_TypeAndTypeArguments_1= ruleTypeAndTypeArguments[$current] (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? )
            {
            // InternalTypesParser.g:4726:2: ( ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) this_TypeAndTypeArguments_1= ruleTypeAndTypeArguments[$current] (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )? )
            // InternalTypesParser.g:4727:3: ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) ) this_TypeAndTypeArguments_1= ruleTypeAndTypeArguments[$current] (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )?
            {
            // InternalTypesParser.g:4727:3: ( (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator ) )
            // InternalTypesParser.g:4728:4: (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator )
            {
            // InternalTypesParser.g:4728:4: (lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator )
            // InternalTypesParser.g:4729:5: lv_definedTypingStrategy_0_0= ruleTypingStrategyUseSiteOperator
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_14);
            lv_definedTypingStrategy_0_0=ruleTypingStrategyUseSiteOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getParameterizedTypeRefStructuralRule());
              					}
              					set(
              						current,
              						"definedTypingStrategy",
              						lv_definedTypingStrategy_0_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyUseSiteOperator");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getParameterizedTypeRefStructuralRule());
              			}
              			newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeAndTypeArgumentsParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_71);
            this_TypeAndTypeArguments_1=ruleTypeAndTypeArguments(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TypeAndTypeArguments_1;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalTypesParser.g:4757:3: (otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current] )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==With) ) {
                int LA95_1 = input.LA(2);

                if ( (LA95_1==LeftCurlyBracket) ) {
                    alt95=1;
                }
            }
            switch (alt95) {
                case 1 :
                    // InternalTypesParser.g:4758:4: otherlv_2= With this_TStructMemberList_3= ruleTStructMemberList[$current]
                    {
                    otherlv_2=(Token)match(input,With,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getParameterizedTypeRefStructuralAccess().getWithKeyword_2_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getParameterizedTypeRefStructuralRule());
                      				}
                      				newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TStructMemberList_3=ruleTStructMemberList(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TStructMemberList_3;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterizedTypeRefStructural"


    // $ANTLR start "ruleTypeAndTypeArguments"
    // InternalTypesParser.g:4779:1: ruleTypeAndTypeArguments[EObject in_current] returns [EObject current=in_current] : ( ( ( ruleTypeReferenceName ) ) ( ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current] )? ) ;
    public final EObject ruleTypeAndTypeArguments(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        EObject this_TypeArguments_1 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4785:2: ( ( ( ( ruleTypeReferenceName ) ) ( ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current] )? ) )
            // InternalTypesParser.g:4786:2: ( ( ( ruleTypeReferenceName ) ) ( ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current] )? )
            {
            // InternalTypesParser.g:4786:2: ( ( ( ruleTypeReferenceName ) ) ( ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current] )? )
            // InternalTypesParser.g:4787:3: ( ( ruleTypeReferenceName ) ) ( ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current] )?
            {
            // InternalTypesParser.g:4787:3: ( ( ruleTypeReferenceName ) )
            // InternalTypesParser.g:4788:4: ( ruleTypeReferenceName )
            {
            // InternalTypesParser.g:4788:4: ( ruleTypeReferenceName )
            // InternalTypesParser.g:4789:5: ruleTypeReferenceName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getTypeAndTypeArgumentsRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTypeAndTypeArgumentsAccess().getDeclaredTypeTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_45);
            ruleTypeReferenceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:4803:3: ( ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current] )?
            int alt96=2;
            alt96 = dfa96.predict(input);
            switch (alt96) {
                case 1 :
                    // InternalTypesParser.g:4804:4: ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getTypeAndTypeArgumentsRule());
                      				}
                      				newCompositeNode(grammarAccess.getTypeAndTypeArgumentsAccess().getTypeArgumentsParserRuleCall_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeArguments_1=ruleTypeArguments(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TypeArguments_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeAndTypeArguments"


    // $ANTLR start "ruleTypeArguments"
    // InternalTypesParser.g:4822:1: ruleTypeArguments[EObject in_current] returns [EObject current=in_current] : (otherlv_0= LessThanSign ( (lv_typeArgs_1_0= ruleTypeArgument ) ) (otherlv_2= Comma ( (lv_typeArgs_3_0= ruleTypeArgument ) ) )* otherlv_4= GreaterThanSign ) ;
    public final EObject ruleTypeArguments(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_typeArgs_1_0 = null;

        EObject lv_typeArgs_3_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4828:2: ( (otherlv_0= LessThanSign ( (lv_typeArgs_1_0= ruleTypeArgument ) ) (otherlv_2= Comma ( (lv_typeArgs_3_0= ruleTypeArgument ) ) )* otherlv_4= GreaterThanSign ) )
            // InternalTypesParser.g:4829:2: (otherlv_0= LessThanSign ( (lv_typeArgs_1_0= ruleTypeArgument ) ) (otherlv_2= Comma ( (lv_typeArgs_3_0= ruleTypeArgument ) ) )* otherlv_4= GreaterThanSign )
            {
            // InternalTypesParser.g:4829:2: (otherlv_0= LessThanSign ( (lv_typeArgs_1_0= ruleTypeArgument ) ) (otherlv_2= Comma ( (lv_typeArgs_3_0= ruleTypeArgument ) ) )* otherlv_4= GreaterThanSign )
            // InternalTypesParser.g:4830:3: otherlv_0= LessThanSign ( (lv_typeArgs_1_0= ruleTypeArgument ) ) (otherlv_2= Comma ( (lv_typeArgs_3_0= ruleTypeArgument ) ) )* otherlv_4= GreaterThanSign
            {
            otherlv_0=(Token)match(input,LessThanSign,FOLLOW_85); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getTypeArgumentsAccess().getLessThanSignKeyword_0());
              		
            }
            // InternalTypesParser.g:4834:3: ( (lv_typeArgs_1_0= ruleTypeArgument ) )
            // InternalTypesParser.g:4835:4: (lv_typeArgs_1_0= ruleTypeArgument )
            {
            // InternalTypesParser.g:4835:4: (lv_typeArgs_1_0= ruleTypeArgument )
            // InternalTypesParser.g:4836:5: lv_typeArgs_1_0= ruleTypeArgument
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_47);
            lv_typeArgs_1_0=ruleTypeArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTypeArgumentsRule());
              					}
              					add(
              						current,
              						"typeArgs",
              						lv_typeArgs_1_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:4853:3: (otherlv_2= Comma ( (lv_typeArgs_3_0= ruleTypeArgument ) ) )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==Comma) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // InternalTypesParser.g:4854:4: otherlv_2= Comma ( (lv_typeArgs_3_0= ruleTypeArgument ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_85); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getTypeArgumentsAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalTypesParser.g:4858:4: ( (lv_typeArgs_3_0= ruleTypeArgument ) )
            	    // InternalTypesParser.g:4859:5: (lv_typeArgs_3_0= ruleTypeArgument )
            	    {
            	    // InternalTypesParser.g:4859:5: (lv_typeArgs_3_0= ruleTypeArgument )
            	    // InternalTypesParser.g:4860:6: lv_typeArgs_3_0= ruleTypeArgument
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
            	    lv_typeArgs_3_0=ruleTypeArgument();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTypeArgumentsRule());
            	      						}
            	      						add(
            	      							current,
            	      							"typeArgs",
            	      							lv_typeArgs_3_0,
            	      							"org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop97;
                }
            } while (true);

            otherlv_4=(Token)match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTypeArgumentsAccess().getGreaterThanSignKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeArguments"


    // $ANTLR start "ruleTStructMemberList"
    // InternalTypesParser.g:4887:1: ruleTStructMemberList[EObject in_current] returns [EObject current=in_current] : (otherlv_0= LeftCurlyBracket ( ( (lv_astStructuralMembers_1_0= ruleTStructMember ) ) (otherlv_2= Semicolon | otherlv_3= Comma )? )* otherlv_4= RightCurlyBracket ) ;
    public final EObject ruleTStructMemberList(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_astStructuralMembers_1_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4893:2: ( (otherlv_0= LeftCurlyBracket ( ( (lv_astStructuralMembers_1_0= ruleTStructMember ) ) (otherlv_2= Semicolon | otherlv_3= Comma )? )* otherlv_4= RightCurlyBracket ) )
            // InternalTypesParser.g:4894:2: (otherlv_0= LeftCurlyBracket ( ( (lv_astStructuralMembers_1_0= ruleTStructMember ) ) (otherlv_2= Semicolon | otherlv_3= Comma )? )* otherlv_4= RightCurlyBracket )
            {
            // InternalTypesParser.g:4894:2: (otherlv_0= LeftCurlyBracket ( ( (lv_astStructuralMembers_1_0= ruleTStructMember ) ) (otherlv_2= Semicolon | otherlv_3= Comma )? )* otherlv_4= RightCurlyBracket )
            // InternalTypesParser.g:4895:3: otherlv_0= LeftCurlyBracket ( ( (lv_astStructuralMembers_1_0= ruleTStructMember ) ) (otherlv_2= Semicolon | otherlv_3= Comma )? )* otherlv_4= RightCurlyBracket
            {
            otherlv_0=(Token)match(input,LeftCurlyBracket,FOLLOW_86); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getTStructMemberListAccess().getLeftCurlyBracketKeyword_0());
              		
            }
            // InternalTypesParser.g:4899:3: ( ( (lv_astStructuralMembers_1_0= ruleTStructMember ) ) (otherlv_2= Semicolon | otherlv_3= Comma )? )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( ((LA99_0>=Intersection && LA99_0<=Constructor)||(LA99_0>=Implements && LA99_0<=Interface)||LA99_0==Protected||(LA99_0>=Abstract && LA99_0<=Finally)||(LA99_0>=Private && LA99_0<=Import)||(LA99_0>=Public && LA99_0<=False)||(LA99_0>=Super && LA99_0<=With)||(LA99_0>=For && LA99_0<=Var)||(LA99_0>=As && LA99_0<=Of)||LA99_0==LessThanSign||LA99_0==RULE_IDENTIFIER) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // InternalTypesParser.g:4900:4: ( (lv_astStructuralMembers_1_0= ruleTStructMember ) ) (otherlv_2= Semicolon | otherlv_3= Comma )?
            	    {
            	    // InternalTypesParser.g:4900:4: ( (lv_astStructuralMembers_1_0= ruleTStructMember ) )
            	    // InternalTypesParser.g:4901:5: (lv_astStructuralMembers_1_0= ruleTStructMember )
            	    {
            	    // InternalTypesParser.g:4901:5: (lv_astStructuralMembers_1_0= ruleTStructMember )
            	    // InternalTypesParser.g:4902:6: lv_astStructuralMembers_1_0= ruleTStructMember
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersTStructMemberParserRuleCall_1_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_87);
            	    lv_astStructuralMembers_1_0=ruleTStructMember();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTStructMemberListRule());
            	      						}
            	      						add(
            	      							current,
            	      							"astStructuralMembers",
            	      							lv_astStructuralMembers_1_0,
            	      							"org.eclipse.n4js.ts.TypeExpressions.TStructMember");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }

            	    // InternalTypesParser.g:4919:4: (otherlv_2= Semicolon | otherlv_3= Comma )?
            	    int alt98=3;
            	    int LA98_0 = input.LA(1);

            	    if ( (LA98_0==Semicolon) ) {
            	        alt98=1;
            	    }
            	    else if ( (LA98_0==Comma) ) {
            	        alt98=2;
            	    }
            	    switch (alt98) {
            	        case 1 :
            	            // InternalTypesParser.g:4920:5: otherlv_2= Semicolon
            	            {
            	            otherlv_2=(Token)match(input,Semicolon,FOLLOW_86); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(otherlv_2, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalTypesParser.g:4925:5: otherlv_3= Comma
            	            {
            	            otherlv_3=(Token)match(input,Comma,FOLLOW_86); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(otherlv_3, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1());
            	              				
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop99;
                }
            } while (true);

            otherlv_4=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTStructMemberListAccess().getRightCurlyBracketKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTStructMemberList"


    // $ANTLR start "entryRuleTStructMember"
    // InternalTypesParser.g:4939:1: entryRuleTStructMember returns [EObject current=null] : iv_ruleTStructMember= ruleTStructMember EOF ;
    public final EObject entryRuleTStructMember() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTStructMember = null;


        try {
            // InternalTypesParser.g:4939:54: (iv_ruleTStructMember= ruleTStructMember EOF )
            // InternalTypesParser.g:4940:2: iv_ruleTStructMember= ruleTStructMember EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTStructMemberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTStructMember=ruleTStructMember();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTStructMember; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTStructMember"


    // $ANTLR start "ruleTStructMember"
    // InternalTypesParser.g:4946:1: ruleTStructMember returns [EObject current=null] : ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter ) | ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter ) | ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod ) | this_TStructField_3= ruleTStructField ) ;
    public final EObject ruleTStructMember() throws RecognitionException {
        EObject current = null;

        EObject this_TStructGetter_0 = null;

        EObject this_TStructSetter_1 = null;

        EObject this_TStructMethod_2 = null;

        EObject this_TStructField_3 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:4952:2: ( ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter ) | ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter ) | ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod ) | this_TStructField_3= ruleTStructField ) )
            // InternalTypesParser.g:4953:2: ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter ) | ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter ) | ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod ) | this_TStructField_3= ruleTStructField )
            {
            // InternalTypesParser.g:4953:2: ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter ) | ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter ) | ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod ) | this_TStructField_3= ruleTStructField )
            int alt100=4;
            alt100 = dfa100.predict(input);
            switch (alt100) {
                case 1 :
                    // InternalTypesParser.g:4954:3: ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter )
                    {
                    // InternalTypesParser.g:4954:3: ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter )
                    // InternalTypesParser.g:4955:4: ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructGetterParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TStructGetter_0=ruleTStructGetter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TStructGetter_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:4976:3: ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter )
                    {
                    // InternalTypesParser.g:4976:3: ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter )
                    // InternalTypesParser.g:4977:4: ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructSetterParserRuleCall_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TStructSetter_1=ruleTStructSetter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TStructSetter_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:4998:3: ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod )
                    {
                    // InternalTypesParser.g:4998:3: ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod )
                    // InternalTypesParser.g:4999:4: ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructMethodParserRuleCall_2());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_TStructMethod_2=ruleTStructMethod();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_TStructMethod_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:5023:3: this_TStructField_3= ruleTStructField
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructFieldParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TStructField_3=ruleTStructField();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TStructField_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTStructMember"


    // $ANTLR start "entryRuleTStructMethod"
    // InternalTypesParser.g:5035:1: entryRuleTStructMethod returns [EObject current=null] : iv_ruleTStructMethod= ruleTStructMethod EOF ;
    public final EObject entryRuleTStructMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTStructMethod = null;


        try {
            // InternalTypesParser.g:5035:54: (iv_ruleTStructMethod= ruleTStructMethod EOF )
            // InternalTypesParser.g:5036:2: iv_ruleTStructMethod= ruleTStructMethod EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTStructMethodRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTStructMethod=ruleTStructMethod();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTStructMethod; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTStructMethod"


    // $ANTLR start "ruleTStructMethod"
    // InternalTypesParser.g:5042:1: ruleTStructMethod returns [EObject current=null] : ( ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=> ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis ) ) this_TAnonymousFormalParameterList_4= ruleTAnonymousFormalParameterList[$current] otherlv_5= RightParenthesis (this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )? ) ;
    public final EObject ruleTStructMethod() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_TypeVariables_1 = null;

        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject this_TAnonymousFormalParameterList_4 = null;

        EObject this_ColonSepReturnTypeRef_6 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5048:2: ( ( ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=> ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis ) ) this_TAnonymousFormalParameterList_4= ruleTAnonymousFormalParameterList[$current] otherlv_5= RightParenthesis (this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )? ) )
            // InternalTypesParser.g:5049:2: ( ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=> ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis ) ) this_TAnonymousFormalParameterList_4= ruleTAnonymousFormalParameterList[$current] otherlv_5= RightParenthesis (this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )? )
            {
            // InternalTypesParser.g:5049:2: ( ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=> ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis ) ) this_TAnonymousFormalParameterList_4= ruleTAnonymousFormalParameterList[$current] otherlv_5= RightParenthesis (this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )? )
            // InternalTypesParser.g:5050:3: ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=> ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis ) ) this_TAnonymousFormalParameterList_4= ruleTAnonymousFormalParameterList[$current] otherlv_5= RightParenthesis (this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )?
            {
            // InternalTypesParser.g:5050:3: ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=> ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis ) )
            // InternalTypesParser.g:5051:4: ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=> ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis )
            {
            // InternalTypesParser.g:5065:4: ( () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis )
            // InternalTypesParser.g:5066:5: () (this_TypeVariables_1= ruleTypeVariables[$current] )? ( (lv_name_2_0= ruleIdentifierName ) ) otherlv_3= LeftParenthesis
            {
            // InternalTypesParser.g:5066:5: ()
            // InternalTypesParser.g:5067:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getTStructMethodAccess().getTStructMethodAction_0_0_0(),
              							current);
              					
            }

            }

            // InternalTypesParser.g:5073:5: (this_TypeVariables_1= ruleTypeVariables[$current] )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==LessThanSign) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // InternalTypesParser.g:5074:6: this_TypeVariables_1= ruleTypeVariables[$current]
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTStructMethodRule());
                      						}
                      						newCompositeNode(grammarAccess.getTStructMethodAccess().getTypeVariablesParserRuleCall_0_0_1());
                      					
                    }
                    pushFollow(FOLLOW_23);
                    this_TypeVariables_1=ruleTypeVariables(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						current = this_TypeVariables_1;
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;

            }

            // InternalTypesParser.g:5086:5: ( (lv_name_2_0= ruleIdentifierName ) )
            // InternalTypesParser.g:5087:6: (lv_name_2_0= ruleIdentifierName )
            {
            // InternalTypesParser.g:5087:6: (lv_name_2_0= ruleIdentifierName )
            // InternalTypesParser.g:5088:7: lv_name_2_0= ruleIdentifierName
            {
            if ( state.backtracking==0 ) {

              							newCompositeNode(grammarAccess.getTStructMethodAccess().getNameIdentifierNameParserRuleCall_0_0_2_0());
              						
            }
            pushFollow(FOLLOW_53);
            lv_name_2_0=ruleIdentifierName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElementForParent(grammarAccess.getTStructMethodRule());
              							}
              							set(
              								current,
              								"name",
              								lv_name_2_0,
              								"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
              							afterParserOrEnumRuleCall();
              						
            }

            }


            }

            otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_76); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_3, grammarAccess.getTStructMethodAccess().getLeftParenthesisKeyword_0_0_3());
              				
            }

            }


            }

            if ( state.backtracking==0 ) {

              			if (current==null) {
              				current = createModelElement(grammarAccess.getTStructMethodRule());
              			}
              			newCompositeNode(grammarAccess.getTStructMethodAccess().getTAnonymousFormalParameterListParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_60);
            this_TAnonymousFormalParameterList_4=ruleTAnonymousFormalParameterList(current);

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_TAnonymousFormalParameterList_4;
              			afterParserOrEnumRuleCall();
              		
            }
            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_88); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getTStructMethodAccess().getRightParenthesisKeyword_2());
              		
            }
            // InternalTypesParser.g:5126:3: (this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current] )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==Colon) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // InternalTypesParser.g:5127:4: this_ColonSepReturnTypeRef_6= ruleColonSepReturnTypeRef[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getTStructMethodRule());
                      				}
                      				newCompositeNode(grammarAccess.getTStructMethodAccess().getColonSepReturnTypeRefParserRuleCall_3());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_ColonSepReturnTypeRef_6=ruleColonSepReturnTypeRef(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ColonSepReturnTypeRef_6;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTStructMethod"


    // $ANTLR start "ruleTypeVariables"
    // InternalTypesParser.g:5144:1: ruleTypeVariables[EObject in_current] returns [EObject current=in_current] : (otherlv_0= LessThanSign ( (lv_typeVars_1_0= ruleTypeVariable ) ) (otherlv_2= Comma ( (lv_typeVars_3_0= ruleTypeVariable ) ) )* otherlv_4= GreaterThanSign ) ;
    public final EObject ruleTypeVariables(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_typeVars_1_0 = null;

        EObject lv_typeVars_3_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5150:2: ( (otherlv_0= LessThanSign ( (lv_typeVars_1_0= ruleTypeVariable ) ) (otherlv_2= Comma ( (lv_typeVars_3_0= ruleTypeVariable ) ) )* otherlv_4= GreaterThanSign ) )
            // InternalTypesParser.g:5151:2: (otherlv_0= LessThanSign ( (lv_typeVars_1_0= ruleTypeVariable ) ) (otherlv_2= Comma ( (lv_typeVars_3_0= ruleTypeVariable ) ) )* otherlv_4= GreaterThanSign )
            {
            // InternalTypesParser.g:5151:2: (otherlv_0= LessThanSign ( (lv_typeVars_1_0= ruleTypeVariable ) ) (otherlv_2= Comma ( (lv_typeVars_3_0= ruleTypeVariable ) ) )* otherlv_4= GreaterThanSign )
            // InternalTypesParser.g:5152:3: otherlv_0= LessThanSign ( (lv_typeVars_1_0= ruleTypeVariable ) ) (otherlv_2= Comma ( (lv_typeVars_3_0= ruleTypeVariable ) ) )* otherlv_4= GreaterThanSign
            {
            otherlv_0=(Token)match(input,LessThanSign,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getTypeVariablesAccess().getLessThanSignKeyword_0());
              		
            }
            // InternalTypesParser.g:5156:3: ( (lv_typeVars_1_0= ruleTypeVariable ) )
            // InternalTypesParser.g:5157:4: (lv_typeVars_1_0= ruleTypeVariable )
            {
            // InternalTypesParser.g:5157:4: (lv_typeVars_1_0= ruleTypeVariable )
            // InternalTypesParser.g:5158:5: lv_typeVars_1_0= ruleTypeVariable
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_47);
            lv_typeVars_1_0=ruleTypeVariable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTypeVariablesRule());
              					}
              					add(
              						current,
              						"typeVars",
              						lv_typeVars_1_0,
              						"org.eclipse.n4js.ts.Types.TypeVariable");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:5175:3: (otherlv_2= Comma ( (lv_typeVars_3_0= ruleTypeVariable ) ) )*
            loop103:
            do {
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( (LA103_0==Comma) ) {
                    alt103=1;
                }


                switch (alt103) {
            	case 1 :
            	    // InternalTypesParser.g:5176:4: otherlv_2= Comma ( (lv_typeVars_3_0= ruleTypeVariable ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getTypeVariablesAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalTypesParser.g:5180:4: ( (lv_typeVars_3_0= ruleTypeVariable ) )
            	    // InternalTypesParser.g:5181:5: (lv_typeVars_3_0= ruleTypeVariable )
            	    {
            	    // InternalTypesParser.g:5181:5: (lv_typeVars_3_0= ruleTypeVariable )
            	    // InternalTypesParser.g:5182:6: lv_typeVars_3_0= ruleTypeVariable
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
            	    lv_typeVars_3_0=ruleTypeVariable();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTypeVariablesRule());
            	      						}
            	      						add(
            	      							current,
            	      							"typeVars",
            	      							lv_typeVars_3_0,
            	      							"org.eclipse.n4js.ts.Types.TypeVariable");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop103;
                }
            } while (true);

            otherlv_4=(Token)match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTypeVariablesAccess().getGreaterThanSignKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeVariables"


    // $ANTLR start "ruleColonSepDeclaredTypeRef"
    // InternalTypesParser.g:5209:1: ruleColonSepDeclaredTypeRef[EObject in_current] returns [EObject current=in_current] : (otherlv_0= Colon ( (lv_declaredTypeRef_1_0= ruleTypeRef ) ) ) ;
    public final EObject ruleColonSepDeclaredTypeRef(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        EObject lv_declaredTypeRef_1_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5215:2: ( (otherlv_0= Colon ( (lv_declaredTypeRef_1_0= ruleTypeRef ) ) ) )
            // InternalTypesParser.g:5216:2: (otherlv_0= Colon ( (lv_declaredTypeRef_1_0= ruleTypeRef ) ) )
            {
            // InternalTypesParser.g:5216:2: (otherlv_0= Colon ( (lv_declaredTypeRef_1_0= ruleTypeRef ) ) )
            // InternalTypesParser.g:5217:3: otherlv_0= Colon ( (lv_declaredTypeRef_1_0= ruleTypeRef ) )
            {
            otherlv_0=(Token)match(input,Colon,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getColonSepDeclaredTypeRefAccess().getColonKeyword_0());
              		
            }
            // InternalTypesParser.g:5221:3: ( (lv_declaredTypeRef_1_0= ruleTypeRef ) )
            // InternalTypesParser.g:5222:4: (lv_declaredTypeRef_1_0= ruleTypeRef )
            {
            // InternalTypesParser.g:5222:4: (lv_declaredTypeRef_1_0= ruleTypeRef )
            // InternalTypesParser.g:5223:5: lv_declaredTypeRef_1_0= ruleTypeRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefTypeRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_declaredTypeRef_1_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getColonSepDeclaredTypeRefRule());
              					}
              					set(
              						current,
              						"declaredTypeRef",
              						lv_declaredTypeRef_1_0,
              						"org.eclipse.n4js.ts.Types.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColonSepDeclaredTypeRef"


    // $ANTLR start "ruleColonSepTypeRef"
    // InternalTypesParser.g:5245:1: ruleColonSepTypeRef[EObject in_current] returns [EObject current=in_current] : (otherlv_0= Colon ( (lv_typeRef_1_0= ruleTypeRef ) ) ) ;
    public final EObject ruleColonSepTypeRef(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        EObject lv_typeRef_1_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5251:2: ( (otherlv_0= Colon ( (lv_typeRef_1_0= ruleTypeRef ) ) ) )
            // InternalTypesParser.g:5252:2: (otherlv_0= Colon ( (lv_typeRef_1_0= ruleTypeRef ) ) )
            {
            // InternalTypesParser.g:5252:2: (otherlv_0= Colon ( (lv_typeRef_1_0= ruleTypeRef ) ) )
            // InternalTypesParser.g:5253:3: otherlv_0= Colon ( (lv_typeRef_1_0= ruleTypeRef ) )
            {
            otherlv_0=(Token)match(input,Colon,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getColonSepTypeRefAccess().getColonKeyword_0());
              		
            }
            // InternalTypesParser.g:5257:3: ( (lv_typeRef_1_0= ruleTypeRef ) )
            // InternalTypesParser.g:5258:4: (lv_typeRef_1_0= ruleTypeRef )
            {
            // InternalTypesParser.g:5258:4: (lv_typeRef_1_0= ruleTypeRef )
            // InternalTypesParser.g:5259:5: lv_typeRef_1_0= ruleTypeRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getColonSepTypeRefAccess().getTypeRefTypeRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_typeRef_1_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getColonSepTypeRefRule());
              					}
              					set(
              						current,
              						"typeRef",
              						lv_typeRef_1_0,
              						"org.eclipse.n4js.ts.Types.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColonSepTypeRef"


    // $ANTLR start "ruleColonSepReturnTypeRef"
    // InternalTypesParser.g:5281:1: ruleColonSepReturnTypeRef[EObject in_current] returns [EObject current=in_current] : (otherlv_0= Colon ( (lv_returnTypeRef_1_0= ruleTypeRef ) ) ) ;
    public final EObject ruleColonSepReturnTypeRef(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        EObject lv_returnTypeRef_1_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5287:2: ( (otherlv_0= Colon ( (lv_returnTypeRef_1_0= ruleTypeRef ) ) ) )
            // InternalTypesParser.g:5288:2: (otherlv_0= Colon ( (lv_returnTypeRef_1_0= ruleTypeRef ) ) )
            {
            // InternalTypesParser.g:5288:2: (otherlv_0= Colon ( (lv_returnTypeRef_1_0= ruleTypeRef ) ) )
            // InternalTypesParser.g:5289:3: otherlv_0= Colon ( (lv_returnTypeRef_1_0= ruleTypeRef ) )
            {
            otherlv_0=(Token)match(input,Colon,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getColonSepReturnTypeRefAccess().getColonKeyword_0());
              		
            }
            // InternalTypesParser.g:5293:3: ( (lv_returnTypeRef_1_0= ruleTypeRef ) )
            // InternalTypesParser.g:5294:4: (lv_returnTypeRef_1_0= ruleTypeRef )
            {
            // InternalTypesParser.g:5294:4: (lv_returnTypeRef_1_0= ruleTypeRef )
            // InternalTypesParser.g:5295:5: lv_returnTypeRef_1_0= ruleTypeRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefTypeRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_returnTypeRef_1_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getColonSepReturnTypeRefRule());
              					}
              					set(
              						current,
              						"returnTypeRef",
              						lv_returnTypeRef_1_0,
              						"org.eclipse.n4js.ts.Types.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColonSepReturnTypeRef"


    // $ANTLR start "entryRuleTStructField"
    // InternalTypesParser.g:5316:1: entryRuleTStructField returns [EObject current=null] : iv_ruleTStructField= ruleTStructField EOF ;
    public final EObject entryRuleTStructField() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTStructField = null;


        try {
            // InternalTypesParser.g:5316:53: (iv_ruleTStructField= ruleTStructField EOF )
            // InternalTypesParser.g:5317:2: iv_ruleTStructField= ruleTStructField EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTStructFieldRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTStructField=ruleTStructField();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTStructField; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTStructField"


    // $ANTLR start "ruleTStructField"
    // InternalTypesParser.g:5323:1: ruleTStructField returns [EObject current=null] : ( ( (lv_name_0_0= ruleIdentifierName ) ) ( (lv_optional_1_0= QuestionMark ) )? (this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )? ) ;
    public final EObject ruleTStructField() throws RecognitionException {
        EObject current = null;

        Token lv_optional_1_0=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject this_ColonSepTypeRef_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5329:2: ( ( ( (lv_name_0_0= ruleIdentifierName ) ) ( (lv_optional_1_0= QuestionMark ) )? (this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )? ) )
            // InternalTypesParser.g:5330:2: ( ( (lv_name_0_0= ruleIdentifierName ) ) ( (lv_optional_1_0= QuestionMark ) )? (this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )? )
            {
            // InternalTypesParser.g:5330:2: ( ( (lv_name_0_0= ruleIdentifierName ) ) ( (lv_optional_1_0= QuestionMark ) )? (this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )? )
            // InternalTypesParser.g:5331:3: ( (lv_name_0_0= ruleIdentifierName ) ) ( (lv_optional_1_0= QuestionMark ) )? (this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )?
            {
            // InternalTypesParser.g:5331:3: ( (lv_name_0_0= ruleIdentifierName ) )
            // InternalTypesParser.g:5332:4: (lv_name_0_0= ruleIdentifierName )
            {
            // InternalTypesParser.g:5332:4: (lv_name_0_0= ruleIdentifierName )
            // InternalTypesParser.g:5333:5: lv_name_0_0= ruleIdentifierName
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTStructFieldAccess().getNameIdentifierNameParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_89);
            lv_name_0_0=ruleIdentifierName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTStructFieldRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalTypesParser.g:5350:3: ( (lv_optional_1_0= QuestionMark ) )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==QuestionMark) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalTypesParser.g:5351:4: (lv_optional_1_0= QuestionMark )
                    {
                    // InternalTypesParser.g:5351:4: (lv_optional_1_0= QuestionMark )
                    // InternalTypesParser.g:5352:5: lv_optional_1_0= QuestionMark
                    {
                    lv_optional_1_0=(Token)match(input,QuestionMark,FOLLOW_89); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_optional_1_0, grammarAccess.getTStructFieldAccess().getOptionalQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTStructFieldRule());
                      					}
                      					setWithLastConsumed(current, "optional", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalTypesParser.g:5364:3: (this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==Colon) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // InternalTypesParser.g:5365:4: this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getTStructFieldRule());
                      				}
                      				newCompositeNode(grammarAccess.getTStructFieldAccess().getColonSepTypeRefParserRuleCall_2());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_ColonSepTypeRef_2=ruleColonSepTypeRef(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ColonSepTypeRef_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTStructField"


    // $ANTLR start "entryRuleTStructGetter"
    // InternalTypesParser.g:5381:1: entryRuleTStructGetter returns [EObject current=null] : iv_ruleTStructGetter= ruleTStructGetter EOF ;
    public final EObject entryRuleTStructGetter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTStructGetter = null;


        try {
            // InternalTypesParser.g:5381:54: (iv_ruleTStructGetter= ruleTStructGetter EOF )
            // InternalTypesParser.g:5382:2: iv_ruleTStructGetter= ruleTStructGetter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTStructGetterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTStructGetter=ruleTStructGetter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTStructGetter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTStructGetter"


    // $ANTLR start "ruleTStructGetter"
    // InternalTypesParser.g:5388:1: ruleTStructGetter returns [EObject current=null] : ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis otherlv_5= RightParenthesis (this_ColonSepDeclaredTypeRef_6= ruleColonSepDeclaredTypeRef[$current] )? ) ;
    public final EObject ruleTStructGetter() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_optional_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject this_ColonSepDeclaredTypeRef_6 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5394:2: ( ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis otherlv_5= RightParenthesis (this_ColonSepDeclaredTypeRef_6= ruleColonSepDeclaredTypeRef[$current] )? ) )
            // InternalTypesParser.g:5395:2: ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis otherlv_5= RightParenthesis (this_ColonSepDeclaredTypeRef_6= ruleColonSepDeclaredTypeRef[$current] )? )
            {
            // InternalTypesParser.g:5395:2: ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis otherlv_5= RightParenthesis (this_ColonSepDeclaredTypeRef_6= ruleColonSepDeclaredTypeRef[$current] )? )
            // InternalTypesParser.g:5396:3: ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis otherlv_5= RightParenthesis (this_ColonSepDeclaredTypeRef_6= ruleColonSepDeclaredTypeRef[$current] )?
            {
            // InternalTypesParser.g:5396:3: ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) ) )
            // InternalTypesParser.g:5397:4: ( ( () Get ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) )
            {
            // InternalTypesParser.g:5408:4: ( () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) ) )
            // InternalTypesParser.g:5409:5: () otherlv_1= Get ( (lv_name_2_0= ruleIdentifierName ) )
            {
            // InternalTypesParser.g:5409:5: ()
            // InternalTypesParser.g:5410:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getTStructGetterAccess().getTStructGetterAction_0_0_0(),
              							current);
              					
            }

            }

            otherlv_1=(Token)match(input,Get,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getTStructGetterAccess().getGetKeyword_0_0_1());
              				
            }
            // InternalTypesParser.g:5420:5: ( (lv_name_2_0= ruleIdentifierName ) )
            // InternalTypesParser.g:5421:6: (lv_name_2_0= ruleIdentifierName )
            {
            // InternalTypesParser.g:5421:6: (lv_name_2_0= ruleIdentifierName )
            // InternalTypesParser.g:5422:7: lv_name_2_0= ruleIdentifierName
            {
            if ( state.backtracking==0 ) {

              							newCompositeNode(grammarAccess.getTStructGetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0());
              						
            }
            pushFollow(FOLLOW_59);
            lv_name_2_0=ruleIdentifierName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElementForParent(grammarAccess.getTStructGetterRule());
              							}
              							set(
              								current,
              								"name",
              								lv_name_2_0,
              								"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
              							afterParserOrEnumRuleCall();
              						
            }

            }


            }


            }


            }

            // InternalTypesParser.g:5441:3: ( (lv_optional_3_0= QuestionMark ) )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==QuestionMark) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // InternalTypesParser.g:5442:4: (lv_optional_3_0= QuestionMark )
                    {
                    // InternalTypesParser.g:5442:4: (lv_optional_3_0= QuestionMark )
                    // InternalTypesParser.g:5443:5: lv_optional_3_0= QuestionMark
                    {
                    lv_optional_3_0=(Token)match(input,QuestionMark,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_optional_3_0, grammarAccess.getTStructGetterAccess().getOptionalQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTStructGetterRule());
                      					}
                      					setWithLastConsumed(current, "optional", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,LeftParenthesis,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTStructGetterAccess().getLeftParenthesisKeyword_2());
              		
            }
            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_88); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getTStructGetterAccess().getRightParenthesisKeyword_3());
              		
            }
            // InternalTypesParser.g:5463:3: (this_ColonSepDeclaredTypeRef_6= ruleColonSepDeclaredTypeRef[$current] )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==Colon) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // InternalTypesParser.g:5464:4: this_ColonSepDeclaredTypeRef_6= ruleColonSepDeclaredTypeRef[$current]
                    {
                    if ( state.backtracking==0 ) {

                      				if (current==null) {
                      					current = createModelElement(grammarAccess.getTStructGetterRule());
                      				}
                      				newCompositeNode(grammarAccess.getTStructGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_ColonSepDeclaredTypeRef_6=ruleColonSepDeclaredTypeRef(current);

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ColonSepDeclaredTypeRef_6;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTStructGetter"


    // $ANTLR start "entryRuleTStructSetter"
    // InternalTypesParser.g:5480:1: entryRuleTStructSetter returns [EObject current=null] : iv_ruleTStructSetter= ruleTStructSetter EOF ;
    public final EObject entryRuleTStructSetter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTStructSetter = null;


        try {
            // InternalTypesParser.g:5480:54: (iv_ruleTStructSetter= ruleTStructSetter EOF )
            // InternalTypesParser.g:5481:2: iv_ruleTStructSetter= ruleTStructSetter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTStructSetterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTStructSetter=ruleTStructSetter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTStructSetter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTStructSetter"


    // $ANTLR start "ruleTStructSetter"
    // InternalTypesParser.g:5487:1: ruleTStructSetter returns [EObject current=null] : ( ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis ( (lv_fpar_5_0= ruleTAnonymousFormalParameter ) ) otherlv_6= RightParenthesis ) ;
    public final EObject ruleTStructSetter() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_optional_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_fpar_5_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5493:2: ( ( ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis ( (lv_fpar_5_0= ruleTAnonymousFormalParameter ) ) otherlv_6= RightParenthesis ) )
            // InternalTypesParser.g:5494:2: ( ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis ( (lv_fpar_5_0= ruleTAnonymousFormalParameter ) ) otherlv_6= RightParenthesis )
            {
            // InternalTypesParser.g:5494:2: ( ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis ( (lv_fpar_5_0= ruleTAnonymousFormalParameter ) ) otherlv_6= RightParenthesis )
            // InternalTypesParser.g:5495:3: ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) ) ) ( (lv_optional_3_0= QuestionMark ) )? otherlv_4= LeftParenthesis ( (lv_fpar_5_0= ruleTAnonymousFormalParameter ) ) otherlv_6= RightParenthesis
            {
            // InternalTypesParser.g:5495:3: ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) ) )
            // InternalTypesParser.g:5496:4: ( ( () Set ( ( ruleIdentifierName ) ) ) )=> ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) )
            {
            // InternalTypesParser.g:5507:4: ( () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) ) )
            // InternalTypesParser.g:5508:5: () otherlv_1= Set ( (lv_name_2_0= ruleIdentifierName ) )
            {
            // InternalTypesParser.g:5508:5: ()
            // InternalTypesParser.g:5509:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getTStructSetterAccess().getTStructSetterAction_0_0_0(),
              							current);
              					
            }

            }

            otherlv_1=(Token)match(input,Set,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getTStructSetterAccess().getSetKeyword_0_0_1());
              				
            }
            // InternalTypesParser.g:5519:5: ( (lv_name_2_0= ruleIdentifierName ) )
            // InternalTypesParser.g:5520:6: (lv_name_2_0= ruleIdentifierName )
            {
            // InternalTypesParser.g:5520:6: (lv_name_2_0= ruleIdentifierName )
            // InternalTypesParser.g:5521:7: lv_name_2_0= ruleIdentifierName
            {
            if ( state.backtracking==0 ) {

              							newCompositeNode(grammarAccess.getTStructSetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0());
              						
            }
            pushFollow(FOLLOW_59);
            lv_name_2_0=ruleIdentifierName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElementForParent(grammarAccess.getTStructSetterRule());
              							}
              							set(
              								current,
              								"name",
              								lv_name_2_0,
              								"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
              							afterParserOrEnumRuleCall();
              						
            }

            }


            }


            }


            }

            // InternalTypesParser.g:5540:3: ( (lv_optional_3_0= QuestionMark ) )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==QuestionMark) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalTypesParser.g:5541:4: (lv_optional_3_0= QuestionMark )
                    {
                    // InternalTypesParser.g:5541:4: (lv_optional_3_0= QuestionMark )
                    // InternalTypesParser.g:5542:5: lv_optional_3_0= QuestionMark
                    {
                    lv_optional_3_0=(Token)match(input,QuestionMark,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_optional_3_0, grammarAccess.getTStructSetterAccess().getOptionalQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTStructSetterRule());
                      					}
                      					setWithLastConsumed(current, "optional", true, "?");
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,LeftParenthesis,FOLLOW_81); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getTStructSetterAccess().getLeftParenthesisKeyword_2());
              		
            }
            // InternalTypesParser.g:5558:3: ( (lv_fpar_5_0= ruleTAnonymousFormalParameter ) )
            // InternalTypesParser.g:5559:4: (lv_fpar_5_0= ruleTAnonymousFormalParameter )
            {
            // InternalTypesParser.g:5559:4: (lv_fpar_5_0= ruleTAnonymousFormalParameter )
            // InternalTypesParser.g:5560:5: lv_fpar_5_0= ruleTAnonymousFormalParameter
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTStructSetterAccess().getFparTAnonymousFormalParameterParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_60);
            lv_fpar_5_0=ruleTAnonymousFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTStructSetterRule());
              					}
              					set(
              						current,
              						"fpar",
              						lv_fpar_5_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameter");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getTStructSetterAccess().getRightParenthesisKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTStructSetter"


    // $ANTLR start "entryRuleTypingStrategyUseSiteOperator"
    // InternalTypesParser.g:5585:1: entryRuleTypingStrategyUseSiteOperator returns [String current=null] : iv_ruleTypingStrategyUseSiteOperator= ruleTypingStrategyUseSiteOperator EOF ;
    public final String entryRuleTypingStrategyUseSiteOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypingStrategyUseSiteOperator = null;


        try {
            // InternalTypesParser.g:5585:69: (iv_ruleTypingStrategyUseSiteOperator= ruleTypingStrategyUseSiteOperator EOF )
            // InternalTypesParser.g:5586:2: iv_ruleTypingStrategyUseSiteOperator= ruleTypingStrategyUseSiteOperator EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypingStrategyUseSiteOperatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypingStrategyUseSiteOperator=ruleTypingStrategyUseSiteOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypingStrategyUseSiteOperator.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypingStrategyUseSiteOperator"


    // $ANTLR start "ruleTypingStrategyUseSiteOperator"
    // InternalTypesParser.g:5592:1: ruleTypingStrategyUseSiteOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= Tilde (kw= Tilde | this_STRUCTMODSUFFIX_2= RULE_STRUCTMODSUFFIX )? ) ;
    public final AntlrDatatypeRuleToken ruleTypingStrategyUseSiteOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_STRUCTMODSUFFIX_2=null;


        	enterRule();

        try {
            // InternalTypesParser.g:5598:2: ( (kw= Tilde (kw= Tilde | this_STRUCTMODSUFFIX_2= RULE_STRUCTMODSUFFIX )? ) )
            // InternalTypesParser.g:5599:2: (kw= Tilde (kw= Tilde | this_STRUCTMODSUFFIX_2= RULE_STRUCTMODSUFFIX )? )
            {
            // InternalTypesParser.g:5599:2: (kw= Tilde (kw= Tilde | this_STRUCTMODSUFFIX_2= RULE_STRUCTMODSUFFIX )? )
            // InternalTypesParser.g:5600:3: kw= Tilde (kw= Tilde | this_STRUCTMODSUFFIX_2= RULE_STRUCTMODSUFFIX )?
            {
            kw=(Token)match(input,Tilde,FOLLOW_90); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_0());
              		
            }
            // InternalTypesParser.g:5605:3: (kw= Tilde | this_STRUCTMODSUFFIX_2= RULE_STRUCTMODSUFFIX )?
            int alt109=3;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==Tilde) ) {
                alt109=1;
            }
            else if ( (LA109_0==RULE_STRUCTMODSUFFIX) ) {
                alt109=2;
            }
            switch (alt109) {
                case 1 :
                    // InternalTypesParser.g:5606:4: kw= Tilde
                    {
                    kw=(Token)match(input,Tilde,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:5612:4: this_STRUCTMODSUFFIX_2= RULE_STRUCTMODSUFFIX
                    {
                    this_STRUCTMODSUFFIX_2=(Token)match(input,RULE_STRUCTMODSUFFIX,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_STRUCTMODSUFFIX_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_STRUCTMODSUFFIX_2, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getSTRUCTMODSUFFIXTerminalRuleCall_1_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypingStrategyUseSiteOperator"


    // $ANTLR start "entryRuleTypingStrategyDefSiteOperator"
    // InternalTypesParser.g:5624:1: entryRuleTypingStrategyDefSiteOperator returns [String current=null] : iv_ruleTypingStrategyDefSiteOperator= ruleTypingStrategyDefSiteOperator EOF ;
    public final String entryRuleTypingStrategyDefSiteOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTypingStrategyDefSiteOperator = null;


        try {
            // InternalTypesParser.g:5624:69: (iv_ruleTypingStrategyDefSiteOperator= ruleTypingStrategyDefSiteOperator EOF )
            // InternalTypesParser.g:5625:2: iv_ruleTypingStrategyDefSiteOperator= ruleTypingStrategyDefSiteOperator EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypingStrategyDefSiteOperatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypingStrategyDefSiteOperator=ruleTypingStrategyDefSiteOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypingStrategyDefSiteOperator.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypingStrategyDefSiteOperator"


    // $ANTLR start "ruleTypingStrategyDefSiteOperator"
    // InternalTypesParser.g:5631:1: ruleTypingStrategyDefSiteOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= Tilde ;
    public final AntlrDatatypeRuleToken ruleTypingStrategyDefSiteOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalTypesParser.g:5637:2: (kw= Tilde )
            // InternalTypesParser.g:5638:2: kw= Tilde
            {
            kw=(Token)match(input,Tilde,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.getTypingStrategyDefSiteOperatorAccess().getTildeKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypingStrategyDefSiteOperator"


    // $ANTLR start "entryRuleTypeTypeRef"
    // InternalTypesParser.g:5646:1: entryRuleTypeTypeRef returns [EObject current=null] : iv_ruleTypeTypeRef= ruleTypeTypeRef EOF ;
    public final EObject entryRuleTypeTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeTypeRef = null;


        try {
            // InternalTypesParser.g:5646:52: (iv_ruleTypeTypeRef= ruleTypeTypeRef EOF )
            // InternalTypesParser.g:5647:2: iv_ruleTypeTypeRef= ruleTypeTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeTypeRef=ruleTypeTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeTypeRef"


    // $ANTLR start "ruleTypeTypeRef"
    // InternalTypesParser.g:5653:1: ruleTypeTypeRef returns [EObject current=null] : ( () (otherlv_1= Type | ( (lv_constructorRef_2_0= Constructor ) ) ) otherlv_3= LeftCurlyBracket ( (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef ) ) otherlv_5= RightCurlyBracket ) ;
    public final EObject ruleTypeTypeRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_constructorRef_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_typeArg_4_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5659:2: ( ( () (otherlv_1= Type | ( (lv_constructorRef_2_0= Constructor ) ) ) otherlv_3= LeftCurlyBracket ( (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef ) ) otherlv_5= RightCurlyBracket ) )
            // InternalTypesParser.g:5660:2: ( () (otherlv_1= Type | ( (lv_constructorRef_2_0= Constructor ) ) ) otherlv_3= LeftCurlyBracket ( (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef ) ) otherlv_5= RightCurlyBracket )
            {
            // InternalTypesParser.g:5660:2: ( () (otherlv_1= Type | ( (lv_constructorRef_2_0= Constructor ) ) ) otherlv_3= LeftCurlyBracket ( (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef ) ) otherlv_5= RightCurlyBracket )
            // InternalTypesParser.g:5661:3: () (otherlv_1= Type | ( (lv_constructorRef_2_0= Constructor ) ) ) otherlv_3= LeftCurlyBracket ( (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef ) ) otherlv_5= RightCurlyBracket
            {
            // InternalTypesParser.g:5661:3: ()
            // InternalTypesParser.g:5662:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getTypeTypeRefAccess().getTypeTypeRefAction_0(),
              					current);
              			
            }

            }

            // InternalTypesParser.g:5668:3: (otherlv_1= Type | ( (lv_constructorRef_2_0= Constructor ) ) )
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==Type) ) {
                alt110=1;
            }
            else if ( (LA110_0==Constructor) ) {
                alt110=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }
            switch (alt110) {
                case 1 :
                    // InternalTypesParser.g:5669:4: otherlv_1= Type
                    {
                    otherlv_1=(Token)match(input,Type,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getTypeTypeRefAccess().getTypeKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:5674:4: ( (lv_constructorRef_2_0= Constructor ) )
                    {
                    // InternalTypesParser.g:5674:4: ( (lv_constructorRef_2_0= Constructor ) )
                    // InternalTypesParser.g:5675:5: (lv_constructorRef_2_0= Constructor )
                    {
                    // InternalTypesParser.g:5675:5: (lv_constructorRef_2_0= Constructor )
                    // InternalTypesParser.g:5676:6: lv_constructorRef_2_0= Constructor
                    {
                    lv_constructorRef_2_0=(Token)match(input,Constructor,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_constructorRef_2_0, grammarAccess.getTypeTypeRefAccess().getConstructorRefConstructorKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTypeTypeRefRule());
                      						}
                      						setWithLastConsumed(current, "constructorRef", true, "constructor");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,LeftCurlyBracket,FOLLOW_91); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getTypeTypeRefAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            // InternalTypesParser.g:5693:3: ( (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef ) )
            // InternalTypesParser.g:5694:4: (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef )
            {
            // InternalTypesParser.g:5694:4: (lv_typeArg_4_0= ruleTypeArgInTypeTypeRef )
            // InternalTypesParser.g:5695:5: lv_typeArg_4_0= ruleTypeArgInTypeTypeRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTypeTypeRefAccess().getTypeArgTypeArgInTypeTypeRefParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_18);
            lv_typeArg_4_0=ruleTypeArgInTypeTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTypeTypeRefRule());
              					}
              					set(
              						current,
              						"typeArg",
              						lv_typeArg_4_0,
              						"org.eclipse.n4js.ts.TypeExpressions.TypeArgInTypeTypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_5=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getTypeTypeRefAccess().getRightCurlyBracketKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeTypeRef"


    // $ANTLR start "entryRuleTypeArgument"
    // InternalTypesParser.g:5720:1: entryRuleTypeArgument returns [EObject current=null] : iv_ruleTypeArgument= ruleTypeArgument EOF ;
    public final EObject entryRuleTypeArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeArgument = null;


        try {
            // InternalTypesParser.g:5720:53: (iv_ruleTypeArgument= ruleTypeArgument EOF )
            // InternalTypesParser.g:5721:2: iv_ruleTypeArgument= ruleTypeArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeArgument=ruleTypeArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeArgument; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeArgument"


    // $ANTLR start "ruleTypeArgument"
    // InternalTypesParser.g:5727:1: ruleTypeArgument returns [EObject current=null] : ( ( ( ( () QuestionMark ) )=>this_Wildcard_0= ruleWildcard ) | this_WildcardNewNotation_1= ruleWildcardNewNotation | this_TypeRef_2= ruleTypeRef ) ;
    public final EObject ruleTypeArgument() throws RecognitionException {
        EObject current = null;

        EObject this_Wildcard_0 = null;

        EObject this_WildcardNewNotation_1 = null;

        EObject this_TypeRef_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5733:2: ( ( ( ( ( () QuestionMark ) )=>this_Wildcard_0= ruleWildcard ) | this_WildcardNewNotation_1= ruleWildcardNewNotation | this_TypeRef_2= ruleTypeRef ) )
            // InternalTypesParser.g:5734:2: ( ( ( ( () QuestionMark ) )=>this_Wildcard_0= ruleWildcard ) | this_WildcardNewNotation_1= ruleWildcardNewNotation | this_TypeRef_2= ruleTypeRef )
            {
            // InternalTypesParser.g:5734:2: ( ( ( ( () QuestionMark ) )=>this_Wildcard_0= ruleWildcard ) | this_WildcardNewNotation_1= ruleWildcardNewNotation | this_TypeRef_2= ruleTypeRef )
            int alt111=3;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==QuestionMark) && (synpred27_InternalTypesParser())) {
                alt111=1;
            }
            else if ( (LA111_0==Out||LA111_0==In) ) {
                alt111=2;
            }
            else if ( ((LA111_0>=Intersection && LA111_0<=Constructor)||LA111_0==Undefined||LA111_0==Indexed||LA111_0==Union||(LA111_0>=Null && LA111_0<=This_1)||(LA111_0>=Type && LA111_0<=Void)||LA111_0==Any||LA111_0==LeftCurlyBracket||LA111_0==Tilde||LA111_0==RULE_IDENTIFIER) ) {
                alt111=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }
            switch (alt111) {
                case 1 :
                    // InternalTypesParser.g:5735:3: ( ( ( () QuestionMark ) )=>this_Wildcard_0= ruleWildcard )
                    {
                    // InternalTypesParser.g:5735:3: ( ( ( () QuestionMark ) )=>this_Wildcard_0= ruleWildcard )
                    // InternalTypesParser.g:5736:4: ( ( () QuestionMark ) )=>this_Wildcard_0= ruleWildcard
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypeArgumentAccess().getWildcardParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Wildcard_0=ruleWildcard();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Wildcard_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:5752:3: this_WildcardNewNotation_1= ruleWildcardNewNotation
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeArgumentAccess().getWildcardNewNotationParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_WildcardNewNotation_1=ruleWildcardNewNotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_WildcardNewNotation_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:5761:3: this_TypeRef_2= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeArgumentAccess().getTypeRefParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeRef_2=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TypeRef_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeArgument"


    // $ANTLR start "entryRuleWildcard"
    // InternalTypesParser.g:5773:1: entryRuleWildcard returns [EObject current=null] : iv_ruleWildcard= ruleWildcard EOF ;
    public final EObject entryRuleWildcard() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWildcard = null;


        try {
            // InternalTypesParser.g:5773:49: (iv_ruleWildcard= ruleWildcard EOF )
            // InternalTypesParser.g:5774:2: iv_ruleWildcard= ruleWildcard EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWildcardRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWildcard=ruleWildcard();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWildcard; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWildcard"


    // $ANTLR start "ruleWildcard"
    // InternalTypesParser.g:5780:1: ruleWildcard returns [EObject current=null] : ( ( ( ( () QuestionMark ) )=> ( () otherlv_1= QuestionMark ) ) ( (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) ) | (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) ) )? ) ;
    public final EObject ruleWildcard() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_declaredUpperBound_3_0 = null;

        EObject lv_declaredLowerBound_5_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5786:2: ( ( ( ( ( () QuestionMark ) )=> ( () otherlv_1= QuestionMark ) ) ( (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) ) | (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) ) )? ) )
            // InternalTypesParser.g:5787:2: ( ( ( ( () QuestionMark ) )=> ( () otherlv_1= QuestionMark ) ) ( (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) ) | (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) ) )? )
            {
            // InternalTypesParser.g:5787:2: ( ( ( ( () QuestionMark ) )=> ( () otherlv_1= QuestionMark ) ) ( (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) ) | (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) ) )? )
            // InternalTypesParser.g:5788:3: ( ( ( () QuestionMark ) )=> ( () otherlv_1= QuestionMark ) ) ( (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) ) | (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) ) )?
            {
            // InternalTypesParser.g:5788:3: ( ( ( () QuestionMark ) )=> ( () otherlv_1= QuestionMark ) )
            // InternalTypesParser.g:5789:4: ( ( () QuestionMark ) )=> ( () otherlv_1= QuestionMark )
            {
            // InternalTypesParser.g:5795:4: ( () otherlv_1= QuestionMark )
            // InternalTypesParser.g:5796:5: () otherlv_1= QuestionMark
            {
            // InternalTypesParser.g:5796:5: ()
            // InternalTypesParser.g:5797:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getWildcardAccess().getWildcardAction_0_0_0(),
              							current);
              					
            }

            }

            otherlv_1=(Token)match(input,QuestionMark,FOLLOW_92); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getWildcardAccess().getQuestionMarkKeyword_0_0_1());
              				
            }

            }


            }

            // InternalTypesParser.g:5809:3: ( (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) ) | (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) ) )?
            int alt112=3;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==Extends) ) {
                alt112=1;
            }
            else if ( (LA112_0==Super) ) {
                alt112=2;
            }
            switch (alt112) {
                case 1 :
                    // InternalTypesParser.g:5810:4: (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) )
                    {
                    // InternalTypesParser.g:5810:4: (otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) ) )
                    // InternalTypesParser.g:5811:5: otherlv_2= Extends ( (lv_declaredUpperBound_3_0= ruleTypeRef ) )
                    {
                    otherlv_2=(Token)match(input,Extends,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getWildcardAccess().getExtendsKeyword_1_0_0());
                      				
                    }
                    // InternalTypesParser.g:5815:5: ( (lv_declaredUpperBound_3_0= ruleTypeRef ) )
                    // InternalTypesParser.g:5816:6: (lv_declaredUpperBound_3_0= ruleTypeRef )
                    {
                    // InternalTypesParser.g:5816:6: (lv_declaredUpperBound_3_0= ruleTypeRef )
                    // InternalTypesParser.g:5817:7: lv_declaredUpperBound_3_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getWildcardAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_declaredUpperBound_3_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getWildcardRule());
                      							}
                      							set(
                      								current,
                      								"declaredUpperBound",
                      								lv_declaredUpperBound_3_0,
                      								"org.eclipse.n4js.ts.Types.TypeRef");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:5836:4: (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) )
                    {
                    // InternalTypesParser.g:5836:4: (otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) ) )
                    // InternalTypesParser.g:5837:5: otherlv_4= Super ( (lv_declaredLowerBound_5_0= ruleTypeRef ) )
                    {
                    otherlv_4=(Token)match(input,Super,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getWildcardAccess().getSuperKeyword_1_1_0());
                      				
                    }
                    // InternalTypesParser.g:5841:5: ( (lv_declaredLowerBound_5_0= ruleTypeRef ) )
                    // InternalTypesParser.g:5842:6: (lv_declaredLowerBound_5_0= ruleTypeRef )
                    {
                    // InternalTypesParser.g:5842:6: (lv_declaredLowerBound_5_0= ruleTypeRef )
                    // InternalTypesParser.g:5843:7: lv_declaredLowerBound_5_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getWildcardAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_declaredLowerBound_5_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getWildcardRule());
                      							}
                      							set(
                      								current,
                      								"declaredLowerBound",
                      								lv_declaredLowerBound_5_0,
                      								"org.eclipse.n4js.ts.Types.TypeRef");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWildcard"


    // $ANTLR start "entryRuleWildcardNewNotation"
    // InternalTypesParser.g:5866:1: entryRuleWildcardNewNotation returns [EObject current=null] : iv_ruleWildcardNewNotation= ruleWildcardNewNotation EOF ;
    public final EObject entryRuleWildcardNewNotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWildcardNewNotation = null;


        try {
            // InternalTypesParser.g:5866:60: (iv_ruleWildcardNewNotation= ruleWildcardNewNotation EOF )
            // InternalTypesParser.g:5867:2: iv_ruleWildcardNewNotation= ruleWildcardNewNotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWildcardNewNotationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWildcardNewNotation=ruleWildcardNewNotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWildcardNewNotation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWildcardNewNotation"


    // $ANTLR start "ruleWildcardNewNotation"
    // InternalTypesParser.g:5873:1: ruleWildcardNewNotation returns [EObject current=null] : ( ( ( (lv_usingInOutNotation_0_0= Out ) ) ( (lv_declaredUpperBound_1_0= ruleTypeRef ) ) ) | ( ( (lv_usingInOutNotation_2_0= In ) ) ( (lv_declaredLowerBound_3_0= ruleTypeRef ) ) ) ) ;
    public final EObject ruleWildcardNewNotation() throws RecognitionException {
        EObject current = null;

        Token lv_usingInOutNotation_0_0=null;
        Token lv_usingInOutNotation_2_0=null;
        EObject lv_declaredUpperBound_1_0 = null;

        EObject lv_declaredLowerBound_3_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5879:2: ( ( ( ( (lv_usingInOutNotation_0_0= Out ) ) ( (lv_declaredUpperBound_1_0= ruleTypeRef ) ) ) | ( ( (lv_usingInOutNotation_2_0= In ) ) ( (lv_declaredLowerBound_3_0= ruleTypeRef ) ) ) ) )
            // InternalTypesParser.g:5880:2: ( ( ( (lv_usingInOutNotation_0_0= Out ) ) ( (lv_declaredUpperBound_1_0= ruleTypeRef ) ) ) | ( ( (lv_usingInOutNotation_2_0= In ) ) ( (lv_declaredLowerBound_3_0= ruleTypeRef ) ) ) )
            {
            // InternalTypesParser.g:5880:2: ( ( ( (lv_usingInOutNotation_0_0= Out ) ) ( (lv_declaredUpperBound_1_0= ruleTypeRef ) ) ) | ( ( (lv_usingInOutNotation_2_0= In ) ) ( (lv_declaredLowerBound_3_0= ruleTypeRef ) ) ) )
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==Out) ) {
                alt113=1;
            }
            else if ( (LA113_0==In) ) {
                alt113=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }
            switch (alt113) {
                case 1 :
                    // InternalTypesParser.g:5881:3: ( ( (lv_usingInOutNotation_0_0= Out ) ) ( (lv_declaredUpperBound_1_0= ruleTypeRef ) ) )
                    {
                    // InternalTypesParser.g:5881:3: ( ( (lv_usingInOutNotation_0_0= Out ) ) ( (lv_declaredUpperBound_1_0= ruleTypeRef ) ) )
                    // InternalTypesParser.g:5882:4: ( (lv_usingInOutNotation_0_0= Out ) ) ( (lv_declaredUpperBound_1_0= ruleTypeRef ) )
                    {
                    // InternalTypesParser.g:5882:4: ( (lv_usingInOutNotation_0_0= Out ) )
                    // InternalTypesParser.g:5883:5: (lv_usingInOutNotation_0_0= Out )
                    {
                    // InternalTypesParser.g:5883:5: (lv_usingInOutNotation_0_0= Out )
                    // InternalTypesParser.g:5884:6: lv_usingInOutNotation_0_0= Out
                    {
                    lv_usingInOutNotation_0_0=(Token)match(input,Out,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_usingInOutNotation_0_0, grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationOutKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getWildcardNewNotationRule());
                      						}
                      						setWithLastConsumed(current, "usingInOutNotation", true, "out");
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:5896:4: ( (lv_declaredUpperBound_1_0= ruleTypeRef ) )
                    // InternalTypesParser.g:5897:5: (lv_declaredUpperBound_1_0= ruleTypeRef )
                    {
                    // InternalTypesParser.g:5897:5: (lv_declaredUpperBound_1_0= ruleTypeRef )
                    // InternalTypesParser.g:5898:6: lv_declaredUpperBound_1_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundTypeRefParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_declaredUpperBound_1_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getWildcardNewNotationRule());
                      						}
                      						set(
                      							current,
                      							"declaredUpperBound",
                      							lv_declaredUpperBound_1_0,
                      							"org.eclipse.n4js.ts.Types.TypeRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:5917:3: ( ( (lv_usingInOutNotation_2_0= In ) ) ( (lv_declaredLowerBound_3_0= ruleTypeRef ) ) )
                    {
                    // InternalTypesParser.g:5917:3: ( ( (lv_usingInOutNotation_2_0= In ) ) ( (lv_declaredLowerBound_3_0= ruleTypeRef ) ) )
                    // InternalTypesParser.g:5918:4: ( (lv_usingInOutNotation_2_0= In ) ) ( (lv_declaredLowerBound_3_0= ruleTypeRef ) )
                    {
                    // InternalTypesParser.g:5918:4: ( (lv_usingInOutNotation_2_0= In ) )
                    // InternalTypesParser.g:5919:5: (lv_usingInOutNotation_2_0= In )
                    {
                    // InternalTypesParser.g:5919:5: (lv_usingInOutNotation_2_0= In )
                    // InternalTypesParser.g:5920:6: lv_usingInOutNotation_2_0= In
                    {
                    lv_usingInOutNotation_2_0=(Token)match(input,In,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_usingInOutNotation_2_0, grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationInKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getWildcardNewNotationRule());
                      						}
                      						setWithLastConsumed(current, "usingInOutNotation", true, "in");
                      					
                    }

                    }


                    }

                    // InternalTypesParser.g:5932:4: ( (lv_declaredLowerBound_3_0= ruleTypeRef ) )
                    // InternalTypesParser.g:5933:5: (lv_declaredLowerBound_3_0= ruleTypeRef )
                    {
                    // InternalTypesParser.g:5933:5: (lv_declaredLowerBound_3_0= ruleTypeRef )
                    // InternalTypesParser.g:5934:6: lv_declaredLowerBound_3_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_declaredLowerBound_3_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getWildcardNewNotationRule());
                      						}
                      						set(
                      							current,
                      							"declaredLowerBound",
                      							lv_declaredLowerBound_3_0,
                      							"org.eclipse.n4js.ts.Types.TypeRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWildcardNewNotation"


    // $ANTLR start "entrySuperTypeVariable"
    // InternalTypesParser.g:5956:1: entrySuperTypeVariable returns [EObject current=null] : iv_superTypeVariable= superTypeVariable EOF ;
    public final EObject entrySuperTypeVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_superTypeVariable = null;


        try {
            // InternalTypesParser.g:5956:54: (iv_superTypeVariable= superTypeVariable EOF )
            // InternalTypesParser.g:5957:2: iv_superTypeVariable= superTypeVariable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeExpressionsTypeVariableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_superTypeVariable=superTypeVariable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_superTypeVariable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entrySuperTypeVariable"


    // $ANTLR start "superTypeVariable"
    // InternalTypesParser.g:5963:1: superTypeVariable returns [EObject current=null] : ( ( ( (lv_declaredCovariant_0_0= Out ) ) | ( (lv_declaredContravariant_1_0= In ) ) )? ( (lv_name_2_0= RULE_IDENTIFIER ) ) (otherlv_3= Extends ( (lv_declaredUpperBound_4_0= ruleTypeRef ) ) )? ) ;
    public final EObject superTypeVariable() throws RecognitionException {
        EObject current = null;

        Token lv_declaredCovariant_0_0=null;
        Token lv_declaredContravariant_1_0=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        EObject lv_declaredUpperBound_4_0 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:5969:2: ( ( ( ( (lv_declaredCovariant_0_0= Out ) ) | ( (lv_declaredContravariant_1_0= In ) ) )? ( (lv_name_2_0= RULE_IDENTIFIER ) ) (otherlv_3= Extends ( (lv_declaredUpperBound_4_0= ruleTypeRef ) ) )? ) )
            // InternalTypesParser.g:5970:2: ( ( ( (lv_declaredCovariant_0_0= Out ) ) | ( (lv_declaredContravariant_1_0= In ) ) )? ( (lv_name_2_0= RULE_IDENTIFIER ) ) (otherlv_3= Extends ( (lv_declaredUpperBound_4_0= ruleTypeRef ) ) )? )
            {
            // InternalTypesParser.g:5970:2: ( ( ( (lv_declaredCovariant_0_0= Out ) ) | ( (lv_declaredContravariant_1_0= In ) ) )? ( (lv_name_2_0= RULE_IDENTIFIER ) ) (otherlv_3= Extends ( (lv_declaredUpperBound_4_0= ruleTypeRef ) ) )? )
            // InternalTypesParser.g:5971:3: ( ( (lv_declaredCovariant_0_0= Out ) ) | ( (lv_declaredContravariant_1_0= In ) ) )? ( (lv_name_2_0= RULE_IDENTIFIER ) ) (otherlv_3= Extends ( (lv_declaredUpperBound_4_0= ruleTypeRef ) ) )?
            {
            // InternalTypesParser.g:5971:3: ( ( (lv_declaredCovariant_0_0= Out ) ) | ( (lv_declaredContravariant_1_0= In ) ) )?
            int alt114=3;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==Out) ) {
                alt114=1;
            }
            else if ( (LA114_0==In) ) {
                alt114=2;
            }
            switch (alt114) {
                case 1 :
                    // InternalTypesParser.g:5972:4: ( (lv_declaredCovariant_0_0= Out ) )
                    {
                    // InternalTypesParser.g:5972:4: ( (lv_declaredCovariant_0_0= Out ) )
                    // InternalTypesParser.g:5973:5: (lv_declaredCovariant_0_0= Out )
                    {
                    // InternalTypesParser.g:5973:5: (lv_declaredCovariant_0_0= Out )
                    // InternalTypesParser.g:5974:6: lv_declaredCovariant_0_0= Out
                    {
                    lv_declaredCovariant_0_0=(Token)match(input,Out,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_declaredCovariant_0_0, grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTypeExpressionsTypeVariableRule());
                      						}
                      						setWithLastConsumed(current, "declaredCovariant", true, "out");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:5987:4: ( (lv_declaredContravariant_1_0= In ) )
                    {
                    // InternalTypesParser.g:5987:4: ( (lv_declaredContravariant_1_0= In ) )
                    // InternalTypesParser.g:5988:5: (lv_declaredContravariant_1_0= In )
                    {
                    // InternalTypesParser.g:5988:5: (lv_declaredContravariant_1_0= In )
                    // InternalTypesParser.g:5989:6: lv_declaredContravariant_1_0= In
                    {
                    lv_declaredContravariant_1_0=(Token)match(input,In,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_declaredContravariant_1_0, grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTypeExpressionsTypeVariableRule());
                      						}
                      						setWithLastConsumed(current, "declaredContravariant", true, "in");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalTypesParser.g:6002:3: ( (lv_name_2_0= RULE_IDENTIFIER ) )
            // InternalTypesParser.g:6003:4: (lv_name_2_0= RULE_IDENTIFIER )
            {
            // InternalTypesParser.g:6003:4: (lv_name_2_0= RULE_IDENTIFIER )
            // InternalTypesParser.g:6004:5: lv_name_2_0= RULE_IDENTIFIER
            {
            lv_name_2_0=(Token)match(input,RULE_IDENTIFIER,FOLLOW_44); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_2_0, grammarAccess.getTypeExpressionsTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getTypeExpressionsTypeVariableRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_2_0,
              						"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
              				
            }

            }


            }

            // InternalTypesParser.g:6020:3: (otherlv_3= Extends ( (lv_declaredUpperBound_4_0= ruleTypeRef ) ) )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==Extends) ) {
                alt115=1;
            }
            switch (alt115) {
                case 1 :
                    // InternalTypesParser.g:6021:4: otherlv_3= Extends ( (lv_declaredUpperBound_4_0= ruleTypeRef ) )
                    {
                    otherlv_3=(Token)match(input,Extends,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getTypeExpressionsTypeVariableAccess().getExtendsKeyword_2_0());
                      			
                    }
                    // InternalTypesParser.g:6025:4: ( (lv_declaredUpperBound_4_0= ruleTypeRef ) )
                    // InternalTypesParser.g:6026:5: (lv_declaredUpperBound_4_0= ruleTypeRef )
                    {
                    // InternalTypesParser.g:6026:5: (lv_declaredUpperBound_4_0= ruleTypeRef )
                    // InternalTypesParser.g:6027:6: lv_declaredUpperBound_4_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_declaredUpperBound_4_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTypeExpressionsTypeVariableRule());
                      						}
                      						set(
                      							current,
                      							"declaredUpperBound",
                      							lv_declaredUpperBound_4_0,
                      							"org.eclipse.n4js.ts.Types.TypeRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "superTypeVariable"


    // $ANTLR start "entryRuleBindingIdentifier"
    // InternalTypesParser.g:6049:1: entryRuleBindingIdentifier returns [String current=null] : iv_ruleBindingIdentifier= ruleBindingIdentifier EOF ;
    public final String entryRuleBindingIdentifier() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBindingIdentifier = null;


        try {
            // InternalTypesParser.g:6049:57: (iv_ruleBindingIdentifier= ruleBindingIdentifier EOF )
            // InternalTypesParser.g:6050:2: iv_ruleBindingIdentifier= ruleBindingIdentifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBindingIdentifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBindingIdentifier=ruleBindingIdentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBindingIdentifier.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBindingIdentifier"


    // $ANTLR start "ruleBindingIdentifier"
    // InternalTypesParser.g:6056:1: ruleBindingIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IDENTIFIER_0= RULE_IDENTIFIER | kw= Yield | this_N4Keyword_2= ruleN4Keyword ) ;
    public final AntlrDatatypeRuleToken ruleBindingIdentifier() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_IDENTIFIER_0=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_N4Keyword_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:6062:2: ( (this_IDENTIFIER_0= RULE_IDENTIFIER | kw= Yield | this_N4Keyword_2= ruleN4Keyword ) )
            // InternalTypesParser.g:6063:2: (this_IDENTIFIER_0= RULE_IDENTIFIER | kw= Yield | this_N4Keyword_2= ruleN4Keyword )
            {
            // InternalTypesParser.g:6063:2: (this_IDENTIFIER_0= RULE_IDENTIFIER | kw= Yield | this_N4Keyword_2= ruleN4Keyword )
            int alt116=3;
            switch ( input.LA(1) ) {
            case RULE_IDENTIFIER:
                {
                alt116=1;
                }
                break;
            case Yield:
                {
                alt116=2;
                }
                break;
            case Intersection:
            case Constructor:
            case Implements:
            case Promisify:
            case Interface:
            case Protected:
            case Abstract:
            case External:
            case Private:
            case Project:
            case Public:
            case Static:
            case Target:
            case Async:
            case Await:
            case Union:
            case This:
            case From:
            case Type:
            case Get:
            case Let:
            case Out:
            case Set:
            case As:
            case Of:
                {
                alt116=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // InternalTypesParser.g:6064:3: this_IDENTIFIER_0= RULE_IDENTIFIER
                    {
                    this_IDENTIFIER_0=(Token)match(input,RULE_IDENTIFIER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_IDENTIFIER_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_IDENTIFIER_0, grammarAccess.getBindingIdentifierAccess().getIDENTIFIERTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:6072:3: kw= Yield
                    {
                    kw=(Token)match(input,Yield,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getBindingIdentifierAccess().getYieldKeyword_1_0());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:6078:3: this_N4Keyword_2= ruleN4Keyword
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getBindingIdentifierAccess().getN4KeywordParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_N4Keyword_2=ruleN4Keyword();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_N4Keyword_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBindingIdentifier"


    // $ANTLR start "entryRuleIdentifierName"
    // InternalTypesParser.g:6092:1: entryRuleIdentifierName returns [String current=null] : iv_ruleIdentifierName= ruleIdentifierName EOF ;
    public final String entryRuleIdentifierName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIdentifierName = null;


        try {
            // InternalTypesParser.g:6092:54: (iv_ruleIdentifierName= ruleIdentifierName EOF )
            // InternalTypesParser.g:6093:2: iv_ruleIdentifierName= ruleIdentifierName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIdentifierNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIdentifierName=ruleIdentifierName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIdentifierName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIdentifierName"


    // $ANTLR start "ruleIdentifierName"
    // InternalTypesParser.g:6099:1: ruleIdentifierName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IDENTIFIER_0= RULE_IDENTIFIER | this_ReservedWord_1= ruleReservedWord | this_N4Keyword_2= ruleN4Keyword ) ;
    public final AntlrDatatypeRuleToken ruleIdentifierName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_IDENTIFIER_0=null;
        AntlrDatatypeRuleToken this_ReservedWord_1 = null;

        AntlrDatatypeRuleToken this_N4Keyword_2 = null;



        	enterRule();

        try {
            // InternalTypesParser.g:6105:2: ( (this_IDENTIFIER_0= RULE_IDENTIFIER | this_ReservedWord_1= ruleReservedWord | this_N4Keyword_2= ruleN4Keyword ) )
            // InternalTypesParser.g:6106:2: (this_IDENTIFIER_0= RULE_IDENTIFIER | this_ReservedWord_1= ruleReservedWord | this_N4Keyword_2= ruleN4Keyword )
            {
            // InternalTypesParser.g:6106:2: (this_IDENTIFIER_0= RULE_IDENTIFIER | this_ReservedWord_1= ruleReservedWord | this_N4Keyword_2= ruleN4Keyword )
            int alt117=3;
            switch ( input.LA(1) ) {
            case RULE_IDENTIFIER:
                {
                alt117=1;
                }
                break;
            case Instanceof:
            case Continue:
            case Debugger:
            case Function:
            case Default:
            case Extends:
            case Finally:
            case Delete:
            case Export:
            case Import:
            case Return:
            case Switch:
            case Typeof:
            case Break:
            case Catch:
            case Class:
            case Const:
            case False:
            case Super:
            case Throw:
            case While:
            case Yield:
            case Case:
            case Else:
            case Enum:
            case Null:
            case This_1:
            case True:
            case Void:
            case With:
            case For:
            case New:
            case Try:
            case Var:
            case Do:
            case If:
            case In:
                {
                alt117=2;
                }
                break;
            case Intersection:
            case Constructor:
            case Implements:
            case Promisify:
            case Interface:
            case Protected:
            case Abstract:
            case External:
            case Private:
            case Project:
            case Public:
            case Static:
            case Target:
            case Async:
            case Await:
            case Union:
            case This:
            case From:
            case Type:
            case Get:
            case Let:
            case Out:
            case Set:
            case As:
            case Of:
                {
                alt117=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }

            switch (alt117) {
                case 1 :
                    // InternalTypesParser.g:6107:3: this_IDENTIFIER_0= RULE_IDENTIFIER
                    {
                    this_IDENTIFIER_0=(Token)match(input,RULE_IDENTIFIER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_IDENTIFIER_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_IDENTIFIER_0, grammarAccess.getIdentifierNameAccess().getIDENTIFIERTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:6115:3: this_ReservedWord_1= ruleReservedWord
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getIdentifierNameAccess().getReservedWordParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ReservedWord_1=ruleReservedWord();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ReservedWord_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:6126:3: this_N4Keyword_2= ruleN4Keyword
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getIdentifierNameAccess().getN4KeywordParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_N4Keyword_2=ruleN4Keyword();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_N4Keyword_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIdentifierName"


    // $ANTLR start "entryRuleReservedWord"
    // InternalTypesParser.g:6140:1: entryRuleReservedWord returns [String current=null] : iv_ruleReservedWord= ruleReservedWord EOF ;
    public final String entryRuleReservedWord() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleReservedWord = null;


        try {
            // InternalTypesParser.g:6140:52: (iv_ruleReservedWord= ruleReservedWord EOF )
            // InternalTypesParser.g:6141:2: iv_ruleReservedWord= ruleReservedWord EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getReservedWordRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleReservedWord=ruleReservedWord();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleReservedWord.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReservedWord"


    // $ANTLR start "ruleReservedWord"
    // InternalTypesParser.g:6147:1: ruleReservedWord returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= Break | kw= Case | kw= Catch | kw= Class | kw= Const | kw= Continue | kw= Debugger | kw= Default | kw= Delete | kw= Do | kw= Else | kw= Export | kw= Extends | kw= Finally | kw= For | kw= Function | kw= If | kw= Import | kw= In | kw= Instanceof | kw= New | kw= Return | kw= Super | kw= Switch | kw= This_1 | kw= Throw | kw= Try | kw= Typeof | kw= Var | kw= Void | kw= While | kw= With | kw= Yield | kw= Null | kw= True | kw= False | kw= Enum ) ;
    public final AntlrDatatypeRuleToken ruleReservedWord() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalTypesParser.g:6153:2: ( (kw= Break | kw= Case | kw= Catch | kw= Class | kw= Const | kw= Continue | kw= Debugger | kw= Default | kw= Delete | kw= Do | kw= Else | kw= Export | kw= Extends | kw= Finally | kw= For | kw= Function | kw= If | kw= Import | kw= In | kw= Instanceof | kw= New | kw= Return | kw= Super | kw= Switch | kw= This_1 | kw= Throw | kw= Try | kw= Typeof | kw= Var | kw= Void | kw= While | kw= With | kw= Yield | kw= Null | kw= True | kw= False | kw= Enum ) )
            // InternalTypesParser.g:6154:2: (kw= Break | kw= Case | kw= Catch | kw= Class | kw= Const | kw= Continue | kw= Debugger | kw= Default | kw= Delete | kw= Do | kw= Else | kw= Export | kw= Extends | kw= Finally | kw= For | kw= Function | kw= If | kw= Import | kw= In | kw= Instanceof | kw= New | kw= Return | kw= Super | kw= Switch | kw= This_1 | kw= Throw | kw= Try | kw= Typeof | kw= Var | kw= Void | kw= While | kw= With | kw= Yield | kw= Null | kw= True | kw= False | kw= Enum )
            {
            // InternalTypesParser.g:6154:2: (kw= Break | kw= Case | kw= Catch | kw= Class | kw= Const | kw= Continue | kw= Debugger | kw= Default | kw= Delete | kw= Do | kw= Else | kw= Export | kw= Extends | kw= Finally | kw= For | kw= Function | kw= If | kw= Import | kw= In | kw= Instanceof | kw= New | kw= Return | kw= Super | kw= Switch | kw= This_1 | kw= Throw | kw= Try | kw= Typeof | kw= Var | kw= Void | kw= While | kw= With | kw= Yield | kw= Null | kw= True | kw= False | kw= Enum )
            int alt118=37;
            switch ( input.LA(1) ) {
            case Break:
                {
                alt118=1;
                }
                break;
            case Case:
                {
                alt118=2;
                }
                break;
            case Catch:
                {
                alt118=3;
                }
                break;
            case Class:
                {
                alt118=4;
                }
                break;
            case Const:
                {
                alt118=5;
                }
                break;
            case Continue:
                {
                alt118=6;
                }
                break;
            case Debugger:
                {
                alt118=7;
                }
                break;
            case Default:
                {
                alt118=8;
                }
                break;
            case Delete:
                {
                alt118=9;
                }
                break;
            case Do:
                {
                alt118=10;
                }
                break;
            case Else:
                {
                alt118=11;
                }
                break;
            case Export:
                {
                alt118=12;
                }
                break;
            case Extends:
                {
                alt118=13;
                }
                break;
            case Finally:
                {
                alt118=14;
                }
                break;
            case For:
                {
                alt118=15;
                }
                break;
            case Function:
                {
                alt118=16;
                }
                break;
            case If:
                {
                alt118=17;
                }
                break;
            case Import:
                {
                alt118=18;
                }
                break;
            case In:
                {
                alt118=19;
                }
                break;
            case Instanceof:
                {
                alt118=20;
                }
                break;
            case New:
                {
                alt118=21;
                }
                break;
            case Return:
                {
                alt118=22;
                }
                break;
            case Super:
                {
                alt118=23;
                }
                break;
            case Switch:
                {
                alt118=24;
                }
                break;
            case This_1:
                {
                alt118=25;
                }
                break;
            case Throw:
                {
                alt118=26;
                }
                break;
            case Try:
                {
                alt118=27;
                }
                break;
            case Typeof:
                {
                alt118=28;
                }
                break;
            case Var:
                {
                alt118=29;
                }
                break;
            case Void:
                {
                alt118=30;
                }
                break;
            case While:
                {
                alt118=31;
                }
                break;
            case With:
                {
                alt118=32;
                }
                break;
            case Yield:
                {
                alt118=33;
                }
                break;
            case Null:
                {
                alt118=34;
                }
                break;
            case True:
                {
                alt118=35;
                }
                break;
            case False:
                {
                alt118=36;
                }
                break;
            case Enum:
                {
                alt118=37;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 118, 0, input);

                throw nvae;
            }

            switch (alt118) {
                case 1 :
                    // InternalTypesParser.g:6155:3: kw= Break
                    {
                    kw=(Token)match(input,Break,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getBreakKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:6161:3: kw= Case
                    {
                    kw=(Token)match(input,Case,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getCaseKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:6167:3: kw= Catch
                    {
                    kw=(Token)match(input,Catch,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getCatchKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:6173:3: kw= Class
                    {
                    kw=(Token)match(input,Class,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getClassKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:6179:3: kw= Const
                    {
                    kw=(Token)match(input,Const,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getConstKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalTypesParser.g:6185:3: kw= Continue
                    {
                    kw=(Token)match(input,Continue,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getContinueKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalTypesParser.g:6191:3: kw= Debugger
                    {
                    kw=(Token)match(input,Debugger,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDebuggerKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalTypesParser.g:6197:3: kw= Default
                    {
                    kw=(Token)match(input,Default,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDefaultKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalTypesParser.g:6203:3: kw= Delete
                    {
                    kw=(Token)match(input,Delete,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDeleteKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalTypesParser.g:6209:3: kw= Do
                    {
                    kw=(Token)match(input,Do,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDoKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalTypesParser.g:6215:3: kw= Else
                    {
                    kw=(Token)match(input,Else,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getElseKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalTypesParser.g:6221:3: kw= Export
                    {
                    kw=(Token)match(input,Export,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getExportKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalTypesParser.g:6227:3: kw= Extends
                    {
                    kw=(Token)match(input,Extends,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getExtendsKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalTypesParser.g:6233:3: kw= Finally
                    {
                    kw=(Token)match(input,Finally,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getFinallyKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalTypesParser.g:6239:3: kw= For
                    {
                    kw=(Token)match(input,For,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getForKeyword_14());
                      		
                    }

                    }
                    break;
                case 16 :
                    // InternalTypesParser.g:6245:3: kw= Function
                    {
                    kw=(Token)match(input,Function,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getFunctionKeyword_15());
                      		
                    }

                    }
                    break;
                case 17 :
                    // InternalTypesParser.g:6251:3: kw= If
                    {
                    kw=(Token)match(input,If,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getIfKeyword_16());
                      		
                    }

                    }
                    break;
                case 18 :
                    // InternalTypesParser.g:6257:3: kw= Import
                    {
                    kw=(Token)match(input,Import,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getImportKeyword_17());
                      		
                    }

                    }
                    break;
                case 19 :
                    // InternalTypesParser.g:6263:3: kw= In
                    {
                    kw=(Token)match(input,In,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getInKeyword_18());
                      		
                    }

                    }
                    break;
                case 20 :
                    // InternalTypesParser.g:6269:3: kw= Instanceof
                    {
                    kw=(Token)match(input,Instanceof,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getInstanceofKeyword_19());
                      		
                    }

                    }
                    break;
                case 21 :
                    // InternalTypesParser.g:6275:3: kw= New
                    {
                    kw=(Token)match(input,New,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getNewKeyword_20());
                      		
                    }

                    }
                    break;
                case 22 :
                    // InternalTypesParser.g:6281:3: kw= Return
                    {
                    kw=(Token)match(input,Return,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getReturnKeyword_21());
                      		
                    }

                    }
                    break;
                case 23 :
                    // InternalTypesParser.g:6287:3: kw= Super
                    {
                    kw=(Token)match(input,Super,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getSuperKeyword_22());
                      		
                    }

                    }
                    break;
                case 24 :
                    // InternalTypesParser.g:6293:3: kw= Switch
                    {
                    kw=(Token)match(input,Switch,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getSwitchKeyword_23());
                      		
                    }

                    }
                    break;
                case 25 :
                    // InternalTypesParser.g:6299:3: kw= This_1
                    {
                    kw=(Token)match(input,This_1,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getThisKeyword_24());
                      		
                    }

                    }
                    break;
                case 26 :
                    // InternalTypesParser.g:6305:3: kw= Throw
                    {
                    kw=(Token)match(input,Throw,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getThrowKeyword_25());
                      		
                    }

                    }
                    break;
                case 27 :
                    // InternalTypesParser.g:6311:3: kw= Try
                    {
                    kw=(Token)match(input,Try,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getTryKeyword_26());
                      		
                    }

                    }
                    break;
                case 28 :
                    // InternalTypesParser.g:6317:3: kw= Typeof
                    {
                    kw=(Token)match(input,Typeof,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getTypeofKeyword_27());
                      		
                    }

                    }
                    break;
                case 29 :
                    // InternalTypesParser.g:6323:3: kw= Var
                    {
                    kw=(Token)match(input,Var,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getVarKeyword_28());
                      		
                    }

                    }
                    break;
                case 30 :
                    // InternalTypesParser.g:6329:3: kw= Void
                    {
                    kw=(Token)match(input,Void,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getVoidKeyword_29());
                      		
                    }

                    }
                    break;
                case 31 :
                    // InternalTypesParser.g:6335:3: kw= While
                    {
                    kw=(Token)match(input,While,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getWhileKeyword_30());
                      		
                    }

                    }
                    break;
                case 32 :
                    // InternalTypesParser.g:6341:3: kw= With
                    {
                    kw=(Token)match(input,With,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getWithKeyword_31());
                      		
                    }

                    }
                    break;
                case 33 :
                    // InternalTypesParser.g:6347:3: kw= Yield
                    {
                    kw=(Token)match(input,Yield,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getYieldKeyword_32());
                      		
                    }

                    }
                    break;
                case 34 :
                    // InternalTypesParser.g:6353:3: kw= Null
                    {
                    kw=(Token)match(input,Null,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getNullKeyword_33());
                      		
                    }

                    }
                    break;
                case 35 :
                    // InternalTypesParser.g:6359:3: kw= True
                    {
                    kw=(Token)match(input,True,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getTrueKeyword_34());
                      		
                    }

                    }
                    break;
                case 36 :
                    // InternalTypesParser.g:6365:3: kw= False
                    {
                    kw=(Token)match(input,False,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getFalseKeyword_35());
                      		
                    }

                    }
                    break;
                case 37 :
                    // InternalTypesParser.g:6371:3: kw= Enum
                    {
                    kw=(Token)match(input,Enum,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getReservedWordAccess().getEnumKeyword_36());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReservedWord"


    // $ANTLR start "entryRuleN4Keyword"
    // InternalTypesParser.g:6380:1: entryRuleN4Keyword returns [String current=null] : iv_ruleN4Keyword= ruleN4Keyword EOF ;
    public final String entryRuleN4Keyword() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleN4Keyword = null;


        try {
            // InternalTypesParser.g:6380:49: (iv_ruleN4Keyword= ruleN4Keyword EOF )
            // InternalTypesParser.g:6381:2: iv_ruleN4Keyword= ruleN4Keyword EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getN4KeywordRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleN4Keyword=ruleN4Keyword();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleN4Keyword.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleN4Keyword"


    // $ANTLR start "ruleN4Keyword"
    // InternalTypesParser.g:6387:1: ruleN4Keyword returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= Get | kw= Set | kw= Let | kw= Project | kw= External | kw= Abstract | kw= Static | kw= As | kw= From | kw= Constructor | kw= Of | kw= Target | kw= Type | kw= Union | kw= Intersection | kw= This | kw= Promisify | kw= Await | kw= Async | kw= Implements | kw= Interface | kw= Private | kw= Protected | kw= Public | kw= Out ) ;
    public final AntlrDatatypeRuleToken ruleN4Keyword() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalTypesParser.g:6393:2: ( (kw= Get | kw= Set | kw= Let | kw= Project | kw= External | kw= Abstract | kw= Static | kw= As | kw= From | kw= Constructor | kw= Of | kw= Target | kw= Type | kw= Union | kw= Intersection | kw= This | kw= Promisify | kw= Await | kw= Async | kw= Implements | kw= Interface | kw= Private | kw= Protected | kw= Public | kw= Out ) )
            // InternalTypesParser.g:6394:2: (kw= Get | kw= Set | kw= Let | kw= Project | kw= External | kw= Abstract | kw= Static | kw= As | kw= From | kw= Constructor | kw= Of | kw= Target | kw= Type | kw= Union | kw= Intersection | kw= This | kw= Promisify | kw= Await | kw= Async | kw= Implements | kw= Interface | kw= Private | kw= Protected | kw= Public | kw= Out )
            {
            // InternalTypesParser.g:6394:2: (kw= Get | kw= Set | kw= Let | kw= Project | kw= External | kw= Abstract | kw= Static | kw= As | kw= From | kw= Constructor | kw= Of | kw= Target | kw= Type | kw= Union | kw= Intersection | kw= This | kw= Promisify | kw= Await | kw= Async | kw= Implements | kw= Interface | kw= Private | kw= Protected | kw= Public | kw= Out )
            int alt119=25;
            switch ( input.LA(1) ) {
            case Get:
                {
                alt119=1;
                }
                break;
            case Set:
                {
                alt119=2;
                }
                break;
            case Let:
                {
                alt119=3;
                }
                break;
            case Project:
                {
                alt119=4;
                }
                break;
            case External:
                {
                alt119=5;
                }
                break;
            case Abstract:
                {
                alt119=6;
                }
                break;
            case Static:
                {
                alt119=7;
                }
                break;
            case As:
                {
                alt119=8;
                }
                break;
            case From:
                {
                alt119=9;
                }
                break;
            case Constructor:
                {
                alt119=10;
                }
                break;
            case Of:
                {
                alt119=11;
                }
                break;
            case Target:
                {
                alt119=12;
                }
                break;
            case Type:
                {
                alt119=13;
                }
                break;
            case Union:
                {
                alt119=14;
                }
                break;
            case Intersection:
                {
                alt119=15;
                }
                break;
            case This:
                {
                alt119=16;
                }
                break;
            case Promisify:
                {
                alt119=17;
                }
                break;
            case Await:
                {
                alt119=18;
                }
                break;
            case Async:
                {
                alt119=19;
                }
                break;
            case Implements:
                {
                alt119=20;
                }
                break;
            case Interface:
                {
                alt119=21;
                }
                break;
            case Private:
                {
                alt119=22;
                }
                break;
            case Protected:
                {
                alt119=23;
                }
                break;
            case Public:
                {
                alt119=24;
                }
                break;
            case Out:
                {
                alt119=25;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }

            switch (alt119) {
                case 1 :
                    // InternalTypesParser.g:6395:3: kw= Get
                    {
                    kw=(Token)match(input,Get,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getGetKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:6401:3: kw= Set
                    {
                    kw=(Token)match(input,Set,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getSetKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:6407:3: kw= Let
                    {
                    kw=(Token)match(input,Let,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getLetKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:6413:3: kw= Project
                    {
                    kw=(Token)match(input,Project,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getProjectKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:6419:3: kw= External
                    {
                    kw=(Token)match(input,External,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getExternalKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalTypesParser.g:6425:3: kw= Abstract
                    {
                    kw=(Token)match(input,Abstract,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAbstractKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalTypesParser.g:6431:3: kw= Static
                    {
                    kw=(Token)match(input,Static,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getStaticKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalTypesParser.g:6437:3: kw= As
                    {
                    kw=(Token)match(input,As,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAsKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalTypesParser.g:6443:3: kw= From
                    {
                    kw=(Token)match(input,From,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getFromKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalTypesParser.g:6449:3: kw= Constructor
                    {
                    kw=(Token)match(input,Constructor,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getConstructorKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalTypesParser.g:6455:3: kw= Of
                    {
                    kw=(Token)match(input,Of,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getOfKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalTypesParser.g:6461:3: kw= Target
                    {
                    kw=(Token)match(input,Target,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getTargetKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalTypesParser.g:6467:3: kw= Type
                    {
                    kw=(Token)match(input,Type,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getTypeKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalTypesParser.g:6473:3: kw= Union
                    {
                    kw=(Token)match(input,Union,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getUnionKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalTypesParser.g:6479:3: kw= Intersection
                    {
                    kw=(Token)match(input,Intersection,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getIntersectionKeyword_14());
                      		
                    }

                    }
                    break;
                case 16 :
                    // InternalTypesParser.g:6485:3: kw= This
                    {
                    kw=(Token)match(input,This,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getThisKeyword_15());
                      		
                    }

                    }
                    break;
                case 17 :
                    // InternalTypesParser.g:6491:3: kw= Promisify
                    {
                    kw=(Token)match(input,Promisify,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getPromisifyKeyword_16());
                      		
                    }

                    }
                    break;
                case 18 :
                    // InternalTypesParser.g:6497:3: kw= Await
                    {
                    kw=(Token)match(input,Await,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAwaitKeyword_17());
                      		
                    }

                    }
                    break;
                case 19 :
                    // InternalTypesParser.g:6503:3: kw= Async
                    {
                    kw=(Token)match(input,Async,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAsyncKeyword_18());
                      		
                    }

                    }
                    break;
                case 20 :
                    // InternalTypesParser.g:6509:3: kw= Implements
                    {
                    kw=(Token)match(input,Implements,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getImplementsKeyword_19());
                      		
                    }

                    }
                    break;
                case 21 :
                    // InternalTypesParser.g:6515:3: kw= Interface
                    {
                    kw=(Token)match(input,Interface,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getInterfaceKeyword_20());
                      		
                    }

                    }
                    break;
                case 22 :
                    // InternalTypesParser.g:6521:3: kw= Private
                    {
                    kw=(Token)match(input,Private,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getPrivateKeyword_21());
                      		
                    }

                    }
                    break;
                case 23 :
                    // InternalTypesParser.g:6527:3: kw= Protected
                    {
                    kw=(Token)match(input,Protected,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getProtectedKeyword_22());
                      		
                    }

                    }
                    break;
                case 24 :
                    // InternalTypesParser.g:6533:3: kw= Public
                    {
                    kw=(Token)match(input,Public,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getPublicKeyword_23());
                      		
                    }

                    }
                    break;
                case 25 :
                    // InternalTypesParser.g:6539:3: kw= Out
                    {
                    kw=(Token)match(input,Out,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getOutKeyword_24());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleN4Keyword"


    // $ANTLR start "ruleTypeAccessModifier"
    // InternalTypesParser.g:6548:1: ruleTypeAccessModifier returns [Enumerator current=null] : ( (enumLiteral_0= Project ) | (enumLiteral_1= PublicInternal ) | (enumLiteral_2= Public ) ) ;
    public final Enumerator ruleTypeAccessModifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalTypesParser.g:6554:2: ( ( (enumLiteral_0= Project ) | (enumLiteral_1= PublicInternal ) | (enumLiteral_2= Public ) ) )
            // InternalTypesParser.g:6555:2: ( (enumLiteral_0= Project ) | (enumLiteral_1= PublicInternal ) | (enumLiteral_2= Public ) )
            {
            // InternalTypesParser.g:6555:2: ( (enumLiteral_0= Project ) | (enumLiteral_1= PublicInternal ) | (enumLiteral_2= Public ) )
            int alt120=3;
            switch ( input.LA(1) ) {
            case Project:
                {
                alt120=1;
                }
                break;
            case PublicInternal:
                {
                alt120=2;
                }
                break;
            case Public:
                {
                alt120=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }

            switch (alt120) {
                case 1 :
                    // InternalTypesParser.g:6556:3: (enumLiteral_0= Project )
                    {
                    // InternalTypesParser.g:6556:3: (enumLiteral_0= Project )
                    // InternalTypesParser.g:6557:4: enumLiteral_0= Project
                    {
                    enumLiteral_0=(Token)match(input,Project,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getTypeAccessModifierAccess().getProjectEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getTypeAccessModifierAccess().getProjectEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:6564:3: (enumLiteral_1= PublicInternal )
                    {
                    // InternalTypesParser.g:6564:3: (enumLiteral_1= PublicInternal )
                    // InternalTypesParser.g:6565:4: enumLiteral_1= PublicInternal
                    {
                    enumLiteral_1=(Token)match(input,PublicInternal,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getTypeAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getTypeAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:6572:3: (enumLiteral_2= Public )
                    {
                    // InternalTypesParser.g:6572:3: (enumLiteral_2= Public )
                    // InternalTypesParser.g:6573:4: enumLiteral_2= Public
                    {
                    enumLiteral_2=(Token)match(input,Public,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getTypeAccessModifierAccess().getPublicEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getTypeAccessModifierAccess().getPublicEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeAccessModifier"


    // $ANTLR start "ruleMemberAccessModifier"
    // InternalTypesParser.g:6583:1: ruleMemberAccessModifier returns [Enumerator current=null] : ( (enumLiteral_0= Private ) | (enumLiteral_1= Project ) | (enumLiteral_2= ProtectedInternal ) | (enumLiteral_3= Protected ) | (enumLiteral_4= PublicInternal ) | (enumLiteral_5= Public ) ) ;
    public final Enumerator ruleMemberAccessModifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;


        	enterRule();

        try {
            // InternalTypesParser.g:6589:2: ( ( (enumLiteral_0= Private ) | (enumLiteral_1= Project ) | (enumLiteral_2= ProtectedInternal ) | (enumLiteral_3= Protected ) | (enumLiteral_4= PublicInternal ) | (enumLiteral_5= Public ) ) )
            // InternalTypesParser.g:6590:2: ( (enumLiteral_0= Private ) | (enumLiteral_1= Project ) | (enumLiteral_2= ProtectedInternal ) | (enumLiteral_3= Protected ) | (enumLiteral_4= PublicInternal ) | (enumLiteral_5= Public ) )
            {
            // InternalTypesParser.g:6590:2: ( (enumLiteral_0= Private ) | (enumLiteral_1= Project ) | (enumLiteral_2= ProtectedInternal ) | (enumLiteral_3= Protected ) | (enumLiteral_4= PublicInternal ) | (enumLiteral_5= Public ) )
            int alt121=6;
            switch ( input.LA(1) ) {
            case Private:
                {
                alt121=1;
                }
                break;
            case Project:
                {
                alt121=2;
                }
                break;
            case ProtectedInternal:
                {
                alt121=3;
                }
                break;
            case Protected:
                {
                alt121=4;
                }
                break;
            case PublicInternal:
                {
                alt121=5;
                }
                break;
            case Public:
                {
                alt121=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 121, 0, input);

                throw nvae;
            }

            switch (alt121) {
                case 1 :
                    // InternalTypesParser.g:6591:3: (enumLiteral_0= Private )
                    {
                    // InternalTypesParser.g:6591:3: (enumLiteral_0= Private )
                    // InternalTypesParser.g:6592:4: enumLiteral_0= Private
                    {
                    enumLiteral_0=(Token)match(input,Private,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMemberAccessModifierAccess().getPrivateEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getMemberAccessModifierAccess().getPrivateEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalTypesParser.g:6599:3: (enumLiteral_1= Project )
                    {
                    // InternalTypesParser.g:6599:3: (enumLiteral_1= Project )
                    // InternalTypesParser.g:6600:4: enumLiteral_1= Project
                    {
                    enumLiteral_1=(Token)match(input,Project,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMemberAccessModifierAccess().getProjectEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getMemberAccessModifierAccess().getProjectEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalTypesParser.g:6607:3: (enumLiteral_2= ProtectedInternal )
                    {
                    // InternalTypesParser.g:6607:3: (enumLiteral_2= ProtectedInternal )
                    // InternalTypesParser.g:6608:4: enumLiteral_2= ProtectedInternal
                    {
                    enumLiteral_2=(Token)match(input,ProtectedInternal,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMemberAccessModifierAccess().getProtectedInternalEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getMemberAccessModifierAccess().getProtectedInternalEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalTypesParser.g:6615:3: (enumLiteral_3= Protected )
                    {
                    // InternalTypesParser.g:6615:3: (enumLiteral_3= Protected )
                    // InternalTypesParser.g:6616:4: enumLiteral_3= Protected
                    {
                    enumLiteral_3=(Token)match(input,Protected,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMemberAccessModifierAccess().getProtectedEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getMemberAccessModifierAccess().getProtectedEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalTypesParser.g:6623:3: (enumLiteral_4= PublicInternal )
                    {
                    // InternalTypesParser.g:6623:3: (enumLiteral_4= PublicInternal )
                    // InternalTypesParser.g:6624:4: enumLiteral_4= PublicInternal
                    {
                    enumLiteral_4=(Token)match(input,PublicInternal,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMemberAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_4, grammarAccess.getMemberAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_4());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalTypesParser.g:6631:3: (enumLiteral_5= Public )
                    {
                    // InternalTypesParser.g:6631:3: (enumLiteral_5= Public )
                    // InternalTypesParser.g:6632:4: enumLiteral_5= Public
                    {
                    enumLiteral_5=(Token)match(input,Public,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getMemberAccessModifierAccess().getPublicEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_5, grammarAccess.getMemberAccessModifierAccess().getPublicEnumLiteralDeclaration_5());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMemberAccessModifier"

    // $ANTLR start synpred2_InternalTypesParser
    public final void synpred2_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:153:5: ( LeftParenthesis )
        // InternalTypesParser.g:153:6: LeftParenthesis
        {
        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalTypesParser

    // $ANTLR start synpred3_InternalTypesParser
    public final void synpred3_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:1344:4: ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )
        // InternalTypesParser.g:1344:5: ( CommercialAt ( ( RULE_IDENTIFIER ) ) )
        {
        // InternalTypesParser.g:1344:5: ( CommercialAt ( ( RULE_IDENTIFIER ) ) )
        // InternalTypesParser.g:1345:5: CommercialAt ( ( RULE_IDENTIFIER ) )
        {
        match(input,CommercialAt,FOLLOW_4); if (state.failed) return ;
        // InternalTypesParser.g:1346:5: ( ( RULE_IDENTIFIER ) )
        // InternalTypesParser.g:1347:6: ( RULE_IDENTIFIER )
        {
        // InternalTypesParser.g:1347:6: ( RULE_IDENTIFIER )
        // InternalTypesParser.g:1348:7: RULE_IDENTIFIER
        {
        match(input,RULE_IDENTIFIER,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred3_InternalTypesParser

    // $ANTLR start synpred4_InternalTypesParser
    public final void synpred4_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:1710:4: ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )
        // InternalTypesParser.g:1710:5: ( CommercialAt ( ( RULE_IDENTIFIER ) ) )
        {
        // InternalTypesParser.g:1710:5: ( CommercialAt ( ( RULE_IDENTIFIER ) ) )
        // InternalTypesParser.g:1711:5: CommercialAt ( ( RULE_IDENTIFIER ) )
        {
        match(input,CommercialAt,FOLLOW_4); if (state.failed) return ;
        // InternalTypesParser.g:1712:5: ( ( RULE_IDENTIFIER ) )
        // InternalTypesParser.g:1713:6: ( RULE_IDENTIFIER )
        {
        // InternalTypesParser.g:1713:6: ( RULE_IDENTIFIER )
        // InternalTypesParser.g:1714:7: RULE_IDENTIFIER
        {
        match(input,RULE_IDENTIFIER,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred4_InternalTypesParser

    // $ANTLR start synpred5_InternalTypesParser
    public final void synpred5_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:1922:4: ( ( CommercialAt ( ( RULE_IDENTIFIER ) ) ) )
        // InternalTypesParser.g:1922:5: ( CommercialAt ( ( RULE_IDENTIFIER ) ) )
        {
        // InternalTypesParser.g:1922:5: ( CommercialAt ( ( RULE_IDENTIFIER ) ) )
        // InternalTypesParser.g:1923:5: CommercialAt ( ( RULE_IDENTIFIER ) )
        {
        match(input,CommercialAt,FOLLOW_4); if (state.failed) return ;
        // InternalTypesParser.g:1924:5: ( ( RULE_IDENTIFIER ) )
        // InternalTypesParser.g:1925:6: ( RULE_IDENTIFIER )
        {
        // InternalTypesParser.g:1925:6: ( RULE_IDENTIFIER )
        // InternalTypesParser.g:1926:7: RULE_IDENTIFIER
        {
        match(input,RULE_IDENTIFIER,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred5_InternalTypesParser

    // $ANTLR start synpred6_InternalTypesParser
    public final void synpred6_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:2286:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )
        // InternalTypesParser.g:2286:5: ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) )
        {
        // InternalTypesParser.g:2286:5: ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) )
        // InternalTypesParser.g:2287:5: ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) )
        {
        // InternalTypesParser.g:2287:5: ( ( ruleMemberAccessModifier ) )
        // InternalTypesParser.g:2288:6: ( ruleMemberAccessModifier )
        {
        // InternalTypesParser.g:2288:6: ( ruleMemberAccessModifier )
        // InternalTypesParser.g:2289:7: ruleMemberAccessModifier
        {
        pushFollow(FOLLOW_57);
        ruleMemberAccessModifier();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalTypesParser.g:2292:5: ( ( ( Abstract ) ) | ( ( Static ) ) )?
        int alt122=3;
        int LA122_0 = input.LA(1);

        if ( (LA122_0==Abstract) ) {
            alt122=1;
        }
        else if ( (LA122_0==Static) ) {
            alt122=2;
        }
        switch (alt122) {
            case 1 :
                // InternalTypesParser.g:2293:6: ( ( Abstract ) )
                {
                // InternalTypesParser.g:2293:6: ( ( Abstract ) )
                // InternalTypesParser.g:2294:7: ( Abstract )
                {
                // InternalTypesParser.g:2294:7: ( Abstract )
                // InternalTypesParser.g:2295:8: Abstract
                {
                match(input,Abstract,FOLLOW_58); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalTypesParser.g:2299:6: ( ( Static ) )
                {
                // InternalTypesParser.g:2299:6: ( ( Static ) )
                // InternalTypesParser.g:2300:7: ( Static )
                {
                // InternalTypesParser.g:2300:7: ( Static )
                // InternalTypesParser.g:2301:8: Static
                {
                match(input,Static,FOLLOW_58); if (state.failed) return ;

                }


                }


                }
                break;

        }

        match(input,Get,FOLLOW_52); if (state.failed) return ;
        // InternalTypesParser.g:2306:5: ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) )
        int alt123=2;
        int LA123_0 = input.LA(1);

        if ( (LA123_0==AssignmnentCompatible||(LA123_0>=AutoboxedType && LA123_0<=Finally)||(LA123_0>=Private && LA123_0<=False)||(LA123_0>=Super && LA123_0<=With)||(LA123_0>=Any && LA123_0<=Var)||(LA123_0>=As && LA123_0<=Of)||LA123_0==RULE_IDENTIFIER) ) {
            alt123=1;
        }
        else if ( (LA123_0==LeftSquareBracket) ) {
            alt123=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 123, 0, input);

            throw nvae;
        }
        switch (alt123) {
            case 1 :
                // InternalTypesParser.g:2307:6: ( ( ruleTypesIdentifier ) )
                {
                // InternalTypesParser.g:2307:6: ( ( ruleTypesIdentifier ) )
                // InternalTypesParser.g:2308:7: ( ruleTypesIdentifier )
                {
                // InternalTypesParser.g:2308:7: ( ruleTypesIdentifier )
                // InternalTypesParser.g:2309:8: ruleTypesIdentifier
                {
                pushFollow(FOLLOW_2);
                ruleTypesIdentifier();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalTypesParser.g:2313:6: ( ( ruleTypesComputedPropertyName ) )
                {
                // InternalTypesParser.g:2313:6: ( ( ruleTypesComputedPropertyName ) )
                // InternalTypesParser.g:2314:7: ( ruleTypesComputedPropertyName )
                {
                // InternalTypesParser.g:2314:7: ( ruleTypesComputedPropertyName )
                // InternalTypesParser.g:2315:8: ruleTypesComputedPropertyName
                {
                pushFollow(FOLLOW_2);
                ruleTypesComputedPropertyName();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred6_InternalTypesParser

    // $ANTLR start synpred7_InternalTypesParser
    public final void synpred7_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:2332:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )
        // InternalTypesParser.g:2332:5: ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) )
        {
        // InternalTypesParser.g:2332:5: ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) )
        // InternalTypesParser.g:2333:5: ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) )
        {
        // InternalTypesParser.g:2333:5: ( ( ruleMemberAccessModifier ) )
        // InternalTypesParser.g:2334:6: ( ruleMemberAccessModifier )
        {
        // InternalTypesParser.g:2334:6: ( ruleMemberAccessModifier )
        // InternalTypesParser.g:2335:7: ruleMemberAccessModifier
        {
        pushFollow(FOLLOW_61);
        ruleMemberAccessModifier();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalTypesParser.g:2338:5: ( ( ( Abstract ) ) | ( ( Static ) ) )?
        int alt124=3;
        int LA124_0 = input.LA(1);

        if ( (LA124_0==Abstract) ) {
            alt124=1;
        }
        else if ( (LA124_0==Static) ) {
            alt124=2;
        }
        switch (alt124) {
            case 1 :
                // InternalTypesParser.g:2339:6: ( ( Abstract ) )
                {
                // InternalTypesParser.g:2339:6: ( ( Abstract ) )
                // InternalTypesParser.g:2340:7: ( Abstract )
                {
                // InternalTypesParser.g:2340:7: ( Abstract )
                // InternalTypesParser.g:2341:8: Abstract
                {
                match(input,Abstract,FOLLOW_62); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalTypesParser.g:2345:6: ( ( Static ) )
                {
                // InternalTypesParser.g:2345:6: ( ( Static ) )
                // InternalTypesParser.g:2346:7: ( Static )
                {
                // InternalTypesParser.g:2346:7: ( Static )
                // InternalTypesParser.g:2347:8: Static
                {
                match(input,Static,FOLLOW_62); if (state.failed) return ;

                }


                }


                }
                break;

        }

        match(input,Set,FOLLOW_52); if (state.failed) return ;
        // InternalTypesParser.g:2352:5: ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) )
        int alt125=2;
        int LA125_0 = input.LA(1);

        if ( (LA125_0==AssignmnentCompatible||(LA125_0>=AutoboxedType && LA125_0<=Finally)||(LA125_0>=Private && LA125_0<=False)||(LA125_0>=Super && LA125_0<=With)||(LA125_0>=Any && LA125_0<=Var)||(LA125_0>=As && LA125_0<=Of)||LA125_0==RULE_IDENTIFIER) ) {
            alt125=1;
        }
        else if ( (LA125_0==LeftSquareBracket) ) {
            alt125=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 125, 0, input);

            throw nvae;
        }
        switch (alt125) {
            case 1 :
                // InternalTypesParser.g:2353:6: ( ( ruleTypesIdentifier ) )
                {
                // InternalTypesParser.g:2353:6: ( ( ruleTypesIdentifier ) )
                // InternalTypesParser.g:2354:7: ( ruleTypesIdentifier )
                {
                // InternalTypesParser.g:2354:7: ( ruleTypesIdentifier )
                // InternalTypesParser.g:2355:8: ruleTypesIdentifier
                {
                pushFollow(FOLLOW_2);
                ruleTypesIdentifier();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalTypesParser.g:2359:6: ( ( ruleTypesComputedPropertyName ) )
                {
                // InternalTypesParser.g:2359:6: ( ( ruleTypesComputedPropertyName ) )
                // InternalTypesParser.g:2360:7: ( ruleTypesComputedPropertyName )
                {
                // InternalTypesParser.g:2360:7: ( ruleTypesComputedPropertyName )
                // InternalTypesParser.g:2361:8: ruleTypesComputedPropertyName
                {
                pushFollow(FOLLOW_2);
                ruleTypesComputedPropertyName();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred7_InternalTypesParser

    // $ANTLR start synpred8_InternalTypesParser
    public final void synpred8_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:2378:4: ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )
        // InternalTypesParser.g:2378:5: ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis )
        {
        // InternalTypesParser.g:2378:5: ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis )
        // InternalTypesParser.g:2379:5: ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis
        {
        // InternalTypesParser.g:2379:5: ( ( ruleMemberAccessModifier ) )
        // InternalTypesParser.g:2380:6: ( ruleMemberAccessModifier )
        {
        // InternalTypesParser.g:2380:6: ( ruleMemberAccessModifier )
        // InternalTypesParser.g:2381:7: ruleMemberAccessModifier
        {
        pushFollow(FOLLOW_52);
        ruleMemberAccessModifier();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalTypesParser.g:2384:5: ( ( ( Abstract ) ) | ( ( Static ) ) )?
        int alt126=3;
        int LA126_0 = input.LA(1);

        if ( (LA126_0==Abstract) ) {
            int LA126_1 = input.LA(2);

            if ( (LA126_1==AssignmnentCompatible||(LA126_1>=AutoboxedType && LA126_1<=Finally)||(LA126_1>=Private && LA126_1<=False)||(LA126_1>=Super && LA126_1<=With)||(LA126_1>=Any && LA126_1<=Var)||(LA126_1>=As && LA126_1<=Of)||LA126_1==LessThanSign||LA126_1==LeftSquareBracket||LA126_1==RULE_IDENTIFIER) ) {
                alt126=1;
            }
        }
        else if ( (LA126_0==Static) ) {
            int LA126_2 = input.LA(2);

            if ( (LA126_2==AssignmnentCompatible||(LA126_2>=AutoboxedType && LA126_2<=Finally)||(LA126_2>=Private && LA126_2<=False)||(LA126_2>=Super && LA126_2<=With)||(LA126_2>=Any && LA126_2<=Var)||(LA126_2>=As && LA126_2<=Of)||LA126_2==LessThanSign||LA126_2==LeftSquareBracket||LA126_2==RULE_IDENTIFIER) ) {
                alt126=2;
            }
        }
        switch (alt126) {
            case 1 :
                // InternalTypesParser.g:2385:6: ( ( Abstract ) )
                {
                // InternalTypesParser.g:2385:6: ( ( Abstract ) )
                // InternalTypesParser.g:2386:7: ( Abstract )
                {
                // InternalTypesParser.g:2386:7: ( Abstract )
                // InternalTypesParser.g:2387:8: Abstract
                {
                match(input,Abstract,FOLLOW_52); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalTypesParser.g:2391:6: ( ( Static ) )
                {
                // InternalTypesParser.g:2391:6: ( ( Static ) )
                // InternalTypesParser.g:2392:7: ( Static )
                {
                // InternalTypesParser.g:2392:7: ( Static )
                // InternalTypesParser.g:2393:8: Static
                {
                match(input,Static,FOLLOW_52); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalTypesParser.g:2397:5: ( ruleTypeVariables[null] )?
        int alt127=2;
        int LA127_0 = input.LA(1);

        if ( (LA127_0==LessThanSign) ) {
            alt127=1;
        }
        switch (alt127) {
            case 1 :
                // InternalTypesParser.g:2398:6: ruleTypeVariables[null]
                {
                pushFollow(FOLLOW_52);
                ruleTypeVariables(null);

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // InternalTypesParser.g:2400:5: ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) )
        int alt128=2;
        int LA128_0 = input.LA(1);

        if ( (LA128_0==AssignmnentCompatible||(LA128_0>=AutoboxedType && LA128_0<=Finally)||(LA128_0>=Private && LA128_0<=False)||(LA128_0>=Super && LA128_0<=With)||(LA128_0>=Any && LA128_0<=Var)||(LA128_0>=As && LA128_0<=Of)||LA128_0==RULE_IDENTIFIER) ) {
            alt128=1;
        }
        else if ( (LA128_0==LeftSquareBracket) ) {
            alt128=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 128, 0, input);

            throw nvae;
        }
        switch (alt128) {
            case 1 :
                // InternalTypesParser.g:2401:6: ( ( ruleTypesIdentifier ) )
                {
                // InternalTypesParser.g:2401:6: ( ( ruleTypesIdentifier ) )
                // InternalTypesParser.g:2402:7: ( ruleTypesIdentifier )
                {
                // InternalTypesParser.g:2402:7: ( ruleTypesIdentifier )
                // InternalTypesParser.g:2403:8: ruleTypesIdentifier
                {
                pushFollow(FOLLOW_53);
                ruleTypesIdentifier();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalTypesParser.g:2407:6: ( ( ruleTypesComputedPropertyName ) )
                {
                // InternalTypesParser.g:2407:6: ( ( ruleTypesComputedPropertyName ) )
                // InternalTypesParser.g:2408:7: ( ruleTypesComputedPropertyName )
                {
                // InternalTypesParser.g:2408:7: ( ruleTypesComputedPropertyName )
                // InternalTypesParser.g:2409:8: ruleTypesComputedPropertyName
                {
                pushFollow(FOLLOW_53);
                ruleTypesComputedPropertyName();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }

        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred8_InternalTypesParser

    // $ANTLR start synpred13_InternalTypesParser
    public final void synpred13_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:3458:4: ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )
        // InternalTypesParser.g:3458:5: ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign )
        {
        // InternalTypesParser.g:3458:5: ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign )
        // InternalTypesParser.g:3459:5: () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign
        {
        // InternalTypesParser.g:3459:5: ()
        // InternalTypesParser.g:3460:5: 
        {
        }

        match(input,LeftParenthesis,FOLLOW_76); if (state.failed) return ;
        pushFollow(FOLLOW_60);
        ruleTAnonymousFormalParameterList(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,RightParenthesis,FOLLOW_78); if (state.failed) return ;
        match(input,EqualsSignGreaterThanSign,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred13_InternalTypesParser

    // $ANTLR start synpred14_InternalTypesParser
    public final void synpred14_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:3541:4: ( ( QuestionMark ) )
        // InternalTypesParser.g:3541:5: ( QuestionMark )
        {
        // InternalTypesParser.g:3541:5: ( QuestionMark )
        // InternalTypesParser.g:3542:5: QuestionMark
        {
        match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred14_InternalTypesParser

    // $ANTLR start synpred15_InternalTypesParser
    public final void synpred15_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:3598:5: ( ( PlusSign ) )
        // InternalTypesParser.g:3598:6: ( PlusSign )
        {
        // InternalTypesParser.g:3598:6: ( PlusSign )
        // InternalTypesParser.g:3599:6: PlusSign
        {
        match(input,PlusSign,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred15_InternalTypesParser

    // $ANTLR start synpred16_InternalTypesParser
    public final void synpred16_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:3753:4: ( ( () QuestionMark ) )
        // InternalTypesParser.g:3753:5: ( () QuestionMark )
        {
        // InternalTypesParser.g:3753:5: ( () QuestionMark )
        // InternalTypesParser.g:3754:5: () QuestionMark
        {
        // InternalTypesParser.g:3754:5: ()
        // InternalTypesParser.g:3755:5: 
        {
        }

        match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred16_InternalTypesParser

    // $ANTLR start synpred18_InternalTypesParser
    public final void synpred18_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:4227:5: ( ( ( ( ruleBindingIdentifier ) ) Colon ) )
        // InternalTypesParser.g:4227:6: ( ( ( ruleBindingIdentifier ) ) Colon )
        {
        // InternalTypesParser.g:4227:6: ( ( ( ruleBindingIdentifier ) ) Colon )
        // InternalTypesParser.g:4228:6: ( ( ruleBindingIdentifier ) ) Colon
        {
        // InternalTypesParser.g:4228:6: ( ( ruleBindingIdentifier ) )
        // InternalTypesParser.g:4229:7: ( ruleBindingIdentifier )
        {
        // InternalTypesParser.g:4229:7: ( ruleBindingIdentifier )
        // InternalTypesParser.g:4230:8: ruleBindingIdentifier
        {
        pushFollow(FOLLOW_54);
        ruleBindingIdentifier();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        match(input,Colon,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred18_InternalTypesParser

    // $ANTLR start synpred20_InternalTypesParser
    public final void synpred20_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:4804:4: ( LessThanSign )
        // InternalTypesParser.g:4804:5: LessThanSign
        {
        match(input,LessThanSign,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_InternalTypesParser

    // $ANTLR start synpred21_InternalTypesParser
    public final void synpred21_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:4955:4: ( ( () Get ( ( ruleIdentifierName ) ) ) )
        // InternalTypesParser.g:4955:5: ( () Get ( ( ruleIdentifierName ) ) )
        {
        // InternalTypesParser.g:4955:5: ( () Get ( ( ruleIdentifierName ) ) )
        // InternalTypesParser.g:4956:5: () Get ( ( ruleIdentifierName ) )
        {
        // InternalTypesParser.g:4956:5: ()
        // InternalTypesParser.g:4957:5: 
        {
        }

        match(input,Get,FOLLOW_23); if (state.failed) return ;
        // InternalTypesParser.g:4959:5: ( ( ruleIdentifierName ) )
        // InternalTypesParser.g:4960:6: ( ruleIdentifierName )
        {
        // InternalTypesParser.g:4960:6: ( ruleIdentifierName )
        // InternalTypesParser.g:4961:7: ruleIdentifierName
        {
        pushFollow(FOLLOW_2);
        ruleIdentifierName();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred21_InternalTypesParser

    // $ANTLR start synpred22_InternalTypesParser
    public final void synpred22_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:4977:4: ( ( () Set ( ( ruleIdentifierName ) ) ) )
        // InternalTypesParser.g:4977:5: ( () Set ( ( ruleIdentifierName ) ) )
        {
        // InternalTypesParser.g:4977:5: ( () Set ( ( ruleIdentifierName ) ) )
        // InternalTypesParser.g:4978:5: () Set ( ( ruleIdentifierName ) )
        {
        // InternalTypesParser.g:4978:5: ()
        // InternalTypesParser.g:4979:5: 
        {
        }

        match(input,Set,FOLLOW_23); if (state.failed) return ;
        // InternalTypesParser.g:4981:5: ( ( ruleIdentifierName ) )
        // InternalTypesParser.g:4982:6: ( ruleIdentifierName )
        {
        // InternalTypesParser.g:4982:6: ( ruleIdentifierName )
        // InternalTypesParser.g:4983:7: ruleIdentifierName
        {
        pushFollow(FOLLOW_2);
        ruleIdentifierName();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred22_InternalTypesParser

    // $ANTLR start synpred23_InternalTypesParser
    public final void synpred23_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:4999:4: ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )
        // InternalTypesParser.g:4999:5: ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis )
        {
        // InternalTypesParser.g:4999:5: ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis )
        // InternalTypesParser.g:5000:5: () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis
        {
        // InternalTypesParser.g:5000:5: ()
        // InternalTypesParser.g:5001:5: 
        {
        }

        // InternalTypesParser.g:5002:5: ( ruleTypeVariables[null] )?
        int alt136=2;
        int LA136_0 = input.LA(1);

        if ( (LA136_0==LessThanSign) ) {
            alt136=1;
        }
        switch (alt136) {
            case 1 :
                // InternalTypesParser.g:5003:6: ruleTypeVariables[null]
                {
                pushFollow(FOLLOW_23);
                ruleTypeVariables(null);

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // InternalTypesParser.g:5005:5: ( ( ruleIdentifierName ) )
        // InternalTypesParser.g:5006:6: ( ruleIdentifierName )
        {
        // InternalTypesParser.g:5006:6: ( ruleIdentifierName )
        // InternalTypesParser.g:5007:7: ruleIdentifierName
        {
        pushFollow(FOLLOW_53);
        ruleIdentifierName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred23_InternalTypesParser

    // $ANTLR start synpred27_InternalTypesParser
    public final void synpred27_InternalTypesParser_fragment() throws RecognitionException {   
        // InternalTypesParser.g:5736:4: ( ( () QuestionMark ) )
        // InternalTypesParser.g:5736:5: ( () QuestionMark )
        {
        // InternalTypesParser.g:5736:5: ( () QuestionMark )
        // InternalTypesParser.g:5737:5: () QuestionMark
        {
        // InternalTypesParser.g:5737:5: ()
        // InternalTypesParser.g:5738:5: 
        {
        }

        match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred27_InternalTypesParser

    // Delegated rules

    public final boolean synpred23_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_InternalTypesParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_InternalTypesParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA72 dfa72 = new DFA72(this);
    protected DFA88 dfa88 = new DFA88(this);
    protected DFA96 dfa96 = new DFA96(this);
    protected DFA100 dfa100 = new DFA100(this);
    static final String dfa_1s = "\22\uffff";
    static final String dfa_2s = "\1\7\3\6\7\uffff\1\17\3\uffff\1\41\2\uffff";
    static final String dfa_3s = "\1\152\3\70\7\uffff\1\70\3\uffff\1\54\2\uffff";
    static final String dfa_4s = "\4\uffff\1\5\1\6\1\7\1\10\1\11\1\13\1\14\1\uffff\1\12\1\3\1\2\1\uffff\1\4\1\1";
    static final String dfa_5s = "\22\uffff}>";
    static final String[] dfa_6s = {
            "\1\2\3\uffff\1\12\4\uffff\1\10\1\uffff\1\6\12\uffff\1\1\4\uffff\1\3\27\uffff\1\7\3\uffff\1\5\2\uffff\1\4\50\uffff\1\11",
            "\1\13\10\uffff\1\15\3\uffff\1\16\3\uffff\1\14\11\uffff\1\21\12\uffff\1\16\2\uffff\1\17\10\uffff\1\20",
            "\1\13\10\uffff\1\15\3\uffff\1\16\3\uffff\1\14\11\uffff\1\21\12\uffff\1\16\2\uffff\1\17\10\uffff\1\20",
            "\1\13\10\uffff\1\15\3\uffff\1\16\3\uffff\1\14\11\uffff\1\21\12\uffff\1\16\2\uffff\1\17\10\uffff\1\20",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\15\3\uffff\1\16\3\uffff\1\14\11\uffff\1\21\12\uffff\1\16\2\uffff\1\17\10\uffff\1\20",
            "",
            "",
            "",
            "\1\21\12\uffff\1\16",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "335:2: (this_TObjectPrototype_0= ruleTObjectPrototype | this_TClass_1= ruleTClass | this_TInterface_2= ruleTInterface | this_TEnum_3= ruleTEnum | this_AnyType_4= ruleAnyType | this_VoidType_5= ruleVoidType | this_UndefinedType_6= ruleUndefinedType | this_NullType_7= ruleNullType | this_PrimitiveType_8= rulePrimitiveType | this_TFunction_9= ruleTFunction | this_TypeVariable_10= ruleTypeVariable | this_VirtualBaseType_11= ruleVirtualBaseType )";
        }
    }
    static final String dfa_7s = "\u01b8\uffff";
    static final String dfa_8s = "\1\5\10\4\1\uffff\54\121\2\4\3\121\1\4\22\121\1\4\2\uffff\3\121\u008e\uffff\1\4\55\uffff\1\4\30\uffff\106\125\1\137\1\4\1\121\106\137";
    static final String dfa_9s = "\1\42\10\152\1\uffff\54\134\2\152\3\134\1\152\22\134\1\152\2\uffff\3\134\u008e\uffff\1\152\55\uffff\1\152\30\uffff\106\125\1\137\1\152\1\134\106\137";
    static final String dfa_10s = "\11\uffff\1\4\105\uffff\2\3\3\uffff\107\1\107\2\1\uffff\55\3\1\uffff\30\3\u008f\uffff";
    static final String dfa_11s = "\1\uffff\1\6\1\7\1\11\1\12\1\13\1\15\1\4\1\16\1\uffff\1\17\1\20\1\22\1\24\1\26\1\30\1\32\1\37\1\21\1\23\1\25\1\27\1\33\1\34\1\35\1\36\1\40\1\42\1\44\1\46\1\50\1\53\1\55\1\57\1\61\1\63\1\66\1\70\1\72\1\74\1\76\1\100\1\103\1\105\1\107\1\111\1\113\1\115\1\117\1\0\1\1\1\2\1\3\1\5\1\64\1\51\1\41\1\43\1\45\1\10\1\54\1\56\1\60\1\62\1\65\1\67\1\71\1\73\1\75\1\77\1\101\1\104\1\106\1\110\1\112\1\114\1\116\1\120\3\uffff\1\31\1\47\1\52\u008e\uffff\1\121\55\uffff\1\102\140\uffff\1\14\106\uffff}>";
    static final String[] dfa_12s = {
            "\1\3\1\uffff\1\5\11\uffff\1\4\12\uffff\1\1\1\2\4\uffff\1\6",
            "\1\20\3\uffff\1\17\1\103\1\76\1\15\1\110\1\44\1\105\1\111\1\16\1\113\1\13\1\73\1\26\1\27\1\72\1\40\1\30\1\35\1\36\1\uffff\1\112\1\71\1\31\1\34\1\42\1\14\1\114\1\46\1\7\1\50\1\100\1\54\1\107\1\106\1\22\1\24\1\25\1\10\1\64\1\11\1\47\1\52\1\102\1\57\1\61\1\104\1\23\1\33\1\65\1\75\1\62\1\51\1\63\1\101\1\56\1\60\1\uffff\1\12\1\37\1\66\1\70\1\45\1\115\1\67\1\53\1\55\1\uffff\1\74\1\32\1\41\1\43\1\77\11\uffff\1\117\4\uffff\1\116\13\uffff\1\21",
            "\1\20\3\uffff\1\17\1\103\1\76\1\15\1\110\1\44\1\105\1\111\1\16\1\113\1\13\1\73\1\26\1\27\1\72\1\40\1\30\1\35\1\36\1\uffff\1\112\1\71\1\31\1\34\1\42\1\14\1\114\1\46\1\7\1\50\1\100\1\54\1\107\1\106\1\22\1\24\1\25\1\10\1\64\1\11\1\47\1\52\1\102\1\57\1\61\1\104\1\23\1\33\1\65\1\75\1\62\1\51\1\63\1\101\1\56\1\60\1\uffff\1\12\1\37\1\66\1\70\1\45\1\115\1\67\1\53\1\55\1\uffff\1\74\1\32\1\41\1\43\1\77\11\uffff\1\117\4\uffff\1\116\13\uffff\1\21",
            "\1\20\3\uffff\1\17\1\103\1\76\1\15\1\110\1\44\1\105\1\111\1\16\1\113\1\13\1\73\1\26\1\27\1\72\1\40\1\30\1\35\1\36\1\uffff\1\112\1\71\1\31\1\34\1\42\1\14\1\114\1\46\1\7\1\50\1\100\1\54\1\107\1\106\1\22\1\24\1\25\1\10\1\64\1\11\1\47\1\52\1\102\1\57\1\61\1\104\1\23\1\33\1\65\1\75\1\62\1\51\1\63\1\101\1\56\1\60\1\uffff\1\12\1\37\1\66\1\70\1\45\1\115\1\67\1\53\1\55\1\uffff\1\74\1\32\1\41\1\43\1\77\11\uffff\1\117\4\uffff\1\116\13\uffff\1\21",
            "\1\20\3\uffff\1\17\1\103\1\76\1\15\1\110\1\44\1\105\1\111\1\16\1\113\1\13\1\73\1\26\1\27\1\72\1\40\1\30\1\35\1\36\1\uffff\1\112\1\71\1\31\1\34\1\42\1\14\1\114\1\46\1\7\1\50\1\100\1\54\1\107\1\106\1\22\1\24\1\25\1\10\1\64\1\11\1\47\1\52\1\102\1\57\1\61\1\104\1\23\1\33\1\65\1\75\1\62\1\51\1\63\1\101\1\56\1\60\1\uffff\1\12\1\37\1\66\1\70\1\45\1\115\1\67\1\53\1\55\1\uffff\1\74\1\32\1\41\1\43\1\77\11\uffff\1\117\4\uffff\1\116\13\uffff\1\21",
            "\1\20\3\uffff\1\17\1\103\1\76\1\15\1\110\1\44\1\105\1\111\1\16\1\113\1\13\1\73\1\26\1\27\1\72\1\40\1\30\1\35\1\36\1\uffff\1\112\1\71\1\31\1\34\1\42\1\14\1\114\1\46\1\7\1\50\1\100\1\54\1\107\1\106\1\22\1\24\1\25\1\10\1\64\1\11\1\47\1\52\1\102\1\57\1\61\1\104\1\23\1\33\1\65\1\75\1\62\1\51\1\63\1\101\1\56\1\60\1\uffff\1\12\1\37\1\66\1\70\1\45\1\115\1\67\1\53\1\55\1\uffff\1\74\1\32\1\41\1\43\1\77\11\uffff\1\117\4\uffff\1\116\13\uffff\1\21",
            "\1\20\3\uffff\1\17\1\103\1\76\1\15\1\110\1\44\1\105\1\111\1\16\1\113\1\13\1\73\1\26\1\27\1\72\1\40\1\30\1\35\1\36\1\uffff\1\112\1\71\1\31\1\34\1\42\1\14\1\114\1\46\1\7\1\50\1\100\1\54\1\107\1\106\1\22\1\24\1\25\1\10\1\64\1\11\1\47\1\52\1\102\1\57\1\61\1\104\1\23\1\33\1\65\1\75\1\62\1\51\1\63\1\101\1\56\1\60\1\uffff\1\12\1\37\1\66\1\70\1\45\1\115\1\67\1\53\1\55\1\uffff\1\74\1\32\1\41\1\43\1\77\11\uffff\1\117\4\uffff\1\116\13\uffff\1\21",
            "\1\20\3\uffff\1\17\1\103\1\76\1\15\1\110\1\44\1\105\1\111\1\16\1\113\1\13\1\122\1\26\1\27\1\72\1\40\1\30\1\35\1\36\1\uffff\1\112\1\71\1\31\1\34\1\42\1\14\1\114\1\46\1\123\1\50\1\100\1\54\1\107\1\106\1\22\1\24\1\25\1\121\1\64\1\uffff\1\47\1\52\1\102\1\57\1\61\1\104\1\23\1\33\1\65\1\75\1\62\1\51\1\63\1\101\1\56\1\60\1\uffff\1\12\1\37\1\66\1\70\1\45\1\115\1\67\1\53\1\55\1\uffff\1\74\1\32\1\41\1\43\1\77\1\uffff\1\120\5\uffff\1\11\1\uffff\1\117\2\uffff\1\11\1\uffff\1\116\13\uffff\1\21",
            "\1\11\3\uffff\23\11\1\uffff\23\11\1\uffff\20\11\1\uffff\11\11\1\uffff\5\11\1\uffff\1\120\5\uffff\1\11\4\uffff\1\11\1\uffff\1\11\13\uffff\1\11",
            "",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\132\3\uffff\1\131\1\u008f\1\u008a\1\127\1\u0094\1\157\1\u0091\1\u0095\1\130\1\u0097\1\125\1\u0086\1\141\1\142\1\u0085\1\153\1\143\1\150\1\151\1\uffff\1\u0096\1\u0084\1\144\1\147\1\155\1\126\1\u0098\1\161\1\u0087\1\163\1\u008c\1\167\1\u0093\1\u0092\1\134\1\136\1\137\1\140\1\177\1\uffff\1\162\1\165\1\u008e\1\172\1\174\1\u0090\1\135\1\146\1\u0080\1\u0089\1\175\1\164\1\176\1\u008d\1\171\1\173\1\uffff\1\124\1\152\1\u0081\1\u0083\1\160\1\u0099\1\u0082\1\166\1\170\1\uffff\1\u0088\1\145\1\154\1\156\1\u008b\1\uffff\1\120\5\uffff\1\11\4\uffff\1\11\1\uffff\1\u009a\13\uffff\1\133",
            "\1\u00a1\3\uffff\1\u00a0\1\u00d6\1\u00d1\1\u009e\1\u00db\1\u00b6\1\u00d8\1\u00dc\1\u009f\1\u00de\1\u009c\1\u00cd\1\u00a8\1\u00a9\1\u00cc\1\u00b2\1\u00aa\1\u00af\1\u00b0\1\uffff\1\u00dd\1\u00cb\1\u00ab\1\u00ae\1\u00b4\1\u009d\1\u00df\1\u00b8\1\u00ce\1\u00ba\1\u00d3\1\u00be\1\u00da\1\u00d9\1\u00a3\1\u00a5\1\u00a6\1\u00a7\1\u00c6\1\uffff\1\u00b9\1\u00bc\1\u00d5\1\u00c1\1\u00c3\1\u00d7\1\u00a4\1\u00ad\1\u00c7\1\u00d0\1\u00c4\1\u00bb\1\u00c5\1\u00d4\1\u00c0\1\u00c2\1\uffff\1\u009b\1\u00b1\1\u00c8\1\u00ca\1\u00b7\1\u00e0\1\u00c9\1\u00bd\1\u00bf\1\uffff\1\u00cf\1\u00ac\1\u00b3\1\u00b5\1\u00d2\1\uffff\1\120\5\uffff\1\11\4\uffff\1\11\1\uffff\1\u00e1\13\uffff\1\u00a2",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\u00e9\3\uffff\1\u00e8\1\u011d\1\u0118\1\u00e6\1\u0122\1\u00fe\1\u011f\1\u0123\1\u00e7\1\u0125\1\u00e4\1\u0114\1\u00f0\1\u00f1\1\u0113\1\u00fa\1\u00f2\1\u00f7\1\u00f8\1\uffff\1\u0124\1\u0112\1\u00f3\1\u00f6\1\u00fc\1\u00e5\1\u0126\1\u0100\1\u0115\1\u0102\1\u011a\1\u0106\1\u0121\1\u0120\1\u00eb\1\u00ed\1\u00ee\1\u00ef\1\u010e\1\uffff\1\u0101\1\u0104\1\u011c\1\u0109\1\u010b\1\u011e\1\u00ec\1\u00f5\1\u010f\1\u0117\1\u010c\1\u0103\1\u010d\1\u011b\1\u0108\1\u010a\1\uffff\1\u00e3\1\u00f9\1\u00e2\1\u0111\1\u00ff\1\u0127\1\u0110\1\u0105\1\u0107\1\uffff\1\u0116\1\u00f4\1\u00fb\1\u00fd\1\u0119\1\uffff\1\120\5\uffff\1\11\1\uffff\1\117\2\uffff\1\11\1\uffff\1\u0128\13\uffff\1\u00ea",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\u012f\3\uffff\1\u012e\1\u0164\1\u015f\1\u012c\1\u0169\1\u0144\1\u0166\1\u016a\1\u012d\1\u016c\1\u012a\1\u015b\1\u0136\1\u0137\1\u015a\1\u0140\1\u0138\1\u013d\1\u013e\1\uffff\1\u016b\1\u0159\1\u0139\1\u013c\1\u0142\1\u012b\1\u016d\1\u0146\1\u015c\1\u0148\1\u0161\1\u014c\1\u0168\1\u0167\1\u0131\1\u0133\1\u0134\1\u0135\1\u0154\1\uffff\1\u0147\1\u014a\1\u0163\1\u014f\1\u0151\1\u0165\1\u0132\1\u013b\1\u0155\1\u015e\1\u0152\1\u0149\1\u0153\1\u0162\1\u014e\1\u0150\1\uffff\1\u0129\1\u013f\1\u0156\1\u0158\1\u0145\1\u016e\1\u0157\1\u014b\1\u014d\1\uffff\1\u015d\1\u013a\1\u0141\1\u0143\1\u0160\24\uffff\1\u016f\5\uffff\1\u0130",
            "",
            "",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\132\3\uffff\1\131\1\u008f\1\u008a\1\127\1\u0094\1\157\1\u0091\1\u0095\1\130\1\u0097\1\125\1\u0086\1\141\1\142\1\u0085\1\153\1\143\1\150\1\151\1\uffff\1\u0096\1\u0084\1\144\1\147\1\155\1\126\1\u0098\1\161\1\u0087\1\163\1\u008c\1\167\1\u0093\1\u0092\1\134\1\136\1\137\1\140\1\177\1\uffff\1\162\1\165\1\u008e\1\172\1\174\1\u0090\1\135\1\146\1\u0080\1\u0089\1\175\1\164\1\176\1\u008d\1\171\1\173\1\uffff\1\124\1\152\1\u0081\1\u0083\1\160\1\u0099\1\u0082\1\166\1\170\1\uffff\1\u0088\1\145\1\154\1\156\1\u008b\1\uffff\1\120\14\uffff\1\u009a\13\uffff\1\133",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00a1\3\uffff\1\u00a0\1\u00d6\1\u00d1\1\u009e\1\u00db\1\u00b6\1\u00d8\1\u00dc\1\u009f\1\u00de\1\u009c\1\u00cd\1\u00a8\1\u00a9\1\u00cc\1\u00b2\1\u00aa\1\u00af\1\u00b0\1\uffff\1\u00dd\1\u00cb\1\u00ab\1\u00ae\1\u00b4\1\u009d\1\u00df\1\u00b8\1\u00ce\1\u00ba\1\u00d3\1\u00be\1\u00da\1\u00d9\1\u00a3\1\u00a5\1\u00a6\1\u00a7\1\u00c6\1\uffff\1\u00b9\1\u00bc\1\u00d5\1\u00c1\1\u00c3\1\u00d7\1\u00a4\1\u00ad\1\u00c7\1\u00d0\1\u00c4\1\u00bb\1\u00c5\1\u00d4\1\u00c0\1\u00c2\1\uffff\1\u009b\1\u00b1\1\u00c8\1\u00ca\1\u00b7\1\u00e0\1\u00c9\1\u00bd\1\u00bf\1\uffff\1\u00cf\1\u00ac\1\u00b3\1\u00b5\1\u00d2\1\uffff\1\120\14\uffff\1\u00e1\13\uffff\1\u00a2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0170",
            "\1\u0171",
            "\1\u0178\3\uffff\1\u0177\1\u01ad\1\u01a8\1\u0175\1\u01b2\1\u018d\1\u01af\1\u01b3\1\u0176\1\u01b5\1\u0173\1\u01a4\1\u017f\1\u0180\1\u01a3\1\u0189\1\u0181\1\u0186\1\u0187\1\uffff\1\u01b4\1\u01a2\1\u0182\1\u0185\1\u018b\1\u0174\1\u01b6\1\u018f\1\u01a5\1\u0191\1\u01aa\1\u0195\1\u01b1\1\u01b0\1\u017a\1\u017c\1\u017d\1\u017e\1\u019d\1\uffff\1\u0190\1\u0193\1\u01ac\1\u0198\1\u019a\1\u01ae\1\u017b\1\u0184\1\u019e\1\u01a7\1\u019b\1\u0192\1\u019c\1\u01ab\1\u0197\1\u0199\1\uffff\1\u0172\1\u0188\1\u019f\1\u01a1\1\u018e\1\u01b7\1\u01a0\1\u0194\1\u0196\1\uffff\1\u01a6\1\u0183\1\u018a\1\u018c\1\u01a9\32\uffff\1\u0179",
            "\1\120\5\uffff\1\11\4\uffff\1\11",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171",
            "\1\u0171"
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[][] dfa_12 = unpackEncodedStringArray(dfa_12s);

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_7;
            this.eof = dfa_7;
            this.min = dfa_8;
            this.max = dfa_9;
            this.accept = dfa_10;
            this.special = dfa_11;
            this.transition = dfa_12;
        }
        public String getDescription() {
            return "2284:2: ( ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Get ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TGetter_0= ruleTGetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? Set ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) ) )=>this_TSetter_1= ruleTSetter ) | ( ( ( ( ( ruleMemberAccessModifier ) ) ( ( ( Abstract ) ) | ( ( Static ) ) )? ( ruleTypeVariables[null] )? ( ( ( ruleTypesIdentifier ) ) | ( ( ruleTypesComputedPropertyName ) ) ) LeftParenthesis ) )=>this_TMethod_2= ruleTMethod ) | this_TField_3= ruleTField )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA53_49 = input.LA(1);

                         
                        int index53_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_49==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_49==Colon||LA53_49==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_49);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA53_50 = input.LA(1);

                         
                        int index53_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_50==Colon||LA53_50==QuestionMark) ) {s = 9;}

                        else if ( (LA53_50==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_50);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA53_51 = input.LA(1);

                         
                        int index53_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_51==Colon||LA53_51==QuestionMark) ) {s = 9;}

                        else if ( (LA53_51==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_51);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA53_52 = input.LA(1);

                         
                        int index53_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_52==Colon||LA53_52==QuestionMark) ) {s = 9;}

                        else if ( (LA53_52==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_52);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA53_7 = input.LA(1);

                         
                        int index53_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_7==Colon||LA53_7==QuestionMark) ) {s = 9;}

                        else if ( (LA53_7==Set) ) {s = 55;}

                        else if ( (LA53_7==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_7==Get) ) {s = 54;}

                        else if ( (LA53_7==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                        else if ( (LA53_7==Any) ) {s = 10;}

                        else if ( (LA53_7==Undefined) ) {s = 11;}

                        else if ( (LA53_7==Object) ) {s = 12;}

                        else if ( (LA53_7==VirtualBase) ) {s = 13;}

                        else if ( (LA53_7==Primitive) ) {s = 14;}

                        else if ( (LA53_7==AutoboxedType) ) {s = 15;}

                        else if ( (LA53_7==AssignmnentCompatible) ) {s = 16;}

                        else if ( (LA53_7==RULE_IDENTIFIER) ) {s = 17;}

                        else if ( (LA53_7==Break) ) {s = 18;}

                        else if ( (LA53_7==Case) ) {s = 19;}

                        else if ( (LA53_7==Catch) ) {s = 20;}

                        else if ( (LA53_7==Class) ) {s = 21;}

                        else if ( (LA53_7==Const) ) {s = 81;}

                        else if ( (LA53_7==Continue) ) {s = 22;}

                        else if ( (LA53_7==Debugger) ) {s = 23;}

                        else if ( (LA53_7==Default) ) {s = 24;}

                        else if ( (LA53_7==Delete) ) {s = 25;}

                        else if ( (LA53_7==Do) ) {s = 26;}

                        else if ( (LA53_7==Else) ) {s = 27;}

                        else if ( (LA53_7==Export) ) {s = 28;}

                        else if ( (LA53_7==Extends) ) {s = 29;}

                        else if ( (LA53_7==Finally) ) {s = 30;}

                        else if ( (LA53_7==For) ) {s = 31;}

                        else if ( (LA53_7==Function) ) {s = 32;}

                        else if ( (LA53_7==If) ) {s = 33;}

                        else if ( (LA53_7==Import) ) {s = 34;}

                        else if ( (LA53_7==In) ) {s = 35;}

                        else if ( (LA53_7==Instanceof) ) {s = 36;}

                        else if ( (LA53_7==New) ) {s = 37;}

                        else if ( (LA53_7==Return) ) {s = 38;}

                        else if ( (LA53_7==Super) ) {s = 39;}

                        else if ( (LA53_7==Switch) ) {s = 40;}

                        else if ( (LA53_7==This_1) ) {s = 41;}

                        else if ( (LA53_7==Throw) ) {s = 42;}

                        else if ( (LA53_7==Try) ) {s = 43;}

                        else if ( (LA53_7==Typeof) ) {s = 44;}

                        else if ( (LA53_7==Var) ) {s = 45;}

                        else if ( (LA53_7==Void) ) {s = 46;}

                        else if ( (LA53_7==While) ) {s = 47;}

                        else if ( (LA53_7==With) ) {s = 48;}

                        else if ( (LA53_7==Yield) ) {s = 49;}

                        else if ( (LA53_7==Null) ) {s = 50;}

                        else if ( (LA53_7==True) ) {s = 51;}

                        else if ( (LA53_7==False) ) {s = 52;}

                        else if ( (LA53_7==Enum) ) {s = 53;}

                        else if ( (LA53_7==Let) ) {s = 56;}

                        else if ( (LA53_7==Project) ) {s = 57;}

                        else if ( (LA53_7==External) ) {s = 58;}

                        else if ( (LA53_7==Abstract) ) {s = 82;}

                        else if ( (LA53_7==Static) ) {s = 83;}

                        else if ( (LA53_7==As) ) {s = 60;}

                        else if ( (LA53_7==From) ) {s = 61;}

                        else if ( (LA53_7==Constructor) ) {s = 62;}

                        else if ( (LA53_7==Of) ) {s = 63;}

                        else if ( (LA53_7==Target) ) {s = 64;}

                        else if ( (LA53_7==Type) ) {s = 65;}

                        else if ( (LA53_7==Union) ) {s = 66;}

                        else if ( (LA53_7==Intersection) ) {s = 67;}

                        else if ( (LA53_7==This) ) {s = 68;}

                        else if ( (LA53_7==Promisify) ) {s = 69;}

                        else if ( (LA53_7==Await) ) {s = 70;}

                        else if ( (LA53_7==Async) ) {s = 71;}

                        else if ( (LA53_7==Implements) ) {s = 72;}

                        else if ( (LA53_7==Interface) ) {s = 73;}

                        else if ( (LA53_7==Private) ) {s = 74;}

                        else if ( (LA53_7==Protected) ) {s = 75;}

                        else if ( (LA53_7==Public) ) {s = 76;}

                        else if ( (LA53_7==Out) ) {s = 77;}

                        else if ( (LA53_7==LeftSquareBracket) ) {s = 78;}

                         
                        input.seek(index53_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA53_53 = input.LA(1);

                         
                        int index53_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_53==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_53==Colon||LA53_53==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_53);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA53_1 = input.LA(1);

                         
                        int index53_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_1==Static) ) {s = 7;}

                        else if ( (LA53_1==Const) ) {s = 8;}

                        else if ( (LA53_1==Final) ) {s = 9;}

                        else if ( (LA53_1==Any) ) {s = 10;}

                        else if ( (LA53_1==Undefined) ) {s = 11;}

                        else if ( (LA53_1==Object) ) {s = 12;}

                        else if ( (LA53_1==VirtualBase) ) {s = 13;}

                        else if ( (LA53_1==Primitive) ) {s = 14;}

                        else if ( (LA53_1==AutoboxedType) ) {s = 15;}

                        else if ( (LA53_1==AssignmnentCompatible) ) {s = 16;}

                        else if ( (LA53_1==RULE_IDENTIFIER) ) {s = 17;}

                        else if ( (LA53_1==Break) ) {s = 18;}

                        else if ( (LA53_1==Case) ) {s = 19;}

                        else if ( (LA53_1==Catch) ) {s = 20;}

                        else if ( (LA53_1==Class) ) {s = 21;}

                        else if ( (LA53_1==Continue) ) {s = 22;}

                        else if ( (LA53_1==Debugger) ) {s = 23;}

                        else if ( (LA53_1==Default) ) {s = 24;}

                        else if ( (LA53_1==Delete) ) {s = 25;}

                        else if ( (LA53_1==Do) ) {s = 26;}

                        else if ( (LA53_1==Else) ) {s = 27;}

                        else if ( (LA53_1==Export) ) {s = 28;}

                        else if ( (LA53_1==Extends) ) {s = 29;}

                        else if ( (LA53_1==Finally) ) {s = 30;}

                        else if ( (LA53_1==For) ) {s = 31;}

                        else if ( (LA53_1==Function) ) {s = 32;}

                        else if ( (LA53_1==If) ) {s = 33;}

                        else if ( (LA53_1==Import) ) {s = 34;}

                        else if ( (LA53_1==In) ) {s = 35;}

                        else if ( (LA53_1==Instanceof) ) {s = 36;}

                        else if ( (LA53_1==New) ) {s = 37;}

                        else if ( (LA53_1==Return) ) {s = 38;}

                        else if ( (LA53_1==Super) ) {s = 39;}

                        else if ( (LA53_1==Switch) ) {s = 40;}

                        else if ( (LA53_1==This_1) ) {s = 41;}

                        else if ( (LA53_1==Throw) ) {s = 42;}

                        else if ( (LA53_1==Try) ) {s = 43;}

                        else if ( (LA53_1==Typeof) ) {s = 44;}

                        else if ( (LA53_1==Var) ) {s = 45;}

                        else if ( (LA53_1==Void) ) {s = 46;}

                        else if ( (LA53_1==While) ) {s = 47;}

                        else if ( (LA53_1==With) ) {s = 48;}

                        else if ( (LA53_1==Yield) ) {s = 49;}

                        else if ( (LA53_1==Null) ) {s = 50;}

                        else if ( (LA53_1==True) ) {s = 51;}

                        else if ( (LA53_1==False) ) {s = 52;}

                        else if ( (LA53_1==Enum) ) {s = 53;}

                        else if ( (LA53_1==Get) ) {s = 54;}

                        else if ( (LA53_1==Set) ) {s = 55;}

                        else if ( (LA53_1==Let) ) {s = 56;}

                        else if ( (LA53_1==Project) ) {s = 57;}

                        else if ( (LA53_1==External) ) {s = 58;}

                        else if ( (LA53_1==Abstract) ) {s = 59;}

                        else if ( (LA53_1==As) ) {s = 60;}

                        else if ( (LA53_1==From) ) {s = 61;}

                        else if ( (LA53_1==Constructor) ) {s = 62;}

                        else if ( (LA53_1==Of) ) {s = 63;}

                        else if ( (LA53_1==Target) ) {s = 64;}

                        else if ( (LA53_1==Type) ) {s = 65;}

                        else if ( (LA53_1==Union) ) {s = 66;}

                        else if ( (LA53_1==Intersection) ) {s = 67;}

                        else if ( (LA53_1==This) ) {s = 68;}

                        else if ( (LA53_1==Promisify) ) {s = 69;}

                        else if ( (LA53_1==Await) ) {s = 70;}

                        else if ( (LA53_1==Async) ) {s = 71;}

                        else if ( (LA53_1==Implements) ) {s = 72;}

                        else if ( (LA53_1==Interface) ) {s = 73;}

                        else if ( (LA53_1==Private) ) {s = 74;}

                        else if ( (LA53_1==Protected) ) {s = 75;}

                        else if ( (LA53_1==Public) ) {s = 76;}

                        else if ( (LA53_1==Out) ) {s = 77;}

                        else if ( (LA53_1==LeftSquareBracket) ) {s = 78;}

                        else if ( (LA53_1==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                         
                        input.seek(index53_1);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA53_2 = input.LA(1);

                         
                        int index53_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_2==Abstract) ) {s = 59;}

                        else if ( (LA53_2==Static) ) {s = 7;}

                        else if ( (LA53_2==Set) ) {s = 55;}

                        else if ( (LA53_2==Const) ) {s = 8;}

                        else if ( (LA53_2==Final) ) {s = 9;}

                        else if ( (LA53_2==Any) ) {s = 10;}

                        else if ( (LA53_2==Undefined) ) {s = 11;}

                        else if ( (LA53_2==Object) ) {s = 12;}

                        else if ( (LA53_2==VirtualBase) ) {s = 13;}

                        else if ( (LA53_2==Primitive) ) {s = 14;}

                        else if ( (LA53_2==AutoboxedType) ) {s = 15;}

                        else if ( (LA53_2==AssignmnentCompatible) ) {s = 16;}

                        else if ( (LA53_2==RULE_IDENTIFIER) ) {s = 17;}

                        else if ( (LA53_2==Break) ) {s = 18;}

                        else if ( (LA53_2==Case) ) {s = 19;}

                        else if ( (LA53_2==Catch) ) {s = 20;}

                        else if ( (LA53_2==Class) ) {s = 21;}

                        else if ( (LA53_2==Continue) ) {s = 22;}

                        else if ( (LA53_2==Debugger) ) {s = 23;}

                        else if ( (LA53_2==Default) ) {s = 24;}

                        else if ( (LA53_2==Delete) ) {s = 25;}

                        else if ( (LA53_2==Do) ) {s = 26;}

                        else if ( (LA53_2==Else) ) {s = 27;}

                        else if ( (LA53_2==Export) ) {s = 28;}

                        else if ( (LA53_2==Extends) ) {s = 29;}

                        else if ( (LA53_2==Finally) ) {s = 30;}

                        else if ( (LA53_2==For) ) {s = 31;}

                        else if ( (LA53_2==Function) ) {s = 32;}

                        else if ( (LA53_2==If) ) {s = 33;}

                        else if ( (LA53_2==Import) ) {s = 34;}

                        else if ( (LA53_2==In) ) {s = 35;}

                        else if ( (LA53_2==Instanceof) ) {s = 36;}

                        else if ( (LA53_2==New) ) {s = 37;}

                        else if ( (LA53_2==Return) ) {s = 38;}

                        else if ( (LA53_2==Super) ) {s = 39;}

                        else if ( (LA53_2==Switch) ) {s = 40;}

                        else if ( (LA53_2==This_1) ) {s = 41;}

                        else if ( (LA53_2==Throw) ) {s = 42;}

                        else if ( (LA53_2==Try) ) {s = 43;}

                        else if ( (LA53_2==Typeof) ) {s = 44;}

                        else if ( (LA53_2==Var) ) {s = 45;}

                        else if ( (LA53_2==Void) ) {s = 46;}

                        else if ( (LA53_2==While) ) {s = 47;}

                        else if ( (LA53_2==With) ) {s = 48;}

                        else if ( (LA53_2==Yield) ) {s = 49;}

                        else if ( (LA53_2==Null) ) {s = 50;}

                        else if ( (LA53_2==True) ) {s = 51;}

                        else if ( (LA53_2==False) ) {s = 52;}

                        else if ( (LA53_2==Enum) ) {s = 53;}

                        else if ( (LA53_2==Get) ) {s = 54;}

                        else if ( (LA53_2==Let) ) {s = 56;}

                        else if ( (LA53_2==Project) ) {s = 57;}

                        else if ( (LA53_2==External) ) {s = 58;}

                        else if ( (LA53_2==As) ) {s = 60;}

                        else if ( (LA53_2==From) ) {s = 61;}

                        else if ( (LA53_2==Constructor) ) {s = 62;}

                        else if ( (LA53_2==Of) ) {s = 63;}

                        else if ( (LA53_2==Target) ) {s = 64;}

                        else if ( (LA53_2==Type) ) {s = 65;}

                        else if ( (LA53_2==Union) ) {s = 66;}

                        else if ( (LA53_2==Intersection) ) {s = 67;}

                        else if ( (LA53_2==This) ) {s = 68;}

                        else if ( (LA53_2==Promisify) ) {s = 69;}

                        else if ( (LA53_2==Await) ) {s = 70;}

                        else if ( (LA53_2==Async) ) {s = 71;}

                        else if ( (LA53_2==Implements) ) {s = 72;}

                        else if ( (LA53_2==Interface) ) {s = 73;}

                        else if ( (LA53_2==Private) ) {s = 74;}

                        else if ( (LA53_2==Protected) ) {s = 75;}

                        else if ( (LA53_2==Public) ) {s = 76;}

                        else if ( (LA53_2==Out) ) {s = 77;}

                        else if ( (LA53_2==LeftSquareBracket) ) {s = 78;}

                        else if ( (LA53_2==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                         
                        input.seek(index53_2);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA53_59 = input.LA(1);

                         
                        int index53_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_59==Get) ) {s = 226;}

                        else if ( (LA53_59==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                        else if ( (LA53_59==Any) && (synpred8_InternalTypesParser())) {s = 227;}

                        else if ( (LA53_59==Undefined) && (synpred8_InternalTypesParser())) {s = 228;}

                        else if ( (LA53_59==Object) && (synpred8_InternalTypesParser())) {s = 229;}

                        else if ( (LA53_59==VirtualBase) && (synpred8_InternalTypesParser())) {s = 230;}

                        else if ( (LA53_59==Primitive) && (synpred8_InternalTypesParser())) {s = 231;}

                        else if ( (LA53_59==AutoboxedType) && (synpred8_InternalTypesParser())) {s = 232;}

                        else if ( (LA53_59==AssignmnentCompatible) && (synpred8_InternalTypesParser())) {s = 233;}

                        else if ( (LA53_59==RULE_IDENTIFIER) && (synpred8_InternalTypesParser())) {s = 234;}

                        else if ( (LA53_59==Break) && (synpred8_InternalTypesParser())) {s = 235;}

                        else if ( (LA53_59==Case) && (synpred8_InternalTypesParser())) {s = 236;}

                        else if ( (LA53_59==Catch) && (synpred8_InternalTypesParser())) {s = 237;}

                        else if ( (LA53_59==Class) && (synpred8_InternalTypesParser())) {s = 238;}

                        else if ( (LA53_59==Const) && (synpred8_InternalTypesParser())) {s = 239;}

                        else if ( (LA53_59==Continue) && (synpred8_InternalTypesParser())) {s = 240;}

                        else if ( (LA53_59==Debugger) && (synpred8_InternalTypesParser())) {s = 241;}

                        else if ( (LA53_59==Default) && (synpred8_InternalTypesParser())) {s = 242;}

                        else if ( (LA53_59==Delete) && (synpred8_InternalTypesParser())) {s = 243;}

                        else if ( (LA53_59==Do) && (synpred8_InternalTypesParser())) {s = 244;}

                        else if ( (LA53_59==Else) && (synpred8_InternalTypesParser())) {s = 245;}

                        else if ( (LA53_59==Export) && (synpred8_InternalTypesParser())) {s = 246;}

                        else if ( (LA53_59==Extends) && (synpred8_InternalTypesParser())) {s = 247;}

                        else if ( (LA53_59==Finally) && (synpred8_InternalTypesParser())) {s = 248;}

                        else if ( (LA53_59==For) && (synpred8_InternalTypesParser())) {s = 249;}

                        else if ( (LA53_59==Function) && (synpred8_InternalTypesParser())) {s = 250;}

                        else if ( (LA53_59==If) && (synpred8_InternalTypesParser())) {s = 251;}

                        else if ( (LA53_59==Import) && (synpred8_InternalTypesParser())) {s = 252;}

                        else if ( (LA53_59==In) && (synpred8_InternalTypesParser())) {s = 253;}

                        else if ( (LA53_59==Instanceof) && (synpred8_InternalTypesParser())) {s = 254;}

                        else if ( (LA53_59==New) && (synpred8_InternalTypesParser())) {s = 255;}

                        else if ( (LA53_59==Return) && (synpred8_InternalTypesParser())) {s = 256;}

                        else if ( (LA53_59==Super) && (synpred8_InternalTypesParser())) {s = 257;}

                        else if ( (LA53_59==Switch) && (synpred8_InternalTypesParser())) {s = 258;}

                        else if ( (LA53_59==This_1) && (synpred8_InternalTypesParser())) {s = 259;}

                        else if ( (LA53_59==Throw) && (synpred8_InternalTypesParser())) {s = 260;}

                        else if ( (LA53_59==Try) && (synpred8_InternalTypesParser())) {s = 261;}

                        else if ( (LA53_59==Typeof) && (synpred8_InternalTypesParser())) {s = 262;}

                        else if ( (LA53_59==Var) && (synpred8_InternalTypesParser())) {s = 263;}

                        else if ( (LA53_59==Void) && (synpred8_InternalTypesParser())) {s = 264;}

                        else if ( (LA53_59==While) && (synpred8_InternalTypesParser())) {s = 265;}

                        else if ( (LA53_59==With) && (synpred8_InternalTypesParser())) {s = 266;}

                        else if ( (LA53_59==Yield) && (synpred8_InternalTypesParser())) {s = 267;}

                        else if ( (LA53_59==Null) && (synpred8_InternalTypesParser())) {s = 268;}

                        else if ( (LA53_59==True) && (synpred8_InternalTypesParser())) {s = 269;}

                        else if ( (LA53_59==False) && (synpred8_InternalTypesParser())) {s = 270;}

                        else if ( (LA53_59==Enum) && (synpred8_InternalTypesParser())) {s = 271;}

                        else if ( (LA53_59==Set) ) {s = 272;}

                        else if ( (LA53_59==Let) && (synpred8_InternalTypesParser())) {s = 273;}

                        else if ( (LA53_59==Project) && (synpred8_InternalTypesParser())) {s = 274;}

                        else if ( (LA53_59==External) && (synpred8_InternalTypesParser())) {s = 275;}

                        else if ( (LA53_59==Abstract) && (synpred8_InternalTypesParser())) {s = 276;}

                        else if ( (LA53_59==Static) && (synpred8_InternalTypesParser())) {s = 277;}

                        else if ( (LA53_59==As) && (synpred8_InternalTypesParser())) {s = 278;}

                        else if ( (LA53_59==From) && (synpred8_InternalTypesParser())) {s = 279;}

                        else if ( (LA53_59==Constructor) && (synpred8_InternalTypesParser())) {s = 280;}

                        else if ( (LA53_59==Of) && (synpred8_InternalTypesParser())) {s = 281;}

                        else if ( (LA53_59==Target) && (synpred8_InternalTypesParser())) {s = 282;}

                        else if ( (LA53_59==Type) && (synpred8_InternalTypesParser())) {s = 283;}

                        else if ( (LA53_59==Union) && (synpred8_InternalTypesParser())) {s = 284;}

                        else if ( (LA53_59==Intersection) && (synpred8_InternalTypesParser())) {s = 285;}

                        else if ( (LA53_59==This) && (synpred8_InternalTypesParser())) {s = 286;}

                        else if ( (LA53_59==Promisify) && (synpred8_InternalTypesParser())) {s = 287;}

                        else if ( (LA53_59==Await) && (synpred8_InternalTypesParser())) {s = 288;}

                        else if ( (LA53_59==Async) && (synpred8_InternalTypesParser())) {s = 289;}

                        else if ( (LA53_59==Implements) && (synpred8_InternalTypesParser())) {s = 290;}

                        else if ( (LA53_59==Interface) && (synpred8_InternalTypesParser())) {s = 291;}

                        else if ( (LA53_59==Private) && (synpred8_InternalTypesParser())) {s = 292;}

                        else if ( (LA53_59==Protected) && (synpred8_InternalTypesParser())) {s = 293;}

                        else if ( (LA53_59==Public) && (synpred8_InternalTypesParser())) {s = 294;}

                        else if ( (LA53_59==Out) && (synpred8_InternalTypesParser())) {s = 295;}

                        else if ( (LA53_59==LeftSquareBracket) && (synpred8_InternalTypesParser())) {s = 296;}

                        else if ( (LA53_59==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_59==Colon||LA53_59==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_59);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA53_3 = input.LA(1);

                         
                        int index53_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_3==Abstract) ) {s = 59;}

                        else if ( (LA53_3==Static) ) {s = 7;}

                        else if ( (LA53_3==Get) ) {s = 54;}

                        else if ( (LA53_3==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                        else if ( (LA53_3==Any) ) {s = 10;}

                        else if ( (LA53_3==Undefined) ) {s = 11;}

                        else if ( (LA53_3==Object) ) {s = 12;}

                        else if ( (LA53_3==VirtualBase) ) {s = 13;}

                        else if ( (LA53_3==Primitive) ) {s = 14;}

                        else if ( (LA53_3==AutoboxedType) ) {s = 15;}

                        else if ( (LA53_3==AssignmnentCompatible) ) {s = 16;}

                        else if ( (LA53_3==RULE_IDENTIFIER) ) {s = 17;}

                        else if ( (LA53_3==Break) ) {s = 18;}

                        else if ( (LA53_3==Case) ) {s = 19;}

                        else if ( (LA53_3==Catch) ) {s = 20;}

                        else if ( (LA53_3==Class) ) {s = 21;}

                        else if ( (LA53_3==Const) ) {s = 8;}

                        else if ( (LA53_3==Continue) ) {s = 22;}

                        else if ( (LA53_3==Debugger) ) {s = 23;}

                        else if ( (LA53_3==Default) ) {s = 24;}

                        else if ( (LA53_3==Delete) ) {s = 25;}

                        else if ( (LA53_3==Do) ) {s = 26;}

                        else if ( (LA53_3==Else) ) {s = 27;}

                        else if ( (LA53_3==Export) ) {s = 28;}

                        else if ( (LA53_3==Extends) ) {s = 29;}

                        else if ( (LA53_3==Finally) ) {s = 30;}

                        else if ( (LA53_3==For) ) {s = 31;}

                        else if ( (LA53_3==Function) ) {s = 32;}

                        else if ( (LA53_3==If) ) {s = 33;}

                        else if ( (LA53_3==Import) ) {s = 34;}

                        else if ( (LA53_3==In) ) {s = 35;}

                        else if ( (LA53_3==Instanceof) ) {s = 36;}

                        else if ( (LA53_3==New) ) {s = 37;}

                        else if ( (LA53_3==Return) ) {s = 38;}

                        else if ( (LA53_3==Super) ) {s = 39;}

                        else if ( (LA53_3==Switch) ) {s = 40;}

                        else if ( (LA53_3==This_1) ) {s = 41;}

                        else if ( (LA53_3==Throw) ) {s = 42;}

                        else if ( (LA53_3==Try) ) {s = 43;}

                        else if ( (LA53_3==Typeof) ) {s = 44;}

                        else if ( (LA53_3==Var) ) {s = 45;}

                        else if ( (LA53_3==Void) ) {s = 46;}

                        else if ( (LA53_3==While) ) {s = 47;}

                        else if ( (LA53_3==With) ) {s = 48;}

                        else if ( (LA53_3==Yield) ) {s = 49;}

                        else if ( (LA53_3==Null) ) {s = 50;}

                        else if ( (LA53_3==True) ) {s = 51;}

                        else if ( (LA53_3==False) ) {s = 52;}

                        else if ( (LA53_3==Enum) ) {s = 53;}

                        else if ( (LA53_3==Set) ) {s = 55;}

                        else if ( (LA53_3==Let) ) {s = 56;}

                        else if ( (LA53_3==Project) ) {s = 57;}

                        else if ( (LA53_3==External) ) {s = 58;}

                        else if ( (LA53_3==As) ) {s = 60;}

                        else if ( (LA53_3==From) ) {s = 61;}

                        else if ( (LA53_3==Constructor) ) {s = 62;}

                        else if ( (LA53_3==Of) ) {s = 63;}

                        else if ( (LA53_3==Target) ) {s = 64;}

                        else if ( (LA53_3==Type) ) {s = 65;}

                        else if ( (LA53_3==Union) ) {s = 66;}

                        else if ( (LA53_3==Intersection) ) {s = 67;}

                        else if ( (LA53_3==This) ) {s = 68;}

                        else if ( (LA53_3==Promisify) ) {s = 69;}

                        else if ( (LA53_3==Await) ) {s = 70;}

                        else if ( (LA53_3==Async) ) {s = 71;}

                        else if ( (LA53_3==Implements) ) {s = 72;}

                        else if ( (LA53_3==Interface) ) {s = 73;}

                        else if ( (LA53_3==Private) ) {s = 74;}

                        else if ( (LA53_3==Protected) ) {s = 75;}

                        else if ( (LA53_3==Public) ) {s = 76;}

                        else if ( (LA53_3==Out) ) {s = 77;}

                        else if ( (LA53_3==LeftSquareBracket) ) {s = 78;}

                        else if ( (LA53_3==Final) ) {s = 9;}

                         
                        input.seek(index53_3);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA53_4 = input.LA(1);

                         
                        int index53_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_4==Static) ) {s = 7;}

                        else if ( (LA53_4==Const) ) {s = 8;}

                        else if ( (LA53_4==Final) ) {s = 9;}

                        else if ( (LA53_4==Any) ) {s = 10;}

                        else if ( (LA53_4==Undefined) ) {s = 11;}

                        else if ( (LA53_4==Object) ) {s = 12;}

                        else if ( (LA53_4==VirtualBase) ) {s = 13;}

                        else if ( (LA53_4==Primitive) ) {s = 14;}

                        else if ( (LA53_4==AutoboxedType) ) {s = 15;}

                        else if ( (LA53_4==AssignmnentCompatible) ) {s = 16;}

                        else if ( (LA53_4==RULE_IDENTIFIER) ) {s = 17;}

                        else if ( (LA53_4==Break) ) {s = 18;}

                        else if ( (LA53_4==Case) ) {s = 19;}

                        else if ( (LA53_4==Catch) ) {s = 20;}

                        else if ( (LA53_4==Class) ) {s = 21;}

                        else if ( (LA53_4==Continue) ) {s = 22;}

                        else if ( (LA53_4==Debugger) ) {s = 23;}

                        else if ( (LA53_4==Default) ) {s = 24;}

                        else if ( (LA53_4==Delete) ) {s = 25;}

                        else if ( (LA53_4==Do) ) {s = 26;}

                        else if ( (LA53_4==Else) ) {s = 27;}

                        else if ( (LA53_4==Export) ) {s = 28;}

                        else if ( (LA53_4==Extends) ) {s = 29;}

                        else if ( (LA53_4==Finally) ) {s = 30;}

                        else if ( (LA53_4==For) ) {s = 31;}

                        else if ( (LA53_4==Function) ) {s = 32;}

                        else if ( (LA53_4==If) ) {s = 33;}

                        else if ( (LA53_4==Import) ) {s = 34;}

                        else if ( (LA53_4==In) ) {s = 35;}

                        else if ( (LA53_4==Instanceof) ) {s = 36;}

                        else if ( (LA53_4==New) ) {s = 37;}

                        else if ( (LA53_4==Return) ) {s = 38;}

                        else if ( (LA53_4==Super) ) {s = 39;}

                        else if ( (LA53_4==Switch) ) {s = 40;}

                        else if ( (LA53_4==This_1) ) {s = 41;}

                        else if ( (LA53_4==Throw) ) {s = 42;}

                        else if ( (LA53_4==Try) ) {s = 43;}

                        else if ( (LA53_4==Typeof) ) {s = 44;}

                        else if ( (LA53_4==Var) ) {s = 45;}

                        else if ( (LA53_4==Void) ) {s = 46;}

                        else if ( (LA53_4==While) ) {s = 47;}

                        else if ( (LA53_4==With) ) {s = 48;}

                        else if ( (LA53_4==Yield) ) {s = 49;}

                        else if ( (LA53_4==Null) ) {s = 50;}

                        else if ( (LA53_4==True) ) {s = 51;}

                        else if ( (LA53_4==False) ) {s = 52;}

                        else if ( (LA53_4==Enum) ) {s = 53;}

                        else if ( (LA53_4==Get) ) {s = 54;}

                        else if ( (LA53_4==Set) ) {s = 55;}

                        else if ( (LA53_4==Let) ) {s = 56;}

                        else if ( (LA53_4==Project) ) {s = 57;}

                        else if ( (LA53_4==External) ) {s = 58;}

                        else if ( (LA53_4==Abstract) ) {s = 59;}

                        else if ( (LA53_4==As) ) {s = 60;}

                        else if ( (LA53_4==From) ) {s = 61;}

                        else if ( (LA53_4==Constructor) ) {s = 62;}

                        else if ( (LA53_4==Of) ) {s = 63;}

                        else if ( (LA53_4==Target) ) {s = 64;}

                        else if ( (LA53_4==Type) ) {s = 65;}

                        else if ( (LA53_4==Union) ) {s = 66;}

                        else if ( (LA53_4==Intersection) ) {s = 67;}

                        else if ( (LA53_4==This) ) {s = 68;}

                        else if ( (LA53_4==Promisify) ) {s = 69;}

                        else if ( (LA53_4==Await) ) {s = 70;}

                        else if ( (LA53_4==Async) ) {s = 71;}

                        else if ( (LA53_4==Implements) ) {s = 72;}

                        else if ( (LA53_4==Interface) ) {s = 73;}

                        else if ( (LA53_4==Private) ) {s = 74;}

                        else if ( (LA53_4==Protected) ) {s = 75;}

                        else if ( (LA53_4==Public) ) {s = 76;}

                        else if ( (LA53_4==Out) ) {s = 77;}

                        else if ( (LA53_4==LeftSquareBracket) ) {s = 78;}

                        else if ( (LA53_4==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                         
                        input.seek(index53_4);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA53_5 = input.LA(1);

                         
                        int index53_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_5==Abstract) ) {s = 59;}

                        else if ( (LA53_5==Static) ) {s = 7;}

                        else if ( (LA53_5==Set) ) {s = 55;}

                        else if ( (LA53_5==Const) ) {s = 8;}

                        else if ( (LA53_5==Final) ) {s = 9;}

                        else if ( (LA53_5==Any) ) {s = 10;}

                        else if ( (LA53_5==Undefined) ) {s = 11;}

                        else if ( (LA53_5==Object) ) {s = 12;}

                        else if ( (LA53_5==VirtualBase) ) {s = 13;}

                        else if ( (LA53_5==Primitive) ) {s = 14;}

                        else if ( (LA53_5==AutoboxedType) ) {s = 15;}

                        else if ( (LA53_5==AssignmnentCompatible) ) {s = 16;}

                        else if ( (LA53_5==RULE_IDENTIFIER) ) {s = 17;}

                        else if ( (LA53_5==Break) ) {s = 18;}

                        else if ( (LA53_5==Case) ) {s = 19;}

                        else if ( (LA53_5==Catch) ) {s = 20;}

                        else if ( (LA53_5==Class) ) {s = 21;}

                        else if ( (LA53_5==Continue) ) {s = 22;}

                        else if ( (LA53_5==Debugger) ) {s = 23;}

                        else if ( (LA53_5==Default) ) {s = 24;}

                        else if ( (LA53_5==Delete) ) {s = 25;}

                        else if ( (LA53_5==Do) ) {s = 26;}

                        else if ( (LA53_5==Else) ) {s = 27;}

                        else if ( (LA53_5==Export) ) {s = 28;}

                        else if ( (LA53_5==Extends) ) {s = 29;}

                        else if ( (LA53_5==Finally) ) {s = 30;}

                        else if ( (LA53_5==For) ) {s = 31;}

                        else if ( (LA53_5==Function) ) {s = 32;}

                        else if ( (LA53_5==If) ) {s = 33;}

                        else if ( (LA53_5==Import) ) {s = 34;}

                        else if ( (LA53_5==In) ) {s = 35;}

                        else if ( (LA53_5==Instanceof) ) {s = 36;}

                        else if ( (LA53_5==New) ) {s = 37;}

                        else if ( (LA53_5==Return) ) {s = 38;}

                        else if ( (LA53_5==Super) ) {s = 39;}

                        else if ( (LA53_5==Switch) ) {s = 40;}

                        else if ( (LA53_5==This_1) ) {s = 41;}

                        else if ( (LA53_5==Throw) ) {s = 42;}

                        else if ( (LA53_5==Try) ) {s = 43;}

                        else if ( (LA53_5==Typeof) ) {s = 44;}

                        else if ( (LA53_5==Var) ) {s = 45;}

                        else if ( (LA53_5==Void) ) {s = 46;}

                        else if ( (LA53_5==While) ) {s = 47;}

                        else if ( (LA53_5==With) ) {s = 48;}

                        else if ( (LA53_5==Yield) ) {s = 49;}

                        else if ( (LA53_5==Null) ) {s = 50;}

                        else if ( (LA53_5==True) ) {s = 51;}

                        else if ( (LA53_5==False) ) {s = 52;}

                        else if ( (LA53_5==Enum) ) {s = 53;}

                        else if ( (LA53_5==Get) ) {s = 54;}

                        else if ( (LA53_5==Let) ) {s = 56;}

                        else if ( (LA53_5==Project) ) {s = 57;}

                        else if ( (LA53_5==External) ) {s = 58;}

                        else if ( (LA53_5==As) ) {s = 60;}

                        else if ( (LA53_5==From) ) {s = 61;}

                        else if ( (LA53_5==Constructor) ) {s = 62;}

                        else if ( (LA53_5==Of) ) {s = 63;}

                        else if ( (LA53_5==Target) ) {s = 64;}

                        else if ( (LA53_5==Type) ) {s = 65;}

                        else if ( (LA53_5==Union) ) {s = 66;}

                        else if ( (LA53_5==Intersection) ) {s = 67;}

                        else if ( (LA53_5==This) ) {s = 68;}

                        else if ( (LA53_5==Promisify) ) {s = 69;}

                        else if ( (LA53_5==Await) ) {s = 70;}

                        else if ( (LA53_5==Async) ) {s = 71;}

                        else if ( (LA53_5==Implements) ) {s = 72;}

                        else if ( (LA53_5==Interface) ) {s = 73;}

                        else if ( (LA53_5==Private) ) {s = 74;}

                        else if ( (LA53_5==Protected) ) {s = 75;}

                        else if ( (LA53_5==Public) ) {s = 76;}

                        else if ( (LA53_5==Out) ) {s = 77;}

                        else if ( (LA53_5==LeftSquareBracket) ) {s = 78;}

                        else if ( (LA53_5==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                         
                        input.seek(index53_5);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA53_369 = input.LA(1);

                         
                        int index53_369 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_369==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_369==Colon||LA53_369==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_369);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA53_6 = input.LA(1);

                         
                        int index53_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_6==Abstract) ) {s = 59;}

                        else if ( (LA53_6==Static) ) {s = 7;}

                        else if ( (LA53_6==LessThanSign) && (synpred8_InternalTypesParser())) {s = 79;}

                        else if ( (LA53_6==Any) ) {s = 10;}

                        else if ( (LA53_6==Undefined) ) {s = 11;}

                        else if ( (LA53_6==Object) ) {s = 12;}

                        else if ( (LA53_6==VirtualBase) ) {s = 13;}

                        else if ( (LA53_6==Primitive) ) {s = 14;}

                        else if ( (LA53_6==AutoboxedType) ) {s = 15;}

                        else if ( (LA53_6==AssignmnentCompatible) ) {s = 16;}

                        else if ( (LA53_6==RULE_IDENTIFIER) ) {s = 17;}

                        else if ( (LA53_6==Break) ) {s = 18;}

                        else if ( (LA53_6==Case) ) {s = 19;}

                        else if ( (LA53_6==Catch) ) {s = 20;}

                        else if ( (LA53_6==Class) ) {s = 21;}

                        else if ( (LA53_6==Const) ) {s = 8;}

                        else if ( (LA53_6==Continue) ) {s = 22;}

                        else if ( (LA53_6==Debugger) ) {s = 23;}

                        else if ( (LA53_6==Default) ) {s = 24;}

                        else if ( (LA53_6==Delete) ) {s = 25;}

                        else if ( (LA53_6==Do) ) {s = 26;}

                        else if ( (LA53_6==Else) ) {s = 27;}

                        else if ( (LA53_6==Export) ) {s = 28;}

                        else if ( (LA53_6==Extends) ) {s = 29;}

                        else if ( (LA53_6==Finally) ) {s = 30;}

                        else if ( (LA53_6==For) ) {s = 31;}

                        else if ( (LA53_6==Function) ) {s = 32;}

                        else if ( (LA53_6==If) ) {s = 33;}

                        else if ( (LA53_6==Import) ) {s = 34;}

                        else if ( (LA53_6==In) ) {s = 35;}

                        else if ( (LA53_6==Instanceof) ) {s = 36;}

                        else if ( (LA53_6==New) ) {s = 37;}

                        else if ( (LA53_6==Return) ) {s = 38;}

                        else if ( (LA53_6==Super) ) {s = 39;}

                        else if ( (LA53_6==Switch) ) {s = 40;}

                        else if ( (LA53_6==This_1) ) {s = 41;}

                        else if ( (LA53_6==Throw) ) {s = 42;}

                        else if ( (LA53_6==Try) ) {s = 43;}

                        else if ( (LA53_6==Typeof) ) {s = 44;}

                        else if ( (LA53_6==Var) ) {s = 45;}

                        else if ( (LA53_6==Void) ) {s = 46;}

                        else if ( (LA53_6==While) ) {s = 47;}

                        else if ( (LA53_6==With) ) {s = 48;}

                        else if ( (LA53_6==Yield) ) {s = 49;}

                        else if ( (LA53_6==Null) ) {s = 50;}

                        else if ( (LA53_6==True) ) {s = 51;}

                        else if ( (LA53_6==False) ) {s = 52;}

                        else if ( (LA53_6==Enum) ) {s = 53;}

                        else if ( (LA53_6==Get) ) {s = 54;}

                        else if ( (LA53_6==Set) ) {s = 55;}

                        else if ( (LA53_6==Let) ) {s = 56;}

                        else if ( (LA53_6==Project) ) {s = 57;}

                        else if ( (LA53_6==External) ) {s = 58;}

                        else if ( (LA53_6==As) ) {s = 60;}

                        else if ( (LA53_6==From) ) {s = 61;}

                        else if ( (LA53_6==Constructor) ) {s = 62;}

                        else if ( (LA53_6==Of) ) {s = 63;}

                        else if ( (LA53_6==Target) ) {s = 64;}

                        else if ( (LA53_6==Type) ) {s = 65;}

                        else if ( (LA53_6==Union) ) {s = 66;}

                        else if ( (LA53_6==Intersection) ) {s = 67;}

                        else if ( (LA53_6==This) ) {s = 68;}

                        else if ( (LA53_6==Promisify) ) {s = 69;}

                        else if ( (LA53_6==Await) ) {s = 70;}

                        else if ( (LA53_6==Async) ) {s = 71;}

                        else if ( (LA53_6==Implements) ) {s = 72;}

                        else if ( (LA53_6==Interface) ) {s = 73;}

                        else if ( (LA53_6==Private) ) {s = 74;}

                        else if ( (LA53_6==Protected) ) {s = 75;}

                        else if ( (LA53_6==Public) ) {s = 76;}

                        else if ( (LA53_6==Out) ) {s = 77;}

                        else if ( (LA53_6==LeftSquareBracket) ) {s = 78;}

                        else if ( (LA53_6==Final) ) {s = 9;}

                         
                        input.seek(index53_6);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA53_8 = input.LA(1);

                         
                        int index53_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_8==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_8==AssignmnentCompatible||(LA53_8>=AutoboxedType && LA53_8<=Finally)||(LA53_8>=Private && LA53_8<=False)||(LA53_8>=Super && LA53_8<=With)||(LA53_8>=Any && LA53_8<=Var)||(LA53_8>=As && LA53_8<=Of)||LA53_8==Colon||LA53_8==QuestionMark||LA53_8==LeftSquareBracket||LA53_8==RULE_IDENTIFIER) ) {s = 9;}

                         
                        input.seek(index53_8);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA53_10 = input.LA(1);

                         
                        int index53_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_10==Colon||LA53_10==QuestionMark) ) {s = 9;}

                        else if ( (LA53_10==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_10);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA53_11 = input.LA(1);

                         
                        int index53_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_11==Colon||LA53_11==QuestionMark) ) {s = 9;}

                        else if ( (LA53_11==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_11);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA53_18 = input.LA(1);

                         
                        int index53_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_18==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_18==Colon||LA53_18==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_18);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA53_12 = input.LA(1);

                         
                        int index53_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_12==Colon||LA53_12==QuestionMark) ) {s = 9;}

                        else if ( (LA53_12==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_12);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA53_19 = input.LA(1);

                         
                        int index53_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_19==Colon||LA53_19==QuestionMark) ) {s = 9;}

                        else if ( (LA53_19==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA53_13 = input.LA(1);

                         
                        int index53_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_13==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_13==Colon||LA53_13==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_13);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA53_20 = input.LA(1);

                         
                        int index53_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_20==Colon||LA53_20==QuestionMark) ) {s = 9;}

                        else if ( (LA53_20==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_20);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA53_14 = input.LA(1);

                         
                        int index53_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_14==Colon||LA53_14==QuestionMark) ) {s = 9;}

                        else if ( (LA53_14==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_14);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA53_21 = input.LA(1);

                         
                        int index53_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_21==Colon||LA53_21==QuestionMark) ) {s = 9;}

                        else if ( (LA53_21==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_21);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA53_15 = input.LA(1);

                         
                        int index53_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_15==Colon||LA53_15==QuestionMark) ) {s = 9;}

                        else if ( (LA53_15==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_15);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA53_81 = input.LA(1);

                         
                        int index53_81 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_81==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_81==Colon||LA53_81==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_81);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA53_16 = input.LA(1);

                         
                        int index53_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_16==Colon||LA53_16==QuestionMark) ) {s = 9;}

                        else if ( (LA53_16==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_16);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA53_22 = input.LA(1);

                         
                        int index53_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_22==Colon||LA53_22==QuestionMark) ) {s = 9;}

                        else if ( (LA53_22==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_22);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA53_23 = input.LA(1);

                         
                        int index53_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_23==Colon||LA53_23==QuestionMark) ) {s = 9;}

                        else if ( (LA53_23==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_23);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA53_24 = input.LA(1);

                         
                        int index53_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_24==Colon||LA53_24==QuestionMark) ) {s = 9;}

                        else if ( (LA53_24==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_24);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA53_25 = input.LA(1);

                         
                        int index53_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_25==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_25==Colon||LA53_25==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_25);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA53_17 = input.LA(1);

                         
                        int index53_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_17==Colon||LA53_17==QuestionMark) ) {s = 9;}

                        else if ( (LA53_17==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_17);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA53_26 = input.LA(1);

                         
                        int index53_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_26==Colon||LA53_26==QuestionMark) ) {s = 9;}

                        else if ( (LA53_26==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_26);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA53_56 = input.LA(1);

                         
                        int index53_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_56==Colon||LA53_56==QuestionMark) ) {s = 9;}

                        else if ( (LA53_56==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_56);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA53_27 = input.LA(1);

                         
                        int index53_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_27==Colon||LA53_27==QuestionMark) ) {s = 9;}

                        else if ( (LA53_27==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_27);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA53_57 = input.LA(1);

                         
                        int index53_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_57==Colon||LA53_57==QuestionMark) ) {s = 9;}

                        else if ( (LA53_57==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_57);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA53_28 = input.LA(1);

                         
                        int index53_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_28==Colon||LA53_28==QuestionMark) ) {s = 9;}

                        else if ( (LA53_28==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_28);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA53_58 = input.LA(1);

                         
                        int index53_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_58==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_58==Colon||LA53_58==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_58);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA53_29 = input.LA(1);

                         
                        int index53_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_29==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_29==Colon||LA53_29==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_29);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA53_82 = input.LA(1);

                         
                        int index53_82 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_82==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_82==Colon||LA53_82==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_82);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA53_30 = input.LA(1);

                         
                        int index53_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_30==Colon||LA53_30==QuestionMark) ) {s = 9;}

                        else if ( (LA53_30==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_30);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA53_55 = input.LA(1);

                         
                        int index53_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_55==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_55==Colon||LA53_55==QuestionMark) ) {s = 9;}

                        else if ( (LA53_55==Any) && (synpred7_InternalTypesParser())) {s = 155;}

                        else if ( (LA53_55==Undefined) && (synpred7_InternalTypesParser())) {s = 156;}

                        else if ( (LA53_55==Object) && (synpred7_InternalTypesParser())) {s = 157;}

                        else if ( (LA53_55==VirtualBase) && (synpred7_InternalTypesParser())) {s = 158;}

                        else if ( (LA53_55==Primitive) && (synpred7_InternalTypesParser())) {s = 159;}

                        else if ( (LA53_55==AutoboxedType) && (synpred7_InternalTypesParser())) {s = 160;}

                        else if ( (LA53_55==AssignmnentCompatible) && (synpred7_InternalTypesParser())) {s = 161;}

                        else if ( (LA53_55==RULE_IDENTIFIER) && (synpred7_InternalTypesParser())) {s = 162;}

                        else if ( (LA53_55==Break) && (synpred7_InternalTypesParser())) {s = 163;}

                        else if ( (LA53_55==Case) && (synpred7_InternalTypesParser())) {s = 164;}

                        else if ( (LA53_55==Catch) && (synpred7_InternalTypesParser())) {s = 165;}

                        else if ( (LA53_55==Class) && (synpred7_InternalTypesParser())) {s = 166;}

                        else if ( (LA53_55==Const) && (synpred7_InternalTypesParser())) {s = 167;}

                        else if ( (LA53_55==Continue) && (synpred7_InternalTypesParser())) {s = 168;}

                        else if ( (LA53_55==Debugger) && (synpred7_InternalTypesParser())) {s = 169;}

                        else if ( (LA53_55==Default) && (synpred7_InternalTypesParser())) {s = 170;}

                        else if ( (LA53_55==Delete) && (synpred7_InternalTypesParser())) {s = 171;}

                        else if ( (LA53_55==Do) && (synpred7_InternalTypesParser())) {s = 172;}

                        else if ( (LA53_55==Else) && (synpred7_InternalTypesParser())) {s = 173;}

                        else if ( (LA53_55==Export) && (synpred7_InternalTypesParser())) {s = 174;}

                        else if ( (LA53_55==Extends) && (synpred7_InternalTypesParser())) {s = 175;}

                        else if ( (LA53_55==Finally) && (synpred7_InternalTypesParser())) {s = 176;}

                        else if ( (LA53_55==For) && (synpred7_InternalTypesParser())) {s = 177;}

                        else if ( (LA53_55==Function) && (synpred7_InternalTypesParser())) {s = 178;}

                        else if ( (LA53_55==If) && (synpred7_InternalTypesParser())) {s = 179;}

                        else if ( (LA53_55==Import) && (synpred7_InternalTypesParser())) {s = 180;}

                        else if ( (LA53_55==In) && (synpred7_InternalTypesParser())) {s = 181;}

                        else if ( (LA53_55==Instanceof) && (synpred7_InternalTypesParser())) {s = 182;}

                        else if ( (LA53_55==New) && (synpred7_InternalTypesParser())) {s = 183;}

                        else if ( (LA53_55==Return) && (synpred7_InternalTypesParser())) {s = 184;}

                        else if ( (LA53_55==Super) && (synpred7_InternalTypesParser())) {s = 185;}

                        else if ( (LA53_55==Switch) && (synpred7_InternalTypesParser())) {s = 186;}

                        else if ( (LA53_55==This_1) && (synpred7_InternalTypesParser())) {s = 187;}

                        else if ( (LA53_55==Throw) && (synpred7_InternalTypesParser())) {s = 188;}

                        else if ( (LA53_55==Try) && (synpred7_InternalTypesParser())) {s = 189;}

                        else if ( (LA53_55==Typeof) && (synpred7_InternalTypesParser())) {s = 190;}

                        else if ( (LA53_55==Var) && (synpred7_InternalTypesParser())) {s = 191;}

                        else if ( (LA53_55==Void) && (synpred7_InternalTypesParser())) {s = 192;}

                        else if ( (LA53_55==While) && (synpred7_InternalTypesParser())) {s = 193;}

                        else if ( (LA53_55==With) && (synpred7_InternalTypesParser())) {s = 194;}

                        else if ( (LA53_55==Yield) && (synpred7_InternalTypesParser())) {s = 195;}

                        else if ( (LA53_55==Null) && (synpred7_InternalTypesParser())) {s = 196;}

                        else if ( (LA53_55==True) && (synpred7_InternalTypesParser())) {s = 197;}

                        else if ( (LA53_55==False) && (synpred7_InternalTypesParser())) {s = 198;}

                        else if ( (LA53_55==Enum) && (synpred7_InternalTypesParser())) {s = 199;}

                        else if ( (LA53_55==Get) && (synpred7_InternalTypesParser())) {s = 200;}

                        else if ( (LA53_55==Set) && (synpred7_InternalTypesParser())) {s = 201;}

                        else if ( (LA53_55==Let) && (synpred7_InternalTypesParser())) {s = 202;}

                        else if ( (LA53_55==Project) && (synpred7_InternalTypesParser())) {s = 203;}

                        else if ( (LA53_55==External) && (synpred7_InternalTypesParser())) {s = 204;}

                        else if ( (LA53_55==Abstract) && (synpred7_InternalTypesParser())) {s = 205;}

                        else if ( (LA53_55==Static) && (synpred7_InternalTypesParser())) {s = 206;}

                        else if ( (LA53_55==As) && (synpred7_InternalTypesParser())) {s = 207;}

                        else if ( (LA53_55==From) && (synpred7_InternalTypesParser())) {s = 208;}

                        else if ( (LA53_55==Constructor) && (synpred7_InternalTypesParser())) {s = 209;}

                        else if ( (LA53_55==Of) && (synpred7_InternalTypesParser())) {s = 210;}

                        else if ( (LA53_55==Target) && (synpred7_InternalTypesParser())) {s = 211;}

                        else if ( (LA53_55==Type) && (synpred7_InternalTypesParser())) {s = 212;}

                        else if ( (LA53_55==Union) && (synpred7_InternalTypesParser())) {s = 213;}

                        else if ( (LA53_55==Intersection) && (synpred7_InternalTypesParser())) {s = 214;}

                        else if ( (LA53_55==This) && (synpred7_InternalTypesParser())) {s = 215;}

                        else if ( (LA53_55==Promisify) && (synpred7_InternalTypesParser())) {s = 216;}

                        else if ( (LA53_55==Await) && (synpred7_InternalTypesParser())) {s = 217;}

                        else if ( (LA53_55==Async) && (synpred7_InternalTypesParser())) {s = 218;}

                        else if ( (LA53_55==Implements) && (synpred7_InternalTypesParser())) {s = 219;}

                        else if ( (LA53_55==Interface) && (synpred7_InternalTypesParser())) {s = 220;}

                        else if ( (LA53_55==Private) && (synpred7_InternalTypesParser())) {s = 221;}

                        else if ( (LA53_55==Protected) && (synpred7_InternalTypesParser())) {s = 222;}

                        else if ( (LA53_55==Public) && (synpred7_InternalTypesParser())) {s = 223;}

                        else if ( (LA53_55==Out) && (synpred7_InternalTypesParser())) {s = 224;}

                        else if ( (LA53_55==LeftSquareBracket) && (synpred7_InternalTypesParser())) {s = 225;}

                         
                        input.seek(index53_55);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA53_83 = input.LA(1);

                         
                        int index53_83 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_83==Colon||LA53_83==QuestionMark) ) {s = 9;}

                        else if ( (LA53_83==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_83);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA53_31 = input.LA(1);

                         
                        int index53_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_31==Colon||LA53_31==QuestionMark) ) {s = 9;}

                        else if ( (LA53_31==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_31);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA53_60 = input.LA(1);

                         
                        int index53_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_60==Colon||LA53_60==QuestionMark) ) {s = 9;}

                        else if ( (LA53_60==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_60);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA53_32 = input.LA(1);

                         
                        int index53_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_32==Colon||LA53_32==QuestionMark) ) {s = 9;}

                        else if ( (LA53_32==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_32);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA53_61 = input.LA(1);

                         
                        int index53_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_61==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_61==Colon||LA53_61==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_61);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA53_33 = input.LA(1);

                         
                        int index53_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_33==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_33==Colon||LA53_33==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_33);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA53_62 = input.LA(1);

                         
                        int index53_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_62==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_62==Colon||LA53_62==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_62);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA53_34 = input.LA(1);

                         
                        int index53_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_34==Colon||LA53_34==QuestionMark) ) {s = 9;}

                        else if ( (LA53_34==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_34);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA53_63 = input.LA(1);

                         
                        int index53_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_63==Colon||LA53_63==QuestionMark) ) {s = 9;}

                        else if ( (LA53_63==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_63);
                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA53_35 = input.LA(1);

                         
                        int index53_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_35==Colon||LA53_35==QuestionMark) ) {s = 9;}

                        else if ( (LA53_35==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_35);
                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA53_54 = input.LA(1);

                         
                        int index53_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_54==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_54==Any) && (synpred6_InternalTypesParser())) {s = 84;}

                        else if ( (LA53_54==Undefined) && (synpred6_InternalTypesParser())) {s = 85;}

                        else if ( (LA53_54==Object) && (synpred6_InternalTypesParser())) {s = 86;}

                        else if ( (LA53_54==VirtualBase) && (synpred6_InternalTypesParser())) {s = 87;}

                        else if ( (LA53_54==Primitive) && (synpred6_InternalTypesParser())) {s = 88;}

                        else if ( (LA53_54==AutoboxedType) && (synpred6_InternalTypesParser())) {s = 89;}

                        else if ( (LA53_54==AssignmnentCompatible) && (synpred6_InternalTypesParser())) {s = 90;}

                        else if ( (LA53_54==RULE_IDENTIFIER) && (synpred6_InternalTypesParser())) {s = 91;}

                        else if ( (LA53_54==Break) && (synpred6_InternalTypesParser())) {s = 92;}

                        else if ( (LA53_54==Case) && (synpred6_InternalTypesParser())) {s = 93;}

                        else if ( (LA53_54==Catch) && (synpred6_InternalTypesParser())) {s = 94;}

                        else if ( (LA53_54==Class) && (synpred6_InternalTypesParser())) {s = 95;}

                        else if ( (LA53_54==Const) && (synpred6_InternalTypesParser())) {s = 96;}

                        else if ( (LA53_54==Continue) && (synpred6_InternalTypesParser())) {s = 97;}

                        else if ( (LA53_54==Debugger) && (synpred6_InternalTypesParser())) {s = 98;}

                        else if ( (LA53_54==Default) && (synpred6_InternalTypesParser())) {s = 99;}

                        else if ( (LA53_54==Delete) && (synpred6_InternalTypesParser())) {s = 100;}

                        else if ( (LA53_54==Do) && (synpred6_InternalTypesParser())) {s = 101;}

                        else if ( (LA53_54==Else) && (synpred6_InternalTypesParser())) {s = 102;}

                        else if ( (LA53_54==Export) && (synpred6_InternalTypesParser())) {s = 103;}

                        else if ( (LA53_54==Extends) && (synpred6_InternalTypesParser())) {s = 104;}

                        else if ( (LA53_54==Finally) && (synpred6_InternalTypesParser())) {s = 105;}

                        else if ( (LA53_54==For) && (synpred6_InternalTypesParser())) {s = 106;}

                        else if ( (LA53_54==Function) && (synpred6_InternalTypesParser())) {s = 107;}

                        else if ( (LA53_54==If) && (synpred6_InternalTypesParser())) {s = 108;}

                        else if ( (LA53_54==Import) && (synpred6_InternalTypesParser())) {s = 109;}

                        else if ( (LA53_54==In) && (synpred6_InternalTypesParser())) {s = 110;}

                        else if ( (LA53_54==Instanceof) && (synpred6_InternalTypesParser())) {s = 111;}

                        else if ( (LA53_54==New) && (synpred6_InternalTypesParser())) {s = 112;}

                        else if ( (LA53_54==Return) && (synpred6_InternalTypesParser())) {s = 113;}

                        else if ( (LA53_54==Super) && (synpred6_InternalTypesParser())) {s = 114;}

                        else if ( (LA53_54==Switch) && (synpred6_InternalTypesParser())) {s = 115;}

                        else if ( (LA53_54==This_1) && (synpred6_InternalTypesParser())) {s = 116;}

                        else if ( (LA53_54==Throw) && (synpred6_InternalTypesParser())) {s = 117;}

                        else if ( (LA53_54==Try) && (synpred6_InternalTypesParser())) {s = 118;}

                        else if ( (LA53_54==Typeof) && (synpred6_InternalTypesParser())) {s = 119;}

                        else if ( (LA53_54==Var) && (synpred6_InternalTypesParser())) {s = 120;}

                        else if ( (LA53_54==Void) && (synpred6_InternalTypesParser())) {s = 121;}

                        else if ( (LA53_54==While) && (synpred6_InternalTypesParser())) {s = 122;}

                        else if ( (LA53_54==With) && (synpred6_InternalTypesParser())) {s = 123;}

                        else if ( (LA53_54==Yield) && (synpred6_InternalTypesParser())) {s = 124;}

                        else if ( (LA53_54==Null) && (synpred6_InternalTypesParser())) {s = 125;}

                        else if ( (LA53_54==True) && (synpred6_InternalTypesParser())) {s = 126;}

                        else if ( (LA53_54==False) && (synpred6_InternalTypesParser())) {s = 127;}

                        else if ( (LA53_54==Enum) && (synpred6_InternalTypesParser())) {s = 128;}

                        else if ( (LA53_54==Get) && (synpred6_InternalTypesParser())) {s = 129;}

                        else if ( (LA53_54==Set) && (synpred6_InternalTypesParser())) {s = 130;}

                        else if ( (LA53_54==Let) && (synpred6_InternalTypesParser())) {s = 131;}

                        else if ( (LA53_54==Project) && (synpred6_InternalTypesParser())) {s = 132;}

                        else if ( (LA53_54==External) && (synpred6_InternalTypesParser())) {s = 133;}

                        else if ( (LA53_54==Abstract) && (synpred6_InternalTypesParser())) {s = 134;}

                        else if ( (LA53_54==Static) && (synpred6_InternalTypesParser())) {s = 135;}

                        else if ( (LA53_54==As) && (synpred6_InternalTypesParser())) {s = 136;}

                        else if ( (LA53_54==From) && (synpred6_InternalTypesParser())) {s = 137;}

                        else if ( (LA53_54==Constructor) && (synpred6_InternalTypesParser())) {s = 138;}

                        else if ( (LA53_54==Of) && (synpred6_InternalTypesParser())) {s = 139;}

                        else if ( (LA53_54==Target) && (synpred6_InternalTypesParser())) {s = 140;}

                        else if ( (LA53_54==Type) && (synpred6_InternalTypesParser())) {s = 141;}

                        else if ( (LA53_54==Union) && (synpred6_InternalTypesParser())) {s = 142;}

                        else if ( (LA53_54==Intersection) && (synpred6_InternalTypesParser())) {s = 143;}

                        else if ( (LA53_54==This) && (synpred6_InternalTypesParser())) {s = 144;}

                        else if ( (LA53_54==Promisify) && (synpred6_InternalTypesParser())) {s = 145;}

                        else if ( (LA53_54==Await) && (synpred6_InternalTypesParser())) {s = 146;}

                        else if ( (LA53_54==Async) && (synpred6_InternalTypesParser())) {s = 147;}

                        else if ( (LA53_54==Implements) && (synpred6_InternalTypesParser())) {s = 148;}

                        else if ( (LA53_54==Interface) && (synpred6_InternalTypesParser())) {s = 149;}

                        else if ( (LA53_54==Private) && (synpred6_InternalTypesParser())) {s = 150;}

                        else if ( (LA53_54==Protected) && (synpred6_InternalTypesParser())) {s = 151;}

                        else if ( (LA53_54==Public) && (synpred6_InternalTypesParser())) {s = 152;}

                        else if ( (LA53_54==Out) && (synpred6_InternalTypesParser())) {s = 153;}

                        else if ( (LA53_54==LeftSquareBracket) && (synpred6_InternalTypesParser())) {s = 154;}

                        else if ( (LA53_54==Colon||LA53_54==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_54);
                        if ( s>=0 ) return s;
                        break;
                    case 53 : 
                        int LA53_64 = input.LA(1);

                         
                        int index53_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_64==Colon||LA53_64==QuestionMark) ) {s = 9;}

                        else if ( (LA53_64==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_64);
                        if ( s>=0 ) return s;
                        break;
                    case 54 : 
                        int LA53_36 = input.LA(1);

                         
                        int index53_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_36==Colon||LA53_36==QuestionMark) ) {s = 9;}

                        else if ( (LA53_36==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_36);
                        if ( s>=0 ) return s;
                        break;
                    case 55 : 
                        int LA53_65 = input.LA(1);

                         
                        int index53_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_65==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_65==Colon||LA53_65==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_65);
                        if ( s>=0 ) return s;
                        break;
                    case 56 : 
                        int LA53_37 = input.LA(1);

                         
                        int index53_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_37==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_37==Colon||LA53_37==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_37);
                        if ( s>=0 ) return s;
                        break;
                    case 57 : 
                        int LA53_66 = input.LA(1);

                         
                        int index53_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_66==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_66==Colon||LA53_66==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_66);
                        if ( s>=0 ) return s;
                        break;
                    case 58 : 
                        int LA53_38 = input.LA(1);

                         
                        int index53_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_38==Colon||LA53_38==QuestionMark) ) {s = 9;}

                        else if ( (LA53_38==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_38);
                        if ( s>=0 ) return s;
                        break;
                    case 59 : 
                        int LA53_67 = input.LA(1);

                         
                        int index53_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_67==Colon||LA53_67==QuestionMark) ) {s = 9;}

                        else if ( (LA53_67==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_67);
                        if ( s>=0 ) return s;
                        break;
                    case 60 : 
                        int LA53_39 = input.LA(1);

                         
                        int index53_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_39==Colon||LA53_39==QuestionMark) ) {s = 9;}

                        else if ( (LA53_39==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_39);
                        if ( s>=0 ) return s;
                        break;
                    case 61 : 
                        int LA53_68 = input.LA(1);

                         
                        int index53_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_68==Colon||LA53_68==QuestionMark) ) {s = 9;}

                        else if ( (LA53_68==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_68);
                        if ( s>=0 ) return s;
                        break;
                    case 62 : 
                        int LA53_40 = input.LA(1);

                         
                        int index53_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_40==Colon||LA53_40==QuestionMark) ) {s = 9;}

                        else if ( (LA53_40==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_40);
                        if ( s>=0 ) return s;
                        break;
                    case 63 : 
                        int LA53_69 = input.LA(1);

                         
                        int index53_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_69==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_69==Colon||LA53_69==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_69);
                        if ( s>=0 ) return s;
                        break;
                    case 64 : 
                        int LA53_41 = input.LA(1);

                         
                        int index53_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_41==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_41==Colon||LA53_41==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_41);
                        if ( s>=0 ) return s;
                        break;
                    case 65 : 
                        int LA53_70 = input.LA(1);

                         
                        int index53_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_70==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_70==Colon||LA53_70==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_70);
                        if ( s>=0 ) return s;
                        break;
                    case 66 : 
                        int LA53_272 = input.LA(1);

                         
                        int index53_272 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_272==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_272==Any) && (synpred7_InternalTypesParser())) {s = 155;}

                        else if ( (LA53_272==Undefined) && (synpred7_InternalTypesParser())) {s = 156;}

                        else if ( (LA53_272==Object) && (synpred7_InternalTypesParser())) {s = 157;}

                        else if ( (LA53_272==VirtualBase) && (synpred7_InternalTypesParser())) {s = 158;}

                        else if ( (LA53_272==Primitive) && (synpred7_InternalTypesParser())) {s = 159;}

                        else if ( (LA53_272==AutoboxedType) && (synpred7_InternalTypesParser())) {s = 160;}

                        else if ( (LA53_272==AssignmnentCompatible) && (synpred7_InternalTypesParser())) {s = 161;}

                        else if ( (LA53_272==RULE_IDENTIFIER) && (synpred7_InternalTypesParser())) {s = 162;}

                        else if ( (LA53_272==Break) && (synpred7_InternalTypesParser())) {s = 163;}

                        else if ( (LA53_272==Case) && (synpred7_InternalTypesParser())) {s = 164;}

                        else if ( (LA53_272==Catch) && (synpred7_InternalTypesParser())) {s = 165;}

                        else if ( (LA53_272==Class) && (synpred7_InternalTypesParser())) {s = 166;}

                        else if ( (LA53_272==Const) && (synpred7_InternalTypesParser())) {s = 167;}

                        else if ( (LA53_272==Continue) && (synpred7_InternalTypesParser())) {s = 168;}

                        else if ( (LA53_272==Debugger) && (synpred7_InternalTypesParser())) {s = 169;}

                        else if ( (LA53_272==Default) && (synpred7_InternalTypesParser())) {s = 170;}

                        else if ( (LA53_272==Delete) && (synpred7_InternalTypesParser())) {s = 171;}

                        else if ( (LA53_272==Do) && (synpred7_InternalTypesParser())) {s = 172;}

                        else if ( (LA53_272==Else) && (synpred7_InternalTypesParser())) {s = 173;}

                        else if ( (LA53_272==Export) && (synpred7_InternalTypesParser())) {s = 174;}

                        else if ( (LA53_272==Extends) && (synpred7_InternalTypesParser())) {s = 175;}

                        else if ( (LA53_272==Finally) && (synpred7_InternalTypesParser())) {s = 176;}

                        else if ( (LA53_272==For) && (synpred7_InternalTypesParser())) {s = 177;}

                        else if ( (LA53_272==Function) && (synpred7_InternalTypesParser())) {s = 178;}

                        else if ( (LA53_272==If) && (synpred7_InternalTypesParser())) {s = 179;}

                        else if ( (LA53_272==Import) && (synpred7_InternalTypesParser())) {s = 180;}

                        else if ( (LA53_272==In) && (synpred7_InternalTypesParser())) {s = 181;}

                        else if ( (LA53_272==Instanceof) && (synpred7_InternalTypesParser())) {s = 182;}

                        else if ( (LA53_272==New) && (synpred7_InternalTypesParser())) {s = 183;}

                        else if ( (LA53_272==Return) && (synpred7_InternalTypesParser())) {s = 184;}

                        else if ( (LA53_272==Super) && (synpred7_InternalTypesParser())) {s = 185;}

                        else if ( (LA53_272==Switch) && (synpred7_InternalTypesParser())) {s = 186;}

                        else if ( (LA53_272==This_1) && (synpred7_InternalTypesParser())) {s = 187;}

                        else if ( (LA53_272==Throw) && (synpred7_InternalTypesParser())) {s = 188;}

                        else if ( (LA53_272==Try) && (synpred7_InternalTypesParser())) {s = 189;}

                        else if ( (LA53_272==Typeof) && (synpred7_InternalTypesParser())) {s = 190;}

                        else if ( (LA53_272==Var) && (synpred7_InternalTypesParser())) {s = 191;}

                        else if ( (LA53_272==Void) && (synpred7_InternalTypesParser())) {s = 192;}

                        else if ( (LA53_272==While) && (synpred7_InternalTypesParser())) {s = 193;}

                        else if ( (LA53_272==With) && (synpred7_InternalTypesParser())) {s = 194;}

                        else if ( (LA53_272==Yield) && (synpred7_InternalTypesParser())) {s = 195;}

                        else if ( (LA53_272==Null) && (synpred7_InternalTypesParser())) {s = 196;}

                        else if ( (LA53_272==True) && (synpred7_InternalTypesParser())) {s = 197;}

                        else if ( (LA53_272==False) && (synpred7_InternalTypesParser())) {s = 198;}

                        else if ( (LA53_272==Enum) && (synpred7_InternalTypesParser())) {s = 199;}

                        else if ( (LA53_272==Get) && (synpred7_InternalTypesParser())) {s = 200;}

                        else if ( (LA53_272==Set) && (synpred7_InternalTypesParser())) {s = 201;}

                        else if ( (LA53_272==Let) && (synpred7_InternalTypesParser())) {s = 202;}

                        else if ( (LA53_272==Project) && (synpred7_InternalTypesParser())) {s = 203;}

                        else if ( (LA53_272==External) && (synpred7_InternalTypesParser())) {s = 204;}

                        else if ( (LA53_272==Abstract) && (synpred7_InternalTypesParser())) {s = 205;}

                        else if ( (LA53_272==Static) && (synpred7_InternalTypesParser())) {s = 206;}

                        else if ( (LA53_272==As) && (synpred7_InternalTypesParser())) {s = 207;}

                        else if ( (LA53_272==From) && (synpred7_InternalTypesParser())) {s = 208;}

                        else if ( (LA53_272==Constructor) && (synpred7_InternalTypesParser())) {s = 209;}

                        else if ( (LA53_272==Of) && (synpred7_InternalTypesParser())) {s = 210;}

                        else if ( (LA53_272==Target) && (synpred7_InternalTypesParser())) {s = 211;}

                        else if ( (LA53_272==Type) && (synpred7_InternalTypesParser())) {s = 212;}

                        else if ( (LA53_272==Union) && (synpred7_InternalTypesParser())) {s = 213;}

                        else if ( (LA53_272==Intersection) && (synpred7_InternalTypesParser())) {s = 214;}

                        else if ( (LA53_272==This) && (synpred7_InternalTypesParser())) {s = 215;}

                        else if ( (LA53_272==Promisify) && (synpred7_InternalTypesParser())) {s = 216;}

                        else if ( (LA53_272==Await) && (synpred7_InternalTypesParser())) {s = 217;}

                        else if ( (LA53_272==Async) && (synpred7_InternalTypesParser())) {s = 218;}

                        else if ( (LA53_272==Implements) && (synpred7_InternalTypesParser())) {s = 219;}

                        else if ( (LA53_272==Interface) && (synpred7_InternalTypesParser())) {s = 220;}

                        else if ( (LA53_272==Private) && (synpred7_InternalTypesParser())) {s = 221;}

                        else if ( (LA53_272==Protected) && (synpred7_InternalTypesParser())) {s = 222;}

                        else if ( (LA53_272==Public) && (synpred7_InternalTypesParser())) {s = 223;}

                        else if ( (LA53_272==Out) && (synpred7_InternalTypesParser())) {s = 224;}

                        else if ( (LA53_272==LeftSquareBracket) && (synpred7_InternalTypesParser())) {s = 225;}

                         
                        input.seek(index53_272);
                        if ( s>=0 ) return s;
                        break;
                    case 67 : 
                        int LA53_42 = input.LA(1);

                         
                        int index53_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_42==Colon||LA53_42==QuestionMark) ) {s = 9;}

                        else if ( (LA53_42==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_42);
                        if ( s>=0 ) return s;
                        break;
                    case 68 : 
                        int LA53_71 = input.LA(1);

                         
                        int index53_71 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_71==Colon||LA53_71==QuestionMark) ) {s = 9;}

                        else if ( (LA53_71==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_71);
                        if ( s>=0 ) return s;
                        break;
                    case 69 : 
                        int LA53_43 = input.LA(1);

                         
                        int index53_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_43==Colon||LA53_43==QuestionMark) ) {s = 9;}

                        else if ( (LA53_43==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_43);
                        if ( s>=0 ) return s;
                        break;
                    case 70 : 
                        int LA53_72 = input.LA(1);

                         
                        int index53_72 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_72==Colon||LA53_72==QuestionMark) ) {s = 9;}

                        else if ( (LA53_72==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_72);
                        if ( s>=0 ) return s;
                        break;
                    case 71 : 
                        int LA53_44 = input.LA(1);

                         
                        int index53_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_44==Colon||LA53_44==QuestionMark) ) {s = 9;}

                        else if ( (LA53_44==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_44);
                        if ( s>=0 ) return s;
                        break;
                    case 72 : 
                        int LA53_73 = input.LA(1);

                         
                        int index53_73 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_73==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_73==Colon||LA53_73==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_73);
                        if ( s>=0 ) return s;
                        break;
                    case 73 : 
                        int LA53_45 = input.LA(1);

                         
                        int index53_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_45==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_45==Colon||LA53_45==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_45);
                        if ( s>=0 ) return s;
                        break;
                    case 74 : 
                        int LA53_74 = input.LA(1);

                         
                        int index53_74 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_74==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_74==Colon||LA53_74==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_74);
                        if ( s>=0 ) return s;
                        break;
                    case 75 : 
                        int LA53_46 = input.LA(1);

                         
                        int index53_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_46==Colon||LA53_46==QuestionMark) ) {s = 9;}

                        else if ( (LA53_46==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_46);
                        if ( s>=0 ) return s;
                        break;
                    case 76 : 
                        int LA53_75 = input.LA(1);

                         
                        int index53_75 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_75==Colon||LA53_75==QuestionMark) ) {s = 9;}

                        else if ( (LA53_75==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_75);
                        if ( s>=0 ) return s;
                        break;
                    case 77 : 
                        int LA53_47 = input.LA(1);

                         
                        int index53_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_47==Colon||LA53_47==QuestionMark) ) {s = 9;}

                        else if ( (LA53_47==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_47);
                        if ( s>=0 ) return s;
                        break;
                    case 78 : 
                        int LA53_76 = input.LA(1);

                         
                        int index53_76 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_76==Colon||LA53_76==QuestionMark) ) {s = 9;}

                        else if ( (LA53_76==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_76);
                        if ( s>=0 ) return s;
                        break;
                    case 79 : 
                        int LA53_48 = input.LA(1);

                         
                        int index53_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_48==Colon||LA53_48==QuestionMark) ) {s = 9;}

                        else if ( (LA53_48==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                         
                        input.seek(index53_48);
                        if ( s>=0 ) return s;
                        break;
                    case 80 : 
                        int LA53_77 = input.LA(1);

                         
                        int index53_77 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_77==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_77==Colon||LA53_77==QuestionMark) ) {s = 9;}

                         
                        input.seek(index53_77);
                        if ( s>=0 ) return s;
                        break;
                    case 81 : 
                        int LA53_226 = input.LA(1);

                         
                        int index53_226 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA53_226==LeftParenthesis) && (synpred8_InternalTypesParser())) {s = 80;}

                        else if ( (LA53_226==Any) && (synpred6_InternalTypesParser())) {s = 84;}

                        else if ( (LA53_226==Undefined) && (synpred6_InternalTypesParser())) {s = 85;}

                        else if ( (LA53_226==Object) && (synpred6_InternalTypesParser())) {s = 86;}

                        else if ( (LA53_226==VirtualBase) && (synpred6_InternalTypesParser())) {s = 87;}

                        else if ( (LA53_226==Primitive) && (synpred6_InternalTypesParser())) {s = 88;}

                        else if ( (LA53_226==AutoboxedType) && (synpred6_InternalTypesParser())) {s = 89;}

                        else if ( (LA53_226==AssignmnentCompatible) && (synpred6_InternalTypesParser())) {s = 90;}

                        else if ( (LA53_226==RULE_IDENTIFIER) && (synpred6_InternalTypesParser())) {s = 91;}

                        else if ( (LA53_226==Break) && (synpred6_InternalTypesParser())) {s = 92;}

                        else if ( (LA53_226==Case) && (synpred6_InternalTypesParser())) {s = 93;}

                        else if ( (LA53_226==Catch) && (synpred6_InternalTypesParser())) {s = 94;}

                        else if ( (LA53_226==Class) && (synpred6_InternalTypesParser())) {s = 95;}

                        else if ( (LA53_226==Const) && (synpred6_InternalTypesParser())) {s = 96;}

                        else if ( (LA53_226==Continue) && (synpred6_InternalTypesParser())) {s = 97;}

                        else if ( (LA53_226==Debugger) && (synpred6_InternalTypesParser())) {s = 98;}

                        else if ( (LA53_226==Default) && (synpred6_InternalTypesParser())) {s = 99;}

                        else if ( (LA53_226==Delete) && (synpred6_InternalTypesParser())) {s = 100;}

                        else if ( (LA53_226==Do) && (synpred6_InternalTypesParser())) {s = 101;}

                        else if ( (LA53_226==Else) && (synpred6_InternalTypesParser())) {s = 102;}

                        else if ( (LA53_226==Export) && (synpred6_InternalTypesParser())) {s = 103;}

                        else if ( (LA53_226==Extends) && (synpred6_InternalTypesParser())) {s = 104;}

                        else if ( (LA53_226==Finally) && (synpred6_InternalTypesParser())) {s = 105;}

                        else if ( (LA53_226==For) && (synpred6_InternalTypesParser())) {s = 106;}

                        else if ( (LA53_226==Function) && (synpred6_InternalTypesParser())) {s = 107;}

                        else if ( (LA53_226==If) && (synpred6_InternalTypesParser())) {s = 108;}

                        else if ( (LA53_226==Import) && (synpred6_InternalTypesParser())) {s = 109;}

                        else if ( (LA53_226==In) && (synpred6_InternalTypesParser())) {s = 110;}

                        else if ( (LA53_226==Instanceof) && (synpred6_InternalTypesParser())) {s = 111;}

                        else if ( (LA53_226==New) && (synpred6_InternalTypesParser())) {s = 112;}

                        else if ( (LA53_226==Return) && (synpred6_InternalTypesParser())) {s = 113;}

                        else if ( (LA53_226==Super) && (synpred6_InternalTypesParser())) {s = 114;}

                        else if ( (LA53_226==Switch) && (synpred6_InternalTypesParser())) {s = 115;}

                        else if ( (LA53_226==This_1) && (synpred6_InternalTypesParser())) {s = 116;}

                        else if ( (LA53_226==Throw) && (synpred6_InternalTypesParser())) {s = 117;}

                        else if ( (LA53_226==Try) && (synpred6_InternalTypesParser())) {s = 118;}

                        else if ( (LA53_226==Typeof) && (synpred6_InternalTypesParser())) {s = 119;}

                        else if ( (LA53_226==Var) && (synpred6_InternalTypesParser())) {s = 120;}

                        else if ( (LA53_226==Void) && (synpred6_InternalTypesParser())) {s = 121;}

                        else if ( (LA53_226==While) && (synpred6_InternalTypesParser())) {s = 122;}

                        else if ( (LA53_226==With) && (synpred6_InternalTypesParser())) {s = 123;}

                        else if ( (LA53_226==Yield) && (synpred6_InternalTypesParser())) {s = 124;}

                        else if ( (LA53_226==Null) && (synpred6_InternalTypesParser())) {s = 125;}

                        else if ( (LA53_226==True) && (synpred6_InternalTypesParser())) {s = 126;}

                        else if ( (LA53_226==False) && (synpred6_InternalTypesParser())) {s = 127;}

                        else if ( (LA53_226==Enum) && (synpred6_InternalTypesParser())) {s = 128;}

                        else if ( (LA53_226==Get) && (synpred6_InternalTypesParser())) {s = 129;}

                        else if ( (LA53_226==Set) && (synpred6_InternalTypesParser())) {s = 130;}

                        else if ( (LA53_226==Let) && (synpred6_InternalTypesParser())) {s = 131;}

                        else if ( (LA53_226==Project) && (synpred6_InternalTypesParser())) {s = 132;}

                        else if ( (LA53_226==External) && (synpred6_InternalTypesParser())) {s = 133;}

                        else if ( (LA53_226==Abstract) && (synpred6_InternalTypesParser())) {s = 134;}

                        else if ( (LA53_226==Static) && (synpred6_InternalTypesParser())) {s = 135;}

                        else if ( (LA53_226==As) && (synpred6_InternalTypesParser())) {s = 136;}

                        else if ( (LA53_226==From) && (synpred6_InternalTypesParser())) {s = 137;}

                        else if ( (LA53_226==Constructor) && (synpred6_InternalTypesParser())) {s = 138;}

                        else if ( (LA53_226==Of) && (synpred6_InternalTypesParser())) {s = 139;}

                        else if ( (LA53_226==Target) && (synpred6_InternalTypesParser())) {s = 140;}

                        else if ( (LA53_226==Type) && (synpred6_InternalTypesParser())) {s = 141;}

                        else if ( (LA53_226==Union) && (synpred6_InternalTypesParser())) {s = 142;}

                        else if ( (LA53_226==Intersection) && (synpred6_InternalTypesParser())) {s = 143;}

                        else if ( (LA53_226==This) && (synpred6_InternalTypesParser())) {s = 144;}

                        else if ( (LA53_226==Promisify) && (synpred6_InternalTypesParser())) {s = 145;}

                        else if ( (LA53_226==Await) && (synpred6_InternalTypesParser())) {s = 146;}

                        else if ( (LA53_226==Async) && (synpred6_InternalTypesParser())) {s = 147;}

                        else if ( (LA53_226==Implements) && (synpred6_InternalTypesParser())) {s = 148;}

                        else if ( (LA53_226==Interface) && (synpred6_InternalTypesParser())) {s = 149;}

                        else if ( (LA53_226==Private) && (synpred6_InternalTypesParser())) {s = 150;}

                        else if ( (LA53_226==Protected) && (synpred6_InternalTypesParser())) {s = 151;}

                        else if ( (LA53_226==Public) && (synpred6_InternalTypesParser())) {s = 152;}

                        else if ( (LA53_226==Out) && (synpred6_InternalTypesParser())) {s = 153;}

                        else if ( (LA53_226==LeftSquareBracket) && (synpred6_InternalTypesParser())) {s = 154;}

                         
                        input.seek(index53_226);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 53, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_13s = "\1\11\1\0\20\uffff";
    static final String dfa_14s = "\1\152\1\0\20\uffff";
    static final String dfa_15s = "\2\uffff\1\2\1\3\14\uffff\1\1\1\4";
    static final String dfa_16s = "\1\uffff\1\0\20\uffff}>";
    static final String[] dfa_17s = {
            "\2\3\7\uffff\1\3\10\uffff\1\3\26\uffff\1\3\7\uffff\2\3\1\uffff\2\3\2\uffff\1\3\17\uffff\1\1\14\uffff\1\2\1\uffff\1\3\1\uffff\1\3\7\uffff\1\3",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
    static final char[] dfa_13 = DFA.unpackEncodedStringToUnsignedChars(dfa_13s);
    static final char[] dfa_14 = DFA.unpackEncodedStringToUnsignedChars(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final short[][] dfa_17 = unpackEncodedStringArray(dfa_17s);

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_13;
            this.max = dfa_14;
            this.accept = dfa_15;
            this.special = dfa_16;
            this.transition = dfa_17;
        }
        public String getDescription() {
            return "3456:2: ( ( ( ( () LeftParenthesis ruleTAnonymousFormalParameterList[null] RightParenthesis EqualsSignGreaterThanSign ) )=>this_ArrowFunctionTypeExpression_0= ruleArrowFunctionTypeExpression ) | this_ArrayTypeRef_1= ruleArrayTypeRef | this_TypeRefWithModifiers_2= ruleTypeRefWithModifiers | (otherlv_3= LeftParenthesis this_TypeRef_4= ruleTypeRef otherlv_5= RightParenthesis ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA72_1 = input.LA(1);

                         
                        int index72_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_InternalTypesParser()) ) {s = 16;}

                        else if ( (true) ) {s = 17;}

                         
                        input.seek(index72_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 72, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_18s = "\36\uffff";
    static final String dfa_19s = "\1\uffff\1\34\34\uffff";
    static final String dfa_20s = "\1\11\1\122\12\uffff\1\127\2\uffff\3\127\14\uffff";
    static final String dfa_21s = "\1\152\1\134\12\uffff\1\140\2\uffff\3\140\14\uffff";
    static final String dfa_22s = "\2\uffff\12\1\1\uffff\2\1\3\uffff\12\1\1\2\1\1";
    static final String dfa_23s = "\1\5\1\1\12\uffff\1\3\2\uffff\1\4\1\2\1\0\14\uffff}>";
    static final String[] dfa_24s = {
            "\1\21\1\14\1\uffff\1\26\1\uffff\1\23\1\27\1\uffff\1\31\1\34\1\10\2\uffff\1\7\4\uffff\1\34\1\30\1\6\4\uffff\1\32\1\uffff\1\11\1\uffff\1\16\1\uffff\1\25\1\24\10\uffff\1\20\1\uffff\1\2\1\22\3\uffff\1\13\2\34\1\uffff\1\17\1\34\2\uffff\1\34\1\uffff\1\3\1\5\1\uffff\1\33\1\4\3\uffff\1\12\3\uffff\1\15\20\uffff\1\34\1\uffff\1\34\7\uffff\1\1",
            "\3\34\1\uffff\1\34\1\35\1\uffff\2\34\1\uffff\1\34",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\35\10\uffff\1\34",
            "",
            "",
            "\1\35\10\uffff\1\34",
            "\1\35\10\uffff\1\34",
            "\1\35\10\uffff\1\34",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final char[] dfa_20 = DFA.unpackEncodedStringToUnsignedChars(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[][] dfa_24 = unpackEncodedStringArray(dfa_24s);

    class DFA88 extends DFA {

        public DFA88(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 88;
            this.eot = dfa_18;
            this.eof = dfa_19;
            this.min = dfa_20;
            this.max = dfa_21;
            this.accept = dfa_22;
            this.special = dfa_23;
            this.transition = dfa_24;
        }
        public String getDescription() {
            return "4225:3: ( ( ( ( ( ( ruleBindingIdentifier ) ) Colon ) )=> ( ( (lv_name_1_0= ruleBindingIdentifier ) ) ( ( Colon )=>this_ColonSepTypeRef_2= ruleColonSepTypeRef[$current] ) ) ) | ( (lv_typeRef_3_0= ruleTypeRef ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA88_17 = input.LA(1);

                         
                        int index88_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_17==LeftCurlyBracket) ) {s = 28;}

                        else if ( (LA88_17==Colon) && (synpred18_InternalTypesParser())) {s = 29;}

                         
                        input.seek(index88_17);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA88_1 = input.LA(1);

                         
                        int index88_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_1==EOF||(LA88_1>=RightParenthesis && LA88_1<=Comma)||LA88_1==Solidus||(LA88_1>=LessThanSign && LA88_1<=EqualsSign)||LA88_1==QuestionMark) ) {s = 28;}

                        else if ( (LA88_1==Colon) && (synpred18_InternalTypesParser())) {s = 29;}

                         
                        input.seek(index88_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA88_16 = input.LA(1);

                         
                        int index88_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_16==LeftCurlyBracket) ) {s = 28;}

                        else if ( (LA88_16==Colon) && (synpred18_InternalTypesParser())) {s = 29;}

                         
                        input.seek(index88_16);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA88_12 = input.LA(1);

                         
                        int index88_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_12==LeftCurlyBracket) ) {s = 28;}

                        else if ( (LA88_12==Colon) && (synpred18_InternalTypesParser())) {s = 29;}

                         
                        input.seek(index88_12);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA88_15 = input.LA(1);

                         
                        int index88_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_15==LeftCurlyBracket) ) {s = 28;}

                        else if ( (LA88_15==Colon) && (synpred18_InternalTypesParser())) {s = 29;}

                         
                        input.seek(index88_15);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA88_0 = input.LA(1);

                         
                        int index88_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_0==RULE_IDENTIFIER) ) {s = 1;}

                        else if ( (LA88_0==Yield) && (synpred18_InternalTypesParser())) {s = 2;}

                        else if ( (LA88_0==Get) && (synpred18_InternalTypesParser())) {s = 3;}

                        else if ( (LA88_0==Set) && (synpred18_InternalTypesParser())) {s = 4;}

                        else if ( (LA88_0==Let) && (synpred18_InternalTypesParser())) {s = 5;}

                        else if ( (LA88_0==Project) && (synpred18_InternalTypesParser())) {s = 6;}

                        else if ( (LA88_0==External) && (synpred18_InternalTypesParser())) {s = 7;}

                        else if ( (LA88_0==Abstract) && (synpred18_InternalTypesParser())) {s = 8;}

                        else if ( (LA88_0==Static) && (synpred18_InternalTypesParser())) {s = 9;}

                        else if ( (LA88_0==As) && (synpred18_InternalTypesParser())) {s = 10;}

                        else if ( (LA88_0==From) && (synpred18_InternalTypesParser())) {s = 11;}

                        else if ( (LA88_0==Constructor) ) {s = 12;}

                        else if ( (LA88_0==Of) && (synpred18_InternalTypesParser())) {s = 13;}

                        else if ( (LA88_0==Target) && (synpred18_InternalTypesParser())) {s = 14;}

                        else if ( (LA88_0==Type) ) {s = 15;}

                        else if ( (LA88_0==Union) ) {s = 16;}

                        else if ( (LA88_0==Intersection) ) {s = 17;}

                        else if ( (LA88_0==This) && (synpred18_InternalTypesParser())) {s = 18;}

                        else if ( (LA88_0==Promisify) && (synpred18_InternalTypesParser())) {s = 19;}

                        else if ( (LA88_0==Await) && (synpred18_InternalTypesParser())) {s = 20;}

                        else if ( (LA88_0==Async) && (synpred18_InternalTypesParser())) {s = 21;}

                        else if ( (LA88_0==Implements) && (synpred18_InternalTypesParser())) {s = 22;}

                        else if ( (LA88_0==Interface) && (synpred18_InternalTypesParser())) {s = 23;}

                        else if ( (LA88_0==Private) && (synpred18_InternalTypesParser())) {s = 24;}

                        else if ( (LA88_0==Protected) && (synpred18_InternalTypesParser())) {s = 25;}

                        else if ( (LA88_0==Public) && (synpred18_InternalTypesParser())) {s = 26;}

                        else if ( (LA88_0==Out) && (synpred18_InternalTypesParser())) {s = 27;}

                        else if ( (LA88_0==Undefined||LA88_0==Indexed||(LA88_0>=Null && LA88_0<=This_1)||LA88_0==Void||LA88_0==Any||LA88_0==LeftCurlyBracket||LA88_0==Tilde) ) {s = 28;}

                         
                        input.seek(index88_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 88, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_25s = "\55\uffff";
    static final String dfa_26s = "\1\2\54\uffff";
    static final String dfa_27s = "\1\5\1\11\1\uffff\1\31\23\uffff\1\11\1\0\10\uffff\1\31\13\uffff";
    static final String dfa_28s = "\2\152\1\uffff\1\134\23\uffff\1\152\1\0\10\uffff\1\134\13\uffff";
    static final String dfa_29s = "\2\uffff\1\2\1\uffff\23\1\2\uffff\10\1\1\uffff\13\1";
    static final String dfa_30s = "\1\uffff\1\3\1\uffff\1\2\23\uffff\1\0\1\4\10\uffff\1\1\13\uffff}>";
    static final String[] dfa_31s = {
            "\1\2\1\uffff\1\2\1\uffff\30\2\1\uffff\15\2\1\uffff\20\2\1\uffff\11\2\1\uffff\5\2\1\uffff\4\2\3\uffff\1\2\1\1\4\2\1\uffff\3\2\10\uffff\1\2",
            "\1\22\1\17\7\uffff\1\11\10\uffff\1\13\26\uffff\1\21\7\uffff\1\12\1\15\1\uffff\1\16\1\7\2\uffff\1\10\4\uffff\1\5\7\uffff\1\6\15\uffff\1\4\3\uffff\1\20\1\uffff\1\14\7\uffff\1\3",
            "",
            "\1\2\71\uffff\1\25\1\27\1\uffff\1\23\2\uffff\1\24\1\uffff\1\30\1\26",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\50\1\45\7\uffff\1\36\10\uffff\1\40\26\uffff\1\47\7\uffff\1\37\1\43\1\uffff\1\44\1\34\2\uffff\1\35\4\uffff\1\32\7\uffff\1\33\15\uffff\1\31\3\uffff\1\46\1\uffff\1\42\7\uffff\1\41",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\2\71\uffff\1\53\1\27\1\uffff\1\51\2\uffff\1\52\1\uffff\1\30\1\54",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final char[] dfa_27 = DFA.unpackEncodedStringToUnsignedChars(dfa_27s);
    static final char[] dfa_28 = DFA.unpackEncodedStringToUnsignedChars(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final short[][] dfa_31 = unpackEncodedStringArray(dfa_31s);

    class DFA96 extends DFA {

        public DFA96(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 96;
            this.eot = dfa_25;
            this.eof = dfa_26;
            this.min = dfa_27;
            this.max = dfa_28;
            this.accept = dfa_29;
            this.special = dfa_30;
            this.transition = dfa_31;
        }
        public String getDescription() {
            return "4803:3: ( ( LessThanSign )=>this_TypeArguments_1= ruleTypeArguments[$current] )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA96_23 = input.LA(1);

                         
                        int index96_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA96_23==QuestionMark) && (synpred20_InternalTypesParser())) {s = 25;}

                        else if ( (LA96_23==Out) && (synpred20_InternalTypesParser())) {s = 26;}

                        else if ( (LA96_23==In) && (synpred20_InternalTypesParser())) {s = 27;}

                        else if ( (LA96_23==Void) && (synpred20_InternalTypesParser())) {s = 28;}

                        else if ( (LA96_23==Any) && (synpred20_InternalTypesParser())) {s = 29;}

                        else if ( (LA96_23==Undefined) && (synpred20_InternalTypesParser())) {s = 30;}

                        else if ( (LA96_23==Null) && (synpred20_InternalTypesParser())) {s = 31;}

                        else if ( (LA96_23==Indexed) && (synpred20_InternalTypesParser())) {s = 32;}

                        else if ( (LA96_23==RULE_IDENTIFIER) ) {s = 33;}

                        else if ( (LA96_23==Tilde) && (synpred20_InternalTypesParser())) {s = 34;}

                        else if ( (LA96_23==This_1) && (synpred20_InternalTypesParser())) {s = 35;}

                        else if ( (LA96_23==Type) && (synpred20_InternalTypesParser())) {s = 36;}

                        else if ( (LA96_23==Constructor) && (synpred20_InternalTypesParser())) {s = 37;}

                        else if ( (LA96_23==LeftCurlyBracket) && (synpred20_InternalTypesParser())) {s = 38;}

                        else if ( (LA96_23==Union) && (synpred20_InternalTypesParser())) {s = 39;}

                        else if ( (LA96_23==Intersection) && (synpred20_InternalTypesParser())) {s = 40;}

                         
                        input.seek(index96_23);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA96_33 = input.LA(1);

                         
                        int index96_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA96_33==Solidus) && (synpred20_InternalTypesParser())) {s = 41;}

                        else if ( (LA96_33==LessThanSign) && (synpred20_InternalTypesParser())) {s = 42;}

                        else if ( (LA96_33==PlusSign) && (synpred20_InternalTypesParser())) {s = 43;}

                        else if ( (LA96_33==QuestionMark) && (synpred20_InternalTypesParser())) {s = 44;}

                        else if ( (LA96_33==GreaterThanSign) ) {s = 24;}

                        else if ( (LA96_33==Comma) ) {s = 23;}

                        else if ( (LA96_33==Extends) ) {s = 2;}

                         
                        input.seek(index96_33);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA96_3 = input.LA(1);

                         
                        int index96_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA96_3==Solidus) && (synpred20_InternalTypesParser())) {s = 19;}

                        else if ( (LA96_3==LessThanSign) && (synpred20_InternalTypesParser())) {s = 20;}

                        else if ( (LA96_3==PlusSign) && (synpred20_InternalTypesParser())) {s = 21;}

                        else if ( (LA96_3==QuestionMark) && (synpred20_InternalTypesParser())) {s = 22;}

                        else if ( (LA96_3==Comma) ) {s = 23;}

                        else if ( (LA96_3==GreaterThanSign) ) {s = 24;}

                        else if ( (LA96_3==Extends) ) {s = 2;}

                         
                        input.seek(index96_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA96_1 = input.LA(1);

                         
                        int index96_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA96_1==RULE_IDENTIFIER) ) {s = 3;}

                        else if ( (LA96_1==QuestionMark) && (synpred20_InternalTypesParser())) {s = 4;}

                        else if ( (LA96_1==Out) && (synpred20_InternalTypesParser())) {s = 5;}

                        else if ( (LA96_1==In) && (synpred20_InternalTypesParser())) {s = 6;}

                        else if ( (LA96_1==Void) && (synpred20_InternalTypesParser())) {s = 7;}

                        else if ( (LA96_1==Any) && (synpred20_InternalTypesParser())) {s = 8;}

                        else if ( (LA96_1==Undefined) && (synpred20_InternalTypesParser())) {s = 9;}

                        else if ( (LA96_1==Null) && (synpred20_InternalTypesParser())) {s = 10;}

                        else if ( (LA96_1==Indexed) && (synpred20_InternalTypesParser())) {s = 11;}

                        else if ( (LA96_1==Tilde) && (synpred20_InternalTypesParser())) {s = 12;}

                        else if ( (LA96_1==This_1) && (synpred20_InternalTypesParser())) {s = 13;}

                        else if ( (LA96_1==Type) && (synpred20_InternalTypesParser())) {s = 14;}

                        else if ( (LA96_1==Constructor) && (synpred20_InternalTypesParser())) {s = 15;}

                        else if ( (LA96_1==LeftCurlyBracket) && (synpred20_InternalTypesParser())) {s = 16;}

                        else if ( (LA96_1==Union) && (synpred20_InternalTypesParser())) {s = 17;}

                        else if ( (LA96_1==Intersection) && (synpred20_InternalTypesParser())) {s = 18;}

                         
                        input.seek(index96_1);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA96_24 = input.LA(1);

                         
                        int index96_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_InternalTypesParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index96_24);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 96, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_32s = "\104\uffff";
    static final String dfa_33s = "\1\11\2\0\1\uffff\75\0\3\uffff";
    static final String dfa_34s = "\1\152\2\0\1\uffff\75\0\3\uffff";
    static final String dfa_35s = "\3\uffff\1\3\75\uffff\1\1\1\4\1\2";
    static final String dfa_36s = "\1\0\1\1\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\3\uffff}>";
    static final String[] dfa_37s = {
            "\1\66\1\61\1\uffff\1\73\1\30\1\70\1\74\1\uffff\1\76\1\uffff\1\55\1\12\1\13\1\54\1\24\1\14\1\21\1\22\1\uffff\1\75\1\53\1\15\1\20\1\26\1\uffff\1\77\1\32\1\56\1\34\1\63\1\40\1\72\1\71\1\5\1\7\1\10\1\11\1\50\1\uffff\1\33\1\36\1\65\1\43\1\45\1\67\1\6\1\17\1\51\1\60\1\46\1\35\1\47\1\64\1\42\1\44\2\uffff\1\23\1\1\1\52\1\31\1\100\1\2\1\37\1\41\1\uffff\1\57\1\16\1\25\1\27\1\62\11\uffff\1\3\20\uffff\1\4",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final char[] dfa_33 = DFA.unpackEncodedStringToUnsignedChars(dfa_33s);
    static final char[] dfa_34 = DFA.unpackEncodedStringToUnsignedChars(dfa_34s);
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[][] dfa_37 = unpackEncodedStringArray(dfa_37s);

    class DFA100 extends DFA {

        public DFA100(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 100;
            this.eot = dfa_32;
            this.eof = dfa_32;
            this.min = dfa_33;
            this.max = dfa_34;
            this.accept = dfa_35;
            this.special = dfa_36;
            this.transition = dfa_37;
        }
        public String getDescription() {
            return "4953:2: ( ( ( ( () Get ( ( ruleIdentifierName ) ) ) )=>this_TStructGetter_0= ruleTStructGetter ) | ( ( ( () Set ( ( ruleIdentifierName ) ) ) )=>this_TStructSetter_1= ruleTStructSetter ) | ( ( ( () ( ruleTypeVariables[null] )? ( ( ruleIdentifierName ) ) LeftParenthesis ) )=>this_TStructMethod_2= ruleTStructMethod ) | this_TStructField_3= ruleTStructField )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA100_0 = input.LA(1);

                         
                        int index100_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA100_0==Get) ) {s = 1;}

                        else if ( (LA100_0==Set) ) {s = 2;}

                        else if ( (LA100_0==LessThanSign) && (synpred23_InternalTypesParser())) {s = 3;}

                        else if ( (LA100_0==RULE_IDENTIFIER) ) {s = 4;}

                        else if ( (LA100_0==Break) ) {s = 5;}

                        else if ( (LA100_0==Case) ) {s = 6;}

                        else if ( (LA100_0==Catch) ) {s = 7;}

                        else if ( (LA100_0==Class) ) {s = 8;}

                        else if ( (LA100_0==Const) ) {s = 9;}

                        else if ( (LA100_0==Continue) ) {s = 10;}

                        else if ( (LA100_0==Debugger) ) {s = 11;}

                        else if ( (LA100_0==Default) ) {s = 12;}

                        else if ( (LA100_0==Delete) ) {s = 13;}

                        else if ( (LA100_0==Do) ) {s = 14;}

                        else if ( (LA100_0==Else) ) {s = 15;}

                        else if ( (LA100_0==Export) ) {s = 16;}

                        else if ( (LA100_0==Extends) ) {s = 17;}

                        else if ( (LA100_0==Finally) ) {s = 18;}

                        else if ( (LA100_0==For) ) {s = 19;}

                        else if ( (LA100_0==Function) ) {s = 20;}

                        else if ( (LA100_0==If) ) {s = 21;}

                        else if ( (LA100_0==Import) ) {s = 22;}

                        else if ( (LA100_0==In) ) {s = 23;}

                        else if ( (LA100_0==Instanceof) ) {s = 24;}

                        else if ( (LA100_0==New) ) {s = 25;}

                        else if ( (LA100_0==Return) ) {s = 26;}

                        else if ( (LA100_0==Super) ) {s = 27;}

                        else if ( (LA100_0==Switch) ) {s = 28;}

                        else if ( (LA100_0==This_1) ) {s = 29;}

                        else if ( (LA100_0==Throw) ) {s = 30;}

                        else if ( (LA100_0==Try) ) {s = 31;}

                        else if ( (LA100_0==Typeof) ) {s = 32;}

                        else if ( (LA100_0==Var) ) {s = 33;}

                        else if ( (LA100_0==Void) ) {s = 34;}

                        else if ( (LA100_0==While) ) {s = 35;}

                        else if ( (LA100_0==With) ) {s = 36;}

                        else if ( (LA100_0==Yield) ) {s = 37;}

                        else if ( (LA100_0==Null) ) {s = 38;}

                        else if ( (LA100_0==True) ) {s = 39;}

                        else if ( (LA100_0==False) ) {s = 40;}

                        else if ( (LA100_0==Enum) ) {s = 41;}

                        else if ( (LA100_0==Let) ) {s = 42;}

                        else if ( (LA100_0==Project) ) {s = 43;}

                        else if ( (LA100_0==External) ) {s = 44;}

                        else if ( (LA100_0==Abstract) ) {s = 45;}

                        else if ( (LA100_0==Static) ) {s = 46;}

                        else if ( (LA100_0==As) ) {s = 47;}

                        else if ( (LA100_0==From) ) {s = 48;}

                        else if ( (LA100_0==Constructor) ) {s = 49;}

                        else if ( (LA100_0==Of) ) {s = 50;}

                        else if ( (LA100_0==Target) ) {s = 51;}

                        else if ( (LA100_0==Type) ) {s = 52;}

                        else if ( (LA100_0==Union) ) {s = 53;}

                        else if ( (LA100_0==Intersection) ) {s = 54;}

                        else if ( (LA100_0==This) ) {s = 55;}

                        else if ( (LA100_0==Promisify) ) {s = 56;}

                        else if ( (LA100_0==Await) ) {s = 57;}

                        else if ( (LA100_0==Async) ) {s = 58;}

                        else if ( (LA100_0==Implements) ) {s = 59;}

                        else if ( (LA100_0==Interface) ) {s = 60;}

                        else if ( (LA100_0==Private) ) {s = 61;}

                        else if ( (LA100_0==Protected) ) {s = 62;}

                        else if ( (LA100_0==Public) ) {s = 63;}

                        else if ( (LA100_0==Out) ) {s = 64;}

                         
                        input.seek(index100_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA100_1 = input.LA(1);

                         
                        int index100_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_InternalTypesParser()) ) {s = 65;}

                        else if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA100_2 = input.LA(1);

                         
                        int index100_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_InternalTypesParser()) ) {s = 67;}

                        else if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA100_4 = input.LA(1);

                         
                        int index100_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA100_5 = input.LA(1);

                         
                        int index100_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA100_6 = input.LA(1);

                         
                        int index100_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA100_7 = input.LA(1);

                         
                        int index100_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA100_8 = input.LA(1);

                         
                        int index100_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA100_9 = input.LA(1);

                         
                        int index100_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA100_10 = input.LA(1);

                         
                        int index100_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA100_11 = input.LA(1);

                         
                        int index100_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA100_12 = input.LA(1);

                         
                        int index100_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA100_13 = input.LA(1);

                         
                        int index100_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA100_14 = input.LA(1);

                         
                        int index100_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_14);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA100_15 = input.LA(1);

                         
                        int index100_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_15);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA100_16 = input.LA(1);

                         
                        int index100_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_16);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA100_17 = input.LA(1);

                         
                        int index100_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_17);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA100_18 = input.LA(1);

                         
                        int index100_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_18);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA100_19 = input.LA(1);

                         
                        int index100_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_19);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA100_20 = input.LA(1);

                         
                        int index100_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_20);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA100_21 = input.LA(1);

                         
                        int index100_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_21);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA100_22 = input.LA(1);

                         
                        int index100_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_22);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA100_23 = input.LA(1);

                         
                        int index100_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_23);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA100_24 = input.LA(1);

                         
                        int index100_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_24);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA100_25 = input.LA(1);

                         
                        int index100_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_25);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA100_26 = input.LA(1);

                         
                        int index100_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_26);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA100_27 = input.LA(1);

                         
                        int index100_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_27);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA100_28 = input.LA(1);

                         
                        int index100_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_28);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA100_29 = input.LA(1);

                         
                        int index100_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_29);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA100_30 = input.LA(1);

                         
                        int index100_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_30);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA100_31 = input.LA(1);

                         
                        int index100_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_31);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA100_32 = input.LA(1);

                         
                        int index100_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_32);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA100_33 = input.LA(1);

                         
                        int index100_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_33);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA100_34 = input.LA(1);

                         
                        int index100_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_34);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA100_35 = input.LA(1);

                         
                        int index100_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_35);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA100_36 = input.LA(1);

                         
                        int index100_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_36);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA100_37 = input.LA(1);

                         
                        int index100_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_37);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA100_38 = input.LA(1);

                         
                        int index100_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_38);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA100_39 = input.LA(1);

                         
                        int index100_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_39);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA100_40 = input.LA(1);

                         
                        int index100_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_40);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA100_41 = input.LA(1);

                         
                        int index100_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_41);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA100_42 = input.LA(1);

                         
                        int index100_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_42);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA100_43 = input.LA(1);

                         
                        int index100_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_43);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA100_44 = input.LA(1);

                         
                        int index100_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_44);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA100_45 = input.LA(1);

                         
                        int index100_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_45);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA100_46 = input.LA(1);

                         
                        int index100_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_46);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA100_47 = input.LA(1);

                         
                        int index100_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_47);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA100_48 = input.LA(1);

                         
                        int index100_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_48);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA100_49 = input.LA(1);

                         
                        int index100_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_49);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA100_50 = input.LA(1);

                         
                        int index100_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_50);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA100_51 = input.LA(1);

                         
                        int index100_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_51);
                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA100_52 = input.LA(1);

                         
                        int index100_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_52);
                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA100_53 = input.LA(1);

                         
                        int index100_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_53);
                        if ( s>=0 ) return s;
                        break;
                    case 53 : 
                        int LA100_54 = input.LA(1);

                         
                        int index100_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_54);
                        if ( s>=0 ) return s;
                        break;
                    case 54 : 
                        int LA100_55 = input.LA(1);

                         
                        int index100_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_55);
                        if ( s>=0 ) return s;
                        break;
                    case 55 : 
                        int LA100_56 = input.LA(1);

                         
                        int index100_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_56);
                        if ( s>=0 ) return s;
                        break;
                    case 56 : 
                        int LA100_57 = input.LA(1);

                         
                        int index100_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_57);
                        if ( s>=0 ) return s;
                        break;
                    case 57 : 
                        int LA100_58 = input.LA(1);

                         
                        int index100_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_58);
                        if ( s>=0 ) return s;
                        break;
                    case 58 : 
                        int LA100_59 = input.LA(1);

                         
                        int index100_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_59);
                        if ( s>=0 ) return s;
                        break;
                    case 59 : 
                        int LA100_60 = input.LA(1);

                         
                        int index100_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_60);
                        if ( s>=0 ) return s;
                        break;
                    case 60 : 
                        int LA100_61 = input.LA(1);

                         
                        int index100_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_61);
                        if ( s>=0 ) return s;
                        break;
                    case 61 : 
                        int LA100_62 = input.LA(1);

                         
                        int index100_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_62);
                        if ( s>=0 ) return s;
                        break;
                    case 62 : 
                        int LA100_63 = input.LA(1);

                         
                        int index100_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_63);
                        if ( s>=0 ) return s;
                        break;
                    case 63 : 
                        int LA100_64 = input.LA(1);

                         
                        int index100_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalTypesParser()) ) {s = 3;}

                        else if ( (true) ) {s = 66;}

                         
                        input.seek(index100_64);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 100, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x4400000420050882L,0x0000040000000002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x6C04000008040600L,0x0000041500040002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000000L,0x0000000000140000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x6C04000008040600L,0x0000041500000002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x62340356304FDF10L,0x00000400000088DAL});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000008000000L,0x0000000102000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000008000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x4400000008040000L,0x0000040000000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000110L,0x0000000200000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000010L,0x0000000200000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0xFFFF7FFFF7FFFF10L,0x000004100000FBFEL});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0xFFFF7FFFF7FFFF10L,0x000004000000FBFEL});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000800200000040L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000800200000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x000000000A000000L,0x0000000122000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x000000000A000000L,0x0000000120000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000008000000L,0x0000000120000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000120000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x00000004300200A0L,0x0000000200020000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x00000004300200A0L,0x0000000200000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000900000080040L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000900000080000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000900000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x62340356304FDF10L,0x00000404000088DAL});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000002001000L,0x0000000120000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000001000L,0x0000000120000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000120100000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000008040L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000002000000L,0x0000000120000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000000L,0x0000040000004040L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000008100000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000001800000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x62340356304FDF10L,0x00000400000488DBL});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x62340356304FDF10L,0x00000400000088DBL});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0xFFFF7FFFF7FFFF10L,0x000004004200FBFEL});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0xFFFFFFFFF7FFFF10L,0x000004004200FBFEL});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000000010800000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000001000080000L,0x0000000000000008L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000010020000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000001000080000L,0x0000000000000080L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000800040L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x62340356304FDF10L,0x00000400020088DAL});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0100000000000040L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000200100000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000800000L,0x0000000020000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x6C04000008040600L,0x0000041540000002L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000000L,0x0000000002020000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x6E340356384FDF10L,0x00000415000488DBL});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000000L,0x0000000200800000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x6C04000008040600L,0x0000041540020002L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x6E340356384FDF10L,0x00000415000088DBL});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x6E340356384FDF10L,0x00000415000088DAL});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x4400000008040002L,0x0000040000000002L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x6C04000008040600L,0x0000041510004042L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0xFFFF7FFFF7FFFF10L,0x000004020200FBFEL});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0xFFFF7FFFF7FFFF10L,0x000004020310FBFEL});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000002L,0x0000000010800000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000002L,0x0000008400000000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x4C00000008040000L,0x0000040010000002L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0001000002000002L});

}
