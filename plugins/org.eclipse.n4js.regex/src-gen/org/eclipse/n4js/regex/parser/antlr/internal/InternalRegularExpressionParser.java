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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ExclamationMark", "DollarSign", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "EqualsSign", "QuestionMark", "LeftSquareBracket", "RightSquareBracket", "CircumflexAccent", "LeftCurlyBracket", "VerticalLine", "RightCurlyBracket", "RULE_WORD_BOUNDARY", "RULE_NOT_WORD_BOUNDARY", "RULE_CHARACTER_CLASS_ESCAPE", "RULE_CONTROL_ESCAPE", "RULE_CONTROL_LETTER_ESCAPE", "RULE_HEX_DIGIT", "RULE_HEX_ESCAPE", "RULE_UNICODE_ESCAPE", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_DECIMAL_ESCAPE", "RULE_IDENTITY_ESCAPE", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_DIGIT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_UNICODE_LETTER", "RULE_PATTERN_CHARACTER_NO_DASH", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_ANY_OTHER"
    };
    public static final int RULE_WHITESPACE_FRAGMENT=44;
    public static final int LeftParenthesis=6;
    public static final int RULE_HEX_ESCAPE=29;
    public static final int RightSquareBracket=18;
    public static final int ExclamationMark=4;
    public static final int RULE_CONTROL_LETTER_ESCAPE=27;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=46;
    public static final int RightParenthesis=7;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=49;
    public static final int RULE_ZWNJ=41;
    public static final int VerticalLine=21;
    public static final int PlusSign=9;
    public static final int LeftSquareBracket=17;
    public static final int RULE_DECIMAL_ESCAPE=32;
    public static final int RULE_ML_COMMENT_FRAGMENT=48;
    public static final int RULE_PATTERN_CHARACTER_NO_DASH=38;
    public static final int RULE_WORD_BOUNDARY=23;
    public static final int RULE_UNICODE_ESCAPE=30;
    public static final int Comma=10;
    public static final int EqualsSign=15;
    public static final int RULE_ZWJ=40;
    public static final int RULE_SL_COMMENT_FRAGMENT=47;
    public static final int HyphenMinus=11;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=34;
    public static final int RULE_UNICODE_LETTER=37;
    public static final int RULE_CONTROL_ESCAPE=26;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=43;
    public static final int Solidus=13;
    public static final int Colon=14;
    public static final int RightCurlyBracket=22;
    public static final int RULE_CHARACTER_CLASS_ESCAPE=25;
    public static final int EOF=-1;
    public static final int Asterisk=8;
    public static final int FullStop=12;
    public static final int RULE_UNICODE_DIGIT=35;
    public static final int RULE_BOM=42;
    public static final int LeftCurlyBracket=20;
    public static final int RULE_ANY_OTHER=51;
    public static final int CircumflexAccent=19;
    public static final int RULE_NOT_WORD_BOUNDARY=24;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=45;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=36;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=39;
    public static final int QuestionMark=16;
    public static final int DollarSign=5;
    public static final int RULE_HEX_DIGIT=28;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=31;
    public static final int RULE_IDENTITY_ESCAPE=33;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=50;

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

            if ( ((LA6_0>=ExclamationMark && LA6_0<=LeftParenthesis)||(LA6_0>=Comma && LA6_0<=FullStop)||(LA6_0>=Colon && LA6_0<=EqualsSign)||(LA6_0>=LeftSquareBracket && LA6_0<=LeftCurlyBracket)||(LA6_0>=RightCurlyBracket && LA6_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA6_0>=RULE_HEX_ESCAPE && LA6_0<=RULE_UNICODE_ESCAPE)||(LA6_0>=RULE_DECIMAL_ESCAPE && LA6_0<=RULE_IDENTITY_ESCAPE)||LA6_0==RULE_UNICODE_DIGIT||(LA6_0>=RULE_UNICODE_LETTER && LA6_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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

                            	    if ( ((LA1_0>=ExclamationMark && LA1_0<=LeftParenthesis)||(LA1_0>=Comma && LA1_0<=FullStop)||(LA1_0>=Colon && LA1_0<=EqualsSign)||(LA1_0>=LeftSquareBracket && LA1_0<=LeftCurlyBracket)||(LA1_0>=RightCurlyBracket && LA1_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA1_0>=RULE_HEX_ESCAPE && LA1_0<=RULE_UNICODE_ESCAPE)||(LA1_0>=RULE_DECIMAL_ESCAPE && LA1_0<=RULE_IDENTITY_ESCAPE)||LA1_0==RULE_UNICODE_DIGIT||(LA1_0>=RULE_UNICODE_LETTER && LA1_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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

                    	    if ( ((LA4_0>=ExclamationMark && LA4_0<=LeftParenthesis)||(LA4_0>=Comma && LA4_0<=FullStop)||(LA4_0>=Colon && LA4_0<=EqualsSign)||(LA4_0>=LeftSquareBracket && LA4_0<=LeftCurlyBracket)||(LA4_0>=RightCurlyBracket && LA4_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA4_0>=RULE_HEX_ESCAPE && LA4_0<=RULE_UNICODE_ESCAPE)||(LA4_0>=RULE_DECIMAL_ESCAPE && LA4_0<=RULE_IDENTITY_ESCAPE)||LA4_0==RULE_UNICODE_DIGIT||(LA4_0>=RULE_UNICODE_LETTER && LA4_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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

            if ( ((LA8_0>=ExclamationMark && LA8_0<=LeftParenthesis)||(LA8_0>=Comma && LA8_0<=FullStop)||(LA8_0>=Colon && LA8_0<=EqualsSign)||(LA8_0>=LeftSquareBracket && LA8_0<=LeftCurlyBracket)||(LA8_0>=RightCurlyBracket && LA8_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA8_0>=RULE_HEX_ESCAPE && LA8_0<=RULE_UNICODE_ESCAPE)||(LA8_0>=RULE_DECIMAL_ESCAPE && LA8_0<=RULE_IDENTITY_ESCAPE)||LA8_0==RULE_UNICODE_DIGIT||(LA8_0>=RULE_UNICODE_LETTER && LA8_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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

                        if ( ((LA7_0>=ExclamationMark && LA7_0<=LeftParenthesis)||(LA7_0>=Comma && LA7_0<=FullStop)||(LA7_0>=Colon && LA7_0<=EqualsSign)||(LA7_0>=LeftSquareBracket && LA7_0<=LeftCurlyBracket)||(LA7_0>=RightCurlyBracket && LA7_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA7_0>=RULE_HEX_ESCAPE && LA7_0<=RULE_UNICODE_ESCAPE)||(LA7_0>=RULE_DECIMAL_ESCAPE && LA7_0<=RULE_IDENTITY_ESCAPE)||LA7_0==RULE_UNICODE_DIGIT||(LA7_0>=RULE_UNICODE_LETTER && LA7_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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
            switch ( input.LA(1) ) {
            case DollarSign:
            case CircumflexAccent:
            case RULE_WORD_BOUNDARY:
            case RULE_NOT_WORD_BOUNDARY:
                {
                alt10=1;
                }
                break;
            case LeftParenthesis:
                {
                int LA10_2 = input.LA(2);

                if ( (LA10_2==QuestionMark) ) {
                    int LA10_4 = input.LA(3);

                    if ( (LA10_4==Colon) ) {
                        alt10=2;
                    }
                    else if ( (LA10_4==ExclamationMark||LA10_4==EqualsSign) ) {
                        alt10=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 4, input);

                        throw nvae;
                    }
                }
                else if ( ((LA10_2>=ExclamationMark && LA10_2<=RightParenthesis)||(LA10_2>=Comma && LA10_2<=FullStop)||(LA10_2>=Colon && LA10_2<=EqualsSign)||(LA10_2>=LeftSquareBracket && LA10_2<=RULE_CONTROL_LETTER_ESCAPE)||(LA10_2>=RULE_HEX_ESCAPE && LA10_2<=RULE_UNICODE_ESCAPE)||(LA10_2>=RULE_DECIMAL_ESCAPE && LA10_2<=RULE_IDENTITY_ESCAPE)||LA10_2==RULE_UNICODE_DIGIT||(LA10_2>=RULE_UNICODE_LETTER && LA10_2<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt10=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 2, input);

                    throw nvae;
                }
                }
                break;
            case ExclamationMark:
            case Comma:
            case HyphenMinus:
            case FullStop:
            case Colon:
            case EqualsSign:
            case LeftSquareBracket:
            case RightSquareBracket:
            case LeftCurlyBracket:
            case RightCurlyBracket:
            case RULE_CHARACTER_CLASS_ESCAPE:
            case RULE_CONTROL_ESCAPE:
            case RULE_CONTROL_LETTER_ESCAPE:
            case RULE_HEX_ESCAPE:
            case RULE_UNICODE_ESCAPE:
            case RULE_DECIMAL_ESCAPE:
            case RULE_IDENTITY_ESCAPE:
            case RULE_UNICODE_DIGIT:
            case RULE_UNICODE_LETTER:
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt10=2;
                }
                break;
            default:
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
    // InternalRegularExpressionParser.g:386:1: ruleAssertion returns [EObject current=null] : (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_LookAhead_3= ruleLookAhead ) ;
    public final EObject ruleAssertion() throws RecognitionException {
        EObject current = null;

        EObject this_LineStart_0 = null;

        EObject this_LineEnd_1 = null;

        EObject this_WordBoundary_2 = null;

        EObject this_LookAhead_3 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:392:2: ( (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_LookAhead_3= ruleLookAhead ) )
            // InternalRegularExpressionParser.g:393:2: (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_LookAhead_3= ruleLookAhead )
            {
            // InternalRegularExpressionParser.g:393:2: (this_LineStart_0= ruleLineStart | this_LineEnd_1= ruleLineEnd | this_WordBoundary_2= ruleWordBoundary | this_LookAhead_3= ruleLookAhead )
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
            case LeftParenthesis:
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
                    // InternalRegularExpressionParser.g:421:3: this_LookAhead_3= ruleLookAhead
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAssertionAccess().getLookAheadParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_LookAhead_3=ruleLookAhead();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_LookAhead_3;
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


    // $ANTLR start "entryRuleLookAhead"
    // InternalRegularExpressionParser.g:544:1: entryRuleLookAhead returns [EObject current=null] : iv_ruleLookAhead= ruleLookAhead EOF ;
    public final EObject entryRuleLookAhead() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookAhead = null;


        try {
            // InternalRegularExpressionParser.g:544:50: (iv_ruleLookAhead= ruleLookAhead EOF )
            // InternalRegularExpressionParser.g:545:2: iv_ruleLookAhead= ruleLookAhead EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLookAheadRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLookAhead=ruleLookAhead();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLookAhead; 
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
    // $ANTLR end "entryRuleLookAhead"


    // $ANTLR start "ruleLookAhead"
    // InternalRegularExpressionParser.g:551:1: ruleLookAhead returns [EObject current=null] : ( () otherlv_1= LeftParenthesis otherlv_2= QuestionMark (otherlv_3= EqualsSign | ( (lv_not_4_0= ExclamationMark ) ) ) ( (lv_pattern_5_0= ruleDisjunction ) ) otherlv_6= RightParenthesis ) ;
    public final EObject ruleLookAhead() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_not_4_0=null;
        Token otherlv_6=null;
        EObject lv_pattern_5_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:557:2: ( ( () otherlv_1= LeftParenthesis otherlv_2= QuestionMark (otherlv_3= EqualsSign | ( (lv_not_4_0= ExclamationMark ) ) ) ( (lv_pattern_5_0= ruleDisjunction ) ) otherlv_6= RightParenthesis ) )
            // InternalRegularExpressionParser.g:558:2: ( () otherlv_1= LeftParenthesis otherlv_2= QuestionMark (otherlv_3= EqualsSign | ( (lv_not_4_0= ExclamationMark ) ) ) ( (lv_pattern_5_0= ruleDisjunction ) ) otherlv_6= RightParenthesis )
            {
            // InternalRegularExpressionParser.g:558:2: ( () otherlv_1= LeftParenthesis otherlv_2= QuestionMark (otherlv_3= EqualsSign | ( (lv_not_4_0= ExclamationMark ) ) ) ( (lv_pattern_5_0= ruleDisjunction ) ) otherlv_6= RightParenthesis )
            // InternalRegularExpressionParser.g:559:3: () otherlv_1= LeftParenthesis otherlv_2= QuestionMark (otherlv_3= EqualsSign | ( (lv_not_4_0= ExclamationMark ) ) ) ( (lv_pattern_5_0= ruleDisjunction ) ) otherlv_6= RightParenthesis
            {
            // InternalRegularExpressionParser.g:559:3: ()
            // InternalRegularExpressionParser.g:560:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getLookAheadAccess().getLookAheadAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookAheadAccess().getLeftParenthesisKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,QuestionMark,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getLookAheadAccess().getQuestionMarkKeyword_2());
              		
            }
            // InternalRegularExpressionParser.g:574:3: (otherlv_3= EqualsSign | ( (lv_not_4_0= ExclamationMark ) ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==EqualsSign) ) {
                alt13=1;
            }
            else if ( (LA13_0==ExclamationMark) ) {
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
                    // InternalRegularExpressionParser.g:575:4: otherlv_3= EqualsSign
                    {
                    otherlv_3=(Token)match(input,EqualsSign,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getLookAheadAccess().getEqualsSignKeyword_3_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:580:4: ( (lv_not_4_0= ExclamationMark ) )
                    {
                    // InternalRegularExpressionParser.g:580:4: ( (lv_not_4_0= ExclamationMark ) )
                    // InternalRegularExpressionParser.g:581:5: (lv_not_4_0= ExclamationMark )
                    {
                    // InternalRegularExpressionParser.g:581:5: (lv_not_4_0= ExclamationMark )
                    // InternalRegularExpressionParser.g:582:6: lv_not_4_0= ExclamationMark
                    {
                    lv_not_4_0=(Token)match(input,ExclamationMark,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_not_4_0, grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getLookAheadRule());
                      						}
                      						setWithLastConsumed(current, "not", true, "!");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalRegularExpressionParser.g:595:3: ( (lv_pattern_5_0= ruleDisjunction ) )
            // InternalRegularExpressionParser.g:596:4: (lv_pattern_5_0= ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:596:4: (lv_pattern_5_0= ruleDisjunction )
            // InternalRegularExpressionParser.g:597:5: lv_pattern_5_0= ruleDisjunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getLookAheadAccess().getPatternDisjunctionParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_pattern_5_0=ruleDisjunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getLookAheadRule());
              					}
              					set(
              						current,
              						"pattern",
              						lv_pattern_5_0,
              						"org.eclipse.n4js.regex.RegularExpression.Disjunction");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getLookAheadAccess().getRightParenthesisKeyword_5());
              		
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
    // $ANTLR end "ruleLookAhead"


    // $ANTLR start "entryRuleAtom"
    // InternalRegularExpressionParser.g:622:1: entryRuleAtom returns [EObject current=null] : iv_ruleAtom= ruleAtom EOF ;
    public final EObject entryRuleAtom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtom = null;


        try {
            // InternalRegularExpressionParser.g:622:45: (iv_ruleAtom= ruleAtom EOF )
            // InternalRegularExpressionParser.g:623:2: iv_ruleAtom= ruleAtom EOF
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
    // InternalRegularExpressionParser.g:629:1: ruleAtom returns [EObject current=null] : (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup ) ;
    public final EObject ruleAtom() throws RecognitionException {
        EObject current = null;

        EObject this_PatternCharacter_0 = null;

        EObject this_Wildcard_1 = null;

        EObject this_AtomEscape_2 = null;

        EObject this_CharacterClass_3 = null;

        EObject this_Group_4 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:635:2: ( (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup ) )
            // InternalRegularExpressionParser.g:636:2: (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup )
            {
            // InternalRegularExpressionParser.g:636:2: (this_PatternCharacter_0= rulePatternCharacter | this_Wildcard_1= ruleWildcard | this_AtomEscape_2= ruleAtomEscape | this_CharacterClass_3= ruleCharacterClass | this_Group_4= ruleGroup )
            int alt14=5;
            switch ( input.LA(1) ) {
            case ExclamationMark:
            case Comma:
            case HyphenMinus:
            case Colon:
            case EqualsSign:
            case RightSquareBracket:
            case LeftCurlyBracket:
            case RightCurlyBracket:
            case RULE_UNICODE_DIGIT:
            case RULE_UNICODE_LETTER:
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt14=1;
                }
                break;
            case FullStop:
                {
                alt14=2;
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
                alt14=3;
                }
                break;
            case LeftSquareBracket:
                {
                alt14=4;
                }
                break;
            case LeftParenthesis:
                {
                alt14=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalRegularExpressionParser.g:637:3: this_PatternCharacter_0= rulePatternCharacter
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
                    // InternalRegularExpressionParser.g:646:3: this_Wildcard_1= ruleWildcard
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
                    // InternalRegularExpressionParser.g:655:3: this_AtomEscape_2= ruleAtomEscape
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
                    // InternalRegularExpressionParser.g:664:3: this_CharacterClass_3= ruleCharacterClass
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
                    // InternalRegularExpressionParser.g:673:3: this_Group_4= ruleGroup
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
    // InternalRegularExpressionParser.g:685:1: entryRulePatternCharacter returns [EObject current=null] : iv_rulePatternCharacter= rulePatternCharacter EOF ;
    public final EObject entryRulePatternCharacter() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePatternCharacter = null;


        try {
            // InternalRegularExpressionParser.g:685:57: (iv_rulePatternCharacter= rulePatternCharacter EOF )
            // InternalRegularExpressionParser.g:686:2: iv_rulePatternCharacter= rulePatternCharacter EOF
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
    // InternalRegularExpressionParser.g:692:1: rulePatternCharacter returns [EObject current=null] : ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket ) ) ) ;
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


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:698:2: ( ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket ) ) ) )
            // InternalRegularExpressionParser.g:699:2: ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket ) ) )
            {
            // InternalRegularExpressionParser.g:699:2: ( ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket ) ) )
            // InternalRegularExpressionParser.g:700:3: ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket ) )
            {
            // InternalRegularExpressionParser.g:700:3: ( (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket ) )
            // InternalRegularExpressionParser.g:701:4: (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket )
            {
            // InternalRegularExpressionParser.g:701:4: (lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH | lv_value_0_2= RULE_UNICODE_LETTER | lv_value_0_3= RULE_UNICODE_DIGIT | lv_value_0_4= HyphenMinus | lv_value_0_5= Comma | lv_value_0_6= EqualsSign | lv_value_0_7= Colon | lv_value_0_8= ExclamationMark | lv_value_0_9= LeftCurlyBracket | lv_value_0_10= RightCurlyBracket | lv_value_0_11= RightSquareBracket )
            int alt15=11;
            switch ( input.LA(1) ) {
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt15=1;
                }
                break;
            case RULE_UNICODE_LETTER:
                {
                alt15=2;
                }
                break;
            case RULE_UNICODE_DIGIT:
                {
                alt15=3;
                }
                break;
            case HyphenMinus:
                {
                alt15=4;
                }
                break;
            case Comma:
                {
                alt15=5;
                }
                break;
            case EqualsSign:
                {
                alt15=6;
                }
                break;
            case Colon:
                {
                alt15=7;
                }
                break;
            case ExclamationMark:
                {
                alt15=8;
                }
                break;
            case LeftCurlyBracket:
                {
                alt15=9;
                }
                break;
            case RightCurlyBracket:
                {
                alt15=10;
                }
                break;
            case RightSquareBracket:
                {
                alt15=11;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalRegularExpressionParser.g:702:5: lv_value_0_1= RULE_PATTERN_CHARACTER_NO_DASH
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
                    // InternalRegularExpressionParser.g:717:5: lv_value_0_2= RULE_UNICODE_LETTER
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
                    // InternalRegularExpressionParser.g:732:5: lv_value_0_3= RULE_UNICODE_DIGIT
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
                    // InternalRegularExpressionParser.g:747:5: lv_value_0_4= HyphenMinus
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
                    // InternalRegularExpressionParser.g:758:5: lv_value_0_5= Comma
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
                    // InternalRegularExpressionParser.g:769:5: lv_value_0_6= EqualsSign
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
                    // InternalRegularExpressionParser.g:780:5: lv_value_0_7= Colon
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
                    // InternalRegularExpressionParser.g:791:5: lv_value_0_8= ExclamationMark
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
                    // InternalRegularExpressionParser.g:802:5: lv_value_0_9= LeftCurlyBracket
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
                    // InternalRegularExpressionParser.g:813:5: lv_value_0_10= RightCurlyBracket
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
                    // InternalRegularExpressionParser.g:824:5: lv_value_0_11= RightSquareBracket
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
    // InternalRegularExpressionParser.g:840:1: entryRuleWildcard returns [EObject current=null] : iv_ruleWildcard= ruleWildcard EOF ;
    public final EObject entryRuleWildcard() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWildcard = null;


        try {
            // InternalRegularExpressionParser.g:840:49: (iv_ruleWildcard= ruleWildcard EOF )
            // InternalRegularExpressionParser.g:841:2: iv_ruleWildcard= ruleWildcard EOF
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
    // InternalRegularExpressionParser.g:847:1: ruleWildcard returns [EObject current=null] : ( () otherlv_1= FullStop ) ;
    public final EObject ruleWildcard() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:853:2: ( ( () otherlv_1= FullStop ) )
            // InternalRegularExpressionParser.g:854:2: ( () otherlv_1= FullStop )
            {
            // InternalRegularExpressionParser.g:854:2: ( () otherlv_1= FullStop )
            // InternalRegularExpressionParser.g:855:3: () otherlv_1= FullStop
            {
            // InternalRegularExpressionParser.g:855:3: ()
            // InternalRegularExpressionParser.g:856:4: 
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
    // InternalRegularExpressionParser.g:870:1: entryRuleAtomEscape returns [EObject current=null] : iv_ruleAtomEscape= ruleAtomEscape EOF ;
    public final EObject entryRuleAtomEscape() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomEscape = null;


        try {
            // InternalRegularExpressionParser.g:870:51: (iv_ruleAtomEscape= ruleAtomEscape EOF )
            // InternalRegularExpressionParser.g:871:2: iv_ruleAtomEscape= ruleAtomEscape EOF
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
    // InternalRegularExpressionParser.g:877:1: ruleAtomEscape returns [EObject current=null] : (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence ) ;
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
            // InternalRegularExpressionParser.g:883:2: ( (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence ) )
            // InternalRegularExpressionParser.g:884:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence )
            {
            // InternalRegularExpressionParser.g:884:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence | this_HexEscapeSequence_3= ruleHexEscapeSequence | this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence )
            int alt16=7;
            switch ( input.LA(1) ) {
            case RULE_DECIMAL_ESCAPE:
                {
                alt16=1;
                }
                break;
            case RULE_CONTROL_ESCAPE:
                {
                alt16=2;
                }
                break;
            case RULE_CONTROL_LETTER_ESCAPE:
                {
                alt16=3;
                }
                break;
            case RULE_HEX_ESCAPE:
                {
                alt16=4;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt16=5;
                }
                break;
            case RULE_IDENTITY_ESCAPE:
                {
                alt16=6;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
                {
                alt16=7;
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
                    // InternalRegularExpressionParser.g:885:3: this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence
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
                    // InternalRegularExpressionParser.g:894:3: this_CharacterEscapeSequence_1= ruleCharacterEscapeSequence
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
                    // InternalRegularExpressionParser.g:903:3: this_ControlLetterEscapeSequence_2= ruleControlLetterEscapeSequence
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
                    // InternalRegularExpressionParser.g:912:3: this_HexEscapeSequence_3= ruleHexEscapeSequence
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
                    // InternalRegularExpressionParser.g:921:3: this_UnicodeEscapeSequence_4= ruleUnicodeEscapeSequence
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
                    // InternalRegularExpressionParser.g:930:3: this_IdentityEscapeSequence_5= ruleIdentityEscapeSequence
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
                    // InternalRegularExpressionParser.g:939:3: this_CharacterClassEscapeSequence_6= ruleCharacterClassEscapeSequence
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
    // InternalRegularExpressionParser.g:951:1: entryRuleCharacterClassEscapeSequence returns [EObject current=null] : iv_ruleCharacterClassEscapeSequence= ruleCharacterClassEscapeSequence EOF ;
    public final EObject entryRuleCharacterClassEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClassEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:951:69: (iv_ruleCharacterClassEscapeSequence= ruleCharacterClassEscapeSequence EOF )
            // InternalRegularExpressionParser.g:952:2: iv_ruleCharacterClassEscapeSequence= ruleCharacterClassEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:958:1: ruleCharacterClassEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) ) ;
    public final EObject ruleCharacterClassEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:964:2: ( ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:965:2: ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:965:2: ( (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE ) )
            // InternalRegularExpressionParser.g:966:3: (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE )
            {
            // InternalRegularExpressionParser.g:966:3: (lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE )
            // InternalRegularExpressionParser.g:967:4: lv_sequence_0_0= RULE_CHARACTER_CLASS_ESCAPE
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
    // InternalRegularExpressionParser.g:986:1: entryRuleCharacterEscapeSequence returns [EObject current=null] : iv_ruleCharacterEscapeSequence= ruleCharacterEscapeSequence EOF ;
    public final EObject entryRuleCharacterEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:986:64: (iv_ruleCharacterEscapeSequence= ruleCharacterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:987:2: iv_ruleCharacterEscapeSequence= ruleCharacterEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:993:1: ruleCharacterEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) ) ;
    public final EObject ruleCharacterEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:999:2: ( ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1000:2: ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1000:2: ( (lv_sequence_0_0= RULE_CONTROL_ESCAPE ) )
            // InternalRegularExpressionParser.g:1001:3: (lv_sequence_0_0= RULE_CONTROL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1001:3: (lv_sequence_0_0= RULE_CONTROL_ESCAPE )
            // InternalRegularExpressionParser.g:1002:4: lv_sequence_0_0= RULE_CONTROL_ESCAPE
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
    // InternalRegularExpressionParser.g:1021:1: entryRuleControlLetterEscapeSequence returns [EObject current=null] : iv_ruleControlLetterEscapeSequence= ruleControlLetterEscapeSequence EOF ;
    public final EObject entryRuleControlLetterEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleControlLetterEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1021:68: (iv_ruleControlLetterEscapeSequence= ruleControlLetterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1022:2: iv_ruleControlLetterEscapeSequence= ruleControlLetterEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:1028:1: ruleControlLetterEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) ) ;
    public final EObject ruleControlLetterEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1034:2: ( ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1035:2: ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1035:2: ( (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE ) )
            // InternalRegularExpressionParser.g:1036:3: (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1036:3: (lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE )
            // InternalRegularExpressionParser.g:1037:4: lv_sequence_0_0= RULE_CONTROL_LETTER_ESCAPE
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
    // InternalRegularExpressionParser.g:1056:1: entryRuleHexEscapeSequence returns [EObject current=null] : iv_ruleHexEscapeSequence= ruleHexEscapeSequence EOF ;
    public final EObject entryRuleHexEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHexEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1056:58: (iv_ruleHexEscapeSequence= ruleHexEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1057:2: iv_ruleHexEscapeSequence= ruleHexEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:1063:1: ruleHexEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) ) ;
    public final EObject ruleHexEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1069:2: ( ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1070:2: ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1070:2: ( (lv_sequence_0_0= RULE_HEX_ESCAPE ) )
            // InternalRegularExpressionParser.g:1071:3: (lv_sequence_0_0= RULE_HEX_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1071:3: (lv_sequence_0_0= RULE_HEX_ESCAPE )
            // InternalRegularExpressionParser.g:1072:4: lv_sequence_0_0= RULE_HEX_ESCAPE
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
    // InternalRegularExpressionParser.g:1091:1: entryRuleUnicodeEscapeSequence returns [EObject current=null] : iv_ruleUnicodeEscapeSequence= ruleUnicodeEscapeSequence EOF ;
    public final EObject entryRuleUnicodeEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnicodeEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1091:62: (iv_ruleUnicodeEscapeSequence= ruleUnicodeEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1092:2: iv_ruleUnicodeEscapeSequence= ruleUnicodeEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:1098:1: ruleUnicodeEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) ) ;
    public final EObject ruleUnicodeEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1104:2: ( ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1105:2: ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1105:2: ( (lv_sequence_0_0= RULE_UNICODE_ESCAPE ) )
            // InternalRegularExpressionParser.g:1106:3: (lv_sequence_0_0= RULE_UNICODE_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1106:3: (lv_sequence_0_0= RULE_UNICODE_ESCAPE )
            // InternalRegularExpressionParser.g:1107:4: lv_sequence_0_0= RULE_UNICODE_ESCAPE
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
    // InternalRegularExpressionParser.g:1126:1: entryRuleIdentityEscapeSequence returns [EObject current=null] : iv_ruleIdentityEscapeSequence= ruleIdentityEscapeSequence EOF ;
    public final EObject entryRuleIdentityEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIdentityEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1126:63: (iv_ruleIdentityEscapeSequence= ruleIdentityEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1127:2: iv_ruleIdentityEscapeSequence= ruleIdentityEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:1133:1: ruleIdentityEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) ) ;
    public final EObject ruleIdentityEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1139:2: ( ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1140:2: ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1140:2: ( (lv_sequence_0_0= RULE_IDENTITY_ESCAPE ) )
            // InternalRegularExpressionParser.g:1141:3: (lv_sequence_0_0= RULE_IDENTITY_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1141:3: (lv_sequence_0_0= RULE_IDENTITY_ESCAPE )
            // InternalRegularExpressionParser.g:1142:4: lv_sequence_0_0= RULE_IDENTITY_ESCAPE
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
    // InternalRegularExpressionParser.g:1161:1: entryRuleDecimalEscapeSequence returns [EObject current=null] : iv_ruleDecimalEscapeSequence= ruleDecimalEscapeSequence EOF ;
    public final EObject entryRuleDecimalEscapeSequence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecimalEscapeSequence = null;


        try {
            // InternalRegularExpressionParser.g:1161:62: (iv_ruleDecimalEscapeSequence= ruleDecimalEscapeSequence EOF )
            // InternalRegularExpressionParser.g:1162:2: iv_ruleDecimalEscapeSequence= ruleDecimalEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:1168:1: ruleDecimalEscapeSequence returns [EObject current=null] : ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) ) ;
    public final EObject ruleDecimalEscapeSequence() throws RecognitionException {
        EObject current = null;

        Token lv_sequence_0_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1174:2: ( ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) ) )
            // InternalRegularExpressionParser.g:1175:2: ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) )
            {
            // InternalRegularExpressionParser.g:1175:2: ( (lv_sequence_0_0= RULE_DECIMAL_ESCAPE ) )
            // InternalRegularExpressionParser.g:1176:3: (lv_sequence_0_0= RULE_DECIMAL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:1176:3: (lv_sequence_0_0= RULE_DECIMAL_ESCAPE )
            // InternalRegularExpressionParser.g:1177:4: lv_sequence_0_0= RULE_DECIMAL_ESCAPE
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
    // InternalRegularExpressionParser.g:1196:1: entryRuleCharacterClass returns [EObject current=null] : iv_ruleCharacterClass= ruleCharacterClass EOF ;
    public final EObject entryRuleCharacterClass() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClass = null;


        try {
            // InternalRegularExpressionParser.g:1196:55: (iv_ruleCharacterClass= ruleCharacterClass EOF )
            // InternalRegularExpressionParser.g:1197:2: iv_ruleCharacterClass= ruleCharacterClass EOF
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
    // InternalRegularExpressionParser.g:1203:1: ruleCharacterClass returns [EObject current=null] : ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket ) ;
    public final EObject ruleCharacterClass() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_negated_2_0=null;
        Token otherlv_4=null;
        EObject lv_elements_3_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1209:2: ( ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket ) )
            // InternalRegularExpressionParser.g:1210:2: ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket )
            {
            // InternalRegularExpressionParser.g:1210:2: ( () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket )
            // InternalRegularExpressionParser.g:1211:3: () otherlv_1= LeftSquareBracket ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )? ( (lv_elements_3_0= ruleCharacterClassElement ) )* otherlv_4= RightSquareBracket
            {
            // InternalRegularExpressionParser.g:1211:3: ()
            // InternalRegularExpressionParser.g:1212:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getCharacterClassAccess().getCharacterClassAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftSquareBracket,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getCharacterClassAccess().getLeftSquareBracketKeyword_1());
              		
            }
            // InternalRegularExpressionParser.g:1222:3: ( ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==CircumflexAccent) ) {
                int LA17_1 = input.LA(2);

                if ( (synpred2_InternalRegularExpressionParser()) ) {
                    alt17=1;
                }
            }
            switch (alt17) {
                case 1 :
                    // InternalRegularExpressionParser.g:1223:4: ( ( ( CircumflexAccent ) ) )=> ( (lv_negated_2_0= CircumflexAccent ) )
                    {
                    // InternalRegularExpressionParser.g:1229:4: ( (lv_negated_2_0= CircumflexAccent ) )
                    // InternalRegularExpressionParser.g:1230:5: (lv_negated_2_0= CircumflexAccent )
                    {
                    // InternalRegularExpressionParser.g:1230:5: (lv_negated_2_0= CircumflexAccent )
                    // InternalRegularExpressionParser.g:1231:6: lv_negated_2_0= CircumflexAccent
                    {
                    lv_negated_2_0=(Token)match(input,CircumflexAccent,FOLLOW_14); if (state.failed) return current;
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

            // InternalRegularExpressionParser.g:1244:3: ( (lv_elements_3_0= ruleCharacterClassElement ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=ExclamationMark && LA18_0<=LeftSquareBracket)||(LA18_0>=CircumflexAccent && LA18_0<=RULE_WORD_BOUNDARY)||(LA18_0>=RULE_CHARACTER_CLASS_ESCAPE && LA18_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA18_0>=RULE_HEX_ESCAPE && LA18_0<=RULE_UNICODE_ESCAPE)||(LA18_0>=RULE_DECIMAL_ESCAPE && LA18_0<=RULE_IDENTITY_ESCAPE)||LA18_0==RULE_UNICODE_DIGIT||(LA18_0>=RULE_UNICODE_LETTER && LA18_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:1245:4: (lv_elements_3_0= ruleCharacterClassElement )
            	    {
            	    // InternalRegularExpressionParser.g:1245:4: (lv_elements_3_0= ruleCharacterClassElement )
            	    // InternalRegularExpressionParser.g:1246:5: lv_elements_3_0= ruleCharacterClassElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getCharacterClassAccess().getElementsCharacterClassElementParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_14);
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
            	    break loop18;
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
    // InternalRegularExpressionParser.g:1271:1: entryRuleCharacterClassElement returns [EObject current=null] : iv_ruleCharacterClassElement= ruleCharacterClassElement EOF ;
    public final EObject entryRuleCharacterClassElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClassElement = null;


        try {
            // InternalRegularExpressionParser.g:1271:62: (iv_ruleCharacterClassElement= ruleCharacterClassElement EOF )
            // InternalRegularExpressionParser.g:1272:2: iv_ruleCharacterClassElement= ruleCharacterClassElement EOF
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
    // InternalRegularExpressionParser.g:1278:1: ruleCharacterClassElement returns [EObject current=null] : (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? ) ;
    public final EObject ruleCharacterClassElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_CharacterClassAtom_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1284:2: ( (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? ) )
            // InternalRegularExpressionParser.g:1285:2: (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? )
            {
            // InternalRegularExpressionParser.g:1285:2: (this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )? )
            // InternalRegularExpressionParser.g:1286:3: this_CharacterClassAtom_0= ruleCharacterClassAtom ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCharacterClassElementAccess().getCharacterClassAtomParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_15);
            this_CharacterClassAtom_0=ruleCharacterClassAtom();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_CharacterClassAtom_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalRegularExpressionParser.g:1294:3: ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )?
            int alt19=2;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // InternalRegularExpressionParser.g:1295:4: ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) )
                    {
                    // InternalRegularExpressionParser.g:1306:4: ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) )
                    // InternalRegularExpressionParser.g:1307:5: () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) )
                    {
                    // InternalRegularExpressionParser.g:1307:5: ()
                    // InternalRegularExpressionParser.g:1308:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }

                    otherlv_2=(Token)match(input,HyphenMinus,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getCharacterClassElementAccess().getHyphenMinusKeyword_1_0_1());
                      				
                    }
                    // InternalRegularExpressionParser.g:1318:5: ( (lv_right_3_0= ruleCharacterClassAtom ) )
                    // InternalRegularExpressionParser.g:1319:6: (lv_right_3_0= ruleCharacterClassAtom )
                    {
                    // InternalRegularExpressionParser.g:1319:6: (lv_right_3_0= ruleCharacterClassAtom )
                    // InternalRegularExpressionParser.g:1320:7: lv_right_3_0= ruleCharacterClassAtom
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
    // InternalRegularExpressionParser.g:1343:1: entryRuleCharacterClassAtom returns [EObject current=null] : iv_ruleCharacterClassAtom= ruleCharacterClassAtom EOF ;
    public final EObject entryRuleCharacterClassAtom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCharacterClassAtom = null;


        try {
            // InternalRegularExpressionParser.g:1343:59: (iv_ruleCharacterClassAtom= ruleCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:1344:2: iv_ruleCharacterClassAtom= ruleCharacterClassAtom EOF
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
    // InternalRegularExpressionParser.g:1350:1: ruleCharacterClassAtom returns [EObject current=null] : (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) ) ) ) ;
    public final EObject ruleCharacterClassAtom() throws RecognitionException {
        EObject current = null;

        Token lv_character_1_1=null;
        Token lv_character_1_2=null;
        Token lv_character_1_3=null;
        Token lv_character_1_4=null;
        Token lv_character_1_5=null;
        Token lv_character_1_6=null;
        Token lv_character_1_7=null;
        Token lv_character_1_8=null;
        Token lv_character_1_9=null;
        Token lv_character_1_10=null;
        Token lv_character_1_11=null;
        Token lv_character_1_12=null;
        Token lv_character_1_13=null;
        Token lv_character_1_14=null;
        Token lv_character_1_15=null;
        Token lv_character_1_16=null;
        Token lv_character_1_17=null;
        Token lv_character_1_18=null;
        Token lv_character_1_19=null;
        Token lv_character_1_20=null;
        Token lv_character_1_21=null;
        EObject this_EscapedCharacterClassAtom_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1356:2: ( (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) ) ) ) )
            // InternalRegularExpressionParser.g:1357:2: (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) ) ) )
            {
            // InternalRegularExpressionParser.g:1357:2: (this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom | ( ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) ) ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_WORD_BOUNDARY||(LA21_0>=RULE_CHARACTER_CLASS_ESCAPE && LA21_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA21_0>=RULE_HEX_ESCAPE && LA21_0<=RULE_UNICODE_ESCAPE)||(LA21_0>=RULE_DECIMAL_ESCAPE && LA21_0<=RULE_IDENTITY_ESCAPE)) ) {
                alt21=1;
            }
            else if ( ((LA21_0>=ExclamationMark && LA21_0<=LeftSquareBracket)||(LA21_0>=CircumflexAccent && LA21_0<=RightCurlyBracket)||LA21_0==RULE_UNICODE_DIGIT||(LA21_0>=RULE_UNICODE_LETTER && LA21_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt21=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalRegularExpressionParser.g:1358:3: this_EscapedCharacterClassAtom_0= ruleEscapedCharacterClassAtom
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
                    // InternalRegularExpressionParser.g:1367:3: ( ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) ) )
                    {
                    // InternalRegularExpressionParser.g:1367:3: ( ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) ) )
                    // InternalRegularExpressionParser.g:1368:4: ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) )
                    {
                    // InternalRegularExpressionParser.g:1368:4: ( (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT ) )
                    // InternalRegularExpressionParser.g:1369:5: (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT )
                    {
                    // InternalRegularExpressionParser.g:1369:5: (lv_character_1_1= Comma | lv_character_1_2= EqualsSign | lv_character_1_3= Colon | lv_character_1_4= ExclamationMark | lv_character_1_5= HyphenMinus | lv_character_1_6= CircumflexAccent | lv_character_1_7= DollarSign | lv_character_1_8= FullStop | lv_character_1_9= Asterisk | lv_character_1_10= PlusSign | lv_character_1_11= QuestionMark | lv_character_1_12= LeftParenthesis | lv_character_1_13= RightParenthesis | lv_character_1_14= LeftSquareBracket | lv_character_1_15= LeftCurlyBracket | lv_character_1_16= RightCurlyBracket | lv_character_1_17= VerticalLine | lv_character_1_18= Solidus | lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH | lv_character_1_20= RULE_UNICODE_LETTER | lv_character_1_21= RULE_UNICODE_DIGIT )
                    int alt20=21;
                    switch ( input.LA(1) ) {
                    case Comma:
                        {
                        alt20=1;
                        }
                        break;
                    case EqualsSign:
                        {
                        alt20=2;
                        }
                        break;
                    case Colon:
                        {
                        alt20=3;
                        }
                        break;
                    case ExclamationMark:
                        {
                        alt20=4;
                        }
                        break;
                    case HyphenMinus:
                        {
                        alt20=5;
                        }
                        break;
                    case CircumflexAccent:
                        {
                        alt20=6;
                        }
                        break;
                    case DollarSign:
                        {
                        alt20=7;
                        }
                        break;
                    case FullStop:
                        {
                        alt20=8;
                        }
                        break;
                    case Asterisk:
                        {
                        alt20=9;
                        }
                        break;
                    case PlusSign:
                        {
                        alt20=10;
                        }
                        break;
                    case QuestionMark:
                        {
                        alt20=11;
                        }
                        break;
                    case LeftParenthesis:
                        {
                        alt20=12;
                        }
                        break;
                    case RightParenthesis:
                        {
                        alt20=13;
                        }
                        break;
                    case LeftSquareBracket:
                        {
                        alt20=14;
                        }
                        break;
                    case LeftCurlyBracket:
                        {
                        alt20=15;
                        }
                        break;
                    case RightCurlyBracket:
                        {
                        alt20=16;
                        }
                        break;
                    case VerticalLine:
                        {
                        alt20=17;
                        }
                        break;
                    case Solidus:
                        {
                        alt20=18;
                        }
                        break;
                    case RULE_PATTERN_CHARACTER_NO_DASH:
                        {
                        alt20=19;
                        }
                        break;
                    case RULE_UNICODE_LETTER:
                        {
                        alt20=20;
                        }
                        break;
                    case RULE_UNICODE_DIGIT:
                        {
                        alt20=21;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 0, input);

                        throw nvae;
                    }

                    switch (alt20) {
                        case 1 :
                            // InternalRegularExpressionParser.g:1370:6: lv_character_1_1= Comma
                            {
                            lv_character_1_1=(Token)match(input,Comma,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_1, grammarAccess.getCharacterClassAtomAccess().getCharacterCommaKeyword_1_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalRegularExpressionParser.g:1381:6: lv_character_1_2= EqualsSign
                            {
                            lv_character_1_2=(Token)match(input,EqualsSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_2, grammarAccess.getCharacterClassAtomAccess().getCharacterEqualsSignKeyword_1_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_2, null);
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalRegularExpressionParser.g:1392:6: lv_character_1_3= Colon
                            {
                            lv_character_1_3=(Token)match(input,Colon,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_3, grammarAccess.getCharacterClassAtomAccess().getCharacterColonKeyword_1_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_3, null);
                              					
                            }

                            }
                            break;
                        case 4 :
                            // InternalRegularExpressionParser.g:1403:6: lv_character_1_4= ExclamationMark
                            {
                            lv_character_1_4=(Token)match(input,ExclamationMark,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_4, grammarAccess.getCharacterClassAtomAccess().getCharacterExclamationMarkKeyword_1_0_3());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_4, null);
                              					
                            }

                            }
                            break;
                        case 5 :
                            // InternalRegularExpressionParser.g:1414:6: lv_character_1_5= HyphenMinus
                            {
                            lv_character_1_5=(Token)match(input,HyphenMinus,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_5, grammarAccess.getCharacterClassAtomAccess().getCharacterHyphenMinusKeyword_1_0_4());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_5, null);
                              					
                            }

                            }
                            break;
                        case 6 :
                            // InternalRegularExpressionParser.g:1425:6: lv_character_1_6= CircumflexAccent
                            {
                            lv_character_1_6=(Token)match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_6, grammarAccess.getCharacterClassAtomAccess().getCharacterCircumflexAccentKeyword_1_0_5());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_6, null);
                              					
                            }

                            }
                            break;
                        case 7 :
                            // InternalRegularExpressionParser.g:1436:6: lv_character_1_7= DollarSign
                            {
                            lv_character_1_7=(Token)match(input,DollarSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_7, grammarAccess.getCharacterClassAtomAccess().getCharacterDollarSignKeyword_1_0_6());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_7, null);
                              					
                            }

                            }
                            break;
                        case 8 :
                            // InternalRegularExpressionParser.g:1447:6: lv_character_1_8= FullStop
                            {
                            lv_character_1_8=(Token)match(input,FullStop,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_8, grammarAccess.getCharacterClassAtomAccess().getCharacterFullStopKeyword_1_0_7());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_8, null);
                              					
                            }

                            }
                            break;
                        case 9 :
                            // InternalRegularExpressionParser.g:1458:6: lv_character_1_9= Asterisk
                            {
                            lv_character_1_9=(Token)match(input,Asterisk,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_9, grammarAccess.getCharacterClassAtomAccess().getCharacterAsteriskKeyword_1_0_8());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_9, null);
                              					
                            }

                            }
                            break;
                        case 10 :
                            // InternalRegularExpressionParser.g:1469:6: lv_character_1_10= PlusSign
                            {
                            lv_character_1_10=(Token)match(input,PlusSign,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_10, grammarAccess.getCharacterClassAtomAccess().getCharacterPlusSignKeyword_1_0_9());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_10, null);
                              					
                            }

                            }
                            break;
                        case 11 :
                            // InternalRegularExpressionParser.g:1480:6: lv_character_1_11= QuestionMark
                            {
                            lv_character_1_11=(Token)match(input,QuestionMark,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_11, grammarAccess.getCharacterClassAtomAccess().getCharacterQuestionMarkKeyword_1_0_10());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_11, null);
                              					
                            }

                            }
                            break;
                        case 12 :
                            // InternalRegularExpressionParser.g:1491:6: lv_character_1_12= LeftParenthesis
                            {
                            lv_character_1_12=(Token)match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_12, grammarAccess.getCharacterClassAtomAccess().getCharacterLeftParenthesisKeyword_1_0_11());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_12, null);
                              					
                            }

                            }
                            break;
                        case 13 :
                            // InternalRegularExpressionParser.g:1502:6: lv_character_1_13= RightParenthesis
                            {
                            lv_character_1_13=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_13, grammarAccess.getCharacterClassAtomAccess().getCharacterRightParenthesisKeyword_1_0_12());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_13, null);
                              					
                            }

                            }
                            break;
                        case 14 :
                            // InternalRegularExpressionParser.g:1513:6: lv_character_1_14= LeftSquareBracket
                            {
                            lv_character_1_14=(Token)match(input,LeftSquareBracket,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_14, grammarAccess.getCharacterClassAtomAccess().getCharacterLeftSquareBracketKeyword_1_0_13());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_14, null);
                              					
                            }

                            }
                            break;
                        case 15 :
                            // InternalRegularExpressionParser.g:1524:6: lv_character_1_15= LeftCurlyBracket
                            {
                            lv_character_1_15=(Token)match(input,LeftCurlyBracket,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_15, grammarAccess.getCharacterClassAtomAccess().getCharacterLeftCurlyBracketKeyword_1_0_14());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_15, null);
                              					
                            }

                            }
                            break;
                        case 16 :
                            // InternalRegularExpressionParser.g:1535:6: lv_character_1_16= RightCurlyBracket
                            {
                            lv_character_1_16=(Token)match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_16, grammarAccess.getCharacterClassAtomAccess().getCharacterRightCurlyBracketKeyword_1_0_15());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_16, null);
                              					
                            }

                            }
                            break;
                        case 17 :
                            // InternalRegularExpressionParser.g:1546:6: lv_character_1_17= VerticalLine
                            {
                            lv_character_1_17=(Token)match(input,VerticalLine,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_17, grammarAccess.getCharacterClassAtomAccess().getCharacterVerticalLineKeyword_1_0_16());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_17, null);
                              					
                            }

                            }
                            break;
                        case 18 :
                            // InternalRegularExpressionParser.g:1557:6: lv_character_1_18= Solidus
                            {
                            lv_character_1_18=(Token)match(input,Solidus,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_18, grammarAccess.getCharacterClassAtomAccess().getCharacterSolidusKeyword_1_0_17());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(current, "character", lv_character_1_18, null);
                              					
                            }

                            }
                            break;
                        case 19 :
                            // InternalRegularExpressionParser.g:1568:6: lv_character_1_19= RULE_PATTERN_CHARACTER_NO_DASH
                            {
                            lv_character_1_19=(Token)match(input,RULE_PATTERN_CHARACTER_NO_DASH,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_19, grammarAccess.getCharacterClassAtomAccess().getCharacterPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_18());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"character",
                              							lv_character_1_19,
                              							"org.eclipse.n4js.regex.RegularExpression.PATTERN_CHARACTER_NO_DASH");
                              					
                            }

                            }
                            break;
                        case 20 :
                            // InternalRegularExpressionParser.g:1583:6: lv_character_1_20= RULE_UNICODE_LETTER
                            {
                            lv_character_1_20=(Token)match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_20, grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_LETTERTerminalRuleCall_1_0_19());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"character",
                              							lv_character_1_20,
                              							"org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
                              					
                            }

                            }
                            break;
                        case 21 :
                            // InternalRegularExpressionParser.g:1598:6: lv_character_1_21= RULE_UNICODE_DIGIT
                            {
                            lv_character_1_21=(Token)match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_character_1_21, grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_DIGITTerminalRuleCall_1_0_20());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCharacterClassAtomRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"character",
                              							lv_character_1_21,
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
    // InternalRegularExpressionParser.g:1619:1: entryRuleEscapedCharacterClassAtom returns [EObject current=null] : iv_ruleEscapedCharacterClassAtom= ruleEscapedCharacterClassAtom EOF ;
    public final EObject entryRuleEscapedCharacterClassAtom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEscapedCharacterClassAtom = null;


        try {
            // InternalRegularExpressionParser.g:1619:66: (iv_ruleEscapedCharacterClassAtom= ruleEscapedCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:1620:2: iv_ruleEscapedCharacterClassAtom= ruleEscapedCharacterClassAtom EOF
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
    // InternalRegularExpressionParser.g:1626:1: ruleEscapedCharacterClassAtom returns [EObject current=null] : (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence ) ;
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
            // InternalRegularExpressionParser.g:1632:2: ( (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence ) )
            // InternalRegularExpressionParser.g:1633:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence )
            {
            // InternalRegularExpressionParser.g:1633:2: (this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence | this_Backspace_1= ruleBackspace | this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence | this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence | this_HexEscapeSequence_4= ruleHexEscapeSequence | this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence | this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence | this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence )
            int alt22=8;
            switch ( input.LA(1) ) {
            case RULE_DECIMAL_ESCAPE:
                {
                alt22=1;
                }
                break;
            case RULE_WORD_BOUNDARY:
                {
                alt22=2;
                }
                break;
            case RULE_CONTROL_ESCAPE:
                {
                alt22=3;
                }
                break;
            case RULE_CONTROL_LETTER_ESCAPE:
                {
                alt22=4;
                }
                break;
            case RULE_HEX_ESCAPE:
                {
                alt22=5;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt22=6;
                }
                break;
            case RULE_IDENTITY_ESCAPE:
                {
                alt22=7;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
                {
                alt22=8;
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
                    // InternalRegularExpressionParser.g:1634:3: this_DecimalEscapeSequence_0= ruleDecimalEscapeSequence
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
                    // InternalRegularExpressionParser.g:1643:3: this_Backspace_1= ruleBackspace
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
                    // InternalRegularExpressionParser.g:1652:3: this_CharacterEscapeSequence_2= ruleCharacterEscapeSequence
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
                    // InternalRegularExpressionParser.g:1661:3: this_ControlLetterEscapeSequence_3= ruleControlLetterEscapeSequence
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
                    // InternalRegularExpressionParser.g:1670:3: this_HexEscapeSequence_4= ruleHexEscapeSequence
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
                    // InternalRegularExpressionParser.g:1679:3: this_UnicodeEscapeSequence_5= ruleUnicodeEscapeSequence
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
                    // InternalRegularExpressionParser.g:1688:3: this_IdentityEscapeSequence_6= ruleIdentityEscapeSequence
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
                    // InternalRegularExpressionParser.g:1697:3: this_CharacterClassEscapeSequence_7= ruleCharacterClassEscapeSequence
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
    // InternalRegularExpressionParser.g:1709:1: entryRuleBackspace returns [EObject current=null] : iv_ruleBackspace= ruleBackspace EOF ;
    public final EObject entryRuleBackspace() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBackspace = null;


        try {
            // InternalRegularExpressionParser.g:1709:50: (iv_ruleBackspace= ruleBackspace EOF )
            // InternalRegularExpressionParser.g:1710:2: iv_ruleBackspace= ruleBackspace EOF
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
    // InternalRegularExpressionParser.g:1716:1: ruleBackspace returns [EObject current=null] : ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY ) ;
    public final EObject ruleBackspace() throws RecognitionException {
        EObject current = null;

        Token this_WORD_BOUNDARY_1=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1722:2: ( ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY ) )
            // InternalRegularExpressionParser.g:1723:2: ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY )
            {
            // InternalRegularExpressionParser.g:1723:2: ( () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY )
            // InternalRegularExpressionParser.g:1724:3: () this_WORD_BOUNDARY_1= RULE_WORD_BOUNDARY
            {
            // InternalRegularExpressionParser.g:1724:3: ()
            // InternalRegularExpressionParser.g:1725:4: 
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
    // InternalRegularExpressionParser.g:1739:1: entryRuleGroup returns [EObject current=null] : iv_ruleGroup= ruleGroup EOF ;
    public final EObject entryRuleGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGroup = null;


        try {
            // InternalRegularExpressionParser.g:1739:46: (iv_ruleGroup= ruleGroup EOF )
            // InternalRegularExpressionParser.g:1740:2: iv_ruleGroup= ruleGroup EOF
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
    // InternalRegularExpressionParser.g:1746:1: ruleGroup returns [EObject current=null] : ( () otherlv_1= LeftParenthesis ( ( (lv_nonCapturing_2_0= QuestionMark ) ) otherlv_3= Colon )? ( (lv_pattern_4_0= ruleDisjunction ) ) otherlv_5= RightParenthesis ) ;
    public final EObject ruleGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_nonCapturing_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_pattern_4_0 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1752:2: ( ( () otherlv_1= LeftParenthesis ( ( (lv_nonCapturing_2_0= QuestionMark ) ) otherlv_3= Colon )? ( (lv_pattern_4_0= ruleDisjunction ) ) otherlv_5= RightParenthesis ) )
            // InternalRegularExpressionParser.g:1753:2: ( () otherlv_1= LeftParenthesis ( ( (lv_nonCapturing_2_0= QuestionMark ) ) otherlv_3= Colon )? ( (lv_pattern_4_0= ruleDisjunction ) ) otherlv_5= RightParenthesis )
            {
            // InternalRegularExpressionParser.g:1753:2: ( () otherlv_1= LeftParenthesis ( ( (lv_nonCapturing_2_0= QuestionMark ) ) otherlv_3= Colon )? ( (lv_pattern_4_0= ruleDisjunction ) ) otherlv_5= RightParenthesis )
            // InternalRegularExpressionParser.g:1754:3: () otherlv_1= LeftParenthesis ( ( (lv_nonCapturing_2_0= QuestionMark ) ) otherlv_3= Colon )? ( (lv_pattern_4_0= ruleDisjunction ) ) otherlv_5= RightParenthesis
            {
            // InternalRegularExpressionParser.g:1754:3: ()
            // InternalRegularExpressionParser.g:1755:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getGroupAccess().getGroupAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalRegularExpressionParser.g:1765:3: ( ( (lv_nonCapturing_2_0= QuestionMark ) ) otherlv_3= Colon )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==QuestionMark) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalRegularExpressionParser.g:1766:4: ( (lv_nonCapturing_2_0= QuestionMark ) ) otherlv_3= Colon
                    {
                    // InternalRegularExpressionParser.g:1766:4: ( (lv_nonCapturing_2_0= QuestionMark ) )
                    // InternalRegularExpressionParser.g:1767:5: (lv_nonCapturing_2_0= QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:1767:5: (lv_nonCapturing_2_0= QuestionMark )
                    // InternalRegularExpressionParser.g:1768:6: lv_nonCapturing_2_0= QuestionMark
                    {
                    lv_nonCapturing_2_0=(Token)match(input,QuestionMark,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nonCapturing_2_0, grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getGroupRule());
                      						}
                      						setWithLastConsumed(current, "nonCapturing", true, "?");
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,Colon,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getGroupAccess().getColonKeyword_2_1());
                      			
                    }

                    }
                    break;

            }

            // InternalRegularExpressionParser.g:1785:3: ( (lv_pattern_4_0= ruleDisjunction ) )
            // InternalRegularExpressionParser.g:1786:4: (lv_pattern_4_0= ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:1786:4: (lv_pattern_4_0= ruleDisjunction )
            // InternalRegularExpressionParser.g:1787:5: lv_pattern_4_0= ruleDisjunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getGroupAccess().getPatternDisjunctionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_pattern_4_0=ruleDisjunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getGroupRule());
              					}
              					set(
              						current,
              						"pattern",
              						lv_pattern_4_0,
              						"org.eclipse.n4js.regex.RegularExpression.Disjunction");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getGroupAccess().getRightParenthesisKeyword_4());
              		
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


    // $ANTLR start "entryRuleQuantifier"
    // InternalRegularExpressionParser.g:1812:1: entryRuleQuantifier returns [EObject current=null] : iv_ruleQuantifier= ruleQuantifier EOF ;
    public final EObject entryRuleQuantifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantifier = null;


        try {
            // InternalRegularExpressionParser.g:1812:51: (iv_ruleQuantifier= ruleQuantifier EOF )
            // InternalRegularExpressionParser.g:1813:2: iv_ruleQuantifier= ruleQuantifier EOF
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
    // InternalRegularExpressionParser.g:1819:1: ruleQuantifier returns [EObject current=null] : (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier ) ;
    public final EObject ruleQuantifier() throws RecognitionException {
        EObject current = null;

        EObject this_SimpleQuantifier_0 = null;

        EObject this_ExactQuantifier_1 = null;



        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1825:2: ( (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier ) )
            // InternalRegularExpressionParser.g:1826:2: (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier )
            {
            // InternalRegularExpressionParser.g:1826:2: (this_SimpleQuantifier_0= ruleSimpleQuantifier | this_ExactQuantifier_1= ruleExactQuantifier )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0>=Asterisk && LA24_0<=PlusSign)||LA24_0==QuestionMark) ) {
                alt24=1;
            }
            else if ( (LA24_0==LeftCurlyBracket) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalRegularExpressionParser.g:1827:3: this_SimpleQuantifier_0= ruleSimpleQuantifier
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
                    // InternalRegularExpressionParser.g:1836:3: this_ExactQuantifier_1= ruleExactQuantifier
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
    // InternalRegularExpressionParser.g:1848:1: entryRuleSimpleQuantifier returns [EObject current=null] : iv_ruleSimpleQuantifier= ruleSimpleQuantifier EOF ;
    public final EObject entryRuleSimpleQuantifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleQuantifier = null;


        try {
            // InternalRegularExpressionParser.g:1848:57: (iv_ruleSimpleQuantifier= ruleSimpleQuantifier EOF )
            // InternalRegularExpressionParser.g:1849:2: iv_ruleSimpleQuantifier= ruleSimpleQuantifier EOF
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
    // InternalRegularExpressionParser.g:1855:1: ruleSimpleQuantifier returns [EObject current=null] : ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? ) ;
    public final EObject ruleSimpleQuantifier() throws RecognitionException {
        EObject current = null;

        Token lv_quantifier_0_1=null;
        Token lv_quantifier_0_2=null;
        Token lv_quantifier_0_3=null;
        Token lv_nonGreedy_1_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:1861:2: ( ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? ) )
            // InternalRegularExpressionParser.g:1862:2: ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? )
            {
            // InternalRegularExpressionParser.g:1862:2: ( ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )? )
            // InternalRegularExpressionParser.g:1863:3: ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) ) ( (lv_nonGreedy_1_0= QuestionMark ) )?
            {
            // InternalRegularExpressionParser.g:1863:3: ( ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) ) )
            // InternalRegularExpressionParser.g:1864:4: ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:1864:4: ( (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark ) )
            // InternalRegularExpressionParser.g:1865:5: (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark )
            {
            // InternalRegularExpressionParser.g:1865:5: (lv_quantifier_0_1= PlusSign | lv_quantifier_0_2= Asterisk | lv_quantifier_0_3= QuestionMark )
            int alt25=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                alt25=1;
                }
                break;
            case Asterisk:
                {
                alt25=2;
                }
                break;
            case QuestionMark:
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
                    // InternalRegularExpressionParser.g:1866:6: lv_quantifier_0_1= PlusSign
                    {
                    lv_quantifier_0_1=(Token)match(input,PlusSign,FOLLOW_19); if (state.failed) return current;
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
                    // InternalRegularExpressionParser.g:1877:6: lv_quantifier_0_2= Asterisk
                    {
                    lv_quantifier_0_2=(Token)match(input,Asterisk,FOLLOW_19); if (state.failed) return current;
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
                    // InternalRegularExpressionParser.g:1888:6: lv_quantifier_0_3= QuestionMark
                    {
                    lv_quantifier_0_3=(Token)match(input,QuestionMark,FOLLOW_19); if (state.failed) return current;
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

            // InternalRegularExpressionParser.g:1901:3: ( (lv_nonGreedy_1_0= QuestionMark ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==QuestionMark) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalRegularExpressionParser.g:1902:4: (lv_nonGreedy_1_0= QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:1902:4: (lv_nonGreedy_1_0= QuestionMark )
                    // InternalRegularExpressionParser.g:1903:5: lv_nonGreedy_1_0= QuestionMark
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
    // InternalRegularExpressionParser.g:1919:1: entryRuleExactQuantifier returns [EObject current=null] : iv_ruleExactQuantifier= ruleExactQuantifier EOF ;
    public final EObject entryRuleExactQuantifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExactQuantifier = null;


        try {
            // InternalRegularExpressionParser.g:1919:56: (iv_ruleExactQuantifier= ruleExactQuantifier EOF )
            // InternalRegularExpressionParser.g:1920:2: iv_ruleExactQuantifier= ruleExactQuantifier EOF
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
    // InternalRegularExpressionParser.g:1926:1: ruleExactQuantifier returns [EObject current=null] : ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? ) ;
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
            // InternalRegularExpressionParser.g:1932:2: ( ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? ) )
            // InternalRegularExpressionParser.g:1933:2: ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? )
            {
            // InternalRegularExpressionParser.g:1933:2: ( () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )? )
            // InternalRegularExpressionParser.g:1934:3: () otherlv_1= LeftCurlyBracket ( (lv_min_2_0= ruleINT ) ) ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )? otherlv_6= RightCurlyBracket ( (lv_nonGreedy_7_0= QuestionMark ) )?
            {
            // InternalRegularExpressionParser.g:1934:3: ()
            // InternalRegularExpressionParser.g:1935:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,LeftCurlyBracket,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getExactQuantifierAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalRegularExpressionParser.g:1945:3: ( (lv_min_2_0= ruleINT ) )
            // InternalRegularExpressionParser.g:1946:4: (lv_min_2_0= ruleINT )
            {
            // InternalRegularExpressionParser.g:1946:4: (lv_min_2_0= ruleINT )
            // InternalRegularExpressionParser.g:1947:5: lv_min_2_0= ruleINT
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getExactQuantifierAccess().getMinINTParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_21);
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

            // InternalRegularExpressionParser.g:1964:3: ( (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) ) | ( (lv_unboundedMax_5_0= Comma ) ) )?
            int alt27=3;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==Comma) ) {
                int LA27_1 = input.LA(2);

                if ( (LA27_1==RightCurlyBracket) ) {
                    alt27=2;
                }
                else if ( (LA27_1==RULE_UNICODE_DIGIT) ) {
                    alt27=1;
                }
            }
            switch (alt27) {
                case 1 :
                    // InternalRegularExpressionParser.g:1965:4: (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) )
                    {
                    // InternalRegularExpressionParser.g:1965:4: (otherlv_3= Comma ( (lv_max_4_0= ruleINT ) ) )
                    // InternalRegularExpressionParser.g:1966:5: otherlv_3= Comma ( (lv_max_4_0= ruleINT ) )
                    {
                    otherlv_3=(Token)match(input,Comma,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getExactQuantifierAccess().getCommaKeyword_3_0_0());
                      				
                    }
                    // InternalRegularExpressionParser.g:1970:5: ( (lv_max_4_0= ruleINT ) )
                    // InternalRegularExpressionParser.g:1971:6: (lv_max_4_0= ruleINT )
                    {
                    // InternalRegularExpressionParser.g:1971:6: (lv_max_4_0= ruleINT )
                    // InternalRegularExpressionParser.g:1972:7: lv_max_4_0= ruleINT
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getExactQuantifierAccess().getMaxINTParserRuleCall_3_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_22);
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
                    // InternalRegularExpressionParser.g:1991:4: ( (lv_unboundedMax_5_0= Comma ) )
                    {
                    // InternalRegularExpressionParser.g:1991:4: ( (lv_unboundedMax_5_0= Comma ) )
                    // InternalRegularExpressionParser.g:1992:5: (lv_unboundedMax_5_0= Comma )
                    {
                    // InternalRegularExpressionParser.g:1992:5: (lv_unboundedMax_5_0= Comma )
                    // InternalRegularExpressionParser.g:1993:6: lv_unboundedMax_5_0= Comma
                    {
                    lv_unboundedMax_5_0=(Token)match(input,Comma,FOLLOW_22); if (state.failed) return current;
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

            otherlv_6=(Token)match(input,RightCurlyBracket,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getExactQuantifierAccess().getRightCurlyBracketKeyword_4());
              		
            }
            // InternalRegularExpressionParser.g:2010:3: ( (lv_nonGreedy_7_0= QuestionMark ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==QuestionMark) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalRegularExpressionParser.g:2011:4: (lv_nonGreedy_7_0= QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:2011:4: (lv_nonGreedy_7_0= QuestionMark )
                    // InternalRegularExpressionParser.g:2012:5: lv_nonGreedy_7_0= QuestionMark
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
    // InternalRegularExpressionParser.g:2028:1: entryRuleRegularExpressionFlags returns [EObject current=null] : iv_ruleRegularExpressionFlags= ruleRegularExpressionFlags EOF ;
    public final EObject entryRuleRegularExpressionFlags() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegularExpressionFlags = null;


        try {
            // InternalRegularExpressionParser.g:2028:63: (iv_ruleRegularExpressionFlags= ruleRegularExpressionFlags EOF )
            // InternalRegularExpressionParser.g:2029:2: iv_ruleRegularExpressionFlags= ruleRegularExpressionFlags EOF
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
    // InternalRegularExpressionParser.g:2035:1: ruleRegularExpressionFlags returns [EObject current=null] : ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* ) ;
    public final EObject ruleRegularExpressionFlags() throws RecognitionException {
        EObject current = null;

        Token lv_flags_1_1=null;
        Token lv_flags_1_2=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2041:2: ( ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* ) )
            // InternalRegularExpressionParser.g:2042:2: ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* )
            {
            // InternalRegularExpressionParser.g:2042:2: ( () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )* )
            // InternalRegularExpressionParser.g:2043:3: () ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )*
            {
            // InternalRegularExpressionParser.g:2043:3: ()
            // InternalRegularExpressionParser.g:2044:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0(),
              					current);
              			
            }

            }

            // InternalRegularExpressionParser.g:2050:3: ( ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_UNICODE_ESCAPE||LA30_0==RULE_UNICODE_LETTER) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2051:4: ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) )
            	    {
            	    // InternalRegularExpressionParser.g:2051:4: ( (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE ) )
            	    // InternalRegularExpressionParser.g:2052:5: (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE )
            	    {
            	    // InternalRegularExpressionParser.g:2052:5: (lv_flags_1_1= RULE_UNICODE_LETTER | lv_flags_1_2= RULE_UNICODE_ESCAPE )
            	    int alt29=2;
            	    int LA29_0 = input.LA(1);

            	    if ( (LA29_0==RULE_UNICODE_LETTER) ) {
            	        alt29=1;
            	    }
            	    else if ( (LA29_0==RULE_UNICODE_ESCAPE) ) {
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
            	            // InternalRegularExpressionParser.g:2053:6: lv_flags_1_1= RULE_UNICODE_LETTER
            	            {
            	            lv_flags_1_1=(Token)match(input,RULE_UNICODE_LETTER,FOLLOW_23); if (state.failed) return current;
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
            	            // InternalRegularExpressionParser.g:2068:6: lv_flags_1_2= RULE_UNICODE_ESCAPE
            	            {
            	            lv_flags_1_2=(Token)match(input,RULE_UNICODE_ESCAPE,FOLLOW_23); if (state.failed) return current;
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
            	    break loop30;
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
    // InternalRegularExpressionParser.g:2089:1: entryRuleINT returns [String current=null] : iv_ruleINT= ruleINT EOF ;
    public final String entryRuleINT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINT = null;


        try {
            // InternalRegularExpressionParser.g:2089:43: (iv_ruleINT= ruleINT EOF )
            // InternalRegularExpressionParser.g:2090:2: iv_ruleINT= ruleINT EOF
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
    // InternalRegularExpressionParser.g:2096:1: ruleINT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+ ;
    public final AntlrDatatypeRuleToken ruleINT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_UNICODE_DIGIT_0=null;


        	enterRule();

        try {
            // InternalRegularExpressionParser.g:2102:2: ( (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+ )
            // InternalRegularExpressionParser.g:2103:2: (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+
            {
            // InternalRegularExpressionParser.g:2103:2: (this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT )+
            int cnt31=0;
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==RULE_UNICODE_DIGIT) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2104:3: this_UNICODE_DIGIT_0= RULE_UNICODE_DIGIT
            	    {
            	    this_UNICODE_DIGIT_0=(Token)match(input,RULE_UNICODE_DIGIT,FOLLOW_24); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_UNICODE_DIGIT_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_UNICODE_DIGIT_0, grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall());
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt31 >= 1 ) break loop31;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(31, input);
                        throw eee;
                }
                cnt31++;
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
        // InternalRegularExpressionParser.g:1223:4: ( ( ( CircumflexAccent ) ) )
        // InternalRegularExpressionParser.g:1223:5: ( ( CircumflexAccent ) )
        {
        // InternalRegularExpressionParser.g:1223:5: ( ( CircumflexAccent ) )
        // InternalRegularExpressionParser.g:1224:5: ( CircumflexAccent )
        {
        // InternalRegularExpressionParser.g:1224:5: ( CircumflexAccent )
        // InternalRegularExpressionParser.g:1225:6: CircumflexAccent
        {
        match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred2_InternalRegularExpressionParser

    // $ANTLR start synpred3_InternalRegularExpressionParser
    public final void synpred3_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:1295:4: ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )
        // InternalRegularExpressionParser.g:1295:5: ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) )
        {
        // InternalRegularExpressionParser.g:1295:5: ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) )
        // InternalRegularExpressionParser.g:1296:5: () HyphenMinus ( ( ruleCharacterClassAtom ) )
        {
        // InternalRegularExpressionParser.g:1296:5: ()
        // InternalRegularExpressionParser.g:1297:5: 
        {
        }

        match(input,HyphenMinus,FOLLOW_16); if (state.failed) return ;
        // InternalRegularExpressionParser.g:1299:5: ( ( ruleCharacterClassAtom ) )
        // InternalRegularExpressionParser.g:1300:6: ( ruleCharacterClassAtom )
        {
        // InternalRegularExpressionParser.g:1300:6: ( ruleCharacterClassAtom )
        // InternalRegularExpressionParser.g:1301:7: ruleCharacterClassAtom
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
    protected DFA19 dfa19 = new DFA19(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\1\5\3\uffff\1\5\1\uffff\2\5\1\uffff\1\5";
    static final String dfa_3s = "\1\4\3\uffff\1\4\1\uffff\2\4\1\0\1\4";
    static final String dfa_4s = "\1\46\3\uffff\1\46\1\uffff\2\46\1\0\1\46";
    static final String dfa_5s = "\1\uffff\3\1\1\uffff\1\2\4\uffff";
    static final String dfa_6s = "\1\1\7\uffff\1\0\1\uffff}>";
    static final String[] dfa_7s = {
            "\4\5\1\2\1\1\6\5\1\3\3\5\1\4\7\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\5\1\uffff\2\5",
            "",
            "",
            "",
            "\30\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\6\1\uffff\2\5",
            "",
            "\6\5\1\7\13\5\1\10\5\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\6\1\uffff\2\5",
            "\22\5\1\10\5\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\11\1\uffff\2\5",
            "\1\uffff",
            "\22\5\1\10\5\5\1\uffff\2\5\1\uffff\2\5\1\uffff\1\11\1\uffff\2\5"
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
                        int LA9_8 = input.LA(1);

                         
                        int index9_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalRegularExpressionParser()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index9_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_0 = input.LA(1);

                         
                        int index9_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA9_0==PlusSign) && (synpred1_InternalRegularExpressionParser())) {s = 1;}

                        else if ( (LA9_0==Asterisk) && (synpred1_InternalRegularExpressionParser())) {s = 2;}

                        else if ( (LA9_0==QuestionMark) && (synpred1_InternalRegularExpressionParser())) {s = 3;}

                        else if ( (LA9_0==LeftCurlyBracket) ) {s = 4;}

                        else if ( (LA9_0==EOF||(LA9_0>=ExclamationMark && LA9_0<=RightParenthesis)||(LA9_0>=Comma && LA9_0<=EqualsSign)||(LA9_0>=LeftSquareBracket && LA9_0<=CircumflexAccent)||(LA9_0>=VerticalLine && LA9_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA9_0>=RULE_HEX_ESCAPE && LA9_0<=RULE_UNICODE_ESCAPE)||(LA9_0>=RULE_DECIMAL_ESCAPE && LA9_0<=RULE_IDENTITY_ESCAPE)||LA9_0==RULE_UNICODE_DIGIT||(LA9_0>=RULE_UNICODE_LETTER && LA9_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {s = 5;}

                         
                        input.seek(index9_0);
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
    static final String dfa_8s = "\41\uffff";
    static final String dfa_9s = "\1\2\40\uffff";
    static final String dfa_10s = "\2\4\1\uffff\35\0\1\uffff";
    static final String dfa_11s = "\2\46\1\uffff\35\0\1\uffff";
    static final String dfa_12s = "\2\uffff\1\2\35\uffff\1\1";
    static final String dfa_13s = "\3\uffff\1\15\1\13\1\24\1\3\1\11\1\27\1\0\1\34\1\16\1\1\1\21\1\5\1\23\1\10\1\31\1\14\1\33\1\20\1\4\1\25\1\7\1\30\1\12\1\32\1\17\1\2\1\22\1\6\1\26\1\uffff}>";
    static final String[] dfa_14s = {
            "\7\2\1\1\14\2\1\uffff\3\2\1\uffff\2\2\1\uffff\2\2\1\uffff\1\2\1\uffff\2\2",
            "\1\16\1\21\1\26\1\27\1\23\1\24\1\13\1\17\1\22\1\34\1\15\1\14\1\25\1\30\1\2\1\20\1\31\1\33\1\32\1\4\1\uffff\1\12\1\5\1\6\1\uffff\1\7\1\10\1\uffff\1\3\1\11\1\uffff\1\37\1\uffff\1\36\1\35",
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
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "1294:3: ( ( ( () HyphenMinus ( ( ruleCharacterClassAtom ) ) ) )=> ( () otherlv_2= HyphenMinus ( (lv_right_3_0= ruleCharacterClassAtom ) ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_9 = input.LA(1);

                         
                        int index19_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA19_12 = input.LA(1);

                         
                        int index19_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_12);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA19_28 = input.LA(1);

                         
                        int index19_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_28);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA19_6 = input.LA(1);

                         
                        int index19_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA19_21 = input.LA(1);

                         
                        int index19_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_21);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA19_14 = input.LA(1);

                         
                        int index19_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA19_30 = input.LA(1);

                         
                        int index19_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_30);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA19_23 = input.LA(1);

                         
                        int index19_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_23);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA19_16 = input.LA(1);

                         
                        int index19_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_16);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA19_7 = input.LA(1);

                         
                        int index19_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_7);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA19_25 = input.LA(1);

                         
                        int index19_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_25);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA19_4 = input.LA(1);

                         
                        int index19_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_4);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA19_18 = input.LA(1);

                         
                        int index19_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_18);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA19_3 = input.LA(1);

                         
                        int index19_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_3);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA19_11 = input.LA(1);

                         
                        int index19_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_11);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA19_27 = input.LA(1);

                         
                        int index19_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_27);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA19_20 = input.LA(1);

                         
                        int index19_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_20);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA19_13 = input.LA(1);

                         
                        int index19_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_13);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA19_29 = input.LA(1);

                         
                        int index19_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_29);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA19_15 = input.LA(1);

                         
                        int index19_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_15);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA19_5 = input.LA(1);

                         
                        int index19_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_5);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA19_22 = input.LA(1);

                         
                        int index19_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_22);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA19_31 = input.LA(1);

                         
                        int index19_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_31);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA19_8 = input.LA(1);

                         
                        int index19_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_8);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA19_24 = input.LA(1);

                         
                        int index19_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_24);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA19_17 = input.LA(1);

                         
                        int index19_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_17);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA19_26 = input.LA(1);

                         
                        int index19_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_26);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA19_19 = input.LA(1);

                         
                        int index19_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_19);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA19_10 = input.LA(1);

                         
                        int index19_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000006B6FFEDC70L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000002040000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000006B6FFEDC72L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000006B6FDEDC72L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000110302L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000006B6FFEDCF0L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000006B6EFFFFF0L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000006B6EFBFFF0L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000006B6FFFDCF0L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000400400L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000002040000002L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000800000002L});

}