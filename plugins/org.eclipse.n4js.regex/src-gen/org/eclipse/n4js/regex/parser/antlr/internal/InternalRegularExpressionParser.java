package org.eclipse.n4js.regex.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;



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
public class InternalRegularExpressionParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LeftParenthesisQuestionMarkLessThanSignExclamationMark", "LeftParenthesisQuestionMarkLessThanSignEqualsSign", "LeftParenthesisQuestionMarkExclamationMark", "LeftParenthesisQuestionMarkColon", "LeftParenthesisQuestionMarkLessThanSign", "LeftParenthesisQuestionMarkEqualsSign", "LeftParenthesisQuestionMark", "ExclamationMark", "DollarSign", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "LessThanSign", "EqualsSign", "GreaterThanSign", "QuestionMark", "LeftSquareBracket", "RightSquareBracket", "CircumflexAccent", "KW__", "LeftCurlyBracket", "VerticalLine", "RightCurlyBracket", "RULE_WORD_BOUNDARY", "RULE_NOT_WORD_BOUNDARY", "RULE_CHARACTER_CLASS_ESCAPE", "RULE_CONTROL_ESCAPE", "RULE_CONTROL_LETTER_ESCAPE", "RULE_HEX_DIGIT", "RULE_HEX_ESCAPE", "RULE_UNICODE_ESCAPE", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_DECIMAL_ESCAPE", "RULE_IDENTITY_ESCAPE", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_DIGIT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_UNICODE_LETTER", "RULE_PATTERN_CHARACTER_NO_DASH", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_ANY_OTHER"
    };
    public static final int LeftParenthesisQuestionMarkLessThanSignEqualsSign=5;
    public static final int LessThanSign=22;
    public static final int LeftParenthesisQuestionMark=10;
    public static final int RULE_WHITESPACE_FRAGMENT=54;
    public static final int LeftParenthesis=13;
    public static final int RULE_HEX_ESCAPE=39;
    public static final int RightSquareBracket=27;
    public static final int ExclamationMark=11;
    public static final int GreaterThanSign=24;
    public static final int RULE_CONTROL_LETTER_ESCAPE=37;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=56;
    public static final int RightParenthesis=14;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=59;
    public static final int LeftParenthesisQuestionMarkLessThanSignExclamationMark=4;
    public static final int LeftParenthesisQuestionMarkExclamationMark=6;
    public static final int LeftParenthesisQuestionMarkColon=7;
    public static final int RULE_ZWNJ=51;
    public static final int VerticalLine=31;
    public static final int PlusSign=16;
    public static final int LeftSquareBracket=26;
    public static final int RULE_DECIMAL_ESCAPE=42;
    public static final int LeftParenthesisQuestionMarkLessThanSign=8;
    public static final int RULE_ML_COMMENT_FRAGMENT=58;
    public static final int RULE_PATTERN_CHARACTER_NO_DASH=48;
    public static final int RULE_WORD_BOUNDARY=33;
    public static final int RULE_UNICODE_ESCAPE=40;
    public static final int Comma=17;
    public static final int EqualsSign=23;
    public static final int RULE_ZWJ=50;
    public static final int RULE_SL_COMMENT_FRAGMENT=57;
    public static final int HyphenMinus=18;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=44;
    public static final int RULE_UNICODE_LETTER=47;
    public static final int RULE_CONTROL_ESCAPE=36;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=53;
    public static final int Solidus=20;
    public static final int Colon=21;
    public static final int RightCurlyBracket=32;
    public static final int RULE_CHARACTER_CLASS_ESCAPE=35;
    public static final int EOF=-1;
    public static final int Asterisk=15;
    public static final int FullStop=19;
    public static final int RULE_UNICODE_DIGIT=45;
    public static final int LeftParenthesisQuestionMarkEqualsSign=9;
    public static final int RULE_BOM=52;
    public static final int LeftCurlyBracket=30;
    public static final int RULE_ANY_OTHER=61;
    public static final int CircumflexAccent=28;
    public static final int RULE_NOT_WORD_BOUNDARY=34;
    public static final int KW__=29;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=55;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=46;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=49;
    public static final int QuestionMark=25;
    public static final int DollarSign=12;
    public static final int RULE_HEX_DIGIT=38;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=41;
    public static final int RULE_IDENTITY_ESCAPE=43;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=60;

    // delegates
    // delegators


        public InternalRegularExpressionParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalRegularExpressionParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalRegularExpressionParser.tokenNames; }
    public String getGrammarFileName() { return "InternalRegularExpressionParser.g"; }



     	private RegularExpressionGrammarAccess grammarAccess;

        public InternalRegularExpressionParser(TokenStream input, RegularExpressionGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "RegularExpressionLiteral";
       	}

       	@Override
       	protected RegularExpressionGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleRegularExpressionLiteral"
    // InternalRegularExpressionParser.g:64:1: entryRuleRegularExpressionLiteral returns [EObject current=null] : iv_ruleRegularExpressionLiteral= ruleRegularExpressionLiteral EOF ;
    public final EObject entryRuleRegularExpressionLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegularExpressionLiteral = null;


        try {
            // InternalRegularExpressionParser.g:64:65: (iv_ruleRegularExpressionLiteral= ruleRegularExpressionLiteral EOF )
            // InternalRegularExpressionParser.g:65:2: iv_ruleRegularExpressionLiteral= ruleRegularExpressionLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRegularExpressionLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRegularExpressionLiteral=ruleRegularExpressionLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRegularExpressionLiteral; 
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
    // $ANTLR end "entryRuleRegularExpressionLiteral"


    // $ANTLR start "ruleRegularExpressionLiteral"
    // InternalRegularExpressionParser.g:71:1: ruleRegularExpressionLiteral returns [EObject current=null] : (otherlv_0= Solidus ( (lv_body_1_0= ruleRegularExpressionBody ) ) otherlv_2= Solidus ( (lv_flags_3_0= ruleRegularExpressionFlags ) ) ) ;
    public final EObject ruleRegularExpressionLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_body_1_0 = null;

        EObject lv_flags_3_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:77:2: ( (otherlv_0= Solidus ( (lv_body_1_0= ruleRegularExpressionBody ) ) otherlv_2= Solidus ( (lv_flags_3_0= ruleRegularExpressionFlags ) ) ) )
            // InternalRegularExpressionParser.g:78:2: (otherlv_0= Solidus ( (lv_body_1_0= ruleRegularExpressionBody ) ) otherlv_2= Solidus ( (lv_flags_3_0= ruleRegularExpressionFlags ) ) )
            {
            // InternalRegularExpressionParser.g:78:2: (otherlv_0= Solidus ( (lv_body_1_0= ruleRegularExpressionBody ) ) otherlv_2= Solidus ( (lv_flags_3_0= ruleRegularExpressionFlags ) ) )
            // InternalRegularExpressionParser.g:79:3: otherlv_0= Solidus ( (lv_body_1_0= ruleRegularExpressionBody ) ) otherlv_2= Solidus ( (lv_flags_3_0= ruleRegularExpressionFlags ) )
            {
            otherlv_0=(Token)match(input,Solidus,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_0());
              		
            }
            // InternalRegularExpressionParser.g:83:3: ( (lv_body_1_0= ruleRegularExpressionBody ) )
            // InternalRegularExpressionParser.g:84:4: (lv_body_1_0= ruleRegularExpressionBody )
            {
            // InternalRegularExpressionParser.g:84:4: (lv_body_1_0= ruleRegularExpressionBody )
            // InternalRegularExpressionParser.g:85:5: lv_body_1_0= ruleRegularExpressionBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getRegularExpressionLiteralAccess().getBodyRegularExpressionBodyParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_4);
            lv_body_1_0=ruleRegularExpressionBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getRegularExpressionLiteralRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_1_0,
              						"org.eclipse.n4js.regex.RegularExpression.RegularExpressionBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,Solidus,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_2());
              		
            }
            // InternalRegularExpressionParser.g:106:3: ( (lv_flags_3_0= ruleRegularExpressionFlags ) )
            // InternalRegularExpressionParser.g:107:4: (lv_flags_3_0= ruleRegularExpressionFlags )
            {
            // InternalRegularExpressionParser.g:107:4: (lv_flags_3_0= ruleRegularExpressionFlags )
            // InternalRegularExpressionParser.g:108:5: lv_flags_3_0= ruleRegularExpressionFlags
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getRegularExpressionLiteralAccess().getFlagsRegularExpressionFlagsParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_flags_3_0=ruleRegularExpressionFlags();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getRegularExpressionLiteralRule());
              					}
              					set(
              						current,
              						"flags",
              						lv_flags_3_0,
              						"org.eclipse.n4js.regex.RegularExpression.RegularExpressionFlags");
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
    // $ANTLR end "ruleRegularExpressionLiteral"


    // $ANTLR start "entryRuleRegularExpressionBody"
    // InternalRegularExpressionParser.g:129:1: entryRuleRegularExpressionBody returns [EObject current=null] : iv_ruleRegularExpressionBody= ruleRegularExpressionBody EOF ;
    public final EObject entryRuleRegularExpressionBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegularExpressionBody = null;


        try {
            // InternalRegularExpressionParser.g:129:62: (iv_ruleRegularExpressionBody= ruleRegularExpressionBody EOF )
            // InternalRegularExpressionParser.g:130:2: iv_ruleRegularExpressionBody= ruleRegularExpressionBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRegularExpressionBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRegularExpressionBody=ruleRegularExpressionBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRegularExpressionBody; 
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
    // $ANTLR end "entryRuleRegularExpressionBody"


    // $ANTLR start "ruleRegularExpressionBody"
    // InternalRegularExpressionParser.g:136:1: ruleRegularExpressionBody returns [EObject current=null] : ( (lv_pattern_0_0= ruleDisjunction ) ) ;
    public final EObject ruleRegularExpressionBody() throws RecognitionException {
        EObject current = null;

        EObject lv_pattern_0_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:142:2: ( ( (lv_pattern_0_0= ruleDisjunction ) ) )
            // InternalRegularExpressionParser.g:143:2: ( (lv_pattern_0_0= ruleDisjunction ) )
            {
            // InternalRegularExpressionParser.g:143:2: ( (lv_pattern_0_0= ruleDisjunction ) )
            // InternalRegularExpressionParser.g:144:3: (lv_pattern_0_0= ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:144:3: (lv_pattern_0_0= ruleDisjunction )
            // InternalRegularExpressionParser.g:145:4: lv_pattern_0_0= ruleDisjunction
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getRegularExpressionBodyAccess().getPatternDisjunctionParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_pattern_0_0=ruleDisjunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getRegularExpressionBodyRule());
              				}
              				set(
              					current,
              					"pattern",
              					lv_pattern_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.Disjunction");
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
    // $ANTLR end "ruleRegularExpressionBody"


    // $ANTLR start "entryRuleDisjunction"
    // InternalRegularExpressionParser.g:165:1: entryRuleDisjunction returns [EObject current=null] : iv_ruleDisjunction= ruleDisjunction EOF ;
    public final EObject entryRuleDisjunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDisjunction = null;


        try {
            // InternalRegularExpressionParser.g:165:52: (iv_ruleDisjunction= ruleDisjunction EOF )
            // InternalRegularExpressionParser.g:166:2: iv_ruleDisjunction= ruleDisjunction EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDisjunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDisjunction=ruleDisjunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDisjunction; 
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
    // $ANTLR end "entryRuleDisjunction"


    // $ANTLR start "ruleDisjunction"
    // InternalRegularExpressionParser.g:172:1: ruleDisjunction returns [EObject current=null] : ( (this_Alternative_0= ruleAlternative ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )? ) | ( () (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )* ) ) ;
    public final EObject ruleDisjunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Alternative_0 = null;

        EObject lv_elements_3_0 = null;

        EObject lv_elements_6_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:178:2: ( ( (this_Alternative_0= ruleAlternative ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )? ) | ( () (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )* ) ) )
            // InternalRegularExpressionParser.g:179:2: ( (this_Alternative_0= ruleAlternative ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )? ) | ( () (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )* ) )
            {
            // InternalRegularExpressionParser.g:179:2: ( (this_Alternative_0= ruleAlternative ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )? ) | ( () (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )* ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA6_0<=LeftParenthesisQuestionMarkEqualsSign)||(LA6_0>=ExclamationMark && LA6_0<=LeftParenthesis)||(LA6_0>=Comma && LA6_0<=FullStop)||(LA6_0>=Colon && LA6_0<=GreaterThanSign)||(LA6_0>=LeftSquareBracket && LA6_0<=CircumflexAccent)||LA6_0==LeftCurlyBracket||(LA6_0>=RightCurlyBracket && LA6_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA6_0>=RULE_HEX_ESCAPE && LA6_0<=RULE_UNICODE_ESCAPE)||(LA6_0>=RULE_DECIMAL_ESCAPE && LA6_0<=RULE_IDENTITY_ESCAPE)||LA6_0==RULE_UNICODE_DIGIT||(LA6_0>=RULE_UNICODE_LETTER && LA6_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt6=1;
            }
            else if ( (LA6_0==EOF||LA6_0==RightParenthesis||LA6_0==Solidus||LA6_0==VerticalLine) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalRegularExpressionParser.g:180:3: (this_Alternative_0= ruleAlternative ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )? )
                    {
                    // InternalRegularExpressionParser.g:180:3: (this_Alternative_0= ruleAlternative ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )? )
                    // InternalRegularExpressionParser.g:181:4: this_Alternative_0= ruleAlternative ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )?
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getDisjunctionAccess().getAlternativeParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_6);
                    this_Alternative_0=ruleAlternative();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Alternative_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalRegularExpressionParser.g:189:4: ( () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+ )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==VerticalLine) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalRegularExpressionParser.g:190:5: () (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+
                            {
                            // InternalRegularExpressionParser.g:190:5: ()
                            // InternalRegularExpressionParser.g:191:6: 
                            {
                            if ( state.backtracking==0 ) {

                              						current = forceCreateModelElementAndAdd(
                              							grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0(),
                              							current);
                              					
                            }

                            }

                            // InternalRegularExpressionParser.g:197:5: (otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )? )+
                            int cnt2=0;
                            loop2:
                            do {
                                int alt2=2;
                                int LA2_0 = input.LA(1);

                                if ( (LA2_0==VerticalLine) ) {
                                    alt2=1;
                                }


                                switch (alt2) {
                            	case 1 :
                            	    // InternalRegularExpressionParser.g:198:6: otherlv_2= VerticalLine ( (lv_elements_3_0= ruleAlternative ) )?
                            	    {
                            	    otherlv_2=(Token)match(input,VerticalLine,FOLLOW_7); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_2, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0());
                            	      					
                            	    }
                            	    // InternalRegularExpressionParser.g:202:6: ( (lv_elements_3_0= ruleAlternative ) )?
                            	    int alt1=2;
                            	    int LA1_0 = input.LA(1);

                            	    if ( ((LA1_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA1_0<=LeftParenthesisQuestionMarkEqualsSign)||(LA1_0>=ExclamationMark && LA1_0<=LeftParenthesis)||(LA1_0>=Comma && LA1_0<=FullStop)||(LA1_0>=Colon && LA1_0<=GreaterThanSign)||(LA1_0>=LeftSquareBracket && LA1_0<=CircumflexAccent)||LA1_0==LeftCurlyBracket||(LA1_0>=RightCurlyBracket && LA1_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA1_0>=RULE_HEX_ESCAPE && LA1_0<=RULE_UNICODE_ESCAPE)||(LA1_0>=RULE_DECIMAL_ESCAPE && LA1_0<=RULE_IDENTITY_ESCAPE)||LA1_0==RULE_UNICODE_DIGIT||(LA1_0>=RULE_UNICODE_LETTER && LA1_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                            	        alt1=1;
                            	    }
                            	    switch (alt1) {
                            	        case 1 :
                            	            // InternalRegularExpressionParser.g:203:7: (lv_elements_3_0= ruleAlternative )
                            	            {
                            	            // InternalRegularExpressionParser.g:203:7: (lv_elements_3_0= ruleAlternative )
                            	            // InternalRegularExpressionParser.g:204:8: lv_elements_3_0= ruleAlternative
                            	            {
                            	            if ( state.backtracking==0 ) {

                            	              								newCompositeNode(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_0_1_1_1_0());
                            	              							
                            	            }
                            	            pushFollow(FOLLOW_6);
                            	            lv_elements_3_0=ruleAlternative();

                            	            state._fsp--;
                            	            if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              								if (current==null) {
                            	              									current = createModelElementForParent(grammarAccess.getDisjunctionRule());
                            	              								}
                            	              								add(
                            	              									current,
                            	              									"elements",
                            	              									lv_elements_3_0,
                            	              									"org.eclipse.n4js.regex.RegularExpression.Alternative");
                            	              								afterParserOrEnumRuleCall();
                            	              							
                            	            }

                            	            }


                            	            }
                            	            break;

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt2 >= 1 ) break loop2;
                            	    if (state.backtracking>0) {state.failed=true; return current;}
                                        EarlyExitException eee =
                                            new EarlyExitException(2, input);
                                        throw eee;
                                }
                                cnt2++;
                            } while (true);


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:225:3: ( () (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )* )
                    {
                    // InternalRegularExpressionParser.g:225:3: ( () (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )* )
                    // InternalRegularExpressionParser.g:226:4: () (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )*
                    {
                    // InternalRegularExpressionParser.g:226:4: ()
                    // InternalRegularExpressionParser.g:227:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getDisjunctionAccess().getDisjunctionAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalRegularExpressionParser.g:233:4: (otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )? )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==VerticalLine) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalRegularExpressionParser.g:234:5: otherlv_5= VerticalLine ( (lv_elements_6_0= ruleAlternative ) )?
                    	    {
                    	    otherlv_5=(Token)match(input,VerticalLine,FOLLOW_7); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_5, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0());
                    	      				
                    	    }
                    	    // InternalRegularExpressionParser.g:238:5: ( (lv_elements_6_0= ruleAlternative ) )?
                    	    int alt4=2;
                    	    int LA4_0 = input.LA(1);

                    	    if ( ((LA4_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA4_0<=LeftParenthesisQuestionMarkEqualsSign)||(LA4_0>=ExclamationMark && LA4_0<=LeftParenthesis)||(LA4_0>=Comma && LA4_0<=FullStop)||(LA4_0>=Colon && LA4_0<=GreaterThanSign)||(LA4_0>=LeftSquareBracket && LA4_0<=CircumflexAccent)||LA4_0==LeftCurlyBracket||(LA4_0>=RightCurlyBracket && LA4_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA4_0>=RULE_HEX_ESCAPE && LA4_0<=RULE_UNICODE_ESCAPE)||(LA4_0>=RULE_DECIMAL_ESCAPE && LA4_0<=RULE_IDENTITY_ESCAPE)||LA4_0==RULE_UNICODE_DIGIT||(LA4_0>=RULE_UNICODE_LETTER && LA4_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    	        alt4=1;
                    	    }
                    	    switch (alt4) {
                    	        case 1 :
                    	            // InternalRegularExpressionParser.g:239:6: (lv_elements_6_0= ruleAlternative )
                    	            {
                    	            // InternalRegularExpressionParser.g:239:6: (lv_elements_6_0= ruleAlternative )
                    	            // InternalRegularExpressionParser.g:240:7: lv_elements_6_0= ruleAlternative
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              							newCompositeNode(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_1_1_1_0());
                    	              						
                    	            }
                    	            pushFollow(FOLLOW_6);
                    	            lv_elements_6_0=ruleAlternative();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              							if (current==null) {
                    	              								current = createModelElementForParent(grammarAccess.getDisjunctionRule());
                    	              							}
                    	              							add(
                    	              								current,
                    	              								"elements",
                    	              								lv_elements_6_0,
                    	              								"org.eclipse.n4js.regex.RegularExpression.Alternative");
                    	              							afterParserOrEnumRuleCall();
                    	              						
                    	            }

                    	            }


                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
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
    // $ANTLR end "ruleDisjunction"


    // $ANTLR start "entryRuleAlternative"
    // InternalRegularExpressionParser.g:263:1: entryRuleAlternative returns [EObject current=null] : iv_ruleAlternative= ruleAlternative EOF ;
    public final EObject entryRuleAlternative() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlternative = null;


        try {
            // InternalRegularExpressionParser.g:263:52: (iv_ruleAlternative= ruleAlternative EOF )
            // InternalRegularExpressionParser.g:264:2: iv_ruleAlternative= ruleAlternative EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAlternativeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAlternative=ruleAlternative();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAlternative; 
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
    // $ANTLR end "entryRuleAlternative"


    // $ANTLR start "ruleAlternative"
    // InternalRegularExpressionParser.g:270:1: ruleAlternative returns [EObject current=null] : (this_Term_0= ruleTerm ( () ( (lv_elements_2_0= ruleTerm ) )+ )? ) ;
    public final EObject ruleAlternative() throws RecognitionException {
        EObject current = null;

        EObject this_Term_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:276:2: ( (this_Term_0= ruleTerm ( () ( (lv_elements_2_0= ruleTerm ) )+ )? ) )
            // InternalRegularExpressionParser.g:277:2: (this_Term_0= ruleTerm ( () ( (lv_elements_2_0= ruleTerm ) )+ )? )
            {
            // InternalRegularExpressionParser.g:277:2: (this_Term_0= ruleTerm ( () ( (lv_elements_2_0= ruleTerm ) )+ )? )
            // InternalRegularExpressionParser.g:278:3: this_Term_0= ruleTerm ( () ( (lv_elements_2_0= ruleTerm ) )+ )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getAlternativeAccess().getTermParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_8);
            this_Term_0=ruleTerm();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Term_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalRegularExpressionParser.g:286:3: ( () ( (lv_elements_2_0= ruleTerm ) )+ )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA8_0<=LeftParenthesisQuestionMarkEqualsSign)||(LA8_0>=ExclamationMark && LA8_0<=LeftParenthesis)||(LA8_0>=Comma && LA8_0<=FullStop)||(LA8_0>=Colon && LA8_0<=GreaterThanSign)||(LA8_0>=LeftSquareBracket && LA8_0<=CircumflexAccent)||LA8_0==LeftCurlyBracket||(LA8_0>=RightCurlyBracket && LA8_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA8_0>=RULE_HEX_ESCAPE && LA8_0<=RULE_UNICODE_ESCAPE)||(LA8_0>=RULE_DECIMAL_ESCAPE && LA8_0<=RULE_IDENTITY_ESCAPE)||LA8_0==RULE_UNICODE_DIGIT||(LA8_0>=RULE_UNICODE_LETTER && LA8_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalRegularExpressionParser.g:287:4: () ( (lv_elements_2_0= ruleTerm ) )+
                    {
                    // InternalRegularExpressionParser.g:287:4: ()
                    // InternalRegularExpressionParser.g:288:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElementAndAdd(
                      						grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalRegularExpressionParser.g:294:4: ( (lv_elements_2_0= ruleTerm ) )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA7_0<=LeftParenthesisQuestionMarkEqualsSign)||(LA7_0>=ExclamationMark && LA7_0<=LeftParenthesis)||(LA7_0>=Comma && LA7_0<=FullStop)||(LA7_0>=Colon && LA7_0<=GreaterThanSign)||(LA7_0>=LeftSquareBracket && LA7_0<=CircumflexAccent)||LA7_0==LeftCurlyBracket||(LA7_0>=RightCurlyBracket && LA7_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA7_0>=RULE_HEX_ESCAPE && LA7_0<=RULE_UNICODE_ESCAPE)||(LA7_0>=RULE_DECIMAL_ESCAPE && LA7_0<=RULE_IDENTITY_ESCAPE)||LA7_0==RULE_UNICODE_DIGIT||(LA7_0>=RULE_UNICODE_LETTER && LA7_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalRegularExpressionParser.g:295:5: (lv_elements_2_0= ruleTerm )
                    	    {
                    	    // InternalRegularExpressionParser.g:295:5: (lv_elements_2_0= ruleTerm )
                    	    // InternalRegularExpressionParser.g:296:6: lv_elements_2_0= ruleTerm
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getAlternativeAccess().getElementsTermParserRuleCall_1_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_8);
                    	    lv_elements_2_0=ruleTerm();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getAlternativeRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"elements",
                    	      							lv_elements_2_0,
                    	      							"org.eclipse.n4js.regex.RegularExpression.Term");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);


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
    // $ANTLR end "ruleAlternative"


    // $ANTLR start "entryRuleTerm"
    // InternalRegularExpressionParser.g:318:1: entryRuleTerm returns [EObject current=null] : iv_ruleTerm= ruleTerm EOF ;
    public final EObject entryRuleTerm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTerm = null;


        try {
            // InternalRegularExpressionParser.g:318:45: (iv_ruleTerm= ruleTerm EOF )
            // InternalRegularExpressionParser.g:319:2: iv_ruleTerm= ruleTerm EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTermRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTerm=ruleTerm();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTerm; 
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
    // $ANTLR end "entryRuleTerm"


    // $ANTLR start "ruleTerm"
    // InternalRegularExpressionParser.g:325:1: ruleTerm returns [EObject current=null] : (this_Assertion_0= ruleAssertion | (this_Atom_1= ruleAtom ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )? ) ) ;
    public final EObject ruleTerm() throws RecognitionException {
        EObject current = null;

        EObject this_Assertion_0 = null;

        EObject this_Atom_1 = null;

        EObject lv_quantifier_2_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:331:2: ( (this_Assertion_0= ruleAssertion | (this_Atom_1= ruleAtom ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )? ) ) )
            // InternalRegularExpressionParser.g:332:2: (this_Assertion_0= ruleAssertion | (this_Atom_1= ruleAtom ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )? ) )
            {
            // InternalRegularExpressionParser.g:332:2: (this_Assertion_0= ruleAssertion | (this_Atom_1= ruleAtom ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )? ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA10_0<=LeftParenthesisQuestionMarkExclamationMark)||LA10_0==LeftParenthesisQuestionMarkEqualsSign||LA10_0==DollarSign||LA10_0==CircumflexAccent||(LA10_0>=RULE_WORD_BOUNDARY && LA10_0<=RULE_NOT_WORD_BOUNDARY)) ) {
                alt10=1;
            }
            else if ( ((LA10_0>=LeftParenthesisQuestionMarkColon && LA10_0<=LeftParenthesisQuestionMarkLessThanSign)||LA10_0==ExclamationMark||LA10_0==LeftParenthesis||(LA10_0>=Comma && LA10_0<=FullStop)||(LA10_0>=Colon && LA10_0<=GreaterThanSign)||(LA10_0>=LeftSquareBracket && LA10_0<=RightSquareBracket)||LA10_0==LeftCurlyBracket||LA10_0==RightCurlyBracket||(LA10_0>=RULE_CHARACTER_CLASS_ESCAPE && LA10_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA10_0>=RULE_HEX_ESCAPE && LA10_0<=RULE_UNICODE_ESCAPE)||(LA10_0>=RULE_DECIMAL_ESCAPE && LA10_0<=RULE_IDENTITY_ESCAPE)||LA10_0==RULE_UNICODE_DIGIT||(LA10_0>=RULE_UNICODE_LETTER && LA10_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalRegularExpressionParser.g:333:3: this_Assertion_0= ruleAssertion
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTermAccess().getAssertionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Assertion_0=ruleAssertion();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_Assertion_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:342:3: (this_Atom_1= ruleAtom ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )? )
                    {
                    // InternalRegularExpressionParser.g:342:3: (this_Atom_1= ruleAtom ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )? )
                    // InternalRegularExpressionParser.g:343:4: this_Atom_1= ruleAtom ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )?
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTermAccess().getAtomParserRuleCall_1_0());
                      			
                    }
                    pushFollow(FOLLOW_9);
                    this_Atom_1=ruleAtom();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Atom_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalRegularExpressionParser.g:351:4: ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )?
                    int alt9=2;
                    alt9 = dfa9.predict(input);
                    switch (alt9) {
                        case 1 :
                            // InternalRegularExpressionParser.g:352:5: ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier )
                            {
                            // InternalRegularExpressionParser.g:356:5: (lv_quantifier_2_0= ruleQuantifier )
                            // InternalRegularExpressionParser.g:357:6: lv_quantifier_2_0= ruleQuantifier
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getTermAccess().getQuantifierQuantifierParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_2);
                            lv_quantifier_2_0=ruleQuantifier();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getTermRule());
                              						}
                              						set(
                              							current,
                              							"quantifier",
                              							lv_quantifier_2_0,
                              							"org.eclipse.n4js.regex.RegularExpression.Quantifier");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

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
    // $ANTLR end "ruleTerm"


    // $ANTLR start "entryRuleAssertion"
    // InternalRegularExpressionParser.g:379:1: entryRuleAssertion returns [EObject current=null] : iv_ruleAssertion= ruleAssertion EOF ;
    public final EObject entryRuleAssertion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssertion = null;


        try {
            // InternalRegularExpressionParser.g:379:50: (iv_ruleAssertion= ruleAssertion EOF )
            // InternalRegularExpressionParser.g:380:2: iv_ruleAssertion= ruleAssertion EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAssertionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAssertion=ruleAssertion();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAssertion; 
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
    // $ANTLR end "entryRuleAssertion"


    // $ANTLR start "ruleAssertion"
    // InternalRegularExpressionParser.g:386:1: ruleAssertion returns [EObject current=null] : (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_AbstractLookAhead_3= ruleAbstractLookAhead ) ;
    public final EObject ruleAssertion() throws RecognitionException {
        EObject current = null;

        EObject this_LineStart_0 = null;

        EObject this_LineEnd_1 = null;

        EObject this_WordBoundary_2 = null;

        EObject this_AbstractLookAhead_3 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:392:2: ( (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_AbstractLookAhead_3= ruleAbstractLookAhead ) )
            // InternalRegularExpressionParser.g:393:2: (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_AbstractLookAhead_3= ruleAbstractLookAhead )
            {
            // InternalRegularExpressionParser.g:393:2: (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_AbstractLookAhead_3= ruleAbstractLookAhead )
            int alt11=4;
            switch ( input.LA(1) ) {
            case CircumflexAccent:
                {
                alt11=1;
                }
                break;
            case DollarSign:
                {
                alt11=2;
                }
                break;
            case RULE_WORD_BOUNDARY:
            case RULE_NOT_WORD_BOUNDARY:
                {
                alt11=3;
                }
                break;
            case LeftParenthesisQuestionMarkLessThanSignExclamationMark:
            case LeftParenthesisQuestionMarkLessThanSignEqualsSign:
            case LeftParenthesisQuestionMarkExclamationMark:
            case LeftParenthesisQuestionMarkEqualsSign:
                {
                alt11=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalRegularExpressionParser.g:394:3: this_LineStart_0= ruleLineStart
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAssertionAccess().getLineStartParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_LineStart_0=ruleLineStart();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_LineStart_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:403:3: this_LineEnd_1= ruleLineEnd
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAssertionAccess().getLineEndParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_LineEnd_1=ruleLineEnd();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_LineEnd_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:412:3: this_WordBoundary_2= ruleWordBoundary
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAssertionAccess().getWordBoundaryParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_WordBoundary_2=ruleWordBoundary();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_WordBoundary_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:421:3: this_AbstractLookAhead_3= ruleAbstractLookAhead
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAssertionAccess().getAbstractLookAheadParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_AbstractLookAhead_3=ruleAbstractLookAhead();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_AbstractLookAhead_3;
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
    // $ANTLR end "ruleAssertion"


    // $ANTLR start "entryRuleLineStart"
    // InternalRegularExpressionParser.g:433:1: entryRuleLineStart returns [EObject current=null] : iv_ruleLineStart= ruleLineStart EOF ;
    public final EObject entryRuleLineStart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLineStart = null;


        try {
            // InternalRegularExpressionParser.g:433:50: (iv_ruleLineStart= ruleLineStart EOF )
            // InternalRegularExpressionParser.g:434:2: iv_ruleLineStart= ruleLineStart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLineStartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLineStart=ruleLineStart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLineStart; 
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
    // $ANTLR end "entryRuleLineStart"


    // $ANTLR start "ruleLineStart"
    // InternalRegularExpressionParser.g:440:1: ruleLineStart returns [EObject current=null] : ( () otherlv_1= CircumflexAccent ) ;
    public final EObject ruleLineStart() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:446:2: ( ( () otherlv_1= CircumflexAccent ) )
            // InternalRegularExpressionParser.g:447:2: ( () otherlv_1= CircumflexAccent )
            {
            // InternalRegularExpressionParser.g:447:2: ( () otherlv_1= CircumflexAccent )
            // InternalRegularExpressionParser.g:448:3: () otherlv_1= CircumflexAccent
            {
            // InternalRegularExpressionParser.g:448:3: ()
            // InternalRegularExpressionParser.g:449:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getLineStartAccess().getLineStartAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLineStartAccess().getCircumflexAccentKeyword_1());
              		
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
    // $ANTLR end "ruleLineStart"


    // $ANTLR start "entryRuleLineEnd"
    // InternalRegularExpressionParser.g:463:1: entryRuleLineEnd returns [EObject current=null] : iv_ruleLineEnd= ruleLineEnd EOF ;
    public final EObject entryRuleLineEnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLineEnd = null;


        try {
            // InternalRegularExpressionParser.g:463:48: (iv_ruleLineEnd= ruleLineEnd EOF )
            // InternalRegularExpressionParser.g:464:2: iv_ruleLineEnd= ruleLineEnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLineEndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLineEnd=ruleLineEnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLineEnd; 
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
    // $ANTLR end "entryRuleLineEnd"


    // $ANTLR start "ruleLineEnd"
    // InternalRegularExpressionParser.g:470:1: ruleLineEnd returns [EObject current=null] : ( () otherlv_1= DollarSign ) ;
    public final EObject ruleLineEnd() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:476:2: ( ( () otherlv_1= DollarSign ) )
            // InternalRegularExpressionParser.g:477:2: ( () otherlv_1= DollarSign )
            {
            // InternalRegularExpressionParser.g:477:2: ( () otherlv_1= DollarSign )
            // InternalRegularExpressionParser.g:478:3: () otherlv_1= DollarSign
            {
            // InternalRegularExpressionParser.g:478:3: ()
            // InternalRegularExpressionParser.g:479:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getLineEndAccess().getLineEndAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,DollarSign,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLineEndAccess().getDollarSignKeyword_1());
              		
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
    // $ANTLR end "ruleLineEnd"


    // $ANTLR start "entryRuleWordBoundary"
    // InternalRegularExpressionParser.g:493:1: entryRuleWordBoundary returns [EObject current=null] : iv_ruleWordBoundary= ruleWordBoundary EOF ;
    public final EObject entryRuleWordBoundary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWordBoundary = null;


        try {
            // InternalRegularExpressionParser.g:493:53: (iv_ruleWordBoundary= ruleWordBoundary EOF )
            // InternalRegularExpressionParser.g:494:2: iv_ruleWordBoundary= ruleWordBoundary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWordBoundaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWordBoundary=ruleWordBoundary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWordBoundary; 
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
    // $ANTLR end "entryRuleWordBoundary"


    // $ANTLR start "ruleWordBoundary"
    // InternalRegularExpressionParser.g:500:1: ruleWordBoundary returns [EObject current=null] : ( () (this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY | ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) ) ) ) ;
    public final EObject ruleWordBoundary() throws RecognitionException {
        EObject current = null;

        Token this_WORD_BOUNDARY_1=null;
        Token lv_not_2_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:506:2: ( ( () (this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY | ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) ) ) ) )
            // InternalRegularExpressionParser.g:507:2: ( () (this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY | ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) ) ) )
            {
            // InternalRegularExpressionParser.g:507:2: ( () (this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY | ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) ) ) )
            // InternalRegularExpressionParser.g:508:3: () (this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY | ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) ) )
            {
            // InternalRegularExpressionParser.g:508:3: ()
            // InternalRegularExpressionParser.g:509:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getWordBoundaryAccess().getWordBoundaryAction_0(),
              					current);
              			
            }

            }

            // InternalRegularExpressionParser.g:515:3: (this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY | ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_WORD_BOUNDARY) ) {
                alt12=1;
            }
            else if ( (LA12_0==RULE_NOT_WORD_BOUNDARY) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalRegularExpressionParser.g:516:4: this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY
                    {
                    this_WORD_BOUNDARY_1=(Token)match(input,RULE_WORD_BOUNDARY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_WORD_BOUNDARY_1, grammarAccess.getWordBoundaryAccess().getWORD_BOUNDARYTerminalRuleCall_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:521:4: ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) )
                    {
                    // InternalRegularExpressionParser.g:521:4: ( (lv_not_2_0= RULE_NOT_WORD_BOUNDARY ) )
                    // InternalRegularExpressionParser.g:522:5: (lv_not_2_0= RULE_NOT_WORD_BOUNDARY )
                    {
                    // InternalRegularExpressionParser.g:522:5: (lv_not_2_0= RULE_NOT_WORD_BOUNDARY )
                    // InternalRegularExpressionParser.g:523:6: lv_not_2_0= RULE_NOT_WORD_BOUNDARY
                    {
                    lv_not_2_0=(Token)match(input,RULE_NOT_WORD_BOUNDARY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_not_2_0, grammarAccess.getWordBoundaryAccess().getNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getWordBoundaryRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"not",
                      							true,
                      							"org.eclipse.n4js.regex.RegularExpression.NOT_WORD_BOUNDARY");
                      					
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
    // $ANTLR end "ruleWordBoundary"


    // $ANTLR start "entryRuleAbstractLookAhead"
    // InternalRegularExpressionParser.g:544:1: entryRuleAbstractLookAhead returns [EObject current=null] : iv_ruleAbstractLookAhead= ruleAbstractLookAhead EOF ;
    public final EObject entryRuleAbstractLookAhead() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractLookAhead = null;


        try {
            // InternalRegularExpressionParser.g:544:58: (iv_ruleAbstractLookAhead= ruleAbstractLookAhead EOF )
            // InternalRegularExpressionParser.g:545:2: iv_ruleAbstractLookAhead= ruleAbstractLookAhead EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAbstractLookAheadRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAbstractLookAhead=ruleAbstractLookAhead();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAbstractLookAhead; 
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
    // $ANTLR end "entryRuleAbstractLookAhead"


    // $ANTLR start "ruleAbstractLookAhead"
    // InternalRegularExpressionParser.g:551:1: ruleAbstractLookAhead returns [EObject current=null] : ( ( ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) ) | ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis ) ;
    public final EObject ruleAbstractLookAhead() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_not_2_0=null;
        Token otherlv_4=null;
        Token lv_not_5_0=null;
        Token otherlv_7=null;
        EObject lv_pattern_6_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:557:2: ( ( ( ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) ) | ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis ) )
            // InternalRegularExpressionParser.g:558:2: ( ( ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) ) | ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis )
            {
            // InternalRegularExpressionParser.g:558:2: ( ( ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) ) | ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis )
            // InternalRegularExpressionParser.g:559:3: ( ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) ) | ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis
            {
            // InternalRegularExpressionParser.g:559:3: ( ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) ) | ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==LeftParenthesisQuestionMarkExclamationMark||LA15_0==LeftParenthesisQuestionMarkEqualsSign) ) {
                alt15=1;
            }
            else if ( ((LA15_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA15_0<=LeftParenthesisQuestionMarkLessThanSignEqualsSign)) ) {
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
                    // InternalRegularExpressionParser.g:560:4: ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) )
                    {
                    // InternalRegularExpressionParser.g:560:4: ( () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) ) )
                    // InternalRegularExpressionParser.g:561:5: () (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) )
                    {
                    // InternalRegularExpressionParser.g:561:5: ()
                    // InternalRegularExpressionParser.g:562:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElement(
                      							grammarAccess.getAbstractLookAheadAccess().getLookAheadAction_0_0_0(),
                      							current);
                      					
                    }

                    }

                    // InternalRegularExpressionParser.g:568:5: (otherlv_1= LeftParenthesisQuestionMarkEqualsSign | ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) ) )
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==LeftParenthesisQuestionMarkEqualsSign) ) {
                        alt13=1;
                    }
                    else if ( (LA13_0==LeftParenthesisQuestionMarkExclamationMark) ) {
                        alt13=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 0, input);

                        throw nvae;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalRegularExpressionParser.g:569:6: otherlv_1= LeftParenthesisQuestionMarkEqualsSign
                            {
                            otherlv_1=(Token)match(input,LeftParenthesisQuestionMarkEqualsSign,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getAbstractLookAheadAccess().getLeftParenthesisQuestionMarkEqualsSignKeyword_0_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalRegularExpressionParser.g:574:6: ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) )
                            {
                            // InternalRegularExpressionParser.g:574:6: ( (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark ) )
                            // InternalRegularExpressionParser.g:575:7: (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark )
                            {
                            // InternalRegularExpressionParser.g:575:7: (lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark )
                            // InternalRegularExpressionParser.g:576:8: lv_not_2_0= LeftParenthesisQuestionMarkExclamationMark
                            {
                            lv_not_2_0=(Token)match(input,LeftParenthesisQuestionMarkExclamationMark,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_not_2_0, grammarAccess.getAbstractLookAheadAccess().getNotLeftParenthesisQuestionMarkExclamationMarkKeyword_0_0_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getAbstractLookAheadRule());
                              								}
                              								setWithLastConsumed(current, "not", true, "(?!");
                              							
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:591:4: ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) )
                    {
                    // InternalRegularExpressionParser.g:591:4: ( () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) ) )
                    // InternalRegularExpressionParser.g:592:5: () (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) )
                    {
                    // InternalRegularExpressionParser.g:592:5: ()
                    // InternalRegularExpressionParser.g:593:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElement(
                      							grammarAccess.getAbstractLookAheadAccess().getLookBehindAction_0_1_0(),
                      							current);
                      					
                    }

                    }

                    // InternalRegularExpressionParser.g:599:5: (otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign | ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) ) )
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==LeftParenthesisQuestionMarkLessThanSignEqualsSign) ) {
                        alt14=1;
                    }
                    else if ( (LA14_0==LeftParenthesisQuestionMarkLessThanSignExclamationMark) ) {
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
                            // InternalRegularExpressionParser.g:600:6: otherlv_4= LeftParenthesisQuestionMarkLessThanSignEqualsSign
                            {
                            otherlv_4=(Token)match(input,LeftParenthesisQuestionMarkLessThanSignEqualsSign,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_4, grammarAccess.getAbstractLookAheadAccess().getLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_0_1_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalRegularExpressionParser.g:605:6: ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) )
                            {
                            // InternalRegularExpressionParser.g:605:6: ( (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark ) )
                            // InternalRegularExpressionParser.g:606:7: (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark )
                            {
                            // InternalRegularExpressionParser.g:606:7: (lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark )
                            // InternalRegularExpressionParser.g:607:8: lv_not_5_0= LeftParenthesisQuestionMarkLessThanSignExclamationMark
                            {
                            lv_not_5_0=(Token)match(input,LeftParenthesisQuestionMarkLessThanSignExclamationMark,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_not_5_0, grammarAccess.getAbstractLookAheadAccess().getNotLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_0_1_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getAbstractLookAheadRule());
                              								}
                              								setWithLastConsumed(current, "not", true, "(?<!");
                              							
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            // InternalRegularExpressionParser.g:622:3: ( (lv_pattern_6_0= ruleDisjunction ) )
            // InternalRegularExpressionParser.g:623:4: (lv_pattern_6_0= ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:623:4: (lv_pattern_6_0= ruleDisjunction )
            // InternalRegularExpressionParser.g:624:5: lv_pattern_6_0= ruleDisjunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getAbstractLookAheadAccess().getPatternDisjunctionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_11);
            lv_pattern_6_0=ruleDisjunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getAbstractLookAheadRule());
              					}
              					set(
              						current,
              						"pattern",
              						lv_pattern_6_0,
              						"org.eclipse.n4js.regex.RegularExpression.Disjunction");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_7=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getAbstractLookAheadAccess().getRightParenthesisKeyword_2());
              		
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
    // $ANTLR end "ruleAbstractLookAhead"


    // $ANTLR start "entryRuleAtom"
    // InternalRegularExpressionParser.g:649:1: entryRuleAtom returns [EObject current=null] : iv_ruleAtom= ruleAtom EOF ;
    public final EObject entryRuleAtom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtom = null;


        try {
            // InternalRegularExpressionParser.g:649:45: (iv_ruleAtom= ruleAtom EOF )
            // InternalRegularExpressionParser.g:650:2: iv_ruleAtom= ruleAtom EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAtomRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAtom=ruleAtom();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAtom; 
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
    // $ANTLR end "entryRuleAtom"


    // $ANTLR start "ruleAtom"
    // InternalRegularExpressionParser.g:656:1: ruleAtom returns [EObject current=null] : (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup ) ;
    public final EObject ruleAtom() throws RecognitionException {
        EObject current = null;

        EObject this_PatternCharacter_0 = null;

        EObject this_Wildcard_1 = null;

        EObject this_AtomEscape_2 = null;

        EObject this_CharacterClass_3 = null;

        EObject this_Group_4 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:662:2: ( (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup ) )
            // InternalRegularExpressionParser.g:663:2: (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup )
            {
            // InternalRegularExpressionParser.g:663:2: (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup )
            int alt16=5;
            switch ( input.LA(1) ) {
            case ExclamationMark:
            case Comma:
            case HyphenMinus:
            case Colon:
            case LessThanSign:
            case EqualsSign:
            case GreaterThanSign:
            case RightSquareBracket:
            case LeftCurlyBracket:
            case RightCurlyBracket:
            case RULE_UNICODE_DIGIT:
            case RULE_UNICODE_LETTER:
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt16=1;
                }
                break;
            case FullStop:
                {
                alt16=2;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
            case RULE_CONTROL_ESCAPE:
            case RULE_CONTROL_LETTER_ESCAPE:
            case RULE_HEX_ESCAPE:
            case RULE_UNICODE_ESCAPE:
            case RULE_DECIMAL_ESCAPE:
            case RULE_IDENTITY_ESCAPE:
                {
                alt16=3;
                }
                break;
            case LeftSquareBracket:
                {
                alt16=4;
                }
                break;
            case LeftParenthesisQuestionMarkColon:
            case LeftParenthesisQuestionMarkLessThanSign:
            case LeftParenthesis:
                {
                alt16=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // InternalRegularExpressionParser.g:664:3: this_PatternCharacter_0= rulePatternCharacter
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomAccess().getPatternCharacterParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_PatternCharacter_0=rulePatternCharacter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_PatternCharacter_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:673:3: this_Wildcard_1= ruleWildcard
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomAccess().getWildcardParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Wildcard_1=ruleWildcard();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_Wildcard_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:682:3: this_AtomEscape_2= ruleAtomEscape
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomAccess().getAtomEscapeParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_AtomEscape_2=ruleAtomEscape();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_AtomEscape_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:691:3: this_CharacterClass_3= ruleCharacterClass
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomAccess().getCharacterClassParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_CharacterClass_3=ruleCharacterClass();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_CharacterClass_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:700:3: this_Group_4= ruleGroup
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomAccess().getGroupParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Group_4=ruleGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_Group_4;
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
    // $ANTLR end "ruleAtom"


    // $ANTLR start "entryRulePatternCharacter"
    // InternalRegularExpressionParser.g:712:1: entryRulePatternCharacter returns [EObject current=null] : iv_rulePatternCharacter= rulePatternCharacter EOF ;
    public final EObject entryRulePatternCharacter() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatternCharacter = null;


        try {
            // InternalRegularExpressionParser.g:712:57: (iv_rulePatternCharacter= rulePatternCharacter EOF )
            // InternalRegularExpressionParser.g:713:2: iv_rulePatternCharacter= rulePatternCharacter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPatternCharacterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePatternCharacter=rulePatternCharacter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePatternCharacter; 
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
    // $ANTLR end "entryRulePatternCharacter"


    // $ANTLR start "rulePatternCharacter"
    // InternalRegularExpressionParser.g:719:1: rulePatternCharacter returns [EObject current=null] : ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign ) ) ) ;
    public final EObject rulePatternCharacter() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_1=null;
        Token lv_value_0_2=null;
        Token lv_value_0_3=null;
        Token lv_value_0_4=null;
        Token lv_value_0_5=null;
        Token lv_value_0_6=null;
        Token lv_value_0_7=null;
        Token lv_value_0_8=null;
        Token lv_value_0_9=null;
        Token lv_value_0_10=null;
        Token lv_value_0_11=null;
        Token lv_value_0_12=null;
        Token lv_value_0_13=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:725:2: ( ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign ) ) ) )
            // InternalRegularExpressionParser.g:726:2: ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign ) ) )
            {
            // InternalRegularExpressionParser.g:726:2: ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign ) ) )
            // InternalRegularExpressionParser.g:727:3: ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign ) )
            {
            // InternalRegularExpressionParser.g:727:3: ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign ) )
            // InternalRegularExpressionParser.g:728:4: (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign )
            {
            // InternalRegularExpressionParser.g:728:4: (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket | lv_value_0_12= LessThanSign | lv_value_0_13= GreaterThanSign )
            int alt17=13;
            switch ( input.LA(1) ) {
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt17=1;
                }
                break;
            case RULE_UNICODE_LETTER:
                {
                alt17=2;
                }
                break;
            case RULE_UNICODE_DIGIT:
                {
                alt17=3;
                }
                break;
            case HyphenMinus:
                {
                alt17=4;
                }
                break;
            case Comma:
                {
                alt17=5;
                }
                break;
            case EqualsSign:
                {
                alt17=6;
                }
                break;
            case Colon:
                {
                alt17=7;
                }
                break;
            case ExclamationMark:
                {
                alt17=8;
                }
                break;
            case LeftCurlyBracket:
                {
                alt17=9;
                }
                break;
            case RightCurlyBracket:
                {
                alt17=10;
                }
                break;
            case RightSquareBracket:
                {
                alt17=11;
                }
                break;
            case LessThanSign:
                {
                alt17=12;
                }
                break;
            case GreaterThanSign:
                {
                alt17=13;
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
                    // InternalRegularExpressionParser.g:729:5: lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH
                    {
                    lv_value_0_1=(Token)match(input,RULE_PATTERN_CHARACTER_NO_DASH,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_1, grammarAccess.getPatternCharacterAccess().getValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"value",
                      						lv_value_0_1,
                      						"org.eclipse.n4js.regex.RegularExpression.PATTERN_CHARACTER_NO_DASH");
                      				
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:744:5: lv_value_0_2= RULE_UNICODE_LETTER
                    {
                    lv_value_0_2=(Token)match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_2, grammarAccess.getPatternCharacterAccess().getValueUNICODE_LETTERTerminalRuleCall_0_1());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"value",
                      						lv_value_0_2,
                      						"org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
                      				
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:759:5: lv_value_0_3= RULE_UNICODE_DIGIT
                    {
                    lv_value_0_3=(Token)match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_3, grammarAccess.getPatternCharacterAccess().getValueUNICODE_DIGITTerminalRuleCall_0_2());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"value",
                      						lv_value_0_3,
                      						"org.eclipse.n4js.regex.RegularExpression.UNICODE_DIGIT");
                      				
                    }

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:774:5: lv_value_0_4= HyphenMinus
                    {
                    lv_value_0_4=(Token)match(input,HyphenMinus,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_4, grammarAccess.getPatternCharacterAccess().getValueHyphenMinusKeyword_0_3());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_4, null);
                      				
                    }

                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:785:5: lv_value_0_5= Comma
                    {
                    lv_value_0_5=(Token)match(input,Comma,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_5, grammarAccess.getPatternCharacterAccess().getValueCommaKeyword_0_4());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_5, null);
                      				
                    }

                    }
                    break;
                case 6 :
                    // InternalRegularExpressionParser.g:796:5: lv_value_0_6= EqualsSign
                    {
                    lv_value_0_6=(Token)match(input,EqualsSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_6, grammarAccess.getPatternCharacterAccess().getValueEqualsSignKeyword_0_5());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_6, null);
                      				
                    }

                    }
                    break;
                case 7 :
                    // InternalRegularExpressionParser.g:807:5: lv_value_0_7= Colon
                    {
                    lv_value_0_7=(Token)match(input,Colon,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_7, grammarAccess.getPatternCharacterAccess().getValueColonKeyword_0_6());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_7, null);
                      				
                    }

                    }
                    break;
                case 8 :
                    // InternalRegularExpressionParser.g:818:5: lv_value_0_8= ExclamationMark
                    {
                    lv_value_0_8=(Token)match(input,ExclamationMark,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_8, grammarAccess.getPatternCharacterAccess().getValueExclamationMarkKeyword_0_7());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_8, null);
                      				
                    }

                    }
                    break;
                case 9 :
                    // InternalRegularExpressionParser.g:829:5: lv_value_0_9= LeftCurlyBracket
                    {
                    lv_value_0_9=(Token)match(input,LeftCurlyBracket,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_9, grammarAccess.getPatternCharacterAccess().getValueLeftCurlyBracketKeyword_0_8());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_9, null);
                      				
                    }

                    }
                    break;
                case 10 :
                    // InternalRegularExpressionParser.g:840:5: lv_value_0_10= RightCurlyBracket
                    {
                    lv_value_0_10=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_10, grammarAccess.getPatternCharacterAccess().getValueRightCurlyBracketKeyword_0_9());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_10, null);
                      				
                    }

                    }
                    break;
                case 11 :
                    // InternalRegularExpressionParser.g:851:5: lv_value_0_11= RightSquareBracket
                    {
                    lv_value_0_11=(Token)match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_11, grammarAccess.getPatternCharacterAccess().getValueRightSquareBracketKeyword_0_10());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_11, null);
                      				
                    }

                    }
                    break;
                case 12 :
                    // InternalRegularExpressionParser.g:862:5: lv_value_0_12= LessThanSign
                    {
                    lv_value_0_12=(Token)match(input,LessThanSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_12, grammarAccess.getPatternCharacterAccess().getValueLessThanSignKeyword_0_11());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_12, null);
                      				
                    }

                    }
                    break;
                case 13 :
                    // InternalRegularExpressionParser.g:873:5: lv_value_0_13= GreaterThanSign
                    {
                    lv_value_0_13=(Token)match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_value_0_13, grammarAccess.getPatternCharacterAccess().getValueGreaterThanSignKeyword_0_12());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPatternCharacterRule());
                      					}
                      					setWithLastConsumed(current, "value", lv_value_0_13, null);
                      				
                    }

                    }
                    break;

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
    // $ANTLR end "rulePatternCharacter"


    // $ANTLR start "entryRuleWildcard"
    // InternalRegularExpressionParser.g:889:1: entryRuleWildcard returns [EObject current=null] : iv_ruleWildcard= ruleWildcard EOF ;
    public final EObject entryRuleWildcard() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWildcard = null;


        try {
            // InternalRegularExpressionParser.g:889:49: (iv_ruleWildcard= ruleWildcard EOF )
            // InternalRegularExpressionParser.g:890:2: iv_ruleWildcard= ruleWildcard EOF
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
    // InternalRegularExpressionParser.g:896:1: ruleWildcard returns [EObject current=null] : ( () otherlv_1= FullStop ) ;
    public final EObject ruleWildcard() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:902:2: ( ( () otherlv_1= FullStop ) )
            // InternalRegularExpressionParser.g:903:2: ( () otherlv_1= FullStop )
            {
            // InternalRegularExpressionParser.g:903:2: ( () otherlv_1= FullStop )
            // InternalRegularExpressionParser.g:904:3: () otherlv_1= FullStop
            {
            // InternalRegularExpressionParser.g:904:3: ()
            // InternalRegularExpressionParser.g:905:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getWildcardAccess().getWildcardAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,FullStop,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getWildcardAccess().getFullStopKeyword_1());
              		
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


    // $ANTLR start "entryRuleAtomEscape"
    // InternalRegularExpressionParser.g:919:1: entryRuleAtomEscape returns [EObject current=null] : iv_ruleAtomEscape= ruleAtomEscape EOF ;
    public final EObject entryRuleAtomEscape() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomEscape = null;


        try {
            // InternalRegularExpressionParser.g:919:51: (iv_ruleAtomEscape= ruleAtomEscape EOF )
            // InternalRegularExpressionParser.g:920:2: iv_ruleAtomEscape= ruleAtomEscape EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAtomEscapeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAtomEscape=ruleAtomEscape();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAtomEscape; 
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
    // $ANTLR end "entryRuleAtomEscape"


    // $ANTLR start "ruleAtomEscape"
    // InternalRegularExpressionParser.g:926:1: ruleAtomEscape returns [EObject current=null] : (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence ) ;
    public final EObject ruleAtomEscape() throws RecognitionException {
        EObject current = null;

        EObject this_DecimalEscapeSequence_0 = null;

        EObject this_CharacterEscapeSequence_1 = null;

        EObject this_ControlLetterEscapeSequence_2 = null;

        EObject this_HexEscapeSequence_3 = null;

        EObject this_UnicodeEscapeSequence_4 = null;

        EObject this_IdentityEscapeSequence_5 = null;

        EObject this_CharacterClassEscapeSequence_6 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:932:2: ( (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence ) )
            // InternalRegularExpressionParser.g:933:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence )
            {
            // InternalRegularExpressionParser.g:933:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence )
            int alt18=7;
            switch ( input.LA(1) ) {
            case RULE_DECIMAL_ESCAPE:
                {
                alt18=1;
                }
                break;
            case RULE_CONTROL_ESCAPE:
                {
                alt18=2;
                }
                break;
            case RULE_CONTROL_LETTER_ESCAPE:
                {
                alt18=3;
                }
                break;
            case RULE_HEX_ESCAPE:
                {
                alt18=4;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt18=5;
                }
                break;
            case RULE_IDENTITY_ESCAPE:
                {
                alt18=6;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
                {
                alt18=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalRegularExpressionParser.g:934:3: this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomEscapeAccess().getDecimalEscapeSequenceParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_DecimalEscapeSequence_0=ruleDecimalEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_DecimalEscapeSequence_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:943:3: this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomEscapeAccess().getCharacterEscapeSequenceParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_CharacterEscapeSequence_1=ruleCharacterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_CharacterEscapeSequence_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:952:3: this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomEscapeAccess().getControlLetterEscapeSequenceParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ControlLetterEscapeSequence_2=ruleControlLetterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ControlLetterEscapeSequence_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:961:3: this_HexEscapeSequence_3= ruleHexEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomEscapeAccess().getHexEscapeSequenceParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_HexEscapeSequence_3=ruleHexEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_HexEscapeSequence_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:970:3: this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomEscapeAccess().getUnicodeEscapeSequenceParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_UnicodeEscapeSequence_4=ruleUnicodeEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_UnicodeEscapeSequence_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalRegularExpressionParser.g:979:3: this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomEscapeAccess().getIdentityEscapeSequenceParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_IdentityEscapeSequence_5=ruleIdentityEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_IdentityEscapeSequence_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalRegularExpressionParser.g:988:3: this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAtomEscapeAccess().getCharacterClassEscapeSequenceParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_CharacterClassEscapeSequence_6=ruleCharacterClassEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_CharacterClassEscapeSequence_6;
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
    // $ANTLR end "ruleAtomEscape"


    // $ANTLR start "entryRuleCharacterClassEscapeSequence"
    // InternalRegularExpressionParser.g:1000:1: entryRuleCharacterClassEscapeSequence returns [EObject current=null] : iv_ruleCharacterClassEscapeSequence= ruleCharacterClassEscapeSequence EOF ;
    public final EObject entryRuleCharacterClassEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClassEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1000:69: (iv_ruleCharacterClassEscapeSequence= ruleCharacterClassEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1001:2: iv_ruleCharacterClassEscapeSequence= ruleCharacterClassEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCharacterClassEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCharacterClassEscapeSequence=ruleCharacterClassEscapeSequence();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCharacterClassEscapeSequence; 
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
    // $ANTLR end "entryRuleCharacterClassEscapeSequence"


    // $ANTLR start "ruleCharacterClassEscapeSequence"
    // InternalRegularExpressionParser.g:1007:1: ruleCharacterClassEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) ) ;
    public final EObject ruleCharacterClassEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1013:2: ( ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1014:2: ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1014:2: ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) )
            // InternalRegularExpressionParser.g:1015:3: (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1015:3: (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE )
            // InternalRegularExpressionParser.g:1016:4: lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE
            {
            lv_sequence_0_0=(Token)match(input,RULE_CHARACTER_CLASS_ESCAPE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_sequence_0_0, grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getCharacterClassEscapeSequenceRule());
              				}
              				setWithLastConsumed(
              					current,
              					"sequence",
              					lv_sequence_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.CHARACTER_CLASS_ESCAPE");
              			
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
    // $ANTLR end "ruleCharacterClassEscapeSequence"


    // $ANTLR start "entryRuleCharacterEscapeSequence"
    // InternalRegularExpressionParser.g:1035:1: entryRuleCharacterEscapeSequence returns [EObject current=null] : iv_ruleCharacterEscapeSequence= ruleCharacterEscapeSequence EOF ;
    public final EObject entryRuleCharacterEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1035:64: (iv_ruleCharacterEscapeSequence= ruleCharacterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1036:2: iv_ruleCharacterEscapeSequence= ruleCharacterEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCharacterEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCharacterEscapeSequence=ruleCharacterEscapeSequence();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCharacterEscapeSequence; 
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
    // $ANTLR end "entryRuleCharacterEscapeSequence"


    // $ANTLR start "ruleCharacterEscapeSequence"
    // InternalRegularExpressionParser.g:1042:1: ruleCharacterEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) ) ;
    public final EObject ruleCharacterEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1048:2: ( ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1049:2: ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1049:2: ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) )
            // InternalRegularExpressionParser.g:1050:3: (lv_sequence_0_0= RULE_CONTROL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1050:3: (lv_sequence_0_0= RULE_CONTROL_ESCAPE )
            // InternalRegularExpressionParser.g:1051:4: lv_sequence_0_0= RULE_CONTROL_ESCAPE
            {
            lv_sequence_0_0=(Token)match(input,RULE_CONTROL_ESCAPE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_sequence_0_0, grammarAccess.getCharacterEscapeSequenceAccess().getSequenceCONTROL_ESCAPETerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getCharacterEscapeSequenceRule());
              				}
              				setWithLastConsumed(
              					current,
              					"sequence",
              					lv_sequence_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.CONTROL_ESCAPE");
              			
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
    // $ANTLR end "ruleCharacterEscapeSequence"


    // $ANTLR start "entryRuleControlLetterEscapeSequence"
    // InternalRegularExpressionParser.g:1070:1: entryRuleControlLetterEscapeSequence returns [EObject current=null] : iv_ruleControlLetterEscapeSequence= ruleControlLetterEscapeSequence EOF ;
    public final EObject entryRuleControlLetterEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleControlLetterEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1070:68: (iv_ruleControlLetterEscapeSequence= ruleControlLetterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1071:2: iv_ruleControlLetterEscapeSequence= ruleControlLetterEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getControlLetterEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleControlLetterEscapeSequence=ruleControlLetterEscapeSequence();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleControlLetterEscapeSequence; 
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
    // $ANTLR end "entryRuleControlLetterEscapeSequence"


    // $ANTLR start "ruleControlLetterEscapeSequence"
    // InternalRegularExpressionParser.g:1077:1: ruleControlLetterEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) ) ;
    public final EObject ruleControlLetterEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1083:2: ( ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1084:2: ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1084:2: ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) )
            // InternalRegularExpressionParser.g:1085:3: (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1085:3: (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE )
            // InternalRegularExpressionParser.g:1086:4: lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE
            {
            lv_sequence_0_0=(Token)match(input,RULE_CONTROL_LETTER_ESCAPE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_sequence_0_0, grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getControlLetterEscapeSequenceRule());
              				}
              				setWithLastConsumed(
              					current,
              					"sequence",
              					lv_sequence_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.CONTROL_LETTER_ESCAPE");
              			
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
    // $ANTLR end "ruleControlLetterEscapeSequence"


    // $ANTLR start "entryRuleHexEscapeSequence"
    // InternalRegularExpressionParser.g:1105:1: entryRuleHexEscapeSequence returns [EObject current=null] : iv_ruleHexEscapeSequence= ruleHexEscapeSequence EOF ;
    public final EObject entryRuleHexEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHexEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1105:58: (iv_ruleHexEscapeSequence= ruleHexEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1106:2: iv_ruleHexEscapeSequence= ruleHexEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getHexEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleHexEscapeSequence=ruleHexEscapeSequence();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleHexEscapeSequence; 
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
    // $ANTLR end "entryRuleHexEscapeSequence"


    // $ANTLR start "ruleHexEscapeSequence"
    // InternalRegularExpressionParser.g:1112:1: ruleHexEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) ) ;
    public final EObject ruleHexEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1118:2: ( ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1119:2: ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1119:2: ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) )
            // InternalRegularExpressionParser.g:1120:3: (lv_sequence_0_0= RULE_HEX_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1120:3: (lv_sequence_0_0= RULE_HEX_ESCAPE )
            // InternalRegularExpressionParser.g:1121:4: lv_sequence_0_0= RULE_HEX_ESCAPE
            {
            lv_sequence_0_0=(Token)match(input,RULE_HEX_ESCAPE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_sequence_0_0, grammarAccess.getHexEscapeSequenceAccess().getSequenceHEX_ESCAPETerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getHexEscapeSequenceRule());
              				}
              				setWithLastConsumed(
              					current,
              					"sequence",
              					lv_sequence_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.HEX_ESCAPE");
              			
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
    // $ANTLR end "ruleHexEscapeSequence"


    // $ANTLR start "entryRuleUnicodeEscapeSequence"
    // InternalRegularExpressionParser.g:1140:1: entryRuleUnicodeEscapeSequence returns [EObject current=null] : iv_ruleUnicodeEscapeSequence= ruleUnicodeEscapeSequence EOF ;
    public final EObject entryRuleUnicodeEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnicodeEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1140:62: (iv_ruleUnicodeEscapeSequence= ruleUnicodeEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1141:2: iv_ruleUnicodeEscapeSequence= ruleUnicodeEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnicodeEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnicodeEscapeSequence=ruleUnicodeEscapeSequence();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnicodeEscapeSequence; 
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
    // $ANTLR end "entryRuleUnicodeEscapeSequence"


    // $ANTLR start "ruleUnicodeEscapeSequence"
    // InternalRegularExpressionParser.g:1147:1: ruleUnicodeEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) ) ;
    public final EObject ruleUnicodeEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1153:2: ( ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1154:2: ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1154:2: ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) )
            // InternalRegularExpressionParser.g:1155:3: (lv_sequence_0_0= RULE_UNICODE_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1155:3: (lv_sequence_0_0= RULE_UNICODE_ESCAPE )
            // InternalRegularExpressionParser.g:1156:4: lv_sequence_0_0= RULE_UNICODE_ESCAPE
            {
            lv_sequence_0_0=(Token)match(input,RULE_UNICODE_ESCAPE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_sequence_0_0, grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceUNICODE_ESCAPETerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getUnicodeEscapeSequenceRule());
              				}
              				setWithLastConsumed(
              					current,
              					"sequence",
              					lv_sequence_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.UNICODE_ESCAPE");
              			
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
    // $ANTLR end "ruleUnicodeEscapeSequence"


    // $ANTLR start "entryRuleIdentityEscapeSequence"
    // InternalRegularExpressionParser.g:1175:1: entryRuleIdentityEscapeSequence returns [EObject current=null] : iv_ruleIdentityEscapeSequence= ruleIdentityEscapeSequence EOF ;
    public final EObject entryRuleIdentityEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIdentityEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1175:63: (iv_ruleIdentityEscapeSequence= ruleIdentityEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1176:2: iv_ruleIdentityEscapeSequence= ruleIdentityEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIdentityEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIdentityEscapeSequence=ruleIdentityEscapeSequence();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIdentityEscapeSequence; 
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
    // $ANTLR end "entryRuleIdentityEscapeSequence"


    // $ANTLR start "ruleIdentityEscapeSequence"
    // InternalRegularExpressionParser.g:1182:1: ruleIdentityEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) ) ;
    public final EObject ruleIdentityEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1188:2: ( ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1189:2: ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1189:2: ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) )
            // InternalRegularExpressionParser.g:1190:3: (lv_sequence_0_0= RULE_IDENTITY_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1190:3: (lv_sequence_0_0= RULE_IDENTITY_ESCAPE )
            // InternalRegularExpressionParser.g:1191:4: lv_sequence_0_0= RULE_IDENTITY_ESCAPE
            {
            lv_sequence_0_0=(Token)match(input,RULE_IDENTITY_ESCAPE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_sequence_0_0, grammarAccess.getIdentityEscapeSequenceAccess().getSequenceIDENTITY_ESCAPETerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getIdentityEscapeSequenceRule());
              				}
              				setWithLastConsumed(
              					current,
              					"sequence",
              					lv_sequence_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.IDENTITY_ESCAPE");
              			
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
    // $ANTLR end "ruleIdentityEscapeSequence"


    // $ANTLR start "entryRuleDecimalEscapeSequence"
    // InternalRegularExpressionParser.g:1210:1: entryRuleDecimalEscapeSequence returns [EObject current=null] : iv_ruleDecimalEscapeSequence= ruleDecimalEscapeSequence EOF ;
    public final EObject entryRuleDecimalEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecimalEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1210:62: (iv_ruleDecimalEscapeSequence= ruleDecimalEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1211:2: iv_ruleDecimalEscapeSequence= ruleDecimalEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDecimalEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDecimalEscapeSequence=ruleDecimalEscapeSequence();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDecimalEscapeSequence; 
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
    // $ANTLR end "entryRuleDecimalEscapeSequence"


    // $ANTLR start "ruleDecimalEscapeSequence"
    // InternalRegularExpressionParser.g:1217:1: ruleDecimalEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) ) ;
    public final EObject ruleDecimalEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1223:2: ( ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1224:2: ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1224:2: ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) )
            // InternalRegularExpressionParser.g:1225:3: (lv_sequence_0_0= RULE_DECIMAL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1225:3: (lv_sequence_0_0= RULE_DECIMAL_ESCAPE )
            // InternalRegularExpressionParser.g:1226:4: lv_sequence_0_0= RULE_DECIMAL_ESCAPE
            {
            lv_sequence_0_0=(Token)match(input,RULE_DECIMAL_ESCAPE,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_sequence_0_0, grammarAccess.getDecimalEscapeSequenceAccess().getSequenceDECIMAL_ESCAPETerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getDecimalEscapeSequenceRule());
              				}
              				setWithLastConsumed(
              					current,
              					"sequence",
              					lv_sequence_0_0,
              					"org.eclipse.n4js.regex.RegularExpression.DECIMAL_ESCAPE");
              			
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
    // $ANTLR end "ruleDecimalEscapeSequence"


    // $ANTLR start "entryRuleCharacterClass"
    // InternalRegularExpressionParser.g:1245:1: entryRuleCharacterClass returns [EObject current=null] : iv_ruleCharacterClass= ruleCharacterClass EOF ;
    public final EObject entryRuleCharacterClass() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClass = null;


        try {
            // InternalRegularExpressionParser.g:1245:55: (iv_ruleCharacterClass= ruleCharacterClass EOF )
            // InternalRegularExpressionParser.g:1246:2: iv_ruleCharacterClass= ruleCharacterClass EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCharacterClassRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCharacterClass=ruleCharacterClass();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCharacterClass; 
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
    // $ANTLR end "entryRuleCharacterClass"


    // $ANTLR start "ruleCharacterClass"
    // InternalRegularExpressionParser.g:1252:1: ruleCharacterClass returns [EObject current=null] : ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleCharacterClass() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_negated_2_0=null;
        Token otherlv_4=null;
        EObject lv_elements_3_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1258:2: ( ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket ) )
            // InternalRegularExpressionParser.g:1259:2: ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket )
            {
            // InternalRegularExpressionParser.g:1259:2: ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket )
            // InternalRegularExpressionParser.g:1260:3: () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket
            {
            // InternalRegularExpressionParser.g:1260:3: ()
            // InternalRegularExpressionParser.g:1261:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getCharacterClassAccess().getCharacterClassAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftSquareBracket,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getCharacterClassAccess().getLeftSquareBracketKeyword_1());
              		
            }
            // InternalRegularExpressionParser.g:1271:3: ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==CircumflexAccent) ) {
                int LA19_1 = input.LA(2);

                if ( (synpred2_InternalRegularExpressionParser()) ) {
                    alt19=1;
                }
            }
            switch (alt19) {
                case 1 :
                    // InternalRegularExpressionParser.g:1272:4: ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) )
                    {
                    // InternalRegularExpressionParser.g:1278:4: ( (lv_negated_2_0= CircumflexAccent ) )
                    // InternalRegularExpressionParser.g:1279:5: (lv_negated_2_0= CircumflexAccent )
                    {
                    // InternalRegularExpressionParser.g:1279:5: (lv_negated_2_0= CircumflexAccent )
                    // InternalRegularExpressionParser.g:1280:6: lv_negated_2_0= CircumflexAccent
                    {
                    lv_negated_2_0=(Token)match(input,CircumflexAccent,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_negated_2_0, grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getCharacterClassRule());
                      						}
                      						setWithLastConsumed(current, "negated", true, "^");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalRegularExpressionParser.g:1293:3: ( (lv_elements_3_0= ruleCharacterClassElement ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA20_0<=LeftParenthesisQuestionMarkExclamationMark)||(LA20_0>=LeftParenthesisQuestionMarkLessThanSign && LA20_0<=LeftSquareBracket)||LA20_0==CircumflexAccent||(LA20_0>=LeftCurlyBracket && LA20_0<=RULE_WORD_BOUNDARY)||(LA20_0>=RULE_CHARACTER_CLASS_ESCAPE && LA20_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA20_0>=RULE_HEX_ESCAPE && LA20_0<=RULE_UNICODE_ESCAPE)||(LA20_0>=RULE_DECIMAL_ESCAPE && LA20_0<=RULE_IDENTITY_ESCAPE)||LA20_0==RULE_UNICODE_DIGIT||(LA20_0>=RULE_UNICODE_LETTER && LA20_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:1294:4: (lv_elements_3_0= ruleCharacterClassElement )
            	    {
            	    // InternalRegularExpressionParser.g:1294:4: (lv_elements_3_0= ruleCharacterClassElement )
            	    // InternalRegularExpressionParser.g:1295:5: lv_elements_3_0= ruleCharacterClassElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getCharacterClassAccess().getElementsCharacterClassElementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
            	    lv_elements_3_0=ruleCharacterClassElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getCharacterClassRule());
            	      					}
            	      					add(
            	      						current,
            	      						"elements",
            	      						lv_elements_3_0,
            	      						"org.eclipse.n4js.regex.RegularExpression.CharacterClassElement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            otherlv_4=(Token)match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getCharacterClassAccess().getRightSquareBracketKeyword_4());
              		
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
    // $ANTLR end "ruleCharacterClass"


    // $ANTLR start "entryRuleCharacterClassElement"
    // InternalRegularExpressionParser.g:1320:1: entryRuleCharacterClassElement returns [EObject current=null] : iv_ruleCharacterClassElement= ruleCharacterClassElement EOF ;
    public final EObject entryRuleCharacterClassElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClassElement = null;


        try {
            // InternalRegularExpressionParser.g:1320:62: (iv_ruleCharacterClassElement= ruleCharacterClassElement EOF )
            // InternalRegularExpressionParser.g:1321:2: iv_ruleCharacterClassElement= ruleCharacterClassElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCharacterClassElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCharacterClassElement=ruleCharacterClassElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCharacterClassElement; 
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
    // $ANTLR end "entryRuleCharacterClassElement"


    // $ANTLR start "ruleCharacterClassElement"
    // InternalRegularExpressionParser.g:1327:1: ruleCharacterClassElement returns [EObject current=null] : (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? ) ;
    public final EObject ruleCharacterClassElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_CharacterClassAtom_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1333:2: ( (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? ) )
            // InternalRegularExpressionParser.g:1334:2: (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? )
            {
            // InternalRegularExpressionParser.g:1334:2: (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? )
            // InternalRegularExpressionParser.g:1335:3: this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCharacterClassElementAccess().getCharacterClassAtomParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_13);
            this_CharacterClassAtom_0=ruleCharacterClassAtom();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_CharacterClassAtom_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalRegularExpressionParser.g:1343:3: ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )?
            int alt21=2;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // InternalRegularExpressionParser.g:1344:4: ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) )
                    {
                    // InternalRegularExpressionParser.g:1355:4: ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) )
                    // InternalRegularExpressionParser.g:1356:5: () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) )
                    {
                    // InternalRegularExpressionParser.g:1356:5: ()
                    // InternalRegularExpressionParser.g:1357:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }

                    otherlv_2=(Token)match(input,HyphenMinus,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getCharacterClassElementAccess().getHyphenMinusKeyword_1_0_1());
                      				
                    }
                    // InternalRegularExpressionParser.g:1367:5: ( (lv_right_3_0= ruleCharacterClassAtom ) )
                    // InternalRegularExpressionParser.g:1368:6: (lv_right_3_0= ruleCharacterClassAtom )
                    {
                    // InternalRegularExpressionParser.g:1368:6: (lv_right_3_0= ruleCharacterClassAtom )
                    // InternalRegularExpressionParser.g:1369:7: lv_right_3_0= ruleCharacterClassAtom
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getCharacterClassElementAccess().getRightCharacterClassAtomParserRuleCall_1_0_2_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleCharacterClassAtom();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getCharacterClassElementRule());
                      							}
                      							set(
                      								current,
                      								"right",
                      								lv_right_3_0,
                      								"org.eclipse.n4js.regex.RegularExpression.CharacterClassAtom");
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
    // $ANTLR end "ruleCharacterClassElement"


    // $ANTLR start "entryRuleCharacterClassAtom"
    // InternalRegularExpressionParser.g:1392:1: entryRuleCharacterClassAtom returns [EObject current=null] : iv_ruleCharacterClassAtom= ruleCharacterClassAtom EOF ;
    public final EObject entryRuleCharacterClassAtom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClassAtom = null;


        try {
            // InternalRegularExpressionParser.g:1392:59: (iv_ruleCharacterClassAtom= ruleCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:1393:2: iv_ruleCharacterClassAtom= ruleCharacterClassAtom EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCharacterClassAtomRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCharacterClassAtom=ruleCharacterClassAtom();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCharacterClassAtom; 
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
    // $ANTLR end "entryRuleCharacterClassAtom"


    // $ANTLR start "ruleCharacterClassAtom"
    // InternalRegularExpressionParser.g:1399:1: ruleCharacterClassAtom returns [EObject current=null] : (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) ) ) ) ;
    public final EObject ruleCharacterClassAtom() throws RecognitionException {
        EObject current = null;

        Token lv_characters_1_1=null;
        Token lv_characters_1_2=null;
        Token lv_characters_1_3=null;
        Token lv_characters_1_4=null;
        Token lv_characters_1_5=null;
        Token lv_characters_1_6=null;
        Token lv_characters_1_7=null;
        Token lv_characters_1_8=null;
        Token lv_characters_1_9=null;
        Token lv_characters_1_10=null;
        Token lv_characters_1_11=null;
        Token lv_characters_1_12=null;
        Token lv_characters_1_13=null;
        Token lv_characters_1_14=null;
        Token lv_characters_1_15=null;
        Token lv_characters_1_16=null;
        Token lv_characters_1_17=null;
        Token lv_characters_1_18=null;
        Token lv_characters_1_19=null;
        Token lv_characters_1_20=null;
        Token lv_characters_1_21=null;
        Token lv_characters_1_22=null;
        Token lv_characters_1_23=null;
        Token lv_characters_1_24=null;
        Token lv_characters_1_25=null;
        Token lv_characters_1_26=null;
        Token lv_characters_1_27=null;
        Token lv_characters_1_28=null;
        Token lv_characters_1_29=null;
        EObject this_EscapedCharacterClassAtom_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1405:2: ( (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) ) ) ) )
            // InternalRegularExpressionParser.g:1406:2: (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) ) ) )
            {
            // InternalRegularExpressionParser.g:1406:2: (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) ) ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_WORD_BOUNDARY||(LA23_0>=RULE_CHARACTER_CLASS_ESCAPE && LA23_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA23_0>=RULE_HEX_ESCAPE && LA23_0<=RULE_UNICODE_ESCAPE)||(LA23_0>=RULE_DECIMAL_ESCAPE && LA23_0<=RULE_IDENTITY_ESCAPE)) ) {
                alt23=1;
            }
            else if ( ((LA23_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA23_0<=LeftParenthesisQuestionMarkExclamationMark)||(LA23_0>=LeftParenthesisQuestionMarkLessThanSign && LA23_0<=LeftSquareBracket)||LA23_0==CircumflexAccent||(LA23_0>=LeftCurlyBracket && LA23_0<=RightCurlyBracket)||LA23_0==RULE_UNICODE_DIGIT||(LA23_0>=RULE_UNICODE_LETTER && LA23_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // InternalRegularExpressionParser.g:1407:3: this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getCharacterClassAtomAccess().getEscapedCharacterClassAtomParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_EscapedCharacterClassAtom_0=ruleEscapedCharacterClassAtom();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_EscapedCharacterClassAtom_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1416:3: ( ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) ) )
                    {
                    // InternalRegularExpressionParser.g:1416:3: ( ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) ) )
                    // InternalRegularExpressionParser.g:1417:4: ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) )
                    {
                    // InternalRegularExpressionParser.g:1417:4: ( (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT ) )
                    // InternalRegularExpressionParser.g:1418:5: (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT )
                    {
                    // InternalRegularExpressionParser.g:1418:5: (lv_characters_1_1= Comma | lv_characters_1_2= EqualsSign | lv_characters_1_3= Colon | lv_characters_1_4= ExclamationMark | lv_characters_1_5= HyphenMinus | lv_characters_1_6= CircumflexAccent | lv_characters_1_7= DollarSign | lv_characters_1_8= FullStop | lv_characters_1_9= Asterisk | lv_characters_1_10= PlusSign | lv_characters_1_11= QuestionMark | lv_characters_1_12= LeftParenthesis | lv_characters_1_13= RightParenthesis | lv_characters_1_14= LeftSquareBracket | lv_characters_1_15= LeftCurlyBracket | lv_characters_1_16= RightCurlyBracket | lv_characters_1_17= VerticalLine | lv_characters_1_18= Solidus | lv_characters_1_19= LessThanSign | lv_characters_1_20= GreaterThanSign | lv_characters_1_21= LeftParenthesisQuestionMark | lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign | lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign | lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark | lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark | lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign | lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH | lv_characters_1_28= RULE_UNICODE_LETTER | lv_characters_1_29= RULE_UNICODE_DIGIT )
                    int alt22=29;
                    switch ( input.LA(1) ) {
                    case Comma:
                        {
                        alt22=1;
                        }
                        break;
                    case EqualsSign:
                        {
                        alt22=2;
                        }
                        break;
                    case Colon:
                        {
                        alt22=3;
                        }
                        break;
                    case ExclamationMark:
                        {
                        alt22=4;
                        }
                        break;
                    case HyphenMinus:
                        {
                        alt22=5;
                        }
                        break;
                    case CircumflexAccent:
                        {
                        alt22=6;
                        }
                        break;
                    case DollarSign:
                        {
                        alt22=7;
                        }
                        break;
                    case FullStop:
                        {
                        alt22=8;
                        }
                        break;
                    case Asterisk:
                        {
                        alt22=9;
                        }
                        break;
                    case PlusSign:
                        {
                        alt22=10;
                        }
                        break;
                    case QuestionMark:
                        {
                        alt22=11;
                        }
                        break;
                    case LeftParenthesis:
                        {
                        alt22=12;
                        }
                        break;
                    case RightParenthesis:
                        {
                        alt22=13;
                        }
                        break;
                    case LeftSquareBracket:
                        {
                        alt22=14;
                        }
                        break;
                    case LeftCurlyBracket:
                        {
                        alt22=15;
                        }
                        break;
                    case RightCurlyBracket:
                        {
                        alt22=16;
                        }
                        break;
                    case VerticalLine:
                        {
                        alt22=17;
                        }
                        break;
                    case Solidus:
                        {
                        alt22=18;
                        }
                        break;
                    case LessThanSign:
                        {
                        alt22=19;
                        }
                        break;
                    case GreaterThanSign:
                        {
                        alt22=20;
                        }
                        break;
                    case LeftParenthesisQuestionMark:
                        {
                        alt22=21;
                        }
                        break;
                    case LeftParenthesisQuestionMarkLessThanSign:
                        {
                        alt22=22;
                        }
                        break;
                    case LeftParenthesisQuestionMarkEqualsSign:
                        {
                        alt22=23;
                        }
                        break;
                    case LeftParenthesisQuestionMarkExclamationMark:
                        {
                        alt22=24;
                        }
                        break;
                    case LeftParenthesisQuestionMarkLessThanSignExclamationMark:
                        {
                        alt22=25;
                        }
                        break;
                    case LeftParenthesisQuestionMarkLessThanSignEqualsSign:
                        {
                        alt22=26;
                        }
                        break;
                    case RULE_PATTERN_CHARACTER_NO_DASH:
                        {
                        alt22=27;
                        }
                        break;
                    case RULE_UNICODE_LETTER:
                        {
                        alt22=28;
                        }
                        break;
                    case RULE_UNICODE_DIGIT:
                        {
                        alt22=29;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 22, 0, input);

                        throw nvae;
                    }

                    switch (alt22) {
                        case 1 :
                            // InternalRegularExpressionParser.g:1419:6: lv_characters_1_1= Comma
                            {
                            lv_characters_1_1=(Token)match(input,Comma,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_1, grammarAccess.getCharacterClassAtomAccess().getCharactersCommaKeyword_1_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalRegularExpressionParser.g:1430:6: lv_characters_1_2= EqualsSign
                            {
                            lv_characters_1_2=(Token)match(input,EqualsSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_2, grammarAccess.getCharacterClassAtomAccess().getCharactersEqualsSignKeyword_1_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_2, null);
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalRegularExpressionParser.g:1441:6: lv_characters_1_3= Colon
                            {
                            lv_characters_1_3=(Token)match(input,Colon,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_3, grammarAccess.getCharacterClassAtomAccess().getCharactersColonKeyword_1_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_3, null);
                              					
                            }

                            }
                            break;
                        case 4 :
                            // InternalRegularExpressionParser.g:1452:6: lv_characters_1_4= ExclamationMark
                            {
                            lv_characters_1_4=(Token)match(input,ExclamationMark,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_4, grammarAccess.getCharacterClassAtomAccess().getCharactersExclamationMarkKeyword_1_0_3());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_4, null);
                              					
                            }

                            }
                            break;
                        case 5 :
                            // InternalRegularExpressionParser.g:1463:6: lv_characters_1_5= HyphenMinus
                            {
                            lv_characters_1_5=(Token)match(input,HyphenMinus,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_5, grammarAccess.getCharacterClassAtomAccess().getCharactersHyphenMinusKeyword_1_0_4());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_5, null);
                              					
                            }

                            }
                            break;
                        case 6 :
                            // InternalRegularExpressionParser.g:1474:6: lv_characters_1_6= CircumflexAccent
                            {
                            lv_characters_1_6=(Token)match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_6, grammarAccess.getCharacterClassAtomAccess().getCharactersCircumflexAccentKeyword_1_0_5());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_6, null);
                              					
                            }

                            }
                            break;
                        case 7 :
                            // InternalRegularExpressionParser.g:1485:6: lv_characters_1_7= DollarSign
                            {
                            lv_characters_1_7=(Token)match(input,DollarSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_7, grammarAccess.getCharacterClassAtomAccess().getCharactersDollarSignKeyword_1_0_6());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_7, null);
                              					
                            }

                            }
                            break;
                        case 8 :
                            // InternalRegularExpressionParser.g:1496:6: lv_characters_1_8= FullStop
                            {
                            lv_characters_1_8=(Token)match(input,FullStop,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_8, grammarAccess.getCharacterClassAtomAccess().getCharactersFullStopKeyword_1_0_7());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_8, null);
                              					
                            }

                            }
                            break;
                        case 9 :
                            // InternalRegularExpressionParser.g:1507:6: lv_characters_1_9= Asterisk
                            {
                            lv_characters_1_9=(Token)match(input,Asterisk,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_9, grammarAccess.getCharacterClassAtomAccess().getCharactersAsteriskKeyword_1_0_8());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_9, null);
                              					
                            }

                            }
                            break;
                        case 10 :
                            // InternalRegularExpressionParser.g:1518:6: lv_characters_1_10= PlusSign
                            {
                            lv_characters_1_10=(Token)match(input,PlusSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_10, grammarAccess.getCharacterClassAtomAccess().getCharactersPlusSignKeyword_1_0_9());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_10, null);
                              					
                            }

                            }
                            break;
                        case 11 :
                            // InternalRegularExpressionParser.g:1529:6: lv_characters_1_11= QuestionMark
                            {
                            lv_characters_1_11=(Token)match(input,QuestionMark,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_11, grammarAccess.getCharacterClassAtomAccess().getCharactersQuestionMarkKeyword_1_0_10());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_11, null);
                              					
                            }

                            }
                            break;
                        case 12 :
                            // InternalRegularExpressionParser.g:1540:6: lv_characters_1_12= LeftParenthesis
                            {
                            lv_characters_1_12=(Token)match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_12, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisKeyword_1_0_11());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_12, null);
                              					
                            }

                            }
                            break;
                        case 13 :
                            // InternalRegularExpressionParser.g:1551:6: lv_characters_1_13= RightParenthesis
                            {
                            lv_characters_1_13=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_13, grammarAccess.getCharacterClassAtomAccess().getCharactersRightParenthesisKeyword_1_0_12());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_13, null);
                              					
                            }

                            }
                            break;
                        case 14 :
                            // InternalRegularExpressionParser.g:1562:6: lv_characters_1_14= LeftSquareBracket
                            {
                            lv_characters_1_14=(Token)match(input,LeftSquareBracket,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_14, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftSquareBracketKeyword_1_0_13());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_14, null);
                              					
                            }

                            }
                            break;
                        case 15 :
                            // InternalRegularExpressionParser.g:1573:6: lv_characters_1_15= LeftCurlyBracket
                            {
                            lv_characters_1_15=(Token)match(input,LeftCurlyBracket,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_15, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftCurlyBracketKeyword_1_0_14());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_15, null);
                              					
                            }

                            }
                            break;
                        case 16 :
                            // InternalRegularExpressionParser.g:1584:6: lv_characters_1_16= RightCurlyBracket
                            {
                            lv_characters_1_16=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_16, grammarAccess.getCharacterClassAtomAccess().getCharactersRightCurlyBracketKeyword_1_0_15());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_16, null);
                              					
                            }

                            }
                            break;
                        case 17 :
                            // InternalRegularExpressionParser.g:1595:6: lv_characters_1_17= VerticalLine
                            {
                            lv_characters_1_17=(Token)match(input,VerticalLine,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_17, grammarAccess.getCharacterClassAtomAccess().getCharactersVerticalLineKeyword_1_0_16());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_17, null);
                              					
                            }

                            }
                            break;
                        case 18 :
                            // InternalRegularExpressionParser.g:1606:6: lv_characters_1_18= Solidus
                            {
                            lv_characters_1_18=(Token)match(input,Solidus,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_18, grammarAccess.getCharacterClassAtomAccess().getCharactersSolidusKeyword_1_0_17());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_18, null);
                              					
                            }

                            }
                            break;
                        case 19 :
                            // InternalRegularExpressionParser.g:1617:6: lv_characters_1_19= LessThanSign
                            {
                            lv_characters_1_19=(Token)match(input,LessThanSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_19, grammarAccess.getCharacterClassAtomAccess().getCharactersLessThanSignKeyword_1_0_18());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_19, null);
                              					
                            }

                            }
                            break;
                        case 20 :
                            // InternalRegularExpressionParser.g:1628:6: lv_characters_1_20= GreaterThanSign
                            {
                            lv_characters_1_20=(Token)match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_20, grammarAccess.getCharacterClassAtomAccess().getCharactersGreaterThanSignKeyword_1_0_19());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_20, null);
                              					
                            }

                            }
                            break;
                        case 21 :
                            // InternalRegularExpressionParser.g:1639:6: lv_characters_1_21= LeftParenthesisQuestionMark
                            {
                            lv_characters_1_21=(Token)match(input,LeftParenthesisQuestionMark,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_21, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkKeyword_1_0_20());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_21, null);
                              					
                            }

                            }
                            break;
                        case 22 :
                            // InternalRegularExpressionParser.g:1650:6: lv_characters_1_22= LeftParenthesisQuestionMarkLessThanSign
                            {
                            lv_characters_1_22=(Token)match(input,LeftParenthesisQuestionMarkLessThanSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_22, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkLessThanSignKeyword_1_0_21());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_22, null);
                              					
                            }

                            }
                            break;
                        case 23 :
                            // InternalRegularExpressionParser.g:1661:6: lv_characters_1_23= LeftParenthesisQuestionMarkEqualsSign
                            {
                            lv_characters_1_23=(Token)match(input,LeftParenthesisQuestionMarkEqualsSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_23, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkEqualsSignKeyword_1_0_22());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_23, null);
                              					
                            }

                            }
                            break;
                        case 24 :
                            // InternalRegularExpressionParser.g:1672:6: lv_characters_1_24= LeftParenthesisQuestionMarkExclamationMark
                            {
                            lv_characters_1_24=(Token)match(input,LeftParenthesisQuestionMarkExclamationMark,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_24, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkExclamationMarkKeyword_1_0_23());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_24, null);
                              					
                            }

                            }
                            break;
                        case 25 :
                            // InternalRegularExpressionParser.g:1683:6: lv_characters_1_25= LeftParenthesisQuestionMarkLessThanSignExclamationMark
                            {
                            lv_characters_1_25=(Token)match(input,LeftParenthesisQuestionMarkLessThanSignExclamationMark,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_25, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_1_0_24());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_25, null);
                              					
                            }

                            }
                            break;
                        case 26 :
                            // InternalRegularExpressionParser.g:1694:6: lv_characters_1_26= LeftParenthesisQuestionMarkLessThanSignEqualsSign
                            {
                            lv_characters_1_26=(Token)match(input,LeftParenthesisQuestionMarkLessThanSignEqualsSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_26, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_1_0_25());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "characters", lv_characters_1_26, null);
                              					
                            }

                            }
                            break;
                        case 27 :
                            // InternalRegularExpressionParser.g:1705:6: lv_characters_1_27= RULE_PATTERN_CHARACTER_NO_DASH
                            {
                            lv_characters_1_27=(Token)match(input,RULE_PATTERN_CHARACTER_NO_DASH,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_27, grammarAccess.getCharacterClassAtomAccess().getCharactersPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_26());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"characters",
                              							lv_characters_1_27,
                              							"org.eclipse.n4js.regex.RegularExpression.PATTERN_CHARACTER_NO_DASH");
                              					
                            }

                            }
                            break;
                        case 28 :
                            // InternalRegularExpressionParser.g:1720:6: lv_characters_1_28= RULE_UNICODE_LETTER
                            {
                            lv_characters_1_28=(Token)match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_28, grammarAccess.getCharacterClassAtomAccess().getCharactersUNICODE_LETTERTerminalRuleCall_1_0_27());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"characters",
                              							lv_characters_1_28,
                              							"org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
                              					
                            }

                            }
                            break;
                        case 29 :
                            // InternalRegularExpressionParser.g:1735:6: lv_characters_1_29= RULE_UNICODE_DIGIT
                            {
                            lv_characters_1_29=(Token)match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_characters_1_29, grammarAccess.getCharacterClassAtomAccess().getCharactersUNICODE_DIGITTerminalRuleCall_1_0_28());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"characters",
                              							lv_characters_1_29,
                              							"org.eclipse.n4js.regex.RegularExpression.UNICODE_DIGIT");
                              					
                            }

                            }
                            break;

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
    // $ANTLR end "ruleCharacterClassAtom"


    // $ANTLR start "entryRuleEscapedCharacterClassAtom"
    // InternalRegularExpressionParser.g:1756:1: entryRuleEscapedCharacterClassAtom returns [EObject current=null] : iv_ruleEscapedCharacterClassAtom= ruleEscapedCharacterClassAtom EOF ;
    public final EObject entryRuleEscapedCharacterClassAtom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEscapedCharacterClassAtom = null;


        try {
            // InternalRegularExpressionParser.g:1756:66: (iv_ruleEscapedCharacterClassAtom= ruleEscapedCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:1757:2: iv_ruleEscapedCharacterClassAtom= ruleEscapedCharacterClassAtom EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEscapedCharacterClassAtomRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEscapedCharacterClassAtom=ruleEscapedCharacterClassAtom();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEscapedCharacterClassAtom; 
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
    // $ANTLR end "entryRuleEscapedCharacterClassAtom"


    // $ANTLR start "ruleEscapedCharacterClassAtom"
    // InternalRegularExpressionParser.g:1763:1: ruleEscapedCharacterClassAtom returns [EObject current=null] : (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence ) ;
    public final EObject ruleEscapedCharacterClassAtom() throws RecognitionException {
        EObject current = null;

        EObject this_DecimalEscapeSequence_0 = null;

        EObject this_Backspace_1 = null;

        EObject this_CharacterEscapeSequence_2 = null;

        EObject this_ControlLetterEscapeSequence_3 = null;

        EObject this_HexEscapeSequence_4 = null;

        EObject this_UnicodeEscapeSequence_5 = null;

        EObject this_IdentityEscapeSequence_6 = null;

        EObject this_CharacterClassEscapeSequence_7 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1769:2: ( (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence ) )
            // InternalRegularExpressionParser.g:1770:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence )
            {
            // InternalRegularExpressionParser.g:1770:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence )
            int alt24=8;
            switch ( input.LA(1) ) {
            case RULE_DECIMAL_ESCAPE:
                {
                alt24=1;
                }
                break;
            case RULE_WORD_BOUNDARY:
                {
                alt24=2;
                }
                break;
            case RULE_CONTROL_ESCAPE:
                {
                alt24=3;
                }
                break;
            case RULE_CONTROL_LETTER_ESCAPE:
                {
                alt24=4;
                }
                break;
            case RULE_HEX_ESCAPE:
                {
                alt24=5;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt24=6;
                }
                break;
            case RULE_IDENTITY_ESCAPE:
                {
                alt24=7;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
                {
                alt24=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // InternalRegularExpressionParser.g:1771:3: this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getDecimalEscapeSequenceParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_DecimalEscapeSequence_0=ruleDecimalEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_DecimalEscapeSequence_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1780:3: this_Backspace_1= ruleBackspace
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getBackspaceParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Backspace_1=ruleBackspace();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_Backspace_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1789:3: this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterEscapeSequenceParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_CharacterEscapeSequence_2=ruleCharacterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_CharacterEscapeSequence_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1798:3: this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getControlLetterEscapeSequenceParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ControlLetterEscapeSequence_3=ruleControlLetterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ControlLetterEscapeSequence_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:1807:3: this_HexEscapeSequence_4= ruleHexEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getHexEscapeSequenceParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_HexEscapeSequence_4=ruleHexEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_HexEscapeSequence_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalRegularExpressionParser.g:1816:3: this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getUnicodeEscapeSequenceParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_UnicodeEscapeSequence_5=ruleUnicodeEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_UnicodeEscapeSequence_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalRegularExpressionParser.g:1825:3: this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getIdentityEscapeSequenceParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_IdentityEscapeSequence_6=ruleIdentityEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_IdentityEscapeSequence_6;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalRegularExpressionParser.g:1834:3: this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterClassEscapeSequenceParserRuleCall_7());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_CharacterClassEscapeSequence_7=ruleCharacterClassEscapeSequence();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_CharacterClassEscapeSequence_7;
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
    // $ANTLR end "ruleEscapedCharacterClassAtom"


    // $ANTLR start "entryRuleBackspace"
    // InternalRegularExpressionParser.g:1846:1: entryRuleBackspace returns [EObject current=null] : iv_ruleBackspace= ruleBackspace EOF ;
    public final EObject entryRuleBackspace() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBackspace = null;


        try {
            // InternalRegularExpressionParser.g:1846:50: (iv_ruleBackspace= ruleBackspace EOF )
            // InternalRegularExpressionParser.g:1847:2: iv_ruleBackspace= ruleBackspace EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBackspaceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBackspace=ruleBackspace();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBackspace; 
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
    // $ANTLR end "entryRuleBackspace"


    // $ANTLR start "ruleBackspace"
    // InternalRegularExpressionParser.g:1853:1: ruleBackspace returns [EObject current=null] : ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY ) ;
    public final EObject ruleBackspace() throws RecognitionException {
        EObject current = null;

        Token this_WORD_BOUNDARY_1=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1859:2: ( ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY ) )
            // InternalRegularExpressionParser.g:1860:2: ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY )
            {
            // InternalRegularExpressionParser.g:1860:2: ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY )
            // InternalRegularExpressionParser.g:1861:3: () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY
            {
            // InternalRegularExpressionParser.g:1861:3: ()
            // InternalRegularExpressionParser.g:1862:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getBackspaceAccess().getBackspaceAction_0(),
              					current);
              			
            }

            }

            this_WORD_BOUNDARY_1=(Token)match(input,RULE_WORD_BOUNDARY,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WORD_BOUNDARY_1, grammarAccess.getBackspaceAccess().getWORD_BOUNDARYTerminalRuleCall_1());
              		
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
    // $ANTLR end "ruleBackspace"


    // $ANTLR start "entryRuleGroup"
    // InternalRegularExpressionParser.g:1876:1: entryRuleGroup returns [EObject current=null] : iv_ruleGroup= ruleGroup EOF ;
    public final EObject entryRuleGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGroup = null;


        try {
            // InternalRegularExpressionParser.g:1876:46: (iv_ruleGroup= ruleGroup EOF )
            // InternalRegularExpressionParser.g:1877:2: iv_ruleGroup= ruleGroup EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGroup=ruleGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGroup; 
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
    // $ANTLR end "entryRuleGroup"


    // $ANTLR start "ruleGroup"
    // InternalRegularExpressionParser.g:1883:1: ruleGroup returns [EObject current=null] : ( () (otherlv_1= LeftParenthesis | ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) ) | ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis ) ;
    public final EObject ruleGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_nonCapturing_2_0=null;
        Token lv_named_3_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject lv_pattern_6_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1889:2: ( ( () (otherlv_1= LeftParenthesis | ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) ) | ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis ) )
            // InternalRegularExpressionParser.g:1890:2: ( () (otherlv_1= LeftParenthesis | ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) ) | ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis )
            {
            // InternalRegularExpressionParser.g:1890:2: ( () (otherlv_1= LeftParenthesis | ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) ) | ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis )
            // InternalRegularExpressionParser.g:1891:3: () (otherlv_1= LeftParenthesis | ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) ) | ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign ) ) ( (lv_pattern_6_0= ruleDisjunction ) ) otherlv_7= RightParenthesis
            {
            // InternalRegularExpressionParser.g:1891:3: ()
            // InternalRegularExpressionParser.g:1892:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getGroupAccess().getGroupAction_0(),
              					current);
              			
            }

            }

            // InternalRegularExpressionParser.g:1898:3: (otherlv_1= LeftParenthesis | ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) ) | ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign ) )
            int alt25=3;
            switch ( input.LA(1) ) {
            case LeftParenthesis:
                {
                alt25=1;
                }
                break;
            case LeftParenthesisQuestionMarkColon:
                {
                alt25=2;
                }
                break;
            case LeftParenthesisQuestionMarkLessThanSign:
                {
                alt25=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalRegularExpressionParser.g:1899:4: otherlv_1= LeftParenthesis
                    {
                    otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1904:4: ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) )
                    {
                    // InternalRegularExpressionParser.g:1904:4: ( (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon ) )
                    // InternalRegularExpressionParser.g:1905:5: (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon )
                    {
                    // InternalRegularExpressionParser.g:1905:5: (lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon )
                    // InternalRegularExpressionParser.g:1906:6: lv_nonCapturing_2_0= LeftParenthesisQuestionMarkColon
                    {
                    lv_nonCapturing_2_0=(Token)match(input,LeftParenthesisQuestionMarkColon,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nonCapturing_2_0, grammarAccess.getGroupAccess().getNonCapturingLeftParenthesisQuestionMarkColonKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getGroupRule());
                      						}
                      						setWithLastConsumed(current, "nonCapturing", true, "(?:");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1919:4: ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign )
                    {
                    // InternalRegularExpressionParser.g:1919:4: ( ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign )
                    // InternalRegularExpressionParser.g:1920:5: ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) ) ( (lv_name_4_0= ruleRegExpIdentifierName ) ) otherlv_5= GreaterThanSign
                    {
                    // InternalRegularExpressionParser.g:1920:5: ( (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign ) )
                    // InternalRegularExpressionParser.g:1921:6: (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign )
                    {
                    // InternalRegularExpressionParser.g:1921:6: (lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign )
                    // InternalRegularExpressionParser.g:1922:7: lv_named_3_0= LeftParenthesisQuestionMarkLessThanSign
                    {
                    lv_named_3_0=(Token)match(input,LeftParenthesisQuestionMarkLessThanSign,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_named_3_0, grammarAccess.getGroupAccess().getNamedLeftParenthesisQuestionMarkLessThanSignKeyword_1_2_0_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getGroupRule());
                      							}
                      							setWithLastConsumed(current, "named", true, "(?<");
                      						
                    }

                    }


                    }

                    // InternalRegularExpressionParser.g:1934:5: ( (lv_name_4_0= ruleRegExpIdentifierName ) )
                    // InternalRegularExpressionParser.g:1935:6: (lv_name_4_0= ruleRegExpIdentifierName )
                    {
                    // InternalRegularExpressionParser.g:1935:6: (lv_name_4_0= ruleRegExpIdentifierName )
                    // InternalRegularExpressionParser.g:1936:7: lv_name_4_0= ruleRegExpIdentifierName
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getGroupAccess().getNameRegExpIdentifierNameParserRuleCall_1_2_1_0());
                      						
                    }
                    pushFollow(FOLLOW_16);
                    lv_name_4_0=ruleRegExpIdentifierName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getGroupRule());
                      							}
                      							set(
                      								current,
                      								"name",
                      								lv_name_4_0,
                      								"org.eclipse.n4js.regex.RegularExpression.RegExpIdentifierName");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    otherlv_5=(Token)match(input,GreaterThanSign,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getGroupAccess().getGreaterThanSignKeyword_1_2_2());
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalRegularExpressionParser.g:1959:3: ( (lv_pattern_6_0= ruleDisjunction ) )
            // InternalRegularExpressionParser.g:1960:4: (lv_pattern_6_0= ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:1960:4: (lv_pattern_6_0= ruleDisjunction )
            // InternalRegularExpressionParser.g:1961:5: lv_pattern_6_0= ruleDisjunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getGroupAccess().getPatternDisjunctionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_11);
            lv_pattern_6_0=ruleDisjunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getGroupRule());
              					}
              					set(
              						current,
              						"pattern",
              						lv_pattern_6_0,
              						"org.eclipse.n4js.regex.RegularExpression.Disjunction");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_7=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getGroupAccess().getRightParenthesisKeyword_3());
              		
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
    // $ANTLR end "ruleGroup"


    // $ANTLR start "entryRuleRegExpIdentifierName"
    // InternalRegularExpressionParser.g:1986:1: entryRuleRegExpIdentifierName returns [String current=null] : iv_ruleRegExpIdentifierName= ruleRegExpIdentifierName EOF ;
    public final String entryRuleRegExpIdentifierName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRegExpIdentifierName = null;


        try {
            // InternalRegularExpressionParser.g:1986:60: (iv_ruleRegExpIdentifierName= ruleRegExpIdentifierName EOF )
            // InternalRegularExpressionParser.g:1987:2: iv_ruleRegExpIdentifierName= ruleRegExpIdentifierName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRegExpIdentifierNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRegExpIdentifierName=ruleRegExpIdentifierName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRegExpIdentifierName.getText(); 
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
    // $ANTLR end "entryRuleRegExpIdentifierName"


    // $ANTLR start "ruleRegExpIdentifierName"
    // InternalRegularExpressionParser.g:1993:1: ruleRegExpIdentifierName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_RegExpIdentifierStart_0= ruleRegExpIdentifierStart (this_RegExpIdentifierPart_1= ruleRegExpIdentifierPart )* ) ;
    public final AntlrDatatypeRuleToken ruleRegExpIdentifierName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_RegExpIdentifierStart_0 = null;

        AntlrDatatypeRuleToken this_RegExpIdentifierPart_1 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1999:2: ( (this_RegExpIdentifierStart_0= ruleRegExpIdentifierStart (this_RegExpIdentifierPart_1= ruleRegExpIdentifierPart )* ) )
            // InternalRegularExpressionParser.g:2000:2: (this_RegExpIdentifierStart_0= ruleRegExpIdentifierStart (this_RegExpIdentifierPart_1= ruleRegExpIdentifierPart )* )
            {
            // InternalRegularExpressionParser.g:2000:2: (this_RegExpIdentifierStart_0= ruleRegExpIdentifierStart (this_RegExpIdentifierPart_1= ruleRegExpIdentifierPart )* )
            // InternalRegularExpressionParser.g:2001:3: this_RegExpIdentifierStart_0= ruleRegExpIdentifierStart (this_RegExpIdentifierPart_1= ruleRegExpIdentifierPart )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierStartParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_17);
            this_RegExpIdentifierStart_0=ruleRegExpIdentifierStart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_RegExpIdentifierStart_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalRegularExpressionParser.g:2011:3: (this_RegExpIdentifierPart_1= ruleRegExpIdentifierPart )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==DollarSign||LA26_0==KW__||LA26_0==RULE_UNICODE_ESCAPE||LA26_0==RULE_UNICODE_DIGIT||LA26_0==RULE_UNICODE_LETTER) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2012:4: this_RegExpIdentifierPart_1= ruleRegExpIdentifierPart
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierPartParserRuleCall_1());
            	      			
            	    }
            	    pushFollow(FOLLOW_17);
            	    this_RegExpIdentifierPart_1=ruleRegExpIdentifierPart();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_RegExpIdentifierPart_1);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


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
    // $ANTLR end "ruleRegExpIdentifierName"


    // $ANTLR start "entryRuleRegExpIdentifierStart"
    // InternalRegularExpressionParser.g:2027:1: entryRuleRegExpIdentifierStart returns [String current=null] : iv_ruleRegExpIdentifierStart= ruleRegExpIdentifierStart EOF ;
    public final String entryRuleRegExpIdentifierStart() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRegExpIdentifierStart = null;


        try {
            // InternalRegularExpressionParser.g:2027:61: (iv_ruleRegExpIdentifierStart= ruleRegExpIdentifierStart EOF )
            // InternalRegularExpressionParser.g:2028:2: iv_ruleRegExpIdentifierStart= ruleRegExpIdentifierStart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRegExpIdentifierStartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRegExpIdentifierStart=ruleRegExpIdentifierStart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRegExpIdentifierStart.getText(); 
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
    // $ANTLR end "entryRuleRegExpIdentifierStart"


    // $ANTLR start "ruleRegExpIdentifierStart"
    // InternalRegularExpressionParser.g:2034:1: ruleRegExpIdentifierStart returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_3= RULE_UNICODE_ESCAPE ) ;
    public final AntlrDatatypeRuleToken ruleRegExpIdentifierStart() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNICODE_LETTER_0=null;
        Token kw=null;
        Token this_UNICODE_ESCAPE_3=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2040:2: ( (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_3= RULE_UNICODE_ESCAPE ) )
            // InternalRegularExpressionParser.g:2041:2: (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_3= RULE_UNICODE_ESCAPE )
            {
            // InternalRegularExpressionParser.g:2041:2: (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_3= RULE_UNICODE_ESCAPE )
            int alt27=4;
            switch ( input.LA(1) ) {
            case RULE_UNICODE_LETTER:
                {
                alt27=1;
                }
                break;
            case DollarSign:
                {
                alt27=2;
                }
                break;
            case KW__:
                {
                alt27=3;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt27=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // InternalRegularExpressionParser.g:2042:3: this_UNICODE_LETTER_0= RULE_UNICODE_LETTER
                    {
                    this_UNICODE_LETTER_0=(Token)match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_UNICODE_LETTER_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_UNICODE_LETTER_0, grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_LETTERTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:2050:3: kw= DollarSign
                    {
                    kw=(Token)match(input,DollarSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getRegExpIdentifierStartAccess().getDollarSignKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:2056:3: kw= KW__
                    {
                    kw=(Token)match(input,KW__,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getRegExpIdentifierStartAccess().get_Keyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:2062:3: this_UNICODE_ESCAPE_3= RULE_UNICODE_ESCAPE
                    {
                    this_UNICODE_ESCAPE_3=(Token)match(input,RULE_UNICODE_ESCAPE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_UNICODE_ESCAPE_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_UNICODE_ESCAPE_3, grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_ESCAPETerminalRuleCall_3());
                      		
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
    // $ANTLR end "ruleRegExpIdentifierStart"


    // $ANTLR start "entryRuleRegExpIdentifierPart"
    // InternalRegularExpressionParser.g:2073:1: entryRuleRegExpIdentifierPart returns [String current=null] : iv_ruleRegExpIdentifierPart= ruleRegExpIdentifierPart EOF ;
    public final String entryRuleRegExpIdentifierPart() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRegExpIdentifierPart = null;


        try {
            // InternalRegularExpressionParser.g:2073:60: (iv_ruleRegExpIdentifierPart= ruleRegExpIdentifierPart EOF )
            // InternalRegularExpressionParser.g:2074:2: iv_ruleRegExpIdentifierPart= ruleRegExpIdentifierPart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRegExpIdentifierPartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRegExpIdentifierPart=ruleRegExpIdentifierPart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRegExpIdentifierPart.getText(); 
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
    // $ANTLR end "entryRuleRegExpIdentifierPart"


    // $ANTLR start "ruleRegExpIdentifierPart"
    // InternalRegularExpressionParser.g:2080:1: ruleRegExpIdentifierPart returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | this_UNICODE_DIGIT_1= RULE_UNICODE_DIGIT | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_4= RULE_UNICODE_ESCAPE ) ;
    public final AntlrDatatypeRuleToken ruleRegExpIdentifierPart() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNICODE_LETTER_0=null;
        Token this_UNICODE_DIGIT_1=null;
        Token kw=null;
        Token this_UNICODE_ESCAPE_4=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2086:2: ( (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | this_UNICODE_DIGIT_1= RULE_UNICODE_DIGIT | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_4= RULE_UNICODE_ESCAPE ) )
            // InternalRegularExpressionParser.g:2087:2: (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | this_UNICODE_DIGIT_1= RULE_UNICODE_DIGIT | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_4= RULE_UNICODE_ESCAPE )
            {
            // InternalRegularExpressionParser.g:2087:2: (this_UNICODE_LETTER_0= RULE_UNICODE_LETTER | this_UNICODE_DIGIT_1= RULE_UNICODE_DIGIT | kw= DollarSign | kw= KW__ | this_UNICODE_ESCAPE_4= RULE_UNICODE_ESCAPE )
            int alt28=5;
            switch ( input.LA(1) ) {
            case RULE_UNICODE_LETTER:
                {
                alt28=1;
                }
                break;
            case RULE_UNICODE_DIGIT:
                {
                alt28=2;
                }
                break;
            case DollarSign:
                {
                alt28=3;
                }
                break;
            case KW__:
                {
                alt28=4;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt28=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // InternalRegularExpressionParser.g:2088:3: this_UNICODE_LETTER_0= RULE_UNICODE_LETTER
                    {
                    this_UNICODE_LETTER_0=(Token)match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_UNICODE_LETTER_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_UNICODE_LETTER_0, grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_LETTERTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:2096:3: this_UNICODE_DIGIT_1= RULE_UNICODE_DIGIT
                    {
                    this_UNICODE_DIGIT_1=(Token)match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_UNICODE_DIGIT_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_UNICODE_DIGIT_1, grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_DIGITTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:2104:3: kw= DollarSign
                    {
                    kw=(Token)match(input,DollarSign,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getRegExpIdentifierPartAccess().getDollarSignKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:2110:3: kw= KW__
                    {
                    kw=(Token)match(input,KW__,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getRegExpIdentifierPartAccess().get_Keyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:2116:3: this_UNICODE_ESCAPE_4= RULE_UNICODE_ESCAPE
                    {
                    this_UNICODE_ESCAPE_4=(Token)match(input,RULE_UNICODE_ESCAPE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_UNICODE_ESCAPE_4);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_UNICODE_ESCAPE_4, grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_ESCAPETerminalRuleCall_4());
                      		
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
    // $ANTLR end "ruleRegExpIdentifierPart"


    // $ANTLR start "entryRuleQuantifier"
    // InternalRegularExpressionParser.g:2127:1: entryRuleQuantifier returns [EObject current=null] : iv_ruleQuantifier= ruleQuantifier EOF ;
    public final EObject entryRuleQuantifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantifier = null;


        try {
            // InternalRegularExpressionParser.g:2127:51: (iv_ruleQuantifier= ruleQuantifier EOF )
            // InternalRegularExpressionParser.g:2128:2: iv_ruleQuantifier= ruleQuantifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQuantifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQuantifier=ruleQuantifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQuantifier; 
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
    // $ANTLR end "entryRuleQuantifier"


    // $ANTLR start "ruleQuantifier"
    // InternalRegularExpressionParser.g:2134:1: ruleQuantifier returns [EObject current=null] : (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier ) ;
    public final EObject ruleQuantifier() throws RecognitionException {
        EObject current = null;

        EObject this_SimpleQuantifier_0 = null;

        EObject this_ExactQuantifier_1 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2140:2: ( (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier ) )
            // InternalRegularExpressionParser.g:2141:2: (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier )
            {
            // InternalRegularExpressionParser.g:2141:2: (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( ((LA29_0>=Asterisk && LA29_0<=PlusSign)||LA29_0==QuestionMark) ) {
                alt29=1;
            }
            else if ( (LA29_0==LeftCurlyBracket) ) {
                alt29=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // InternalRegularExpressionParser.g:2142:3: this_SimpleQuantifier_0= ruleSimpleQuantifier
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getQuantifierAccess().getSimpleQuantifierParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_SimpleQuantifier_0=ruleSimpleQuantifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_SimpleQuantifier_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:2151:3: this_ExactQuantifier_1= ruleExactQuantifier
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getQuantifierAccess().getExactQuantifierParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ExactQuantifier_1=ruleExactQuantifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ExactQuantifier_1;
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
    // $ANTLR end "ruleQuantifier"


    // $ANTLR start "entryRuleSimpleQuantifier"
    // InternalRegularExpressionParser.g:2163:1: entryRuleSimpleQuantifier returns [EObject current=null] : iv_ruleSimpleQuantifier= ruleSimpleQuantifier EOF ;
    public final EObject entryRuleSimpleQuantifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleQuantifier = null;


        try {
            // InternalRegularExpressionParser.g:2163:57: (iv_ruleSimpleQuantifier= ruleSimpleQuantifier EOF )
            // InternalRegularExpressionParser.g:2164:2: iv_ruleSimpleQuantifier= ruleSimpleQuantifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSimpleQuantifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSimpleQuantifier=ruleSimpleQuantifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSimpleQuantifier; 
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
    // $ANTLR end "entryRuleSimpleQuantifier"


    // $ANTLR start "ruleSimpleQuantifier"
    // InternalRegularExpressionParser.g:2170:1: ruleSimpleQuantifier returns [EObject current=null] : ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? ) ;
    public final EObject ruleSimpleQuantifier() throws RecognitionException {
        EObject current = null;

        Token lv_quantifier_0_1=null;
        Token lv_quantifier_0_2=null;
        Token lv_quantifier_0_3=null;
        Token lv_nonGreedy_1_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2176:2: ( ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? ) )
            // InternalRegularExpressionParser.g:2177:2: ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? )
            {
            // InternalRegularExpressionParser.g:2177:2: ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? )
            // InternalRegularExpressionParser.g:2178:3: ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )?
            {
            // InternalRegularExpressionParser.g:2178:3: ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) )
            // InternalRegularExpressionParser.g:2179:4: ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:2179:4: ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) )
            // InternalRegularExpressionParser.g:2180:5: (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark )
            {
            // InternalRegularExpressionParser.g:2180:5: (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark )
            int alt30=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                alt30=1;
                }
                break;
            case Asterisk:
                {
                alt30=2;
                }
                break;
            case QuestionMark:
                {
                alt30=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // InternalRegularExpressionParser.g:2181:6: lv_quantifier_0_1= PlusSign
                    {
                    lv_quantifier_0_1=(Token)match(input,PlusSign,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_quantifier_0_1, grammarAccess.getSimpleQuantifierAccess().getQuantifierPlusSignKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSimpleQuantifierRule());
                      						}
                      						setWithLastConsumed(current, "quantifier", lv_quantifier_0_1, null);
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:2192:6: lv_quantifier_0_2= Asterisk
                    {
                    lv_quantifier_0_2=(Token)match(input,Asterisk,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_quantifier_0_2, grammarAccess.getSimpleQuantifierAccess().getQuantifierAsteriskKeyword_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSimpleQuantifierRule());
                      						}
                      						setWithLastConsumed(current, "quantifier", lv_quantifier_0_2, null);
                      					
                    }

                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:2203:6: lv_quantifier_0_3= QuestionMark
                    {
                    lv_quantifier_0_3=(Token)match(input,QuestionMark,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_quantifier_0_3, grammarAccess.getSimpleQuantifierAccess().getQuantifierQuestionMarkKeyword_0_0_2());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getSimpleQuantifierRule());
                      						}
                      						setWithLastConsumed(current, "quantifier", lv_quantifier_0_3, null);
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalRegularExpressionParser.g:2216:3: ( (lv_nonGreedy_1_0= QuestionMark ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==QuestionMark) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalRegularExpressionParser.g:2217:4: (lv_nonGreedy_1_0= QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:2217:4: (lv_nonGreedy_1_0= QuestionMark )
                    // InternalRegularExpressionParser.g:2218:5: lv_nonGreedy_1_0= QuestionMark
                    {
                    lv_nonGreedy_1_0=(Token)match(input,QuestionMark,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nonGreedy_1_0, grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSimpleQuantifierRule());
                      					}
                      					setWithLastConsumed(current, "nonGreedy", true, "?");
                      				
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
    // $ANTLR end "ruleSimpleQuantifier"


    // $ANTLR start "entryRuleExactQuantifier"
    // InternalRegularExpressionParser.g:2234:1: entryRuleExactQuantifier returns [EObject current=null] : iv_ruleExactQuantifier= ruleExactQuantifier EOF ;
    public final EObject entryRuleExactQuantifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExactQuantifier = null;


        try {
            // InternalRegularExpressionParser.g:2234:56: (iv_ruleExactQuantifier= ruleExactQuantifier EOF )
            // InternalRegularExpressionParser.g:2235:2: iv_ruleExactQuantifier= ruleExactQuantifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExactQuantifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExactQuantifier=ruleExactQuantifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExactQuantifier; 
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
    // $ANTLR end "entryRuleExactQuantifier"


    // $ANTLR start "ruleExactQuantifier"
    // InternalRegularExpressionParser.g:2241:1: ruleExactQuantifier returns [EObject current=null] : ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? ) ;
    public final EObject ruleExactQuantifier() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_unboundedMax_5_0=null;
        Token otherlv_6=null;
        Token lv_nonGreedy_7_0=null;
        AntlrDatatypeRuleToken lv_min_2_0 = null;

        AntlrDatatypeRuleToken lv_max_4_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2247:2: ( ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? ) )
            // InternalRegularExpressionParser.g:2248:2: ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? )
            {
            // InternalRegularExpressionParser.g:2248:2: ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? )
            // InternalRegularExpressionParser.g:2249:3: () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )?
            {
            // InternalRegularExpressionParser.g:2249:3: ()
            // InternalRegularExpressionParser.g:2250:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftCurlyBracket,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getExactQuantifierAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalRegularExpressionParser.g:2260:3: ( (lv_min_2_0= ruleINT ) )
            // InternalRegularExpressionParser.g:2261:4: (lv_min_2_0= ruleINT )
            {
            // InternalRegularExpressionParser.g:2261:4: (lv_min_2_0= ruleINT )
            // InternalRegularExpressionParser.g:2262:5: lv_min_2_0= ruleINT
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getExactQuantifierAccess().getMinINTParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_20);
            lv_min_2_0=ruleINT();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getExactQuantifierRule());
              					}
              					set(
              						current,
              						"min",
              						lv_min_2_0,
              						"org.eclipse.n4js.regex.RegularExpression.INT");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalRegularExpressionParser.g:2279:3: ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )?
            int alt32=3;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==Comma) ) {
                int LA32_1 = input.LA(2);

                if ( (LA32_1==RightCurlyBracket) ) {
                    alt32=2;
                }
                else if ( (LA32_1==RULE_UNICODE_DIGIT) ) {
                    alt32=1;
                }
            }
            switch (alt32) {
                case 1 :
                    // InternalRegularExpressionParser.g:2280:4: (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) )
                    {
                    // InternalRegularExpressionParser.g:2280:4: (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) )
                    // InternalRegularExpressionParser.g:2281:5: otherlv_3= Comma ( (lv_max_4_0= ruleINT ) )
                    {
                    otherlv_3=(Token)match(input,Comma,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getExactQuantifierAccess().getCommaKeyword_3_0_0());
                      				
                    }
                    // InternalRegularExpressionParser.g:2285:5: ( (lv_max_4_0= ruleINT ) )
                    // InternalRegularExpressionParser.g:2286:6: (lv_max_4_0= ruleINT )
                    {
                    // InternalRegularExpressionParser.g:2286:6: (lv_max_4_0= ruleINT )
                    // InternalRegularExpressionParser.g:2287:7: lv_max_4_0= ruleINT
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getExactQuantifierAccess().getMaxINTParserRuleCall_3_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_21);
                    lv_max_4_0=ruleINT();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getExactQuantifierRule());
                      							}
                      							set(
                      								current,
                      								"max",
                      								lv_max_4_0,
                      								"org.eclipse.n4js.regex.RegularExpression.INT");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:2306:4: ( (lv_unboundedMax_5_0= Comma ) )
                    {
                    // InternalRegularExpressionParser.g:2306:4: ( (lv_unboundedMax_5_0= Comma ) )
                    // InternalRegularExpressionParser.g:2307:5: (lv_unboundedMax_5_0= Comma )
                    {
                    // InternalRegularExpressionParser.g:2307:5: (lv_unboundedMax_5_0= Comma )
                    // InternalRegularExpressionParser.g:2308:6: lv_unboundedMax_5_0= Comma
                    {
                    lv_unboundedMax_5_0=(Token)match(input,Comma,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_unboundedMax_5_0, grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getExactQuantifierRule());
                      						}
                      						setWithLastConsumed(current, "unboundedMax", true, ",");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,RightCurlyBracket,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getExactQuantifierAccess().getRightCurlyBracketKeyword_4());
              		
            }
            // InternalRegularExpressionParser.g:2325:3: ( (lv_nonGreedy_7_0= QuestionMark ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==QuestionMark) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalRegularExpressionParser.g:2326:4: (lv_nonGreedy_7_0= QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:2326:4: (lv_nonGreedy_7_0= QuestionMark )
                    // InternalRegularExpressionParser.g:2327:5: lv_nonGreedy_7_0= QuestionMark
                    {
                    lv_nonGreedy_7_0=(Token)match(input,QuestionMark,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nonGreedy_7_0, grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getExactQuantifierRule());
                      					}
                      					setWithLastConsumed(current, "nonGreedy", true, "?");
                      				
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
    // $ANTLR end "ruleExactQuantifier"


    // $ANTLR start "entryRuleRegularExpressionFlags"
    // InternalRegularExpressionParser.g:2343:1: entryRuleRegularExpressionFlags returns [EObject current=null] : iv_ruleRegularExpressionFlags= ruleRegularExpressionFlags EOF ;
    public final EObject entryRuleRegularExpressionFlags() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegularExpressionFlags = null;


        try {
            // InternalRegularExpressionParser.g:2343:63: (iv_ruleRegularExpressionFlags= ruleRegularExpressionFlags EOF )
            // InternalRegularExpressionParser.g:2344:2: iv_ruleRegularExpressionFlags= ruleRegularExpressionFlags EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRegularExpressionFlagsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleRegularExpressionFlags=ruleRegularExpressionFlags();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRegularExpressionFlags; 
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
    // $ANTLR end "entryRuleRegularExpressionFlags"


    // $ANTLR start "ruleRegularExpressionFlags"
    // InternalRegularExpressionParser.g:2350:1: ruleRegularExpressionFlags returns [EObject current=null] : ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* ) ;
    public final EObject ruleRegularExpressionFlags() throws RecognitionException {
        EObject current = null;

        Token lv_flags_1_1=null;
        Token lv_flags_1_2=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2356:2: ( ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* ) )
            // InternalRegularExpressionParser.g:2357:2: ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* )
            {
            // InternalRegularExpressionParser.g:2357:2: ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* )
            // InternalRegularExpressionParser.g:2358:3: () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )*
            {
            // InternalRegularExpressionParser.g:2358:3: ()
            // InternalRegularExpressionParser.g:2359:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0(),
              					current);
              			
            }

            }

            // InternalRegularExpressionParser.g:2365:3: ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_UNICODE_ESCAPE||LA35_0==RULE_UNICODE_LETTER) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2366:4: ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) )
            	    {
            	    // InternalRegularExpressionParser.g:2366:4: ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) )
            	    // InternalRegularExpressionParser.g:2367:5: (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE )
            	    {
            	    // InternalRegularExpressionParser.g:2367:5: (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE )
            	    int alt34=2;
            	    int LA34_0 = input.LA(1);

            	    if ( (LA34_0==RULE_UNICODE_LETTER) ) {
            	        alt34=1;
            	    }
            	    else if ( (LA34_0==RULE_UNICODE_ESCAPE) ) {
            	        alt34=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 34, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt34) {
            	        case 1 :
            	            // InternalRegularExpressionParser.g:2368:6: lv_flags_1_1= RULE_UNICODE_LETTER
            	            {
            	            lv_flags_1_1=(Token)match(input,RULE_UNICODE_LETTER,FOLLOW_22); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(lv_flags_1_1, grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_LETTERTerminalRuleCall_1_0_0());
            	              					
            	            }
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElement(grammarAccess.getRegularExpressionFlagsRule());
            	              						}
            	              						addWithLastConsumed(
            	              							current,
            	              							"flags",
            	              							lv_flags_1_1,
            	              							"org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
            	              					
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalRegularExpressionParser.g:2383:6: lv_flags_1_2= RULE_UNICODE_ESCAPE
            	            {
            	            lv_flags_1_2=(Token)match(input,RULE_UNICODE_ESCAPE,FOLLOW_22); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(lv_flags_1_2, grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1());
            	              					
            	            }
            	            if ( state.backtracking==0 ) {

            	              						if (current==null) {
            	              							current = createModelElement(grammarAccess.getRegularExpressionFlagsRule());
            	              						}
            	              						addWithLastConsumed(
            	              							current,
            	              							"flags",
            	              							lv_flags_1_2,
            	              							"org.eclipse.n4js.regex.RegularExpression.UNICODE_ESCAPE");
            	              					
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


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
    // $ANTLR end "ruleRegularExpressionFlags"


    // $ANTLR start "entryRuleINT"
    // InternalRegularExpressionParser.g:2404:1: entryRuleINT returns [String current=null] : iv_ruleINT= ruleINT EOF ;
    public final String entryRuleINT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINT = null;


        try {
            // InternalRegularExpressionParser.g:2404:43: (iv_ruleINT= ruleINT EOF )
            // InternalRegularExpressionParser.g:2405:2: iv_ruleINT= ruleINT EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getINTRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleINT=ruleINT();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleINT.getText(); 
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
    // $ANTLR end "entryRuleINT"


    // $ANTLR start "ruleINT"
    // InternalRegularExpressionParser.g:2411:1: ruleINT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+ ;
    public final AntlrDatatypeRuleToken ruleINT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNICODE_DIGIT_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2417:2: ( (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+ )
            // InternalRegularExpressionParser.g:2418:2: (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+
            {
            // InternalRegularExpressionParser.g:2418:2: (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+
            int cnt36=0;
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==RULE_UNICODE_DIGIT) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2419:3: this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT
            	    {
            	    this_UNICODE_DIGIT_0=(Token)match(input,RULE_UNICODE_DIGIT,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_UNICODE_DIGIT_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_UNICODE_DIGIT_0, grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall());
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt36 >= 1 ) break loop36;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(36, input);
                        throw eee;
                }
                cnt36++;
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
    // $ANTLR end "ruleINT"

    // $ANTLR start synpred1_InternalRegularExpressionParser
    public final void synpred1_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:352:5: ( ( ruleQuantifier ) )
        // InternalRegularExpressionParser.g:352:6: ( ruleQuantifier )
        {
        // InternalRegularExpressionParser.g:352:6: ( ruleQuantifier )
        // InternalRegularExpressionParser.g:353:6: ruleQuantifier
        {
        pushFollow(FOLLOW_2);
        ruleQuantifier();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalRegularExpressionParser

    // $ANTLR start synpred2_InternalRegularExpressionParser
    public final void synpred2_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:1272:4: ( ( ( CircumflexAccent ) ) )
        // InternalRegularExpressionParser.g:1272:5: ( ( CircumflexAccent ) )
        {
        // InternalRegularExpressionParser.g:1272:5: ( ( CircumflexAccent ) )
        // InternalRegularExpressionParser.g:1273:5: ( CircumflexAccent )
        {
        // InternalRegularExpressionParser.g:1273:5: ( CircumflexAccent )
        // InternalRegularExpressionParser.g:1274:6: CircumflexAccent
        {
        match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred2_InternalRegularExpressionParser

    // $ANTLR start synpred3_InternalRegularExpressionParser
    public final void synpred3_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:1344:4: ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )
        // InternalRegularExpressionParser.g:1344:5: ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) )
        {
        // InternalRegularExpressionParser.g:1344:5: ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) )
        // InternalRegularExpressionParser.g:1345:5: () HyphenMinus ( ( ruleCharacterClassAtom ) )
        {
        // InternalRegularExpressionParser.g:1345:5: ()
        // InternalRegularExpressionParser.g:1346:5: 
        {
        }

        match(input,HyphenMinus,FOLLOW_14); if (state.failed) return ;
        // InternalRegularExpressionParser.g:1348:5: ( ( ruleCharacterClassAtom ) )
        // InternalRegularExpressionParser.g:1349:6: ( ruleCharacterClassAtom )
        {
        // InternalRegularExpressionParser.g:1349:6: ( ruleCharacterClassAtom )
        // InternalRegularExpressionParser.g:1350:7: ruleCharacterClassAtom
        {
        pushFollow(FOLLOW_2);
        ruleCharacterClassAtom();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred3_InternalRegularExpressionParser

    // Delegated rules

    public final boolean synpred2_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA21 dfa21 = new DFA21(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\1\5\3\uffff\1\5\1\uffff\2\5\1\uffff\1\5";
    static final String dfa_3s = "\1\4\3\uffff\1\4\1\uffff\2\4\1\0\1\4";
    static final String dfa_4s = "\1\60\3\uffff\1\60\1\uffff\2\60\1\0\1\60";
    static final String dfa_5s = "\1\uffff\3\1\1\uffff\1\2\4\uffff";
    static final String dfa_6s = "\1\0\7\uffff\1\1\1\uffff}>";
    static final String[] dfa_7s = {
            "\6\5\1\uffff\4\5\1\2\1\1\10\5\1\3\3\5\1\uffff\1\4\7\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\5\1\uffff\2\5",
            "",
            "",
            "",
            "\6\5\1\uffff\22\5\1\uffff\10\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\6\1\uffff\2\5",
            "",
            "\6\5\1\uffff\6\5\1\7\13\5\1\uffff\2\5\1\10\5\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\6\1\uffff\2\5",
            "\6\5\1\uffff\22\5\1\uffff\2\5\1\10\5\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\11\1\uffff\2\5",
            "\1\uffff",
            "\6\5\1\uffff\22\5\1\uffff\2\5\1\10\5\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\11\1\uffff\2\5"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "351:4: ( ( ( ruleQuantifier ) )=> (lv_quantifier_2_0= ruleQuantifier ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_0 = input.LA(1);

                         
                        int index9_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA9_0==PlusSign) && (synpred1_InternalRegularExpressionParser())) {s = 1;}

                        else if ( (LA9_0==Asterisk) && (synpred1_InternalRegularExpressionParser())) {s = 2;}

                        else if ( (LA9_0==QuestionMark) && (synpred1_InternalRegularExpressionParser())) {s = 3;}

                        else if ( (LA9_0==LeftCurlyBracket) ) {s = 4;}

                        else if ( (LA9_0==EOF||(LA9_0>=LeftParenthesisQuestionMarkLessThanSignExclamationMark && LA9_0<=LeftParenthesisQuestionMarkEqualsSign)||(LA9_0>=ExclamationMark && LA9_0<=RightParenthesis)||(LA9_0>=Comma && LA9_0<=GreaterThanSign)||(LA9_0>=LeftSquareBracket && LA9_0<=CircumflexAccent)||(LA9_0>=VerticalLine && LA9_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA9_0>=RULE_HEX_ESCAPE && LA9_0<=RULE_UNICODE_ESCAPE)||(LA9_0>=RULE_DECIMAL_ESCAPE && LA9_0<=RULE_IDENTITY_ESCAPE)||LA9_0==RULE_UNICODE_DIGIT||(LA9_0>=RULE_UNICODE_LETTER && LA9_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {s = 5;}

                         
                        input.seek(index9_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_8 = input.LA(1);

                         
                        int index9_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalRegularExpressionParser()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index9_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\51\uffff";
    static final String dfa_9s = "\1\2\50\uffff";
    static final String dfa_10s = "\2\4\1\uffff\45\0\1\uffff";
    static final String dfa_11s = "\2\60\1\uffff\45\0\1\uffff";
    static final String dfa_12s = "\2\uffff\1\2\45\uffff\1\1";
    static final String dfa_13s = "\3\uffff\1\11\1\13\1\20\1\26\1\42\1\4\1\23\1\43\1\34\1\16\1\2\1\24\1\7\1\14\1\35\1\21\1\40\1\27\1\0\1\32\1\5\1\37\1\12\1\44\1\17\1\3\1\25\1\10\1\31\1\15\1\36\1\22\1\41\1\30\1\1\1\33\1\6\1\uffff}>";
    static final String[] dfa_14s = {
            "\3\2\1\uffff\12\2\1\1\12\2\1\uffff\4\2\1\uffff\3\2\1\uffff\2\2\1\uffff\2\2\1\uffff\1\2\1\uffff\2\2",
            "\1\43\1\44\1\42\1\uffff\1\40\1\41\1\37\1\17\1\21\1\26\1\27\1\23\1\24\1\14\1\3\1\22\1\34\1\16\1\35\1\15\1\36\1\25\1\30\1\2\1\20\1\uffff\1\31\1\33\1\32\1\5\1\uffff\1\13\1\6\1\7\1\uffff\1\10\1\11\1\uffff\1\4\1\12\1\uffff\1\47\1\uffff\1\46\1\45",
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
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "1343:3: ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_21 = input.LA(1);

                         
                        int index21_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_21);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_37 = input.LA(1);

                         
                        int index21_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_37);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA21_13 = input.LA(1);

                         
                        int index21_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_13);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA21_28 = input.LA(1);

                         
                        int index21_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_28);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA21_8 = input.LA(1);

                         
                        int index21_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_8);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA21_23 = input.LA(1);

                         
                        int index21_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_23);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA21_39 = input.LA(1);

                         
                        int index21_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_39);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA21_15 = input.LA(1);

                         
                        int index21_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_15);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA21_30 = input.LA(1);

                         
                        int index21_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_30);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA21_3 = input.LA(1);

                         
                        int index21_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_3);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA21_25 = input.LA(1);

                         
                        int index21_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_25);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA21_4 = input.LA(1);

                         
                        int index21_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_4);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA21_16 = input.LA(1);

                         
                        int index21_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_16);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA21_32 = input.LA(1);

                         
                        int index21_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_32);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA21_12 = input.LA(1);

                         
                        int index21_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_12);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA21_27 = input.LA(1);

                         
                        int index21_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_27);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA21_5 = input.LA(1);

                         
                        int index21_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_5);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA21_18 = input.LA(1);

                         
                        int index21_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_18);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA21_34 = input.LA(1);

                         
                        int index21_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_34);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA21_9 = input.LA(1);

                         
                        int index21_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_9);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA21_14 = input.LA(1);

                         
                        int index21_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_14);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA21_29 = input.LA(1);

                         
                        int index21_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_29);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA21_6 = input.LA(1);

                         
                        int index21_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_6);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA21_20 = input.LA(1);

                         
                        int index21_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_20);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA21_36 = input.LA(1);

                         
                        int index21_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_36);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA21_31 = input.LA(1);

                         
                        int index21_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_31);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA21_22 = input.LA(1);

                         
                        int index21_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_22);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA21_38 = input.LA(1);

                         
                        int index21_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_38);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA21_11 = input.LA(1);

                         
                        int index21_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_11);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA21_17 = input.LA(1);

                         
                        int index21_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_17);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA21_33 = input.LA(1);

                         
                        int index21_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_33);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA21_24 = input.LA(1);

                         
                        int index21_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_24);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA21_19 = input.LA(1);

                         
                        int index21_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_19);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA21_35 = input.LA(1);

                         
                        int index21_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_35);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA21_7 = input.LA(1);

                         
                        int index21_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_7);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA21_10 = input.LA(1);

                         
                        int index21_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_10);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA21_26 = input.LA(1);

                         
                        int index21_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 40;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_26);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0001ADBFDDEE3BF0L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000810000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0001ADBFDDEE3BF2L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0001ADBF5DEE3BF2L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000042018002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0001ADBFDDEE7BF0L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0001ADBBDFFFFF70L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0001ADBBD7FFFF70L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000810020001000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000A10020001002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000100020000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000810000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000200000000002L});

}