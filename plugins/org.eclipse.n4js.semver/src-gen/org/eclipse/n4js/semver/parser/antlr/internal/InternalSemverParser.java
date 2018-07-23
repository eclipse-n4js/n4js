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
import org.eclipse.n4js.semver.services.SemverGrammarAccess;



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
public class InternalSemverParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_WS", "RULE_LETTER_V", "RULE_DIGITS", "RULE_LETTER_NO_VX", "RULE_LETTER_X", "RULE_ASTERIX", "RULE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'file:'", "'://'", "'#'", "'semver:'", "'||'", "'-'", "'.'", "'+'", "'/'", "':'", "'@'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='"
    };
    public static final int RULE_WHITESPACE_FRAGMENT=11;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=12;
    public static final int RULE_EOL=13;
    public static final int RULE_DIGIT=10;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=24;
    public static final int RULE_ZWNJ=18;
    public static final int T__29=29;
    public static final int RULE_ASTERIX=9;
    public static final int RULE_LETTER_NO_VX=7;
    public static final int RULE_ML_COMMENT_FRAGMENT=23;
    public static final int RULE_DIGITS=6;
    public static final int RULE_ZWJ=17;
    public static final int RULE_SL_COMMENT_FRAGMENT=22;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=25;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=20;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=4;
    public static final int RULE_BOM=19;
    public static final int RULE_LETTER_V=5;
    public static final int RULE_LETTER_X=8;
    public static final int RULE_ANY_OTHER=28;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=21;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=27;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=14;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_HEX_DIGIT=15;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=16;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=26;
    public static final int T__46=46;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators


        public InternalSemverParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSemverParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSemverParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSemver.g"; }



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */

     	private SemverGrammarAccess grammarAccess;

        public InternalSemverParser(TokenStream input, SemverGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "NPMVersionRequirement";
       	}

       	@Override
       	protected SemverGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleNPMVersionRequirement"
    // InternalSemver.g:78:1: entryRuleNPMVersionRequirement returns [EObject current=null] : iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF ;
    public final EObject entryRuleNPMVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNPMVersionRequirement = null;


        try {
            // InternalSemver.g:78:62: (iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF )
            // InternalSemver.g:79:2: iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF
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
    // InternalSemver.g:85:1: ruleNPMVersionRequirement returns [EObject current=null] : ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) ) ;
    public final EObject ruleNPMVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token this_WS_0=null;
        Token this_WS_6=null;
        EObject this_VersionRangeSetRequirement_1 = null;

        EObject this_LocalPathVersionRequirement_2 = null;

        EObject this_URLVersionRequirement_3 = null;

        EObject this_GitHubVersionRequirement_4 = null;

        EObject this_TagVersionRequirement_5 = null;



        	enterRule();

        try {
            // InternalSemver.g:91:2: ( ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) ) )
            // InternalSemver.g:92:2: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) )
            {
            // InternalSemver.g:92:2: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) )
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // InternalSemver.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    {
                    // InternalSemver.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    // InternalSemver.g:94:4: (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement
                    {
                    // InternalSemver.g:94:4: (this_WS_0= RULE_WS )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==RULE_WS) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // InternalSemver.g:95:5: this_WS_0= RULE_WS
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
                    // InternalSemver.g:113:3: ( ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* )
                    {
                    // InternalSemver.g:113:3: ( ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* )
                    // InternalSemver.g:114:4: ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )*
                    {
                    // InternalSemver.g:114:4: ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement )
                    int alt2=4;
                    alt2 = dfa2.predict(input);
                    switch (alt2) {
                        case 1 :
                            // InternalSemver.g:115:5: ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement )
                            {
                            // InternalSemver.g:115:5: ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement )
                            // InternalSemver.g:116:6: ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement
                            {
                            if ( state.backtracking==0 ) {

                              						/* */
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0());
                              					
                            }
                            pushFollow(FOLLOW_4);
                            this_LocalPathVersionRequirement_2=ruleLocalPathVersionRequirement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						current = this_LocalPathVersionRequirement_2;
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalSemver.g:130:5: this_URLVersionRequirement_3= ruleURLVersionRequirement
                            {
                            if ( state.backtracking==0 ) {

                              					/* */
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1());
                              				
                            }
                            pushFollow(FOLLOW_4);
                            this_URLVersionRequirement_3=ruleURLVersionRequirement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_URLVersionRequirement_3;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 3 :
                            // InternalSemver.g:142:5: this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement
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
                            // InternalSemver.g:154:5: this_TagVersionRequirement_5= ruleTagVersionRequirement
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

                    // InternalSemver.g:166:4: (this_WS_6= RULE_WS )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==RULE_WS) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // InternalSemver.g:167:5: this_WS_6= RULE_WS
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


    // $ANTLR start "entryRuleLocalPathVersionRequirement"
    // InternalSemver.g:177:1: entryRuleLocalPathVersionRequirement returns [EObject current=null] : iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF ;
    public final EObject entryRuleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalPathVersionRequirement = null;


        try {
            // InternalSemver.g:177:68: (iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF )
            // InternalSemver.g:178:2: iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF
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
    // InternalSemver.g:184:1: ruleLocalPathVersionRequirement returns [EObject current=null] : ( ( ( 'file:' )=>otherlv_0= 'file:' ) ( (lv_localPath_1_0= rulePATH ) ) ) ;
    public final EObject ruleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_localPath_1_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:190:2: ( ( ( ( 'file:' )=>otherlv_0= 'file:' ) ( (lv_localPath_1_0= rulePATH ) ) ) )
            // InternalSemver.g:191:2: ( ( ( 'file:' )=>otherlv_0= 'file:' ) ( (lv_localPath_1_0= rulePATH ) ) )
            {
            // InternalSemver.g:191:2: ( ( ( 'file:' )=>otherlv_0= 'file:' ) ( (lv_localPath_1_0= rulePATH ) ) )
            // InternalSemver.g:192:3: ( ( 'file:' )=>otherlv_0= 'file:' ) ( (lv_localPath_1_0= rulePATH ) )
            {
            // InternalSemver.g:192:3: ( ( 'file:' )=>otherlv_0= 'file:' )
            // InternalSemver.g:193:4: ( 'file:' )=>otherlv_0= 'file:'
            {
            otherlv_0=(Token)match(input,29,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_0, grammarAccess.getLocalPathVersionRequirementAccess().getFileKeyword_0());
              			
            }

            }

            // InternalSemver.g:199:3: ( (lv_localPath_1_0= rulePATH ) )
            // InternalSemver.g:200:4: (lv_localPath_1_0= rulePATH )
            {
            // InternalSemver.g:200:4: (lv_localPath_1_0= rulePATH )
            // InternalSemver.g:201:5: lv_localPath_1_0= rulePATH
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
              						"org.eclipse.n4js.semver.Semver.PATH");
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


    // $ANTLR start "entryRuleURLVersionRequirement"
    // InternalSemver.g:222:1: entryRuleURLVersionRequirement returns [EObject current=null] : iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF ;
    public final EObject entryRuleURLVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionRequirement = null;


        try {
            // InternalSemver.g:222:62: (iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF )
            // InternalSemver.g:223:2: iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF
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
    // InternalSemver.g:229:1: ruleURLVersionRequirement returns [EObject current=null] : ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) ( ( '://' )=>otherlv_1= '://' ) ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? ) ;
    public final EObject ruleURLVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_protocol_0_0 = null;

        AntlrDatatypeRuleToken lv_url_2_0 = null;

        EObject lv_versionSpecifier_4_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:235:2: ( ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) ( ( '://' )=>otherlv_1= '://' ) ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? ) )
            // InternalSemver.g:236:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) ( ( '://' )=>otherlv_1= '://' ) ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? )
            {
            // InternalSemver.g:236:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) ( ( '://' )=>otherlv_1= '://' ) ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )? )
            // InternalSemver.g:237:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) ( ( '://' )=>otherlv_1= '://' ) ( (lv_url_2_0= ruleURL ) ) (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )?
            {
            // InternalSemver.g:237:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) )
            // InternalSemver.g:238:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            {
            // InternalSemver.g:238:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            // InternalSemver.g:239:5: lv_protocol_0_0= ruleURL_PROTOCOL
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getProtocolURL_PROTOCOLParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_6);
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
              						"org.eclipse.n4js.semver.Semver.URL_PROTOCOL");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:256:3: ( ( '://' )=>otherlv_1= '://' )
            // InternalSemver.g:257:4: ( '://' )=>otherlv_1= '://'
            {
            otherlv_1=(Token)match(input,30,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getURLVersionRequirementAccess().getColonSolidusSolidusKeyword_1());
              			
            }

            }

            // InternalSemver.g:263:3: ( (lv_url_2_0= ruleURL ) )
            // InternalSemver.g:264:4: (lv_url_2_0= ruleURL )
            {
            // InternalSemver.g:264:4: (lv_url_2_0= ruleURL )
            // InternalSemver.g:265:5: lv_url_2_0= ruleURL
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_8);
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
              						"org.eclipse.n4js.semver.Semver.URL");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:282:3: (otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==31) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSemver.g:283:4: otherlv_3= '#' ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) )
                    {
                    otherlv_3=(Token)match(input,31,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0());
                      			
                    }
                    // InternalSemver.g:287:4: ( (lv_versionSpecifier_4_0= ruleURLVersionSpecifier ) )
                    // InternalSemver.g:288:5: (lv_versionSpecifier_4_0= ruleURLVersionSpecifier )
                    {
                    // InternalSemver.g:288:5: (lv_versionSpecifier_4_0= ruleURLVersionSpecifier )
                    // InternalSemver.g:289:6: lv_versionSpecifier_4_0= ruleURLVersionSpecifier
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
                      							"org.eclipse.n4js.semver.Semver.URLVersionSpecifier");
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
    // InternalSemver.g:311:1: entryRuleURLVersionSpecifier returns [EObject current=null] : iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF ;
    public final EObject entryRuleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionSpecifier = null;


        try {
            // InternalSemver.g:311:60: (iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF )
            // InternalSemver.g:312:2: iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF
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
    // InternalSemver.g:318:1: ruleURLVersionSpecifier returns [EObject current=null] : ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH ) ;
    public final EObject ruleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject this_URLSemver_0 = null;

        EObject this_URLCommitISH_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:324:2: ( ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH ) )
            // InternalSemver.g:325:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH )
            {
            // InternalSemver.g:325:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | this_URLCommitISH_1= ruleURLCommitISH )
            int alt6=2;
            switch ( input.LA(1) ) {
            case RULE_ASTERIX:
            case 32:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
                {
                alt6=1;
                }
                break;
            case RULE_LETTER_V:
                {
                switch ( input.LA(2) ) {
                case EOF:
                case RULE_WS:
                case RULE_LETTER_V:
                case RULE_LETTER_NO_VX:
                case 34:
                    {
                    alt6=2;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    int LA6_4 = input.LA(3);

                    if ( (synpred12_InternalSemver()) ) {
                        alt6=1;
                    }
                    else if ( (true) ) {
                        alt6=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    int LA6_3 = input.LA(3);

                    if ( (synpred12_InternalSemver()) ) {
                        alt6=1;
                    }
                    else if ( (true) ) {
                        alt6=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_ASTERIX:
                    {
                    alt6=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_LETTER_X:
                {
                int LA6_3 = input.LA(2);

                if ( (synpred12_InternalSemver()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_DIGITS:
                {
                int LA6_4 = input.LA(2);

                if ( (synpred12_InternalSemver()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 4, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LETTER_NO_VX:
            case 34:
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
                    // InternalSemver.g:326:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    {
                    // InternalSemver.g:326:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    // InternalSemver.g:327:4: ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver
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
                    // InternalSemver.g:341:3: this_URLCommitISH_1= ruleURLCommitISH
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
    // InternalSemver.g:356:1: entryRuleURLSemver returns [EObject current=null] : iv_ruleURLSemver= ruleURLSemver EOF ;
    public final EObject entryRuleURLSemver() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLSemver = null;


        try {
            // InternalSemver.g:356:50: (iv_ruleURLSemver= ruleURLSemver EOF )
            // InternalSemver.g:357:2: iv_ruleURLSemver= ruleURLSemver EOF
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
    // InternalSemver.g:363:1: ruleURLSemver returns [EObject current=null] : ( ( ( 'semver:' )=>otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) ) ;
    public final EObject ruleURLSemver() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_simpleVersion_1_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:369:2: ( ( ( ( 'semver:' )=>otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) ) )
            // InternalSemver.g:370:2: ( ( ( 'semver:' )=>otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) )
            {
            // InternalSemver.g:370:2: ( ( ( 'semver:' )=>otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) ) )
            // InternalSemver.g:371:3: ( ( 'semver:' )=>otherlv_0= 'semver:' )? ( (lv_simpleVersion_1_0= ruleSimpleVersion ) )
            {
            // InternalSemver.g:371:3: ( ( 'semver:' )=>otherlv_0= 'semver:' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==32) && (synpred13_InternalSemver())) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:372:4: ( 'semver:' )=>otherlv_0= 'semver:'
                    {
                    otherlv_0=(Token)match(input,32,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getURLSemverAccess().getSemverKeyword_0());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:378:3: ( (lv_simpleVersion_1_0= ruleSimpleVersion ) )
            // InternalSemver.g:379:4: (lv_simpleVersion_1_0= ruleSimpleVersion )
            {
            // InternalSemver.g:379:4: (lv_simpleVersion_1_0= ruleSimpleVersion )
            // InternalSemver.g:380:5: lv_simpleVersion_1_0= ruleSimpleVersion
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
              						"org.eclipse.n4js.semver.Semver.SimpleVersion");
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
    // InternalSemver.g:401:1: entryRuleURLCommitISH returns [EObject current=null] : iv_ruleURLCommitISH= ruleURLCommitISH EOF ;
    public final EObject entryRuleURLCommitISH() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLCommitISH = null;


        try {
            // InternalSemver.g:401:53: (iv_ruleURLCommitISH= ruleURLCommitISH EOF )
            // InternalSemver.g:402:2: iv_ruleURLCommitISH= ruleURLCommitISH EOF
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
    // InternalSemver.g:408:1: ruleURLCommitISH returns [EObject current=null] : ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) ) ;
    public final EObject ruleURLCommitISH() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_commitISH_0_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:414:2: ( ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) ) )
            // InternalSemver.g:415:2: ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) )
            {
            // InternalSemver.g:415:2: ( (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:416:3: (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:416:3: (lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:417:4: lv_commitISH_0_0= ruleALPHA_NUMERIC_CHARS
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
              					"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
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
    // InternalSemver.g:437:1: entryRuleTagVersionRequirement returns [EObject current=null] : iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF ;
    public final EObject entryRuleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTagVersionRequirement = null;


        try {
            // InternalSemver.g:437:62: (iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF )
            // InternalSemver.g:438:2: iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF
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
    // InternalSemver.g:444:1: ruleTagVersionRequirement returns [EObject current=null] : ( (lv_tagName_0_0= ruleTAG ) ) ;
    public final EObject ruleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_tagName_0_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:450:2: ( ( (lv_tagName_0_0= ruleTAG ) ) )
            // InternalSemver.g:451:2: ( (lv_tagName_0_0= ruleTAG ) )
            {
            // InternalSemver.g:451:2: ( (lv_tagName_0_0= ruleTAG ) )
            // InternalSemver.g:452:3: (lv_tagName_0_0= ruleTAG )
            {
            // InternalSemver.g:452:3: (lv_tagName_0_0= ruleTAG )
            // InternalSemver.g:453:4: lv_tagName_0_0= ruleTAG
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
              					"org.eclipse.n4js.semver.Semver.TAG");
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
    // InternalSemver.g:473:1: entryRuleGitHubVersionRequirement returns [EObject current=null] : iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF ;
    public final EObject entryRuleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGitHubVersionRequirement = null;


        try {
            // InternalSemver.g:473:65: (iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF )
            // InternalSemver.g:474:2: iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF
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
    // InternalSemver.g:480:1: ruleGitHubVersionRequirement returns [EObject current=null] : ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) ;
    public final EObject ruleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_githubUrl_0_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:486:2: ( ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) )
            // InternalSemver.g:487:2: ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            {
            // InternalSemver.g:487:2: ( ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            // InternalSemver.g:488:3: ( (lv_githubUrl_0_0= ruleURL ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            {
            // InternalSemver.g:488:3: ( (lv_githubUrl_0_0= ruleURL ) )
            // InternalSemver.g:489:4: (lv_githubUrl_0_0= ruleURL )
            {
            // InternalSemver.g:489:4: (lv_githubUrl_0_0= ruleURL )
            // InternalSemver.g:490:5: lv_githubUrl_0_0= ruleURL
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURLParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_8);
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
              						"org.eclipse.n4js.semver.Semver.URL");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:507:3: (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==31) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSemver.g:508:4: otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_1=(Token)match(input,31,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:512:4: ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSemver.g:513:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSemver.g:513:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSemver.g:514:6: lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS
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
                      							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
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


    // $ANTLR start "entryRuleVersionRangeSetRequirement"
    // InternalSemver.g:536:1: entryRuleVersionRangeSetRequirement returns [EObject current=null] : iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF ;
    public final EObject entryRuleVersionRangeSetRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeSetRequirement = null;


        try {
            // InternalSemver.g:536:67: (iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF )
            // InternalSemver.g:537:2: iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF
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
    // InternalSemver.g:543:1: ruleVersionRangeSetRequirement returns [EObject current=null] : ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? ) ;
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
            // InternalSemver.g:549:2: ( ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? ) )
            // InternalSemver.g:550:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? )
            {
            // InternalSemver.g:550:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? )
            // InternalSemver.g:551:3: () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )?
            {
            // InternalSemver.g:551:3: ()
            // InternalSemver.g:552:4: 
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

            // InternalSemver.g:561:3: ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=RULE_LETTER_V && LA13_0<=RULE_DIGITS)||(LA13_0>=RULE_LETTER_X && LA13_0<=RULE_ASTERIX)||(LA13_0>=40 && LA13_0<=46)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalSemver.g:562:4: ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )*
                    {
                    // InternalSemver.g:562:4: ( (lv_ranges_1_0= ruleVersionRange ) )
                    // InternalSemver.g:563:5: (lv_ranges_1_0= ruleVersionRange )
                    {
                    // InternalSemver.g:563:5: (lv_ranges_1_0= ruleVersionRange )
                    // InternalSemver.g:564:6: lv_ranges_1_0= ruleVersionRange
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
                      							"org.eclipse.n4js.semver.Semver.VersionRange");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSemver.g:581:4: ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )*
                    loop11:
                    do {
                        int alt11=2;
                        alt11 = dfa11.predict(input);
                        switch (alt11) {
                    	case 1 :
                    	    // InternalSemver.g:582:5: (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    {
                    	    // InternalSemver.g:582:5: (this_WS_2= RULE_WS )*
                    	    loop9:
                    	    do {
                    	        int alt9=2;
                    	        int LA9_0 = input.LA(1);

                    	        if ( (LA9_0==RULE_WS) ) {
                    	            alt9=1;
                    	        }


                    	        switch (alt9) {
                    	    	case 1 :
                    	    	    // InternalSemver.g:583:6: this_WS_2= RULE_WS
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

                    	    otherlv_3=(Token)match(input,33,FOLLOW_3); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1());
                    	      				
                    	    }
                    	    // InternalSemver.g:592:5: (this_WS_4= RULE_WS )*
                    	    loop10:
                    	    do {
                    	        int alt10=2;
                    	        int LA10_0 = input.LA(1);

                    	        if ( (LA10_0==RULE_WS) ) {
                    	            alt10=1;
                    	        }


                    	        switch (alt10) {
                    	    	case 1 :
                    	    	    // InternalSemver.g:593:6: this_WS_4= RULE_WS
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

                    	    // InternalSemver.g:598:5: ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    // InternalSemver.g:599:6: (lv_ranges_5_0= ruleVersionRange )
                    	    {
                    	    // InternalSemver.g:599:6: (lv_ranges_5_0= ruleVersionRange )
                    	    // InternalSemver.g:600:7: lv_ranges_5_0= ruleVersionRange
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
                    	      								"org.eclipse.n4js.semver.Semver.VersionRange");
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

                    // InternalSemver.g:618:4: (this_WS_6= RULE_WS )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==RULE_WS) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalSemver.g:619:5: this_WS_6= RULE_WS
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
    // InternalSemver.g:629:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // InternalSemver.g:629:53: (iv_ruleVersionRange= ruleVersionRange EOF )
            // InternalSemver.g:630:2: iv_ruleVersionRange= ruleVersionRange EOF
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
    // InternalSemver.g:636:1: ruleVersionRange returns [EObject current=null] : (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_VersionRangeContraint_0 = null;

        EObject this_HyphenVersionRange_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:642:2: ( (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) )
            // InternalSemver.g:643:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            {
            // InternalSemver.g:643:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            int alt14=2;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // InternalSemver.g:644:3: this_VersionRangeContraint_0= ruleVersionRangeContraint
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
                    // InternalSemver.g:656:3: this_HyphenVersionRange_1= ruleHyphenVersionRange
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
    // InternalSemver.g:671:1: entryRuleHyphenVersionRange returns [EObject current=null] : iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF ;
    public final EObject entryRuleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHyphenVersionRange = null;


        try {
            // InternalSemver.g:671:59: (iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF )
            // InternalSemver.g:672:2: iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF
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
    // InternalSemver.g:678:1: ruleHyphenVersionRange returns [EObject current=null] : ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Token otherlv_3=null;
        Token this_WS_4=null;
        EObject lv_from_1_0 = null;

        EObject lv_to_5_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:684:2: ( ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) )
            // InternalSemver.g:685:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            {
            // InternalSemver.g:685:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            // InternalSemver.g:686:3: () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) )
            {
            // InternalSemver.g:686:3: ()
            // InternalSemver.g:687:4: 
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

            // InternalSemver.g:696:3: ( (lv_from_1_0= ruleVersionNumber ) )
            // InternalSemver.g:697:4: (lv_from_1_0= ruleVersionNumber )
            {
            // InternalSemver.g:697:4: (lv_from_1_0= ruleVersionNumber )
            // InternalSemver.g:698:5: lv_from_1_0= ruleVersionNumber
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
              						"org.eclipse.n4js.semver.Semver.VersionNumber");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:715:3: (this_WS_2= RULE_WS )+
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
            	    // InternalSemver.g:716:4: this_WS_2= RULE_WS
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

            otherlv_3=(Token)match(input,34,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3());
              		
            }
            // InternalSemver.g:725:3: (this_WS_4= RULE_WS )+
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
            	    // InternalSemver.g:726:4: this_WS_4= RULE_WS
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

            // InternalSemver.g:731:3: ( (lv_to_5_0= ruleVersionNumber ) )
            // InternalSemver.g:732:4: (lv_to_5_0= ruleVersionNumber )
            {
            // InternalSemver.g:732:4: (lv_to_5_0= ruleVersionNumber )
            // InternalSemver.g:733:5: lv_to_5_0= ruleVersionNumber
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
              						"org.eclipse.n4js.semver.Semver.VersionNumber");
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
    // InternalSemver.g:754:1: entryRuleVersionRangeContraint returns [EObject current=null] : iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF ;
    public final EObject entryRuleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeContraint = null;


        try {
            // InternalSemver.g:754:62: (iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF )
            // InternalSemver.g:755:2: iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF
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
    // InternalSemver.g:761:1: ruleVersionRangeContraint returns [EObject current=null] : ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) ;
    public final EObject ruleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        EObject lv_versionConstraints_1_0 = null;

        EObject lv_versionConstraints_3_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:767:2: ( ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) )
            // InternalSemver.g:768:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            {
            // InternalSemver.g:768:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            // InternalSemver.g:769:3: () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            {
            // InternalSemver.g:769:3: ()
            // InternalSemver.g:770:4: 
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

            // InternalSemver.g:779:3: ( (lv_versionConstraints_1_0= ruleSimpleVersion ) )
            // InternalSemver.g:780:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            {
            // InternalSemver.g:780:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            // InternalSemver.g:781:5: lv_versionConstraints_1_0= ruleSimpleVersion
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
              						"org.eclipse.n4js.semver.Semver.SimpleVersion");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:798:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            loop18:
            do {
                int alt18=2;
                alt18 = dfa18.predict(input);
                switch (alt18) {
            	case 1 :
            	    // InternalSemver.g:799:4: (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    {
            	    // InternalSemver.g:799:4: (this_WS_2= RULE_WS )+
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
            	    	    // InternalSemver.g:800:5: this_WS_2= RULE_WS
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

            	    // InternalSemver.g:805:4: ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    // InternalSemver.g:806:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    {
            	    // InternalSemver.g:806:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    // InternalSemver.g:807:6: lv_versionConstraints_3_0= ruleSimpleVersion
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
            	      							"org.eclipse.n4js.semver.Semver.SimpleVersion");
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
    // InternalSemver.g:829:1: entryRuleSimpleVersion returns [EObject current=null] : iv_ruleSimpleVersion= ruleSimpleVersion EOF ;
    public final EObject entryRuleSimpleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleVersion = null;


        try {
            // InternalSemver.g:829:54: (iv_ruleSimpleVersion= ruleSimpleVersion EOF )
            // InternalSemver.g:830:2: iv_ruleSimpleVersion= ruleSimpleVersion EOF
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
    // InternalSemver.g:836:1: ruleSimpleVersion returns [EObject current=null] : ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_withLetterV_3_0= RULE_LETTER_V ) )? ( (lv_number_4_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleSimpleVersion() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Token lv_withLetterV_3_0=null;
        Enumerator lv_comparators_1_0 = null;

        EObject lv_number_4_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:842:2: ( ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_withLetterV_3_0= RULE_LETTER_V ) )? ( (lv_number_4_0= ruleVersionNumber ) ) ) )
            // InternalSemver.g:843:2: ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_withLetterV_3_0= RULE_LETTER_V ) )? ( (lv_number_4_0= ruleVersionNumber ) ) )
            {
            // InternalSemver.g:843:2: ( () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_withLetterV_3_0= RULE_LETTER_V ) )? ( (lv_number_4_0= ruleVersionNumber ) ) )
            // InternalSemver.g:844:3: () ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )* ( (lv_withLetterV_3_0= RULE_LETTER_V ) )? ( (lv_number_4_0= ruleVersionNumber ) )
            {
            // InternalSemver.g:844:3: ()
            // InternalSemver.g:845:4: 
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

            // InternalSemver.g:854:3: ( ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )* )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=40 && LA20_0<=46)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSemver.g:855:4: ( (lv_comparators_1_0= ruleVersionComparator ) ) (this_WS_2= RULE_WS )*
            	    {
            	    // InternalSemver.g:855:4: ( (lv_comparators_1_0= ruleVersionComparator ) )
            	    // InternalSemver.g:856:5: (lv_comparators_1_0= ruleVersionComparator )
            	    {
            	    // InternalSemver.g:856:5: (lv_comparators_1_0= ruleVersionComparator )
            	    // InternalSemver.g:857:6: lv_comparators_1_0= ruleVersionComparator
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
            	      							"org.eclipse.n4js.semver.Semver.VersionComparator");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }

            	    // InternalSemver.g:874:4: (this_WS_2= RULE_WS )*
            	    loop19:
            	    do {
            	        int alt19=2;
            	        int LA19_0 = input.LA(1);

            	        if ( (LA19_0==RULE_WS) ) {
            	            alt19=1;
            	        }


            	        switch (alt19) {
            	    	case 1 :
            	    	    // InternalSemver.g:875:5: this_WS_2= RULE_WS
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

            // InternalSemver.g:881:3: ( (lv_withLetterV_3_0= RULE_LETTER_V ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LETTER_V) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSemver.g:882:4: (lv_withLetterV_3_0= RULE_LETTER_V )
                    {
                    // InternalSemver.g:882:4: (lv_withLetterV_3_0= RULE_LETTER_V )
                    // InternalSemver.g:883:5: lv_withLetterV_3_0= RULE_LETTER_V
                    {
                    lv_withLetterV_3_0=(Token)match(input,RULE_LETTER_V,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_withLetterV_3_0, grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSimpleVersionRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"withLetterV",
                      						true,
                      						"org.eclipse.n4js.semver.Semver.LETTER_V");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSemver.g:899:3: ( (lv_number_4_0= ruleVersionNumber ) )
            // InternalSemver.g:900:4: (lv_number_4_0= ruleVersionNumber )
            {
            // InternalSemver.g:900:4: (lv_number_4_0= ruleVersionNumber )
            // InternalSemver.g:901:5: lv_number_4_0= ruleVersionNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_number_4_0=ruleVersionNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
              					}
              					set(
              						current,
              						"number",
              						lv_number_4_0,
              						"org.eclipse.n4js.semver.Semver.VersionNumber");
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
    // InternalSemver.g:922:1: entryRuleVersionNumber returns [EObject current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final EObject entryRuleVersionNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionNumber = null;


        try {
            // InternalSemver.g:922:54: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalSemver.g:923:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalSemver.g:929:1: ruleVersionNumber returns [EObject current=null] : ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) ;
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
            // InternalSemver.g:935:2: ( ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) )
            // InternalSemver.g:936:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            {
            // InternalSemver.g:936:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            // InternalSemver.g:937:3: ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )?
            {
            // InternalSemver.g:937:3: ( (lv_major_0_0= ruleVersionPart ) )
            // InternalSemver.g:938:4: (lv_major_0_0= ruleVersionPart )
            {
            // InternalSemver.g:938:4: (lv_major_0_0= ruleVersionPart )
            // InternalSemver.g:939:5: lv_major_0_0= ruleVersionPart
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
              						"org.eclipse.n4js.semver.Semver.VersionPart");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:956:3: (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==35) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSemver.g:957:4: otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:961:4: ( (lv_minor_2_0= ruleVersionPart ) )
                    // InternalSemver.g:962:5: (lv_minor_2_0= ruleVersionPart )
                    {
                    // InternalSemver.g:962:5: (lv_minor_2_0= ruleVersionPart )
                    // InternalSemver.g:963:6: lv_minor_2_0= ruleVersionPart
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
                      							"org.eclipse.n4js.semver.Semver.VersionPart");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalSemver.g:980:4: (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==35) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalSemver.g:981:5: otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            {
                            otherlv_3=(Token)match(input,35,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            // InternalSemver.g:985:5: ( (lv_patch_4_0= ruleVersionPart ) )
                            // InternalSemver.g:986:6: (lv_patch_4_0= ruleVersionPart )
                            {
                            // InternalSemver.g:986:6: (lv_patch_4_0= ruleVersionPart )
                            // InternalSemver.g:987:7: lv_patch_4_0= ruleVersionPart
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
                              								"org.eclipse.n4js.semver.Semver.VersionPart");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalSemver.g:1004:5: (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            loop22:
                            do {
                                int alt22=2;
                                int LA22_0 = input.LA(1);

                                if ( (LA22_0==35) ) {
                                    alt22=1;
                                }


                                switch (alt22) {
                            	case 1 :
                            	    // InternalSemver.g:1005:6: otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) )
                            	    {
                            	    otherlv_5=(Token)match(input,35,FOLLOW_10); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_5, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0());
                            	      					
                            	    }
                            	    // InternalSemver.g:1009:6: ( (lv_extended_6_0= ruleVersionPart ) )
                            	    // InternalSemver.g:1010:7: (lv_extended_6_0= ruleVersionPart )
                            	    {
                            	    // InternalSemver.g:1010:7: (lv_extended_6_0= ruleVersionPart )
                            	    // InternalSemver.g:1011:8: lv_extended_6_0= ruleVersionPart
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
                            	      									"org.eclipse.n4js.semver.Semver.VersionPart");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop22;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalSemver.g:1031:3: ( (lv_qualifier_7_0= ruleQualifier ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==34||LA25_0==36) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSemver.g:1032:4: (lv_qualifier_7_0= ruleQualifier )
                    {
                    // InternalSemver.g:1032:4: (lv_qualifier_7_0= ruleQualifier )
                    // InternalSemver.g:1033:5: lv_qualifier_7_0= ruleQualifier
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
                      						"org.eclipse.n4js.semver.Semver.Qualifier");
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
    // InternalSemver.g:1054:1: entryRuleVersionPart returns [EObject current=null] : iv_ruleVersionPart= ruleVersionPart EOF ;
    public final EObject entryRuleVersionPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionPart = null;


        try {
            // InternalSemver.g:1054:52: (iv_ruleVersionPart= ruleVersionPart EOF )
            // InternalSemver.g:1055:2: iv_ruleVersionPart= ruleVersionPart EOF
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
    // InternalSemver.g:1061:1: ruleVersionPart returns [EObject current=null] : ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) ;
    public final EObject ruleVersionPart() throws RecognitionException {
        EObject current = null;

        Token lv_numberRaw_1_0=null;
        AntlrDatatypeRuleToken lv_wildcard_0_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:1067:2: ( ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) )
            // InternalSemver.g:1068:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            {
            // InternalSemver.g:1068:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>=RULE_LETTER_X && LA26_0<=RULE_ASTERIX)) ) {
                alt26=1;
            }
            else if ( (LA26_0==RULE_DIGITS) ) {
                alt26=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // InternalSemver.g:1069:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    {
                    // InternalSemver.g:1069:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    // InternalSemver.g:1070:4: (lv_wildcard_0_0= ruleWILDCARD )
                    {
                    // InternalSemver.g:1070:4: (lv_wildcard_0_0= ruleWILDCARD )
                    // InternalSemver.g:1071:5: lv_wildcard_0_0= ruleWILDCARD
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
                      						"org.eclipse.n4js.semver.Semver.WILDCARD");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1089:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    {
                    // InternalSemver.g:1089:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    // InternalSemver.g:1090:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    {
                    // InternalSemver.g:1090:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    // InternalSemver.g:1091:5: lv_numberRaw_1_0= RULE_DIGITS
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
                      						"org.eclipse.n4js.semver.Semver.DIGITS");
                      				
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
    // InternalSemver.g:1111:1: entryRuleQualifier returns [EObject current=null] : iv_ruleQualifier= ruleQualifier EOF ;
    public final EObject entryRuleQualifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifier = null;


        try {
            // InternalSemver.g:1111:50: (iv_ruleQualifier= ruleQualifier EOF )
            // InternalSemver.g:1112:2: iv_ruleQualifier= ruleQualifier EOF
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
    // InternalSemver.g:1118:1: ruleQualifier returns [EObject current=null] : ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) ) ;
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
            // InternalSemver.g:1124:2: ( ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) ) )
            // InternalSemver.g:1125:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )
            {
            // InternalSemver.g:1125:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )
            int alt27=3;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalSemver.g:1126:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) )
                    {
                    // InternalSemver.g:1126:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) )
                    // InternalSemver.g:1127:4: otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) )
                    {
                    otherlv_0=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
                      			
                    }
                    // InternalSemver.g:1131:4: ( (lv_preRelease_1_0= ruleQualifierTag ) )
                    // InternalSemver.g:1132:5: (lv_preRelease_1_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1132:5: (lv_preRelease_1_0= ruleQualifierTag )
                    // InternalSemver.g:1133:6: lv_preRelease_1_0= ruleQualifierTag
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
                      							"org.eclipse.n4js.semver.Semver.QualifierTag");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1152:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )
                    {
                    // InternalSemver.g:1152:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )
                    // InternalSemver.g:1153:4: otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                    {
                    otherlv_2=(Token)match(input,36,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:1157:4: ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                    // InternalSemver.g:1158:5: (lv_buildMetadata_3_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1158:5: (lv_buildMetadata_3_0= ruleQualifierTag )
                    // InternalSemver.g:1159:6: lv_buildMetadata_3_0= ruleQualifierTag
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
                      							"org.eclipse.n4js.semver.Semver.QualifierTag");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1178:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) )
                    {
                    // InternalSemver.g:1178:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) )
                    // InternalSemver.g:1179:4: otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) )
                    {
                    otherlv_4=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0());
                      			
                    }
                    // InternalSemver.g:1183:4: ( (lv_preRelease_5_0= ruleQualifierTag ) )
                    // InternalSemver.g:1184:5: (lv_preRelease_5_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1184:5: (lv_preRelease_5_0= ruleQualifierTag )
                    // InternalSemver.g:1185:6: lv_preRelease_5_0= ruleQualifierTag
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
                      							"org.eclipse.n4js.semver.Semver.QualifierTag");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_6=(Token)match(input,36,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2());
                      			
                    }
                    // InternalSemver.g:1206:4: ( (lv_buildMetadata_7_0= ruleQualifierTag ) )
                    // InternalSemver.g:1207:5: (lv_buildMetadata_7_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1207:5: (lv_buildMetadata_7_0= ruleQualifierTag )
                    // InternalSemver.g:1208:6: lv_buildMetadata_7_0= ruleQualifierTag
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
                      							"org.eclipse.n4js.semver.Semver.QualifierTag");
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
    // InternalSemver.g:1230:1: entryRuleQualifierTag returns [EObject current=null] : iv_ruleQualifierTag= ruleQualifierTag EOF ;
    public final EObject entryRuleQualifierTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifierTag = null;


        try {
            // InternalSemver.g:1230:53: (iv_ruleQualifierTag= ruleQualifierTag EOF )
            // InternalSemver.g:1231:2: iv_ruleQualifierTag= ruleQualifierTag EOF
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
    // InternalSemver.g:1237:1: ruleQualifierTag returns [EObject current=null] : ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) ;
    public final EObject ruleQualifierTag() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_parts_0_0 = null;

        AntlrDatatypeRuleToken lv_parts_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:1243:2: ( ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) )
            // InternalSemver.g:1244:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            {
            // InternalSemver.g:1244:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            // InternalSemver.g:1245:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            {
            // InternalSemver.g:1245:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:1246:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:1246:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:1247:5: lv_parts_0_0= ruleALPHA_NUMERIC_CHARS
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
              						"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:1264:3: (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==35) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSemver.g:1265:4: otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    {
            	    otherlv_1=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    // InternalSemver.g:1269:4: ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    // InternalSemver.g:1270:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    {
            	    // InternalSemver.g:1270:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    // InternalSemver.g:1271:6: lv_parts_2_0= ruleALPHA_NUMERIC_CHARS
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
            	      							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
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


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:1293:1: entryRulePATH returns [String current=null] : iv_rulePATH= rulePATH EOF ;
    public final String entryRulePATH() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePATH = null;


        try {
            // InternalSemver.g:1293:44: (iv_rulePATH= rulePATH EOF )
            // InternalSemver.g:1294:2: iv_rulePATH= rulePATH EOF
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
    // InternalSemver.g:1300:1: rulePATH returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | kw= '/' | kw= '.' ) (kw= '/' | kw= '.' | kw= '-' | this_DIGITS_6= RULE_DIGITS | this_LETTER_V_7= RULE_LETTER_V | this_LETTER_X_8= RULE_LETTER_X | this_LETTER_NO_VX_9= RULE_LETTER_NO_VX )+ ) ;
    public final AntlrDatatypeRuleToken rulePATH() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_NO_VX_0=null;
        Token kw=null;
        Token this_DIGITS_6=null;
        Token this_LETTER_V_7=null;
        Token this_LETTER_X_8=null;
        Token this_LETTER_NO_VX_9=null;


        	enterRule();

        try {
            // InternalSemver.g:1306:2: ( ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | kw= '/' | kw= '.' ) (kw= '/' | kw= '.' | kw= '-' | this_DIGITS_6= RULE_DIGITS | this_LETTER_V_7= RULE_LETTER_V | this_LETTER_X_8= RULE_LETTER_X | this_LETTER_NO_VX_9= RULE_LETTER_NO_VX )+ ) )
            // InternalSemver.g:1307:2: ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | kw= '/' | kw= '.' ) (kw= '/' | kw= '.' | kw= '-' | this_DIGITS_6= RULE_DIGITS | this_LETTER_V_7= RULE_LETTER_V | this_LETTER_X_8= RULE_LETTER_X | this_LETTER_NO_VX_9= RULE_LETTER_NO_VX )+ )
            {
            // InternalSemver.g:1307:2: ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | kw= '/' | kw= '.' ) (kw= '/' | kw= '.' | kw= '-' | this_DIGITS_6= RULE_DIGITS | this_LETTER_V_7= RULE_LETTER_V | this_LETTER_X_8= RULE_LETTER_X | this_LETTER_NO_VX_9= RULE_LETTER_NO_VX )+ )
            // InternalSemver.g:1308:3: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | kw= '/' | kw= '.' ) (kw= '/' | kw= '.' | kw= '-' | this_DIGITS_6= RULE_DIGITS | this_LETTER_V_7= RULE_LETTER_V | this_LETTER_X_8= RULE_LETTER_X | this_LETTER_NO_VX_9= RULE_LETTER_NO_VX )+
            {
            // InternalSemver.g:1308:3: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | kw= '/' | kw= '.' )
            int alt29=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_NO_VX:
                {
                alt29=1;
                }
                break;
            case 37:
                {
                alt29=2;
                }
                break;
            case 35:
                {
                alt29=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // InternalSemver.g:1309:4: this_LETTER_NO_VX_0= RULE_LETTER_NO_VX
                    {
                    this_LETTER_NO_VX_0=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LETTER_NO_VX_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LETTER_NO_VX_0, grammarAccess.getPATHAccess().getLETTER_NO_VXTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1317:4: kw= '/'
                    {
                    kw=(Token)match(input,37,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getPATHAccess().getSolidusKeyword_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1323:4: kw= '.'
                    {
                    kw=(Token)match(input,35,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getPATHAccess().getFullStopKeyword_0_2());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1329:3: (kw= '/' | kw= '.' | kw= '-' | this_DIGITS_6= RULE_DIGITS | this_LETTER_V_7= RULE_LETTER_V | this_LETTER_X_8= RULE_LETTER_X | this_LETTER_NO_VX_9= RULE_LETTER_NO_VX )+
            int cnt30=0;
            loop30:
            do {
                int alt30=8;
                switch ( input.LA(1) ) {
                case 37:
                    {
                    alt30=1;
                    }
                    break;
                case 35:
                    {
                    alt30=2;
                    }
                    break;
                case 34:
                    {
                    alt30=3;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt30=4;
                    }
                    break;
                case RULE_LETTER_V:
                    {
                    alt30=5;
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    alt30=6;
                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    alt30=7;
                    }
                    break;

                }

                switch (alt30) {
            	case 1 :
            	    // InternalSemver.g:1330:4: kw= '/'
            	    {
            	    kw=(Token)match(input,37,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPATHAccess().getSolidusKeyword_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1336:4: kw= '.'
            	    {
            	    kw=(Token)match(input,35,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPATHAccess().getFullStopKeyword_1_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1342:4: kw= '-'
            	    {
            	    kw=(Token)match(input,34,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPATHAccess().getHyphenMinusKeyword_1_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1348:4: this_DIGITS_6= RULE_DIGITS
            	    {
            	    this_DIGITS_6=(Token)match(input,RULE_DIGITS,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_6);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_6, grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_1_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1356:4: this_LETTER_V_7= RULE_LETTER_V
            	    {
            	    this_LETTER_V_7=(Token)match(input,RULE_LETTER_V,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_V_7);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_V_7, grammarAccess.getPATHAccess().getLETTER_VTerminalRuleCall_1_4());
            	      			
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1364:4: this_LETTER_X_8= RULE_LETTER_X
            	    {
            	    this_LETTER_X_8=(Token)match(input,RULE_LETTER_X,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_X_8);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_X_8, grammarAccess.getPATHAccess().getLETTER_XTerminalRuleCall_1_5());
            	      			
            	    }

            	    }
            	    break;
            	case 7 :
            	    // InternalSemver.g:1372:4: this_LETTER_NO_VX_9= RULE_LETTER_NO_VX
            	    {
            	    this_LETTER_NO_VX_9=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_NO_VX_9);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_NO_VX_9, grammarAccess.getPATHAccess().getLETTER_NO_VXTerminalRuleCall_1_6());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt30 >= 1 ) break loop30;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(30, input);
                        throw eee;
                }
                cnt30++;
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


    // $ANTLR start "entryRuleURL_PROTOCOL"
    // InternalSemver.g:1384:1: entryRuleURL_PROTOCOL returns [String current=null] : iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF ;
    public final String entryRuleURL_PROTOCOL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL_PROTOCOL = null;


        try {
            // InternalSemver.g:1384:52: (iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF )
            // InternalSemver.g:1385:2: iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF
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
    // InternalSemver.g:1391:1: ruleURL_PROTOCOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (this_LETTER_V_1= RULE_LETTER_V | this_LETTER_X_2= RULE_LETTER_X | this_LETTER_NO_VX_3= RULE_LETTER_NO_VX | kw= '+' )+ ) ;
    public final AntlrDatatypeRuleToken ruleURL_PROTOCOL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_NO_VX_0=null;
        Token this_LETTER_V_1=null;
        Token this_LETTER_X_2=null;
        Token this_LETTER_NO_VX_3=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSemver.g:1397:2: ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (this_LETTER_V_1= RULE_LETTER_V | this_LETTER_X_2= RULE_LETTER_X | this_LETTER_NO_VX_3= RULE_LETTER_NO_VX | kw= '+' )+ ) )
            // InternalSemver.g:1398:2: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (this_LETTER_V_1= RULE_LETTER_V | this_LETTER_X_2= RULE_LETTER_X | this_LETTER_NO_VX_3= RULE_LETTER_NO_VX | kw= '+' )+ )
            {
            // InternalSemver.g:1398:2: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (this_LETTER_V_1= RULE_LETTER_V | this_LETTER_X_2= RULE_LETTER_X | this_LETTER_NO_VX_3= RULE_LETTER_NO_VX | kw= '+' )+ )
            // InternalSemver.g:1399:3: this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (this_LETTER_V_1= RULE_LETTER_V | this_LETTER_X_2= RULE_LETTER_X | this_LETTER_NO_VX_3= RULE_LETTER_NO_VX | kw= '+' )+
            {
            this_LETTER_NO_VX_0=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_NO_VX_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_NO_VX_0, grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXTerminalRuleCall_0());
              		
            }
            // InternalSemver.g:1406:3: (this_LETTER_V_1= RULE_LETTER_V | this_LETTER_X_2= RULE_LETTER_X | this_LETTER_NO_VX_3= RULE_LETTER_NO_VX | kw= '+' )+
            int cnt31=0;
            loop31:
            do {
                int alt31=5;
                switch ( input.LA(1) ) {
                case RULE_LETTER_V:
                    {
                    alt31=1;
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    alt31=2;
                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    alt31=3;
                    }
                    break;
                case 36:
                    {
                    alt31=4;
                    }
                    break;

                }

                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:1407:4: this_LETTER_V_1= RULE_LETTER_V
            	    {
            	    this_LETTER_V_1=(Token)match(input,RULE_LETTER_V,FOLLOW_21); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_V_1);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_V_1, grammarAccess.getURL_PROTOCOLAccess().getLETTER_VTerminalRuleCall_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1415:4: this_LETTER_X_2= RULE_LETTER_X
            	    {
            	    this_LETTER_X_2=(Token)match(input,RULE_LETTER_X,FOLLOW_21); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_X_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_X_2, grammarAccess.getURL_PROTOCOLAccess().getLETTER_XTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1423:4: this_LETTER_NO_VX_3= RULE_LETTER_NO_VX
            	    {
            	    this_LETTER_NO_VX_3=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_21); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_NO_VX_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_NO_VX_3, grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXTerminalRuleCall_1_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1431:4: kw= '+'
            	    {
            	    kw=(Token)match(input,36,FOLLOW_21); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_3());
            	      			
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


    // $ANTLR start "entryRuleURL"
    // InternalSemver.g:1441:1: entryRuleURL returns [String current=null] : iv_ruleURL= ruleURL EOF ;
    public final String entryRuleURL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL = null;


        try {
            // InternalSemver.g:1441:43: (iv_ruleURL= ruleURL EOF )
            // InternalSemver.g:1442:2: iv_ruleURL= ruleURL EOF
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
    // InternalSemver.g:1448:1: ruleURL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_V_3= RULE_LETTER_V | this_LETTER_X_4= RULE_LETTER_X | this_LETTER_NO_VX_5= RULE_LETTER_NO_VX )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | this_DIGITS_15= RULE_DIGITS | this_LETTER_V_16= RULE_LETTER_V | this_LETTER_X_17= RULE_LETTER_X | this_LETTER_NO_VX_18= RULE_LETTER_NO_VX )* ) ;
    public final AntlrDatatypeRuleToken ruleURL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_NO_VX_0=null;
        Token kw=null;
        Token this_DIGITS_2=null;
        Token this_LETTER_V_3=null;
        Token this_LETTER_X_4=null;
        Token this_LETTER_NO_VX_5=null;
        Token this_DIGITS_15=null;
        Token this_LETTER_V_16=null;
        Token this_LETTER_X_17=null;
        Token this_LETTER_NO_VX_18=null;


        	enterRule();

        try {
            // InternalSemver.g:1454:2: ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_V_3= RULE_LETTER_V | this_LETTER_X_4= RULE_LETTER_X | this_LETTER_NO_VX_5= RULE_LETTER_NO_VX )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | this_DIGITS_15= RULE_DIGITS | this_LETTER_V_16= RULE_LETTER_V | this_LETTER_X_17= RULE_LETTER_X | this_LETTER_NO_VX_18= RULE_LETTER_NO_VX )* ) )
            // InternalSemver.g:1455:2: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_V_3= RULE_LETTER_V | this_LETTER_X_4= RULE_LETTER_X | this_LETTER_NO_VX_5= RULE_LETTER_NO_VX )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | this_DIGITS_15= RULE_DIGITS | this_LETTER_V_16= RULE_LETTER_V | this_LETTER_X_17= RULE_LETTER_X | this_LETTER_NO_VX_18= RULE_LETTER_NO_VX )* )
            {
            // InternalSemver.g:1455:2: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_V_3= RULE_LETTER_V | this_LETTER_X_4= RULE_LETTER_X | this_LETTER_NO_VX_5= RULE_LETTER_NO_VX )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | this_DIGITS_15= RULE_DIGITS | this_LETTER_V_16= RULE_LETTER_V | this_LETTER_X_17= RULE_LETTER_X | this_LETTER_NO_VX_18= RULE_LETTER_NO_VX )* )
            // InternalSemver.g:1456:3: this_LETTER_NO_VX_0= RULE_LETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_V_3= RULE_LETTER_V | this_LETTER_X_4= RULE_LETTER_X | this_LETTER_NO_VX_5= RULE_LETTER_NO_VX )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | this_DIGITS_15= RULE_DIGITS | this_LETTER_V_16= RULE_LETTER_V | this_LETTER_X_17= RULE_LETTER_X | this_LETTER_NO_VX_18= RULE_LETTER_NO_VX )*
            {
            this_LETTER_NO_VX_0=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_NO_VX_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_NO_VX_0, grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_0());
              		
            }
            // InternalSemver.g:1463:3: (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_V_3= RULE_LETTER_V | this_LETTER_X_4= RULE_LETTER_X | this_LETTER_NO_VX_5= RULE_LETTER_NO_VX )*
            loop32:
            do {
                int alt32=6;
                switch ( input.LA(1) ) {
                case 34:
                    {
                    alt32=1;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt32=2;
                    }
                    break;
                case RULE_LETTER_V:
                    {
                    alt32=3;
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    alt32=4;
                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    alt32=5;
                    }
                    break;

                }

                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:1464:4: kw= '-'
            	    {
            	    kw=(Token)match(input,34,FOLLOW_22); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getHyphenMinusKeyword_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1470:4: this_DIGITS_2= RULE_DIGITS
            	    {
            	    this_DIGITS_2=(Token)match(input,RULE_DIGITS,FOLLOW_22); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_2, grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1478:4: this_LETTER_V_3= RULE_LETTER_V
            	    {
            	    this_LETTER_V_3=(Token)match(input,RULE_LETTER_V,FOLLOW_22); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_V_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_V_3, grammarAccess.getURLAccess().getLETTER_VTerminalRuleCall_1_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1486:4: this_LETTER_X_4= RULE_LETTER_X
            	    {
            	    this_LETTER_X_4=(Token)match(input,RULE_LETTER_X,FOLLOW_22); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_X_4);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_X_4, grammarAccess.getURLAccess().getLETTER_XTerminalRuleCall_1_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1494:4: this_LETTER_NO_VX_5= RULE_LETTER_NO_VX
            	    {
            	    this_LETTER_NO_VX_5=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_22); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_NO_VX_5);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_NO_VX_5, grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_1_4());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

            // InternalSemver.g:1502:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' )
            int alt33=4;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt33=1;
                }
                break;
            case 35:
                {
                alt33=2;
                }
                break;
            case 38:
                {
                alt33=3;
                }
                break;
            case 39:
                {
                alt33=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }

            switch (alt33) {
                case 1 :
                    // InternalSemver.g:1503:4: kw= '/'
                    {
                    kw=(Token)match(input,37,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_2_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1509:4: kw= '.'
                    {
                    kw=(Token)match(input,35,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_2_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1515:4: kw= ':'
                    {
                    kw=(Token)match(input,38,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_2_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSemver.g:1521:4: kw= '@'
                    {
                    kw=(Token)match(input,39,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_2_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1527:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | this_DIGITS_15= RULE_DIGITS | this_LETTER_V_16= RULE_LETTER_V | this_LETTER_X_17= RULE_LETTER_X | this_LETTER_NO_VX_18= RULE_LETTER_NO_VX )*
            loop34:
            do {
                int alt34=10;
                switch ( input.LA(1) ) {
                case 37:
                    {
                    alt34=1;
                    }
                    break;
                case 35:
                    {
                    alt34=2;
                    }
                    break;
                case 38:
                    {
                    alt34=3;
                    }
                    break;
                case 39:
                    {
                    alt34=4;
                    }
                    break;
                case 34:
                    {
                    alt34=5;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt34=6;
                    }
                    break;
                case RULE_LETTER_V:
                    {
                    alt34=7;
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    alt34=8;
                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    alt34=9;
                    }
                    break;

                }

                switch (alt34) {
            	case 1 :
            	    // InternalSemver.g:1528:4: kw= '/'
            	    {
            	    kw=(Token)match(input,37,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_3_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1534:4: kw= '.'
            	    {
            	    kw=(Token)match(input,35,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_3_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1540:4: kw= ':'
            	    {
            	    kw=(Token)match(input,38,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_3_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1546:4: kw= '@'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_3_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1552:4: kw= '-'
            	    {
            	    kw=(Token)match(input,34,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getHyphenMinusKeyword_3_4());
            	      			
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1558:4: this_DIGITS_15= RULE_DIGITS
            	    {
            	    this_DIGITS_15=(Token)match(input,RULE_DIGITS,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_15);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_15, grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_3_5());
            	      			
            	    }

            	    }
            	    break;
            	case 7 :
            	    // InternalSemver.g:1566:4: this_LETTER_V_16= RULE_LETTER_V
            	    {
            	    this_LETTER_V_16=(Token)match(input,RULE_LETTER_V,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_V_16);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_V_16, grammarAccess.getURLAccess().getLETTER_VTerminalRuleCall_3_6());
            	      			
            	    }

            	    }
            	    break;
            	case 8 :
            	    // InternalSemver.g:1574:4: this_LETTER_X_17= RULE_LETTER_X
            	    {
            	    this_LETTER_X_17=(Token)match(input,RULE_LETTER_X,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_X_17);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_X_17, grammarAccess.getURLAccess().getLETTER_XTerminalRuleCall_3_7());
            	      			
            	    }

            	    }
            	    break;
            	case 9 :
            	    // InternalSemver.g:1582:4: this_LETTER_NO_VX_18= RULE_LETTER_NO_VX
            	    {
            	    this_LETTER_NO_VX_18=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_NO_VX_18);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_NO_VX_18, grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_3_8());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop34;
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
    // $ANTLR end "ruleURL"


    // $ANTLR start "entryRuleTAG"
    // InternalSemver.g:1594:1: entryRuleTAG returns [String current=null] : iv_ruleTAG= ruleTAG EOF ;
    public final String entryRuleTAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTAG = null;


        try {
            // InternalSemver.g:1594:43: (iv_ruleTAG= ruleTAG EOF )
            // InternalSemver.g:1595:2: iv_ruleTAG= ruleTAG EOF
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
    // InternalSemver.g:1601:1: ruleTAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | this_LETTER_X_1= RULE_LETTER_X ) (kw= '-' | this_DIGITS_3= RULE_DIGITS | this_LETTER_V_4= RULE_LETTER_V | this_LETTER_X_5= RULE_LETTER_X | this_LETTER_NO_VX_6= RULE_LETTER_NO_VX )+ ) ;
    public final AntlrDatatypeRuleToken ruleTAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_NO_VX_0=null;
        Token this_LETTER_X_1=null;
        Token kw=null;
        Token this_DIGITS_3=null;
        Token this_LETTER_V_4=null;
        Token this_LETTER_X_5=null;
        Token this_LETTER_NO_VX_6=null;


        	enterRule();

        try {
            // InternalSemver.g:1607:2: ( ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | this_LETTER_X_1= RULE_LETTER_X ) (kw= '-' | this_DIGITS_3= RULE_DIGITS | this_LETTER_V_4= RULE_LETTER_V | this_LETTER_X_5= RULE_LETTER_X | this_LETTER_NO_VX_6= RULE_LETTER_NO_VX )+ ) )
            // InternalSemver.g:1608:2: ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | this_LETTER_X_1= RULE_LETTER_X ) (kw= '-' | this_DIGITS_3= RULE_DIGITS | this_LETTER_V_4= RULE_LETTER_V | this_LETTER_X_5= RULE_LETTER_X | this_LETTER_NO_VX_6= RULE_LETTER_NO_VX )+ )
            {
            // InternalSemver.g:1608:2: ( (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | this_LETTER_X_1= RULE_LETTER_X ) (kw= '-' | this_DIGITS_3= RULE_DIGITS | this_LETTER_V_4= RULE_LETTER_V | this_LETTER_X_5= RULE_LETTER_X | this_LETTER_NO_VX_6= RULE_LETTER_NO_VX )+ )
            // InternalSemver.g:1609:3: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | this_LETTER_X_1= RULE_LETTER_X ) (kw= '-' | this_DIGITS_3= RULE_DIGITS | this_LETTER_V_4= RULE_LETTER_V | this_LETTER_X_5= RULE_LETTER_X | this_LETTER_NO_VX_6= RULE_LETTER_NO_VX )+
            {
            // InternalSemver.g:1609:3: (this_LETTER_NO_VX_0= RULE_LETTER_NO_VX | this_LETTER_X_1= RULE_LETTER_X )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_LETTER_NO_VX) ) {
                alt35=1;
            }
            else if ( (LA35_0==RULE_LETTER_X) ) {
                alt35=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // InternalSemver.g:1610:4: this_LETTER_NO_VX_0= RULE_LETTER_NO_VX
                    {
                    this_LETTER_NO_VX_0=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_24); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LETTER_NO_VX_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LETTER_NO_VX_0, grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1618:4: this_LETTER_X_1= RULE_LETTER_X
                    {
                    this_LETTER_X_1=(Token)match(input,RULE_LETTER_X,FOLLOW_24); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LETTER_X_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LETTER_X_1, grammarAccess.getTAGAccess().getLETTER_XTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1626:3: (kw= '-' | this_DIGITS_3= RULE_DIGITS | this_LETTER_V_4= RULE_LETTER_V | this_LETTER_X_5= RULE_LETTER_X | this_LETTER_NO_VX_6= RULE_LETTER_NO_VX )+
            int cnt36=0;
            loop36:
            do {
                int alt36=6;
                switch ( input.LA(1) ) {
                case 34:
                    {
                    alt36=1;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt36=2;
                    }
                    break;
                case RULE_LETTER_V:
                    {
                    alt36=3;
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    alt36=4;
                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    alt36=5;
                    }
                    break;

                }

                switch (alt36) {
            	case 1 :
            	    // InternalSemver.g:1627:4: kw= '-'
            	    {
            	    kw=(Token)match(input,34,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1633:4: this_DIGITS_3= RULE_DIGITS
            	    {
            	    this_DIGITS_3=(Token)match(input,RULE_DIGITS,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_3, grammarAccess.getTAGAccess().getDIGITSTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1641:4: this_LETTER_V_4= RULE_LETTER_V
            	    {
            	    this_LETTER_V_4=(Token)match(input,RULE_LETTER_V,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_V_4);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_V_4, grammarAccess.getTAGAccess().getLETTER_VTerminalRuleCall_1_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1649:4: this_LETTER_X_5= RULE_LETTER_X
            	    {
            	    this_LETTER_X_5=(Token)match(input,RULE_LETTER_X,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_X_5);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_X_5, grammarAccess.getTAGAccess().getLETTER_XTerminalRuleCall_1_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1657:4: this_LETTER_NO_VX_6= RULE_LETTER_NO_VX
            	    {
            	    this_LETTER_NO_VX_6=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_NO_VX_6);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LETTER_NO_VX_6, grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_1_4());
            	      			
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
    // $ANTLR end "ruleTAG"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSemver.g:1669:1: entryRuleALPHA_NUMERIC_CHARS returns [String current=null] : iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF ;
    public final String entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHARS = null;


        try {
            // InternalSemver.g:1669:59: (iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:1670:2: iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:1676:1: ruleALPHA_NUMERIC_CHARS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_V_2= RULE_LETTER_V | this_LETTER_X_3= RULE_LETTER_X | this_LETTER_NO_VX_4= RULE_LETTER_NO_VX )+ ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHARS() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_1=null;
        Token this_LETTER_V_2=null;
        Token this_LETTER_X_3=null;
        Token this_LETTER_NO_VX_4=null;


        	enterRule();

        try {
            // InternalSemver.g:1682:2: ( (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_V_2= RULE_LETTER_V | this_LETTER_X_3= RULE_LETTER_X | this_LETTER_NO_VX_4= RULE_LETTER_NO_VX )+ )
            // InternalSemver.g:1683:2: (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_V_2= RULE_LETTER_V | this_LETTER_X_3= RULE_LETTER_X | this_LETTER_NO_VX_4= RULE_LETTER_NO_VX )+
            {
            // InternalSemver.g:1683:2: (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_V_2= RULE_LETTER_V | this_LETTER_X_3= RULE_LETTER_X | this_LETTER_NO_VX_4= RULE_LETTER_NO_VX )+
            int cnt37=0;
            loop37:
            do {
                int alt37=6;
                switch ( input.LA(1) ) {
                case 34:
                    {
                    alt37=1;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt37=2;
                    }
                    break;
                case RULE_LETTER_V:
                    {
                    alt37=3;
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    alt37=4;
                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    alt37=5;
                    }
                    break;

                }

                switch (alt37) {
            	case 1 :
            	    // InternalSemver.g:1684:3: kw= '-'
            	    {
            	    kw=(Token)match(input,34,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0());
            	      		
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1690:3: this_DIGITS_1= RULE_DIGITS
            	    {
            	    this_DIGITS_1=(Token)match(input,RULE_DIGITS,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_DIGITS_1);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_DIGITS_1, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getDIGITSTerminalRuleCall_1());
            	      		
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1698:3: this_LETTER_V_2= RULE_LETTER_V
            	    {
            	    this_LETTER_V_2=(Token)match(input,RULE_LETTER_V,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_LETTER_V_2);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_LETTER_V_2, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_VTerminalRuleCall_2());
            	      		
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1706:3: this_LETTER_X_3= RULE_LETTER_X
            	    {
            	    this_LETTER_X_3=(Token)match(input,RULE_LETTER_X,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_LETTER_X_3);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_LETTER_X_3, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_XTerminalRuleCall_3());
            	      		
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1714:3: this_LETTER_NO_VX_4= RULE_LETTER_NO_VX
            	    {
            	    this_LETTER_NO_VX_4=(Token)match(input,RULE_LETTER_NO_VX,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_LETTER_NO_VX_4);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_LETTER_NO_VX_4, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_NO_VXTerminalRuleCall_4());
            	      		
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


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSemver.g:1725:1: entryRuleWILDCARD returns [String current=null] : iv_ruleWILDCARD= ruleWILDCARD EOF ;
    public final String entryRuleWILDCARD() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleWILDCARD = null;


        try {
            // InternalSemver.g:1725:48: (iv_ruleWILDCARD= ruleWILDCARD EOF )
            // InternalSemver.g:1726:2: iv_ruleWILDCARD= ruleWILDCARD EOF
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
    // InternalSemver.g:1732:1: ruleWILDCARD returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX ) ;
    public final AntlrDatatypeRuleToken ruleWILDCARD() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_X_0=null;
        Token this_ASTERIX_1=null;


        	enterRule();

        try {
            // InternalSemver.g:1738:2: ( (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX ) )
            // InternalSemver.g:1739:2: (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX )
            {
            // InternalSemver.g:1739:2: (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_LETTER_X) ) {
                alt38=1;
            }
            else if ( (LA38_0==RULE_ASTERIX) ) {
                alt38=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // InternalSemver.g:1740:3: this_LETTER_X_0= RULE_LETTER_X
                    {
                    this_LETTER_X_0=(Token)match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_X_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_X_0, grammarAccess.getWILDCARDAccess().getLETTER_XTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1748:3: this_ASTERIX_1= RULE_ASTERIX
                    {
                    this_ASTERIX_1=(Token)match(input,RULE_ASTERIX,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ASTERIX_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ASTERIX_1, grammarAccess.getWILDCARDAccess().getASTERIXTerminalRuleCall_1());
                      		
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


    // $ANTLR start "ruleVersionComparator"
    // InternalSemver.g:1759:1: ruleVersionComparator returns [Enumerator current=null] : ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) ) ;
    public final Enumerator ruleVersionComparator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;


        	enterRule();

        try {
            // InternalSemver.g:1765:2: ( ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) ) )
            // InternalSemver.g:1766:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) )
            {
            // InternalSemver.g:1766:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) )
            int alt39=7;
            switch ( input.LA(1) ) {
            case 40:
                {
                alt39=1;
                }
                break;
            case 41:
                {
                alt39=2;
                }
                break;
            case 42:
                {
                alt39=3;
                }
                break;
            case 43:
                {
                alt39=4;
                }
                break;
            case 44:
                {
                alt39=5;
                }
                break;
            case 45:
                {
                alt39=6;
                }
                break;
            case 46:
                {
                alt39=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // InternalSemver.g:1767:3: (enumLiteral_0= '=' )
                    {
                    // InternalSemver.g:1767:3: (enumLiteral_0= '=' )
                    // InternalSemver.g:1768:4: enumLiteral_0= '='
                    {
                    enumLiteral_0=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1775:3: (enumLiteral_1= '<' )
                    {
                    // InternalSemver.g:1775:3: (enumLiteral_1= '<' )
                    // InternalSemver.g:1776:4: enumLiteral_1= '<'
                    {
                    enumLiteral_1=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1783:3: (enumLiteral_2= '~' )
                    {
                    // InternalSemver.g:1783:3: (enumLiteral_2= '~' )
                    // InternalSemver.g:1784:4: enumLiteral_2= '~'
                    {
                    enumLiteral_2=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1791:3: (enumLiteral_3= '^' )
                    {
                    // InternalSemver.g:1791:3: (enumLiteral_3= '^' )
                    // InternalSemver.g:1792:4: enumLiteral_3= '^'
                    {
                    enumLiteral_3=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1799:3: (enumLiteral_4= '<=' )
                    {
                    // InternalSemver.g:1799:3: (enumLiteral_4= '<=' )
                    // InternalSemver.g:1800:4: enumLiteral_4= '<='
                    {
                    enumLiteral_4=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1807:3: (enumLiteral_5= '>' )
                    {
                    // InternalSemver.g:1807:3: (enumLiteral_5= '>' )
                    // InternalSemver.g:1808:4: enumLiteral_5= '>'
                    {
                    enumLiteral_5=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_5, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5());
                      			
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1815:3: (enumLiteral_6= '>=' )
                    {
                    // InternalSemver.g:1815:3: (enumLiteral_6= '>=' )
                    // InternalSemver.g:1816:4: enumLiteral_6= '>='
                    {
                    enumLiteral_6=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_6, grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6());
                      			
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

    // $ANTLR start synpred2_InternalSemver
    public final void synpred2_InternalSemver_fragment() throws RecognitionException {   
        Token this_WS_0=null;
        EObject this_VersionRangeSetRequirement_1 = null;


        // InternalSemver.g:93:3: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) )
        // InternalSemver.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
        {
        // InternalSemver.g:93:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
        // InternalSemver.g:94:4: (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement
        {
        // InternalSemver.g:94:4: (this_WS_0= RULE_WS )*
        loop40:
        do {
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==RULE_WS) ) {
                alt40=1;
            }


            switch (alt40) {
        	case 1 :
        	    // InternalSemver.g:95:5: this_WS_0= RULE_WS
        	    {
        	    this_WS_0=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop40;
            }
        } while (true);

        pushFollow(FOLLOW_2);
        this_VersionRangeSetRequirement_1=ruleVersionRangeSetRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred2_InternalSemver

    // $ANTLR start synpred12_InternalSemver
    public final void synpred12_InternalSemver_fragment() throws RecognitionException {   
        EObject this_URLSemver_0 = null;


        // InternalSemver.g:326:3: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) )
        // InternalSemver.g:326:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
        {
        // InternalSemver.g:326:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
        // InternalSemver.g:327:4: ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver
        {
        pushFollow(FOLLOW_2);
        this_URLSemver_0=ruleURLSemver();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred12_InternalSemver

    // $ANTLR start synpred13_InternalSemver
    public final void synpred13_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:372:4: ( 'semver:' )
        // InternalSemver.g:372:5: 'semver:'
        {
        match(input,32,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_InternalSemver

    // Delegated rules

    public final boolean synpred2_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA27 dfa27 = new DFA27(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\1\1\1\uffff\1\1\1\uffff\1\3\5\uffff";
    static final String dfa_3s = "\1\4\1\uffff\1\4\1\uffff\1\4\5\0";
    static final String dfa_4s = "\1\56\1\uffff\1\44\1\uffff\1\42\5\0";
    static final String dfa_5s = "\1\uffff\1\1\1\uffff\1\2\6\uffff";
    static final String dfa_6s = "\5\uffff\1\0\1\3\1\1\1\4\1\2}>";
    static final String[] dfa_7s = {
            "\3\1\1\3\1\2\1\1\23\uffff\1\3\12\uffff\7\1",
            "",
            "\1\1\4\3\30\uffff\1\1\1\4\2\1",
            "",
            "\1\3\1\7\1\6\1\11\1\10\31\uffff\1\5",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff"
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
            return "92:2: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) (this_WS_6= RULE_WS )* ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_5 = input.LA(1);

                         
                        int index4_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index4_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index4_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_9 = input.LA(1);

                         
                        int index4_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index4_9);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_6 = input.LA(1);

                         
                        int index4_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index4_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index4_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 4, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\16\uffff";
    static final String dfa_9s = "\4\uffff\5\3\2\uffff\3\3";
    static final String dfa_10s = "\1\7\1\uffff\1\5\1\uffff\5\4\2\uffff\3\4";
    static final String dfa_11s = "\1\35\1\uffff\1\47\1\uffff\5\47\2\uffff\3\47";
    static final String dfa_12s = "\1\uffff\1\1\1\uffff\1\4\5\uffff\1\3\1\2\3\uffff";
    static final String dfa_13s = "\16\uffff}>";
    static final String[] dfa_14s = {
            "\1\2\1\3\24\uffff\1\1",
            "",
            "\1\6\1\5\1\10\1\7\31\uffff\1\4\1\11\1\12\3\11",
            "",
            "\1\3\1\13\1\5\1\15\1\14\31\uffff\1\4\1\11\1\uffff\3\11",
            "\1\3\1\13\1\5\1\15\1\14\31\uffff\1\4\1\11\1\uffff\3\11",
            "\1\3\1\6\1\5\1\10\1\7\25\uffff\1\12\3\uffff\1\4\1\11\1\12\3\11",
            "\1\3\1\6\1\5\1\10\1\7\25\uffff\1\12\3\uffff\1\4\1\11\1\12\3\11",
            "\1\3\1\6\1\5\1\10\1\7\25\uffff\1\12\3\uffff\1\4\1\11\1\12\3\11",
            "",
            "",
            "\1\3\1\13\1\5\1\15\1\14\31\uffff\1\4\1\11\1\uffff\3\11",
            "\1\3\1\13\1\5\1\15\1\14\31\uffff\1\4\1\11\1\uffff\3\11",
            "\1\3\1\13\1\5\1\15\1\14\31\uffff\1\4\1\11\1\uffff\3\11"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "114:4: ( ( ( 'file:' )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | this_URLVersionRequirement_3= ruleURLVersionRequirement | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement )";
        }
    }
    static final String dfa_15s = "\4\uffff";
    static final String dfa_16s = "\2\2\2\uffff";
    static final String dfa_17s = "\2\4\2\uffff";
    static final String dfa_18s = "\2\41\2\uffff";
    static final String dfa_19s = "\2\uffff\1\2\1\1";
    static final String dfa_20s = "\4\uffff}>";
    static final String[] dfa_21s = {
            "\1\1\34\uffff\1\3",
            "\1\1\34\uffff\1\3",
            "",
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "()* loopback of 581:4: ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )*";
        }
    }
    static final String dfa_22s = "\67\uffff";
    static final String dfa_23s = "\2\uffff\3\1\3\uffff\16\1\5\uffff\22\1\2\uffff\10\1";
    static final String dfa_24s = "\1\5\1\uffff\3\4\1\6\2\5\16\4\1\uffff\1\6\3\5\22\4\1\6\1\5\10\4";
    static final String dfa_25s = "\1\56\1\uffff\3\44\1\11\2\42\1\56\10\44\5\43\1\uffff\1\11\3\42\10\44\12\43\1\11\1\42\3\44\5\43";
    static final String dfa_26s = "\1\uffff\1\1\24\uffff\1\2\40\uffff";
    static final String dfa_27s = "\67\uffff}>";
    static final String[] dfa_28s = {
            "\1\1\1\4\1\uffff\1\2\1\3\36\uffff\7\1",
            "",
            "\1\10\34\uffff\1\1\1\6\1\5\1\7",
            "\1\10\34\uffff\1\1\1\6\1\5\1\7",
            "\1\10\34\uffff\1\1\1\6\1\5\1\7",
            "\1\13\1\uffff\1\11\1\12",
            "\1\16\1\15\1\20\1\17\31\uffff\1\14",
            "\1\23\1\22\1\25\1\24\31\uffff\1\21",
            "\1\10\2\1\1\uffff\2\1\27\uffff\1\1\1\26\5\uffff\7\1",
            "\1\10\34\uffff\1\1\1\6\1\27\1\7",
            "\1\10\34\uffff\1\1\1\6\1\27\1\7",
            "\1\10\34\uffff\1\1\1\6\1\27\1\7",
            "\1\10\1\16\1\15\1\20\1\17\30\uffff\1\1\1\14\1\30\1\31",
            "\1\10\1\16\1\15\1\20\1\17\30\uffff\1\1\1\14\1\30\1\31",
            "\1\10\1\16\1\15\1\20\1\17\30\uffff\1\1\1\14\1\30\1\31",
            "\1\10\1\16\1\15\1\20\1\17\30\uffff\1\1\1\14\1\30\1\31",
            "\1\10\1\16\1\15\1\20\1\17\30\uffff\1\1\1\14\1\30\1\31",
            "\1\10\1\23\1\22\1\25\1\24\30\uffff\1\1\1\21\1\32",
            "\1\10\1\23\1\22\1\25\1\24\30\uffff\1\1\1\21\1\32",
            "\1\10\1\23\1\22\1\25\1\24\30\uffff\1\1\1\21\1\32",
            "\1\10\1\23\1\22\1\25\1\24\30\uffff\1\1\1\21\1\32",
            "\1\10\1\23\1\22\1\25\1\24\30\uffff\1\1\1\21\1\32",
            "",
            "\1\35\1\uffff\1\33\1\34",
            "\1\40\1\37\1\42\1\41\31\uffff\1\36",
            "\1\45\1\44\1\47\1\46\31\uffff\1\43",
            "\1\52\1\51\1\54\1\53\31\uffff\1\50",
            "\1\10\34\uffff\1\1\1\6\1\55\1\7",
            "\1\10\34\uffff\1\1\1\6\1\55\1\7",
            "\1\10\34\uffff\1\1\1\6\1\55\1\7",
            "\1\10\1\40\1\37\1\42\1\41\30\uffff\1\1\1\36\1\30\1\31",
            "\1\10\1\40\1\37\1\42\1\41\30\uffff\1\1\1\36\1\30\1\31",
            "\1\10\1\40\1\37\1\42\1\41\30\uffff\1\1\1\36\1\30\1\31",
            "\1\10\1\40\1\37\1\42\1\41\30\uffff\1\1\1\36\1\30\1\31",
            "\1\10\1\40\1\37\1\42\1\41\30\uffff\1\1\1\36\1\30\1\31",
            "\1\10\1\45\1\44\1\47\1\46\30\uffff\1\1\1\43\1\56",
            "\1\10\1\45\1\44\1\47\1\46\30\uffff\1\1\1\43\1\56",
            "\1\10\1\45\1\44\1\47\1\46\30\uffff\1\1\1\43\1\56",
            "\1\10\1\45\1\44\1\47\1\46\30\uffff\1\1\1\43\1\56",
            "\1\10\1\45\1\44\1\47\1\46\30\uffff\1\1\1\43\1\56",
            "\1\10\1\52\1\51\1\54\1\53\30\uffff\1\1\1\50\1\32",
            "\1\10\1\52\1\51\1\54\1\53\30\uffff\1\1\1\50\1\32",
            "\1\10\1\52\1\51\1\54\1\53\30\uffff\1\1\1\50\1\32",
            "\1\10\1\52\1\51\1\54\1\53\30\uffff\1\1\1\50\1\32",
            "\1\10\1\52\1\51\1\54\1\53\30\uffff\1\1\1\50\1\32",
            "\1\61\1\uffff\1\57\1\60",
            "\1\64\1\63\1\66\1\65\31\uffff\1\62",
            "\1\10\34\uffff\1\1\1\6\1\55\1\7",
            "\1\10\34\uffff\1\1\1\6\1\55\1\7",
            "\1\10\34\uffff\1\1\1\6\1\55\1\7",
            "\1\10\1\64\1\63\1\66\1\65\30\uffff\1\1\1\62\1\56",
            "\1\10\1\64\1\63\1\66\1\65\30\uffff\1\1\1\62\1\56",
            "\1\10\1\64\1\63\1\66\1\65\30\uffff\1\1\1\62\1\56",
            "\1\10\1\64\1\63\1\66\1\65\30\uffff\1\1\1\62\1\56",
            "\1\10\1\64\1\63\1\66\1\65\30\uffff\1\1\1\62\1\56"
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "643:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )";
        }
    }
    static final String dfa_29s = "\1\41\1\56\2\uffff";
    static final String[] dfa_30s = {
            "\1\1\34\uffff\1\2",
            "\1\1\2\3\1\uffff\2\3\27\uffff\1\2\6\uffff\7\3",
            "",
            ""
    };
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final short[][] dfa_30 = unpackEncodedStringArray(dfa_30s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_29;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_30;
        }
        public String getDescription() {
            return "()* loopback of 798:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*";
        }
    }
    static final String dfa_31s = "\20\uffff";
    static final String dfa_32s = "\3\uffff\5\12\3\uffff\5\12";
    static final String dfa_33s = "\1\42\1\5\1\uffff\5\4\1\5\2\uffff\5\4";
    static final String dfa_34s = "\1\44\1\42\1\uffff\5\44\1\42\2\uffff\5\44";
    static final String dfa_35s = "\2\uffff\1\2\6\uffff\1\3\1\1\5\uffff";
    static final String dfa_36s = "\20\uffff}>";
    static final String[] dfa_37s = {
            "\1\1\1\uffff\1\2",
            "\1\5\1\4\1\7\1\6\31\uffff\1\3",
            "",
            "\1\12\1\5\1\4\1\7\1\6\30\uffff\1\12\1\3\1\10\1\11",
            "\1\12\1\5\1\4\1\7\1\6\30\uffff\1\12\1\3\1\10\1\11",
            "\1\12\1\5\1\4\1\7\1\6\30\uffff\1\12\1\3\1\10\1\11",
            "\1\12\1\5\1\4\1\7\1\6\30\uffff\1\12\1\3\1\10\1\11",
            "\1\12\1\5\1\4\1\7\1\6\30\uffff\1\12\1\3\1\10\1\11",
            "\1\15\1\14\1\17\1\16\31\uffff\1\13",
            "",
            "",
            "\1\12\1\15\1\14\1\17\1\16\30\uffff\1\12\1\13\1\10\1\11",
            "\1\12\1\15\1\14\1\17\1\16\30\uffff\1\12\1\13\1\10\1\11",
            "\1\12\1\15\1\14\1\17\1\16\30\uffff\1\12\1\13\1\10\1\11",
            "\1\12\1\15\1\14\1\17\1\16\30\uffff\1\12\1\13\1\10\1\11",
            "\1\12\1\15\1\14\1\17\1\16\30\uffff\1\12\1\13\1\10\1\11"
    };

    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final char[] dfa_33 = DFA.unpackEncodedStringToUnsignedChars(dfa_33s);
    static final char[] dfa_34 = DFA.unpackEncodedStringToUnsignedChars(dfa_34s);
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[][] dfa_37 = unpackEncodedStringArray(dfa_37s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_31;
            this.eof = dfa_32;
            this.min = dfa_33;
            this.max = dfa_34;
            this.accept = dfa_35;
            this.special = dfa_36;
            this.transition = dfa_37;
        }
        public String getDescription() {
            return "1125:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x00007F0000000370L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000002800000080L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00007F05000003E0L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00007F0000000360L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000200000012L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000200000010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000400000010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000001C00000002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000002C000001E0L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000002C000001E2L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x00000010000001A0L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x00000010000001A2L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x000000EC000001E0L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x000000EC000001E2L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000004000001E0L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000004000001E2L});

}