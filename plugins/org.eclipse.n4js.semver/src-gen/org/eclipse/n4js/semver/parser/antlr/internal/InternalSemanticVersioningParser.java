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
import org.eclipse.n4js.semver.services.SemanticVersioningGrammarAccess;



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
public class InternalSemanticVersioningParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_PART", "RULE_WHITESPACE_FRAGMENT", "RULE_WS", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'||'", "'-'", "'~'", "'^'", "'.'", "'x'", "'X'", "'*'", "'+'", "'='", "'<'", "'<='", "'>'", "'>='"
    };
    public static final int RULE_ML_COMMENT_FRAGMENT=18;
    public static final int RULE_ZWJ=12;
    public static final int RULE_SL_COMMENT_FRAGMENT=17;
    public static final int RULE_WHITESPACE_FRAGMENT=5;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=20;
    public static final int T__37=37;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=15;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=7;
    public static final int RULE_WS=6;
    public static final int RULE_EOL=8;
    public static final int RULE_BOM=14;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=19;
    public static final int RULE_ANY_OTHER=23;
    public static final int RULE_ZWNJ=13;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=16;
    public static final int RULE_PART=4;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=22;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=9;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int RULE_HEX_DIGIT=10;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=11;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=21;
    public static final int T__24=24;
    public static final int T__25=25;

    // delegates
    // delegators


        public InternalSemanticVersioningParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSemanticVersioningParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSemanticVersioningParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSemanticVersioning.g"; }



     	private SemanticVersioningGrammarAccess grammarAccess;

        public InternalSemanticVersioningParser(TokenStream input, SemanticVersioningGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "VersionRangeSet";
       	}

       	@Override
       	protected SemanticVersioningGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleVersionRangeSet"
    // InternalSemanticVersioning.g:72:1: entryRuleVersionRangeSet returns [EObject current=null] : iv_ruleVersionRangeSet= ruleVersionRangeSet EOF ;
    public final EObject entryRuleVersionRangeSet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeSet = null;


        try {
            // InternalSemanticVersioning.g:72:56: (iv_ruleVersionRangeSet= ruleVersionRangeSet EOF )
            // InternalSemanticVersioning.g:73:2: iv_ruleVersionRangeSet= ruleVersionRangeSet EOF
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
    // InternalSemanticVersioning.g:79:1: ruleVersionRangeSet returns [EObject current=null] : ( ( (lv_ranges_0_0= ruleVersionRange ) )? (otherlv_1= '||' ( (lv_ranges_2_0= ruleVersionRange ) ) )* ) ;
    public final EObject ruleVersionRangeSet() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_ranges_0_0 = null;

        EObject lv_ranges_2_0 = null;



        	enterRule();

        try {
            // InternalSemanticVersioning.g:85:2: ( ( ( (lv_ranges_0_0= ruleVersionRange ) )? (otherlv_1= '||' ( (lv_ranges_2_0= ruleVersionRange ) ) )* ) )
            // InternalSemanticVersioning.g:86:2: ( ( (lv_ranges_0_0= ruleVersionRange ) )? (otherlv_1= '||' ( (lv_ranges_2_0= ruleVersionRange ) ) )* )
            {
            // InternalSemanticVersioning.g:86:2: ( ( (lv_ranges_0_0= ruleVersionRange ) )? (otherlv_1= '||' ( (lv_ranges_2_0= ruleVersionRange ) ) )* )
            // InternalSemanticVersioning.g:87:3: ( (lv_ranges_0_0= ruleVersionRange ) )? (otherlv_1= '||' ( (lv_ranges_2_0= ruleVersionRange ) ) )*
            {
            // InternalSemanticVersioning.g:87:3: ( (lv_ranges_0_0= ruleVersionRange ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==26||(LA1_0>=29 && LA1_0<=31)||(LA1_0>=33 && LA1_0<=37)) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalSemanticVersioning.g:88:4: (lv_ranges_0_0= ruleVersionRange )
                    {
                    // InternalSemanticVersioning.g:88:4: (lv_ranges_0_0= ruleVersionRange )
                    // InternalSemanticVersioning.g:89:5: lv_ranges_0_0= ruleVersionRange
                    {

                    					newCompositeNode(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_3);
                    lv_ranges_0_0=ruleVersionRange();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getVersionRangeSetRule());
                    					}
                    					add(
                    						current,
                    						"ranges",
                    						lv_ranges_0_0,
                    						"org.eclipse.n4js.semver.SemanticVersioning.VersionRange");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalSemanticVersioning.g:106:3: (otherlv_1= '||' ( (lv_ranges_2_0= ruleVersionRange ) ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==24) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSemanticVersioning.g:107:4: otherlv_1= '||' ( (lv_ranges_2_0= ruleVersionRange ) )
            	    {
            	    otherlv_1=(Token)match(input,24,FOLLOW_4); 

            	    				newLeafNode(otherlv_1, grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_1_0());
            	    			
            	    // InternalSemanticVersioning.g:111:4: ( (lv_ranges_2_0= ruleVersionRange ) )
            	    // InternalSemanticVersioning.g:112:5: (lv_ranges_2_0= ruleVersionRange )
            	    {
            	    // InternalSemanticVersioning.g:112:5: (lv_ranges_2_0= ruleVersionRange )
            	    // InternalSemanticVersioning.g:113:6: lv_ranges_2_0= ruleVersionRange
            	    {

            	    						newCompositeNode(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_3);
            	    lv_ranges_2_0=ruleVersionRange();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getVersionRangeSetRule());
            	    						}
            	    						add(
            	    							current,
            	    							"ranges",
            	    							lv_ranges_2_0,
            	    							"org.eclipse.n4js.semver.SemanticVersioning.VersionRange");
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
    // InternalSemanticVersioning.g:135:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // InternalSemanticVersioning.g:135:53: (iv_ruleVersionRange= ruleVersionRange EOF )
            // InternalSemanticVersioning.g:136:2: iv_ruleVersionRange= ruleVersionRange EOF
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
    // InternalSemanticVersioning.g:142:1: ruleVersionRange returns [EObject current=null] : (this_HyphenVersionRange_0= ruleHyphenVersionRange | this_EnumeratedVersionRange_1= ruleEnumeratedVersionRange ) ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_HyphenVersionRange_0 = null;

        EObject this_EnumeratedVersionRange_1 = null;



        	enterRule();

        try {
            // InternalSemanticVersioning.g:148:2: ( (this_HyphenVersionRange_0= ruleHyphenVersionRange | this_EnumeratedVersionRange_1= ruleEnumeratedVersionRange ) )
            // InternalSemanticVersioning.g:149:2: (this_HyphenVersionRange_0= ruleHyphenVersionRange | this_EnumeratedVersionRange_1= ruleEnumeratedVersionRange )
            {
            // InternalSemanticVersioning.g:149:2: (this_HyphenVersionRange_0= ruleHyphenVersionRange | this_EnumeratedVersionRange_1= ruleEnumeratedVersionRange )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=29 && LA3_0<=31)) ) {
                alt3=1;
            }
            else if ( (LA3_0==26||(LA3_0>=33 && LA3_0<=37)) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalSemanticVersioning.g:150:3: this_HyphenVersionRange_0= ruleHyphenVersionRange
                    {

                    			newCompositeNode(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_HyphenVersionRange_0=ruleHyphenVersionRange();

                    state._fsp--;


                    			current = this_HyphenVersionRange_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:159:3: this_EnumeratedVersionRange_1= ruleEnumeratedVersionRange
                    {

                    			newCompositeNode(grammarAccess.getVersionRangeAccess().getEnumeratedVersionRangeParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_EnumeratedVersionRange_1=ruleEnumeratedVersionRange();

                    state._fsp--;


                    			current = this_EnumeratedVersionRange_1;
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
    // InternalSemanticVersioning.g:171:1: entryRuleHyphenVersionRange returns [EObject current=null] : iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF ;
    public final EObject entryRuleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHyphenVersionRange = null;


        try {
            // InternalSemanticVersioning.g:171:59: (iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF )
            // InternalSemanticVersioning.g:172:2: iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF
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
    // InternalSemanticVersioning.g:178:1: ruleHyphenVersionRange returns [EObject current=null] : ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_from_1_0 = null;

        EObject lv_to_3_0 = null;



        	enterRule();

        try {
            // InternalSemanticVersioning.g:184:2: ( ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) ) )
            // InternalSemanticVersioning.g:185:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) )
            {
            // InternalSemanticVersioning.g:185:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) ) )
            // InternalSemanticVersioning.g:186:3: () ( (lv_from_1_0= ruleVersionNumber ) ) otherlv_2= '-' ( (lv_to_3_0= ruleVersionNumber ) )
            {
            // InternalSemanticVersioning.g:186:3: ()
            // InternalSemanticVersioning.g:187:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0(),
            					current);
            			

            }

            // InternalSemanticVersioning.g:193:3: ( (lv_from_1_0= ruleVersionNumber ) )
            // InternalSemanticVersioning.g:194:4: (lv_from_1_0= ruleVersionNumber )
            {
            // InternalSemanticVersioning.g:194:4: (lv_from_1_0= ruleVersionNumber )
            // InternalSemanticVersioning.g:195:5: lv_from_1_0= ruleVersionNumber
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
            						"org.eclipse.n4js.semver.SemanticVersioning.VersionNumber");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,25,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_2());
            		
            // InternalSemanticVersioning.g:216:3: ( (lv_to_3_0= ruleVersionNumber ) )
            // InternalSemanticVersioning.g:217:4: (lv_to_3_0= ruleVersionNumber )
            {
            // InternalSemanticVersioning.g:217:4: (lv_to_3_0= ruleVersionNumber )
            // InternalSemanticVersioning.g:218:5: lv_to_3_0= ruleVersionNumber
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
            						"org.eclipse.n4js.semver.SemanticVersioning.VersionNumber");
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


    // $ANTLR start "entryRuleEnumeratedVersionRange"
    // InternalSemanticVersioning.g:239:1: entryRuleEnumeratedVersionRange returns [EObject current=null] : iv_ruleEnumeratedVersionRange= ruleEnumeratedVersionRange EOF ;
    public final EObject entryRuleEnumeratedVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumeratedVersionRange = null;


        try {
            // InternalSemanticVersioning.g:239:63: (iv_ruleEnumeratedVersionRange= ruleEnumeratedVersionRange EOF )
            // InternalSemanticVersioning.g:240:2: iv_ruleEnumeratedVersionRange= ruleEnumeratedVersionRange EOF
            {
             newCompositeNode(grammarAccess.getEnumeratedVersionRangeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEnumeratedVersionRange=ruleEnumeratedVersionRange();

            state._fsp--;

             current =iv_ruleEnumeratedVersionRange; 
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
    // $ANTLR end "entryRuleEnumeratedVersionRange"


    // $ANTLR start "ruleEnumeratedVersionRange"
    // InternalSemanticVersioning.g:246:1: ruleEnumeratedVersionRange returns [EObject current=null] : ( () ( (lv_simpleVersions_1_0= ruleSimpleVersion ) )+ ) ;
    public final EObject ruleEnumeratedVersionRange() throws RecognitionException {
        EObject current = null;

        EObject lv_simpleVersions_1_0 = null;



        	enterRule();

        try {
            // InternalSemanticVersioning.g:252:2: ( ( () ( (lv_simpleVersions_1_0= ruleSimpleVersion ) )+ ) )
            // InternalSemanticVersioning.g:253:2: ( () ( (lv_simpleVersions_1_0= ruleSimpleVersion ) )+ )
            {
            // InternalSemanticVersioning.g:253:2: ( () ( (lv_simpleVersions_1_0= ruleSimpleVersion ) )+ )
            // InternalSemanticVersioning.g:254:3: () ( (lv_simpleVersions_1_0= ruleSimpleVersion ) )+
            {
            // InternalSemanticVersioning.g:254:3: ()
            // InternalSemanticVersioning.g:255:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getEnumeratedVersionRangeAccess().getEnumeratedVersionRangeAction_0(),
            					current);
            			

            }

            // InternalSemanticVersioning.g:261:3: ( (lv_simpleVersions_1_0= ruleSimpleVersion ) )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==26||(LA4_0>=33 && LA4_0<=37)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSemanticVersioning.g:262:4: (lv_simpleVersions_1_0= ruleSimpleVersion )
            	    {
            	    // InternalSemanticVersioning.g:262:4: (lv_simpleVersions_1_0= ruleSimpleVersion )
            	    // InternalSemanticVersioning.g:263:5: lv_simpleVersions_1_0= ruleSimpleVersion
            	    {

            	    					newCompositeNode(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsSimpleVersionParserRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_7);
            	    lv_simpleVersions_1_0=ruleSimpleVersion();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getEnumeratedVersionRangeRule());
            	    					}
            	    					add(
            	    						current,
            	    						"simpleVersions",
            	    						lv_simpleVersions_1_0,
            	    						"org.eclipse.n4js.semver.SemanticVersioning.SimpleVersion");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
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
    // $ANTLR end "ruleEnumeratedVersionRange"


    // $ANTLR start "entryRuleSimpleVersion"
    // InternalSemanticVersioning.g:284:1: entryRuleSimpleVersion returns [EObject current=null] : iv_ruleSimpleVersion= ruleSimpleVersion EOF ;
    public final EObject entryRuleSimpleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleVersion = null;


        try {
            // InternalSemanticVersioning.g:284:54: (iv_ruleSimpleVersion= ruleSimpleVersion EOF )
            // InternalSemanticVersioning.g:285:2: iv_ruleSimpleVersion= ruleSimpleVersion EOF
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
    // InternalSemanticVersioning.g:291:1: ruleSimpleVersion returns [EObject current=null] : ( ( (lv_comparator_0_0= ruleVersionComparator ) )? ( (lv_hasTilde_1_0= '~' ) ) ( (lv_hasCaret_2_0= '^' ) ) ( (lv_number_3_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleSimpleVersion() throws RecognitionException {
        EObject current = null;

        Token lv_hasTilde_1_0=null;
        Token lv_hasCaret_2_0=null;
        Enumerator lv_comparator_0_0 = null;

        EObject lv_number_3_0 = null;



        	enterRule();

        try {
            // InternalSemanticVersioning.g:297:2: ( ( ( (lv_comparator_0_0= ruleVersionComparator ) )? ( (lv_hasTilde_1_0= '~' ) ) ( (lv_hasCaret_2_0= '^' ) ) ( (lv_number_3_0= ruleVersionNumber ) ) ) )
            // InternalSemanticVersioning.g:298:2: ( ( (lv_comparator_0_0= ruleVersionComparator ) )? ( (lv_hasTilde_1_0= '~' ) ) ( (lv_hasCaret_2_0= '^' ) ) ( (lv_number_3_0= ruleVersionNumber ) ) )
            {
            // InternalSemanticVersioning.g:298:2: ( ( (lv_comparator_0_0= ruleVersionComparator ) )? ( (lv_hasTilde_1_0= '~' ) ) ( (lv_hasCaret_2_0= '^' ) ) ( (lv_number_3_0= ruleVersionNumber ) ) )
            // InternalSemanticVersioning.g:299:3: ( (lv_comparator_0_0= ruleVersionComparator ) )? ( (lv_hasTilde_1_0= '~' ) ) ( (lv_hasCaret_2_0= '^' ) ) ( (lv_number_3_0= ruleVersionNumber ) )
            {
            // InternalSemanticVersioning.g:299:3: ( (lv_comparator_0_0= ruleVersionComparator ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=33 && LA5_0<=37)) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSemanticVersioning.g:300:4: (lv_comparator_0_0= ruleVersionComparator )
                    {
                    // InternalSemanticVersioning.g:300:4: (lv_comparator_0_0= ruleVersionComparator )
                    // InternalSemanticVersioning.g:301:5: lv_comparator_0_0= ruleVersionComparator
                    {

                    					newCompositeNode(grammarAccess.getSimpleVersionAccess().getComparatorVersionComparatorEnumRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_8);
                    lv_comparator_0_0=ruleVersionComparator();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
                    					}
                    					set(
                    						current,
                    						"comparator",
                    						lv_comparator_0_0,
                    						"org.eclipse.n4js.semver.SemanticVersioning.VersionComparator");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalSemanticVersioning.g:318:3: ( (lv_hasTilde_1_0= '~' ) )
            // InternalSemanticVersioning.g:319:4: (lv_hasTilde_1_0= '~' )
            {
            // InternalSemanticVersioning.g:319:4: (lv_hasTilde_1_0= '~' )
            // InternalSemanticVersioning.g:320:5: lv_hasTilde_1_0= '~'
            {
            lv_hasTilde_1_0=(Token)match(input,26,FOLLOW_9); 

            					newLeafNode(lv_hasTilde_1_0, grammarAccess.getSimpleVersionAccess().getHasTildeTildeKeyword_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSimpleVersionRule());
            					}
            					setWithLastConsumed(current, "hasTilde", true, "~");
            				

            }


            }

            // InternalSemanticVersioning.g:332:3: ( (lv_hasCaret_2_0= '^' ) )
            // InternalSemanticVersioning.g:333:4: (lv_hasCaret_2_0= '^' )
            {
            // InternalSemanticVersioning.g:333:4: (lv_hasCaret_2_0= '^' )
            // InternalSemanticVersioning.g:334:5: lv_hasCaret_2_0= '^'
            {
            lv_hasCaret_2_0=(Token)match(input,27,FOLLOW_6); 

            					newLeafNode(lv_hasCaret_2_0, grammarAccess.getSimpleVersionAccess().getHasCaretCircumflexAccentKeyword_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSimpleVersionRule());
            					}
            					setWithLastConsumed(current, "hasCaret", true, "^");
            				

            }


            }

            // InternalSemanticVersioning.g:346:3: ( (lv_number_3_0= ruleVersionNumber ) )
            // InternalSemanticVersioning.g:347:4: (lv_number_3_0= ruleVersionNumber )
            {
            // InternalSemanticVersioning.g:347:4: (lv_number_3_0= ruleVersionNumber )
            // InternalSemanticVersioning.g:348:5: lv_number_3_0= ruleVersionNumber
            {

            					newCompositeNode(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_number_3_0=ruleVersionNumber();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
            					}
            					set(
            						current,
            						"number",
            						lv_number_3_0,
            						"org.eclipse.n4js.semver.SemanticVersioning.VersionNumber");
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
    // InternalSemanticVersioning.g:369:1: entryRuleVersionNumber returns [EObject current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final EObject entryRuleVersionNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionNumber = null;


        try {
            // InternalSemanticVersioning.g:369:54: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalSemanticVersioning.g:370:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalSemanticVersioning.g:376:1: ruleVersionNumber returns [EObject current=null] : ( ( (lv_major_0_0= ruleXr ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleXr ) ) (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )? )? ) ;
    public final EObject ruleVersionNumber() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_major_0_0 = null;

        AntlrDatatypeRuleToken lv_minor_2_0 = null;

        AntlrDatatypeRuleToken lv_path_4_0 = null;

        EObject lv_qualifier_5_0 = null;



        	enterRule();

        try {
            // InternalSemanticVersioning.g:382:2: ( ( ( (lv_major_0_0= ruleXr ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleXr ) ) (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )? )? ) )
            // InternalSemanticVersioning.g:383:2: ( ( (lv_major_0_0= ruleXr ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleXr ) ) (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )? )? )
            {
            // InternalSemanticVersioning.g:383:2: ( ( (lv_major_0_0= ruleXr ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleXr ) ) (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )? )? )
            // InternalSemanticVersioning.g:384:3: ( (lv_major_0_0= ruleXr ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleXr ) ) (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )? )?
            {
            // InternalSemanticVersioning.g:384:3: ( (lv_major_0_0= ruleXr ) )
            // InternalSemanticVersioning.g:385:4: (lv_major_0_0= ruleXr )
            {
            // InternalSemanticVersioning.g:385:4: (lv_major_0_0= ruleXr )
            // InternalSemanticVersioning.g:386:5: lv_major_0_0= ruleXr
            {

            					newCompositeNode(grammarAccess.getVersionNumberAccess().getMajorXrParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_10);
            lv_major_0_0=ruleXr();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVersionNumberRule());
            					}
            					set(
            						current,
            						"major",
            						lv_major_0_0,
            						"org.eclipse.n4js.semver.SemanticVersioning.Xr");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSemanticVersioning.g:403:3: (otherlv_1= '.' ( (lv_minor_2_0= ruleXr ) ) (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )? )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==28) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSemanticVersioning.g:404:4: otherlv_1= '.' ( (lv_minor_2_0= ruleXr ) ) (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )?
                    {
                    otherlv_1=(Token)match(input,28,FOLLOW_6); 

                    				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                    			
                    // InternalSemanticVersioning.g:408:4: ( (lv_minor_2_0= ruleXr ) )
                    // InternalSemanticVersioning.g:409:5: (lv_minor_2_0= ruleXr )
                    {
                    // InternalSemanticVersioning.g:409:5: (lv_minor_2_0= ruleXr )
                    // InternalSemanticVersioning.g:410:6: lv_minor_2_0= ruleXr
                    {

                    						newCompositeNode(grammarAccess.getVersionNumberAccess().getMinorXrParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_10);
                    lv_minor_2_0=ruleXr();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                    						}
                    						set(
                    							current,
                    							"minor",
                    							lv_minor_2_0,
                    							"org.eclipse.n4js.semver.SemanticVersioning.Xr");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalSemanticVersioning.g:427:4: (otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )? )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==28) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // InternalSemanticVersioning.g:428:5: otherlv_3= '.' ( (lv_path_4_0= ruleXr ) ) ( (lv_qualifier_5_0= ruleQualifier ) )?
                            {
                            otherlv_3=(Token)match(input,28,FOLLOW_6); 

                            					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                            				
                            // InternalSemanticVersioning.g:432:5: ( (lv_path_4_0= ruleXr ) )
                            // InternalSemanticVersioning.g:433:6: (lv_path_4_0= ruleXr )
                            {
                            // InternalSemanticVersioning.g:433:6: (lv_path_4_0= ruleXr )
                            // InternalSemanticVersioning.g:434:7: lv_path_4_0= ruleXr
                            {

                            							newCompositeNode(grammarAccess.getVersionNumberAccess().getPathXrParserRuleCall_1_2_1_0());
                            						
                            pushFollow(FOLLOW_11);
                            lv_path_4_0=ruleXr();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                            							}
                            							set(
                            								current,
                            								"path",
                            								lv_path_4_0,
                            								"org.eclipse.n4js.semver.SemanticVersioning.Xr");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalSemanticVersioning.g:451:5: ( (lv_qualifier_5_0= ruleQualifier ) )?
                            int alt6=2;
                            int LA6_0 = input.LA(1);

                            if ( (LA6_0==25) ) {
                                int LA6_1 = input.LA(2);

                                if ( (LA6_1==RULE_PART) ) {
                                    alt6=1;
                                }
                            }
                            else if ( (LA6_0==32) ) {
                                alt6=1;
                            }
                            switch (alt6) {
                                case 1 :
                                    // InternalSemanticVersioning.g:452:6: (lv_qualifier_5_0= ruleQualifier )
                                    {
                                    // InternalSemanticVersioning.g:452:6: (lv_qualifier_5_0= ruleQualifier )
                                    // InternalSemanticVersioning.g:453:7: lv_qualifier_5_0= ruleQualifier
                                    {

                                    							newCompositeNode(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_1_2_2_0());
                                    						
                                    pushFollow(FOLLOW_2);
                                    lv_qualifier_5_0=ruleQualifier();

                                    state._fsp--;


                                    							if (current==null) {
                                    								current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                                    							}
                                    							set(
                                    								current,
                                    								"qualifier",
                                    								lv_qualifier_5_0,
                                    								"org.eclipse.n4js.semver.SemanticVersioning.Qualifier");
                                    							afterParserOrEnumRuleCall();
                                    						

                                    }


                                    }
                                    break;

                            }


                            }
                            break;

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


    // $ANTLR start "entryRuleXr"
    // InternalSemanticVersioning.g:476:1: entryRuleXr returns [String current=null] : iv_ruleXr= ruleXr EOF ;
    public final String entryRuleXr() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleXr = null;


        try {
            // InternalSemanticVersioning.g:476:42: (iv_ruleXr= ruleXr EOF )
            // InternalSemanticVersioning.g:477:2: iv_ruleXr= ruleXr EOF
            {
             newCompositeNode(grammarAccess.getXrRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleXr=ruleXr();

            state._fsp--;

             current =iv_ruleXr.getText(); 
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
    // $ANTLR end "entryRuleXr"


    // $ANTLR start "ruleXr"
    // InternalSemanticVersioning.g:483:1: ruleXr returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'x' | kw= 'X' | kw= '*' ) ;
    public final AntlrDatatypeRuleToken ruleXr() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSemanticVersioning.g:489:2: ( (kw= 'x' | kw= 'X' | kw= '*' ) )
            // InternalSemanticVersioning.g:490:2: (kw= 'x' | kw= 'X' | kw= '*' )
            {
            // InternalSemanticVersioning.g:490:2: (kw= 'x' | kw= 'X' | kw= '*' )
            int alt9=3;
            switch ( input.LA(1) ) {
            case 29:
                {
                alt9=1;
                }
                break;
            case 30:
                {
                alt9=2;
                }
                break;
            case 31:
                {
                alt9=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalSemanticVersioning.g:491:3: kw= 'x'
                    {
                    kw=(Token)match(input,29,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getXrAccess().getXKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:497:3: kw= 'X'
                    {
                    kw=(Token)match(input,30,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getXrAccess().getXKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSemanticVersioning.g:503:3: kw= '*'
                    {
                    kw=(Token)match(input,31,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getXrAccess().getAsteriskKeyword_2());
                    		

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
    // $ANTLR end "ruleXr"


    // $ANTLR start "entryRuleQualifier"
    // InternalSemanticVersioning.g:512:1: entryRuleQualifier returns [EObject current=null] : iv_ruleQualifier= ruleQualifier EOF ;
    public final EObject entryRuleQualifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifier = null;


        try {
            // InternalSemanticVersioning.g:512:50: (iv_ruleQualifier= ruleQualifier EOF )
            // InternalSemanticVersioning.g:513:2: iv_ruleQualifier= ruleQualifier EOF
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
    // InternalSemanticVersioning.g:519:1: ruleQualifier returns [EObject current=null] : ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleParts ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleParts ) ) ) ) ;
    public final EObject ruleQualifier() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_preRelease_1_0 = null;

        AntlrDatatypeRuleToken lv_buildMetadata_3_0 = null;



        	enterRule();

        try {
            // InternalSemanticVersioning.g:525:2: ( ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleParts ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleParts ) ) ) ) )
            // InternalSemanticVersioning.g:526:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleParts ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleParts ) ) ) )
            {
            // InternalSemanticVersioning.g:526:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleParts ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleParts ) ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==25) ) {
                alt10=1;
            }
            else if ( (LA10_0==32) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalSemanticVersioning.g:527:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleParts ) ) )
                    {
                    // InternalSemanticVersioning.g:527:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleParts ) ) )
                    // InternalSemanticVersioning.g:528:4: otherlv_0= '-' ( (lv_preRelease_1_0= ruleParts ) )
                    {
                    otherlv_0=(Token)match(input,25,FOLLOW_12); 

                    				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
                    			
                    // InternalSemanticVersioning.g:532:4: ( (lv_preRelease_1_0= ruleParts ) )
                    // InternalSemanticVersioning.g:533:5: (lv_preRelease_1_0= ruleParts )
                    {
                    // InternalSemanticVersioning.g:533:5: (lv_preRelease_1_0= ruleParts )
                    // InternalSemanticVersioning.g:534:6: lv_preRelease_1_0= ruleParts
                    {

                    						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleasePartsParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_preRelease_1_0=ruleParts();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQualifierRule());
                    						}
                    						set(
                    							current,
                    							"preRelease",
                    							lv_preRelease_1_0,
                    							"org.eclipse.n4js.semver.SemanticVersioning.Parts");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:553:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleParts ) ) )
                    {
                    // InternalSemanticVersioning.g:553:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleParts ) ) )
                    // InternalSemanticVersioning.g:554:4: otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleParts ) )
                    {
                    otherlv_2=(Token)match(input,32,FOLLOW_12); 

                    				newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
                    			
                    // InternalSemanticVersioning.g:558:4: ( (lv_buildMetadata_3_0= ruleParts ) )
                    // InternalSemanticVersioning.g:559:5: (lv_buildMetadata_3_0= ruleParts )
                    {
                    // InternalSemanticVersioning.g:559:5: (lv_buildMetadata_3_0= ruleParts )
                    // InternalSemanticVersioning.g:560:6: lv_buildMetadata_3_0= ruleParts
                    {

                    						newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataPartsParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_buildMetadata_3_0=ruleParts();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQualifierRule());
                    						}
                    						set(
                    							current,
                    							"buildMetadata",
                    							lv_buildMetadata_3_0,
                    							"org.eclipse.n4js.semver.SemanticVersioning.Parts");
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


    // $ANTLR start "entryRuleParts"
    // InternalSemanticVersioning.g:582:1: entryRuleParts returns [String current=null] : iv_ruleParts= ruleParts EOF ;
    public final String entryRuleParts() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleParts = null;


        try {
            // InternalSemanticVersioning.g:582:45: (iv_ruleParts= ruleParts EOF )
            // InternalSemanticVersioning.g:583:2: iv_ruleParts= ruleParts EOF
            {
             newCompositeNode(grammarAccess.getPartsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParts=ruleParts();

            state._fsp--;

             current =iv_ruleParts.getText(); 
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
    // $ANTLR end "entryRuleParts"


    // $ANTLR start "ruleParts"
    // InternalSemanticVersioning.g:589:1: ruleParts returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PART_0= RULE_PART (kw= '.' this_PART_2= RULE_PART )* ) ;
    public final AntlrDatatypeRuleToken ruleParts() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_PART_0=null;
        Token kw=null;
        Token this_PART_2=null;


        	enterRule();

        try {
            // InternalSemanticVersioning.g:595:2: ( (this_PART_0= RULE_PART (kw= '.' this_PART_2= RULE_PART )* ) )
            // InternalSemanticVersioning.g:596:2: (this_PART_0= RULE_PART (kw= '.' this_PART_2= RULE_PART )* )
            {
            // InternalSemanticVersioning.g:596:2: (this_PART_0= RULE_PART (kw= '.' this_PART_2= RULE_PART )* )
            // InternalSemanticVersioning.g:597:3: this_PART_0= RULE_PART (kw= '.' this_PART_2= RULE_PART )*
            {
            this_PART_0=(Token)match(input,RULE_PART,FOLLOW_10); 

            			current.merge(this_PART_0);
            		

            			newLeafNode(this_PART_0, grammarAccess.getPartsAccess().getPARTTerminalRuleCall_0());
            		
            // InternalSemanticVersioning.g:604:3: (kw= '.' this_PART_2= RULE_PART )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==28) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalSemanticVersioning.g:605:4: kw= '.' this_PART_2= RULE_PART
            	    {
            	    kw=(Token)match(input,28,FOLLOW_12); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getPartsAccess().getFullStopKeyword_1_0());
            	    			
            	    this_PART_2=(Token)match(input,RULE_PART,FOLLOW_10); 

            	    				current.merge(this_PART_2);
            	    			

            	    				newLeafNode(this_PART_2, grammarAccess.getPartsAccess().getPARTTerminalRuleCall_1_1());
            	    			

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
    // $ANTLR end "ruleParts"


    // $ANTLR start "ruleVersionComparator"
    // InternalSemanticVersioning.g:622:1: ruleVersionComparator returns [Enumerator current=null] : ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) ) ;
    public final Enumerator ruleVersionComparator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSemanticVersioning.g:628:2: ( ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) ) )
            // InternalSemanticVersioning.g:629:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) )
            {
            // InternalSemanticVersioning.g:629:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) )
            int alt12=5;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt12=1;
                }
                break;
            case 34:
                {
                alt12=2;
                }
                break;
            case 35:
                {
                alt12=3;
                }
                break;
            case 36:
                {
                alt12=4;
                }
                break;
            case 37:
                {
                alt12=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // InternalSemanticVersioning.g:630:3: (enumLiteral_0= '=' )
                    {
                    // InternalSemanticVersioning.g:630:3: (enumLiteral_0= '=' )
                    // InternalSemanticVersioning.g:631:4: enumLiteral_0= '='
                    {
                    enumLiteral_0=(Token)match(input,33,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:638:3: (enumLiteral_1= '<' )
                    {
                    // InternalSemanticVersioning.g:638:3: (enumLiteral_1= '<' )
                    // InternalSemanticVersioning.g:639:4: enumLiteral_1= '<'
                    {
                    enumLiteral_1=(Token)match(input,34,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSemanticVersioning.g:646:3: (enumLiteral_2= '<=' )
                    {
                    // InternalSemanticVersioning.g:646:3: (enumLiteral_2= '<=' )
                    // InternalSemanticVersioning.g:647:4: enumLiteral_2= '<='
                    {
                    enumLiteral_2=(Token)match(input,35,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSemanticVersioning.g:654:3: (enumLiteral_3= '>' )
                    {
                    // InternalSemanticVersioning.g:654:3: (enumLiteral_3= '>' )
                    // InternalSemanticVersioning.g:655:4: enumLiteral_3= '>'
                    {
                    enumLiteral_3=(Token)match(input,36,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSemanticVersioning.g:662:3: (enumLiteral_4= '>=' )
                    {
                    // InternalSemanticVersioning.g:662:3: (enumLiteral_4= '>=' )
                    // InternalSemanticVersioning.g:663:4: enumLiteral_4= '>='
                    {
                    enumLiteral_4=(Token)match(input,37,FOLLOW_2); 

                    				current = grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_4());
                    			

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


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000003EE4000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x00000000E0000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000003EE4000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000102000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000010L});

}