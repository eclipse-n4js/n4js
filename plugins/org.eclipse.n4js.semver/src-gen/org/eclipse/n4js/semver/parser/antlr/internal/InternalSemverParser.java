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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_WS", "RULE_LETTER_V", "RULE_DIGITS", "RULE_LETTER_F", "RULE_LETTER_I", "RULE_LETTER_L", "RULE_LETTER_E", "RULE_LETTER_S", "RULE_LETTER_M", "RULE_LETTER_R", "RULE_LETTER_X", "RULE_ASTERIX", "RULE_LETTER_OTHER", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "':'", "'/'", "'#'", "'||'", "'-'", "'.'", "'+'", "'@'", "'_'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='"
    };
    public static final int T__50=50;
    public static final int RULE_WHITESPACE_FRAGMENT=17;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=18;
    public static final int RULE_EOL=19;
    public static final int RULE_LETTER_OTHER=16;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=30;
    public static final int RULE_ZWNJ=24;
    public static final int RULE_ASTERIX=15;
    public static final int RULE_LETTER_E=10;
    public static final int RULE_ML_COMMENT_FRAGMENT=29;
    public static final int RULE_DIGITS=6;
    public static final int RULE_ZWJ=23;
    public static final int RULE_SL_COMMENT_FRAGMENT=28;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=31;
    public static final int T__37=37;
    public static final int RULE_LETTER_R=13;
    public static final int T__38=38;
    public static final int RULE_LETTER_S=11;
    public static final int T__39=39;
    public static final int RULE_LETTER_F=7;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=26;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_LETTER_I=8;
    public static final int EOF=-1;
    public static final int RULE_LETTER_L=9;
    public static final int RULE_LETTER_M=12;
    public static final int RULE_WS=4;
    public static final int RULE_BOM=25;
    public static final int RULE_LETTER_V=5;
    public static final int RULE_LETTER_X=14;
    public static final int RULE_ANY_OTHER=34;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=27;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=33;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=20;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_HEX_DIGIT=21;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=22;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=32;
    public static final int T__46=46;
    public static final int T__47=47;
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
    // InternalSemver.g:72:1: entryRuleNPMVersionRequirement returns [EObject current=null] : iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF ;
    public final EObject entryRuleNPMVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNPMVersionRequirement = null;


        try {
            // InternalSemver.g:72:62: (iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF )
            // InternalSemver.g:73:2: iv_ruleNPMVersionRequirement= ruleNPMVersionRequirement EOF
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
    // InternalSemver.g:79:1: ruleNPMVersionRequirement returns [EObject current=null] : ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) ) (this_WS_6= RULE_WS )* ) ) ;
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
            // InternalSemver.g:85:2: ( ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) ) (this_WS_6= RULE_WS )* ) ) )
            // InternalSemver.g:86:2: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) ) (this_WS_6= RULE_WS )* ) )
            {
            // InternalSemver.g:86:2: ( ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) ) (this_WS_6= RULE_WS )* ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==EOF||(LA5_0>=RULE_WS && LA5_0<=RULE_DIGITS)||(LA5_0>=RULE_LETTER_X && LA5_0<=RULE_ASTERIX)||(LA5_0>=44 && LA5_0<=50)) ) {
                alt5=1;
            }
            else if ( ((LA5_0>=RULE_LETTER_F && LA5_0<=RULE_LETTER_R)||LA5_0==RULE_LETTER_OTHER||LA5_0==39||LA5_0==43) ) {
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
                    // InternalSemver.g:87:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    {
                    // InternalSemver.g:87:3: ( (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    // InternalSemver.g:88:4: (this_WS_0= RULE_WS )* this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement
                    {
                    // InternalSemver.g:88:4: (this_WS_0= RULE_WS )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==RULE_WS) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // InternalSemver.g:89:5: this_WS_0= RULE_WS
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
                    // InternalSemver.g:104:3: ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) ) (this_WS_6= RULE_WS )* )
                    {
                    // InternalSemver.g:104:3: ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) ) (this_WS_6= RULE_WS )* )
                    // InternalSemver.g:105:4: ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) ) (this_WS_6= RULE_WS )*
                    {
                    // InternalSemver.g:105:4: ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) )
                    int alt3=2;
                    alt3 = dfa3.predict(input);
                    switch (alt3) {
                        case 1 :
                            // InternalSemver.g:106:5: ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement )
                            {
                            // InternalSemver.g:106:5: ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement )
                            // InternalSemver.g:107:6: ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement
                            {
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
                            // InternalSemver.g:118:5: ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement )
                            {
                            // InternalSemver.g:118:5: ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement )
                            int alt2=3;
                            alt2 = dfa2.predict(input);
                            switch (alt2) {
                                case 1 :
                                    // InternalSemver.g:119:6: ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement )
                                    {
                                    // InternalSemver.g:119:6: ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement )
                                    // InternalSemver.g:120:7: ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement
                                    {
                                    if ( state.backtracking==0 ) {

                                      							newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0());
                                      						
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


                                    }
                                    break;
                                case 2 :
                                    // InternalSemver.g:131:6: this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement
                                    {
                                    if ( state.backtracking==0 ) {

                                      						newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1());
                                      					
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
                                case 3 :
                                    // InternalSemver.g:140:6: this_TagVersionRequirement_5= ruleTagVersionRequirement
                                    {
                                    if ( state.backtracking==0 ) {

                                      						newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_2());
                                      					
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


                            }
                            break;

                    }

                    // InternalSemver.g:150:4: (this_WS_6= RULE_WS )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==RULE_WS) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // InternalSemver.g:151:5: this_WS_6= RULE_WS
                    	    {
                    	    this_WS_6=(Token)match(input,RULE_WS,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_WS_6, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
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
    // InternalSemver.g:161:1: entryRuleLocalPathVersionRequirement returns [EObject current=null] : iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF ;
    public final EObject entryRuleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalPathVersionRequirement = null;


        try {
            // InternalSemver.g:161:68: (iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF )
            // InternalSemver.g:162:2: iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF
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
    // InternalSemver.g:168:1: ruleLocalPathVersionRequirement returns [EObject current=null] : ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) ) ;
    public final EObject ruleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_localPath_1_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:174:2: ( ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) ) )
            // InternalSemver.g:175:2: ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) )
            {
            // InternalSemver.g:175:2: ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) )
            // InternalSemver.g:176:3: ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_5);
            ruleFILE_TAG();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSemver.g:183:3: ( (lv_localPath_1_0= rulePATH ) )
            // InternalSemver.g:184:4: (lv_localPath_1_0= rulePATH )
            {
            // InternalSemver.g:184:4: (lv_localPath_1_0= rulePATH )
            // InternalSemver.g:185:5: lv_localPath_1_0= rulePATH
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
    // InternalSemver.g:206:1: entryRuleURLVersionRequirement returns [EObject current=null] : iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF ;
    public final EObject entryRuleURLVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionRequirement = null;


        try {
            // InternalSemver.g:206:62: (iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF )
            // InternalSemver.g:207:2: iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF
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
    // InternalSemver.g:213:1: ruleURLVersionRequirement returns [EObject current=null] : ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? ) ;
    public final EObject ruleURLVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_protocol_0_0 = null;

        AntlrDatatypeRuleToken lv_url_4_0 = null;

        EObject lv_versionSpecifier_6_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:219:2: ( ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? ) )
            // InternalSemver.g:220:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? )
            {
            // InternalSemver.g:220:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? )
            // InternalSemver.g:221:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )?
            {
            // InternalSemver.g:221:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) )
            // InternalSemver.g:222:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            {
            // InternalSemver.g:222:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            // InternalSemver.g:223:5: lv_protocol_0_0= ruleURL_PROTOCOL
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

            // InternalSemver.g:240:3: (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' )
            // InternalSemver.g:241:4: otherlv_1= ':' otherlv_2= '/' otherlv_3= '/'
            {
            otherlv_1=(Token)match(input,35,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0());
              			
            }
            otherlv_2=(Token)match(input,36,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_2, grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1());
              			
            }
            otherlv_3=(Token)match(input,36,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_3, grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2());
              			
            }

            }

            // InternalSemver.g:254:3: ( (lv_url_4_0= ruleURL ) )
            // InternalSemver.g:255:4: (lv_url_4_0= ruleURL )
            {
            // InternalSemver.g:255:4: (lv_url_4_0= ruleURL )
            // InternalSemver.g:256:5: lv_url_4_0= ruleURL
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_9);
            lv_url_4_0=ruleURL();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
              					}
              					set(
              						current,
              						"url",
              						lv_url_4_0,
              						"org.eclipse.n4js.semver.Semver.URL");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:273:3: (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==37) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSemver.g:274:4: otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) )
                    {
                    otherlv_5=(Token)match(input,37,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0());
                      			
                    }
                    // InternalSemver.g:278:4: ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) )
                    // InternalSemver.g:279:5: (lv_versionSpecifier_6_0= ruleURLVersionSpecifier )
                    {
                    // InternalSemver.g:279:5: (lv_versionSpecifier_6_0= ruleURLVersionSpecifier )
                    // InternalSemver.g:280:6: lv_versionSpecifier_6_0= ruleURLVersionSpecifier
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_versionSpecifier_6_0=ruleURLVersionSpecifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
                      						}
                      						set(
                      							current,
                      							"versionSpecifier",
                      							lv_versionSpecifier_6_0,
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
    // InternalSemver.g:302:1: entryRuleURLVersionSpecifier returns [EObject current=null] : iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF ;
    public final EObject entryRuleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionSpecifier = null;


        try {
            // InternalSemver.g:302:60: (iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF )
            // InternalSemver.g:303:2: iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF
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
    // InternalSemver.g:309:1: ruleURLVersionSpecifier returns [EObject current=null] : ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) ) ;
    public final EObject ruleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject this_URLSemver_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_2_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_4_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:315:2: ( ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) ) )
            // InternalSemver.g:316:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) )
            {
            // InternalSemver.g:316:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) )
            int alt7=3;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:317:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    {
                    // InternalSemver.g:317:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    // InternalSemver.g:318:4: ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0_0());
                      			
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
                    // InternalSemver.g:330:3: ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) )
                    {
                    // InternalSemver.g:330:3: ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) )
                    // InternalSemver.g:331:4: () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
                    {
                    // InternalSemver.g:331:4: ()
                    // InternalSemver.g:332:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSemver.g:338:4: ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
                    // InternalSemver.g:339:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
                    {
                    // InternalSemver.g:339:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
                    // InternalSemver.g:340:6: lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARS_START_WITH_DIGITSParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_commitISH_2_0=ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getURLVersionSpecifierRule());
                      						}
                      						set(
                      							current,
                      							"commitISH",
                      							lv_commitISH_2_0,
                      							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS_START_WITH_DIGITS");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:359:3: ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSemver.g:359:3: ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    // InternalSemver.g:360:4: () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    // InternalSemver.g:360:4: ()
                    // InternalSemver.g:361:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSemver.g:367:4: ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSemver.g:368:5: (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSemver.g:368:5: (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSemver.g:369:6: lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_commitISH_4_0=ruleALPHA_NUMERIC_CHARS();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getURLVersionSpecifierRule());
                      						}
                      						set(
                      							current,
                      							"commitISH",
                      							lv_commitISH_4_0,
                      							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
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
    // $ANTLR end "ruleURLVersionSpecifier"


    // $ANTLR start "entryRuleURLSemver"
    // InternalSemver.g:391:1: entryRuleURLSemver returns [EObject current=null] : iv_ruleURLSemver= ruleURLSemver EOF ;
    public final EObject entryRuleURLSemver() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLSemver = null;


        try {
            // InternalSemver.g:391:50: (iv_ruleURLSemver= ruleURLSemver EOF )
            // InternalSemver.g:392:2: iv_ruleURLSemver= ruleURLSemver EOF
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
    // InternalSemver.g:398:1: ruleURLSemver returns [EObject current=null] : ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) ) ;
    public final EObject ruleURLSemver() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_withSemverTag_1_0 = null;

        EObject lv_simpleVersion_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:404:2: ( ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) ) )
            // InternalSemver.g:405:2: ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) )
            {
            // InternalSemver.g:405:2: ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) )
            // InternalSemver.g:406:3: () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) )
            {
            // InternalSemver.g:406:3: ()
            // InternalSemver.g:407:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getURLSemverAccess().getURLSemverAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:413:3: ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_LETTER_S) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSemver.g:414:4: (lv_withSemverTag_1_0= ruleSEMVER_TAG )
                    {
                    // InternalSemver.g:414:4: (lv_withSemverTag_1_0= ruleSEMVER_TAG )
                    // InternalSemver.g:415:5: lv_withSemverTag_1_0= ruleSEMVER_TAG
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getURLSemverAccess().getWithSemverTagSEMVER_TAGParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_11);
                    lv_withSemverTag_1_0=ruleSEMVER_TAG();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getURLSemverRule());
                      					}
                      					set(
                      						current,
                      						"withSemverTag",
                      						true,
                      						"org.eclipse.n4js.semver.Semver.SEMVER_TAG");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSemver.g:432:3: ( (lv_simpleVersion_2_0= ruleSimpleVersion ) )
            // InternalSemver.g:433:4: (lv_simpleVersion_2_0= ruleSimpleVersion )
            {
            // InternalSemver.g:433:4: (lv_simpleVersion_2_0= ruleSimpleVersion )
            // InternalSemver.g:434:5: lv_simpleVersion_2_0= ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_simpleVersion_2_0=ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getURLSemverRule());
              					}
              					set(
              						current,
              						"simpleVersion",
              						lv_simpleVersion_2_0,
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


    // $ANTLR start "entryRuleTagVersionRequirement"
    // InternalSemver.g:455:1: entryRuleTagVersionRequirement returns [EObject current=null] : iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF ;
    public final EObject entryRuleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTagVersionRequirement = null;


        try {
            // InternalSemver.g:455:62: (iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF )
            // InternalSemver.g:456:2: iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF
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
    // InternalSemver.g:462:1: ruleTagVersionRequirement returns [EObject current=null] : ( (lv_tagName_0_0= ruleTAG ) ) ;
    public final EObject ruleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_tagName_0_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:468:2: ( ( (lv_tagName_0_0= ruleTAG ) ) )
            // InternalSemver.g:469:2: ( (lv_tagName_0_0= ruleTAG ) )
            {
            // InternalSemver.g:469:2: ( (lv_tagName_0_0= ruleTAG ) )
            // InternalSemver.g:470:3: (lv_tagName_0_0= ruleTAG )
            {
            // InternalSemver.g:470:3: (lv_tagName_0_0= ruleTAG )
            // InternalSemver.g:471:4: lv_tagName_0_0= ruleTAG
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
    // InternalSemver.g:491:1: entryRuleGitHubVersionRequirement returns [EObject current=null] : iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF ;
    public final EObject entryRuleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGitHubVersionRequirement = null;


        try {
            // InternalSemver.g:491:65: (iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF )
            // InternalSemver.g:492:2: iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF
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
    // InternalSemver.g:498:1: ruleGitHubVersionRequirement returns [EObject current=null] : ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) ;
    public final EObject ruleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_githubUrl_0_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:504:2: ( ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) )
            // InternalSemver.g:505:2: ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            {
            // InternalSemver.g:505:2: ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            // InternalSemver.g:506:3: ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            {
            // InternalSemver.g:506:3: ( (lv_githubUrl_0_0= ruleURL_NO_VX ) )
            // InternalSemver.g:507:4: (lv_githubUrl_0_0= ruleURL_NO_VX )
            {
            // InternalSemver.g:507:4: (lv_githubUrl_0_0= ruleURL_NO_VX )
            // InternalSemver.g:508:5: lv_githubUrl_0_0= ruleURL_NO_VX
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURL_NO_VXParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_9);
            lv_githubUrl_0_0=ruleURL_NO_VX();

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
              						"org.eclipse.n4js.semver.Semver.URL_NO_VX");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalSemver.g:525:3: (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==37) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSemver.g:526:4: otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_1=(Token)match(input,37,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:530:4: ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSemver.g:531:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSemver.g:531:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSemver.g:532:6: lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:554:1: entryRuleVersionRangeSetRequirement returns [EObject current=null] : iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF ;
    public final EObject entryRuleVersionRangeSetRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeSetRequirement = null;


        try {
            // InternalSemver.g:554:67: (iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF )
            // InternalSemver.g:555:2: iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF
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
    // InternalSemver.g:561:1: ruleVersionRangeSetRequirement returns [EObject current=null] : ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? ) ;
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
            // InternalSemver.g:567:2: ( ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? ) )
            // InternalSemver.g:568:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? )
            {
            // InternalSemver.g:568:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )? )
            // InternalSemver.g:569:3: () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )?
            {
            // InternalSemver.g:569:3: ()
            // InternalSemver.g:570:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:576:3: ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=RULE_LETTER_V && LA14_0<=RULE_DIGITS)||(LA14_0>=RULE_LETTER_X && LA14_0<=RULE_ASTERIX)||(LA14_0>=44 && LA14_0<=50)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalSemver.g:577:4: ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )*
                    {
                    // InternalSemver.g:577:4: ( (lv_ranges_1_0= ruleVersionRange ) )
                    // InternalSemver.g:578:5: (lv_ranges_1_0= ruleVersionRange )
                    {
                    // InternalSemver.g:578:5: (lv_ranges_1_0= ruleVersionRange )
                    // InternalSemver.g:579:6: lv_ranges_1_0= ruleVersionRange
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_12);
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

                    // InternalSemver.g:596:4: ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )*
                    loop12:
                    do {
                        int alt12=2;
                        alt12 = dfa12.predict(input);
                        switch (alt12) {
                    	case 1 :
                    	    // InternalSemver.g:597:5: (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    {
                    	    // InternalSemver.g:597:5: (this_WS_2= RULE_WS )*
                    	    loop10:
                    	    do {
                    	        int alt10=2;
                    	        int LA10_0 = input.LA(1);

                    	        if ( (LA10_0==RULE_WS) ) {
                    	            alt10=1;
                    	        }


                    	        switch (alt10) {
                    	    	case 1 :
                    	    	    // InternalSemver.g:598:6: this_WS_2= RULE_WS
                    	    	    {
                    	    	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_13); if (state.failed) return current;
                    	    	    if ( state.backtracking==0 ) {

                    	    	      						newLeafNode(this_WS_2, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0());
                    	    	      					
                    	    	    }

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop10;
                    	        }
                    	    } while (true);

                    	    otherlv_3=(Token)match(input,38,FOLLOW_3); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1());
                    	      				
                    	    }
                    	    // InternalSemver.g:607:5: (this_WS_4= RULE_WS )*
                    	    loop11:
                    	    do {
                    	        int alt11=2;
                    	        int LA11_0 = input.LA(1);

                    	        if ( (LA11_0==RULE_WS) ) {
                    	            alt11=1;
                    	        }


                    	        switch (alt11) {
                    	    	case 1 :
                    	    	    // InternalSemver.g:608:6: this_WS_4= RULE_WS
                    	    	    {
                    	    	    this_WS_4=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
                    	    	    if ( state.backtracking==0 ) {

                    	    	      						newLeafNode(this_WS_4, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2());
                    	    	      					
                    	    	    }

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop11;
                    	        }
                    	    } while (true);

                    	    // InternalSemver.g:613:5: ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    // InternalSemver.g:614:6: (lv_ranges_5_0= ruleVersionRange )
                    	    {
                    	    // InternalSemver.g:614:6: (lv_ranges_5_0= ruleVersionRange )
                    	    // InternalSemver.g:615:7: lv_ranges_5_0= ruleVersionRange
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_1_3_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_12);
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
                    	    break loop12;
                        }
                    } while (true);

                    // InternalSemver.g:633:4: (this_WS_6= RULE_WS )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==RULE_WS) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalSemver.g:634:5: this_WS_6= RULE_WS
                    	    {
                    	    this_WS_6=(Token)match(input,RULE_WS,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_WS_6, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2());
                    	      				
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
    // InternalSemver.g:644:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // InternalSemver.g:644:53: (iv_ruleVersionRange= ruleVersionRange EOF )
            // InternalSemver.g:645:2: iv_ruleVersionRange= ruleVersionRange EOF
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
    // InternalSemver.g:651:1: ruleVersionRange returns [EObject current=null] : (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_VersionRangeContraint_0 = null;

        EObject this_HyphenVersionRange_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:657:2: ( (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) )
            // InternalSemver.g:658:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            {
            // InternalSemver.g:658:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            int alt15=2;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // InternalSemver.g:659:3: this_VersionRangeContraint_0= ruleVersionRangeContraint
                    {
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
                    // InternalSemver.g:668:3: this_HyphenVersionRange_1= ruleHyphenVersionRange
                    {
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
    // InternalSemver.g:680:1: entryRuleHyphenVersionRange returns [EObject current=null] : iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF ;
    public final EObject entryRuleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHyphenVersionRange = null;


        try {
            // InternalSemver.g:680:59: (iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF )
            // InternalSemver.g:681:2: iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF
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
    // InternalSemver.g:687:1: ruleHyphenVersionRange returns [EObject current=null] : ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Token otherlv_3=null;
        Token this_WS_4=null;
        EObject lv_from_1_0 = null;

        EObject lv_to_5_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:693:2: ( ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) ) )
            // InternalSemver.g:694:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            {
            // InternalSemver.g:694:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) ) )
            // InternalSemver.g:695:3: () ( (lv_from_1_0= ruleVersionNumber ) ) (this_WS_2= RULE_WS )+ otherlv_3= '-' (this_WS_4= RULE_WS )+ ( (lv_to_5_0= ruleVersionNumber ) )
            {
            // InternalSemver.g:695:3: ()
            // InternalSemver.g:696:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:702:3: ( (lv_from_1_0= ruleVersionNumber ) )
            // InternalSemver.g:703:4: (lv_from_1_0= ruleVersionNumber )
            {
            // InternalSemver.g:703:4: (lv_from_1_0= ruleVersionNumber )
            // InternalSemver.g:704:5: lv_from_1_0= ruleVersionNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_14);
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

            // InternalSemver.g:721:3: (this_WS_2= RULE_WS )+
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
            	    // InternalSemver.g:722:4: this_WS_2= RULE_WS
            	    {
            	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_WS_2, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2());
            	      			
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

            otherlv_3=(Token)match(input,39,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3());
              		
            }
            // InternalSemver.g:731:3: (this_WS_4= RULE_WS )+
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
            	    // InternalSemver.g:732:4: this_WS_4= RULE_WS
            	    {
            	    this_WS_4=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_WS_4, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4());
            	      			
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

            // InternalSemver.g:737:3: ( (lv_to_5_0= ruleVersionNumber ) )
            // InternalSemver.g:738:4: (lv_to_5_0= ruleVersionNumber )
            {
            // InternalSemver.g:738:4: (lv_to_5_0= ruleVersionNumber )
            // InternalSemver.g:739:5: lv_to_5_0= ruleVersionNumber
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
    // InternalSemver.g:760:1: entryRuleVersionRangeContraint returns [EObject current=null] : iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF ;
    public final EObject entryRuleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeContraint = null;


        try {
            // InternalSemver.g:760:62: (iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF )
            // InternalSemver.g:761:2: iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF
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
    // InternalSemver.g:767:1: ruleVersionRangeContraint returns [EObject current=null] : ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) ;
    public final EObject ruleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        EObject lv_versionConstraints_1_0 = null;

        EObject lv_versionConstraints_3_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:773:2: ( ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) )
            // InternalSemver.g:774:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            {
            // InternalSemver.g:774:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            // InternalSemver.g:775:3: () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            {
            // InternalSemver.g:775:3: ()
            // InternalSemver.g:776:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:782:3: ( (lv_versionConstraints_1_0= ruleSimpleVersion ) )
            // InternalSemver.g:783:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            {
            // InternalSemver.g:783:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            // InternalSemver.g:784:5: lv_versionConstraints_1_0= ruleSimpleVersion
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

            // InternalSemver.g:801:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            loop19:
            do {
                int alt19=2;
                alt19 = dfa19.predict(input);
                switch (alt19) {
            	case 1 :
            	    // InternalSemver.g:802:4: (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    {
            	    // InternalSemver.g:802:4: (this_WS_2= RULE_WS )+
            	    int cnt18=0;
            	    loop18:
            	    do {
            	        int alt18=2;
            	        int LA18_0 = input.LA(1);

            	        if ( (LA18_0==RULE_WS) ) {
            	            alt18=1;
            	        }


            	        switch (alt18) {
            	    	case 1 :
            	    	    // InternalSemver.g:803:5: this_WS_2= RULE_WS
            	    	    {
            	    	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					newLeafNode(this_WS_2, grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt18 >= 1 ) break loop18;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(18, input);
            	                throw eee;
            	        }
            	        cnt18++;
            	    } while (true);

            	    // InternalSemver.g:808:4: ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    // InternalSemver.g:809:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    {
            	    // InternalSemver.g:809:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    // InternalSemver.g:810:6: lv_versionConstraints_3_0= ruleSimpleVersion
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
            	    break loop19;
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
    // InternalSemver.g:832:1: entryRuleSimpleVersion returns [EObject current=null] : iv_ruleSimpleVersion= ruleSimpleVersion EOF ;
    public final EObject entryRuleSimpleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleVersion = null;


        try {
            // InternalSemver.g:832:54: (iv_ruleSimpleVersion= ruleSimpleVersion EOF )
            // InternalSemver.g:833:2: iv_ruleSimpleVersion= ruleSimpleVersion EOF
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
    // InternalSemver.g:839:1: ruleSimpleVersion returns [EObject current=null] : ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )* )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleSimpleVersion() throws RecognitionException {
        EObject current = null;

        Token this_WS_1=null;
        Token lv_withLetterV_2_0=null;
        Enumerator lv_comparators_0_0 = null;

        EObject lv_number_3_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:845:2: ( ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )* )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) ) )
            // InternalSemver.g:846:2: ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )* )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) )
            {
            // InternalSemver.g:846:2: ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )* )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) )
            // InternalSemver.g:847:3: ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )* )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) )
            {
            // InternalSemver.g:847:3: ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )* )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>=44 && LA21_0<=50)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalSemver.g:848:4: ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )*
            	    {
            	    // InternalSemver.g:848:4: ( (lv_comparators_0_0= ruleVersionComparator ) )
            	    // InternalSemver.g:849:5: (lv_comparators_0_0= ruleVersionComparator )
            	    {
            	    // InternalSemver.g:849:5: (lv_comparators_0_0= ruleVersionComparator )
            	    // InternalSemver.g:850:6: lv_comparators_0_0= ruleVersionComparator
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_0_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_comparators_0_0=ruleVersionComparator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"comparators",
            	      							lv_comparators_0_0,
            	      							"org.eclipse.n4js.semver.Semver.VersionComparator");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }

            	    // InternalSemver.g:867:4: (this_WS_1= RULE_WS )*
            	    loop20:
            	    do {
            	        int alt20=2;
            	        int LA20_0 = input.LA(1);

            	        if ( (LA20_0==RULE_WS) ) {
            	            alt20=1;
            	        }


            	        switch (alt20) {
            	    	case 1 :
            	    	    // InternalSemver.g:868:5: this_WS_1= RULE_WS
            	    	    {
            	    	    this_WS_1=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      					newLeafNode(this_WS_1, grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1());
            	    	      				
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop20;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            // InternalSemver.g:874:3: ( (lv_withLetterV_2_0= RULE_LETTER_V ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_LETTER_V) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSemver.g:875:4: (lv_withLetterV_2_0= RULE_LETTER_V )
                    {
                    // InternalSemver.g:875:4: (lv_withLetterV_2_0= RULE_LETTER_V )
                    // InternalSemver.g:876:5: lv_withLetterV_2_0= RULE_LETTER_V
                    {
                    lv_withLetterV_2_0=(Token)match(input,RULE_LETTER_V,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_withLetterV_2_0, grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_1_0());
                      				
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

            // InternalSemver.g:892:3: ( (lv_number_3_0= ruleVersionNumber ) )
            // InternalSemver.g:893:4: (lv_number_3_0= ruleVersionNumber )
            {
            // InternalSemver.g:893:4: (lv_number_3_0= ruleVersionNumber )
            // InternalSemver.g:894:5: lv_number_3_0= ruleVersionNumber
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
    // InternalSemver.g:915:1: entryRuleVersionNumber returns [EObject current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final EObject entryRuleVersionNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionNumber = null;


        try {
            // InternalSemver.g:915:54: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalSemver.g:916:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalSemver.g:922:1: ruleVersionNumber returns [EObject current=null] : ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) ;
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
            // InternalSemver.g:928:2: ( ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) )
            // InternalSemver.g:929:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            {
            // InternalSemver.g:929:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            // InternalSemver.g:930:3: ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )?
            {
            // InternalSemver.g:930:3: ( (lv_major_0_0= ruleVersionPart ) )
            // InternalSemver.g:931:4: (lv_major_0_0= ruleVersionPart )
            {
            // InternalSemver.g:931:4: (lv_major_0_0= ruleVersionPart )
            // InternalSemver.g:932:5: lv_major_0_0= ruleVersionPart
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_16);
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

            // InternalSemver.g:949:3: (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==40) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSemver.g:950:4: otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    {
                    otherlv_1=(Token)match(input,40,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:954:4: ( (lv_minor_2_0= ruleVersionPart ) )
                    // InternalSemver.g:955:5: (lv_minor_2_0= ruleVersionPart )
                    {
                    // InternalSemver.g:955:5: (lv_minor_2_0= ruleVersionPart )
                    // InternalSemver.g:956:6: lv_minor_2_0= ruleVersionPart
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_16);
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

                    // InternalSemver.g:973:4: (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==40) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // InternalSemver.g:974:5: otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            {
                            otherlv_3=(Token)match(input,40,FOLLOW_11); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            // InternalSemver.g:978:5: ( (lv_patch_4_0= ruleVersionPart ) )
                            // InternalSemver.g:979:6: (lv_patch_4_0= ruleVersionPart )
                            {
                            // InternalSemver.g:979:6: (lv_patch_4_0= ruleVersionPart )
                            // InternalSemver.g:980:7: lv_patch_4_0= ruleVersionPart
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0());
                              						
                            }
                            pushFollow(FOLLOW_16);
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

                            // InternalSemver.g:997:5: (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            loop23:
                            do {
                                int alt23=2;
                                int LA23_0 = input.LA(1);

                                if ( (LA23_0==40) ) {
                                    alt23=1;
                                }


                                switch (alt23) {
                            	case 1 :
                            	    // InternalSemver.g:998:6: otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) )
                            	    {
                            	    otherlv_5=(Token)match(input,40,FOLLOW_11); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_5, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0());
                            	      					
                            	    }
                            	    // InternalSemver.g:1002:6: ( (lv_extended_6_0= ruleVersionPart ) )
                            	    // InternalSemver.g:1003:7: (lv_extended_6_0= ruleVersionPart )
                            	    {
                            	    // InternalSemver.g:1003:7: (lv_extended_6_0= ruleVersionPart )
                            	    // InternalSemver.g:1004:8: lv_extended_6_0= ruleVersionPart
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_16);
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
                            	    break loop23;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalSemver.g:1024:3: ( (lv_qualifier_7_0= ruleQualifier ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==39||LA26_0==41) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalSemver.g:1025:4: (lv_qualifier_7_0= ruleQualifier )
                    {
                    // InternalSemver.g:1025:4: (lv_qualifier_7_0= ruleQualifier )
                    // InternalSemver.g:1026:5: lv_qualifier_7_0= ruleQualifier
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
    // InternalSemver.g:1047:1: entryRuleVersionPart returns [EObject current=null] : iv_ruleVersionPart= ruleVersionPart EOF ;
    public final EObject entryRuleVersionPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionPart = null;


        try {
            // InternalSemver.g:1047:52: (iv_ruleVersionPart= ruleVersionPart EOF )
            // InternalSemver.g:1048:2: iv_ruleVersionPart= ruleVersionPart EOF
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
    // InternalSemver.g:1054:1: ruleVersionPart returns [EObject current=null] : ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) ;
    public final EObject ruleVersionPart() throws RecognitionException {
        EObject current = null;

        Token lv_numberRaw_1_0=null;
        AntlrDatatypeRuleToken lv_wildcard_0_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:1060:2: ( ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) )
            // InternalSemver.g:1061:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            {
            // InternalSemver.g:1061:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=RULE_LETTER_X && LA27_0<=RULE_ASTERIX)) ) {
                alt27=1;
            }
            else if ( (LA27_0==RULE_DIGITS) ) {
                alt27=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // InternalSemver.g:1062:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    {
                    // InternalSemver.g:1062:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    // InternalSemver.g:1063:4: (lv_wildcard_0_0= ruleWILDCARD )
                    {
                    // InternalSemver.g:1063:4: (lv_wildcard_0_0= ruleWILDCARD )
                    // InternalSemver.g:1064:5: lv_wildcard_0_0= ruleWILDCARD
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
                    // InternalSemver.g:1082:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    {
                    // InternalSemver.g:1082:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    // InternalSemver.g:1083:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    {
                    // InternalSemver.g:1083:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    // InternalSemver.g:1084:5: lv_numberRaw_1_0= RULE_DIGITS
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
    // InternalSemver.g:1104:1: entryRuleQualifier returns [EObject current=null] : iv_ruleQualifier= ruleQualifier EOF ;
    public final EObject entryRuleQualifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifier = null;


        try {
            // InternalSemver.g:1104:50: (iv_ruleQualifier= ruleQualifier EOF )
            // InternalSemver.g:1105:2: iv_ruleQualifier= ruleQualifier EOF
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
    // InternalSemver.g:1111:1: ruleQualifier returns [EObject current=null] : ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) ) ;
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
            // InternalSemver.g:1117:2: ( ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) ) )
            // InternalSemver.g:1118:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )
            {
            // InternalSemver.g:1118:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )
            int alt28=3;
            alt28 = dfa28.predict(input);
            switch (alt28) {
                case 1 :
                    // InternalSemver.g:1119:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) )
                    {
                    // InternalSemver.g:1119:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) )
                    // InternalSemver.g:1120:4: otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) )
                    {
                    otherlv_0=(Token)match(input,39,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
                      			
                    }
                    // InternalSemver.g:1124:4: ( (lv_preRelease_1_0= ruleQualifierTag ) )
                    // InternalSemver.g:1125:5: (lv_preRelease_1_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1125:5: (lv_preRelease_1_0= ruleQualifierTag )
                    // InternalSemver.g:1126:6: lv_preRelease_1_0= ruleQualifierTag
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
                    // InternalSemver.g:1145:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )
                    {
                    // InternalSemver.g:1145:3: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )
                    // InternalSemver.g:1146:4: otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                    {
                    otherlv_2=(Token)match(input,41,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:1150:4: ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                    // InternalSemver.g:1151:5: (lv_buildMetadata_3_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1151:5: (lv_buildMetadata_3_0= ruleQualifierTag )
                    // InternalSemver.g:1152:6: lv_buildMetadata_3_0= ruleQualifierTag
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
                    // InternalSemver.g:1171:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) )
                    {
                    // InternalSemver.g:1171:3: (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) )
                    // InternalSemver.g:1172:4: otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) )
                    {
                    otherlv_4=(Token)match(input,39,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0());
                      			
                    }
                    // InternalSemver.g:1176:4: ( (lv_preRelease_5_0= ruleQualifierTag ) )
                    // InternalSemver.g:1177:5: (lv_preRelease_5_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1177:5: (lv_preRelease_5_0= ruleQualifierTag )
                    // InternalSemver.g:1178:6: lv_preRelease_5_0= ruleQualifierTag
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
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

                    otherlv_6=(Token)match(input,41,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2());
                      			
                    }
                    // InternalSemver.g:1199:4: ( (lv_buildMetadata_7_0= ruleQualifierTag ) )
                    // InternalSemver.g:1200:5: (lv_buildMetadata_7_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1200:5: (lv_buildMetadata_7_0= ruleQualifierTag )
                    // InternalSemver.g:1201:6: lv_buildMetadata_7_0= ruleQualifierTag
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
    // InternalSemver.g:1223:1: entryRuleQualifierTag returns [EObject current=null] : iv_ruleQualifierTag= ruleQualifierTag EOF ;
    public final EObject entryRuleQualifierTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifierTag = null;


        try {
            // InternalSemver.g:1223:53: (iv_ruleQualifierTag= ruleQualifierTag EOF )
            // InternalSemver.g:1224:2: iv_ruleQualifierTag= ruleQualifierTag EOF
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
    // InternalSemver.g:1230:1: ruleQualifierTag returns [EObject current=null] : ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) ;
    public final EObject ruleQualifierTag() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_parts_0_0 = null;

        AntlrDatatypeRuleToken lv_parts_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:1236:2: ( ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) )
            // InternalSemver.g:1237:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            {
            // InternalSemver.g:1237:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            // InternalSemver.g:1238:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            {
            // InternalSemver.g:1238:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:1239:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:1239:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:1240:5: lv_parts_0_0= ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_18);
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

            // InternalSemver.g:1257:3: (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==40) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSemver.g:1258:4: otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    {
            	    otherlv_1=(Token)match(input,40,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    // InternalSemver.g:1262:4: ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    // InternalSemver.g:1263:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    {
            	    // InternalSemver.g:1263:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    // InternalSemver.g:1264:6: lv_parts_2_0= ruleALPHA_NUMERIC_CHARS
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_18);
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
            	    break loop29;
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


    // $ANTLR start "entryRuleFILE_TAG"
    // InternalSemver.g:1286:1: entryRuleFILE_TAG returns [String current=null] : iv_ruleFILE_TAG= ruleFILE_TAG EOF ;
    public final String entryRuleFILE_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFILE_TAG = null;


        try {
            // InternalSemver.g:1286:48: (iv_ruleFILE_TAG= ruleFILE_TAG EOF )
            // InternalSemver.g:1287:2: iv_ruleFILE_TAG= ruleFILE_TAG EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFILE_TAGRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFILE_TAG=ruleFILE_TAG();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFILE_TAG.getText(); 
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
    // $ANTLR end "entryRuleFILE_TAG"


    // $ANTLR start "ruleFILE_TAG"
    // InternalSemver.g:1293:1: ruleFILE_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' ) ;
    public final AntlrDatatypeRuleToken ruleFILE_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_F_0=null;
        Token this_LETTER_I_1=null;
        Token this_LETTER_L_2=null;
        Token this_LETTER_E_3=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSemver.g:1299:2: ( (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' ) )
            // InternalSemver.g:1300:2: (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' )
            {
            // InternalSemver.g:1300:2: (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' )
            // InternalSemver.g:1301:3: this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':'
            {
            this_LETTER_F_0=(Token)match(input,RULE_LETTER_F,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_F_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_F_0, grammarAccess.getFILE_TAGAccess().getLETTER_FTerminalRuleCall_0());
              		
            }
            this_LETTER_I_1=(Token)match(input,RULE_LETTER_I,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_I_1);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_I_1, grammarAccess.getFILE_TAGAccess().getLETTER_ITerminalRuleCall_1());
              		
            }
            this_LETTER_L_2=(Token)match(input,RULE_LETTER_L,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_L_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_L_2, grammarAccess.getFILE_TAGAccess().getLETTER_LTerminalRuleCall_2());
              		
            }
            this_LETTER_E_3=(Token)match(input,RULE_LETTER_E,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_E_3);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_E_3, grammarAccess.getFILE_TAGAccess().getLETTER_ETerminalRuleCall_3());
              		
            }
            kw=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getFILE_TAGAccess().getColonKeyword_4());
              		
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
    // $ANTLR end "ruleFILE_TAG"


    // $ANTLR start "entryRuleSEMVER_TAG"
    // InternalSemver.g:1338:1: entryRuleSEMVER_TAG returns [String current=null] : iv_ruleSEMVER_TAG= ruleSEMVER_TAG EOF ;
    public final String entryRuleSEMVER_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSEMVER_TAG = null;


        try {
            // InternalSemver.g:1338:50: (iv_ruleSEMVER_TAG= ruleSEMVER_TAG EOF )
            // InternalSemver.g:1339:2: iv_ruleSEMVER_TAG= ruleSEMVER_TAG EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSEMVER_TAGRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSEMVER_TAG=ruleSEMVER_TAG();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSEMVER_TAG.getText(); 
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
    // $ANTLR end "entryRuleSEMVER_TAG"


    // $ANTLR start "ruleSEMVER_TAG"
    // InternalSemver.g:1345:1: ruleSEMVER_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' ) ;
    public final AntlrDatatypeRuleToken ruleSEMVER_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_S_0=null;
        Token this_LETTER_E_1=null;
        Token this_LETTER_M_2=null;
        Token this_LETTER_V_3=null;
        Token this_LETTER_E_4=null;
        Token this_LETTER_R_5=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSemver.g:1351:2: ( (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' ) )
            // InternalSemver.g:1352:2: (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' )
            {
            // InternalSemver.g:1352:2: (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' )
            // InternalSemver.g:1353:3: this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':'
            {
            this_LETTER_S_0=(Token)match(input,RULE_LETTER_S,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_S_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_S_0, grammarAccess.getSEMVER_TAGAccess().getLETTER_STerminalRuleCall_0());
              		
            }
            this_LETTER_E_1=(Token)match(input,RULE_LETTER_E,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_E_1);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_E_1, grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_1());
              		
            }
            this_LETTER_M_2=(Token)match(input,RULE_LETTER_M,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_M_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_M_2, grammarAccess.getSEMVER_TAGAccess().getLETTER_MTerminalRuleCall_2());
              		
            }
            this_LETTER_V_3=(Token)match(input,RULE_LETTER_V,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_V_3);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_V_3, grammarAccess.getSEMVER_TAGAccess().getLETTER_VTerminalRuleCall_3());
              		
            }
            this_LETTER_E_4=(Token)match(input,RULE_LETTER_E,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_E_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_E_4, grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_4());
              		
            }
            this_LETTER_R_5=(Token)match(input,RULE_LETTER_R,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_R_5);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_R_5, grammarAccess.getSEMVER_TAGAccess().getLETTER_RTerminalRuleCall_5());
              		
            }
            kw=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6());
              		
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
    // $ANTLR end "ruleSEMVER_TAG"


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:1404:1: entryRulePATH returns [String current=null] : iv_rulePATH= rulePATH EOF ;
    public final String entryRulePATH() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePATH = null;


        try {
            // InternalSemver.g:1404:44: (iv_rulePATH= rulePATH EOF )
            // InternalSemver.g:1405:2: iv_rulePATH= rulePATH EOF
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
    // InternalSemver.g:1411:1: rulePATH returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '/' | kw= '.' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )+ ;
    public final AntlrDatatypeRuleToken rulePATH() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_5=null;
        AntlrDatatypeRuleToken this_LETTER_6 = null;



        	enterRule();

        try {
            // InternalSemver.g:1417:2: ( (kw= '/' | kw= '.' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )+ )
            // InternalSemver.g:1418:2: (kw= '/' | kw= '.' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )+
            {
            // InternalSemver.g:1418:2: (kw= '/' | kw= '.' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )+
            int cnt30=0;
            loop30:
            do {
                int alt30=8;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt30=1;
                    }
                    break;
                case 40:
                    {
                    alt30=2;
                    }
                    break;
                case 42:
                    {
                    alt30=3;
                    }
                    break;
                case 39:
                    {
                    alt30=4;
                    }
                    break;
                case 43:
                    {
                    alt30=5;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt30=6;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt30=7;
                    }
                    break;

                }

                switch (alt30) {
            	case 1 :
            	    // InternalSemver.g:1419:3: kw= '/'
            	    {
            	    kw=(Token)match(input,36,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().getSolidusKeyword_0());
            	      		
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1425:3: kw= '.'
            	    {
            	    kw=(Token)match(input,40,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().getFullStopKeyword_1());
            	      		
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1431:3: kw= '@'
            	    {
            	    kw=(Token)match(input,42,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().getCommercialAtKeyword_2());
            	      		
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1437:3: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().getHyphenMinusKeyword_3());
            	      		
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1443:3: kw= '_'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().get_Keyword_4());
            	      		
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1449:3: this_DIGITS_5= RULE_DIGITS
            	    {
            	    this_DIGITS_5=(Token)match(input,RULE_DIGITS,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_DIGITS_5);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_DIGITS_5, grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_5());
            	      		
            	    }

            	    }
            	    break;
            	case 7 :
            	    // InternalSemver.g:1457:3: this_LETTER_6= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getPATHAccess().getLETTERParserRuleCall_6());
            	      		
            	    }
            	    pushFollow(FOLLOW_25);
            	    this_LETTER_6=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_LETTER_6);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
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
    // InternalSemver.g:1471:1: entryRuleURL_PROTOCOL returns [String current=null] : iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF ;
    public final String entryRuleURL_PROTOCOL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL_PROTOCOL = null;


        try {
            // InternalSemver.g:1471:52: (iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF )
            // InternalSemver.g:1472:2: iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF
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
    // InternalSemver.g:1478:1: ruleURL_PROTOCOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ ) ;
    public final AntlrDatatypeRuleToken ruleURL_PROTOCOL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_LETTER_NO_VX_0 = null;

        AntlrDatatypeRuleToken this_LETTER_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:1484:2: ( (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ ) )
            // InternalSemver.g:1485:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ )
            {
            // InternalSemver.g:1485:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ )
            // InternalSemver.g:1486:3: this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_26);
            this_LETTER_NO_VX_0=ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_NO_VX_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSemver.g:1496:3: (this_LETTER_1= ruleLETTER | kw= '+' )+
            int cnt31=0;
            loop31:
            do {
                int alt31=3;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==RULE_LETTER_V||(LA31_0>=RULE_LETTER_F && LA31_0<=RULE_LETTER_X)||LA31_0==RULE_LETTER_OTHER) ) {
                    alt31=1;
                }
                else if ( (LA31_0==41) ) {
                    alt31=2;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:1497:4: this_LETTER_1= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURL_PROTOCOLAccess().getLETTERParserRuleCall_1_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_27);
            	    this_LETTER_1=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_1);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1508:4: kw= '+'
            	    {
            	    kw=(Token)match(input,41,FOLLOW_27); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1());
            	      			
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
    // InternalSemver.g:1518:1: entryRuleURL returns [String current=null] : iv_ruleURL= ruleURL EOF ;
    public final String entryRuleURL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL = null;


        try {
            // InternalSemver.g:1518:43: (iv_ruleURL= ruleURL EOF )
            // InternalSemver.g:1519:2: iv_ruleURL= ruleURL EOF
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
    // InternalSemver.g:1525:1: ruleURL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' | kw= '_' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_14= RULE_DIGITS | this_LETTER_15= ruleLETTER )* ) ;
    public final AntlrDatatypeRuleToken ruleURL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_2=null;
        Token this_DIGITS_14=null;
        AntlrDatatypeRuleToken this_LETTER_3 = null;

        AntlrDatatypeRuleToken this_LETTER_15 = null;



        	enterRule();

        try {
            // InternalSemver.g:1531:2: ( ( (kw= '-' | kw= '_' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_14= RULE_DIGITS | this_LETTER_15= ruleLETTER )* ) )
            // InternalSemver.g:1532:2: ( (kw= '-' | kw= '_' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_14= RULE_DIGITS | this_LETTER_15= ruleLETTER )* )
            {
            // InternalSemver.g:1532:2: ( (kw= '-' | kw= '_' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_14= RULE_DIGITS | this_LETTER_15= ruleLETTER )* )
            // InternalSemver.g:1533:3: (kw= '-' | kw= '_' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_14= RULE_DIGITS | this_LETTER_15= ruleLETTER )*
            {
            // InternalSemver.g:1533:3: (kw= '-' | kw= '_' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )*
            loop32:
            do {
                int alt32=5;
                switch ( input.LA(1) ) {
                case 39:
                    {
                    alt32=1;
                    }
                    break;
                case 43:
                    {
                    alt32=2;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt32=3;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt32=4;
                    }
                    break;

                }

                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:1534:4: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1540:4: kw= '_'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().get_Keyword_0_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1546:4: this_DIGITS_2= RULE_DIGITS
            	    {
            	    this_DIGITS_2=(Token)match(input,RULE_DIGITS,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_2, grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1554:4: this_LETTER_3= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_3());
            	      			
            	    }
            	    pushFollow(FOLLOW_8);
            	    this_LETTER_3=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

            // InternalSemver.g:1565:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' )
            int alt33=4;
            switch ( input.LA(1) ) {
            case 36:
                {
                alt33=1;
                }
                break;
            case 40:
                {
                alt33=2;
                }
                break;
            case 35:
                {
                alt33=3;
                }
                break;
            case 42:
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
                    // InternalSemver.g:1566:4: kw= '/'
                    {
                    kw=(Token)match(input,36,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1572:4: kw= '.'
                    {
                    kw=(Token)match(input,40,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1578:4: kw= ':'
                    {
                    kw=(Token)match(input,35,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_1_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSemver.g:1584:4: kw= '@'
                    {
                    kw=(Token)match(input,42,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_1_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1590:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_14= RULE_DIGITS | this_LETTER_15= ruleLETTER )*
            loop34:
            do {
                int alt34=9;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt34=1;
                    }
                    break;
                case 40:
                    {
                    alt34=2;
                    }
                    break;
                case 35:
                    {
                    alt34=3;
                    }
                    break;
                case 42:
                    {
                    alt34=4;
                    }
                    break;
                case 39:
                    {
                    alt34=5;
                    }
                    break;
                case 43:
                    {
                    alt34=6;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt34=7;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt34=8;
                    }
                    break;

                }

                switch (alt34) {
            	case 1 :
            	    // InternalSemver.g:1591:4: kw= '/'
            	    {
            	    kw=(Token)match(input,36,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_2_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1597:4: kw= '.'
            	    {
            	    kw=(Token)match(input,40,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_2_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1603:4: kw= ':'
            	    {
            	    kw=(Token)match(input,35,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_2_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1609:4: kw= '@'
            	    {
            	    kw=(Token)match(input,42,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_2_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1615:4: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4());
            	      			
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1621:4: kw= '_'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().get_Keyword_2_5());
            	      			
            	    }

            	    }
            	    break;
            	case 7 :
            	    // InternalSemver.g:1627:4: this_DIGITS_14= RULE_DIGITS
            	    {
            	    this_DIGITS_14=(Token)match(input,RULE_DIGITS,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_14);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_14, grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_6());
            	      			
            	    }

            	    }
            	    break;
            	case 8 :
            	    // InternalSemver.g:1635:4: this_LETTER_15= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_7());
            	      			
            	    }
            	    pushFollow(FOLLOW_28);
            	    this_LETTER_15=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_15);
            	      			
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


    // $ANTLR start "entryRuleURL_NO_VX"
    // InternalSemver.g:1650:1: entryRuleURL_NO_VX returns [String current=null] : iv_ruleURL_NO_VX= ruleURL_NO_VX EOF ;
    public final String entryRuleURL_NO_VX() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL_NO_VX = null;


        try {
            // InternalSemver.g:1650:49: (iv_ruleURL_NO_VX= ruleURL_NO_VX EOF )
            // InternalSemver.g:1651:2: iv_ruleURL_NO_VX= ruleURL_NO_VX EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getURL_NO_VXRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleURL_NO_VX=ruleURL_NO_VX();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleURL_NO_VX.getText(); 
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
    // $ANTLR end "entryRuleURL_NO_VX"


    // $ANTLR start "ruleURL_NO_VX"
    // InternalSemver.g:1657:1: ruleURL_NO_VX returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' | kw= '_' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_17= RULE_DIGITS | this_LETTER_18= ruleLETTER )* ) ;
    public final AntlrDatatypeRuleToken ruleURL_NO_VX() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_5=null;
        Token this_DIGITS_17=null;
        AntlrDatatypeRuleToken this_LETTER_NO_VX_2 = null;

        AntlrDatatypeRuleToken this_LETTER_6 = null;

        AntlrDatatypeRuleToken this_LETTER_18 = null;



        	enterRule();

        try {
            // InternalSemver.g:1663:2: ( ( (kw= '-' | kw= '_' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_17= RULE_DIGITS | this_LETTER_18= ruleLETTER )* ) )
            // InternalSemver.g:1664:2: ( (kw= '-' | kw= '_' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_17= RULE_DIGITS | this_LETTER_18= ruleLETTER )* )
            {
            // InternalSemver.g:1664:2: ( (kw= '-' | kw= '_' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_17= RULE_DIGITS | this_LETTER_18= ruleLETTER )* )
            // InternalSemver.g:1665:3: (kw= '-' | kw= '_' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_17= RULE_DIGITS | this_LETTER_18= ruleLETTER )*
            {
            // InternalSemver.g:1665:3: (kw= '-' | kw= '_' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX )
            int alt35=3;
            switch ( input.LA(1) ) {
            case 39:
                {
                alt35=1;
                }
                break;
            case 43:
                {
                alt35=2;
                }
                break;
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_OTHER:
                {
                alt35=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }

            switch (alt35) {
                case 1 :
                    // InternalSemver.g:1666:4: kw= '-'
                    {
                    kw=(Token)match(input,39,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1672:4: kw= '_'
                    {
                    kw=(Token)match(input,43,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1678:4: this_LETTER_NO_VX_2= ruleLETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0_2());
                      			
                    }
                    pushFollow(FOLLOW_8);
                    this_LETTER_NO_VX_2=ruleLETTER_NO_VX();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LETTER_NO_VX_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1689:3: (kw= '-' | kw= '_' | this_DIGITS_5= RULE_DIGITS | this_LETTER_6= ruleLETTER )*
            loop36:
            do {
                int alt36=5;
                switch ( input.LA(1) ) {
                case 39:
                    {
                    alt36=1;
                    }
                    break;
                case 43:
                    {
                    alt36=2;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt36=3;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt36=4;
                    }
                    break;

                }

                switch (alt36) {
            	case 1 :
            	    // InternalSemver.g:1690:4: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1696:4: kw= '_'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_1_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1702:4: this_DIGITS_5= RULE_DIGITS
            	    {
            	    this_DIGITS_5=(Token)match(input,RULE_DIGITS,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_5);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_5, grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1710:4: this_LETTER_6= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_3());
            	      			
            	    }
            	    pushFollow(FOLLOW_8);
            	    this_LETTER_6=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_6);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            // InternalSemver.g:1721:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' )
            int alt37=4;
            switch ( input.LA(1) ) {
            case 36:
                {
                alt37=1;
                }
                break;
            case 40:
                {
                alt37=2;
                }
                break;
            case 35:
                {
                alt37=3;
                }
                break;
            case 42:
                {
                alt37=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }

            switch (alt37) {
                case 1 :
                    // InternalSemver.g:1722:4: kw= '/'
                    {
                    kw=(Token)match(input,36,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1728:4: kw= '.'
                    {
                    kw=(Token)match(input,40,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1734:4: kw= ':'
                    {
                    kw=(Token)match(input,35,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSemver.g:1740:4: kw= '@'
                    {
                    kw=(Token)match(input,42,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1746:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '-' | kw= '_' | this_DIGITS_17= RULE_DIGITS | this_LETTER_18= ruleLETTER )*
            loop38:
            do {
                int alt38=9;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt38=1;
                    }
                    break;
                case 40:
                    {
                    alt38=2;
                    }
                    break;
                case 35:
                    {
                    alt38=3;
                    }
                    break;
                case 42:
                    {
                    alt38=4;
                    }
                    break;
                case 39:
                    {
                    alt38=5;
                    }
                    break;
                case 43:
                    {
                    alt38=6;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt38=7;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt38=8;
                    }
                    break;

                }

                switch (alt38) {
            	case 1 :
            	    // InternalSemver.g:1747:4: kw= '/'
            	    {
            	    kw=(Token)match(input,36,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1753:4: kw= '.'
            	    {
            	    kw=(Token)match(input,40,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1759:4: kw= ':'
            	    {
            	    kw=(Token)match(input,35,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1765:4: kw= '@'
            	    {
            	    kw=(Token)match(input,42,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1771:4: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4());
            	      			
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1777:4: kw= '_'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_3_5());
            	      			
            	    }

            	    }
            	    break;
            	case 7 :
            	    // InternalSemver.g:1783:4: this_DIGITS_17= RULE_DIGITS
            	    {
            	    this_DIGITS_17=(Token)match(input,RULE_DIGITS,FOLLOW_28); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_17);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_17, grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_6());
            	      			
            	    }

            	    }
            	    break;
            	case 8 :
            	    // InternalSemver.g:1791:4: this_LETTER_18= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_7());
            	      			
            	    }
            	    pushFollow(FOLLOW_28);
            	    this_LETTER_18=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_18);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop38;
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
    // $ANTLR end "ruleURL_NO_VX"


    // $ANTLR start "entryRuleTAG"
    // InternalSemver.g:1806:1: entryRuleTAG returns [String current=null] : iv_ruleTAG= ruleTAG EOF ;
    public final String entryRuleTAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTAG = null;


        try {
            // InternalSemver.g:1806:43: (iv_ruleTAG= ruleTAG EOF )
            // InternalSemver.g:1807:2: iv_ruleTAG= ruleTAG EOF
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
    // InternalSemver.g:1813:1: ruleTAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ ) ;
    public final AntlrDatatypeRuleToken ruleTAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_2=null;
        AntlrDatatypeRuleToken this_LETTER_NO_VX_0 = null;

        AntlrDatatypeRuleToken this_LETTER_3 = null;



        	enterRule();

        try {
            // InternalSemver.g:1819:2: ( (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ ) )
            // InternalSemver.g:1820:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ )
            {
            // InternalSemver.g:1820:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ )
            // InternalSemver.g:1821:3: this_LETTER_NO_VX_0= ruleLETTER_NO_VX (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTAGAccess().getLETTER_NO_VXParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_5);
            this_LETTER_NO_VX_0=ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_NO_VX_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSemver.g:1831:3: (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+
            int cnt39=0;
            loop39:
            do {
                int alt39=4;
                switch ( input.LA(1) ) {
                case 39:
                    {
                    alt39=1;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt39=2;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt39=3;
                    }
                    break;

                }

                switch (alt39) {
            	case 1 :
            	    // InternalSemver.g:1832:4: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1838:4: this_DIGITS_2= RULE_DIGITS
            	    {
            	    this_DIGITS_2=(Token)match(input,RULE_DIGITS,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_2, grammarAccess.getTAGAccess().getDIGITSTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1846:4: this_LETTER_3= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getTAGAccess().getLETTERParserRuleCall_1_2());
            	      			
            	    }
            	    pushFollow(FOLLOW_25);
            	    this_LETTER_3=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt39 >= 1 ) break loop39;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(39, input);
                        throw eee;
                }
                cnt39++;
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
    // InternalSemver.g:1861:1: entryRuleALPHA_NUMERIC_CHARS returns [String current=null] : iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF ;
    public final String entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHARS = null;


        try {
            // InternalSemver.g:1861:59: (iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:1862:2: iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:1868:1: ruleALPHA_NUMERIC_CHARS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER )+ ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHARS() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_1=null;
        AntlrDatatypeRuleToken this_LETTER_2 = null;



        	enterRule();

        try {
            // InternalSemver.g:1874:2: ( (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER )+ )
            // InternalSemver.g:1875:2: (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER )+
            {
            // InternalSemver.g:1875:2: (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER )+
            int cnt40=0;
            loop40:
            do {
                int alt40=4;
                switch ( input.LA(1) ) {
                case 39:
                    {
                    alt40=1;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt40=2;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt40=3;
                    }
                    break;

                }

                switch (alt40) {
            	case 1 :
            	    // InternalSemver.g:1876:3: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0());
            	      		
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1882:3: this_DIGITS_1= RULE_DIGITS
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
            	    // InternalSemver.g:1890:3: this_LETTER_2= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTERParserRuleCall_2());
            	      		
            	    }
            	    pushFollow(FOLLOW_25);
            	    this_LETTER_2=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_LETTER_2);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt40 >= 1 ) break loop40;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(40, input);
                        throw eee;
                }
                cnt40++;
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"
    // InternalSemver.g:1904:1: entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS returns [String current=null] : iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF ;
    public final String entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS = null;


        try {
            // InternalSemver.g:1904:77: (iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF )
            // InternalSemver.g:1905:2: iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS=ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS.getText(); 
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
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"


    // $ANTLR start "ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"
    // InternalSemver.g:1911:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_DIGITS_0= RULE_DIGITS (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ ) ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_DIGITS_0=null;
        Token kw=null;
        Token this_DIGITS_2=null;
        AntlrDatatypeRuleToken this_LETTER_3 = null;



        	enterRule();

        try {
            // InternalSemver.g:1917:2: ( (this_DIGITS_0= RULE_DIGITS (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ ) )
            // InternalSemver.g:1918:2: (this_DIGITS_0= RULE_DIGITS (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ )
            {
            // InternalSemver.g:1918:2: (this_DIGITS_0= RULE_DIGITS (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+ )
            // InternalSemver.g:1919:3: this_DIGITS_0= RULE_DIGITS (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+
            {
            this_DIGITS_0=(Token)match(input,RULE_DIGITS,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_DIGITS_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_DIGITS_0, grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_0());
              		
            }
            // InternalSemver.g:1926:3: (kw= '-' | this_DIGITS_2= RULE_DIGITS | this_LETTER_3= ruleLETTER )+
            int cnt41=0;
            loop41:
            do {
                int alt41=4;
                switch ( input.LA(1) ) {
                case 39:
                    {
                    alt41=1;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    alt41=2;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                    {
                    alt41=3;
                    }
                    break;

                }

                switch (alt41) {
            	case 1 :
            	    // InternalSemver.g:1927:4: kw= '-'
            	    {
            	    kw=(Token)match(input,39,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1933:4: this_DIGITS_2= RULE_DIGITS
            	    {
            	    this_DIGITS_2=(Token)match(input,RULE_DIGITS,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_DIGITS_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_DIGITS_2, grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1941:4: this_LETTER_3= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTERParserRuleCall_1_2());
            	      			
            	    }
            	    pushFollow(FOLLOW_25);
            	    this_LETTER_3=ruleLETTER();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LETTER_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt41 >= 1 ) break loop41;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(41, input);
                        throw eee;
                }
                cnt41++;
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
    // $ANTLR end "ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSemver.g:1956:1: entryRuleWILDCARD returns [String current=null] : iv_ruleWILDCARD= ruleWILDCARD EOF ;
    public final String entryRuleWILDCARD() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleWILDCARD = null;


        try {
            // InternalSemver.g:1956:48: (iv_ruleWILDCARD= ruleWILDCARD EOF )
            // InternalSemver.g:1957:2: iv_ruleWILDCARD= ruleWILDCARD EOF
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
    // InternalSemver.g:1963:1: ruleWILDCARD returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX ) ;
    public final AntlrDatatypeRuleToken ruleWILDCARD() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_X_0=null;
        Token this_ASTERIX_1=null;


        	enterRule();

        try {
            // InternalSemver.g:1969:2: ( (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX ) )
            // InternalSemver.g:1970:2: (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX )
            {
            // InternalSemver.g:1970:2: (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==RULE_LETTER_X) ) {
                alt42=1;
            }
            else if ( (LA42_0==RULE_ASTERIX) ) {
                alt42=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // InternalSemver.g:1971:3: this_LETTER_X_0= RULE_LETTER_X
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
                    // InternalSemver.g:1979:3: this_ASTERIX_1= RULE_ASTERIX
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


    // $ANTLR start "ruleLETTER"
    // InternalSemver.g:1991:1: ruleLETTER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) ;
    public final AntlrDatatypeRuleToken ruleLETTER() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_V_0=null;
        Token this_LETTER_X_1=null;
        AntlrDatatypeRuleToken this_LETTER_NO_VX_2 = null;



        	enterRule();

        try {
            // InternalSemver.g:1997:2: ( (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) )
            // InternalSemver.g:1998:2: (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX )
            {
            // InternalSemver.g:1998:2: (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX )
            int alt43=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_V:
                {
                alt43=1;
                }
                break;
            case RULE_LETTER_X:
                {
                alt43=2;
                }
                break;
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_OTHER:
                {
                alt43=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // InternalSemver.g:1999:3: this_LETTER_V_0= RULE_LETTER_V
                    {
                    this_LETTER_V_0=(Token)match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_V_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_V_0, grammarAccess.getLETTERAccess().getLETTER_VTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:2007:3: this_LETTER_X_1= RULE_LETTER_X
                    {
                    this_LETTER_X_1=(Token)match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_X_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_X_1, grammarAccess.getLETTERAccess().getLETTER_XTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:2015:3: this_LETTER_NO_VX_2= ruleLETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getLETTERAccess().getLETTER_NO_VXParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_LETTER_NO_VX_2=ruleLETTER_NO_VX();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_NO_VX_2);
                      		
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
    // $ANTLR end "ruleLETTER"


    // $ANTLR start "ruleLETTER_NO_VX"
    // InternalSemver.g:2030:1: ruleLETTER_NO_VX returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_S_0= RULE_LETTER_S | this_LETTER_M_1= RULE_LETTER_M | this_LETTER_R_2= RULE_LETTER_R | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_L_5= RULE_LETTER_L | this_LETTER_E_6= RULE_LETTER_E | this_LETTER_OTHER_7= RULE_LETTER_OTHER ) ;
    public final AntlrDatatypeRuleToken ruleLETTER_NO_VX() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_S_0=null;
        Token this_LETTER_M_1=null;
        Token this_LETTER_R_2=null;
        Token this_LETTER_F_3=null;
        Token this_LETTER_I_4=null;
        Token this_LETTER_L_5=null;
        Token this_LETTER_E_6=null;
        Token this_LETTER_OTHER_7=null;


        	enterRule();

        try {
            // InternalSemver.g:2036:2: ( (this_LETTER_S_0= RULE_LETTER_S | this_LETTER_M_1= RULE_LETTER_M | this_LETTER_R_2= RULE_LETTER_R | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_L_5= RULE_LETTER_L | this_LETTER_E_6= RULE_LETTER_E | this_LETTER_OTHER_7= RULE_LETTER_OTHER ) )
            // InternalSemver.g:2037:2: (this_LETTER_S_0= RULE_LETTER_S | this_LETTER_M_1= RULE_LETTER_M | this_LETTER_R_2= RULE_LETTER_R | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_L_5= RULE_LETTER_L | this_LETTER_E_6= RULE_LETTER_E | this_LETTER_OTHER_7= RULE_LETTER_OTHER )
            {
            // InternalSemver.g:2037:2: (this_LETTER_S_0= RULE_LETTER_S | this_LETTER_M_1= RULE_LETTER_M | this_LETTER_R_2= RULE_LETTER_R | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_L_5= RULE_LETTER_L | this_LETTER_E_6= RULE_LETTER_E | this_LETTER_OTHER_7= RULE_LETTER_OTHER )
            int alt44=8;
            switch ( input.LA(1) ) {
            case RULE_LETTER_S:
                {
                alt44=1;
                }
                break;
            case RULE_LETTER_M:
                {
                alt44=2;
                }
                break;
            case RULE_LETTER_R:
                {
                alt44=3;
                }
                break;
            case RULE_LETTER_F:
                {
                alt44=4;
                }
                break;
            case RULE_LETTER_I:
                {
                alt44=5;
                }
                break;
            case RULE_LETTER_L:
                {
                alt44=6;
                }
                break;
            case RULE_LETTER_E:
                {
                alt44=7;
                }
                break;
            case RULE_LETTER_OTHER:
                {
                alt44=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // InternalSemver.g:2038:3: this_LETTER_S_0= RULE_LETTER_S
                    {
                    this_LETTER_S_0=(Token)match(input,RULE_LETTER_S,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_S_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_S_0, grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:2046:3: this_LETTER_M_1= RULE_LETTER_M
                    {
                    this_LETTER_M_1=(Token)match(input,RULE_LETTER_M,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_M_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_M_1, grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:2054:3: this_LETTER_R_2= RULE_LETTER_R
                    {
                    this_LETTER_R_2=(Token)match(input,RULE_LETTER_R,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_R_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_R_2, grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSemver.g:2062:3: this_LETTER_F_3= RULE_LETTER_F
                    {
                    this_LETTER_F_3=(Token)match(input,RULE_LETTER_F,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_F_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_F_3, grammarAccess.getLETTER_NO_VXAccess().getLETTER_FTerminalRuleCall_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalSemver.g:2070:3: this_LETTER_I_4= RULE_LETTER_I
                    {
                    this_LETTER_I_4=(Token)match(input,RULE_LETTER_I,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_I_4);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_I_4, grammarAccess.getLETTER_NO_VXAccess().getLETTER_ITerminalRuleCall_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalSemver.g:2078:3: this_LETTER_L_5= RULE_LETTER_L
                    {
                    this_LETTER_L_5=(Token)match(input,RULE_LETTER_L,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_L_5);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_L_5, grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSemver.g:2086:3: this_LETTER_E_6= RULE_LETTER_E
                    {
                    this_LETTER_E_6=(Token)match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_E_6);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_E_6, grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSemver.g:2094:3: this_LETTER_OTHER_7= RULE_LETTER_OTHER
                    {
                    this_LETTER_OTHER_7=(Token)match(input,RULE_LETTER_OTHER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_OTHER_7);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_OTHER_7, grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_7());
                      		
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
    // $ANTLR end "ruleLETTER_NO_VX"


    // $ANTLR start "ruleVersionComparator"
    // InternalSemver.g:2105:1: ruleVersionComparator returns [Enumerator current=null] : ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) ) ;
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
            // InternalSemver.g:2111:2: ( ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) ) )
            // InternalSemver.g:2112:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) )
            {
            // InternalSemver.g:2112:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '~' ) | (enumLiteral_3= '^' ) | (enumLiteral_4= '<=' ) | (enumLiteral_5= '>' ) | (enumLiteral_6= '>=' ) )
            int alt45=7;
            switch ( input.LA(1) ) {
            case 44:
                {
                alt45=1;
                }
                break;
            case 45:
                {
                alt45=2;
                }
                break;
            case 46:
                {
                alt45=3;
                }
                break;
            case 47:
                {
                alt45=4;
                }
                break;
            case 48:
                {
                alt45=5;
                }
                break;
            case 49:
                {
                alt45=6;
                }
                break;
            case 50:
                {
                alt45=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // InternalSemver.g:2113:3: (enumLiteral_0= '=' )
                    {
                    // InternalSemver.g:2113:3: (enumLiteral_0= '=' )
                    // InternalSemver.g:2114:4: enumLiteral_0= '='
                    {
                    enumLiteral_0=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:2121:3: (enumLiteral_1= '<' )
                    {
                    // InternalSemver.g:2121:3: (enumLiteral_1= '<' )
                    // InternalSemver.g:2122:4: enumLiteral_1= '<'
                    {
                    enumLiteral_1=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:2129:3: (enumLiteral_2= '~' )
                    {
                    // InternalSemver.g:2129:3: (enumLiteral_2= '~' )
                    // InternalSemver.g:2130:4: enumLiteral_2= '~'
                    {
                    enumLiteral_2=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:2137:3: (enumLiteral_3= '^' )
                    {
                    // InternalSemver.g:2137:3: (enumLiteral_3= '^' )
                    // InternalSemver.g:2138:4: enumLiteral_3= '^'
                    {
                    enumLiteral_3=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:2145:3: (enumLiteral_4= '<=' )
                    {
                    // InternalSemver.g:2145:3: (enumLiteral_4= '<=' )
                    // InternalSemver.g:2146:4: enumLiteral_4= '<='
                    {
                    enumLiteral_4=(Token)match(input,48,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:2153:3: (enumLiteral_5= '>' )
                    {
                    // InternalSemver.g:2153:3: (enumLiteral_5= '>' )
                    // InternalSemver.g:2154:4: enumLiteral_5= '>'
                    {
                    enumLiteral_5=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_5, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5());
                      			
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:2161:3: (enumLiteral_6= '>=' )
                    {
                    // InternalSemver.g:2161:3: (enumLiteral_6= '>=' )
                    // InternalSemver.g:2162:4: enumLiteral_6= '>='
                    {
                    enumLiteral_6=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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

    // $ANTLR start synpred1_InternalSemver
    public final void synpred1_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:107:6: ( ruleLocalPathVersionRequirement )
        // InternalSemver.g:107:7: ruleLocalPathVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleLocalPathVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_InternalSemver

    // $ANTLR start synpred2_InternalSemver
    public final void synpred2_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:120:7: ( ruleURLVersionRequirement )
        // InternalSemver.g:120:8: ruleURLVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleURLVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalSemver

    // $ANTLR start synpred3_InternalSemver
    public final void synpred3_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:318:4: ( ruleURLSemver )
        // InternalSemver.g:318:5: ruleURLSemver
        {
        pushFollow(FOLLOW_2);
        ruleURLSemver();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSemver

    // Delegated rules

    public final boolean synpred1_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
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
    public final boolean synpred3_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA12 dfa12 = new DFA12(this);
    protected DFA15 dfa15 = new DFA15(this);
    protected DFA19 dfa19 = new DFA19(this);
    protected DFA28 dfa28 = new DFA28(this);
    static final String dfa_1s = "\30\uffff";
    static final String dfa_2s = "\3\uffff\4\2\21\uffff";
    static final String dfa_3s = "\1\7\1\5\1\uffff\4\4\20\0\1\uffff";
    static final String dfa_4s = "\2\53\1\uffff\4\53\20\0\1\uffff";
    static final String dfa_5s = "\2\uffff\1\2\24\uffff\1\1";
    static final String dfa_6s = "\7\uffff\1\10\1\16\1\12\1\4\1\13\1\1\1\0\1\3\1\11\1\14\1\15\1\17\1\2\1\5\1\6\1\7\1\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\6\2\2\uffff\1\2\26\uffff\1\2\3\uffff\1\2",
            "\3\2\1\3\6\2\1\uffff\1\2\22\uffff\2\2\2\uffff\5\2",
            "",
            "\5\2\1\4\5\2\1\uffff\1\2\22\uffff\2\2\2\uffff\5\2",
            "\6\2\1\5\4\2\1\uffff\1\2\22\uffff\2\2\2\uffff\5\2",
            "\13\2\1\uffff\1\2\22\uffff\1\6\1\2\2\uffff\5\2",
            "\1\2\1\15\1\14\1\22\1\23\1\24\1\25\1\17\1\20\1\21\1\16\1\uffff\1\26\22\uffff\1\2\1\7\1\2\1\uffff\1\12\1\10\1\uffff\1\11\1\13",
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
            return "105:4: ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA3_13 = input.LA(1);

                         
                        int index3_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_13);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA3_12 = input.LA(1);

                         
                        int index3_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_12);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA3_19 = input.LA(1);

                         
                        int index3_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_19);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA3_14 = input.LA(1);

                         
                        int index3_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_14);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA3_10 = input.LA(1);

                         
                        int index3_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA3_20 = input.LA(1);

                         
                        int index3_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_20);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA3_21 = input.LA(1);

                         
                        int index3_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_21);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA3_22 = input.LA(1);

                         
                        int index3_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_22);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA3_7 = input.LA(1);

                         
                        int index3_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_7);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA3_15 = input.LA(1);

                         
                        int index3_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_15);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA3_9 = input.LA(1);

                         
                        int index3_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_9);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA3_11 = input.LA(1);

                         
                        int index3_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA3_16 = input.LA(1);

                         
                        int index3_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_16);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA3_17 = input.LA(1);

                         
                        int index3_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_17);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA3_8 = input.LA(1);

                         
                        int index3_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_8);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA3_18 = input.LA(1);

                         
                        int index3_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index3_18);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 3, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\66\uffff";
    static final String dfa_9s = "\12\uffff\14\41\1\uffff\12\41\1\uffff\20\11\4\uffff";
    static final String dfa_10s = "\1\7\10\5\1\uffff\14\4\1\uffff\12\4\1\uffff\20\4\4\0";
    static final String dfa_11s = "\11\53\1\uffff\14\53\1\uffff\12\53\1\uffff\20\53\4\0";
    static final String dfa_12s = "\11\uffff\1\2\14\uffff\1\1\12\uffff\1\3\24\uffff";
    static final String dfa_13s = "\1\uffff\1\17\1\25\1\12\1\22\1\7\1\20\1\3\1\13\3\uffff\1\4\1\14\1\16\1\0\1\11\1\23\1\6\1\21\1\2\1\15\34\uffff\1\24\1\1\1\5\1\10}>";
    static final String[] dfa_14s = {
            "\1\4\1\5\1\6\1\7\1\1\1\2\1\3\2\uffff\1\10\26\uffff\1\11\3\uffff\1\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\2\11\2\uffff\1\12\1\11\1\26\2\11",
            "",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "\1\41\1\14\1\13\1\21\1\22\1\23\1\24\1\16\1\17\1\20\1\15\1\uffff\1\25\22\uffff\1\42\1\11\2\uffff\1\12\1\11\1\26\2\11",
            "",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "\1\41\1\27\1\13\1\34\1\35\1\36\1\37\1\31\1\32\1\33\1\30\1\uffff\1\40\22\uffff\2\11\2\uffff\1\12\1\11\1\uffff\2\11",
            "",
            "\13\11\1\uffff\1\11\22\uffff\1\11\1\43\1\11\1\uffff\2\11\1\uffff\2\11",
            "\13\11\1\uffff\1\11\22\uffff\1\11\1\44\1\11\1\uffff\2\11\1\uffff\2\11",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\11\1\50\1\47\1\55\1\56\1\57\1\60\1\52\1\53\1\54\1\51\1\uffff\1\61\22\uffff\1\64\1\62\1\11\1\uffff\1\45\1\63\1\uffff\1\65\1\46",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff"
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
            return "118:5: ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | this_GitHubVersionRequirement_4= ruleGitHubVersionRequirement | this_TagVersionRequirement_5= ruleTagVersionRequirement )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA2_15 = input.LA(1);

                         
                        int index2_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_15==35) ) {s = 34;}

                        else if ( (LA2_15==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_15==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_15==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_15==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_15==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_15==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_15==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_15==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_15==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_15==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_15==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_15==EOF||LA2_15==RULE_WS) ) {s = 33;}

                        else if ( (LA2_15==39) ) {s = 10;}

                        else if ( (LA2_15==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_15==36||LA2_15==40||(LA2_15>=42 && LA2_15<=43)) ) {s = 9;}

                         
                        input.seek(index2_15);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA2_51 = input.LA(1);

                         
                        int index2_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 22;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index2_51);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA2_20 = input.LA(1);

                         
                        int index2_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_20==35) ) {s = 34;}

                        else if ( (LA2_20==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_20==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_20==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_20==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_20==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_20==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_20==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_20==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_20==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_20==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_20==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_20==EOF||LA2_20==RULE_WS) ) {s = 33;}

                        else if ( (LA2_20==39) ) {s = 10;}

                        else if ( (LA2_20==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_20==36||LA2_20==40||(LA2_20>=42 && LA2_20<=43)) ) {s = 9;}

                         
                        input.seek(index2_20);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA2_7 = input.LA(1);

                         
                        int index2_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_7==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_7==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_7==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_7==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_7==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_7==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_7==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_7==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_7==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_7==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_7==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_7==39) ) {s = 10;}

                        else if ( ((LA2_7>=35 && LA2_7<=36)||LA2_7==40||(LA2_7>=42 && LA2_7<=43)) ) {s = 9;}

                        else if ( (LA2_7==RULE_DIGITS) ) {s = 11;}

                         
                        input.seek(index2_7);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA2_12 = input.LA(1);

                         
                        int index2_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_12==EOF||LA2_12==RULE_WS) ) {s = 33;}

                        else if ( (LA2_12==39) ) {s = 10;}

                        else if ( (LA2_12==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_12==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_12==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_12==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_12==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_12==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_12==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_12==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_12==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_12==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_12==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_12==36||LA2_12==40||(LA2_12>=42 && LA2_12<=43)) ) {s = 9;}

                        else if ( (LA2_12==35) ) {s = 34;}

                        else if ( (LA2_12==41) && (synpred2_InternalSemver())) {s = 22;}

                         
                        input.seek(index2_12);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA2_52 = input.LA(1);

                         
                        int index2_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 22;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index2_52);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA2_18 = input.LA(1);

                         
                        int index2_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_18==35) ) {s = 34;}

                        else if ( (LA2_18==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_18==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_18==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_18==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_18==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_18==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_18==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_18==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_18==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_18==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_18==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_18==EOF||LA2_18==RULE_WS) ) {s = 33;}

                        else if ( (LA2_18==39) ) {s = 10;}

                        else if ( (LA2_18==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_18==36||LA2_18==40||(LA2_18>=42 && LA2_18<=43)) ) {s = 9;}

                         
                        input.seek(index2_18);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA2_5 = input.LA(1);

                         
                        int index2_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_5==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_5==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_5==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_5==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_5==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_5==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_5==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_5==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_5==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_5==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_5==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_5==39) ) {s = 10;}

                        else if ( ((LA2_5>=35 && LA2_5<=36)||LA2_5==40||(LA2_5>=42 && LA2_5<=43)) ) {s = 9;}

                        else if ( (LA2_5==RULE_DIGITS) ) {s = 11;}

                         
                        input.seek(index2_5);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA2_53 = input.LA(1);

                         
                        int index2_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 22;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index2_53);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA2_16 = input.LA(1);

                         
                        int index2_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_16==36||LA2_16==40||(LA2_16>=42 && LA2_16<=43)) ) {s = 9;}

                        else if ( (LA2_16==35) ) {s = 34;}

                        else if ( (LA2_16==39) ) {s = 10;}

                        else if ( (LA2_16==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_16==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_16==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_16==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_16==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_16==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_16==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_16==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_16==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_16==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_16==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_16==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_16==EOF||LA2_16==RULE_WS) ) {s = 33;}

                         
                        input.seek(index2_16);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA2_3 = input.LA(1);

                         
                        int index2_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_3==39) ) {s = 10;}

                        else if ( (LA2_3==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_3==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_3==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_3==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_3==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_3==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_3==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_3==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_3==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_3==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_3==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_3==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( ((LA2_3>=35 && LA2_3<=36)||LA2_3==40||(LA2_3>=42 && LA2_3<=43)) ) {s = 9;}

                         
                        input.seek(index2_3);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA2_8 = input.LA(1);

                         
                        int index2_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_8==39) ) {s = 10;}

                        else if ( (LA2_8==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_8==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_8==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_8==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_8==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_8==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_8==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_8==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_8==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_8==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_8==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_8==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( ((LA2_8>=35 && LA2_8<=36)||LA2_8==40||(LA2_8>=42 && LA2_8<=43)) ) {s = 9;}

                         
                        input.seek(index2_8);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA2_13 = input.LA(1);

                         
                        int index2_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_13==35) ) {s = 34;}

                        else if ( (LA2_13==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_13==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_13==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_13==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_13==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_13==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_13==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_13==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_13==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_13==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_13==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_13==EOF||LA2_13==RULE_WS) ) {s = 33;}

                        else if ( (LA2_13==39) ) {s = 10;}

                        else if ( (LA2_13==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_13==36||LA2_13==40||(LA2_13>=42 && LA2_13<=43)) ) {s = 9;}

                         
                        input.seek(index2_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA2_21 = input.LA(1);

                         
                        int index2_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_21==35) ) {s = 34;}

                        else if ( (LA2_21==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_21==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_21==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_21==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_21==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_21==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_21==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_21==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_21==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_21==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_21==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_21==EOF||LA2_21==RULE_WS) ) {s = 33;}

                        else if ( (LA2_21==39) ) {s = 10;}

                        else if ( (LA2_21==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_21==36||LA2_21==40||(LA2_21>=42 && LA2_21<=43)) ) {s = 9;}

                         
                        input.seek(index2_21);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA2_14 = input.LA(1);

                         
                        int index2_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_14==EOF||LA2_14==RULE_WS) ) {s = 33;}

                        else if ( (LA2_14==39) ) {s = 10;}

                        else if ( (LA2_14==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_14==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_14==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_14==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_14==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_14==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_14==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_14==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_14==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_14==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_14==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_14==36||LA2_14==40||(LA2_14>=42 && LA2_14<=43)) ) {s = 9;}

                        else if ( (LA2_14==35) ) {s = 34;}

                        else if ( (LA2_14==41) && (synpred2_InternalSemver())) {s = 22;}

                         
                        input.seek(index2_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA2_1 = input.LA(1);

                         
                        int index2_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_1==39) ) {s = 10;}

                        else if ( ((LA2_1>=35 && LA2_1<=36)||LA2_1==40||(LA2_1>=42 && LA2_1<=43)) ) {s = 9;}

                        else if ( (LA2_1==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_1==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_1==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_1==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_1==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_1==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_1==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_1==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_1==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_1==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_1==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_1==41) && (synpred2_InternalSemver())) {s = 22;}

                         
                        input.seek(index2_1);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA2_6 = input.LA(1);

                         
                        int index2_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_6==39) ) {s = 10;}

                        else if ( ((LA2_6>=35 && LA2_6<=36)||LA2_6==40||(LA2_6>=42 && LA2_6<=43)) ) {s = 9;}

                        else if ( (LA2_6==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_6==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_6==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_6==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_6==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_6==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_6==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_6==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_6==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_6==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_6==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_6==41) && (synpred2_InternalSemver())) {s = 22;}

                         
                        input.seek(index2_6);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA2_19 = input.LA(1);

                         
                        int index2_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_19==36||LA2_19==40||(LA2_19>=42 && LA2_19<=43)) ) {s = 9;}

                        else if ( (LA2_19==35) ) {s = 34;}

                        else if ( (LA2_19==39) ) {s = 10;}

                        else if ( (LA2_19==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_19==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_19==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_19==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_19==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_19==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_19==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_19==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_19==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_19==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_19==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_19==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_19==EOF||LA2_19==RULE_WS) ) {s = 33;}

                         
                        input.seek(index2_19);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA2_4 = input.LA(1);

                         
                        int index2_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_4==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_4==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_4==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_4==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_4==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_4==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_4==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_4==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_4==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_4==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_4==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_4==39) ) {s = 10;}

                        else if ( ((LA2_4>=35 && LA2_4<=36)||LA2_4==40||(LA2_4>=42 && LA2_4<=43)) ) {s = 9;}

                        else if ( (LA2_4==RULE_DIGITS) ) {s = 11;}

                         
                        input.seek(index2_4);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA2_17 = input.LA(1);

                         
                        int index2_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_17==EOF||LA2_17==RULE_WS) ) {s = 33;}

                        else if ( (LA2_17==39) ) {s = 10;}

                        else if ( (LA2_17==RULE_DIGITS) ) {s = 11;}

                        else if ( (LA2_17==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_17==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_17==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_17==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_17==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_17==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_17==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_17==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_17==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_17==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_17==36||LA2_17==40||(LA2_17>=42 && LA2_17<=43)) ) {s = 9;}

                        else if ( (LA2_17==35) ) {s = 34;}

                        else if ( (LA2_17==41) && (synpred2_InternalSemver())) {s = 22;}

                         
                        input.seek(index2_17);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA2_50 = input.LA(1);

                         
                        int index2_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 22;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index2_50);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA2_2 = input.LA(1);

                         
                        int index2_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_2==RULE_LETTER_V) ) {s = 12;}

                        else if ( (LA2_2==RULE_LETTER_X) ) {s = 13;}

                        else if ( (LA2_2==RULE_LETTER_S) ) {s = 14;}

                        else if ( (LA2_2==RULE_LETTER_M) ) {s = 15;}

                        else if ( (LA2_2==RULE_LETTER_R) ) {s = 16;}

                        else if ( (LA2_2==RULE_LETTER_F) ) {s = 17;}

                        else if ( (LA2_2==RULE_LETTER_I) ) {s = 18;}

                        else if ( (LA2_2==RULE_LETTER_L) ) {s = 19;}

                        else if ( (LA2_2==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA2_2==RULE_LETTER_OTHER) ) {s = 21;}

                        else if ( (LA2_2==41) && (synpred2_InternalSemver())) {s = 22;}

                        else if ( (LA2_2==39) ) {s = 10;}

                        else if ( ((LA2_2>=35 && LA2_2<=36)||LA2_2==40||(LA2_2>=42 && LA2_2<=43)) ) {s = 9;}

                        else if ( (LA2_2==RULE_DIGITS) ) {s = 11;}

                         
                        input.seek(index2_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 2, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\26\uffff";
    static final String dfa_16s = "\1\uffff\1\15\7\uffff\1\15\4\uffff\1\15\2\uffff\4\15\1\uffff";
    static final String dfa_17s = "\1\5\1\4\7\uffff\1\4\1\0\1\uffff\1\5\1\uffff\1\4\1\0\1\uffff\4\4\1\uffff";
    static final String dfa_18s = "\1\62\1\47\7\uffff\1\47\1\0\1\uffff\1\47\1\uffff\1\47\1\0\1\uffff\4\47\1\uffff";
    static final String dfa_19s = "\2\uffff\7\1\2\uffff\1\1\1\uffff\1\3\2\uffff\1\2\4\uffff\1\1";
    static final String dfa_20s = "\1\5\10\uffff\1\0\1\2\1\uffff\1\1\2\uffff\1\3\4\uffff\1\4\1\uffff}>";
    static final String[] dfa_21s = {
            "\1\11\1\14\4\15\1\1\2\15\1\12\1\13\1\15\26\uffff\1\15\4\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10",
            "\6\15\1\16\4\15\1\uffff\1\15\26\uffff\1\15",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\15\1\17\7\15\1\12\1\13\1\15\26\uffff\1\15",
            "\1\uffff",
            "",
            "\12\20\1\uffff\1\20\26\uffff\1\20",
            "",
            "\10\15\1\21\2\15\1\uffff\1\15\26\uffff\1\15",
            "\1\uffff",
            "",
            "\1\15\1\22\11\15\1\uffff\1\15\26\uffff\1\15",
            "\6\15\1\23\4\15\1\uffff\1\15\26\uffff\1\15",
            "\11\15\1\24\1\15\1\uffff\1\15\26\uffff\1\15",
            "\13\15\1\uffff\1\15\22\uffff\1\25\3\uffff\1\15",
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "316:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_9 = input.LA(1);

                         
                        int index7_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA7_9==EOF||(LA7_9>=RULE_WS && LA7_9<=RULE_LETTER_V)||(LA7_9>=RULE_LETTER_F && LA7_9<=RULE_LETTER_R)||LA7_9==RULE_LETTER_OTHER||LA7_9==39) ) {s = 13;}

                        else if ( (LA7_9==RULE_DIGITS) ) {s = 15;}

                        else if ( (LA7_9==RULE_LETTER_X) ) {s = 10;}

                        else if ( (LA7_9==RULE_ASTERIX) && (synpred3_InternalSemver())) {s = 11;}

                         
                        input.seek(index7_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_12 = input.LA(1);

                         
                        int index7_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA7_12>=RULE_LETTER_V && LA7_12<=RULE_LETTER_X)||LA7_12==RULE_LETTER_OTHER||LA7_12==39) ) {s = 16;}

                        else if ( (synpred3_InternalSemver()) ) {s = 11;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index7_12);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA7_10 = input.LA(1);

                         
                        int index7_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 11;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index7_10);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA7_15 = input.LA(1);

                         
                        int index7_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 11;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index7_15);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA7_20 = input.LA(1);

                         
                        int index7_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA7_20==EOF||(LA7_20>=RULE_WS && LA7_20<=RULE_LETTER_X)||LA7_20==RULE_LETTER_OTHER||LA7_20==39) ) {s = 13;}

                        else if ( (LA7_20==35) && (synpred3_InternalSemver())) {s = 21;}

                         
                        input.seek(index7_20);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA7_0 = input.LA(1);

                         
                        int index7_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA7_0==RULE_LETTER_S) ) {s = 1;}

                        else if ( (LA7_0==44) && (synpred3_InternalSemver())) {s = 2;}

                        else if ( (LA7_0==45) && (synpred3_InternalSemver())) {s = 3;}

                        else if ( (LA7_0==46) && (synpred3_InternalSemver())) {s = 4;}

                        else if ( (LA7_0==47) && (synpred3_InternalSemver())) {s = 5;}

                        else if ( (LA7_0==48) && (synpred3_InternalSemver())) {s = 6;}

                        else if ( (LA7_0==49) && (synpred3_InternalSemver())) {s = 7;}

                        else if ( (LA7_0==50) && (synpred3_InternalSemver())) {s = 8;}

                        else if ( (LA7_0==RULE_LETTER_V) ) {s = 9;}

                        else if ( (LA7_0==RULE_LETTER_X) ) {s = 10;}

                        else if ( (LA7_0==RULE_ASTERIX) && (synpred3_InternalSemver())) {s = 11;}

                        else if ( (LA7_0==RULE_DIGITS) ) {s = 12;}

                        else if ( ((LA7_0>=RULE_LETTER_F && LA7_0<=RULE_LETTER_E)||(LA7_0>=RULE_LETTER_M && LA7_0<=RULE_LETTER_R)||LA7_0==RULE_LETTER_OTHER||LA7_0==39) ) {s = 13;}

                         
                        input.seek(index7_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_22s = "\4\uffff";
    static final String dfa_23s = "\2\2\2\uffff";
    static final String dfa_24s = "\2\4\2\uffff";
    static final String dfa_25s = "\2\46\2\uffff";
    static final String dfa_26s = "\2\uffff\1\2\1\1";
    static final String dfa_27s = "\4\uffff}>";
    static final String[] dfa_28s = {
            "\1\1\41\uffff\1\3",
            "\1\1\41\uffff\1\3",
            "",
            ""
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "()* loopback of 596:4: ( (this_WS_2= RULE_WS )* otherlv_3= '||' (this_WS_4= RULE_WS )* ( (lv_ranges_5_0= ruleVersionRange ) ) )*";
        }
    }
    static final String dfa_29s = "\141\uffff";
    static final String dfa_30s = "\2\uffff\3\1\3\uffff\34\1\5\uffff\47\1\2\uffff\17\1";
    static final String dfa_31s = "\1\5\1\uffff\3\4\1\6\2\5\34\4\1\uffff\1\6\3\5\47\4\1\6\1\5\17\4";
    static final String dfa_32s = "\1\62\1\uffff\3\51\1\17\2\47\1\62\17\51\14\50\1\uffff\1\17\3\47\17\51\30\50\1\17\1\47\3\51\14\50";
    static final String dfa_33s = "\1\uffff\1\1\42\uffff\1\2\74\uffff";
    static final String dfa_34s = "\141\uffff}>";
    static final String[] dfa_35s = {
            "\1\1\1\4\7\uffff\1\2\1\3\34\uffff\7\1",
            "",
            "\1\10\41\uffff\1\1\1\6\1\5\1\7",
            "\1\10\41\uffff\1\1\1\6\1\5\1\7",
            "\1\10\41\uffff\1\1\1\6\1\5\1\7",
            "\1\13\7\uffff\1\11\1\12",
            "\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\26\uffff\1\14",
            "\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\26\uffff\1\30",
            "\1\10\2\1\7\uffff\2\1\26\uffff\1\1\1\44\4\uffff\7\1",
            "\1\10\41\uffff\1\1\1\6\1\45\1\7",
            "\1\10\41\uffff\1\1\1\6\1\45\1\7",
            "\1\10\41\uffff\1\1\1\6\1\45\1\7",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\16\1\15\1\23\1\24\1\25\1\26\1\20\1\21\1\22\1\17\1\uffff\1\27\25\uffff\1\1\1\14\1\46\1\47",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "\1\10\1\32\1\31\1\37\1\40\1\41\1\42\1\34\1\35\1\36\1\33\1\uffff\1\43\25\uffff\1\1\1\30\1\50",
            "",
            "\1\53\7\uffff\1\51\1\52",
            "\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\26\uffff\1\54",
            "\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\26\uffff\1\70",
            "\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\26\uffff\1\104",
            "\1\10\41\uffff\1\1\1\6\1\120\1\7",
            "\1\10\41\uffff\1\1\1\6\1\120\1\7",
            "\1\10\41\uffff\1\1\1\6\1\120\1\7",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\56\1\55\1\63\1\64\1\65\1\66\1\60\1\61\1\62\1\57\1\uffff\1\67\25\uffff\1\1\1\54\1\46\1\47",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\72\1\71\1\77\1\100\1\101\1\102\1\74\1\75\1\76\1\73\1\uffff\1\103\25\uffff\1\1\1\70\1\121",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\10\1\106\1\105\1\113\1\114\1\115\1\116\1\110\1\111\1\112\1\107\1\uffff\1\117\25\uffff\1\1\1\104\1\50",
            "\1\124\7\uffff\1\122\1\123",
            "\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\26\uffff\1\125",
            "\1\10\41\uffff\1\1\1\6\1\120\1\7",
            "\1\10\41\uffff\1\1\1\6\1\120\1\7",
            "\1\10\41\uffff\1\1\1\6\1\120\1\7",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121",
            "\1\10\1\127\1\126\1\134\1\135\1\136\1\137\1\131\1\132\1\133\1\130\1\uffff\1\140\25\uffff\1\1\1\125\1\121"
    };

    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[][] dfa_35 = unpackEncodedStringArray(dfa_35s);

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "658:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )";
        }
    }
    static final String dfa_36s = "\1\46\1\62\2\uffff";
    static final String[] dfa_37s = {
            "\1\1\41\uffff\1\2",
            "\1\1\2\3\7\uffff\2\3\26\uffff\1\2\5\uffff\7\3",
            "",
            ""
    };
    static final char[] dfa_36 = DFA.unpackEncodedStringToUnsignedChars(dfa_36s);
    static final short[][] dfa_37 = unpackEncodedStringArray(dfa_37s);

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_36;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_37;
        }
        public String getDescription() {
            return "()* loopback of 801:3: ( (this_WS_2= RULE_WS )+ ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*";
        }
    }
    static final String dfa_38s = "\36\uffff";
    static final String dfa_39s = "\3\uffff\14\21\3\uffff\14\21";
    static final String dfa_40s = "\1\47\1\5\1\uffff\14\4\1\5\2\uffff\14\4";
    static final String dfa_41s = "\1\51\1\47\1\uffff\14\51\1\47\2\uffff\14\51";
    static final String dfa_42s = "\2\uffff\1\2\15\uffff\1\3\1\1\14\uffff";
    static final String dfa_43s = "\36\uffff}>";
    static final String[] dfa_44s = {
            "\1\1\1\uffff\1\2",
            "\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\26\uffff\1\3",
            "",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\21\1\5\1\4\1\12\1\13\1\14\1\15\1\7\1\10\1\11\1\6\1\uffff\1\16\25\uffff\1\21\1\3\1\17\1\20",
            "\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\26\uffff\1\22",
            "",
            "",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20",
            "\1\21\1\24\1\23\1\31\1\32\1\33\1\34\1\26\1\27\1\30\1\25\1\uffff\1\35\25\uffff\1\21\1\22\1\17\1\20"
    };

    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final char[] dfa_40 = DFA.unpackEncodedStringToUnsignedChars(dfa_40s);
    static final char[] dfa_41 = DFA.unpackEncodedStringToUnsignedChars(dfa_41s);
    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final short[] dfa_43 = DFA.unpackEncodedString(dfa_43s);
    static final short[][] dfa_44 = unpackEncodedStringArray(dfa_44s);

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = dfa_38;
            this.eof = dfa_39;
            this.min = dfa_40;
            this.max = dfa_41;
            this.accept = dfa_42;
            this.special = dfa_43;
            this.transition = dfa_44;
        }
        public String getDescription() {
            return "1118:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) ) | (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) ) | (otherlv_4= '-' ( (lv_preRelease_5_0= ruleQualifierTag ) ) otherlv_6= '+' ( (lv_buildMetadata_7_0= ruleQualifierTag ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0007F0000000C070L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000D9000017FE0L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000D9800017FE0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0007FD900001FFE0L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0007F0000000C060L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000004000000010L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000008000000010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000038000000002L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000D9000017FE2L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000F9000017FE0L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000F9000017FE2L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000D9800017FE2L});

}