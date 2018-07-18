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
public class InternalSEMVERParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_WS", "RULE_DIGITS", "RULE_LETTERS", "RULE_LETTER", "RULE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'://'", "'#'", "'semver:'", "'file:'", "'||'", "'-'", "'.'", "'+'", "'x'", "'X'", "'*'", "'/'", "':'", "'@'", "'v'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='"
    };
    public static final int RULE_WHITESPACE_FRAGMENT=9;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=10;
    public static final int RULE_EOL=11;
    public static final int RULE_DIGIT=8;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=22;
    public static final int RULE_LETTERS=6;
    public static final int RULE_ZWNJ=16;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int RULE_ML_COMMENT_FRAGMENT=21;
    public static final int RULE_DIGITS=5;
    public static final int RULE_ZWJ=15;
    public static final int RULE_SL_COMMENT_FRAGMENT=20;
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
    public static final int RULE_WS=4;
    public static final int RULE_BOM=17;
    public static final int RULE_ANY_OTHER=26;
    public static final int RULE_LETTER=7;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=19;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=25;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=12;
    public static final int T__48=48;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_HEX_DIGIT=13;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=14;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=24;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

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



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */

     	private SEMVERGrammarAccess grammarAccess;

        public InternalSEMVERParser(TokenStream input, SEMVERGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "NPMVersionRequirement";
       	}

       	@Override
       	protected SEMVERGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleNPMVersionRequirement"
    // InternalSEMVER.g:78:1: entryRuleNPMVersionRequirement returns [EObject current=null] : iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF ;
    public final EObject entryRuleNPMVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNPMVersionRequirement = null;


        try {
            // InternalSEMVER.g:78:62: (iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF )
            // InternalSEMVER.g:79:2: iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNPMVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNPMVersionRequirement=ruleNPMVersionRequirement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNPMVersionRequirement; 
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
    // $ANTLR end "entryRuleNPMVersionRequirement"


    // $ANTLR start "ruleNPMVersionRequirement"
    // InternalSEMVER.g:85:1: ruleNPMVersionRequirement returns [EObject current=null] : ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) ) ;
    public final EObject ruleNPMVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token this_WS_0=null;
        Token this_WS_6=null;
        EObject this_VersionRangeSetRequirement_1 = null;

        EObject this_URLVersionRequirement_2 = null;

        EObject this_LocalPathVersionRequirement_3 = null;

        EObject this_GitHubVersionRequirement_4 = null;

        EObject this_TagVersionRequirement_5 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:91:2: ( ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) ) )
            // InternalSEMVER.g:92:2: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) )
            {
            // InternalSEMVER.g:92:2: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) )
            int alt4=2;
            switch ( input.LA(1) ) {
            case EOF:
            case RULE_WS:
            case 35:
            case 36:
            case 37:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
                {
                alt4=1;
                }
                break;
            case RULE_DIGITS:
                {
                int LA4_2 = input.LA(2);

                if ( (synpred2_InternalSEMVER()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LETTERS:
            case 30:
            case 32:
            case 33:
            case 34:
            case 38:
            case 39:
            case 40:
                {
                alt4=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalSEMVER.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    {
                    // InternalSEMVER.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    // InternalSEMVER.g:94:4: (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement
                    {
                    // InternalSEMVER.g:94:4: (this_WS_0= RULE_WS )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==RULE_WS) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // InternalSEMVER.g:95:5: this_WS_0= RULE_WS
                    	    {
                    	    this_WS_0=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_WS_0, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getVersionRangeSetRequirementParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_VersionRangeSetRequirement_1=ruleVersionRangeSetRequirement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_VersionRangeSetRequirement_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:113:3: ( (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* )
                    {
                    // InternalSEMVER.g:113:3: ( (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* )
                    // InternalSEMVER.g:114:4: (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )*
                    {
                    // InternalSEMVER.g:114:4: (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement )
                    int alt2=4;
                    alt2 = dfa2.predict(input);
                    switch (alt2) {
                        case 1 :
                            // InternalSEMVER.g:115:5: this_URLVersionRequirement_2= ruleURLVersionRequirement
                            {
                            if ( state.backtracking==0 ) {

                              					/* */
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_0());
                              				
                            }
                            pushFollow(FOLLOW_4);
                            this_URLVersionRequirement_2=ruleURLVersionRequirement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_URLVersionRequirement_2;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalSEMVER.g:127:5: this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement
                            {
                            if ( state.backtracking==0 ) {

                              					/* */
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_1());
                              				
                            }
                            pushFollow(FOLLOW_4);
                            this_LocalPathVersionRequirement_3=ruleLocalPathVersionRequirement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_LocalPathVersionRequirement_3;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 3 :
                            // InternalSEMVER.g:139:5: this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement
                            {
                            if ( state.backtracking==0 ) {

                              					/* */
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_2());
                              				
                            }
                            pushFollow(FOLLOW_4);
                            this_GitHubVersionRequirement_4=ruleGitHubVersionRequirement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_GitHubVersionRequirement_4;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 4 :
                            // InternalSEMVER.g:151:5: this_TagVersionRequirement_5= ruleTagVersionRequirement
                            {
                            if ( state.backtracking==0 ) {

                              					/* */
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_3());
                              				
                            }
                            pushFollow(FOLLOW_4);
                            this_TagVersionRequirement_5=ruleTagVersionRequirement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_TagVersionRequirement_5;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;

                    }

                    // InternalSEMVER.g:163:4: (this_WS_6= RULE_WS )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==RULE_WS) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // InternalSEMVER.g:164:5: this_WS_6= RULE_WS
                    	    {
                    	    this_WS_6=(Token)match(input,RULE_WS,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_WS_6, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
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
    // $ANTLR end "ruleNPMVersionRequirement"


    // $ANTLR start "entryRuleURLVersionRequirement"
    // InternalSEMVER.g:174:1: entryRuleURLVersionRequirement returns [EObject current=null] : iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF ;
    public final EObject entryRuleURLVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionRequirement = null;


        try {
            // InternalSEMVER.g:174:62: (iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF )
            // InternalSEMVER.g:175:2: iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getURLVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleURLVersionRequirement=ruleURLVersionRequirement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleURLVersionRequirement; 
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
    // $ANTLR end "entryRuleURLVersionRequirement"


    // $ANTLR start "ruleURLVersionRequirement"
    // InternalSEMVER.g:181:1: ruleURLVersionRequirement returns [EObject current=null] : ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) otherlv_1= '://' ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? ) ;
    public final EObject ruleURLVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_protocol_0_0 = null;

        AntlrDatatypeRuleToken lv_url_2_0 = null;

        EObject lv_versionSpecifier_4_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:187:2: ( ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) otherlv_1= '://' ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? ) )
            // InternalSEMVER.g:188:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) otherlv_1= '://' ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? )
            {
            // InternalSEMVER.g:188:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) otherlv_1= '://' ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? )
            // InternalSEMVER.g:189:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) otherlv_1= '://' ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )?
            {
            // InternalSEMVER.g:189:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) )
            // InternalSEMVER.g:190:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            {
            // InternalSEMVER.g:190:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            // InternalSEMVER.g:191:5: lv_protocol_0_0= ruleURL_PROTOCOL
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getProtocolURL_PROTOCOLParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_5);
            lv_protocol_0_0=ruleURL_PROTOCOL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
              					}
              					set(
              						current,
              						"protocol",
              						lv_protocol_0_0,
              						"org.eclipse.n4js.semver.SEMVER.URL_PROTOCOL");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getURLVersionRequirementAccess().getColonSolidusSolidusKeyword_1());
              		
            }
            // InternalSEMVER.g:212:3: ( (lv_url_2_0= ruleURL ) )
            // InternalSEMVER.g:213:4: (lv_url_2_0= ruleURL )
            {
            // InternalSEMVER.g:213:4: (lv_url_2_0= ruleURL )
            // InternalSEMVER.g:214:5: lv_url_2_0= ruleURL
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_7);
            lv_url_2_0=ruleURL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
              					}
              					set(
              						current,
              						"url",
              						lv_url_2_0,
              						"org.eclipse.n4js.semver.SEMVER.URL");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSEMVER.g:231:3: (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==28) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSEMVER.g:232:4: otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) )
                    {
                    otherlv_3=(Token)match(input,28,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0());
                      			
                    }
                    // InternalSEMVER.g:236:4: ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) )
                    // InternalSEMVER.g:237:5: (lv_versionSpecifier_4_0= ruleURLVersionSpecifier )
                    {
                    // InternalSEMVER.g:237:5: (lv_versionSpecifier_4_0= ruleURLVersionSpecifier )
                    // InternalSEMVER.g:238:6: lv_versionSpecifier_4_0= ruleURLVersionSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_versionSpecifier_4_0=ruleURLVersionSpecifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
                      						}
                      						set(
                      							current,
                      							"versionSpecifier",
                      							lv_versionSpecifier_4_0,
                      							"org.eclipse.n4js.semver.SEMVER.URLVersionSpecifier");
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
    // $ANTLR end "ruleURLVersionRequirement"


    // $ANTLR start "entryRuleURLVersionSpecifier"
    // InternalSEMVER.g:260:1: entryRuleURLVersionSpecifier returns [EObject current=null] : iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF ;
    public final EObject entryRuleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionSpecifier = null;


        try {
            // InternalSEMVER.g:260:60: (iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF )
            // InternalSEMVER.g:261:2: iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getURLVersionSpecifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleURLVersionSpecifier=ruleURLVersionSpecifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleURLVersionSpecifier; 
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
    // $ANTLR end "entryRuleURLVersionSpecifier"


    // $ANTLR start "ruleURLVersionSpecifier"
    // InternalSEMVER.g:267:1: ruleURLVersionSpecifier returns [EObject current=null] : ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH ) ;
    public final EObject ruleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject this_URLSemver_0 = null;

        EObject this_URLCommitISH_1 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:273:2: ( ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH ) )
            // InternalSEMVER.g:274:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH )
            {
            // InternalSEMVER.g:274:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH )
            int alt6=2;
            switch ( input.LA(1) ) {
            case 29:
            case 35:
            case 36:
            case 37:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
                {
                alt6=1;
                }
                break;
            case RULE_DIGITS:
                {
                int LA6_2 = input.LA(2);

                if ( (synpred9_InternalSEMVER()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LETTERS:
            case 32:
                {
                alt6=2;
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
                    // InternalSEMVER.g:275:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    {
                    // InternalSEMVER.g:275:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    // InternalSEMVER.g:276:4: ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver
                    {
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_URLSemver_0=ruleURLSemver();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_URLSemver_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:290:3: this_URLCommitISH_1= ruleURLCommitISH
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_URLCommitISH_1=ruleURLCommitISH();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_URLCommitISH_1;
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
    // $ANTLR end "ruleURLVersionSpecifier"


    // $ANTLR start "entryRuleURLSemver"
    // InternalSEMVER.g:305:1: entryRuleURLSemver returns [EObject current=null] : iv_ruleURLSemver= ruleURLSemver EOF ;
    public final EObject entryRuleURLSemver() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLSemver = null;


        try {
            // InternalSEMVER.g:305:50: (iv_ruleURLSemver= ruleURLSemver EOF )
            // InternalSEMVER.g:306:2: iv_ruleURLSemver= ruleURLSemver EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getURLSemverRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleURLSemver=ruleURLSemver();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleURLSemver; 
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
    // $ANTLR end "entryRuleURLSemver"


    // $ANTLR start "ruleURLSemver"
    // InternalSEMVER.g:312:1: ruleURLSemver returns [EObject current=null] : ( (otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) ) ;
    public final EObject ruleURLSemver() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_simpleVersion_1_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:318:2: ( ( (otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) ) )
            // InternalSEMVER.g:319:2: ( (otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) )
            {
            // InternalSEMVER.g:319:2: ( (otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) )
            // InternalSEMVER.g:320:3: (otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) )
            {
            // InternalSEMVER.g:320:3: (otherlv_0= 'semver:' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==29) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSEMVER.g:321:4: otherlv_0= 'semver:'
                    {
                    otherlv_0=(Token)match(input,29,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getURLSemverAccess().getSemverKeyword_0());
                      			
                    }

                    }
                    break;

            }

            // InternalSEMVER.g:326:3: ( (lv_simpleVersion_1_0= ruleSimpleVersion ) )
            // InternalSEMVER.g:327:4: (lv_simpleVersion_1_0= ruleSimpleVersion )
            {
            // InternalSEMVER.g:327:4: (lv_simpleVersion_1_0= ruleSimpleVersion )
            // InternalSEMVER.g:328:5: lv_simpleVersion_1_0= ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_simpleVersion_1_0=ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getURLSemverRule());
              					}
              					set(
              						current,
              						"simpleVersion",
              						lv_simpleVersion_1_0,
              						"org.eclipse.n4js.semver.SEMVER.SimpleVersion");
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
    // $ANTLR end "ruleURLSemver"


    // $ANTLR start "entryRuleURLCommitISH"
    // InternalSEMVER.g:349:1: entryRuleURLCommitISH returns [EObject current=null] : iv_ruleURLCommitISH= ruleURLCommitISH EOF ;
    public final EObject entryRuleURLCommitISH() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLCommitISH = null;


        try {
            // InternalSEMVER.g:349:53: (iv_ruleURLCommitISH= ruleURLCommitISH EOF )
            // InternalSEMVER.g:350:2: iv_ruleURLCommitISH= ruleURLCommitISH EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getURLCommitISHRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleURLCommitISH=ruleURLCommitISH();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleURLCommitISH; 
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
    // $ANTLR end "entryRuleURLCommitISH"


    // $ANTLR start "ruleURLCommitISH"
    // InternalSEMVER.g:356:1: ruleURLCommitISH returns [EObject current=null] : ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) ) ;
    public final EObject ruleURLCommitISH() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_commitISH_0_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:362:2: ( ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) ) )
            // InternalSEMVER.g:363:2: ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) )
            {
            // InternalSEMVER.g:363:2: ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:364:3: (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:364:3: (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:365:4: lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getURLCommitISHAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_commitISH_0_0=ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getURLCommitISHRule());
              				}
              				set(
              					current,
              					"commitISH",
              					lv_commitISH_0_0,
              					"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
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
    // $ANTLR end "ruleURLCommitISH"


    // $ANTLR start "entryRuleTagVersionRequirement"
    // InternalSEMVER.g:385:1: entryRuleTagVersionRequirement returns [EObject current=null] : iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF ;
    public final EObject entryRuleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTagVersionRequirement = null;


        try {
            // InternalSEMVER.g:385:62: (iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF )
            // InternalSEMVER.g:386:2: iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTagVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTagVersionRequirement=ruleTagVersionRequirement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTagVersionRequirement; 
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
    // $ANTLR end "entryRuleTagVersionRequirement"


    // $ANTLR start "ruleTagVersionRequirement"
    // InternalSEMVER.g:392:1: ruleTagVersionRequirement returns [EObject current=null] : ( (lv_tagName_0_0= ruleTAG ) ) ;
    public final EObject ruleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_tagName_0_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:398:2: ( ( (lv_tagName_0_0= ruleTAG ) ) )
            // InternalSEMVER.g:399:2: ( (lv_tagName_0_0= ruleTAG ) )
            {
            // InternalSEMVER.g:399:2: ( (lv_tagName_0_0= ruleTAG ) )
            // InternalSEMVER.g:400:3: (lv_tagName_0_0= ruleTAG )
            {
            // InternalSEMVER.g:400:3: (lv_tagName_0_0= ruleTAG )
            // InternalSEMVER.g:401:4: lv_tagName_0_0= ruleTAG
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getTagVersionRequirementAccess().getTagNameTAGParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_tagName_0_0=ruleTAG();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getTagVersionRequirementRule());
              				}
              				set(
              					current,
              					"tagName",
              					lv_tagName_0_0,
              					"org.eclipse.n4js.semver.SEMVER.TAG");
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
    // $ANTLR end "ruleTagVersionRequirement"


    // $ANTLR start "entryRuleGitHubVersionRequirement"
    // InternalSEMVER.g:421:1: entryRuleGitHubVersionRequirement returns [EObject current=null] : iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF ;
    public final EObject entryRuleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGitHubVersionRequirement = null;


        try {
            // InternalSEMVER.g:421:65: (iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF )
            // InternalSEMVER.g:422:2: iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGitHubVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGitHubVersionRequirement=ruleGitHubVersionRequirement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGitHubVersionRequirement; 
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
    // $ANTLR end "entryRuleGitHubVersionRequirement"


    // $ANTLR start "ruleGitHubVersionRequirement"
    // InternalSEMVER.g:428:1: ruleGitHubVersionRequirement returns [EObject current=null] : ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) ;
    public final EObject ruleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_githubUrl_0_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_2_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:434:2: ( ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) )
            // InternalSEMVER.g:435:2: ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            {
            // InternalSEMVER.g:435:2: ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            // InternalSEMVER.g:436:3: ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            {
            // InternalSEMVER.g:436:3: ( (lv_githubUrl_0_0= ruleURL ) )
            // InternalSEMVER.g:437:4: (lv_githubUrl_0_0= ruleURL )
            {
            // InternalSEMVER.g:437:4: (lv_githubUrl_0_0= ruleURL )
            // InternalSEMVER.g:438:5: lv_githubUrl_0_0= ruleURL
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURLParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_7);
            lv_githubUrl_0_0=ruleURL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getGitHubVersionRequirementRule());
              					}
              					set(
              						current,
              						"githubUrl",
              						lv_githubUrl_0_0,
              						"org.eclipse.n4js.semver.SEMVER.URL");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSEMVER.g:455:3: (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==28) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSEMVER.g:456:4: otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_1=(Token)match(input,28,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0());
                      			
                    }
                    // InternalSEMVER.g:460:4: ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSEMVER.g:461:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSEMVER.g:461:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSEMVER.g:462:6: lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_commitISH_2_0=ruleALPHA_NUMERIC_CHARS();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getGitHubVersionRequirementRule());
                      						}
                      						set(
                      							current,
                      							"commitISH",
                      							lv_commitISH_2_0,
                      							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
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
    // $ANTLR end "ruleGitHubVersionRequirement"


    // $ANTLR start "entryRuleLocalPathVersionRequirement"
    // InternalSEMVER.g:484:1: entryRuleLocalPathVersionRequirement returns [EObject current=null] : iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF ;
    public final EObject entryRuleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalPathVersionRequirement = null;


        try {
            // InternalSEMVER.g:484:68: (iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF )
            // InternalSEMVER.g:485:2: iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLocalPathVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLocalPathVersionRequirement=ruleLocalPathVersionRequirement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLocalPathVersionRequirement; 
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
    // $ANTLR end "entryRuleLocalPathVersionRequirement"


    // $ANTLR start "ruleLocalPathVersionRequirement"
    // InternalSEMVER.g:491:1: ruleLocalPathVersionRequirement returns [EObject current=null] : (otherlv_0= 'file:' ( (lv_localPath_1_0= rulePATH ) ) ) ;
    public final EObject ruleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_localPath_1_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:497:2: ( (otherlv_0= 'file:' ( (lv_localPath_1_0= rulePATH ) ) ) )
            // InternalSEMVER.g:498:2: (otherlv_0= 'file:' ( (lv_localPath_1_0= rulePATH ) ) )
            {
            // InternalSEMVER.g:498:2: (otherlv_0= 'file:' ( (lv_localPath_1_0= rulePATH ) ) )
            // InternalSEMVER.g:499:3: otherlv_0= 'file:' ( (lv_localPath_1_0= rulePATH ) )
            {
            otherlv_0=(Token)match(input,30,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getLocalPathVersionRequirementAccess().getFileKeyword_0());
              		
            }
            // InternalSEMVER.g:503:3: ( (lv_localPath_1_0= rulePATH ) )
            // InternalSEMVER.g:504:4: (lv_localPath_1_0= rulePATH )
            {
            // InternalSEMVER.g:504:4: (lv_localPath_1_0= rulePATH )
            // InternalSEMVER.g:505:5: lv_localPath_1_0= rulePATH
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathPATHParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_localPath_1_0=rulePATH();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getLocalPathVersionRequirementRule());
              					}
              					set(
              						current,
              						"localPath",
              						lv_localPath_1_0,
              						"org.eclipse.n4js.semver.SEMVER.PATH");
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
    // $ANTLR end "ruleLocalPathVersionRequirement"


    // $ANTLR start "entryRuleVersionRangeSetRequirement"
    // InternalSEMVER.g:526:1: entryRuleVersionRangeSetRequirement returns [EObject current=null] : iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF ;
    public final EObject entryRuleVersionRangeSetRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeSetRequirement = null;


        try {
            // InternalSEMVER.g:526:67: (iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF )
            // InternalSEMVER.g:527:2: iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVersionRangeSetRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVersionRangeSetRequirement=ruleVersionRangeSetRequirement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVersionRangeSetRequirement; 
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
    // $ANTLR end "entryRuleVersionRangeSetRequirement"


    // $ANTLR start "ruleVersionRangeSetRequirement"
    // InternalSEMVER.g:533:1: ruleVersionRangeSetRequirement returns [EObject current=null] : ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? ) ;
    public final EObject ruleVersionRangeSetRequirement() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Token otherlv_3=null;
        Token this_WS_4=null;
        Token this_WS_6=null;
        EObject lv_ranges_1_0 = null;

        EObject lv_ranges_5_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:539:2: ( ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? ) )
            // InternalSEMVER.g:540:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? )
            {
            // InternalSEMVER.g:540:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? )
            // InternalSEMVER.g:541:3: () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )?
            {
            // InternalSEMVER.g:541:3: ()
            // InternalSEMVER.g:542:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0(),
              					current);
              			
            }

            }

            // InternalSEMVER.g:551:3: ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_DIGITS||(LA13_0>=35 && LA13_0<=37)||(LA13_0>=41 && LA13_0<=48)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalSEMVER.g:552:4: ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )*
                    {
                    // InternalSEMVER.g:552:4: ( (lv_ranges_1_0= ruleVersionRange ) )
                    // InternalSEMVER.g:553:5: (lv_ranges_1_0= ruleVersionRange )
                    {
                    // InternalSEMVER.g:553:5: (lv_ranges_1_0= ruleVersionRange )
                    // InternalSEMVER.g:554:6: lv_ranges_1_0= ruleVersionRange
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_11);
                    lv_ranges_1_0=ruleVersionRange();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVersionRangeSetRequirementRule());
                      						}
                      						add(
                      							current,
                      							"ranges",
                      							lv_ranges_1_0,
                      							"org.eclipse.n4js.semver.SEMVER.VersionRange");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSEMVER.g:571:4: ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )*
                    loop11:
                    do {
                        int alt11=2;
                        alt11 = dfa11.predict(input);
                        switch (alt11) {
                    	case 1 :
                    	    // InternalSEMVER.g:572:5: (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    {
                    	    // InternalSEMVER.g:572:5: (this_WS_2= RULE_WS )*
                    	    loop9:
                    	    do {
                    	        int alt9=2;
                    	        int LA9_0 = input.LA(1);

                    	        if ( (LA9_0==RULE_WS) ) {
                    	            alt9=1;
                    	        }


                    	        switch (alt9) {
                    	    	case 1 :
                    	    	    // InternalSEMVER.g:573:6: this_WS_2= RULE_WS
                    	    	    {
                    	    	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_12); if (state.failed) return current;
                    	    	    if ( state.backtracking==0 ) {

                    	    	      						newLeafNode(this_WS_2, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0());
                    	    	      					
                    	    	    }

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop9;
                    	        }
                    	    } while (true);

                    	    otherlv_3=(Token)match(input,31,FOLLOW_3); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1());
                    	      				
                    	    }
                    	    // InternalSEMVER.g:582:5: (this_WS_4= RULE_WS )*
                    	    loop10:
                    	    do {
                    	        int alt10=2;
                    	        int LA10_0 = input.LA(1);

                    	        if ( (LA10_0==RULE_WS) ) {
                    	            alt10=1;
                    	        }


                    	        switch (alt10) {
                    	    	case 1 :
                    	    	    // InternalSEMVER.g:583:6: this_WS_4= RULE_WS
                    	    	    {
                    	    	    this_WS_4=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
                    	    	    if ( state.backtracking==0 ) {

                    	    	      						newLeafNode(this_WS_4, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2());
                    	    	      					
                    	    	    }

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop10;
                    	        }
                    	    } while (true);

                    	    // InternalSEMVER.g:588:5: ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    // InternalSEMVER.g:589:6: (lv_ranges_5_0= ruleVersionRange )
                    	    {
                    	    // InternalSEMVER.g:589:6: (lv_ranges_5_0= ruleVersionRange )
                    	    // InternalSEMVER.g:590:7: lv_ranges_5_0= ruleVersionRange
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_1_3_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_11);
                    	    lv_ranges_5_0=ruleVersionRange();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getVersionRangeSetRequirementRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"ranges",
                    	      								lv_ranges_5_0,
                    	      								"org.eclipse.n4js.semver.SEMVER.VersionRange");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    // InternalSEMVER.g:608:4: (this_WS_6= RULE_WS )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==RULE_WS) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalSEMVER.g:609:5: this_WS_6= RULE_WS
                    	    {
                    	    this_WS_6=(Token)match(input,RULE_WS,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_WS_6, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
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
    // $ANTLR end "ruleVersionRangeSetRequirement"


    // $ANTLR start "entryRuleVersionRange"
    // InternalSEMVER.g:619:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // InternalSEMVER.g:619:53: (iv_ruleVersionRange= ruleVersionRange EOF )
            // InternalSEMVER.g:620:2: iv_ruleVersionRange= ruleVersionRange EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVersionRangeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVersionRange=ruleVersionRange();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVersionRange; 
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
    // $ANTLR end "entryRuleVersionRange"


    // $ANTLR start "ruleVersionRange"
    // InternalSEMVER.g:626:1: ruleVersionRange returns [EObject current=null] : (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_VersionRangeContraint_0 = null;

        EObject this_HyphenVersionRange_1 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:632:2: ( (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) )
            // InternalSEMVER.g:633:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            {
            // InternalSEMVER.g:633:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            int alt14=2;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // InternalSEMVER.g:634:3: this_VersionRangeContraint_0= ruleVersionRangeContraint
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_VersionRangeContraint_0=ruleVersionRangeContraint();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_VersionRangeContraint_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:646:3: this_HyphenVersionRange_1= ruleHyphenVersionRange
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_HyphenVersionRange_1=ruleHyphenVersionRange();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_HyphenVersionRange_1;
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
    // $ANTLR end "ruleVersionRange"


    // $ANTLR start "entryRuleHyphenVersionRange"
    // InternalSEMVER.g:661:1: entryRuleHyphenVersionRange returns [EObject current=null] : iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF ;
    public final EObject entryRuleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHyphenVersionRange = null;


        try {
            // InternalSEMVER.g:661:59: (iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF )
            // InternalSEMVER.g:662:2: iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getHyphenVersionRangeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleHyphenVersionRange=ruleHyphenVersionRange();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleHyphenVersionRange; 
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
    // $ANTLR end "entryRuleHyphenVersionRange"


    // $ANTLR start "ruleHyphenVersionRange"
    // InternalSEMVER.g:668:1: ruleHyphenVersionRange returns [EObject current=null] : ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Token otherlv_3=null;
        Token this_WS_4=null;
        EObject lv_from_1_0 = null;

        EObject lv_to_5_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:674:2: ( ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) )
            // InternalSEMVER.g:675:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            {
            // InternalSEMVER.g:675:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            // InternalSEMVER.g:676:3: () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) )
            {
            // InternalSEMVER.g:676:3: ()
            // InternalSEMVER.g:677:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0(),
              					current);
              			
            }

            }

            // InternalSEMVER.g:686:3: ( (lv_from_1_0= ruleVersionNumber ) )
            // InternalSEMVER.g:687:4: (lv_from_1_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:687:4: (lv_from_1_0= ruleVersionNumber )
            // InternalSEMVER.g:688:5: lv_from_1_0= ruleVersionNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_from_1_0=ruleVersionNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

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


            }

            // InternalSEMVER.g:705:3: (this_WS_2= RULE_WS )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_WS) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalSEMVER.g:706:4: this_WS_2= RULE_WS
            	    {
            	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_14); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_WS_2, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);

            otherlv_3=(Token)match(input,32,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3());
              		
            }
            // InternalSEMVER.g:715:3: (this_WS_4= RULE_WS )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_WS) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalSEMVER.g:716:4: this_WS_4= RULE_WS
            	    {
            	    this_WS_4=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_WS_4, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);

            // InternalSEMVER.g:721:3: ( (lv_to_5_0= ruleVersionNumber ) )
            // InternalSEMVER.g:722:4: (lv_to_5_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:722:4: (lv_to_5_0= ruleVersionNumber )
            // InternalSEMVER.g:723:5: lv_to_5_0= ruleVersionNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_to_5_0=ruleVersionNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleHyphenVersionRange"


    // $ANTLR start "entryRuleVersionRangeContraint"
    // InternalSEMVER.g:744:1: entryRuleVersionRangeContraint returns [EObject current=null] : iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF ;
    public final EObject entryRuleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeContraint = null;


        try {
            // InternalSEMVER.g:744:62: (iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF )
            // InternalSEMVER.g:745:2: iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVersionRangeContraintRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVersionRangeContraint=ruleVersionRangeContraint();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVersionRangeContraint; 
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
    // $ANTLR end "entryRuleVersionRangeContraint"


    // $ANTLR start "ruleVersionRangeContraint"
    // InternalSEMVER.g:751:1: ruleVersionRangeContraint returns [EObject current=null] : ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) ;
    public final EObject ruleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        EObject lv_versionConstraints_1_0 = null;

        EObject lv_versionConstraints_3_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:757:2: ( ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) )
            // InternalSEMVER.g:758:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            {
            // InternalSEMVER.g:758:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            // InternalSEMVER.g:759:3: () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            {
            // InternalSEMVER.g:759:3: ()
            // InternalSEMVER.g:760:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0(),
              					current);
              			
            }

            }

            // InternalSEMVER.g:769:3: ( (lv_versionConstraints_1_0= ruleSimpleVersion ) )
            // InternalSEMVER.g:770:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            {
            // InternalSEMVER.g:770:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            // InternalSEMVER.g:771:5: lv_versionConstraints_1_0= ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_4);
            lv_versionConstraints_1_0=ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

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


            }

            // InternalSEMVER.g:788:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            loop18:
            do {
                int alt18=2;
                alt18 = dfa18.predict(input);
                switch (alt18) {
            	case 1 :
            	    // InternalSEMVER.g:789:4: (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    {
            	    // InternalSEMVER.g:789:4: (this_WS_2= RULE_WS )+
            	    int cnt17=0;
            	    loop17:
            	    do {
            	        int alt17=2;
            	        int LA17_0 = input.LA(1);

            	        if ( (LA17_0==RULE_WS) ) {
            	            alt17=1;
            	        }


            	        switch (alt17) {
            	    	case 1 :
            	    	    // InternalSEMVER.g:790:5: this_WS_2= RULE_WS
            	    	    {
            	    	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					newLeafNode(this_WS_2, grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt17 >= 1 ) break loop17;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(17, input);
            	                throw eee;
            	        }
            	        cnt17++;
            	    } while (true);

            	    // InternalSEMVER.g:795:4: ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    // InternalSEMVER.g:796:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    {
            	    // InternalSEMVER.g:796:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    // InternalSEMVER.g:797:6: lv_versionConstraints_3_0= ruleSimpleVersion
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_4);
            	    lv_versionConstraints_3_0=ruleSimpleVersion();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

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


            	    }
            	    break;

            	default :
            	    break loop18;
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
    // $ANTLR end "ruleVersionRangeContraint"


    // $ANTLR start "entryRuleSimpleVersion"
    // InternalSEMVER.g:819:1: entryRuleSimpleVersion returns [EObject current=null] : iv_ruleSimpleVersion= ruleSimpleVersion EOF ;
    public final EObject entryRuleSimpleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleVersion = null;


        try {
            // InternalSEMVER.g:819:54: (iv_ruleSimpleVersion= ruleSimpleVersion EOF )
            // InternalSEMVER.g:820:2: iv_ruleSimpleVersion= ruleSimpleVersion EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSimpleVersionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSimpleVersion=ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSimpleVersion; 
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
    // $ANTLR end "entryRuleSimpleVersion"


    // $ANTLR start "ruleSimpleVersion"
    // InternalSEMVER.g:826:1: ruleSimpleVersion returns [EObject current=null] : ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_number_3_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleSimpleVersion() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Enumerator lv_comparators_1_0 = null;

        EObject lv_number_3_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:832:2: ( ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_number_3_0= ruleVersionNumber ) ) ) )
            // InternalSEMVER.g:833:2: ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_number_3_0= ruleVersionNumber ) ) )
            {
            // InternalSEMVER.g:833:2: ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_number_3_0= ruleVersionNumber ) ) )
            // InternalSEMVER.g:834:3: () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_number_3_0= ruleVersionNumber ) )
            {
            // InternalSEMVER.g:834:3: ()
            // InternalSEMVER.g:835:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0(),
              					current);
              			
            }

            }

            // InternalSEMVER.g:844:3: ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=41 && LA20_0<=48)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSEMVER.g:845:4: ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )*
            	    {
            	    // InternalSEMVER.g:845:4: ( (lv_comparators_1_0= ruleVersionComparator ) )
            	    // InternalSEMVER.g:846:5: (lv_comparators_1_0= ruleVersionComparator )
            	    {
            	    // InternalSEMVER.g:846:5: (lv_comparators_1_0= ruleVersionComparator )
            	    // InternalSEMVER.g:847:6: lv_comparators_1_0= ruleVersionComparator
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_comparators_1_0=ruleVersionComparator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

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


            	    }

            	    // InternalSEMVER.g:864:4: (this_WS_2= RULE_WS )*
            	    loop19:
            	    do {
            	        int alt19=2;
            	        int LA19_0 = input.LA(1);

            	        if ( (LA19_0==RULE_WS) ) {
            	            alt19=1;
            	        }


            	        switch (alt19) {
            	    	case 1 :
            	    	    // InternalSEMVER.g:865:5: this_WS_2= RULE_WS
            	    	    {
            	    	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					newLeafNode(this_WS_2, grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_1_1());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop19;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            // InternalSEMVER.g:871:3: ( (lv_number_3_0= ruleVersionNumber ) )
            // InternalSEMVER.g:872:4: (lv_number_3_0= ruleVersionNumber )
            {
            // InternalSEMVER.g:872:4: (lv_number_3_0= ruleVersionNumber )
            // InternalSEMVER.g:873:5: lv_number_3_0= ruleVersionNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_number_3_0=ruleVersionNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
              					}
              					set(
              						current,
              						"number",
              						lv_number_3_0,
              						"org.eclipse.n4js.semver.SEMVER.VersionNumber");
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
    // $ANTLR end "ruleSimpleVersion"


    // $ANTLR start "entryRuleVersionNumber"
    // InternalSEMVER.g:894:1: entryRuleVersionNumber returns [EObject current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final EObject entryRuleVersionNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionNumber = null;


        try {
            // InternalSEMVER.g:894:54: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalSEMVER.g:895:2: iv_ruleVersionNumber= ruleVersionNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVersionNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVersionNumber=ruleVersionNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVersionNumber; 
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
    // $ANTLR end "entryRuleVersionNumber"


    // $ANTLR start "ruleVersionNumber"
    // InternalSEMVER.g:901:1: ruleVersionNumber returns [EObject current=null] : ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) ;
    public final EObject ruleVersionNumber() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_major_0_0 = null;

        EObject lv_minor_2_0 = null;

        EObject lv_patch_4_0 = null;

        EObject lv_extended_6_0 = null;

        EObject lv_qualifier_7_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:907:2: ( ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) )
            // InternalSEMVER.g:908:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            {
            // InternalSEMVER.g:908:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            // InternalSEMVER.g:909:3: ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )?
            {
            // InternalSEMVER.g:909:3: ( (lv_major_0_0= ruleVersionPart ) )
            // InternalSEMVER.g:910:4: (lv_major_0_0= ruleVersionPart )
            {
            // InternalSEMVER.g:910:4: (lv_major_0_0= ruleVersionPart )
            // InternalSEMVER.g:911:5: lv_major_0_0= ruleVersionPart
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_major_0_0=ruleVersionPart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getVersionNumberRule());
              					}
              					set(
              						current,
              						"major",
              						lv_major_0_0,
              						"org.eclipse.n4js.semver.SEMVER.VersionPart");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSEMVER.g:928:3: (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==33) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSEMVER.g:929:4: otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    // InternalSEMVER.g:933:4: ( (lv_minor_2_0= ruleVersionPart ) )
                    // InternalSEMVER.g:934:5: (lv_minor_2_0= ruleVersionPart )
                    {
                    // InternalSEMVER.g:934:5: (lv_minor_2_0= ruleVersionPart )
                    // InternalSEMVER.g:935:6: lv_minor_2_0= ruleVersionPart
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
                    lv_minor_2_0=ruleVersionPart();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                      						}
                      						set(
                      							current,
                      							"minor",
                      							lv_minor_2_0,
                      							"org.eclipse.n4js.semver.SEMVER.VersionPart");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSEMVER.g:952:4: (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==33) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalSEMVER.g:953:5: otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            {
                            otherlv_3=(Token)match(input,33,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            // InternalSEMVER.g:957:5: ( (lv_patch_4_0= ruleVersionPart ) )
                            // InternalSEMVER.g:958:6: (lv_patch_4_0= ruleVersionPart )
                            {
                            // InternalSEMVER.g:958:6: (lv_patch_4_0= ruleVersionPart )
                            // InternalSEMVER.g:959:7: lv_patch_4_0= ruleVersionPart
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0());
                              						
                            }
                            pushFollow(FOLLOW_15);
                            lv_patch_4_0=ruleVersionPart();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                              							}
                              							set(
                              								current,
                              								"patch",
                              								lv_patch_4_0,
                              								"org.eclipse.n4js.semver.SEMVER.VersionPart");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalSEMVER.g:976:5: (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            loop21:
                            do {
                                int alt21=2;
                                int LA21_0 = input.LA(1);

                                if ( (LA21_0==33) ) {
                                    alt21=1;
                                }


                                switch (alt21) {
                            	case 1 :
                            	    // InternalSEMVER.g:977:6: otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) )
                            	    {
                            	    otherlv_5=(Token)match(input,33,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_5, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0());
                            	      					
                            	    }
                            	    // InternalSEMVER.g:981:6: ( (lv_extended_6_0= ruleVersionPart ) )
                            	    // InternalSEMVER.g:982:7: (lv_extended_6_0= ruleVersionPart )
                            	    {
                            	    // InternalSEMVER.g:982:7: (lv_extended_6_0= ruleVersionPart )
                            	    // InternalSEMVER.g:983:8: lv_extended_6_0= ruleVersionPart
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_15);
                            	    lv_extended_6_0=ruleVersionPart();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getVersionNumberRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"extended",
                            	      									lv_extended_6_0,
                            	      									"org.eclipse.n4js.semver.SEMVER.VersionPart");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop21;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalSEMVER.g:1003:3: ( (lv_qualifier_7_0= ruleQualifier ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==32||LA24_0==34) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSEMVER.g:1004:4: (lv_qualifier_7_0= ruleQualifier )
                    {
                    // InternalSEMVER.g:1004:4: (lv_qualifier_7_0= ruleQualifier )
                    // InternalSEMVER.g:1005:5: lv_qualifier_7_0= ruleQualifier
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_qualifier_7_0=ruleQualifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleVersionNumber"


    // $ANTLR start "entryRuleVersionPart"
    // InternalSEMVER.g:1026:1: entryRuleVersionPart returns [EObject current=null] : iv_ruleVersionPart= ruleVersionPart EOF ;
    public final EObject entryRuleVersionPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionPart = null;


        try {
            // InternalSEMVER.g:1026:52: (iv_ruleVersionPart= ruleVersionPart EOF )
            // InternalSEMVER.g:1027:2: iv_ruleVersionPart= ruleVersionPart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVersionPartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVersionPart=ruleVersionPart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVersionPart; 
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
    // $ANTLR end "entryRuleVersionPart"


    // $ANTLR start "ruleVersionPart"
    // InternalSEMVER.g:1033:1: ruleVersionPart returns [EObject current=null] : ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) ;
    public final EObject ruleVersionPart() throws RecognitionException {
        EObject current = null;

        Token lv_numberRaw_1_0=null;
        AntlrDatatypeRuleToken lv_wildcard_0_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:1039:2: ( ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) )
            // InternalSEMVER.g:1040:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            {
            // InternalSEMVER.g:1040:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=35 && LA25_0<=37)) ) {
                alt25=1;
            }
            else if ( (LA25_0==RULE_DIGITS) ) {
                alt25=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // InternalSEMVER.g:1041:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    {
                    // InternalSEMVER.g:1041:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    // InternalSEMVER.g:1042:4: (lv_wildcard_0_0= ruleWILDCARD )
                    {
                    // InternalSEMVER.g:1042:4: (lv_wildcard_0_0= ruleWILDCARD )
                    // InternalSEMVER.g:1043:5: lv_wildcard_0_0= ruleWILDCARD
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_wildcard_0_0=ruleWILDCARD();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getVersionPartRule());
                      					}
                      					set(
                      						current,
                      						"wildcard",
                      						true,
                      						"org.eclipse.n4js.semver.SEMVER.WILDCARD");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:1061:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    {
                    // InternalSEMVER.g:1061:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    // InternalSEMVER.g:1062:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    {
                    // InternalSEMVER.g:1062:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    // InternalSEMVER.g:1063:5: lv_numberRaw_1_0= RULE_DIGITS
                    {
                    lv_numberRaw_1_0=(Token)match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_numberRaw_1_0, grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getVersionPartRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"numberRaw",
                      						lv_numberRaw_1_0,
                      						"org.eclipse.n4js.semver.SEMVER.DIGITS");
                      				
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
    // $ANTLR end "ruleVersionPart"


    // $ANTLR start "entryRuleQualifier"
    // InternalSEMVER.g:1083:1: entryRuleQualifier returns [EObject current=null] : iv_ruleQualifier= ruleQualifier EOF ;
    public final EObject entryRuleQualifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifier = null;


        try {
            // InternalSEMVER.g:1083:50: (iv_ruleQualifier= ruleQualifier EOF )
            // InternalSEMVER.g:1084:2: iv_ruleQualifier= ruleQualifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQualifier=ruleQualifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifier; 
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
    // $ANTLR end "entryRuleQualifier"


    // $ANTLR start "ruleQualifier"
    // InternalSEMVER.g:1090:1: ruleQualifier returns [EObject current=null] : ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) ) ;
    public final EObject ruleQualifier() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_preRelease_1_0 = null;

        EObject lv_buildMetadata_3_0 = null;

        EObject lv_preRelease_5_0 = null;

        EObject lv_buildMetadata_7_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:1096:2: ( ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) ) )
            // InternalSEMVER.g:1097:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )
            {
            // InternalSEMVER.g:1097:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )
            int alt26=3;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // InternalSEMVER.g:1098:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) )
                    {
                    // InternalSEMVER.g:1098:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) )
                    // InternalSEMVER.g:1099:4: otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) )
                    {
                    otherlv_0=(Token)match(input,32,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
                      			
                    }
                    // InternalSEMVER.g:1103:4: ( (lv_preRelease_1_0= ruleQualifierTag ) )
                    // InternalSEMVER.g:1104:5: (lv_preRelease_1_0= ruleQualifierTag )
                    {
                    // InternalSEMVER.g:1104:5: (lv_preRelease_1_0= ruleQualifierTag )
                    // InternalSEMVER.g:1105:6: lv_preRelease_1_0= ruleQualifierTag
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_preRelease_1_0=ruleQualifierTag();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQualifierRule());
                      						}
                      						set(
                      							current,
                      							"preRelease",
                      							lv_preRelease_1_0,
                      							"org.eclipse.n4js.semver.SEMVER.QualifierTag");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:1124:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )
                    {
                    // InternalSEMVER.g:1124:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )
                    // InternalSEMVER.g:1125:4: otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                    {
                    otherlv_2=(Token)match(input,34,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
                      			
                    }
                    // InternalSEMVER.g:1129:4: ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                    // InternalSEMVER.g:1130:5: (lv_buildMetadata_3_0= ruleQualifierTag )
                    {
                    // InternalSEMVER.g:1130:5: (lv_buildMetadata_3_0= ruleQualifierTag )
                    // InternalSEMVER.g:1131:6: lv_buildMetadata_3_0= ruleQualifierTag
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_buildMetadata_3_0=ruleQualifierTag();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQualifierRule());
                      						}
                      						set(
                      							current,
                      							"buildMetadata",
                      							lv_buildMetadata_3_0,
                      							"org.eclipse.n4js.semver.SEMVER.QualifierTag");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:1150:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) )
                    {
                    // InternalSEMVER.g:1150:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) )
                    // InternalSEMVER.g:1151:4: otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) )
                    {
                    otherlv_4=(Token)match(input,32,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0());
                      			
                    }
                    // InternalSEMVER.g:1155:4: ( (lv_preRelease_5_0= ruleQualifierTag ) )
                    // InternalSEMVER.g:1156:5: (lv_preRelease_5_0= ruleQualifierTag )
                    {
                    // InternalSEMVER.g:1156:5: (lv_preRelease_5_0= ruleQualifierTag )
                    // InternalSEMVER.g:1157:6: lv_preRelease_5_0= ruleQualifierTag
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_16);
                    lv_preRelease_5_0=ruleQualifierTag();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQualifierRule());
                      						}
                      						set(
                      							current,
                      							"preRelease",
                      							lv_preRelease_5_0,
                      							"org.eclipse.n4js.semver.SEMVER.QualifierTag");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_6=(Token)match(input,34,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2());
                      			
                    }
                    // InternalSEMVER.g:1178:4: ( (lv_buildMetadata_7_0= ruleQualifierTag ) )
                    // InternalSEMVER.g:1179:5: (lv_buildMetadata_7_0= ruleQualifierTag )
                    {
                    // InternalSEMVER.g:1179:5: (lv_buildMetadata_7_0= ruleQualifierTag )
                    // InternalSEMVER.g:1180:6: lv_buildMetadata_7_0= ruleQualifierTag
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_2_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_buildMetadata_7_0=ruleQualifierTag();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQualifierRule());
                      						}
                      						set(
                      							current,
                      							"buildMetadata",
                      							lv_buildMetadata_7_0,
                      							"org.eclipse.n4js.semver.SEMVER.QualifierTag");
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
    // $ANTLR end "ruleQualifier"


    // $ANTLR start "entryRuleQualifierTag"
    // InternalSEMVER.g:1202:1: entryRuleQualifierTag returns [EObject current=null] : iv_ruleQualifierTag= ruleQualifierTag EOF ;
    public final EObject entryRuleQualifierTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifierTag = null;


        try {
            // InternalSEMVER.g:1202:53: (iv_ruleQualifierTag= ruleQualifierTag EOF )
            // InternalSEMVER.g:1203:2: iv_ruleQualifierTag= ruleQualifierTag EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifierTagRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQualifierTag=ruleQualifierTag();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifierTag; 
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
    // $ANTLR end "entryRuleQualifierTag"


    // $ANTLR start "ruleQualifierTag"
    // InternalSEMVER.g:1209:1: ruleQualifierTag returns [EObject current=null] : ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) ;
    public final EObject ruleQualifierTag() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_parts_0_0 = null;

        AntlrDatatypeRuleToken lv_parts_2_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:1215:2: ( ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) )
            // InternalSEMVER.g:1216:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            {
            // InternalSEMVER.g:1216:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            // InternalSEMVER.g:1217:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            {
            // InternalSEMVER.g:1217:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1218:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1218:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1219:5: lv_parts_0_0= ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_parts_0_0=ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getQualifierTagRule());
              					}
              					add(
              						current,
              						"parts",
              						lv_parts_0_0,
              						"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSEMVER.g:1236:3: (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==33) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSEMVER.g:1237:4: otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    {
            	    otherlv_1=(Token)match(input,33,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    // InternalSEMVER.g:1241:4: ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    // InternalSEMVER.g:1242:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    {
            	    // InternalSEMVER.g:1242:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    // InternalSEMVER.g:1243:6: lv_parts_2_0= ruleALPHA_NUMERIC_CHARS
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_17);
            	    lv_parts_2_0=ruleALPHA_NUMERIC_CHARS();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getQualifierTagRule());
            	      						}
            	      						add(
            	      							current,
            	      							"parts",
            	      							lv_parts_2_0,
            	      							"org.eclipse.n4js.semver.SEMVER.ALPHA_NUMERIC_CHARS");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
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
    // $ANTLR end "ruleQualifierTag"


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSEMVER.g:1265:1: entryRuleWILDCARD returns [String current=null] : iv_ruleWILDCARD= ruleWILDCARD EOF ;
    public final String entryRuleWILDCARD() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleWILDCARD = null;


        try {
            // InternalSEMVER.g:1265:48: (iv_ruleWILDCARD= ruleWILDCARD EOF )
            // InternalSEMVER.g:1266:2: iv_ruleWILDCARD= ruleWILDCARD EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWILDCARDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWILDCARD=ruleWILDCARD();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWILDCARD.getText(); 
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
    // $ANTLR end "entryRuleWILDCARD"


    // $ANTLR start "ruleWILDCARD"
    // InternalSEMVER.g:1272:1: ruleWILDCARD returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'x' | kw= 'X' | kw= '*' ) ;
    public final AntlrDatatypeRuleToken ruleWILDCARD() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSEMVER.g:1278:2: ( (kw= 'x' | kw= 'X' | kw= '*' ) )
            // InternalSEMVER.g:1279:2: (kw= 'x' | kw= 'X' | kw= '*' )
            {
            // InternalSEMVER.g:1279:2: (kw= 'x' | kw= 'X' | kw= '*' )
            int alt28=3;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt28=1;
                }
                break;
            case 36:
                {
                alt28=2;
                }
                break;
            case 37:
                {
                alt28=3;
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
                    // InternalSEMVER.g:1280:3: kw= 'x'
                    {
                    kw=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getWILDCARDAccess().getXKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:1286:3: kw= 'X'
                    {
                    kw=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getWILDCARDAccess().getXKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:1292:3: kw= '*'
                    {
                    kw=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getWILDCARDAccess().getAsteriskKeyword_2());
                      		
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
    // $ANTLR end "ruleWILDCARD"


    // $ANTLR start "entryRuleURL_PROTOCOL"
    // InternalSEMVER.g:1301:1: entryRuleURL_PROTOCOL returns [String current=null] : iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF ;
    public final String entryRuleURL_PROTOCOL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL_PROTOCOL = null;


        try {
            // InternalSEMVER.g:1301:52: (iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF )
            // InternalSEMVER.g:1302:2: iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getURL_PROTOCOLRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleURL_PROTOCOL=ruleURL_PROTOCOL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleURL_PROTOCOL.getText(); 
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
    // $ANTLR end "entryRuleURL_PROTOCOL"


    // $ANTLR start "ruleURL_PROTOCOL"
    // InternalSEMVER.g:1308:1: ruleURL_PROTOCOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTERS_0= RULE_LETTERS | kw= '+' )+ ;
    public final AntlrDatatypeRuleToken ruleURL_PROTOCOL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTERS_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSEMVER.g:1314:2: ( (this_LETTERS_0= RULE_LETTERS | kw= '+' )+ )
            // InternalSEMVER.g:1315:2: (this_LETTERS_0= RULE_LETTERS | kw= '+' )+
            {
            // InternalSEMVER.g:1315:2: (this_LETTERS_0= RULE_LETTERS | kw= '+' )+
            int cnt29=0;
            loop29:
            do {
                int alt29=3;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==RULE_LETTERS) ) {
                    alt29=1;
                }
                else if ( (LA29_0==34) ) {
                    alt29=2;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSEMVER.g:1316:3: this_LETTERS_0= RULE_LETTERS
            	    {
            	    this_LETTERS_0=(Token)match(input,RULE_LETTERS,FOLLOW_18); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_LETTERS_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_LETTERS_0, grammarAccess.getURL_PROTOCOLAccess().getLETTERSTerminalRuleCall_0());
            	      		
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSEMVER.g:1324:3: kw= '+'
            	    {
            	    kw=(Token)match(input,34,FOLLOW_18); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1());
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
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
    // $ANTLR end "ruleURL_PROTOCOL"


    // $ANTLR start "entryRuleTAG"
    // InternalSEMVER.g:1333:1: entryRuleTAG returns [String current=null] : iv_ruleTAG= ruleTAG EOF ;
    public final String entryRuleTAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTAG = null;


        try {
            // InternalSEMVER.g:1333:43: (iv_ruleTAG= ruleTAG EOF )
            // InternalSEMVER.g:1334:2: iv_ruleTAG= ruleTAG EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTAGRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTAG=ruleTAG();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTAG.getText(); 
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
    // $ANTLR end "entryRuleTAG"


    // $ANTLR start "ruleTAG"
    // InternalSEMVER.g:1340:1: ruleTAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS ;
    public final AntlrDatatypeRuleToken ruleTAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHARS_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:1346:2: (this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1347:2: this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getTAGAccess().getALPHA_NUMERIC_CHARSParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_ALPHA_NUMERIC_CHARS_0=ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_ALPHA_NUMERIC_CHARS_0);
              	
            }
            if ( state.backtracking==0 ) {

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
    // $ANTLR end "ruleTAG"


    // $ANTLR start "entryRulePATH"
    // InternalSEMVER.g:1360:1: entryRulePATH returns [String current=null] : iv_rulePATH= rulePATH EOF ;
    public final String entryRulePATH() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePATH = null;


        try {
            // InternalSEMVER.g:1360:44: (iv_rulePATH= rulePATH EOF )
            // InternalSEMVER.g:1361:2: iv_rulePATH= rulePATH EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPATHRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePATH=rulePATH();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePATH.getText(); 
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
    // $ANTLR end "entryRulePATH"


    // $ANTLR start "rulePATH"
    // InternalSEMVER.g:1367:1: rulePATH returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS )* ( (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS )+ (kw= '/' | kw= '.' )* ) ;
    public final AntlrDatatypeRuleToken rulePATH() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHARS_0 = null;

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHARS_3 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:1373:2: ( ( (this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS )* ( (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS )+ (kw= '/' | kw= '.' )* ) )
            // InternalSEMVER.g:1374:2: ( (this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS )* ( (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS )+ (kw= '/' | kw= '.' )* )
            {
            // InternalSEMVER.g:1374:2: ( (this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS )* ( (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS )+ (kw= '/' | kw= '.' )* )
            // InternalSEMVER.g:1375:3: (this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS )* ( (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS )+ (kw= '/' | kw= '.' )*
            {
            // InternalSEMVER.g:1375:3: (this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>=RULE_DIGITS && LA30_0<=RULE_LETTERS)||LA30_0==32) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSEMVER.g:1376:4: this_ALPHA_NUMERIC_CHARS_0= ruleALPHA_NUMERIC_CHARS
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARSParserRuleCall_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_10);
            	    this_ALPHA_NUMERIC_CHARS_0=ruleALPHA_NUMERIC_CHARS();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHARS_0);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            // InternalSEMVER.g:1387:3: ( (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS )+
            int cnt32=0;
            loop32:
            do {
                int alt32=2;
                alt32 = dfa32.predict(input);
                switch (alt32) {
            	case 1 :
            	    // InternalSEMVER.g:1388:4: (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS
            	    {
            	    // InternalSEMVER.g:1388:4: (kw= '/' | kw= '.' )+
            	    int cnt31=0;
            	    loop31:
            	    do {
            	        int alt31=3;
            	        int LA31_0 = input.LA(1);

            	        if ( (LA31_0==38) ) {
            	            alt31=1;
            	        }
            	        else if ( (LA31_0==33) ) {
            	            alt31=2;
            	        }


            	        switch (alt31) {
            	    	case 1 :
            	    	    // InternalSEMVER.g:1389:5: kw= '/'
            	    	    {
            	    	    kw=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					current.merge(kw);
            	    	      					newLeafNode(kw, grammarAccess.getPATHAccess().getSolidusKeyword_1_0_0());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // InternalSEMVER.g:1395:5: kw= '.'
            	    	    {
            	    	    kw=(Token)match(input,33,FOLLOW_10); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					current.merge(kw);
            	    	      					newLeafNode(kw, grammarAccess.getPATHAccess().getFullStopKeyword_1_0_1());
            	    	      				
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

            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1_1());
            	      			
            	    }
            	    pushFollow(FOLLOW_19);
            	    this_ALPHA_NUMERIC_CHARS_3=ruleALPHA_NUMERIC_CHARS();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHARS_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt32 >= 1 ) break loop32;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(32, input);
                        throw eee;
                }
                cnt32++;
            } while (true);

            // InternalSEMVER.g:1412:3: (kw= '/' | kw= '.' )*
            loop33:
            do {
                int alt33=3;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==38) ) {
                    alt33=1;
                }
                else if ( (LA33_0==33) ) {
                    alt33=2;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSEMVER.g:1413:4: kw= '/'
            	    {
            	    kw=(Token)match(input,38,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPATHAccess().getSolidusKeyword_2_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSEMVER.g:1419:4: kw= '.'
            	    {
            	    kw=(Token)match(input,33,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPATHAccess().getFullStopKeyword_2_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop33;
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
    // $ANTLR end "rulePATH"


    // $ANTLR start "entryRuleURL"
    // InternalSEMVER.g:1429:1: entryRuleURL returns [String current=null] : iv_ruleURL= ruleURL EOF ;
    public final String entryRuleURL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL = null;


        try {
            // InternalSEMVER.g:1429:43: (iv_ruleURL= ruleURL EOF )
            // InternalSEMVER.g:1430:2: iv_ruleURL= ruleURL EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getURLRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleURL=ruleURL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleURL.getText(); 
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
    // $ANTLR end "entryRuleURL"


    // $ANTLR start "ruleURL"
    // InternalSEMVER.g:1436:1: ruleURL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )* ( (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+ this_ALPHA_NUMERIC_CHARS_5= ruleALPHA_NUMERIC_CHARS )+ ) ;
    public final AntlrDatatypeRuleToken ruleURL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_0 = null;

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHARS_5 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:1442:2: ( ( (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )* ( (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+ this_ALPHA_NUMERIC_CHARS_5= ruleALPHA_NUMERIC_CHARS )+ ) )
            // InternalSEMVER.g:1443:2: ( (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )* ( (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+ this_ALPHA_NUMERIC_CHARS_5= ruleALPHA_NUMERIC_CHARS )+ )
            {
            // InternalSEMVER.g:1443:2: ( (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )* ( (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+ this_ALPHA_NUMERIC_CHARS_5= ruleALPHA_NUMERIC_CHARS )+ )
            // InternalSEMVER.g:1444:3: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )* ( (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+ this_ALPHA_NUMERIC_CHARS_5= ruleALPHA_NUMERIC_CHARS )+
            {
            // InternalSEMVER.g:1444:3: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( ((LA34_0>=RULE_DIGITS && LA34_0<=RULE_LETTERS)||LA34_0==32) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSEMVER.g:1445:4: this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARParserRuleCall_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_6);
            	    this_ALPHA_NUMERIC_CHAR_0=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHAR_0);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            // InternalSEMVER.g:1456:3: ( (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+ this_ALPHA_NUMERIC_CHARS_5= ruleALPHA_NUMERIC_CHARS )+
            int cnt36=0;
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==33||(LA36_0>=38 && LA36_0<=40)) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSEMVER.g:1457:4: (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+ this_ALPHA_NUMERIC_CHARS_5= ruleALPHA_NUMERIC_CHARS
            	    {
            	    // InternalSEMVER.g:1457:4: (kw= '/' | kw= '.' | kw= ':' | kw= '@' )+
            	    int cnt35=0;
            	    loop35:
            	    do {
            	        int alt35=5;
            	        switch ( input.LA(1) ) {
            	        case 38:
            	            {
            	            alt35=1;
            	            }
            	            break;
            	        case 33:
            	            {
            	            alt35=2;
            	            }
            	            break;
            	        case 39:
            	            {
            	            alt35=3;
            	            }
            	            break;
            	        case 40:
            	            {
            	            alt35=4;
            	            }
            	            break;

            	        }

            	        switch (alt35) {
            	    	case 1 :
            	    	    // InternalSEMVER.g:1458:5: kw= '/'
            	    	    {
            	    	    kw=(Token)match(input,38,FOLLOW_20); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					current.merge(kw);
            	    	      					newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_1_0_0());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // InternalSEMVER.g:1464:5: kw= '.'
            	    	    {
            	    	    kw=(Token)match(input,33,FOLLOW_20); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					current.merge(kw);
            	    	      					newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_1_0_1());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;
            	    	case 3 :
            	    	    // InternalSEMVER.g:1470:5: kw= ':'
            	    	    {
            	    	    kw=(Token)match(input,39,FOLLOW_20); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					current.merge(kw);
            	    	      					newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_1_0_2());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;
            	    	case 4 :
            	    	    // InternalSEMVER.g:1476:5: kw= '@'
            	    	    {
            	    	    kw=(Token)match(input,40,FOLLOW_20); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					current.merge(kw);
            	    	      					newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_1_0_3());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt35 >= 1 ) break loop35;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(35, input);
            	                throw eee;
            	        }
            	        cnt35++;
            	    } while (true);

            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1_1());
            	      			
            	    }
            	    pushFollow(FOLLOW_21);
            	    this_ALPHA_NUMERIC_CHARS_5=ruleALPHA_NUMERIC_CHARS();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHARS_5);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
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
    // $ANTLR end "ruleURL"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSEMVER.g:1497:1: entryRuleALPHA_NUMERIC_CHARS returns [String current=null] : iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF ;
    public final String entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHARS = null;


        try {
            // InternalSEMVER.g:1497:59: (iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSEMVER.g:1498:2: iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleALPHA_NUMERIC_CHARS=ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleALPHA_NUMERIC_CHARS.getText(); 
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
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHARS"


    // $ANTLR start "ruleALPHA_NUMERIC_CHARS"
    // InternalSEMVER.g:1504:1: ruleALPHA_NUMERIC_CHARS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+ ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHARS() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_0 = null;



        	enterRule();

        try {
            // InternalSEMVER.g:1510:2: ( (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+ )
            // InternalSEMVER.g:1511:2: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+
            {
            // InternalSEMVER.g:1511:2: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+
            int cnt37=0;
            loop37:
            do {
                int alt37=2;
                switch ( input.LA(1) ) {
                case 32:
                    {
                    int LA37_2 = input.LA(2);

                    if ( (synpred48_InternalSEMVER()) ) {
                        alt37=1;
                    }


                    }
                    break;
                case RULE_DIGITS:
                    {
                    int LA37_3 = input.LA(2);

                    if ( (synpred48_InternalSEMVER()) ) {
                        alt37=1;
                    }


                    }
                    break;
                case RULE_LETTERS:
                    {
                    int LA37_4 = input.LA(2);

                    if ( (synpred48_InternalSEMVER()) ) {
                        alt37=1;
                    }


                    }
                    break;

                }

                switch (alt37) {
            	case 1 :
            	    // InternalSEMVER.g:1512:3: this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall());
            	      		
            	    }
            	    pushFollow(FOLLOW_22);
            	    this_ALPHA_NUMERIC_CHAR_0=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_ALPHA_NUMERIC_CHAR_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt37 >= 1 ) break loop37;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(37, input);
                        throw eee;
                }
                cnt37++;
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
    // $ANTLR end "ruleALPHA_NUMERIC_CHARS"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHAR"
    // InternalSEMVER.g:1526:1: entryRuleALPHA_NUMERIC_CHAR returns [String current=null] : iv_ruleALPHA_NUMERIC_CHAR= ruleALPHA_NUMERIC_CHAR EOF ;
    public final String entryRuleALPHA_NUMERIC_CHAR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHAR = null;


        try {
            // InternalSEMVER.g:1526:58: (iv_ruleALPHA_NUMERIC_CHAR= ruleALPHA_NUMERIC_CHAR EOF )
            // InternalSEMVER.g:1527:2: iv_ruleALPHA_NUMERIC_CHAR= ruleALPHA_NUMERIC_CHAR EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleALPHA_NUMERIC_CHAR=ruleALPHA_NUMERIC_CHAR();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleALPHA_NUMERIC_CHAR.getText(); 
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
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHAR"


    // $ANTLR start "ruleALPHA_NUMERIC_CHAR"
    // InternalSEMVER.g:1533:1: ruleALPHA_NUMERIC_CHAR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? (this_DIGITS_1= RULE_DIGITS | this_LETTERS_2= RULE_LETTERS ) ) ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHAR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_1=null;
        Token this_LETTERS_2=null;


        	enterRule();

        try {
            // InternalSEMVER.g:1539:2: ( ( (kw= '-' )? (this_DIGITS_1= RULE_DIGITS | this_LETTERS_2= RULE_LETTERS ) ) )
            // InternalSEMVER.g:1540:2: ( (kw= '-' )? (this_DIGITS_1= RULE_DIGITS | this_LETTERS_2= RULE_LETTERS ) )
            {
            // InternalSEMVER.g:1540:2: ( (kw= '-' )? (this_DIGITS_1= RULE_DIGITS | this_LETTERS_2= RULE_LETTERS ) )
            // InternalSEMVER.g:1541:3: (kw= '-' )? (this_DIGITS_1= RULE_DIGITS | this_LETTERS_2= RULE_LETTERS )
            {
            // InternalSEMVER.g:1541:3: (kw= '-' )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==32) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalSEMVER.g:1542:4: kw= '-'
                    {
                    kw=(Token)match(input,32,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0());
                      			
                    }

                    }
                    break;

            }

            // InternalSEMVER.g:1548:3: (this_DIGITS_1= RULE_DIGITS | this_LETTERS_2= RULE_LETTERS )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==RULE_DIGITS) ) {
                alt39=1;
            }
            else if ( (LA39_0==RULE_LETTERS) ) {
                alt39=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    // InternalSEMVER.g:1549:4: this_DIGITS_1= RULE_DIGITS
                    {
                    this_DIGITS_1=(Token)match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_DIGITS_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DIGITS_1, grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:1557:4: this_LETTERS_2= RULE_LETTERS
                    {
                    this_LETTERS_2=(Token)match(input,RULE_LETTERS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LETTERS_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LETTERS_2, grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSTerminalRuleCall_1_1());
                      			
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
    // $ANTLR end "ruleALPHA_NUMERIC_CHAR"


    // $ANTLR start "ruleVersionComparator"
    // InternalSEMVER.g:1569:1: ruleVersionComparator returns [Enumerator current=null] : ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) ) ;
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
            // InternalSEMVER.g:1575:2: ( ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) ) )
            // InternalSEMVER.g:1576:2: ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) )
            {
            // InternalSEMVER.g:1576:2: ( (enumLiteral_0= 'v' ) | (enumLiteral_1= '=' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '~' ) | (enumLiteral_4= '^' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>' ) | (enumLiteral_7= '>=' ) )
            int alt40=8;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt40=1;
                }
                break;
            case 42:
                {
                alt40=2;
                }
                break;
            case 43:
                {
                alt40=3;
                }
                break;
            case 44:
                {
                alt40=4;
                }
                break;
            case 45:
                {
                alt40=5;
                }
                break;
            case 46:
                {
                alt40=6;
                }
                break;
            case 47:
                {
                alt40=7;
                }
                break;
            case 48:
                {
                alt40=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // InternalSEMVER.g:1577:3: (enumLiteral_0= 'v' )
                    {
                    // InternalSEMVER.g:1577:3: (enumLiteral_0= 'v' )
                    // InternalSEMVER.g:1578:4: enumLiteral_0= 'v'
                    {
                    enumLiteral_0=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:1585:3: (enumLiteral_1= '=' )
                    {
                    // InternalSEMVER.g:1585:3: (enumLiteral_1= '=' )
                    // InternalSEMVER.g:1586:4: enumLiteral_1= '='
                    {
                    enumLiteral_1=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:1593:3: (enumLiteral_2= '<' )
                    {
                    // InternalSEMVER.g:1593:3: (enumLiteral_2= '<' )
                    // InternalSEMVER.g:1594:4: enumLiteral_2= '<'
                    {
                    enumLiteral_2=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:1601:3: (enumLiteral_3= '~' )
                    {
                    // InternalSEMVER.g:1601:3: (enumLiteral_3= '~' )
                    // InternalSEMVER.g:1602:4: enumLiteral_3= '~'
                    {
                    enumLiteral_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSEMVER.g:1609:3: (enumLiteral_4= '^' )
                    {
                    // InternalSEMVER.g:1609:3: (enumLiteral_4= '^' )
                    // InternalSEMVER.g:1610:4: enumLiteral_4= '^'
                    {
                    enumLiteral_4=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSEMVER.g:1617:3: (enumLiteral_5= '<=' )
                    {
                    // InternalSEMVER.g:1617:3: (enumLiteral_5= '<=' )
                    // InternalSEMVER.g:1618:4: enumLiteral_5= '<='
                    {
                    enumLiteral_5=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_5, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5());
                      			
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSEMVER.g:1625:3: (enumLiteral_6= '>' )
                    {
                    // InternalSEMVER.g:1625:3: (enumLiteral_6= '>' )
                    // InternalSEMVER.g:1626:4: enumLiteral_6= '>'
                    {
                    enumLiteral_6=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_6, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6());
                      			
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSEMVER.g:1633:3: (enumLiteral_7= '>=' )
                    {
                    // InternalSEMVER.g:1633:3: (enumLiteral_7= '>=' )
                    // InternalSEMVER.g:1634:4: enumLiteral_7= '>='
                    {
                    enumLiteral_7=(Token)match(input,48,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_7, grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7());
                      			
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
    // $ANTLR end "ruleVersionComparator"

    // $ANTLR start synpred2_InternalSEMVER
    public final void synpred2_InternalSEMVER_fragment() throws RecognitionException {   
        Token this_WS_0=null;
        EObject this_VersionRangeSetRequirement_1 = null;


        // InternalSEMVER.g:93:3: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) )
        // InternalSEMVER.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
        {
        // InternalSEMVER.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
        // InternalSEMVER.g:94:4: (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement
        {
        // InternalSEMVER.g:94:4: (this_WS_0= RULE_WS )*
        loop41:
        do {
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_WS) ) {
                alt41=1;
            }


            switch (alt41) {
        	case 1 :
        	    // InternalSEMVER.g:95:5: this_WS_0= RULE_WS
        	    {
        	    this_WS_0=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop41;
            }
        } while (true);

        pushFollow(FOLLOW_2);
        this_VersionRangeSetRequirement_1=ruleVersionRangeSetRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSEMVER

    // $ANTLR start synpred9_InternalSEMVER
    public final void synpred9_InternalSEMVER_fragment() throws RecognitionException {   
        EObject this_URLSemver_0 = null;


        // InternalSEMVER.g:275:3: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) )
        // InternalSEMVER.g:275:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
        {
        // InternalSEMVER.g:275:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
        // InternalSEMVER.g:276:4: ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver
        {
        pushFollow(FOLLOW_2);
        this_URLSemver_0=ruleURLSemver();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred9_InternalSEMVER

    // $ANTLR start synpred48_InternalSEMVER
    public final void synpred48_InternalSEMVER_fragment() throws RecognitionException {   
        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_0 = null;


        // InternalSEMVER.g:1512:3: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )
        // InternalSEMVER.g:1512:3: this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR
        {
        if ( state.backtracking==0 ) {

          			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall());
          		
        }
        pushFollow(FOLLOW_2);
        this_ALPHA_NUMERIC_CHAR_0=ruleALPHA_NUMERIC_CHAR();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred48_InternalSEMVER

    // Delegated rules

    public final boolean synpred2_InternalSEMVER() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalSEMVER_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalSEMVER() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalSEMVER_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred48_InternalSEMVER() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred48_InternalSEMVER_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA32 dfa32 = new DFA32(this);
    static final String dfa_1s = "\11\uffff";
    static final String dfa_2s = "\1\uffff\1\7\3\uffff\1\7\2\uffff\1\7";
    static final String dfa_3s = "\1\5\1\4\2\uffff\1\5\1\4\2\uffff\1\4";
    static final String dfa_4s = "\2\50\2\uffff\1\6\1\50\2\uffff\1\50";
    static final String dfa_5s = "\2\uffff\1\1\1\2\2\uffff\1\3\1\4\1\uffff";
    static final String dfa_6s = "\11\uffff}>";
    static final String[] dfa_7s = {
            "\1\5\1\1\27\uffff\1\3\1\uffff\1\4\1\6\1\2\3\uffff\3\6",
            "\1\7\1\5\1\1\24\uffff\1\2\4\uffff\1\4\1\6\1\2\3\uffff\3\6",
            "",
            "",
            "\1\5\1\10",
            "\1\7\1\5\1\10\31\uffff\1\4\1\6\4\uffff\3\6",
            "",
            "",
            "\1\7\1\5\1\10\31\uffff\1\4\1\6\4\uffff\3\6"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "114:4: (this_URLVersionRequirement_2= ruleURLVersionRequirement | this_LocalPathVersionRequirement_3= ruleLocalPathVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement )";
        }
    }
    static final String dfa_8s = "\4\uffff";
    static final String dfa_9s = "\2\2\2\uffff";
    static final String dfa_10s = "\2\4\2\uffff";
    static final String dfa_11s = "\2\37\2\uffff";
    static final String dfa_12s = "\2\uffff\1\2\1\1";
    static final String dfa_13s = "\4\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\32\uffff\1\3",
            "\1\1\32\uffff\1\3",
            "",
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "()* loopback of 571:4: ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )*";
        }
    }
    static final String dfa_15s = "\57\uffff";
    static final String dfa_16s = "\2\uffff\4\1\3\uffff\5\1\1\uffff\2\1\1\uffff\2\1\5\uffff\4\1\1\uffff\2\1\1\uffff\2\1\1\uffff\2\1\2\uffff\4\1\1\uffff\2\1";
    static final String dfa_17s = "\1\5\1\uffff\4\4\3\5\5\4\1\5\2\4\1\5\2\4\1\uffff\4\5\4\4\1\5\2\4\1\5\2\4\1\5\2\4\2\5\4\4\1\5\2\4";
    static final String dfa_18s = "\1\60\1\uffff\4\42\1\45\2\40\1\60\4\42\1\6\2\42\1\6\2\41\1\uffff\1\45\3\40\4\42\1\6\2\42\1\6\2\41\1\6\2\41\1\45\1\40\4\42\1\6\2\41";
    static final String dfa_19s = "\1\uffff\1\1\22\uffff\1\2\32\uffff";
    static final String dfa_20s = "\57\uffff}>";
    static final String[] dfa_21s = {
            "\1\5\35\uffff\1\2\1\3\1\4\3\uffff\10\1",
            "",
            "\1\11\32\uffff\1\1\1\7\1\6\1\10",
            "\1\11\32\uffff\1\1\1\7\1\6\1\10",
            "\1\11\32\uffff\1\1\1\7\1\6\1\10",
            "\1\11\32\uffff\1\1\1\7\1\6\1\10",
            "\1\15\35\uffff\1\12\1\13\1\14",
            "\1\17\1\20\31\uffff\1\16",
            "\1\22\1\23\31\uffff\1\21",
            "\1\11\1\1\31\uffff\1\1\1\24\2\uffff\3\1\3\uffff\10\1",
            "\1\11\32\uffff\1\1\1\7\1\25\1\10",
            "\1\11\32\uffff\1\1\1\7\1\25\1\10",
            "\1\11\32\uffff\1\1\1\7\1\25\1\10",
            "\1\11\32\uffff\1\1\1\7\1\25\1\10",
            "\1\17\1\20",
            "\1\11\1\17\1\20\30\uffff\1\1\1\16\1\26\1\27",
            "\1\11\1\17\1\20\30\uffff\1\1\1\16\1\26\1\27",
            "\1\22\1\23",
            "\1\11\1\22\1\23\30\uffff\1\1\1\21\1\30",
            "\1\11\1\22\1\23\30\uffff\1\1\1\21\1\30",
            "",
            "\1\34\35\uffff\1\31\1\32\1\33",
            "\1\36\1\37\31\uffff\1\35",
            "\1\41\1\42\31\uffff\1\40",
            "\1\44\1\45\31\uffff\1\43",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\36\1\37",
            "\1\11\1\36\1\37\30\uffff\1\1\1\35\1\26\1\27",
            "\1\11\1\36\1\37\30\uffff\1\1\1\35\1\26\1\27",
            "\1\41\1\42",
            "\1\11\1\41\1\42\30\uffff\1\1\1\40\1\47",
            "\1\11\1\41\1\42\30\uffff\1\1\1\40\1\47",
            "\1\44\1\45",
            "\1\11\1\44\1\45\30\uffff\1\1\1\43\1\30",
            "\1\11\1\44\1\45\30\uffff\1\1\1\43\1\30",
            "\1\53\35\uffff\1\50\1\51\1\52",
            "\1\55\1\56\31\uffff\1\54",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\11\32\uffff\1\1\1\7\1\46\1\10",
            "\1\55\1\56",
            "\1\11\1\55\1\56\30\uffff\1\1\1\54\1\47",
            "\1\11\1\55\1\56\30\uffff\1\1\1\54\1\47"
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "633:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )";
        }
    }
    static final String dfa_22s = "\1\37\1\60\2\uffff";
    static final String[] dfa_23s = {
            "\1\1\32\uffff\1\2",
            "\1\1\1\3\31\uffff\1\2\3\uffff\3\3\3\uffff\10\3",
            "",
            ""
    };
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final short[][] dfa_23 = unpackEncodedStringArray(dfa_23s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_22;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_23;
        }
        public String getDescription() {
            return "()* loopback of 788:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*";
        }
    }
    static final String dfa_24s = "\14\uffff";
    static final String dfa_25s = "\4\uffff\2\7\4\uffff\2\7";
    static final String dfa_26s = "\1\40\1\5\1\uffff\1\5\2\4\1\5\2\uffff\1\5\2\4";
    static final String dfa_27s = "\1\42\1\40\1\uffff\1\6\2\42\1\40\2\uffff\1\6\2\42";
    static final String dfa_28s = "\2\uffff\1\2\4\uffff\1\1\1\3\3\uffff";
    static final String dfa_29s = "\14\uffff}>";
    static final String[] dfa_30s = {
            "\1\1\1\uffff\1\2",
            "\1\4\1\5\31\uffff\1\3",
            "",
            "\1\4\1\5",
            "\1\7\1\4\1\5\30\uffff\1\7\1\3\1\6\1\10",
            "\1\7\1\4\1\5\30\uffff\1\7\1\3\1\6\1\10",
            "\1\12\1\13\31\uffff\1\11",
            "",
            "",
            "\1\12\1\13",
            "\1\7\1\12\1\13\30\uffff\1\7\1\11\1\6\1\10",
            "\1\7\1\12\1\13\30\uffff\1\7\1\11\1\6\1\10"
    };

    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final char[] dfa_26 = DFA.unpackEncodedStringToUnsignedChars(dfa_26s);
    static final char[] dfa_27 = DFA.unpackEncodedStringToUnsignedChars(dfa_27s);
    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[][] dfa_30 = unpackEncodedStringArray(dfa_30s);

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_24;
            this.eof = dfa_25;
            this.min = dfa_26;
            this.max = dfa_27;
            this.accept = dfa_28;
            this.special = dfa_29;
            this.transition = dfa_30;
        }
        public String getDescription() {
            return "1097:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )";
        }
    }
    static final String dfa_31s = "\5\uffff";
    static final String dfa_32s = "\3\3\2\uffff";
    static final String dfa_33s = "\3\4\2\uffff";
    static final String dfa_34s = "\3\46\2\uffff";
    static final String dfa_35s = "\3\uffff\1\2\1\1";
    static final String dfa_36s = "\5\uffff}>";
    static final String[] dfa_37s = {
            "\1\3\34\uffff\1\2\4\uffff\1\1",
            "\1\3\2\4\31\uffff\1\4\1\2\4\uffff\1\1",
            "\1\3\2\4\31\uffff\1\4\1\2\4\uffff\1\1",
            "",
            ""
    };

    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final char[] dfa_33 = DFA.unpackEncodedStringToUnsignedChars(dfa_33s);
    static final char[] dfa_34 = DFA.unpackEncodedStringToUnsignedChars(dfa_34s);
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[][] dfa_37 = unpackEncodedStringArray(dfa_37s);

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = dfa_31;
            this.eof = dfa_32;
            this.min = dfa_33;
            this.max = dfa_34;
            this.accept = dfa_35;
            this.special = dfa_36;
            this.transition = dfa_37;
        }
        public String getDescription() {
            return "()+ loopback of 1387:3: ( (kw= '/' | kw= '.' )+ this_ALPHA_NUMERIC_CHARS_3= ruleALPHA_NUMERIC_CHARS )+";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0001FE3800000030L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000001C300000060L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0001FE3920000060L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0001FE3800000020L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0001FE7B20000060L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000080000012L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000080000010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000100000010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000700000002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000400000042L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000004200000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0001FFFB20000060L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x000001C200000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000100000062L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000060L});

}