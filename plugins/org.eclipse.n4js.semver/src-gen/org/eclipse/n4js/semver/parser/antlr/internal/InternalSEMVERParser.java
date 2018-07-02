package org.eclipse.n4js.semver.parser.antlr.internal;

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
import org.eclipse.n4js.semver.services.SEMVERGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

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
public class InternalSEMVERParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_WS", "RULE_DIGITS", "RULE_LETTERS", "RULE_LETTER", "RULE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'||'", "'-'", "'.'", "'+'", "'x'", "'X'", "'*'", "'v'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='"
    };
    public static final int RULE_ML_COMMENT_FRAGMENT=21;
    public static final int RULE_DIGITS=5;
    public static final int RULE_ZWJ=15;
    public static final int RULE_SL_COMMENT_FRAGMENT=20;
    public static final int RULE_WHITESPACE_FRAGMENT=9;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=23;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=18;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=10;
    public static final int RULE_WS=4;
    public static final int RULE_EOL=11;
    public static final int RULE_BOM=17;
    public static final int RULE_DIGIT=8;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=22;
    public static final int RULE_ANY_OTHER=26;
    public static final int RULE_LETTERS=6;
    public static final int RULE_LETTER=7;
    public static final int RULE_ZWNJ=16;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=19;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=25;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=12;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int RULE_HEX_DIGIT=13;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=14;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=24;
    public static final int T__40=40;
    public static final int T__41=41;

    // delegates
    // delegators


        public InternalSEMVERParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSEMVERParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSEMVERParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSEMVER.g"; }



     	private SEMVERGrammarAccess grammarAccess;

        public InternalSEMVERParser(TokenStream input, SEMVERGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "VersionRangeSet";
       	}

       	@Override
       	protected SEMVERGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleVersionRangeSet"
    // InternalSEMVER.g:72:1: entryRuleVersionRangeSet returns [EObject current=null] : iv_ruleVersionRangeSet= ruleVersionRangeSet EOF ;
    public final EObject entryRuleVersionRangeSet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeSet = null;


        try {
            // InternalSEMVER.g:72:56: (iv_ruleVersionRangeSet= ruleVersionRangeSet EOF )
            // InternalSEMVER.g:73:2: iv_ruleVersionRangeSet= ruleVersionRangeSet EOF
            {
             newCompositeNode(grammarAccess.getVersionRangeSetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVersionRangeSet=ruleVersionRangeSet();

            state._fsp--;

             current =iv_ruleVersionRangeSet; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionRangeSet"


    // $ANTLR start "ruleVersionRangeSet"
    // InternalSEMVER.g:79:1: ruleVersionRangeSet returns [EObject current=null] : ( () (this_WS_1= RULE_WS )* ( ( (lv_ranges_2_0= ruleVersionRange ) ) ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )* (this_WS_7= RULE_WS )* )? ) ;
    public final EObject ruleVersionRangeSet() throws RecognitionException {
        EObject current = null;

        Token this_WS_1=null;
        Token this_WS_3=null;
        Token otherlv_4=null;
        Token this_WS_5=null;
        Token this_WS_7=null;
        EObject lv_ranges_2_0 = null;

        EObject lv_ranges_6_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:85:2: ( ( () (this_WS_1= RULE_WS )* ( ( (lv_ranges_2_0= ruleVersionRange ) ) ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )* (this_WS_7= RULE_WS )* )? ) )
            // InternalSEMVER.g:86:2: ( () (this_WS_1= RULE_WS )* ( ( (lv_ranges_2_0= ruleVersionRange ) ) ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )* (this_WS_7= RULE_WS )* )? )
            {
            // InternalSEMVER.g:86:2: ( () (this_WS_1= RULE_WS )* ( ( (lv_ranges_2_0= ruleVersionRange ) ) ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )* (this_WS_7= RULE_WS )* )? )
            // InternalSEMVER.g:87:3: () (this_WS_1= RULE_WS )* ( ( (lv_ranges_2_0= ruleVersionRange ) ) ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )* (this_WS_7= RULE_WS )* )?
            {
            // InternalSEMVER.g:87:3: ()
            // InternalSEMVER.g:88:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getVersionRangeSetAccess().getVersionRangeSetAction_0(),
            					current);
            			

            }

            // InternalSEMVER.g:94:3: (this_WS_1= RULE_WS )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_WS) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSEMVER.g:95:4: this_WS_1= RULE_WS
            	    {
            	    this_WS_1=(Token)match(input,RULE_WS,FOLLOW_3); 

            	    				newLeafNode(this_WS_1, grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalSEMVER.g:100:3: ( ( (lv_ranges_2_0= ruleVersionRange ) ) ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )* (this_WS_7= RULE_WS )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_DIGITS||(LA6_0>=31 && LA6_0<=41)) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSEMVER.g:101:4: ( (lv_ranges_2_0= ruleVersionRange ) ) ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )* (this_WS_7= RULE_WS )*
                    {
                    // InternalSEMVER.g:101:4: ( (lv_ranges_2_0= ruleVersionRange ) )
                    // InternalSEMVER.g:102:5: (lv_ranges_2_0= ruleVersionRange )
                    {
                    // InternalSEMVER.g:102:5: (lv_ranges_2_0= ruleVersionRange )
                    // InternalSEMVER.g:103:6: lv_ranges_2_0= ruleVersionRange
                    {

                    						newCompositeNode(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_4);
                    lv_ranges_2_0=ruleVersionRange();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersionRangeSetRule());
                    						}
                    						add(
                    							current,
                    							"ranges",
                    							lv_ranges_2_0,
                    							"org.eclipse.n4js.semver.SEMVER.VersionRange");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalSEMVER.g:120:4: ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )*
                    loop4:
                    do {
                        int alt4=2;
                        alt4 = dfa4.predict(input);
                        switch (alt4) {
                    	case 1 :
                    	    // InternalSEMVER.g:121:5: (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) )
                    	    {
                    	    // InternalSEMVER.g:121:5: (this_WS_3= RULE_WS )*
                    	    loop2:
                    	    do {
                    	        int alt2=2;
                    	        int LA2_0 = input.LA(1);

                    	        if ( (LA2_0==RULE_WS) ) {
                    	            alt2=1;
                    	        }


                    	        switch (alt2) {
                    	    	case 1 :
                    	    	    // InternalSEMVER.g:122:6: this_WS_3= RULE_WS
                    	    	    {
                    	    	    this_WS_3=(Token)match(input,RULE_WS,FOLLOW_5); 

                    	    	    						newLeafNode(this_WS_3, grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_0());
                    	    	    					

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop2;
                    	        }
                    	    } while (true);

                    	    otherlv_4=(Token)match(input,27,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_2_1_1());
                    	    				
                    	    // InternalSEMVER.g:131:5: (this_WS_5= RULE_WS )*
                    	    loop3:
                    	    do {
                    	        int alt3=2;
                    	        int LA3_0 = input.LA(1);

                    	        if ( (LA3_0==RULE_WS) ) {
                    	            alt3=1;
                    	        }


                    	        switch (alt3) {
                    	    	case 1 :
                    	    	    // InternalSEMVER.g:132:6: this_WS_5= RULE_WS
                    	    	    {
                    	    	    this_WS_5=(Token)match(input,RULE_WS,FOLLOW_6); 

                    	    	    						newLeafNode(this_WS_5, grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_2());
                    	    	    					

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop3;
                    	        }
                    	    } while (true);

                    	    // InternalSEMVER.g:137:5: ( (lv_ranges_6_0= ruleVersionRange ) )
                    	    // InternalSEMVER.g:138:6: (lv_ranges_6_0= ruleVersionRange )
                    	    {
                    	    // InternalSEMVER.g:138:6: (lv_ranges_6_0= ruleVersionRange )
                    	    // InternalSEMVER.g:139:7: lv_ranges_6_0= ruleVersionRange
                    	    {

                    	    							newCompositeNode(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_1_3_0());
                    	    						
                    	    pushFollow(FOLLOW_4);
                    	    lv_ranges_6_0=ruleVersionRange();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getVersionRangeSetRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"ranges",
                    	    								lv_ranges_6_0,
                    	    								"org.eclipse.n4js.semver.SEMVER.VersionRange");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    // InternalSEMVER.g:157:4: (this_WS_7= RULE_WS )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==RULE_WS) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalSEMVER.g:158:5: this_WS_7= RULE_WS
                    	    {
                    	    this_WS_7=(Token)match(input,RULE_WS,FOLLOW_7); 

                    	    					newLeafNode(this_WS_7, grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_2());
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionRangeSet"


    // $ANTLR start "entryRuleVersionRange"
    // InternalSEMVER.g:168:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // InternalSEMVER.g:168:53: (iv_ruleVersionRange= ruleVersionRange EOF )
            // InternalSEMVER.g:169:2: iv_ruleVersionRange= ruleVersionRange EOF
            {
             newCompositeNode(grammarAccess.getVersionRangeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVersionRange=ruleVersionRange();

            state._fsp--;

             current =iv_ruleVersionRange; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionRange"


    // $ANTLR start "ruleVersionRange"
    // InternalSEMVER.g:175:1: ruleVersionRange returns [EObject current=null] : (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_VersionRangeContraint_0 = null;

        EObject this_HyphenVersionRange_1 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:181:2: ( (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) )
            // InternalSEMVER.g:182:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            {
            // InternalSEMVER.g:182:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSEMVER.g:183:3: this_VersionRangeContraint_0= ruleVersionRangeContraint
                    {

                    			newCompositeNode(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_VersionRangeContraint_0=ruleVersionRangeContraint();

                    state._fsp--;


                    			current = this_VersionRangeContraint_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:192:3: this_HyphenVersionRange_1= ruleHyphenVersionRange
                    {

                    			newCompositeNode(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_HyphenVersionRange_1=ruleHyphenVersionRange();

                    state._fsp--;


                    			current = this_HyphenVersionRange_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionRange"


    // $ANTLR start "entryRuleHyphenVersionRange"
    // InternalSEMVER.g:204:1: entryRuleHyphenVersionRange returns [EObject current=null] : iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF ;
    public final EObject entryRuleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHyphenVersionRange = null;


        try {
            // InternalSEMVER.g:204:59: (iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF )
            // InternalSEMVER.g:205:2: iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF
            {
             newCompositeNode(grammarAccess.getHyphenVersionRangeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleHyphenVersionRange=ruleHyphenVersionRange();

            state._fsp--;

             current =iv_ruleHyphenVersionRange; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleHyphenVersionRange"


    // $ANTLR start "ruleHyphenVersionRange"
    // InternalSEMVER.g:211:1: ruleHyphenVersionRange returns [EObject current=null] : ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Token otherlv_3=null;
        Token this_WS_4=null;
        EObject lv_from_1_0 = null;

        EObject lv_to_5_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:217:2: ( ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) )
            // InternalSEMVER.g:218:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            {
            // InternalSEMVER.g:218:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            // InternalSEMVER.g:219:3: () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) )
            {
            // InternalSEMVER.g:219:3: ()
            // InternalSEMVER.g:220:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0(),
            					current);
            			

            }

            // InternalSEMVER.g:226:3: ( (lv_from_1_0= ruleVersionNumber ) )
            // InternalSEMVER.g:227:4: (lv_from_1_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:227:4: (lv_from_1_0= ruleVersionNumber )
            // InternalSEMVER.g:228:5: lv_from_1_0= ruleVersionNumber
            {

            					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_8);
            lv_from_1_0=ruleVersionNumber();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getHyphenVersionRangeRule());
            					}
            					set(
            						current,
            						"from",
            						lv_from_1_0,
            						"org.eclipse.n4js.semver.SEMVER.VersionNumber");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSEMVER.g:245:3: (this_WS_2= RULE_WS )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_WS) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSEMVER.g:246:4: this_WS_2= RULE_WS
            	    {
            	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_9); 

            	    				newLeafNode(this_WS_2, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2());
            	    			

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            otherlv_3=(Token)match(input,28,FOLLOW_8); 

            			newLeafNode(otherlv_3, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3());
            		
            // InternalSEMVER.g:255:3: (this_WS_4= RULE_WS )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_WS) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSEMVER.g:256:4: this_WS_4= RULE_WS
            	    {
            	    this_WS_4=(Token)match(input,RULE_WS,FOLLOW_6); 

            	    				newLeafNode(this_WS_4, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4());
            	    			

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);

            // InternalSEMVER.g:261:3: ( (lv_to_5_0= ruleVersionNumber ) )
            // InternalSEMVER.g:262:4: (lv_to_5_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:262:4: (lv_to_5_0= ruleVersionNumber )
            // InternalSEMVER.g:263:5: lv_to_5_0= ruleVersionNumber
            {

            					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_2);
            lv_to_5_0=ruleVersionNumber();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getHyphenVersionRangeRule());
            					}
            					set(
            						current,
            						"to",
            						lv_to_5_0,
            						"org.eclipse.n4js.semver.SEMVER.VersionNumber");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleHyphenVersionRange"


    // $ANTLR start "entryRuleVersionRangeContraint"
    // InternalSEMVER.g:284:1: entryRuleVersionRangeContraint returns [EObject current=null] : iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF ;
    public final EObject entryRuleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeContraint = null;


        try {
            // InternalSEMVER.g:284:62: (iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF )
            // InternalSEMVER.g:285:2: iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF
            {
             newCompositeNode(grammarAccess.getVersionRangeContraintRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVersionRangeContraint=ruleVersionRangeContraint();

            state._fsp--;

             current =iv_ruleVersionRangeContraint; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionRangeContraint"


    // $ANTLR start "ruleVersionRangeContraint"
    // InternalSEMVER.g:291:1: ruleVersionRangeContraint returns [EObject current=null] : ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) ;
    public final EObject ruleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        EObject lv_versionConstraints_1_0 = null;

        EObject lv_versionConstraints_3_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:297:2: ( ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) )
            // InternalSEMVER.g:298:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            {
            // InternalSEMVER.g:298:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            // InternalSEMVER.g:299:3: () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            {
            // InternalSEMVER.g:299:3: ()
            // InternalSEMVER.g:300:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0(),
            					current);
            			

            }

            // InternalSEMVER.g:306:3: ( (lv_versionConstraints_1_0= ruleSimpleVersion ) )
            // InternalSEMVER.g:307:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            {
            // InternalSEMVER.g:307:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            // InternalSEMVER.g:308:5: lv_versionConstraints_1_0= ruleSimpleVersion
            {

            					newCompositeNode(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_7);
            lv_versionConstraints_1_0=ruleSimpleVersion();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVersionRangeContraintRule());
            					}
            					add(
            						current,
            						"versionConstraints",
            						lv_versionConstraints_1_0,
            						"org.eclipse.n4js.semver.SEMVER.SimpleVersion");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSEMVER.g:325:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            loop11:
            do {
                int alt11=2;
                alt11 = dfa11.predict(input);
                switch (alt11) {
            	case 1 :
            	    // InternalSEMVER.g:326:4: (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    {
            	    // InternalSEMVER.g:326:4: (this_WS_2= RULE_WS )+
            	    int cnt10=0;
            	    loop10:
            	    do {
            	        int alt10=2;
            	        int LA10_0 = input.LA(1);

            	        if ( (LA10_0==RULE_WS) ) {
            	            alt10=1;
            	        }


            	        switch (alt10) {
            	    	case 1 :
            	    	    // InternalSEMVER.g:327:5: this_WS_2= RULE_WS
            	    	    {
            	    	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_6); 

            	    	    					newLeafNode(this_WS_2, grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0());
            	    	    				

            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt10 >= 1 ) break loop10;
            	                EarlyExitException eee =
            	                    new EarlyExitException(10, input);
            	                throw eee;
            	        }
            	        cnt10++;
            	    } while (true);

            	    // InternalSEMVER.g:332:4: ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    // InternalSEMVER.g:333:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    {
            	    // InternalSEMVER.g:333:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    // InternalSEMVER.g:334:6: lv_versionConstraints_3_0= ruleSimpleVersion
            	    {

            	    						newCompositeNode(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_7);
            	    lv_versionConstraints_3_0=ruleSimpleVersion();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getVersionRangeContraintRule());
            	    						}
            	    						add(
            	    							current,
            	    							"versionConstraints",
            	    							lv_versionConstraints_3_0,
            	    							"org.eclipse.n4js.semver.SEMVER.SimpleVersion");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionRangeContraint"


    // $ANTLR start "entryRuleSimpleVersion"
    // InternalSEMVER.g:356:1: entryRuleSimpleVersion returns [EObject current=null] : iv_ruleSimpleVersion= ruleSimpleVersion EOF ;
    public final EObject entryRuleSimpleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleVersion = null;


        try {
            // InternalSEMVER.g:356:54: (iv_ruleSimpleVersion= ruleSimpleVersion EOF )
            // InternalSEMVER.g:357:2: iv_ruleSimpleVersion= ruleSimpleVersion EOF
            {
             newCompositeNode(grammarAccess.getSimpleVersionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSimpleVersion=ruleSimpleVersion();

            state._fsp--;

             current =iv_ruleSimpleVersion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSimpleVersion"


    // $ANTLR start "ruleSimpleVersion"
    // InternalSEMVER.g:363:1: ruleSimpleVersion returns [EObject current=null] : ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleSimpleVersion() throws RecognitionException {
        EObject current = null;

        Enumerator lv_comparators_1_0 = null;

        EObject lv_number_2_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:369:2: ( ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) ) )
            // InternalSEMVER.g:370:2: ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) )
            {
            // InternalSEMVER.g:370:2: ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) )
            // InternalSEMVER.g:371:3: () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) )
            {
            // InternalSEMVER.g:371:3: ()
            // InternalSEMVER.g:372:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0(),
            					current);
            			

            }

            // InternalSEMVER.g:378:3: ( (lv_comparators_1_0= ruleVersionComparator ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>=34 && LA12_0<=41)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSEMVER.g:379:4: (lv_comparators_1_0= ruleVersionComparator )
            	    {
            	    // InternalSEMVER.g:379:4: (lv_comparators_1_0= ruleVersionComparator )
            	    // InternalSEMVER.g:380:5: lv_comparators_1_0= ruleVersionComparator
            	    {

            	    					newCompositeNode(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_10);
            	    lv_comparators_1_0=ruleVersionComparator();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"comparators",
            	    						lv_comparators_1_0,
            	    						"org.eclipse.n4js.semver.SEMVER.VersionComparator");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            // InternalSEMVER.g:397:3: ( (lv_number_2_0= ruleVersionNumber ) )
            // InternalSEMVER.g:398:4: (lv_number_2_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:398:4: (lv_number_2_0= ruleVersionNumber )
            // InternalSEMVER.g:399:5: lv_number_2_0= ruleVersionNumber
            {

            					newCompositeNode(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_number_2_0=ruleVersionNumber();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
            					}
            					set(
            						current,
            						"number",
            						lv_number_2_0,
            						"org.eclipse.n4js.semver.SEMVER.VersionNumber");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSimpleVersion"


    // $ANTLR start "entryRuleVersionNumber"
    // InternalSEMVER.g:420:1: entryRuleVersionNumber returns [EObject current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final EObject entryRuleVersionNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionNumber = null;


        try {
            // InternalSEMVER.g:420:54: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalSEMVER.g:421:2: iv_ruleVersionNumber= ruleVersionNumber EOF
            {
             newCompositeNode(grammarAccess.getVersionNumberRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVersionNumber=ruleVersionNumber();

            state._fsp--;

             current =iv_ruleVersionNumber; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionNumber"


    // $ANTLR start "ruleVersionNumber"
    // InternalSEMVER.g:427:1: ruleVersionNumber returns [EObject current=null] : ( ( (lv_major_0_0= ruleVERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) ;
    public final EObject ruleVersionNumber() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_major_0_0 = null;

        AntlrDatatypeRuleToken lv_minor_2_0 = null;

        AntlrDatatypeRuleToken lv_patch_4_0 = null;

        AntlrDatatypeRuleToken lv_extended_6_0 = null;

        EObject lv_qualifier_7_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:433:2: ( ( ( (lv_major_0_0= ruleVERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) )
            // InternalSEMVER.g:434:2: ( ( (lv_major_0_0= ruleVERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            {
            // InternalSEMVER.g:434:2: ( ( (lv_major_0_0= ruleVERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            // InternalSEMVER.g:435:3: ( (lv_major_0_0= ruleVERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )?
            {
            // InternalSEMVER.g:435:3: ( (lv_major_0_0= ruleVERSION_PART ) )
            // InternalSEMVER.g:436:4: (lv_major_0_0= ruleVERSION_PART )
            {
            // InternalSEMVER.g:436:4: (lv_major_0_0= ruleVERSION_PART )
            // InternalSEMVER.g:437:5: lv_major_0_0= ruleVERSION_PART
            {

            					newCompositeNode(grammarAccess.getVersionNumberAccess().getMajorVERSION_PARTParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_11);
            lv_major_0_0=ruleVERSION_PART();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVersionNumberRule());
            					}
            					set(
            						current,
            						"major",
            						lv_major_0_0,
            						"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSEMVER.g:454:3: (otherlv_1= '.' ( (lv_minor_2_0= ruleVERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )? )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==29) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalSEMVER.g:455:4: otherlv_1= '.' ( (lv_minor_2_0= ruleVERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )?
                    {
                    otherlv_1=(Token)match(input,29,FOLLOW_10); 

                    				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                    			
                    // InternalSEMVER.g:459:4: ( (lv_minor_2_0= ruleVERSION_PART ) )
                    // InternalSEMVER.g:460:5: (lv_minor_2_0= ruleVERSION_PART )
                    {
                    // InternalSEMVER.g:460:5: (lv_minor_2_0= ruleVERSION_PART )
                    // InternalSEMVER.g:461:6: lv_minor_2_0= ruleVERSION_PART
                    {

                    						newCompositeNode(grammarAccess.getVersionNumberAccess().getMinorVERSION_PARTParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_11);
                    lv_minor_2_0=ruleVERSION_PART();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                    						}
                    						set(
                    							current,
                    							"minor",
                    							lv_minor_2_0,
                    							"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalSEMVER.g:478:4: (otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )* )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==29) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // InternalSEMVER.g:479:5: otherlv_3= '.' ( (lv_patch_4_0= ruleVERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )*
                            {
                            otherlv_3=(Token)match(input,29,FOLLOW_10); 

                            					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                            				
                            // InternalSEMVER.g:483:5: ( (lv_patch_4_0= ruleVERSION_PART ) )
                            // InternalSEMVER.g:484:6: (lv_patch_4_0= ruleVERSION_PART )
                            {
                            // InternalSEMVER.g:484:6: (lv_patch_4_0= ruleVERSION_PART )
                            // InternalSEMVER.g:485:7: lv_patch_4_0= ruleVERSION_PART
                            {

                            							newCompositeNode(grammarAccess.getVersionNumberAccess().getPatchVERSION_PARTParserRuleCall_1_2_1_0());
                            						
                            pushFollow(FOLLOW_11);
                            lv_patch_4_0=ruleVERSION_PART();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                            							}
                            							set(
                            								current,
                            								"patch",
                            								lv_patch_4_0,
                            								"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalSEMVER.g:502:5: (otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) ) )*
                            loop13:
                            do {
                                int alt13=2;
                                int LA13_0 = input.LA(1);

                                if ( (LA13_0==29) ) {
                                    alt13=1;
                                }


                                switch (alt13) {
                            	case 1 :
                            	    // InternalSEMVER.g:503:6: otherlv_5= '.' ( (lv_extended_6_0= ruleVERSION_PART ) )
                            	    {
                            	    otherlv_5=(Token)match(input,29,FOLLOW_10); 

                            	    						newLeafNode(otherlv_5, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0());
                            	    					
                            	    // InternalSEMVER.g:507:6: ( (lv_extended_6_0= ruleVERSION_PART ) )
                            	    // InternalSEMVER.g:508:7: (lv_extended_6_0= ruleVERSION_PART )
                            	    {
                            	    // InternalSEMVER.g:508:7: (lv_extended_6_0= ruleVERSION_PART )
                            	    // InternalSEMVER.g:509:8: lv_extended_6_0= ruleVERSION_PART
                            	    {

                            	    								newCompositeNode(grammarAccess.getVersionNumberAccess().getExtendedVERSION_PARTParserRuleCall_1_2_2_1_0());
                            	    							
                            	    pushFollow(FOLLOW_11);
                            	    lv_extended_6_0=ruleVERSION_PART();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"extended",
                            	    									lv_extended_6_0,
                            	    									"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop13;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalSEMVER.g:529:3: ( (lv_qualifier_7_0= ruleQualifier ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==28||LA16_0==30) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSEMVER.g:530:4: (lv_qualifier_7_0= ruleQualifier )
                    {
                    // InternalSEMVER.g:530:4: (lv_qualifier_7_0= ruleQualifier )
                    // InternalSEMVER.g:531:5: lv_qualifier_7_0= ruleQualifier
                    {

                    					newCompositeNode(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_qualifier_7_0=ruleQualifier();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                    					}
                    					set(
                    						current,
                    						"qualifier",
                    						lv_qualifier_7_0,
                    						"org.eclipse.n4js.semver.SEMVER.Qualifier");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionNumber"


    // $ANTLR start "entryRuleQualifier"
    // InternalSEMVER.g:552:1: entryRuleQualifier returns [EObject current=null] : iv_ruleQualifier= ruleQualifier EOF ;
    public final EObject entryRuleQualifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifier = null;


        try {
            // InternalSEMVER.g:552:50: (iv_ruleQualifier= ruleQualifier EOF )
            // InternalSEMVER.g:553:2: iv_ruleQualifier= ruleQualifier EOF
            {
             newCompositeNode(grammarAccess.getQualifierRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifier=ruleQualifier();

            state._fsp--;

             current =iv_ruleQualifier; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifier"


    // $ANTLR start "ruleQualifier"
    // InternalSEMVER.g:559:1: ruleQualifier returns [EObject current=null] : ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) ) ) ) ;
    public final EObject ruleQualifier() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_preRelease_1_0 = null;

        AntlrDatatypeRuleToken lv_buildMetadata_3_0 = null;

        AntlrDatatypeRuleToken lv_preRelease_5_0 = null;

        AntlrDatatypeRuleToken lv_buildMetadata_7_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:565:2: ( ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) ) ) ) )
            // InternalSEMVER.g:566:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) ) ) )
            {
            // InternalSEMVER.g:566:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) ) ) )
            int alt17=3;
            alt17 = dfa17.predict(input);
            switch (alt17) {
                case 1 :
                    // InternalSEMVER.g:567:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSEMVER.g:567:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    // InternalSEMVER.g:568:4: otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_0=(Token)match(input,28,FOLLOW_12); 

                    				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
                    			
                    // InternalSEMVER.g:572:4: ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:573:5: (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:573:5: (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:574:6: lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS
                    {

                    						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_preRelease_1_0=ruleALPHA_NUMERIC_CHARS();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQualifierRule());
                    						}
                    						set(
                    							current,
                    							"preRelease",
                    							lv_preRelease_1_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:593:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSEMVER.g:593:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    // InternalSEMVER.g:594:4: otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_2=(Token)match(input,30,FOLLOW_12); 

                    				newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
                    			
                    // InternalSEMVER.g:598:4: ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:599:5: (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:599:5: (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:600:6: lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS
                    {

                    						newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_buildMetadata_3_0=ruleALPHA_NUMERIC_CHARS();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQualifierRule());
                    						}
                    						set(
                    							current,
                    							"buildMetadata",
                    							lv_buildMetadata_3_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:619:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSEMVER.g:619:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    // InternalSEMVER.g:620:4: otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_4=(Token)match(input,28,FOLLOW_12); 

                    				newLeafNode(otherlv_4, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0());
                    			
                    // InternalSEMVER.g:624:4: ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:625:5: (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:625:5: (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:626:6: lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS
                    {

                    						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_13);
                    lv_preRelease_5_0=ruleALPHA_NUMERIC_CHARS();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQualifierRule());
                    						}
                    						set(
                    							current,
                    							"preRelease",
                    							lv_preRelease_5_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,30,FOLLOW_12); 

                    				newLeafNode(otherlv_6, grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2());
                    			
                    // InternalSEMVER.g:647:4: ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:648:5: (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:648:5: (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:649:6: lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS
                    {

                    						newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSParserRuleCall_2_3_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_buildMetadata_7_0=ruleALPHA_NUMERIC_CHARS();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQualifierRule());
                    						}
                    						set(
                    							current,
                    							"buildMetadata",
                    							lv_buildMetadata_7_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifier"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSEMVER.g:671:1: entryRuleALPHA_NUMERIC_CHARS returns [String current=null] : iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF ;
    public final String entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHARS = null;


        try {
            // InternalSEMVER.g:671:59: (iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSEMVER.g:672:2: iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF
            {
             newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleALPHA_NUMERIC_CHARS=ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             current =iv_ruleALPHA_NUMERIC_CHARS.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHARS"


    // $ANTLR start "ruleALPHA_NUMERIC_CHARS"
    // InternalSEMVER.g:678:1: ruleALPHA_NUMERIC_CHARS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+ ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHARS() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:684:2: ( (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+ )
            // InternalSEMVER.g:685:2: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+
            {
            // InternalSEMVER.g:685:2: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=RULE_DIGITS && LA18_0<=RULE_LETTERS)||(LA18_0>=28 && LA18_0<=29)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalSEMVER.g:686:3: this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR
            	    {

            	    			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall());
            	    		
            	    pushFollow(FOLLOW_14);
            	    this_ALPHA_NUMERIC_CHAR_0=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;


            	    			current.merge(this_ALPHA_NUMERIC_CHAR_0);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleALPHA_NUMERIC_CHARS"


    // $ANTLR start "entryRuleVERSION_PART"
    // InternalSEMVER.g:700:1: entryRuleVERSION_PART returns [String current=null] : iv_ruleVERSION_PART= ruleVERSION_PART EOF ;
    public final String entryRuleVERSION_PART() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVERSION_PART = null;


        try {
            // InternalSEMVER.g:700:52: (iv_ruleVERSION_PART= ruleVERSION_PART EOF )
            // InternalSEMVER.g:701:2: iv_ruleVERSION_PART= ruleVERSION_PART EOF
            {
             newCompositeNode(grammarAccess.getVERSION_PARTRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVERSION_PART=ruleVERSION_PART();

            state._fsp--;

             current =iv_ruleVERSION_PART.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVERSION_PART"


    // $ANTLR start "ruleVERSION_PART"
    // InternalSEMVER.g:707:1: ruleVERSION_PART returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'x' | kw= 'X' | kw= '*' | this_DIGITS_3= RULE_DIGITS ) ;
    public final AntlrDatatypeRuleToken ruleVERSION_PART() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_3=null;


        	enterRule();

        try {
            // InternalSEMVER.g:713:2: ( (kw= 'x' | kw= 'X' | kw= '*' | this_DIGITS_3= RULE_DIGITS ) )
            // InternalSEMVER.g:714:2: (kw= 'x' | kw= 'X' | kw= '*' | this_DIGITS_3= RULE_DIGITS )
            {
            // InternalSEMVER.g:714:2: (kw= 'x' | kw= 'X' | kw= '*' | this_DIGITS_3= RULE_DIGITS )
            int alt19=4;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt19=1;
                }
                break;
            case 32:
                {
                alt19=2;
                }
                break;
            case 33:
                {
                alt19=3;
                }
                break;
            case RULE_DIGITS:
                {
                alt19=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalSEMVER.g:715:3: kw= 'x'
                    {
                    kw=(Token)match(input,31,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVERSION_PARTAccess().getXKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:721:3: kw= 'X'
                    {
                    kw=(Token)match(input,32,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVERSION_PARTAccess().getXKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:727:3: kw= '*'
                    {
                    kw=(Token)match(input,33,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVERSION_PARTAccess().getAsteriskKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:733:3: this_DIGITS_3= RULE_DIGITS
                    {
                    this_DIGITS_3=(Token)match(input,RULE_DIGITS,FOLLOW_2); 

                    			current.merge(this_DIGITS_3);
                    		

                    			newLeafNode(this_DIGITS_3, grammarAccess.getVERSION_PARTAccess().getDIGITSTerminalRuleCall_3());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVERSION_PART"


    // $ANTLR start "ruleALPHA_NUMERIC_CHAR"
    // InternalSEMVER.g:745:1: ruleALPHA_NUMERIC_CHAR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? (kw= '.' )? (this_DIGITS_2= RULE_DIGITS | this_LETTERS_3= RULE_LETTERS ) ) ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHAR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_2=null;
        Token this_LETTERS_3=null;


        	enterRule();

        try {
            // InternalSEMVER.g:751:2: ( ( (kw= '-' )? (kw= '.' )? (this_DIGITS_2= RULE_DIGITS | this_LETTERS_3= RULE_LETTERS ) ) )
            // InternalSEMVER.g:752:2: ( (kw= '-' )? (kw= '.' )? (this_DIGITS_2= RULE_DIGITS | this_LETTERS_3= RULE_LETTERS ) )
            {
            // InternalSEMVER.g:752:2: ( (kw= '-' )? (kw= '.' )? (this_DIGITS_2= RULE_DIGITS | this_LETTERS_3= RULE_LETTERS ) )
            // InternalSEMVER.g:753:3: (kw= '-' )? (kw= '.' )? (this_DIGITS_2= RULE_DIGITS | this_LETTERS_3= RULE_LETTERS )
            {
            // InternalSEMVER.g:753:3: (kw= '-' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==28) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSEMVER.g:754:4: kw= '-'
                    {
                    kw=(Token)match(input,28,FOLLOW_15); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            // InternalSEMVER.g:760:3: (kw= '.' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==29) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSEMVER.g:761:4: kw= '.'
                    {
                    kw=(Token)match(input,29,FOLLOW_16); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARAccess().getFullStopKeyword_1());
                    			

                    }
                    break;

            }

            // InternalSEMVER.g:767:3: (this_DIGITS_2= RULE_DIGITS | this_LETTERS_3= RULE_LETTERS )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_DIGITS) ) {
                alt22=1;
            }
            else if ( (LA22_0==RULE_LETTERS) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalSEMVER.g:768:4: this_DIGITS_2= RULE_DIGITS
                    {
                    this_DIGITS_2=(Token)match(input,RULE_DIGITS,FOLLOW_2); 

                    				current.merge(this_DIGITS_2);
                    			

                    				newLeafNode(this_DIGITS_2, grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_2_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:776:4: this_LETTERS_3= RULE_LETTERS
                    {
                    this_LETTERS_3=(Token)match(input,RULE_LETTERS,FOLLOW_2); 

                    				current.merge(this_LETTERS_3);
                    			

                    				newLeafNode(this_LETTERS_3, grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSTerminalRuleCall_2_1());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleALPHA_NUMERIC_CHAR"


    // $ANTLR start "ruleVersionComparator"
    // InternalSEMVER.g:788:1: ruleVersionComparator returns [Enumerator current=null] : ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) ) ;
    public final Enumerator ruleVersionComparator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;


        	enterRule();

        try {
            // InternalSEMVER.g:794:2: ( ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) ) )
            // InternalSEMVER.g:795:2: ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) )
            {
            // InternalSEMVER.g:795:2: ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) )
            int alt23=8;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt23=1;
                }
                break;
            case 35:
                {
                alt23=2;
                }
                break;
            case 36:
                {
                alt23=3;
                }
                break;
            case 37:
                {
                alt23=4;
                }
                break;
            case 38:
                {
                alt23=5;
                }
                break;
            case 39:
                {
                alt23=6;
                }
                break;
            case 40:
                {
                alt23=7;
                }
                break;
            case 41:
                {
                alt23=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // InternalSEMVER.g:796:3: (enumLiteral_0= 'v' )
                    {
                    // InternalSEMVER.g:796:3: (enumLiteral_0= 'v' )
                    // InternalSEMVER.g:797:4: enumLiteral_0= 'v'
                    {
                    enumLiteral_0=(Token)match(input,34,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:804:3: (enumLiteral_1= '=' )
                    {
                    // InternalSEMVER.g:804:3: (enumLiteral_1= '=' )
                    // InternalSEMVER.g:805:4: enumLiteral_1= '='
                    {
                    enumLiteral_1=(Token)match(input,35,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:812:3: (enumLiteral_2= '<' )
                    {
                    // InternalSEMVER.g:812:3: (enumLiteral_2= '<' )
                    // InternalSEMVER.g:813:4: enumLiteral_2= '<'
                    {
                    enumLiteral_2=(Token)match(input,36,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:820:3: (enumLiteral_3= '~' )
                    {
                    // InternalSEMVER.g:820:3: (enumLiteral_3= '~' )
                    // InternalSEMVER.g:821:4: enumLiteral_3= '~'
                    {
                    enumLiteral_3=(Token)match(input,37,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSEMVER.g:828:3: (enumLiteral_4= '^' )
                    {
                    // InternalSEMVER.g:828:3: (enumLiteral_4= '^' )
                    // InternalSEMVER.g:829:4: enumLiteral_4= '^'
                    {
                    enumLiteral_4=(Token)match(input,38,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSEMVER.g:836:3: (enumLiteral_5= '<=' )
                    {
                    // InternalSEMVER.g:836:3: (enumLiteral_5= '<=' )
                    // InternalSEMVER.g:837:4: enumLiteral_5= '<='
                    {
                    enumLiteral_5=(Token)match(input,39,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSEMVER.g:844:3: (enumLiteral_6= '>' )
                    {
                    // InternalSEMVER.g:844:3: (enumLiteral_6= '>' )
                    // InternalSEMVER.g:845:4: enumLiteral_6= '>'
                    {
                    enumLiteral_6=(Token)match(input,40,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSEMVER.g:852:3: (enumLiteral_7= '>=' )
                    {
                    // InternalSEMVER.g:852:3: (enumLiteral_7= '>=' )
                    // InternalSEMVER.g:853:4: enumLiteral_7= '>='
                    {
                    enumLiteral_7=(Token)match(input,41,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionComparator"

    // Delegated rules


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String dfa_1s = "\4\uffff";
    static final String dfa_2s = "\2\2\2\uffff";
    static final String dfa_3s = "\2\4\2\uffff";
    static final String dfa_4s = "\2\33\2\uffff";
    static final String dfa_5s = "\2\uffff\1\2\1\1";
    static final String dfa_6s = "\4\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\26\uffff\1\3",
            "\1\1\26\uffff\1\3",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 120:4: ( (this_WS_3= RULE_WS )* otherlv_4= '||' (this_WS_5= RULE_WS )* ( (lv_ranges_6_0= ruleVersionRange ) ) )*";
        }
    }
    static final String dfa_8s = "\46\uffff";
    static final String dfa_9s = "\2\uffff\4\1\3\uffff\5\1\2\uffff\2\1\2\uffff\2\1\3\uffff\4\1\2\uffff\2\1\1\uffff\4\1";
    static final String dfa_10s = "\1\5\1\uffff\4\4\3\5\5\4\2\5\2\4\2\5\2\4\1\uffff\2\5\4\4\2\5\2\4\1\5\4\4";
    static final String dfa_11s = "\1\51\1\uffff\4\36\1\41\2\35\1\51\4\36\1\35\1\6\2\36\1\35\1\6\2\35\1\uffff\1\41\1\35\4\36\1\35\1\6\2\35\1\41\4\36";
    static final String dfa_12s = "\1\uffff\1\1\24\uffff\1\2\17\uffff";
    static final String dfa_13s = "\46\uffff}>";
    static final String[] dfa_14s = {
            "\1\5\31\uffff\1\2\1\3\1\4\10\1",
            "",
            "\1\11\26\uffff\1\1\1\7\1\6\1\10",
            "\1\11\26\uffff\1\1\1\7\1\6\1\10",
            "\1\11\26\uffff\1\1\1\7\1\6\1\10",
            "\1\11\26\uffff\1\1\1\7\1\6\1\10",
            "\1\15\31\uffff\1\12\1\13\1\14",
            "\1\20\1\21\25\uffff\1\16\1\17",
            "\1\24\1\25\25\uffff\1\22\1\23",
            "\1\11\1\1\25\uffff\1\1\1\26\2\uffff\13\1",
            "\1\11\26\uffff\1\1\1\7\1\27\1\10",
            "\1\11\26\uffff\1\1\1\7\1\27\1\10",
            "\1\11\26\uffff\1\1\1\7\1\27\1\10",
            "\1\11\26\uffff\1\1\1\7\1\27\1\10",
            "\1\20\1\21\26\uffff\1\17",
            "\1\20\1\21",
            "\1\11\1\20\1\21\24\uffff\1\1\1\16\1\17\1\30",
            "\1\11\1\20\1\21\24\uffff\1\1\1\16\1\17\1\30",
            "\1\24\1\25\26\uffff\1\23",
            "\1\24\1\25",
            "\1\11\1\24\1\25\24\uffff\1\1\1\22\1\23",
            "\1\11\1\24\1\25\24\uffff\1\1\1\22\1\23",
            "",
            "\1\34\31\uffff\1\31\1\32\1\33",
            "\1\37\1\40\25\uffff\1\35\1\36",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10",
            "\1\37\1\40\26\uffff\1\36",
            "\1\37\1\40",
            "\1\11\1\37\1\40\24\uffff\1\1\1\35\1\36",
            "\1\11\1\37\1\40\24\uffff\1\1\1\35\1\36",
            "\1\45\31\uffff\1\42\1\43\1\44",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10",
            "\1\11\26\uffff\1\1\1\7\1\41\1\10"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "182:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )";
        }
    }
    static final String dfa_15s = "\1\33\1\51\2\uffff";
    static final String[] dfa_16s = {
            "\1\1\26\uffff\1\2",
            "\1\1\1\3\25\uffff\1\2\3\uffff\13\3",
            "",
            ""
    };
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final short[][] dfa_16 = unpackEncodedStringArray(dfa_16s);

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_15;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_16;
        }
        public String getDescription() {
            return "()* loopback of 325:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*";
        }
    }
    static final String dfa_17s = "\11\uffff";
    static final String dfa_18s = "\5\uffff\2\10\2\uffff";
    static final String dfa_19s = "\1\34\1\5\1\uffff\2\5\2\4\2\uffff";
    static final String dfa_20s = "\1\36\1\35\1\uffff\1\35\1\6\2\36\2\uffff";
    static final String dfa_21s = "\2\uffff\1\2\4\uffff\1\3\1\1";
    static final String dfa_22s = "\11\uffff}>";
    static final String[] dfa_23s = {
            "\1\1\1\uffff\1\2",
            "\1\5\1\6\25\uffff\1\3\1\4",
            "",
            "\1\5\1\6\26\uffff\1\4",
            "\1\5\1\6",
            "\1\10\1\5\1\6\24\uffff\1\10\1\3\1\4\1\7",
            "\1\10\1\5\1\6\24\uffff\1\10\1\3\1\4\1\7",
            "",
            ""
    };

    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final char[] dfa_19 = DFA.unpackEncodedStringToUnsignedChars(dfa_19s);
    static final char[] dfa_20 = DFA.unpackEncodedStringToUnsignedChars(dfa_20s);
    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[][] dfa_23 = unpackEncodedStringArray(dfa_23s);

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = dfa_17;
            this.eof = dfa_18;
            this.min = dfa_19;
            this.max = dfa_20;
            this.accept = dfa_21;
            this.special = dfa_22;
            this.transition = dfa_23;
        }
        public String getDescription() {
            return "566:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleALPHA_NUMERIC_CHARS ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000003FF80000032L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000008000012L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000008000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000003FF80000030L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000010000010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x000003FF80000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000070000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000030000060L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000030000062L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000020000060L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000060L});

}