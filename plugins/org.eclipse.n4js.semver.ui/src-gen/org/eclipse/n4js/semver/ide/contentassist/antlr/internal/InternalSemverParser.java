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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LETTER_NO_VX", "RULE_DIGITS", "RULE_LETTER_V", "RULE_LETTER_X", "RULE_ASTERIX", "RULE_WS", "RULE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'/'", "'.'", "'-'", "'+'", "':'", "'@'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'file:'", "'://'", "'#'", "'semver:'", "'||'"
    };
    public static final int RULE_WHITESPACE_FRAGMENT=11;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=12;
    public static final int RULE_EOL=13;
    public static final int RULE_DIGIT=10;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=24;
    public static final int RULE_ZWNJ=18;
    public static final int T__29=29;
    public static final int RULE_ASTERIX=8;
    public static final int RULE_LETTER_NO_VX=4;
    public static final int RULE_ML_COMMENT_FRAGMENT=23;
    public static final int RULE_DIGITS=5;
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
    public static final int RULE_LETTER_V=6;
    public static final int RULE_LETTER_X=7;
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


    // $ANTLR start "entryRuleURLCommitISH"
    // InternalSemver.g:186:1: entryRuleURLCommitISH : ruleURLCommitISH EOF ;
    public final void entryRuleURLCommitISH() throws RecognitionException {
        try {
            // InternalSemver.g:187:1: ( ruleURLCommitISH EOF )
            // InternalSemver.g:188:1: ruleURLCommitISH EOF
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
    // InternalSemver.g:195:1: ruleURLCommitISH : ( ( rule__URLCommitISH__CommitISHAssignment ) ) ;
    public final void ruleURLCommitISH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:199:2: ( ( ( rule__URLCommitISH__CommitISHAssignment ) ) )
            // InternalSemver.g:200:2: ( ( rule__URLCommitISH__CommitISHAssignment ) )
            {
            // InternalSemver.g:200:2: ( ( rule__URLCommitISH__CommitISHAssignment ) )
            // InternalSemver.g:201:3: ( rule__URLCommitISH__CommitISHAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLCommitISHAccess().getCommitISHAssignment()); 
            }
            // InternalSemver.g:202:3: ( rule__URLCommitISH__CommitISHAssignment )
            // InternalSemver.g:202:4: rule__URLCommitISH__CommitISHAssignment
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
    // InternalSemver.g:211:1: entryRuleTagVersionRequirement : ruleTagVersionRequirement EOF ;
    public final void entryRuleTagVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:212:1: ( ruleTagVersionRequirement EOF )
            // InternalSemver.g:213:1: ruleTagVersionRequirement EOF
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
    // InternalSemver.g:220:1: ruleTagVersionRequirement : ( ( rule__TagVersionRequirement__TagNameAssignment ) ) ;
    public final void ruleTagVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:224:2: ( ( ( rule__TagVersionRequirement__TagNameAssignment ) ) )
            // InternalSemver.g:225:2: ( ( rule__TagVersionRequirement__TagNameAssignment ) )
            {
            // InternalSemver.g:225:2: ( ( rule__TagVersionRequirement__TagNameAssignment ) )
            // InternalSemver.g:226:3: ( rule__TagVersionRequirement__TagNameAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTagVersionRequirementAccess().getTagNameAssignment()); 
            }
            // InternalSemver.g:227:3: ( rule__TagVersionRequirement__TagNameAssignment )
            // InternalSemver.g:227:4: rule__TagVersionRequirement__TagNameAssignment
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
    // InternalSemver.g:236:1: entryRuleGitHubVersionRequirement : ruleGitHubVersionRequirement EOF ;
    public final void entryRuleGitHubVersionRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:237:1: ( ruleGitHubVersionRequirement EOF )
            // InternalSemver.g:238:1: ruleGitHubVersionRequirement EOF
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
    // InternalSemver.g:245:1: ruleGitHubVersionRequirement : ( ( rule__GitHubVersionRequirement__Group__0 ) ) ;
    public final void ruleGitHubVersionRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:249:2: ( ( ( rule__GitHubVersionRequirement__Group__0 ) ) )
            // InternalSemver.g:250:2: ( ( rule__GitHubVersionRequirement__Group__0 ) )
            {
            // InternalSemver.g:250:2: ( ( rule__GitHubVersionRequirement__Group__0 ) )
            // InternalSemver.g:251:3: ( rule__GitHubVersionRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:252:3: ( rule__GitHubVersionRequirement__Group__0 )
            // InternalSemver.g:252:4: rule__GitHubVersionRequirement__Group__0
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


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:486:1: entryRulePATH : rulePATH EOF ;
    public final void entryRulePATH() throws RecognitionException {
        try {
            // InternalSemver.g:487:1: ( rulePATH EOF )
            // InternalSemver.g:488:1: rulePATH EOF
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
    // InternalSemver.g:495:1: rulePATH : ( ( rule__PATH__Group__0 ) ) ;
    public final void rulePATH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:499:2: ( ( ( rule__PATH__Group__0 ) ) )
            // InternalSemver.g:500:2: ( ( rule__PATH__Group__0 ) )
            {
            // InternalSemver.g:500:2: ( ( rule__PATH__Group__0 ) )
            // InternalSemver.g:501:3: ( rule__PATH__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getGroup()); 
            }
            // InternalSemver.g:502:3: ( rule__PATH__Group__0 )
            // InternalSemver.g:502:4: rule__PATH__Group__0
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


    // $ANTLR start "entryRuleURL_PROTOCOL"
    // InternalSemver.g:511:1: entryRuleURL_PROTOCOL : ruleURL_PROTOCOL EOF ;
    public final void entryRuleURL_PROTOCOL() throws RecognitionException {
        try {
            // InternalSemver.g:512:1: ( ruleURL_PROTOCOL EOF )
            // InternalSemver.g:513:1: ruleURL_PROTOCOL EOF
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
    // InternalSemver.g:520:1: ruleURL_PROTOCOL : ( ( rule__URL_PROTOCOL__Group__0 ) ) ;
    public final void ruleURL_PROTOCOL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:524:2: ( ( ( rule__URL_PROTOCOL__Group__0 ) ) )
            // InternalSemver.g:525:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            {
            // InternalSemver.g:525:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            // InternalSemver.g:526:3: ( rule__URL_PROTOCOL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getGroup()); 
            }
            // InternalSemver.g:527:3: ( rule__URL_PROTOCOL__Group__0 )
            // InternalSemver.g:527:4: rule__URL_PROTOCOL__Group__0
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
    // InternalSemver.g:536:1: entryRuleURL : ruleURL EOF ;
    public final void entryRuleURL() throws RecognitionException {
        try {
            // InternalSemver.g:537:1: ( ruleURL EOF )
            // InternalSemver.g:538:1: ruleURL EOF
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
    // InternalSemver.g:545:1: ruleURL : ( ( rule__URL__Group__0 ) ) ;
    public final void ruleURL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:549:2: ( ( ( rule__URL__Group__0 ) ) )
            // InternalSemver.g:550:2: ( ( rule__URL__Group__0 ) )
            {
            // InternalSemver.g:550:2: ( ( rule__URL__Group__0 ) )
            // InternalSemver.g:551:3: ( rule__URL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup()); 
            }
            // InternalSemver.g:552:3: ( rule__URL__Group__0 )
            // InternalSemver.g:552:4: rule__URL__Group__0
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


    // $ANTLR start "entryRuleTAG"
    // InternalSemver.g:561:1: entryRuleTAG : ruleTAG EOF ;
    public final void entryRuleTAG() throws RecognitionException {
        try {
            // InternalSemver.g:562:1: ( ruleTAG EOF )
            // InternalSemver.g:563:1: ruleTAG EOF
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
    // InternalSemver.g:570:1: ruleTAG : ( ( rule__TAG__Group__0 ) ) ;
    public final void ruleTAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:574:2: ( ( ( rule__TAG__Group__0 ) ) )
            // InternalSemver.g:575:2: ( ( rule__TAG__Group__0 ) )
            {
            // InternalSemver.g:575:2: ( ( rule__TAG__Group__0 ) )
            // InternalSemver.g:576:3: ( rule__TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getGroup()); 
            }
            // InternalSemver.g:577:3: ( rule__TAG__Group__0 )
            // InternalSemver.g:577:4: rule__TAG__Group__0
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSemver.g:586:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSemver.g:587:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:588:1: ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:595:1: ruleALPHA_NUMERIC_CHARS : ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:599:2: ( ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) )
            // InternalSemver.g:600:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            {
            // InternalSemver.g:600:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            // InternalSemver.g:601:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            {
            // InternalSemver.g:601:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) )
            // InternalSemver.g:602:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:603:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            // InternalSemver.g:603:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
            {
            pushFollow(FOLLOW_3);
            rule__ALPHA_NUMERIC_CHARS__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }

            }

            // InternalSemver.g:606:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            // InternalSemver.g:607:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:608:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_LETTER_NO_VX && LA1_0<=RULE_LETTER_X)||LA1_0==31) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSemver.g:608:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__ALPHA_NUMERIC_CHARS__Alternatives();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
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


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSemver.g:618:1: entryRuleWILDCARD : ruleWILDCARD EOF ;
    public final void entryRuleWILDCARD() throws RecognitionException {
        try {
            // InternalSemver.g:619:1: ( ruleWILDCARD EOF )
            // InternalSemver.g:620:1: ruleWILDCARD EOF
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
    // InternalSemver.g:627:1: ruleWILDCARD : ( ( rule__WILDCARD__Alternatives ) ) ;
    public final void ruleWILDCARD() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:631:2: ( ( ( rule__WILDCARD__Alternatives ) ) )
            // InternalSemver.g:632:2: ( ( rule__WILDCARD__Alternatives ) )
            {
            // InternalSemver.g:632:2: ( ( rule__WILDCARD__Alternatives ) )
            // InternalSemver.g:633:3: ( rule__WILDCARD__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWILDCARDAccess().getAlternatives()); 
            }
            // InternalSemver.g:634:3: ( rule__WILDCARD__Alternatives )
            // InternalSemver.g:634:4: rule__WILDCARD__Alternatives
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
    // InternalSemver.g:643:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:647:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSemver.g:648:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSemver.g:648:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSemver.g:649:3: ( rule__VersionComparator__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            }
            // InternalSemver.g:650:3: ( rule__VersionComparator__Alternatives )
            // InternalSemver.g:650:4: rule__VersionComparator__Alternatives
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
    // InternalSemver.g:658:1: rule__NPMVersionRequirement__Alternatives : ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:662:1: ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) )
            int alt2=2;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // InternalSemver.g:663:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    {
                    // InternalSemver.g:663:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    // InternalSemver.g:664:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:665:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    // InternalSemver.g:665:4: rule__NPMVersionRequirement__Group_0__0
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
                    // InternalSemver.g:669:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    {
                    // InternalSemver.g:669:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    // InternalSemver.g:670:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:671:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    // InternalSemver.g:671:4: rule__NPMVersionRequirement__Group_1__0
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
    // InternalSemver.g:679:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ruleURLVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:683:1: ( ( ( ruleLocalPathVersionRequirement ) ) | ( ruleURLVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) )
            int alt3=4;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // InternalSemver.g:684:2: ( ( ruleLocalPathVersionRequirement ) )
                    {
                    // InternalSemver.g:684:2: ( ( ruleLocalPathVersionRequirement ) )
                    // InternalSemver.g:685:3: ( ruleLocalPathVersionRequirement )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
                    }
                    // InternalSemver.g:686:3: ( ruleLocalPathVersionRequirement )
                    // InternalSemver.g:686:4: ruleLocalPathVersionRequirement
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
                    // InternalSemver.g:690:2: ( ruleURLVersionRequirement )
                    {
                    // InternalSemver.g:690:2: ( ruleURLVersionRequirement )
                    // InternalSemver.g:691:3: ruleURLVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleURLVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:696:2: ( ruleGitHubVersionRequirement )
                    {
                    // InternalSemver.g:696:2: ( ruleGitHubVersionRequirement )
                    // InternalSemver.g:697:3: ruleGitHubVersionRequirement
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
                    // InternalSemver.g:702:2: ( ruleTagVersionRequirement )
                    {
                    // InternalSemver.g:702:2: ( ruleTagVersionRequirement )
                    // InternalSemver.g:703:3: ruleTagVersionRequirement
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
    // InternalSemver.g:712:1: rule__URLVersionSpecifier__Alternatives : ( ( ( ruleURLSemver ) ) | ( ruleURLCommitISH ) );
    public final void rule__URLVersionSpecifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:716:1: ( ( ( ruleURLSemver ) ) | ( ruleURLCommitISH ) )
            int alt4=2;
            switch ( input.LA(1) ) {
            case RULE_ASTERIX:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 45:
                {
                alt4=1;
                }
                break;
            case RULE_LETTER_V:
                {
                switch ( input.LA(2) ) {
                case EOF:
                case RULE_LETTER_NO_VX:
                case RULE_LETTER_V:
                case RULE_WS:
                case 31:
                    {
                    alt4=2;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    int LA4_6 = input.LA(3);

                    if ( (synpred6_InternalSemver()) ) {
                        alt4=1;
                    }
                    else if ( (true) ) {
                        alt4=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_LETTER_X:
                    {
                    int LA4_7 = input.LA(3);

                    if ( (synpred6_InternalSemver()) ) {
                        alt4=1;
                    }
                    else if ( (true) ) {
                        alt4=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_ASTERIX:
                    {
                    alt4=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_LETTER_X:
                {
                int LA4_3 = input.LA(2);

                if ( (synpred6_InternalSemver()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_DIGITS:
                {
                int LA4_4 = input.LA(2);

                if ( (synpred6_InternalSemver()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 4, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LETTER_NO_VX:
            case 31:
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
                    // InternalSemver.g:717:2: ( ( ruleURLSemver ) )
                    {
                    // InternalSemver.g:717:2: ( ( ruleURLSemver ) )
                    // InternalSemver.g:718:3: ( ruleURLSemver )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0()); 
                    }
                    // InternalSemver.g:719:3: ( ruleURLSemver )
                    // InternalSemver.g:719:4: ruleURLSemver
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
                    // InternalSemver.g:723:2: ( ruleURLCommitISH )
                    {
                    // InternalSemver.g:723:2: ( ruleURLCommitISH )
                    // InternalSemver.g:724:3: ruleURLCommitISH
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
    // InternalSemver.g:733:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:737:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSemver.g:738:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSemver.g:738:2: ( ruleVersionRangeContraint )
                    // InternalSemver.g:739:3: ruleVersionRangeContraint
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
                    // InternalSemver.g:744:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSemver.g:744:2: ( ruleHyphenVersionRange )
                    // InternalSemver.g:745:3: ruleHyphenVersionRange
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
    // InternalSemver.g:754:1: rule__VersionPart__Alternatives : ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) );
    public final void rule__VersionPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:758:1: ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0>=RULE_LETTER_X && LA6_0<=RULE_ASTERIX)) ) {
                alt6=1;
            }
            else if ( (LA6_0==RULE_DIGITS) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalSemver.g:759:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    {
                    // InternalSemver.g:759:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    // InternalSemver.g:760:3: ( rule__VersionPart__WildcardAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    }
                    // InternalSemver.g:761:3: ( rule__VersionPart__WildcardAssignment_0 )
                    // InternalSemver.g:761:4: rule__VersionPart__WildcardAssignment_0
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
                    // InternalSemver.g:765:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    {
                    // InternalSemver.g:765:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    // InternalSemver.g:766:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    }
                    // InternalSemver.g:767:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    // InternalSemver.g:767:4: rule__VersionPart__NumberRawAssignment_1
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
    // InternalSemver.g:775:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:779:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt7=3;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:780:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:780:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSemver.g:781:3: ( rule__Qualifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:782:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSemver.g:782:4: rule__Qualifier__Group_0__0
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
                    // InternalSemver.g:786:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:786:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSemver.g:787:3: ( rule__Qualifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:788:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSemver.g:788:4: rule__Qualifier__Group_1__0
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
                    // InternalSemver.g:792:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:792:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSemver.g:793:3: ( rule__Qualifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:794:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSemver.g:794:4: rule__Qualifier__Group_2__0
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


    // $ANTLR start "rule__PATH__Alternatives_0"
    // InternalSemver.g:802:1: rule__PATH__Alternatives_0 : ( ( RULE_LETTER_NO_VX ) | ( '/' ) | ( '.' ) );
    public final void rule__PATH__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:806:1: ( ( RULE_LETTER_NO_VX ) | ( '/' ) | ( '.' ) )
            int alt8=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_NO_VX:
                {
                alt8=1;
                }
                break;
            case 29:
                {
                alt8=2;
                }
                break;
            case 30:
                {
                alt8=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalSemver.g:807:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:807:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:808:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTER_NO_VXTerminalRuleCall_0_0()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTER_NO_VXTerminalRuleCall_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:813:2: ( '/' )
                    {
                    // InternalSemver.g:813:2: ( '/' )
                    // InternalSemver.g:814:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_0_1()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:819:2: ( '.' )
                    {
                    // InternalSemver.g:819:2: ( '.' )
                    // InternalSemver.g:820:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_0_2()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_0_2()); 
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
    // $ANTLR end "rule__PATH__Alternatives_0"


    // $ANTLR start "rule__PATH__Alternatives_1"
    // InternalSemver.g:829:1: rule__PATH__Alternatives_1 : ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__PATH__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:833:1: ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt9=7;
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
            case RULE_DIGITS:
                {
                alt9=4;
                }
                break;
            case RULE_LETTER_V:
                {
                alt9=5;
                }
                break;
            case RULE_LETTER_X:
                {
                alt9=6;
                }
                break;
            case RULE_LETTER_NO_VX:
                {
                alt9=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalSemver.g:834:2: ( '/' )
                    {
                    // InternalSemver.g:834:2: ( '/' )
                    // InternalSemver.g:835:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_1_0()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:840:2: ( '.' )
                    {
                    // InternalSemver.g:840:2: ( '.' )
                    // InternalSemver.g:841:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_1_1()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:846:2: ( '-' )
                    {
                    // InternalSemver.g:846:2: ( '-' )
                    // InternalSemver.g:847:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getHyphenMinusKeyword_1_2()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getHyphenMinusKeyword_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:852:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:852:2: ( RULE_DIGITS )
                    // InternalSemver.g:853:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_1_3()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_1_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:858:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:858:2: ( RULE_LETTER_V )
                    // InternalSemver.g:859:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTER_VTerminalRuleCall_1_4()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTER_VTerminalRuleCall_1_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:864:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:864:2: ( RULE_LETTER_X )
                    // InternalSemver.g:865:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTER_XTerminalRuleCall_1_5()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTER_XTerminalRuleCall_1_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:870:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:870:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:871:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTER_NO_VXTerminalRuleCall_1_6()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTER_NO_VXTerminalRuleCall_1_6()); 
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
    // $ANTLR end "rule__PATH__Alternatives_1"


    // $ANTLR start "rule__URL_PROTOCOL__Alternatives_1"
    // InternalSemver.g:880:1: rule__URL_PROTOCOL__Alternatives_1 : ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) | ( '+' ) );
    public final void rule__URL_PROTOCOL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:884:1: ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) | ( '+' ) )
            int alt10=4;
            switch ( input.LA(1) ) {
            case RULE_LETTER_V:
                {
                alt10=1;
                }
                break;
            case RULE_LETTER_X:
                {
                alt10=2;
                }
                break;
            case RULE_LETTER_NO_VX:
                {
                alt10=3;
                }
                break;
            case 32:
                {
                alt10=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalSemver.g:885:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:885:2: ( RULE_LETTER_V )
                    // InternalSemver.g:886:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getLETTER_VTerminalRuleCall_1_0()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getLETTER_VTerminalRuleCall_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:891:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:891:2: ( RULE_LETTER_X )
                    // InternalSemver.g:892:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getLETTER_XTerminalRuleCall_1_1()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getLETTER_XTerminalRuleCall_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:897:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:897:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:898:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXTerminalRuleCall_1_2()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXTerminalRuleCall_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:903:2: ( '+' )
                    {
                    // InternalSemver.g:903:2: ( '+' )
                    // InternalSemver.g:904:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_3()); 
                    }
                    match(input,32,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_3()); 
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


    // $ANTLR start "rule__URL__Alternatives_1"
    // InternalSemver.g:913:1: rule__URL__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__URL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:917:1: ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt11=5;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt11=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt11=2;
                }
                break;
            case RULE_LETTER_V:
                {
                alt11=3;
                }
                break;
            case RULE_LETTER_X:
                {
                alt11=4;
                }
                break;
            case RULE_LETTER_NO_VX:
                {
                alt11=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalSemver.g:918:2: ( '-' )
                    {
                    // InternalSemver.g:918:2: ( '-' )
                    // InternalSemver.g:919:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:924:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:924:2: ( RULE_DIGITS )
                    // InternalSemver.g:925:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:930:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:930:2: ( RULE_LETTER_V )
                    // InternalSemver.g:931:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTER_VTerminalRuleCall_1_2()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTER_VTerminalRuleCall_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:936:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:936:2: ( RULE_LETTER_X )
                    // InternalSemver.g:937:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTER_XTerminalRuleCall_1_3()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTER_XTerminalRuleCall_1_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:942:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:942:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:943:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_1_4()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_1_4()); 
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
    // InternalSemver.g:952:1: rule__URL__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:956:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt12=4;
            switch ( input.LA(1) ) {
            case 29:
                {
                alt12=1;
                }
                break;
            case 30:
                {
                alt12=2;
                }
                break;
            case 33:
                {
                alt12=3;
                }
                break;
            case 34:
                {
                alt12=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // InternalSemver.g:957:2: ( '/' )
                    {
                    // InternalSemver.g:957:2: ( '/' )
                    // InternalSemver.g:958:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:963:2: ( '.' )
                    {
                    // InternalSemver.g:963:2: ( '.' )
                    // InternalSemver.g:964:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:969:2: ( ':' )
                    {
                    // InternalSemver.g:969:2: ( ':' )
                    // InternalSemver.g:970:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }
                    match(input,33,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:975:2: ( '@' )
                    {
                    // InternalSemver.g:975:2: ( '@' )
                    // InternalSemver.g:976:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,34,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
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


    // $ANTLR start "rule__URL__Alternatives_3"
    // InternalSemver.g:985:1: rule__URL__Alternatives_3 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__URL__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:989:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt13=9;
            switch ( input.LA(1) ) {
            case 29:
                {
                alt13=1;
                }
                break;
            case 30:
                {
                alt13=2;
                }
                break;
            case 33:
                {
                alt13=3;
                }
                break;
            case 34:
                {
                alt13=4;
                }
                break;
            case 31:
                {
                alt13=5;
                }
                break;
            case RULE_DIGITS:
                {
                alt13=6;
                }
                break;
            case RULE_LETTER_V:
                {
                alt13=7;
                }
                break;
            case RULE_LETTER_X:
                {
                alt13=8;
                }
                break;
            case RULE_LETTER_NO_VX:
                {
                alt13=9;
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
                    // InternalSemver.g:990:2: ( '/' )
                    {
                    // InternalSemver.g:990:2: ( '/' )
                    // InternalSemver.g:991:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_3_0()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:996:2: ( '.' )
                    {
                    // InternalSemver.g:996:2: ( '.' )
                    // InternalSemver.g:997:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_3_1()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_3_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1002:2: ( ':' )
                    {
                    // InternalSemver.g:1002:2: ( ':' )
                    // InternalSemver.g:1003:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_3_2()); 
                    }
                    match(input,33,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_3_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1008:2: ( '@' )
                    {
                    // InternalSemver.g:1008:2: ( '@' )
                    // InternalSemver.g:1009:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_3_3()); 
                    }
                    match(input,34,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_3_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1014:2: ( '-' )
                    {
                    // InternalSemver.g:1014:2: ( '-' )
                    // InternalSemver.g:1015:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_3_4()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_3_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1020:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1020:2: ( RULE_DIGITS )
                    // InternalSemver.g:1021:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_3_5()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_3_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1026:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1026:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1027:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTER_VTerminalRuleCall_3_6()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTER_VTerminalRuleCall_3_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSemver.g:1032:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1032:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1033:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTER_XTerminalRuleCall_3_7()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTER_XTerminalRuleCall_3_7()); 
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalSemver.g:1038:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1038:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1039:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_3_8()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_3_8()); 
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
    // $ANTLR end "rule__URL__Alternatives_3"


    // $ANTLR start "rule__TAG__Alternatives_0"
    // InternalSemver.g:1048:1: rule__TAG__Alternatives_0 : ( ( RULE_LETTER_NO_VX ) | ( RULE_LETTER_X ) );
    public final void rule__TAG__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1052:1: ( ( RULE_LETTER_NO_VX ) | ( RULE_LETTER_X ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_LETTER_NO_VX) ) {
                alt14=1;
            }
            else if ( (LA14_0==RULE_LETTER_X) ) {
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
                    // InternalSemver.g:1053:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1053:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1054:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_0_0()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1059:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1059:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1060:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getLETTER_XTerminalRuleCall_0_1()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getLETTER_XTerminalRuleCall_0_1()); 
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
    // $ANTLR end "rule__TAG__Alternatives_0"


    // $ANTLR start "rule__TAG__Alternatives_1"
    // InternalSemver.g:1069:1: rule__TAG__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__TAG__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1073:1: ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt15=5;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt15=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt15=2;
                }
                break;
            case RULE_LETTER_V:
                {
                alt15=3;
                }
                break;
            case RULE_LETTER_X:
                {
                alt15=4;
                }
                break;
            case RULE_LETTER_NO_VX:
                {
                alt15=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalSemver.g:1074:2: ( '-' )
                    {
                    // InternalSemver.g:1074:2: ( '-' )
                    // InternalSemver.g:1075:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1080:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1080:2: ( RULE_DIGITS )
                    // InternalSemver.g:1081:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1086:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1086:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1087:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getLETTER_VTerminalRuleCall_1_2()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getLETTER_VTerminalRuleCall_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1092:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1092:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1093:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getLETTER_XTerminalRuleCall_1_3()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getLETTER_XTerminalRuleCall_1_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1098:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1098:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1099:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_1_4()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_1_4()); 
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
    // $ANTLR end "rule__TAG__Alternatives_1"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS__Alternatives"
    // InternalSemver.g:1108:1: rule__ALPHA_NUMERIC_CHARS__Alternatives : ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__ALPHA_NUMERIC_CHARS__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1112:1: ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt16=5;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt16=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt16=2;
                }
                break;
            case RULE_LETTER_V:
                {
                alt16=3;
                }
                break;
            case RULE_LETTER_X:
                {
                alt16=4;
                }
                break;
            case RULE_LETTER_NO_VX:
                {
                alt16=5;
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
                    // InternalSemver.g:1113:2: ( '-' )
                    {
                    // InternalSemver.g:1113:2: ( '-' )
                    // InternalSemver.g:1114:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1119:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1119:2: ( RULE_DIGITS )
                    // InternalSemver.g:1120:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getDIGITSTerminalRuleCall_1()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getDIGITSTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1125:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1125:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1126:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_VTerminalRuleCall_2()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_VTerminalRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1131:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1131:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1132:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_XTerminalRuleCall_3()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_XTerminalRuleCall_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1137:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1137:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1138:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_NO_VXTerminalRuleCall_4()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTER_NO_VXTerminalRuleCall_4()); 
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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHARS__Alternatives"


    // $ANTLR start "rule__WILDCARD__Alternatives"
    // InternalSemver.g:1147:1: rule__WILDCARD__Alternatives : ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) );
    public final void rule__WILDCARD__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1151:1: ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) )
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
                    // InternalSemver.g:1152:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1152:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1153:3: RULE_LETTER_X
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
                    // InternalSemver.g:1158:2: ( RULE_ASTERIX )
                    {
                    // InternalSemver.g:1158:2: ( RULE_ASTERIX )
                    // InternalSemver.g:1159:3: RULE_ASTERIX
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
    // InternalSemver.g:1168:1: rule__VersionComparator__Alternatives : ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1172:1: ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt18=7;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt18=1;
                }
                break;
            case 36:
                {
                alt18=2;
                }
                break;
            case 37:
                {
                alt18=3;
                }
                break;
            case 38:
                {
                alt18=4;
                }
                break;
            case 39:
                {
                alt18=5;
                }
                break;
            case 40:
                {
                alt18=6;
                }
                break;
            case 41:
                {
                alt18=7;
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
                    // InternalSemver.g:1173:2: ( ( '=' ) )
                    {
                    // InternalSemver.g:1173:2: ( ( '=' ) )
                    // InternalSemver.g:1174:3: ( '=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }
                    // InternalSemver.g:1175:3: ( '=' )
                    // InternalSemver.g:1175:4: '='
                    {
                    match(input,35,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1179:2: ( ( '<' ) )
                    {
                    // InternalSemver.g:1179:2: ( ( '<' ) )
                    // InternalSemver.g:1180:3: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }
                    // InternalSemver.g:1181:3: ( '<' )
                    // InternalSemver.g:1181:4: '<'
                    {
                    match(input,36,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1185:2: ( ( '~' ) )
                    {
                    // InternalSemver.g:1185:2: ( ( '~' ) )
                    // InternalSemver.g:1186:3: ( '~' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }
                    // InternalSemver.g:1187:3: ( '~' )
                    // InternalSemver.g:1187:4: '~'
                    {
                    match(input,37,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1191:2: ( ( '^' ) )
                    {
                    // InternalSemver.g:1191:2: ( ( '^' ) )
                    // InternalSemver.g:1192:3: ( '^' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }
                    // InternalSemver.g:1193:3: ( '^' )
                    // InternalSemver.g:1193:4: '^'
                    {
                    match(input,38,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1197:2: ( ( '<=' ) )
                    {
                    // InternalSemver.g:1197:2: ( ( '<=' ) )
                    // InternalSemver.g:1198:3: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }
                    // InternalSemver.g:1199:3: ( '<=' )
                    // InternalSemver.g:1199:4: '<='
                    {
                    match(input,39,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1203:2: ( ( '>' ) )
                    {
                    // InternalSemver.g:1203:2: ( ( '>' ) )
                    // InternalSemver.g:1204:3: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }
                    // InternalSemver.g:1205:3: ( '>' )
                    // InternalSemver.g:1205:4: '>'
                    {
                    match(input,40,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1209:2: ( ( '>=' ) )
                    {
                    // InternalSemver.g:1209:2: ( ( '>=' ) )
                    // InternalSemver.g:1210:3: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); 
                    }
                    // InternalSemver.g:1211:3: ( '>=' )
                    // InternalSemver.g:1211:4: '>='
                    {
                    match(input,41,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1219:1: rule__NPMVersionRequirement__Group_0__0 : rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 ;
    public final void rule__NPMVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1223:1: ( rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 )
            // InternalSemver.g:1224:2: rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:1231:1: rule__NPMVersionRequirement__Group_0__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1235:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1236:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1236:1: ( ( RULE_WS )* )
            // InternalSemver.g:1237:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }
            // InternalSemver.g:1238:2: ( RULE_WS )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_WS) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSemver.g:1238:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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
    // InternalSemver.g:1246:1: rule__NPMVersionRequirement__Group_0__1 : rule__NPMVersionRequirement__Group_0__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1250:1: ( rule__NPMVersionRequirement__Group_0__1__Impl )
            // InternalSemver.g:1251:2: rule__NPMVersionRequirement__Group_0__1__Impl
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
    // InternalSemver.g:1257:1: rule__NPMVersionRequirement__Group_0__1__Impl : ( ruleVersionRangeSetRequirement ) ;
    public final void rule__NPMVersionRequirement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1261:1: ( ( ruleVersionRangeSetRequirement ) )
            // InternalSemver.g:1262:1: ( ruleVersionRangeSetRequirement )
            {
            // InternalSemver.g:1262:1: ( ruleVersionRangeSetRequirement )
            // InternalSemver.g:1263:2: ruleVersionRangeSetRequirement
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
    // InternalSemver.g:1273:1: rule__NPMVersionRequirement__Group_1__0 : rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 ;
    public final void rule__NPMVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1277:1: ( rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 )
            // InternalSemver.g:1278:2: rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1
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
    // InternalSemver.g:1285:1: rule__NPMVersionRequirement__Group_1__0__Impl : ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) ;
    public final void rule__NPMVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1289:1: ( ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) )
            // InternalSemver.g:1290:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            {
            // InternalSemver.g:1290:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            // InternalSemver.g:1291:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:1292:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            // InternalSemver.g:1292:3: rule__NPMVersionRequirement__Alternatives_1_0
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
    // InternalSemver.g:1300:1: rule__NPMVersionRequirement__Group_1__1 : rule__NPMVersionRequirement__Group_1__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1304:1: ( rule__NPMVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1305:2: rule__NPMVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1311:1: rule__NPMVersionRequirement__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1315:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1316:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1316:1: ( ( RULE_WS )* )
            // InternalSemver.g:1317:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:1318:2: ( RULE_WS )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==RULE_WS) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSemver.g:1318:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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


    // $ANTLR start "rule__LocalPathVersionRequirement__Group__0"
    // InternalSemver.g:1327:1: rule__LocalPathVersionRequirement__Group__0 : rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 ;
    public final void rule__LocalPathVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1331:1: ( rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 )
            // InternalSemver.g:1332:2: rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1
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
    // InternalSemver.g:1339:1: rule__LocalPathVersionRequirement__Group__0__Impl : ( ( 'file:' ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1343:1: ( ( ( 'file:' ) ) )
            // InternalSemver.g:1344:1: ( ( 'file:' ) )
            {
            // InternalSemver.g:1344:1: ( ( 'file:' ) )
            // InternalSemver.g:1345:2: ( 'file:' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getFileKeyword_0()); 
            }
            // InternalSemver.g:1346:2: ( 'file:' )
            // InternalSemver.g:1346:3: 'file:'
            {
            match(input,42,FOLLOW_2); if (state.failed) return ;

            }

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
    // InternalSemver.g:1354:1: rule__LocalPathVersionRequirement__Group__1 : rule__LocalPathVersionRequirement__Group__1__Impl ;
    public final void rule__LocalPathVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1358:1: ( rule__LocalPathVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1359:2: rule__LocalPathVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1365:1: rule__LocalPathVersionRequirement__Group__1__Impl : ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1369:1: ( ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) )
            // InternalSemver.g:1370:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            {
            // InternalSemver.g:1370:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            // InternalSemver.g:1371:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }
            // InternalSemver.g:1372:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            // InternalSemver.g:1372:3: rule__LocalPathVersionRequirement__LocalPathAssignment_1
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
    // InternalSemver.g:1381:1: rule__URLVersionRequirement__Group__0 : rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 ;
    public final void rule__URLVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1385:1: ( rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 )
            // InternalSemver.g:1386:2: rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1
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
    // InternalSemver.g:1393:1: rule__URLVersionRequirement__Group__0__Impl : ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) ;
    public final void rule__URLVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1397:1: ( ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) )
            // InternalSemver.g:1398:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            {
            // InternalSemver.g:1398:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            // InternalSemver.g:1399:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }
            // InternalSemver.g:1400:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            // InternalSemver.g:1400:3: rule__URLVersionRequirement__ProtocolAssignment_0
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
    // InternalSemver.g:1408:1: rule__URLVersionRequirement__Group__1 : rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 ;
    public final void rule__URLVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1412:1: ( rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 )
            // InternalSemver.g:1413:2: rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2
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
    // InternalSemver.g:1420:1: rule__URLVersionRequirement__Group__1__Impl : ( ( '://' ) ) ;
    public final void rule__URLVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1424:1: ( ( ( '://' ) ) )
            // InternalSemver.g:1425:1: ( ( '://' ) )
            {
            // InternalSemver.g:1425:1: ( ( '://' ) )
            // InternalSemver.g:1426:2: ( '://' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getColonSolidusSolidusKeyword_1()); 
            }
            // InternalSemver.g:1427:2: ( '://' )
            // InternalSemver.g:1427:3: '://'
            {
            match(input,43,FOLLOW_2); if (state.failed) return ;

            }

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
    // InternalSemver.g:1435:1: rule__URLVersionRequirement__Group__2 : rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 ;
    public final void rule__URLVersionRequirement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1439:1: ( rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 )
            // InternalSemver.g:1440:2: rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3
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
    // InternalSemver.g:1447:1: rule__URLVersionRequirement__Group__2__Impl : ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) ;
    public final void rule__URLVersionRequirement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1451:1: ( ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) )
            // InternalSemver.g:1452:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            {
            // InternalSemver.g:1452:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            // InternalSemver.g:1453:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }
            // InternalSemver.g:1454:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            // InternalSemver.g:1454:3: rule__URLVersionRequirement__UrlAssignment_2
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
    // InternalSemver.g:1462:1: rule__URLVersionRequirement__Group__3 : rule__URLVersionRequirement__Group__3__Impl ;
    public final void rule__URLVersionRequirement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1466:1: ( rule__URLVersionRequirement__Group__3__Impl )
            // InternalSemver.g:1467:2: rule__URLVersionRequirement__Group__3__Impl
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
    // InternalSemver.g:1473:1: rule__URLVersionRequirement__Group__3__Impl : ( ( rule__URLVersionRequirement__Group_3__0 )? ) ;
    public final void rule__URLVersionRequirement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1477:1: ( ( ( rule__URLVersionRequirement__Group_3__0 )? ) )
            // InternalSemver.g:1478:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            {
            // InternalSemver.g:1478:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            // InternalSemver.g:1479:2: ( rule__URLVersionRequirement__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }
            // InternalSemver.g:1480:2: ( rule__URLVersionRequirement__Group_3__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==44) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSemver.g:1480:3: rule__URLVersionRequirement__Group_3__0
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
    // InternalSemver.g:1489:1: rule__URLVersionRequirement__Group_3__0 : rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 ;
    public final void rule__URLVersionRequirement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1493:1: ( rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 )
            // InternalSemver.g:1494:2: rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:1501:1: rule__URLVersionRequirement__Group_3__0__Impl : ( '#' ) ;
    public final void rule__URLVersionRequirement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1505:1: ( ( '#' ) )
            // InternalSemver.g:1506:1: ( '#' )
            {
            // InternalSemver.g:1506:1: ( '#' )
            // InternalSemver.g:1507:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); 
            }
            match(input,44,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1516:1: rule__URLVersionRequirement__Group_3__1 : rule__URLVersionRequirement__Group_3__1__Impl ;
    public final void rule__URLVersionRequirement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1520:1: ( rule__URLVersionRequirement__Group_3__1__Impl )
            // InternalSemver.g:1521:2: rule__URLVersionRequirement__Group_3__1__Impl
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
    // InternalSemver.g:1527:1: rule__URLVersionRequirement__Group_3__1__Impl : ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) ;
    public final void rule__URLVersionRequirement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1531:1: ( ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) )
            // InternalSemver.g:1532:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            {
            // InternalSemver.g:1532:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            // InternalSemver.g:1533:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }
            // InternalSemver.g:1534:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            // InternalSemver.g:1534:3: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
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
    // InternalSemver.g:1543:1: rule__URLSemver__Group__0 : rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 ;
    public final void rule__URLSemver__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1547:1: ( rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 )
            // InternalSemver.g:1548:2: rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1
            {
            pushFollow(FOLLOW_12);
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
    // InternalSemver.g:1555:1: rule__URLSemver__Group__0__Impl : ( ( 'semver:' )? ) ;
    public final void rule__URLSemver__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1559:1: ( ( ( 'semver:' )? ) )
            // InternalSemver.g:1560:1: ( ( 'semver:' )? )
            {
            // InternalSemver.g:1560:1: ( ( 'semver:' )? )
            // InternalSemver.g:1561:2: ( 'semver:' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSemverKeyword_0()); 
            }
            // InternalSemver.g:1562:2: ( 'semver:' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==45) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSemver.g:1562:3: 'semver:'
                    {
                    match(input,45,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1570:1: rule__URLSemver__Group__1 : rule__URLSemver__Group__1__Impl ;
    public final void rule__URLSemver__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1574:1: ( rule__URLSemver__Group__1__Impl )
            // InternalSemver.g:1575:2: rule__URLSemver__Group__1__Impl
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
    // InternalSemver.g:1581:1: rule__URLSemver__Group__1__Impl : ( ( rule__URLSemver__SimpleVersionAssignment_1 ) ) ;
    public final void rule__URLSemver__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1585:1: ( ( ( rule__URLSemver__SimpleVersionAssignment_1 ) ) )
            // InternalSemver.g:1586:1: ( ( rule__URLSemver__SimpleVersionAssignment_1 ) )
            {
            // InternalSemver.g:1586:1: ( ( rule__URLSemver__SimpleVersionAssignment_1 ) )
            // InternalSemver.g:1587:2: ( rule__URLSemver__SimpleVersionAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_1()); 
            }
            // InternalSemver.g:1588:2: ( rule__URLSemver__SimpleVersionAssignment_1 )
            // InternalSemver.g:1588:3: rule__URLSemver__SimpleVersionAssignment_1
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
    // InternalSemver.g:1597:1: rule__GitHubVersionRequirement__Group__0 : rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 ;
    public final void rule__GitHubVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1601:1: ( rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 )
            // InternalSemver.g:1602:2: rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1
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
    // InternalSemver.g:1609:1: rule__GitHubVersionRequirement__Group__0__Impl : ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) ;
    public final void rule__GitHubVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1613:1: ( ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) )
            // InternalSemver.g:1614:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            {
            // InternalSemver.g:1614:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            // InternalSemver.g:1615:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }
            // InternalSemver.g:1616:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            // InternalSemver.g:1616:3: rule__GitHubVersionRequirement__GithubUrlAssignment_0
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
    // InternalSemver.g:1624:1: rule__GitHubVersionRequirement__Group__1 : rule__GitHubVersionRequirement__Group__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1628:1: ( rule__GitHubVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1629:2: rule__GitHubVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1635:1: rule__GitHubVersionRequirement__Group__1__Impl : ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) ;
    public final void rule__GitHubVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1639:1: ( ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) )
            // InternalSemver.g:1640:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:1640:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            // InternalSemver.g:1641:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1642:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==44) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSemver.g:1642:3: rule__GitHubVersionRequirement__Group_1__0
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
    // InternalSemver.g:1651:1: rule__GitHubVersionRequirement__Group_1__0 : rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 ;
    public final void rule__GitHubVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1655:1: ( rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 )
            // InternalSemver.g:1656:2: rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:1663:1: rule__GitHubVersionRequirement__Group_1__0__Impl : ( '#' ) ;
    public final void rule__GitHubVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1667:1: ( ( '#' ) )
            // InternalSemver.g:1668:1: ( '#' )
            {
            // InternalSemver.g:1668:1: ( '#' )
            // InternalSemver.g:1669:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); 
            }
            match(input,44,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1678:1: rule__GitHubVersionRequirement__Group_1__1 : rule__GitHubVersionRequirement__Group_1__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1682:1: ( rule__GitHubVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1683:2: rule__GitHubVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1689:1: rule__GitHubVersionRequirement__Group_1__1__Impl : ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) ;
    public final void rule__GitHubVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1693:1: ( ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1694:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1694:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1695:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1696:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            // InternalSemver.g:1696:3: rule__GitHubVersionRequirement__CommitISHAssignment_1_1
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
    // InternalSemver.g:1705:1: rule__VersionRangeSetRequirement__Group__0 : rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 ;
    public final void rule__VersionRangeSetRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1709:1: ( rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 )
            // InternalSemver.g:1710:2: rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:1717:1: rule__VersionRangeSetRequirement__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSetRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1721:1: ( ( () ) )
            // InternalSemver.g:1722:1: ( () )
            {
            // InternalSemver.g:1722:1: ( () )
            // InternalSemver.g:1723:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }
            // InternalSemver.g:1724:2: ()
            // InternalSemver.g:1724:3: 
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
    // InternalSemver.g:1732:1: rule__VersionRangeSetRequirement__Group__1 : rule__VersionRangeSetRequirement__Group__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1736:1: ( rule__VersionRangeSetRequirement__Group__1__Impl )
            // InternalSemver.g:1737:2: rule__VersionRangeSetRequirement__Group__1__Impl
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
    // InternalSemver.g:1743:1: rule__VersionRangeSetRequirement__Group__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) ;
    public final void rule__VersionRangeSetRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1747:1: ( ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) )
            // InternalSemver.g:1748:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:1748:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            // InternalSemver.g:1749:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1750:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0>=RULE_DIGITS && LA24_0<=RULE_ASTERIX)||(LA24_0>=35 && LA24_0<=41)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSemver.g:1750:3: rule__VersionRangeSetRequirement__Group_1__0
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
    // InternalSemver.g:1759:1: rule__VersionRangeSetRequirement__Group_1__0 : rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1763:1: ( rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 )
            // InternalSemver.g:1764:2: rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1
            {
            pushFollow(FOLLOW_13);
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
    // InternalSemver.g:1771:1: rule__VersionRangeSetRequirement__Group_1__0__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1775:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) )
            // InternalSemver.g:1776:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            {
            // InternalSemver.g:1776:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            // InternalSemver.g:1777:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }
            // InternalSemver.g:1778:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            // InternalSemver.g:1778:3: rule__VersionRangeSetRequirement__RangesAssignment_1_0
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
    // InternalSemver.g:1786:1: rule__VersionRangeSetRequirement__Group_1__1 : rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1790:1: ( rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 )
            // InternalSemver.g:1791:2: rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2
            {
            pushFollow(FOLLOW_13);
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
    // InternalSemver.g:1798:1: rule__VersionRangeSetRequirement__Group_1__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1802:1: ( ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) )
            // InternalSemver.g:1803:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            {
            // InternalSemver.g:1803:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            // InternalSemver.g:1804:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }
            // InternalSemver.g:1805:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            loop25:
            do {
                int alt25=2;
                alt25 = dfa25.predict(input);
                switch (alt25) {
            	case 1 :
            	    // InternalSemver.g:1805:3: rule__VersionRangeSetRequirement__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_14);
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
    // InternalSemver.g:1813:1: rule__VersionRangeSetRequirement__Group_1__2 : rule__VersionRangeSetRequirement__Group_1__2__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1817:1: ( rule__VersionRangeSetRequirement__Group_1__2__Impl )
            // InternalSemver.g:1818:2: rule__VersionRangeSetRequirement__Group_1__2__Impl
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
    // InternalSemver.g:1824:1: rule__VersionRangeSetRequirement__Group_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1828:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1829:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1829:1: ( ( RULE_WS )* )
            // InternalSemver.g:1830:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); 
            }
            // InternalSemver.g:1831:2: ( RULE_WS )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_WS) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSemver.g:1831:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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
    // InternalSemver.g:1840:1: rule__VersionRangeSetRequirement__Group_1_1__0 : rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1844:1: ( rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 )
            // InternalSemver.g:1845:2: rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1
            {
            pushFollow(FOLLOW_13);
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
    // InternalSemver.g:1852:1: rule__VersionRangeSetRequirement__Group_1_1__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1856:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1857:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1857:1: ( ( RULE_WS )* )
            // InternalSemver.g:1858:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }
            // InternalSemver.g:1859:2: ( RULE_WS )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==RULE_WS) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSemver.g:1859:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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
    // InternalSemver.g:1867:1: rule__VersionRangeSetRequirement__Group_1_1__1 : rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1871:1: ( rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 )
            // InternalSemver.g:1872:2: rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2
            {
            pushFollow(FOLLOW_15);
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
    // InternalSemver.g:1879:1: rule__VersionRangeSetRequirement__Group_1_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1883:1: ( ( '||' ) )
            // InternalSemver.g:1884:1: ( '||' )
            {
            // InternalSemver.g:1884:1: ( '||' )
            // InternalSemver.g:1885:2: '||'
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
    // InternalSemver.g:1894:1: rule__VersionRangeSetRequirement__Group_1_1__2 : rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1898:1: ( rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 )
            // InternalSemver.g:1899:2: rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3
            {
            pushFollow(FOLLOW_15);
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
    // InternalSemver.g:1906:1: rule__VersionRangeSetRequirement__Group_1_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1910:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1911:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1911:1: ( ( RULE_WS )* )
            // InternalSemver.g:1912:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }
            // InternalSemver.g:1913:2: ( RULE_WS )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==RULE_WS) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSemver.g:1913:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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
    // InternalSemver.g:1921:1: rule__VersionRangeSetRequirement__Group_1_1__3 : rule__VersionRangeSetRequirement__Group_1_1__3__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1925:1: ( rule__VersionRangeSetRequirement__Group_1_1__3__Impl )
            // InternalSemver.g:1926:2: rule__VersionRangeSetRequirement__Group_1_1__3__Impl
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
    // InternalSemver.g:1932:1: rule__VersionRangeSetRequirement__Group_1_1__3__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1936:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) )
            // InternalSemver.g:1937:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            {
            // InternalSemver.g:1937:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            // InternalSemver.g:1938:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }
            // InternalSemver.g:1939:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            // InternalSemver.g:1939:3: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
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
    // InternalSemver.g:1948:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1952:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemver.g:1953:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:1960:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1964:1: ( ( () ) )
            // InternalSemver.g:1965:1: ( () )
            {
            // InternalSemver.g:1965:1: ( () )
            // InternalSemver.g:1966:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }
            // InternalSemver.g:1967:2: ()
            // InternalSemver.g:1967:3: 
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
    // InternalSemver.g:1975:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1979:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemver.g:1980:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
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
    // InternalSemver.g:1987:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1991:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemver.g:1992:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemver.g:1992:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemver.g:1993:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }
            // InternalSemver.g:1994:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemver.g:1994:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSemver.g:2002:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2006:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemver.g:2007:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
            {
            pushFollow(FOLLOW_16);
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
    // InternalSemver.g:2014:1: rule__HyphenVersionRange__Group__2__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2018:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2019:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2019:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2020:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2020:2: ( ( RULE_WS ) )
            // InternalSemver.g:2021:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2022:3: ( RULE_WS )
            // InternalSemver.g:2022:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }

            }

            // InternalSemver.g:2025:2: ( ( RULE_WS )* )
            // InternalSemver.g:2026:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2027:3: ( RULE_WS )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==RULE_WS) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSemver.g:2027:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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
    // InternalSemver.g:2036:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2040:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSemver.g:2041:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
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
    // InternalSemver.g:2048:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2052:1: ( ( '-' ) )
            // InternalSemver.g:2053:1: ( '-' )
            {
            // InternalSemver.g:2053:1: ( '-' )
            // InternalSemver.g:2054:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2063:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2067:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSemver.g:2068:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2075:1: rule__HyphenVersionRange__Group__4__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2079:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2080:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2080:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2081:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2081:2: ( ( RULE_WS ) )
            // InternalSemver.g:2082:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2083:3: ( RULE_WS )
            // InternalSemver.g:2083:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }

            }

            // InternalSemver.g:2086:2: ( ( RULE_WS )* )
            // InternalSemver.g:2087:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2088:3: ( RULE_WS )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_WS) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSemver.g:2088:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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
    // InternalSemver.g:2097:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2101:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSemver.g:2102:2: rule__HyphenVersionRange__Group__5__Impl
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
    // InternalSemver.g:2108:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2112:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSemver.g:2113:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSemver.g:2113:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSemver.g:2114:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }
            // InternalSemver.g:2115:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSemver.g:2115:3: rule__HyphenVersionRange__ToAssignment_5
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
    // InternalSemver.g:2124:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2128:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSemver.g:2129:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2136:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2140:1: ( ( () ) )
            // InternalSemver.g:2141:1: ( () )
            {
            // InternalSemver.g:2141:1: ( () )
            // InternalSemver.g:2142:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }
            // InternalSemver.g:2143:2: ()
            // InternalSemver.g:2143:3: 
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
    // InternalSemver.g:2151:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2155:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSemver.g:2156:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
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
    // InternalSemver.g:2163:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2167:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSemver.g:2168:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSemver.g:2168:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSemver.g:2169:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }
            // InternalSemver.g:2170:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSemver.g:2170:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
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
    // InternalSemver.g:2178:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2182:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSemver.g:2183:2: rule__VersionRangeContraint__Group__2__Impl
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
    // InternalSemver.g:2189:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2193:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSemver.g:2194:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSemver.g:2194:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSemver.g:2195:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }
            // InternalSemver.g:2196:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop31:
            do {
                int alt31=2;
                alt31 = dfa31.predict(input);
                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:2196:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_5);
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
    // InternalSemver.g:2205:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2209:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSemver.g:2210:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2217:1: rule__VersionRangeContraint__Group_2__0__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2221:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2222:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2222:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2223:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2223:2: ( ( RULE_WS ) )
            // InternalSemver.g:2224:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2225:3: ( RULE_WS )
            // InternalSemver.g:2225:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }

            }

            // InternalSemver.g:2228:2: ( ( RULE_WS )* )
            // InternalSemver.g:2229:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2230:3: ( RULE_WS )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_WS) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:2230:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

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
    // InternalSemver.g:2239:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2243:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSemver.g:2244:2: rule__VersionRangeContraint__Group_2__1__Impl
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
    // InternalSemver.g:2250:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2254:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSemver.g:2255:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSemver.g:2255:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSemver.g:2256:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }
            // InternalSemver.g:2257:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSemver.g:2257:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
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
    // InternalSemver.g:2266:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2270:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemver.g:2271:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2278:1: rule__SimpleVersion__Group__0__Impl : ( () ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2282:1: ( ( () ) )
            // InternalSemver.g:2283:1: ( () )
            {
            // InternalSemver.g:2283:1: ( () )
            // InternalSemver.g:2284:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0()); 
            }
            // InternalSemver.g:2285:2: ()
            // InternalSemver.g:2285:3: 
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
    // InternalSemver.g:2293:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2297:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemver.g:2298:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2305:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__Group_1__0 )* ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2309:1: ( ( ( rule__SimpleVersion__Group_1__0 )* ) )
            // InternalSemver.g:2310:1: ( ( rule__SimpleVersion__Group_1__0 )* )
            {
            // InternalSemver.g:2310:1: ( ( rule__SimpleVersion__Group_1__0 )* )
            // InternalSemver.g:2311:2: ( rule__SimpleVersion__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup_1()); 
            }
            // InternalSemver.g:2312:2: ( rule__SimpleVersion__Group_1__0 )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( ((LA33_0>=35 && LA33_0<=41)) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSemver.g:2312:3: rule__SimpleVersion__Group_1__0
            	    {
            	    pushFollow(FOLLOW_17);
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
    // InternalSemver.g:2320:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl rule__SimpleVersion__Group__3 ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2324:1: ( rule__SimpleVersion__Group__2__Impl rule__SimpleVersion__Group__3 )
            // InternalSemver.g:2325:2: rule__SimpleVersion__Group__2__Impl rule__SimpleVersion__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__SimpleVersion__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__3();

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
    // InternalSemver.g:2332:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__WithLetterVAssignment_2 )? ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2336:1: ( ( ( rule__SimpleVersion__WithLetterVAssignment_2 )? ) )
            // InternalSemver.g:2337:1: ( ( rule__SimpleVersion__WithLetterVAssignment_2 )? )
            {
            // InternalSemver.g:2337:1: ( ( rule__SimpleVersion__WithLetterVAssignment_2 )? )
            // InternalSemver.g:2338:2: ( rule__SimpleVersion__WithLetterVAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_2()); 
            }
            // InternalSemver.g:2339:2: ( rule__SimpleVersion__WithLetterVAssignment_2 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RULE_LETTER_V) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalSemver.g:2339:3: rule__SimpleVersion__WithLetterVAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__SimpleVersion__WithLetterVAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_2()); 
            }

            }


            }

        }
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


    // $ANTLR start "rule__SimpleVersion__Group__3"
    // InternalSemver.g:2347:1: rule__SimpleVersion__Group__3 : rule__SimpleVersion__Group__3__Impl ;
    public final void rule__SimpleVersion__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2351:1: ( rule__SimpleVersion__Group__3__Impl )
            // InternalSemver.g:2352:2: rule__SimpleVersion__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__3__Impl();

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
    // $ANTLR end "rule__SimpleVersion__Group__3"


    // $ANTLR start "rule__SimpleVersion__Group__3__Impl"
    // InternalSemver.g:2358:1: rule__SimpleVersion__Group__3__Impl : ( ( rule__SimpleVersion__NumberAssignment_3 ) ) ;
    public final void rule__SimpleVersion__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2362:1: ( ( ( rule__SimpleVersion__NumberAssignment_3 ) ) )
            // InternalSemver.g:2363:1: ( ( rule__SimpleVersion__NumberAssignment_3 ) )
            {
            // InternalSemver.g:2363:1: ( ( rule__SimpleVersion__NumberAssignment_3 ) )
            // InternalSemver.g:2364:2: ( rule__SimpleVersion__NumberAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_3()); 
            }
            // InternalSemver.g:2365:2: ( rule__SimpleVersion__NumberAssignment_3 )
            // InternalSemver.g:2365:3: rule__SimpleVersion__NumberAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__NumberAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getNumberAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__Group__3__Impl"


    // $ANTLR start "rule__SimpleVersion__Group_1__0"
    // InternalSemver.g:2374:1: rule__SimpleVersion__Group_1__0 : rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1 ;
    public final void rule__SimpleVersion__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2378:1: ( rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1 )
            // InternalSemver.g:2379:2: rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:2386:1: rule__SimpleVersion__Group_1__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) ) ;
    public final void rule__SimpleVersion__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2390:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) ) )
            // InternalSemver.g:2391:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) )
            {
            // InternalSemver.g:2391:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) )
            // InternalSemver.g:2392:2: ( rule__SimpleVersion__ComparatorsAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1_0()); 
            }
            // InternalSemver.g:2393:2: ( rule__SimpleVersion__ComparatorsAssignment_1_0 )
            // InternalSemver.g:2393:3: rule__SimpleVersion__ComparatorsAssignment_1_0
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
    // InternalSemver.g:2401:1: rule__SimpleVersion__Group_1__1 : rule__SimpleVersion__Group_1__1__Impl ;
    public final void rule__SimpleVersion__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2405:1: ( rule__SimpleVersion__Group_1__1__Impl )
            // InternalSemver.g:2406:2: rule__SimpleVersion__Group_1__1__Impl
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
    // InternalSemver.g:2412:1: rule__SimpleVersion__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__SimpleVersion__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2416:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2417:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2417:1: ( ( RULE_WS )* )
            // InternalSemver.g:2418:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:2419:2: ( RULE_WS )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_WS) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSemver.g:2419:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop35;
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
    // InternalSemver.g:2428:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2432:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemver.g:2433:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
            {
            pushFollow(FOLLOW_18);
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
    // InternalSemver.g:2440:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2444:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemver.g:2445:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemver.g:2445:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemver.g:2446:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }
            // InternalSemver.g:2447:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemver.g:2447:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSemver.g:2455:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2459:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSemver.g:2460:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
            {
            pushFollow(FOLLOW_18);
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
    // InternalSemver.g:2467:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2471:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemver.g:2472:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemver.g:2472:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemver.g:2473:2: ( rule__VersionNumber__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }
            // InternalSemver.g:2474:2: ( rule__VersionNumber__Group_1__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==30) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalSemver.g:2474:3: rule__VersionNumber__Group_1__0
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
    // InternalSemver.g:2482:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2486:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSemver.g:2487:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSemver.g:2493:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2497:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSemver.g:2498:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSemver.g:2498:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSemver.g:2499:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }
            // InternalSemver.g:2500:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( ((LA37_0>=31 && LA37_0<=32)) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalSemver.g:2500:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSemver.g:2509:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2513:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemver.g:2514:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2521:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2525:1: ( ( '.' ) )
            // InternalSemver.g:2526:1: ( '.' )
            {
            // InternalSemver.g:2526:1: ( '.' )
            // InternalSemver.g:2527:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2536:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2540:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemver.g:2541:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
            {
            pushFollow(FOLLOW_19);
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
    // InternalSemver.g:2548:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2552:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemver.g:2553:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemver.g:2553:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemver.g:2554:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }
            // InternalSemver.g:2555:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemver.g:2555:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSemver.g:2563:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2567:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemver.g:2568:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSemver.g:2574:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2578:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemver.g:2579:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemver.g:2579:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemver.g:2580:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }
            // InternalSemver.g:2581:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==30) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalSemver.g:2581:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSemver.g:2590:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2594:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemver.g:2595:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2602:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2606:1: ( ( '.' ) )
            // InternalSemver.g:2607:1: ( '.' )
            {
            // InternalSemver.g:2607:1: ( '.' )
            // InternalSemver.g:2608:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2617:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2621:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemver.g:2622:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
            {
            pushFollow(FOLLOW_19);
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
    // InternalSemver.g:2629:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2633:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSemver.g:2634:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSemver.g:2634:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSemver.g:2635:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }
            // InternalSemver.g:2636:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSemver.g:2636:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSemver.g:2644:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2648:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemver.g:2649:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSemver.g:2655:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2659:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSemver.g:2660:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSemver.g:2660:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSemver.g:2661:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }
            // InternalSemver.g:2662:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==30) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSemver.g:2662:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_20);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop39;
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
    // InternalSemver.g:2671:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2675:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSemver.g:2676:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSemver.g:2683:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2687:1: ( ( '.' ) )
            // InternalSemver.g:2688:1: ( '.' )
            {
            // InternalSemver.g:2688:1: ( '.' )
            // InternalSemver.g:2689:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2698:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2702:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSemver.g:2703:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSemver.g:2709:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2713:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSemver.g:2714:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSemver.g:2714:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSemver.g:2715:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }
            // InternalSemver.g:2716:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSemver.g:2716:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSemver.g:2725:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2729:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemver.g:2730:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:2737:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2741:1: ( ( '-' ) )
            // InternalSemver.g:2742:1: ( '-' )
            {
            // InternalSemver.g:2742:1: ( '-' )
            // InternalSemver.g:2743:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2752:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2756:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSemver.g:2757:2: rule__Qualifier__Group_0__1__Impl
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
    // InternalSemver.g:2763:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2767:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemver.g:2768:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemver.g:2768:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemver.g:2769:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }
            // InternalSemver.g:2770:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemver.g:2770:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSemver.g:2779:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2783:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemver.g:2784:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:2791:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2795:1: ( ( '+' ) )
            // InternalSemver.g:2796:1: ( '+' )
            {
            // InternalSemver.g:2796:1: ( '+' )
            // InternalSemver.g:2797:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2806:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2810:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemver.g:2811:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSemver.g:2817:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2821:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemver.g:2822:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemver.g:2822:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemver.g:2823:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }
            // InternalSemver.g:2824:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemver.g:2824:3: rule__Qualifier__BuildMetadataAssignment_1_1
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
    // InternalSemver.g:2833:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2837:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSemver.g:2838:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:2845:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2849:1: ( ( '-' ) )
            // InternalSemver.g:2850:1: ( '-' )
            {
            // InternalSemver.g:2850:1: ( '-' )
            // InternalSemver.g:2851:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2860:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2864:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSemver.g:2865:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
            {
            pushFollow(FOLLOW_21);
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
    // InternalSemver.g:2872:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2876:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSemver.g:2877:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSemver.g:2877:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSemver.g:2878:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            }
            // InternalSemver.g:2879:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSemver.g:2879:3: rule__Qualifier__PreReleaseAssignment_2_1
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
    // InternalSemver.g:2887:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2891:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSemver.g:2892:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:2899:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2903:1: ( ( '+' ) )
            // InternalSemver.g:2904:1: ( '+' )
            {
            // InternalSemver.g:2904:1: ( '+' )
            // InternalSemver.g:2905:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2914:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2918:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSemver.g:2919:2: rule__Qualifier__Group_2__3__Impl
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
    // InternalSemver.g:2925:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2929:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSemver.g:2930:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSemver.g:2930:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSemver.g:2931:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            }
            // InternalSemver.g:2932:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSemver.g:2932:3: rule__Qualifier__BuildMetadataAssignment_2_3
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
    // InternalSemver.g:2941:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2945:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSemver.g:2946:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
            {
            pushFollow(FOLLOW_19);
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
    // InternalSemver.g:2953:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2957:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSemver.g:2958:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSemver.g:2958:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSemver.g:2959:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }
            // InternalSemver.g:2960:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSemver.g:2960:3: rule__QualifierTag__PartsAssignment_0
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
    // InternalSemver.g:2968:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2972:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSemver.g:2973:2: rule__QualifierTag__Group__1__Impl
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
    // InternalSemver.g:2979:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2983:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSemver.g:2984:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSemver.g:2984:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSemver.g:2985:2: ( rule__QualifierTag__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }
            // InternalSemver.g:2986:2: ( rule__QualifierTag__Group_1__0 )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==30) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSemver.g:2986:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_20);
            	    rule__QualifierTag__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop40;
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
    // InternalSemver.g:2995:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2999:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSemver.g:3000:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:3007:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3011:1: ( ( '.' ) )
            // InternalSemver.g:3012:1: ( '.' )
            {
            // InternalSemver.g:3012:1: ( '.' )
            // InternalSemver.g:3013:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3022:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3026:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSemver.g:3027:2: rule__QualifierTag__Group_1__1__Impl
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
    // InternalSemver.g:3033:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3037:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSemver.g:3038:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSemver.g:3038:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSemver.g:3039:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }
            // InternalSemver.g:3040:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSemver.g:3040:3: rule__QualifierTag__PartsAssignment_1_1
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


    // $ANTLR start "rule__PATH__Group__0"
    // InternalSemver.g:3049:1: rule__PATH__Group__0 : rule__PATH__Group__0__Impl rule__PATH__Group__1 ;
    public final void rule__PATH__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3053:1: ( rule__PATH__Group__0__Impl rule__PATH__Group__1 )
            // InternalSemver.g:3054:2: rule__PATH__Group__0__Impl rule__PATH__Group__1
            {
            pushFollow(FOLLOW_22);
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
    // InternalSemver.g:3061:1: rule__PATH__Group__0__Impl : ( ( rule__PATH__Alternatives_0 ) ) ;
    public final void rule__PATH__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3065:1: ( ( ( rule__PATH__Alternatives_0 ) ) )
            // InternalSemver.g:3066:1: ( ( rule__PATH__Alternatives_0 ) )
            {
            // InternalSemver.g:3066:1: ( ( rule__PATH__Alternatives_0 ) )
            // InternalSemver.g:3067:2: ( rule__PATH__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:3068:2: ( rule__PATH__Alternatives_0 )
            // InternalSemver.g:3068:3: rule__PATH__Alternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__PATH__Alternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3076:1: rule__PATH__Group__1 : rule__PATH__Group__1__Impl ;
    public final void rule__PATH__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3080:1: ( rule__PATH__Group__1__Impl )
            // InternalSemver.g:3081:2: rule__PATH__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PATH__Group__1__Impl();

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
    // InternalSemver.g:3087:1: rule__PATH__Group__1__Impl : ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) ) ;
    public final void rule__PATH__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3091:1: ( ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) ) )
            // InternalSemver.g:3092:1: ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3092:1: ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) )
            // InternalSemver.g:3093:2: ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* )
            {
            // InternalSemver.g:3093:2: ( ( rule__PATH__Alternatives_1 ) )
            // InternalSemver.g:3094:3: ( rule__PATH__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3095:3: ( rule__PATH__Alternatives_1 )
            // InternalSemver.g:3095:4: rule__PATH__Alternatives_1
            {
            pushFollow(FOLLOW_23);
            rule__PATH__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3098:2: ( ( rule__PATH__Alternatives_1 )* )
            // InternalSemver.g:3099:3: ( rule__PATH__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3100:3: ( rule__PATH__Alternatives_1 )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( ((LA41_0>=RULE_LETTER_NO_VX && LA41_0<=RULE_LETTER_X)||(LA41_0>=29 && LA41_0<=31)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSemver.g:3100:4: rule__PATH__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_23);
            	    rule__PATH__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_1()); 
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


    // $ANTLR start "rule__URL_PROTOCOL__Group__0"
    // InternalSemver.g:3110:1: rule__URL_PROTOCOL__Group__0 : rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 ;
    public final void rule__URL_PROTOCOL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3114:1: ( rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 )
            // InternalSemver.g:3115:2: rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1
            {
            pushFollow(FOLLOW_24);
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
    // InternalSemver.g:3122:1: rule__URL_PROTOCOL__Group__0__Impl : ( RULE_LETTER_NO_VX ) ;
    public final void rule__URL_PROTOCOL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3126:1: ( ( RULE_LETTER_NO_VX ) )
            // InternalSemver.g:3127:1: ( RULE_LETTER_NO_VX )
            {
            // InternalSemver.g:3127:1: ( RULE_LETTER_NO_VX )
            // InternalSemver.g:3128:2: RULE_LETTER_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXTerminalRuleCall_0()); 
            }
            match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXTerminalRuleCall_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3137:1: rule__URL_PROTOCOL__Group__1 : rule__URL_PROTOCOL__Group__1__Impl ;
    public final void rule__URL_PROTOCOL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3141:1: ( rule__URL_PROTOCOL__Group__1__Impl )
            // InternalSemver.g:3142:2: rule__URL_PROTOCOL__Group__1__Impl
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
    // InternalSemver.g:3148:1: rule__URL_PROTOCOL__Group__1__Impl : ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) ;
    public final void rule__URL_PROTOCOL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3152:1: ( ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) )
            // InternalSemver.g:3153:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3153:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            // InternalSemver.g:3154:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            {
            // InternalSemver.g:3154:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) )
            // InternalSemver.g:3155:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3156:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            // InternalSemver.g:3156:4: rule__URL_PROTOCOL__Alternatives_1
            {
            pushFollow(FOLLOW_25);
            rule__URL_PROTOCOL__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3159:2: ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            // InternalSemver.g:3160:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3161:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==RULE_LETTER_NO_VX||(LA42_0>=RULE_LETTER_V && LA42_0<=RULE_LETTER_X)||LA42_0==32) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSemver.g:3161:4: rule__URL_PROTOCOL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_25);
            	    rule__URL_PROTOCOL__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop42;
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
    // InternalSemver.g:3171:1: rule__URL__Group__0 : rule__URL__Group__0__Impl rule__URL__Group__1 ;
    public final void rule__URL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3175:1: ( rule__URL__Group__0__Impl rule__URL__Group__1 )
            // InternalSemver.g:3176:2: rule__URL__Group__0__Impl rule__URL__Group__1
            {
            pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3183:1: rule__URL__Group__0__Impl : ( RULE_LETTER_NO_VX ) ;
    public final void rule__URL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3187:1: ( ( RULE_LETTER_NO_VX ) )
            // InternalSemver.g:3188:1: ( RULE_LETTER_NO_VX )
            {
            // InternalSemver.g:3188:1: ( RULE_LETTER_NO_VX )
            // InternalSemver.g:3189:2: RULE_LETTER_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_0()); 
            }
            match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getLETTER_NO_VXTerminalRuleCall_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3198:1: rule__URL__Group__1 : rule__URL__Group__1__Impl rule__URL__Group__2 ;
    public final void rule__URL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3202:1: ( rule__URL__Group__1__Impl rule__URL__Group__2 )
            // InternalSemver.g:3203:2: rule__URL__Group__1__Impl rule__URL__Group__2
            {
            pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3210:1: rule__URL__Group__1__Impl : ( ( rule__URL__Alternatives_1 )* ) ;
    public final void rule__URL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3214:1: ( ( ( rule__URL__Alternatives_1 )* ) )
            // InternalSemver.g:3215:1: ( ( rule__URL__Alternatives_1 )* )
            {
            // InternalSemver.g:3215:1: ( ( rule__URL__Alternatives_1 )* )
            // InternalSemver.g:3216:2: ( rule__URL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3217:2: ( rule__URL__Alternatives_1 )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=RULE_LETTER_NO_VX && LA43_0<=RULE_LETTER_X)||LA43_0==31) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSemver.g:3217:3: rule__URL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

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
    // InternalSemver.g:3225:1: rule__URL__Group__2 : rule__URL__Group__2__Impl rule__URL__Group__3 ;
    public final void rule__URL__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3229:1: ( rule__URL__Group__2__Impl rule__URL__Group__3 )
            // InternalSemver.g:3230:2: rule__URL__Group__2__Impl rule__URL__Group__3
            {
            pushFollow(FOLLOW_26);
            rule__URL__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__URL__Group__3();

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
    // InternalSemver.g:3237:1: rule__URL__Group__2__Impl : ( ( rule__URL__Alternatives_2 ) ) ;
    public final void rule__URL__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3241:1: ( ( ( rule__URL__Alternatives_2 ) ) )
            // InternalSemver.g:3242:1: ( ( rule__URL__Alternatives_2 ) )
            {
            // InternalSemver.g:3242:1: ( ( rule__URL__Alternatives_2 ) )
            // InternalSemver.g:3243:2: ( rule__URL__Alternatives_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:3244:2: ( rule__URL__Alternatives_2 )
            // InternalSemver.g:3244:3: rule__URL__Alternatives_2
            {
            pushFollow(FOLLOW_2);
            rule__URL__Alternatives_2();

            state._fsp--;
            if (state.failed) return ;

            }

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


    // $ANTLR start "rule__URL__Group__3"
    // InternalSemver.g:3252:1: rule__URL__Group__3 : rule__URL__Group__3__Impl ;
    public final void rule__URL__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3256:1: ( rule__URL__Group__3__Impl )
            // InternalSemver.g:3257:2: rule__URL__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__URL__Group__3__Impl();

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
    // $ANTLR end "rule__URL__Group__3"


    // $ANTLR start "rule__URL__Group__3__Impl"
    // InternalSemver.g:3263:1: rule__URL__Group__3__Impl : ( ( rule__URL__Alternatives_3 )* ) ;
    public final void rule__URL__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3267:1: ( ( ( rule__URL__Alternatives_3 )* ) )
            // InternalSemver.g:3268:1: ( ( rule__URL__Alternatives_3 )* )
            {
            // InternalSemver.g:3268:1: ( ( rule__URL__Alternatives_3 )* )
            // InternalSemver.g:3269:2: ( rule__URL__Alternatives_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_3()); 
            }
            // InternalSemver.g:3270:2: ( rule__URL__Alternatives_3 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( ((LA44_0>=RULE_LETTER_NO_VX && LA44_0<=RULE_LETTER_X)||(LA44_0>=29 && LA44_0<=31)||(LA44_0>=33 && LA44_0<=34)) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSemver.g:3270:3: rule__URL__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_27);
            	    rule__URL__Alternatives_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getAlternatives_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__URL__Group__3__Impl"


    // $ANTLR start "rule__TAG__Group__0"
    // InternalSemver.g:3279:1: rule__TAG__Group__0 : rule__TAG__Group__0__Impl rule__TAG__Group__1 ;
    public final void rule__TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3283:1: ( rule__TAG__Group__0__Impl rule__TAG__Group__1 )
            // InternalSemver.g:3284:2: rule__TAG__Group__0__Impl rule__TAG__Group__1
            {
            pushFollow(FOLLOW_28);
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
    // InternalSemver.g:3291:1: rule__TAG__Group__0__Impl : ( ( rule__TAG__Alternatives_0 ) ) ;
    public final void rule__TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3295:1: ( ( ( rule__TAG__Alternatives_0 ) ) )
            // InternalSemver.g:3296:1: ( ( rule__TAG__Alternatives_0 ) )
            {
            // InternalSemver.g:3296:1: ( ( rule__TAG__Alternatives_0 ) )
            // InternalSemver.g:3297:2: ( rule__TAG__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:3298:2: ( rule__TAG__Alternatives_0 )
            // InternalSemver.g:3298:3: rule__TAG__Alternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__TAG__Alternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getAlternatives_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3306:1: rule__TAG__Group__1 : rule__TAG__Group__1__Impl ;
    public final void rule__TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3310:1: ( rule__TAG__Group__1__Impl )
            // InternalSemver.g:3311:2: rule__TAG__Group__1__Impl
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
    // InternalSemver.g:3317:1: rule__TAG__Group__1__Impl : ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) ;
    public final void rule__TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3321:1: ( ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) )
            // InternalSemver.g:3322:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3322:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            // InternalSemver.g:3323:2: ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* )
            {
            // InternalSemver.g:3323:2: ( ( rule__TAG__Alternatives_1 ) )
            // InternalSemver.g:3324:3: ( rule__TAG__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3325:3: ( rule__TAG__Alternatives_1 )
            // InternalSemver.g:3325:4: rule__TAG__Alternatives_1
            {
            pushFollow(FOLLOW_3);
            rule__TAG__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3328:2: ( ( rule__TAG__Alternatives_1 )* )
            // InternalSemver.g:3329:3: ( rule__TAG__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3330:3: ( rule__TAG__Alternatives_1 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=RULE_LETTER_NO_VX && LA45_0<=RULE_LETTER_X)||LA45_0==31) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSemver.g:3330:4: rule__TAG__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__TAG__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getAlternatives_1()); 
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
    // $ANTLR end "rule__TAG__Group__1__Impl"


    // $ANTLR start "rule__LocalPathVersionRequirement__LocalPathAssignment_1"
    // InternalSemver.g:3340:1: rule__LocalPathVersionRequirement__LocalPathAssignment_1 : ( rulePATH ) ;
    public final void rule__LocalPathVersionRequirement__LocalPathAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3344:1: ( ( rulePATH ) )
            // InternalSemver.g:3345:2: ( rulePATH )
            {
            // InternalSemver.g:3345:2: ( rulePATH )
            // InternalSemver.g:3346:3: rulePATH
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
    // InternalSemver.g:3355:1: rule__URLVersionRequirement__ProtocolAssignment_0 : ( ruleURL_PROTOCOL ) ;
    public final void rule__URLVersionRequirement__ProtocolAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3359:1: ( ( ruleURL_PROTOCOL ) )
            // InternalSemver.g:3360:2: ( ruleURL_PROTOCOL )
            {
            // InternalSemver.g:3360:2: ( ruleURL_PROTOCOL )
            // InternalSemver.g:3361:3: ruleURL_PROTOCOL
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
    // InternalSemver.g:3370:1: rule__URLVersionRequirement__UrlAssignment_2 : ( ruleURL ) ;
    public final void rule__URLVersionRequirement__UrlAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3374:1: ( ( ruleURL ) )
            // InternalSemver.g:3375:2: ( ruleURL )
            {
            // InternalSemver.g:3375:2: ( ruleURL )
            // InternalSemver.g:3376:3: ruleURL
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
    // InternalSemver.g:3385:1: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 : ( ruleURLVersionSpecifier ) ;
    public final void rule__URLVersionRequirement__VersionSpecifierAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3389:1: ( ( ruleURLVersionSpecifier ) )
            // InternalSemver.g:3390:2: ( ruleURLVersionSpecifier )
            {
            // InternalSemver.g:3390:2: ( ruleURLVersionSpecifier )
            // InternalSemver.g:3391:3: ruleURLVersionSpecifier
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
    // InternalSemver.g:3400:1: rule__URLSemver__SimpleVersionAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__URLSemver__SimpleVersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3404:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3405:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3405:2: ( ruleSimpleVersion )
            // InternalSemver.g:3406:3: ruleSimpleVersion
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
    // InternalSemver.g:3415:1: rule__URLCommitISH__CommitISHAssignment : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URLCommitISH__CommitISHAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3419:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3420:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3420:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3421:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:3430:1: rule__TagVersionRequirement__TagNameAssignment : ( ruleTAG ) ;
    public final void rule__TagVersionRequirement__TagNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3434:1: ( ( ruleTAG ) )
            // InternalSemver.g:3435:2: ( ruleTAG )
            {
            // InternalSemver.g:3435:2: ( ruleTAG )
            // InternalSemver.g:3436:3: ruleTAG
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
    // InternalSemver.g:3445:1: rule__GitHubVersionRequirement__GithubUrlAssignment_0 : ( ruleURL ) ;
    public final void rule__GitHubVersionRequirement__GithubUrlAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3449:1: ( ( ruleURL ) )
            // InternalSemver.g:3450:2: ( ruleURL )
            {
            // InternalSemver.g:3450:2: ( ruleURL )
            // InternalSemver.g:3451:3: ruleURL
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
    // InternalSemver.g:3460:1: rule__GitHubVersionRequirement__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__GitHubVersionRequirement__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3464:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3465:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3465:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3466:3: ruleALPHA_NUMERIC_CHARS
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


    // $ANTLR start "rule__VersionRangeSetRequirement__RangesAssignment_1_0"
    // InternalSemver.g:3475:1: rule__VersionRangeSetRequirement__RangesAssignment_1_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3479:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:3480:2: ( ruleVersionRange )
            {
            // InternalSemver.g:3480:2: ( ruleVersionRange )
            // InternalSemver.g:3481:3: ruleVersionRange
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
    // InternalSemver.g:3490:1: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3494:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:3495:2: ( ruleVersionRange )
            {
            // InternalSemver.g:3495:2: ( ruleVersionRange )
            // InternalSemver.g:3496:3: ruleVersionRange
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
    // InternalSemver.g:3505:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3509:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3510:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3510:2: ( ruleVersionNumber )
            // InternalSemver.g:3511:3: ruleVersionNumber
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
    // InternalSemver.g:3520:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3524:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3525:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3525:2: ( ruleVersionNumber )
            // InternalSemver.g:3526:3: ruleVersionNumber
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
    // InternalSemver.g:3535:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3539:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3540:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3540:2: ( ruleSimpleVersion )
            // InternalSemver.g:3541:3: ruleSimpleVersion
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
    // InternalSemver.g:3550:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3554:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3555:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3555:2: ( ruleSimpleVersion )
            // InternalSemver.g:3556:3: ruleSimpleVersion
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
    // InternalSemver.g:3565:1: rule__SimpleVersion__ComparatorsAssignment_1_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3569:1: ( ( ruleVersionComparator ) )
            // InternalSemver.g:3570:2: ( ruleVersionComparator )
            {
            // InternalSemver.g:3570:2: ( ruleVersionComparator )
            // InternalSemver.g:3571:3: ruleVersionComparator
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


    // $ANTLR start "rule__SimpleVersion__WithLetterVAssignment_2"
    // InternalSemver.g:3580:1: rule__SimpleVersion__WithLetterVAssignment_2 : ( RULE_LETTER_V ) ;
    public final void rule__SimpleVersion__WithLetterVAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3584:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:3585:2: ( RULE_LETTER_V )
            {
            // InternalSemver.g:3585:2: ( RULE_LETTER_V )
            // InternalSemver.g:3586:3: RULE_LETTER_V
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_2_0()); 
            }
            match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__WithLetterVAssignment_2"


    // $ANTLR start "rule__SimpleVersion__NumberAssignment_3"
    // InternalSemver.g:3595:1: rule__SimpleVersion__NumberAssignment_3 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3599:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3600:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3600:2: ( ruleVersionNumber )
            // InternalSemver.g:3601:3: ruleVersionNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__NumberAssignment_3"


    // $ANTLR start "rule__VersionNumber__MajorAssignment_0"
    // InternalSemver.g:3610:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3614:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3615:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3615:2: ( ruleVersionPart )
            // InternalSemver.g:3616:3: ruleVersionPart
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
    // InternalSemver.g:3625:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3629:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3630:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3630:2: ( ruleVersionPart )
            // InternalSemver.g:3631:3: ruleVersionPart
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
    // InternalSemver.g:3640:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3644:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3645:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3645:2: ( ruleVersionPart )
            // InternalSemver.g:3646:3: ruleVersionPart
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
    // InternalSemver.g:3655:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3659:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3660:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3660:2: ( ruleVersionPart )
            // InternalSemver.g:3661:3: ruleVersionPart
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
    // InternalSemver.g:3670:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3674:1: ( ( ruleQualifier ) )
            // InternalSemver.g:3675:2: ( ruleQualifier )
            {
            // InternalSemver.g:3675:2: ( ruleQualifier )
            // InternalSemver.g:3676:3: ruleQualifier
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
    // InternalSemver.g:3685:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3689:1: ( ( ruleWILDCARD ) )
            // InternalSemver.g:3690:2: ( ruleWILDCARD )
            {
            // InternalSemver.g:3690:2: ( ruleWILDCARD )
            // InternalSemver.g:3691:3: ruleWILDCARD
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
    // InternalSemver.g:3700:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3704:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:3705:2: ( RULE_DIGITS )
            {
            // InternalSemver.g:3705:2: ( RULE_DIGITS )
            // InternalSemver.g:3706:3: RULE_DIGITS
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
    // InternalSemver.g:3715:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3719:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3720:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3720:2: ( ruleQualifierTag )
            // InternalSemver.g:3721:3: ruleQualifierTag
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
    // InternalSemver.g:3730:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3734:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3735:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3735:2: ( ruleQualifierTag )
            // InternalSemver.g:3736:3: ruleQualifierTag
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
    // InternalSemver.g:3745:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3749:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3750:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3750:2: ( ruleQualifierTag )
            // InternalSemver.g:3751:3: ruleQualifierTag
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
    // InternalSemver.g:3760:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3764:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3765:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3765:2: ( ruleQualifierTag )
            // InternalSemver.g:3766:3: ruleQualifierTag
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
    // InternalSemver.g:3775:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3779:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3780:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3780:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3781:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:3790:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3794:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3795:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3795:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3796:3: ruleALPHA_NUMERIC_CHARS
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
        // InternalSemver.g:663:2: ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) )
        // InternalSemver.g:663:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
        {
        // InternalSemver.g:663:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
        // InternalSemver.g:664:3: ( rule__NPMVersionRequirement__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
        }
        // InternalSemver.g:665:3: ( rule__NPMVersionRequirement__Group_0__0 )
        // InternalSemver.g:665:4: rule__NPMVersionRequirement__Group_0__0
        {
        pushFollow(FOLLOW_2);
        rule__NPMVersionRequirement__Group_0__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred2_InternalSemver

    // $ANTLR start synpred6_InternalSemver
    public final void synpred6_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:717:2: ( ( ( ruleURLSemver ) ) )
        // InternalSemver.g:717:2: ( ( ruleURLSemver ) )
        {
        // InternalSemver.g:717:2: ( ( ruleURLSemver ) )
        // InternalSemver.g:718:3: ( ruleURLSemver )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0()); 
        }
        // InternalSemver.g:719:3: ( ruleURLSemver )
        // InternalSemver.g:719:4: ruleURLSemver
        {
        pushFollow(FOLLOW_2);
        ruleURLSemver();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred6_InternalSemver

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


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA3 dfa3 = new DFA3(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA31 dfa31 = new DFA31(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\1\1\1\uffff\1\1\1\uffff\1\3\5\uffff";
    static final String dfa_3s = "\1\4\1\uffff\1\4\1\uffff\1\4\5\0";
    static final String dfa_4s = "\1\52\1\uffff\1\56\1\uffff\1\37\5\0";
    static final String dfa_5s = "\1\uffff\1\1\1\uffff\1\2\6\uffff";
    static final String dfa_6s = "\5\uffff\1\2\1\4\1\0\1\3\1\1}>";
    static final String[] dfa_7s = {
            "\1\3\2\1\1\2\2\1\31\uffff\7\1\1\3",
            "",
            "\4\3\1\uffff\1\1\24\uffff\1\1\1\4\1\1\15\uffff\1\1",
            "",
            "\1\11\1\6\1\7\1\10\1\uffff\1\3\25\uffff\1\5",
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
            return "658:1: rule__NPMVersionRequirement__Alternatives : ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA2_7 = input.LA(1);

                         
                        int index2_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index2_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA2_9 = input.LA(1);

                         
                        int index2_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index2_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA2_5 = input.LA(1);

                         
                        int index2_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index2_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA2_8 = input.LA(1);

                         
                        int index2_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index2_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA2_6 = input.LA(1);

                         
                        int index2_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalSemver()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index2_6);
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
    static final String dfa_8s = "\23\uffff";
    static final String dfa_9s = "\4\uffff\5\3\2\uffff\10\3";
    static final String dfa_10s = "\1\4\1\uffff\1\4\1\uffff\5\4\2\uffff\10\4";
    static final String dfa_11s = "\1\52\1\uffff\1\42\1\uffff\2\42\3\53\2\uffff\5\42\3\53";
    static final String dfa_12s = "\1\uffff\1\1\1\uffff\1\4\5\uffff\1\3\1\2\10\uffff";
    static final String dfa_13s = "\23\uffff}>";
    static final String[] dfa_14s = {
            "\1\2\2\uffff\1\3\42\uffff\1\1",
            "",
            "\1\10\1\5\1\6\1\7\25\uffff\2\11\1\4\1\12\2\11",
            "",
            "\1\17\1\14\1\15\1\16\1\uffff\1\3\23\uffff\2\11\1\13\1\uffff\2\11",
            "\1\17\1\14\1\15\1\16\1\uffff\1\3\23\uffff\2\11\1\13\1\uffff\2\11",
            "\1\22\1\14\1\20\1\21\1\uffff\1\3\23\uffff\2\11\1\13\1\12\2\11\10\uffff\1\12",
            "\1\22\1\14\1\20\1\21\1\uffff\1\3\23\uffff\2\11\1\13\1\12\2\11\10\uffff\1\12",
            "\1\22\1\14\1\20\1\21\1\uffff\1\3\23\uffff\2\11\1\13\1\12\2\11\10\uffff\1\12",
            "",
            "",
            "\1\17\1\14\1\15\1\16\1\uffff\1\3\23\uffff\2\11\1\13\1\uffff\2\11",
            "\1\17\1\14\1\15\1\16\1\uffff\1\3\23\uffff\2\11\1\13\1\uffff\2\11",
            "\1\17\1\14\1\15\1\16\1\uffff\1\3\23\uffff\2\11\1\13\1\uffff\2\11",
            "\1\17\1\14\1\15\1\16\1\uffff\1\3\23\uffff\2\11\1\13\1\uffff\2\11",
            "\1\17\1\14\1\15\1\16\1\uffff\1\3\23\uffff\2\11\1\13\1\uffff\2\11",
            "\1\22\1\14\1\20\1\21\1\uffff\1\3\23\uffff\2\11\1\13\1\12\2\11\10\uffff\1\12",
            "\1\22\1\14\1\20\1\21\1\uffff\1\3\23\uffff\2\11\1\13\1\12\2\11\10\uffff\1\12",
            "\1\22\1\14\1\20\1\21\1\uffff\1\3\23\uffff\2\11\1\13\1\12\2\11\10\uffff\1\12"
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
            return "679:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ruleURLVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );";
        }
    }
    static final String dfa_15s = "\126\uffff";
    static final String dfa_16s = "\2\uffff\3\1\3\uffff\17\1\2\uffff\5\1\2\uffff\5\1\1\uffff\22\1\1\uffff\12\1\1\uffff\22\1";
    static final String dfa_17s = "\1\5\1\uffff\3\11\1\5\2\4\1\5\3\11\12\4\1\5\1\uffff\1\5\15\4\3\11\17\4\1\5\20\4\3\11\12\4";
    static final String dfa_18s = "\1\51\1\uffff\3\56\1\10\2\37\17\56\1\uffff\1\10\5\56\2\37\5\56\1\37\22\56\1\10\12\56\1\37\22\56";
    static final String dfa_19s = "\1\uffff\1\1\25\uffff\1\2\76\uffff";
    static final String dfa_20s = "\126\uffff}>";
    static final String[] dfa_21s = {
            "\1\4\1\1\1\2\1\3\32\uffff\7\1",
            "",
            "\1\10\24\uffff\1\5\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\5\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\5\1\6\1\7\15\uffff\1\1",
            "\1\13\1\uffff\1\11\1\12",
            "\1\20\1\15\1\16\1\17\27\uffff\1\14",
            "\1\25\1\22\1\23\1\24\27\uffff\1\21",
            "\4\1\1\26\25\uffff\1\27\3\uffff\7\1\4\uffff\1\1",
            "\1\10\24\uffff\1\30\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\30\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\30\1\6\1\7\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\4\1\1\26\25\uffff\1\27\3\uffff\7\1\4\uffff\1\1",
            "",
            "\1\50\1\uffff\1\46\1\47",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\24\uffff\1\36\1\31\1\37\15\uffff\1\1",
            "\1\55\1\52\1\53\1\54\27\uffff\1\51",
            "\1\62\1\57\1\60\1\61\27\uffff\1\56",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\24\uffff\1\45\1\40\16\uffff\1\1",
            "\1\67\1\64\1\65\1\66\27\uffff\1\63",
            "\1\10\24\uffff\1\70\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\70\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\70\1\6\1\7\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\113\1\uffff\1\111\1\112",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\24\uffff\1\36\1\71\1\37\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\24\uffff\1\103\1\76\16\uffff\1\1",
            "\1\120\1\115\1\116\1\117\27\uffff\1\114",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\24\uffff\1\45\1\104\16\uffff\1\1",
            "\1\10\24\uffff\1\70\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\70\1\6\1\7\15\uffff\1\1",
            "\1\10\24\uffff\1\70\1\6\1\7\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\24\uffff\1\103\1\121\16\uffff\1\1"
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "733:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
    static final String dfa_22s = "\32\uffff";
    static final String dfa_23s = "\3\uffff\12\16\3\uffff\12\16";
    static final String dfa_24s = "\1\37\1\4\1\uffff\13\4\2\uffff\12\4";
    static final String dfa_25s = "\1\40\1\37\1\uffff\12\56\1\37\2\uffff\12\56";
    static final String dfa_26s = "\2\uffff\1\2\13\uffff\1\1\1\3\12\uffff";
    static final String dfa_27s = "\32\uffff}>";
    static final String[] dfa_28s = {
            "\1\1\1\2",
            "\1\7\1\4\1\5\1\6\27\uffff\1\3",
            "",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\14\1\11\1\12\1\13\1\uffff\1\16\24\uffff\1\15\1\10\1\17\15\uffff\1\16",
            "\1\24\1\21\1\22\1\23\27\uffff\1\20",
            "",
            "",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16",
            "\1\31\1\26\1\27\1\30\1\uffff\1\16\24\uffff\1\15\1\25\1\17\15\uffff\1\16"
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "775:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );";
        }
    }
    static final String dfa_29s = "\4\uffff";
    static final String dfa_30s = "\2\2\2\uffff";
    static final String dfa_31s = "\2\11\2\uffff";
    static final String dfa_32s = "\2\56\2\uffff";
    static final String dfa_33s = "\2\uffff\1\2\1\1";
    static final String dfa_34s = "\4\uffff}>";
    static final String[] dfa_35s = {
            "\1\1\44\uffff\1\3",
            "\1\1\44\uffff\1\3",
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

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "()* loopback of 1805:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*";
        }
    }
    static final String dfa_36s = "\5\uffff";
    static final String dfa_37s = "\1\1\1\uffff\2\1\1\uffff";
    static final String dfa_38s = "\1\11\1\uffff\2\5\1\uffff";
    static final String dfa_39s = "\1\56\1\uffff\2\56\1\uffff";
    static final String dfa_40s = "\1\uffff\1\2\2\uffff\1\1";
    static final String dfa_41s = "\5\uffff}>";
    static final String[] dfa_42s = {
            "\1\2\44\uffff\1\1",
            "",
            "\4\4\1\3\31\uffff\7\4\4\uffff\1\1",
            "\4\4\1\3\31\uffff\7\4\4\uffff\1\1",
            ""
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = dfa_36;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "()* loopback of 2196:2: ( rule__VersionRangeContraint__Group_2__0 )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x00000000800000F2L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x000003F8000001E0L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000060000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x000023F8800001F0L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x000023F8000001E0L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000400000000200L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000400000000202L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x000003F8000003E0L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x000003F800000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x00000001C0000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00000000E00000F0L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000000E00000F2L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000001000000D0L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000001000000D2L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000006E00000F0L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000006E00000F2L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000000800000F0L});

}