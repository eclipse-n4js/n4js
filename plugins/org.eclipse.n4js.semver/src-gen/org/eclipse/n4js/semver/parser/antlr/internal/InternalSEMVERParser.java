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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_VERSION_PART", "RULE_ALPHA_NUMERIC_CHARS", "RULE_NUMERIC_ID", "RULE_ALPHA_NUMERIC_CHAR", "RULE_DIGIT", "RULE_NON_DIGIT", "RULE_LETTER", "RULE_POSITIVE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_WS", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'||'", "'-'", "'.'", "'+'", "'v'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='"
    };
    public static final int RULE_ML_COMMENT_FRAGMENT=25;
    public static final int RULE_ZWJ=19;
    public static final int RULE_SL_COMMENT_FRAGMENT=24;
    public static final int RULE_WHITESPACE_FRAGMENT=12;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=27;
    public static final int T__37=37;
    public static final int RULE_ALPHA_NUMERIC_CHARS=5;
    public static final int T__38=38;
    public static final int RULE_NUMERIC_ID=6;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=22;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_VERSION_PART=4;
    public static final int EOF=-1;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_NON_DIGIT=9;
    public static final int RULE_ALPHA_NUMERIC_CHAR=7;
    public static final int RULE_POSITIVE_DIGIT=11;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=14;
    public static final int RULE_WS=13;
    public static final int RULE_EOL=15;
    public static final int RULE_BOM=21;
    public static final int RULE_DIGIT=8;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=26;
    public static final int RULE_ANY_OTHER=30;
    public static final int RULE_LETTER=10;
    public static final int RULE_ZWNJ=20;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=23;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=29;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=16;
    public static final int RULE_HEX_DIGIT=17;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=18;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=28;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;

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
    // InternalSEMVER.g:79:1: ruleVersionRangeSet returns [EObject current=null] : ( () ( (lv_ranges_1_0= ruleVersionRange ) )? (otherlv_2= '||' ( (lv_ranges_3_0= ruleVersionRange ) ) )* ) ;
    public final EObject ruleVersionRangeSet() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_ranges_1_0 = null;

        EObject lv_ranges_3_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:85:2: ( ( () ( (lv_ranges_1_0= ruleVersionRange ) )? (otherlv_2= '||' ( (lv_ranges_3_0= ruleVersionRange ) ) )* ) )
            // InternalSEMVER.g:86:2: ( () ( (lv_ranges_1_0= ruleVersionRange ) )? (otherlv_2= '||' ( (lv_ranges_3_0= ruleVersionRange ) ) )* )
            {
            // InternalSEMVER.g:86:2: ( () ( (lv_ranges_1_0= ruleVersionRange ) )? (otherlv_2= '||' ( (lv_ranges_3_0= ruleVersionRange ) ) )* )
            // InternalSEMVER.g:87:3: () ( (lv_ranges_1_0= ruleVersionRange ) )? (otherlv_2= '||' ( (lv_ranges_3_0= ruleVersionRange ) ) )*
            {
            // InternalSEMVER.g:87:3: ()
            // InternalSEMVER.g:88:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getVersionRangeSetAccess().getVersionRangeSetAction_0(),
            					current);
            			

            }

            // InternalSEMVER.g:94:3: ( (lv_ranges_1_0= ruleVersionRange ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_VERSION_PART||(LA1_0>=35 && LA1_0<=42)) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalSEMVER.g:95:4: (lv_ranges_1_0= ruleVersionRange )
                    {
                    // InternalSEMVER.g:95:4: (lv_ranges_1_0= ruleVersionRange )
                    // InternalSEMVER.g:96:5: lv_ranges_1_0= ruleVersionRange
                    {

                    					newCompositeNode(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_3);
                    lv_ranges_1_0=ruleVersionRange();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getVersionRangeSetRule());
                    					}
                    					add(
                    						current,
                    						"ranges",
                    						lv_ranges_1_0,
                    						"org.eclipse.n4js.semver.SEMVER.VersionRange");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalSEMVER.g:113:3: (otherlv_2= '||' ( (lv_ranges_3_0= ruleVersionRange ) ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==31) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSEMVER.g:114:4: otherlv_2= '||' ( (lv_ranges_3_0= ruleVersionRange ) )
            	    {
            	    otherlv_2=(Token)match(input,31,FOLLOW_4); 

            	    				newLeafNode(otherlv_2, grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_2_0());
            	    			
            	    // InternalSEMVER.g:118:4: ( (lv_ranges_3_0= ruleVersionRange ) )
            	    // InternalSEMVER.g:119:5: (lv_ranges_3_0= ruleVersionRange )
            	    {
            	    // InternalSEMVER.g:119:5: (lv_ranges_3_0= ruleVersionRange )
            	    // InternalSEMVER.g:120:6: lv_ranges_3_0= ruleVersionRange
            	    {

            	    						newCompositeNode(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_3);
            	    lv_ranges_3_0=ruleVersionRange();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getVersionRangeSetRule());
            	    						}
            	    						add(
            	    							current,
            	    							"ranges",
            	    							lv_ranges_3_0,
            	    							"org.eclipse.n4js.semver.SEMVER.VersionRange");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
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
    // $ANTLR end "ruleVersionRangeSet"


    // $ANTLR start "entryRuleVersionRange"
    // InternalSEMVER.g:142:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // InternalSEMVER.g:142:53: (iv_ruleVersionRange= ruleVersionRange EOF )
            // InternalSEMVER.g:143:2: iv_ruleVersionRange= ruleVersionRange EOF
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
    // InternalSEMVER.g:149:1: ruleVersionRange returns [EObject current=null] : (this_SimpleVersion_0= ruleSimpleVersion | this_HyphenVersionRange_1= ruleHyphenVersionRange ) ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_SimpleVersion_0 = null;

        EObject this_HyphenVersionRange_1 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:155:2: ( (this_SimpleVersion_0= ruleSimpleVersion | this_HyphenVersionRange_1= ruleHyphenVersionRange ) )
            // InternalSEMVER.g:156:2: (this_SimpleVersion_0= ruleSimpleVersion | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            {
            // InternalSEMVER.g:156:2: (this_SimpleVersion_0= ruleSimpleVersion | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // InternalSEMVER.g:157:3: this_SimpleVersion_0= ruleSimpleVersion
                    {

                    			newCompositeNode(grammarAccess.getVersionRangeAccess().getSimpleVersionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_SimpleVersion_0=ruleSimpleVersion();

                    state._fsp--;


                    			current = this_SimpleVersion_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:166:3: this_HyphenVersionRange_1= ruleHyphenVersionRange
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
    // InternalSEMVER.g:178:1: entryRuleHyphenVersionRange returns [EObject current=null] : iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF ;
    public final EObject entryRuleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHyphenVersionRange = null;


        try {
            // InternalSEMVER.g:178:59: (iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF )
            // InternalSEMVER.g:179:2: iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF
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
    // InternalSEMVER.g:185:1: ruleHyphenVersionRange returns [EObject current=null] : ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_from_1_0 = null;

        EObject lv_to_3_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:191:2: ( ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) ) )
            // InternalSEMVER.g:192:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) )
            {
            // InternalSEMVER.g:192:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) )
            // InternalSEMVER.g:193:3: () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) )
            {
            // InternalSEMVER.g:193:3: ()
            // InternalSEMVER.g:194:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0(),
            					current);
            			

            }

            // InternalSEMVER.g:200:3: ( (lv_from_1_0= ruleVersionNumber ) )
            // InternalSEMVER.g:201:4: (lv_from_1_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:201:4: (lv_from_1_0= ruleVersionNumber )
            // InternalSEMVER.g:202:5: lv_from_1_0= ruleVersionNumber
            {

            					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_5);
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

            otherlv_2=(Token)match(input,32,FOLLOW_4); 

            			newLeafNode(otherlv_2, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_2());
            		
            // InternalSEMVER.g:223:3: ( (lv_to_3_0= ruleVersionNumber ) )
            // InternalSEMVER.g:224:4: (lv_to_3_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:224:4: (lv_to_3_0= ruleVersionNumber )
            // InternalSEMVER.g:225:5: lv_to_3_0= ruleVersionNumber
            {

            					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_to_3_0=ruleVersionNumber();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getHyphenVersionRangeRule());
            					}
            					set(
            						current,
            						"to",
            						lv_to_3_0,
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


    // $ANTLR start "entryRuleSimpleVersion"
    // InternalSEMVER.g:246:1: entryRuleSimpleVersion returns [EObject current=null] : iv_ruleSimpleVersion= ruleSimpleVersion EOF ;
    public final EObject entryRuleSimpleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleVersion = null;


        try {
            // InternalSEMVER.g:246:54: (iv_ruleSimpleVersion= ruleSimpleVersion EOF )
            // InternalSEMVER.g:247:2: iv_ruleSimpleVersion= ruleSimpleVersion EOF
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
    // InternalSEMVER.g:253:1: ruleSimpleVersion returns [EObject current=null] : ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleSimpleVersion() throws RecognitionException {
        EObject current = null;

        Enumerator lv_comparators_1_0 = null;

        EObject lv_number_2_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:259:2: ( ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) ) )
            // InternalSEMVER.g:260:2: ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) )
            {
            // InternalSEMVER.g:260:2: ( () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) ) )
            // InternalSEMVER.g:261:3: () ( (lv_comparators_1_0= ruleVersionComparator ) )* ( (lv_number_2_0= ruleVersionNumber ) )
            {
            // InternalSEMVER.g:261:3: ()
            // InternalSEMVER.g:262:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0(),
            					current);
            			

            }

            // InternalSEMVER.g:268:3: ( (lv_comparators_1_0= ruleVersionComparator ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=35 && LA4_0<=42)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSEMVER.g:269:4: (lv_comparators_1_0= ruleVersionComparator )
            	    {
            	    // InternalSEMVER.g:269:4: (lv_comparators_1_0= ruleVersionComparator )
            	    // InternalSEMVER.g:270:5: lv_comparators_1_0= ruleVersionComparator
            	    {

            	    					newCompositeNode(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_4);
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
            	    break loop4;
                }
            } while (true);

            // InternalSEMVER.g:287:3: ( (lv_number_2_0= ruleVersionNumber ) )
            // InternalSEMVER.g:288:4: (lv_number_2_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:288:4: (lv_number_2_0= ruleVersionNumber )
            // InternalSEMVER.g:289:5: lv_number_2_0= ruleVersionNumber
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
    // InternalSEMVER.g:310:1: entryRuleVersionNumber returns [EObject current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final EObject entryRuleVersionNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionNumber = null;


        try {
            // InternalSEMVER.g:310:54: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalSEMVER.g:311:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalSEMVER.g:317:1: ruleVersionNumber returns [EObject current=null] : ( ( (lv_major_0_0= RULE_VERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= RULE_VERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) ;
    public final EObject ruleVersionNumber() throws RecognitionException {
        EObject current = null;

        Token lv_major_0_0=null;
        Token otherlv_1=null;
        Token lv_minor_2_0=null;
        Token otherlv_3=null;
        Token lv_patch_4_0=null;
        Token otherlv_5=null;
        Token lv_extended_6_0=null;
        EObject lv_qualifier_7_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:323:2: ( ( ( (lv_major_0_0= RULE_VERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= RULE_VERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) )
            // InternalSEMVER.g:324:2: ( ( (lv_major_0_0= RULE_VERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= RULE_VERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            {
            // InternalSEMVER.g:324:2: ( ( (lv_major_0_0= RULE_VERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= RULE_VERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            // InternalSEMVER.g:325:3: ( (lv_major_0_0= RULE_VERSION_PART ) ) (otherlv_1= '.' ( (lv_minor_2_0= RULE_VERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )?
            {
            // InternalSEMVER.g:325:3: ( (lv_major_0_0= RULE_VERSION_PART ) )
            // InternalSEMVER.g:326:4: (lv_major_0_0= RULE_VERSION_PART )
            {
            // InternalSEMVER.g:326:4: (lv_major_0_0= RULE_VERSION_PART )
            // InternalSEMVER.g:327:5: lv_major_0_0= RULE_VERSION_PART
            {
            lv_major_0_0=(Token)match(input,RULE_VERSION_PART,FOLLOW_6); 

            					newLeafNode(lv_major_0_0, grammarAccess.getVersionNumberAccess().getMajorVERSION_PARTTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVersionNumberRule());
            					}
            					setWithLastConsumed(
            						current,
            						"major",
            						lv_major_0_0,
            						"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
            				

            }


            }

            // InternalSEMVER.g:343:3: (otherlv_1= '.' ( (lv_minor_2_0= RULE_VERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )? )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==33) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSEMVER.g:344:4: otherlv_1= '.' ( (lv_minor_2_0= RULE_VERSION_PART ) ) (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )?
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_7); 

                    				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                    			
                    // InternalSEMVER.g:348:4: ( (lv_minor_2_0= RULE_VERSION_PART ) )
                    // InternalSEMVER.g:349:5: (lv_minor_2_0= RULE_VERSION_PART )
                    {
                    // InternalSEMVER.g:349:5: (lv_minor_2_0= RULE_VERSION_PART )
                    // InternalSEMVER.g:350:6: lv_minor_2_0= RULE_VERSION_PART
                    {
                    lv_minor_2_0=(Token)match(input,RULE_VERSION_PART,FOLLOW_6); 

                    						newLeafNode(lv_minor_2_0, grammarAccess.getVersionNumberAccess().getMinorVERSION_PARTTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getVersionNumberRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"minor",
                    							lv_minor_2_0,
                    							"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
                    					

                    }


                    }

                    // InternalSEMVER.g:366:4: (otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )* )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==33) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // InternalSEMVER.g:367:5: otherlv_3= '.' ( (lv_patch_4_0= RULE_VERSION_PART ) ) (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )*
                            {
                            otherlv_3=(Token)match(input,33,FOLLOW_7); 

                            					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                            				
                            // InternalSEMVER.g:371:5: ( (lv_patch_4_0= RULE_VERSION_PART ) )
                            // InternalSEMVER.g:372:6: (lv_patch_4_0= RULE_VERSION_PART )
                            {
                            // InternalSEMVER.g:372:6: (lv_patch_4_0= RULE_VERSION_PART )
                            // InternalSEMVER.g:373:7: lv_patch_4_0= RULE_VERSION_PART
                            {
                            lv_patch_4_0=(Token)match(input,RULE_VERSION_PART,FOLLOW_6); 

                            							newLeafNode(lv_patch_4_0, grammarAccess.getVersionNumberAccess().getPatchVERSION_PARTTerminalRuleCall_1_2_1_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getVersionNumberRule());
                            							}
                            							setWithLastConsumed(
                            								current,
                            								"patch",
                            								lv_patch_4_0,
                            								"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
                            						

                            }


                            }

                            // InternalSEMVER.g:389:5: (otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) ) )*
                            loop5:
                            do {
                                int alt5=2;
                                int LA5_0 = input.LA(1);

                                if ( (LA5_0==33) ) {
                                    alt5=1;
                                }


                                switch (alt5) {
                            	case 1 :
                            	    // InternalSEMVER.g:390:6: otherlv_5= '.' ( (lv_extended_6_0= RULE_VERSION_PART ) )
                            	    {
                            	    otherlv_5=(Token)match(input,33,FOLLOW_7); 

                            	    						newLeafNode(otherlv_5, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0());
                            	    					
                            	    // InternalSEMVER.g:394:6: ( (lv_extended_6_0= RULE_VERSION_PART ) )
                            	    // InternalSEMVER.g:395:7: (lv_extended_6_0= RULE_VERSION_PART )
                            	    {
                            	    // InternalSEMVER.g:395:7: (lv_extended_6_0= RULE_VERSION_PART )
                            	    // InternalSEMVER.g:396:8: lv_extended_6_0= RULE_VERSION_PART
                            	    {
                            	    lv_extended_6_0=(Token)match(input,RULE_VERSION_PART,FOLLOW_6); 

                            	    								newLeafNode(lv_extended_6_0, grammarAccess.getVersionNumberAccess().getExtendedVERSION_PARTTerminalRuleCall_1_2_2_1_0());
                            	    							

                            	    								if (current==null) {
                            	    									current = createModelElement(grammarAccess.getVersionNumberRule());
                            	    								}
                            	    								addWithLastConsumed(
                            	    									current,
                            	    									"extended",
                            	    									lv_extended_6_0,
                            	    									"org.eclipse.n4js.semver.SEMVER.VERSION_PART");
                            	    							

                            	    }


                            	    }


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
                    break;

            }

            // InternalSEMVER.g:415:3: ( (lv_qualifier_7_0= ruleQualifier ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==32) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==RULE_ALPHA_NUMERIC_CHARS) ) {
                    alt8=1;
                }
            }
            else if ( (LA8_0==34) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSEMVER.g:416:4: (lv_qualifier_7_0= ruleQualifier )
                    {
                    // InternalSEMVER.g:416:4: (lv_qualifier_7_0= ruleQualifier )
                    // InternalSEMVER.g:417:5: lv_qualifier_7_0= ruleQualifier
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
    // InternalSEMVER.g:438:1: entryRuleQualifier returns [EObject current=null] : iv_ruleQualifier= ruleQualifier EOF ;
    public final EObject entryRuleQualifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifier = null;


        try {
            // InternalSEMVER.g:438:50: (iv_ruleQualifier= ruleQualifier EOF )
            // InternalSEMVER.g:439:2: iv_ruleQualifier= ruleQualifier EOF
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
    // InternalSEMVER.g:445:1: ruleQualifier returns [EObject current=null] : ( (otherlv_0= '-' ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) ) ;
    public final EObject ruleQualifier() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_preRelease_1_0=null;
        Token otherlv_2=null;
        Token lv_buildMetadata_3_0=null;
        Token otherlv_4=null;
        Token lv_preRelease_5_0=null;
        Token otherlv_6=null;
        Token lv_buildMetadata_7_0=null;


        	enterRule();

        try {
            // InternalSEMVER.g:451:2: ( ( (otherlv_0= '-' ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) ) )
            // InternalSEMVER.g:452:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) )
            {
            // InternalSEMVER.g:452:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) ) ) )
            int alt9=3;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==32) ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==RULE_ALPHA_NUMERIC_CHARS) ) {
                    int LA9_3 = input.LA(3);

                    if ( (LA9_3==EOF||(LA9_3>=31 && LA9_3<=32)) ) {
                        alt9=1;
                    }
                    else if ( (LA9_3==34) ) {
                        alt9=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA9_0==34) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalSEMVER.g:453:3: (otherlv_0= '-' ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSEMVER.g:453:3: (otherlv_0= '-' ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) ) )
                    // InternalSEMVER.g:454:4: otherlv_0= '-' ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_0=(Token)match(input,32,FOLLOW_8); 

                    				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
                    			
                    // InternalSEMVER.g:458:4: ( (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:459:5: (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:459:5: (lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:460:6: lv_preRelease_1_0= RULE_ALPHA_NUMERIC_CHARS
                    {
                    lv_preRelease_1_0=(Token)match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_2); 

                    						newLeafNode(lv_preRelease_1_0, grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSTerminalRuleCall_0_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getQualifierRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"preRelease",
                    							lv_preRelease_1_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:478:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSEMVER.g:478:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) ) )
                    // InternalSEMVER.g:479:4: otherlv_2= '+' ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_2=(Token)match(input,34,FOLLOW_8); 

                    				newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
                    			
                    // InternalSEMVER.g:483:4: ( (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:484:5: (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:484:5: (lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:485:6: lv_buildMetadata_3_0= RULE_ALPHA_NUMERIC_CHARS
                    {
                    lv_buildMetadata_3_0=(Token)match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_2); 

                    						newLeafNode(lv_buildMetadata_3_0, grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getQualifierRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"buildMetadata",
                    							lv_buildMetadata_3_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    					

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:503:3: (otherlv_4= '-' ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSEMVER.g:503:3: (otherlv_4= '-' ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) ) )
                    // InternalSEMVER.g:504:4: otherlv_4= '-' ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_4=(Token)match(input,32,FOLLOW_8); 

                    				newLeafNode(otherlv_4, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0());
                    			
                    // InternalSEMVER.g:508:4: ( (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:509:5: (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:509:5: (lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:510:6: lv_preRelease_5_0= RULE_ALPHA_NUMERIC_CHARS
                    {
                    lv_preRelease_5_0=(Token)match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_9); 

                    						newLeafNode(lv_preRelease_5_0, grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSTerminalRuleCall_2_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getQualifierRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"preRelease",
                    							lv_preRelease_5_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,34,FOLLOW_8); 

                    				newLeafNode(otherlv_6, grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2());
                    			
                    // InternalSEMVER.g:530:4: ( (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:531:5: (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:531:5: (lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:532:6: lv_buildMetadata_7_0= RULE_ALPHA_NUMERIC_CHARS
                    {
                    lv_buildMetadata_7_0=(Token)match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_2); 

                    						newLeafNode(lv_buildMetadata_7_0, grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSTerminalRuleCall_2_3_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getQualifierRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"buildMetadata",
                    							lv_buildMetadata_7_0,
                    							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
                    					

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


    // $ANTLR start "ruleVersionComparator"
    // InternalSEMVER.g:553:1: ruleVersionComparator returns [Enumerator current=null] : ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) ) ;
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
            // InternalSEMVER.g:559:2: ( ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) ) )
            // InternalSEMVER.g:560:2: ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) )
            {
            // InternalSEMVER.g:560:2: ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) )
            int alt10=8;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt10=1;
                }
                break;
            case 36:
                {
                alt10=2;
                }
                break;
            case 37:
                {
                alt10=3;
                }
                break;
            case 38:
                {
                alt10=4;
                }
                break;
            case 39:
                {
                alt10=5;
                }
                break;
            case 40:
                {
                alt10=6;
                }
                break;
            case 41:
                {
                alt10=7;
                }
                break;
            case 42:
                {
                alt10=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalSEMVER.g:561:3: (enumLiteral_0= 'v' )
                    {
                    // InternalSEMVER.g:561:3: (enumLiteral_0= 'v' )
                    // InternalSEMVER.g:562:4: enumLiteral_0= 'v'
                    {
                    enumLiteral_0=(Token)match(input,35,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:569:3: (enumLiteral_1= '=' )
                    {
                    // InternalSEMVER.g:569:3: (enumLiteral_1= '=' )
                    // InternalSEMVER.g:570:4: enumLiteral_1= '='
                    {
                    enumLiteral_1=(Token)match(input,36,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:577:3: (enumLiteral_2= '<' )
                    {
                    // InternalSEMVER.g:577:3: (enumLiteral_2= '<' )
                    // InternalSEMVER.g:578:4: enumLiteral_2= '<'
                    {
                    enumLiteral_2=(Token)match(input,37,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:585:3: (enumLiteral_3= '~' )
                    {
                    // InternalSEMVER.g:585:3: (enumLiteral_3= '~' )
                    // InternalSEMVER.g:586:4: enumLiteral_3= '~'
                    {
                    enumLiteral_3=(Token)match(input,38,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSEMVER.g:593:3: (enumLiteral_4= '^' )
                    {
                    // InternalSEMVER.g:593:3: (enumLiteral_4= '^' )
                    // InternalSEMVER.g:594:4: enumLiteral_4= '^'
                    {
                    enumLiteral_4=(Token)match(input,39,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSEMVER.g:601:3: (enumLiteral_5= '<=' )
                    {
                    // InternalSEMVER.g:601:3: (enumLiteral_5= '<=' )
                    // InternalSEMVER.g:602:4: enumLiteral_5= '<='
                    {
                    enumLiteral_5=(Token)match(input,40,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSEMVER.g:609:3: (enumLiteral_6= '>' )
                    {
                    // InternalSEMVER.g:609:3: (enumLiteral_6= '>' )
                    // InternalSEMVER.g:610:4: enumLiteral_6= '>'
                    {
                    enumLiteral_6=(Token)match(input,41,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSEMVER.g:617:3: (enumLiteral_7= '>=' )
                    {
                    // InternalSEMVER.g:617:3: (enumLiteral_7= '>=' )
                    // InternalSEMVER.g:618:4: enumLiteral_7= '>='
                    {
                    enumLiteral_7=(Token)match(input,42,FOLLOW_2); 

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


    protected DFA3 dfa3 = new DFA3(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\2\uffff\1\1\3\uffff\2\1\1\uffff\1\1\2\uffff\2\1\1\uffff\1\1";
    static final String dfa_3s = "\1\4\1\uffff\1\37\2\4\1\5\2\37\1\uffff\1\37\1\4\1\5\2\37\1\4\1\37";
    static final String dfa_4s = "\1\52\1\uffff\1\42\1\4\2\5\2\42\1\uffff\1\40\1\4\1\5\1\42\1\40\1\4\1\42";
    static final String dfa_5s = "\1\uffff\1\1\6\uffff\1\2\7\uffff";
    static final String dfa_6s = "\20\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\36\uffff\10\1",
            "",
            "\1\1\1\4\1\3\1\5",
            "\1\6",
            "\1\10\1\7",
            "\1\11",
            "\1\1\1\4\1\12\1\5",
            "\1\1\1\10\1\uffff\1\13",
            "",
            "\1\1\1\10",
            "\1\14",
            "\1\15",
            "\1\1\1\4\1\16\1\5",
            "\1\1\1\10",
            "\1\17",
            "\1\1\1\4\1\16\1\5"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "156:2: (this_SimpleVersion_0= ruleSimpleVersion | this_HyphenVersionRange_1= ruleHyphenVersionRange )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x000007F800000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000700000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000400000000L});

}