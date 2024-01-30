package org.eclipse.n4js.semver.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
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
public class InternalSemverParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ASTERIX", "RULE_DIGITS", "RULE_LETTER_X", "RULE_LETTER_V", "RULE_LETTER_A", "RULE_LETTER_C", "RULE_LETTER_E", "RULE_LETTER_F", "RULE_LETTER_I", "RULE_LETTER_K", "RULE_LETTER_L", "RULE_LETTER_M", "RULE_LETTER_O", "RULE_LETTER_P", "RULE_LETTER_R", "RULE_LETTER_S", "RULE_LETTER_W", "RULE_LETTER_OTHER", "RULE_WS", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'/'", "'.'", "'@'", "'_'", "'+'", "':'", "'-'", "'='", "'~'", "'^'", "'<'", "'>'", "'<='", "'>='", "'#'", "'||'"
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
    public static final int RULE_LETTER_OTHER=21;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=36;
    public static final int RULE_ZWNJ=30;
    public static final int RULE_LETTER_A=8;
    public static final int RULE_LETTER_C=9;
    public static final int RULE_ASTERIX=4;
    public static final int RULE_LETTER_E=10;
    public static final int RULE_ML_COMMENT_FRAGMENT=35;
    public static final int RULE_DIGITS=5;
    public static final int RULE_LETTER_O=16;
    public static final int RULE_ZWJ=29;
    public static final int RULE_SL_COMMENT_FRAGMENT=34;
    public static final int RULE_LETTER_P=17;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=37;
    public static final int RULE_LETTER_R=18;
    public static final int RULE_LETTER_S=19;
    public static final int RULE_LETTER_F=11;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=32;
    public static final int RULE_LETTER_I=12;
    public static final int EOF=-1;
    public static final int RULE_LETTER_K=13;
    public static final int RULE_LETTER_L=14;
    public static final int RULE_LETTER_M=15;
    public static final int RULE_WS=22;
    public static final int RULE_BOM=31;
    public static final int RULE_LETTER_V=7;
    public static final int RULE_LETTER_W=20;
    public static final int RULE_LETTER_X=6;
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

    	public void setGrammarAccess(SemverGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleNPMVersionRequirement"
    // InternalSemver.g:61:1: entryRuleNPMVersionRequirement : ruleNPMVersionRequirement EOF ;
    public final void entryRuleNPMVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:62:1: ( ruleNPMVersionRequirement EOF )
            // InternalSemver.g:63:1: ruleNPMVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleNPMVersionRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNPMVersionRequirementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNPMVersionRequirement"


    // $ANTLR start "ruleNPMVersionRequirement"
    // InternalSemver.g:70:1: ruleNPMVersionRequirement : ( ( rule__NPMVersionRequirement__Alternatives ) ) ;
    public final void ruleNPMVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:74:2: ( ( ( rule__NPMVersionRequirement__Alternatives ) ) )
            // InternalSemver.g:75:2: ( ( rule__NPMVersionRequirement__Alternatives ) )
            {
            // InternalSemver.g:75:2: ( ( rule__NPMVersionRequirement__Alternatives ) )
            // InternalSemver.g:76:3: ( rule__NPMVersionRequirement__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives()); 
            }
            // InternalSemver.g:77:3: ( rule__NPMVersionRequirement__Alternatives )
            // InternalSemver.g:77:4: rule__NPMVersionRequirement__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__NPMVersionRequirement__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNPMVersionRequirementAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNPMVersionRequirement"


    // $ANTLR start "entryRuleLocalPathVersionRequirement"
    // InternalSemver.g:86:1: entryRuleLocalPathVersionRequirement : ruleLocalPathVersionRequirement EOF ;
    public final void entryRuleLocalPathVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:87:1: ( ruleLocalPathVersionRequirement EOF )
            // InternalSemver.g:88:1: ruleLocalPathVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLocalPathVersionRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLocalPathVersionRequirement"


    // $ANTLR start "ruleLocalPathVersionRequirement"
    // InternalSemver.g:95:1: ruleLocalPathVersionRequirement : ( ( rule__LocalPathVersionRequirement__Group__0 ) ) ;
    public final void ruleLocalPathVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:99:2: ( ( ( rule__LocalPathVersionRequirement__Group__0 ) ) )
            // InternalSemver.g:100:2: ( ( rule__LocalPathVersionRequirement__Group__0 ) )
            {
            // InternalSemver.g:100:2: ( ( rule__LocalPathVersionRequirement__Group__0 ) )
            // InternalSemver.g:101:3: ( rule__LocalPathVersionRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:102:3: ( rule__LocalPathVersionRequirement__Group__0 )
            // InternalSemver.g:102:4: rule__LocalPathVersionRequirement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LocalPathVersionRequirement__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLocalPathVersionRequirement"


    // $ANTLR start "entryRuleURLVersionRequirement"
    // InternalSemver.g:111:1: entryRuleURLVersionRequirement : ruleURLVersionRequirement EOF ;
    public final void entryRuleURLVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:112:1: ( ruleURLVersionRequirement EOF )
            // InternalSemver.g:113:1: ruleURLVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleURLVersionRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleURLVersionRequirement"


    // $ANTLR start "ruleURLVersionRequirement"
    // InternalSemver.g:120:1: ruleURLVersionRequirement : ( ( rule__URLVersionRequirement__Group__0 ) ) ;
    public final void ruleURLVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:124:2: ( ( ( rule__URLVersionRequirement__Group__0 ) ) )
            // InternalSemver.g:125:2: ( ( rule__URLVersionRequirement__Group__0 ) )
            {
            // InternalSemver.g:125:2: ( ( rule__URLVersionRequirement__Group__0 ) )
            // InternalSemver.g:126:3: ( rule__URLVersionRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:127:3: ( rule__URLVersionRequirement__Group__0 )
            // InternalSemver.g:127:4: rule__URLVersionRequirement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleURLVersionRequirement"


    // $ANTLR start "entryRuleURLVersionSpecifier"
    // InternalSemver.g:136:1: entryRuleURLVersionSpecifier : ruleURLVersionSpecifier EOF ;
    public final void entryRuleURLVersionSpecifier() throws RecognitionException {
        try {
            // InternalSemver.g:137:1: ( ruleURLVersionSpecifier EOF )
            // InternalSemver.g:138:1: ruleURLVersionSpecifier EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleURLVersionSpecifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleURLVersionSpecifier"


    // $ANTLR start "ruleURLVersionSpecifier"
    // InternalSemver.g:145:1: ruleURLVersionSpecifier : ( ( rule__URLVersionSpecifier__Alternatives ) ) ;
    public final void ruleURLVersionSpecifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:149:2: ( ( ( rule__URLVersionSpecifier__Alternatives ) ) )
            // InternalSemver.g:150:2: ( ( rule__URLVersionSpecifier__Alternatives ) )
            {
            // InternalSemver.g:150:2: ( ( rule__URLVersionSpecifier__Alternatives ) )
            // InternalSemver.g:151:3: ( rule__URLVersionSpecifier__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getAlternatives()); 
            }
            // InternalSemver.g:152:3: ( rule__URLVersionSpecifier__Alternatives )
            // InternalSemver.g:152:4: rule__URLVersionSpecifier__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleURLVersionSpecifier"


    // $ANTLR start "entryRuleURLSemver"
    // InternalSemver.g:161:1: entryRuleURLSemver : ruleURLSemver EOF ;
    public final void entryRuleURLSemver() throws RecognitionException {
        try {
            // InternalSemver.g:162:1: ( ruleURLSemver EOF )
            // InternalSemver.g:163:1: ruleURLSemver EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleURLSemver();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleURLSemver"


    // $ANTLR start "ruleURLSemver"
    // InternalSemver.g:170:1: ruleURLSemver : ( ( rule__URLSemver__Group__0 ) ) ;
    public final void ruleURLSemver() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:174:2: ( ( ( rule__URLSemver__Group__0 ) ) )
            // InternalSemver.g:175:2: ( ( rule__URLSemver__Group__0 ) )
            {
            // InternalSemver.g:175:2: ( ( rule__URLSemver__Group__0 ) )
            // InternalSemver.g:176:3: ( rule__URLSemver__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getGroup()); 
            }
            // InternalSemver.g:177:3: ( rule__URLSemver__Group__0 )
            // InternalSemver.g:177:4: rule__URLSemver__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__URLSemver__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleURLSemver"


    // $ANTLR start "entryRuleWorkspaceVersionRequirement"
    // InternalSemver.g:186:1: entryRuleWorkspaceVersionRequirement : ruleWorkspaceVersionRequirement EOF ;
    public final void entryRuleWorkspaceVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:187:1: ( ruleWorkspaceVersionRequirement EOF )
            // InternalSemver.g:188:1: ruleWorkspaceVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWorkspaceVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleWorkspaceVersionRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWorkspaceVersionRequirementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWorkspaceVersionRequirement"


    // $ANTLR start "ruleWorkspaceVersionRequirement"
    // InternalSemver.g:195:1: ruleWorkspaceVersionRequirement : ( ( rule__WorkspaceVersionRequirement__Group__0 ) ) ;
    public final void ruleWorkspaceVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:199:2: ( ( ( rule__WorkspaceVersionRequirement__Group__0 ) ) )
            // InternalSemver.g:200:2: ( ( rule__WorkspaceVersionRequirement__Group__0 ) )
            {
            // InternalSemver.g:200:2: ( ( rule__WorkspaceVersionRequirement__Group__0 ) )
            // InternalSemver.g:201:3: ( rule__WorkspaceVersionRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWorkspaceVersionRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:202:3: ( rule__WorkspaceVersionRequirement__Group__0 )
            // InternalSemver.g:202:4: rule__WorkspaceVersionRequirement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__WorkspaceVersionRequirement__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWorkspaceVersionRequirementAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWorkspaceVersionRequirement"


    // $ANTLR start "entryRuleGitHubVersionRequirement"
    // InternalSemver.g:211:1: entryRuleGitHubVersionRequirement : ruleGitHubVersionRequirement EOF ;
    public final void entryRuleGitHubVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:212:1: ( ruleGitHubVersionRequirement EOF )
            // InternalSemver.g:213:1: ruleGitHubVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleGitHubVersionRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGitHubVersionRequirement"


    // $ANTLR start "ruleGitHubVersionRequirement"
    // InternalSemver.g:220:1: ruleGitHubVersionRequirement : ( ( rule__GitHubVersionRequirement__Group__0 ) ) ;
    public final void ruleGitHubVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:224:2: ( ( ( rule__GitHubVersionRequirement__Group__0 ) ) )
            // InternalSemver.g:225:2: ( ( rule__GitHubVersionRequirement__Group__0 ) )
            {
            // InternalSemver.g:225:2: ( ( rule__GitHubVersionRequirement__Group__0 ) )
            // InternalSemver.g:226:3: ( rule__GitHubVersionRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:227:3: ( rule__GitHubVersionRequirement__Group__0 )
            // InternalSemver.g:227:4: rule__GitHubVersionRequirement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__GitHubVersionRequirement__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGitHubVersionRequirement"


    // $ANTLR start "entryRuleTagVersionRequirement"
    // InternalSemver.g:236:1: entryRuleTagVersionRequirement : ruleTagVersionRequirement EOF ;
    public final void entryRuleTagVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:237:1: ( ruleTagVersionRequirement EOF )
            // InternalSemver.g:238:1: ruleTagVersionRequirement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTagVersionRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleTagVersionRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTagVersionRequirementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTagVersionRequirement"


    // $ANTLR start "ruleTagVersionRequirement"
    // InternalSemver.g:245:1: ruleTagVersionRequirement : ( ( rule__TagVersionRequirement__TagNameAssignment ) ) ;
    public final void ruleTagVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:249:2: ( ( ( rule__TagVersionRequirement__TagNameAssignment ) ) )
            // InternalSemver.g:250:2: ( ( rule__TagVersionRequirement__TagNameAssignment ) )
            {
            // InternalSemver.g:250:2: ( ( rule__TagVersionRequirement__TagNameAssignment ) )
            // InternalSemver.g:251:3: ( rule__TagVersionRequirement__TagNameAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTagVersionRequirementAccess().getTagNameAssignment()); 
            }
            // InternalSemver.g:252:3: ( rule__TagVersionRequirement__TagNameAssignment )
            // InternalSemver.g:252:4: rule__TagVersionRequirement__TagNameAssignment
            {
            pushFollow(FOLLOW_2);
            rule__TagVersionRequirement__TagNameAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTagVersionRequirementAccess().getTagNameAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTagVersionRequirement"


    // $ANTLR start "entryRuleVersionRangeSetRequirement"
    // InternalSemver.g:261:1: entryRuleVersionRangeSetRequirement : ruleVersionRangeSetRequirement EOF ;
    public final void entryRuleVersionRangeSetRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:262:1: ( ruleVersionRangeSetRequirement EOF )
            // InternalSemver.g:263:1: ruleVersionRangeSetRequirement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleVersionRangeSetRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVersionRangeSetRequirement"


    // $ANTLR start "ruleVersionRangeSetRequirement"
    // InternalSemver.g:270:1: ruleVersionRangeSetRequirement : ( ( rule__VersionRangeSetRequirement__Group__0 ) ) ;
    public final void ruleVersionRangeSetRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:274:2: ( ( ( rule__VersionRangeSetRequirement__Group__0 ) ) )
            // InternalSemver.g:275:2: ( ( rule__VersionRangeSetRequirement__Group__0 ) )
            {
            // InternalSemver.g:275:2: ( ( rule__VersionRangeSetRequirement__Group__0 ) )
            // InternalSemver.g:276:3: ( rule__VersionRangeSetRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:277:3: ( rule__VersionRangeSetRequirement__Group__0 )
            // InternalSemver.g:277:4: rule__VersionRangeSetRequirement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionRangeSetRequirement"


    // $ANTLR start "entryRuleVersionRange"
    // InternalSemver.g:286:1: entryRuleVersionRange : ruleVersionRange EOF ;
    public final void entryRuleVersionRange() throws RecognitionException {
        try {
            // InternalSemver.g:287:1: ( ruleVersionRange EOF )
            // InternalSemver.g:288:1: ruleVersionRange EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleVersionRange();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVersionRange"


    // $ANTLR start "ruleVersionRange"
    // InternalSemver.g:295:1: ruleVersionRange : ( ( rule__VersionRange__Alternatives ) ) ;
    public final void ruleVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:299:2: ( ( ( rule__VersionRange__Alternatives ) ) )
            // InternalSemver.g:300:2: ( ( rule__VersionRange__Alternatives ) )
            {
            // InternalSemver.g:300:2: ( ( rule__VersionRange__Alternatives ) )
            // InternalSemver.g:301:3: ( rule__VersionRange__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeAccess().getAlternatives()); 
            }
            // InternalSemver.g:302:3: ( rule__VersionRange__Alternatives )
            // InternalSemver.g:302:4: rule__VersionRange__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VersionRange__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionRange"


    // $ANTLR start "entryRuleHyphenVersionRange"
    // InternalSemver.g:311:1: entryRuleHyphenVersionRange : ruleHyphenVersionRange EOF ;
    public final void entryRuleHyphenVersionRange() throws RecognitionException {
        try {
            // InternalSemver.g:312:1: ( ruleHyphenVersionRange EOF )
            // InternalSemver.g:313:1: ruleHyphenVersionRange EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleHyphenVersionRange();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleHyphenVersionRange"


    // $ANTLR start "ruleHyphenVersionRange"
    // InternalSemver.g:320:1: ruleHyphenVersionRange : ( ( rule__HyphenVersionRange__Group__0 ) ) ;
    public final void ruleHyphenVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:324:2: ( ( ( rule__HyphenVersionRange__Group__0 ) ) )
            // InternalSemver.g:325:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            {
            // InternalSemver.g:325:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            // InternalSemver.g:326:3: ( rule__HyphenVersionRange__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getGroup()); 
            }
            // InternalSemver.g:327:3: ( rule__HyphenVersionRange__Group__0 )
            // InternalSemver.g:327:4: rule__HyphenVersionRange__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleHyphenVersionRange"


    // $ANTLR start "entryRuleVersionRangeContraint"
    // InternalSemver.g:336:1: entryRuleVersionRangeContraint : ruleVersionRangeContraint EOF ;
    public final void entryRuleVersionRangeContraint() throws RecognitionException {
        try {
            // InternalSemver.g:337:1: ( ruleVersionRangeContraint EOF )
            // InternalSemver.g:338:1: ruleVersionRangeContraint EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleVersionRangeContraint();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVersionRangeContraint"


    // $ANTLR start "ruleVersionRangeContraint"
    // InternalSemver.g:345:1: ruleVersionRangeContraint : ( ( rule__VersionRangeContraint__Group__0 ) ) ;
    public final void ruleVersionRangeContraint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:349:2: ( ( ( rule__VersionRangeContraint__Group__0 ) ) )
            // InternalSemver.g:350:2: ( ( rule__VersionRangeContraint__Group__0 ) )
            {
            // InternalSemver.g:350:2: ( ( rule__VersionRangeContraint__Group__0 ) )
            // InternalSemver.g:351:3: ( rule__VersionRangeContraint__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup()); 
            }
            // InternalSemver.g:352:3: ( rule__VersionRangeContraint__Group__0 )
            // InternalSemver.g:352:4: rule__VersionRangeContraint__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionRangeContraint"


    // $ANTLR start "entryRuleSimpleVersion"
    // InternalSemver.g:361:1: entryRuleSimpleVersion : ruleSimpleVersion EOF ;
    public final void entryRuleSimpleVersion() throws RecognitionException {
        try {
            // InternalSemver.g:362:1: ( ruleSimpleVersion EOF )
            // InternalSemver.g:363:1: ruleSimpleVersion EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSimpleVersion"


    // $ANTLR start "ruleSimpleVersion"
    // InternalSemver.g:370:1: ruleSimpleVersion : ( ( rule__SimpleVersion__Group__0 ) ) ;
    public final void ruleSimpleVersion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:374:2: ( ( ( rule__SimpleVersion__Group__0 ) ) )
            // InternalSemver.g:375:2: ( ( rule__SimpleVersion__Group__0 ) )
            {
            // InternalSemver.g:375:2: ( ( rule__SimpleVersion__Group__0 ) )
            // InternalSemver.g:376:3: ( rule__SimpleVersion__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup()); 
            }
            // InternalSemver.g:377:3: ( rule__SimpleVersion__Group__0 )
            // InternalSemver.g:377:4: rule__SimpleVersion__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSimpleVersion"


    // $ANTLR start "entryRuleVersionNumber"
    // InternalSemver.g:386:1: entryRuleVersionNumber : ruleVersionNumber EOF ;
    public final void entryRuleVersionNumber() throws RecognitionException {
        try {
            // InternalSemver.g:387:1: ( ruleVersionNumber EOF )
            // InternalSemver.g:388:1: ruleVersionNumber EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleVersionNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVersionNumber"


    // $ANTLR start "ruleVersionNumber"
    // InternalSemver.g:395:1: ruleVersionNumber : ( ( rule__VersionNumber__Group__0 ) ) ;
    public final void ruleVersionNumber() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:399:2: ( ( ( rule__VersionNumber__Group__0 ) ) )
            // InternalSemver.g:400:2: ( ( rule__VersionNumber__Group__0 ) )
            {
            // InternalSemver.g:400:2: ( ( rule__VersionNumber__Group__0 ) )
            // InternalSemver.g:401:3: ( rule__VersionNumber__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup()); 
            }
            // InternalSemver.g:402:3: ( rule__VersionNumber__Group__0 )
            // InternalSemver.g:402:4: rule__VersionNumber__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionNumber"


    // $ANTLR start "entryRuleVersionPart"
    // InternalSemver.g:411:1: entryRuleVersionPart : ruleVersionPart EOF ;
    public final void entryRuleVersionPart() throws RecognitionException {
        try {
            // InternalSemver.g:412:1: ( ruleVersionPart EOF )
            // InternalSemver.g:413:1: ruleVersionPart EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionPartRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleVersionPart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionPartRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVersionPart"


    // $ANTLR start "ruleVersionPart"
    // InternalSemver.g:420:1: ruleVersionPart : ( ( rule__VersionPart__Alternatives ) ) ;
    public final void ruleVersionPart() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:424:2: ( ( ( rule__VersionPart__Alternatives ) ) )
            // InternalSemver.g:425:2: ( ( rule__VersionPart__Alternatives ) )
            {
            // InternalSemver.g:425:2: ( ( rule__VersionPart__Alternatives ) )
            // InternalSemver.g:426:3: ( rule__VersionPart__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionPartAccess().getAlternatives()); 
            }
            // InternalSemver.g:427:3: ( rule__VersionPart__Alternatives )
            // InternalSemver.g:427:4: rule__VersionPart__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VersionPart__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionPartAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionPart"


    // $ANTLR start "entryRuleQualifier"
    // InternalSemver.g:436:1: entryRuleQualifier : ruleQualifier EOF ;
    public final void entryRuleQualifier() throws RecognitionException {
        try {
            // InternalSemver.g:437:1: ( ruleQualifier EOF )
            // InternalSemver.g:438:1: ruleQualifier EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleQualifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleQualifier"


    // $ANTLR start "ruleQualifier"
    // InternalSemver.g:445:1: ruleQualifier : ( ( rule__Qualifier__Alternatives ) ) ;
    public final void ruleQualifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:449:2: ( ( ( rule__Qualifier__Alternatives ) ) )
            // InternalSemver.g:450:2: ( ( rule__Qualifier__Alternatives ) )
            {
            // InternalSemver.g:450:2: ( ( rule__Qualifier__Alternatives ) )
            // InternalSemver.g:451:3: ( rule__Qualifier__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getAlternatives()); 
            }
            // InternalSemver.g:452:3: ( rule__Qualifier__Alternatives )
            // InternalSemver.g:452:4: rule__Qualifier__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQualifier"


    // $ANTLR start "entryRuleQualifierTag"
    // InternalSemver.g:461:1: entryRuleQualifierTag : ruleQualifierTag EOF ;
    public final void entryRuleQualifierTag() throws RecognitionException {
        try {
            // InternalSemver.g:462:1: ( ruleQualifierTag EOF )
            // InternalSemver.g:463:1: ruleQualifierTag EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleQualifierTag();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleQualifierTag"


    // $ANTLR start "ruleQualifierTag"
    // InternalSemver.g:470:1: ruleQualifierTag : ( ( rule__QualifierTag__Group__0 ) ) ;
    public final void ruleQualifierTag() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:474:2: ( ( ( rule__QualifierTag__Group__0 ) ) )
            // InternalSemver.g:475:2: ( ( rule__QualifierTag__Group__0 ) )
            {
            // InternalSemver.g:475:2: ( ( rule__QualifierTag__Group__0 ) )
            // InternalSemver.g:476:3: ( rule__QualifierTag__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup()); 
            }
            // InternalSemver.g:477:3: ( rule__QualifierTag__Group__0 )
            // InternalSemver.g:477:4: rule__QualifierTag__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQualifierTag"


    // $ANTLR start "entryRuleFILE_TAG"
    // InternalSemver.g:486:1: entryRuleFILE_TAG : ruleFILE_TAG EOF ;
    public final void entryRuleFILE_TAG() throws RecognitionException {
        try {
            // InternalSemver.g:487:1: ( ruleFILE_TAG EOF )
            // InternalSemver.g:488:1: ruleFILE_TAG EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleFILE_TAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFILE_TAGRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFILE_TAG"


    // $ANTLR start "ruleFILE_TAG"
    // InternalSemver.g:495:1: ruleFILE_TAG : ( ( rule__FILE_TAG__Group__0 ) ) ;
    public final void ruleFILE_TAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:499:2: ( ( ( rule__FILE_TAG__Group__0 ) ) )
            // InternalSemver.g:500:2: ( ( rule__FILE_TAG__Group__0 ) )
            {
            // InternalSemver.g:500:2: ( ( rule__FILE_TAG__Group__0 ) )
            // InternalSemver.g:501:3: ( rule__FILE_TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getGroup()); 
            }
            // InternalSemver.g:502:3: ( rule__FILE_TAG__Group__0 )
            // InternalSemver.g:502:4: rule__FILE_TAG__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__FILE_TAG__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFILE_TAGAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFILE_TAG"


    // $ANTLR start "entryRuleSEMVER_TAG"
    // InternalSemver.g:511:1: entryRuleSEMVER_TAG : ruleSEMVER_TAG EOF ;
    public final void entryRuleSEMVER_TAG() throws RecognitionException {
        try {
            // InternalSemver.g:512:1: ( ruleSEMVER_TAG EOF )
            // InternalSemver.g:513:1: ruleSEMVER_TAG EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleSEMVER_TAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSEMVER_TAG"


    // $ANTLR start "ruleSEMVER_TAG"
    // InternalSemver.g:520:1: ruleSEMVER_TAG : ( ( rule__SEMVER_TAG__Group__0 ) ) ;
    public final void ruleSEMVER_TAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:524:2: ( ( ( rule__SEMVER_TAG__Group__0 ) ) )
            // InternalSemver.g:525:2: ( ( rule__SEMVER_TAG__Group__0 ) )
            {
            // InternalSemver.g:525:2: ( ( rule__SEMVER_TAG__Group__0 ) )
            // InternalSemver.g:526:3: ( rule__SEMVER_TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getGroup()); 
            }
            // InternalSemver.g:527:3: ( rule__SEMVER_TAG__Group__0 )
            // InternalSemver.g:527:4: rule__SEMVER_TAG__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSEMVER_TAG"


    // $ANTLR start "entryRuleWORKSPACE_TAG"
    // InternalSemver.g:536:1: entryRuleWORKSPACE_TAG : ruleWORKSPACE_TAG EOF ;
    public final void entryRuleWORKSPACE_TAG() throws RecognitionException {
        try {
            // InternalSemver.g:537:1: ( ruleWORKSPACE_TAG EOF )
            // InternalSemver.g:538:1: ruleWORKSPACE_TAG EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleWORKSPACE_TAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWORKSPACE_TAG"


    // $ANTLR start "ruleWORKSPACE_TAG"
    // InternalSemver.g:545:1: ruleWORKSPACE_TAG : ( ( rule__WORKSPACE_TAG__Group__0 ) ) ;
    public final void ruleWORKSPACE_TAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:549:2: ( ( ( rule__WORKSPACE_TAG__Group__0 ) ) )
            // InternalSemver.g:550:2: ( ( rule__WORKSPACE_TAG__Group__0 ) )
            {
            // InternalSemver.g:550:2: ( ( rule__WORKSPACE_TAG__Group__0 ) )
            // InternalSemver.g:551:3: ( rule__WORKSPACE_TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getGroup()); 
            }
            // InternalSemver.g:552:3: ( rule__WORKSPACE_TAG__Group__0 )
            // InternalSemver.g:552:4: rule__WORKSPACE_TAG__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWORKSPACE_TAG"


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:561:1: entryRulePATH : rulePATH EOF ;
    public final void entryRulePATH() throws RecognitionException {
        try {
            // InternalSemver.g:562:1: ( rulePATH EOF )
            // InternalSemver.g:563:1: rulePATH EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHRule()); 
            }
            pushFollow(FOLLOW_1);
            rulePATH();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePATH"


    // $ANTLR start "rulePATH"
    // InternalSemver.g:570:1: rulePATH : ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) ) ;
    public final void rulePATH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:574:2: ( ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) ) )
            // InternalSemver.g:575:2: ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) )
            {
            // InternalSemver.g:575:2: ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) )
            // InternalSemver.g:576:3: ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* )
            {
            // InternalSemver.g:576:3: ( ( rule__PATH__Alternatives ) )
            // InternalSemver.g:577:4: ( rule__PATH__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives()); 
            }
            // InternalSemver.g:578:4: ( rule__PATH__Alternatives )
            // InternalSemver.g:578:5: rule__PATH__Alternatives
            {
            pushFollow(FOLLOW_3);
            rule__PATH__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives()); 
            }

            }

            // InternalSemver.g:581:3: ( ( rule__PATH__Alternatives )* )
            // InternalSemver.g:582:4: ( rule__PATH__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives()); 
            }
            // InternalSemver.g:583:4: ( rule__PATH__Alternatives )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_DIGITS && LA1_0<=RULE_LETTER_OTHER)||(LA1_0>=41 && LA1_0<=44)||LA1_0==47) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSemver.g:583:5: rule__PATH__Alternatives
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__PATH__Alternatives();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives()); 
            }

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePATH"


    // $ANTLR start "entryRuleURL_PROTOCOL"
    // InternalSemver.g:593:1: entryRuleURL_PROTOCOL : ruleURL_PROTOCOL EOF ;
    public final void entryRuleURL_PROTOCOL() throws RecognitionException {
        try {
            // InternalSemver.g:594:1: ( ruleURL_PROTOCOL EOF )
            // InternalSemver.g:595:1: ruleURL_PROTOCOL EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleURL_PROTOCOL();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleURL_PROTOCOL"


    // $ANTLR start "ruleURL_PROTOCOL"
    // InternalSemver.g:602:1: ruleURL_PROTOCOL : ( ( rule__URL_PROTOCOL__Group__0 ) ) ;
    public final void ruleURL_PROTOCOL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:606:2: ( ( ( rule__URL_PROTOCOL__Group__0 ) ) )
            // InternalSemver.g:607:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            {
            // InternalSemver.g:607:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            // InternalSemver.g:608:3: ( rule__URL_PROTOCOL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getGroup()); 
            }
            // InternalSemver.g:609:3: ( rule__URL_PROTOCOL__Group__0 )
            // InternalSemver.g:609:4: rule__URL_PROTOCOL__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__URL_PROTOCOL__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleURL_PROTOCOL"


    // $ANTLR start "entryRuleURL"
    // InternalSemver.g:618:1: entryRuleURL : ruleURL EOF ;
    public final void entryRuleURL() throws RecognitionException {
        try {
            // InternalSemver.g:619:1: ( ruleURL EOF )
            // InternalSemver.g:620:1: ruleURL EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleURL();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleURL"


    // $ANTLR start "ruleURL"
    // InternalSemver.g:627:1: ruleURL : ( ( rule__URL__Group__0 ) ) ;
    public final void ruleURL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:631:2: ( ( ( rule__URL__Group__0 ) ) )
            // InternalSemver.g:632:2: ( ( rule__URL__Group__0 ) )
            {
            // InternalSemver.g:632:2: ( ( rule__URL__Group__0 ) )
            // InternalSemver.g:633:3: ( rule__URL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup()); 
            }
            // InternalSemver.g:634:3: ( rule__URL__Group__0 )
            // InternalSemver.g:634:4: rule__URL__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__URL__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleURL"


    // $ANTLR start "entryRuleURL_NO_VX"
    // InternalSemver.g:643:1: entryRuleURL_NO_VX : ruleURL_NO_VX EOF ;
    public final void entryRuleURL_NO_VX() throws RecognitionException {
        try {
            // InternalSemver.g:644:1: ( ruleURL_NO_VX EOF )
            // InternalSemver.g:645:1: ruleURL_NO_VX EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleURL_NO_VX();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_NO_VXRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleURL_NO_VX"


    // $ANTLR start "ruleURL_NO_VX"
    // InternalSemver.g:652:1: ruleURL_NO_VX : ( ( rule__URL_NO_VX__Group__0 ) ) ;
    public final void ruleURL_NO_VX() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:656:2: ( ( ( rule__URL_NO_VX__Group__0 ) ) )
            // InternalSemver.g:657:2: ( ( rule__URL_NO_VX__Group__0 ) )
            {
            // InternalSemver.g:657:2: ( ( rule__URL_NO_VX__Group__0 ) )
            // InternalSemver.g:658:3: ( rule__URL_NO_VX__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getGroup()); 
            }
            // InternalSemver.g:659:3: ( rule__URL_NO_VX__Group__0 )
            // InternalSemver.g:659:4: rule__URL_NO_VX__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__URL_NO_VX__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_NO_VXAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleURL_NO_VX"


    // $ANTLR start "entryRuleTAG"
    // InternalSemver.g:668:1: entryRuleTAG : ruleTAG EOF ;
    public final void entryRuleTAG() throws RecognitionException {
        try {
            // InternalSemver.g:669:1: ( ruleTAG EOF )
            // InternalSemver.g:670:1: ruleTAG EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleTAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTAG"


    // $ANTLR start "ruleTAG"
    // InternalSemver.g:677:1: ruleTAG : ( ( rule__TAG__Group__0 ) ) ;
    public final void ruleTAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:681:2: ( ( ( rule__TAG__Group__0 ) ) )
            // InternalSemver.g:682:2: ( ( rule__TAG__Group__0 ) )
            {
            // InternalSemver.g:682:2: ( ( rule__TAG__Group__0 ) )
            // InternalSemver.g:683:3: ( rule__TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getGroup()); 
            }
            // InternalSemver.g:684:3: ( rule__TAG__Group__0 )
            // InternalSemver.g:684:4: rule__TAG__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__TAG__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTAG"


    // $ANTLR start "entryRuleWORKSPACE_VERSION"
    // InternalSemver.g:693:1: entryRuleWORKSPACE_VERSION : ruleWORKSPACE_VERSION EOF ;
    public final void entryRuleWORKSPACE_VERSION() throws RecognitionException {
        try {
            // InternalSemver.g:694:1: ( ruleWORKSPACE_VERSION EOF )
            // InternalSemver.g:695:1: ruleWORKSPACE_VERSION EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_VERSIONRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleWORKSPACE_VERSION();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_VERSIONRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWORKSPACE_VERSION"


    // $ANTLR start "ruleWORKSPACE_VERSION"
    // InternalSemver.g:702:1: ruleWORKSPACE_VERSION : ( ( ( rule__WORKSPACE_VERSION__Alternatives ) ) ( ( rule__WORKSPACE_VERSION__Alternatives )* ) ) ;
    public final void ruleWORKSPACE_VERSION() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:706:2: ( ( ( ( rule__WORKSPACE_VERSION__Alternatives ) ) ( ( rule__WORKSPACE_VERSION__Alternatives )* ) ) )
            // InternalSemver.g:707:2: ( ( ( rule__WORKSPACE_VERSION__Alternatives ) ) ( ( rule__WORKSPACE_VERSION__Alternatives )* ) )
            {
            // InternalSemver.g:707:2: ( ( ( rule__WORKSPACE_VERSION__Alternatives ) ) ( ( rule__WORKSPACE_VERSION__Alternatives )* ) )
            // InternalSemver.g:708:3: ( ( rule__WORKSPACE_VERSION__Alternatives ) ) ( ( rule__WORKSPACE_VERSION__Alternatives )* )
            {
            // InternalSemver.g:708:3: ( ( rule__WORKSPACE_VERSION__Alternatives ) )
            // InternalSemver.g:709:4: ( rule__WORKSPACE_VERSION__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_VERSIONAccess().getAlternatives()); 
            }
            // InternalSemver.g:710:4: ( rule__WORKSPACE_VERSION__Alternatives )
            // InternalSemver.g:710:5: rule__WORKSPACE_VERSION__Alternatives
            {
            pushFollow(FOLLOW_4);
            rule__WORKSPACE_VERSION__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_VERSIONAccess().getAlternatives()); 
            }

            }

            // InternalSemver.g:713:3: ( ( rule__WORKSPACE_VERSION__Alternatives )* )
            // InternalSemver.g:714:4: ( rule__WORKSPACE_VERSION__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_VERSIONAccess().getAlternatives()); 
            }
            // InternalSemver.g:715:4: ( rule__WORKSPACE_VERSION__Alternatives )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=RULE_ASTERIX && LA2_0<=RULE_LETTER_OTHER)||(LA2_0>=41 && LA2_0<=44)||(LA2_0>=46 && LA2_0<=54)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSemver.g:715:5: rule__WORKSPACE_VERSION__Alternatives
            	    {
            	    pushFollow(FOLLOW_4);
            	    rule__WORKSPACE_VERSION__Alternatives();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_VERSIONAccess().getAlternatives()); 
            }

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWORKSPACE_VERSION"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"
    // InternalSemver.g:725:1: entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        try {
            // InternalSemver.g:726:1: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF )
            // InternalSemver.g:727:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"


    // $ANTLR start "ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"
    // InternalSemver.g:734:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:738:2: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) )
            // InternalSemver.g:739:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            {
            // InternalSemver.g:739:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            // InternalSemver.g:740:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup()); 
            }
            // InternalSemver.g:741:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            // InternalSemver.g:741:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSemver.g:750:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSemver.g:751:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:752:1: ruleALPHA_NUMERIC_CHARS EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARSRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHARS"


    // $ANTLR start "ruleALPHA_NUMERIC_CHARS"
    // InternalSemver.g:759:1: ruleALPHA_NUMERIC_CHARS : ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:763:2: ( ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) )
            // InternalSemver.g:764:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            {
            // InternalSemver.g:764:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            // InternalSemver.g:765:3: ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* )
            {
            // InternalSemver.g:765:3: ( ( ruleALPHA_NUMERIC_CHAR ) )
            // InternalSemver.g:766:4: ( ruleALPHA_NUMERIC_CHAR )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            }
            // InternalSemver.g:767:4: ( ruleALPHA_NUMERIC_CHAR )
            // InternalSemver.g:767:5: ruleALPHA_NUMERIC_CHAR
            {
            pushFollow(FOLLOW_3);
            ruleALPHA_NUMERIC_CHAR();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            }

            }

            // InternalSemver.g:770:3: ( ( ruleALPHA_NUMERIC_CHAR )* )
            // InternalSemver.g:771:4: ( ruleALPHA_NUMERIC_CHAR )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            }
            // InternalSemver.g:772:4: ( ruleALPHA_NUMERIC_CHAR )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=RULE_DIGITS && LA3_0<=RULE_LETTER_OTHER)||LA3_0==47) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSemver.g:772:5: ruleALPHA_NUMERIC_CHAR
            	    {
            	    pushFollow(FOLLOW_3);
            	    ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            }

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleALPHA_NUMERIC_CHARS"


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHAR"
    // InternalSemver.g:782:1: entryRuleALPHA_NUMERIC_CHAR : ruleALPHA_NUMERIC_CHAR EOF ;
    public final void entryRuleALPHA_NUMERIC_CHAR() throws RecognitionException {
        try {
            // InternalSemver.g:783:1: ( ruleALPHA_NUMERIC_CHAR EOF )
            // InternalSemver.g:784:1: ruleALPHA_NUMERIC_CHAR EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleALPHA_NUMERIC_CHAR();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHAR"


    // $ANTLR start "ruleALPHA_NUMERIC_CHAR"
    // InternalSemver.g:791:1: ruleALPHA_NUMERIC_CHAR : ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives ) ) ;
    public final void ruleALPHA_NUMERIC_CHAR() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:795:2: ( ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives ) ) )
            // InternalSemver.g:796:2: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives ) )
            {
            // InternalSemver.g:796:2: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives ) )
            // InternalSemver.g:797:3: ( rule__ALPHA_NUMERIC_CHAR__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives()); 
            }
            // InternalSemver.g:798:3: ( rule__ALPHA_NUMERIC_CHAR__Alternatives )
            // InternalSemver.g:798:4: rule__ALPHA_NUMERIC_CHAR__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleALPHA_NUMERIC_CHAR"


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSemver.g:807:1: entryRuleWILDCARD : ruleWILDCARD EOF ;
    public final void entryRuleWILDCARD() throws RecognitionException {
        try {
            // InternalSemver.g:808:1: ( ruleWILDCARD EOF )
            // InternalSemver.g:809:1: ruleWILDCARD EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWILDCARDRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleWILDCARD();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWILDCARDRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWILDCARD"


    // $ANTLR start "ruleWILDCARD"
    // InternalSemver.g:816:1: ruleWILDCARD : ( ( rule__WILDCARD__Alternatives ) ) ;
    public final void ruleWILDCARD() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:820:2: ( ( ( rule__WILDCARD__Alternatives ) ) )
            // InternalSemver.g:821:2: ( ( rule__WILDCARD__Alternatives ) )
            {
            // InternalSemver.g:821:2: ( ( rule__WILDCARD__Alternatives ) )
            // InternalSemver.g:822:3: ( rule__WILDCARD__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWILDCARDAccess().getAlternatives()); 
            }
            // InternalSemver.g:823:3: ( rule__WILDCARD__Alternatives )
            // InternalSemver.g:823:4: rule__WILDCARD__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__WILDCARD__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWILDCARDAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWILDCARD"


    // $ANTLR start "ruleLETTER"
    // InternalSemver.g:833:1: ruleLETTER : ( ( rule__LETTER__Alternatives ) ) ;
    public final void ruleLETTER() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:837:2: ( ( ( rule__LETTER__Alternatives ) ) )
            // InternalSemver.g:838:2: ( ( rule__LETTER__Alternatives ) )
            {
            // InternalSemver.g:838:2: ( ( rule__LETTER__Alternatives ) )
            // InternalSemver.g:839:3: ( rule__LETTER__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTERAccess().getAlternatives()); 
            }
            // InternalSemver.g:840:3: ( rule__LETTER__Alternatives )
            // InternalSemver.g:840:4: rule__LETTER__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__LETTER__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLETTERAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLETTER"


    // $ANTLR start "ruleLETTER_NO_VX"
    // InternalSemver.g:850:1: ruleLETTER_NO_VX : ( ( rule__LETTER_NO_VX__Alternatives ) ) ;
    public final void ruleLETTER_NO_VX() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:854:2: ( ( ( rule__LETTER_NO_VX__Alternatives ) ) )
            // InternalSemver.g:855:2: ( ( rule__LETTER_NO_VX__Alternatives ) )
            {
            // InternalSemver.g:855:2: ( ( rule__LETTER_NO_VX__Alternatives ) )
            // InternalSemver.g:856:3: ( rule__LETTER_NO_VX__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTER_NO_VXAccess().getAlternatives()); 
            }
            // InternalSemver.g:857:3: ( rule__LETTER_NO_VX__Alternatives )
            // InternalSemver.g:857:4: rule__LETTER_NO_VX__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__LETTER_NO_VX__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLETTER_NO_VXAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLETTER_NO_VX"


    // $ANTLR start "ruleVersionComparator"
    // InternalSemver.g:866:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:870:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSemver.g:871:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSemver.g:871:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSemver.g:872:3: ( rule__VersionComparator__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            }
            // InternalSemver.g:873:3: ( rule__VersionComparator__Alternatives )
            // InternalSemver.g:873:4: rule__VersionComparator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VersionComparator__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionComparator"


    // $ANTLR start "rule__NPMVersionRequirement__Alternatives"
    // InternalSemver.g:881:1: rule__NPMVersionRequirement__Alternatives : ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:885:1: ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==EOF||(LA4_0>=RULE_ASTERIX && LA4_0<=RULE_LETTER_V)||LA4_0==RULE_WS||(LA4_0>=48 && LA4_0<=54)) ) {
                alt4=1;
            }
            else if ( ((LA4_0>=RULE_LETTER_A && LA4_0<=RULE_LETTER_OTHER)||LA4_0==44||LA4_0==47) ) {
                alt4=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalSemver.g:886:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    {
                    // InternalSemver.g:886:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    // InternalSemver.g:887:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:888:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    // InternalSemver.g:888:4: rule__NPMVersionRequirement__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__NPMVersionRequirement__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:892:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    {
                    // InternalSemver.g:892:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    // InternalSemver.g:893:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:894:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    // InternalSemver.g:894:4: rule__NPMVersionRequirement__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__NPMVersionRequirement__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Alternatives"


    // $ANTLR start "rule__NPMVersionRequirement__Alternatives_1_0"
    // InternalSemver.g:902:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:906:1: ( ( ( ruleLocalPathVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) ) )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSemver.g:907:2: ( ( ruleLocalPathVersionRequirement ) )
                    {
                    // InternalSemver.g:907:2: ( ( ruleLocalPathVersionRequirement ) )
                    // InternalSemver.g:908:3: ( ruleLocalPathVersionRequirement )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
                    }
                    // InternalSemver.g:909:3: ( ruleLocalPathVersionRequirement )
                    // InternalSemver.g:909:4: ruleLocalPathVersionRequirement
                    {
                    pushFollow(FOLLOW_2);
                    ruleLocalPathVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:913:2: ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) )
                    {
                    // InternalSemver.g:913:2: ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) )
                    // InternalSemver.g:914:3: ( rule__NPMVersionRequirement__Alternatives_1_0_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1()); 
                    }
                    // InternalSemver.g:915:3: ( rule__NPMVersionRequirement__Alternatives_1_0_1 )
                    // InternalSemver.g:915:4: rule__NPMVersionRequirement__Alternatives_1_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__NPMVersionRequirement__Alternatives_1_0_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Alternatives_1_0"


    // $ANTLR start "rule__NPMVersionRequirement__Alternatives_1_0_1"
    // InternalSemver.g:923:1: rule__NPMVersionRequirement__Alternatives_1_0_1 : ( ( ( ruleURLVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1_1 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:927:1: ( ( ( ruleURLVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1_1 ) ) )
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // InternalSemver.g:928:2: ( ( ruleURLVersionRequirement ) )
                    {
                    // InternalSemver.g:928:2: ( ( ruleURLVersionRequirement ) )
                    // InternalSemver.g:929:3: ( ruleURLVersionRequirement )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0()); 
                    }
                    // InternalSemver.g:930:3: ( ruleURLVersionRequirement )
                    // InternalSemver.g:930:4: ruleURLVersionRequirement
                    {
                    pushFollow(FOLLOW_2);
                    ruleURLVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:934:2: ( ( rule__NPMVersionRequirement__Alternatives_1_0_1_1 ) )
                    {
                    // InternalSemver.g:934:2: ( ( rule__NPMVersionRequirement__Alternatives_1_0_1_1 ) )
                    // InternalSemver.g:935:3: ( rule__NPMVersionRequirement__Alternatives_1_0_1_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1_1()); 
                    }
                    // InternalSemver.g:936:3: ( rule__NPMVersionRequirement__Alternatives_1_0_1_1 )
                    // InternalSemver.g:936:4: rule__NPMVersionRequirement__Alternatives_1_0_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__NPMVersionRequirement__Alternatives_1_0_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Alternatives_1_0_1"


    // $ANTLR start "rule__NPMVersionRequirement__Alternatives_1_0_1_1"
    // InternalSemver.g:944:1: rule__NPMVersionRequirement__Alternatives_1_0_1_1 : ( ( ( ruleWorkspaceVersionRequirement ) ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:948:1: ( ( ( ruleWorkspaceVersionRequirement ) ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) )
            int alt7=3;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:949:2: ( ( ruleWorkspaceVersionRequirement ) )
                    {
                    // InternalSemver.g:949:2: ( ( ruleWorkspaceVersionRequirement ) )
                    // InternalSemver.g:950:3: ( ruleWorkspaceVersionRequirement )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getWorkspaceVersionRequirementParserRuleCall_1_0_1_1_0()); 
                    }
                    // InternalSemver.g:951:3: ( ruleWorkspaceVersionRequirement )
                    // InternalSemver.g:951:4: ruleWorkspaceVersionRequirement
                    {
                    pushFollow(FOLLOW_2);
                    ruleWorkspaceVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getWorkspaceVersionRequirementParserRuleCall_1_0_1_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:955:2: ( ruleGitHubVersionRequirement )
                    {
                    // InternalSemver.g:955:2: ( ruleGitHubVersionRequirement )
                    // InternalSemver.g:956:3: ruleGitHubVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleGitHubVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:961:2: ( ruleTagVersionRequirement )
                    {
                    // InternalSemver.g:961:2: ( ruleTagVersionRequirement )
                    // InternalSemver.g:962:3: ruleTagVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_1_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleTagVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_1_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Alternatives_1_0_1_1"


    // $ANTLR start "rule__URLVersionSpecifier__Alternatives"
    // InternalSemver.g:971:1: rule__URLVersionSpecifier__Alternatives : ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) );
    public final void rule__URLVersionSpecifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:975:1: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) )
            int alt8=3;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // InternalSemver.g:976:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:976:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    // InternalSemver.g:977:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:978:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    // InternalSemver.g:978:4: rule__URLVersionSpecifier__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__URLVersionSpecifier__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:982:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:982:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    // InternalSemver.g:983:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:984:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    // InternalSemver.g:984:4: rule__URLVersionSpecifier__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__URLVersionSpecifier__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLVersionSpecifierAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:988:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:988:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    // InternalSemver.g:989:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:990:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    // InternalSemver.g:990:4: rule__URLVersionSpecifier__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__URLVersionSpecifier__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLVersionSpecifierAccess().getGroup_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Alternatives"


    // $ANTLR start "rule__WorkspaceVersionRequirement__Alternatives_1"
    // InternalSemver.g:998:1: rule__WorkspaceVersionRequirement__Alternatives_1 : ( ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) ) | ( ( rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 ) ) );
    public final void rule__WorkspaceVersionRequirement__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1002:1: ( ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) ) | ( ( rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 ) ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // InternalSemver.g:1003:2: ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) )
                    {
                    // InternalSemver.g:1003:2: ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) )
                    // InternalSemver.g:1004:3: ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWorkspaceVersionRequirementAccess().getVersionAssignment_1_0()); 
                    }
                    // InternalSemver.g:1005:3: ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 )
                    // InternalSemver.g:1005:4: rule__WorkspaceVersionRequirement__VersionAssignment_1_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__WorkspaceVersionRequirement__VersionAssignment_1_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWorkspaceVersionRequirementAccess().getVersionAssignment_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1009:2: ( ( rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 ) )
                    {
                    // InternalSemver.g:1009:2: ( ( rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 ) )
                    // InternalSemver.g:1010:3: ( rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWorkspaceVersionRequirementAccess().getOtherVersionAssignment_1_1()); 
                    }
                    // InternalSemver.g:1011:3: ( rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 )
                    // InternalSemver.g:1011:4: rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWorkspaceVersionRequirementAccess().getOtherVersionAssignment_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WorkspaceVersionRequirement__Alternatives_1"


    // $ANTLR start "rule__VersionRange__Alternatives"
    // InternalSemver.g:1019:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1023:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // InternalSemver.g:1024:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSemver.g:1024:2: ( ruleVersionRangeContraint )
                    // InternalSemver.g:1025:3: ruleVersionRangeContraint
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleVersionRangeContraint();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1030:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSemver.g:1030:2: ( ruleHyphenVersionRange )
                    // InternalSemver.g:1031:3: ruleHyphenVersionRange
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleHyphenVersionRange();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRange__Alternatives"


    // $ANTLR start "rule__VersionPart__Alternatives"
    // InternalSemver.g:1040:1: rule__VersionPart__Alternatives : ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) );
    public final void rule__VersionPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1044:1: ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_ASTERIX||LA11_0==RULE_LETTER_X) ) {
                alt11=1;
            }
            else if ( (LA11_0==RULE_DIGITS) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalSemver.g:1045:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    {
                    // InternalSemver.g:1045:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    // InternalSemver.g:1046:3: ( rule__VersionPart__WildcardAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    }
                    // InternalSemver.g:1047:3: ( rule__VersionPart__WildcardAssignment_0 )
                    // InternalSemver.g:1047:4: rule__VersionPart__WildcardAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionPart__WildcardAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1051:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    {
                    // InternalSemver.g:1051:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    // InternalSemver.g:1052:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    }
                    // InternalSemver.g:1053:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    // InternalSemver.g:1053:4: rule__VersionPart__NumberRawAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionPart__NumberRawAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionPart__Alternatives"


    // $ANTLR start "rule__Qualifier__Alternatives"
    // InternalSemver.g:1061:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1065:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==47) ) {
                alt12=1;
            }
            else if ( (LA12_0==45) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalSemver.g:1066:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:1066:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSemver.g:1067:3: ( rule__Qualifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:1068:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSemver.g:1068:4: rule__Qualifier__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getQualifierAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1072:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:1072:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSemver.g:1073:3: ( rule__Qualifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:1074:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSemver.g:1074:4: rule__Qualifier__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getQualifierAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Alternatives"


    // $ANTLR start "rule__PATH__Alternatives"
    // InternalSemver.g:1082:1: rule__PATH__Alternatives : ( ( '/' ) | ( '.' ) | ( '@' ) | ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) );
    public final void rule__PATH__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1086:1: ( ( '/' ) | ( '.' ) | ( '@' ) | ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) )
            int alt13=5;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt13=1;
                }
                break;
            case 42:
                {
                alt13=2;
                }
                break;
            case 43:
                {
                alt13=3;
                }
                break;
            case 44:
                {
                alt13=4;
                }
                break;
            case RULE_DIGITS:
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_E:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_K:
            case RULE_LETTER_L:
            case RULE_LETTER_M:
            case RULE_LETTER_O:
            case RULE_LETTER_P:
            case RULE_LETTER_R:
            case RULE_LETTER_S:
            case RULE_LETTER_W:
            case RULE_LETTER_OTHER:
            case 47:
                {
                alt13=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // InternalSemver.g:1087:2: ( '/' )
                    {
                    // InternalSemver.g:1087:2: ( '/' )
                    // InternalSemver.g:1088:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_0()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1093:2: ( '.' )
                    {
                    // InternalSemver.g:1093:2: ( '.' )
                    // InternalSemver.g:1094:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_1()); 
                    }
                    match(input,42,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1099:2: ( '@' )
                    {
                    // InternalSemver.g:1099:2: ( '@' )
                    // InternalSemver.g:1100:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getCommercialAtKeyword_2()); 
                    }
                    match(input,43,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getCommercialAtKeyword_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1105:2: ( '_' )
                    {
                    // InternalSemver.g:1105:2: ( '_' )
                    // InternalSemver.g:1106:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().get_Keyword_3()); 
                    }
                    match(input,44,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().get_Keyword_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1111:2: ( ruleALPHA_NUMERIC_CHAR )
                    {
                    // InternalSemver.g:1111:2: ( ruleALPHA_NUMERIC_CHAR )
                    // InternalSemver.g:1112:3: ruleALPHA_NUMERIC_CHAR
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARParserRuleCall_4()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleALPHA_NUMERIC_CHAR();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARParserRuleCall_4()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PATH__Alternatives"


    // $ANTLR start "rule__URL_PROTOCOL__Alternatives_1"
    // InternalSemver.g:1121:1: rule__URL_PROTOCOL__Alternatives_1 : ( ( ruleLETTER ) | ( '+' ) );
    public final void rule__URL_PROTOCOL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1125:1: ( ( ruleLETTER ) | ( '+' ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=RULE_LETTER_X && LA14_0<=RULE_LETTER_OTHER)) ) {
                alt14=1;
            }
            else if ( (LA14_0==45) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalSemver.g:1126:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1126:2: ( ruleLETTER )
                    // InternalSemver.g:1127:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getLETTERParserRuleCall_1_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getLETTERParserRuleCall_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1132:2: ( '+' )
                    {
                    // InternalSemver.g:1132:2: ( '+' )
                    // InternalSemver.g:1133:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1()); 
                    }
                    match(input,45,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_PROTOCOL__Alternatives_1"


    // $ANTLR start "rule__URL__Alternatives_0"
    // InternalSemver.g:1142:1: rule__URL__Alternatives_0 : ( ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) );
    public final void rule__URL__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1146:1: ( ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==44) ) {
                alt15=1;
            }
            else if ( ((LA15_0>=RULE_DIGITS && LA15_0<=RULE_LETTER_OTHER)||LA15_0==47) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalSemver.g:1147:2: ( '_' )
                    {
                    // InternalSemver.g:1147:2: ( '_' )
                    // InternalSemver.g:1148:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().get_Keyword_0_0()); 
                    }
                    match(input,44,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().get_Keyword_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1153:2: ( ruleALPHA_NUMERIC_CHAR )
                    {
                    // InternalSemver.g:1153:2: ( ruleALPHA_NUMERIC_CHAR )
                    // InternalSemver.g:1154:3: ruleALPHA_NUMERIC_CHAR
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARParserRuleCall_0_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleALPHA_NUMERIC_CHAR();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARParserRuleCall_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Alternatives_0"


    // $ANTLR start "rule__URL__Alternatives_1"
    // InternalSemver.g:1163:1: rule__URL__Alternatives_1 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1167:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt16=4;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt16=1;
                }
                break;
            case 42:
                {
                alt16=2;
                }
                break;
            case 46:
                {
                alt16=3;
                }
                break;
            case 43:
                {
                alt16=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // InternalSemver.g:1168:2: ( '/' )
                    {
                    // InternalSemver.g:1168:2: ( '/' )
                    // InternalSemver.g:1169:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_1_0()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1174:2: ( '.' )
                    {
                    // InternalSemver.g:1174:2: ( '.' )
                    // InternalSemver.g:1175:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_1_1()); 
                    }
                    match(input,42,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1180:2: ( ':' )
                    {
                    // InternalSemver.g:1180:2: ( ':' )
                    // InternalSemver.g:1181:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_1_2()); 
                    }
                    match(input,46,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1186:2: ( '@' )
                    {
                    // InternalSemver.g:1186:2: ( '@' )
                    // InternalSemver.g:1187:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_1_3()); 
                    }
                    match(input,43,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_1_3()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Alternatives_1"


    // $ANTLR start "rule__URL__Alternatives_2"
    // InternalSemver.g:1196:1: rule__URL__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) );
    public final void rule__URL__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1200:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) )
            int alt17=6;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt17=1;
                }
                break;
            case 42:
                {
                alt17=2;
                }
                break;
            case 46:
                {
                alt17=3;
                }
                break;
            case 43:
                {
                alt17=4;
                }
                break;
            case 44:
                {
                alt17=5;
                }
                break;
            case RULE_DIGITS:
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_E:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_K:
            case RULE_LETTER_L:
            case RULE_LETTER_M:
            case RULE_LETTER_O:
            case RULE_LETTER_P:
            case RULE_LETTER_R:
            case RULE_LETTER_S:
            case RULE_LETTER_W:
            case RULE_LETTER_OTHER:
            case 47:
                {
                alt17=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalSemver.g:1201:2: ( '/' )
                    {
                    // InternalSemver.g:1201:2: ( '/' )
                    // InternalSemver.g:1202:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1207:2: ( '.' )
                    {
                    // InternalSemver.g:1207:2: ( '.' )
                    // InternalSemver.g:1208:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }
                    match(input,42,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1213:2: ( ':' )
                    {
                    // InternalSemver.g:1213:2: ( ':' )
                    // InternalSemver.g:1214:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }
                    match(input,46,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1219:2: ( '@' )
                    {
                    // InternalSemver.g:1219:2: ( '@' )
                    // InternalSemver.g:1220:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,43,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1225:2: ( '_' )
                    {
                    // InternalSemver.g:1225:2: ( '_' )
                    // InternalSemver.g:1226:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().get_Keyword_2_4()); 
                    }
                    match(input,44,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().get_Keyword_2_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1231:2: ( ruleALPHA_NUMERIC_CHAR )
                    {
                    // InternalSemver.g:1231:2: ( ruleALPHA_NUMERIC_CHAR )
                    // InternalSemver.g:1232:3: ruleALPHA_NUMERIC_CHAR
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARParserRuleCall_2_5()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleALPHA_NUMERIC_CHAR();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARParserRuleCall_2_5()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Alternatives_2"


    // $ANTLR start "rule__URL_NO_VX__Alternatives_0"
    // InternalSemver.g:1241:1: rule__URL_NO_VX__Alternatives_0 : ( ( '_' ) | ( '-' ) | ( ruleLETTER_NO_VX ) );
    public final void rule__URL_NO_VX__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1245:1: ( ( '_' ) | ( '-' ) | ( ruleLETTER_NO_VX ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case 44:
                {
                alt18=1;
                }
                break;
            case 47:
                {
                alt18=2;
                }
                break;
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_E:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_K:
            case RULE_LETTER_L:
            case RULE_LETTER_M:
            case RULE_LETTER_O:
            case RULE_LETTER_P:
            case RULE_LETTER_R:
            case RULE_LETTER_S:
            case RULE_LETTER_W:
            case RULE_LETTER_OTHER:
                {
                alt18=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalSemver.g:1246:2: ( '_' )
                    {
                    // InternalSemver.g:1246:2: ( '_' )
                    // InternalSemver.g:1247:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().get_Keyword_0_0()); 
                    }
                    match(input,44,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().get_Keyword_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1252:2: ( '-' )
                    {
                    // InternalSemver.g:1252:2: ( '-' )
                    // InternalSemver.g:1253:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_1()); 
                    }
                    match(input,47,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1258:2: ( ruleLETTER_NO_VX )
                    {
                    // InternalSemver.g:1258:2: ( ruleLETTER_NO_VX )
                    // InternalSemver.g:1259:3: ruleLETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER_NO_VX();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Alternatives_0"


    // $ANTLR start "rule__URL_NO_VX__Alternatives_1"
    // InternalSemver.g:1268:1: rule__URL_NO_VX__Alternatives_1 : ( ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) );
    public final void rule__URL_NO_VX__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1272:1: ( ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==44) ) {
                alt19=1;
            }
            else if ( ((LA19_0>=RULE_DIGITS && LA19_0<=RULE_LETTER_OTHER)||LA19_0==47) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalSemver.g:1273:2: ( '_' )
                    {
                    // InternalSemver.g:1273:2: ( '_' )
                    // InternalSemver.g:1274:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().get_Keyword_1_0()); 
                    }
                    match(input,44,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().get_Keyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1279:2: ( ruleALPHA_NUMERIC_CHAR )
                    {
                    // InternalSemver.g:1279:2: ( ruleALPHA_NUMERIC_CHAR )
                    // InternalSemver.g:1280:3: ruleALPHA_NUMERIC_CHAR
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getALPHA_NUMERIC_CHARParserRuleCall_1_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleALPHA_NUMERIC_CHAR();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getALPHA_NUMERIC_CHARParserRuleCall_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Alternatives_1"


    // $ANTLR start "rule__URL_NO_VX__Alternatives_2"
    // InternalSemver.g:1289:1: rule__URL_NO_VX__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL_NO_VX__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1293:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt20=4;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt20=1;
                }
                break;
            case 42:
                {
                alt20=2;
                }
                break;
            case 46:
                {
                alt20=3;
                }
                break;
            case 43:
                {
                alt20=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // InternalSemver.g:1294:2: ( '/' )
                    {
                    // InternalSemver.g:1294:2: ( '/' )
                    // InternalSemver.g:1295:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1300:2: ( '.' )
                    {
                    // InternalSemver.g:1300:2: ( '.' )
                    // InternalSemver.g:1301:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1()); 
                    }
                    match(input,42,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1306:2: ( ':' )
                    {
                    // InternalSemver.g:1306:2: ( ':' )
                    // InternalSemver.g:1307:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); 
                    }
                    match(input,46,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1312:2: ( '@' )
                    {
                    // InternalSemver.g:1312:2: ( '@' )
                    // InternalSemver.g:1313:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,43,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Alternatives_2"


    // $ANTLR start "rule__URL_NO_VX__Alternatives_3"
    // InternalSemver.g:1322:1: rule__URL_NO_VX__Alternatives_3 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) );
    public final void rule__URL_NO_VX__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1326:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '_' ) | ( ruleALPHA_NUMERIC_CHAR ) )
            int alt21=6;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt21=1;
                }
                break;
            case 42:
                {
                alt21=2;
                }
                break;
            case 46:
                {
                alt21=3;
                }
                break;
            case 43:
                {
                alt21=4;
                }
                break;
            case 44:
                {
                alt21=5;
                }
                break;
            case RULE_DIGITS:
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_E:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_K:
            case RULE_LETTER_L:
            case RULE_LETTER_M:
            case RULE_LETTER_O:
            case RULE_LETTER_P:
            case RULE_LETTER_R:
            case RULE_LETTER_S:
            case RULE_LETTER_W:
            case RULE_LETTER_OTHER:
            case 47:
                {
                alt21=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // InternalSemver.g:1327:2: ( '/' )
                    {
                    // InternalSemver.g:1327:2: ( '/' )
                    // InternalSemver.g:1328:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1333:2: ( '.' )
                    {
                    // InternalSemver.g:1333:2: ( '.' )
                    // InternalSemver.g:1334:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1()); 
                    }
                    match(input,42,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1339:2: ( ':' )
                    {
                    // InternalSemver.g:1339:2: ( ':' )
                    // InternalSemver.g:1340:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); 
                    }
                    match(input,46,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1345:2: ( '@' )
                    {
                    // InternalSemver.g:1345:2: ( '@' )
                    // InternalSemver.g:1346:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); 
                    }
                    match(input,43,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1351:2: ( '_' )
                    {
                    // InternalSemver.g:1351:2: ( '_' )
                    // InternalSemver.g:1352:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().get_Keyword_3_4()); 
                    }
                    match(input,44,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().get_Keyword_3_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1357:2: ( ruleALPHA_NUMERIC_CHAR )
                    {
                    // InternalSemver.g:1357:2: ( ruleALPHA_NUMERIC_CHAR )
                    // InternalSemver.g:1358:3: ruleALPHA_NUMERIC_CHAR
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getALPHA_NUMERIC_CHARParserRuleCall_3_5()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleALPHA_NUMERIC_CHAR();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getALPHA_NUMERIC_CHARParserRuleCall_3_5()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Alternatives_3"


    // $ANTLR start "rule__WORKSPACE_VERSION__Alternatives"
    // InternalSemver.g:1367:1: rule__WORKSPACE_VERSION__Alternatives : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '_' ) | ( '=' ) | ( '~' ) | ( '^' ) | ( '<' ) | ( '>' ) | ( '<=' ) | ( '>=' ) | ( RULE_ASTERIX ) | ( ruleALPHA_NUMERIC_CHAR ) );
    public final void rule__WORKSPACE_VERSION__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1371:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '_' ) | ( '=' ) | ( '~' ) | ( '^' ) | ( '<' ) | ( '>' ) | ( '<=' ) | ( '>=' ) | ( RULE_ASTERIX ) | ( ruleALPHA_NUMERIC_CHAR ) )
            int alt22=14;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt22=1;
                }
                break;
            case 42:
                {
                alt22=2;
                }
                break;
            case 46:
                {
                alt22=3;
                }
                break;
            case 43:
                {
                alt22=4;
                }
                break;
            case 44:
                {
                alt22=5;
                }
                break;
            case 48:
                {
                alt22=6;
                }
                break;
            case 49:
                {
                alt22=7;
                }
                break;
            case 50:
                {
                alt22=8;
                }
                break;
            case 51:
                {
                alt22=9;
                }
                break;
            case 52:
                {
                alt22=10;
                }
                break;
            case 53:
                {
                alt22=11;
                }
                break;
            case 54:
                {
                alt22=12;
                }
                break;
            case RULE_ASTERIX:
                {
                alt22=13;
                }
                break;
            case RULE_DIGITS:
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_E:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_K:
            case RULE_LETTER_L:
            case RULE_LETTER_M:
            case RULE_LETTER_O:
            case RULE_LETTER_P:
            case RULE_LETTER_R:
            case RULE_LETTER_S:
            case RULE_LETTER_W:
            case RULE_LETTER_OTHER:
            case 47:
                {
                alt22=14;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }

            switch (alt22) {
                case 1 :
                    // InternalSemver.g:1372:2: ( '/' )
                    {
                    // InternalSemver.g:1372:2: ( '/' )
                    // InternalSemver.g:1373:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getSolidusKeyword_0()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getSolidusKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1378:2: ( '.' )
                    {
                    // InternalSemver.g:1378:2: ( '.' )
                    // InternalSemver.g:1379:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getFullStopKeyword_1()); 
                    }
                    match(input,42,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getFullStopKeyword_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1384:2: ( ':' )
                    {
                    // InternalSemver.g:1384:2: ( ':' )
                    // InternalSemver.g:1385:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getColonKeyword_2()); 
                    }
                    match(input,46,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getColonKeyword_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1390:2: ( '@' )
                    {
                    // InternalSemver.g:1390:2: ( '@' )
                    // InternalSemver.g:1391:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getCommercialAtKeyword_3()); 
                    }
                    match(input,43,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getCommercialAtKeyword_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1396:2: ( '_' )
                    {
                    // InternalSemver.g:1396:2: ( '_' )
                    // InternalSemver.g:1397:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().get_Keyword_4()); 
                    }
                    match(input,44,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().get_Keyword_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1402:2: ( '=' )
                    {
                    // InternalSemver.g:1402:2: ( '=' )
                    // InternalSemver.g:1403:3: '='
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getEqualsSignKeyword_5()); 
                    }
                    match(input,48,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getEqualsSignKeyword_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1408:2: ( '~' )
                    {
                    // InternalSemver.g:1408:2: ( '~' )
                    // InternalSemver.g:1409:3: '~'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getTildeKeyword_6()); 
                    }
                    match(input,49,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getTildeKeyword_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSemver.g:1414:2: ( '^' )
                    {
                    // InternalSemver.g:1414:2: ( '^' )
                    // InternalSemver.g:1415:3: '^'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getCircumflexAccentKeyword_7()); 
                    }
                    match(input,50,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getCircumflexAccentKeyword_7()); 
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalSemver.g:1420:2: ( '<' )
                    {
                    // InternalSemver.g:1420:2: ( '<' )
                    // InternalSemver.g:1421:3: '<'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getLessThanSignKeyword_8()); 
                    }
                    match(input,51,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getLessThanSignKeyword_8()); 
                    }

                    }


                    }
                    break;
                case 10 :
                    // InternalSemver.g:1426:2: ( '>' )
                    {
                    // InternalSemver.g:1426:2: ( '>' )
                    // InternalSemver.g:1427:3: '>'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getGreaterThanSignKeyword_9()); 
                    }
                    match(input,52,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getGreaterThanSignKeyword_9()); 
                    }

                    }


                    }
                    break;
                case 11 :
                    // InternalSemver.g:1432:2: ( '<=' )
                    {
                    // InternalSemver.g:1432:2: ( '<=' )
                    // InternalSemver.g:1433:3: '<='
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getLessThanSignEqualsSignKeyword_10()); 
                    }
                    match(input,53,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getLessThanSignEqualsSignKeyword_10()); 
                    }

                    }


                    }
                    break;
                case 12 :
                    // InternalSemver.g:1438:2: ( '>=' )
                    {
                    // InternalSemver.g:1438:2: ( '>=' )
                    // InternalSemver.g:1439:3: '>='
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getGreaterThanSignEqualsSignKeyword_11()); 
                    }
                    match(input,54,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getGreaterThanSignEqualsSignKeyword_11()); 
                    }

                    }


                    }
                    break;
                case 13 :
                    // InternalSemver.g:1444:2: ( RULE_ASTERIX )
                    {
                    // InternalSemver.g:1444:2: ( RULE_ASTERIX )
                    // InternalSemver.g:1445:3: RULE_ASTERIX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getASTERIXTerminalRuleCall_12()); 
                    }
                    match(input,RULE_ASTERIX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getASTERIXTerminalRuleCall_12()); 
                    }

                    }


                    }
                    break;
                case 14 :
                    // InternalSemver.g:1450:2: ( ruleALPHA_NUMERIC_CHAR )
                    {
                    // InternalSemver.g:1450:2: ( ruleALPHA_NUMERIC_CHAR )
                    // InternalSemver.g:1451:3: ruleALPHA_NUMERIC_CHAR
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWORKSPACE_VERSIONAccess().getALPHA_NUMERIC_CHARParserRuleCall_13()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleALPHA_NUMERIC_CHAR();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWORKSPACE_VERSIONAccess().getALPHA_NUMERIC_CHARParserRuleCall_13()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_VERSION__Alternatives"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Alternatives"
    // InternalSemver.g:1460:1: rule__ALPHA_NUMERIC_CHAR__Alternatives : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__ALPHA_NUMERIC_CHAR__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1464:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt23=3;
            switch ( input.LA(1) ) {
            case 47:
                {
                alt23=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt23=2;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_E:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_K:
            case RULE_LETTER_L:
            case RULE_LETTER_M:
            case RULE_LETTER_O:
            case RULE_LETTER_P:
            case RULE_LETTER_R:
            case RULE_LETTER_S:
            case RULE_LETTER_W:
            case RULE_LETTER_OTHER:
                {
                alt23=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // InternalSemver.g:1465:2: ( '-' )
                    {
                    // InternalSemver.g:1465:2: ( '-' )
                    // InternalSemver.g:1466:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0()); 
                    }
                    match(input,47,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1471:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1471:2: ( RULE_DIGITS )
                    // InternalSemver.g:1472:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1477:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1477:2: ( ruleLETTER )
                    // InternalSemver.g:1478:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERParserRuleCall_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Alternatives"


    // $ANTLR start "rule__WILDCARD__Alternatives"
    // InternalSemver.g:1487:1: rule__WILDCARD__Alternatives : ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) );
    public final void rule__WILDCARD__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1491:1: ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_LETTER_X) ) {
                alt24=1;
            }
            else if ( (LA24_0==RULE_ASTERIX) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalSemver.g:1492:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1492:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1493:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWILDCARDAccess().getLETTER_XTerminalRuleCall_0()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWILDCARDAccess().getLETTER_XTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1498:2: ( RULE_ASTERIX )
                    {
                    // InternalSemver.g:1498:2: ( RULE_ASTERIX )
                    // InternalSemver.g:1499:3: RULE_ASTERIX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWILDCARDAccess().getASTERIXTerminalRuleCall_1()); 
                    }
                    match(input,RULE_ASTERIX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWILDCARDAccess().getASTERIXTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WILDCARD__Alternatives"


    // $ANTLR start "rule__LETTER__Alternatives"
    // InternalSemver.g:1508:1: rule__LETTER__Alternatives : ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) );
    public final void rule__LETTER__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1512:1: ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) )
            int alt25=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_V:
                {
                alt25=1;
                }
                break;
            case RULE_LETTER_X:
                {
                alt25=2;
                }
                break;
            case RULE_LETTER_A:
            case RULE_LETTER_C:
            case RULE_LETTER_E:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_K:
            case RULE_LETTER_L:
            case RULE_LETTER_M:
            case RULE_LETTER_O:
            case RULE_LETTER_P:
            case RULE_LETTER_R:
            case RULE_LETTER_S:
            case RULE_LETTER_W:
            case RULE_LETTER_OTHER:
                {
                alt25=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalSemver.g:1513:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1513:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1514:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTERAccess().getLETTER_VTerminalRuleCall_0()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTERAccess().getLETTER_VTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1519:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1519:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1520:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTERAccess().getLETTER_XTerminalRuleCall_1()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTERAccess().getLETTER_XTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1525:2: ( ruleLETTER_NO_VX )
                    {
                    // InternalSemver.g:1525:2: ( ruleLETTER_NO_VX )
                    // InternalSemver.g:1526:3: ruleLETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTERAccess().getLETTER_NO_VXParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER_NO_VX();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTERAccess().getLETTER_NO_VXParserRuleCall_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LETTER__Alternatives"


    // $ANTLR start "rule__LETTER_NO_VX__Alternatives"
    // InternalSemver.g:1535:1: rule__LETTER_NO_VX__Alternatives : ( ( RULE_LETTER_A ) | ( RULE_LETTER_C ) | ( RULE_LETTER_E ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_K ) | ( RULE_LETTER_L ) | ( RULE_LETTER_M ) | ( RULE_LETTER_O ) | ( RULE_LETTER_P ) | ( RULE_LETTER_R ) | ( RULE_LETTER_S ) | ( RULE_LETTER_W ) | ( RULE_LETTER_OTHER ) );
    public final void rule__LETTER_NO_VX__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1539:1: ( ( RULE_LETTER_A ) | ( RULE_LETTER_C ) | ( RULE_LETTER_E ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_K ) | ( RULE_LETTER_L ) | ( RULE_LETTER_M ) | ( RULE_LETTER_O ) | ( RULE_LETTER_P ) | ( RULE_LETTER_R ) | ( RULE_LETTER_S ) | ( RULE_LETTER_W ) | ( RULE_LETTER_OTHER ) )
            int alt26=14;
            switch ( input.LA(1) ) {
            case RULE_LETTER_A:
                {
                alt26=1;
                }
                break;
            case RULE_LETTER_C:
                {
                alt26=2;
                }
                break;
            case RULE_LETTER_E:
                {
                alt26=3;
                }
                break;
            case RULE_LETTER_F:
                {
                alt26=4;
                }
                break;
            case RULE_LETTER_I:
                {
                alt26=5;
                }
                break;
            case RULE_LETTER_K:
                {
                alt26=6;
                }
                break;
            case RULE_LETTER_L:
                {
                alt26=7;
                }
                break;
            case RULE_LETTER_M:
                {
                alt26=8;
                }
                break;
            case RULE_LETTER_O:
                {
                alt26=9;
                }
                break;
            case RULE_LETTER_P:
                {
                alt26=10;
                }
                break;
            case RULE_LETTER_R:
                {
                alt26=11;
                }
                break;
            case RULE_LETTER_S:
                {
                alt26=12;
                }
                break;
            case RULE_LETTER_W:
                {
                alt26=13;
                }
                break;
            case RULE_LETTER_OTHER:
                {
                alt26=14;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // InternalSemver.g:1540:2: ( RULE_LETTER_A )
                    {
                    // InternalSemver.g:1540:2: ( RULE_LETTER_A )
                    // InternalSemver.g:1541:3: RULE_LETTER_A
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ATerminalRuleCall_0()); 
                    }
                    match(input,RULE_LETTER_A,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ATerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1546:2: ( RULE_LETTER_C )
                    {
                    // InternalSemver.g:1546:2: ( RULE_LETTER_C )
                    // InternalSemver.g:1547:3: RULE_LETTER_C
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_CTerminalRuleCall_1()); 
                    }
                    match(input,RULE_LETTER_C,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_CTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1552:2: ( RULE_LETTER_E )
                    {
                    // InternalSemver.g:1552:2: ( RULE_LETTER_E )
                    // InternalSemver.g:1553:3: RULE_LETTER_E
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_2()); 
                    }
                    match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1558:2: ( RULE_LETTER_F )
                    {
                    // InternalSemver.g:1558:2: ( RULE_LETTER_F )
                    // InternalSemver.g:1559:3: RULE_LETTER_F
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_FTerminalRuleCall_3()); 
                    }
                    match(input,RULE_LETTER_F,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_FTerminalRuleCall_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1564:2: ( RULE_LETTER_I )
                    {
                    // InternalSemver.g:1564:2: ( RULE_LETTER_I )
                    // InternalSemver.g:1565:3: RULE_LETTER_I
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ITerminalRuleCall_4()); 
                    }
                    match(input,RULE_LETTER_I,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ITerminalRuleCall_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1570:2: ( RULE_LETTER_K )
                    {
                    // InternalSemver.g:1570:2: ( RULE_LETTER_K )
                    // InternalSemver.g:1571:3: RULE_LETTER_K
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_KTerminalRuleCall_5()); 
                    }
                    match(input,RULE_LETTER_K,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_KTerminalRuleCall_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1576:2: ( RULE_LETTER_L )
                    {
                    // InternalSemver.g:1576:2: ( RULE_LETTER_L )
                    // InternalSemver.g:1577:3: RULE_LETTER_L
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_6()); 
                    }
                    match(input,RULE_LETTER_L,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSemver.g:1582:2: ( RULE_LETTER_M )
                    {
                    // InternalSemver.g:1582:2: ( RULE_LETTER_M )
                    // InternalSemver.g:1583:3: RULE_LETTER_M
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_7()); 
                    }
                    match(input,RULE_LETTER_M,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_7()); 
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalSemver.g:1588:2: ( RULE_LETTER_O )
                    {
                    // InternalSemver.g:1588:2: ( RULE_LETTER_O )
                    // InternalSemver.g:1589:3: RULE_LETTER_O
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTerminalRuleCall_8()); 
                    }
                    match(input,RULE_LETTER_O,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTerminalRuleCall_8()); 
                    }

                    }


                    }
                    break;
                case 10 :
                    // InternalSemver.g:1594:2: ( RULE_LETTER_P )
                    {
                    // InternalSemver.g:1594:2: ( RULE_LETTER_P )
                    // InternalSemver.g:1595:3: RULE_LETTER_P
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_PTerminalRuleCall_9()); 
                    }
                    match(input,RULE_LETTER_P,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_PTerminalRuleCall_9()); 
                    }

                    }


                    }
                    break;
                case 11 :
                    // InternalSemver.g:1600:2: ( RULE_LETTER_R )
                    {
                    // InternalSemver.g:1600:2: ( RULE_LETTER_R )
                    // InternalSemver.g:1601:3: RULE_LETTER_R
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_10()); 
                    }
                    match(input,RULE_LETTER_R,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_10()); 
                    }

                    }


                    }
                    break;
                case 12 :
                    // InternalSemver.g:1606:2: ( RULE_LETTER_S )
                    {
                    // InternalSemver.g:1606:2: ( RULE_LETTER_S )
                    // InternalSemver.g:1607:3: RULE_LETTER_S
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_11()); 
                    }
                    match(input,RULE_LETTER_S,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_11()); 
                    }

                    }


                    }
                    break;
                case 13 :
                    // InternalSemver.g:1612:2: ( RULE_LETTER_W )
                    {
                    // InternalSemver.g:1612:2: ( RULE_LETTER_W )
                    // InternalSemver.g:1613:3: RULE_LETTER_W
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_WTerminalRuleCall_12()); 
                    }
                    match(input,RULE_LETTER_W,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_WTerminalRuleCall_12()); 
                    }

                    }


                    }
                    break;
                case 14 :
                    // InternalSemver.g:1618:2: ( RULE_LETTER_OTHER )
                    {
                    // InternalSemver.g:1618:2: ( RULE_LETTER_OTHER )
                    // InternalSemver.g:1619:3: RULE_LETTER_OTHER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_13()); 
                    }
                    match(input,RULE_LETTER_OTHER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_13()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LETTER_NO_VX__Alternatives"


    // $ANTLR start "rule__VersionComparator__Alternatives"
    // InternalSemver.g:1628:1: rule__VersionComparator__Alternatives : ( ( ( '=' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<' ) ) | ( ( '>' ) ) | ( ( '<=' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1632:1: ( ( ( '=' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<' ) ) | ( ( '>' ) ) | ( ( '<=' ) ) | ( ( '>=' ) ) )
            int alt27=7;
            switch ( input.LA(1) ) {
            case 48:
                {
                alt27=1;
                }
                break;
            case 49:
                {
                alt27=2;
                }
                break;
            case 50:
                {
                alt27=3;
                }
                break;
            case 51:
                {
                alt27=4;
                }
                break;
            case 52:
                {
                alt27=5;
                }
                break;
            case 53:
                {
                alt27=6;
                }
                break;
            case 54:
                {
                alt27=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // InternalSemver.g:1633:2: ( ( '=' ) )
                    {
                    // InternalSemver.g:1633:2: ( ( '=' ) )
                    // InternalSemver.g:1634:3: ( '=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }
                    // InternalSemver.g:1635:3: ( '=' )
                    // InternalSemver.g:1635:4: '='
                    {
                    match(input,48,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1639:2: ( ( '~' ) )
                    {
                    // InternalSemver.g:1639:2: ( ( '~' ) )
                    // InternalSemver.g:1640:3: ( '~' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_1()); 
                    }
                    // InternalSemver.g:1641:3: ( '~' )
                    // InternalSemver.g:1641:4: '~'
                    {
                    match(input,49,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1645:2: ( ( '^' ) )
                    {
                    // InternalSemver.g:1645:2: ( ( '^' ) )
                    // InternalSemver.g:1646:3: ( '^' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_2()); 
                    }
                    // InternalSemver.g:1647:3: ( '^' )
                    // InternalSemver.g:1647:4: '^'
                    {
                    match(input,50,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1651:2: ( ( '<' ) )
                    {
                    // InternalSemver.g:1651:2: ( ( '<' ) )
                    // InternalSemver.g:1652:3: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_3()); 
                    }
                    // InternalSemver.g:1653:3: ( '<' )
                    // InternalSemver.g:1653:4: '<'
                    {
                    match(input,51,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1657:2: ( ( '>' ) )
                    {
                    // InternalSemver.g:1657:2: ( ( '>' ) )
                    // InternalSemver.g:1658:3: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_4()); 
                    }
                    // InternalSemver.g:1659:3: ( '>' )
                    // InternalSemver.g:1659:4: '>'
                    {
                    match(input,52,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1663:2: ( ( '<=' ) )
                    {
                    // InternalSemver.g:1663:2: ( ( '<=' ) )
                    // InternalSemver.g:1664:3: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 
                    }
                    // InternalSemver.g:1665:3: ( '<=' )
                    // InternalSemver.g:1665:4: '<='
                    {
                    match(input,53,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1669:2: ( ( '>=' ) )
                    {
                    // InternalSemver.g:1669:2: ( ( '>=' ) )
                    // InternalSemver.g:1670:3: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); 
                    }
                    // InternalSemver.g:1671:3: ( '>=' )
                    // InternalSemver.g:1671:4: '>='
                    {
                    match(input,54,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionComparator__Alternatives"


    // $ANTLR start "rule__NPMVersionRequirement__Group_0__0"
    // InternalSemver.g:1679:1: rule__NPMVersionRequirement__Group_0__0 : rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 ;
    public final void rule__NPMVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1683:1: ( rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 )
            // InternalSemver.g:1684:2: rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1
            {
            pushFollow(FOLLOW_5);
            rule__NPMVersionRequirement__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__NPMVersionRequirement__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_0__0"


    // $ANTLR start "rule__NPMVersionRequirement__Group_0__0__Impl"
    // InternalSemver.g:1691:1: rule__NPMVersionRequirement__Group_0__0__Impl : ( ( RULE_WS )? ) ;
    public final void rule__NPMVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1695:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:1696:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:1696:1: ( ( RULE_WS )? )
            // InternalSemver.g:1697:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }
            // InternalSemver.g:1698:2: ( RULE_WS )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_WS) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalSemver.g:1698:3: RULE_WS
                    {
                    match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_0__0__Impl"


    // $ANTLR start "rule__NPMVersionRequirement__Group_0__1"
    // InternalSemver.g:1706:1: rule__NPMVersionRequirement__Group_0__1 : rule__NPMVersionRequirement__Group_0__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1710:1: ( rule__NPMVersionRequirement__Group_0__1__Impl )
            // InternalSemver.g:1711:2: rule__NPMVersionRequirement__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__NPMVersionRequirement__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_0__1"


    // $ANTLR start "rule__NPMVersionRequirement__Group_0__1__Impl"
    // InternalSemver.g:1717:1: rule__NPMVersionRequirement__Group_0__1__Impl : ( ruleVersionRangeSetRequirement ) ;
    public final void rule__NPMVersionRequirement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1721:1: ( ( ruleVersionRangeSetRequirement ) )
            // InternalSemver.g:1722:1: ( ruleVersionRangeSetRequirement )
            {
            // InternalSemver.g:1722:1: ( ruleVersionRangeSetRequirement )
            // InternalSemver.g:1723:2: ruleVersionRangeSetRequirement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getVersionRangeSetRequirementParserRuleCall_0_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionRangeSetRequirement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNPMVersionRequirementAccess().getVersionRangeSetRequirementParserRuleCall_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_0__1__Impl"


    // $ANTLR start "rule__NPMVersionRequirement__Group_1__0"
    // InternalSemver.g:1733:1: rule__NPMVersionRequirement__Group_1__0 : rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 ;
    public final void rule__NPMVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1737:1: ( rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 )
            // InternalSemver.g:1738:2: rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1
            {
            pushFollow(FOLLOW_6);
            rule__NPMVersionRequirement__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__NPMVersionRequirement__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_1__0"


    // $ANTLR start "rule__NPMVersionRequirement__Group_1__0__Impl"
    // InternalSemver.g:1745:1: rule__NPMVersionRequirement__Group_1__0__Impl : ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) ;
    public final void rule__NPMVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1749:1: ( ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) )
            // InternalSemver.g:1750:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            {
            // InternalSemver.g:1750:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            // InternalSemver.g:1751:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:1752:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            // InternalSemver.g:1752:3: rule__NPMVersionRequirement__Alternatives_1_0
            {
            pushFollow(FOLLOW_2);
            rule__NPMVersionRequirement__Alternatives_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_1__0__Impl"


    // $ANTLR start "rule__NPMVersionRequirement__Group_1__1"
    // InternalSemver.g:1760:1: rule__NPMVersionRequirement__Group_1__1 : rule__NPMVersionRequirement__Group_1__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1764:1: ( rule__NPMVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1765:2: rule__NPMVersionRequirement__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__NPMVersionRequirement__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_1__1"


    // $ANTLR start "rule__NPMVersionRequirement__Group_1__1__Impl"
    // InternalSemver.g:1771:1: rule__NPMVersionRequirement__Group_1__1__Impl : ( ( RULE_WS )? ) ;
    public final void rule__NPMVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1775:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:1776:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:1776:1: ( ( RULE_WS )? )
            // InternalSemver.g:1777:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:1778:2: ( RULE_WS )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_WS) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalSemver.g:1778:3: RULE_WS
                    {
                    match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NPMVersionRequirement__Group_1__1__Impl"


    // $ANTLR start "rule__LocalPathVersionRequirement__Group__0"
    // InternalSemver.g:1787:1: rule__LocalPathVersionRequirement__Group__0 : rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 ;
    public final void rule__LocalPathVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1791:1: ( rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 )
            // InternalSemver.g:1792:2: rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_7);
            rule__LocalPathVersionRequirement__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LocalPathVersionRequirement__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalPathVersionRequirement__Group__0"


    // $ANTLR start "rule__LocalPathVersionRequirement__Group__0__Impl"
    // InternalSemver.g:1799:1: rule__LocalPathVersionRequirement__Group__0__Impl : ( ruleFILE_TAG ) ;
    public final void rule__LocalPathVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1803:1: ( ( ruleFILE_TAG ) )
            // InternalSemver.g:1804:1: ( ruleFILE_TAG )
            {
            // InternalSemver.g:1804:1: ( ruleFILE_TAG )
            // InternalSemver.g:1805:2: ruleFILE_TAG
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleFILE_TAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalPathVersionRequirement__Group__0__Impl"


    // $ANTLR start "rule__LocalPathVersionRequirement__Group__1"
    // InternalSemver.g:1814:1: rule__LocalPathVersionRequirement__Group__1 : rule__LocalPathVersionRequirement__Group__1__Impl ;
    public final void rule__LocalPathVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1818:1: ( rule__LocalPathVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1819:2: rule__LocalPathVersionRequirement__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LocalPathVersionRequirement__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalPathVersionRequirement__Group__1"


    // $ANTLR start "rule__LocalPathVersionRequirement__Group__1__Impl"
    // InternalSemver.g:1825:1: rule__LocalPathVersionRequirement__Group__1__Impl : ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1829:1: ( ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) )
            // InternalSemver.g:1830:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            {
            // InternalSemver.g:1830:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            // InternalSemver.g:1831:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }
            // InternalSemver.g:1832:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            // InternalSemver.g:1832:3: rule__LocalPathVersionRequirement__LocalPathAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__LocalPathVersionRequirement__LocalPathAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalPathVersionRequirement__Group__1__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group__0"
    // InternalSemver.g:1841:1: rule__URLVersionRequirement__Group__0 : rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 ;
    public final void rule__URLVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1845:1: ( rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 )
            // InternalSemver.g:1846:2: rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__URLVersionRequirement__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__0"


    // $ANTLR start "rule__URLVersionRequirement__Group__0__Impl"
    // InternalSemver.g:1853:1: rule__URLVersionRequirement__Group__0__Impl : ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) ;
    public final void rule__URLVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1857:1: ( ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) )
            // InternalSemver.g:1858:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            {
            // InternalSemver.g:1858:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            // InternalSemver.g:1859:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }
            // InternalSemver.g:1860:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            // InternalSemver.g:1860:3: rule__URLVersionRequirement__ProtocolAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__ProtocolAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__0__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group__1"
    // InternalSemver.g:1868:1: rule__URLVersionRequirement__Group__1 : rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 ;
    public final void rule__URLVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1872:1: ( rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 )
            // InternalSemver.g:1873:2: rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2
            {
            pushFollow(FOLLOW_9);
            rule__URLVersionRequirement__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__1"


    // $ANTLR start "rule__URLVersionRequirement__Group__1__Impl"
    // InternalSemver.g:1880:1: rule__URLVersionRequirement__Group__1__Impl : ( ( rule__URLVersionRequirement__Group_1__0 ) ) ;
    public final void rule__URLVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1884:1: ( ( ( rule__URLVersionRequirement__Group_1__0 ) ) )
            // InternalSemver.g:1885:1: ( ( rule__URLVersionRequirement__Group_1__0 ) )
            {
            // InternalSemver.g:1885:1: ( ( rule__URLVersionRequirement__Group_1__0 ) )
            // InternalSemver.g:1886:2: ( rule__URLVersionRequirement__Group_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1887:2: ( rule__URLVersionRequirement__Group_1__0 )
            // InternalSemver.g:1887:3: rule__URLVersionRequirement__Group_1__0
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group_1__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__1__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group__2"
    // InternalSemver.g:1895:1: rule__URLVersionRequirement__Group__2 : rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 ;
    public final void rule__URLVersionRequirement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1899:1: ( rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 )
            // InternalSemver.g:1900:2: rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3
            {
            pushFollow(FOLLOW_10);
            rule__URLVersionRequirement__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__2"


    // $ANTLR start "rule__URLVersionRequirement__Group__2__Impl"
    // InternalSemver.g:1907:1: rule__URLVersionRequirement__Group__2__Impl : ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) ;
    public final void rule__URLVersionRequirement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1911:1: ( ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) )
            // InternalSemver.g:1912:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            {
            // InternalSemver.g:1912:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            // InternalSemver.g:1913:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }
            // InternalSemver.g:1914:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            // InternalSemver.g:1914:3: rule__URLVersionRequirement__UrlAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__UrlAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__2__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group__3"
    // InternalSemver.g:1922:1: rule__URLVersionRequirement__Group__3 : rule__URLVersionRequirement__Group__3__Impl ;
    public final void rule__URLVersionRequirement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1926:1: ( rule__URLVersionRequirement__Group__3__Impl )
            // InternalSemver.g:1927:2: rule__URLVersionRequirement__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__3"


    // $ANTLR start "rule__URLVersionRequirement__Group__3__Impl"
    // InternalSemver.g:1933:1: rule__URLVersionRequirement__Group__3__Impl : ( ( rule__URLVersionRequirement__Group_3__0 )? ) ;
    public final void rule__URLVersionRequirement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1937:1: ( ( ( rule__URLVersionRequirement__Group_3__0 )? ) )
            // InternalSemver.g:1938:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            {
            // InternalSemver.g:1938:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            // InternalSemver.g:1939:2: ( rule__URLVersionRequirement__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }
            // InternalSemver.g:1940:2: ( rule__URLVersionRequirement__Group_3__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==55) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalSemver.g:1940:3: rule__URLVersionRequirement__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__URLVersionRequirement__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group__3__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group_1__0"
    // InternalSemver.g:1949:1: rule__URLVersionRequirement__Group_1__0 : rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1 ;
    public final void rule__URLVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1953:1: ( rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1 )
            // InternalSemver.g:1954:2: rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1
            {
            pushFollow(FOLLOW_11);
            rule__URLVersionRequirement__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_1__0"


    // $ANTLR start "rule__URLVersionRequirement__Group_1__0__Impl"
    // InternalSemver.g:1961:1: rule__URLVersionRequirement__Group_1__0__Impl : ( ':' ) ;
    public final void rule__URLVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1965:1: ( ( ':' ) )
            // InternalSemver.g:1966:1: ( ':' )
            {
            // InternalSemver.g:1966:1: ( ':' )
            // InternalSemver.g:1967:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0()); 
            }
            match(input,46,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_1__0__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group_1__1"
    // InternalSemver.g:1976:1: rule__URLVersionRequirement__Group_1__1 : rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2 ;
    public final void rule__URLVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1980:1: ( rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2 )
            // InternalSemver.g:1981:2: rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2
            {
            pushFollow(FOLLOW_11);
            rule__URLVersionRequirement__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_1__1"


    // $ANTLR start "rule__URLVersionRequirement__Group_1__1__Impl"
    // InternalSemver.g:1988:1: rule__URLVersionRequirement__Group_1__1__Impl : ( '/' ) ;
    public final void rule__URLVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1992:1: ( ( '/' ) )
            // InternalSemver.g:1993:1: ( '/' )
            {
            // InternalSemver.g:1993:1: ( '/' )
            // InternalSemver.g:1994:2: '/'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1()); 
            }
            match(input,41,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_1__1__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group_1__2"
    // InternalSemver.g:2003:1: rule__URLVersionRequirement__Group_1__2 : rule__URLVersionRequirement__Group_1__2__Impl ;
    public final void rule__URLVersionRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2007:1: ( rule__URLVersionRequirement__Group_1__2__Impl )
            // InternalSemver.g:2008:2: rule__URLVersionRequirement__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_1__2"


    // $ANTLR start "rule__URLVersionRequirement__Group_1__2__Impl"
    // InternalSemver.g:2014:1: rule__URLVersionRequirement__Group_1__2__Impl : ( '/' ) ;
    public final void rule__URLVersionRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2018:1: ( ( '/' ) )
            // InternalSemver.g:2019:1: ( '/' )
            {
            // InternalSemver.g:2019:1: ( '/' )
            // InternalSemver.g:2020:2: '/'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2()); 
            }
            match(input,41,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_1__2__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group_3__0"
    // InternalSemver.g:2030:1: rule__URLVersionRequirement__Group_3__0 : rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 ;
    public final void rule__URLVersionRequirement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2034:1: ( rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 )
            // InternalSemver.g:2035:2: rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1
            {
            pushFollow(FOLLOW_12);
            rule__URLVersionRequirement__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_3__0"


    // $ANTLR start "rule__URLVersionRequirement__Group_3__0__Impl"
    // InternalSemver.g:2042:1: rule__URLVersionRequirement__Group_3__0__Impl : ( '#' ) ;
    public final void rule__URLVersionRequirement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2046:1: ( ( '#' ) )
            // InternalSemver.g:2047:1: ( '#' )
            {
            // InternalSemver.g:2047:1: ( '#' )
            // InternalSemver.g:2048:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); 
            }
            match(input,55,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_3__0__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group_3__1"
    // InternalSemver.g:2057:1: rule__URLVersionRequirement__Group_3__1 : rule__URLVersionRequirement__Group_3__1__Impl ;
    public final void rule__URLVersionRequirement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2061:1: ( rule__URLVersionRequirement__Group_3__1__Impl )
            // InternalSemver.g:2062:2: rule__URLVersionRequirement__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_3__1"


    // $ANTLR start "rule__URLVersionRequirement__Group_3__1__Impl"
    // InternalSemver.g:2068:1: rule__URLVersionRequirement__Group_3__1__Impl : ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) ;
    public final void rule__URLVersionRequirement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2072:1: ( ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) )
            // InternalSemver.g:2073:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            {
            // InternalSemver.g:2073:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            // InternalSemver.g:2074:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }
            // InternalSemver.g:2075:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            // InternalSemver.g:2075:3: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionRequirement__VersionSpecifierAssignment_3_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__Group_3__1__Impl"


    // $ANTLR start "rule__URLVersionSpecifier__Group_0__0"
    // InternalSemver.g:2084:1: rule__URLVersionSpecifier__Group_0__0 : rule__URLVersionSpecifier__Group_0__0__Impl ;
    public final void rule__URLVersionSpecifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2088:1: ( rule__URLVersionSpecifier__Group_0__0__Impl )
            // InternalSemver.g:2089:2: rule__URLVersionSpecifier__Group_0__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_0__0"


    // $ANTLR start "rule__URLVersionSpecifier__Group_0__0__Impl"
    // InternalSemver.g:2095:1: rule__URLVersionSpecifier__Group_0__0__Impl : ( ruleURLSemver ) ;
    public final void rule__URLVersionSpecifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2099:1: ( ( ruleURLSemver ) )
            // InternalSemver.g:2100:1: ( ruleURLSemver )
            {
            // InternalSemver.g:2100:1: ( ruleURLSemver )
            // InternalSemver.g:2101:2: ruleURLSemver
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleURLSemver();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_0__0__Impl"


    // $ANTLR start "rule__URLVersionSpecifier__Group_1__0"
    // InternalSemver.g:2111:1: rule__URLVersionSpecifier__Group_1__0 : rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 ;
    public final void rule__URLVersionSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2115:1: ( rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 )
            // InternalSemver.g:2116:2: rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1
            {
            pushFollow(FOLLOW_13);
            rule__URLVersionSpecifier__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_1__0"


    // $ANTLR start "rule__URLVersionSpecifier__Group_1__0__Impl"
    // InternalSemver.g:2123:1: rule__URLVersionSpecifier__Group_1__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2127:1: ( ( () ) )
            // InternalSemver.g:2128:1: ( () )
            {
            // InternalSemver.g:2128:1: ( () )
            // InternalSemver.g:2129:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); 
            }
            // InternalSemver.g:2130:2: ()
            // InternalSemver.g:2130:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_1__0__Impl"


    // $ANTLR start "rule__URLVersionSpecifier__Group_1__1"
    // InternalSemver.g:2138:1: rule__URLVersionSpecifier__Group_1__1 : rule__URLVersionSpecifier__Group_1__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2142:1: ( rule__URLVersionSpecifier__Group_1__1__Impl )
            // InternalSemver.g:2143:2: rule__URLVersionSpecifier__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_1__1"


    // $ANTLR start "rule__URLVersionSpecifier__Group_1__1__Impl"
    // InternalSemver.g:2149:1: rule__URLVersionSpecifier__Group_1__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2153:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:2154:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:2154:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:2155:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:2156:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            // InternalSemver.g:2156:3: rule__URLVersionSpecifier__CommitISHAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__CommitISHAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_1__1__Impl"


    // $ANTLR start "rule__URLVersionSpecifier__Group_2__0"
    // InternalSemver.g:2165:1: rule__URLVersionSpecifier__Group_2__0 : rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 ;
    public final void rule__URLVersionSpecifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2169:1: ( rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 )
            // InternalSemver.g:2170:2: rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1
            {
            pushFollow(FOLLOW_12);
            rule__URLVersionSpecifier__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_2__0"


    // $ANTLR start "rule__URLVersionSpecifier__Group_2__0__Impl"
    // InternalSemver.g:2177:1: rule__URLVersionSpecifier__Group_2__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2181:1: ( ( () ) )
            // InternalSemver.g:2182:1: ( () )
            {
            // InternalSemver.g:2182:1: ( () )
            // InternalSemver.g:2183:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); 
            }
            // InternalSemver.g:2184:2: ()
            // InternalSemver.g:2184:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_2__0__Impl"


    // $ANTLR start "rule__URLVersionSpecifier__Group_2__1"
    // InternalSemver.g:2192:1: rule__URLVersionSpecifier__Group_2__1 : rule__URLVersionSpecifier__Group_2__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2196:1: ( rule__URLVersionSpecifier__Group_2__1__Impl )
            // InternalSemver.g:2197:2: rule__URLVersionSpecifier__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_2__1"


    // $ANTLR start "rule__URLVersionSpecifier__Group_2__1__Impl"
    // InternalSemver.g:2203:1: rule__URLVersionSpecifier__Group_2__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2207:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) )
            // InternalSemver.g:2208:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            {
            // InternalSemver.g:2208:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            // InternalSemver.g:2209:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); 
            }
            // InternalSemver.g:2210:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            // InternalSemver.g:2210:3: rule__URLVersionSpecifier__CommitISHAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__URLVersionSpecifier__CommitISHAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__Group_2__1__Impl"


    // $ANTLR start "rule__URLSemver__Group__0"
    // InternalSemver.g:2219:1: rule__URLSemver__Group__0 : rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 ;
    public final void rule__URLSemver__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2223:1: ( rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 )
            // InternalSemver.g:2224:2: rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1
            {
            pushFollow(FOLLOW_14);
            rule__URLSemver__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLSemver__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__Group__0"


    // $ANTLR start "rule__URLSemver__Group__0__Impl"
    // InternalSemver.g:2231:1: rule__URLSemver__Group__0__Impl : ( () ) ;
    public final void rule__URLSemver__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2235:1: ( ( () ) )
            // InternalSemver.g:2236:1: ( () )
            {
            // InternalSemver.g:2236:1: ( () )
            // InternalSemver.g:2237:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); 
            }
            // InternalSemver.g:2238:2: ()
            // InternalSemver.g:2238:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__Group__0__Impl"


    // $ANTLR start "rule__URLSemver__Group__1"
    // InternalSemver.g:2246:1: rule__URLSemver__Group__1 : rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 ;
    public final void rule__URLSemver__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2250:1: ( rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 )
            // InternalSemver.g:2251:2: rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2
            {
            pushFollow(FOLLOW_14);
            rule__URLSemver__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URLSemver__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__Group__1"


    // $ANTLR start "rule__URLSemver__Group__1__Impl"
    // InternalSemver.g:2258:1: rule__URLSemver__Group__1__Impl : ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) ;
    public final void rule__URLSemver__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2262:1: ( ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) )
            // InternalSemver.g:2263:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            {
            // InternalSemver.g:2263:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            // InternalSemver.g:2264:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); 
            }
            // InternalSemver.g:2265:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_LETTER_S) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalSemver.g:2265:3: rule__URLSemver__WithSemverTagAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__URLSemver__WithSemverTagAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__Group__1__Impl"


    // $ANTLR start "rule__URLSemver__Group__2"
    // InternalSemver.g:2273:1: rule__URLSemver__Group__2 : rule__URLSemver__Group__2__Impl ;
    public final void rule__URLSemver__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2277:1: ( rule__URLSemver__Group__2__Impl )
            // InternalSemver.g:2278:2: rule__URLSemver__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLSemver__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__Group__2"


    // $ANTLR start "rule__URLSemver__Group__2__Impl"
    // InternalSemver.g:2284:1: rule__URLSemver__Group__2__Impl : ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) ;
    public final void rule__URLSemver__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2288:1: ( ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) )
            // InternalSemver.g:2289:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            {
            // InternalSemver.g:2289:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            // InternalSemver.g:2290:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); 
            }
            // InternalSemver.g:2291:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            // InternalSemver.g:2291:3: rule__URLSemver__SimpleVersionAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__URLSemver__SimpleVersionAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__Group__2__Impl"


    // $ANTLR start "rule__WorkspaceVersionRequirement__Group__0"
    // InternalSemver.g:2300:1: rule__WorkspaceVersionRequirement__Group__0 : rule__WorkspaceVersionRequirement__Group__0__Impl rule__WorkspaceVersionRequirement__Group__1 ;
    public final void rule__WorkspaceVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2304:1: ( rule__WorkspaceVersionRequirement__Group__0__Impl rule__WorkspaceVersionRequirement__Group__1 )
            // InternalSemver.g:2305:2: rule__WorkspaceVersionRequirement__Group__0__Impl rule__WorkspaceVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_15);
            rule__WorkspaceVersionRequirement__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WorkspaceVersionRequirement__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WorkspaceVersionRequirement__Group__0"


    // $ANTLR start "rule__WorkspaceVersionRequirement__Group__0__Impl"
    // InternalSemver.g:2312:1: rule__WorkspaceVersionRequirement__Group__0__Impl : ( ruleWORKSPACE_TAG ) ;
    public final void rule__WorkspaceVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2316:1: ( ( ruleWORKSPACE_TAG ) )
            // InternalSemver.g:2317:1: ( ruleWORKSPACE_TAG )
            {
            // InternalSemver.g:2317:1: ( ruleWORKSPACE_TAG )
            // InternalSemver.g:2318:2: ruleWORKSPACE_TAG
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWorkspaceVersionRequirementAccess().getWORKSPACE_TAGParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleWORKSPACE_TAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWorkspaceVersionRequirementAccess().getWORKSPACE_TAGParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WorkspaceVersionRequirement__Group__0__Impl"


    // $ANTLR start "rule__WorkspaceVersionRequirement__Group__1"
    // InternalSemver.g:2327:1: rule__WorkspaceVersionRequirement__Group__1 : rule__WorkspaceVersionRequirement__Group__1__Impl ;
    public final void rule__WorkspaceVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2331:1: ( rule__WorkspaceVersionRequirement__Group__1__Impl )
            // InternalSemver.g:2332:2: rule__WorkspaceVersionRequirement__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__WorkspaceVersionRequirement__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WorkspaceVersionRequirement__Group__1"


    // $ANTLR start "rule__WorkspaceVersionRequirement__Group__1__Impl"
    // InternalSemver.g:2338:1: rule__WorkspaceVersionRequirement__Group__1__Impl : ( ( rule__WorkspaceVersionRequirement__Alternatives_1 ) ) ;
    public final void rule__WorkspaceVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2342:1: ( ( ( rule__WorkspaceVersionRequirement__Alternatives_1 ) ) )
            // InternalSemver.g:2343:1: ( ( rule__WorkspaceVersionRequirement__Alternatives_1 ) )
            {
            // InternalSemver.g:2343:1: ( ( rule__WorkspaceVersionRequirement__Alternatives_1 ) )
            // InternalSemver.g:2344:2: ( rule__WorkspaceVersionRequirement__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWorkspaceVersionRequirementAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:2345:2: ( rule__WorkspaceVersionRequirement__Alternatives_1 )
            // InternalSemver.g:2345:3: rule__WorkspaceVersionRequirement__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__WorkspaceVersionRequirement__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWorkspaceVersionRequirementAccess().getAlternatives_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WorkspaceVersionRequirement__Group__1__Impl"


    // $ANTLR start "rule__GitHubVersionRequirement__Group__0"
    // InternalSemver.g:2354:1: rule__GitHubVersionRequirement__Group__0 : rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 ;
    public final void rule__GitHubVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2358:1: ( rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 )
            // InternalSemver.g:2359:2: rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__GitHubVersionRequirement__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__GitHubVersionRequirement__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group__0"


    // $ANTLR start "rule__GitHubVersionRequirement__Group__0__Impl"
    // InternalSemver.g:2366:1: rule__GitHubVersionRequirement__Group__0__Impl : ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) ;
    public final void rule__GitHubVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2370:1: ( ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) )
            // InternalSemver.g:2371:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            {
            // InternalSemver.g:2371:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            // InternalSemver.g:2372:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }
            // InternalSemver.g:2373:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            // InternalSemver.g:2373:3: rule__GitHubVersionRequirement__GithubUrlAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__GitHubVersionRequirement__GithubUrlAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group__0__Impl"


    // $ANTLR start "rule__GitHubVersionRequirement__Group__1"
    // InternalSemver.g:2381:1: rule__GitHubVersionRequirement__Group__1 : rule__GitHubVersionRequirement__Group__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2385:1: ( rule__GitHubVersionRequirement__Group__1__Impl )
            // InternalSemver.g:2386:2: rule__GitHubVersionRequirement__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GitHubVersionRequirement__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group__1"


    // $ANTLR start "rule__GitHubVersionRequirement__Group__1__Impl"
    // InternalSemver.g:2392:1: rule__GitHubVersionRequirement__Group__1__Impl : ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) ;
    public final void rule__GitHubVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2396:1: ( ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) )
            // InternalSemver.g:2397:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:2397:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            // InternalSemver.g:2398:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:2399:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==55) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalSemver.g:2399:3: rule__GitHubVersionRequirement__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__GitHubVersionRequirement__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group__1__Impl"


    // $ANTLR start "rule__GitHubVersionRequirement__Group_1__0"
    // InternalSemver.g:2408:1: rule__GitHubVersionRequirement__Group_1__0 : rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 ;
    public final void rule__GitHubVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2412:1: ( rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 )
            // InternalSemver.g:2413:2: rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__GitHubVersionRequirement__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__GitHubVersionRequirement__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group_1__0"


    // $ANTLR start "rule__GitHubVersionRequirement__Group_1__0__Impl"
    // InternalSemver.g:2420:1: rule__GitHubVersionRequirement__Group_1__0__Impl : ( '#' ) ;
    public final void rule__GitHubVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2424:1: ( ( '#' ) )
            // InternalSemver.g:2425:1: ( '#' )
            {
            // InternalSemver.g:2425:1: ( '#' )
            // InternalSemver.g:2426:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); 
            }
            match(input,55,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group_1__0__Impl"


    // $ANTLR start "rule__GitHubVersionRequirement__Group_1__1"
    // InternalSemver.g:2435:1: rule__GitHubVersionRequirement__Group_1__1 : rule__GitHubVersionRequirement__Group_1__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2439:1: ( rule__GitHubVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:2440:2: rule__GitHubVersionRequirement__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GitHubVersionRequirement__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group_1__1"


    // $ANTLR start "rule__GitHubVersionRequirement__Group_1__1__Impl"
    // InternalSemver.g:2446:1: rule__GitHubVersionRequirement__Group_1__1__Impl : ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) ;
    public final void rule__GitHubVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2450:1: ( ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:2451:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:2451:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:2452:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:2453:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            // InternalSemver.g:2453:3: rule__GitHubVersionRequirement__CommitISHAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__GitHubVersionRequirement__CommitISHAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__Group_1__1__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group__0"
    // InternalSemver.g:2462:1: rule__VersionRangeSetRequirement__Group__0 : rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 ;
    public final void rule__VersionRangeSetRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2466:1: ( rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 )
            // InternalSemver.g:2467:2: rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__VersionRangeSetRequirement__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group__0"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group__0__Impl"
    // InternalSemver.g:2474:1: rule__VersionRangeSetRequirement__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSetRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2478:1: ( ( () ) )
            // InternalSemver.g:2479:1: ( () )
            {
            // InternalSemver.g:2479:1: ( () )
            // InternalSemver.g:2480:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }
            // InternalSemver.g:2481:2: ()
            // InternalSemver.g:2481:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group__0__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group__1"
    // InternalSemver.g:2489:1: rule__VersionRangeSetRequirement__Group__1 : rule__VersionRangeSetRequirement__Group__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2493:1: ( rule__VersionRangeSetRequirement__Group__1__Impl )
            // InternalSemver.g:2494:2: rule__VersionRangeSetRequirement__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group__1"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group__1__Impl"
    // InternalSemver.g:2500:1: rule__VersionRangeSetRequirement__Group__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) ;
    public final void rule__VersionRangeSetRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2504:1: ( ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) )
            // InternalSemver.g:2505:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:2505:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            // InternalSemver.g:2506:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:2507:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( ((LA33_0>=RULE_ASTERIX && LA33_0<=RULE_LETTER_V)||(LA33_0>=48 && LA33_0<=54)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalSemver.g:2507:3: rule__VersionRangeSetRequirement__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionRangeSetRequirement__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group__1__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1__0"
    // InternalSemver.g:2516:1: rule__VersionRangeSetRequirement__Group_1__0 : rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2520:1: ( rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 )
            // InternalSemver.g:2521:2: rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1
            {
            pushFollow(FOLLOW_16);
            rule__VersionRangeSetRequirement__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1__0"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1__0__Impl"
    // InternalSemver.g:2528:1: rule__VersionRangeSetRequirement__Group_1__0__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2532:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) )
            // InternalSemver.g:2533:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            {
            // InternalSemver.g:2533:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            // InternalSemver.g:2534:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }
            // InternalSemver.g:2535:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            // InternalSemver.g:2535:3: rule__VersionRangeSetRequirement__RangesAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__RangesAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1__0__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1__1"
    // InternalSemver.g:2543:1: rule__VersionRangeSetRequirement__Group_1__1 : rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2547:1: ( rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 )
            // InternalSemver.g:2548:2: rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2
            {
            pushFollow(FOLLOW_16);
            rule__VersionRangeSetRequirement__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1__1"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1__1__Impl"
    // InternalSemver.g:2555:1: rule__VersionRangeSetRequirement__Group_1__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2559:1: ( ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) )
            // InternalSemver.g:2560:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            {
            // InternalSemver.g:2560:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            // InternalSemver.g:2561:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }
            // InternalSemver.g:2562:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==RULE_WS) ) {
                    int LA34_1 = input.LA(2);

                    if ( (LA34_1==56) ) {
                        alt34=1;
                    }


                }
                else if ( (LA34_0==56) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSemver.g:2562:3: rule__VersionRangeSetRequirement__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_17);
            	    rule__VersionRangeSetRequirement__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1__1__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1__2"
    // InternalSemver.g:2570:1: rule__VersionRangeSetRequirement__Group_1__2 : rule__VersionRangeSetRequirement__Group_1__2__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2574:1: ( rule__VersionRangeSetRequirement__Group_1__2__Impl )
            // InternalSemver.g:2575:2: rule__VersionRangeSetRequirement__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1__2"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1__2__Impl"
    // InternalSemver.g:2581:1: rule__VersionRangeSetRequirement__Group_1__2__Impl : ( ( RULE_WS )? ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2585:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:2586:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:2586:1: ( ( RULE_WS )? )
            // InternalSemver.g:2587:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); 
            }
            // InternalSemver.g:2588:2: ( RULE_WS )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_WS) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalSemver.g:2588:3: RULE_WS
                    {
                    match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1__2__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__0"
    // InternalSemver.g:2597:1: rule__VersionRangeSetRequirement__Group_1_1__0 : rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2601:1: ( rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 )
            // InternalSemver.g:2602:2: rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1
            {
            pushFollow(FOLLOW_16);
            rule__VersionRangeSetRequirement__Group_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__0"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__0__Impl"
    // InternalSemver.g:2609:1: rule__VersionRangeSetRequirement__Group_1_1__0__Impl : ( ( RULE_WS )? ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2613:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:2614:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:2614:1: ( ( RULE_WS )? )
            // InternalSemver.g:2615:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }
            // InternalSemver.g:2616:2: ( RULE_WS )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_WS) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalSemver.g:2616:3: RULE_WS
                    {
                    match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__0__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__1"
    // InternalSemver.g:2624:1: rule__VersionRangeSetRequirement__Group_1_1__1 : rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2628:1: ( rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 )
            // InternalSemver.g:2629:2: rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2
            {
            pushFollow(FOLLOW_18);
            rule__VersionRangeSetRequirement__Group_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__1"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__1__Impl"
    // InternalSemver.g:2636:1: rule__VersionRangeSetRequirement__Group_1_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2640:1: ( ( '||' ) )
            // InternalSemver.g:2641:1: ( '||' )
            {
            // InternalSemver.g:2641:1: ( '||' )
            // InternalSemver.g:2642:2: '||'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); 
            }
            match(input,56,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__1__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__2"
    // InternalSemver.g:2651:1: rule__VersionRangeSetRequirement__Group_1_1__2 : rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2655:1: ( rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 )
            // InternalSemver.g:2656:2: rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3
            {
            pushFollow(FOLLOW_18);
            rule__VersionRangeSetRequirement__Group_1_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1_1__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__2"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__2__Impl"
    // InternalSemver.g:2663:1: rule__VersionRangeSetRequirement__Group_1_1__2__Impl : ( ( RULE_WS )? ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2667:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:2668:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:2668:1: ( ( RULE_WS )? )
            // InternalSemver.g:2669:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }
            // InternalSemver.g:2670:2: ( RULE_WS )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_WS) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalSemver.g:2670:3: RULE_WS
                    {
                    match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__2__Impl"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__3"
    // InternalSemver.g:2678:1: rule__VersionRangeSetRequirement__Group_1_1__3 : rule__VersionRangeSetRequirement__Group_1_1__3__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2682:1: ( rule__VersionRangeSetRequirement__Group_1_1__3__Impl )
            // InternalSemver.g:2683:2: rule__VersionRangeSetRequirement__Group_1_1__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1_1__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__3"


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__3__Impl"
    // InternalSemver.g:2689:1: rule__VersionRangeSetRequirement__Group_1_1__3__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2693:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) )
            // InternalSemver.g:2694:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            {
            // InternalSemver.g:2694:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            // InternalSemver.g:2695:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }
            // InternalSemver.g:2696:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            // InternalSemver.g:2696:3: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__RangesAssignment_1_1_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__Group_1_1__3__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__0"
    // InternalSemver.g:2705:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2709:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemver.g:2710:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__HyphenVersionRange__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__0"


    // $ANTLR start "rule__HyphenVersionRange__Group__0__Impl"
    // InternalSemver.g:2717:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2721:1: ( ( () ) )
            // InternalSemver.g:2722:1: ( () )
            {
            // InternalSemver.g:2722:1: ( () )
            // InternalSemver.g:2723:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }
            // InternalSemver.g:2724:2: ()
            // InternalSemver.g:2724:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__0__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__1"
    // InternalSemver.g:2732:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2736:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemver.g:2737:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__HyphenVersionRange__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__1"


    // $ANTLR start "rule__HyphenVersionRange__Group__1__Impl"
    // InternalSemver.g:2744:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2748:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemver.g:2749:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemver.g:2749:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemver.g:2750:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }
            // InternalSemver.g:2751:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemver.g:2751:3: rule__HyphenVersionRange__FromAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__FromAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__1__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__2"
    // InternalSemver.g:2759:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2763:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemver.g:2764:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__HyphenVersionRange__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__2"


    // $ANTLR start "rule__HyphenVersionRange__Group__2__Impl"
    // InternalSemver.g:2771:1: rule__HyphenVersionRange__Group__2__Impl : ( RULE_WS ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2775:1: ( ( RULE_WS ) )
            // InternalSemver.g:2776:1: ( RULE_WS )
            {
            // InternalSemver.g:2776:1: ( RULE_WS )
            // InternalSemver.g:2777:2: RULE_WS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__2__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__3"
    // InternalSemver.g:2786:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2790:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSemver.g:2791:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
            {
            pushFollow(FOLLOW_6);
            rule__HyphenVersionRange__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__3"


    // $ANTLR start "rule__HyphenVersionRange__Group__3__Impl"
    // InternalSemver.g:2798:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2802:1: ( ( '-' ) )
            // InternalSemver.g:2803:1: ( '-' )
            {
            // InternalSemver.g:2803:1: ( '-' )
            // InternalSemver.g:2804:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 
            }
            match(input,47,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__3__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__4"
    // InternalSemver.g:2813:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2817:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSemver.g:2818:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
            {
            pushFollow(FOLLOW_5);
            rule__HyphenVersionRange__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__4"


    // $ANTLR start "rule__HyphenVersionRange__Group__4__Impl"
    // InternalSemver.g:2825:1: rule__HyphenVersionRange__Group__4__Impl : ( RULE_WS ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2829:1: ( ( RULE_WS ) )
            // InternalSemver.g:2830:1: ( RULE_WS )
            {
            // InternalSemver.g:2830:1: ( RULE_WS )
            // InternalSemver.g:2831:2: RULE_WS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__4__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__5"
    // InternalSemver.g:2840:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2844:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSemver.g:2845:2: rule__HyphenVersionRange__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__5"


    // $ANTLR start "rule__HyphenVersionRange__Group__5__Impl"
    // InternalSemver.g:2851:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2855:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSemver.g:2856:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSemver.g:2856:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSemver.g:2857:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }
            // InternalSemver.g:2858:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSemver.g:2858:3: rule__HyphenVersionRange__ToAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__ToAssignment_5();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__Group__5__Impl"


    // $ANTLR start "rule__VersionRangeContraint__Group__0"
    // InternalSemver.g:2867:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2871:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSemver.g:2872:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__VersionRangeContraint__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group__0"


    // $ANTLR start "rule__VersionRangeContraint__Group__0__Impl"
    // InternalSemver.g:2879:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2883:1: ( ( () ) )
            // InternalSemver.g:2884:1: ( () )
            {
            // InternalSemver.g:2884:1: ( () )
            // InternalSemver.g:2885:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }
            // InternalSemver.g:2886:2: ()
            // InternalSemver.g:2886:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group__0__Impl"


    // $ANTLR start "rule__VersionRangeContraint__Group__1"
    // InternalSemver.g:2894:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2898:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSemver.g:2899:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__VersionRangeContraint__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group__1"


    // $ANTLR start "rule__VersionRangeContraint__Group__1__Impl"
    // InternalSemver.g:2906:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2910:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSemver.g:2911:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSemver.g:2911:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSemver.g:2912:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }
            // InternalSemver.g:2913:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSemver.g:2913:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__VersionConstraintsAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group__1__Impl"


    // $ANTLR start "rule__VersionRangeContraint__Group__2"
    // InternalSemver.g:2921:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2925:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSemver.g:2926:2: rule__VersionRangeContraint__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group__2"


    // $ANTLR start "rule__VersionRangeContraint__Group__2__Impl"
    // InternalSemver.g:2932:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2936:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSemver.g:2937:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSemver.g:2937:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSemver.g:2938:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }
            // InternalSemver.g:2939:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_WS) ) {
                    int LA38_2 = input.LA(2);

                    if ( ((LA38_2>=RULE_ASTERIX && LA38_2<=RULE_LETTER_V)||(LA38_2>=48 && LA38_2<=54)) ) {
                        alt38=1;
                    }


                }


                switch (alt38) {
            	case 1 :
            	    // InternalSemver.g:2939:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_20);
            	    rule__VersionRangeContraint__Group_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group__2__Impl"


    // $ANTLR start "rule__VersionRangeContraint__Group_2__0"
    // InternalSemver.g:2948:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2952:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSemver.g:2953:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
            {
            pushFollow(FOLLOW_5);
            rule__VersionRangeContraint__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group_2__0"


    // $ANTLR start "rule__VersionRangeContraint__Group_2__0__Impl"
    // InternalSemver.g:2960:1: rule__VersionRangeContraint__Group_2__0__Impl : ( RULE_WS ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2964:1: ( ( RULE_WS ) )
            // InternalSemver.g:2965:1: ( RULE_WS )
            {
            // InternalSemver.g:2965:1: ( RULE_WS )
            // InternalSemver.g:2966:2: RULE_WS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group_2__0__Impl"


    // $ANTLR start "rule__VersionRangeContraint__Group_2__1"
    // InternalSemver.g:2975:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2979:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSemver.g:2980:2: rule__VersionRangeContraint__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group_2__1"


    // $ANTLR start "rule__VersionRangeContraint__Group_2__1__Impl"
    // InternalSemver.g:2986:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2990:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSemver.g:2991:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSemver.g:2991:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSemver.g:2992:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }
            // InternalSemver.g:2993:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSemver.g:2993:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__VersionConstraintsAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__Group_2__1__Impl"


    // $ANTLR start "rule__SimpleVersion__Group__0"
    // InternalSemver.g:3002:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3006:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemver.g:3007:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__SimpleVersion__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__0"


    // $ANTLR start "rule__SimpleVersion__Group__0__Impl"
    // InternalSemver.g:3014:1: rule__SimpleVersion__Group__0__Impl : ( ( rule__SimpleVersion__Group_0__0 )* ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3018:1: ( ( ( rule__SimpleVersion__Group_0__0 )* ) )
            // InternalSemver.g:3019:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            {
            // InternalSemver.g:3019:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            // InternalSemver.g:3020:2: ( rule__SimpleVersion__Group_0__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup_0()); 
            }
            // InternalSemver.g:3021:2: ( rule__SimpleVersion__Group_0__0 )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( ((LA39_0>=48 && LA39_0<=54)) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSemver.g:3021:3: rule__SimpleVersion__Group_0__0
            	    {
            	    pushFollow(FOLLOW_21);
            	    rule__SimpleVersion__Group_0__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getGroup_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__0__Impl"


    // $ANTLR start "rule__SimpleVersion__Group__1"
    // InternalSemver.g:3029:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3033:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemver.g:3034:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
            {
            pushFollow(FOLLOW_5);
            rule__SimpleVersion__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__1"


    // $ANTLR start "rule__SimpleVersion__Group__1__Impl"
    // InternalSemver.g:3041:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3045:1: ( ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) )
            // InternalSemver.g:3046:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            {
            // InternalSemver.g:3046:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            // InternalSemver.g:3047:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); 
            }
            // InternalSemver.g:3048:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==RULE_LETTER_V) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalSemver.g:3048:3: rule__SimpleVersion__WithLetterVAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__SimpleVersion__WithLetterVAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__1__Impl"


    // $ANTLR start "rule__SimpleVersion__Group__2"
    // InternalSemver.g:3056:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3060:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSemver.g:3061:2: rule__SimpleVersion__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__2"


    // $ANTLR start "rule__SimpleVersion__Group__2__Impl"
    // InternalSemver.g:3067:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3071:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSemver.g:3072:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSemver.g:3072:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSemver.g:3073:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            }
            // InternalSemver.g:3074:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSemver.g:3074:3: rule__SimpleVersion__NumberAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__NumberAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__2__Impl"


    // $ANTLR start "rule__SimpleVersion__Group_0__0"
    // InternalSemver.g:3083:1: rule__SimpleVersion__Group_0__0 : rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 ;
    public final void rule__SimpleVersion__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3087:1: ( rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 )
            // InternalSemver.g:3088:2: rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1
            {
            pushFollow(FOLLOW_6);
            rule__SimpleVersion__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group_0__0"


    // $ANTLR start "rule__SimpleVersion__Group_0__0__Impl"
    // InternalSemver.g:3095:1: rule__SimpleVersion__Group_0__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) ;
    public final void rule__SimpleVersion__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3099:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) )
            // InternalSemver.g:3100:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            {
            // InternalSemver.g:3100:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            // InternalSemver.g:3101:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); 
            }
            // InternalSemver.g:3102:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            // InternalSemver.g:3102:3: rule__SimpleVersion__ComparatorsAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__ComparatorsAssignment_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group_0__0__Impl"


    // $ANTLR start "rule__SimpleVersion__Group_0__1"
    // InternalSemver.g:3110:1: rule__SimpleVersion__Group_0__1 : rule__SimpleVersion__Group_0__1__Impl ;
    public final void rule__SimpleVersion__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3114:1: ( rule__SimpleVersion__Group_0__1__Impl )
            // InternalSemver.g:3115:2: rule__SimpleVersion__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group_0__1"


    // $ANTLR start "rule__SimpleVersion__Group_0__1__Impl"
    // InternalSemver.g:3121:1: rule__SimpleVersion__Group_0__1__Impl : ( ( RULE_WS )? ) ;
    public final void rule__SimpleVersion__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3125:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:3126:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:3126:1: ( ( RULE_WS )? )
            // InternalSemver.g:3127:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); 
            }
            // InternalSemver.g:3128:2: ( RULE_WS )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_WS) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalSemver.g:3128:3: RULE_WS
                    {
                    match(input,RULE_WS,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group_0__1__Impl"


    // $ANTLR start "rule__VersionNumber__Group__0"
    // InternalSemver.g:3137:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3141:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemver.g:3142:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
            {
            pushFollow(FOLLOW_22);
            rule__VersionNumber__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group__0"


    // $ANTLR start "rule__VersionNumber__Group__0__Impl"
    // InternalSemver.g:3149:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3153:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemver.g:3154:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemver.g:3154:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemver.g:3155:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }
            // InternalSemver.g:3156:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemver.g:3156:3: rule__VersionNumber__MajorAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__MajorAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group__0__Impl"


    // $ANTLR start "rule__VersionNumber__Group__1"
    // InternalSemver.g:3164:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3168:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSemver.g:3169:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
            {
            pushFollow(FOLLOW_22);
            rule__VersionNumber__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group__1"


    // $ANTLR start "rule__VersionNumber__Group__1__Impl"
    // InternalSemver.g:3176:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3180:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemver.g:3181:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemver.g:3181:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemver.g:3182:2: ( rule__VersionNumber__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }
            // InternalSemver.g:3183:2: ( rule__VersionNumber__Group_1__0 )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==42) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalSemver.g:3183:3: rule__VersionNumber__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionNumber__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group__1__Impl"


    // $ANTLR start "rule__VersionNumber__Group__2"
    // InternalSemver.g:3191:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3195:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSemver.g:3196:2: rule__VersionNumber__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group__2"


    // $ANTLR start "rule__VersionNumber__Group__2__Impl"
    // InternalSemver.g:3202:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3206:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSemver.g:3207:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSemver.g:3207:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSemver.g:3208:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }
            // InternalSemver.g:3209:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==45||LA43_0==47) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalSemver.g:3209:3: rule__VersionNumber__QualifierAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionNumber__QualifierAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group__2__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1__0"
    // InternalSemver.g:3218:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3222:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemver.g:3223:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
            {
            pushFollow(FOLLOW_5);
            rule__VersionNumber__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1__0"


    // $ANTLR start "rule__VersionNumber__Group_1__0__Impl"
    // InternalSemver.g:3230:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3234:1: ( ( '.' ) )
            // InternalSemver.g:3235:1: ( '.' )
            {
            // InternalSemver.g:3235:1: ( '.' )
            // InternalSemver.g:3236:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            }
            match(input,42,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1__0__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1__1"
    // InternalSemver.g:3245:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3249:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemver.g:3250:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
            {
            pushFollow(FOLLOW_23);
            rule__VersionNumber__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1__1"


    // $ANTLR start "rule__VersionNumber__Group_1__1__Impl"
    // InternalSemver.g:3257:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3261:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemver.g:3262:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemver.g:3262:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemver.g:3263:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }
            // InternalSemver.g:3264:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemver.g:3264:3: rule__VersionNumber__MinorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__MinorAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1__1__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1__2"
    // InternalSemver.g:3272:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3276:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemver.g:3277:2: rule__VersionNumber__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1__2"


    // $ANTLR start "rule__VersionNumber__Group_1__2__Impl"
    // InternalSemver.g:3283:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3287:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemver.g:3288:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemver.g:3288:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemver.g:3289:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }
            // InternalSemver.g:3290:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==42) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalSemver.g:3290:3: rule__VersionNumber__Group_1_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionNumber__Group_1_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1__2__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1_2__0"
    // InternalSemver.g:3299:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3303:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemver.g:3304:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
            {
            pushFollow(FOLLOW_5);
            rule__VersionNumber__Group_1_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2__0"


    // $ANTLR start "rule__VersionNumber__Group_1_2__0__Impl"
    // InternalSemver.g:3311:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3315:1: ( ( '.' ) )
            // InternalSemver.g:3316:1: ( '.' )
            {
            // InternalSemver.g:3316:1: ( '.' )
            // InternalSemver.g:3317:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            }
            match(input,42,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2__0__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1_2__1"
    // InternalSemver.g:3326:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3330:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemver.g:3331:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
            {
            pushFollow(FOLLOW_23);
            rule__VersionNumber__Group_1_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2__1"


    // $ANTLR start "rule__VersionNumber__Group_1_2__1__Impl"
    // InternalSemver.g:3338:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3342:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSemver.g:3343:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSemver.g:3343:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSemver.g:3344:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }
            // InternalSemver.g:3345:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSemver.g:3345:3: rule__VersionNumber__PatchAssignment_1_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__PatchAssignment_1_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2__1__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1_2__2"
    // InternalSemver.g:3353:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3357:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemver.g:3358:2: rule__VersionNumber__Group_1_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2__2"


    // $ANTLR start "rule__VersionNumber__Group_1_2__2__Impl"
    // InternalSemver.g:3364:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3368:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSemver.g:3369:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSemver.g:3369:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSemver.g:3370:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }
            // InternalSemver.g:3371:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==42) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSemver.g:3371:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_24);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2__2__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1_2_2__0"
    // InternalSemver.g:3380:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3384:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSemver.g:3385:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
            {
            pushFollow(FOLLOW_5);
            rule__VersionNumber__Group_1_2_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2_2__0"


    // $ANTLR start "rule__VersionNumber__Group_1_2_2__0__Impl"
    // InternalSemver.g:3392:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3396:1: ( ( '.' ) )
            // InternalSemver.g:3397:1: ( '.' )
            {
            // InternalSemver.g:3397:1: ( '.' )
            // InternalSemver.g:3398:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            }
            match(input,42,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2_2__0__Impl"


    // $ANTLR start "rule__VersionNumber__Group_1_2_2__1"
    // InternalSemver.g:3407:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3411:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSemver.g:3412:2: rule__VersionNumber__Group_1_2_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2_2__1"


    // $ANTLR start "rule__VersionNumber__Group_1_2_2__1__Impl"
    // InternalSemver.g:3418:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3422:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSemver.g:3423:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSemver.g:3423:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSemver.g:3424:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }
            // InternalSemver.g:3425:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSemver.g:3425:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__ExtendedAssignment_1_2_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__Group_1_2_2__1__Impl"


    // $ANTLR start "rule__Qualifier__Group_0__0"
    // InternalSemver.g:3434:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3438:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemver.g:3439:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
            {
            pushFollow(FOLLOW_12);
            rule__Qualifier__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0__0"


    // $ANTLR start "rule__Qualifier__Group_0__0__Impl"
    // InternalSemver.g:3446:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3450:1: ( ( '-' ) )
            // InternalSemver.g:3451:1: ( '-' )
            {
            // InternalSemver.g:3451:1: ( '-' )
            // InternalSemver.g:3452:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            }
            match(input,47,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0__0__Impl"


    // $ANTLR start "rule__Qualifier__Group_0__1"
    // InternalSemver.g:3461:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl rule__Qualifier__Group_0__2 ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3465:1: ( rule__Qualifier__Group_0__1__Impl rule__Qualifier__Group_0__2 )
            // InternalSemver.g:3466:2: rule__Qualifier__Group_0__1__Impl rule__Qualifier__Group_0__2
            {
            pushFollow(FOLLOW_25);
            rule__Qualifier__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0__1"


    // $ANTLR start "rule__Qualifier__Group_0__1__Impl"
    // InternalSemver.g:3473:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3477:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemver.g:3478:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemver.g:3478:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemver.g:3479:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }
            // InternalSemver.g:3480:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemver.g:3480:3: rule__Qualifier__PreReleaseAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__PreReleaseAssignment_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0__1__Impl"


    // $ANTLR start "rule__Qualifier__Group_0__2"
    // InternalSemver.g:3488:1: rule__Qualifier__Group_0__2 : rule__Qualifier__Group_0__2__Impl ;
    public final void rule__Qualifier__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3492:1: ( rule__Qualifier__Group_0__2__Impl )
            // InternalSemver.g:3493:2: rule__Qualifier__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0__2"


    // $ANTLR start "rule__Qualifier__Group_0__2__Impl"
    // InternalSemver.g:3499:1: rule__Qualifier__Group_0__2__Impl : ( ( rule__Qualifier__Group_0_2__0 )? ) ;
    public final void rule__Qualifier__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3503:1: ( ( ( rule__Qualifier__Group_0_2__0 )? ) )
            // InternalSemver.g:3504:1: ( ( rule__Qualifier__Group_0_2__0 )? )
            {
            // InternalSemver.g:3504:1: ( ( rule__Qualifier__Group_0_2__0 )? )
            // InternalSemver.g:3505:2: ( rule__Qualifier__Group_0_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getGroup_0_2()); 
            }
            // InternalSemver.g:3506:2: ( rule__Qualifier__Group_0_2__0 )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==45) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalSemver.g:3506:3: rule__Qualifier__Group_0_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_0_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getGroup_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0__2__Impl"


    // $ANTLR start "rule__Qualifier__Group_0_2__0"
    // InternalSemver.g:3515:1: rule__Qualifier__Group_0_2__0 : rule__Qualifier__Group_0_2__0__Impl rule__Qualifier__Group_0_2__1 ;
    public final void rule__Qualifier__Group_0_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3519:1: ( rule__Qualifier__Group_0_2__0__Impl rule__Qualifier__Group_0_2__1 )
            // InternalSemver.g:3520:2: rule__Qualifier__Group_0_2__0__Impl rule__Qualifier__Group_0_2__1
            {
            pushFollow(FOLLOW_12);
            rule__Qualifier__Group_0_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0_2__0"


    // $ANTLR start "rule__Qualifier__Group_0_2__0__Impl"
    // InternalSemver.g:3527:1: rule__Qualifier__Group_0_2__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_0_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3531:1: ( ( '+' ) )
            // InternalSemver.g:3532:1: ( '+' )
            {
            // InternalSemver.g:3532:1: ( '+' )
            // InternalSemver.g:3533:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_0_2_0()); 
            }
            match(input,45,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getPlusSignKeyword_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0_2__0__Impl"


    // $ANTLR start "rule__Qualifier__Group_0_2__1"
    // InternalSemver.g:3542:1: rule__Qualifier__Group_0_2__1 : rule__Qualifier__Group_0_2__1__Impl ;
    public final void rule__Qualifier__Group_0_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3546:1: ( rule__Qualifier__Group_0_2__1__Impl )
            // InternalSemver.g:3547:2: rule__Qualifier__Group_0_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0_2__1"


    // $ANTLR start "rule__Qualifier__Group_0_2__1__Impl"
    // InternalSemver.g:3553:1: rule__Qualifier__Group_0_2__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) ) ;
    public final void rule__Qualifier__Group_0_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3557:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) ) )
            // InternalSemver.g:3558:1: ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) )
            {
            // InternalSemver.g:3558:1: ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) )
            // InternalSemver.g:3559:2: ( rule__Qualifier__BuildMetadataAssignment_0_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_0_2_1()); 
            }
            // InternalSemver.g:3560:2: ( rule__Qualifier__BuildMetadataAssignment_0_2_1 )
            // InternalSemver.g:3560:3: rule__Qualifier__BuildMetadataAssignment_0_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__BuildMetadataAssignment_0_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_0_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_0_2__1__Impl"


    // $ANTLR start "rule__Qualifier__Group_1__0"
    // InternalSemver.g:3569:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3573:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemver.g:3574:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__Qualifier__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_1__0"


    // $ANTLR start "rule__Qualifier__Group_1__0__Impl"
    // InternalSemver.g:3581:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3585:1: ( ( '+' ) )
            // InternalSemver.g:3586:1: ( '+' )
            {
            // InternalSemver.g:3586:1: ( '+' )
            // InternalSemver.g:3587:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            }
            match(input,45,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_1__0__Impl"


    // $ANTLR start "rule__Qualifier__Group_1__1"
    // InternalSemver.g:3596:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3600:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemver.g:3601:2: rule__Qualifier__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_1__1"


    // $ANTLR start "rule__Qualifier__Group_1__1__Impl"
    // InternalSemver.g:3607:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3611:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemver.g:3612:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemver.g:3612:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemver.g:3613:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }
            // InternalSemver.g:3614:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemver.g:3614:3: rule__Qualifier__BuildMetadataAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__BuildMetadataAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__Group_1__1__Impl"


    // $ANTLR start "rule__QualifierTag__Group__0"
    // InternalSemver.g:3623:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3627:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSemver.g:3628:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
            {
            pushFollow(FOLLOW_23);
            rule__QualifierTag__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group__0"


    // $ANTLR start "rule__QualifierTag__Group__0__Impl"
    // InternalSemver.g:3635:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3639:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSemver.g:3640:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSemver.g:3640:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSemver.g:3641:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }
            // InternalSemver.g:3642:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSemver.g:3642:3: rule__QualifierTag__PartsAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__PartsAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group__0__Impl"


    // $ANTLR start "rule__QualifierTag__Group__1"
    // InternalSemver.g:3650:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3654:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSemver.g:3655:2: rule__QualifierTag__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group__1"


    // $ANTLR start "rule__QualifierTag__Group__1__Impl"
    // InternalSemver.g:3661:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3665:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSemver.g:3666:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSemver.g:3666:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSemver.g:3667:2: ( rule__QualifierTag__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }
            // InternalSemver.g:3668:2: ( rule__QualifierTag__Group_1__0 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==42) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSemver.g:3668:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_24);
            	    rule__QualifierTag__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group__1__Impl"


    // $ANTLR start "rule__QualifierTag__Group_1__0"
    // InternalSemver.g:3677:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3681:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSemver.g:3682:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__QualifierTag__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group_1__0"


    // $ANTLR start "rule__QualifierTag__Group_1__0__Impl"
    // InternalSemver.g:3689:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3693:1: ( ( '.' ) )
            // InternalSemver.g:3694:1: ( '.' )
            {
            // InternalSemver.g:3694:1: ( '.' )
            // InternalSemver.g:3695:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 
            }
            match(input,42,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group_1__0__Impl"


    // $ANTLR start "rule__QualifierTag__Group_1__1"
    // InternalSemver.g:3704:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3708:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSemver.g:3709:2: rule__QualifierTag__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group_1__1"


    // $ANTLR start "rule__QualifierTag__Group_1__1__Impl"
    // InternalSemver.g:3715:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3719:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSemver.g:3720:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSemver.g:3720:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSemver.g:3721:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }
            // InternalSemver.g:3722:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSemver.g:3722:3: rule__QualifierTag__PartsAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__PartsAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__Group_1__1__Impl"


    // $ANTLR start "rule__FILE_TAG__Group__0"
    // InternalSemver.g:3731:1: rule__FILE_TAG__Group__0 : rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 ;
    public final void rule__FILE_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3735:1: ( rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 )
            // InternalSemver.g:3736:2: rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1
            {
            pushFollow(FOLLOW_26);
            rule__FILE_TAG__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__FILE_TAG__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__0"


    // $ANTLR start "rule__FILE_TAG__Group__0__Impl"
    // InternalSemver.g:3743:1: rule__FILE_TAG__Group__0__Impl : ( RULE_LETTER_F ) ;
    public final void rule__FILE_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3747:1: ( ( RULE_LETTER_F ) )
            // InternalSemver.g:3748:1: ( RULE_LETTER_F )
            {
            // InternalSemver.g:3748:1: ( RULE_LETTER_F )
            // InternalSemver.g:3749:2: RULE_LETTER_F
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getLETTER_FTerminalRuleCall_0()); 
            }
            match(input,RULE_LETTER_F,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFILE_TAGAccess().getLETTER_FTerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__0__Impl"


    // $ANTLR start "rule__FILE_TAG__Group__1"
    // InternalSemver.g:3758:1: rule__FILE_TAG__Group__1 : rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 ;
    public final void rule__FILE_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3762:1: ( rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 )
            // InternalSemver.g:3763:2: rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2
            {
            pushFollow(FOLLOW_27);
            rule__FILE_TAG__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__FILE_TAG__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__1"


    // $ANTLR start "rule__FILE_TAG__Group__1__Impl"
    // InternalSemver.g:3770:1: rule__FILE_TAG__Group__1__Impl : ( RULE_LETTER_I ) ;
    public final void rule__FILE_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3774:1: ( ( RULE_LETTER_I ) )
            // InternalSemver.g:3775:1: ( RULE_LETTER_I )
            {
            // InternalSemver.g:3775:1: ( RULE_LETTER_I )
            // InternalSemver.g:3776:2: RULE_LETTER_I
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getLETTER_ITerminalRuleCall_1()); 
            }
            match(input,RULE_LETTER_I,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFILE_TAGAccess().getLETTER_ITerminalRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__1__Impl"


    // $ANTLR start "rule__FILE_TAG__Group__2"
    // InternalSemver.g:3785:1: rule__FILE_TAG__Group__2 : rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 ;
    public final void rule__FILE_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3789:1: ( rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 )
            // InternalSemver.g:3790:2: rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3
            {
            pushFollow(FOLLOW_28);
            rule__FILE_TAG__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__FILE_TAG__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__2"


    // $ANTLR start "rule__FILE_TAG__Group__2__Impl"
    // InternalSemver.g:3797:1: rule__FILE_TAG__Group__2__Impl : ( RULE_LETTER_L ) ;
    public final void rule__FILE_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3801:1: ( ( RULE_LETTER_L ) )
            // InternalSemver.g:3802:1: ( RULE_LETTER_L )
            {
            // InternalSemver.g:3802:1: ( RULE_LETTER_L )
            // InternalSemver.g:3803:2: RULE_LETTER_L
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getLETTER_LTerminalRuleCall_2()); 
            }
            match(input,RULE_LETTER_L,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFILE_TAGAccess().getLETTER_LTerminalRuleCall_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__2__Impl"


    // $ANTLR start "rule__FILE_TAG__Group__3"
    // InternalSemver.g:3812:1: rule__FILE_TAG__Group__3 : rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 ;
    public final void rule__FILE_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3816:1: ( rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 )
            // InternalSemver.g:3817:2: rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4
            {
            pushFollow(FOLLOW_8);
            rule__FILE_TAG__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__FILE_TAG__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__3"


    // $ANTLR start "rule__FILE_TAG__Group__3__Impl"
    // InternalSemver.g:3824:1: rule__FILE_TAG__Group__3__Impl : ( RULE_LETTER_E ) ;
    public final void rule__FILE_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3828:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3829:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3829:1: ( RULE_LETTER_E )
            // InternalSemver.g:3830:2: RULE_LETTER_E
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getLETTER_ETerminalRuleCall_3()); 
            }
            match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFILE_TAGAccess().getLETTER_ETerminalRuleCall_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__3__Impl"


    // $ANTLR start "rule__FILE_TAG__Group__4"
    // InternalSemver.g:3839:1: rule__FILE_TAG__Group__4 : rule__FILE_TAG__Group__4__Impl ;
    public final void rule__FILE_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3843:1: ( rule__FILE_TAG__Group__4__Impl )
            // InternalSemver.g:3844:2: rule__FILE_TAG__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__FILE_TAG__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__4"


    // $ANTLR start "rule__FILE_TAG__Group__4__Impl"
    // InternalSemver.g:3850:1: rule__FILE_TAG__Group__4__Impl : ( ':' ) ;
    public final void rule__FILE_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3854:1: ( ( ':' ) )
            // InternalSemver.g:3855:1: ( ':' )
            {
            // InternalSemver.g:3855:1: ( ':' )
            // InternalSemver.g:3856:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getColonKeyword_4()); 
            }
            match(input,46,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFILE_TAGAccess().getColonKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FILE_TAG__Group__4__Impl"


    // $ANTLR start "rule__SEMVER_TAG__Group__0"
    // InternalSemver.g:3866:1: rule__SEMVER_TAG__Group__0 : rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 ;
    public final void rule__SEMVER_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3870:1: ( rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 )
            // InternalSemver.g:3871:2: rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1
            {
            pushFollow(FOLLOW_28);
            rule__SEMVER_TAG__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__0"


    // $ANTLR start "rule__SEMVER_TAG__Group__0__Impl"
    // InternalSemver.g:3878:1: rule__SEMVER_TAG__Group__0__Impl : ( RULE_LETTER_S ) ;
    public final void rule__SEMVER_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3882:1: ( ( RULE_LETTER_S ) )
            // InternalSemver.g:3883:1: ( RULE_LETTER_S )
            {
            // InternalSemver.g:3883:1: ( RULE_LETTER_S )
            // InternalSemver.g:3884:2: RULE_LETTER_S
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getLETTER_STerminalRuleCall_0()); 
            }
            match(input,RULE_LETTER_S,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getLETTER_STerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__0__Impl"


    // $ANTLR start "rule__SEMVER_TAG__Group__1"
    // InternalSemver.g:3893:1: rule__SEMVER_TAG__Group__1 : rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 ;
    public final void rule__SEMVER_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3897:1: ( rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 )
            // InternalSemver.g:3898:2: rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2
            {
            pushFollow(FOLLOW_29);
            rule__SEMVER_TAG__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__1"


    // $ANTLR start "rule__SEMVER_TAG__Group__1__Impl"
    // InternalSemver.g:3905:1: rule__SEMVER_TAG__Group__1__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3909:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3910:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3910:1: ( RULE_LETTER_E )
            // InternalSemver.g:3911:2: RULE_LETTER_E
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_1()); 
            }
            match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__1__Impl"


    // $ANTLR start "rule__SEMVER_TAG__Group__2"
    // InternalSemver.g:3920:1: rule__SEMVER_TAG__Group__2 : rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 ;
    public final void rule__SEMVER_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3924:1: ( rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 )
            // InternalSemver.g:3925:2: rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3
            {
            pushFollow(FOLLOW_30);
            rule__SEMVER_TAG__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__2"


    // $ANTLR start "rule__SEMVER_TAG__Group__2__Impl"
    // InternalSemver.g:3932:1: rule__SEMVER_TAG__Group__2__Impl : ( RULE_LETTER_M ) ;
    public final void rule__SEMVER_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3936:1: ( ( RULE_LETTER_M ) )
            // InternalSemver.g:3937:1: ( RULE_LETTER_M )
            {
            // InternalSemver.g:3937:1: ( RULE_LETTER_M )
            // InternalSemver.g:3938:2: RULE_LETTER_M
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getLETTER_MTerminalRuleCall_2()); 
            }
            match(input,RULE_LETTER_M,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getLETTER_MTerminalRuleCall_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__2__Impl"


    // $ANTLR start "rule__SEMVER_TAG__Group__3"
    // InternalSemver.g:3947:1: rule__SEMVER_TAG__Group__3 : rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 ;
    public final void rule__SEMVER_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3951:1: ( rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 )
            // InternalSemver.g:3952:2: rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4
            {
            pushFollow(FOLLOW_28);
            rule__SEMVER_TAG__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__3"


    // $ANTLR start "rule__SEMVER_TAG__Group__3__Impl"
    // InternalSemver.g:3959:1: rule__SEMVER_TAG__Group__3__Impl : ( RULE_LETTER_V ) ;
    public final void rule__SEMVER_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3963:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:3964:1: ( RULE_LETTER_V )
            {
            // InternalSemver.g:3964:1: ( RULE_LETTER_V )
            // InternalSemver.g:3965:2: RULE_LETTER_V
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getLETTER_VTerminalRuleCall_3()); 
            }
            match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getLETTER_VTerminalRuleCall_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__3__Impl"


    // $ANTLR start "rule__SEMVER_TAG__Group__4"
    // InternalSemver.g:3974:1: rule__SEMVER_TAG__Group__4 : rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 ;
    public final void rule__SEMVER_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3978:1: ( rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 )
            // InternalSemver.g:3979:2: rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5
            {
            pushFollow(FOLLOW_31);
            rule__SEMVER_TAG__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__4"


    // $ANTLR start "rule__SEMVER_TAG__Group__4__Impl"
    // InternalSemver.g:3986:1: rule__SEMVER_TAG__Group__4__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3990:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3991:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3991:1: ( RULE_LETTER_E )
            // InternalSemver.g:3992:2: RULE_LETTER_E
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_4()); 
            }
            match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__4__Impl"


    // $ANTLR start "rule__SEMVER_TAG__Group__5"
    // InternalSemver.g:4001:1: rule__SEMVER_TAG__Group__5 : rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 ;
    public final void rule__SEMVER_TAG__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4005:1: ( rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 )
            // InternalSemver.g:4006:2: rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6
            {
            pushFollow(FOLLOW_8);
            rule__SEMVER_TAG__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__5"


    // $ANTLR start "rule__SEMVER_TAG__Group__5__Impl"
    // InternalSemver.g:4013:1: rule__SEMVER_TAG__Group__5__Impl : ( RULE_LETTER_R ) ;
    public final void rule__SEMVER_TAG__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4017:1: ( ( RULE_LETTER_R ) )
            // InternalSemver.g:4018:1: ( RULE_LETTER_R )
            {
            // InternalSemver.g:4018:1: ( RULE_LETTER_R )
            // InternalSemver.g:4019:2: RULE_LETTER_R
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getLETTER_RTerminalRuleCall_5()); 
            }
            match(input,RULE_LETTER_R,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getLETTER_RTerminalRuleCall_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__5__Impl"


    // $ANTLR start "rule__SEMVER_TAG__Group__6"
    // InternalSemver.g:4028:1: rule__SEMVER_TAG__Group__6 : rule__SEMVER_TAG__Group__6__Impl ;
    public final void rule__SEMVER_TAG__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4032:1: ( rule__SEMVER_TAG__Group__6__Impl )
            // InternalSemver.g:4033:2: rule__SEMVER_TAG__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SEMVER_TAG__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__6"


    // $ANTLR start "rule__SEMVER_TAG__Group__6__Impl"
    // InternalSemver.g:4039:1: rule__SEMVER_TAG__Group__6__Impl : ( ':' ) ;
    public final void rule__SEMVER_TAG__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4043:1: ( ( ':' ) )
            // InternalSemver.g:4044:1: ( ':' )
            {
            // InternalSemver.g:4044:1: ( ':' )
            // InternalSemver.g:4045:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6()); 
            }
            match(input,46,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SEMVER_TAG__Group__6__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__0"
    // InternalSemver.g:4055:1: rule__WORKSPACE_TAG__Group__0 : rule__WORKSPACE_TAG__Group__0__Impl rule__WORKSPACE_TAG__Group__1 ;
    public final void rule__WORKSPACE_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4059:1: ( rule__WORKSPACE_TAG__Group__0__Impl rule__WORKSPACE_TAG__Group__1 )
            // InternalSemver.g:4060:2: rule__WORKSPACE_TAG__Group__0__Impl rule__WORKSPACE_TAG__Group__1
            {
            pushFollow(FOLLOW_32);
            rule__WORKSPACE_TAG__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__0"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__0__Impl"
    // InternalSemver.g:4067:1: rule__WORKSPACE_TAG__Group__0__Impl : ( RULE_LETTER_W ) ;
    public final void rule__WORKSPACE_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4071:1: ( ( RULE_LETTER_W ) )
            // InternalSemver.g:4072:1: ( RULE_LETTER_W )
            {
            // InternalSemver.g:4072:1: ( RULE_LETTER_W )
            // InternalSemver.g:4073:2: RULE_LETTER_W
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_WTerminalRuleCall_0()); 
            }
            match(input,RULE_LETTER_W,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_WTerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__0__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__1"
    // InternalSemver.g:4082:1: rule__WORKSPACE_TAG__Group__1 : rule__WORKSPACE_TAG__Group__1__Impl rule__WORKSPACE_TAG__Group__2 ;
    public final void rule__WORKSPACE_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4086:1: ( rule__WORKSPACE_TAG__Group__1__Impl rule__WORKSPACE_TAG__Group__2 )
            // InternalSemver.g:4087:2: rule__WORKSPACE_TAG__Group__1__Impl rule__WORKSPACE_TAG__Group__2
            {
            pushFollow(FOLLOW_31);
            rule__WORKSPACE_TAG__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__1"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__1__Impl"
    // InternalSemver.g:4094:1: rule__WORKSPACE_TAG__Group__1__Impl : ( RULE_LETTER_O ) ;
    public final void rule__WORKSPACE_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4098:1: ( ( RULE_LETTER_O ) )
            // InternalSemver.g:4099:1: ( RULE_LETTER_O )
            {
            // InternalSemver.g:4099:1: ( RULE_LETTER_O )
            // InternalSemver.g:4100:2: RULE_LETTER_O
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_OTerminalRuleCall_1()); 
            }
            match(input,RULE_LETTER_O,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_OTerminalRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__1__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__2"
    // InternalSemver.g:4109:1: rule__WORKSPACE_TAG__Group__2 : rule__WORKSPACE_TAG__Group__2__Impl rule__WORKSPACE_TAG__Group__3 ;
    public final void rule__WORKSPACE_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4113:1: ( rule__WORKSPACE_TAG__Group__2__Impl rule__WORKSPACE_TAG__Group__3 )
            // InternalSemver.g:4114:2: rule__WORKSPACE_TAG__Group__2__Impl rule__WORKSPACE_TAG__Group__3
            {
            pushFollow(FOLLOW_33);
            rule__WORKSPACE_TAG__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__2"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__2__Impl"
    // InternalSemver.g:4121:1: rule__WORKSPACE_TAG__Group__2__Impl : ( RULE_LETTER_R ) ;
    public final void rule__WORKSPACE_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4125:1: ( ( RULE_LETTER_R ) )
            // InternalSemver.g:4126:1: ( RULE_LETTER_R )
            {
            // InternalSemver.g:4126:1: ( RULE_LETTER_R )
            // InternalSemver.g:4127:2: RULE_LETTER_R
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_RTerminalRuleCall_2()); 
            }
            match(input,RULE_LETTER_R,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_RTerminalRuleCall_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__2__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__3"
    // InternalSemver.g:4136:1: rule__WORKSPACE_TAG__Group__3 : rule__WORKSPACE_TAG__Group__3__Impl rule__WORKSPACE_TAG__Group__4 ;
    public final void rule__WORKSPACE_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4140:1: ( rule__WORKSPACE_TAG__Group__3__Impl rule__WORKSPACE_TAG__Group__4 )
            // InternalSemver.g:4141:2: rule__WORKSPACE_TAG__Group__3__Impl rule__WORKSPACE_TAG__Group__4
            {
            pushFollow(FOLLOW_34);
            rule__WORKSPACE_TAG__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__3"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__3__Impl"
    // InternalSemver.g:4148:1: rule__WORKSPACE_TAG__Group__3__Impl : ( RULE_LETTER_K ) ;
    public final void rule__WORKSPACE_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4152:1: ( ( RULE_LETTER_K ) )
            // InternalSemver.g:4153:1: ( RULE_LETTER_K )
            {
            // InternalSemver.g:4153:1: ( RULE_LETTER_K )
            // InternalSemver.g:4154:2: RULE_LETTER_K
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_KTerminalRuleCall_3()); 
            }
            match(input,RULE_LETTER_K,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_KTerminalRuleCall_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__3__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__4"
    // InternalSemver.g:4163:1: rule__WORKSPACE_TAG__Group__4 : rule__WORKSPACE_TAG__Group__4__Impl rule__WORKSPACE_TAG__Group__5 ;
    public final void rule__WORKSPACE_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4167:1: ( rule__WORKSPACE_TAG__Group__4__Impl rule__WORKSPACE_TAG__Group__5 )
            // InternalSemver.g:4168:2: rule__WORKSPACE_TAG__Group__4__Impl rule__WORKSPACE_TAG__Group__5
            {
            pushFollow(FOLLOW_35);
            rule__WORKSPACE_TAG__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__4"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__4__Impl"
    // InternalSemver.g:4175:1: rule__WORKSPACE_TAG__Group__4__Impl : ( RULE_LETTER_S ) ;
    public final void rule__WORKSPACE_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4179:1: ( ( RULE_LETTER_S ) )
            // InternalSemver.g:4180:1: ( RULE_LETTER_S )
            {
            // InternalSemver.g:4180:1: ( RULE_LETTER_S )
            // InternalSemver.g:4181:2: RULE_LETTER_S
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_STerminalRuleCall_4()); 
            }
            match(input,RULE_LETTER_S,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_STerminalRuleCall_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__4__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__5"
    // InternalSemver.g:4190:1: rule__WORKSPACE_TAG__Group__5 : rule__WORKSPACE_TAG__Group__5__Impl rule__WORKSPACE_TAG__Group__6 ;
    public final void rule__WORKSPACE_TAG__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4194:1: ( rule__WORKSPACE_TAG__Group__5__Impl rule__WORKSPACE_TAG__Group__6 )
            // InternalSemver.g:4195:2: rule__WORKSPACE_TAG__Group__5__Impl rule__WORKSPACE_TAG__Group__6
            {
            pushFollow(FOLLOW_36);
            rule__WORKSPACE_TAG__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__5"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__5__Impl"
    // InternalSemver.g:4202:1: rule__WORKSPACE_TAG__Group__5__Impl : ( RULE_LETTER_P ) ;
    public final void rule__WORKSPACE_TAG__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4206:1: ( ( RULE_LETTER_P ) )
            // InternalSemver.g:4207:1: ( RULE_LETTER_P )
            {
            // InternalSemver.g:4207:1: ( RULE_LETTER_P )
            // InternalSemver.g:4208:2: RULE_LETTER_P
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_PTerminalRuleCall_5()); 
            }
            match(input,RULE_LETTER_P,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_PTerminalRuleCall_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__5__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__6"
    // InternalSemver.g:4217:1: rule__WORKSPACE_TAG__Group__6 : rule__WORKSPACE_TAG__Group__6__Impl rule__WORKSPACE_TAG__Group__7 ;
    public final void rule__WORKSPACE_TAG__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4221:1: ( rule__WORKSPACE_TAG__Group__6__Impl rule__WORKSPACE_TAG__Group__7 )
            // InternalSemver.g:4222:2: rule__WORKSPACE_TAG__Group__6__Impl rule__WORKSPACE_TAG__Group__7
            {
            pushFollow(FOLLOW_37);
            rule__WORKSPACE_TAG__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__7();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__6"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__6__Impl"
    // InternalSemver.g:4229:1: rule__WORKSPACE_TAG__Group__6__Impl : ( RULE_LETTER_A ) ;
    public final void rule__WORKSPACE_TAG__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4233:1: ( ( RULE_LETTER_A ) )
            // InternalSemver.g:4234:1: ( RULE_LETTER_A )
            {
            // InternalSemver.g:4234:1: ( RULE_LETTER_A )
            // InternalSemver.g:4235:2: RULE_LETTER_A
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_ATerminalRuleCall_6()); 
            }
            match(input,RULE_LETTER_A,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_ATerminalRuleCall_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__6__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__7"
    // InternalSemver.g:4244:1: rule__WORKSPACE_TAG__Group__7 : rule__WORKSPACE_TAG__Group__7__Impl rule__WORKSPACE_TAG__Group__8 ;
    public final void rule__WORKSPACE_TAG__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4248:1: ( rule__WORKSPACE_TAG__Group__7__Impl rule__WORKSPACE_TAG__Group__8 )
            // InternalSemver.g:4249:2: rule__WORKSPACE_TAG__Group__7__Impl rule__WORKSPACE_TAG__Group__8
            {
            pushFollow(FOLLOW_28);
            rule__WORKSPACE_TAG__Group__7__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__8();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__7"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__7__Impl"
    // InternalSemver.g:4256:1: rule__WORKSPACE_TAG__Group__7__Impl : ( RULE_LETTER_C ) ;
    public final void rule__WORKSPACE_TAG__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4260:1: ( ( RULE_LETTER_C ) )
            // InternalSemver.g:4261:1: ( RULE_LETTER_C )
            {
            // InternalSemver.g:4261:1: ( RULE_LETTER_C )
            // InternalSemver.g:4262:2: RULE_LETTER_C
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_CTerminalRuleCall_7()); 
            }
            match(input,RULE_LETTER_C,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_CTerminalRuleCall_7()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__7__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__8"
    // InternalSemver.g:4271:1: rule__WORKSPACE_TAG__Group__8 : rule__WORKSPACE_TAG__Group__8__Impl rule__WORKSPACE_TAG__Group__9 ;
    public final void rule__WORKSPACE_TAG__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4275:1: ( rule__WORKSPACE_TAG__Group__8__Impl rule__WORKSPACE_TAG__Group__9 )
            // InternalSemver.g:4276:2: rule__WORKSPACE_TAG__Group__8__Impl rule__WORKSPACE_TAG__Group__9
            {
            pushFollow(FOLLOW_8);
            rule__WORKSPACE_TAG__Group__8__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__9();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__8"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__8__Impl"
    // InternalSemver.g:4283:1: rule__WORKSPACE_TAG__Group__8__Impl : ( RULE_LETTER_E ) ;
    public final void rule__WORKSPACE_TAG__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4287:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:4288:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:4288:1: ( RULE_LETTER_E )
            // InternalSemver.g:4289:2: RULE_LETTER_E
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_ETerminalRuleCall_8()); 
            }
            match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getLETTER_ETerminalRuleCall_8()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__8__Impl"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__9"
    // InternalSemver.g:4298:1: rule__WORKSPACE_TAG__Group__9 : rule__WORKSPACE_TAG__Group__9__Impl ;
    public final void rule__WORKSPACE_TAG__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4302:1: ( rule__WORKSPACE_TAG__Group__9__Impl )
            // InternalSemver.g:4303:2: rule__WORKSPACE_TAG__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__WORKSPACE_TAG__Group__9__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__9"


    // $ANTLR start "rule__WORKSPACE_TAG__Group__9__Impl"
    // InternalSemver.g:4309:1: rule__WORKSPACE_TAG__Group__9__Impl : ( ':' ) ;
    public final void rule__WORKSPACE_TAG__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4313:1: ( ( ':' ) )
            // InternalSemver.g:4314:1: ( ':' )
            {
            // InternalSemver.g:4314:1: ( ':' )
            // InternalSemver.g:4315:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWORKSPACE_TAGAccess().getColonKeyword_9()); 
            }
            match(input,46,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWORKSPACE_TAGAccess().getColonKeyword_9()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WORKSPACE_TAG__Group__9__Impl"


    // $ANTLR start "rule__URL_PROTOCOL__Group__0"
    // InternalSemver.g:4325:1: rule__URL_PROTOCOL__Group__0 : rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 ;
    public final void rule__URL_PROTOCOL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4329:1: ( rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 )
            // InternalSemver.g:4330:2: rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1
            {
            pushFollow(FOLLOW_38);
            rule__URL_PROTOCOL__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL_PROTOCOL__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_PROTOCOL__Group__0"


    // $ANTLR start "rule__URL_PROTOCOL__Group__0__Impl"
    // InternalSemver.g:4337:1: rule__URL_PROTOCOL__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__URL_PROTOCOL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4341:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:4342:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:4342:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:4343:2: ruleLETTER_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_PROTOCOL__Group__0__Impl"


    // $ANTLR start "rule__URL_PROTOCOL__Group__1"
    // InternalSemver.g:4352:1: rule__URL_PROTOCOL__Group__1 : rule__URL_PROTOCOL__Group__1__Impl ;
    public final void rule__URL_PROTOCOL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4356:1: ( rule__URL_PROTOCOL__Group__1__Impl )
            // InternalSemver.g:4357:2: rule__URL_PROTOCOL__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URL_PROTOCOL__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_PROTOCOL__Group__1"


    // $ANTLR start "rule__URL_PROTOCOL__Group__1__Impl"
    // InternalSemver.g:4363:1: rule__URL_PROTOCOL__Group__1__Impl : ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) ;
    public final void rule__URL_PROTOCOL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4367:1: ( ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) )
            // InternalSemver.g:4368:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            {
            // InternalSemver.g:4368:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            // InternalSemver.g:4369:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            {
            // InternalSemver.g:4369:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) )
            // InternalSemver.g:4370:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4371:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            // InternalSemver.g:4371:4: rule__URL_PROTOCOL__Alternatives_1
            {
            pushFollow(FOLLOW_39);
            rule__URL_PROTOCOL__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:4374:2: ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            // InternalSemver.g:4375:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4376:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=RULE_LETTER_X && LA48_0<=RULE_LETTER_OTHER)||LA48_0==45) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalSemver.g:4376:4: rule__URL_PROTOCOL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_39);
            	    rule__URL_PROTOCOL__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_PROTOCOL__Group__1__Impl"


    // $ANTLR start "rule__URL__Group__0"
    // InternalSemver.g:4386:1: rule__URL__Group__0 : rule__URL__Group__0__Impl rule__URL__Group__1 ;
    public final void rule__URL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4390:1: ( rule__URL__Group__0__Impl rule__URL__Group__1 )
            // InternalSemver.g:4391:2: rule__URL__Group__0__Impl rule__URL__Group__1
            {
            pushFollow(FOLLOW_9);
            rule__URL__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group__0"


    // $ANTLR start "rule__URL__Group__0__Impl"
    // InternalSemver.g:4398:1: rule__URL__Group__0__Impl : ( ( rule__URL__Alternatives_0 )* ) ;
    public final void rule__URL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4402:1: ( ( ( rule__URL__Alternatives_0 )* ) )
            // InternalSemver.g:4403:1: ( ( rule__URL__Alternatives_0 )* )
            {
            // InternalSemver.g:4403:1: ( ( rule__URL__Alternatives_0 )* )
            // InternalSemver.g:4404:2: ( rule__URL__Alternatives_0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:4405:2: ( rule__URL__Alternatives_0 )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( ((LA49_0>=RULE_DIGITS && LA49_0<=RULE_LETTER_OTHER)||LA49_0==44||LA49_0==47) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalSemver.g:4405:3: rule__URL__Alternatives_0
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL__Alternatives_0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group__0__Impl"


    // $ANTLR start "rule__URL__Group__1"
    // InternalSemver.g:4413:1: rule__URL__Group__1 : rule__URL__Group__1__Impl rule__URL__Group__2 ;
    public final void rule__URL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4417:1: ( rule__URL__Group__1__Impl rule__URL__Group__2 )
            // InternalSemver.g:4418:2: rule__URL__Group__1__Impl rule__URL__Group__2
            {
            pushFollow(FOLLOW_9);
            rule__URL__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group__1"


    // $ANTLR start "rule__URL__Group__1__Impl"
    // InternalSemver.g:4425:1: rule__URL__Group__1__Impl : ( ( rule__URL__Alternatives_1 ) ) ;
    public final void rule__URL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4429:1: ( ( ( rule__URL__Alternatives_1 ) ) )
            // InternalSemver.g:4430:1: ( ( rule__URL__Alternatives_1 ) )
            {
            // InternalSemver.g:4430:1: ( ( rule__URL__Alternatives_1 ) )
            // InternalSemver.g:4431:2: ( rule__URL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4432:2: ( rule__URL__Alternatives_1 )
            // InternalSemver.g:4432:3: rule__URL__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__URL__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getAlternatives_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group__1__Impl"


    // $ANTLR start "rule__URL__Group__2"
    // InternalSemver.g:4440:1: rule__URL__Group__2 : rule__URL__Group__2__Impl ;
    public final void rule__URL__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4444:1: ( rule__URL__Group__2__Impl )
            // InternalSemver.g:4445:2: rule__URL__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URL__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group__2"


    // $ANTLR start "rule__URL__Group__2__Impl"
    // InternalSemver.g:4451:1: rule__URL__Group__2__Impl : ( ( rule__URL__Alternatives_2 )* ) ;
    public final void rule__URL__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4455:1: ( ( ( rule__URL__Alternatives_2 )* ) )
            // InternalSemver.g:4456:1: ( ( rule__URL__Alternatives_2 )* )
            {
            // InternalSemver.g:4456:1: ( ( rule__URL__Alternatives_2 )* )
            // InternalSemver.g:4457:2: ( rule__URL__Alternatives_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:4458:2: ( rule__URL__Alternatives_2 )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=RULE_DIGITS && LA50_0<=RULE_LETTER_OTHER)||(LA50_0>=41 && LA50_0<=44)||(LA50_0>=46 && LA50_0<=47)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalSemver.g:4458:3: rule__URL__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_40);
            	    rule__URL__Alternatives_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getAlternatives_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group__2__Impl"


    // $ANTLR start "rule__URL_NO_VX__Group__0"
    // InternalSemver.g:4467:1: rule__URL_NO_VX__Group__0 : rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1 ;
    public final void rule__URL_NO_VX__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4471:1: ( rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1 )
            // InternalSemver.g:4472:2: rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1
            {
            pushFollow(FOLLOW_9);
            rule__URL_NO_VX__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL_NO_VX__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__0"


    // $ANTLR start "rule__URL_NO_VX__Group__0__Impl"
    // InternalSemver.g:4479:1: rule__URL_NO_VX__Group__0__Impl : ( ( rule__URL_NO_VX__Alternatives_0 ) ) ;
    public final void rule__URL_NO_VX__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4483:1: ( ( ( rule__URL_NO_VX__Alternatives_0 ) ) )
            // InternalSemver.g:4484:1: ( ( rule__URL_NO_VX__Alternatives_0 ) )
            {
            // InternalSemver.g:4484:1: ( ( rule__URL_NO_VX__Alternatives_0 ) )
            // InternalSemver.g:4485:2: ( rule__URL_NO_VX__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:4486:2: ( rule__URL_NO_VX__Alternatives_0 )
            // InternalSemver.g:4486:3: rule__URL_NO_VX__Alternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__URL_NO_VX__Alternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_NO_VXAccess().getAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__0__Impl"


    // $ANTLR start "rule__URL_NO_VX__Group__1"
    // InternalSemver.g:4494:1: rule__URL_NO_VX__Group__1 : rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2 ;
    public final void rule__URL_NO_VX__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4498:1: ( rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2 )
            // InternalSemver.g:4499:2: rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2
            {
            pushFollow(FOLLOW_9);
            rule__URL_NO_VX__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL_NO_VX__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__1"


    // $ANTLR start "rule__URL_NO_VX__Group__1__Impl"
    // InternalSemver.g:4506:1: rule__URL_NO_VX__Group__1__Impl : ( ( rule__URL_NO_VX__Alternatives_1 )* ) ;
    public final void rule__URL_NO_VX__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4510:1: ( ( ( rule__URL_NO_VX__Alternatives_1 )* ) )
            // InternalSemver.g:4511:1: ( ( rule__URL_NO_VX__Alternatives_1 )* )
            {
            // InternalSemver.g:4511:1: ( ( rule__URL_NO_VX__Alternatives_1 )* )
            // InternalSemver.g:4512:2: ( rule__URL_NO_VX__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4513:2: ( rule__URL_NO_VX__Alternatives_1 )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( ((LA51_0>=RULE_DIGITS && LA51_0<=RULE_LETTER_OTHER)||LA51_0==44||LA51_0==47) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSemver.g:4513:3: rule__URL_NO_VX__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL_NO_VX__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_NO_VXAccess().getAlternatives_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__1__Impl"


    // $ANTLR start "rule__URL_NO_VX__Group__2"
    // InternalSemver.g:4521:1: rule__URL_NO_VX__Group__2 : rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3 ;
    public final void rule__URL_NO_VX__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4525:1: ( rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3 )
            // InternalSemver.g:4526:2: rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3
            {
            pushFollow(FOLLOW_9);
            rule__URL_NO_VX__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL_NO_VX__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__2"


    // $ANTLR start "rule__URL_NO_VX__Group__2__Impl"
    // InternalSemver.g:4533:1: rule__URL_NO_VX__Group__2__Impl : ( ( rule__URL_NO_VX__Alternatives_2 ) ) ;
    public final void rule__URL_NO_VX__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4537:1: ( ( ( rule__URL_NO_VX__Alternatives_2 ) ) )
            // InternalSemver.g:4538:1: ( ( rule__URL_NO_VX__Alternatives_2 ) )
            {
            // InternalSemver.g:4538:1: ( ( rule__URL_NO_VX__Alternatives_2 ) )
            // InternalSemver.g:4539:2: ( rule__URL_NO_VX__Alternatives_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:4540:2: ( rule__URL_NO_VX__Alternatives_2 )
            // InternalSemver.g:4540:3: rule__URL_NO_VX__Alternatives_2
            {
            pushFollow(FOLLOW_2);
            rule__URL_NO_VX__Alternatives_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_NO_VXAccess().getAlternatives_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__2__Impl"


    // $ANTLR start "rule__URL_NO_VX__Group__3"
    // InternalSemver.g:4548:1: rule__URL_NO_VX__Group__3 : rule__URL_NO_VX__Group__3__Impl ;
    public final void rule__URL_NO_VX__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4552:1: ( rule__URL_NO_VX__Group__3__Impl )
            // InternalSemver.g:4553:2: rule__URL_NO_VX__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URL_NO_VX__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__3"


    // $ANTLR start "rule__URL_NO_VX__Group__3__Impl"
    // InternalSemver.g:4559:1: rule__URL_NO_VX__Group__3__Impl : ( ( rule__URL_NO_VX__Alternatives_3 )* ) ;
    public final void rule__URL_NO_VX__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4563:1: ( ( ( rule__URL_NO_VX__Alternatives_3 )* ) )
            // InternalSemver.g:4564:1: ( ( rule__URL_NO_VX__Alternatives_3 )* )
            {
            // InternalSemver.g:4564:1: ( ( rule__URL_NO_VX__Alternatives_3 )* )
            // InternalSemver.g:4565:2: ( rule__URL_NO_VX__Alternatives_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_3()); 
            }
            // InternalSemver.g:4566:2: ( rule__URL_NO_VX__Alternatives_3 )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( ((LA52_0>=RULE_DIGITS && LA52_0<=RULE_LETTER_OTHER)||(LA52_0>=41 && LA52_0<=44)||(LA52_0>=46 && LA52_0<=47)) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalSemver.g:4566:3: rule__URL_NO_VX__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_40);
            	    rule__URL_NO_VX__Alternatives_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_NO_VXAccess().getAlternatives_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL_NO_VX__Group__3__Impl"


    // $ANTLR start "rule__TAG__Group__0"
    // InternalSemver.g:4575:1: rule__TAG__Group__0 : rule__TAG__Group__0__Impl rule__TAG__Group__1 ;
    public final void rule__TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4579:1: ( rule__TAG__Group__0__Impl rule__TAG__Group__1 )
            // InternalSemver.g:4580:2: rule__TAG__Group__0__Impl rule__TAG__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__TAG__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__TAG__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TAG__Group__0"


    // $ANTLR start "rule__TAG__Group__0__Impl"
    // InternalSemver.g:4587:1: rule__TAG__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4591:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:4592:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:4592:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:4593:2: ruleLETTER_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TAG__Group__0__Impl"


    // $ANTLR start "rule__TAG__Group__1"
    // InternalSemver.g:4602:1: rule__TAG__Group__1 : rule__TAG__Group__1__Impl ;
    public final void rule__TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4606:1: ( rule__TAG__Group__1__Impl )
            // InternalSemver.g:4607:2: rule__TAG__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TAG__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TAG__Group__1"


    // $ANTLR start "rule__TAG__Group__1__Impl"
    // InternalSemver.g:4613:1: rule__TAG__Group__1__Impl : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4617:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4618:1: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4618:1: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4619:2: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TAG__Group__1__Impl"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0"
    // InternalSemver.g:4629:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4633:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 )
            // InternalSemver.g:4634:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl"
    // InternalSemver.g:4641:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl : ( RULE_DIGITS ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4645:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:4646:1: ( RULE_DIGITS )
            {
            // InternalSemver.g:4646:1: ( RULE_DIGITS )
            // InternalSemver.g:4647:2: RULE_DIGITS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_0()); 
            }
            match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1"
    // InternalSemver.g:4656:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4660:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl )
            // InternalSemver.g:4661:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl"
    // InternalSemver.g:4667:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4671:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4672:1: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4672:1: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4673:2: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl"


    // $ANTLR start "rule__LocalPathVersionRequirement__LocalPathAssignment_1"
    // InternalSemver.g:4683:1: rule__LocalPathVersionRequirement__LocalPathAssignment_1 : ( rulePATH ) ;
    public final void rule__LocalPathVersionRequirement__LocalPathAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4687:1: ( ( rulePATH ) )
            // InternalSemver.g:4688:2: ( rulePATH )
            {
            // InternalSemver.g:4688:2: ( rulePATH )
            // InternalSemver.g:4689:3: rulePATH
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathPATHParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            rulePATH();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathPATHParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalPathVersionRequirement__LocalPathAssignment_1"


    // $ANTLR start "rule__URLVersionRequirement__ProtocolAssignment_0"
    // InternalSemver.g:4698:1: rule__URLVersionRequirement__ProtocolAssignment_0 : ( ruleURL_PROTOCOL ) ;
    public final void rule__URLVersionRequirement__ProtocolAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4702:1: ( ( ruleURL_PROTOCOL ) )
            // InternalSemver.g:4703:2: ( ruleURL_PROTOCOL )
            {
            // InternalSemver.g:4703:2: ( ruleURL_PROTOCOL )
            // InternalSemver.g:4704:3: ruleURL_PROTOCOL
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolURL_PROTOCOLParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleURL_PROTOCOL();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getProtocolURL_PROTOCOLParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__ProtocolAssignment_0"


    // $ANTLR start "rule__URLVersionRequirement__UrlAssignment_2"
    // InternalSemver.g:4713:1: rule__URLVersionRequirement__UrlAssignment_2 : ( ruleURL ) ;
    public final void rule__URLVersionRequirement__UrlAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4717:1: ( ( ruleURL ) )
            // InternalSemver.g:4718:2: ( ruleURL )
            {
            // InternalSemver.g:4718:2: ( ruleURL )
            // InternalSemver.g:4719:3: ruleURL
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleURL();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__UrlAssignment_2"


    // $ANTLR start "rule__URLVersionRequirement__VersionSpecifierAssignment_3_1"
    // InternalSemver.g:4728:1: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 : ( ruleURLVersionSpecifier ) ;
    public final void rule__URLVersionRequirement__VersionSpecifierAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4732:1: ( ( ruleURLVersionSpecifier ) )
            // InternalSemver.g:4733:2: ( ruleURLVersionSpecifier )
            {
            // InternalSemver.g:4733:2: ( ruleURLVersionSpecifier )
            // InternalSemver.g:4734:3: ruleURLVersionSpecifier
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleURLVersionSpecifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionRequirement__VersionSpecifierAssignment_3_1"


    // $ANTLR start "rule__URLVersionSpecifier__CommitISHAssignment_1_1"
    // InternalSemver.g:4743:1: rule__URLVersionSpecifier__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4747:1: ( ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
            // InternalSemver.g:4748:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            {
            // InternalSemver.g:4748:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            // InternalSemver.g:4749:3: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARS_START_WITH_DIGITSParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARS_START_WITH_DIGITSParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__CommitISHAssignment_1_1"


    // $ANTLR start "rule__URLVersionSpecifier__CommitISHAssignment_2_1"
    // InternalSemver.g:4758:1: rule__URLVersionSpecifier__CommitISHAssignment_2_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4762:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4763:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4763:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4764:3: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLVersionSpecifier__CommitISHAssignment_2_1"


    // $ANTLR start "rule__URLSemver__WithSemverTagAssignment_1"
    // InternalSemver.g:4773:1: rule__URLSemver__WithSemverTagAssignment_1 : ( ruleSEMVER_TAG ) ;
    public final void rule__URLSemver__WithSemverTagAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4777:1: ( ( ruleSEMVER_TAG ) )
            // InternalSemver.g:4778:2: ( ruleSEMVER_TAG )
            {
            // InternalSemver.g:4778:2: ( ruleSEMVER_TAG )
            // InternalSemver.g:4779:3: ruleSEMVER_TAG
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagSEMVER_TAGParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleSEMVER_TAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getWithSemverTagSEMVER_TAGParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__WithSemverTagAssignment_1"


    // $ANTLR start "rule__URLSemver__SimpleVersionAssignment_2"
    // InternalSemver.g:4788:1: rule__URLSemver__SimpleVersionAssignment_2 : ( ruleSimpleVersion ) ;
    public final void rule__URLSemver__SimpleVersionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4792:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4793:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4793:2: ( ruleSimpleVersion )
            // InternalSemver.g:4794:3: ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URLSemver__SimpleVersionAssignment_2"


    // $ANTLR start "rule__WorkspaceVersionRequirement__VersionAssignment_1_0"
    // InternalSemver.g:4803:1: rule__WorkspaceVersionRequirement__VersionAssignment_1_0 : ( ruleSimpleVersion ) ;
    public final void rule__WorkspaceVersionRequirement__VersionAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4807:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4808:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4808:2: ( ruleSimpleVersion )
            // InternalSemver.g:4809:3: ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWorkspaceVersionRequirementAccess().getVersionSimpleVersionParserRuleCall_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWorkspaceVersionRequirementAccess().getVersionSimpleVersionParserRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WorkspaceVersionRequirement__VersionAssignment_1_0"


    // $ANTLR start "rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1"
    // InternalSemver.g:4818:1: rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 : ( ruleWORKSPACE_VERSION ) ;
    public final void rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4822:1: ( ( ruleWORKSPACE_VERSION ) )
            // InternalSemver.g:4823:2: ( ruleWORKSPACE_VERSION )
            {
            // InternalSemver.g:4823:2: ( ruleWORKSPACE_VERSION )
            // InternalSemver.g:4824:3: ruleWORKSPACE_VERSION
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWorkspaceVersionRequirementAccess().getOtherVersionWORKSPACE_VERSIONParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleWORKSPACE_VERSION();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWorkspaceVersionRequirementAccess().getOtherVersionWORKSPACE_VERSIONParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1"


    // $ANTLR start "rule__GitHubVersionRequirement__GithubUrlAssignment_0"
    // InternalSemver.g:4833:1: rule__GitHubVersionRequirement__GithubUrlAssignment_0 : ( ruleURL_NO_VX ) ;
    public final void rule__GitHubVersionRequirement__GithubUrlAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4837:1: ( ( ruleURL_NO_VX ) )
            // InternalSemver.g:4838:2: ( ruleURL_NO_VX )
            {
            // InternalSemver.g:4838:2: ( ruleURL_NO_VX )
            // InternalSemver.g:4839:3: ruleURL_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURL_NO_VXParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleURL_NO_VX();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURL_NO_VXParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__GithubUrlAssignment_0"


    // $ANTLR start "rule__GitHubVersionRequirement__CommitISHAssignment_1_1"
    // InternalSemver.g:4848:1: rule__GitHubVersionRequirement__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__GitHubVersionRequirement__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4852:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4853:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4853:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4854:3: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GitHubVersionRequirement__CommitISHAssignment_1_1"


    // $ANTLR start "rule__TagVersionRequirement__TagNameAssignment"
    // InternalSemver.g:4863:1: rule__TagVersionRequirement__TagNameAssignment : ( ruleTAG ) ;
    public final void rule__TagVersionRequirement__TagNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4867:1: ( ( ruleTAG ) )
            // InternalSemver.g:4868:2: ( ruleTAG )
            {
            // InternalSemver.g:4868:2: ( ruleTAG )
            // InternalSemver.g:4869:3: ruleTAG
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTagVersionRequirementAccess().getTagNameTAGParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleTAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTagVersionRequirementAccess().getTagNameTAGParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TagVersionRequirement__TagNameAssignment"


    // $ANTLR start "rule__VersionRangeSetRequirement__RangesAssignment_1_0"
    // InternalSemver.g:4878:1: rule__VersionRangeSetRequirement__RangesAssignment_1_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4882:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4883:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4883:2: ( ruleVersionRange )
            // InternalSemver.g:4884:3: ruleVersionRange
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__RangesAssignment_1_0"


    // $ANTLR start "rule__VersionRangeSetRequirement__RangesAssignment_1_1_3"
    // InternalSemver.g:4893:1: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4897:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4898:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4898:2: ( ruleVersionRange )
            // InternalSemver.g:4899:3: ruleVersionRange
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_1_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_1_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSetRequirement__RangesAssignment_1_1_3"


    // $ANTLR start "rule__HyphenVersionRange__FromAssignment_1"
    // InternalSemver.g:4908:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4912:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4913:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4913:2: ( ruleVersionNumber )
            // InternalSemver.g:4914:3: ruleVersionNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__FromAssignment_1"


    // $ANTLR start "rule__HyphenVersionRange__ToAssignment_5"
    // InternalSemver.g:4923:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4927:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4928:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4928:2: ( ruleVersionNumber )
            // InternalSemver.g:4929:3: ruleVersionNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__ToAssignment_5"


    // $ANTLR start "rule__VersionRangeContraint__VersionConstraintsAssignment_1"
    // InternalSemver.g:4938:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4942:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4943:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4943:2: ( ruleSimpleVersion )
            // InternalSemver.g:4944:3: ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__VersionConstraintsAssignment_1"


    // $ANTLR start "rule__VersionRangeContraint__VersionConstraintsAssignment_2_1"
    // InternalSemver.g:4953:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4957:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4958:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4958:2: ( ruleSimpleVersion )
            // InternalSemver.g:4959:3: ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeContraint__VersionConstraintsAssignment_2_1"


    // $ANTLR start "rule__SimpleVersion__ComparatorsAssignment_0_0"
    // InternalSemver.g:4968:1: rule__SimpleVersion__ComparatorsAssignment_0_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4972:1: ( ( ruleVersionComparator ) )
            // InternalSemver.g:4973:2: ( ruleVersionComparator )
            {
            // InternalSemver.g:4973:2: ( ruleVersionComparator )
            // InternalSemver.g:4974:3: ruleVersionComparator
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_0_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionComparator();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__ComparatorsAssignment_0_0"


    // $ANTLR start "rule__SimpleVersion__WithLetterVAssignment_1"
    // InternalSemver.g:4983:1: rule__SimpleVersion__WithLetterVAssignment_1 : ( RULE_LETTER_V ) ;
    public final void rule__SimpleVersion__WithLetterVAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4987:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:4988:2: ( RULE_LETTER_V )
            {
            // InternalSemver.g:4988:2: ( RULE_LETTER_V )
            // InternalSemver.g:4989:3: RULE_LETTER_V
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_1_0()); 
            }
            match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__WithLetterVAssignment_1"


    // $ANTLR start "rule__SimpleVersion__NumberAssignment_2"
    // InternalSemver.g:4998:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5002:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:5003:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:5003:2: ( ruleVersionNumber )
            // InternalSemver.g:5004:3: ruleVersionNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__NumberAssignment_2"


    // $ANTLR start "rule__VersionNumber__MajorAssignment_0"
    // InternalSemver.g:5013:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5017:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:5018:2: ( ruleVersionPart )
            {
            // InternalSemver.g:5018:2: ( ruleVersionPart )
            // InternalSemver.g:5019:3: ruleVersionPart
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__MajorAssignment_0"


    // $ANTLR start "rule__VersionNumber__MinorAssignment_1_1"
    // InternalSemver.g:5028:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5032:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:5033:2: ( ruleVersionPart )
            {
            // InternalSemver.g:5033:2: ( ruleVersionPart )
            // InternalSemver.g:5034:3: ruleVersionPart
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__MinorAssignment_1_1"


    // $ANTLR start "rule__VersionNumber__PatchAssignment_1_2_1"
    // InternalSemver.g:5043:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5047:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:5048:2: ( ruleVersionPart )
            {
            // InternalSemver.g:5048:2: ( ruleVersionPart )
            // InternalSemver.g:5049:3: ruleVersionPart
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__PatchAssignment_1_2_1"


    // $ANTLR start "rule__VersionNumber__ExtendedAssignment_1_2_2_1"
    // InternalSemver.g:5058:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5062:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:5063:2: ( ruleVersionPart )
            {
            // InternalSemver.g:5063:2: ( ruleVersionPart )
            // InternalSemver.g:5064:3: ruleVersionPart
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__ExtendedAssignment_1_2_2_1"


    // $ANTLR start "rule__VersionNumber__QualifierAssignment_2"
    // InternalSemver.g:5073:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5077:1: ( ( ruleQualifier ) )
            // InternalSemver.g:5078:2: ( ruleQualifier )
            {
            // InternalSemver.g:5078:2: ( ruleQualifier )
            // InternalSemver.g:5079:3: ruleQualifier
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleQualifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__QualifierAssignment_2"


    // $ANTLR start "rule__VersionPart__WildcardAssignment_0"
    // InternalSemver.g:5088:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5092:1: ( ( ruleWILDCARD ) )
            // InternalSemver.g:5093:2: ( ruleWILDCARD )
            {
            // InternalSemver.g:5093:2: ( ruleWILDCARD )
            // InternalSemver.g:5094:3: ruleWILDCARD
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleWILDCARD();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionPart__WildcardAssignment_0"


    // $ANTLR start "rule__VersionPart__NumberRawAssignment_1"
    // InternalSemver.g:5103:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5107:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:5108:2: ( RULE_DIGITS )
            {
            // InternalSemver.g:5108:2: ( RULE_DIGITS )
            // InternalSemver.g:5109:3: RULE_DIGITS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0()); 
            }
            match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionPart__NumberRawAssignment_1"


    // $ANTLR start "rule__Qualifier__PreReleaseAssignment_0_1"
    // InternalSemver.g:5118:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5122:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:5123:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:5123:2: ( ruleQualifierTag )
            // InternalSemver.g:5124:3: ruleQualifierTag
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__PreReleaseAssignment_0_1"


    // $ANTLR start "rule__Qualifier__BuildMetadataAssignment_0_2_1"
    // InternalSemver.g:5133:1: rule__Qualifier__BuildMetadataAssignment_0_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_0_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5137:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:5138:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:5138:2: ( ruleQualifierTag )
            // InternalSemver.g:5139:3: ruleQualifierTag
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_0_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_0_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__BuildMetadataAssignment_0_2_1"


    // $ANTLR start "rule__Qualifier__BuildMetadataAssignment_1_1"
    // InternalSemver.g:5148:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5152:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:5153:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:5153:2: ( ruleQualifierTag )
            // InternalSemver.g:5154:3: ruleQualifierTag
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Qualifier__BuildMetadataAssignment_1_1"


    // $ANTLR start "rule__QualifierTag__PartsAssignment_0"
    // InternalSemver.g:5163:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5167:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:5168:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:5168:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:5169:3: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__PartsAssignment_0"


    // $ANTLR start "rule__QualifierTag__PartsAssignment_1_1"
    // InternalSemver.g:5178:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:5182:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:5183:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:5183:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:5184:3: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifierTag__PartsAssignment_1_1"

    // $ANTLR start synpred5_InternalSemver
    public final void synpred5_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:907:2: ( ( ( ruleLocalPathVersionRequirement ) ) )
        // InternalSemver.g:907:2: ( ( ruleLocalPathVersionRequirement ) )
        {
        // InternalSemver.g:907:2: ( ( ruleLocalPathVersionRequirement ) )
        // InternalSemver.g:908:3: ( ruleLocalPathVersionRequirement )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
        }
        // InternalSemver.g:909:3: ( ruleLocalPathVersionRequirement )
        // InternalSemver.g:909:4: ruleLocalPathVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleLocalPathVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred5_InternalSemver

    // $ANTLR start synpred6_InternalSemver
    public final void synpred6_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:928:2: ( ( ( ruleURLVersionRequirement ) ) )
        // InternalSemver.g:928:2: ( ( ruleURLVersionRequirement ) )
        {
        // InternalSemver.g:928:2: ( ( ruleURLVersionRequirement ) )
        // InternalSemver.g:929:3: ( ruleURLVersionRequirement )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0()); 
        }
        // InternalSemver.g:930:3: ( ruleURLVersionRequirement )
        // InternalSemver.g:930:4: ruleURLVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleURLVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred6_InternalSemver

    // $ANTLR start synpred7_InternalSemver
    public final void synpred7_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:949:2: ( ( ( ruleWorkspaceVersionRequirement ) ) )
        // InternalSemver.g:949:2: ( ( ruleWorkspaceVersionRequirement ) )
        {
        // InternalSemver.g:949:2: ( ( ruleWorkspaceVersionRequirement ) )
        // InternalSemver.g:950:3: ( ruleWorkspaceVersionRequirement )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getWorkspaceVersionRequirementParserRuleCall_1_0_1_1_0()); 
        }
        // InternalSemver.g:951:3: ( ruleWorkspaceVersionRequirement )
        // InternalSemver.g:951:4: ruleWorkspaceVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleWorkspaceVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred7_InternalSemver

    // $ANTLR start synpred8_InternalSemver
    public final void synpred8_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:955:2: ( ( ruleGitHubVersionRequirement ) )
        // InternalSemver.g:955:2: ( ruleGitHubVersionRequirement )
        {
        // InternalSemver.g:955:2: ( ruleGitHubVersionRequirement )
        // InternalSemver.g:956:3: ruleGitHubVersionRequirement
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1_1()); 
        }
        pushFollow(FOLLOW_2);
        ruleGitHubVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred8_InternalSemver

    // $ANTLR start synpred9_InternalSemver
    public final void synpred9_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:976:2: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) )
        // InternalSemver.g:976:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        {
        // InternalSemver.g:976:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        // InternalSemver.g:977:3: ( rule__URLVersionSpecifier__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
        }
        // InternalSemver.g:978:3: ( rule__URLVersionSpecifier__Group_0__0 )
        // InternalSemver.g:978:4: rule__URLVersionSpecifier__Group_0__0
        {
        pushFollow(FOLLOW_2);
        rule__URLVersionSpecifier__Group_0__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred9_InternalSemver

    // $ANTLR start synpred11_InternalSemver
    public final void synpred11_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:1003:2: ( ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) ) )
        // InternalSemver.g:1003:2: ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) )
        {
        // InternalSemver.g:1003:2: ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) )
        // InternalSemver.g:1004:3: ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getWorkspaceVersionRequirementAccess().getVersionAssignment_1_0()); 
        }
        // InternalSemver.g:1005:3: ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 )
        // InternalSemver.g:1005:4: rule__WorkspaceVersionRequirement__VersionAssignment_1_0
        {
        pushFollow(FOLLOW_2);
        rule__WorkspaceVersionRequirement__VersionAssignment_1_0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred11_InternalSemver

    // Delegated rules

    public final boolean synpred6_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalSemver_fragment(); // can never throw exception
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
    public final boolean synpred7_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalSemver() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalSemver_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA10 dfa10 = new DFA10(this);
    static final String dfa_1s = "\36\uffff";
    static final String dfa_2s = "\3\uffff\4\2\27\uffff";
    static final String dfa_3s = "\1\10\1\5\1\uffff\4\5\26\0\1\uffff";
    static final String dfa_4s = "\2\57\1\uffff\3\57\1\67\26\0\1\uffff";
    static final String dfa_5s = "\2\uffff\1\2\32\uffff\1\1";
    static final String dfa_6s = "\7\uffff\1\0\1\4\1\22\1\6\1\12\1\23\1\11\1\21\1\24\1\7\1\5\1\16\1\1\1\14\1\13\1\25\1\10\1\20\1\17\1\2\1\15\1\3\1\uffff}>";
    static final String[] dfa_7s = {
            "\3\2\1\1\12\2\26\uffff\1\2\2\uffff\1\2",
            "\7\2\1\3\11\2\23\uffff\7\2",
            "",
            "\11\2\1\4\10\2\22\uffff\7\2",
            "\5\2\1\5\14\2\22\uffff\7\2",
            "\22\2\22\uffff\5\2\1\6\1\2",
            "\1\14\1\16\1\15\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\2\22\uffff\1\7\1\10\1\11\1\12\1\uffff\1\2\1\13\7\uffff\1\2",
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

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "902:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_7 = input.LA(1);

                         
                        int index5_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_19 = input.LA(1);

                         
                        int index5_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_19);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_26 = input.LA(1);

                         
                        int index5_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_26);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_28 = input.LA(1);

                         
                        int index5_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_28);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA5_8 = input.LA(1);

                         
                        int index5_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_8);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA5_17 = input.LA(1);

                         
                        int index5_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_17);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA5_10 = input.LA(1);

                         
                        int index5_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_10);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA5_16 = input.LA(1);

                         
                        int index5_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_16);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA5_23 = input.LA(1);

                         
                        int index5_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_23);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA5_13 = input.LA(1);

                         
                        int index5_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_13);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA5_11 = input.LA(1);

                         
                        int index5_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA5_21 = input.LA(1);

                         
                        int index5_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_21);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA5_20 = input.LA(1);

                         
                        int index5_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_20);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA5_27 = input.LA(1);

                         
                        int index5_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_27);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA5_18 = input.LA(1);

                         
                        int index5_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_18);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA5_25 = input.LA(1);

                         
                        int index5_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_25);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA5_24 = input.LA(1);

                         
                        int index5_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_24);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA5_14 = input.LA(1);

                         
                        int index5_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_14);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA5_9 = input.LA(1);

                         
                        int index5_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_9);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA5_12 = input.LA(1);

                         
                        int index5_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_12);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA5_15 = input.LA(1);

                         
                        int index5_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_15);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA5_22 = input.LA(1);

                         
                        int index5_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_22);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 5, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\155\uffff";
    static final String dfa_9s = "\20\uffff\20\17\1\uffff\52\17\4\uffff\32\17\4\uffff";
    static final String dfa_10s = "\1\10\16\5\1\uffff\20\5\1\uffff\52\5\4\0\4\5\26\4\4\0";
    static final String dfa_11s = "\17\57\1\uffff\20\57\1\uffff\1\57\1\67\21\57\1\67\1\57\1\67\1\57\23\67\4\0\4\57\26\67\4\0";
    static final String dfa_12s = "\17\uffff\1\2\20\uffff\1\1\114\uffff";
    static final String dfa_13s = "\113\uffff\1\2\1\4\1\5\1\6\32\uffff\1\3\1\7\1\0\1\1}>";
    static final String[] dfa_14s = {
            "\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\26\uffff\1\17\2\uffff\1\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\41\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "\1\17\1\21\1\20\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\23\uffff\4\17\1\40\2\17",
            "",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\63\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\22\17\22\uffff\1\64\3\17\1\uffff\2\17\7\uffff\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\65\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\22\17\22\uffff\1\66\3\17\1\uffff\2\17\7\uffff\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\67\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\117\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\72\1\74\1\73\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112\1\17\22\uffff\1\113\1\114\1\116\1\70\1\uffff\1\115\1\71\7\uffff\1\17",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\17\1\44\1\43\1\120\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\121\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\122\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\42\1\17",
            "\1\17\1\44\1\43\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\17\22\uffff\4\17\1\40\1\123\1\17",
            "\23\17\22\uffff\1\124\3\17\1\uffff\12\17",
            "\23\17\22\uffff\1\125\3\17\1\uffff\12\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
            "\1\17\1\130\1\132\1\131\1\133\1\134\1\135\1\136\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\17\22\uffff\1\151\1\152\1\154\1\126\1\uffff\1\153\1\127\10\17",
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

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "923:1: rule__NPMVersionRequirement__Alternatives_1_0_1 : ( ( ( ruleURLVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1_1 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_107 = input.LA(1);

                         
                        int index6_107 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_107);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA6_108 = input.LA(1);

                         
                        int index6_108 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_108);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA6_75 = input.LA(1);

                         
                        int index6_75 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_75);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA6_105 = input.LA(1);

                         
                        int index6_105 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_105);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA6_76 = input.LA(1);

                         
                        int index6_76 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_76);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA6_77 = input.LA(1);

                         
                        int index6_77 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_77);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA6_78 = input.LA(1);

                         
                        int index6_78 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_78);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA6_106 = input.LA(1);

                         
                        int index6_106 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 32;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index6_106);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\126\uffff";
    static final String dfa_16s = "\20\uffff\45\65\1\uffff\7\65\1\2\30\uffff";
    static final String dfa_17s = "\1\10\1\5\1\uffff\62\5\1\uffff\7\5\1\4\1\uffff\27\0";
    static final String dfa_18s = "\2\57\1\uffff\62\57\1\uffff\7\57\1\67\1\uffff\27\0";
    static final String dfa_19s = "\2\uffff\1\2\62\uffff\1\3\10\uffff\1\1\27\uffff";
    static final String dfa_20s = "\77\uffff\1\23\1\0\1\24\1\4\1\12\1\21\1\1\1\7\1\25\1\5\1\13\1\22\1\2\1\10\1\15\1\26\1\6\1\14\1\17\1\3\1\11\1\16\1\20}>";
    static final String[] dfa_21s = {
            "\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\1\1\17\26\uffff\1\2\2\uffff\1\2",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\21\1\23\1\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\42\1\35\1\36\1\37\1\40\1\41\23\uffff\4\2\1\uffff\1\2\1\20",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\66\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\67\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\70\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\71\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\72\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\73\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\74\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\2\1\43",
            "\1\44\1\46\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\22\uffff\4\2\1\uffff\1\75\1\43",
            "\1\76\1\101\1\100\1\77\1\110\1\111\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\2\22\uffff\1\102\1\103\1\105\1\106\1\uffff\1\104\1\107\7\76\1\2",
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
            "\1\uffff"
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
            return "944:1: rule__NPMVersionRequirement__Alternatives_1_0_1_1 : ( ( ( ruleWorkspaceVersionRequirement ) ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_64 = input.LA(1);

                         
                        int index7_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_64);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_69 = input.LA(1);

                         
                        int index7_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_69);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA7_75 = input.LA(1);

                         
                        int index7_75 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_75);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA7_82 = input.LA(1);

                         
                        int index7_82 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_82);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA7_66 = input.LA(1);

                         
                        int index7_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_66);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA7_72 = input.LA(1);

                         
                        int index7_72 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_72);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA7_79 = input.LA(1);

                         
                        int index7_79 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_79);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA7_70 = input.LA(1);

                         
                        int index7_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_70);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA7_76 = input.LA(1);

                         
                        int index7_76 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_76);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA7_83 = input.LA(1);

                         
                        int index7_83 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_83);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA7_67 = input.LA(1);

                         
                        int index7_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_67);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA7_73 = input.LA(1);

                         
                        int index7_73 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_73);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA7_80 = input.LA(1);

                         
                        int index7_80 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_80);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA7_77 = input.LA(1);

                         
                        int index7_77 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_77);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA7_84 = input.LA(1);

                         
                        int index7_84 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_84);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA7_81 = input.LA(1);

                         
                        int index7_81 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_81);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA7_85 = input.LA(1);

                         
                        int index7_85 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_85);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA7_68 = input.LA(1);

                         
                        int index7_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_68);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA7_74 = input.LA(1);

                         
                        int index7_74 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_74);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA7_63 = input.LA(1);

                         
                        int index7_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_63);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA7_65 = input.LA(1);

                         
                        int index7_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_65);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA7_71 = input.LA(1);

                         
                        int index7_71 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_71);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA7_78 = input.LA(1);

                         
                        int index7_78 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 62;}

                        else if ( (synpred8_InternalSemver()) ) {s = 2;}

                         
                        input.seek(index7_78);
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
    static final String dfa_22s = "\17\uffff";
    static final String dfa_23s = "\1\uffff\1\6\1\uffff\1\6\3\uffff\1\6\3\uffff\4\6";
    static final String dfa_24s = "\1\4\1\5\1\uffff\1\4\1\0\1\5\1\uffff\1\5\2\0\1\uffff\4\5";
    static final String dfa_25s = "\1\66\1\57\1\uffff\1\57\1\0\1\57\1\uffff\1\57\2\0\1\uffff\4\57";
    static final String dfa_26s = "\2\uffff\1\1\3\uffff\1\3\3\uffff\1\2\4\uffff";
    static final String dfa_27s = "\4\uffff\1\1\1\0\2\uffff\1\3\1\2\5\uffff}>";
    static final String[] dfa_28s = {
            "\1\2\1\5\1\4\1\3\13\6\1\1\2\6\31\uffff\1\6\7\2",
            "\5\6\1\7\14\6\30\uffff\1\6",
            "",
            "\1\2\1\10\1\11\20\6\30\uffff\1\6",
            "\1\uffff",
            "\21\12\31\uffff\1\12",
            "",
            "\12\6\1\13\7\6\30\uffff\1\6",
            "\1\uffff",
            "\1\uffff",
            "",
            "\2\6\1\14\17\6\30\uffff\1\6",
            "\5\6\1\15\14\6\30\uffff\1\6",
            "\15\6\1\16\4\6\30\uffff\1\6",
            "\22\6\27\uffff\1\2\1\6"
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
            return "971:1: rule__URLVersionSpecifier__Alternatives : ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_5 = input.LA(1);

                         
                        int index8_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA8_5>=RULE_DIGITS && LA8_5<=RULE_LETTER_OTHER)||LA8_5==47) ) {s = 10;}

                        else if ( (synpred9_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index8_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_4 = input.LA(1);

                         
                        int index8_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index8_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_9 = input.LA(1);

                         
                        int index8_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index8_9);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_8 = input.LA(1);

                         
                        int index8_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index8_8);
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
    static final String dfa_29s = "\32\uffff";
    static final String dfa_30s = "\1\uffff\10\14\4\uffff\7\14\2\uffff\1\14\1\uffff\1\14\1\uffff";
    static final String dfa_31s = "\11\4\3\0\1\uffff\7\4\2\0\1\4\1\0\1\4\1\uffff";
    static final String dfa_32s = "\11\66\3\0\1\uffff\7\66\2\0\1\66\1\0\1\66\1\uffff";
    static final String dfa_33s = "\14\uffff\1\2\14\uffff\1\1";
    static final String dfa_34s = "\11\uffff\1\4\1\5\1\2\10\uffff\1\0\1\1\1\uffff\1\3\2\uffff}>";
    static final String[] dfa_35s = {
            "\1\12\1\13\1\11\1\10\16\14\23\uffff\4\14\1\uffff\2\14\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\20\14\22\uffff\4\14\1\uffff\11\14",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\24\1\25\1\27\1\26\16\14\1\30\22\uffff\4\14\1\uffff\2\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\uffff",
            "\1\uffff",
            "\1\24\1\25\1\27\20\14\22\uffff\4\14\1\uffff\11\14",
            "\1\uffff",
            "\4\31\50\uffff\7\31",
            ""
    };

    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[][] dfa_35 = unpackEncodedStringArray(dfa_35s);

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "998:1: rule__WorkspaceVersionRequirement__Alternatives_1 : ( ( ( rule__WorkspaceVersionRequirement__VersionAssignment_1_0 ) ) | ( ( rule__WorkspaceVersionRequirement__OtherVersionAssignment_1_1 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_20 = input.LA(1);

                         
                        int index9_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_InternalSemver()) ) {s = 25;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index9_20);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_21 = input.LA(1);

                         
                        int index9_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_InternalSemver()) ) {s = 25;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index9_21);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA9_11 = input.LA(1);

                         
                        int index9_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_InternalSemver()) ) {s = 25;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index9_11);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA9_23 = input.LA(1);

                         
                        int index9_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_InternalSemver()) ) {s = 25;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index9_23);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA9_9 = input.LA(1);

                         
                        int index9_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_InternalSemver()) ) {s = 25;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index9_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA9_10 = input.LA(1);

                         
                        int index9_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_InternalSemver()) ) {s = 25;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index9_10);
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
    static final String dfa_36s = "\u00f1\uffff";
    static final String dfa_37s = "\2\uffff\3\1\3\uffff\50\1\2\uffff\22\1\2\uffff\22\1\1\uffff\71\1\1\uffff\44\1\1\uffff\71\1";
    static final String dfa_38s = "\1\4\1\uffff\3\26\1\4\2\5\1\4\3\26\44\5\1\uffff\1\4\47\5\3\26\66\5\1\4\67\5\3\26\44\5";
    static final String dfa_39s = "\1\66\1\uffff\3\70\1\6\2\57\50\70\1\uffff\1\6\22\70\2\57\22\70\1\57\71\70\1\6\44\70\1\57\71\70";
    static final String dfa_40s = "\1\uffff\1\1\56\uffff\1\2\u00c0\uffff";
    static final String dfa_41s = "\u00f1\uffff}>";
    static final String[] dfa_42s = {
            "\1\3\1\4\1\2\1\1\50\uffff\7\1",
            "",
            "\1\10\23\uffff\1\5\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\5\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\5\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\12\1\13\1\11",
            "\1\15\1\17\1\16\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\31\uffff\1\14",
            "\1\37\1\41\1\40\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\31\uffff\1\36",
            "\4\1\47\uffff\1\60\7\1\1\uffff\1\1",
            "\1\10\23\uffff\1\61\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\61\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\61\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "",
            "\1\132\1\133\1\131",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\63\1\65\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102\1\103\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\62\10\uffff\1\1",
            "\1\135\1\137\1\136\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\1\151\1\152\1\153\1\154\1\155\31\uffff\1\134",
            "\1\157\1\161\1\160\1\162\1\163\1\164\1\165\1\166\1\167\1\170\1\171\1\172\1\173\1\174\1\175\1\176\1\177\31\uffff\1\156",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\107\1\111\1\110\1\112\1\113\1\114\1\115\1\116\1\117\1\120\1\121\1\122\1\123\1\124\1\125\1\126\1\127\1\10\23\uffff\1\130\4\uffff\1\106\10\uffff\1\1",
            "\1\u0081\1\u0083\1\u0082\1\u0084\1\u0085\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\u008f\1\u0090\1\u0091\31\uffff\1\u0080",
            "\1\10\23\uffff\1\u0092\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\u0092\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\u0092\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00cb\1\u00cc\1\u00ca",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u0094\1\u0096\1\u0095\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\1\u009e\1\u009f\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\10\23\uffff\1\104\2\uffff\1\105\1\uffff\1\u0093\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00a6\1\u00a8\1\u00a7\1\u00a9\1\u00aa\1\u00ab\1\u00ac\1\u00ad\1\u00ae\1\u00af\1\u00b0\1\u00b1\1\u00b2\1\u00b3\1\u00b4\1\u00b5\1\u00b6\1\10\23\uffff\1\u00b7\4\uffff\1\u00a5\10\uffff\1\1",
            "\1\u00ce\1\u00d0\1\u00cf\1\u00d1\1\u00d2\1\u00d3\1\u00d4\1\u00d5\1\u00d6\1\u00d7\1\u00d8\1\u00d9\1\u00da\1\u00db\1\u00dc\1\u00dd\1\u00de\31\uffff\1\u00cd",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\u00b9\1\u00bb\1\u00ba\1\u00bc\1\u00bd\1\u00be\1\u00bf\1\u00c0\1\u00c1\1\u00c2\1\u00c3\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\u00c8\1\u00c9\1\10\23\uffff\1\130\4\uffff\1\u00b8\10\uffff\1\1",
            "\1\10\23\uffff\1\u0092\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\u0092\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\10\23\uffff\1\u0092\2\uffff\1\7\1\uffff\1\6\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1",
            "\1\u00e0\1\u00e2\1\u00e1\1\u00e3\1\u00e4\1\u00e5\1\u00e6\1\u00e7\1\u00e8\1\u00e9\1\u00ea\1\u00eb\1\u00ec\1\u00ed\1\u00ee\1\u00ef\1\u00f0\1\10\23\uffff\1\u00b7\4\uffff\1\u00df\10\uffff\1\1"
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = dfa_36;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "1019:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x00009E00003FFFE2L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x007FDE00003FFFF2L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x007F0000000000F0L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x00009E00003FFFE0L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000DE00003FFFE0L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x007F9E00003FFFF0L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x007F0000000800F0L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x007FDE00003FFFF0L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0100000000400000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0100000000400002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x007F0000004000F0L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x007F000000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000A40000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000BE00003FFFE0L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000BE00003FFFE2L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000DE00003FFFE2L});

}