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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_DIGITS", "RULE_LETTER_V", "RULE_LETTER_X", "RULE_LETTER_NO_VX", "RULE_ASTERIX", "RULE_WS", "RULE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'+'", "'/'", "'.'", "':'", "'@'", "'v'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'://'", "'#'", "'semver:'", "'file:'", "'||'", "'-'"
    };
    public static final int RULE_WHITESPACE_FRAGMENT=11;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=12;
    public static final int RULE_EOL=13;
    public static final int RULE_DIGIT=10;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=24;
    public static final int RULE_ZWNJ=18;
    public static final int T__29=29;
    public static final int RULE_ASTERIX=8;
    public static final int RULE_LETTER_NO_VX=7;
    public static final int RULE_ML_COMMENT_FRAGMENT=23;
    public static final int RULE_DIGITS=4;
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
    public static final int RULE_WS=9;
    public static final int RULE_BOM=19;
    public static final int RULE_LETTER_V=5;
    public static final int RULE_LETTER_X=6;
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


    // $ANTLR start "entryRuleURLVersionRequirement"
    // InternalSemver.g:86:1: entryRuleURLVersionRequirement : ruleURLVersionRequirement EOF ;
    public final void entryRuleURLVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:87:1: ( ruleURLVersionRequirement EOF )
            // InternalSemver.g:88:1: ruleURLVersionRequirement EOF
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
    // InternalSemver.g:95:1: ruleURLVersionRequirement : ( ( rule__URLVersionRequirement__Group__0 ) ) ;
    public final void ruleURLVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:99:2: ( ( ( rule__URLVersionRequirement__Group__0 ) ) )
            // InternalSemver.g:100:2: ( ( rule__URLVersionRequirement__Group__0 ) )
            {
            // InternalSemver.g:100:2: ( ( rule__URLVersionRequirement__Group__0 ) )
            // InternalSemver.g:101:3: ( rule__URLVersionRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:102:3: ( rule__URLVersionRequirement__Group__0 )
            // InternalSemver.g:102:4: rule__URLVersionRequirement__Group__0
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
    // InternalSemver.g:111:1: entryRuleURLVersionSpecifier : ruleURLVersionSpecifier EOF ;
    public final void entryRuleURLVersionSpecifier() throws RecognitionException {
        try {
            // InternalSemver.g:112:1: ( ruleURLVersionSpecifier EOF )
            // InternalSemver.g:113:1: ruleURLVersionSpecifier EOF
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
    // InternalSemver.g:120:1: ruleURLVersionSpecifier : ( ( rule__URLVersionSpecifier__Alternatives ) ) ;
    public final void ruleURLVersionSpecifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:124:2: ( ( ( rule__URLVersionSpecifier__Alternatives ) ) )
            // InternalSemver.g:125:2: ( ( rule__URLVersionSpecifier__Alternatives ) )
            {
            // InternalSemver.g:125:2: ( ( rule__URLVersionSpecifier__Alternatives ) )
            // InternalSemver.g:126:3: ( rule__URLVersionSpecifier__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getAlternatives()); 
            }
            // InternalSemver.g:127:3: ( rule__URLVersionSpecifier__Alternatives )
            // InternalSemver.g:127:4: rule__URLVersionSpecifier__Alternatives
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
    // InternalSemver.g:136:1: entryRuleURLSemver : ruleURLSemver EOF ;
    public final void entryRuleURLSemver() throws RecognitionException {
        try {
            // InternalSemver.g:137:1: ( ruleURLSemver EOF )
            // InternalSemver.g:138:1: ruleURLSemver EOF
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
    // InternalSemver.g:145:1: ruleURLSemver : ( ( rule__URLSemver__Group__0 ) ) ;
    public final void ruleURLSemver() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:149:2: ( ( ( rule__URLSemver__Group__0 ) ) )
            // InternalSemver.g:150:2: ( ( rule__URLSemver__Group__0 ) )
            {
            // InternalSemver.g:150:2: ( ( rule__URLSemver__Group__0 ) )
            // InternalSemver.g:151:3: ( rule__URLSemver__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getGroup()); 
            }
            // InternalSemver.g:152:3: ( rule__URLSemver__Group__0 )
            // InternalSemver.g:152:4: rule__URLSemver__Group__0
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


    // $ANTLR start "entryRuleURLCommitISH"
    // InternalSemver.g:161:1: entryRuleURLCommitISH : ruleURLCommitISH EOF ;
    public final void entryRuleURLCommitISH() throws RecognitionException {
        try {
            // InternalSemver.g:162:1: ( ruleURLCommitISH EOF )
            // InternalSemver.g:163:1: ruleURLCommitISH EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLCommitISHRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleURLCommitISH();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLCommitISHRule()); 
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
    // $ANTLR end "entryRuleURLCommitISH"


    // $ANTLR start "ruleURLCommitISH"
    // InternalSemver.g:170:1: ruleURLCommitISH : ( ( rule__URLCommitISH__CommitISHAssignment ) ) ;
    public final void ruleURLCommitISH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:174:2: ( ( ( rule__URLCommitISH__CommitISHAssignment ) ) )
            // InternalSemver.g:175:2: ( ( rule__URLCommitISH__CommitISHAssignment ) )
            {
            // InternalSemver.g:175:2: ( ( rule__URLCommitISH__CommitISHAssignment ) )
            // InternalSemver.g:176:3: ( rule__URLCommitISH__CommitISHAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLCommitISHAccess().getCommitISHAssignment()); 
            }
            // InternalSemver.g:177:3: ( rule__URLCommitISH__CommitISHAssignment )
            // InternalSemver.g:177:4: rule__URLCommitISH__CommitISHAssignment
            {
            pushFollow(FOLLOW_2);
            rule__URLCommitISH__CommitISHAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLCommitISHAccess().getCommitISHAssignment()); 
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
    // $ANTLR end "ruleURLCommitISH"


    // $ANTLR start "entryRuleTagVersionRequirement"
    // InternalSemver.g:186:1: entryRuleTagVersionRequirement : ruleTagVersionRequirement EOF ;
    public final void entryRuleTagVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:187:1: ( ruleTagVersionRequirement EOF )
            // InternalSemver.g:188:1: ruleTagVersionRequirement EOF
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
    // InternalSemver.g:195:1: ruleTagVersionRequirement : ( ( rule__TagVersionRequirement__TagNameAssignment ) ) ;
    public final void ruleTagVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:199:2: ( ( ( rule__TagVersionRequirement__TagNameAssignment ) ) )
            // InternalSemver.g:200:2: ( ( rule__TagVersionRequirement__TagNameAssignment ) )
            {
            // InternalSemver.g:200:2: ( ( rule__TagVersionRequirement__TagNameAssignment ) )
            // InternalSemver.g:201:3: ( rule__TagVersionRequirement__TagNameAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTagVersionRequirementAccess().getTagNameAssignment()); 
            }
            // InternalSemver.g:202:3: ( rule__TagVersionRequirement__TagNameAssignment )
            // InternalSemver.g:202:4: rule__TagVersionRequirement__TagNameAssignment
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


    // $ANTLR start "entryRuleLocalPathVersionRequirement"
    // InternalSemver.g:236:1: entryRuleLocalPathVersionRequirement : ruleLocalPathVersionRequirement EOF ;
    public final void entryRuleLocalPathVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:237:1: ( ruleLocalPathVersionRequirement EOF )
            // InternalSemver.g:238:1: ruleLocalPathVersionRequirement EOF
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
    // InternalSemver.g:245:1: ruleLocalPathVersionRequirement : ( ( rule__LocalPathVersionRequirement__Group__0 ) ) ;
    public final void ruleLocalPathVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:249:2: ( ( ( rule__LocalPathVersionRequirement__Group__0 ) ) )
            // InternalSemver.g:250:2: ( ( rule__LocalPathVersionRequirement__Group__0 ) )
            {
            // InternalSemver.g:250:2: ( ( rule__LocalPathVersionRequirement__Group__0 ) )
            // InternalSemver.g:251:3: ( rule__LocalPathVersionRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:252:3: ( rule__LocalPathVersionRequirement__Group__0 )
            // InternalSemver.g:252:4: rule__LocalPathVersionRequirement__Group__0
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


    // $ANTLR start "entryRuleURL_PROTOCOL"
    // InternalSemver.g:486:1: entryRuleURL_PROTOCOL : ruleURL_PROTOCOL EOF ;
    public final void entryRuleURL_PROTOCOL() throws RecognitionException {
        try {
            // InternalSemver.g:487:1: ( ruleURL_PROTOCOL EOF )
            // InternalSemver.g:488:1: ruleURL_PROTOCOL EOF
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
    // InternalSemver.g:495:1: ruleURL_PROTOCOL : ( ( ( rule__URL_PROTOCOL__Alternatives ) ) ( ( rule__URL_PROTOCOL__Alternatives )* ) ) ;
    public final void ruleURL_PROTOCOL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:499:2: ( ( ( ( rule__URL_PROTOCOL__Alternatives ) ) ( ( rule__URL_PROTOCOL__Alternatives )* ) ) )
            // InternalSemver.g:500:2: ( ( ( rule__URL_PROTOCOL__Alternatives ) ) ( ( rule__URL_PROTOCOL__Alternatives )* ) )
            {
            // InternalSemver.g:500:2: ( ( ( rule__URL_PROTOCOL__Alternatives ) ) ( ( rule__URL_PROTOCOL__Alternatives )* ) )
            // InternalSemver.g:501:3: ( ( rule__URL_PROTOCOL__Alternatives ) ) ( ( rule__URL_PROTOCOL__Alternatives )* )
            {
            // InternalSemver.g:501:3: ( ( rule__URL_PROTOCOL__Alternatives ) )
            // InternalSemver.g:502:4: ( rule__URL_PROTOCOL__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives()); 
            }
            // InternalSemver.g:503:4: ( rule__URL_PROTOCOL__Alternatives )
            // InternalSemver.g:503:5: rule__URL_PROTOCOL__Alternatives
            {
            pushFollow(FOLLOW_3);
            rule__URL_PROTOCOL__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives()); 
            }

            }

            // InternalSemver.g:506:3: ( ( rule__URL_PROTOCOL__Alternatives )* )
            // InternalSemver.g:507:4: ( rule__URL_PROTOCOL__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives()); 
            }
            // InternalSemver.g:508:4: ( rule__URL_PROTOCOL__Alternatives )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_LETTER_V && LA1_0<=RULE_LETTER_NO_VX)||LA1_0==29) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSemver.g:508:5: rule__URL_PROTOCOL__Alternatives
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL_PROTOCOL__Alternatives();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives()); 
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
    // $ANTLR end "ruleURL_PROTOCOL"


    // $ANTLR start "entryRuleTAG"
    // InternalSemver.g:518:1: entryRuleTAG : ruleTAG EOF ;
    public final void entryRuleTAG() throws RecognitionException {
        try {
            // InternalSemver.g:519:1: ( ruleTAG EOF )
            // InternalSemver.g:520:1: ruleTAG EOF
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
    // InternalSemver.g:527:1: ruleTAG : ( ( rule__TAG__Group__0 ) ) ;
    public final void ruleTAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:531:2: ( ( ( rule__TAG__Group__0 ) ) )
            // InternalSemver.g:532:2: ( ( rule__TAG__Group__0 ) )
            {
            // InternalSemver.g:532:2: ( ( rule__TAG__Group__0 ) )
            // InternalSemver.g:533:3: ( rule__TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getGroup()); 
            }
            // InternalSemver.g:534:3: ( rule__TAG__Group__0 )
            // InternalSemver.g:534:4: rule__TAG__Group__0
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


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:543:1: entryRulePATH : rulePATH EOF ;
    public final void entryRulePATH() throws RecognitionException {
        try {
            // InternalSemver.g:544:1: ( rulePATH EOF )
            // InternalSemver.g:545:1: rulePATH EOF
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
    // InternalSemver.g:552:1: rulePATH : ( ( rule__PATH__Group__0 ) ) ;
    public final void rulePATH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:556:2: ( ( ( rule__PATH__Group__0 ) ) )
            // InternalSemver.g:557:2: ( ( rule__PATH__Group__0 ) )
            {
            // InternalSemver.g:557:2: ( ( rule__PATH__Group__0 ) )
            // InternalSemver.g:558:3: ( rule__PATH__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getGroup()); 
            }
            // InternalSemver.g:559:3: ( rule__PATH__Group__0 )
            // InternalSemver.g:559:4: rule__PATH__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PATH__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getGroup()); 
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


    // $ANTLR start "entryRuleURL"
    // InternalSemver.g:568:1: entryRuleURL : ruleURL EOF ;
    public final void entryRuleURL() throws RecognitionException {
        try {
            // InternalSemver.g:569:1: ( ruleURL EOF )
            // InternalSemver.g:570:1: ruleURL EOF
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
    // InternalSemver.g:577:1: ruleURL : ( ( rule__URL__Group__0 ) ) ;
    public final void ruleURL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:581:2: ( ( ( rule__URL__Group__0 ) ) )
            // InternalSemver.g:582:2: ( ( rule__URL__Group__0 ) )
            {
            // InternalSemver.g:582:2: ( ( rule__URL__Group__0 ) )
            // InternalSemver.g:583:3: ( rule__URL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup()); 
            }
            // InternalSemver.g:584:3: ( rule__URL__Group__0 )
            // InternalSemver.g:584:4: rule__URL__Group__0
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSemver.g:593:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSemver.g:594:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:595:1: ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:602:1: ruleALPHA_NUMERIC_CHARS : ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:606:2: ( ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) )
            // InternalSemver.g:607:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            {
            // InternalSemver.g:607:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            // InternalSemver.g:608:3: ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* )
            {
            // InternalSemver.g:608:3: ( ( ruleALPHA_NUMERIC_CHAR ) )
            // InternalSemver.g:609:4: ( ruleALPHA_NUMERIC_CHAR )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            }
            // InternalSemver.g:610:4: ( ruleALPHA_NUMERIC_CHAR )
            // InternalSemver.g:610:5: ruleALPHA_NUMERIC_CHAR
            {
            pushFollow(FOLLOW_4);
            ruleALPHA_NUMERIC_CHAR();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            }

            }

            // InternalSemver.g:613:3: ( ( ruleALPHA_NUMERIC_CHAR )* )
            // InternalSemver.g:614:4: ( ruleALPHA_NUMERIC_CHAR )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            }
            // InternalSemver.g:615:4: ( ruleALPHA_NUMERIC_CHAR )*
            loop2:
            do {
                int alt2=2;
                switch ( input.LA(1) ) {
                case 47:
                    {
                    switch ( input.LA(2) ) {
                    case RULE_DIGITS:
                        {
                        int LA2_3 = input.LA(3);

                        if ( (synpred2_InternalSemver()) ) {
                            alt2=1;
                        }


                        }
                        break;
                    case RULE_LETTER_V:
                        {
                        int LA2_4 = input.LA(3);

                        if ( (synpred2_InternalSemver()) ) {
                            alt2=1;
                        }


                        }
                        break;
                    case RULE_LETTER_X:
                        {
                        int LA2_5 = input.LA(3);

                        if ( (synpred2_InternalSemver()) ) {
                            alt2=1;
                        }


                        }
                        break;
                    case RULE_LETTER_NO_VX:
                        {
                        int LA2_6 = input.LA(3);

                        if ( (synpred2_InternalSemver()) ) {
                            alt2=1;
                        }


                        }
                        break;

                    }

                    }
                    break;
                case RULE_DIGITS:
                    {
                    int LA2_3 = input.LA(2);

                    if ( (synpred2_InternalSemver()) ) {
                        alt2=1;
                    }


                    }
                    break;
                case RULE_LETTER_V:
                    {
                    int LA2_4 = input.LA(2);

                    if ( (synpred2_InternalSemver()) ) {
                        alt2=1;
                    }


                    }
                    break;
                case RULE_LETTER_X:
                    {
                    int LA2_5 = input.LA(2);

                    if ( (synpred2_InternalSemver()) ) {
                        alt2=1;
                    }


                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    int LA2_6 = input.LA(2);

                    if ( (synpred2_InternalSemver()) ) {
                        alt2=1;
                    }


                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // InternalSemver.g:615:5: ruleALPHA_NUMERIC_CHAR
            	    {
            	    pushFollow(FOLLOW_4);
            	    ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
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
    // InternalSemver.g:625:1: entryRuleALPHA_NUMERIC_CHAR : ruleALPHA_NUMERIC_CHAR EOF ;
    public final void entryRuleALPHA_NUMERIC_CHAR() throws RecognitionException {
        try {
            // InternalSemver.g:626:1: ( ruleALPHA_NUMERIC_CHAR EOF )
            // InternalSemver.g:627:1: ruleALPHA_NUMERIC_CHAR EOF
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
    // InternalSemver.g:634:1: ruleALPHA_NUMERIC_CHAR : ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) ) ;
    public final void ruleALPHA_NUMERIC_CHAR() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:638:2: ( ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) ) )
            // InternalSemver.g:639:2: ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) )
            {
            // InternalSemver.g:639:2: ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) )
            // InternalSemver.g:640:3: ( rule__ALPHA_NUMERIC_CHAR__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getGroup()); 
            }
            // InternalSemver.g:641:3: ( rule__ALPHA_NUMERIC_CHAR__Group__0 )
            // InternalSemver.g:641:4: rule__ALPHA_NUMERIC_CHAR__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getGroup()); 
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


    // $ANTLR start "entryRuleLETTERS"
    // InternalSemver.g:650:1: entryRuleLETTERS : ruleLETTERS EOF ;
    public final void entryRuleLETTERS() throws RecognitionException {
        try {
            // InternalSemver.g:651:1: ( ruleLETTERS EOF )
            // InternalSemver.g:652:1: ruleLETTERS EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTERSRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLETTERS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLETTERSRule()); 
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
    // $ANTLR end "entryRuleLETTERS"


    // $ANTLR start "ruleLETTERS"
    // InternalSemver.g:659:1: ruleLETTERS : ( ( ( rule__LETTERS__Alternatives ) ) ( ( rule__LETTERS__Alternatives )* ) ) ;
    public final void ruleLETTERS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:663:2: ( ( ( ( rule__LETTERS__Alternatives ) ) ( ( rule__LETTERS__Alternatives )* ) ) )
            // InternalSemver.g:664:2: ( ( ( rule__LETTERS__Alternatives ) ) ( ( rule__LETTERS__Alternatives )* ) )
            {
            // InternalSemver.g:664:2: ( ( ( rule__LETTERS__Alternatives ) ) ( ( rule__LETTERS__Alternatives )* ) )
            // InternalSemver.g:665:3: ( ( rule__LETTERS__Alternatives ) ) ( ( rule__LETTERS__Alternatives )* )
            {
            // InternalSemver.g:665:3: ( ( rule__LETTERS__Alternatives ) )
            // InternalSemver.g:666:4: ( rule__LETTERS__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTERSAccess().getAlternatives()); 
            }
            // InternalSemver.g:667:4: ( rule__LETTERS__Alternatives )
            // InternalSemver.g:667:5: rule__LETTERS__Alternatives
            {
            pushFollow(FOLLOW_5);
            rule__LETTERS__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLETTERSAccess().getAlternatives()); 
            }

            }

            // InternalSemver.g:670:3: ( ( rule__LETTERS__Alternatives )* )
            // InternalSemver.g:671:4: ( rule__LETTERS__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTERSAccess().getAlternatives()); 
            }
            // InternalSemver.g:672:4: ( rule__LETTERS__Alternatives )*
            loop3:
            do {
                int alt3=2;
                switch ( input.LA(1) ) {
                case RULE_LETTER_V:
                    {
                    int LA3_2 = input.LA(2);

                    if ( (synpred3_InternalSemver()) ) {
                        alt3=1;
                    }


                    }
                    break;
                case RULE_LETTER_X:
                    {
                    int LA3_3 = input.LA(2);

                    if ( (synpred3_InternalSemver()) ) {
                        alt3=1;
                    }


                    }
                    break;
                case RULE_LETTER_NO_VX:
                    {
                    int LA3_4 = input.LA(2);

                    if ( (synpred3_InternalSemver()) ) {
                        alt3=1;
                    }


                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // InternalSemver.g:672:5: rule__LETTERS__Alternatives
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__LETTERS__Alternatives();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLETTERSAccess().getAlternatives()); 
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
    // $ANTLR end "ruleLETTERS"


    // $ANTLR start "entryRuleLETTER_NO_V"
    // InternalSemver.g:682:1: entryRuleLETTER_NO_V : ruleLETTER_NO_V EOF ;
    public final void entryRuleLETTER_NO_V() throws RecognitionException {
        try {
            // InternalSemver.g:683:1: ( ruleLETTER_NO_V EOF )
            // InternalSemver.g:684:1: ruleLETTER_NO_V EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTER_NO_VRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLETTER_NO_V();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLETTER_NO_VRule()); 
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
    // $ANTLR end "entryRuleLETTER_NO_V"


    // $ANTLR start "ruleLETTER_NO_V"
    // InternalSemver.g:691:1: ruleLETTER_NO_V : ( ( rule__LETTER_NO_V__Alternatives ) ) ;
    public final void ruleLETTER_NO_V() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:695:2: ( ( ( rule__LETTER_NO_V__Alternatives ) ) )
            // InternalSemver.g:696:2: ( ( rule__LETTER_NO_V__Alternatives ) )
            {
            // InternalSemver.g:696:2: ( ( rule__LETTER_NO_V__Alternatives ) )
            // InternalSemver.g:697:3: ( rule__LETTER_NO_V__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTER_NO_VAccess().getAlternatives()); 
            }
            // InternalSemver.g:698:3: ( rule__LETTER_NO_V__Alternatives )
            // InternalSemver.g:698:4: rule__LETTER_NO_V__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__LETTER_NO_V__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLETTER_NO_VAccess().getAlternatives()); 
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
    // $ANTLR end "ruleLETTER_NO_V"


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSemver.g:707:1: entryRuleWILDCARD : ruleWILDCARD EOF ;
    public final void entryRuleWILDCARD() throws RecognitionException {
        try {
            // InternalSemver.g:708:1: ( ruleWILDCARD EOF )
            // InternalSemver.g:709:1: ruleWILDCARD EOF
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
    // InternalSemver.g:716:1: ruleWILDCARD : ( ( rule__WILDCARD__Alternatives ) ) ;
    public final void ruleWILDCARD() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:720:2: ( ( ( rule__WILDCARD__Alternatives ) ) )
            // InternalSemver.g:721:2: ( ( rule__WILDCARD__Alternatives ) )
            {
            // InternalSemver.g:721:2: ( ( rule__WILDCARD__Alternatives ) )
            // InternalSemver.g:722:3: ( rule__WILDCARD__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWILDCARDAccess().getAlternatives()); 
            }
            // InternalSemver.g:723:3: ( rule__WILDCARD__Alternatives )
            // InternalSemver.g:723:4: rule__WILDCARD__Alternatives
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


    // $ANTLR start "ruleVersionComparator"
    // InternalSemver.g:732:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:736:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSemver.g:737:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSemver.g:737:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSemver.g:738:3: ( rule__VersionComparator__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            }
            // InternalSemver.g:739:3: ( rule__VersionComparator__Alternatives )
            // InternalSemver.g:739:4: rule__VersionComparator__Alternatives
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
    // InternalSemver.g:747:1: rule__NPMVersionRequirement__Alternatives : ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:751:1: ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) )
            int alt4=2;
            switch ( input.LA(1) ) {
            case EOF:
            case RULE_DIGITS:
            case RULE_ASTERIX:
            case RULE_WS:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
                {
                alt4=1;
                }
                break;
            case RULE_LETTER_X:
                {
                int LA4_2 = input.LA(2);

                if ( (synpred4_InternalSemver()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LETTER_V:
            case RULE_LETTER_NO_VX:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 45:
                {
                alt4=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalSemver.g:752:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    {
                    // InternalSemver.g:752:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    // InternalSemver.g:753:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:754:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    // InternalSemver.g:754:4: rule__NPMVersionRequirement__Group_0__0
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
                    // InternalSemver.g:758:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    {
                    // InternalSemver.g:758:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    // InternalSemver.g:759:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:760:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    // InternalSemver.g:760:4: rule__NPMVersionRequirement__Group_1__0
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
    // InternalSemver.g:768:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ruleURLVersionRequirement ) | ( ruleLocalPathVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:772:1: ( ( ruleURLVersionRequirement ) | ( ruleLocalPathVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) )
            int alt5=4;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSemver.g:773:2: ( ruleURLVersionRequirement )
                    {
                    // InternalSemver.g:773:2: ( ruleURLVersionRequirement )
                    // InternalSemver.g:774:3: ruleURLVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleURLVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:779:2: ( ruleLocalPathVersionRequirement )
                    {
                    // InternalSemver.g:779:2: ( ruleLocalPathVersionRequirement )
                    // InternalSemver.g:780:3: ruleLocalPathVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLocalPathVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:785:2: ( ruleGitHubVersionRequirement )
                    {
                    // InternalSemver.g:785:2: ( ruleGitHubVersionRequirement )
                    // InternalSemver.g:786:3: ruleGitHubVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleGitHubVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:791:2: ( ruleTagVersionRequirement )
                    {
                    // InternalSemver.g:791:2: ( ruleTagVersionRequirement )
                    // InternalSemver.g:792:3: ruleTagVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_3()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleTagVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_3()); 
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


    // $ANTLR start "rule__URLVersionSpecifier__Alternatives"
    // InternalSemver.g:801:1: rule__URLVersionSpecifier__Alternatives : ( ( ( ruleURLSemver ) ) | ( ruleURLCommitISH ) );
    public final void rule__URLVersionSpecifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:805:1: ( ( ( ruleURLSemver ) ) | ( ruleURLCommitISH ) )
            int alt6=2;
            switch ( input.LA(1) ) {
            case RULE_ASTERIX:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 44:
                {
                alt6=1;
                }
                break;
            case RULE_LETTER_X:
                {
                int LA6_2 = input.LA(2);

                if ( (synpred8_InternalSemver()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_DIGITS:
                {
                int LA6_3 = input.LA(2);

                if ( (synpred8_InternalSemver()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LETTER_V:
            case RULE_LETTER_NO_VX:
            case 47:
                {
                alt6=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalSemver.g:806:2: ( ( ruleURLSemver ) )
                    {
                    // InternalSemver.g:806:2: ( ( ruleURLSemver ) )
                    // InternalSemver.g:807:3: ( ruleURLSemver )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0()); 
                    }
                    // InternalSemver.g:808:3: ( ruleURLSemver )
                    // InternalSemver.g:808:4: ruleURLSemver
                    {
                    pushFollow(FOLLOW_2);
                    ruleURLSemver();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:812:2: ( ruleURLCommitISH )
                    {
                    // InternalSemver.g:812:2: ( ruleURLCommitISH )
                    // InternalSemver.g:813:3: ruleURLCommitISH
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleURLCommitISH();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHParserRuleCall_1()); 
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


    // $ANTLR start "rule__VersionRange__Alternatives"
    // InternalSemver.g:822:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:826:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:827:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSemver.g:827:2: ( ruleVersionRangeContraint )
                    // InternalSemver.g:828:3: ruleVersionRangeContraint
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
                    // InternalSemver.g:833:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSemver.g:833:2: ( ruleHyphenVersionRange )
                    // InternalSemver.g:834:3: ruleHyphenVersionRange
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
    // InternalSemver.g:843:1: rule__VersionPart__Alternatives : ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) );
    public final void rule__VersionPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:847:1: ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_LETTER_X||LA8_0==RULE_ASTERIX) ) {
                alt8=1;
            }
            else if ( (LA8_0==RULE_DIGITS) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalSemver.g:848:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    {
                    // InternalSemver.g:848:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    // InternalSemver.g:849:3: ( rule__VersionPart__WildcardAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    }
                    // InternalSemver.g:850:3: ( rule__VersionPart__WildcardAssignment_0 )
                    // InternalSemver.g:850:4: rule__VersionPart__WildcardAssignment_0
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
                    // InternalSemver.g:854:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    {
                    // InternalSemver.g:854:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    // InternalSemver.g:855:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    }
                    // InternalSemver.g:856:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    // InternalSemver.g:856:4: rule__VersionPart__NumberRawAssignment_1
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
    // InternalSemver.g:864:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:868:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt9=3;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // InternalSemver.g:869:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:869:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSemver.g:870:3: ( rule__Qualifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:871:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSemver.g:871:4: rule__Qualifier__Group_0__0
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
                    // InternalSemver.g:875:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:875:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSemver.g:876:3: ( rule__Qualifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:877:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSemver.g:877:4: rule__Qualifier__Group_1__0
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
                case 3 :
                    // InternalSemver.g:881:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:881:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSemver.g:882:3: ( rule__Qualifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:883:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSemver.g:883:4: rule__Qualifier__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getQualifierAccess().getGroup_2()); 
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


    // $ANTLR start "rule__URL_PROTOCOL__Alternatives"
    // InternalSemver.g:891:1: rule__URL_PROTOCOL__Alternatives : ( ( ruleLETTERS ) | ( '+' ) );
    public final void rule__URL_PROTOCOL__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:895:1: ( ( ruleLETTERS ) | ( '+' ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>=RULE_LETTER_V && LA10_0<=RULE_LETTER_NO_VX)) ) {
                alt10=1;
            }
            else if ( (LA10_0==29) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalSemver.g:896:2: ( ruleLETTERS )
                    {
                    // InternalSemver.g:896:2: ( ruleLETTERS )
                    // InternalSemver.g:897:3: ruleLETTERS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getLETTERSParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTERS();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getLETTERSParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:902:2: ( '+' )
                    {
                    // InternalSemver.g:902:2: ( '+' )
                    // InternalSemver.g:903:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1()); 
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
    // $ANTLR end "rule__URL_PROTOCOL__Alternatives"


    // $ANTLR start "rule__PATH__Alternatives_1_0"
    // InternalSemver.g:912:1: rule__PATH__Alternatives_1_0 : ( ( '/' ) | ( '.' ) );
    public final void rule__PATH__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:916:1: ( ( '/' ) | ( '.' ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==30) ) {
                alt11=1;
            }
            else if ( (LA11_0==31) ) {
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
                    // InternalSemver.g:917:2: ( '/' )
                    {
                    // InternalSemver.g:917:2: ( '/' )
                    // InternalSemver.g:918:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_1_0_0()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_1_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:923:2: ( '.' )
                    {
                    // InternalSemver.g:923:2: ( '.' )
                    // InternalSemver.g:924:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_1_0_1()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_1_0_1()); 
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
    // $ANTLR end "rule__PATH__Alternatives_1_0"


    // $ANTLR start "rule__PATH__Alternatives_2"
    // InternalSemver.g:933:1: rule__PATH__Alternatives_2 : ( ( '/' ) | ( '.' ) );
    public final void rule__PATH__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:937:1: ( ( '/' ) | ( '.' ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==30) ) {
                alt12=1;
            }
            else if ( (LA12_0==31) ) {
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
                    // InternalSemver.g:938:2: ( '/' )
                    {
                    // InternalSemver.g:938:2: ( '/' )
                    // InternalSemver.g:939:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_2_0()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:944:2: ( '.' )
                    {
                    // InternalSemver.g:944:2: ( '.' )
                    // InternalSemver.g:945:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_2_1()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_2_1()); 
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
    // $ANTLR end "rule__PATH__Alternatives_2"


    // $ANTLR start "rule__URL__Alternatives_1_0"
    // InternalSemver.g:954:1: rule__URL__Alternatives_1_0 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:958:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt13=4;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt13=1;
                }
                break;
            case 31:
                {
                alt13=2;
                }
                break;
            case 32:
                {
                alt13=3;
                }
                break;
            case 33:
                {
                alt13=4;
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
                    // InternalSemver.g:959:2: ( '/' )
                    {
                    // InternalSemver.g:959:2: ( '/' )
                    // InternalSemver.g:960:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_1_0_0()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_1_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:965:2: ( '.' )
                    {
                    // InternalSemver.g:965:2: ( '.' )
                    // InternalSemver.g:966:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_1_0_1()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_1_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:971:2: ( ':' )
                    {
                    // InternalSemver.g:971:2: ( ':' )
                    // InternalSemver.g:972:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_1_0_2()); 
                    }
                    match(input,32,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_1_0_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:977:2: ( '@' )
                    {
                    // InternalSemver.g:977:2: ( '@' )
                    // InternalSemver.g:978:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_1_0_3()); 
                    }
                    match(input,33,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_1_0_3()); 
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
    // $ANTLR end "rule__URL__Alternatives_1_0"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Alternatives_1"
    // InternalSemver.g:987:1: rule__ALPHA_NUMERIC_CHAR__Alternatives_1 : ( ( RULE_DIGITS ) | ( ruleLETTERS ) );
    public final void rule__ALPHA_NUMERIC_CHAR__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:991:1: ( ( RULE_DIGITS ) | ( ruleLETTERS ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_DIGITS) ) {
                alt14=1;
            }
            else if ( ((LA14_0>=RULE_LETTER_V && LA14_0<=RULE_LETTER_NO_VX)) ) {
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
                    // InternalSemver.g:992:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:992:2: ( RULE_DIGITS )
                    // InternalSemver.g:993:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1_0()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:998:2: ( ruleLETTERS )
                    {
                    // InternalSemver.g:998:2: ( ruleLETTERS )
                    // InternalSemver.g:999:3: ruleLETTERS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSParserRuleCall_1_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTERS();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSParserRuleCall_1_1()); 
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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Alternatives_1"


    // $ANTLR start "rule__LETTERS__Alternatives"
    // InternalSemver.g:1008:1: rule__LETTERS__Alternatives : ( ( RULE_LETTER_V ) | ( ruleLETTER_NO_V ) );
    public final void rule__LETTERS__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1012:1: ( ( RULE_LETTER_V ) | ( ruleLETTER_NO_V ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_LETTER_V) ) {
                alt15=1;
            }
            else if ( ((LA15_0>=RULE_LETTER_X && LA15_0<=RULE_LETTER_NO_VX)) ) {
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
                    // InternalSemver.g:1013:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1013:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1014:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTERSAccess().getLETTER_VTerminalRuleCall_0()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTERSAccess().getLETTER_VTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1019:2: ( ruleLETTER_NO_V )
                    {
                    // InternalSemver.g:1019:2: ( ruleLETTER_NO_V )
                    // InternalSemver.g:1020:3: ruleLETTER_NO_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTERSAccess().getLETTER_NO_VParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER_NO_V();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTERSAccess().getLETTER_NO_VParserRuleCall_1()); 
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
    // $ANTLR end "rule__LETTERS__Alternatives"


    // $ANTLR start "rule__LETTER_NO_V__Alternatives"
    // InternalSemver.g:1029:1: rule__LETTER_NO_V__Alternatives : ( ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__LETTER_NO_V__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1033:1: ( ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_LETTER_X) ) {
                alt16=1;
            }
            else if ( (LA16_0==RULE_LETTER_NO_VX) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalSemver.g:1034:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1034:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1035:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VAccess().getLETTER_XTerminalRuleCall_0()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VAccess().getLETTER_XTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1040:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1040:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1041:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VAccess().getLETTER_NO_VXTerminalRuleCall_1()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VAccess().getLETTER_NO_VXTerminalRuleCall_1()); 
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
    // $ANTLR end "rule__LETTER_NO_V__Alternatives"


    // $ANTLR start "rule__WILDCARD__Alternatives"
    // InternalSemver.g:1050:1: rule__WILDCARD__Alternatives : ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) );
    public final void rule__WILDCARD__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1054:1: ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==RULE_LETTER_X) ) {
                alt17=1;
            }
            else if ( (LA17_0==RULE_ASTERIX) ) {
                alt17=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // InternalSemver.g:1055:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1055:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1056:3: RULE_LETTER_X
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
                    // InternalSemver.g:1061:2: ( RULE_ASTERIX )
                    {
                    // InternalSemver.g:1061:2: ( RULE_ASTERIX )
                    // InternalSemver.g:1062:3: RULE_ASTERIX
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


    // $ANTLR start "rule__VersionComparator__Alternatives"
    // InternalSemver.g:1071:1: rule__VersionComparator__Alternatives : ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1075:1: ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt18=8;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt18=1;
                }
                break;
            case 35:
                {
                alt18=2;
                }
                break;
            case 36:
                {
                alt18=3;
                }
                break;
            case 37:
                {
                alt18=4;
                }
                break;
            case 38:
                {
                alt18=5;
                }
                break;
            case 39:
                {
                alt18=6;
                }
                break;
            case 40:
                {
                alt18=7;
                }
                break;
            case 41:
                {
                alt18=8;
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
                    // InternalSemver.g:1076:2: ( ( 'v' ) )
                    {
                    // InternalSemver.g:1076:2: ( ( 'v' ) )
                    // InternalSemver.g:1077:3: ( 'v' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 
                    }
                    // InternalSemver.g:1078:3: ( 'v' )
                    // InternalSemver.g:1078:4: 'v'
                    {
                    match(input,34,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1082:2: ( ( '=' ) )
                    {
                    // InternalSemver.g:1082:2: ( ( '=' ) )
                    // InternalSemver.g:1083:3: ( '=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 
                    }
                    // InternalSemver.g:1084:3: ( '=' )
                    // InternalSemver.g:1084:4: '='
                    {
                    match(input,35,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1088:2: ( ( '<' ) )
                    {
                    // InternalSemver.g:1088:2: ( ( '<' ) )
                    // InternalSemver.g:1089:3: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 
                    }
                    // InternalSemver.g:1090:3: ( '<' )
                    // InternalSemver.g:1090:4: '<'
                    {
                    match(input,36,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1094:2: ( ( '~' ) )
                    {
                    // InternalSemver.g:1094:2: ( ( '~' ) )
                    // InternalSemver.g:1095:3: ( '~' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 
                    }
                    // InternalSemver.g:1096:3: ( '~' )
                    // InternalSemver.g:1096:4: '~'
                    {
                    match(input,37,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1100:2: ( ( '^' ) )
                    {
                    // InternalSemver.g:1100:2: ( ( '^' ) )
                    // InternalSemver.g:1101:3: ( '^' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 
                    }
                    // InternalSemver.g:1102:3: ( '^' )
                    // InternalSemver.g:1102:4: '^'
                    {
                    match(input,38,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1106:2: ( ( '<=' ) )
                    {
                    // InternalSemver.g:1106:2: ( ( '<=' ) )
                    // InternalSemver.g:1107:3: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 
                    }
                    // InternalSemver.g:1108:3: ( '<=' )
                    // InternalSemver.g:1108:4: '<='
                    {
                    match(input,39,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1112:2: ( ( '>' ) )
                    {
                    // InternalSemver.g:1112:2: ( ( '>' ) )
                    // InternalSemver.g:1113:3: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 
                    }
                    // InternalSemver.g:1114:3: ( '>' )
                    // InternalSemver.g:1114:4: '>'
                    {
                    match(input,40,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSemver.g:1118:2: ( ( '>=' ) )
                    {
                    // InternalSemver.g:1118:2: ( ( '>=' ) )
                    // InternalSemver.g:1119:3: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7()); 
                    }
                    // InternalSemver.g:1120:3: ( '>=' )
                    // InternalSemver.g:1120:4: '>='
                    {
                    match(input,41,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7()); 
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
    // InternalSemver.g:1128:1: rule__NPMVersionRequirement__Group_0__0 : rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 ;
    public final void rule__NPMVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1132:1: ( rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 )
            // InternalSemver.g:1133:2: rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:1140:1: rule__NPMVersionRequirement__Group_0__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1144:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1145:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1145:1: ( ( RULE_WS )* )
            // InternalSemver.g:1146:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }
            // InternalSemver.g:1147:2: ( RULE_WS )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_WS) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSemver.g:1147:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

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
    // InternalSemver.g:1155:1: rule__NPMVersionRequirement__Group_0__1 : rule__NPMVersionRequirement__Group_0__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1159:1: ( rule__NPMVersionRequirement__Group_0__1__Impl )
            // InternalSemver.g:1160:2: rule__NPMVersionRequirement__Group_0__1__Impl
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
    // InternalSemver.g:1166:1: rule__NPMVersionRequirement__Group_0__1__Impl : ( ruleVersionRangeSetRequirement ) ;
    public final void rule__NPMVersionRequirement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1170:1: ( ( ruleVersionRangeSetRequirement ) )
            // InternalSemver.g:1171:1: ( ruleVersionRangeSetRequirement )
            {
            // InternalSemver.g:1171:1: ( ruleVersionRangeSetRequirement )
            // InternalSemver.g:1172:2: ruleVersionRangeSetRequirement
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
    // InternalSemver.g:1182:1: rule__NPMVersionRequirement__Group_1__0 : rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 ;
    public final void rule__NPMVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1186:1: ( rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 )
            // InternalSemver.g:1187:2: rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:1194:1: rule__NPMVersionRequirement__Group_1__0__Impl : ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) ;
    public final void rule__NPMVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1198:1: ( ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) )
            // InternalSemver.g:1199:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            {
            // InternalSemver.g:1199:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            // InternalSemver.g:1200:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:1201:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            // InternalSemver.g:1201:3: rule__NPMVersionRequirement__Alternatives_1_0
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
    // InternalSemver.g:1209:1: rule__NPMVersionRequirement__Group_1__1 : rule__NPMVersionRequirement__Group_1__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1213:1: ( rule__NPMVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1214:2: rule__NPMVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1220:1: rule__NPMVersionRequirement__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1224:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1225:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1225:1: ( ( RULE_WS )* )
            // InternalSemver.g:1226:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:1227:2: ( RULE_WS )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==RULE_WS) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSemver.g:1227:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

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


    // $ANTLR start "rule__URLVersionRequirement__Group__0"
    // InternalSemver.g:1236:1: rule__URLVersionRequirement__Group__0 : rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 ;
    public final void rule__URLVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1240:1: ( rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 )
            // InternalSemver.g:1241:2: rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_9);
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
    // InternalSemver.g:1248:1: rule__URLVersionRequirement__Group__0__Impl : ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) ;
    public final void rule__URLVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1252:1: ( ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) )
            // InternalSemver.g:1253:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            {
            // InternalSemver.g:1253:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            // InternalSemver.g:1254:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }
            // InternalSemver.g:1255:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            // InternalSemver.g:1255:3: rule__URLVersionRequirement__ProtocolAssignment_0
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
    // InternalSemver.g:1263:1: rule__URLVersionRequirement__Group__1 : rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 ;
    public final void rule__URLVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1267:1: ( rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 )
            // InternalSemver.g:1268:2: rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2
            {
            pushFollow(FOLLOW_10);
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
    // InternalSemver.g:1275:1: rule__URLVersionRequirement__Group__1__Impl : ( '://' ) ;
    public final void rule__URLVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1279:1: ( ( '://' ) )
            // InternalSemver.g:1280:1: ( '://' )
            {
            // InternalSemver.g:1280:1: ( '://' )
            // InternalSemver.g:1281:2: '://'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getColonSolidusSolidusKeyword_1()); 
            }
            match(input,42,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLVersionRequirementAccess().getColonSolidusSolidusKeyword_1()); 
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
    // InternalSemver.g:1290:1: rule__URLVersionRequirement__Group__2 : rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 ;
    public final void rule__URLVersionRequirement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1294:1: ( rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 )
            // InternalSemver.g:1295:2: rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:1302:1: rule__URLVersionRequirement__Group__2__Impl : ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) ;
    public final void rule__URLVersionRequirement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1306:1: ( ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) )
            // InternalSemver.g:1307:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            {
            // InternalSemver.g:1307:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            // InternalSemver.g:1308:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }
            // InternalSemver.g:1309:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            // InternalSemver.g:1309:3: rule__URLVersionRequirement__UrlAssignment_2
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
    // InternalSemver.g:1317:1: rule__URLVersionRequirement__Group__3 : rule__URLVersionRequirement__Group__3__Impl ;
    public final void rule__URLVersionRequirement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1321:1: ( rule__URLVersionRequirement__Group__3__Impl )
            // InternalSemver.g:1322:2: rule__URLVersionRequirement__Group__3__Impl
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
    // InternalSemver.g:1328:1: rule__URLVersionRequirement__Group__3__Impl : ( ( rule__URLVersionRequirement__Group_3__0 )? ) ;
    public final void rule__URLVersionRequirement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1332:1: ( ( ( rule__URLVersionRequirement__Group_3__0 )? ) )
            // InternalSemver.g:1333:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            {
            // InternalSemver.g:1333:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            // InternalSemver.g:1334:2: ( rule__URLVersionRequirement__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }
            // InternalSemver.g:1335:2: ( rule__URLVersionRequirement__Group_3__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==43) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSemver.g:1335:3: rule__URLVersionRequirement__Group_3__0
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


    // $ANTLR start "rule__URLVersionRequirement__Group_3__0"
    // InternalSemver.g:1344:1: rule__URLVersionRequirement__Group_3__0 : rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 ;
    public final void rule__URLVersionRequirement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1348:1: ( rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 )
            // InternalSemver.g:1349:2: rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1
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
    // InternalSemver.g:1356:1: rule__URLVersionRequirement__Group_3__0__Impl : ( '#' ) ;
    public final void rule__URLVersionRequirement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1360:1: ( ( '#' ) )
            // InternalSemver.g:1361:1: ( '#' )
            {
            // InternalSemver.g:1361:1: ( '#' )
            // InternalSemver.g:1362:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); 
            }
            match(input,43,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1371:1: rule__URLVersionRequirement__Group_3__1 : rule__URLVersionRequirement__Group_3__1__Impl ;
    public final void rule__URLVersionRequirement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1375:1: ( rule__URLVersionRequirement__Group_3__1__Impl )
            // InternalSemver.g:1376:2: rule__URLVersionRequirement__Group_3__1__Impl
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
    // InternalSemver.g:1382:1: rule__URLVersionRequirement__Group_3__1__Impl : ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) ;
    public final void rule__URLVersionRequirement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1386:1: ( ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) )
            // InternalSemver.g:1387:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            {
            // InternalSemver.g:1387:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            // InternalSemver.g:1388:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }
            // InternalSemver.g:1389:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            // InternalSemver.g:1389:3: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
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


    // $ANTLR start "rule__URLSemver__Group__0"
    // InternalSemver.g:1398:1: rule__URLSemver__Group__0 : rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 ;
    public final void rule__URLSemver__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1402:1: ( rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 )
            // InternalSemver.g:1403:2: rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1
            {
            pushFollow(FOLLOW_13);
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
    // InternalSemver.g:1410:1: rule__URLSemver__Group__0__Impl : ( ( 'semver:' )? ) ;
    public final void rule__URLSemver__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1414:1: ( ( ( 'semver:' )? ) )
            // InternalSemver.g:1415:1: ( ( 'semver:' )? )
            {
            // InternalSemver.g:1415:1: ( ( 'semver:' )? )
            // InternalSemver.g:1416:2: ( 'semver:' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSemverKeyword_0()); 
            }
            // InternalSemver.g:1417:2: ( 'semver:' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==44) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSemver.g:1417:3: 'semver:'
                    {
                    match(input,44,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getSemverKeyword_0()); 
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
    // $ANTLR end "rule__URLSemver__Group__0__Impl"


    // $ANTLR start "rule__URLSemver__Group__1"
    // InternalSemver.g:1425:1: rule__URLSemver__Group__1 : rule__URLSemver__Group__1__Impl ;
    public final void rule__URLSemver__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1429:1: ( rule__URLSemver__Group__1__Impl )
            // InternalSemver.g:1430:2: rule__URLSemver__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URLSemver__Group__1__Impl();

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
    // InternalSemver.g:1436:1: rule__URLSemver__Group__1__Impl : ( ( rule__URLSemver__SimpleVersionAssignment_1 ) ) ;
    public final void rule__URLSemver__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1440:1: ( ( ( rule__URLSemver__SimpleVersionAssignment_1 ) ) )
            // InternalSemver.g:1441:1: ( ( rule__URLSemver__SimpleVersionAssignment_1 ) )
            {
            // InternalSemver.g:1441:1: ( ( rule__URLSemver__SimpleVersionAssignment_1 ) )
            // InternalSemver.g:1442:2: ( rule__URLSemver__SimpleVersionAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_1()); 
            }
            // InternalSemver.g:1443:2: ( rule__URLSemver__SimpleVersionAssignment_1 )
            // InternalSemver.g:1443:3: rule__URLSemver__SimpleVersionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__URLSemver__SimpleVersionAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_1()); 
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


    // $ANTLR start "rule__GitHubVersionRequirement__Group__0"
    // InternalSemver.g:1452:1: rule__GitHubVersionRequirement__Group__0 : rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 ;
    public final void rule__GitHubVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1456:1: ( rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 )
            // InternalSemver.g:1457:2: rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:1464:1: rule__GitHubVersionRequirement__Group__0__Impl : ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) ;
    public final void rule__GitHubVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1468:1: ( ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) )
            // InternalSemver.g:1469:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            {
            // InternalSemver.g:1469:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            // InternalSemver.g:1470:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }
            // InternalSemver.g:1471:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            // InternalSemver.g:1471:3: rule__GitHubVersionRequirement__GithubUrlAssignment_0
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
    // InternalSemver.g:1479:1: rule__GitHubVersionRequirement__Group__1 : rule__GitHubVersionRequirement__Group__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1483:1: ( rule__GitHubVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1484:2: rule__GitHubVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1490:1: rule__GitHubVersionRequirement__Group__1__Impl : ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) ;
    public final void rule__GitHubVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1494:1: ( ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) )
            // InternalSemver.g:1495:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:1495:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            // InternalSemver.g:1496:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1497:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==43) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSemver.g:1497:3: rule__GitHubVersionRequirement__Group_1__0
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
    // InternalSemver.g:1506:1: rule__GitHubVersionRequirement__Group_1__0 : rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 ;
    public final void rule__GitHubVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1510:1: ( rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 )
            // InternalSemver.g:1511:2: rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1
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
    // InternalSemver.g:1518:1: rule__GitHubVersionRequirement__Group_1__0__Impl : ( '#' ) ;
    public final void rule__GitHubVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1522:1: ( ( '#' ) )
            // InternalSemver.g:1523:1: ( '#' )
            {
            // InternalSemver.g:1523:1: ( '#' )
            // InternalSemver.g:1524:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); 
            }
            match(input,43,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1533:1: rule__GitHubVersionRequirement__Group_1__1 : rule__GitHubVersionRequirement__Group_1__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1537:1: ( rule__GitHubVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1538:2: rule__GitHubVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1544:1: rule__GitHubVersionRequirement__Group_1__1__Impl : ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) ;
    public final void rule__GitHubVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1548:1: ( ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1549:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1549:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1550:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1551:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            // InternalSemver.g:1551:3: rule__GitHubVersionRequirement__CommitISHAssignment_1_1
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


    // $ANTLR start "rule__LocalPathVersionRequirement__Group__0"
    // InternalSemver.g:1560:1: rule__LocalPathVersionRequirement__Group__0 : rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 ;
    public final void rule__LocalPathVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1564:1: ( rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 )
            // InternalSemver.g:1565:2: rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_14);
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
    // InternalSemver.g:1572:1: rule__LocalPathVersionRequirement__Group__0__Impl : ( 'file:' ) ;
    public final void rule__LocalPathVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1576:1: ( ( 'file:' ) )
            // InternalSemver.g:1577:1: ( 'file:' )
            {
            // InternalSemver.g:1577:1: ( 'file:' )
            // InternalSemver.g:1578:2: 'file:'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getFileKeyword_0()); 
            }
            match(input,45,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementAccess().getFileKeyword_0()); 
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
    // InternalSemver.g:1587:1: rule__LocalPathVersionRequirement__Group__1 : rule__LocalPathVersionRequirement__Group__1__Impl ;
    public final void rule__LocalPathVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1591:1: ( rule__LocalPathVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1592:2: rule__LocalPathVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1598:1: rule__LocalPathVersionRequirement__Group__1__Impl : ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1602:1: ( ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) )
            // InternalSemver.g:1603:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            {
            // InternalSemver.g:1603:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            // InternalSemver.g:1604:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }
            // InternalSemver.g:1605:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            // InternalSemver.g:1605:3: rule__LocalPathVersionRequirement__LocalPathAssignment_1
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


    // $ANTLR start "rule__VersionRangeSetRequirement__Group__0"
    // InternalSemver.g:1614:1: rule__VersionRangeSetRequirement__Group__0 : rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 ;
    public final void rule__VersionRangeSetRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1618:1: ( rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 )
            // InternalSemver.g:1619:2: rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:1626:1: rule__VersionRangeSetRequirement__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSetRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1630:1: ( ( () ) )
            // InternalSemver.g:1631:1: ( () )
            {
            // InternalSemver.g:1631:1: ( () )
            // InternalSemver.g:1632:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }
            // InternalSemver.g:1633:2: ()
            // InternalSemver.g:1633:3: 
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
    // InternalSemver.g:1641:1: rule__VersionRangeSetRequirement__Group__1 : rule__VersionRangeSetRequirement__Group__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1645:1: ( rule__VersionRangeSetRequirement__Group__1__Impl )
            // InternalSemver.g:1646:2: rule__VersionRangeSetRequirement__Group__1__Impl
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
    // InternalSemver.g:1652:1: rule__VersionRangeSetRequirement__Group__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) ;
    public final void rule__VersionRangeSetRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1656:1: ( ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) )
            // InternalSemver.g:1657:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:1657:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            // InternalSemver.g:1658:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1659:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_DIGITS||LA24_0==RULE_LETTER_X||LA24_0==RULE_ASTERIX||(LA24_0>=34 && LA24_0<=41)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSemver.g:1659:3: rule__VersionRangeSetRequirement__Group_1__0
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
    // InternalSemver.g:1668:1: rule__VersionRangeSetRequirement__Group_1__0 : rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1672:1: ( rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 )
            // InternalSemver.g:1673:2: rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1
            {
            pushFollow(FOLLOW_15);
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
    // InternalSemver.g:1680:1: rule__VersionRangeSetRequirement__Group_1__0__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1684:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) )
            // InternalSemver.g:1685:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            {
            // InternalSemver.g:1685:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            // InternalSemver.g:1686:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }
            // InternalSemver.g:1687:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            // InternalSemver.g:1687:3: rule__VersionRangeSetRequirement__RangesAssignment_1_0
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
    // InternalSemver.g:1695:1: rule__VersionRangeSetRequirement__Group_1__1 : rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1699:1: ( rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 )
            // InternalSemver.g:1700:2: rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2
            {
            pushFollow(FOLLOW_15);
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
    // InternalSemver.g:1707:1: rule__VersionRangeSetRequirement__Group_1__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1711:1: ( ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) )
            // InternalSemver.g:1712:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            {
            // InternalSemver.g:1712:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            // InternalSemver.g:1713:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }
            // InternalSemver.g:1714:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            loop25:
            do {
                int alt25=2;
                alt25 = dfa25.predict(input);
                switch (alt25) {
            	case 1 :
            	    // InternalSemver.g:1714:3: rule__VersionRangeSetRequirement__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_16);
            	    rule__VersionRangeSetRequirement__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop25;
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
    // InternalSemver.g:1722:1: rule__VersionRangeSetRequirement__Group_1__2 : rule__VersionRangeSetRequirement__Group_1__2__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1726:1: ( rule__VersionRangeSetRequirement__Group_1__2__Impl )
            // InternalSemver.g:1727:2: rule__VersionRangeSetRequirement__Group_1__2__Impl
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
    // InternalSemver.g:1733:1: rule__VersionRangeSetRequirement__Group_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1737:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1738:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1738:1: ( ( RULE_WS )* )
            // InternalSemver.g:1739:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); 
            }
            // InternalSemver.g:1740:2: ( RULE_WS )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_WS) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSemver.g:1740:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

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
    // InternalSemver.g:1749:1: rule__VersionRangeSetRequirement__Group_1_1__0 : rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1753:1: ( rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 )
            // InternalSemver.g:1754:2: rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1
            {
            pushFollow(FOLLOW_15);
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
    // InternalSemver.g:1761:1: rule__VersionRangeSetRequirement__Group_1_1__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1765:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1766:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1766:1: ( ( RULE_WS )* )
            // InternalSemver.g:1767:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }
            // InternalSemver.g:1768:2: ( RULE_WS )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==RULE_WS) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSemver.g:1768:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

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
    // InternalSemver.g:1776:1: rule__VersionRangeSetRequirement__Group_1_1__1 : rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1780:1: ( rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 )
            // InternalSemver.g:1781:2: rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2
            {
            pushFollow(FOLLOW_17);
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
    // InternalSemver.g:1788:1: rule__VersionRangeSetRequirement__Group_1_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1792:1: ( ( '||' ) )
            // InternalSemver.g:1793:1: ( '||' )
            {
            // InternalSemver.g:1793:1: ( '||' )
            // InternalSemver.g:1794:2: '||'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); 
            }
            match(input,46,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1803:1: rule__VersionRangeSetRequirement__Group_1_1__2 : rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1807:1: ( rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 )
            // InternalSemver.g:1808:2: rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3
            {
            pushFollow(FOLLOW_17);
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
    // InternalSemver.g:1815:1: rule__VersionRangeSetRequirement__Group_1_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1819:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1820:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1820:1: ( ( RULE_WS )* )
            // InternalSemver.g:1821:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }
            // InternalSemver.g:1822:2: ( RULE_WS )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==RULE_WS) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSemver.g:1822:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

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
    // InternalSemver.g:1830:1: rule__VersionRangeSetRequirement__Group_1_1__3 : rule__VersionRangeSetRequirement__Group_1_1__3__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1834:1: ( rule__VersionRangeSetRequirement__Group_1_1__3__Impl )
            // InternalSemver.g:1835:2: rule__VersionRangeSetRequirement__Group_1_1__3__Impl
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
    // InternalSemver.g:1841:1: rule__VersionRangeSetRequirement__Group_1_1__3__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1845:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) )
            // InternalSemver.g:1846:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            {
            // InternalSemver.g:1846:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            // InternalSemver.g:1847:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }
            // InternalSemver.g:1848:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            // InternalSemver.g:1848:3: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
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
    // InternalSemver.g:1857:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1861:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemver.g:1862:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:1869:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1873:1: ( ( () ) )
            // InternalSemver.g:1874:1: ( () )
            {
            // InternalSemver.g:1874:1: ( () )
            // InternalSemver.g:1875:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }
            // InternalSemver.g:1876:2: ()
            // InternalSemver.g:1876:3: 
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
    // InternalSemver.g:1884:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1888:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemver.g:1889:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:1896:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1900:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemver.g:1901:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemver.g:1901:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemver.g:1902:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }
            // InternalSemver.g:1903:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemver.g:1903:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSemver.g:1911:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1915:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemver.g:1916:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
            {
            pushFollow(FOLLOW_18);
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
    // InternalSemver.g:1923:1: rule__HyphenVersionRange__Group__2__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1927:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:1928:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:1928:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:1929:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:1929:2: ( ( RULE_WS ) )
            // InternalSemver.g:1930:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:1931:3: ( RULE_WS )
            // InternalSemver.g:1931:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }

            }

            // InternalSemver.g:1934:2: ( ( RULE_WS )* )
            // InternalSemver.g:1935:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:1936:3: ( RULE_WS )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==RULE_WS) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSemver.g:1936:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
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
    // $ANTLR end "rule__HyphenVersionRange__Group__2__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__3"
    // InternalSemver.g:1945:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1949:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSemver.g:1950:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:1957:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1961:1: ( ( '-' ) )
            // InternalSemver.g:1962:1: ( '-' )
            {
            // InternalSemver.g:1962:1: ( '-' )
            // InternalSemver.g:1963:2: '-'
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
    // InternalSemver.g:1972:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1976:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSemver.g:1977:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:1984:1: rule__HyphenVersionRange__Group__4__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1988:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:1989:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:1989:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:1990:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:1990:2: ( ( RULE_WS ) )
            // InternalSemver.g:1991:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:1992:3: ( RULE_WS )
            // InternalSemver.g:1992:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }

            }

            // InternalSemver.g:1995:2: ( ( RULE_WS )* )
            // InternalSemver.g:1996:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:1997:3: ( RULE_WS )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_WS) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSemver.g:1997:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
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
    // $ANTLR end "rule__HyphenVersionRange__Group__4__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__5"
    // InternalSemver.g:2006:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2010:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSemver.g:2011:2: rule__HyphenVersionRange__Group__5__Impl
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
    // InternalSemver.g:2017:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2021:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSemver.g:2022:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSemver.g:2022:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSemver.g:2023:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }
            // InternalSemver.g:2024:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSemver.g:2024:3: rule__HyphenVersionRange__ToAssignment_5
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
    // InternalSemver.g:2033:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2037:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSemver.g:2038:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2045:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2049:1: ( ( () ) )
            // InternalSemver.g:2050:1: ( () )
            {
            // InternalSemver.g:2050:1: ( () )
            // InternalSemver.g:2051:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }
            // InternalSemver.g:2052:2: ()
            // InternalSemver.g:2052:3: 
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
    // InternalSemver.g:2060:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2064:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSemver.g:2065:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:2072:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2076:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSemver.g:2077:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSemver.g:2077:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSemver.g:2078:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }
            // InternalSemver.g:2079:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSemver.g:2079:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
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
    // InternalSemver.g:2087:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2091:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSemver.g:2092:2: rule__VersionRangeContraint__Group__2__Impl
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
    // InternalSemver.g:2098:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2102:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSemver.g:2103:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSemver.g:2103:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSemver.g:2104:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }
            // InternalSemver.g:2105:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop31:
            do {
                int alt31=2;
                alt31 = dfa31.predict(input);
                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:2105:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__VersionRangeContraint__Group_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop31;
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
    // InternalSemver.g:2114:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2118:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSemver.g:2119:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2126:1: rule__VersionRangeContraint__Group_2__0__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2130:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2131:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2131:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2132:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2132:2: ( ( RULE_WS ) )
            // InternalSemver.g:2133:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2134:3: ( RULE_WS )
            // InternalSemver.g:2134:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }

            }

            // InternalSemver.g:2137:2: ( ( RULE_WS )* )
            // InternalSemver.g:2138:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2139:3: ( RULE_WS )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_WS) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:2139:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
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
    // $ANTLR end "rule__VersionRangeContraint__Group_2__0__Impl"


    // $ANTLR start "rule__VersionRangeContraint__Group_2__1"
    // InternalSemver.g:2148:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2152:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSemver.g:2153:2: rule__VersionRangeContraint__Group_2__1__Impl
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
    // InternalSemver.g:2159:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2163:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSemver.g:2164:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSemver.g:2164:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSemver.g:2165:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }
            // InternalSemver.g:2166:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSemver.g:2166:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
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
    // InternalSemver.g:2175:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2179:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemver.g:2180:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2187:1: rule__SimpleVersion__Group__0__Impl : ( () ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2191:1: ( ( () ) )
            // InternalSemver.g:2192:1: ( () )
            {
            // InternalSemver.g:2192:1: ( () )
            // InternalSemver.g:2193:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0()); 
            }
            // InternalSemver.g:2194:2: ()
            // InternalSemver.g:2194:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__0__Impl"


    // $ANTLR start "rule__SimpleVersion__Group__1"
    // InternalSemver.g:2202:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2206:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemver.g:2207:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2214:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__Group_1__0 )* ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2218:1: ( ( ( rule__SimpleVersion__Group_1__0 )* ) )
            // InternalSemver.g:2219:1: ( ( rule__SimpleVersion__Group_1__0 )* )
            {
            // InternalSemver.g:2219:1: ( ( rule__SimpleVersion__Group_1__0 )* )
            // InternalSemver.g:2220:2: ( rule__SimpleVersion__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup_1()); 
            }
            // InternalSemver.g:2221:2: ( rule__SimpleVersion__Group_1__0 )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( ((LA33_0>=34 && LA33_0<=41)) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSemver.g:2221:3: rule__SimpleVersion__Group_1__0
            	    {
            	    pushFollow(FOLLOW_19);
            	    rule__SimpleVersion__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getGroup_1()); 
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
    // InternalSemver.g:2229:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2233:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSemver.g:2234:2: rule__SimpleVersion__Group__2__Impl
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
    // InternalSemver.g:2240:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2244:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSemver.g:2245:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSemver.g:2245:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSemver.g:2246:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            }
            // InternalSemver.g:2247:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSemver.g:2247:3: rule__SimpleVersion__NumberAssignment_2
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


    // $ANTLR start "rule__SimpleVersion__Group_1__0"
    // InternalSemver.g:2256:1: rule__SimpleVersion__Group_1__0 : rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1 ;
    public final void rule__SimpleVersion__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2260:1: ( rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1 )
            // InternalSemver.g:2261:2: rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1
            {
            pushFollow(FOLLOW_8);
            rule__SimpleVersion__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group_1__1();

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
    // $ANTLR end "rule__SimpleVersion__Group_1__0"


    // $ANTLR start "rule__SimpleVersion__Group_1__0__Impl"
    // InternalSemver.g:2268:1: rule__SimpleVersion__Group_1__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) ) ;
    public final void rule__SimpleVersion__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2272:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) ) )
            // InternalSemver.g:2273:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) )
            {
            // InternalSemver.g:2273:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) )
            // InternalSemver.g:2274:2: ( rule__SimpleVersion__ComparatorsAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1_0()); 
            }
            // InternalSemver.g:2275:2: ( rule__SimpleVersion__ComparatorsAssignment_1_0 )
            // InternalSemver.g:2275:3: rule__SimpleVersion__ComparatorsAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__ComparatorsAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1_0()); 
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
    // $ANTLR end "rule__SimpleVersion__Group_1__0__Impl"


    // $ANTLR start "rule__SimpleVersion__Group_1__1"
    // InternalSemver.g:2283:1: rule__SimpleVersion__Group_1__1 : rule__SimpleVersion__Group_1__1__Impl ;
    public final void rule__SimpleVersion__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2287:1: ( rule__SimpleVersion__Group_1__1__Impl )
            // InternalSemver.g:2288:2: rule__SimpleVersion__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group_1__1__Impl();

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
    // $ANTLR end "rule__SimpleVersion__Group_1__1"


    // $ANTLR start "rule__SimpleVersion__Group_1__1__Impl"
    // InternalSemver.g:2294:1: rule__SimpleVersion__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__SimpleVersion__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2298:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2299:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2299:1: ( ( RULE_WS )* )
            // InternalSemver.g:2300:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:2301:2: ( RULE_WS )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==RULE_WS) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSemver.g:2301:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_7); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_1_1()); 
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
    // $ANTLR end "rule__SimpleVersion__Group_1__1__Impl"


    // $ANTLR start "rule__VersionNumber__Group__0"
    // InternalSemver.g:2310:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2314:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemver.g:2315:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
            {
            pushFollow(FOLLOW_20);
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
    // InternalSemver.g:2322:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2326:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemver.g:2327:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemver.g:2327:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemver.g:2328:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }
            // InternalSemver.g:2329:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemver.g:2329:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSemver.g:2337:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2341:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSemver.g:2342:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
            {
            pushFollow(FOLLOW_20);
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
    // InternalSemver.g:2349:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2353:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemver.g:2354:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemver.g:2354:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemver.g:2355:2: ( rule__VersionNumber__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }
            // InternalSemver.g:2356:2: ( rule__VersionNumber__Group_1__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==31) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalSemver.g:2356:3: rule__VersionNumber__Group_1__0
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
    // InternalSemver.g:2364:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2368:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSemver.g:2369:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSemver.g:2375:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2379:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSemver.g:2380:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSemver.g:2380:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSemver.g:2381:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }
            // InternalSemver.g:2382:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==29||LA36_0==47) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalSemver.g:2382:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSemver.g:2391:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2395:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemver.g:2396:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2403:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2407:1: ( ( '.' ) )
            // InternalSemver.g:2408:1: ( '.' )
            {
            // InternalSemver.g:2408:1: ( '.' )
            // InternalSemver.g:2409:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2418:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2422:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemver.g:2423:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
            {
            pushFollow(FOLLOW_21);
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
    // InternalSemver.g:2430:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2434:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemver.g:2435:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemver.g:2435:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemver.g:2436:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }
            // InternalSemver.g:2437:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemver.g:2437:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSemver.g:2445:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2449:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemver.g:2450:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSemver.g:2456:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2460:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemver.g:2461:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemver.g:2461:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemver.g:2462:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }
            // InternalSemver.g:2463:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==31) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalSemver.g:2463:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSemver.g:2472:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2476:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemver.g:2477:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2484:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2488:1: ( ( '.' ) )
            // InternalSemver.g:2489:1: ( '.' )
            {
            // InternalSemver.g:2489:1: ( '.' )
            // InternalSemver.g:2490:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2499:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2503:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemver.g:2504:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
            {
            pushFollow(FOLLOW_21);
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
    // InternalSemver.g:2511:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2515:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSemver.g:2516:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSemver.g:2516:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSemver.g:2517:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }
            // InternalSemver.g:2518:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSemver.g:2518:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSemver.g:2526:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2530:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemver.g:2531:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSemver.g:2537:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2541:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSemver.g:2542:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSemver.g:2542:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSemver.g:2543:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }
            // InternalSemver.g:2544:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==31) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSemver.g:2544:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop38;
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
    // InternalSemver.g:2553:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2557:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSemver.g:2558:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2565:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2569:1: ( ( '.' ) )
            // InternalSemver.g:2570:1: ( '.' )
            {
            // InternalSemver.g:2570:1: ( '.' )
            // InternalSemver.g:2571:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2580:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2584:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSemver.g:2585:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSemver.g:2591:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2595:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSemver.g:2596:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSemver.g:2596:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSemver.g:2597:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }
            // InternalSemver.g:2598:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSemver.g:2598:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSemver.g:2607:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2611:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemver.g:2612:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
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
    // InternalSemver.g:2619:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2623:1: ( ( '-' ) )
            // InternalSemver.g:2624:1: ( '-' )
            {
            // InternalSemver.g:2624:1: ( '-' )
            // InternalSemver.g:2625:2: '-'
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
    // InternalSemver.g:2634:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2638:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSemver.g:2639:2: rule__Qualifier__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0__1__Impl();

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
    // InternalSemver.g:2645:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2649:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemver.g:2650:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemver.g:2650:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemver.g:2651:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }
            // InternalSemver.g:2652:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemver.g:2652:3: rule__Qualifier__PreReleaseAssignment_0_1
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


    // $ANTLR start "rule__Qualifier__Group_1__0"
    // InternalSemver.g:2661:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2665:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemver.g:2666:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
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
    // InternalSemver.g:2673:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2677:1: ( ( '+' ) )
            // InternalSemver.g:2678:1: ( '+' )
            {
            // InternalSemver.g:2678:1: ( '+' )
            // InternalSemver.g:2679:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2688:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2692:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemver.g:2693:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSemver.g:2699:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2703:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemver.g:2704:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemver.g:2704:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemver.g:2705:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }
            // InternalSemver.g:2706:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemver.g:2706:3: rule__Qualifier__BuildMetadataAssignment_1_1
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


    // $ANTLR start "rule__Qualifier__Group_2__0"
    // InternalSemver.g:2715:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2719:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSemver.g:2720:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
            {
            pushFollow(FOLLOW_12);
            rule__Qualifier__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__1();

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
    // $ANTLR end "rule__Qualifier__Group_2__0"


    // $ANTLR start "rule__Qualifier__Group_2__0__Impl"
    // InternalSemver.g:2727:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2731:1: ( ( '-' ) )
            // InternalSemver.g:2732:1: ( '-' )
            {
            // InternalSemver.g:2732:1: ( '-' )
            // InternalSemver.g:2733:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 
            }
            match(input,47,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 
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
    // $ANTLR end "rule__Qualifier__Group_2__0__Impl"


    // $ANTLR start "rule__Qualifier__Group_2__1"
    // InternalSemver.g:2742:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2746:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSemver.g:2747:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
            {
            pushFollow(FOLLOW_23);
            rule__Qualifier__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__2();

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
    // $ANTLR end "rule__Qualifier__Group_2__1"


    // $ANTLR start "rule__Qualifier__Group_2__1__Impl"
    // InternalSemver.g:2754:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2758:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSemver.g:2759:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSemver.g:2759:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSemver.g:2760:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            }
            // InternalSemver.g:2761:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSemver.g:2761:3: rule__Qualifier__PreReleaseAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__PreReleaseAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
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
    // $ANTLR end "rule__Qualifier__Group_2__1__Impl"


    // $ANTLR start "rule__Qualifier__Group_2__2"
    // InternalSemver.g:2769:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2773:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSemver.g:2774:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
            {
            pushFollow(FOLLOW_12);
            rule__Qualifier__Group_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__3();

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
    // $ANTLR end "rule__Qualifier__Group_2__2"


    // $ANTLR start "rule__Qualifier__Group_2__2__Impl"
    // InternalSemver.g:2781:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2785:1: ( ( '+' ) )
            // InternalSemver.g:2786:1: ( '+' )
            {
            // InternalSemver.g:2786:1: ( '+' )
            // InternalSemver.g:2787:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 
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
    // $ANTLR end "rule__Qualifier__Group_2__2__Impl"


    // $ANTLR start "rule__Qualifier__Group_2__3"
    // InternalSemver.g:2796:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2800:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSemver.g:2801:2: rule__Qualifier__Group_2__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__3__Impl();

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
    // $ANTLR end "rule__Qualifier__Group_2__3"


    // $ANTLR start "rule__Qualifier__Group_2__3__Impl"
    // InternalSemver.g:2807:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2811:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSemver.g:2812:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSemver.g:2812:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSemver.g:2813:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            }
            // InternalSemver.g:2814:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSemver.g:2814:3: rule__Qualifier__BuildMetadataAssignment_2_3
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__BuildMetadataAssignment_2_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
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
    // $ANTLR end "rule__Qualifier__Group_2__3__Impl"


    // $ANTLR start "rule__QualifierTag__Group__0"
    // InternalSemver.g:2823:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2827:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSemver.g:2828:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
            {
            pushFollow(FOLLOW_21);
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
    // InternalSemver.g:2835:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2839:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSemver.g:2840:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSemver.g:2840:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSemver.g:2841:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }
            // InternalSemver.g:2842:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSemver.g:2842:3: rule__QualifierTag__PartsAssignment_0
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
    // InternalSemver.g:2850:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2854:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSemver.g:2855:2: rule__QualifierTag__Group__1__Impl
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
    // InternalSemver.g:2861:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2865:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSemver.g:2866:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSemver.g:2866:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSemver.g:2867:2: ( rule__QualifierTag__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }
            // InternalSemver.g:2868:2: ( rule__QualifierTag__Group_1__0 )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==31) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSemver.g:2868:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__QualifierTag__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop39;
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
    // InternalSemver.g:2877:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2881:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSemver.g:2882:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
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
    // InternalSemver.g:2889:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2893:1: ( ( '.' ) )
            // InternalSemver.g:2894:1: ( '.' )
            {
            // InternalSemver.g:2894:1: ( '.' )
            // InternalSemver.g:2895:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2904:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2908:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSemver.g:2909:2: rule__QualifierTag__Group_1__1__Impl
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
    // InternalSemver.g:2915:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2919:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSemver.g:2920:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSemver.g:2920:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSemver.g:2921:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }
            // InternalSemver.g:2922:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSemver.g:2922:3: rule__QualifierTag__PartsAssignment_1_1
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


    // $ANTLR start "rule__TAG__Group__0"
    // InternalSemver.g:2931:1: rule__TAG__Group__0 : rule__TAG__Group__0__Impl rule__TAG__Group__1 ;
    public final void rule__TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2935:1: ( rule__TAG__Group__0__Impl rule__TAG__Group__1 )
            // InternalSemver.g:2936:2: rule__TAG__Group__0__Impl rule__TAG__Group__1
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
    // InternalSemver.g:2943:1: rule__TAG__Group__0__Impl : ( ruleLETTER_NO_V ) ;
    public final void rule__TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2947:1: ( ( ruleLETTER_NO_V ) )
            // InternalSemver.g:2948:1: ( ruleLETTER_NO_V )
            {
            // InternalSemver.g:2948:1: ( ruleLETTER_NO_V )
            // InternalSemver.g:2949:2: ruleLETTER_NO_V
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getLETTER_NO_VParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLETTER_NO_V();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getLETTER_NO_VParserRuleCall_0()); 
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
    // InternalSemver.g:2958:1: rule__TAG__Group__1 : rule__TAG__Group__1__Impl ;
    public final void rule__TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2962:1: ( rule__TAG__Group__1__Impl )
            // InternalSemver.g:2963:2: rule__TAG__Group__1__Impl
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
    // InternalSemver.g:2969:1: rule__TAG__Group__1__Impl : ( ( ruleALPHA_NUMERIC_CHARS )* ) ;
    public final void rule__TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2973:1: ( ( ( ruleALPHA_NUMERIC_CHARS )* ) )
            // InternalSemver.g:2974:1: ( ( ruleALPHA_NUMERIC_CHARS )* )
            {
            // InternalSemver.g:2974:1: ( ( ruleALPHA_NUMERIC_CHARS )* )
            // InternalSemver.g:2975:2: ( ruleALPHA_NUMERIC_CHARS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1()); 
            }
            // InternalSemver.g:2976:2: ( ruleALPHA_NUMERIC_CHARS )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=RULE_DIGITS && LA40_0<=RULE_LETTER_NO_VX)||LA40_0==47) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSemver.g:2976:3: ruleALPHA_NUMERIC_CHARS
            	    {
            	    pushFollow(FOLLOW_24);
            	    ruleALPHA_NUMERIC_CHARS();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);

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


    // $ANTLR start "rule__PATH__Group__0"
    // InternalSemver.g:2985:1: rule__PATH__Group__0 : rule__PATH__Group__0__Impl rule__PATH__Group__1 ;
    public final void rule__PATH__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2989:1: ( rule__PATH__Group__0__Impl rule__PATH__Group__1 )
            // InternalSemver.g:2990:2: rule__PATH__Group__0__Impl rule__PATH__Group__1
            {
            pushFollow(FOLLOW_14);
            rule__PATH__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__PATH__Group__1();

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
    // $ANTLR end "rule__PATH__Group__0"


    // $ANTLR start "rule__PATH__Group__0__Impl"
    // InternalSemver.g:2997:1: rule__PATH__Group__0__Impl : ( ( rule__PATH__Group_0__0 )* ) ;
    public final void rule__PATH__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3001:1: ( ( ( rule__PATH__Group_0__0 )* ) )
            // InternalSemver.g:3002:1: ( ( rule__PATH__Group_0__0 )* )
            {
            // InternalSemver.g:3002:1: ( ( rule__PATH__Group_0__0 )* )
            // InternalSemver.g:3003:2: ( rule__PATH__Group_0__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getGroup_0()); 
            }
            // InternalSemver.g:3004:2: ( rule__PATH__Group_0__0 )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( ((LA41_0>=RULE_LETTER_X && LA41_0<=RULE_LETTER_NO_VX)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSemver.g:3004:3: rule__PATH__Group_0__0
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__PATH__Group_0__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getGroup_0()); 
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
    // $ANTLR end "rule__PATH__Group__0__Impl"


    // $ANTLR start "rule__PATH__Group__1"
    // InternalSemver.g:3012:1: rule__PATH__Group__1 : rule__PATH__Group__1__Impl rule__PATH__Group__2 ;
    public final void rule__PATH__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3016:1: ( rule__PATH__Group__1__Impl rule__PATH__Group__2 )
            // InternalSemver.g:3017:2: rule__PATH__Group__1__Impl rule__PATH__Group__2
            {
            pushFollow(FOLLOW_25);
            rule__PATH__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__PATH__Group__2();

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
    // $ANTLR end "rule__PATH__Group__1"


    // $ANTLR start "rule__PATH__Group__1__Impl"
    // InternalSemver.g:3024:1: rule__PATH__Group__1__Impl : ( ( ( rule__PATH__Group_1__0 ) ) ( ( rule__PATH__Group_1__0 )* ) ) ;
    public final void rule__PATH__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3028:1: ( ( ( ( rule__PATH__Group_1__0 ) ) ( ( rule__PATH__Group_1__0 )* ) ) )
            // InternalSemver.g:3029:1: ( ( ( rule__PATH__Group_1__0 ) ) ( ( rule__PATH__Group_1__0 )* ) )
            {
            // InternalSemver.g:3029:1: ( ( ( rule__PATH__Group_1__0 ) ) ( ( rule__PATH__Group_1__0 )* ) )
            // InternalSemver.g:3030:2: ( ( rule__PATH__Group_1__0 ) ) ( ( rule__PATH__Group_1__0 )* )
            {
            // InternalSemver.g:3030:2: ( ( rule__PATH__Group_1__0 ) )
            // InternalSemver.g:3031:3: ( rule__PATH__Group_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getGroup_1()); 
            }
            // InternalSemver.g:3032:3: ( rule__PATH__Group_1__0 )
            // InternalSemver.g:3032:4: rule__PATH__Group_1__0
            {
            pushFollow(FOLLOW_26);
            rule__PATH__Group_1__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getGroup_1()); 
            }

            }

            // InternalSemver.g:3035:2: ( ( rule__PATH__Group_1__0 )* )
            // InternalSemver.g:3036:3: ( rule__PATH__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getGroup_1()); 
            }
            // InternalSemver.g:3037:3: ( rule__PATH__Group_1__0 )*
            loop42:
            do {
                int alt42=2;
                alt42 = dfa42.predict(input);
                switch (alt42) {
            	case 1 :
            	    // InternalSemver.g:3037:4: rule__PATH__Group_1__0
            	    {
            	    pushFollow(FOLLOW_26);
            	    rule__PATH__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getGroup_1()); 
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
    // $ANTLR end "rule__PATH__Group__1__Impl"


    // $ANTLR start "rule__PATH__Group__2"
    // InternalSemver.g:3046:1: rule__PATH__Group__2 : rule__PATH__Group__2__Impl ;
    public final void rule__PATH__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3050:1: ( rule__PATH__Group__2__Impl )
            // InternalSemver.g:3051:2: rule__PATH__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PATH__Group__2__Impl();

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
    // $ANTLR end "rule__PATH__Group__2"


    // $ANTLR start "rule__PATH__Group__2__Impl"
    // InternalSemver.g:3057:1: rule__PATH__Group__2__Impl : ( ( rule__PATH__Alternatives_2 )* ) ;
    public final void rule__PATH__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3061:1: ( ( ( rule__PATH__Alternatives_2 )* ) )
            // InternalSemver.g:3062:1: ( ( rule__PATH__Alternatives_2 )* )
            {
            // InternalSemver.g:3062:1: ( ( rule__PATH__Alternatives_2 )* )
            // InternalSemver.g:3063:2: ( rule__PATH__Alternatives_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:3064:2: ( rule__PATH__Alternatives_2 )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=30 && LA43_0<=31)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSemver.g:3064:3: rule__PATH__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_27);
            	    rule__PATH__Alternatives_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_2()); 
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
    // $ANTLR end "rule__PATH__Group__2__Impl"


    // $ANTLR start "rule__PATH__Group_0__0"
    // InternalSemver.g:3073:1: rule__PATH__Group_0__0 : rule__PATH__Group_0__0__Impl rule__PATH__Group_0__1 ;
    public final void rule__PATH__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3077:1: ( rule__PATH__Group_0__0__Impl rule__PATH__Group_0__1 )
            // InternalSemver.g:3078:2: rule__PATH__Group_0__0__Impl rule__PATH__Group_0__1
            {
            pushFollow(FOLLOW_28);
            rule__PATH__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__PATH__Group_0__1();

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
    // $ANTLR end "rule__PATH__Group_0__0"


    // $ANTLR start "rule__PATH__Group_0__0__Impl"
    // InternalSemver.g:3085:1: rule__PATH__Group_0__0__Impl : ( ruleLETTER_NO_V ) ;
    public final void rule__PATH__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3089:1: ( ( ruleLETTER_NO_V ) )
            // InternalSemver.g:3090:1: ( ruleLETTER_NO_V )
            {
            // InternalSemver.g:3090:1: ( ruleLETTER_NO_V )
            // InternalSemver.g:3091:2: ruleLETTER_NO_V
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getLETTER_NO_VParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLETTER_NO_V();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getLETTER_NO_VParserRuleCall_0_0()); 
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
    // $ANTLR end "rule__PATH__Group_0__0__Impl"


    // $ANTLR start "rule__PATH__Group_0__1"
    // InternalSemver.g:3100:1: rule__PATH__Group_0__1 : rule__PATH__Group_0__1__Impl ;
    public final void rule__PATH__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3104:1: ( rule__PATH__Group_0__1__Impl )
            // InternalSemver.g:3105:2: rule__PATH__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PATH__Group_0__1__Impl();

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
    // $ANTLR end "rule__PATH__Group_0__1"


    // $ANTLR start "rule__PATH__Group_0__1__Impl"
    // InternalSemver.g:3111:1: rule__PATH__Group_0__1__Impl : ( ruleALPHA_NUMERIC_CHAR ) ;
    public final void rule__PATH__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3115:1: ( ( ruleALPHA_NUMERIC_CHAR ) )
            // InternalSemver.g:3116:1: ( ruleALPHA_NUMERIC_CHAR )
            {
            // InternalSemver.g:3116:1: ( ruleALPHA_NUMERIC_CHAR )
            // InternalSemver.g:3117:2: ruleALPHA_NUMERIC_CHAR
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARParserRuleCall_0_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHAR();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARParserRuleCall_0_1()); 
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
    // $ANTLR end "rule__PATH__Group_0__1__Impl"


    // $ANTLR start "rule__PATH__Group_1__0"
    // InternalSemver.g:3127:1: rule__PATH__Group_1__0 : rule__PATH__Group_1__0__Impl rule__PATH__Group_1__1 ;
    public final void rule__PATH__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3131:1: ( rule__PATH__Group_1__0__Impl rule__PATH__Group_1__1 )
            // InternalSemver.g:3132:2: rule__PATH__Group_1__0__Impl rule__PATH__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__PATH__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__PATH__Group_1__1();

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
    // $ANTLR end "rule__PATH__Group_1__0"


    // $ANTLR start "rule__PATH__Group_1__0__Impl"
    // InternalSemver.g:3139:1: rule__PATH__Group_1__0__Impl : ( ( ( rule__PATH__Alternatives_1_0 ) ) ( ( rule__PATH__Alternatives_1_0 )* ) ) ;
    public final void rule__PATH__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3143:1: ( ( ( ( rule__PATH__Alternatives_1_0 ) ) ( ( rule__PATH__Alternatives_1_0 )* ) ) )
            // InternalSemver.g:3144:1: ( ( ( rule__PATH__Alternatives_1_0 ) ) ( ( rule__PATH__Alternatives_1_0 )* ) )
            {
            // InternalSemver.g:3144:1: ( ( ( rule__PATH__Alternatives_1_0 ) ) ( ( rule__PATH__Alternatives_1_0 )* ) )
            // InternalSemver.g:3145:2: ( ( rule__PATH__Alternatives_1_0 ) ) ( ( rule__PATH__Alternatives_1_0 )* )
            {
            // InternalSemver.g:3145:2: ( ( rule__PATH__Alternatives_1_0 ) )
            // InternalSemver.g:3146:3: ( rule__PATH__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:3147:3: ( rule__PATH__Alternatives_1_0 )
            // InternalSemver.g:3147:4: rule__PATH__Alternatives_1_0
            {
            pushFollow(FOLLOW_26);
            rule__PATH__Alternatives_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_1_0()); 
            }

            }

            // InternalSemver.g:3150:2: ( ( rule__PATH__Alternatives_1_0 )* )
            // InternalSemver.g:3151:3: ( rule__PATH__Alternatives_1_0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:3152:3: ( rule__PATH__Alternatives_1_0 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( ((LA44_0>=30 && LA44_0<=31)) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSemver.g:3152:4: rule__PATH__Alternatives_1_0
            	    {
            	    pushFollow(FOLLOW_26);
            	    rule__PATH__Alternatives_1_0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_1_0()); 
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
    // $ANTLR end "rule__PATH__Group_1__0__Impl"


    // $ANTLR start "rule__PATH__Group_1__1"
    // InternalSemver.g:3161:1: rule__PATH__Group_1__1 : rule__PATH__Group_1__1__Impl ;
    public final void rule__PATH__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3165:1: ( rule__PATH__Group_1__1__Impl )
            // InternalSemver.g:3166:2: rule__PATH__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PATH__Group_1__1__Impl();

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
    // $ANTLR end "rule__PATH__Group_1__1"


    // $ANTLR start "rule__PATH__Group_1__1__Impl"
    // InternalSemver.g:3172:1: rule__PATH__Group_1__1__Impl : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__PATH__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3176:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3177:1: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3177:1: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3178:2: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1_1()); 
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
    // $ANTLR end "rule__PATH__Group_1__1__Impl"


    // $ANTLR start "rule__URL__Group__0"
    // InternalSemver.g:3188:1: rule__URL__Group__0 : rule__URL__Group__0__Impl rule__URL__Group__1 ;
    public final void rule__URL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3192:1: ( rule__URL__Group__0__Impl rule__URL__Group__1 )
            // InternalSemver.g:3193:2: rule__URL__Group__0__Impl rule__URL__Group__1
            {
            pushFollow(FOLLOW_10);
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
    // InternalSemver.g:3200:1: rule__URL__Group__0__Impl : ( ( rule__URL__Group_0__0 )* ) ;
    public final void rule__URL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3204:1: ( ( ( rule__URL__Group_0__0 )* ) )
            // InternalSemver.g:3205:1: ( ( rule__URL__Group_0__0 )* )
            {
            // InternalSemver.g:3205:1: ( ( rule__URL__Group_0__0 )* )
            // InternalSemver.g:3206:2: ( rule__URL__Group_0__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup_0()); 
            }
            // InternalSemver.g:3207:2: ( rule__URL__Group_0__0 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=RULE_LETTER_X && LA45_0<=RULE_LETTER_NO_VX)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSemver.g:3207:3: rule__URL__Group_0__0
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__URL__Group_0__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getGroup_0()); 
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
    // InternalSemver.g:3215:1: rule__URL__Group__1 : rule__URL__Group__1__Impl ;
    public final void rule__URL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3219:1: ( rule__URL__Group__1__Impl )
            // InternalSemver.g:3220:2: rule__URL__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URL__Group__1__Impl();

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
    // InternalSemver.g:3226:1: rule__URL__Group__1__Impl : ( ( ( rule__URL__Group_1__0 ) ) ( ( rule__URL__Group_1__0 )* ) ) ;
    public final void rule__URL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3230:1: ( ( ( ( rule__URL__Group_1__0 ) ) ( ( rule__URL__Group_1__0 )* ) ) )
            // InternalSemver.g:3231:1: ( ( ( rule__URL__Group_1__0 ) ) ( ( rule__URL__Group_1__0 )* ) )
            {
            // InternalSemver.g:3231:1: ( ( ( rule__URL__Group_1__0 ) ) ( ( rule__URL__Group_1__0 )* ) )
            // InternalSemver.g:3232:2: ( ( rule__URL__Group_1__0 ) ) ( ( rule__URL__Group_1__0 )* )
            {
            // InternalSemver.g:3232:2: ( ( rule__URL__Group_1__0 ) )
            // InternalSemver.g:3233:3: ( rule__URL__Group_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup_1()); 
            }
            // InternalSemver.g:3234:3: ( rule__URL__Group_1__0 )
            // InternalSemver.g:3234:4: rule__URL__Group_1__0
            {
            pushFollow(FOLLOW_29);
            rule__URL__Group_1__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getGroup_1()); 
            }

            }

            // InternalSemver.g:3237:2: ( ( rule__URL__Group_1__0 )* )
            // InternalSemver.g:3238:3: ( rule__URL__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup_1()); 
            }
            // InternalSemver.g:3239:3: ( rule__URL__Group_1__0 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=30 && LA46_0<=33)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSemver.g:3239:4: rule__URL__Group_1__0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__URL__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getGroup_1()); 
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
    // $ANTLR end "rule__URL__Group__1__Impl"


    // $ANTLR start "rule__URL__Group_0__0"
    // InternalSemver.g:3249:1: rule__URL__Group_0__0 : rule__URL__Group_0__0__Impl rule__URL__Group_0__1 ;
    public final void rule__URL__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3253:1: ( rule__URL__Group_0__0__Impl rule__URL__Group_0__1 )
            // InternalSemver.g:3254:2: rule__URL__Group_0__0__Impl rule__URL__Group_0__1
            {
            pushFollow(FOLLOW_28);
            rule__URL__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL__Group_0__1();

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
    // $ANTLR end "rule__URL__Group_0__0"


    // $ANTLR start "rule__URL__Group_0__0__Impl"
    // InternalSemver.g:3261:1: rule__URL__Group_0__0__Impl : ( ruleLETTER_NO_V ) ;
    public final void rule__URL__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3265:1: ( ( ruleLETTER_NO_V ) )
            // InternalSemver.g:3266:1: ( ruleLETTER_NO_V )
            {
            // InternalSemver.g:3266:1: ( ruleLETTER_NO_V )
            // InternalSemver.g:3267:2: ruleLETTER_NO_V
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getLETTER_NO_VParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLETTER_NO_V();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getLETTER_NO_VParserRuleCall_0_0()); 
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
    // $ANTLR end "rule__URL__Group_0__0__Impl"


    // $ANTLR start "rule__URL__Group_0__1"
    // InternalSemver.g:3276:1: rule__URL__Group_0__1 : rule__URL__Group_0__1__Impl ;
    public final void rule__URL__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3280:1: ( rule__URL__Group_0__1__Impl )
            // InternalSemver.g:3281:2: rule__URL__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URL__Group_0__1__Impl();

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
    // $ANTLR end "rule__URL__Group_0__1"


    // $ANTLR start "rule__URL__Group_0__1__Impl"
    // InternalSemver.g:3287:1: rule__URL__Group_0__1__Impl : ( ruleALPHA_NUMERIC_CHAR ) ;
    public final void rule__URL__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3291:1: ( ( ruleALPHA_NUMERIC_CHAR ) )
            // InternalSemver.g:3292:1: ( ruleALPHA_NUMERIC_CHAR )
            {
            // InternalSemver.g:3292:1: ( ruleALPHA_NUMERIC_CHAR )
            // InternalSemver.g:3293:2: ruleALPHA_NUMERIC_CHAR
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group_0__1__Impl"


    // $ANTLR start "rule__URL__Group_1__0"
    // InternalSemver.g:3303:1: rule__URL__Group_1__0 : rule__URL__Group_1__0__Impl rule__URL__Group_1__1 ;
    public final void rule__URL__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3307:1: ( rule__URL__Group_1__0__Impl rule__URL__Group_1__1 )
            // InternalSemver.g:3308:2: rule__URL__Group_1__0__Impl rule__URL__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__URL__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL__Group_1__1();

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
    // $ANTLR end "rule__URL__Group_1__0"


    // $ANTLR start "rule__URL__Group_1__0__Impl"
    // InternalSemver.g:3315:1: rule__URL__Group_1__0__Impl : ( ( ( rule__URL__Alternatives_1_0 ) ) ( ( rule__URL__Alternatives_1_0 )* ) ) ;
    public final void rule__URL__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3319:1: ( ( ( ( rule__URL__Alternatives_1_0 ) ) ( ( rule__URL__Alternatives_1_0 )* ) ) )
            // InternalSemver.g:3320:1: ( ( ( rule__URL__Alternatives_1_0 ) ) ( ( rule__URL__Alternatives_1_0 )* ) )
            {
            // InternalSemver.g:3320:1: ( ( ( rule__URL__Alternatives_1_0 ) ) ( ( rule__URL__Alternatives_1_0 )* ) )
            // InternalSemver.g:3321:2: ( ( rule__URL__Alternatives_1_0 ) ) ( ( rule__URL__Alternatives_1_0 )* )
            {
            // InternalSemver.g:3321:2: ( ( rule__URL__Alternatives_1_0 ) )
            // InternalSemver.g:3322:3: ( rule__URL__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:3323:3: ( rule__URL__Alternatives_1_0 )
            // InternalSemver.g:3323:4: rule__URL__Alternatives_1_0
            {
            pushFollow(FOLLOW_29);
            rule__URL__Alternatives_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getAlternatives_1_0()); 
            }

            }

            // InternalSemver.g:3326:2: ( ( rule__URL__Alternatives_1_0 )* )
            // InternalSemver.g:3327:3: ( rule__URL__Alternatives_1_0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:3328:3: ( rule__URL__Alternatives_1_0 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( ((LA47_0>=30 && LA47_0<=33)) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSemver.g:3328:4: rule__URL__Alternatives_1_0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__URL__Alternatives_1_0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getAlternatives_1_0()); 
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
    // $ANTLR end "rule__URL__Group_1__0__Impl"


    // $ANTLR start "rule__URL__Group_1__1"
    // InternalSemver.g:3337:1: rule__URL__Group_1__1 : rule__URL__Group_1__1__Impl ;
    public final void rule__URL__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3341:1: ( rule__URL__Group_1__1__Impl )
            // InternalSemver.g:3342:2: rule__URL__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URL__Group_1__1__Impl();

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
    // $ANTLR end "rule__URL__Group_1__1"


    // $ANTLR start "rule__URL__Group_1__1__Impl"
    // InternalSemver.g:3348:1: rule__URL__Group_1__1__Impl : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URL__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3352:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3353:1: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3353:1: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3354:2: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1_1()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getALPHA_NUMERIC_CHARSParserRuleCall_1_1()); 
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
    // $ANTLR end "rule__URL__Group_1__1__Impl"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__0"
    // InternalSemver.g:3364:1: rule__ALPHA_NUMERIC_CHAR__Group__0 : rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3368:1: ( rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1 )
            // InternalSemver.g:3369:2: rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1
            {
            pushFollow(FOLLOW_28);
            rule__ALPHA_NUMERIC_CHAR__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__1();

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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__0"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__0__Impl"
    // InternalSemver.g:3376:1: rule__ALPHA_NUMERIC_CHAR__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3380:1: ( ( ( '-' )? ) )
            // InternalSemver.g:3381:1: ( ( '-' )? )
            {
            // InternalSemver.g:3381:1: ( ( '-' )? )
            // InternalSemver.g:3382:2: ( '-' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0()); 
            }
            // InternalSemver.g:3383:2: ( '-' )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==47) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalSemver.g:3383:3: '-'
                    {
                    match(input,47,FOLLOW_2); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0()); 
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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__0__Impl"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__1"
    // InternalSemver.g:3391:1: rule__ALPHA_NUMERIC_CHAR__Group__1 : rule__ALPHA_NUMERIC_CHAR__Group__1__Impl ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3395:1: ( rule__ALPHA_NUMERIC_CHAR__Group__1__Impl )
            // InternalSemver.g:3396:2: rule__ALPHA_NUMERIC_CHAR__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__1__Impl();

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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__1"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__1__Impl"
    // InternalSemver.g:3402:1: rule__ALPHA_NUMERIC_CHAR__Group__1__Impl : ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) ) ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3406:1: ( ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) ) )
            // InternalSemver.g:3407:1: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) )
            {
            // InternalSemver.g:3407:1: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) )
            // InternalSemver.g:3408:2: ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3409:2: ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 )
            // InternalSemver.g:3409:3: rule__ALPHA_NUMERIC_CHAR__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives_1()); 
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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__1__Impl"


    // $ANTLR start "rule__URLVersionRequirement__ProtocolAssignment_0"
    // InternalSemver.g:3418:1: rule__URLVersionRequirement__ProtocolAssignment_0 : ( ruleURL_PROTOCOL ) ;
    public final void rule__URLVersionRequirement__ProtocolAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3422:1: ( ( ruleURL_PROTOCOL ) )
            // InternalSemver.g:3423:2: ( ruleURL_PROTOCOL )
            {
            // InternalSemver.g:3423:2: ( ruleURL_PROTOCOL )
            // InternalSemver.g:3424:3: ruleURL_PROTOCOL
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
    // InternalSemver.g:3433:1: rule__URLVersionRequirement__UrlAssignment_2 : ( ruleURL ) ;
    public final void rule__URLVersionRequirement__UrlAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3437:1: ( ( ruleURL ) )
            // InternalSemver.g:3438:2: ( ruleURL )
            {
            // InternalSemver.g:3438:2: ( ruleURL )
            // InternalSemver.g:3439:3: ruleURL
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
    // InternalSemver.g:3448:1: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 : ( ruleURLVersionSpecifier ) ;
    public final void rule__URLVersionRequirement__VersionSpecifierAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3452:1: ( ( ruleURLVersionSpecifier ) )
            // InternalSemver.g:3453:2: ( ruleURLVersionSpecifier )
            {
            // InternalSemver.g:3453:2: ( ruleURLVersionSpecifier )
            // InternalSemver.g:3454:3: ruleURLVersionSpecifier
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


    // $ANTLR start "rule__URLSemver__SimpleVersionAssignment_1"
    // InternalSemver.g:3463:1: rule__URLSemver__SimpleVersionAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__URLSemver__SimpleVersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3467:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3468:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3468:2: ( ruleSimpleVersion )
            // InternalSemver.g:3469:3: ruleSimpleVersion
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_1_0()); 
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
    // $ANTLR end "rule__URLSemver__SimpleVersionAssignment_1"


    // $ANTLR start "rule__URLCommitISH__CommitISHAssignment"
    // InternalSemver.g:3478:1: rule__URLCommitISH__CommitISHAssignment : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URLCommitISH__CommitISHAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3482:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3483:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3483:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3484:3: ruleALPHA_NUMERIC_CHARS
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLCommitISHAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLCommitISHAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_0()); 
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
    // $ANTLR end "rule__URLCommitISH__CommitISHAssignment"


    // $ANTLR start "rule__TagVersionRequirement__TagNameAssignment"
    // InternalSemver.g:3493:1: rule__TagVersionRequirement__TagNameAssignment : ( ruleTAG ) ;
    public final void rule__TagVersionRequirement__TagNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3497:1: ( ( ruleTAG ) )
            // InternalSemver.g:3498:2: ( ruleTAG )
            {
            // InternalSemver.g:3498:2: ( ruleTAG )
            // InternalSemver.g:3499:3: ruleTAG
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


    // $ANTLR start "rule__GitHubVersionRequirement__GithubUrlAssignment_0"
    // InternalSemver.g:3508:1: rule__GitHubVersionRequirement__GithubUrlAssignment_0 : ( ruleURL ) ;
    public final void rule__GitHubVersionRequirement__GithubUrlAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3512:1: ( ( ruleURL ) )
            // InternalSemver.g:3513:2: ( ruleURL )
            {
            // InternalSemver.g:3513:2: ( ruleURL )
            // InternalSemver.g:3514:3: ruleURL
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURLParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleURL();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURLParserRuleCall_0_0()); 
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
    // InternalSemver.g:3523:1: rule__GitHubVersionRequirement__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__GitHubVersionRequirement__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3527:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3528:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3528:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3529:3: ruleALPHA_NUMERIC_CHARS
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


    // $ANTLR start "rule__LocalPathVersionRequirement__LocalPathAssignment_1"
    // InternalSemver.g:3538:1: rule__LocalPathVersionRequirement__LocalPathAssignment_1 : ( rulePATH ) ;
    public final void rule__LocalPathVersionRequirement__LocalPathAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3542:1: ( ( rulePATH ) )
            // InternalSemver.g:3543:2: ( rulePATH )
            {
            // InternalSemver.g:3543:2: ( rulePATH )
            // InternalSemver.g:3544:3: rulePATH
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


    // $ANTLR start "rule__VersionRangeSetRequirement__RangesAssignment_1_0"
    // InternalSemver.g:3553:1: rule__VersionRangeSetRequirement__RangesAssignment_1_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3557:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:3558:2: ( ruleVersionRange )
            {
            // InternalSemver.g:3558:2: ( ruleVersionRange )
            // InternalSemver.g:3559:3: ruleVersionRange
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
    // InternalSemver.g:3568:1: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3572:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:3573:2: ( ruleVersionRange )
            {
            // InternalSemver.g:3573:2: ( ruleVersionRange )
            // InternalSemver.g:3574:3: ruleVersionRange
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
    // InternalSemver.g:3583:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3587:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3588:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3588:2: ( ruleVersionNumber )
            // InternalSemver.g:3589:3: ruleVersionNumber
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
    // InternalSemver.g:3598:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3602:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3603:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3603:2: ( ruleVersionNumber )
            // InternalSemver.g:3604:3: ruleVersionNumber
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
    // InternalSemver.g:3613:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3617:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3618:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3618:2: ( ruleSimpleVersion )
            // InternalSemver.g:3619:3: ruleSimpleVersion
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
    // InternalSemver.g:3628:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3632:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3633:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3633:2: ( ruleSimpleVersion )
            // InternalSemver.g:3634:3: ruleSimpleVersion
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


    // $ANTLR start "rule__SimpleVersion__ComparatorsAssignment_1_0"
    // InternalSemver.g:3643:1: rule__SimpleVersion__ComparatorsAssignment_1_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3647:1: ( ( ruleVersionComparator ) )
            // InternalSemver.g:3648:2: ( ruleVersionComparator )
            {
            // InternalSemver.g:3648:2: ( ruleVersionComparator )
            // InternalSemver.g:3649:3: ruleVersionComparator
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionComparator();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0_0()); 
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
    // $ANTLR end "rule__SimpleVersion__ComparatorsAssignment_1_0"


    // $ANTLR start "rule__SimpleVersion__NumberAssignment_2"
    // InternalSemver.g:3658:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3662:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3663:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3663:2: ( ruleVersionNumber )
            // InternalSemver.g:3664:3: ruleVersionNumber
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
    // InternalSemver.g:3673:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3677:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3678:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3678:2: ( ruleVersionPart )
            // InternalSemver.g:3679:3: ruleVersionPart
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
    // InternalSemver.g:3688:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3692:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3693:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3693:2: ( ruleVersionPart )
            // InternalSemver.g:3694:3: ruleVersionPart
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
    // InternalSemver.g:3703:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3707:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3708:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3708:2: ( ruleVersionPart )
            // InternalSemver.g:3709:3: ruleVersionPart
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
    // InternalSemver.g:3718:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3722:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3723:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3723:2: ( ruleVersionPart )
            // InternalSemver.g:3724:3: ruleVersionPart
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
    // InternalSemver.g:3733:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3737:1: ( ( ruleQualifier ) )
            // InternalSemver.g:3738:2: ( ruleQualifier )
            {
            // InternalSemver.g:3738:2: ( ruleQualifier )
            // InternalSemver.g:3739:3: ruleQualifier
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
    // InternalSemver.g:3748:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3752:1: ( ( ruleWILDCARD ) )
            // InternalSemver.g:3753:2: ( ruleWILDCARD )
            {
            // InternalSemver.g:3753:2: ( ruleWILDCARD )
            // InternalSemver.g:3754:3: ruleWILDCARD
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
    // InternalSemver.g:3763:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3767:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:3768:2: ( RULE_DIGITS )
            {
            // InternalSemver.g:3768:2: ( RULE_DIGITS )
            // InternalSemver.g:3769:3: RULE_DIGITS
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
    // InternalSemver.g:3778:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3782:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3783:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3783:2: ( ruleQualifierTag )
            // InternalSemver.g:3784:3: ruleQualifierTag
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


    // $ANTLR start "rule__Qualifier__BuildMetadataAssignment_1_1"
    // InternalSemver.g:3793:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3797:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3798:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3798:2: ( ruleQualifierTag )
            // InternalSemver.g:3799:3: ruleQualifierTag
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


    // $ANTLR start "rule__Qualifier__PreReleaseAssignment_2_1"
    // InternalSemver.g:3808:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3812:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3813:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3813:2: ( ruleQualifierTag )
            // InternalSemver.g:3814:3: ruleQualifierTag
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0()); 
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
    // $ANTLR end "rule__Qualifier__PreReleaseAssignment_2_1"


    // $ANTLR start "rule__Qualifier__BuildMetadataAssignment_2_3"
    // InternalSemver.g:3823:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3827:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3828:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3828:2: ( ruleQualifierTag )
            // InternalSemver.g:3829:3: ruleQualifierTag
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_2_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_2_3_0()); 
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
    // $ANTLR end "rule__Qualifier__BuildMetadataAssignment_2_3"


    // $ANTLR start "rule__QualifierTag__PartsAssignment_0"
    // InternalSemver.g:3838:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3842:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3843:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3843:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3844:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:3853:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3857:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3858:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3858:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3859:3: ruleALPHA_NUMERIC_CHARS
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

    // $ANTLR start synpred2_InternalSemver
    public final void synpred2_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:615:5: ( ruleALPHA_NUMERIC_CHAR )
        // InternalSemver.g:615:5: ruleALPHA_NUMERIC_CHAR
        {
        pushFollow(FOLLOW_2);
        ruleALPHA_NUMERIC_CHAR();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalSemver

    // $ANTLR start synpred3_InternalSemver
    public final void synpred3_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:672:5: ( rule__LETTERS__Alternatives )
        // InternalSemver.g:672:5: rule__LETTERS__Alternatives
        {
        pushFollow(FOLLOW_2);
        rule__LETTERS__Alternatives();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalSemver

    // $ANTLR start synpred4_InternalSemver
    public final void synpred4_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:752:2: ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) )
        // InternalSemver.g:752:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
        {
        // InternalSemver.g:752:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
        // InternalSemver.g:753:3: ( rule__NPMVersionRequirement__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
        }
        // InternalSemver.g:754:3: ( rule__NPMVersionRequirement__Group_0__0 )
        // InternalSemver.g:754:4: rule__NPMVersionRequirement__Group_0__0
        {
        pushFollow(FOLLOW_2);
        rule__NPMVersionRequirement__Group_0__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred4_InternalSemver

    // $ANTLR start synpred8_InternalSemver
    public final void synpred8_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:806:2: ( ( ( ruleURLSemver ) ) )
        // InternalSemver.g:806:2: ( ( ruleURLSemver ) )
        {
        // InternalSemver.g:806:2: ( ( ruleURLSemver ) )
        // InternalSemver.g:807:3: ( ruleURLSemver )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0()); 
        }
        // InternalSemver.g:808:3: ( ruleURLSemver )
        // InternalSemver.g:808:4: ruleURLSemver
        {
        pushFollow(FOLLOW_2);
        ruleURLSemver();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred8_InternalSemver

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
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA31 dfa31 = new DFA31(this);
    protected DFA42 dfa42 = new DFA42(this);
    static final String dfa_1s = "\53\uffff";
    static final String dfa_2s = "\2\uffff\2\13\2\uffff\3\13\1\uffff\1\13\1\uffff\16\13\1\uffff\20\13";
    static final String dfa_3s = "\1\5\1\uffff\2\4\2\uffff\5\4\1\uffff\37\4";
    static final String dfa_4s = "\1\55\1\uffff\2\57\2\uffff\3\57\1\7\1\57\1\uffff\16\57\1\7\20\57";
    static final String dfa_5s = "\1\uffff\1\1\2\uffff\1\2\1\3\5\uffff\1\4\37\uffff";
    static final String dfa_6s = "\53\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\1\2\1\3\25\uffff\1\1\4\5\13\uffff\1\4",
            "",
            "\1\12\1\6\1\7\1\10\1\uffff\1\13\23\uffff\1\1\14\uffff\1\1\4\uffff\1\11",
            "\1\12\1\6\1\7\1\10\1\uffff\1\13\23\uffff\1\1\14\uffff\1\1\4\uffff\1\11",
            "",
            "",
            "\1\13\1\14\1\15\1\16\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\13",
            "\1\13\1\14\1\15\1\16\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\13",
            "\1\13\1\14\1\15\1\16\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\13",
            "\1\12\1\17\1\20\1\21",
            "\2\13\1\22\1\23\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "",
            "\1\13\1\24\1\25\1\26\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\13",
            "\1\33\1\27\1\30\1\31\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\32",
            "\1\33\1\27\1\30\1\31\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\32",
            "\1\13\1\34\1\35\1\36\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\34\1\35\1\36\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\34\1\35\1\36\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\33\1\37\1\40\1\41\1\uffff\1\13\45\uffff\1\32",
            "\1\33\1\37\1\40\1\41\1\uffff\1\13\45\uffff\1\32",
            "\1\13\1\24\1\25\1\26\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\13",
            "\1\33\1\27\1\30\1\31\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\32",
            "\1\33\1\27\1\30\1\31\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\32",
            "\1\13\1\24\1\25\1\26\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\13",
            "\1\33\1\27\1\30\1\31\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\32",
            "\1\33\1\27\1\30\1\31\1\uffff\1\13\23\uffff\1\1\4\5\10\uffff\1\1\4\uffff\1\32",
            "\1\33\1\42\1\43\1\44",
            "\2\13\1\22\1\23\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\33\1\50\1\51\1\52\1\uffff\1\13\24\uffff\4\5\15\uffff\1\32",
            "\1\33\1\50\1\51\1\52\1\uffff\1\13\24\uffff\4\5\15\uffff\1\32",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\33\1\50\1\51\1\52\1\uffff\1\13\24\uffff\4\5\15\uffff\1\32",
            "\1\33\1\50\1\51\1\52\1\uffff\1\13\24\uffff\4\5\15\uffff\1\32",
            "\1\13\1\45\1\46\1\47\1\uffff\1\13\24\uffff\4\5\15\uffff\1\13",
            "\1\33\1\50\1\51\1\52\1\uffff\1\13\24\uffff\4\5\15\uffff\1\32",
            "\1\33\1\50\1\51\1\52\1\uffff\1\13\24\uffff\4\5\15\uffff\1\32"
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
            return "768:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ruleURLVersionRequirement ) | ( ruleLocalPathVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );";
        }
    }
    static final String dfa_8s = "\u008c\uffff";
    static final String dfa_9s = "\2\uffff\3\1\3\uffff\4\1\1\uffff\4\1\1\uffff\5\1\3\uffff\4\1\2\uffff\3\1\1\uffff\4\1\1\uffff\11\1\1\uffff\4\1\1\uffff\12\1\1\uffff\7\1\2\uffff\7\1\1\uffff\4\1\1\uffff\3\1\1\uffff\23\1\1\uffff\15\1\1\uffff\15\1";
    static final String dfa_10s = "\1\4\1\uffff\3\11\4\4\3\11\13\4\1\uffff\24\4\3\11\65\4\3\11\45\4";
    static final String dfa_11s = "\1\51\1\uffff\3\57\1\10\6\57\1\7\4\57\1\7\5\57\1\uffff\1\10\1\7\11\57\1\7\16\57\1\7\4\57\1\7\12\57\1\7\7\57\1\10\1\7\7\57\1\7\10\57\1\7\23\57\1\7\15\57\1\7\15\57";
    static final String dfa_12s = "\1\uffff\1\1\25\uffff\1\2\164\uffff";
    static final String dfa_13s = "\u008c\uffff}>";
    static final String[] dfa_14s = {
            "\1\4\1\uffff\1\2\1\uffff\1\3\31\uffff\10\1",
            "",
            "\1\10\23\uffff\1\7\1\uffff\1\5\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\5\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\5\16\uffff\1\1\1\6",
            "\1\13\1\uffff\1\11\1\uffff\1\12",
            "\1\15\1\16\1\17\1\20\47\uffff\1\14",
            "\1\22\1\23\1\24\1\25\47\uffff\1\21",
            "\1\1\1\uffff\1\1\1\uffff\1\1\1\26\30\uffff\10\1\4\uffff\1\1\1\27",
            "\1\10\23\uffff\1\7\1\uffff\1\30\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\30\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\30\16\uffff\1\1\1\6",
            "\1\15\1\16\1\17\1\20",
            "\1\32\1\33\1\34\1\35\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\40\1\41\1\42\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\40\1\41\1\42\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\40\1\41\1\42\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\22\1\23\1\24\1\25",
            "\1\44\1\45\1\46\1\47\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\51\1\52\1\53\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\51\1\52\1\53\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\51\1\52\1\53\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\1\1\uffff\1\1\1\uffff\1\1\1\26\30\uffff\10\1\4\uffff\1\1\1\27",
            "",
            "\1\56\1\uffff\1\54\1\uffff\1\55",
            "\1\32\1\33\1\34\1\35",
            "\1\32\1\33\1\34\1\35\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\57\1\60\1\61\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\57\1\60\1\61\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\57\1\60\1\61\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\63\1\64\1\65\1\66\47\uffff\1\62",
            "\1\70\1\71\1\72\1\73\47\uffff\1\67",
            "\1\32\1\74\1\75\1\76\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\74\1\75\1\76\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\74\1\75\1\76\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\44\1\45\1\46\1\47",
            "\1\44\1\45\1\46\1\47\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\77\1\100\1\101\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\77\1\100\1\101\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\77\1\100\1\101\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\103\1\104\1\105\1\106\47\uffff\1\102",
            "\1\44\1\107\1\110\1\111\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\107\1\110\1\111\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\107\1\110\1\111\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\10\23\uffff\1\7\1\uffff\1\112\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\112\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\112\16\uffff\1\1\1\6",
            "\1\32\1\57\1\60\1\61\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\57\1\60\1\61\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\57\1\60\1\61\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\63\1\64\1\65\1\66",
            "\1\114\1\115\1\116\1\117\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\120\1\121\1\122\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\120\1\121\1\122\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\120\1\121\1\122\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\70\1\71\1\72\1\73",
            "\1\124\1\125\1\126\1\127\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\131\1\132\1\133\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\131\1\132\1\133\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\131\1\132\1\133\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\32\1\74\1\75\1\76\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\74\1\75\1\76\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\32\1\74\1\75\1\76\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\31",
            "\1\44\1\77\1\100\1\101\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\77\1\100\1\101\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\77\1\100\1\101\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\103\1\104\1\105\1\106",
            "\1\135\1\136\1\137\1\140\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\141\1\142\1\143\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\141\1\142\1\143\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\141\1\142\1\143\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\44\1\107\1\110\1\111\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\107\1\110\1\111\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\44\1\107\1\110\1\111\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\43",
            "\1\146\1\uffff\1\144\1\uffff\1\145",
            "\1\114\1\115\1\116\1\117",
            "\1\114\1\115\1\116\1\117\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\147\1\150\1\151\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\147\1\150\1\151\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\147\1\150\1\151\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\152\1\153\1\154\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\152\1\153\1\154\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\152\1\153\1\154\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\124\1\125\1\126\1\127",
            "\1\124\1\125\1\126\1\127\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\155\1\156\1\157\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\155\1\156\1\157\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\155\1\156\1\157\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\161\1\162\1\163\1\164\47\uffff\1\160",
            "\1\124\1\165\1\166\1\167\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\165\1\166\1\167\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\165\1\166\1\167\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\135\1\136\1\137\1\140",
            "\1\135\1\136\1\137\1\140\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\170\1\171\1\172\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\170\1\171\1\172\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\170\1\171\1\172\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\173\1\174\1\175\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\173\1\174\1\175\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\173\1\174\1\175\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\10\23\uffff\1\7\1\uffff\1\112\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\112\16\uffff\1\1\1\6",
            "\1\10\23\uffff\1\7\1\uffff\1\112\16\uffff\1\1\1\6",
            "\1\114\1\147\1\150\1\151\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\147\1\150\1\151\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\147\1\150\1\151\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\152\1\153\1\154\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\152\1\153\1\154\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\114\1\152\1\153\1\154\1\uffff\1\10\23\uffff\1\37\1\uffff\1\36\16\uffff\1\1\1\113",
            "\1\124\1\155\1\156\1\157\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\155\1\156\1\157\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\155\1\156\1\157\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\161\1\162\1\163\1\164",
            "\1\177\1\u0080\1\u0081\1\u0082\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0083\1\u0084\1\u0085\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0083\1\u0084\1\u0085\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0083\1\u0084\1\u0085\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\124\1\165\1\166\1\167\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\165\1\166\1\167\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\124\1\165\1\166\1\167\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\123",
            "\1\135\1\170\1\171\1\172\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\170\1\171\1\172\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\170\1\171\1\172\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\173\1\174\1\175\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\173\1\174\1\175\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\135\1\173\1\174\1\175\1\uffff\1\10\25\uffff\1\50\16\uffff\1\1\1\134",
            "\1\177\1\u0080\1\u0081\1\u0082",
            "\1\177\1\u0080\1\u0081\1\u0082\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0086\1\u0087\1\u0088\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0086\1\u0087\1\u0088\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0086\1\u0087\1\u0088\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0089\1\u008a\1\u008b\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0089\1\u008a\1\u008b\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0089\1\u008a\1\u008b\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0086\1\u0087\1\u0088\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0086\1\u0087\1\u0088\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0086\1\u0087\1\u0088\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0089\1\u008a\1\u008b\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0089\1\u008a\1\u008b\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176",
            "\1\177\1\u0089\1\u008a\1\u008b\1\uffff\1\10\25\uffff\1\130\16\uffff\1\1\1\176"
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
            return "822:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
    static final String dfa_15s = "\54\uffff";
    static final String dfa_16s = "\4\uffff\4\17\1\uffff\4\17\3\uffff\6\17\1\uffff\7\17\1\uffff\15\17";
    static final String dfa_17s = "\1\35\1\4\1\uffff\13\4\2\uffff\34\4";
    static final String dfa_18s = "\2\57\1\uffff\1\7\4\57\1\7\5\57\2\uffff\6\57\1\7\7\57\1\7\15\57";
    static final String dfa_19s = "\2\uffff\1\2\13\uffff\1\3\1\1\34\uffff";
    static final String dfa_20s = "\54\uffff}>";
    static final String[] dfa_21s = {
            "\1\2\21\uffff\1\1",
            "\1\4\1\5\1\6\1\7\47\uffff\1\3",
            "",
            "\1\4\1\5\1\6\1\7",
            "\1\11\1\12\1\13\1\14\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\20\1\21\1\22\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\20\1\21\1\22\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\20\1\21\1\22\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\12\1\13\1\14",
            "\1\11\1\12\1\13\1\14\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\23\1\24\1\25\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\23\1\24\1\25\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\23\1\24\1\25\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\27\1\30\1\31\1\32\47\uffff\1\26",
            "",
            "",
            "\1\11\1\33\1\34\1\35\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\33\1\34\1\35\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\33\1\34\1\35\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\23\1\24\1\25\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\23\1\24\1\25\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\23\1\24\1\25\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\27\1\30\1\31\1\32",
            "\1\37\1\40\1\41\1\42\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\43\1\44\1\45\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\43\1\44\1\45\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\43\1\44\1\45\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\11\1\33\1\34\1\35\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\33\1\34\1\35\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\11\1\33\1\34\1\35\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\10",
            "\1\37\1\40\1\41\1\42",
            "\1\37\1\40\1\41\1\42\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\46\1\47\1\50\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\46\1\47\1\50\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\46\1\47\1\50\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\51\1\52\1\53\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\51\1\52\1\53\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\51\1\52\1\53\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\46\1\47\1\50\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\46\1\47\1\50\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\46\1\47\1\50\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\51\1\52\1\53\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\51\1\52\1\53\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36",
            "\1\37\1\51\1\52\1\53\1\uffff\1\17\23\uffff\1\16\1\uffff\1\15\16\uffff\1\17\1\36"
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "864:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );";
        }
    }
    static final String dfa_22s = "\4\uffff";
    static final String dfa_23s = "\2\2\2\uffff";
    static final String dfa_24s = "\2\11\2\uffff";
    static final String dfa_25s = "\2\56\2\uffff";
    static final String dfa_26s = "\2\uffff\1\2\1\1";
    static final String dfa_27s = "\4\uffff}>";
    static final String[] dfa_28s = {
            "\1\1\44\uffff\1\3",
            "\1\1\44\uffff\1\3",
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

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "()* loopback of 1714:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*";
        }
    }
    static final String dfa_29s = "\5\uffff";
    static final String dfa_30s = "\1\1\1\uffff\2\1\1\uffff";
    static final String dfa_31s = "\1\11\1\uffff\2\4\1\uffff";
    static final String dfa_32s = "\1\56\1\uffff\2\56\1\uffff";
    static final String dfa_33s = "\1\uffff\1\2\2\uffff\1\1";
    static final String dfa_34s = "\5\uffff}>";
    static final String[] dfa_35s = {
            "\1\2\44\uffff\1\1",
            "",
            "\1\4\1\uffff\1\4\1\uffff\1\4\1\3\30\uffff\10\4\4\uffff\1\1",
            "\1\4\1\uffff\1\4\1\uffff\1\4\1\3\30\uffff\10\4\4\uffff\1\1",
            ""
    };

    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[][] dfa_35 = unpackEncodedStringArray(dfa_35s);

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "()* loopback of 2105:2: ( rule__VersionRangeContraint__Group_2__0 )*";
        }
    }
    static final String dfa_36s = "\7\uffff";
    static final String dfa_37s = "\3\3\1\uffff\2\3\1\uffff";
    static final String dfa_38s = "\1\11\2\4\1\uffff\2\4\1\uffff";
    static final String dfa_39s = "\1\37\2\57\1\uffff\2\57\1\uffff";
    static final String dfa_40s = "\3\uffff\1\2\2\uffff\1\1";
    static final String dfa_41s = "\7\uffff}>";
    static final String[] dfa_42s = {
            "\1\3\24\uffff\1\1\1\2",
            "\4\6\1\uffff\1\3\24\uffff\1\4\1\5\17\uffff\1\6",
            "\4\6\1\uffff\1\3\24\uffff\1\4\1\5\17\uffff\1\6",
            "",
            "\4\6\1\uffff\1\3\24\uffff\1\4\1\5\17\uffff\1\6",
            "\4\6\1\uffff\1\3\24\uffff\1\4\1\5\17\uffff\1\6",
            ""
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = dfa_36;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "()* loopback of 3037:3: ( rule__PATH__Group_1__0 )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x00000000200000E2L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x00008000000000F2L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000000000000E2L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000003FC00000150L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000003C00000E0L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x000093FC000001F0L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x000013FC00000150L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x00000000C00000E0L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000400000000200L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000400000000202L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x000003FC00000350L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x000003FC00000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x00008000A0000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x000093FC000001F2L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000000C00000E2L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000000C0000002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00008000000000F0L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x00000003C00000E2L});

}