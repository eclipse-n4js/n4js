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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_WS", "RULE_LETTER_V", "RULE_DIGITS", "RULE_LETTER_F", "RULE_LETTER_I", "RULE_LETTER_L", "RULE_LETTER_E", "RULE_LETTER_S", "RULE_LETTER_M", "RULE_LETTER_R", "RULE_LETTER_W", "RULE_LETTER_O", "RULE_LETTER_K", "RULE_LETTER_P", "RULE_LETTER_A", "RULE_LETTER_C", "RULE_ASTERIX", "RULE_LETTER_X", "RULE_LETTER_OTHER", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "':'", "'/'", "'#'", "'||'", "'-'", "'.'", "'+'", "'@'", "'_'", "'='", "'~'", "'^'", "'<'", "'>'", "'<='", "'>='"
    };
    public static final int T__50=50;
    public static final int RULE_WHITESPACE_FRAGMENT=23;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=24;
    public static final int RULE_EOL=25;
    public static final int RULE_LETTER_OTHER=22;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=36;
    public static final int RULE_ZWNJ=30;
    public static final int RULE_LETTER_A=18;
    public static final int RULE_LETTER_C=19;
    public static final int RULE_ASTERIX=20;
    public static final int RULE_LETTER_E=10;
    public static final int RULE_ML_COMMENT_FRAGMENT=35;
    public static final int RULE_DIGITS=6;
    public static final int RULE_LETTER_O=15;
    public static final int RULE_ZWJ=29;
    public static final int RULE_SL_COMMENT_FRAGMENT=34;
    public static final int RULE_LETTER_P=17;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=37;
    public static final int RULE_LETTER_R=13;
    public static final int RULE_LETTER_S=11;
    public static final int RULE_LETTER_F=7;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=32;
    public static final int RULE_LETTER_I=8;
    public static final int EOF=-1;
    public static final int RULE_LETTER_K=16;
    public static final int RULE_LETTER_L=9;
    public static final int RULE_LETTER_M=12;
    public static final int RULE_WS=4;
    public static final int RULE_BOM=31;
    public static final int RULE_LETTER_V=5;
    public static final int RULE_LETTER_W=14;
    public static final int RULE_LETTER_X=21;
    public static final int RULE_ANY_OTHER=40;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=33;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=39;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=26;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_HEX_DIGIT=27;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=28;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=38;
    public static final int T__46=46;
    public static final int T__47=47;
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
    // InternalSemver.g:79:1: ruleNPMVersionRequirement returns [EObject current=null] : ( ( (this_WS_0= RULE_WS )? this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) ) (this_WS_7= RULE_WS )? ) ) ;
    public final EObject ruleNPMVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token this_WS_0=null;
        Token this_WS_7=null;
        EObject this_VersionRangeSetRequirement_1 = null;

        EObject this_LocalPathVersionRequirement_2 = null;

        EObject this_URLVersionRequirement_3 = null;

        EObject this_WorkspaceVersionRequirement_4 = null;

        EObject this_GitHubVersionRequirement_5 = null;

        EObject this_TagVersionRequirement_6 = null;



        	enterRule();

        try {
            // InternalSemver.g:85:2: ( ( ( (this_WS_0= RULE_WS )? this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) ) (this_WS_7= RULE_WS )? ) ) )
            // InternalSemver.g:86:2: ( ( (this_WS_0= RULE_WS )? this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) ) (this_WS_7= RULE_WS )? ) )
            {
            // InternalSemver.g:86:2: ( ( (this_WS_0= RULE_WS )? this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement ) | ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) ) (this_WS_7= RULE_WS )? ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==EOF||(LA6_0>=RULE_WS && LA6_0<=RULE_DIGITS)||(LA6_0>=RULE_ASTERIX && LA6_0<=RULE_LETTER_X)||(LA6_0>=50 && LA6_0<=56)) ) {
                alt6=1;
            }
            else if ( ((LA6_0>=RULE_LETTER_F && LA6_0<=RULE_LETTER_C)||LA6_0==RULE_LETTER_OTHER||LA6_0==45||LA6_0==49) ) {
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
                    // InternalSemver.g:87:3: ( (this_WS_0= RULE_WS )? this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    {
                    // InternalSemver.g:87:3: ( (this_WS_0= RULE_WS )? this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement )
                    // InternalSemver.g:88:4: (this_WS_0= RULE_WS )? this_VersionRangeSetRequirement_1= ruleVersionRangeSetRequirement
                    {
                    // InternalSemver.g:88:4: (this_WS_0= RULE_WS )?
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
                    // InternalSemver.g:104:3: ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) ) (this_WS_7= RULE_WS )? )
                    {
                    // InternalSemver.g:104:3: ( ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) ) (this_WS_7= RULE_WS )? )
                    // InternalSemver.g:105:4: ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) ) (this_WS_7= RULE_WS )?
                    {
                    // InternalSemver.g:105:4: ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) )
                    int alt4=2;
                    alt4 = dfa4.predict(input);
                    switch (alt4) {
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
                            // InternalSemver.g:118:5: ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) )
                            {
                            // InternalSemver.g:118:5: ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) )
                            int alt3=2;
                            alt3 = dfa3.predict(input);
                            switch (alt3) {
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
                                    // InternalSemver.g:131:6: ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement )
                                    {
                                    // InternalSemver.g:131:6: ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement )
                                    int alt2=3;
                                    alt2 = dfa2.predict(input);
                                    switch (alt2) {
                                        case 1 :
                                            // InternalSemver.g:132:7: ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement )
                                            {
                                            // InternalSemver.g:132:7: ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement )
                                            // InternalSemver.g:133:8: ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement
                                            {
                                            if ( state.backtracking==0 ) {

                                              								newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getWorkspaceVersionRequirementParserRuleCall_1_0_1_1_0());
                                              							
                                            }
                                            pushFollow(FOLLOW_4);
                                            this_WorkspaceVersionRequirement_4=ruleWorkspaceVersionRequirement();

                                            state._fsp--;
                                            if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              								current = this_WorkspaceVersionRequirement_4;
                                              								afterParserOrEnumRuleCall();
                                              							
                                            }

                                            }


                                            }
                                            break;
                                        case 2 :
                                            // InternalSemver.g:144:7: this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement
                                            {
                                            if ( state.backtracking==0 ) {

                                              							newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1_1());
                                              						
                                            }
                                            pushFollow(FOLLOW_4);
                                            this_GitHubVersionRequirement_5=ruleGitHubVersionRequirement();

                                            state._fsp--;
                                            if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              							current = this_GitHubVersionRequirement_5;
                                              							afterParserOrEnumRuleCall();
                                              						
                                            }

                                            }
                                            break;
                                        case 3 :
                                            // InternalSemver.g:153:7: this_TagVersionRequirement_6= ruleTagVersionRequirement
                                            {
                                            if ( state.backtracking==0 ) {

                                              							newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_1_2());
                                              						
                                            }
                                            pushFollow(FOLLOW_4);
                                            this_TagVersionRequirement_6=ruleTagVersionRequirement();

                                            state._fsp--;
                                            if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              							current = this_TagVersionRequirement_6;
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

                    // InternalSemver.g:164:4: (this_WS_7= RULE_WS )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==RULE_WS) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // InternalSemver.g:165:5: this_WS_7= RULE_WS
                            {
                            this_WS_7=(Token)match(input,RULE_WS,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_WS_7, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1());
                              				
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
    // $ANTLR end "ruleNPMVersionRequirement"


    // $ANTLR start "entryRuleLocalPathVersionRequirement"
    // InternalSemver.g:175:1: entryRuleLocalPathVersionRequirement returns [EObject current=null] : iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF ;
    public final EObject entryRuleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalPathVersionRequirement = null;


        try {
            // InternalSemver.g:175:68: (iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF )
            // InternalSemver.g:176:2: iv_ruleLocalPathVersionRequirement= ruleLocalPathVersionRequirement EOF
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
    // InternalSemver.g:182:1: ruleLocalPathVersionRequirement returns [EObject current=null] : ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) ) ;
    public final EObject ruleLocalPathVersionRequirement() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_localPath_1_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:188:2: ( ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) ) )
            // InternalSemver.g:189:2: ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) )
            {
            // InternalSemver.g:189:2: ( ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) ) )
            // InternalSemver.g:190:3: ruleFILE_TAG ( (lv_localPath_1_0= rulePATH ) )
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
            // InternalSemver.g:197:3: ( (lv_localPath_1_0= rulePATH ) )
            // InternalSemver.g:198:4: (lv_localPath_1_0= rulePATH )
            {
            // InternalSemver.g:198:4: (lv_localPath_1_0= rulePATH )
            // InternalSemver.g:199:5: lv_localPath_1_0= rulePATH
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
    // InternalSemver.g:220:1: entryRuleURLVersionRequirement returns [EObject current=null] : iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF ;
    public final EObject entryRuleURLVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionRequirement = null;


        try {
            // InternalSemver.g:220:62: (iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF )
            // InternalSemver.g:221:2: iv_ruleURLVersionRequirement= ruleURLVersionRequirement EOF
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
    // InternalSemver.g:227:1: ruleURLVersionRequirement returns [EObject current=null] : ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? ) ;
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
            // InternalSemver.g:233:2: ( ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? ) )
            // InternalSemver.g:234:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? )
            {
            // InternalSemver.g:234:2: ( ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )? )
            // InternalSemver.g:235:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) ) (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' ) ( (lv_url_4_0= ruleURL ) ) (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )?
            {
            // InternalSemver.g:235:3: ( (lv_protocol_0_0= ruleURL_PROTOCOL ) )
            // InternalSemver.g:236:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            {
            // InternalSemver.g:236:4: (lv_protocol_0_0= ruleURL_PROTOCOL )
            // InternalSemver.g:237:5: lv_protocol_0_0= ruleURL_PROTOCOL
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

            // InternalSemver.g:254:3: (otherlv_1= ':' otherlv_2= '/' otherlv_3= '/' )
            // InternalSemver.g:255:4: otherlv_1= ':' otherlv_2= '/' otherlv_3= '/'
            {
            otherlv_1=(Token)match(input,41,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0());
              			
            }
            otherlv_2=(Token)match(input,42,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_2, grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1());
              			
            }
            otherlv_3=(Token)match(input,42,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_3, grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2());
              			
            }

            }

            // InternalSemver.g:268:3: ( (lv_url_4_0= ruleURL ) )
            // InternalSemver.g:269:4: (lv_url_4_0= ruleURL )
            {
            // InternalSemver.g:269:4: (lv_url_4_0= ruleURL )
            // InternalSemver.g:270:5: lv_url_4_0= ruleURL
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

            // InternalSemver.g:287:3: (otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==43) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:288:4: otherlv_5= '#' ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) )
                    {
                    otherlv_5=(Token)match(input,43,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0());
                      			
                    }
                    // InternalSemver.g:292:4: ( (lv_versionSpecifier_6_0= ruleURLVersionSpecifier ) )
                    // InternalSemver.g:293:5: (lv_versionSpecifier_6_0= ruleURLVersionSpecifier )
                    {
                    // InternalSemver.g:293:5: (lv_versionSpecifier_6_0= ruleURLVersionSpecifier )
                    // InternalSemver.g:294:6: lv_versionSpecifier_6_0= ruleURLVersionSpecifier
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
    // InternalSemver.g:316:1: entryRuleURLVersionSpecifier returns [EObject current=null] : iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF ;
    public final EObject entryRuleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLVersionSpecifier = null;


        try {
            // InternalSemver.g:316:60: (iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF )
            // InternalSemver.g:317:2: iv_ruleURLVersionSpecifier= ruleURLVersionSpecifier EOF
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
    // InternalSemver.g:323:1: ruleURLVersionSpecifier returns [EObject current=null] : ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) ) ;
    public final EObject ruleURLVersionSpecifier() throws RecognitionException {
        EObject current = null;

        EObject this_URLSemver_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_2_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_4_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:329:2: ( ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) ) )
            // InternalSemver.g:330:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) )
            {
            // InternalSemver.g:330:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) )
            int alt8=3;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // InternalSemver.g:331:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    {
                    // InternalSemver.g:331:3: ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver )
                    // InternalSemver.g:332:4: ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver
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
                    // InternalSemver.g:344:3: ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) )
                    {
                    // InternalSemver.g:344:3: ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) )
                    // InternalSemver.g:345:4: () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
                    {
                    // InternalSemver.g:345:4: ()
                    // InternalSemver.g:346:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSemver.g:352:4: ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
                    // InternalSemver.g:353:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
                    {
                    // InternalSemver.g:353:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
                    // InternalSemver.g:354:6: lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
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
                    // InternalSemver.g:373:3: ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    {
                    // InternalSemver.g:373:3: ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) )
                    // InternalSemver.g:374:4: () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    // InternalSemver.g:374:4: ()
                    // InternalSemver.g:375:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalSemver.g:381:4: ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSemver.g:382:5: (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSemver.g:382:5: (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSemver.g:383:6: lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:405:1: entryRuleURLSemver returns [EObject current=null] : iv_ruleURLSemver= ruleURLSemver EOF ;
    public final EObject entryRuleURLSemver() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleURLSemver = null;


        try {
            // InternalSemver.g:405:50: (iv_ruleURLSemver= ruleURLSemver EOF )
            // InternalSemver.g:406:2: iv_ruleURLSemver= ruleURLSemver EOF
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
    // InternalSemver.g:412:1: ruleURLSemver returns [EObject current=null] : ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) ) ;
    public final EObject ruleURLSemver() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_withSemverTag_1_0 = null;

        EObject lv_simpleVersion_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:418:2: ( ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) ) )
            // InternalSemver.g:419:2: ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) )
            {
            // InternalSemver.g:419:2: ( () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) ) )
            // InternalSemver.g:420:3: () ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )? ( (lv_simpleVersion_2_0= ruleSimpleVersion ) )
            {
            // InternalSemver.g:420:3: ()
            // InternalSemver.g:421:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getURLSemverAccess().getURLSemverAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:427:3: ( (lv_withSemverTag_1_0= ruleSEMVER_TAG ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_LETTER_S) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSemver.g:428:4: (lv_withSemverTag_1_0= ruleSEMVER_TAG )
                    {
                    // InternalSemver.g:428:4: (lv_withSemverTag_1_0= ruleSEMVER_TAG )
                    // InternalSemver.g:429:5: lv_withSemverTag_1_0= ruleSEMVER_TAG
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getURLSemverAccess().getWithSemverTagSEMVER_TAGParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_3);
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
                      						lv_withSemverTag_1_0 != null,
                      						"org.eclipse.n4js.semver.Semver.SEMVER_TAG");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSemver.g:446:3: ( (lv_simpleVersion_2_0= ruleSimpleVersion ) )
            // InternalSemver.g:447:4: (lv_simpleVersion_2_0= ruleSimpleVersion )
            {
            // InternalSemver.g:447:4: (lv_simpleVersion_2_0= ruleSimpleVersion )
            // InternalSemver.g:448:5: lv_simpleVersion_2_0= ruleSimpleVersion
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


    // $ANTLR start "entryRuleWorkspaceVersionRequirement"
    // InternalSemver.g:469:1: entryRuleWorkspaceVersionRequirement returns [EObject current=null] : iv_ruleWorkspaceVersionRequirement= ruleWorkspaceVersionRequirement EOF ;
    public final EObject entryRuleWorkspaceVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWorkspaceVersionRequirement = null;


        try {
            // InternalSemver.g:469:68: (iv_ruleWorkspaceVersionRequirement= ruleWorkspaceVersionRequirement EOF )
            // InternalSemver.g:470:2: iv_ruleWorkspaceVersionRequirement= ruleWorkspaceVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWorkspaceVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWorkspaceVersionRequirement=ruleWorkspaceVersionRequirement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWorkspaceVersionRequirement; 
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
    // $ANTLR end "entryRuleWorkspaceVersionRequirement"


    // $ANTLR start "ruleWorkspaceVersionRequirement"
    // InternalSemver.g:476:1: ruleWorkspaceVersionRequirement returns [EObject current=null] : ( ruleWORKSPACE_TAG ( ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) ) | ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) ) ) ) ;
    public final EObject ruleWorkspaceVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject lv_version_1_0 = null;

        AntlrDatatypeRuleToken lv_otherVersion_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:482:2: ( ( ruleWORKSPACE_TAG ( ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) ) | ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) ) ) ) )
            // InternalSemver.g:483:2: ( ruleWORKSPACE_TAG ( ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) ) | ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) ) ) )
            {
            // InternalSemver.g:483:2: ( ruleWORKSPACE_TAG ( ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) ) | ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) ) ) )
            // InternalSemver.g:484:3: ruleWORKSPACE_TAG ( ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) ) | ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) ) )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getWorkspaceVersionRequirementAccess().getWORKSPACE_TAGParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_11);
            ruleWORKSPACE_TAG();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSemver.g:491:3: ( ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) ) | ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) ) )
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // InternalSemver.g:492:4: ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) )
                    {
                    // InternalSemver.g:492:4: ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) )
                    // InternalSemver.g:493:5: ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion )
                    {
                    // InternalSemver.g:497:5: (lv_version_1_0= ruleSimpleVersion )
                    // InternalSemver.g:498:6: lv_version_1_0= ruleSimpleVersion
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getWorkspaceVersionRequirementAccess().getVersionSimpleVersionParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_version_1_0=ruleSimpleVersion();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getWorkspaceVersionRequirementRule());
                      						}
                      						set(
                      							current,
                      							"version",
                      							lv_version_1_0,
                      							"org.eclipse.n4js.semver.Semver.SimpleVersion");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:516:4: ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) )
                    {
                    // InternalSemver.g:516:4: ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) )
                    // InternalSemver.g:517:5: (lv_otherVersion_2_0= ruleWORKSPACE_VERSION )
                    {
                    // InternalSemver.g:517:5: (lv_otherVersion_2_0= ruleWORKSPACE_VERSION )
                    // InternalSemver.g:518:6: lv_otherVersion_2_0= ruleWORKSPACE_VERSION
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getWorkspaceVersionRequirementAccess().getOtherVersionWORKSPACE_VERSIONParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_otherVersion_2_0=ruleWORKSPACE_VERSION();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getWorkspaceVersionRequirementRule());
                      						}
                      						set(
                      							current,
                      							"otherVersion",
                      							lv_otherVersion_2_0,
                      							"org.eclipse.n4js.semver.Semver.WORKSPACE_VERSION");
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
    // $ANTLR end "ruleWorkspaceVersionRequirement"


    // $ANTLR start "entryRuleGitHubVersionRequirement"
    // InternalSemver.g:540:1: entryRuleGitHubVersionRequirement returns [EObject current=null] : iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF ;
    public final EObject entryRuleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGitHubVersionRequirement = null;


        try {
            // InternalSemver.g:540:65: (iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF )
            // InternalSemver.g:541:2: iv_ruleGitHubVersionRequirement= ruleGitHubVersionRequirement EOF
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
    // InternalSemver.g:547:1: ruleGitHubVersionRequirement returns [EObject current=null] : ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) ;
    public final EObject ruleGitHubVersionRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_githubUrl_0_0 = null;

        AntlrDatatypeRuleToken lv_commitISH_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:553:2: ( ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? ) )
            // InternalSemver.g:554:2: ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            {
            // InternalSemver.g:554:2: ( ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )? )
            // InternalSemver.g:555:3: ( (lv_githubUrl_0_0= ruleURL_NO_VX ) ) (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            {
            // InternalSemver.g:555:3: ( (lv_githubUrl_0_0= ruleURL_NO_VX ) )
            // InternalSemver.g:556:4: (lv_githubUrl_0_0= ruleURL_NO_VX )
            {
            // InternalSemver.g:556:4: (lv_githubUrl_0_0= ruleURL_NO_VX )
            // InternalSemver.g:557:5: lv_githubUrl_0_0= ruleURL_NO_VX
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

            // InternalSemver.g:574:3: (otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==43) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSemver.g:575:4: otherlv_1= '#' ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:579:4: ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS ) )
                    // InternalSemver.g:580:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    {
                    // InternalSemver.g:580:5: (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS )
                    // InternalSemver.g:581:6: lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS
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


    // $ANTLR start "entryRuleTagVersionRequirement"
    // InternalSemver.g:603:1: entryRuleTagVersionRequirement returns [EObject current=null] : iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF ;
    public final EObject entryRuleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTagVersionRequirement = null;


        try {
            // InternalSemver.g:603:62: (iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF )
            // InternalSemver.g:604:2: iv_ruleTagVersionRequirement= ruleTagVersionRequirement EOF
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
    // InternalSemver.g:610:1: ruleTagVersionRequirement returns [EObject current=null] : ( (lv_tagName_0_0= ruleTAG ) ) ;
    public final EObject ruleTagVersionRequirement() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_tagName_0_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:616:2: ( ( (lv_tagName_0_0= ruleTAG ) ) )
            // InternalSemver.g:617:2: ( (lv_tagName_0_0= ruleTAG ) )
            {
            // InternalSemver.g:617:2: ( (lv_tagName_0_0= ruleTAG ) )
            // InternalSemver.g:618:3: (lv_tagName_0_0= ruleTAG )
            {
            // InternalSemver.g:618:3: (lv_tagName_0_0= ruleTAG )
            // InternalSemver.g:619:4: lv_tagName_0_0= ruleTAG
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


    // $ANTLR start "entryRuleVersionRangeSetRequirement"
    // InternalSemver.g:639:1: entryRuleVersionRangeSetRequirement returns [EObject current=null] : iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF ;
    public final EObject entryRuleVersionRangeSetRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeSetRequirement = null;


        try {
            // InternalSemver.g:639:67: (iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF )
            // InternalSemver.g:640:2: iv_ruleVersionRangeSetRequirement= ruleVersionRangeSetRequirement EOF
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
    // InternalSemver.g:646:1: ruleVersionRangeSetRequirement returns [EObject current=null] : ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )? )? ) ;
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
            // InternalSemver.g:652:2: ( ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )? )? ) )
            // InternalSemver.g:653:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )? )? )
            {
            // InternalSemver.g:653:2: ( () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )? )? )
            // InternalSemver.g:654:3: () ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )? )?
            {
            // InternalSemver.g:654:3: ()
            // InternalSemver.g:655:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:661:3: ( ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )? )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=RULE_LETTER_V && LA16_0<=RULE_DIGITS)||(LA16_0>=RULE_ASTERIX && LA16_0<=RULE_LETTER_X)||(LA16_0>=50 && LA16_0<=56)) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSemver.g:662:4: ( (lv_ranges_1_0= ruleVersionRange ) ) ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )* (this_WS_6= RULE_WS )?
                    {
                    // InternalSemver.g:662:4: ( (lv_ranges_1_0= ruleVersionRange ) )
                    // InternalSemver.g:663:5: (lv_ranges_1_0= ruleVersionRange )
                    {
                    // InternalSemver.g:663:5: (lv_ranges_1_0= ruleVersionRange )
                    // InternalSemver.g:664:6: lv_ranges_1_0= ruleVersionRange
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

                    // InternalSemver.g:681:4: ( (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==RULE_WS) ) {
                            int LA14_1 = input.LA(2);

                            if ( (LA14_1==44) ) {
                                alt14=1;
                            }


                        }
                        else if ( (LA14_0==44) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalSemver.g:682:5: (this_WS_2= RULE_WS )? otherlv_3= '||' (this_WS_4= RULE_WS )? ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    {
                    	    // InternalSemver.g:682:5: (this_WS_2= RULE_WS )?
                    	    int alt12=2;
                    	    int LA12_0 = input.LA(1);

                    	    if ( (LA12_0==RULE_WS) ) {
                    	        alt12=1;
                    	    }
                    	    switch (alt12) {
                    	        case 1 :
                    	            // InternalSemver.g:683:6: this_WS_2= RULE_WS
                    	            {
                    	            this_WS_2=(Token)match(input,RULE_WS,FOLLOW_13); if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              						newLeafNode(this_WS_2, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0());
                    	              					
                    	            }

                    	            }
                    	            break;

                    	    }

                    	    otherlv_3=(Token)match(input,44,FOLLOW_14); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1());
                    	      				
                    	    }
                    	    // InternalSemver.g:692:5: (this_WS_4= RULE_WS )?
                    	    int alt13=2;
                    	    int LA13_0 = input.LA(1);

                    	    if ( (LA13_0==RULE_WS) ) {
                    	        alt13=1;
                    	    }
                    	    switch (alt13) {
                    	        case 1 :
                    	            // InternalSemver.g:693:6: this_WS_4= RULE_WS
                    	            {
                    	            this_WS_4=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              						newLeafNode(this_WS_4, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2());
                    	              					
                    	            }

                    	            }
                    	            break;

                    	    }

                    	    // InternalSemver.g:698:5: ( (lv_ranges_5_0= ruleVersionRange ) )
                    	    // InternalSemver.g:699:6: (lv_ranges_5_0= ruleVersionRange )
                    	    {
                    	    // InternalSemver.g:699:6: (lv_ranges_5_0= ruleVersionRange )
                    	    // InternalSemver.g:700:7: lv_ranges_5_0= ruleVersionRange
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
                    	    break loop14;
                        }
                    } while (true);

                    // InternalSemver.g:718:4: (this_WS_6= RULE_WS )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==RULE_WS) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalSemver.g:719:5: this_WS_6= RULE_WS
                            {
                            this_WS_6=(Token)match(input,RULE_WS,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_WS_6, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2());
                              				
                            }

                            }
                            break;

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
    // $ANTLR end "ruleVersionRangeSetRequirement"


    // $ANTLR start "entryRuleVersionRange"
    // InternalSemver.g:729:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // InternalSemver.g:729:53: (iv_ruleVersionRange= ruleVersionRange EOF )
            // InternalSemver.g:730:2: iv_ruleVersionRange= ruleVersionRange EOF
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
    // InternalSemver.g:736:1: ruleVersionRange returns [EObject current=null] : (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_VersionRangeContraint_0 = null;

        EObject this_HyphenVersionRange_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:742:2: ( (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange ) )
            // InternalSemver.g:743:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            {
            // InternalSemver.g:743:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )
            int alt17=2;
            alt17 = dfa17.predict(input);
            switch (alt17) {
                case 1 :
                    // InternalSemver.g:744:3: this_VersionRangeContraint_0= ruleVersionRangeContraint
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
                    // InternalSemver.g:753:3: this_HyphenVersionRange_1= ruleHyphenVersionRange
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
    // InternalSemver.g:765:1: entryRuleHyphenVersionRange returns [EObject current=null] : iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF ;
    public final EObject entryRuleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHyphenVersionRange = null;


        try {
            // InternalSemver.g:765:59: (iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF )
            // InternalSemver.g:766:2: iv_ruleHyphenVersionRange= ruleHyphenVersionRange EOF
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
    // InternalSemver.g:772:1: ruleHyphenVersionRange returns [EObject current=null] : ( () ( (lv_from_1_0= ruleVersionNumber ) ) this_WS_2= RULE_WS otherlv_3= '-' this_WS_4= RULE_WS ( (lv_to_5_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleHyphenVersionRange() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        Token otherlv_3=null;
        Token this_WS_4=null;
        EObject lv_from_1_0 = null;

        EObject lv_to_5_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:778:2: ( ( () ( (lv_from_1_0= ruleVersionNumber ) ) this_WS_2= RULE_WS otherlv_3= '-' this_WS_4= RULE_WS ( (lv_to_5_0= ruleVersionNumber ) ) ) )
            // InternalSemver.g:779:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) this_WS_2= RULE_WS otherlv_3= '-' this_WS_4= RULE_WS ( (lv_to_5_0= ruleVersionNumber ) ) )
            {
            // InternalSemver.g:779:2: ( () ( (lv_from_1_0= ruleVersionNumber ) ) this_WS_2= RULE_WS otherlv_3= '-' this_WS_4= RULE_WS ( (lv_to_5_0= ruleVersionNumber ) ) )
            // InternalSemver.g:780:3: () ( (lv_from_1_0= ruleVersionNumber ) ) this_WS_2= RULE_WS otherlv_3= '-' this_WS_4= RULE_WS ( (lv_to_5_0= ruleVersionNumber ) )
            {
            // InternalSemver.g:780:3: ()
            // InternalSemver.g:781:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:787:3: ( (lv_from_1_0= ruleVersionNumber ) )
            // InternalSemver.g:788:4: (lv_from_1_0= ruleVersionNumber )
            {
            // InternalSemver.g:788:4: (lv_from_1_0= ruleVersionNumber )
            // InternalSemver.g:789:5: lv_from_1_0= ruleVersionNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
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

            this_WS_2=(Token)match(input,RULE_WS,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WS_2, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2());
              		
            }
            otherlv_3=(Token)match(input,45,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3());
              		
            }
            this_WS_4=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WS_4, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4());
              		
            }
            // InternalSemver.g:818:3: ( (lv_to_5_0= ruleVersionNumber ) )
            // InternalSemver.g:819:4: (lv_to_5_0= ruleVersionNumber )
            {
            // InternalSemver.g:819:4: (lv_to_5_0= ruleVersionNumber )
            // InternalSemver.g:820:5: lv_to_5_0= ruleVersionNumber
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
    // InternalSemver.g:841:1: entryRuleVersionRangeContraint returns [EObject current=null] : iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF ;
    public final EObject entryRuleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRangeContraint = null;


        try {
            // InternalSemver.g:841:62: (iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF )
            // InternalSemver.g:842:2: iv_ruleVersionRangeContraint= ruleVersionRangeContraint EOF
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
    // InternalSemver.g:848:1: ruleVersionRangeContraint returns [EObject current=null] : ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) (this_WS_2= RULE_WS ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) ;
    public final EObject ruleVersionRangeContraint() throws RecognitionException {
        EObject current = null;

        Token this_WS_2=null;
        EObject lv_versionConstraints_1_0 = null;

        EObject lv_versionConstraints_3_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:854:2: ( ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) (this_WS_2= RULE_WS ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* ) )
            // InternalSemver.g:855:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) (this_WS_2= RULE_WS ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            {
            // InternalSemver.g:855:2: ( () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) (this_WS_2= RULE_WS ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )* )
            // InternalSemver.g:856:3: () ( (lv_versionConstraints_1_0= ruleSimpleVersion ) ) (this_WS_2= RULE_WS ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            {
            // InternalSemver.g:856:3: ()
            // InternalSemver.g:857:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0(),
              					current);
              			
            }

            }

            // InternalSemver.g:863:3: ( (lv_versionConstraints_1_0= ruleSimpleVersion ) )
            // InternalSemver.g:864:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            {
            // InternalSemver.g:864:4: (lv_versionConstraints_1_0= ruleSimpleVersion )
            // InternalSemver.g:865:5: lv_versionConstraints_1_0= ruleSimpleVersion
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

            // InternalSemver.g:882:3: (this_WS_2= RULE_WS ( (lv_versionConstraints_3_0= ruleSimpleVersion ) ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==RULE_WS) ) {
                    int LA18_1 = input.LA(2);

                    if ( ((LA18_1>=RULE_LETTER_V && LA18_1<=RULE_DIGITS)||(LA18_1>=RULE_ASTERIX && LA18_1<=RULE_LETTER_X)||(LA18_1>=50 && LA18_1<=56)) ) {
                        alt18=1;
                    }


                }


                switch (alt18) {
            	case 1 :
            	    // InternalSemver.g:883:4: this_WS_2= RULE_WS ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    {
            	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_WS_2, grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0());
            	      			
            	    }
            	    // InternalSemver.g:887:4: ( (lv_versionConstraints_3_0= ruleSimpleVersion ) )
            	    // InternalSemver.g:888:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    {
            	    // InternalSemver.g:888:5: (lv_versionConstraints_3_0= ruleSimpleVersion )
            	    // InternalSemver.g:889:6: lv_versionConstraints_3_0= ruleSimpleVersion
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
    // InternalSemver.g:911:1: entryRuleSimpleVersion returns [EObject current=null] : iv_ruleSimpleVersion= ruleSimpleVersion EOF ;
    public final EObject entryRuleSimpleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleVersion = null;


        try {
            // InternalSemver.g:911:54: (iv_ruleSimpleVersion= ruleSimpleVersion EOF )
            // InternalSemver.g:912:2: iv_ruleSimpleVersion= ruleSimpleVersion EOF
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
    // InternalSemver.g:918:1: ruleSimpleVersion returns [EObject current=null] : ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )? )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) ) ;
    public final EObject ruleSimpleVersion() throws RecognitionException {
        EObject current = null;

        Token this_WS_1=null;
        Token lv_withLetterV_2_0=null;
        Enumerator lv_comparators_0_0 = null;

        EObject lv_number_3_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:924:2: ( ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )? )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) ) )
            // InternalSemver.g:925:2: ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )? )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) )
            {
            // InternalSemver.g:925:2: ( ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )? )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) ) )
            // InternalSemver.g:926:3: ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )? )* ( (lv_withLetterV_2_0= RULE_LETTER_V ) )? ( (lv_number_3_0= ruleVersionNumber ) )
            {
            // InternalSemver.g:926:3: ( ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )? )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=50 && LA20_0<=56)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSemver.g:927:4: ( (lv_comparators_0_0= ruleVersionComparator ) ) (this_WS_1= RULE_WS )?
            	    {
            	    // InternalSemver.g:927:4: ( (lv_comparators_0_0= ruleVersionComparator ) )
            	    // InternalSemver.g:928:5: (lv_comparators_0_0= ruleVersionComparator )
            	    {
            	    // InternalSemver.g:928:5: (lv_comparators_0_0= ruleVersionComparator )
            	    // InternalSemver.g:929:6: lv_comparators_0_0= ruleVersionComparator
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_0_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_14);
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

            	    // InternalSemver.g:946:4: (this_WS_1= RULE_WS )?
            	    int alt19=2;
            	    int LA19_0 = input.LA(1);

            	    if ( (LA19_0==RULE_WS) ) {
            	        alt19=1;
            	    }
            	    switch (alt19) {
            	        case 1 :
            	            // InternalSemver.g:947:5: this_WS_1= RULE_WS
            	            {
            	            this_WS_1=(Token)match(input,RULE_WS,FOLLOW_3); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_WS_1, grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            // InternalSemver.g:953:3: ( (lv_withLetterV_2_0= RULE_LETTER_V ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LETTER_V) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSemver.g:954:4: (lv_withLetterV_2_0= RULE_LETTER_V )
                    {
                    // InternalSemver.g:954:4: (lv_withLetterV_2_0= RULE_LETTER_V )
                    // InternalSemver.g:955:5: lv_withLetterV_2_0= RULE_LETTER_V
                    {
                    lv_withLetterV_2_0=(Token)match(input,RULE_LETTER_V,FOLLOW_3); if (state.failed) return current;
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
                      						lv_withLetterV_2_0 != null,
                      						"org.eclipse.n4js.semver.Semver.LETTER_V");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalSemver.g:971:3: ( (lv_number_3_0= ruleVersionNumber ) )
            // InternalSemver.g:972:4: (lv_number_3_0= ruleVersionNumber )
            {
            // InternalSemver.g:972:4: (lv_number_3_0= ruleVersionNumber )
            // InternalSemver.g:973:5: lv_number_3_0= ruleVersionNumber
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
    // InternalSemver.g:994:1: entryRuleVersionNumber returns [EObject current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final EObject entryRuleVersionNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionNumber = null;


        try {
            // InternalSemver.g:994:54: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalSemver.g:995:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalSemver.g:1001:1: ruleVersionNumber returns [EObject current=null] : ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) ;
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
            // InternalSemver.g:1007:2: ( ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? ) )
            // InternalSemver.g:1008:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            {
            // InternalSemver.g:1008:2: ( ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )? )
            // InternalSemver.g:1009:3: ( (lv_major_0_0= ruleVersionPart ) ) (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )? ( (lv_qualifier_7_0= ruleQualifier ) )?
            {
            // InternalSemver.g:1009:3: ( (lv_major_0_0= ruleVersionPart ) )
            // InternalSemver.g:1010:4: (lv_major_0_0= ruleVersionPart )
            {
            // InternalSemver.g:1010:4: (lv_major_0_0= ruleVersionPart )
            // InternalSemver.g:1011:5: lv_major_0_0= ruleVersionPart
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_17);
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

            // InternalSemver.g:1028:3: (otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )? )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==46) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSemver.g:1029:4: otherlv_1= '.' ( (lv_minor_2_0= ruleVersionPart ) ) (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    {
                    otherlv_1=(Token)match(input,46,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:1033:4: ( (lv_minor_2_0= ruleVersionPart ) )
                    // InternalSemver.g:1034:5: (lv_minor_2_0= ruleVersionPart )
                    {
                    // InternalSemver.g:1034:5: (lv_minor_2_0= ruleVersionPart )
                    // InternalSemver.g:1035:6: lv_minor_2_0= ruleVersionPart
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
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

                    // InternalSemver.g:1052:4: (otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )* )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==46) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalSemver.g:1053:5: otherlv_3= '.' ( (lv_patch_4_0= ruleVersionPart ) ) (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            {
                            otherlv_3=(Token)match(input,46,FOLLOW_3); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            // InternalSemver.g:1057:5: ( (lv_patch_4_0= ruleVersionPart ) )
                            // InternalSemver.g:1058:6: (lv_patch_4_0= ruleVersionPart )
                            {
                            // InternalSemver.g:1058:6: (lv_patch_4_0= ruleVersionPart )
                            // InternalSemver.g:1059:7: lv_patch_4_0= ruleVersionPart
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0());
                              						
                            }
                            pushFollow(FOLLOW_17);
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

                            // InternalSemver.g:1076:5: (otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) ) )*
                            loop22:
                            do {
                                int alt22=2;
                                int LA22_0 = input.LA(1);

                                if ( (LA22_0==46) ) {
                                    alt22=1;
                                }


                                switch (alt22) {
                            	case 1 :
                            	    // InternalSemver.g:1077:6: otherlv_5= '.' ( (lv_extended_6_0= ruleVersionPart ) )
                            	    {
                            	    otherlv_5=(Token)match(input,46,FOLLOW_3); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_5, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0());
                            	      					
                            	    }
                            	    // InternalSemver.g:1081:6: ( (lv_extended_6_0= ruleVersionPart ) )
                            	    // InternalSemver.g:1082:7: (lv_extended_6_0= ruleVersionPart )
                            	    {
                            	    // InternalSemver.g:1082:7: (lv_extended_6_0= ruleVersionPart )
                            	    // InternalSemver.g:1083:8: lv_extended_6_0= ruleVersionPart
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_17);
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

            // InternalSemver.g:1103:3: ( (lv_qualifier_7_0= ruleQualifier ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==45||LA25_0==47) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSemver.g:1104:4: (lv_qualifier_7_0= ruleQualifier )
                    {
                    // InternalSemver.g:1104:4: (lv_qualifier_7_0= ruleQualifier )
                    // InternalSemver.g:1105:5: lv_qualifier_7_0= ruleQualifier
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
    // InternalSemver.g:1126:1: entryRuleVersionPart returns [EObject current=null] : iv_ruleVersionPart= ruleVersionPart EOF ;
    public final EObject entryRuleVersionPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionPart = null;


        try {
            // InternalSemver.g:1126:52: (iv_ruleVersionPart= ruleVersionPart EOF )
            // InternalSemver.g:1127:2: iv_ruleVersionPart= ruleVersionPart EOF
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
    // InternalSemver.g:1133:1: ruleVersionPart returns [EObject current=null] : ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) ;
    public final EObject ruleVersionPart() throws RecognitionException {
        EObject current = null;

        Token lv_numberRaw_1_0=null;
        AntlrDatatypeRuleToken lv_wildcard_0_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:1139:2: ( ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) ) )
            // InternalSemver.g:1140:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            {
            // InternalSemver.g:1140:2: ( ( (lv_wildcard_0_0= ruleWILDCARD ) ) | ( (lv_numberRaw_1_0= RULE_DIGITS ) ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>=RULE_ASTERIX && LA26_0<=RULE_LETTER_X)) ) {
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
                    // InternalSemver.g:1141:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    {
                    // InternalSemver.g:1141:3: ( (lv_wildcard_0_0= ruleWILDCARD ) )
                    // InternalSemver.g:1142:4: (lv_wildcard_0_0= ruleWILDCARD )
                    {
                    // InternalSemver.g:1142:4: (lv_wildcard_0_0= ruleWILDCARD )
                    // InternalSemver.g:1143:5: lv_wildcard_0_0= ruleWILDCARD
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
                      						lv_wildcard_0_0 != null,
                      						"org.eclipse.n4js.semver.Semver.WILDCARD");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1161:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    {
                    // InternalSemver.g:1161:3: ( (lv_numberRaw_1_0= RULE_DIGITS ) )
                    // InternalSemver.g:1162:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    {
                    // InternalSemver.g:1162:4: (lv_numberRaw_1_0= RULE_DIGITS )
                    // InternalSemver.g:1163:5: lv_numberRaw_1_0= RULE_DIGITS
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
    // InternalSemver.g:1183:1: entryRuleQualifier returns [EObject current=null] : iv_ruleQualifier= ruleQualifier EOF ;
    public final EObject entryRuleQualifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifier = null;


        try {
            // InternalSemver.g:1183:50: (iv_ruleQualifier= ruleQualifier EOF )
            // InternalSemver.g:1184:2: iv_ruleQualifier= ruleQualifier EOF
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
    // InternalSemver.g:1190:1: ruleQualifier returns [EObject current=null] : ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )? ) | (otherlv_4= '+' ( (lv_buildMetadata_5_0= ruleQualifierTag ) ) ) ) ;
    public final EObject ruleQualifier() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_preRelease_1_0 = null;

        EObject lv_buildMetadata_3_0 = null;

        EObject lv_buildMetadata_5_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:1196:2: ( ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )? ) | (otherlv_4= '+' ( (lv_buildMetadata_5_0= ruleQualifierTag ) ) ) ) )
            // InternalSemver.g:1197:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )? ) | (otherlv_4= '+' ( (lv_buildMetadata_5_0= ruleQualifierTag ) ) ) )
            {
            // InternalSemver.g:1197:2: ( (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )? ) | (otherlv_4= '+' ( (lv_buildMetadata_5_0= ruleQualifierTag ) ) ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==45) ) {
                alt28=1;
            }
            else if ( (LA28_0==47) ) {
                alt28=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // InternalSemver.g:1198:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )? )
                    {
                    // InternalSemver.g:1198:3: (otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )? )
                    // InternalSemver.g:1199:4: otherlv_0= '-' ( (lv_preRelease_1_0= ruleQualifierTag ) ) (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )?
                    {
                    otherlv_0=(Token)match(input,45,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
                      			
                    }
                    // InternalSemver.g:1203:4: ( (lv_preRelease_1_0= ruleQualifierTag ) )
                    // InternalSemver.g:1204:5: (lv_preRelease_1_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1204:5: (lv_preRelease_1_0= ruleQualifierTag )
                    // InternalSemver.g:1205:6: lv_preRelease_1_0= ruleQualifierTag
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
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

                    // InternalSemver.g:1222:4: (otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) ) )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==47) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalSemver.g:1223:5: otherlv_2= '+' ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                            {
                            otherlv_2=(Token)match(input,47,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_0_2_0());
                              				
                            }
                            // InternalSemver.g:1227:5: ( (lv_buildMetadata_3_0= ruleQualifierTag ) )
                            // InternalSemver.g:1228:6: (lv_buildMetadata_3_0= ruleQualifierTag )
                            {
                            // InternalSemver.g:1228:6: (lv_buildMetadata_3_0= ruleQualifierTag )
                            // InternalSemver.g:1229:7: lv_buildMetadata_3_0= ruleQualifierTag
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_0_2_1_0());
                              						
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
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1249:3: (otherlv_4= '+' ( (lv_buildMetadata_5_0= ruleQualifierTag ) ) )
                    {
                    // InternalSemver.g:1249:3: (otherlv_4= '+' ( (lv_buildMetadata_5_0= ruleQualifierTag ) ) )
                    // InternalSemver.g:1250:4: otherlv_4= '+' ( (lv_buildMetadata_5_0= ruleQualifierTag ) )
                    {
                    otherlv_4=(Token)match(input,47,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
                      			
                    }
                    // InternalSemver.g:1254:4: ( (lv_buildMetadata_5_0= ruleQualifierTag ) )
                    // InternalSemver.g:1255:5: (lv_buildMetadata_5_0= ruleQualifierTag )
                    {
                    // InternalSemver.g:1255:5: (lv_buildMetadata_5_0= ruleQualifierTag )
                    // InternalSemver.g:1256:6: lv_buildMetadata_5_0= ruleQualifierTag
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_buildMetadata_5_0=ruleQualifierTag();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQualifierRule());
                      						}
                      						set(
                      							current,
                      							"buildMetadata",
                      							lv_buildMetadata_5_0,
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
    // InternalSemver.g:1278:1: entryRuleQualifierTag returns [EObject current=null] : iv_ruleQualifierTag= ruleQualifierTag EOF ;
    public final EObject entryRuleQualifierTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQualifierTag = null;


        try {
            // InternalSemver.g:1278:53: (iv_ruleQualifierTag= ruleQualifierTag EOF )
            // InternalSemver.g:1279:2: iv_ruleQualifierTag= ruleQualifierTag EOF
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
    // InternalSemver.g:1285:1: ruleQualifierTag returns [EObject current=null] : ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) ;
    public final EObject ruleQualifierTag() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_parts_0_0 = null;

        AntlrDatatypeRuleToken lv_parts_2_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:1291:2: ( ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* ) )
            // InternalSemver.g:1292:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            {
            // InternalSemver.g:1292:2: ( ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )* )
            // InternalSemver.g:1293:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) ) (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            {
            // InternalSemver.g:1293:3: ( (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:1294:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:1294:4: (lv_parts_0_0= ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:1295:5: lv_parts_0_0= ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_19);
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

            // InternalSemver.g:1312:3: (otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==46) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSemver.g:1313:4: otherlv_1= '.' ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    {
            	    otherlv_1=(Token)match(input,46,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    // InternalSemver.g:1317:4: ( (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS ) )
            	    // InternalSemver.g:1318:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    {
            	    // InternalSemver.g:1318:5: (lv_parts_2_0= ruleALPHA_NUMERIC_CHARS )
            	    // InternalSemver.g:1319:6: lv_parts_2_0= ruleALPHA_NUMERIC_CHARS
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_19);
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
    // InternalSemver.g:1341:1: entryRuleFILE_TAG returns [String current=null] : iv_ruleFILE_TAG= ruleFILE_TAG EOF ;
    public final String entryRuleFILE_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFILE_TAG = null;


        try {
            // InternalSemver.g:1341:48: (iv_ruleFILE_TAG= ruleFILE_TAG EOF )
            // InternalSemver.g:1342:2: iv_ruleFILE_TAG= ruleFILE_TAG EOF
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
    // InternalSemver.g:1348:1: ruleFILE_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' ) ;
    public final AntlrDatatypeRuleToken ruleFILE_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_F_0=null;
        Token this_LETTER_I_1=null;
        Token this_LETTER_L_2=null;
        Token this_LETTER_E_3=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSemver.g:1354:2: ( (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' ) )
            // InternalSemver.g:1355:2: (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' )
            {
            // InternalSemver.g:1355:2: (this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':' )
            // InternalSemver.g:1356:3: this_LETTER_F_0= RULE_LETTER_F this_LETTER_I_1= RULE_LETTER_I this_LETTER_L_2= RULE_LETTER_L this_LETTER_E_3= RULE_LETTER_E kw= ':'
            {
            this_LETTER_F_0=(Token)match(input,RULE_LETTER_F,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_F_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_F_0, grammarAccess.getFILE_TAGAccess().getLETTER_FTerminalRuleCall_0());
              		
            }
            this_LETTER_I_1=(Token)match(input,RULE_LETTER_I,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_I_1);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_I_1, grammarAccess.getFILE_TAGAccess().getLETTER_ITerminalRuleCall_1());
              		
            }
            this_LETTER_L_2=(Token)match(input,RULE_LETTER_L,FOLLOW_22); if (state.failed) return current;
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
            kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
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
    // InternalSemver.g:1393:1: entryRuleSEMVER_TAG returns [String current=null] : iv_ruleSEMVER_TAG= ruleSEMVER_TAG EOF ;
    public final String entryRuleSEMVER_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSEMVER_TAG = null;


        try {
            // InternalSemver.g:1393:50: (iv_ruleSEMVER_TAG= ruleSEMVER_TAG EOF )
            // InternalSemver.g:1394:2: iv_ruleSEMVER_TAG= ruleSEMVER_TAG EOF
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
    // InternalSemver.g:1400:1: ruleSEMVER_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' ) ;
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
            // InternalSemver.g:1406:2: ( (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' ) )
            // InternalSemver.g:1407:2: (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' )
            {
            // InternalSemver.g:1407:2: (this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':' )
            // InternalSemver.g:1408:3: this_LETTER_S_0= RULE_LETTER_S this_LETTER_E_1= RULE_LETTER_E this_LETTER_M_2= RULE_LETTER_M this_LETTER_V_3= RULE_LETTER_V this_LETTER_E_4= RULE_LETTER_E this_LETTER_R_5= RULE_LETTER_R kw= ':'
            {
            this_LETTER_S_0=(Token)match(input,RULE_LETTER_S,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_S_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_S_0, grammarAccess.getSEMVER_TAGAccess().getLETTER_STerminalRuleCall_0());
              		
            }
            this_LETTER_E_1=(Token)match(input,RULE_LETTER_E,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_E_1);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_E_1, grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_1());
              		
            }
            this_LETTER_M_2=(Token)match(input,RULE_LETTER_M,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_M_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_M_2, grammarAccess.getSEMVER_TAGAccess().getLETTER_MTerminalRuleCall_2());
              		
            }
            this_LETTER_V_3=(Token)match(input,RULE_LETTER_V,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_V_3);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_V_3, grammarAccess.getSEMVER_TAGAccess().getLETTER_VTerminalRuleCall_3());
              		
            }
            this_LETTER_E_4=(Token)match(input,RULE_LETTER_E,FOLLOW_25); if (state.failed) return current;
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
            kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleWORKSPACE_TAG"
    // InternalSemver.g:1459:1: entryRuleWORKSPACE_TAG returns [String current=null] : iv_ruleWORKSPACE_TAG= ruleWORKSPACE_TAG EOF ;
    public final String entryRuleWORKSPACE_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleWORKSPACE_TAG = null;


        try {
            // InternalSemver.g:1459:53: (iv_ruleWORKSPACE_TAG= ruleWORKSPACE_TAG EOF )
            // InternalSemver.g:1460:2: iv_ruleWORKSPACE_TAG= ruleWORKSPACE_TAG EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWORKSPACE_TAGRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWORKSPACE_TAG=ruleWORKSPACE_TAG();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWORKSPACE_TAG.getText(); 
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
    // $ANTLR end "entryRuleWORKSPACE_TAG"


    // $ANTLR start "ruleWORKSPACE_TAG"
    // InternalSemver.g:1466:1: ruleWORKSPACE_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_W_0= RULE_LETTER_W this_LETTER_O_1= RULE_LETTER_O this_LETTER_R_2= RULE_LETTER_R this_LETTER_K_3= RULE_LETTER_K this_LETTER_S_4= RULE_LETTER_S this_LETTER_P_5= RULE_LETTER_P this_LETTER_A_6= RULE_LETTER_A this_LETTER_C_7= RULE_LETTER_C this_LETTER_E_8= RULE_LETTER_E kw= ':' ) ;
    public final AntlrDatatypeRuleToken ruleWORKSPACE_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_W_0=null;
        Token this_LETTER_O_1=null;
        Token this_LETTER_R_2=null;
        Token this_LETTER_K_3=null;
        Token this_LETTER_S_4=null;
        Token this_LETTER_P_5=null;
        Token this_LETTER_A_6=null;
        Token this_LETTER_C_7=null;
        Token this_LETTER_E_8=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSemver.g:1472:2: ( (this_LETTER_W_0= RULE_LETTER_W this_LETTER_O_1= RULE_LETTER_O this_LETTER_R_2= RULE_LETTER_R this_LETTER_K_3= RULE_LETTER_K this_LETTER_S_4= RULE_LETTER_S this_LETTER_P_5= RULE_LETTER_P this_LETTER_A_6= RULE_LETTER_A this_LETTER_C_7= RULE_LETTER_C this_LETTER_E_8= RULE_LETTER_E kw= ':' ) )
            // InternalSemver.g:1473:2: (this_LETTER_W_0= RULE_LETTER_W this_LETTER_O_1= RULE_LETTER_O this_LETTER_R_2= RULE_LETTER_R this_LETTER_K_3= RULE_LETTER_K this_LETTER_S_4= RULE_LETTER_S this_LETTER_P_5= RULE_LETTER_P this_LETTER_A_6= RULE_LETTER_A this_LETTER_C_7= RULE_LETTER_C this_LETTER_E_8= RULE_LETTER_E kw= ':' )
            {
            // InternalSemver.g:1473:2: (this_LETTER_W_0= RULE_LETTER_W this_LETTER_O_1= RULE_LETTER_O this_LETTER_R_2= RULE_LETTER_R this_LETTER_K_3= RULE_LETTER_K this_LETTER_S_4= RULE_LETTER_S this_LETTER_P_5= RULE_LETTER_P this_LETTER_A_6= RULE_LETTER_A this_LETTER_C_7= RULE_LETTER_C this_LETTER_E_8= RULE_LETTER_E kw= ':' )
            // InternalSemver.g:1474:3: this_LETTER_W_0= RULE_LETTER_W this_LETTER_O_1= RULE_LETTER_O this_LETTER_R_2= RULE_LETTER_R this_LETTER_K_3= RULE_LETTER_K this_LETTER_S_4= RULE_LETTER_S this_LETTER_P_5= RULE_LETTER_P this_LETTER_A_6= RULE_LETTER_A this_LETTER_C_7= RULE_LETTER_C this_LETTER_E_8= RULE_LETTER_E kw= ':'
            {
            this_LETTER_W_0=(Token)match(input,RULE_LETTER_W,FOLLOW_26); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_W_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_W_0, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_WTerminalRuleCall_0());
              		
            }
            this_LETTER_O_1=(Token)match(input,RULE_LETTER_O,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_O_1);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_O_1, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_OTerminalRuleCall_1());
              		
            }
            this_LETTER_R_2=(Token)match(input,RULE_LETTER_R,FOLLOW_27); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_R_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_R_2, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_RTerminalRuleCall_2());
              		
            }
            this_LETTER_K_3=(Token)match(input,RULE_LETTER_K,FOLLOW_28); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_K_3);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_K_3, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_KTerminalRuleCall_3());
              		
            }
            this_LETTER_S_4=(Token)match(input,RULE_LETTER_S,FOLLOW_29); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_S_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_S_4, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_STerminalRuleCall_4());
              		
            }
            this_LETTER_P_5=(Token)match(input,RULE_LETTER_P,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_P_5);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_P_5, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_PTerminalRuleCall_5());
              		
            }
            this_LETTER_A_6=(Token)match(input,RULE_LETTER_A,FOLLOW_31); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_A_6);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_A_6, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_ATerminalRuleCall_6());
              		
            }
            this_LETTER_C_7=(Token)match(input,RULE_LETTER_C,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_C_7);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_C_7, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_CTerminalRuleCall_7());
              		
            }
            this_LETTER_E_8=(Token)match(input,RULE_LETTER_E,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_E_8);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LETTER_E_8, grammarAccess.getWORKSPACE_TAGAccess().getLETTER_ETerminalRuleCall_8());
              		
            }
            kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getWORKSPACE_TAGAccess().getColonKeyword_9());
              		
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
    // $ANTLR end "ruleWORKSPACE_TAG"


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:1546:1: entryRulePATH returns [String current=null] : iv_rulePATH= rulePATH EOF ;
    public final String entryRulePATH() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePATH = null;


        try {
            // InternalSemver.g:1546:44: (iv_rulePATH= rulePATH EOF )
            // InternalSemver.g:1547:2: iv_rulePATH= rulePATH EOF
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
    // InternalSemver.g:1553:1: rulePATH returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '/' | kw= '.' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )+ ;
    public final AntlrDatatypeRuleToken rulePATH() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_4 = null;



        	enterRule();

        try {
            // InternalSemver.g:1559:2: ( (kw= '/' | kw= '.' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )+ )
            // InternalSemver.g:1560:2: (kw= '/' | kw= '.' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )+
            {
            // InternalSemver.g:1560:2: (kw= '/' | kw= '.' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )+
            int cnt30=0;
            loop30:
            do {
                int alt30=6;
                switch ( input.LA(1) ) {
                case 42:
                    {
                    alt30=1;
                    }
                    break;
                case 46:
                    {
                    alt30=2;
                    }
                    break;
                case 48:
                    {
                    alt30=3;
                    }
                    break;
                case 49:
                    {
                    alt30=4;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_DIGITS:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_W:
                case RULE_LETTER_O:
                case RULE_LETTER_K:
                case RULE_LETTER_P:
                case RULE_LETTER_A:
                case RULE_LETTER_C:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                case 45:
                    {
                    alt30=5;
                    }
                    break;

                }

                switch (alt30) {
            	case 1 :
            	    // InternalSemver.g:1561:3: kw= '/'
            	    {
            	    kw=(Token)match(input,42,FOLLOW_32); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().getSolidusKeyword_0());
            	      		
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1567:3: kw= '.'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_32); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().getFullStopKeyword_1());
            	      		
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1573:3: kw= '@'
            	    {
            	    kw=(Token)match(input,48,FOLLOW_32); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().getCommercialAtKeyword_2());
            	      		
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1579:3: kw= '_'
            	    {
            	    kw=(Token)match(input,49,FOLLOW_32); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getPATHAccess().get_Keyword_3());
            	      		
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1585:3: this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARParserRuleCall_4());
            	      		
            	    }
            	    pushFollow(FOLLOW_32);
            	    this_ALPHA_NUMERIC_CHAR_4=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_ALPHA_NUMERIC_CHAR_4);
            	      		
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
    // InternalSemver.g:1599:1: entryRuleURL_PROTOCOL returns [String current=null] : iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF ;
    public final String entryRuleURL_PROTOCOL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL_PROTOCOL = null;


        try {
            // InternalSemver.g:1599:52: (iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF )
            // InternalSemver.g:1600:2: iv_ruleURL_PROTOCOL= ruleURL_PROTOCOL EOF
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
    // InternalSemver.g:1606:1: ruleURL_PROTOCOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ ) ;
    public final AntlrDatatypeRuleToken ruleURL_PROTOCOL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_LETTER_NO_VX_0 = null;

        AntlrDatatypeRuleToken this_LETTER_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:1612:2: ( (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ ) )
            // InternalSemver.g:1613:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ )
            {
            // InternalSemver.g:1613:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+ )
            // InternalSemver.g:1614:3: this_LETTER_NO_VX_0= ruleLETTER_NO_VX (this_LETTER_1= ruleLETTER | kw= '+' )+
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_33);
            this_LETTER_NO_VX_0=ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_NO_VX_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalSemver.g:1624:3: (this_LETTER_1= ruleLETTER | kw= '+' )+
            int cnt31=0;
            loop31:
            do {
                int alt31=3;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==RULE_LETTER_V||(LA31_0>=RULE_LETTER_F && LA31_0<=RULE_LETTER_C)||(LA31_0>=RULE_LETTER_X && LA31_0<=RULE_LETTER_OTHER)) ) {
                    alt31=1;
                }
                else if ( (LA31_0==47) ) {
                    alt31=2;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:1625:4: this_LETTER_1= ruleLETTER
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURL_PROTOCOLAccess().getLETTERParserRuleCall_1_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_34);
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
            	    // InternalSemver.g:1636:4: kw= '+'
            	    {
            	    kw=(Token)match(input,47,FOLLOW_34); if (state.failed) return current;
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
    // InternalSemver.g:1646:1: entryRuleURL returns [String current=null] : iv_ruleURL= ruleURL EOF ;
    public final String entryRuleURL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL = null;


        try {
            // InternalSemver.g:1646:43: (iv_ruleURL= ruleURL EOF )
            // InternalSemver.g:1647:2: iv_ruleURL= ruleURL EOF
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
    // InternalSemver.g:1653:1: ruleURL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '_' | this_ALPHA_NUMERIC_CHAR_1= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_11= ruleALPHA_NUMERIC_CHAR )* ) ;
    public final AntlrDatatypeRuleToken ruleURL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_1 = null;

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_11 = null;



        	enterRule();

        try {
            // InternalSemver.g:1659:2: ( ( (kw= '_' | this_ALPHA_NUMERIC_CHAR_1= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_11= ruleALPHA_NUMERIC_CHAR )* ) )
            // InternalSemver.g:1660:2: ( (kw= '_' | this_ALPHA_NUMERIC_CHAR_1= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_11= ruleALPHA_NUMERIC_CHAR )* )
            {
            // InternalSemver.g:1660:2: ( (kw= '_' | this_ALPHA_NUMERIC_CHAR_1= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_11= ruleALPHA_NUMERIC_CHAR )* )
            // InternalSemver.g:1661:3: (kw= '_' | this_ALPHA_NUMERIC_CHAR_1= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_11= ruleALPHA_NUMERIC_CHAR )*
            {
            // InternalSemver.g:1661:3: (kw= '_' | this_ALPHA_NUMERIC_CHAR_1= ruleALPHA_NUMERIC_CHAR )*
            loop32:
            do {
                int alt32=3;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==49) ) {
                    alt32=1;
                }
                else if ( ((LA32_0>=RULE_LETTER_V && LA32_0<=RULE_LETTER_C)||(LA32_0>=RULE_LETTER_X && LA32_0<=RULE_LETTER_OTHER)||LA32_0==45) ) {
                    alt32=2;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:1662:4: kw= '_'
            	    {
            	    kw=(Token)match(input,49,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().get_Keyword_0_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1668:4: this_ALPHA_NUMERIC_CHAR_1= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARParserRuleCall_0_1());
            	      			
            	    }
            	    pushFollow(FOLLOW_8);
            	    this_ALPHA_NUMERIC_CHAR_1=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHAR_1);
            	      			
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

            // InternalSemver.g:1679:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' )
            int alt33=4;
            switch ( input.LA(1) ) {
            case 42:
                {
                alt33=1;
                }
                break;
            case 46:
                {
                alt33=2;
                }
                break;
            case 41:
                {
                alt33=3;
                }
                break;
            case 48:
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
                    // InternalSemver.g:1680:4: kw= '/'
                    {
                    kw=(Token)match(input,42,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1686:4: kw= '.'
                    {
                    kw=(Token)match(input,46,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1692:4: kw= ':'
                    {
                    kw=(Token)match(input,41,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_1_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSemver.g:1698:4: kw= '@'
                    {
                    kw=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_1_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1704:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_11= ruleALPHA_NUMERIC_CHAR )*
            loop34:
            do {
                int alt34=7;
                switch ( input.LA(1) ) {
                case 42:
                    {
                    alt34=1;
                    }
                    break;
                case 46:
                    {
                    alt34=2;
                    }
                    break;
                case 41:
                    {
                    alt34=3;
                    }
                    break;
                case 48:
                    {
                    alt34=4;
                    }
                    break;
                case 49:
                    {
                    alt34=5;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_DIGITS:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_W:
                case RULE_LETTER_O:
                case RULE_LETTER_K:
                case RULE_LETTER_P:
                case RULE_LETTER_A:
                case RULE_LETTER_C:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                case 45:
                    {
                    alt34=6;
                    }
                    break;

                }

                switch (alt34) {
            	case 1 :
            	    // InternalSemver.g:1705:4: kw= '/'
            	    {
            	    kw=(Token)match(input,42,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_2_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1711:4: kw= '.'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_2_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1717:4: kw= ':'
            	    {
            	    kw=(Token)match(input,41,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_2_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1723:4: kw= '@'
            	    {
            	    kw=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_2_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1729:4: kw= '_'
            	    {
            	    kw=(Token)match(input,49,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURLAccess().get_Keyword_2_4());
            	      			
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1735:4: this_ALPHA_NUMERIC_CHAR_11= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARParserRuleCall_2_5());
            	      			
            	    }
            	    pushFollow(FOLLOW_35);
            	    this_ALPHA_NUMERIC_CHAR_11=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHAR_11);
            	      			
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
    // InternalSemver.g:1750:1: entryRuleURL_NO_VX returns [String current=null] : iv_ruleURL_NO_VX= ruleURL_NO_VX EOF ;
    public final String entryRuleURL_NO_VX() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleURL_NO_VX = null;


        try {
            // InternalSemver.g:1750:49: (iv_ruleURL_NO_VX= ruleURL_NO_VX EOF )
            // InternalSemver.g:1751:2: iv_ruleURL_NO_VX= ruleURL_NO_VX EOF
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
    // InternalSemver.g:1757:1: ruleURL_NO_VX returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '_' | kw= '-' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_14= ruleALPHA_NUMERIC_CHAR )* ) ;
    public final AntlrDatatypeRuleToken ruleURL_NO_VX() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_LETTER_NO_VX_2 = null;

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_4 = null;

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_14 = null;



        	enterRule();

        try {
            // InternalSemver.g:1763:2: ( ( (kw= '_' | kw= '-' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_14= ruleALPHA_NUMERIC_CHAR )* ) )
            // InternalSemver.g:1764:2: ( (kw= '_' | kw= '-' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_14= ruleALPHA_NUMERIC_CHAR )* )
            {
            // InternalSemver.g:1764:2: ( (kw= '_' | kw= '-' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_14= ruleALPHA_NUMERIC_CHAR )* )
            // InternalSemver.g:1765:3: (kw= '_' | kw= '-' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) (kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )* (kw= '/' | kw= '.' | kw= ':' | kw= '@' ) (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_14= ruleALPHA_NUMERIC_CHAR )*
            {
            // InternalSemver.g:1765:3: (kw= '_' | kw= '-' | this_LETTER_NO_VX_2= ruleLETTER_NO_VX )
            int alt35=3;
            switch ( input.LA(1) ) {
            case 49:
                {
                alt35=1;
                }
                break;
            case 45:
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
            case RULE_LETTER_W:
            case RULE_LETTER_O:
            case RULE_LETTER_K:
            case RULE_LETTER_P:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
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
                    // InternalSemver.g:1766:4: kw= '_'
                    {
                    kw=(Token)match(input,49,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1772:4: kw= '-'
                    {
                    kw=(Token)match(input,45,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1778:4: this_LETTER_NO_VX_2= ruleLETTER_NO_VX
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

            // InternalSemver.g:1789:3: (kw= '_' | this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR )*
            loop36:
            do {
                int alt36=3;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==49) ) {
                    alt36=1;
                }
                else if ( ((LA36_0>=RULE_LETTER_V && LA36_0<=RULE_LETTER_C)||(LA36_0>=RULE_LETTER_X && LA36_0<=RULE_LETTER_OTHER)||LA36_0==45) ) {
                    alt36=2;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSemver.g:1790:4: kw= '_'
            	    {
            	    kw=(Token)match(input,49,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_1_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1796:4: this_ALPHA_NUMERIC_CHAR_4= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getALPHA_NUMERIC_CHARParserRuleCall_1_1());
            	      			
            	    }
            	    pushFollow(FOLLOW_8);
            	    this_ALPHA_NUMERIC_CHAR_4=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHAR_4);
            	      			
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

            // InternalSemver.g:1807:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' )
            int alt37=4;
            switch ( input.LA(1) ) {
            case 42:
                {
                alt37=1;
                }
                break;
            case 46:
                {
                alt37=2;
                }
                break;
            case 41:
                {
                alt37=3;
                }
                break;
            case 48:
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
                    // InternalSemver.g:1808:4: kw= '/'
                    {
                    kw=(Token)match(input,42,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:1814:4: kw= '.'
                    {
                    kw=(Token)match(input,46,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:1820:4: kw= ':'
                    {
                    kw=(Token)match(input,41,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalSemver.g:1826:4: kw= '@'
                    {
                    kw=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3());
                      			
                    }

                    }
                    break;

            }

            // InternalSemver.g:1832:3: (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | this_ALPHA_NUMERIC_CHAR_14= ruleALPHA_NUMERIC_CHAR )*
            loop38:
            do {
                int alt38=7;
                switch ( input.LA(1) ) {
                case 42:
                    {
                    alt38=1;
                    }
                    break;
                case 46:
                    {
                    alt38=2;
                    }
                    break;
                case 41:
                    {
                    alt38=3;
                    }
                    break;
                case 48:
                    {
                    alt38=4;
                    }
                    break;
                case 49:
                    {
                    alt38=5;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_DIGITS:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_W:
                case RULE_LETTER_O:
                case RULE_LETTER_K:
                case RULE_LETTER_P:
                case RULE_LETTER_A:
                case RULE_LETTER_C:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                case 45:
                    {
                    alt38=6;
                    }
                    break;

                }

                switch (alt38) {
            	case 1 :
            	    // InternalSemver.g:1833:4: kw= '/'
            	    {
            	    kw=(Token)match(input,42,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0());
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1839:4: kw= '.'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1());
            	      			
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1845:4: kw= ':'
            	    {
            	    kw=(Token)match(input,41,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2());
            	      			
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1851:4: kw= '@'
            	    {
            	    kw=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3());
            	      			
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1857:4: kw= '_'
            	    {
            	    kw=(Token)match(input,49,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_3_4());
            	      			
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1863:4: this_ALPHA_NUMERIC_CHAR_14= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getALPHA_NUMERIC_CHARParserRuleCall_3_5());
            	      			
            	    }
            	    pushFollow(FOLLOW_35);
            	    this_ALPHA_NUMERIC_CHAR_14=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ALPHA_NUMERIC_CHAR_14);
            	      			
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
    // InternalSemver.g:1878:1: entryRuleTAG returns [String current=null] : iv_ruleTAG= ruleTAG EOF ;
    public final String entryRuleTAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTAG = null;


        try {
            // InternalSemver.g:1878:43: (iv_ruleTAG= ruleTAG EOF )
            // InternalSemver.g:1879:2: iv_ruleTAG= ruleTAG EOF
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
    // InternalSemver.g:1885:1: ruleTAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_NO_VX_0= ruleLETTER_NO_VX this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS ) ;
    public final AntlrDatatypeRuleToken ruleTAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_LETTER_NO_VX_0 = null;

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHARS_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:1891:2: ( (this_LETTER_NO_VX_0= ruleLETTER_NO_VX this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:1892:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:1892:2: (this_LETTER_NO_VX_0= ruleLETTER_NO_VX this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:1893:3: this_LETTER_NO_VX_0= ruleLETTER_NO_VX this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTAGAccess().getLETTER_NO_VXParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_10);
            this_LETTER_NO_VX_0=ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LETTER_NO_VX_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTAGAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_ALPHA_NUMERIC_CHARS_1=ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ALPHA_NUMERIC_CHARS_1);
              		
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
    // $ANTLR end "ruleTAG"


    // $ANTLR start "entryRuleWORKSPACE_VERSION"
    // InternalSemver.g:1917:1: entryRuleWORKSPACE_VERSION returns [String current=null] : iv_ruleWORKSPACE_VERSION= ruleWORKSPACE_VERSION EOF ;
    public final String entryRuleWORKSPACE_VERSION() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleWORKSPACE_VERSION = null;


        try {
            // InternalSemver.g:1917:57: (iv_ruleWORKSPACE_VERSION= ruleWORKSPACE_VERSION EOF )
            // InternalSemver.g:1918:2: iv_ruleWORKSPACE_VERSION= ruleWORKSPACE_VERSION EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWORKSPACE_VERSIONRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWORKSPACE_VERSION=ruleWORKSPACE_VERSION();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWORKSPACE_VERSION.getText(); 
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
    // $ANTLR end "entryRuleWORKSPACE_VERSION"


    // $ANTLR start "ruleWORKSPACE_VERSION"
    // InternalSemver.g:1924:1: ruleWORKSPACE_VERSION returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | kw= '=' | kw= '~' | kw= '^' | kw= '<' | kw= '>' | kw= '<=' | kw= '>=' | this_ASTERIX_12= RULE_ASTERIX | this_ALPHA_NUMERIC_CHAR_13= ruleALPHA_NUMERIC_CHAR )+ ;
    public final AntlrDatatypeRuleToken ruleWORKSPACE_VERSION() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_ASTERIX_12=null;
        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_13 = null;



        	enterRule();

        try {
            // InternalSemver.g:1930:2: ( (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | kw= '=' | kw= '~' | kw= '^' | kw= '<' | kw= '>' | kw= '<=' | kw= '>=' | this_ASTERIX_12= RULE_ASTERIX | this_ALPHA_NUMERIC_CHAR_13= ruleALPHA_NUMERIC_CHAR )+ )
            // InternalSemver.g:1931:2: (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | kw= '=' | kw= '~' | kw= '^' | kw= '<' | kw= '>' | kw= '<=' | kw= '>=' | this_ASTERIX_12= RULE_ASTERIX | this_ALPHA_NUMERIC_CHAR_13= ruleALPHA_NUMERIC_CHAR )+
            {
            // InternalSemver.g:1931:2: (kw= '/' | kw= '.' | kw= ':' | kw= '@' | kw= '_' | kw= '=' | kw= '~' | kw= '^' | kw= '<' | kw= '>' | kw= '<=' | kw= '>=' | this_ASTERIX_12= RULE_ASTERIX | this_ALPHA_NUMERIC_CHAR_13= ruleALPHA_NUMERIC_CHAR )+
            int cnt39=0;
            loop39:
            do {
                int alt39=15;
                switch ( input.LA(1) ) {
                case 42:
                    {
                    alt39=1;
                    }
                    break;
                case 46:
                    {
                    alt39=2;
                    }
                    break;
                case 41:
                    {
                    alt39=3;
                    }
                    break;
                case 48:
                    {
                    alt39=4;
                    }
                    break;
                case 49:
                    {
                    alt39=5;
                    }
                    break;
                case 50:
                    {
                    alt39=6;
                    }
                    break;
                case 51:
                    {
                    alt39=7;
                    }
                    break;
                case 52:
                    {
                    alt39=8;
                    }
                    break;
                case 53:
                    {
                    alt39=9;
                    }
                    break;
                case 54:
                    {
                    alt39=10;
                    }
                    break;
                case 55:
                    {
                    alt39=11;
                    }
                    break;
                case 56:
                    {
                    alt39=12;
                    }
                    break;
                case RULE_ASTERIX:
                    {
                    alt39=13;
                    }
                    break;
                case RULE_LETTER_V:
                case RULE_DIGITS:
                case RULE_LETTER_F:
                case RULE_LETTER_I:
                case RULE_LETTER_L:
                case RULE_LETTER_E:
                case RULE_LETTER_S:
                case RULE_LETTER_M:
                case RULE_LETTER_R:
                case RULE_LETTER_W:
                case RULE_LETTER_O:
                case RULE_LETTER_K:
                case RULE_LETTER_P:
                case RULE_LETTER_A:
                case RULE_LETTER_C:
                case RULE_LETTER_X:
                case RULE_LETTER_OTHER:
                case 45:
                    {
                    alt39=14;
                    }
                    break;

                }

                switch (alt39) {
            	case 1 :
            	    // InternalSemver.g:1932:3: kw= '/'
            	    {
            	    kw=(Token)match(input,42,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getSolidusKeyword_0());
            	      		
            	    }

            	    }
            	    break;
            	case 2 :
            	    // InternalSemver.g:1938:3: kw= '.'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getFullStopKeyword_1());
            	      		
            	    }

            	    }
            	    break;
            	case 3 :
            	    // InternalSemver.g:1944:3: kw= ':'
            	    {
            	    kw=(Token)match(input,41,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getColonKeyword_2());
            	      		
            	    }

            	    }
            	    break;
            	case 4 :
            	    // InternalSemver.g:1950:3: kw= '@'
            	    {
            	    kw=(Token)match(input,48,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getCommercialAtKeyword_3());
            	      		
            	    }

            	    }
            	    break;
            	case 5 :
            	    // InternalSemver.g:1956:3: kw= '_'
            	    {
            	    kw=(Token)match(input,49,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().get_Keyword_4());
            	      		
            	    }

            	    }
            	    break;
            	case 6 :
            	    // InternalSemver.g:1962:3: kw= '='
            	    {
            	    kw=(Token)match(input,50,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getEqualsSignKeyword_5());
            	      		
            	    }

            	    }
            	    break;
            	case 7 :
            	    // InternalSemver.g:1968:3: kw= '~'
            	    {
            	    kw=(Token)match(input,51,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getTildeKeyword_6());
            	      		
            	    }

            	    }
            	    break;
            	case 8 :
            	    // InternalSemver.g:1974:3: kw= '^'
            	    {
            	    kw=(Token)match(input,52,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getCircumflexAccentKeyword_7());
            	      		
            	    }

            	    }
            	    break;
            	case 9 :
            	    // InternalSemver.g:1980:3: kw= '<'
            	    {
            	    kw=(Token)match(input,53,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getLessThanSignKeyword_8());
            	      		
            	    }

            	    }
            	    break;
            	case 10 :
            	    // InternalSemver.g:1986:3: kw= '>'
            	    {
            	    kw=(Token)match(input,54,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getGreaterThanSignKeyword_9());
            	      		
            	    }

            	    }
            	    break;
            	case 11 :
            	    // InternalSemver.g:1992:3: kw= '<='
            	    {
            	    kw=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getLessThanSignEqualsSignKeyword_10());
            	      		
            	    }

            	    }
            	    break;
            	case 12 :
            	    // InternalSemver.g:1998:3: kw= '>='
            	    {
            	    kw=(Token)match(input,56,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getWORKSPACE_VERSIONAccess().getGreaterThanSignEqualsSignKeyword_11());
            	      		
            	    }

            	    }
            	    break;
            	case 13 :
            	    // InternalSemver.g:2004:3: this_ASTERIX_12= RULE_ASTERIX
            	    {
            	    this_ASTERIX_12=(Token)match(input,RULE_ASTERIX,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_ASTERIX_12);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_ASTERIX_12, grammarAccess.getWORKSPACE_VERSIONAccess().getASTERIXTerminalRuleCall_12());
            	      		
            	    }

            	    }
            	    break;
            	case 14 :
            	    // InternalSemver.g:2012:3: this_ALPHA_NUMERIC_CHAR_13= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getWORKSPACE_VERSIONAccess().getALPHA_NUMERIC_CHARParserRuleCall_13());
            	      		
            	    }
            	    pushFollow(FOLLOW_36);
            	    this_ALPHA_NUMERIC_CHAR_13=ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_ALPHA_NUMERIC_CHAR_13);
            	      		
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
    // $ANTLR end "ruleWORKSPACE_VERSION"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"
    // InternalSemver.g:2026:1: entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS returns [String current=null] : iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF ;
    public final String entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS = null;


        try {
            // InternalSemver.g:2026:77: (iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF )
            // InternalSemver.g:2027:2: iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF
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
    // InternalSemver.g:2033:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_DIGITS_0= RULE_DIGITS this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS ) ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_DIGITS_0=null;
        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHARS_1 = null;



        	enterRule();

        try {
            // InternalSemver.g:2039:2: ( (this_DIGITS_0= RULE_DIGITS this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:2040:2: (this_DIGITS_0= RULE_DIGITS this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:2040:2: (this_DIGITS_0= RULE_DIGITS this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:2041:3: this_DIGITS_0= RULE_DIGITS this_ALPHA_NUMERIC_CHARS_1= ruleALPHA_NUMERIC_CHARS
            {
            this_DIGITS_0=(Token)match(input,RULE_DIGITS,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_DIGITS_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_DIGITS_0, grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_ALPHA_NUMERIC_CHARS_1=ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ALPHA_NUMERIC_CHARS_1);
              		
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
    // $ANTLR end "ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSemver.g:2062:1: entryRuleALPHA_NUMERIC_CHARS returns [String current=null] : iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF ;
    public final String entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHARS = null;


        try {
            // InternalSemver.g:2062:59: (iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:2063:2: iv_ruleALPHA_NUMERIC_CHARS= ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:2069:1: ruleALPHA_NUMERIC_CHARS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+ ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHARS() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_ALPHA_NUMERIC_CHAR_0 = null;



        	enterRule();

        try {
            // InternalSemver.g:2075:2: ( (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+ )
            // InternalSemver.g:2076:2: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+
            {
            // InternalSemver.g:2076:2: (this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR )+
            int cnt40=0;
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=RULE_LETTER_V && LA40_0<=RULE_LETTER_C)||(LA40_0>=RULE_LETTER_X && LA40_0<=RULE_LETTER_OTHER)||LA40_0==45) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSemver.g:2077:3: this_ALPHA_NUMERIC_CHAR_0= ruleALPHA_NUMERIC_CHAR
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall());
            	      		
            	    }
            	    pushFollow(FOLLOW_32);
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHAR"
    // InternalSemver.g:2091:1: entryRuleALPHA_NUMERIC_CHAR returns [String current=null] : iv_ruleALPHA_NUMERIC_CHAR= ruleALPHA_NUMERIC_CHAR EOF ;
    public final String entryRuleALPHA_NUMERIC_CHAR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleALPHA_NUMERIC_CHAR = null;


        try {
            // InternalSemver.g:2091:58: (iv_ruleALPHA_NUMERIC_CHAR= ruleALPHA_NUMERIC_CHAR EOF )
            // InternalSemver.g:2092:2: iv_ruleALPHA_NUMERIC_CHAR= ruleALPHA_NUMERIC_CHAR EOF
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
    // InternalSemver.g:2098:1: ruleALPHA_NUMERIC_CHAR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER ) ;
    public final AntlrDatatypeRuleToken ruleALPHA_NUMERIC_CHAR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_DIGITS_1=null;
        AntlrDatatypeRuleToken this_LETTER_2 = null;



        	enterRule();

        try {
            // InternalSemver.g:2104:2: ( (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER ) )
            // InternalSemver.g:2105:2: (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER )
            {
            // InternalSemver.g:2105:2: (kw= '-' | this_DIGITS_1= RULE_DIGITS | this_LETTER_2= ruleLETTER )
            int alt41=3;
            switch ( input.LA(1) ) {
            case 45:
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
            case RULE_LETTER_W:
            case RULE_LETTER_O:
            case RULE_LETTER_K:
            case RULE_LETTER_P:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_X:
            case RULE_LETTER_OTHER:
                {
                alt41=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }

            switch (alt41) {
                case 1 :
                    // InternalSemver.g:2106:3: kw= '-'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:2112:3: this_DIGITS_1= RULE_DIGITS
                    {
                    this_DIGITS_1=(Token)match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_DIGITS_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_DIGITS_1, grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:2120:3: this_LETTER_2= ruleLETTER
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
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


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSemver.g:2134:1: entryRuleWILDCARD returns [String current=null] : iv_ruleWILDCARD= ruleWILDCARD EOF ;
    public final String entryRuleWILDCARD() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleWILDCARD = null;


        try {
            // InternalSemver.g:2134:48: (iv_ruleWILDCARD= ruleWILDCARD EOF )
            // InternalSemver.g:2135:2: iv_ruleWILDCARD= ruleWILDCARD EOF
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
    // InternalSemver.g:2141:1: ruleWILDCARD returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX ) ;
    public final AntlrDatatypeRuleToken ruleWILDCARD() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_X_0=null;
        Token this_ASTERIX_1=null;


        	enterRule();

        try {
            // InternalSemver.g:2147:2: ( (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX ) )
            // InternalSemver.g:2148:2: (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX )
            {
            // InternalSemver.g:2148:2: (this_LETTER_X_0= RULE_LETTER_X | this_ASTERIX_1= RULE_ASTERIX )
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
                    // InternalSemver.g:2149:3: this_LETTER_X_0= RULE_LETTER_X
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
                    // InternalSemver.g:2157:3: this_ASTERIX_1= RULE_ASTERIX
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
    // InternalSemver.g:2169:1: ruleLETTER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) ;
    public final AntlrDatatypeRuleToken ruleLETTER() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_V_0=null;
        Token this_LETTER_X_1=null;
        AntlrDatatypeRuleToken this_LETTER_NO_VX_2 = null;



        	enterRule();

        try {
            // InternalSemver.g:2175:2: ( (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX ) )
            // InternalSemver.g:2176:2: (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX )
            {
            // InternalSemver.g:2176:2: (this_LETTER_V_0= RULE_LETTER_V | this_LETTER_X_1= RULE_LETTER_X | this_LETTER_NO_VX_2= ruleLETTER_NO_VX )
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
            case RULE_LETTER_W:
            case RULE_LETTER_O:
            case RULE_LETTER_K:
            case RULE_LETTER_P:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
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
                    // InternalSemver.g:2177:3: this_LETTER_V_0= RULE_LETTER_V
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
                    // InternalSemver.g:2185:3: this_LETTER_X_1= RULE_LETTER_X
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
                    // InternalSemver.g:2193:3: this_LETTER_NO_VX_2= ruleLETTER_NO_VX
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
    // InternalSemver.g:2208:1: ruleLETTER_NO_VX returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LETTER_A_0= RULE_LETTER_A | this_LETTER_C_1= RULE_LETTER_C | this_LETTER_E_2= RULE_LETTER_E | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_K_5= RULE_LETTER_K | this_LETTER_L_6= RULE_LETTER_L | this_LETTER_M_7= RULE_LETTER_M | this_LETTER_O_8= RULE_LETTER_O | this_LETTER_P_9= RULE_LETTER_P | this_LETTER_R_10= RULE_LETTER_R | this_LETTER_S_11= RULE_LETTER_S | this_LETTER_W_12= RULE_LETTER_W | this_LETTER_OTHER_13= RULE_LETTER_OTHER ) ;
    public final AntlrDatatypeRuleToken ruleLETTER_NO_VX() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LETTER_A_0=null;
        Token this_LETTER_C_1=null;
        Token this_LETTER_E_2=null;
        Token this_LETTER_F_3=null;
        Token this_LETTER_I_4=null;
        Token this_LETTER_K_5=null;
        Token this_LETTER_L_6=null;
        Token this_LETTER_M_7=null;
        Token this_LETTER_O_8=null;
        Token this_LETTER_P_9=null;
        Token this_LETTER_R_10=null;
        Token this_LETTER_S_11=null;
        Token this_LETTER_W_12=null;
        Token this_LETTER_OTHER_13=null;


        	enterRule();

        try {
            // InternalSemver.g:2214:2: ( (this_LETTER_A_0= RULE_LETTER_A | this_LETTER_C_1= RULE_LETTER_C | this_LETTER_E_2= RULE_LETTER_E | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_K_5= RULE_LETTER_K | this_LETTER_L_6= RULE_LETTER_L | this_LETTER_M_7= RULE_LETTER_M | this_LETTER_O_8= RULE_LETTER_O | this_LETTER_P_9= RULE_LETTER_P | this_LETTER_R_10= RULE_LETTER_R | this_LETTER_S_11= RULE_LETTER_S | this_LETTER_W_12= RULE_LETTER_W | this_LETTER_OTHER_13= RULE_LETTER_OTHER ) )
            // InternalSemver.g:2215:2: (this_LETTER_A_0= RULE_LETTER_A | this_LETTER_C_1= RULE_LETTER_C | this_LETTER_E_2= RULE_LETTER_E | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_K_5= RULE_LETTER_K | this_LETTER_L_6= RULE_LETTER_L | this_LETTER_M_7= RULE_LETTER_M | this_LETTER_O_8= RULE_LETTER_O | this_LETTER_P_9= RULE_LETTER_P | this_LETTER_R_10= RULE_LETTER_R | this_LETTER_S_11= RULE_LETTER_S | this_LETTER_W_12= RULE_LETTER_W | this_LETTER_OTHER_13= RULE_LETTER_OTHER )
            {
            // InternalSemver.g:2215:2: (this_LETTER_A_0= RULE_LETTER_A | this_LETTER_C_1= RULE_LETTER_C | this_LETTER_E_2= RULE_LETTER_E | this_LETTER_F_3= RULE_LETTER_F | this_LETTER_I_4= RULE_LETTER_I | this_LETTER_K_5= RULE_LETTER_K | this_LETTER_L_6= RULE_LETTER_L | this_LETTER_M_7= RULE_LETTER_M | this_LETTER_O_8= RULE_LETTER_O | this_LETTER_P_9= RULE_LETTER_P | this_LETTER_R_10= RULE_LETTER_R | this_LETTER_S_11= RULE_LETTER_S | this_LETTER_W_12= RULE_LETTER_W | this_LETTER_OTHER_13= RULE_LETTER_OTHER )
            int alt44=14;
            switch ( input.LA(1) ) {
            case RULE_LETTER_A:
                {
                alt44=1;
                }
                break;
            case RULE_LETTER_C:
                {
                alt44=2;
                }
                break;
            case RULE_LETTER_E:
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
            case RULE_LETTER_K:
                {
                alt44=6;
                }
                break;
            case RULE_LETTER_L:
                {
                alt44=7;
                }
                break;
            case RULE_LETTER_M:
                {
                alt44=8;
                }
                break;
            case RULE_LETTER_O:
                {
                alt44=9;
                }
                break;
            case RULE_LETTER_P:
                {
                alt44=10;
                }
                break;
            case RULE_LETTER_R:
                {
                alt44=11;
                }
                break;
            case RULE_LETTER_S:
                {
                alt44=12;
                }
                break;
            case RULE_LETTER_W:
                {
                alt44=13;
                }
                break;
            case RULE_LETTER_OTHER:
                {
                alt44=14;
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
                    // InternalSemver.g:2216:3: this_LETTER_A_0= RULE_LETTER_A
                    {
                    this_LETTER_A_0=(Token)match(input,RULE_LETTER_A,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_A_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_A_0, grammarAccess.getLETTER_NO_VXAccess().getLETTER_ATerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalSemver.g:2224:3: this_LETTER_C_1= RULE_LETTER_C
                    {
                    this_LETTER_C_1=(Token)match(input,RULE_LETTER_C,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_C_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_C_1, grammarAccess.getLETTER_NO_VXAccess().getLETTER_CTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalSemver.g:2232:3: this_LETTER_E_2= RULE_LETTER_E
                    {
                    this_LETTER_E_2=(Token)match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_E_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_E_2, grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalSemver.g:2240:3: this_LETTER_F_3= RULE_LETTER_F
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
                    // InternalSemver.g:2248:3: this_LETTER_I_4= RULE_LETTER_I
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
                    // InternalSemver.g:2256:3: this_LETTER_K_5= RULE_LETTER_K
                    {
                    this_LETTER_K_5=(Token)match(input,RULE_LETTER_K,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_K_5);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_K_5, grammarAccess.getLETTER_NO_VXAccess().getLETTER_KTerminalRuleCall_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalSemver.g:2264:3: this_LETTER_L_6= RULE_LETTER_L
                    {
                    this_LETTER_L_6=(Token)match(input,RULE_LETTER_L,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_L_6);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_L_6, grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalSemver.g:2272:3: this_LETTER_M_7= RULE_LETTER_M
                    {
                    this_LETTER_M_7=(Token)match(input,RULE_LETTER_M,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_M_7);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_M_7, grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalSemver.g:2280:3: this_LETTER_O_8= RULE_LETTER_O
                    {
                    this_LETTER_O_8=(Token)match(input,RULE_LETTER_O,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_O_8);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_O_8, grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTerminalRuleCall_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalSemver.g:2288:3: this_LETTER_P_9= RULE_LETTER_P
                    {
                    this_LETTER_P_9=(Token)match(input,RULE_LETTER_P,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_P_9);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_P_9, grammarAccess.getLETTER_NO_VXAccess().getLETTER_PTerminalRuleCall_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalSemver.g:2296:3: this_LETTER_R_10= RULE_LETTER_R
                    {
                    this_LETTER_R_10=(Token)match(input,RULE_LETTER_R,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_R_10);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_R_10, grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalSemver.g:2304:3: this_LETTER_S_11= RULE_LETTER_S
                    {
                    this_LETTER_S_11=(Token)match(input,RULE_LETTER_S,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_S_11);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_S_11, grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalSemver.g:2312:3: this_LETTER_W_12= RULE_LETTER_W
                    {
                    this_LETTER_W_12=(Token)match(input,RULE_LETTER_W,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_W_12);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_W_12, grammarAccess.getLETTER_NO_VXAccess().getLETTER_WTerminalRuleCall_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalSemver.g:2320:3: this_LETTER_OTHER_13= RULE_LETTER_OTHER
                    {
                    this_LETTER_OTHER_13=(Token)match(input,RULE_LETTER_OTHER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LETTER_OTHER_13);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LETTER_OTHER_13, grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_13());
                      		
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
    // InternalSemver.g:2331:1: ruleVersionComparator returns [Enumerator current=null] : ( (enumLiteral_0= '=' ) | (enumLiteral_1= '~' ) | (enumLiteral_2= '^' ) | (enumLiteral_3= '<' ) | (enumLiteral_4= '>' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>=' ) ) ;
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
            // InternalSemver.g:2337:2: ( ( (enumLiteral_0= '=' ) | (enumLiteral_1= '~' ) | (enumLiteral_2= '^' ) | (enumLiteral_3= '<' ) | (enumLiteral_4= '>' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>=' ) ) )
            // InternalSemver.g:2338:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '~' ) | (enumLiteral_2= '^' ) | (enumLiteral_3= '<' ) | (enumLiteral_4= '>' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>=' ) )
            {
            // InternalSemver.g:2338:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '~' ) | (enumLiteral_2= '^' ) | (enumLiteral_3= '<' ) | (enumLiteral_4= '>' ) | (enumLiteral_5= '<=' ) | (enumLiteral_6= '>=' ) )
            int alt45=7;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt45=1;
                }
                break;
            case 51:
                {
                alt45=2;
                }
                break;
            case 52:
                {
                alt45=3;
                }
                break;
            case 53:
                {
                alt45=4;
                }
                break;
            case 54:
                {
                alt45=5;
                }
                break;
            case 55:
                {
                alt45=6;
                }
                break;
            case 56:
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
                    // InternalSemver.g:2339:3: (enumLiteral_0= '=' )
                    {
                    // InternalSemver.g:2339:3: (enumLiteral_0= '=' )
                    // InternalSemver.g:2340:4: enumLiteral_0= '='
                    {
                    enumLiteral_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:2347:3: (enumLiteral_1= '~' )
                    {
                    // InternalSemver.g:2347:3: (enumLiteral_1= '~' )
                    // InternalSemver.g:2348:4: enumLiteral_1= '~'
                    {
                    enumLiteral_1=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:2355:3: (enumLiteral_2= '^' )
                    {
                    // InternalSemver.g:2355:3: (enumLiteral_2= '^' )
                    // InternalSemver.g:2356:4: enumLiteral_2= '^'
                    {
                    enumLiteral_2=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:2363:3: (enumLiteral_3= '<' )
                    {
                    // InternalSemver.g:2363:3: (enumLiteral_3= '<' )
                    // InternalSemver.g:2364:4: enumLiteral_3= '<'
                    {
                    enumLiteral_3=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:2371:3: (enumLiteral_4= '>' )
                    {
                    // InternalSemver.g:2371:3: (enumLiteral_4= '>' )
                    // InternalSemver.g:2372:4: enumLiteral_4= '>'
                    {
                    enumLiteral_4=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_4());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:2379:3: (enumLiteral_5= '<=' )
                    {
                    // InternalSemver.g:2379:3: (enumLiteral_5= '<=' )
                    // InternalSemver.g:2380:4: enumLiteral_5= '<='
                    {
                    enumLiteral_5=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_5, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5());
                      			
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:2387:3: (enumLiteral_6= '>=' )
                    {
                    // InternalSemver.g:2387:3: (enumLiteral_6= '>=' )
                    // InternalSemver.g:2388:4: enumLiteral_6= '>='
                    {
                    enumLiteral_6=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
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
        // InternalSemver.g:133:8: ( ruleWorkspaceVersionRequirement )
        // InternalSemver.g:133:9: ruleWorkspaceVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleWorkspaceVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSemver

    // $ANTLR start synpred4_InternalSemver
    public final void synpred4_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:332:4: ( ruleURLSemver )
        // InternalSemver.g:332:5: ruleURLSemver
        {
        pushFollow(FOLLOW_2);
        ruleURLSemver();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_InternalSemver

    // $ANTLR start synpred5_InternalSemver
    public final void synpred5_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:493:5: ( ( ruleSimpleVersion ) )
        // InternalSemver.g:493:6: ( ruleSimpleVersion )
        {
        // InternalSemver.g:493:6: ( ruleSimpleVersion )
        // InternalSemver.g:494:6: ruleSimpleVersion
        {
        pushFollow(FOLLOW_2);
        ruleSimpleVersion();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred5_InternalSemver

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
    public final boolean synpred4_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalSemver_fragment(); // can never throw exception
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
    protected DFA3 dfa3 = new DFA3(this);
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String dfa_1s = "\36\uffff";
    static final String dfa_2s = "\3\uffff\4\2\27\uffff";
    static final String dfa_3s = "\1\7\1\5\1\uffff\4\4\26\0\1\uffff";
    static final String dfa_4s = "\2\61\1\uffff\4\61\26\0\1\uffff";
    static final String dfa_5s = "\2\uffff\1\2\32\uffff\1\1";
    static final String dfa_6s = "\7\uffff\1\2\1\22\1\4\1\13\1\10\1\24\1\17\1\5\1\7\1\23\1\12\1\0\1\15\1\3\1\20\1\6\1\21\1\11\1\25\1\14\1\1\1\16\1\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\14\2\2\uffff\1\2\26\uffff\1\2\3\uffff\1\2",
            "\3\2\1\3\13\2\1\uffff\2\2\22\uffff\2\2\2\uffff\5\2",
            "",
            "\5\2\1\4\12\2\1\uffff\2\2\22\uffff\2\2\2\uffff\5\2",
            "\6\2\1\5\11\2\1\uffff\2\2\22\uffff\2\2\2\uffff\5\2",
            "\20\2\1\uffff\2\2\22\uffff\1\6\1\2\2\uffff\5\2",
            "\1\2\1\15\1\14\1\22\1\23\1\25\1\21\1\32\1\26\1\31\1\33\1\27\1\24\1\30\1\17\1\20\1\uffff\1\16\1\34\22\uffff\1\2\1\7\1\2\1\uffff\1\13\1\10\1\uffff\1\11\1\12",
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
            return "105:4: ( ( ( ruleLocalPathVersionRequirement )=>this_LocalPathVersionRequirement_2= ruleLocalPathVersionRequirement ) | ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_18 = input.LA(1);

                         
                        int index4_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_18);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_27 = input.LA(1);

                         
                        int index4_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_27);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_20 = input.LA(1);

                         
                        int index4_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_20);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA4_9 = input.LA(1);

                         
                        int index4_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA4_14 = input.LA(1);

                         
                        int index4_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA4_22 = input.LA(1);

                         
                        int index4_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_22);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA4_15 = input.LA(1);

                         
                        int index4_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_15);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA4_11 = input.LA(1);

                         
                        int index4_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA4_24 = input.LA(1);

                         
                        int index4_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_24);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA4_17 = input.LA(1);

                         
                        int index4_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_17);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA4_10 = input.LA(1);

                         
                        int index4_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_10);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA4_26 = input.LA(1);

                         
                        int index4_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_26);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA4_19 = input.LA(1);

                         
                        int index4_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_19);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA4_28 = input.LA(1);

                         
                        int index4_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_28);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA4_13 = input.LA(1);

                         
                        int index4_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_13);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA4_21 = input.LA(1);

                         
                        int index4_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_21);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA4_23 = input.LA(1);

                         
                        int index4_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_23);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_8);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA4_16 = input.LA(1);

                         
                        int index4_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_16);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA4_12 = input.LA(1);

                         
                        int index4_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_12);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA4_25 = input.LA(1);

                         
                        int index4_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_25);
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
    static final String dfa_8s = "\135\uffff";
    static final String dfa_9s = "\20\uffff\20\17\1\uffff\7\17\4\uffff\32\17\4\uffff\23\17";
    static final String dfa_10s = "\1\7\16\5\1\uffff\20\4\1\uffff\7\4\4\0\32\4\4\0\23\4";
    static final String dfa_11s = "\17\61\1\uffff\20\61\1\uffff\7\61\4\0\27\61\3\70\4\0\23\70";
    static final String dfa_12s = "\17\uffff\1\2\20\uffff\1\1\74\uffff";
    static final String dfa_13s = "\1\uffff\1\50\1\12\1\45\1\6\1\26\1\54\1\22\1\51\1\10\1\34\1\4\1\31\1\42\1\15\1\uffff\1\7\1\27\1\17\1\46\1\11\1\37\1\0\1\23\1\53\1\20\1\44\1\5\1\33\1\1\1\21\1\47\1\uffff\1\35\1\uffff\1\40\1\uffff\1\36\1\uffff\1\16\1\14\1\24\1\32\1\43\23\uffff\1\41\1\55\1\30\1\2\3\uffff\1\52\1\3\1\13\1\25\23\uffff}>";
    static final String[] dfa_14s = {
            "\1\4\1\5\1\7\1\3\1\14\1\10\1\13\1\15\1\11\1\6\1\12\1\1\1\2\2\uffff\1\16\26\uffff\1\17\3\uffff\1\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\41\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\2\17\2\uffff\2\17\1\40\2\17",
            "",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\43\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\20\17\1\uffff\2\17\22\uffff\1\17\1\44\1\17\1\uffff\2\17\1\uffff\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\45\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\20\17\1\uffff\2\17\22\uffff\1\17\1\46\1\17\1\uffff\2\17\1\uffff\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\47\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\77\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\57\1\56\1\64\1\65\1\67\1\63\1\74\1\70\1\73\1\75\1\71\1\66\1\72\1\61\1\62\1\uffff\1\60\1\76\22\uffff\1\52\1\50\1\17\1\uffff\1\55\1\51\1\uffff\1\53\1\54",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\100\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\101\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\102\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\42\1\17\2\uffff\2\17\1\40\2\17",
            "\1\17\1\20\1\17\1\25\1\26\1\30\1\24\1\35\1\31\1\34\1\36\1\32\1\27\1\33\1\22\1\23\1\uffff\1\21\1\37\22\uffff\1\103\1\17\2\uffff\2\17\1\40\2\17",
            "\23\17\22\uffff\1\17\1\104\1\17\1\uffff\2\17\1\uffff\11\17",
            "\23\17\22\uffff\1\17\1\105\1\17\1\uffff\2\17\1\uffff\11\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17",
            "\1\17\1\115\1\114\1\122\1\123\1\125\1\121\1\132\1\126\1\131\1\133\1\127\1\124\1\130\1\117\1\120\1\17\1\116\1\134\22\uffff\1\110\1\106\1\17\1\uffff\1\113\1\107\1\uffff\1\111\1\112\7\17"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "118:5: ( ( ( ruleURLVersionRequirement )=>this_URLVersionRequirement_3= ruleURLVersionRequirement ) | ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA3_22 = input.LA(1);

                         
                        int index3_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_22==EOF||LA3_22==RULE_WS||LA3_22==RULE_DIGITS||LA3_22==42||(LA3_22>=45 && LA3_22<=46)||(LA3_22>=48 && LA3_22<=49)) ) {s = 15;}

                        else if ( (LA3_22==41) ) {s = 34;}

                        else if ( (LA3_22==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_22==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_22==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_22==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_22==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_22==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_22==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_22==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_22==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_22==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_22==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_22==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_22==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_22==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_22==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_22==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_22==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_22);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA3_29 = input.LA(1);

                         
                        int index3_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_29==EOF||LA3_29==RULE_WS||LA3_29==RULE_DIGITS||LA3_29==42||(LA3_29>=45 && LA3_29<=46)||(LA3_29>=48 && LA3_29<=49)) ) {s = 15;}

                        else if ( (LA3_29==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_29==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_29==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_29==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_29==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_29==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_29==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_29==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_29==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_29==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_29==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_29==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_29==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_29==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_29==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_29==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_29==41) ) {s = 34;}

                        else if ( (LA3_29==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_29);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA3_66 = input.LA(1);

                         
                        int index3_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_66==41) ) {s = 67;}

                        else if ( (LA3_66==EOF||LA3_66==RULE_WS||LA3_66==RULE_DIGITS||LA3_66==42||(LA3_66>=45 && LA3_66<=46)||(LA3_66>=48 && LA3_66<=49)) ) {s = 15;}

                        else if ( (LA3_66==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_66==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_66==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_66==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_66==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_66==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_66==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_66==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_66==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_66==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_66==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_66==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_66==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_66==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_66==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_66==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_66==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_66);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA3_71 = input.LA(1);

                         
                        int index3_71 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_71);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA3_11 = input.LA(1);

                         
                        int index3_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_11==RULE_DIGITS||(LA3_11>=41 && LA3_11<=42)||(LA3_11>=45 && LA3_11<=46)||(LA3_11>=48 && LA3_11<=49)) ) {s = 15;}

                        else if ( (LA3_11==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_11==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_11==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_11==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_11==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_11==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_11==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_11==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_11==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_11==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_11==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_11==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_11==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_11==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_11==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_11==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_11==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_11);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA3_27 = input.LA(1);

                         
                        int index3_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_27==EOF||LA3_27==RULE_WS||LA3_27==RULE_DIGITS||LA3_27==42||(LA3_27>=45 && LA3_27<=46)||(LA3_27>=48 && LA3_27<=49)) ) {s = 15;}

                        else if ( (LA3_27==41) ) {s = 34;}

                        else if ( (LA3_27==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_27==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_27==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_27==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_27==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_27==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_27==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_27==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_27==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_27==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_27==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_27==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_27==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_27==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_27==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_27==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_27==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_27);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA3_4 = input.LA(1);

                         
                        int index3_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_4==RULE_DIGITS||(LA3_4>=41 && LA3_4<=42)||(LA3_4>=45 && LA3_4<=46)||(LA3_4>=48 && LA3_4<=49)) ) {s = 15;}

                        else if ( (LA3_4==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_4==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_4==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_4==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_4==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_4==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_4==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_4==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_4==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_4==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_4==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_4==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_4==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_4==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_4==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_4==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_4==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_4);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA3_16 = input.LA(1);

                         
                        int index3_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_16==EOF||LA3_16==RULE_WS||LA3_16==RULE_DIGITS||LA3_16==42||(LA3_16>=45 && LA3_16<=46)||(LA3_16>=48 && LA3_16<=49)) ) {s = 15;}

                        else if ( (LA3_16==41) ) {s = 34;}

                        else if ( (LA3_16==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_16==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_16==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_16==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_16==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_16==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_16==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_16==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_16==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_16==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_16==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_16==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_16==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_16==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_16==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_16==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_16==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_16);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA3_9 = input.LA(1);

                         
                        int index3_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_9==RULE_DIGITS||(LA3_9>=41 && LA3_9<=42)||(LA3_9>=45 && LA3_9<=46)||(LA3_9>=48 && LA3_9<=49)) ) {s = 15;}

                        else if ( (LA3_9==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_9==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_9==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_9==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_9==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_9==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_9==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_9==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_9==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_9==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_9==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_9==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_9==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_9==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_9==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_9==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_9==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA3_20 = input.LA(1);

                         
                        int index3_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_20==EOF||LA3_20==RULE_WS||LA3_20==RULE_DIGITS||LA3_20==42||(LA3_20>=45 && LA3_20<=46)||(LA3_20>=48 && LA3_20<=49)) ) {s = 15;}

                        else if ( (LA3_20==41) ) {s = 34;}

                        else if ( (LA3_20==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_20==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_20==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_20==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_20==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_20==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_20==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_20==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_20==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_20==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_20==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_20==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_20==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_20==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_20==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_20==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_20==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_20);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA3_2 = input.LA(1);

                         
                        int index3_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_2==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_2==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_2==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_2==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_2==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_2==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_2==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_2==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_2==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_2==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_2==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_2==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_2==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_2==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_2==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_2==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_2==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_2==RULE_DIGITS||(LA3_2>=41 && LA3_2<=42)||(LA3_2>=45 && LA3_2<=46)||(LA3_2>=48 && LA3_2<=49)) ) {s = 15;}

                         
                        input.seek(index3_2);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA3_72 = input.LA(1);

                         
                        int index3_72 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_72);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA3_40 = input.LA(1);

                         
                        int index3_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_40);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA3_14 = input.LA(1);

                         
                        int index3_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_14==RULE_DIGITS||(LA3_14>=41 && LA3_14<=42)||(LA3_14>=45 && LA3_14<=46)||(LA3_14>=48 && LA3_14<=49)) ) {s = 15;}

                        else if ( (LA3_14==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_14==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_14==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_14==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_14==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_14==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_14==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_14==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_14==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_14==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_14==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_14==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_14==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_14==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_14==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_14==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_14==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_14);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA3_39 = input.LA(1);

                         
                        int index3_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_39==EOF||LA3_39==RULE_WS||LA3_39==RULE_DIGITS||LA3_39==42||(LA3_39>=45 && LA3_39<=46)||(LA3_39>=48 && LA3_39<=49)) ) {s = 15;}

                        else if ( (LA3_39==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_39==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_39==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_39==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_39==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_39==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_39==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_39==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_39==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_39==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_39==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_39==RULE_LETTER_P) ) {s = 63;}

                        else if ( (LA3_39==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_39==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_39==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_39==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_39==41) ) {s = 34;}

                        else if ( (LA3_39==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_39);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA3_18 = input.LA(1);

                         
                        int index3_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_18==41) ) {s = 34;}

                        else if ( (LA3_18==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_18==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_18==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_18==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_18==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_18==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_18==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_18==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_18==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_18==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_18==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_18==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_18==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_18==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_18==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_18==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_18==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_18==EOF||LA3_18==RULE_WS||LA3_18==RULE_DIGITS||LA3_18==42||(LA3_18>=45 && LA3_18<=46)||(LA3_18>=48 && LA3_18<=49)) ) {s = 15;}

                         
                        input.seek(index3_18);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA3_25 = input.LA(1);

                         
                        int index3_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_25==EOF||LA3_25==RULE_WS||LA3_25==RULE_DIGITS||LA3_25==42||(LA3_25>=45 && LA3_25<=46)||(LA3_25>=48 && LA3_25<=49)) ) {s = 15;}

                        else if ( (LA3_25==41) ) {s = 34;}

                        else if ( (LA3_25==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_25==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_25==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_25==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_25==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_25==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_25==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_25==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_25==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_25==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_25==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_25==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_25==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_25==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_25==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_25==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_25==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_25);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA3_30 = input.LA(1);

                         
                        int index3_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_30==EOF||LA3_30==RULE_WS||LA3_30==RULE_DIGITS||LA3_30==42||(LA3_30>=45 && LA3_30<=46)||(LA3_30>=48 && LA3_30<=49)) ) {s = 15;}

                        else if ( (LA3_30==41) ) {s = 34;}

                        else if ( (LA3_30==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_30==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_30==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_30==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_30==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_30==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_30==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_30==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_30==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_30==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_30==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_30==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_30==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_30==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_30==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_30==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_30==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_30);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA3_7 = input.LA(1);

                         
                        int index3_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_7==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_7==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_7==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_7==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_7==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_7==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_7==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_7==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_7==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_7==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_7==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_7==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_7==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_7==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_7==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_7==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_7==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_7==RULE_DIGITS||(LA3_7>=41 && LA3_7<=42)||(LA3_7>=45 && LA3_7<=46)||(LA3_7>=48 && LA3_7<=49)) ) {s = 15;}

                         
                        input.seek(index3_7);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA3_23 = input.LA(1);

                         
                        int index3_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_23==EOF||LA3_23==RULE_WS||LA3_23==RULE_DIGITS||LA3_23==42||(LA3_23>=45 && LA3_23<=46)||(LA3_23>=48 && LA3_23<=49)) ) {s = 15;}

                        else if ( (LA3_23==41) ) {s = 34;}

                        else if ( (LA3_23==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_23==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_23==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_23==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_23==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_23==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_23==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_23==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_23==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_23==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_23==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_23==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_23==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_23==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_23==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_23==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_23==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_23);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA3_41 = input.LA(1);

                         
                        int index3_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_41);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA3_73 = input.LA(1);

                         
                        int index3_73 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_73);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA3_5 = input.LA(1);

                         
                        int index3_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_5==RULE_DIGITS||(LA3_5>=41 && LA3_5<=42)||(LA3_5>=45 && LA3_5<=46)||(LA3_5>=48 && LA3_5<=49)) ) {s = 15;}

                        else if ( (LA3_5==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_5==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_5==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_5==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_5==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_5==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_5==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_5==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_5==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_5==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_5==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_5==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_5==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_5==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_5==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_5==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_5==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_5);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA3_17 = input.LA(1);

                         
                        int index3_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_17==41) ) {s = 34;}

                        else if ( (LA3_17==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_17==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_17==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_17==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_17==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_17==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_17==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_17==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_17==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_17==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_17==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_17==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_17==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_17==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_17==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_17==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_17==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_17==EOF||LA3_17==RULE_WS||LA3_17==RULE_DIGITS||LA3_17==42||(LA3_17>=45 && LA3_17<=46)||(LA3_17>=48 && LA3_17<=49)) ) {s = 15;}

                         
                        input.seek(index3_17);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA3_65 = input.LA(1);

                         
                        int index3_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_65==EOF||LA3_65==RULE_WS||LA3_65==RULE_DIGITS||LA3_65==42||(LA3_65>=45 && LA3_65<=46)||(LA3_65>=48 && LA3_65<=49)) ) {s = 15;}

                        else if ( (LA3_65==41) ) {s = 34;}

                        else if ( (LA3_65==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_65==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_65==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_65==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_65==RULE_LETTER_E) ) {s = 66;}

                        else if ( (LA3_65==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_65==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_65==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_65==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_65==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_65==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_65==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_65==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_65==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_65==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_65==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_65==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_65);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA3_12 = input.LA(1);

                         
                        int index3_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_12==RULE_DIGITS||(LA3_12>=41 && LA3_12<=42)||(LA3_12>=45 && LA3_12<=46)||(LA3_12>=48 && LA3_12<=49)) ) {s = 15;}

                        else if ( (LA3_12==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_12==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_12==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_12==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_12==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_12==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_12==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_12==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_12==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_12==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_12==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_12==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_12==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_12==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_12==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_12==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_12==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_12);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA3_42 = input.LA(1);

                         
                        int index3_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_42);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA3_28 = input.LA(1);

                         
                        int index3_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_28==EOF||LA3_28==RULE_WS||LA3_28==RULE_DIGITS||LA3_28==42||(LA3_28>=45 && LA3_28<=46)||(LA3_28>=48 && LA3_28<=49)) ) {s = 15;}

                        else if ( (LA3_28==41) ) {s = 34;}

                        else if ( (LA3_28==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_28==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_28==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_28==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_28==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_28==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_28==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_28==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_28==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_28==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_28==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_28==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_28==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_28==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_28==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_28==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_28==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_28);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA3_10 = input.LA(1);

                         
                        int index3_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_10==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_10==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_10==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_10==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_10==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_10==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_10==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_10==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_10==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_10==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_10==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_10==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_10==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_10==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_10==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_10==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_10==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_10==RULE_DIGITS||(LA3_10>=41 && LA3_10<=42)||(LA3_10>=45 && LA3_10<=46)||(LA3_10>=48 && LA3_10<=49)) ) {s = 15;}

                         
                        input.seek(index3_10);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA3_33 = input.LA(1);

                         
                        int index3_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_33==41) ) {s = 34;}

                        else if ( (LA3_33==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_33==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_33==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_33==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_33==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_33==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_33==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_33==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_33==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_33==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_33==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_33==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_33==RULE_LETTER_R) ) {s = 35;}

                        else if ( (LA3_33==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_33==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_33==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_33==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_33==EOF||LA3_33==RULE_WS||LA3_33==RULE_DIGITS||LA3_33==42||(LA3_33>=45 && LA3_33<=46)||(LA3_33>=48 && LA3_33<=49)) ) {s = 15;}

                         
                        input.seek(index3_33);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA3_37 = input.LA(1);

                         
                        int index3_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_37==RULE_LETTER_S) ) {s = 39;}

                        else if ( (LA3_37==EOF||LA3_37==RULE_WS||LA3_37==RULE_DIGITS||LA3_37==42||(LA3_37>=45 && LA3_37<=46)||(LA3_37>=48 && LA3_37<=49)) ) {s = 15;}

                        else if ( (LA3_37==41) ) {s = 34;}

                        else if ( (LA3_37==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_37==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_37==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_37==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_37==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_37==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_37==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_37==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_37==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_37==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_37==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_37==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_37==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_37==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_37==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_37==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_37);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA3_21 = input.LA(1);

                         
                        int index3_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_21==EOF||LA3_21==RULE_WS||LA3_21==RULE_DIGITS||LA3_21==42||(LA3_21>=45 && LA3_21<=46)||(LA3_21>=48 && LA3_21<=49)) ) {s = 15;}

                        else if ( (LA3_21==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_21==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_21==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_21==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_21==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_21==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_21==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_21==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_21==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_21==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_21==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_21==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_21==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_21==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_21==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_21==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_21==41) ) {s = 34;}

                        else if ( (LA3_21==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_21);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA3_35 = input.LA(1);

                         
                        int index3_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_35==EOF||LA3_35==RULE_WS||LA3_35==RULE_DIGITS||LA3_35==42||(LA3_35>=45 && LA3_35<=46)||(LA3_35>=48 && LA3_35<=49)) ) {s = 15;}

                        else if ( (LA3_35==41) ) {s = 34;}

                        else if ( (LA3_35==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_35==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_35==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_35==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_35==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_35==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_35==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_35==RULE_LETTER_K) ) {s = 37;}

                        else if ( (LA3_35==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_35==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_35==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_35==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_35==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_35==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_35==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_35==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_35==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_35);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA3_63 = input.LA(1);

                         
                        int index3_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_63==RULE_LETTER_A) ) {s = 64;}

                        else if ( (LA3_63==EOF||LA3_63==RULE_WS||LA3_63==RULE_DIGITS||LA3_63==42||(LA3_63>=45 && LA3_63<=46)||(LA3_63>=48 && LA3_63<=49)) ) {s = 15;}

                        else if ( (LA3_63==41) ) {s = 34;}

                        else if ( (LA3_63==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_63==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_63==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_63==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_63==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_63==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_63==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_63==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_63==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_63==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_63==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_63==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_63==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_63==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_63==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_63==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_63);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA3_13 = input.LA(1);

                         
                        int index3_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_13==RULE_DIGITS||(LA3_13>=41 && LA3_13<=42)||(LA3_13>=45 && LA3_13<=46)||(LA3_13>=48 && LA3_13<=49)) ) {s = 15;}

                        else if ( (LA3_13==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_13==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_13==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_13==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_13==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_13==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_13==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_13==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_13==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_13==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_13==RULE_LETTER_O) ) {s = 33;}

                        else if ( (LA3_13==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_13==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_13==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_13==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_13==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_13==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_13);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA3_43 = input.LA(1);

                         
                        int index3_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_43);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA3_26 = input.LA(1);

                         
                        int index3_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_26==41) ) {s = 34;}

                        else if ( (LA3_26==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_26==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_26==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_26==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_26==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_26==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_26==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_26==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_26==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_26==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_26==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_26==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_26==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_26==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_26==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_26==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_26==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_26==EOF||LA3_26==RULE_WS||LA3_26==RULE_DIGITS||LA3_26==42||(LA3_26>=45 && LA3_26<=46)||(LA3_26>=48 && LA3_26<=49)) ) {s = 15;}

                         
                        input.seek(index3_26);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA3_3 = input.LA(1);

                         
                        int index3_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_3==RULE_DIGITS||(LA3_3>=41 && LA3_3<=42)||(LA3_3>=45 && LA3_3<=46)||(LA3_3>=48 && LA3_3<=49)) ) {s = 15;}

                        else if ( (LA3_3==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_3==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_3==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_3==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_3==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_3==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_3==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_3==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_3==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_3==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_3==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_3==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_3==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_3==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_3==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_3==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_3==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_3);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA3_19 = input.LA(1);

                         
                        int index3_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_19==EOF||LA3_19==RULE_WS||LA3_19==RULE_DIGITS||LA3_19==42||(LA3_19>=45 && LA3_19<=46)||(LA3_19>=48 && LA3_19<=49)) ) {s = 15;}

                        else if ( (LA3_19==41) ) {s = 34;}

                        else if ( (LA3_19==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_19==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_19==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_19==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_19==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_19==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_19==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_19==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_19==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_19==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_19==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_19==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_19==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_19==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_19==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_19==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_19==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_19);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA3_31 = input.LA(1);

                         
                        int index3_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_31==EOF||LA3_31==RULE_WS||LA3_31==RULE_DIGITS||LA3_31==42||(LA3_31>=45 && LA3_31<=46)||(LA3_31>=48 && LA3_31<=49)) ) {s = 15;}

                        else if ( (LA3_31==41) ) {s = 34;}

                        else if ( (LA3_31==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_31==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_31==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_31==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_31==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_31==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_31==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_31==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_31==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_31==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_31==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_31==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_31==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_31==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_31==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_31==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_31==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_31);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA3_1 = input.LA(1);

                         
                        int index3_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_1==RULE_DIGITS||(LA3_1>=41 && LA3_1<=42)||(LA3_1>=45 && LA3_1<=46)||(LA3_1>=48 && LA3_1<=49)) ) {s = 15;}

                        else if ( (LA3_1==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_1==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_1==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_1==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_1==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_1==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_1==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_1==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_1==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_1==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_1==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_1==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_1==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_1==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_1==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_1==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_1==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_1);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA3_8 = input.LA(1);

                         
                        int index3_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_8==RULE_DIGITS||(LA3_8>=41 && LA3_8<=42)||(LA3_8>=45 && LA3_8<=46)||(LA3_8>=48 && LA3_8<=49)) ) {s = 15;}

                        else if ( (LA3_8==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_8==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_8==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_8==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_8==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_8==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_8==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_8==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_8==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_8==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_8==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_8==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_8==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_8==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_8==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_8==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_8==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_8);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA3_70 = input.LA(1);

                         
                        int index3_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index3_70);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA3_24 = input.LA(1);

                         
                        int index3_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_24==EOF||LA3_24==RULE_WS||LA3_24==RULE_DIGITS||LA3_24==42||(LA3_24>=45 && LA3_24<=46)||(LA3_24>=48 && LA3_24<=49)) ) {s = 15;}

                        else if ( (LA3_24==41) ) {s = 34;}

                        else if ( (LA3_24==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_24==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_24==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_24==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_24==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_24==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_24==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_24==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_24==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_24==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_24==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_24==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_24==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_24==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_24==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_24==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_24==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_24);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA3_6 = input.LA(1);

                         
                        int index3_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_6==RULE_DIGITS||(LA3_6>=41 && LA3_6<=42)||(LA3_6>=45 && LA3_6<=46)||(LA3_6>=48 && LA3_6<=49)) ) {s = 15;}

                        else if ( (LA3_6==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_6==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_6==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_6==RULE_LETTER_C) ) {s = 19;}

                        else if ( (LA3_6==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_6==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_6==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_6==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_6==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_6==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_6==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_6==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_6==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_6==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_6==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_6==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_6==47) && (synpred2_InternalSemver())) {s = 32;}

                         
                        input.seek(index3_6);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA3_64 = input.LA(1);

                         
                        int index3_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA3_64==41) ) {s = 34;}

                        else if ( (LA3_64==RULE_LETTER_V) ) {s = 16;}

                        else if ( (LA3_64==RULE_LETTER_X) ) {s = 17;}

                        else if ( (LA3_64==RULE_LETTER_A) ) {s = 18;}

                        else if ( (LA3_64==RULE_LETTER_C) ) {s = 65;}

                        else if ( (LA3_64==RULE_LETTER_E) ) {s = 20;}

                        else if ( (LA3_64==RULE_LETTER_F) ) {s = 21;}

                        else if ( (LA3_64==RULE_LETTER_I) ) {s = 22;}

                        else if ( (LA3_64==RULE_LETTER_K) ) {s = 23;}

                        else if ( (LA3_64==RULE_LETTER_L) ) {s = 24;}

                        else if ( (LA3_64==RULE_LETTER_M) ) {s = 25;}

                        else if ( (LA3_64==RULE_LETTER_O) ) {s = 26;}

                        else if ( (LA3_64==RULE_LETTER_P) ) {s = 27;}

                        else if ( (LA3_64==RULE_LETTER_R) ) {s = 28;}

                        else if ( (LA3_64==RULE_LETTER_S) ) {s = 29;}

                        else if ( (LA3_64==RULE_LETTER_W) ) {s = 30;}

                        else if ( (LA3_64==RULE_LETTER_OTHER) ) {s = 31;}

                        else if ( (LA3_64==47) && (synpred2_InternalSemver())) {s = 32;}

                        else if ( (LA3_64==EOF||LA3_64==RULE_WS||LA3_64==RULE_DIGITS||LA3_64==42||(LA3_64>=45 && LA3_64<=46)||(LA3_64>=48 && LA3_64<=49)) ) {s = 15;}

                         
                        input.seek(index3_64);
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
    static final String dfa_15s = "\113\uffff";
    static final String dfa_16s = "\20\uffff\23\43\1\uffff\7\43\1\2\37\uffff";
    static final String dfa_17s = "\1\7\1\5\1\uffff\15\5\23\4\1\uffff\10\4\27\0\10\uffff";
    static final String dfa_18s = "\2\61\1\uffff\40\61\1\uffff\7\61\1\70\27\0\10\uffff";
    static final String dfa_19s = "\2\uffff\1\2\40\uffff\1\3\37\uffff\10\1";
    static final String dfa_20s = "\53\uffff\1\26\1\24\1\0\1\3\1\7\1\12\1\4\1\23\1\11\1\20\1\1\1\13\1\16\1\27\1\5\1\15\1\21\1\2\1\10\1\17\1\25\1\6\1\14\1\22\10\uffff}>";
    static final String[] dfa_21s = {
            "\1\6\1\7\1\11\1\5\1\16\1\12\1\15\1\1\1\13\1\10\1\14\1\3\1\4\2\uffff\1\17\26\uffff\1\2\3\uffff\1\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\34\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\44\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\45\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\46\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\47\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\50\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\51\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\52\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\2\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\43\1\22\1\21\1\27\1\30\1\32\1\26\1\37\1\33\1\36\1\40\1\42\1\31\1\35\1\24\1\25\1\uffff\1\23\1\41\22\uffff\1\53\1\2\2\uffff\1\20\1\2\1\uffff\2\2",
            "\1\2\1\63\1\62\1\70\1\71\1\73\1\67\1\100\1\74\1\77\1\101\1\75\1\72\1\76\1\65\1\66\1\112\1\64\1\102\22\uffff\1\56\1\54\1\2\1\uffff\1\61\1\55\1\uffff\1\57\1\60\1\103\1\104\1\105\1\106\1\107\1\110\1\111",
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
            "",
            "",
            "",
            "",
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

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "131:6: ( ( ( ruleWorkspaceVersionRequirement )=>this_WorkspaceVersionRequirement_4= ruleWorkspaceVersionRequirement ) | this_GitHubVersionRequirement_5= ruleGitHubVersionRequirement | this_TagVersionRequirement_6= ruleTagVersionRequirement )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA2_45 = input.LA(1);

                         
                        int index2_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_45);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA2_53 = input.LA(1);

                         
                        int index2_53 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_53);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA2_60 = input.LA(1);

                         
                        int index2_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_60);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA2_46 = input.LA(1);

                         
                        int index2_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_46);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA2_49 = input.LA(1);

                         
                        int index2_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_49);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA2_57 = input.LA(1);

                         
                        int index2_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_57);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA2_64 = input.LA(1);

                         
                        int index2_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_64);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA2_47 = input.LA(1);

                         
                        int index2_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_47);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA2_61 = input.LA(1);

                         
                        int index2_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_61);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA2_51 = input.LA(1);

                         
                        int index2_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_51);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA2_48 = input.LA(1);

                         
                        int index2_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_48);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA2_54 = input.LA(1);

                         
                        int index2_54 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_54);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA2_65 = input.LA(1);

                         
                        int index2_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_65);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA2_58 = input.LA(1);

                         
                        int index2_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_58);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA2_55 = input.LA(1);

                         
                        int index2_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_55);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA2_62 = input.LA(1);

                         
                        int index2_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_62);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA2_52 = input.LA(1);

                         
                        int index2_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_52);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA2_59 = input.LA(1);

                         
                        int index2_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_59);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA2_66 = input.LA(1);

                         
                        int index2_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_66);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA2_50 = input.LA(1);

                         
                        int index2_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_50);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA2_44 = input.LA(1);

                         
                        int index2_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_44);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA2_63 = input.LA(1);

                         
                        int index2_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_63);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA2_43 = input.LA(1);

                         
                        int index2_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA2_43==42) ) {s = 44;}

                        else if ( (LA2_43==46) ) {s = 45;}

                        else if ( (LA2_43==41) ) {s = 46;}

                        else if ( (LA2_43==48) ) {s = 47;}

                        else if ( (LA2_43==49) ) {s = 48;}

                        else if ( (LA2_43==45) ) {s = 49;}

                        else if ( (LA2_43==RULE_DIGITS) ) {s = 50;}

                        else if ( (LA2_43==RULE_LETTER_V) ) {s = 51;}

                        else if ( (LA2_43==RULE_LETTER_X) ) {s = 52;}

                        else if ( (LA2_43==RULE_LETTER_A) ) {s = 53;}

                        else if ( (LA2_43==RULE_LETTER_C) ) {s = 54;}

                        else if ( (LA2_43==RULE_LETTER_E) ) {s = 55;}

                        else if ( (LA2_43==RULE_LETTER_F) ) {s = 56;}

                        else if ( (LA2_43==RULE_LETTER_I) ) {s = 57;}

                        else if ( (LA2_43==RULE_LETTER_K) ) {s = 58;}

                        else if ( (LA2_43==RULE_LETTER_L) ) {s = 59;}

                        else if ( (LA2_43==RULE_LETTER_M) ) {s = 60;}

                        else if ( (LA2_43==RULE_LETTER_O) ) {s = 61;}

                        else if ( (LA2_43==RULE_LETTER_P) ) {s = 62;}

                        else if ( (LA2_43==RULE_LETTER_R) ) {s = 63;}

                        else if ( (LA2_43==RULE_LETTER_S) ) {s = 64;}

                        else if ( (LA2_43==RULE_LETTER_W) ) {s = 65;}

                        else if ( (LA2_43==RULE_LETTER_OTHER) ) {s = 66;}

                        else if ( (LA2_43==EOF||LA2_43==RULE_WS||LA2_43==43) ) {s = 2;}

                        else if ( (LA2_43==50) && (synpred3_InternalSemver())) {s = 67;}

                        else if ( (LA2_43==51) && (synpred3_InternalSemver())) {s = 68;}

                        else if ( (LA2_43==52) && (synpred3_InternalSemver())) {s = 69;}

                        else if ( (LA2_43==53) && (synpred3_InternalSemver())) {s = 70;}

                        else if ( (LA2_43==54) && (synpred3_InternalSemver())) {s = 71;}

                        else if ( (LA2_43==55) && (synpred3_InternalSemver())) {s = 72;}

                        else if ( (LA2_43==56) && (synpred3_InternalSemver())) {s = 73;}

                        else if ( (LA2_43==RULE_ASTERIX) && (synpred3_InternalSemver())) {s = 74;}

                         
                        input.seek(index2_43);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA2_56 = input.LA(1);

                         
                        int index2_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index2_56);
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
    static final String dfa_22s = "\26\uffff";
    static final String dfa_23s = "\1\uffff\1\15\7\uffff\1\15\4\uffff\1\15\2\uffff\4\15\1\uffff";
    static final String dfa_24s = "\1\5\1\4\7\uffff\1\4\1\0\1\uffff\1\5\1\uffff\1\4\1\0\1\uffff\4\4\1\uffff";
    static final String dfa_25s = "\1\70\1\55\7\uffff\1\55\1\0\1\uffff\1\55\1\uffff\1\55\1\0\1\uffff\4\55\1\uffff";
    static final String dfa_26s = "\2\uffff\7\1\2\uffff\1\1\1\uffff\1\3\2\uffff\1\2\4\uffff\1\1";
    static final String dfa_27s = "\1\5\10\uffff\1\0\1\4\1\uffff\1\3\2\uffff\1\1\4\uffff\1\2\1\uffff}>";
    static final String[] dfa_28s = {
            "\1\11\1\14\4\15\1\1\10\15\1\13\1\12\1\15\26\uffff\1\15\4\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10",
            "\6\15\1\16\11\15\1\uffff\2\15\26\uffff\1\15",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\15\1\17\15\15\1\13\1\12\1\15\26\uffff\1\15",
            "\1\uffff",
            "",
            "\17\20\1\uffff\2\20\26\uffff\1\20",
            "",
            "\10\15\1\21\7\15\1\uffff\2\15\26\uffff\1\15",
            "\1\uffff",
            "",
            "\1\15\1\22\16\15\1\uffff\2\15\26\uffff\1\15",
            "\6\15\1\23\11\15\1\uffff\2\15\26\uffff\1\15",
            "\11\15\1\24\6\15\1\uffff\2\15\26\uffff\1\15",
            "\20\15\1\uffff\2\15\22\uffff\1\25\3\uffff\1\15",
            ""
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "330:2: ( ( ( ruleURLSemver )=>this_URLSemver_0= ruleURLSemver ) | ( () ( (lv_commitISH_2_0= ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ) ) | ( () ( (lv_commitISH_4_0= ruleALPHA_NUMERIC_CHARS ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_9 = input.LA(1);

                         
                        int index8_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA8_9==EOF||(LA8_9>=RULE_WS && LA8_9<=RULE_LETTER_V)||(LA8_9>=RULE_LETTER_F && LA8_9<=RULE_LETTER_C)||LA8_9==RULE_LETTER_OTHER||LA8_9==45) ) {s = 13;}

                        else if ( (LA8_9==RULE_DIGITS) ) {s = 15;}

                        else if ( (LA8_9==RULE_LETTER_X) ) {s = 10;}

                        else if ( (LA8_9==RULE_ASTERIX) && (synpred4_InternalSemver())) {s = 11;}

                         
                        input.seek(index8_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_15 = input.LA(1);

                         
                        int index8_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 11;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index8_15);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_20 = input.LA(1);

                         
                        int index8_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA8_20==41) && (synpred4_InternalSemver())) {s = 21;}

                        else if ( (LA8_20==EOF||(LA8_20>=RULE_WS && LA8_20<=RULE_LETTER_C)||(LA8_20>=RULE_LETTER_X && LA8_20<=RULE_LETTER_OTHER)||LA8_20==45) ) {s = 13;}

                         
                        input.seek(index8_20);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_12 = input.LA(1);

                         
                        int index8_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA8_12>=RULE_LETTER_V && LA8_12<=RULE_LETTER_C)||(LA8_12>=RULE_LETTER_X && LA8_12<=RULE_LETTER_OTHER)||LA8_12==45) ) {s = 16;}

                        else if ( (synpred4_InternalSemver()) ) {s = 11;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index8_12);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA8_10 = input.LA(1);

                         
                        int index8_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 11;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index8_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA8_0 = input.LA(1);

                         
                        int index8_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA8_0==RULE_LETTER_S) ) {s = 1;}

                        else if ( (LA8_0==50) && (synpred4_InternalSemver())) {s = 2;}

                        else if ( (LA8_0==51) && (synpred4_InternalSemver())) {s = 3;}

                        else if ( (LA8_0==52) && (synpred4_InternalSemver())) {s = 4;}

                        else if ( (LA8_0==53) && (synpred4_InternalSemver())) {s = 5;}

                        else if ( (LA8_0==54) && (synpred4_InternalSemver())) {s = 6;}

                        else if ( (LA8_0==55) && (synpred4_InternalSemver())) {s = 7;}

                        else if ( (LA8_0==56) && (synpred4_InternalSemver())) {s = 8;}

                        else if ( (LA8_0==RULE_LETTER_V) ) {s = 9;}

                        else if ( (LA8_0==RULE_LETTER_X) ) {s = 10;}

                        else if ( (LA8_0==RULE_ASTERIX) && (synpred4_InternalSemver())) {s = 11;}

                        else if ( (LA8_0==RULE_DIGITS) ) {s = 12;}

                        else if ( ((LA8_0>=RULE_LETTER_F && LA8_0<=RULE_LETTER_E)||(LA8_0>=RULE_LETTER_M && LA8_0<=RULE_LETTER_C)||LA8_0==RULE_LETTER_OTHER||LA8_0==45) ) {s = 13;}

                         
                        input.seek(index8_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_29s = "\31\uffff";
    static final String dfa_30s = "\1\uffff\10\14\4\uffff\1\14\13\uffff";
    static final String dfa_31s = "\1\5\10\4\3\0\1\uffff\1\5\13\uffff";
    static final String dfa_32s = "\11\70\3\0\1\uffff\1\70\13\uffff";
    static final String dfa_33s = "\14\uffff\1\2\1\uffff\13\1";
    static final String dfa_34s = "\11\uffff\1\0\1\2\1\3\1\uffff\1\1\13\uffff}>";
    static final String[] dfa_35s = {
            "\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\15\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\15\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\15\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\15\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\15\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\15\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\15\1\10\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\2\14\1\13\15\14\1\12\1\11\1\14\22\uffff\2\14\2\uffff\2\14\1\uffff\11\14",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\17\1\21\15\uffff\1\20\1\16\34\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1\30",
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

    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[][] dfa_35 = unpackEncodedStringArray(dfa_35s);

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "491:3: ( ( ( ( ruleSimpleVersion ) )=> (lv_version_1_0= ruleSimpleVersion ) ) | ( (lv_otherVersion_2_0= ruleWORKSPACE_VERSION ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_9 = input.LA(1);

                         
                        int index10_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 14;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index10_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA10_13 = input.LA(1);

                         
                        int index10_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA10_13==EOF) ) {s = 12;}

                        else if ( (LA10_13==RULE_LETTER_V) && (synpred5_InternalSemver())) {s = 15;}

                        else if ( (LA10_13==RULE_LETTER_X) && (synpred5_InternalSemver())) {s = 14;}

                        else if ( (LA10_13==RULE_ASTERIX) && (synpred5_InternalSemver())) {s = 16;}

                        else if ( (LA10_13==RULE_DIGITS) && (synpred5_InternalSemver())) {s = 17;}

                        else if ( (LA10_13==50) && (synpred5_InternalSemver())) {s = 18;}

                        else if ( (LA10_13==51) && (synpred5_InternalSemver())) {s = 19;}

                        else if ( (LA10_13==52) && (synpred5_InternalSemver())) {s = 20;}

                        else if ( (LA10_13==53) && (synpred5_InternalSemver())) {s = 21;}

                        else if ( (LA10_13==54) && (synpred5_InternalSemver())) {s = 22;}

                        else if ( (LA10_13==55) && (synpred5_InternalSemver())) {s = 23;}

                        else if ( (LA10_13==56) && (synpred5_InternalSemver())) {s = 24;}

                         
                        input.seek(index10_13);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA10_10 = input.LA(1);

                         
                        int index10_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 14;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index10_10);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA10_11 = input.LA(1);

                         
                        int index10_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 14;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index10_11);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_36s = "\u0085\uffff";
    static final String dfa_37s = "\2\uffff\3\1\3\uffff\50\1\5\uffff\71\1\2\uffff\25\1";
    static final String dfa_38s = "\1\5\1\uffff\3\4\1\6\3\5\47\4\1\uffff\1\6\3\5\71\4\1\6\1\5\25\4";
    static final String dfa_39s = "\1\70\1\uffff\3\57\1\25\2\55\1\70\25\57\22\56\1\uffff\1\25\3\55\25\57\44\56\1\25\1\55\3\57\22\56";
    static final String dfa_40s = "\1\uffff\1\1\56\uffff\1\2\124\uffff";
    static final String dfa_41s = "\u0085\uffff}>";
    static final String[] dfa_42s = {
            "\1\1\1\4\15\uffff\1\3\1\2\34\uffff\7\1",
            "",
            "\1\10\47\uffff\1\1\1\6\1\5\1\7",
            "\1\10\47\uffff\1\1\1\6\1\5\1\7",
            "\1\10\47\uffff\1\1\1\6\1\5\1\7",
            "\1\13\15\uffff\1\12\1\11",
            "\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\26\uffff\1\14",
            "\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\26\uffff\1\36",
            "\2\1\15\uffff\2\1\26\uffff\1\1\1\60\4\uffff\7\1",
            "\1\10\47\uffff\1\1\1\6\1\61\1\7",
            "\1\10\47\uffff\1\1\1\6\1\61\1\7",
            "\1\10\47\uffff\1\1\1\6\1\61\1\7",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\16\1\15\1\23\1\24\1\26\1\22\1\33\1\27\1\32\1\34\1\30\1\25\1\31\1\20\1\21\1\uffff\1\17\1\35\25\uffff\1\1\1\14\1\62\1\63",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "\1\10\1\40\1\37\1\45\1\46\1\50\1\44\1\55\1\51\1\54\1\56\1\52\1\47\1\53\1\42\1\43\1\uffff\1\41\1\57\25\uffff\1\1\1\36\1\64",
            "",
            "\1\67\15\uffff\1\66\1\65",
            "\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\26\uffff\1\70",
            "\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\26\uffff\1\112",
            "\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\26\uffff\1\134",
            "\1\10\47\uffff\1\1\1\6\1\156\1\7",
            "\1\10\47\uffff\1\1\1\6\1\156\1\7",
            "\1\10\47\uffff\1\1\1\6\1\156\1\7",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\72\1\71\1\77\1\100\1\102\1\76\1\107\1\103\1\106\1\110\1\104\1\101\1\105\1\74\1\75\1\uffff\1\73\1\111\25\uffff\1\1\1\70\1\62\1\63",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\114\1\113\1\121\1\122\1\124\1\120\1\131\1\125\1\130\1\132\1\126\1\123\1\127\1\116\1\117\1\uffff\1\115\1\133\25\uffff\1\1\1\112\1\157",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\10\1\136\1\135\1\143\1\144\1\146\1\142\1\153\1\147\1\152\1\154\1\150\1\145\1\151\1\140\1\141\1\uffff\1\137\1\155\25\uffff\1\1\1\134\1\64",
            "\1\162\15\uffff\1\161\1\160",
            "\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\26\uffff\1\163",
            "\1\10\47\uffff\1\1\1\6\1\156\1\7",
            "\1\10\47\uffff\1\1\1\6\1\156\1\7",
            "\1\10\47\uffff\1\1\1\6\1\156\1\7",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157",
            "\1\10\1\165\1\164\1\172\1\173\1\175\1\171\1\u0082\1\176\1\u0081\1\u0083\1\177\1\174\1\u0080\1\167\1\170\1\uffff\1\166\1\u0084\25\uffff\1\1\1\163\1\157"
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = dfa_36;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "743:2: (this_VersionRangeContraint_0= ruleVersionRangeContraint | this_HyphenVersionRange_1= ruleHyphenVersionRange )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x01FC000000300060L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00036400006FFFE0L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00036600006FFFE0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x01FF6400007FFFE0L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x01FF6600007FFFE0L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000100000000012L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x01FC000000300070L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000E00000000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x00036400006FFFE2L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0003E400006FFFE0L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0003E400006FFFE2L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x00036600006FFFE2L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x01FF6600007FFFE2L});

}