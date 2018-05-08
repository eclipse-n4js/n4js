package org.eclipse.n4js.json.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.n4js.json.services.JSONGrammarAccess;



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
public class InternalJSONParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_NUMBER", "RULE_DOUBLE", "RULE_INT", "RULE_DOUBLE_STRING_CHAR", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_EXPONENT_PART", "RULE_SIGNED_INT", "RULE_WHITESPACE_FRAGMENT", "RULE_WS", "RULE_EOL", "RULE_ML_COMMENT_FRAGMENT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_HEX_DIGIT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'{'", "','", "'}'", "':'", "'['", "']'", "'true'", "'false'", "'null'"
    };
    public static final int RULE_ML_COMMENT_FRAGMENT=18;
    public static final int RULE_STRING=4;
    public static final int RULE_EXPONENT_PART=13;
    public static final int RULE_SL_COMMENT=20;
    public static final int RULE_ZWJ=22;
    public static final int RULE_SL_COMMENT_FRAGMENT=26;
    public static final int RULE_WHITESPACE_FRAGMENT=15;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=28;
    public static final int T__37=37;
    public static final int RULE_DOUBLE=6;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=25;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__32=32;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=10;
    public static final int RULE_WS=16;
    public static final int RULE_EOL=17;
    public static final int RULE_BOM=24;
    public static final int RULE_SIGNED_INT=14;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=27;
    public static final int RULE_ANY_OTHER=31;
    public static final int RULE_DOUBLE_STRING_CHAR=8;
    public static final int RULE_NUMBER=5;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=9;
    public static final int RULE_ZWNJ=23;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=30;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=12;
    public static final int RULE_INT=7;
    public static final int RULE_ML_COMMENT=19;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=11;
    public static final int RULE_HEX_DIGIT=21;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=29;
    public static final int T__40=40;

    // delegates
    // delegators


        public InternalJSONParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalJSONParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalJSONParser.tokenNames; }
    public String getGrammarFileName() { return "InternalJSON.g"; }



     	private JSONGrammarAccess grammarAccess;

        public InternalJSONParser(TokenStream input, JSONGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "JSONDocument";
       	}

       	@Override
       	protected JSONGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleJSONDocument"
    // InternalJSON.g:71:1: entryRuleJSONDocument returns [EObject current=null] : iv_ruleJSONDocument= ruleJSONDocument EOF ;
    public final EObject entryRuleJSONDocument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONDocument = null;


        try {
            // InternalJSON.g:71:53: (iv_ruleJSONDocument= ruleJSONDocument EOF )
            // InternalJSON.g:72:2: iv_ruleJSONDocument= ruleJSONDocument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONDocumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONDocument=ruleJSONDocument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONDocument; 
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
    // $ANTLR end "entryRuleJSONDocument"


    // $ANTLR start "ruleJSONDocument"
    // InternalJSON.g:78:1: ruleJSONDocument returns [EObject current=null] : ( () ( (lv_content_1_0= ruleJSONValue ) )? ) ;
    public final EObject ruleJSONDocument() throws RecognitionException {
        EObject current = null;

        EObject lv_content_1_0 = null;



        	enterRule();

        try {
            // InternalJSON.g:84:2: ( ( () ( (lv_content_1_0= ruleJSONValue ) )? ) )
            // InternalJSON.g:85:2: ( () ( (lv_content_1_0= ruleJSONValue ) )? )
            {
            // InternalJSON.g:85:2: ( () ( (lv_content_1_0= ruleJSONValue ) )? )
            // InternalJSON.g:86:3: () ( (lv_content_1_0= ruleJSONValue ) )?
            {
            // InternalJSON.g:86:3: ()
            // InternalJSON.g:87:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getJSONDocumentAccess().getJSONDocumentAction_0(),
              					current);
              			
            }

            }

            // InternalJSON.g:93:3: ( (lv_content_1_0= ruleJSONValue ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>=RULE_STRING && LA1_0<=RULE_NUMBER)||LA1_0==32||LA1_0==36||(LA1_0>=38 && LA1_0<=40)) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalJSON.g:94:4: (lv_content_1_0= ruleJSONValue )
                    {
                    // InternalJSON.g:94:4: (lv_content_1_0= ruleJSONValue )
                    // InternalJSON.g:95:5: lv_content_1_0= ruleJSONValue
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getJSONDocumentAccess().getContentJSONValueParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_content_1_0=ruleJSONValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getJSONDocumentRule());
                      					}
                      					set(
                      						current,
                      						"content",
                      						lv_content_1_0,
                      						"org.eclipse.n4js.json.JSON.JSONValue");
                      					afterParserOrEnumRuleCall();
                      				
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
    // $ANTLR end "ruleJSONDocument"


    // $ANTLR start "entryRuleJSONObject"
    // InternalJSON.g:116:1: entryRuleJSONObject returns [EObject current=null] : iv_ruleJSONObject= ruleJSONObject EOF ;
    public final EObject entryRuleJSONObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONObject = null;


        try {
            // InternalJSON.g:116:51: (iv_ruleJSONObject= ruleJSONObject EOF )
            // InternalJSON.g:117:2: iv_ruleJSONObject= ruleJSONObject EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONObjectRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONObject=ruleJSONObject();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONObject; 
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
    // $ANTLR end "entryRuleJSONObject"


    // $ANTLR start "ruleJSONObject"
    // InternalJSON.g:123:1: ruleJSONObject returns [EObject current=null] : ( (otherlv_0= '{' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) ) (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )* otherlv_4= '}' ) | ( () otherlv_6= '{' otherlv_7= '}' ) ) ;
    public final EObject ruleJSONObject() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_nameValuePairs_1_0 = null;

        EObject lv_nameValuePairs_3_0 = null;



        	enterRule();

        try {
            // InternalJSON.g:129:2: ( ( (otherlv_0= '{' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) ) (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )* otherlv_4= '}' ) | ( () otherlv_6= '{' otherlv_7= '}' ) ) )
            // InternalJSON.g:130:2: ( (otherlv_0= '{' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) ) (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )* otherlv_4= '}' ) | ( () otherlv_6= '{' otherlv_7= '}' ) )
            {
            // InternalJSON.g:130:2: ( (otherlv_0= '{' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) ) (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )* otherlv_4= '}' ) | ( () otherlv_6= '{' otherlv_7= '}' ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==32) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==34) ) {
                    alt3=2;
                }
                else if ( (LA3_1==RULE_STRING) ) {
                    alt3=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalJSON.g:131:3: (otherlv_0= '{' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) ) (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )* otherlv_4= '}' )
                    {
                    // InternalJSON.g:131:3: (otherlv_0= '{' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) ) (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )* otherlv_4= '}' )
                    // InternalJSON.g:132:4: otherlv_0= '{' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) ) (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )* otherlv_4= '}'
                    {
                    otherlv_0=(Token)match(input,32,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getJSONObjectAccess().getLeftCurlyBracketKeyword_0_0());
                      			
                    }
                    // InternalJSON.g:136:4: ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair ) )
                    // InternalJSON.g:137:5: ( ( RULE_STRING ) )=> (lv_nameValuePairs_1_0= ruleNameValuePair )
                    {
                    // InternalJSON.g:141:5: (lv_nameValuePairs_1_0= ruleNameValuePair )
                    // InternalJSON.g:142:6: lv_nameValuePairs_1_0= ruleNameValuePair
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getJSONObjectAccess().getNameValuePairsNameValuePairParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_4);
                    lv_nameValuePairs_1_0=ruleNameValuePair();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getJSONObjectRule());
                      						}
                      						add(
                      							current,
                      							"nameValuePairs",
                      							lv_nameValuePairs_1_0,
                      							"org.eclipse.n4js.json.JSON.NameValuePair");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalJSON.g:159:4: (otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) ) )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==33) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // InternalJSON.g:160:5: otherlv_2= ',' ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) )
                    	    {
                    	    otherlv_2=(Token)match(input,33,FOLLOW_3); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_2, grammarAccess.getJSONObjectAccess().getCommaKeyword_0_2_0());
                    	      				
                    	    }
                    	    // InternalJSON.g:164:5: ( ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair ) )
                    	    // InternalJSON.g:165:6: ( ( RULE_STRING ) )=> (lv_nameValuePairs_3_0= ruleNameValuePair )
                    	    {
                    	    // InternalJSON.g:169:6: (lv_nameValuePairs_3_0= ruleNameValuePair )
                    	    // InternalJSON.g:170:7: lv_nameValuePairs_3_0= ruleNameValuePair
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getJSONObjectAccess().getNameValuePairsNameValuePairParserRuleCall_0_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_4);
                    	    lv_nameValuePairs_3_0=ruleNameValuePair();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getJSONObjectRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"nameValuePairs",
                    	      								lv_nameValuePairs_3_0,
                    	      								"org.eclipse.n4js.json.JSON.NameValuePair");
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

                    otherlv_4=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getJSONObjectAccess().getRightCurlyBracketKeyword_0_3());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalJSON.g:194:3: ( () otherlv_6= '{' otherlv_7= '}' )
                    {
                    // InternalJSON.g:194:3: ( () otherlv_6= '{' otherlv_7= '}' )
                    // InternalJSON.g:195:4: () otherlv_6= '{' otherlv_7= '}'
                    {
                    // InternalJSON.g:195:4: ()
                    // InternalJSON.g:196:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getJSONObjectAccess().getJSONObjectAction_1_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_6=(Token)match(input,32,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getJSONObjectAccess().getLeftCurlyBracketKeyword_1_1());
                      			
                    }
                    otherlv_7=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getJSONObjectAccess().getRightCurlyBracketKeyword_1_2());
                      			
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
    // $ANTLR end "ruleJSONObject"


    // $ANTLR start "entryRuleNameValuePair"
    // InternalJSON.g:215:1: entryRuleNameValuePair returns [EObject current=null] : iv_ruleNameValuePair= ruleNameValuePair EOF ;
    public final EObject entryRuleNameValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNameValuePair = null;


        try {
            // InternalJSON.g:215:54: (iv_ruleNameValuePair= ruleNameValuePair EOF )
            // InternalJSON.g:216:2: iv_ruleNameValuePair= ruleNameValuePair EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNameValuePairRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNameValuePair=ruleNameValuePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNameValuePair; 
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
    // $ANTLR end "entryRuleNameValuePair"


    // $ANTLR start "ruleNameValuePair"
    // InternalJSON.g:222:1: ruleNameValuePair returns [EObject current=null] : ( ( ( ( RULE_STRING ) )=> (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleJSONValue ) ) ) ;
    public final EObject ruleNameValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalJSON.g:228:2: ( ( ( ( ( RULE_STRING ) )=> (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleJSONValue ) ) ) )
            // InternalJSON.g:229:2: ( ( ( ( RULE_STRING ) )=> (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleJSONValue ) ) )
            {
            // InternalJSON.g:229:2: ( ( ( ( RULE_STRING ) )=> (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleJSONValue ) ) )
            // InternalJSON.g:230:3: ( ( ( RULE_STRING ) )=> (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleJSONValue ) )
            {
            // InternalJSON.g:230:3: ( ( ( RULE_STRING ) )=> (lv_name_0_0= RULE_STRING ) )
            // InternalJSON.g:231:4: ( ( RULE_STRING ) )=> (lv_name_0_0= RULE_STRING )
            {
            // InternalJSON.g:235:4: (lv_name_0_0= RULE_STRING )
            // InternalJSON.g:236:5: lv_name_0_0= RULE_STRING
            {
            lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getNameValuePairAccess().getNameSTRINGTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getNameValuePairRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.eclipse.n4js.json.JSON.STRING");
              				
            }

            }


            }

            otherlv_1=(Token)match(input,35,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getNameValuePairAccess().getColonKeyword_1());
              		
            }
            // InternalJSON.g:256:3: ( (lv_value_2_0= ruleJSONValue ) )
            // InternalJSON.g:257:4: (lv_value_2_0= ruleJSONValue )
            {
            // InternalJSON.g:257:4: (lv_value_2_0= ruleJSONValue )
            // InternalJSON.g:258:5: lv_value_2_0= ruleJSONValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getNameValuePairAccess().getValueJSONValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleJSONValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getNameValuePairRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"org.eclipse.n4js.json.JSON.JSONValue");
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
    // $ANTLR end "ruleNameValuePair"


    // $ANTLR start "entryRuleJSONArray"
    // InternalJSON.g:279:1: entryRuleJSONArray returns [EObject current=null] : iv_ruleJSONArray= ruleJSONArray EOF ;
    public final EObject entryRuleJSONArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONArray = null;


        try {
            // InternalJSON.g:279:50: (iv_ruleJSONArray= ruleJSONArray EOF )
            // InternalJSON.g:280:2: iv_ruleJSONArray= ruleJSONArray EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONArrayRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONArray=ruleJSONArray();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONArray; 
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
    // $ANTLR end "entryRuleJSONArray"


    // $ANTLR start "ruleJSONArray"
    // InternalJSON.g:286:1: ruleJSONArray returns [EObject current=null] : ( (otherlv_0= '[' ( (lv_elements_1_0= ruleJSONValue ) ) (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )* otherlv_4= ']' ) | ( () otherlv_6= '[' otherlv_7= ']' ) ) ;
    public final EObject ruleJSONArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_elements_1_0 = null;

        EObject lv_elements_3_0 = null;



        	enterRule();

        try {
            // InternalJSON.g:292:2: ( ( (otherlv_0= '[' ( (lv_elements_1_0= ruleJSONValue ) ) (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )* otherlv_4= ']' ) | ( () otherlv_6= '[' otherlv_7= ']' ) ) )
            // InternalJSON.g:293:2: ( (otherlv_0= '[' ( (lv_elements_1_0= ruleJSONValue ) ) (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )* otherlv_4= ']' ) | ( () otherlv_6= '[' otherlv_7= ']' ) )
            {
            // InternalJSON.g:293:2: ( (otherlv_0= '[' ( (lv_elements_1_0= ruleJSONValue ) ) (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )* otherlv_4= ']' ) | ( () otherlv_6= '[' otherlv_7= ']' ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==36) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==37) ) {
                    alt5=2;
                }
                else if ( ((LA5_1>=RULE_STRING && LA5_1<=RULE_NUMBER)||LA5_1==32||LA5_1==36||(LA5_1>=38 && LA5_1<=40)) ) {
                    alt5=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalJSON.g:294:3: (otherlv_0= '[' ( (lv_elements_1_0= ruleJSONValue ) ) (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )* otherlv_4= ']' )
                    {
                    // InternalJSON.g:294:3: (otherlv_0= '[' ( (lv_elements_1_0= ruleJSONValue ) ) (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )* otherlv_4= ']' )
                    // InternalJSON.g:295:4: otherlv_0= '[' ( (lv_elements_1_0= ruleJSONValue ) ) (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )* otherlv_4= ']'
                    {
                    otherlv_0=(Token)match(input,36,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getJSONArrayAccess().getLeftSquareBracketKeyword_0_0());
                      			
                    }
                    // InternalJSON.g:299:4: ( (lv_elements_1_0= ruleJSONValue ) )
                    // InternalJSON.g:300:5: (lv_elements_1_0= ruleJSONValue )
                    {
                    // InternalJSON.g:300:5: (lv_elements_1_0= ruleJSONValue )
                    // InternalJSON.g:301:6: lv_elements_1_0= ruleJSONValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getJSONArrayAccess().getElementsJSONValueParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_8);
                    lv_elements_1_0=ruleJSONValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getJSONArrayRule());
                      						}
                      						add(
                      							current,
                      							"elements",
                      							lv_elements_1_0,
                      							"org.eclipse.n4js.json.JSON.JSONValue");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalJSON.g:318:4: (otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==33) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // InternalJSON.g:319:5: otherlv_2= ',' ( (lv_elements_3_0= ruleJSONValue ) )
                    	    {
                    	    otherlv_2=(Token)match(input,33,FOLLOW_7); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_2, grammarAccess.getJSONArrayAccess().getCommaKeyword_0_2_0());
                    	      				
                    	    }
                    	    // InternalJSON.g:323:5: ( (lv_elements_3_0= ruleJSONValue ) )
                    	    // InternalJSON.g:324:6: (lv_elements_3_0= ruleJSONValue )
                    	    {
                    	    // InternalJSON.g:324:6: (lv_elements_3_0= ruleJSONValue )
                    	    // InternalJSON.g:325:7: lv_elements_3_0= ruleJSONValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getJSONArrayAccess().getElementsJSONValueParserRuleCall_0_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_8);
                    	    lv_elements_3_0=ruleJSONValue();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getJSONArrayRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"elements",
                    	      								lv_elements_3_0,
                    	      								"org.eclipse.n4js.json.JSON.JSONValue");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getJSONArrayAccess().getRightSquareBracketKeyword_0_3());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalJSON.g:349:3: ( () otherlv_6= '[' otherlv_7= ']' )
                    {
                    // InternalJSON.g:349:3: ( () otherlv_6= '[' otherlv_7= ']' )
                    // InternalJSON.g:350:4: () otherlv_6= '[' otherlv_7= ']'
                    {
                    // InternalJSON.g:350:4: ()
                    // InternalJSON.g:351:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getJSONArrayAccess().getJSONArrayAction_1_0(),
                      						current);
                      				
                    }

                    }

                    otherlv_6=(Token)match(input,36,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getJSONArrayAccess().getLeftSquareBracketKeyword_1_1());
                      			
                    }
                    otherlv_7=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getJSONArrayAccess().getRightSquareBracketKeyword_1_2());
                      			
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
    // $ANTLR end "ruleJSONArray"


    // $ANTLR start "entryRuleJSONValue"
    // InternalJSON.g:370:1: entryRuleJSONValue returns [EObject current=null] : iv_ruleJSONValue= ruleJSONValue EOF ;
    public final EObject entryRuleJSONValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONValue = null;


        try {
            // InternalJSON.g:370:50: (iv_ruleJSONValue= ruleJSONValue EOF )
            // InternalJSON.g:371:2: iv_ruleJSONValue= ruleJSONValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONValue=ruleJSONValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONValue; 
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
    // $ANTLR end "entryRuleJSONValue"


    // $ANTLR start "ruleJSONValue"
    // InternalJSON.g:377:1: ruleJSONValue returns [EObject current=null] : (this_JSONObject_0= ruleJSONObject | this_JSONArray_1= ruleJSONArray | this_JSONStringLiteral_2= ruleJSONStringLiteral | this_JSONNumericLiteral_3= ruleJSONNumericLiteral | this_JSONNullLiteral_4= ruleJSONNullLiteral | this_JSONBooleanLiteral_5= ruleJSONBooleanLiteral ) ;
    public final EObject ruleJSONValue() throws RecognitionException {
        EObject current = null;

        EObject this_JSONObject_0 = null;

        EObject this_JSONArray_1 = null;

        EObject this_JSONStringLiteral_2 = null;

        EObject this_JSONNumericLiteral_3 = null;

        EObject this_JSONNullLiteral_4 = null;

        EObject this_JSONBooleanLiteral_5 = null;



        	enterRule();

        try {
            // InternalJSON.g:383:2: ( (this_JSONObject_0= ruleJSONObject | this_JSONArray_1= ruleJSONArray | this_JSONStringLiteral_2= ruleJSONStringLiteral | this_JSONNumericLiteral_3= ruleJSONNumericLiteral | this_JSONNullLiteral_4= ruleJSONNullLiteral | this_JSONBooleanLiteral_5= ruleJSONBooleanLiteral ) )
            // InternalJSON.g:384:2: (this_JSONObject_0= ruleJSONObject | this_JSONArray_1= ruleJSONArray | this_JSONStringLiteral_2= ruleJSONStringLiteral | this_JSONNumericLiteral_3= ruleJSONNumericLiteral | this_JSONNullLiteral_4= ruleJSONNullLiteral | this_JSONBooleanLiteral_5= ruleJSONBooleanLiteral )
            {
            // InternalJSON.g:384:2: (this_JSONObject_0= ruleJSONObject | this_JSONArray_1= ruleJSONArray | this_JSONStringLiteral_2= ruleJSONStringLiteral | this_JSONNumericLiteral_3= ruleJSONNumericLiteral | this_JSONNullLiteral_4= ruleJSONNullLiteral | this_JSONBooleanLiteral_5= ruleJSONBooleanLiteral )
            int alt6=6;
            switch ( input.LA(1) ) {
            case 32:
                {
                alt6=1;
                }
                break;
            case 36:
                {
                alt6=2;
                }
                break;
            case RULE_STRING:
                {
                alt6=3;
                }
                break;
            case RULE_NUMBER:
                {
                alt6=4;
                }
                break;
            case 40:
                {
                alt6=5;
                }
                break;
            case 38:
            case 39:
                {
                alt6=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalJSON.g:385:3: this_JSONObject_0= ruleJSONObject
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJSONValueAccess().getJSONObjectParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JSONObject_0=ruleJSONObject();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JSONObject_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalJSON.g:394:3: this_JSONArray_1= ruleJSONArray
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJSONValueAccess().getJSONArrayParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JSONArray_1=ruleJSONArray();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JSONArray_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalJSON.g:403:3: this_JSONStringLiteral_2= ruleJSONStringLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJSONValueAccess().getJSONStringLiteralParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JSONStringLiteral_2=ruleJSONStringLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JSONStringLiteral_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalJSON.g:412:3: this_JSONNumericLiteral_3= ruleJSONNumericLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJSONValueAccess().getJSONNumericLiteralParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JSONNumericLiteral_3=ruleJSONNumericLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JSONNumericLiteral_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalJSON.g:421:3: this_JSONNullLiteral_4= ruleJSONNullLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJSONValueAccess().getJSONNullLiteralParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JSONNullLiteral_4=ruleJSONNullLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JSONNullLiteral_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalJSON.g:430:3: this_JSONBooleanLiteral_5= ruleJSONBooleanLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJSONValueAccess().getJSONBooleanLiteralParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JSONBooleanLiteral_5=ruleJSONBooleanLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JSONBooleanLiteral_5;
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
    // $ANTLR end "ruleJSONValue"


    // $ANTLR start "entryRuleJSONStringLiteral"
    // InternalJSON.g:442:1: entryRuleJSONStringLiteral returns [EObject current=null] : iv_ruleJSONStringLiteral= ruleJSONStringLiteral EOF ;
    public final EObject entryRuleJSONStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONStringLiteral = null;


        try {
            // InternalJSON.g:442:58: (iv_ruleJSONStringLiteral= ruleJSONStringLiteral EOF )
            // InternalJSON.g:443:2: iv_ruleJSONStringLiteral= ruleJSONStringLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONStringLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONStringLiteral=ruleJSONStringLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONStringLiteral; 
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
    // $ANTLR end "entryRuleJSONStringLiteral"


    // $ANTLR start "ruleJSONStringLiteral"
    // InternalJSON.g:449:1: ruleJSONStringLiteral returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleJSONStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;


        	enterRule();

        try {
            // InternalJSON.g:455:2: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // InternalJSON.g:456:2: ( (lv_value_0_0= RULE_STRING ) )
            {
            // InternalJSON.g:456:2: ( (lv_value_0_0= RULE_STRING ) )
            // InternalJSON.g:457:3: (lv_value_0_0= RULE_STRING )
            {
            // InternalJSON.g:457:3: (lv_value_0_0= RULE_STRING )
            // InternalJSON.g:458:4: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_value_0_0, grammarAccess.getJSONStringLiteralAccess().getValueSTRINGTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getJSONStringLiteralRule());
              				}
              				setWithLastConsumed(
              					current,
              					"value",
              					lv_value_0_0,
              					"org.eclipse.n4js.json.JSON.STRING");
              			
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
    // $ANTLR end "ruleJSONStringLiteral"


    // $ANTLR start "entryRuleJSONNumericLiteral"
    // InternalJSON.g:477:1: entryRuleJSONNumericLiteral returns [EObject current=null] : iv_ruleJSONNumericLiteral= ruleJSONNumericLiteral EOF ;
    public final EObject entryRuleJSONNumericLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONNumericLiteral = null;


        try {
            // InternalJSON.g:477:59: (iv_ruleJSONNumericLiteral= ruleJSONNumericLiteral EOF )
            // InternalJSON.g:478:2: iv_ruleJSONNumericLiteral= ruleJSONNumericLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONNumericLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONNumericLiteral=ruleJSONNumericLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONNumericLiteral; 
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
    // $ANTLR end "entryRuleJSONNumericLiteral"


    // $ANTLR start "ruleJSONNumericLiteral"
    // InternalJSON.g:484:1: ruleJSONNumericLiteral returns [EObject current=null] : ( (lv_value_0_0= RULE_NUMBER ) ) ;
    public final EObject ruleJSONNumericLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;


        	enterRule();

        try {
            // InternalJSON.g:490:2: ( ( (lv_value_0_0= RULE_NUMBER ) ) )
            // InternalJSON.g:491:2: ( (lv_value_0_0= RULE_NUMBER ) )
            {
            // InternalJSON.g:491:2: ( (lv_value_0_0= RULE_NUMBER ) )
            // InternalJSON.g:492:3: (lv_value_0_0= RULE_NUMBER )
            {
            // InternalJSON.g:492:3: (lv_value_0_0= RULE_NUMBER )
            // InternalJSON.g:493:4: lv_value_0_0= RULE_NUMBER
            {
            lv_value_0_0=(Token)match(input,RULE_NUMBER,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_value_0_0, grammarAccess.getJSONNumericLiteralAccess().getValueNUMBERTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getJSONNumericLiteralRule());
              				}
              				setWithLastConsumed(
              					current,
              					"value",
              					lv_value_0_0,
              					"org.eclipse.n4js.json.JSON.NUMBER");
              			
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
    // $ANTLR end "ruleJSONNumericLiteral"


    // $ANTLR start "entryRuleJSONBooleanLiteral"
    // InternalJSON.g:512:1: entryRuleJSONBooleanLiteral returns [EObject current=null] : iv_ruleJSONBooleanLiteral= ruleJSONBooleanLiteral EOF ;
    public final EObject entryRuleJSONBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONBooleanLiteral = null;


        try {
            // InternalJSON.g:512:59: (iv_ruleJSONBooleanLiteral= ruleJSONBooleanLiteral EOF )
            // InternalJSON.g:513:2: iv_ruleJSONBooleanLiteral= ruleJSONBooleanLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONBooleanLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONBooleanLiteral=ruleJSONBooleanLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONBooleanLiteral; 
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
    // $ANTLR end "entryRuleJSONBooleanLiteral"


    // $ANTLR start "ruleJSONBooleanLiteral"
    // InternalJSON.g:519:1: ruleJSONBooleanLiteral returns [EObject current=null] : ( () ( ( (lv_booleanValue_1_0= 'true' ) ) | otherlv_2= 'false' ) ) ;
    public final EObject ruleJSONBooleanLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_booleanValue_1_0=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalJSON.g:525:2: ( ( () ( ( (lv_booleanValue_1_0= 'true' ) ) | otherlv_2= 'false' ) ) )
            // InternalJSON.g:526:2: ( () ( ( (lv_booleanValue_1_0= 'true' ) ) | otherlv_2= 'false' ) )
            {
            // InternalJSON.g:526:2: ( () ( ( (lv_booleanValue_1_0= 'true' ) ) | otherlv_2= 'false' ) )
            // InternalJSON.g:527:3: () ( ( (lv_booleanValue_1_0= 'true' ) ) | otherlv_2= 'false' )
            {
            // InternalJSON.g:527:3: ()
            // InternalJSON.g:528:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getJSONBooleanLiteralAccess().getJSONBooleanLiteralAction_0(),
              					current);
              			
            }

            }

            // InternalJSON.g:534:3: ( ( (lv_booleanValue_1_0= 'true' ) ) | otherlv_2= 'false' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==38) ) {
                alt7=1;
            }
            else if ( (LA7_0==39) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalJSON.g:535:4: ( (lv_booleanValue_1_0= 'true' ) )
                    {
                    // InternalJSON.g:535:4: ( (lv_booleanValue_1_0= 'true' ) )
                    // InternalJSON.g:536:5: (lv_booleanValue_1_0= 'true' )
                    {
                    // InternalJSON.g:536:5: (lv_booleanValue_1_0= 'true' )
                    // InternalJSON.g:537:6: lv_booleanValue_1_0= 'true'
                    {
                    lv_booleanValue_1_0=(Token)match(input,38,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_booleanValue_1_0, grammarAccess.getJSONBooleanLiteralAccess().getBooleanValueTrueKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getJSONBooleanLiteralRule());
                      						}
                      						setWithLastConsumed(current, "booleanValue", true, "true");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalJSON.g:550:4: otherlv_2= 'false'
                    {
                    otherlv_2=(Token)match(input,39,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getJSONBooleanLiteralAccess().getFalseKeyword_1_1());
                      			
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
    // $ANTLR end "ruleJSONBooleanLiteral"


    // $ANTLR start "entryRuleJSONNullLiteral"
    // InternalJSON.g:559:1: entryRuleJSONNullLiteral returns [EObject current=null] : iv_ruleJSONNullLiteral= ruleJSONNullLiteral EOF ;
    public final EObject entryRuleJSONNullLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJSONNullLiteral = null;


        try {
            // InternalJSON.g:559:56: (iv_ruleJSONNullLiteral= ruleJSONNullLiteral EOF )
            // InternalJSON.g:560:2: iv_ruleJSONNullLiteral= ruleJSONNullLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJSONNullLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJSONNullLiteral=ruleJSONNullLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJSONNullLiteral; 
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
    // $ANTLR end "entryRuleJSONNullLiteral"


    // $ANTLR start "ruleJSONNullLiteral"
    // InternalJSON.g:566:1: ruleJSONNullLiteral returns [EObject current=null] : ( () otherlv_1= 'null' ) ;
    public final EObject ruleJSONNullLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalJSON.g:572:2: ( ( () otherlv_1= 'null' ) )
            // InternalJSON.g:573:2: ( () otherlv_1= 'null' )
            {
            // InternalJSON.g:573:2: ( () otherlv_1= 'null' )
            // InternalJSON.g:574:3: () otherlv_1= 'null'
            {
            // InternalJSON.g:574:3: ()
            // InternalJSON.g:575:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getJSONNullLiteralAccess().getJSONNullLiteralAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getJSONNullLiteralAccess().getNullKeyword_1());
              		
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
    // $ANTLR end "ruleJSONNullLiteral"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000600000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000001D100000030L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000002200000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000002000000000L});

}