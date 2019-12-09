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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_DIGITS", "RULE_LETTER_X", "RULE_ASTERIX", "RULE_LETTER_V", "RULE_LETTER_S", "RULE_LETTER_M", "RULE_LETTER_R", "RULE_LETTER_F", "RULE_LETTER_I", "RULE_LETTER_L", "RULE_LETTER_E", "RULE_LETTER_OTHER", "RULE_WS", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'/'", "'.'", "'@'", "'-'", "'_'", "'+'", "':'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'#'", "'||'"
    };
    public static final int T__50=50;
    public static final int RULE_WHITESPACE_FRAGMENT=17;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=18;
    public static final int RULE_EOL=19;
    public static final int RULE_LETTER_OTHER=15;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=30;
    public static final int RULE_ZWNJ=24;
    public static final int RULE_ASTERIX=6;
    public static final int RULE_LETTER_E=14;
    public static final int RULE_ML_COMMENT_FRAGMENT=29;
    public static final int RULE_DIGITS=4;
    public static final int RULE_ZWJ=23;
    public static final int RULE_SL_COMMENT_FRAGMENT=28;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=31;
    public static final int T__37=37;
    public static final int RULE_LETTER_R=10;
    public static final int T__38=38;
    public static final int RULE_LETTER_S=8;
    public static final int T__39=39;
    public static final int RULE_LETTER_F=11;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=26;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_LETTER_I=12;
    public static final int EOF=-1;
    public static final int RULE_LETTER_L=13;
    public static final int RULE_LETTER_M=9;
    public static final int RULE_WS=16;
    public static final int RULE_BOM=25;
    public static final int RULE_LETTER_V=7;
    public static final int RULE_LETTER_X=5;
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


    // $ANTLR start "entryRuleVersionRangeSetRequirement"
    // InternalSemver.g:236:1: entryRuleVersionRangeSetRequirement : ruleVersionRangeSetRequirement EOF ;
    public final void entryRuleVersionRangeSetRequirement() throws RecognitionException {
        try {
            // InternalSemver.g:237:1: ( ruleVersionRangeSetRequirement EOF )
            // InternalSemver.g:238:1: ruleVersionRangeSetRequirement EOF
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
    // InternalSemver.g:245:1: ruleVersionRangeSetRequirement : ( ( rule__VersionRangeSetRequirement__Group__0 ) ) ;
    public final void ruleVersionRangeSetRequirement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:249:2: ( ( ( rule__VersionRangeSetRequirement__Group__0 ) ) )
            // InternalSemver.g:250:2: ( ( rule__VersionRangeSetRequirement__Group__0 ) )
            {
            // InternalSemver.g:250:2: ( ( rule__VersionRangeSetRequirement__Group__0 ) )
            // InternalSemver.g:251:3: ( rule__VersionRangeSetRequirement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup()); 
            }
            // InternalSemver.g:252:3: ( rule__VersionRangeSetRequirement__Group__0 )
            // InternalSemver.g:252:4: rule__VersionRangeSetRequirement__Group__0
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
    // InternalSemver.g:261:1: entryRuleVersionRange : ruleVersionRange EOF ;
    public final void entryRuleVersionRange() throws RecognitionException {
        try {
            // InternalSemver.g:262:1: ( ruleVersionRange EOF )
            // InternalSemver.g:263:1: ruleVersionRange EOF
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
    // InternalSemver.g:270:1: ruleVersionRange : ( ( rule__VersionRange__Alternatives ) ) ;
    public final void ruleVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:274:2: ( ( ( rule__VersionRange__Alternatives ) ) )
            // InternalSemver.g:275:2: ( ( rule__VersionRange__Alternatives ) )
            {
            // InternalSemver.g:275:2: ( ( rule__VersionRange__Alternatives ) )
            // InternalSemver.g:276:3: ( rule__VersionRange__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeAccess().getAlternatives()); 
            }
            // InternalSemver.g:277:3: ( rule__VersionRange__Alternatives )
            // InternalSemver.g:277:4: rule__VersionRange__Alternatives
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
    // InternalSemver.g:286:1: entryRuleHyphenVersionRange : ruleHyphenVersionRange EOF ;
    public final void entryRuleHyphenVersionRange() throws RecognitionException {
        try {
            // InternalSemver.g:287:1: ( ruleHyphenVersionRange EOF )
            // InternalSemver.g:288:1: ruleHyphenVersionRange EOF
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
    // InternalSemver.g:295:1: ruleHyphenVersionRange : ( ( rule__HyphenVersionRange__Group__0 ) ) ;
    public final void ruleHyphenVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:299:2: ( ( ( rule__HyphenVersionRange__Group__0 ) ) )
            // InternalSemver.g:300:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            {
            // InternalSemver.g:300:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            // InternalSemver.g:301:3: ( rule__HyphenVersionRange__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getGroup()); 
            }
            // InternalSemver.g:302:3: ( rule__HyphenVersionRange__Group__0 )
            // InternalSemver.g:302:4: rule__HyphenVersionRange__Group__0
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
    // InternalSemver.g:311:1: entryRuleVersionRangeContraint : ruleVersionRangeContraint EOF ;
    public final void entryRuleVersionRangeContraint() throws RecognitionException {
        try {
            // InternalSemver.g:312:1: ( ruleVersionRangeContraint EOF )
            // InternalSemver.g:313:1: ruleVersionRangeContraint EOF
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
    // InternalSemver.g:320:1: ruleVersionRangeContraint : ( ( rule__VersionRangeContraint__Group__0 ) ) ;
    public final void ruleVersionRangeContraint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:324:2: ( ( ( rule__VersionRangeContraint__Group__0 ) ) )
            // InternalSemver.g:325:2: ( ( rule__VersionRangeContraint__Group__0 ) )
            {
            // InternalSemver.g:325:2: ( ( rule__VersionRangeContraint__Group__0 ) )
            // InternalSemver.g:326:3: ( rule__VersionRangeContraint__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup()); 
            }
            // InternalSemver.g:327:3: ( rule__VersionRangeContraint__Group__0 )
            // InternalSemver.g:327:4: rule__VersionRangeContraint__Group__0
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
    // InternalSemver.g:336:1: entryRuleSimpleVersion : ruleSimpleVersion EOF ;
    public final void entryRuleSimpleVersion() throws RecognitionException {
        try {
            // InternalSemver.g:337:1: ( ruleSimpleVersion EOF )
            // InternalSemver.g:338:1: ruleSimpleVersion EOF
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
    // InternalSemver.g:345:1: ruleSimpleVersion : ( ( rule__SimpleVersion__Group__0 ) ) ;
    public final void ruleSimpleVersion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:349:2: ( ( ( rule__SimpleVersion__Group__0 ) ) )
            // InternalSemver.g:350:2: ( ( rule__SimpleVersion__Group__0 ) )
            {
            // InternalSemver.g:350:2: ( ( rule__SimpleVersion__Group__0 ) )
            // InternalSemver.g:351:3: ( rule__SimpleVersion__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup()); 
            }
            // InternalSemver.g:352:3: ( rule__SimpleVersion__Group__0 )
            // InternalSemver.g:352:4: rule__SimpleVersion__Group__0
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
    // InternalSemver.g:361:1: entryRuleVersionNumber : ruleVersionNumber EOF ;
    public final void entryRuleVersionNumber() throws RecognitionException {
        try {
            // InternalSemver.g:362:1: ( ruleVersionNumber EOF )
            // InternalSemver.g:363:1: ruleVersionNumber EOF
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
    // InternalSemver.g:370:1: ruleVersionNumber : ( ( rule__VersionNumber__Group__0 ) ) ;
    public final void ruleVersionNumber() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:374:2: ( ( ( rule__VersionNumber__Group__0 ) ) )
            // InternalSemver.g:375:2: ( ( rule__VersionNumber__Group__0 ) )
            {
            // InternalSemver.g:375:2: ( ( rule__VersionNumber__Group__0 ) )
            // InternalSemver.g:376:3: ( rule__VersionNumber__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup()); 
            }
            // InternalSemver.g:377:3: ( rule__VersionNumber__Group__0 )
            // InternalSemver.g:377:4: rule__VersionNumber__Group__0
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
    // InternalSemver.g:386:1: entryRuleVersionPart : ruleVersionPart EOF ;
    public final void entryRuleVersionPart() throws RecognitionException {
        try {
            // InternalSemver.g:387:1: ( ruleVersionPart EOF )
            // InternalSemver.g:388:1: ruleVersionPart EOF
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
    // InternalSemver.g:395:1: ruleVersionPart : ( ( rule__VersionPart__Alternatives ) ) ;
    public final void ruleVersionPart() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:399:2: ( ( ( rule__VersionPart__Alternatives ) ) )
            // InternalSemver.g:400:2: ( ( rule__VersionPart__Alternatives ) )
            {
            // InternalSemver.g:400:2: ( ( rule__VersionPart__Alternatives ) )
            // InternalSemver.g:401:3: ( rule__VersionPart__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionPartAccess().getAlternatives()); 
            }
            // InternalSemver.g:402:3: ( rule__VersionPart__Alternatives )
            // InternalSemver.g:402:4: rule__VersionPart__Alternatives
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
    // InternalSemver.g:411:1: entryRuleQualifier : ruleQualifier EOF ;
    public final void entryRuleQualifier() throws RecognitionException {
        try {
            // InternalSemver.g:412:1: ( ruleQualifier EOF )
            // InternalSemver.g:413:1: ruleQualifier EOF
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
    // InternalSemver.g:420:1: ruleQualifier : ( ( rule__Qualifier__Alternatives ) ) ;
    public final void ruleQualifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:424:2: ( ( ( rule__Qualifier__Alternatives ) ) )
            // InternalSemver.g:425:2: ( ( rule__Qualifier__Alternatives ) )
            {
            // InternalSemver.g:425:2: ( ( rule__Qualifier__Alternatives ) )
            // InternalSemver.g:426:3: ( rule__Qualifier__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getAlternatives()); 
            }
            // InternalSemver.g:427:3: ( rule__Qualifier__Alternatives )
            // InternalSemver.g:427:4: rule__Qualifier__Alternatives
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
    // InternalSemver.g:436:1: entryRuleQualifierTag : ruleQualifierTag EOF ;
    public final void entryRuleQualifierTag() throws RecognitionException {
        try {
            // InternalSemver.g:437:1: ( ruleQualifierTag EOF )
            // InternalSemver.g:438:1: ruleQualifierTag EOF
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
    // InternalSemver.g:445:1: ruleQualifierTag : ( ( rule__QualifierTag__Group__0 ) ) ;
    public final void ruleQualifierTag() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:449:2: ( ( ( rule__QualifierTag__Group__0 ) ) )
            // InternalSemver.g:450:2: ( ( rule__QualifierTag__Group__0 ) )
            {
            // InternalSemver.g:450:2: ( ( rule__QualifierTag__Group__0 ) )
            // InternalSemver.g:451:3: ( rule__QualifierTag__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup()); 
            }
            // InternalSemver.g:452:3: ( rule__QualifierTag__Group__0 )
            // InternalSemver.g:452:4: rule__QualifierTag__Group__0
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
    // InternalSemver.g:461:1: entryRuleFILE_TAG : ruleFILE_TAG EOF ;
    public final void entryRuleFILE_TAG() throws RecognitionException {
        try {
            // InternalSemver.g:462:1: ( ruleFILE_TAG EOF )
            // InternalSemver.g:463:1: ruleFILE_TAG EOF
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
    // InternalSemver.g:470:1: ruleFILE_TAG : ( ( rule__FILE_TAG__Group__0 ) ) ;
    public final void ruleFILE_TAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:474:2: ( ( ( rule__FILE_TAG__Group__0 ) ) )
            // InternalSemver.g:475:2: ( ( rule__FILE_TAG__Group__0 ) )
            {
            // InternalSemver.g:475:2: ( ( rule__FILE_TAG__Group__0 ) )
            // InternalSemver.g:476:3: ( rule__FILE_TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getGroup()); 
            }
            // InternalSemver.g:477:3: ( rule__FILE_TAG__Group__0 )
            // InternalSemver.g:477:4: rule__FILE_TAG__Group__0
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
    // InternalSemver.g:486:1: entryRuleSEMVER_TAG : ruleSEMVER_TAG EOF ;
    public final void entryRuleSEMVER_TAG() throws RecognitionException {
        try {
            // InternalSemver.g:487:1: ( ruleSEMVER_TAG EOF )
            // InternalSemver.g:488:1: ruleSEMVER_TAG EOF
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
    // InternalSemver.g:495:1: ruleSEMVER_TAG : ( ( rule__SEMVER_TAG__Group__0 ) ) ;
    public final void ruleSEMVER_TAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:499:2: ( ( ( rule__SEMVER_TAG__Group__0 ) ) )
            // InternalSemver.g:500:2: ( ( rule__SEMVER_TAG__Group__0 ) )
            {
            // InternalSemver.g:500:2: ( ( rule__SEMVER_TAG__Group__0 ) )
            // InternalSemver.g:501:3: ( rule__SEMVER_TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getGroup()); 
            }
            // InternalSemver.g:502:3: ( rule__SEMVER_TAG__Group__0 )
            // InternalSemver.g:502:4: rule__SEMVER_TAG__Group__0
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


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:511:1: entryRulePATH : rulePATH EOF ;
    public final void entryRulePATH() throws RecognitionException {
        try {
            // InternalSemver.g:512:1: ( rulePATH EOF )
            // InternalSemver.g:513:1: rulePATH EOF
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
    // InternalSemver.g:520:1: rulePATH : ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) ) ;
    public final void rulePATH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:524:2: ( ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) ) )
            // InternalSemver.g:525:2: ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) )
            {
            // InternalSemver.g:525:2: ( ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* ) )
            // InternalSemver.g:526:3: ( ( rule__PATH__Alternatives ) ) ( ( rule__PATH__Alternatives )* )
            {
            // InternalSemver.g:526:3: ( ( rule__PATH__Alternatives ) )
            // InternalSemver.g:527:4: ( rule__PATH__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives()); 
            }
            // InternalSemver.g:528:4: ( rule__PATH__Alternatives )
            // InternalSemver.g:528:5: rule__PATH__Alternatives
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

            // InternalSemver.g:531:3: ( ( rule__PATH__Alternatives )* )
            // InternalSemver.g:532:4: ( rule__PATH__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives()); 
            }
            // InternalSemver.g:533:4: ( rule__PATH__Alternatives )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_DIGITS && LA1_0<=RULE_LETTER_X)||(LA1_0>=RULE_LETTER_V && LA1_0<=RULE_LETTER_OTHER)||(LA1_0>=35 && LA1_0<=39)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSemver.g:533:5: rule__PATH__Alternatives
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
    // InternalSemver.g:543:1: entryRuleURL_PROTOCOL : ruleURL_PROTOCOL EOF ;
    public final void entryRuleURL_PROTOCOL() throws RecognitionException {
        try {
            // InternalSemver.g:544:1: ( ruleURL_PROTOCOL EOF )
            // InternalSemver.g:545:1: ruleURL_PROTOCOL EOF
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
    // InternalSemver.g:552:1: ruleURL_PROTOCOL : ( ( rule__URL_PROTOCOL__Group__0 ) ) ;
    public final void ruleURL_PROTOCOL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:556:2: ( ( ( rule__URL_PROTOCOL__Group__0 ) ) )
            // InternalSemver.g:557:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            {
            // InternalSemver.g:557:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            // InternalSemver.g:558:3: ( rule__URL_PROTOCOL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getGroup()); 
            }
            // InternalSemver.g:559:3: ( rule__URL_PROTOCOL__Group__0 )
            // InternalSemver.g:559:4: rule__URL_PROTOCOL__Group__0
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


    // $ANTLR start "entryRuleURL_NO_VX"
    // InternalSemver.g:593:1: entryRuleURL_NO_VX : ruleURL_NO_VX EOF ;
    public final void entryRuleURL_NO_VX() throws RecognitionException {
        try {
            // InternalSemver.g:594:1: ( ruleURL_NO_VX EOF )
            // InternalSemver.g:595:1: ruleURL_NO_VX EOF
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
    // InternalSemver.g:602:1: ruleURL_NO_VX : ( ( rule__URL_NO_VX__Group__0 ) ) ;
    public final void ruleURL_NO_VX() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:606:2: ( ( ( rule__URL_NO_VX__Group__0 ) ) )
            // InternalSemver.g:607:2: ( ( rule__URL_NO_VX__Group__0 ) )
            {
            // InternalSemver.g:607:2: ( ( rule__URL_NO_VX__Group__0 ) )
            // InternalSemver.g:608:3: ( rule__URL_NO_VX__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getGroup()); 
            }
            // InternalSemver.g:609:3: ( rule__URL_NO_VX__Group__0 )
            // InternalSemver.g:609:4: rule__URL_NO_VX__Group__0
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
    // InternalSemver.g:618:1: entryRuleTAG : ruleTAG EOF ;
    public final void entryRuleTAG() throws RecognitionException {
        try {
            // InternalSemver.g:619:1: ( ruleTAG EOF )
            // InternalSemver.g:620:1: ruleTAG EOF
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
    // InternalSemver.g:627:1: ruleTAG : ( ( rule__TAG__Group__0 ) ) ;
    public final void ruleTAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:631:2: ( ( ( rule__TAG__Group__0 ) ) )
            // InternalSemver.g:632:2: ( ( rule__TAG__Group__0 ) )
            {
            // InternalSemver.g:632:2: ( ( rule__TAG__Group__0 ) )
            // InternalSemver.g:633:3: ( rule__TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getGroup()); 
            }
            // InternalSemver.g:634:3: ( rule__TAG__Group__0 )
            // InternalSemver.g:634:4: rule__TAG__Group__0
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
    // InternalSemver.g:643:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSemver.g:644:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:645:1: ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:652:1: ruleALPHA_NUMERIC_CHARS : ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:656:2: ( ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) )
            // InternalSemver.g:657:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            {
            // InternalSemver.g:657:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            // InternalSemver.g:658:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            {
            // InternalSemver.g:658:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) )
            // InternalSemver.g:659:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:660:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            // InternalSemver.g:660:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
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

            // InternalSemver.g:663:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            // InternalSemver.g:664:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:665:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=RULE_DIGITS && LA2_0<=RULE_LETTER_X)||(LA2_0>=RULE_LETTER_V && LA2_0<=RULE_LETTER_OTHER)||LA2_0==38) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSemver.g:665:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__ALPHA_NUMERIC_CHARS__Alternatives();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"
    // InternalSemver.g:675:1: entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        try {
            // InternalSemver.g:676:1: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF )
            // InternalSemver.g:677:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF
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
    // InternalSemver.g:684:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:688:2: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) )
            // InternalSemver.g:689:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            {
            // InternalSemver.g:689:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            // InternalSemver.g:690:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup()); 
            }
            // InternalSemver.g:691:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            // InternalSemver.g:691:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0
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


    // $ANTLR start "entryRuleWILDCARD"
    // InternalSemver.g:700:1: entryRuleWILDCARD : ruleWILDCARD EOF ;
    public final void entryRuleWILDCARD() throws RecognitionException {
        try {
            // InternalSemver.g:701:1: ( ruleWILDCARD EOF )
            // InternalSemver.g:702:1: ruleWILDCARD EOF
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
    // InternalSemver.g:709:1: ruleWILDCARD : ( ( rule__WILDCARD__Alternatives ) ) ;
    public final void ruleWILDCARD() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:713:2: ( ( ( rule__WILDCARD__Alternatives ) ) )
            // InternalSemver.g:714:2: ( ( rule__WILDCARD__Alternatives ) )
            {
            // InternalSemver.g:714:2: ( ( rule__WILDCARD__Alternatives ) )
            // InternalSemver.g:715:3: ( rule__WILDCARD__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWILDCARDAccess().getAlternatives()); 
            }
            // InternalSemver.g:716:3: ( rule__WILDCARD__Alternatives )
            // InternalSemver.g:716:4: rule__WILDCARD__Alternatives
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
    // InternalSemver.g:726:1: ruleLETTER : ( ( rule__LETTER__Alternatives ) ) ;
    public final void ruleLETTER() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:730:2: ( ( ( rule__LETTER__Alternatives ) ) )
            // InternalSemver.g:731:2: ( ( rule__LETTER__Alternatives ) )
            {
            // InternalSemver.g:731:2: ( ( rule__LETTER__Alternatives ) )
            // InternalSemver.g:732:3: ( rule__LETTER__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTERAccess().getAlternatives()); 
            }
            // InternalSemver.g:733:3: ( rule__LETTER__Alternatives )
            // InternalSemver.g:733:4: rule__LETTER__Alternatives
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
    // InternalSemver.g:743:1: ruleLETTER_NO_VX : ( ( rule__LETTER_NO_VX__Alternatives ) ) ;
    public final void ruleLETTER_NO_VX() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:747:2: ( ( ( rule__LETTER_NO_VX__Alternatives ) ) )
            // InternalSemver.g:748:2: ( ( rule__LETTER_NO_VX__Alternatives ) )
            {
            // InternalSemver.g:748:2: ( ( rule__LETTER_NO_VX__Alternatives ) )
            // InternalSemver.g:749:3: ( rule__LETTER_NO_VX__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTER_NO_VXAccess().getAlternatives()); 
            }
            // InternalSemver.g:750:3: ( rule__LETTER_NO_VX__Alternatives )
            // InternalSemver.g:750:4: rule__LETTER_NO_VX__Alternatives
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
    // InternalSemver.g:759:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:763:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSemver.g:764:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSemver.g:764:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSemver.g:765:3: ( rule__VersionComparator__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            }
            // InternalSemver.g:766:3: ( rule__VersionComparator__Alternatives )
            // InternalSemver.g:766:4: rule__VersionComparator__Alternatives
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
    // InternalSemver.g:774:1: rule__NPMVersionRequirement__Alternatives : ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:778:1: ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==EOF||(LA3_0>=RULE_DIGITS && LA3_0<=RULE_LETTER_V)||LA3_0==RULE_WS||(LA3_0>=42 && LA3_0<=48)) ) {
                alt3=1;
            }
            else if ( ((LA3_0>=RULE_LETTER_S && LA3_0<=RULE_LETTER_OTHER)||(LA3_0>=38 && LA3_0<=39)) ) {
                alt3=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalSemver.g:779:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    {
                    // InternalSemver.g:779:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    // InternalSemver.g:780:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:781:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    // InternalSemver.g:781:4: rule__NPMVersionRequirement__Group_0__0
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
                    // InternalSemver.g:785:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    {
                    // InternalSemver.g:785:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    // InternalSemver.g:786:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:787:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    // InternalSemver.g:787:4: rule__NPMVersionRequirement__Group_1__0
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
    // InternalSemver.g:795:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:799:1: ( ( ( ruleLocalPathVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) ) )
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // InternalSemver.g:800:2: ( ( ruleLocalPathVersionRequirement ) )
                    {
                    // InternalSemver.g:800:2: ( ( ruleLocalPathVersionRequirement ) )
                    // InternalSemver.g:801:3: ( ruleLocalPathVersionRequirement )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
                    }
                    // InternalSemver.g:802:3: ( ruleLocalPathVersionRequirement )
                    // InternalSemver.g:802:4: ruleLocalPathVersionRequirement
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
                    // InternalSemver.g:806:2: ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) )
                    {
                    // InternalSemver.g:806:2: ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) )
                    // InternalSemver.g:807:3: ( rule__NPMVersionRequirement__Alternatives_1_0_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1()); 
                    }
                    // InternalSemver.g:808:3: ( rule__NPMVersionRequirement__Alternatives_1_0_1 )
                    // InternalSemver.g:808:4: rule__NPMVersionRequirement__Alternatives_1_0_1
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
    // InternalSemver.g:816:1: rule__NPMVersionRequirement__Alternatives_1_0_1 : ( ( ( ruleURLVersionRequirement ) ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:820:1: ( ( ( ruleURLVersionRequirement ) ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) )
            int alt5=3;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSemver.g:821:2: ( ( ruleURLVersionRequirement ) )
                    {
                    // InternalSemver.g:821:2: ( ( ruleURLVersionRequirement ) )
                    // InternalSemver.g:822:3: ( ruleURLVersionRequirement )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0()); 
                    }
                    // InternalSemver.g:823:3: ( ruleURLVersionRequirement )
                    // InternalSemver.g:823:4: ruleURLVersionRequirement
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
                    // InternalSemver.g:827:2: ( ruleGitHubVersionRequirement )
                    {
                    // InternalSemver.g:827:2: ( ruleGitHubVersionRequirement )
                    // InternalSemver.g:828:3: ruleGitHubVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleGitHubVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:833:2: ( ruleTagVersionRequirement )
                    {
                    // InternalSemver.g:833:2: ( ruleTagVersionRequirement )
                    // InternalSemver.g:834:3: ruleTagVersionRequirement
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleTagVersionRequirement();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_2()); 
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


    // $ANTLR start "rule__URLVersionSpecifier__Alternatives"
    // InternalSemver.g:843:1: rule__URLVersionSpecifier__Alternatives : ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) );
    public final void rule__URLVersionSpecifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:847:1: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) )
            int alt6=3;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // InternalSemver.g:848:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:848:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    // InternalSemver.g:849:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:850:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    // InternalSemver.g:850:4: rule__URLVersionSpecifier__Group_0__0
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
                    // InternalSemver.g:854:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:854:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    // InternalSemver.g:855:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:856:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    // InternalSemver.g:856:4: rule__URLVersionSpecifier__Group_1__0
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
                    // InternalSemver.g:860:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:860:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    // InternalSemver.g:861:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:862:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    // InternalSemver.g:862:4: rule__URLVersionSpecifier__Group_2__0
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


    // $ANTLR start "rule__VersionRange__Alternatives"
    // InternalSemver.g:870:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:874:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:875:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSemver.g:875:2: ( ruleVersionRangeContraint )
                    // InternalSemver.g:876:3: ruleVersionRangeContraint
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
                    // InternalSemver.g:881:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSemver.g:881:2: ( ruleHyphenVersionRange )
                    // InternalSemver.g:882:3: ruleHyphenVersionRange
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
    // InternalSemver.g:891:1: rule__VersionPart__Alternatives : ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) );
    public final void rule__VersionPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:895:1: ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=RULE_LETTER_X && LA8_0<=RULE_ASTERIX)) ) {
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
                    // InternalSemver.g:896:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    {
                    // InternalSemver.g:896:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    // InternalSemver.g:897:3: ( rule__VersionPart__WildcardAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    }
                    // InternalSemver.g:898:3: ( rule__VersionPart__WildcardAssignment_0 )
                    // InternalSemver.g:898:4: rule__VersionPart__WildcardAssignment_0
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
                    // InternalSemver.g:902:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    {
                    // InternalSemver.g:902:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    // InternalSemver.g:903:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    }
                    // InternalSemver.g:904:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    // InternalSemver.g:904:4: rule__VersionPart__NumberRawAssignment_1
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
    // InternalSemver.g:912:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:916:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==38) ) {
                alt9=1;
            }
            else if ( (LA9_0==40) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalSemver.g:917:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:917:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSemver.g:918:3: ( rule__Qualifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:919:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSemver.g:919:4: rule__Qualifier__Group_0__0
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
                    // InternalSemver.g:923:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:923:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSemver.g:924:3: ( rule__Qualifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:925:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSemver.g:925:4: rule__Qualifier__Group_1__0
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
    // InternalSemver.g:933:1: rule__PATH__Alternatives : ( ( '/' ) | ( '.' ) | ( '@' ) | ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__PATH__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:937:1: ( ( '/' ) | ( '.' ) | ( '@' ) | ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt10=7;
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
            case RULE_DIGITS:
                {
                alt10=6;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
                {
                alt10=7;
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
                    // InternalSemver.g:938:2: ( '/' )
                    {
                    // InternalSemver.g:938:2: ( '/' )
                    // InternalSemver.g:939:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_0()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_0()); 
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
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_1()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:950:2: ( '@' )
                    {
                    // InternalSemver.g:950:2: ( '@' )
                    // InternalSemver.g:951:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getCommercialAtKeyword_2()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getCommercialAtKeyword_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:956:2: ( '-' )
                    {
                    // InternalSemver.g:956:2: ( '-' )
                    // InternalSemver.g:957:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getHyphenMinusKeyword_3()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getHyphenMinusKeyword_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:962:2: ( '_' )
                    {
                    // InternalSemver.g:962:2: ( '_' )
                    // InternalSemver.g:963:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().get_Keyword_4()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().get_Keyword_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:968:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:968:2: ( RULE_DIGITS )
                    // InternalSemver.g:969:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_5()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:974:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:974:2: ( ruleLETTER )
                    // InternalSemver.g:975:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTERParserRuleCall_6()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTERParserRuleCall_6()); 
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
    // InternalSemver.g:984:1: rule__URL_PROTOCOL__Alternatives_1 : ( ( ruleLETTER ) | ( '+' ) );
    public final void rule__URL_PROTOCOL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:988:1: ( ( ruleLETTER ) | ( '+' ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_LETTER_X||(LA11_0>=RULE_LETTER_V && LA11_0<=RULE_LETTER_OTHER)) ) {
                alt11=1;
            }
            else if ( (LA11_0==40) ) {
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
                    // InternalSemver.g:989:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:989:2: ( ruleLETTER )
                    // InternalSemver.g:990:3: ruleLETTER
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
                    // InternalSemver.g:995:2: ( '+' )
                    {
                    // InternalSemver.g:995:2: ( '+' )
                    // InternalSemver.g:996:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1()); 
                    }
                    match(input,40,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1005:1: rule__URL__Alternatives_0 : ( ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1009:1: ( ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt12=4;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt12=1;
                }
                break;
            case 39:
                {
                alt12=2;
                }
                break;
            case RULE_DIGITS:
                {
                alt12=3;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
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
                    // InternalSemver.g:1010:2: ( '-' )
                    {
                    // InternalSemver.g:1010:2: ( '-' )
                    // InternalSemver.g:1011:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1016:2: ( '_' )
                    {
                    // InternalSemver.g:1016:2: ( '_' )
                    // InternalSemver.g:1017:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().get_Keyword_0_1()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().get_Keyword_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1022:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1022:2: ( RULE_DIGITS )
                    // InternalSemver.g:1023:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_2()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1028:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1028:2: ( ruleLETTER )
                    // InternalSemver.g:1029:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_3()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_3()); 
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
    // InternalSemver.g:1038:1: rule__URL__Alternatives_1 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1042:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt13=4;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt13=1;
                }
                break;
            case 36:
                {
                alt13=2;
                }
                break;
            case 41:
                {
                alt13=3;
                }
                break;
            case 37:
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
                    // InternalSemver.g:1043:2: ( '/' )
                    {
                    // InternalSemver.g:1043:2: ( '/' )
                    // InternalSemver.g:1044:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_1_0()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1049:2: ( '.' )
                    {
                    // InternalSemver.g:1049:2: ( '.' )
                    // InternalSemver.g:1050:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_1_1()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1055:2: ( ':' )
                    {
                    // InternalSemver.g:1055:2: ( ':' )
                    // InternalSemver.g:1056:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_1_2()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1061:2: ( '@' )
                    {
                    // InternalSemver.g:1061:2: ( '@' )
                    // InternalSemver.g:1062:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_1_3()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1071:1: rule__URL__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1075:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt14=8;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt14=1;
                }
                break;
            case 36:
                {
                alt14=2;
                }
                break;
            case 41:
                {
                alt14=3;
                }
                break;
            case 37:
                {
                alt14=4;
                }
                break;
            case 38:
                {
                alt14=5;
                }
                break;
            case 39:
                {
                alt14=6;
                }
                break;
            case RULE_DIGITS:
                {
                alt14=7;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
                {
                alt14=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalSemver.g:1076:2: ( '/' )
                    {
                    // InternalSemver.g:1076:2: ( '/' )
                    // InternalSemver.g:1077:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1082:2: ( '.' )
                    {
                    // InternalSemver.g:1082:2: ( '.' )
                    // InternalSemver.g:1083:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1088:2: ( ':' )
                    {
                    // InternalSemver.g:1088:2: ( ':' )
                    // InternalSemver.g:1089:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1094:2: ( '@' )
                    {
                    // InternalSemver.g:1094:2: ( '@' )
                    // InternalSemver.g:1095:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1100:2: ( '-' )
                    {
                    // InternalSemver.g:1100:2: ( '-' )
                    // InternalSemver.g:1101:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1106:2: ( '_' )
                    {
                    // InternalSemver.g:1106:2: ( '_' )
                    // InternalSemver.g:1107:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().get_Keyword_2_5()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().get_Keyword_2_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1112:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1112:2: ( RULE_DIGITS )
                    // InternalSemver.g:1113:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_6()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSemver.g:1118:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1118:2: ( ruleLETTER )
                    // InternalSemver.g:1119:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_7()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_7()); 
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
    // InternalSemver.g:1128:1: rule__URL_NO_VX__Alternatives_0 : ( ( '-' ) | ( '_' ) | ( ruleLETTER_NO_VX ) );
    public final void rule__URL_NO_VX__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1132:1: ( ( '-' ) | ( '_' ) | ( ruleLETTER_NO_VX ) )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt15=1;
                }
                break;
            case 39:
                {
                alt15=2;
                }
                break;
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
                {
                alt15=3;
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
                    // InternalSemver.g:1133:2: ( '-' )
                    {
                    // InternalSemver.g:1133:2: ( '-' )
                    // InternalSemver.g:1134:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_0()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1139:2: ( '_' )
                    {
                    // InternalSemver.g:1139:2: ( '_' )
                    // InternalSemver.g:1140:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().get_Keyword_0_1()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().get_Keyword_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1145:2: ( ruleLETTER_NO_VX )
                    {
                    // InternalSemver.g:1145:2: ( ruleLETTER_NO_VX )
                    // InternalSemver.g:1146:3: ruleLETTER_NO_VX
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
    // InternalSemver.g:1155:1: rule__URL_NO_VX__Alternatives_1 : ( ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL_NO_VX__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1159:1: ( ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt16=4;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt16=1;
                }
                break;
            case 39:
                {
                alt16=2;
                }
                break;
            case RULE_DIGITS:
                {
                alt16=3;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
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
                    // InternalSemver.g:1160:2: ( '-' )
                    {
                    // InternalSemver.g:1160:2: ( '-' )
                    // InternalSemver.g:1161:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1166:2: ( '_' )
                    {
                    // InternalSemver.g:1166:2: ( '_' )
                    // InternalSemver.g:1167:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().get_Keyword_1_1()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().get_Keyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1172:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1172:2: ( RULE_DIGITS )
                    // InternalSemver.g:1173:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_2()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1178:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1178:2: ( ruleLETTER )
                    // InternalSemver.g:1179:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_3()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_3()); 
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
    // InternalSemver.g:1188:1: rule__URL_NO_VX__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL_NO_VX__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1192:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt17=4;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt17=1;
                }
                break;
            case 36:
                {
                alt17=2;
                }
                break;
            case 41:
                {
                alt17=3;
                }
                break;
            case 37:
                {
                alt17=4;
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
                    // InternalSemver.g:1193:2: ( '/' )
                    {
                    // InternalSemver.g:1193:2: ( '/' )
                    // InternalSemver.g:1194:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1199:2: ( '.' )
                    {
                    // InternalSemver.g:1199:2: ( '.' )
                    // InternalSemver.g:1200:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1205:2: ( ':' )
                    {
                    // InternalSemver.g:1205:2: ( ':' )
                    // InternalSemver.g:1206:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1211:2: ( '@' )
                    {
                    // InternalSemver.g:1211:2: ( '@' )
                    // InternalSemver.g:1212:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1221:1: rule__URL_NO_VX__Alternatives_3 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL_NO_VX__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1225:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( '_' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt18=8;
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
            case 41:
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
            case RULE_DIGITS:
                {
                alt18=7;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
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
                    // InternalSemver.g:1226:2: ( '/' )
                    {
                    // InternalSemver.g:1226:2: ( '/' )
                    // InternalSemver.g:1227:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1232:2: ( '.' )
                    {
                    // InternalSemver.g:1232:2: ( '.' )
                    // InternalSemver.g:1233:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1238:2: ( ':' )
                    {
                    // InternalSemver.g:1238:2: ( ':' )
                    // InternalSemver.g:1239:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); 
                    }
                    match(input,41,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1244:2: ( '@' )
                    {
                    // InternalSemver.g:1244:2: ( '@' )
                    // InternalSemver.g:1245:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1250:2: ( '-' )
                    {
                    // InternalSemver.g:1250:2: ( '-' )
                    // InternalSemver.g:1251:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1256:2: ( '_' )
                    {
                    // InternalSemver.g:1256:2: ( '_' )
                    // InternalSemver.g:1257:3: '_'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().get_Keyword_3_5()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().get_Keyword_3_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1262:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1262:2: ( RULE_DIGITS )
                    // InternalSemver.g:1263:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_6()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSemver.g:1268:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1268:2: ( ruleLETTER )
                    // InternalSemver.g:1269:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_7()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_7()); 
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


    // $ANTLR start "rule__TAG__Alternatives_1"
    // InternalSemver.g:1278:1: rule__TAG__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__TAG__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1282:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt19=3;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt19=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt19=2;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
                {
                alt19=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalSemver.g:1283:2: ( '-' )
                    {
                    // InternalSemver.g:1283:2: ( '-' )
                    // InternalSemver.g:1284:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1289:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1289:2: ( RULE_DIGITS )
                    // InternalSemver.g:1290:3: RULE_DIGITS
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
                    // InternalSemver.g:1295:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1295:2: ( ruleLETTER )
                    // InternalSemver.g:1296:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getLETTERParserRuleCall_1_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getLETTERParserRuleCall_1_2()); 
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
    // InternalSemver.g:1305:1: rule__ALPHA_NUMERIC_CHARS__Alternatives : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__ALPHA_NUMERIC_CHARS__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1309:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt20=3;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt20=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt20=2;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
                {
                alt20=3;
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
                    // InternalSemver.g:1310:2: ( '-' )
                    {
                    // InternalSemver.g:1310:2: ( '-' )
                    // InternalSemver.g:1311:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1316:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1316:2: ( RULE_DIGITS )
                    // InternalSemver.g:1317:3: RULE_DIGITS
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
                    // InternalSemver.g:1322:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1322:2: ( ruleLETTER )
                    // InternalSemver.g:1323:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTERParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTERParserRuleCall_2()); 
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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1"
    // InternalSemver.g:1332:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1336:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt21=3;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt21=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt21=2;
                }
                break;
            case RULE_LETTER_X:
            case RULE_LETTER_V:
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
                {
                alt21=3;
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
                    // InternalSemver.g:1337:2: ( '-' )
                    {
                    // InternalSemver.g:1337:2: ( '-' )
                    // InternalSemver.g:1338:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1343:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1343:2: ( RULE_DIGITS )
                    // InternalSemver.g:1344:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1349:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1349:2: ( ruleLETTER )
                    // InternalSemver.g:1350:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTERParserRuleCall_1_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTERParserRuleCall_1_2()); 
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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1"


    // $ANTLR start "rule__WILDCARD__Alternatives"
    // InternalSemver.g:1359:1: rule__WILDCARD__Alternatives : ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) );
    public final void rule__WILDCARD__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1363:1: ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_LETTER_X) ) {
                alt22=1;
            }
            else if ( (LA22_0==RULE_ASTERIX) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalSemver.g:1364:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1364:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1365:3: RULE_LETTER_X
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
                    // InternalSemver.g:1370:2: ( RULE_ASTERIX )
                    {
                    // InternalSemver.g:1370:2: ( RULE_ASTERIX )
                    // InternalSemver.g:1371:3: RULE_ASTERIX
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
    // InternalSemver.g:1380:1: rule__LETTER__Alternatives : ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) );
    public final void rule__LETTER__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1384:1: ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) )
            int alt23=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_V:
                {
                alt23=1;
                }
                break;
            case RULE_LETTER_X:
                {
                alt23=2;
                }
                break;
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
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
                    // InternalSemver.g:1385:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1385:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1386:3: RULE_LETTER_V
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
                    // InternalSemver.g:1391:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1391:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1392:3: RULE_LETTER_X
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
                    // InternalSemver.g:1397:2: ( ruleLETTER_NO_VX )
                    {
                    // InternalSemver.g:1397:2: ( ruleLETTER_NO_VX )
                    // InternalSemver.g:1398:3: ruleLETTER_NO_VX
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
    // InternalSemver.g:1407:1: rule__LETTER_NO_VX__Alternatives : ( ( RULE_LETTER_S ) | ( RULE_LETTER_M ) | ( RULE_LETTER_R ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_L ) | ( RULE_LETTER_E ) | ( RULE_LETTER_OTHER ) );
    public final void rule__LETTER_NO_VX__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1411:1: ( ( RULE_LETTER_S ) | ( RULE_LETTER_M ) | ( RULE_LETTER_R ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_L ) | ( RULE_LETTER_E ) | ( RULE_LETTER_OTHER ) )
            int alt24=8;
            switch ( input.LA(1) ) {
            case RULE_LETTER_S:
                {
                alt24=1;
                }
                break;
            case RULE_LETTER_M:
                {
                alt24=2;
                }
                break;
            case RULE_LETTER_R:
                {
                alt24=3;
                }
                break;
            case RULE_LETTER_F:
                {
                alt24=4;
                }
                break;
            case RULE_LETTER_I:
                {
                alt24=5;
                }
                break;
            case RULE_LETTER_L:
                {
                alt24=6;
                }
                break;
            case RULE_LETTER_E:
                {
                alt24=7;
                }
                break;
            case RULE_LETTER_OTHER:
                {
                alt24=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // InternalSemver.g:1412:2: ( RULE_LETTER_S )
                    {
                    // InternalSemver.g:1412:2: ( RULE_LETTER_S )
                    // InternalSemver.g:1413:3: RULE_LETTER_S
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_0()); 
                    }
                    match(input,RULE_LETTER_S,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1418:2: ( RULE_LETTER_M )
                    {
                    // InternalSemver.g:1418:2: ( RULE_LETTER_M )
                    // InternalSemver.g:1419:3: RULE_LETTER_M
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_1()); 
                    }
                    match(input,RULE_LETTER_M,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1424:2: ( RULE_LETTER_R )
                    {
                    // InternalSemver.g:1424:2: ( RULE_LETTER_R )
                    // InternalSemver.g:1425:3: RULE_LETTER_R
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_2()); 
                    }
                    match(input,RULE_LETTER_R,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1430:2: ( RULE_LETTER_F )
                    {
                    // InternalSemver.g:1430:2: ( RULE_LETTER_F )
                    // InternalSemver.g:1431:3: RULE_LETTER_F
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
                    // InternalSemver.g:1436:2: ( RULE_LETTER_I )
                    {
                    // InternalSemver.g:1436:2: ( RULE_LETTER_I )
                    // InternalSemver.g:1437:3: RULE_LETTER_I
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
                    // InternalSemver.g:1442:2: ( RULE_LETTER_L )
                    {
                    // InternalSemver.g:1442:2: ( RULE_LETTER_L )
                    // InternalSemver.g:1443:3: RULE_LETTER_L
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_5()); 
                    }
                    match(input,RULE_LETTER_L,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1448:2: ( RULE_LETTER_E )
                    {
                    // InternalSemver.g:1448:2: ( RULE_LETTER_E )
                    // InternalSemver.g:1449:3: RULE_LETTER_E
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_6()); 
                    }
                    match(input,RULE_LETTER_E,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalSemver.g:1454:2: ( RULE_LETTER_OTHER )
                    {
                    // InternalSemver.g:1454:2: ( RULE_LETTER_OTHER )
                    // InternalSemver.g:1455:3: RULE_LETTER_OTHER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_7()); 
                    }
                    match(input,RULE_LETTER_OTHER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_7()); 
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
    // InternalSemver.g:1464:1: rule__VersionComparator__Alternatives : ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1468:1: ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt25=7;
            switch ( input.LA(1) ) {
            case 42:
                {
                alt25=1;
                }
                break;
            case 43:
                {
                alt25=2;
                }
                break;
            case 44:
                {
                alt25=3;
                }
                break;
            case 45:
                {
                alt25=4;
                }
                break;
            case 46:
                {
                alt25=5;
                }
                break;
            case 47:
                {
                alt25=6;
                }
                break;
            case 48:
                {
                alt25=7;
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
                    // InternalSemver.g:1469:2: ( ( '=' ) )
                    {
                    // InternalSemver.g:1469:2: ( ( '=' ) )
                    // InternalSemver.g:1470:3: ( '=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }
                    // InternalSemver.g:1471:3: ( '=' )
                    // InternalSemver.g:1471:4: '='
                    {
                    match(input,42,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1475:2: ( ( '<' ) )
                    {
                    // InternalSemver.g:1475:2: ( ( '<' ) )
                    // InternalSemver.g:1476:3: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }
                    // InternalSemver.g:1477:3: ( '<' )
                    // InternalSemver.g:1477:4: '<'
                    {
                    match(input,43,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1481:2: ( ( '~' ) )
                    {
                    // InternalSemver.g:1481:2: ( ( '~' ) )
                    // InternalSemver.g:1482:3: ( '~' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }
                    // InternalSemver.g:1483:3: ( '~' )
                    // InternalSemver.g:1483:4: '~'
                    {
                    match(input,44,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1487:2: ( ( '^' ) )
                    {
                    // InternalSemver.g:1487:2: ( ( '^' ) )
                    // InternalSemver.g:1488:3: ( '^' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }
                    // InternalSemver.g:1489:3: ( '^' )
                    // InternalSemver.g:1489:4: '^'
                    {
                    match(input,45,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1493:2: ( ( '<=' ) )
                    {
                    // InternalSemver.g:1493:2: ( ( '<=' ) )
                    // InternalSemver.g:1494:3: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }
                    // InternalSemver.g:1495:3: ( '<=' )
                    // InternalSemver.g:1495:4: '<='
                    {
                    match(input,46,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1499:2: ( ( '>' ) )
                    {
                    // InternalSemver.g:1499:2: ( ( '>' ) )
                    // InternalSemver.g:1500:3: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }
                    // InternalSemver.g:1501:3: ( '>' )
                    // InternalSemver.g:1501:4: '>'
                    {
                    match(input,47,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1505:2: ( ( '>=' ) )
                    {
                    // InternalSemver.g:1505:2: ( ( '>=' ) )
                    // InternalSemver.g:1506:3: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); 
                    }
                    // InternalSemver.g:1507:3: ( '>=' )
                    // InternalSemver.g:1507:4: '>='
                    {
                    match(input,48,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1515:1: rule__NPMVersionRequirement__Group_0__0 : rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 ;
    public final void rule__NPMVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1519:1: ( rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 )
            // InternalSemver.g:1520:2: rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1
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
    // InternalSemver.g:1527:1: rule__NPMVersionRequirement__Group_0__0__Impl : ( ( RULE_WS )? ) ;
    public final void rule__NPMVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1531:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:1532:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:1532:1: ( ( RULE_WS )? )
            // InternalSemver.g:1533:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }
            // InternalSemver.g:1534:2: ( RULE_WS )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_WS) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalSemver.g:1534:3: RULE_WS
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
    // InternalSemver.g:1542:1: rule__NPMVersionRequirement__Group_0__1 : rule__NPMVersionRequirement__Group_0__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1546:1: ( rule__NPMVersionRequirement__Group_0__1__Impl )
            // InternalSemver.g:1547:2: rule__NPMVersionRequirement__Group_0__1__Impl
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
    // InternalSemver.g:1553:1: rule__NPMVersionRequirement__Group_0__1__Impl : ( ruleVersionRangeSetRequirement ) ;
    public final void rule__NPMVersionRequirement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1557:1: ( ( ruleVersionRangeSetRequirement ) )
            // InternalSemver.g:1558:1: ( ruleVersionRangeSetRequirement )
            {
            // InternalSemver.g:1558:1: ( ruleVersionRangeSetRequirement )
            // InternalSemver.g:1559:2: ruleVersionRangeSetRequirement
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
    // InternalSemver.g:1569:1: rule__NPMVersionRequirement__Group_1__0 : rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 ;
    public final void rule__NPMVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1573:1: ( rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 )
            // InternalSemver.g:1574:2: rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1
            {
            pushFollow(FOLLOW_5);
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
    // InternalSemver.g:1581:1: rule__NPMVersionRequirement__Group_1__0__Impl : ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) ;
    public final void rule__NPMVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1585:1: ( ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) )
            // InternalSemver.g:1586:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            {
            // InternalSemver.g:1586:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            // InternalSemver.g:1587:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:1588:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            // InternalSemver.g:1588:3: rule__NPMVersionRequirement__Alternatives_1_0
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
    // InternalSemver.g:1596:1: rule__NPMVersionRequirement__Group_1__1 : rule__NPMVersionRequirement__Group_1__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1600:1: ( rule__NPMVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1601:2: rule__NPMVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1607:1: rule__NPMVersionRequirement__Group_1__1__Impl : ( ( RULE_WS )? ) ;
    public final void rule__NPMVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1611:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:1612:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:1612:1: ( ( RULE_WS )? )
            // InternalSemver.g:1613:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:1614:2: ( RULE_WS )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==RULE_WS) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalSemver.g:1614:3: RULE_WS
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
    // InternalSemver.g:1623:1: rule__LocalPathVersionRequirement__Group__0 : rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 ;
    public final void rule__LocalPathVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1627:1: ( rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 )
            // InternalSemver.g:1628:2: rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:1635:1: rule__LocalPathVersionRequirement__Group__0__Impl : ( ruleFILE_TAG ) ;
    public final void rule__LocalPathVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1639:1: ( ( ruleFILE_TAG ) )
            // InternalSemver.g:1640:1: ( ruleFILE_TAG )
            {
            // InternalSemver.g:1640:1: ( ruleFILE_TAG )
            // InternalSemver.g:1641:2: ruleFILE_TAG
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
    // InternalSemver.g:1650:1: rule__LocalPathVersionRequirement__Group__1 : rule__LocalPathVersionRequirement__Group__1__Impl ;
    public final void rule__LocalPathVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1654:1: ( rule__LocalPathVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1655:2: rule__LocalPathVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1661:1: rule__LocalPathVersionRequirement__Group__1__Impl : ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1665:1: ( ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) )
            // InternalSemver.g:1666:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            {
            // InternalSemver.g:1666:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            // InternalSemver.g:1667:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }
            // InternalSemver.g:1668:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            // InternalSemver.g:1668:3: rule__LocalPathVersionRequirement__LocalPathAssignment_1
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
    // InternalSemver.g:1677:1: rule__URLVersionRequirement__Group__0 : rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 ;
    public final void rule__URLVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1681:1: ( rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 )
            // InternalSemver.g:1682:2: rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_7);
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
    // InternalSemver.g:1689:1: rule__URLVersionRequirement__Group__0__Impl : ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) ;
    public final void rule__URLVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1693:1: ( ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) )
            // InternalSemver.g:1694:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            {
            // InternalSemver.g:1694:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            // InternalSemver.g:1695:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }
            // InternalSemver.g:1696:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            // InternalSemver.g:1696:3: rule__URLVersionRequirement__ProtocolAssignment_0
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
    // InternalSemver.g:1704:1: rule__URLVersionRequirement__Group__1 : rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 ;
    public final void rule__URLVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1708:1: ( rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 )
            // InternalSemver.g:1709:2: rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:1716:1: rule__URLVersionRequirement__Group__1__Impl : ( ( rule__URLVersionRequirement__Group_1__0 ) ) ;
    public final void rule__URLVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1720:1: ( ( ( rule__URLVersionRequirement__Group_1__0 ) ) )
            // InternalSemver.g:1721:1: ( ( rule__URLVersionRequirement__Group_1__0 ) )
            {
            // InternalSemver.g:1721:1: ( ( rule__URLVersionRequirement__Group_1__0 ) )
            // InternalSemver.g:1722:2: ( rule__URLVersionRequirement__Group_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1723:2: ( rule__URLVersionRequirement__Group_1__0 )
            // InternalSemver.g:1723:3: rule__URLVersionRequirement__Group_1__0
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
    // InternalSemver.g:1731:1: rule__URLVersionRequirement__Group__2 : rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 ;
    public final void rule__URLVersionRequirement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1735:1: ( rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 )
            // InternalSemver.g:1736:2: rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3
            {
            pushFollow(FOLLOW_9);
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
    // InternalSemver.g:1743:1: rule__URLVersionRequirement__Group__2__Impl : ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) ;
    public final void rule__URLVersionRequirement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1747:1: ( ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) )
            // InternalSemver.g:1748:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            {
            // InternalSemver.g:1748:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            // InternalSemver.g:1749:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }
            // InternalSemver.g:1750:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            // InternalSemver.g:1750:3: rule__URLVersionRequirement__UrlAssignment_2
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
    // InternalSemver.g:1758:1: rule__URLVersionRequirement__Group__3 : rule__URLVersionRequirement__Group__3__Impl ;
    public final void rule__URLVersionRequirement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1762:1: ( rule__URLVersionRequirement__Group__3__Impl )
            // InternalSemver.g:1763:2: rule__URLVersionRequirement__Group__3__Impl
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
    // InternalSemver.g:1769:1: rule__URLVersionRequirement__Group__3__Impl : ( ( rule__URLVersionRequirement__Group_3__0 )? ) ;
    public final void rule__URLVersionRequirement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1773:1: ( ( ( rule__URLVersionRequirement__Group_3__0 )? ) )
            // InternalSemver.g:1774:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            {
            // InternalSemver.g:1774:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            // InternalSemver.g:1775:2: ( rule__URLVersionRequirement__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }
            // InternalSemver.g:1776:2: ( rule__URLVersionRequirement__Group_3__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==49) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalSemver.g:1776:3: rule__URLVersionRequirement__Group_3__0
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
    // InternalSemver.g:1785:1: rule__URLVersionRequirement__Group_1__0 : rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1 ;
    public final void rule__URLVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1789:1: ( rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1 )
            // InternalSemver.g:1790:2: rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1
            {
            pushFollow(FOLLOW_10);
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
    // InternalSemver.g:1797:1: rule__URLVersionRequirement__Group_1__0__Impl : ( ':' ) ;
    public final void rule__URLVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1801:1: ( ( ':' ) )
            // InternalSemver.g:1802:1: ( ':' )
            {
            // InternalSemver.g:1802:1: ( ':' )
            // InternalSemver.g:1803:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0()); 
            }
            match(input,41,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1812:1: rule__URLVersionRequirement__Group_1__1 : rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2 ;
    public final void rule__URLVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1816:1: ( rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2 )
            // InternalSemver.g:1817:2: rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2
            {
            pushFollow(FOLLOW_10);
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
    // InternalSemver.g:1824:1: rule__URLVersionRequirement__Group_1__1__Impl : ( '/' ) ;
    public final void rule__URLVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1828:1: ( ( '/' ) )
            // InternalSemver.g:1829:1: ( '/' )
            {
            // InternalSemver.g:1829:1: ( '/' )
            // InternalSemver.g:1830:2: '/'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1()); 
            }
            match(input,35,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1839:1: rule__URLVersionRequirement__Group_1__2 : rule__URLVersionRequirement__Group_1__2__Impl ;
    public final void rule__URLVersionRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1843:1: ( rule__URLVersionRequirement__Group_1__2__Impl )
            // InternalSemver.g:1844:2: rule__URLVersionRequirement__Group_1__2__Impl
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
    // InternalSemver.g:1850:1: rule__URLVersionRequirement__Group_1__2__Impl : ( '/' ) ;
    public final void rule__URLVersionRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1854:1: ( ( '/' ) )
            // InternalSemver.g:1855:1: ( '/' )
            {
            // InternalSemver.g:1855:1: ( '/' )
            // InternalSemver.g:1856:2: '/'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2()); 
            }
            match(input,35,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1866:1: rule__URLVersionRequirement__Group_3__0 : rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 ;
    public final void rule__URLVersionRequirement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1870:1: ( rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 )
            // InternalSemver.g:1871:2: rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1
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
    // InternalSemver.g:1878:1: rule__URLVersionRequirement__Group_3__0__Impl : ( '#' ) ;
    public final void rule__URLVersionRequirement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1882:1: ( ( '#' ) )
            // InternalSemver.g:1883:1: ( '#' )
            {
            // InternalSemver.g:1883:1: ( '#' )
            // InternalSemver.g:1884:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); 
            }
            match(input,49,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1893:1: rule__URLVersionRequirement__Group_3__1 : rule__URLVersionRequirement__Group_3__1__Impl ;
    public final void rule__URLVersionRequirement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1897:1: ( rule__URLVersionRequirement__Group_3__1__Impl )
            // InternalSemver.g:1898:2: rule__URLVersionRequirement__Group_3__1__Impl
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
    // InternalSemver.g:1904:1: rule__URLVersionRequirement__Group_3__1__Impl : ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) ;
    public final void rule__URLVersionRequirement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1908:1: ( ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) )
            // InternalSemver.g:1909:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            {
            // InternalSemver.g:1909:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            // InternalSemver.g:1910:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }
            // InternalSemver.g:1911:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            // InternalSemver.g:1911:3: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
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
    // InternalSemver.g:1920:1: rule__URLVersionSpecifier__Group_0__0 : rule__URLVersionSpecifier__Group_0__0__Impl ;
    public final void rule__URLVersionSpecifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1924:1: ( rule__URLVersionSpecifier__Group_0__0__Impl )
            // InternalSemver.g:1925:2: rule__URLVersionSpecifier__Group_0__0__Impl
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
    // InternalSemver.g:1931:1: rule__URLVersionSpecifier__Group_0__0__Impl : ( ruleURLSemver ) ;
    public final void rule__URLVersionSpecifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1935:1: ( ( ruleURLSemver ) )
            // InternalSemver.g:1936:1: ( ruleURLSemver )
            {
            // InternalSemver.g:1936:1: ( ruleURLSemver )
            // InternalSemver.g:1937:2: ruleURLSemver
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
    // InternalSemver.g:1947:1: rule__URLVersionSpecifier__Group_1__0 : rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 ;
    public final void rule__URLVersionSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1951:1: ( rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 )
            // InternalSemver.g:1952:2: rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1
            {
            pushFollow(FOLLOW_12);
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
    // InternalSemver.g:1959:1: rule__URLVersionSpecifier__Group_1__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1963:1: ( ( () ) )
            // InternalSemver.g:1964:1: ( () )
            {
            // InternalSemver.g:1964:1: ( () )
            // InternalSemver.g:1965:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); 
            }
            // InternalSemver.g:1966:2: ()
            // InternalSemver.g:1966:3: 
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
    // InternalSemver.g:1974:1: rule__URLVersionSpecifier__Group_1__1 : rule__URLVersionSpecifier__Group_1__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1978:1: ( rule__URLVersionSpecifier__Group_1__1__Impl )
            // InternalSemver.g:1979:2: rule__URLVersionSpecifier__Group_1__1__Impl
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
    // InternalSemver.g:1985:1: rule__URLVersionSpecifier__Group_1__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1989:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1990:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1990:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1991:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1992:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            // InternalSemver.g:1992:3: rule__URLVersionSpecifier__CommitISHAssignment_1_1
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
    // InternalSemver.g:2001:1: rule__URLVersionSpecifier__Group_2__0 : rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 ;
    public final void rule__URLVersionSpecifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2005:1: ( rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 )
            // InternalSemver.g:2006:2: rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:2013:1: rule__URLVersionSpecifier__Group_2__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2017:1: ( ( () ) )
            // InternalSemver.g:2018:1: ( () )
            {
            // InternalSemver.g:2018:1: ( () )
            // InternalSemver.g:2019:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); 
            }
            // InternalSemver.g:2020:2: ()
            // InternalSemver.g:2020:3: 
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
    // InternalSemver.g:2028:1: rule__URLVersionSpecifier__Group_2__1 : rule__URLVersionSpecifier__Group_2__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2032:1: ( rule__URLVersionSpecifier__Group_2__1__Impl )
            // InternalSemver.g:2033:2: rule__URLVersionSpecifier__Group_2__1__Impl
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
    // InternalSemver.g:2039:1: rule__URLVersionSpecifier__Group_2__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2043:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) )
            // InternalSemver.g:2044:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            {
            // InternalSemver.g:2044:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            // InternalSemver.g:2045:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); 
            }
            // InternalSemver.g:2046:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            // InternalSemver.g:2046:3: rule__URLVersionSpecifier__CommitISHAssignment_2_1
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
    // InternalSemver.g:2055:1: rule__URLSemver__Group__0 : rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 ;
    public final void rule__URLSemver__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2059:1: ( rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 )
            // InternalSemver.g:2060:2: rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1
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
    // InternalSemver.g:2067:1: rule__URLSemver__Group__0__Impl : ( () ) ;
    public final void rule__URLSemver__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2071:1: ( ( () ) )
            // InternalSemver.g:2072:1: ( () )
            {
            // InternalSemver.g:2072:1: ( () )
            // InternalSemver.g:2073:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); 
            }
            // InternalSemver.g:2074:2: ()
            // InternalSemver.g:2074:3: 
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
    // InternalSemver.g:2082:1: rule__URLSemver__Group__1 : rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 ;
    public final void rule__URLSemver__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2086:1: ( rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 )
            // InternalSemver.g:2087:2: rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2
            {
            pushFollow(FOLLOW_13);
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
    // InternalSemver.g:2094:1: rule__URLSemver__Group__1__Impl : ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) ;
    public final void rule__URLSemver__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2098:1: ( ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) )
            // InternalSemver.g:2099:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            {
            // InternalSemver.g:2099:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            // InternalSemver.g:2100:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); 
            }
            // InternalSemver.g:2101:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_LETTER_S) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalSemver.g:2101:3: rule__URLSemver__WithSemverTagAssignment_1
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
    // InternalSemver.g:2109:1: rule__URLSemver__Group__2 : rule__URLSemver__Group__2__Impl ;
    public final void rule__URLSemver__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2113:1: ( rule__URLSemver__Group__2__Impl )
            // InternalSemver.g:2114:2: rule__URLSemver__Group__2__Impl
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
    // InternalSemver.g:2120:1: rule__URLSemver__Group__2__Impl : ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) ;
    public final void rule__URLSemver__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2124:1: ( ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) )
            // InternalSemver.g:2125:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            {
            // InternalSemver.g:2125:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            // InternalSemver.g:2126:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); 
            }
            // InternalSemver.g:2127:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            // InternalSemver.g:2127:3: rule__URLSemver__SimpleVersionAssignment_2
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


    // $ANTLR start "rule__GitHubVersionRequirement__Group__0"
    // InternalSemver.g:2136:1: rule__GitHubVersionRequirement__Group__0 : rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 ;
    public final void rule__GitHubVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2140:1: ( rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 )
            // InternalSemver.g:2141:2: rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1
            {
            pushFollow(FOLLOW_9);
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
    // InternalSemver.g:2148:1: rule__GitHubVersionRequirement__Group__0__Impl : ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) ;
    public final void rule__GitHubVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2152:1: ( ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) )
            // InternalSemver.g:2153:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            {
            // InternalSemver.g:2153:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            // InternalSemver.g:2154:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }
            // InternalSemver.g:2155:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            // InternalSemver.g:2155:3: rule__GitHubVersionRequirement__GithubUrlAssignment_0
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
    // InternalSemver.g:2163:1: rule__GitHubVersionRequirement__Group__1 : rule__GitHubVersionRequirement__Group__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2167:1: ( rule__GitHubVersionRequirement__Group__1__Impl )
            // InternalSemver.g:2168:2: rule__GitHubVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:2174:1: rule__GitHubVersionRequirement__Group__1__Impl : ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) ;
    public final void rule__GitHubVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2178:1: ( ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) )
            // InternalSemver.g:2179:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:2179:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            // InternalSemver.g:2180:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:2181:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==49) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalSemver.g:2181:3: rule__GitHubVersionRequirement__Group_1__0
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
    // InternalSemver.g:2190:1: rule__GitHubVersionRequirement__Group_1__0 : rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 ;
    public final void rule__GitHubVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2194:1: ( rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 )
            // InternalSemver.g:2195:2: rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1
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
    // InternalSemver.g:2202:1: rule__GitHubVersionRequirement__Group_1__0__Impl : ( '#' ) ;
    public final void rule__GitHubVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2206:1: ( ( '#' ) )
            // InternalSemver.g:2207:1: ( '#' )
            {
            // InternalSemver.g:2207:1: ( '#' )
            // InternalSemver.g:2208:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); 
            }
            match(input,49,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2217:1: rule__GitHubVersionRequirement__Group_1__1 : rule__GitHubVersionRequirement__Group_1__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2221:1: ( rule__GitHubVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:2222:2: rule__GitHubVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:2228:1: rule__GitHubVersionRequirement__Group_1__1__Impl : ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) ;
    public final void rule__GitHubVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2232:1: ( ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:2233:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:2233:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:2234:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:2235:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            // InternalSemver.g:2235:3: rule__GitHubVersionRequirement__CommitISHAssignment_1_1
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
    // InternalSemver.g:2244:1: rule__VersionRangeSetRequirement__Group__0 : rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 ;
    public final void rule__VersionRangeSetRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2248:1: ( rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 )
            // InternalSemver.g:2249:2: rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1
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
    // InternalSemver.g:2256:1: rule__VersionRangeSetRequirement__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSetRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2260:1: ( ( () ) )
            // InternalSemver.g:2261:1: ( () )
            {
            // InternalSemver.g:2261:1: ( () )
            // InternalSemver.g:2262:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }
            // InternalSemver.g:2263:2: ()
            // InternalSemver.g:2263:3: 
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
    // InternalSemver.g:2271:1: rule__VersionRangeSetRequirement__Group__1 : rule__VersionRangeSetRequirement__Group__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2275:1: ( rule__VersionRangeSetRequirement__Group__1__Impl )
            // InternalSemver.g:2276:2: rule__VersionRangeSetRequirement__Group__1__Impl
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
    // InternalSemver.g:2282:1: rule__VersionRangeSetRequirement__Group__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) ;
    public final void rule__VersionRangeSetRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2286:1: ( ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) )
            // InternalSemver.g:2287:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:2287:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            // InternalSemver.g:2288:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:2289:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=RULE_DIGITS && LA31_0<=RULE_LETTER_V)||(LA31_0>=42 && LA31_0<=48)) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalSemver.g:2289:3: rule__VersionRangeSetRequirement__Group_1__0
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
    // InternalSemver.g:2298:1: rule__VersionRangeSetRequirement__Group_1__0 : rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2302:1: ( rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 )
            // InternalSemver.g:2303:2: rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1
            {
            pushFollow(FOLLOW_14);
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
    // InternalSemver.g:2310:1: rule__VersionRangeSetRequirement__Group_1__0__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2314:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) )
            // InternalSemver.g:2315:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            {
            // InternalSemver.g:2315:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            // InternalSemver.g:2316:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }
            // InternalSemver.g:2317:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            // InternalSemver.g:2317:3: rule__VersionRangeSetRequirement__RangesAssignment_1_0
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
    // InternalSemver.g:2325:1: rule__VersionRangeSetRequirement__Group_1__1 : rule__VersionRangeSetRequirement__Group_1__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2329:1: ( rule__VersionRangeSetRequirement__Group_1__1__Impl )
            // InternalSemver.g:2330:2: rule__VersionRangeSetRequirement__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSetRequirement__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // InternalSemver.g:2336:1: rule__VersionRangeSetRequirement__Group_1__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2340:1: ( ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) )
            // InternalSemver.g:2341:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            {
            // InternalSemver.g:2341:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            // InternalSemver.g:2342:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }
            // InternalSemver.g:2343:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_WS||LA32_0==50) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:2343:3: rule__VersionRangeSetRequirement__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_15);
            	    rule__VersionRangeSetRequirement__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop32;
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


    // $ANTLR start "rule__VersionRangeSetRequirement__Group_1_1__0"
    // InternalSemver.g:2352:1: rule__VersionRangeSetRequirement__Group_1_1__0 : rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2356:1: ( rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 )
            // InternalSemver.g:2357:2: rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1
            {
            pushFollow(FOLLOW_14);
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
    // InternalSemver.g:2364:1: rule__VersionRangeSetRequirement__Group_1_1__0__Impl : ( ( RULE_WS )? ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2368:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:2369:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:2369:1: ( ( RULE_WS )? )
            // InternalSemver.g:2370:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }
            // InternalSemver.g:2371:2: ( RULE_WS )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RULE_WS) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalSemver.g:2371:3: RULE_WS
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
    // InternalSemver.g:2379:1: rule__VersionRangeSetRequirement__Group_1_1__1 : rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2383:1: ( rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 )
            // InternalSemver.g:2384:2: rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2
            {
            pushFollow(FOLLOW_16);
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
    // InternalSemver.g:2391:1: rule__VersionRangeSetRequirement__Group_1_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2395:1: ( ( '||' ) )
            // InternalSemver.g:2396:1: ( '||' )
            {
            // InternalSemver.g:2396:1: ( '||' )
            // InternalSemver.g:2397:2: '||'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); 
            }
            match(input,50,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2406:1: rule__VersionRangeSetRequirement__Group_1_1__2 : rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2410:1: ( rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 )
            // InternalSemver.g:2411:2: rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3
            {
            pushFollow(FOLLOW_16);
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
    // InternalSemver.g:2418:1: rule__VersionRangeSetRequirement__Group_1_1__2__Impl : ( ( RULE_WS )? ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2422:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:2423:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:2423:1: ( ( RULE_WS )? )
            // InternalSemver.g:2424:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }
            // InternalSemver.g:2425:2: ( RULE_WS )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RULE_WS) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalSemver.g:2425:3: RULE_WS
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
    // InternalSemver.g:2433:1: rule__VersionRangeSetRequirement__Group_1_1__3 : rule__VersionRangeSetRequirement__Group_1_1__3__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2437:1: ( rule__VersionRangeSetRequirement__Group_1_1__3__Impl )
            // InternalSemver.g:2438:2: rule__VersionRangeSetRequirement__Group_1_1__3__Impl
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
    // InternalSemver.g:2444:1: rule__VersionRangeSetRequirement__Group_1_1__3__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2448:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) )
            // InternalSemver.g:2449:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            {
            // InternalSemver.g:2449:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            // InternalSemver.g:2450:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }
            // InternalSemver.g:2451:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            // InternalSemver.g:2451:3: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
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
    // InternalSemver.g:2460:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2464:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemver.g:2465:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
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
    // InternalSemver.g:2472:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2476:1: ( ( () ) )
            // InternalSemver.g:2477:1: ( () )
            {
            // InternalSemver.g:2477:1: ( () )
            // InternalSemver.g:2478:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }
            // InternalSemver.g:2479:2: ()
            // InternalSemver.g:2479:3: 
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
    // InternalSemver.g:2487:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2491:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemver.g:2492:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
            {
            pushFollow(FOLLOW_5);
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
    // InternalSemver.g:2499:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2503:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemver.g:2504:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemver.g:2504:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemver.g:2505:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }
            // InternalSemver.g:2506:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemver.g:2506:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSemver.g:2514:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2518:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemver.g:2519:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
            {
            pushFollow(FOLLOW_17);
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
    // InternalSemver.g:2526:1: rule__HyphenVersionRange__Group__2__Impl : ( RULE_WS ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2530:1: ( ( RULE_WS ) )
            // InternalSemver.g:2531:1: ( RULE_WS )
            {
            // InternalSemver.g:2531:1: ( RULE_WS )
            // InternalSemver.g:2532:2: RULE_WS
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
    // InternalSemver.g:2541:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2545:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSemver.g:2546:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
            {
            pushFollow(FOLLOW_5);
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
    // InternalSemver.g:2553:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2557:1: ( ( '-' ) )
            // InternalSemver.g:2558:1: ( '-' )
            {
            // InternalSemver.g:2558:1: ( '-' )
            // InternalSemver.g:2559:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 
            }
            match(input,38,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2568:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2572:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSemver.g:2573:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
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
    // InternalSemver.g:2580:1: rule__HyphenVersionRange__Group__4__Impl : ( RULE_WS ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2584:1: ( ( RULE_WS ) )
            // InternalSemver.g:2585:1: ( RULE_WS )
            {
            // InternalSemver.g:2585:1: ( RULE_WS )
            // InternalSemver.g:2586:2: RULE_WS
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
    // InternalSemver.g:2595:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2599:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSemver.g:2600:2: rule__HyphenVersionRange__Group__5__Impl
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
    // InternalSemver.g:2606:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2610:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSemver.g:2611:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSemver.g:2611:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSemver.g:2612:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }
            // InternalSemver.g:2613:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSemver.g:2613:3: rule__HyphenVersionRange__ToAssignment_5
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
    // InternalSemver.g:2622:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2626:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSemver.g:2627:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
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
    // InternalSemver.g:2634:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2638:1: ( ( () ) )
            // InternalSemver.g:2639:1: ( () )
            {
            // InternalSemver.g:2639:1: ( () )
            // InternalSemver.g:2640:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }
            // InternalSemver.g:2641:2: ()
            // InternalSemver.g:2641:3: 
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
    // InternalSemver.g:2649:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2653:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSemver.g:2654:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
            {
            pushFollow(FOLLOW_5);
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
    // InternalSemver.g:2661:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2665:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSemver.g:2666:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSemver.g:2666:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSemver.g:2667:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }
            // InternalSemver.g:2668:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSemver.g:2668:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
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
    // InternalSemver.g:2676:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2680:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSemver.g:2681:2: rule__VersionRangeContraint__Group__2__Impl
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
    // InternalSemver.g:2687:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2691:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSemver.g:2692:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSemver.g:2692:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSemver.g:2693:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }
            // InternalSemver.g:2694:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_WS) ) {
                    int LA35_2 = input.LA(2);

                    if ( ((LA35_2>=RULE_DIGITS && LA35_2<=RULE_LETTER_V)||(LA35_2>=42 && LA35_2<=48)) ) {
                        alt35=1;
                    }


                }


                switch (alt35) {
            	case 1 :
            	    // InternalSemver.g:2694:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__VersionRangeContraint__Group_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop35;
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
    // InternalSemver.g:2703:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2707:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSemver.g:2708:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
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
    // InternalSemver.g:2715:1: rule__VersionRangeContraint__Group_2__0__Impl : ( RULE_WS ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2719:1: ( ( RULE_WS ) )
            // InternalSemver.g:2720:1: ( RULE_WS )
            {
            // InternalSemver.g:2720:1: ( RULE_WS )
            // InternalSemver.g:2721:2: RULE_WS
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
    // InternalSemver.g:2730:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2734:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSemver.g:2735:2: rule__VersionRangeContraint__Group_2__1__Impl
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
    // InternalSemver.g:2741:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2745:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSemver.g:2746:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSemver.g:2746:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSemver.g:2747:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }
            // InternalSemver.g:2748:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSemver.g:2748:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
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
    // InternalSemver.g:2757:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2761:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemver.g:2762:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
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
    // InternalSemver.g:2769:1: rule__SimpleVersion__Group__0__Impl : ( ( rule__SimpleVersion__Group_0__0 )* ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2773:1: ( ( ( rule__SimpleVersion__Group_0__0 )* ) )
            // InternalSemver.g:2774:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            {
            // InternalSemver.g:2774:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            // InternalSemver.g:2775:2: ( rule__SimpleVersion__Group_0__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup_0()); 
            }
            // InternalSemver.g:2776:2: ( rule__SimpleVersion__Group_0__0 )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( ((LA36_0>=42 && LA36_0<=48)) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSemver.g:2776:3: rule__SimpleVersion__Group_0__0
            	    {
            	    pushFollow(FOLLOW_19);
            	    rule__SimpleVersion__Group_0__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop36;
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
    // InternalSemver.g:2784:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2788:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemver.g:2789:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
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
    // InternalSemver.g:2796:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2800:1: ( ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) )
            // InternalSemver.g:2801:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            {
            // InternalSemver.g:2801:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            // InternalSemver.g:2802:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); 
            }
            // InternalSemver.g:2803:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_LETTER_V) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalSemver.g:2803:3: rule__SimpleVersion__WithLetterVAssignment_1
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
    // InternalSemver.g:2811:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2815:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSemver.g:2816:2: rule__SimpleVersion__Group__2__Impl
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
    // InternalSemver.g:2822:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2826:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSemver.g:2827:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSemver.g:2827:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSemver.g:2828:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            }
            // InternalSemver.g:2829:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSemver.g:2829:3: rule__SimpleVersion__NumberAssignment_2
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
    // InternalSemver.g:2838:1: rule__SimpleVersion__Group_0__0 : rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 ;
    public final void rule__SimpleVersion__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2842:1: ( rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 )
            // InternalSemver.g:2843:2: rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1
            {
            pushFollow(FOLLOW_5);
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
    // InternalSemver.g:2850:1: rule__SimpleVersion__Group_0__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) ;
    public final void rule__SimpleVersion__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2854:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) )
            // InternalSemver.g:2855:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            {
            // InternalSemver.g:2855:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            // InternalSemver.g:2856:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); 
            }
            // InternalSemver.g:2857:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            // InternalSemver.g:2857:3: rule__SimpleVersion__ComparatorsAssignment_0_0
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
    // InternalSemver.g:2865:1: rule__SimpleVersion__Group_0__1 : rule__SimpleVersion__Group_0__1__Impl ;
    public final void rule__SimpleVersion__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2869:1: ( rule__SimpleVersion__Group_0__1__Impl )
            // InternalSemver.g:2870:2: rule__SimpleVersion__Group_0__1__Impl
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
    // InternalSemver.g:2876:1: rule__SimpleVersion__Group_0__1__Impl : ( ( RULE_WS )? ) ;
    public final void rule__SimpleVersion__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2880:1: ( ( ( RULE_WS )? ) )
            // InternalSemver.g:2881:1: ( ( RULE_WS )? )
            {
            // InternalSemver.g:2881:1: ( ( RULE_WS )? )
            // InternalSemver.g:2882:2: ( RULE_WS )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); 
            }
            // InternalSemver.g:2883:2: ( RULE_WS )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_WS) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalSemver.g:2883:3: RULE_WS
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
    // InternalSemver.g:2892:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2896:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemver.g:2897:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
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
    // InternalSemver.g:2904:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2908:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemver.g:2909:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemver.g:2909:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemver.g:2910:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }
            // InternalSemver.g:2911:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemver.g:2911:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSemver.g:2919:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2923:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSemver.g:2924:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
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
    // InternalSemver.g:2931:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2935:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemver.g:2936:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemver.g:2936:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemver.g:2937:2: ( rule__VersionNumber__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }
            // InternalSemver.g:2938:2: ( rule__VersionNumber__Group_1__0 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==36) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalSemver.g:2938:3: rule__VersionNumber__Group_1__0
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
    // InternalSemver.g:2946:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2950:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSemver.g:2951:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSemver.g:2957:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2961:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSemver.g:2962:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSemver.g:2962:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSemver.g:2963:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }
            // InternalSemver.g:2964:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==38||LA40_0==40) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalSemver.g:2964:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSemver.g:2973:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2977:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemver.g:2978:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
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
    // InternalSemver.g:2985:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2989:1: ( ( '.' ) )
            // InternalSemver.g:2990:1: ( '.' )
            {
            // InternalSemver.g:2990:1: ( '.' )
            // InternalSemver.g:2991:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            }
            match(input,36,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3000:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3004:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemver.g:3005:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
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
    // InternalSemver.g:3012:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3016:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemver.g:3017:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemver.g:3017:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemver.g:3018:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }
            // InternalSemver.g:3019:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemver.g:3019:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSemver.g:3027:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3031:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemver.g:3032:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSemver.g:3038:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3042:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemver.g:3043:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemver.g:3043:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemver.g:3044:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }
            // InternalSemver.g:3045:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==36) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalSemver.g:3045:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSemver.g:3054:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3058:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemver.g:3059:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
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
    // InternalSemver.g:3066:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3070:1: ( ( '.' ) )
            // InternalSemver.g:3071:1: ( '.' )
            {
            // InternalSemver.g:3071:1: ( '.' )
            // InternalSemver.g:3072:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            }
            match(input,36,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3081:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3085:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemver.g:3086:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
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
    // InternalSemver.g:3093:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3097:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSemver.g:3098:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSemver.g:3098:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSemver.g:3099:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }
            // InternalSemver.g:3100:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSemver.g:3100:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSemver.g:3108:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3112:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemver.g:3113:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSemver.g:3119:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3123:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSemver.g:3124:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSemver.g:3124:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSemver.g:3125:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }
            // InternalSemver.g:3126:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==36) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSemver.g:3126:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop42;
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
    // InternalSemver.g:3135:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3139:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSemver.g:3140:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
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
    // InternalSemver.g:3147:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3151:1: ( ( '.' ) )
            // InternalSemver.g:3152:1: ( '.' )
            {
            // InternalSemver.g:3152:1: ( '.' )
            // InternalSemver.g:3153:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            }
            match(input,36,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3162:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3166:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSemver.g:3167:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSemver.g:3173:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3177:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSemver.g:3178:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSemver.g:3178:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSemver.g:3179:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }
            // InternalSemver.g:3180:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSemver.g:3180:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSemver.g:3189:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3193:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemver.g:3194:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
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
    // InternalSemver.g:3201:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3205:1: ( ( '-' ) )
            // InternalSemver.g:3206:1: ( '-' )
            {
            // InternalSemver.g:3206:1: ( '-' )
            // InternalSemver.g:3207:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            }
            match(input,38,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3216:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl rule__Qualifier__Group_0__2 ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3220:1: ( rule__Qualifier__Group_0__1__Impl rule__Qualifier__Group_0__2 )
            // InternalSemver.g:3221:2: rule__Qualifier__Group_0__1__Impl rule__Qualifier__Group_0__2
            {
            pushFollow(FOLLOW_23);
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
    // InternalSemver.g:3228:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3232:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemver.g:3233:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemver.g:3233:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemver.g:3234:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }
            // InternalSemver.g:3235:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemver.g:3235:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSemver.g:3243:1: rule__Qualifier__Group_0__2 : rule__Qualifier__Group_0__2__Impl ;
    public final void rule__Qualifier__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3247:1: ( rule__Qualifier__Group_0__2__Impl )
            // InternalSemver.g:3248:2: rule__Qualifier__Group_0__2__Impl
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
    // InternalSemver.g:3254:1: rule__Qualifier__Group_0__2__Impl : ( ( rule__Qualifier__Group_0_2__0 )? ) ;
    public final void rule__Qualifier__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3258:1: ( ( ( rule__Qualifier__Group_0_2__0 )? ) )
            // InternalSemver.g:3259:1: ( ( rule__Qualifier__Group_0_2__0 )? )
            {
            // InternalSemver.g:3259:1: ( ( rule__Qualifier__Group_0_2__0 )? )
            // InternalSemver.g:3260:2: ( rule__Qualifier__Group_0_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getGroup_0_2()); 
            }
            // InternalSemver.g:3261:2: ( rule__Qualifier__Group_0_2__0 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==40) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalSemver.g:3261:3: rule__Qualifier__Group_0_2__0
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
    // InternalSemver.g:3270:1: rule__Qualifier__Group_0_2__0 : rule__Qualifier__Group_0_2__0__Impl rule__Qualifier__Group_0_2__1 ;
    public final void rule__Qualifier__Group_0_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3274:1: ( rule__Qualifier__Group_0_2__0__Impl rule__Qualifier__Group_0_2__1 )
            // InternalSemver.g:3275:2: rule__Qualifier__Group_0_2__0__Impl rule__Qualifier__Group_0_2__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalSemver.g:3282:1: rule__Qualifier__Group_0_2__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_0_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3286:1: ( ( '+' ) )
            // InternalSemver.g:3287:1: ( '+' )
            {
            // InternalSemver.g:3287:1: ( '+' )
            // InternalSemver.g:3288:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_0_2_0()); 
            }
            match(input,40,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3297:1: rule__Qualifier__Group_0_2__1 : rule__Qualifier__Group_0_2__1__Impl ;
    public final void rule__Qualifier__Group_0_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3301:1: ( rule__Qualifier__Group_0_2__1__Impl )
            // InternalSemver.g:3302:2: rule__Qualifier__Group_0_2__1__Impl
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
    // InternalSemver.g:3308:1: rule__Qualifier__Group_0_2__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) ) ;
    public final void rule__Qualifier__Group_0_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3312:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) ) )
            // InternalSemver.g:3313:1: ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) )
            {
            // InternalSemver.g:3313:1: ( ( rule__Qualifier__BuildMetadataAssignment_0_2_1 ) )
            // InternalSemver.g:3314:2: ( rule__Qualifier__BuildMetadataAssignment_0_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_0_2_1()); 
            }
            // InternalSemver.g:3315:2: ( rule__Qualifier__BuildMetadataAssignment_0_2_1 )
            // InternalSemver.g:3315:3: rule__Qualifier__BuildMetadataAssignment_0_2_1
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
    // InternalSemver.g:3324:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3328:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemver.g:3329:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
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
    // InternalSemver.g:3336:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3340:1: ( ( '+' ) )
            // InternalSemver.g:3341:1: ( '+' )
            {
            // InternalSemver.g:3341:1: ( '+' )
            // InternalSemver.g:3342:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            }
            match(input,40,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3351:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3355:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemver.g:3356:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSemver.g:3362:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3366:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemver.g:3367:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemver.g:3367:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemver.g:3368:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }
            // InternalSemver.g:3369:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemver.g:3369:3: rule__Qualifier__BuildMetadataAssignment_1_1
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
    // InternalSemver.g:3378:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3382:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSemver.g:3383:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
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
    // InternalSemver.g:3390:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3394:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSemver.g:3395:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSemver.g:3395:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSemver.g:3396:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }
            // InternalSemver.g:3397:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSemver.g:3397:3: rule__QualifierTag__PartsAssignment_0
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
    // InternalSemver.g:3405:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3409:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSemver.g:3410:2: rule__QualifierTag__Group__1__Impl
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
    // InternalSemver.g:3416:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3420:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSemver.g:3421:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSemver.g:3421:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSemver.g:3422:2: ( rule__QualifierTag__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }
            // InternalSemver.g:3423:2: ( rule__QualifierTag__Group_1__0 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==36) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSemver.g:3423:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__QualifierTag__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop44;
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
    // InternalSemver.g:3432:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3436:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSemver.g:3437:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
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
    // InternalSemver.g:3444:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3448:1: ( ( '.' ) )
            // InternalSemver.g:3449:1: ( '.' )
            {
            // InternalSemver.g:3449:1: ( '.' )
            // InternalSemver.g:3450:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 
            }
            match(input,36,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3459:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3463:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSemver.g:3464:2: rule__QualifierTag__Group_1__1__Impl
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
    // InternalSemver.g:3470:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3474:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSemver.g:3475:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSemver.g:3475:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSemver.g:3476:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }
            // InternalSemver.g:3477:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSemver.g:3477:3: rule__QualifierTag__PartsAssignment_1_1
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
    // InternalSemver.g:3486:1: rule__FILE_TAG__Group__0 : rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 ;
    public final void rule__FILE_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3490:1: ( rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 )
            // InternalSemver.g:3491:2: rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1
            {
            pushFollow(FOLLOW_24);
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
    // InternalSemver.g:3498:1: rule__FILE_TAG__Group__0__Impl : ( RULE_LETTER_F ) ;
    public final void rule__FILE_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3502:1: ( ( RULE_LETTER_F ) )
            // InternalSemver.g:3503:1: ( RULE_LETTER_F )
            {
            // InternalSemver.g:3503:1: ( RULE_LETTER_F )
            // InternalSemver.g:3504:2: RULE_LETTER_F
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
    // InternalSemver.g:3513:1: rule__FILE_TAG__Group__1 : rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 ;
    public final void rule__FILE_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3517:1: ( rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 )
            // InternalSemver.g:3518:2: rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2
            {
            pushFollow(FOLLOW_25);
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
    // InternalSemver.g:3525:1: rule__FILE_TAG__Group__1__Impl : ( RULE_LETTER_I ) ;
    public final void rule__FILE_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3529:1: ( ( RULE_LETTER_I ) )
            // InternalSemver.g:3530:1: ( RULE_LETTER_I )
            {
            // InternalSemver.g:3530:1: ( RULE_LETTER_I )
            // InternalSemver.g:3531:2: RULE_LETTER_I
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
    // InternalSemver.g:3540:1: rule__FILE_TAG__Group__2 : rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 ;
    public final void rule__FILE_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3544:1: ( rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 )
            // InternalSemver.g:3545:2: rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3
            {
            pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3552:1: rule__FILE_TAG__Group__2__Impl : ( RULE_LETTER_L ) ;
    public final void rule__FILE_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3556:1: ( ( RULE_LETTER_L ) )
            // InternalSemver.g:3557:1: ( RULE_LETTER_L )
            {
            // InternalSemver.g:3557:1: ( RULE_LETTER_L )
            // InternalSemver.g:3558:2: RULE_LETTER_L
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
    // InternalSemver.g:3567:1: rule__FILE_TAG__Group__3 : rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 ;
    public final void rule__FILE_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3571:1: ( rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 )
            // InternalSemver.g:3572:2: rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4
            {
            pushFollow(FOLLOW_7);
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
    // InternalSemver.g:3579:1: rule__FILE_TAG__Group__3__Impl : ( RULE_LETTER_E ) ;
    public final void rule__FILE_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3583:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3584:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3584:1: ( RULE_LETTER_E )
            // InternalSemver.g:3585:2: RULE_LETTER_E
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
    // InternalSemver.g:3594:1: rule__FILE_TAG__Group__4 : rule__FILE_TAG__Group__4__Impl ;
    public final void rule__FILE_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3598:1: ( rule__FILE_TAG__Group__4__Impl )
            // InternalSemver.g:3599:2: rule__FILE_TAG__Group__4__Impl
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
    // InternalSemver.g:3605:1: rule__FILE_TAG__Group__4__Impl : ( ':' ) ;
    public final void rule__FILE_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3609:1: ( ( ':' ) )
            // InternalSemver.g:3610:1: ( ':' )
            {
            // InternalSemver.g:3610:1: ( ':' )
            // InternalSemver.g:3611:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getColonKeyword_4()); 
            }
            match(input,41,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3621:1: rule__SEMVER_TAG__Group__0 : rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 ;
    public final void rule__SEMVER_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3625:1: ( rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 )
            // InternalSemver.g:3626:2: rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1
            {
            pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3633:1: rule__SEMVER_TAG__Group__0__Impl : ( RULE_LETTER_S ) ;
    public final void rule__SEMVER_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3637:1: ( ( RULE_LETTER_S ) )
            // InternalSemver.g:3638:1: ( RULE_LETTER_S )
            {
            // InternalSemver.g:3638:1: ( RULE_LETTER_S )
            // InternalSemver.g:3639:2: RULE_LETTER_S
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
    // InternalSemver.g:3648:1: rule__SEMVER_TAG__Group__1 : rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 ;
    public final void rule__SEMVER_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3652:1: ( rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 )
            // InternalSemver.g:3653:2: rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2
            {
            pushFollow(FOLLOW_27);
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
    // InternalSemver.g:3660:1: rule__SEMVER_TAG__Group__1__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3664:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3665:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3665:1: ( RULE_LETTER_E )
            // InternalSemver.g:3666:2: RULE_LETTER_E
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
    // InternalSemver.g:3675:1: rule__SEMVER_TAG__Group__2 : rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 ;
    public final void rule__SEMVER_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3679:1: ( rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 )
            // InternalSemver.g:3680:2: rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3
            {
            pushFollow(FOLLOW_28);
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
    // InternalSemver.g:3687:1: rule__SEMVER_TAG__Group__2__Impl : ( RULE_LETTER_M ) ;
    public final void rule__SEMVER_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3691:1: ( ( RULE_LETTER_M ) )
            // InternalSemver.g:3692:1: ( RULE_LETTER_M )
            {
            // InternalSemver.g:3692:1: ( RULE_LETTER_M )
            // InternalSemver.g:3693:2: RULE_LETTER_M
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
    // InternalSemver.g:3702:1: rule__SEMVER_TAG__Group__3 : rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 ;
    public final void rule__SEMVER_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3706:1: ( rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 )
            // InternalSemver.g:3707:2: rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4
            {
            pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3714:1: rule__SEMVER_TAG__Group__3__Impl : ( RULE_LETTER_V ) ;
    public final void rule__SEMVER_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3718:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:3719:1: ( RULE_LETTER_V )
            {
            // InternalSemver.g:3719:1: ( RULE_LETTER_V )
            // InternalSemver.g:3720:2: RULE_LETTER_V
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
    // InternalSemver.g:3729:1: rule__SEMVER_TAG__Group__4 : rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 ;
    public final void rule__SEMVER_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3733:1: ( rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 )
            // InternalSemver.g:3734:2: rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5
            {
            pushFollow(FOLLOW_29);
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
    // InternalSemver.g:3741:1: rule__SEMVER_TAG__Group__4__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3745:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3746:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3746:1: ( RULE_LETTER_E )
            // InternalSemver.g:3747:2: RULE_LETTER_E
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
    // InternalSemver.g:3756:1: rule__SEMVER_TAG__Group__5 : rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 ;
    public final void rule__SEMVER_TAG__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3760:1: ( rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 )
            // InternalSemver.g:3761:2: rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6
            {
            pushFollow(FOLLOW_7);
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
    // InternalSemver.g:3768:1: rule__SEMVER_TAG__Group__5__Impl : ( RULE_LETTER_R ) ;
    public final void rule__SEMVER_TAG__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3772:1: ( ( RULE_LETTER_R ) )
            // InternalSemver.g:3773:1: ( RULE_LETTER_R )
            {
            // InternalSemver.g:3773:1: ( RULE_LETTER_R )
            // InternalSemver.g:3774:2: RULE_LETTER_R
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
    // InternalSemver.g:3783:1: rule__SEMVER_TAG__Group__6 : rule__SEMVER_TAG__Group__6__Impl ;
    public final void rule__SEMVER_TAG__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3787:1: ( rule__SEMVER_TAG__Group__6__Impl )
            // InternalSemver.g:3788:2: rule__SEMVER_TAG__Group__6__Impl
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
    // InternalSemver.g:3794:1: rule__SEMVER_TAG__Group__6__Impl : ( ':' ) ;
    public final void rule__SEMVER_TAG__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3798:1: ( ( ':' ) )
            // InternalSemver.g:3799:1: ( ':' )
            {
            // InternalSemver.g:3799:1: ( ':' )
            // InternalSemver.g:3800:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6()); 
            }
            match(input,41,FOLLOW_2); if (state.failed) return ;
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


    // $ANTLR start "rule__URL_PROTOCOL__Group__0"
    // InternalSemver.g:3810:1: rule__URL_PROTOCOL__Group__0 : rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 ;
    public final void rule__URL_PROTOCOL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3814:1: ( rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 )
            // InternalSemver.g:3815:2: rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1
            {
            pushFollow(FOLLOW_30);
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
    // InternalSemver.g:3822:1: rule__URL_PROTOCOL__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__URL_PROTOCOL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3826:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:3827:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:3827:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:3828:2: ruleLETTER_NO_VX
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
    // InternalSemver.g:3837:1: rule__URL_PROTOCOL__Group__1 : rule__URL_PROTOCOL__Group__1__Impl ;
    public final void rule__URL_PROTOCOL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3841:1: ( rule__URL_PROTOCOL__Group__1__Impl )
            // InternalSemver.g:3842:2: rule__URL_PROTOCOL__Group__1__Impl
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
    // InternalSemver.g:3848:1: rule__URL_PROTOCOL__Group__1__Impl : ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) ;
    public final void rule__URL_PROTOCOL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3852:1: ( ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) )
            // InternalSemver.g:3853:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3853:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            // InternalSemver.g:3854:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            {
            // InternalSemver.g:3854:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) )
            // InternalSemver.g:3855:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3856:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            // InternalSemver.g:3856:4: rule__URL_PROTOCOL__Alternatives_1
            {
            pushFollow(FOLLOW_31);
            rule__URL_PROTOCOL__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3859:2: ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            // InternalSemver.g:3860:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3861:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==RULE_LETTER_X||(LA45_0>=RULE_LETTER_V && LA45_0<=RULE_LETTER_OTHER)||LA45_0==40) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSemver.g:3861:4: rule__URL_PROTOCOL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_31);
            	    rule__URL_PROTOCOL__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop45;
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
    // InternalSemver.g:3871:1: rule__URL__Group__0 : rule__URL__Group__0__Impl rule__URL__Group__1 ;
    public final void rule__URL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3875:1: ( rule__URL__Group__0__Impl rule__URL__Group__1 )
            // InternalSemver.g:3876:2: rule__URL__Group__0__Impl rule__URL__Group__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:3883:1: rule__URL__Group__0__Impl : ( ( rule__URL__Alternatives_0 )* ) ;
    public final void rule__URL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3887:1: ( ( ( rule__URL__Alternatives_0 )* ) )
            // InternalSemver.g:3888:1: ( ( rule__URL__Alternatives_0 )* )
            {
            // InternalSemver.g:3888:1: ( ( rule__URL__Alternatives_0 )* )
            // InternalSemver.g:3889:2: ( rule__URL__Alternatives_0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:3890:2: ( rule__URL__Alternatives_0 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=RULE_DIGITS && LA46_0<=RULE_LETTER_X)||(LA46_0>=RULE_LETTER_V && LA46_0<=RULE_LETTER_OTHER)||(LA46_0>=38 && LA46_0<=39)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSemver.g:3890:3: rule__URL__Alternatives_0
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL__Alternatives_0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop46;
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
    // InternalSemver.g:3898:1: rule__URL__Group__1 : rule__URL__Group__1__Impl rule__URL__Group__2 ;
    public final void rule__URL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3902:1: ( rule__URL__Group__1__Impl rule__URL__Group__2 )
            // InternalSemver.g:3903:2: rule__URL__Group__1__Impl rule__URL__Group__2
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:3910:1: rule__URL__Group__1__Impl : ( ( rule__URL__Alternatives_1 ) ) ;
    public final void rule__URL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3914:1: ( ( ( rule__URL__Alternatives_1 ) ) )
            // InternalSemver.g:3915:1: ( ( rule__URL__Alternatives_1 ) )
            {
            // InternalSemver.g:3915:1: ( ( rule__URL__Alternatives_1 ) )
            // InternalSemver.g:3916:2: ( rule__URL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3917:2: ( rule__URL__Alternatives_1 )
            // InternalSemver.g:3917:3: rule__URL__Alternatives_1
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
    // InternalSemver.g:3925:1: rule__URL__Group__2 : rule__URL__Group__2__Impl ;
    public final void rule__URL__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3929:1: ( rule__URL__Group__2__Impl )
            // InternalSemver.g:3930:2: rule__URL__Group__2__Impl
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
    // InternalSemver.g:3936:1: rule__URL__Group__2__Impl : ( ( rule__URL__Alternatives_2 )* ) ;
    public final void rule__URL__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3940:1: ( ( ( rule__URL__Alternatives_2 )* ) )
            // InternalSemver.g:3941:1: ( ( rule__URL__Alternatives_2 )* )
            {
            // InternalSemver.g:3941:1: ( ( rule__URL__Alternatives_2 )* )
            // InternalSemver.g:3942:2: ( rule__URL__Alternatives_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:3943:2: ( rule__URL__Alternatives_2 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( ((LA47_0>=RULE_DIGITS && LA47_0<=RULE_LETTER_X)||(LA47_0>=RULE_LETTER_V && LA47_0<=RULE_LETTER_OTHER)||(LA47_0>=35 && LA47_0<=39)||LA47_0==41) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSemver.g:3943:3: rule__URL__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_32);
            	    rule__URL__Alternatives_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop47;
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
    // InternalSemver.g:3952:1: rule__URL_NO_VX__Group__0 : rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1 ;
    public final void rule__URL_NO_VX__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3956:1: ( rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1 )
            // InternalSemver.g:3957:2: rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:3964:1: rule__URL_NO_VX__Group__0__Impl : ( ( rule__URL_NO_VX__Alternatives_0 ) ) ;
    public final void rule__URL_NO_VX__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3968:1: ( ( ( rule__URL_NO_VX__Alternatives_0 ) ) )
            // InternalSemver.g:3969:1: ( ( rule__URL_NO_VX__Alternatives_0 ) )
            {
            // InternalSemver.g:3969:1: ( ( rule__URL_NO_VX__Alternatives_0 ) )
            // InternalSemver.g:3970:2: ( rule__URL_NO_VX__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:3971:2: ( rule__URL_NO_VX__Alternatives_0 )
            // InternalSemver.g:3971:3: rule__URL_NO_VX__Alternatives_0
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
    // InternalSemver.g:3979:1: rule__URL_NO_VX__Group__1 : rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2 ;
    public final void rule__URL_NO_VX__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3983:1: ( rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2 )
            // InternalSemver.g:3984:2: rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:3991:1: rule__URL_NO_VX__Group__1__Impl : ( ( rule__URL_NO_VX__Alternatives_1 )* ) ;
    public final void rule__URL_NO_VX__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3995:1: ( ( ( rule__URL_NO_VX__Alternatives_1 )* ) )
            // InternalSemver.g:3996:1: ( ( rule__URL_NO_VX__Alternatives_1 )* )
            {
            // InternalSemver.g:3996:1: ( ( rule__URL_NO_VX__Alternatives_1 )* )
            // InternalSemver.g:3997:2: ( rule__URL_NO_VX__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3998:2: ( rule__URL_NO_VX__Alternatives_1 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=RULE_DIGITS && LA48_0<=RULE_LETTER_X)||(LA48_0>=RULE_LETTER_V && LA48_0<=RULE_LETTER_OTHER)||(LA48_0>=38 && LA48_0<=39)) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalSemver.g:3998:3: rule__URL_NO_VX__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL_NO_VX__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop48;
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
    // InternalSemver.g:4006:1: rule__URL_NO_VX__Group__2 : rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3 ;
    public final void rule__URL_NO_VX__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4010:1: ( rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3 )
            // InternalSemver.g:4011:2: rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3
            {
            pushFollow(FOLLOW_8);
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
    // InternalSemver.g:4018:1: rule__URL_NO_VX__Group__2__Impl : ( ( rule__URL_NO_VX__Alternatives_2 ) ) ;
    public final void rule__URL_NO_VX__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4022:1: ( ( ( rule__URL_NO_VX__Alternatives_2 ) ) )
            // InternalSemver.g:4023:1: ( ( rule__URL_NO_VX__Alternatives_2 ) )
            {
            // InternalSemver.g:4023:1: ( ( rule__URL_NO_VX__Alternatives_2 ) )
            // InternalSemver.g:4024:2: ( rule__URL_NO_VX__Alternatives_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:4025:2: ( rule__URL_NO_VX__Alternatives_2 )
            // InternalSemver.g:4025:3: rule__URL_NO_VX__Alternatives_2
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
    // InternalSemver.g:4033:1: rule__URL_NO_VX__Group__3 : rule__URL_NO_VX__Group__3__Impl ;
    public final void rule__URL_NO_VX__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4037:1: ( rule__URL_NO_VX__Group__3__Impl )
            // InternalSemver.g:4038:2: rule__URL_NO_VX__Group__3__Impl
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
    // InternalSemver.g:4044:1: rule__URL_NO_VX__Group__3__Impl : ( ( rule__URL_NO_VX__Alternatives_3 )* ) ;
    public final void rule__URL_NO_VX__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4048:1: ( ( ( rule__URL_NO_VX__Alternatives_3 )* ) )
            // InternalSemver.g:4049:1: ( ( rule__URL_NO_VX__Alternatives_3 )* )
            {
            // InternalSemver.g:4049:1: ( ( rule__URL_NO_VX__Alternatives_3 )* )
            // InternalSemver.g:4050:2: ( rule__URL_NO_VX__Alternatives_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_3()); 
            }
            // InternalSemver.g:4051:2: ( rule__URL_NO_VX__Alternatives_3 )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( ((LA49_0>=RULE_DIGITS && LA49_0<=RULE_LETTER_X)||(LA49_0>=RULE_LETTER_V && LA49_0<=RULE_LETTER_OTHER)||(LA49_0>=35 && LA49_0<=39)||LA49_0==41) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalSemver.g:4051:3: rule__URL_NO_VX__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_32);
            	    rule__URL_NO_VX__Alternatives_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop49;
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
    // InternalSemver.g:4060:1: rule__TAG__Group__0 : rule__TAG__Group__0__Impl rule__TAG__Group__1 ;
    public final void rule__TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4064:1: ( rule__TAG__Group__0__Impl rule__TAG__Group__1 )
            // InternalSemver.g:4065:2: rule__TAG__Group__0__Impl rule__TAG__Group__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:4072:1: rule__TAG__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4076:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:4077:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:4077:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:4078:2: ruleLETTER_NO_VX
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
    // InternalSemver.g:4087:1: rule__TAG__Group__1 : rule__TAG__Group__1__Impl ;
    public final void rule__TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4091:1: ( rule__TAG__Group__1__Impl )
            // InternalSemver.g:4092:2: rule__TAG__Group__1__Impl
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
    // InternalSemver.g:4098:1: rule__TAG__Group__1__Impl : ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) ;
    public final void rule__TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4102:1: ( ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) )
            // InternalSemver.g:4103:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            {
            // InternalSemver.g:4103:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            // InternalSemver.g:4104:2: ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* )
            {
            // InternalSemver.g:4104:2: ( ( rule__TAG__Alternatives_1 ) )
            // InternalSemver.g:4105:3: ( rule__TAG__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4106:3: ( rule__TAG__Alternatives_1 )
            // InternalSemver.g:4106:4: rule__TAG__Alternatives_1
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

            // InternalSemver.g:4109:2: ( ( rule__TAG__Alternatives_1 )* )
            // InternalSemver.g:4110:3: ( rule__TAG__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4111:3: ( rule__TAG__Alternatives_1 )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=RULE_DIGITS && LA50_0<=RULE_LETTER_X)||(LA50_0>=RULE_LETTER_V && LA50_0<=RULE_LETTER_OTHER)||LA50_0==38) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalSemver.g:4111:4: rule__TAG__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__TAG__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop50;
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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0"
    // InternalSemver.g:4121:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4125:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 )
            // InternalSemver.g:4126:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1
            {
            pushFollow(FOLLOW_6);
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
    // InternalSemver.g:4133:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl : ( RULE_DIGITS ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4137:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:4138:1: ( RULE_DIGITS )
            {
            // InternalSemver.g:4138:1: ( RULE_DIGITS )
            // InternalSemver.g:4139:2: RULE_DIGITS
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
    // InternalSemver.g:4148:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4152:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl )
            // InternalSemver.g:4153:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl
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
    // InternalSemver.g:4159:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl : ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4163:1: ( ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) )
            // InternalSemver.g:4164:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            {
            // InternalSemver.g:4164:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            // InternalSemver.g:4165:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            {
            // InternalSemver.g:4165:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) )
            // InternalSemver.g:4166:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4167:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            // InternalSemver.g:4167:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
            {
            pushFollow(FOLLOW_3);
            rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:4170:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            // InternalSemver.g:4171:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4172:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( ((LA51_0>=RULE_DIGITS && LA51_0<=RULE_LETTER_X)||(LA51_0>=RULE_LETTER_V && LA51_0<=RULE_LETTER_OTHER)||LA51_0==38) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSemver.g:4172:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl"


    // $ANTLR start "rule__LocalPathVersionRequirement__LocalPathAssignment_1"
    // InternalSemver.g:4182:1: rule__LocalPathVersionRequirement__LocalPathAssignment_1 : ( rulePATH ) ;
    public final void rule__LocalPathVersionRequirement__LocalPathAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4186:1: ( ( rulePATH ) )
            // InternalSemver.g:4187:2: ( rulePATH )
            {
            // InternalSemver.g:4187:2: ( rulePATH )
            // InternalSemver.g:4188:3: rulePATH
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
    // InternalSemver.g:4197:1: rule__URLVersionRequirement__ProtocolAssignment_0 : ( ruleURL_PROTOCOL ) ;
    public final void rule__URLVersionRequirement__ProtocolAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4201:1: ( ( ruleURL_PROTOCOL ) )
            // InternalSemver.g:4202:2: ( ruleURL_PROTOCOL )
            {
            // InternalSemver.g:4202:2: ( ruleURL_PROTOCOL )
            // InternalSemver.g:4203:3: ruleURL_PROTOCOL
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
    // InternalSemver.g:4212:1: rule__URLVersionRequirement__UrlAssignment_2 : ( ruleURL ) ;
    public final void rule__URLVersionRequirement__UrlAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4216:1: ( ( ruleURL ) )
            // InternalSemver.g:4217:2: ( ruleURL )
            {
            // InternalSemver.g:4217:2: ( ruleURL )
            // InternalSemver.g:4218:3: ruleURL
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
    // InternalSemver.g:4227:1: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 : ( ruleURLVersionSpecifier ) ;
    public final void rule__URLVersionRequirement__VersionSpecifierAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4231:1: ( ( ruleURLVersionSpecifier ) )
            // InternalSemver.g:4232:2: ( ruleURLVersionSpecifier )
            {
            // InternalSemver.g:4232:2: ( ruleURLVersionSpecifier )
            // InternalSemver.g:4233:3: ruleURLVersionSpecifier
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
    // InternalSemver.g:4242:1: rule__URLVersionSpecifier__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4246:1: ( ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
            // InternalSemver.g:4247:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            {
            // InternalSemver.g:4247:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            // InternalSemver.g:4248:3: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
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
    // InternalSemver.g:4257:1: rule__URLVersionSpecifier__CommitISHAssignment_2_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4261:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4262:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4262:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4263:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4272:1: rule__URLSemver__WithSemverTagAssignment_1 : ( ruleSEMVER_TAG ) ;
    public final void rule__URLSemver__WithSemverTagAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4276:1: ( ( ruleSEMVER_TAG ) )
            // InternalSemver.g:4277:2: ( ruleSEMVER_TAG )
            {
            // InternalSemver.g:4277:2: ( ruleSEMVER_TAG )
            // InternalSemver.g:4278:3: ruleSEMVER_TAG
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
    // InternalSemver.g:4287:1: rule__URLSemver__SimpleVersionAssignment_2 : ( ruleSimpleVersion ) ;
    public final void rule__URLSemver__SimpleVersionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4291:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4292:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4292:2: ( ruleSimpleVersion )
            // InternalSemver.g:4293:3: ruleSimpleVersion
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


    // $ANTLR start "rule__TagVersionRequirement__TagNameAssignment"
    // InternalSemver.g:4302:1: rule__TagVersionRequirement__TagNameAssignment : ( ruleTAG ) ;
    public final void rule__TagVersionRequirement__TagNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4306:1: ( ( ruleTAG ) )
            // InternalSemver.g:4307:2: ( ruleTAG )
            {
            // InternalSemver.g:4307:2: ( ruleTAG )
            // InternalSemver.g:4308:3: ruleTAG
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
    // InternalSemver.g:4317:1: rule__GitHubVersionRequirement__GithubUrlAssignment_0 : ( ruleURL_NO_VX ) ;
    public final void rule__GitHubVersionRequirement__GithubUrlAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4321:1: ( ( ruleURL_NO_VX ) )
            // InternalSemver.g:4322:2: ( ruleURL_NO_VX )
            {
            // InternalSemver.g:4322:2: ( ruleURL_NO_VX )
            // InternalSemver.g:4323:3: ruleURL_NO_VX
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
    // InternalSemver.g:4332:1: rule__GitHubVersionRequirement__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__GitHubVersionRequirement__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4336:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4337:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4337:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4338:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4347:1: rule__VersionRangeSetRequirement__RangesAssignment_1_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4351:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4352:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4352:2: ( ruleVersionRange )
            // InternalSemver.g:4353:3: ruleVersionRange
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
    // InternalSemver.g:4362:1: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4366:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4367:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4367:2: ( ruleVersionRange )
            // InternalSemver.g:4368:3: ruleVersionRange
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
    // InternalSemver.g:4377:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4381:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4382:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4382:2: ( ruleVersionNumber )
            // InternalSemver.g:4383:3: ruleVersionNumber
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
    // InternalSemver.g:4392:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4396:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4397:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4397:2: ( ruleVersionNumber )
            // InternalSemver.g:4398:3: ruleVersionNumber
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
    // InternalSemver.g:4407:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4411:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4412:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4412:2: ( ruleSimpleVersion )
            // InternalSemver.g:4413:3: ruleSimpleVersion
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
    // InternalSemver.g:4422:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4426:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4427:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4427:2: ( ruleSimpleVersion )
            // InternalSemver.g:4428:3: ruleSimpleVersion
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
    // InternalSemver.g:4437:1: rule__SimpleVersion__ComparatorsAssignment_0_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4441:1: ( ( ruleVersionComparator ) )
            // InternalSemver.g:4442:2: ( ruleVersionComparator )
            {
            // InternalSemver.g:4442:2: ( ruleVersionComparator )
            // InternalSemver.g:4443:3: ruleVersionComparator
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
    // InternalSemver.g:4452:1: rule__SimpleVersion__WithLetterVAssignment_1 : ( RULE_LETTER_V ) ;
    public final void rule__SimpleVersion__WithLetterVAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4456:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:4457:2: ( RULE_LETTER_V )
            {
            // InternalSemver.g:4457:2: ( RULE_LETTER_V )
            // InternalSemver.g:4458:3: RULE_LETTER_V
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
    // InternalSemver.g:4467:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4471:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4472:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4472:2: ( ruleVersionNumber )
            // InternalSemver.g:4473:3: ruleVersionNumber
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
    // InternalSemver.g:4482:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4486:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4487:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4487:2: ( ruleVersionPart )
            // InternalSemver.g:4488:3: ruleVersionPart
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
    // InternalSemver.g:4497:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4501:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4502:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4502:2: ( ruleVersionPart )
            // InternalSemver.g:4503:3: ruleVersionPart
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
    // InternalSemver.g:4512:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4516:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4517:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4517:2: ( ruleVersionPart )
            // InternalSemver.g:4518:3: ruleVersionPart
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
    // InternalSemver.g:4527:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4531:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4532:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4532:2: ( ruleVersionPart )
            // InternalSemver.g:4533:3: ruleVersionPart
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
    // InternalSemver.g:4542:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4546:1: ( ( ruleQualifier ) )
            // InternalSemver.g:4547:2: ( ruleQualifier )
            {
            // InternalSemver.g:4547:2: ( ruleQualifier )
            // InternalSemver.g:4548:3: ruleQualifier
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
    // InternalSemver.g:4557:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4561:1: ( ( ruleWILDCARD ) )
            // InternalSemver.g:4562:2: ( ruleWILDCARD )
            {
            // InternalSemver.g:4562:2: ( ruleWILDCARD )
            // InternalSemver.g:4563:3: ruleWILDCARD
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
    // InternalSemver.g:4572:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4576:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:4577:2: ( RULE_DIGITS )
            {
            // InternalSemver.g:4577:2: ( RULE_DIGITS )
            // InternalSemver.g:4578:3: RULE_DIGITS
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
    // InternalSemver.g:4587:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4591:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4592:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4592:2: ( ruleQualifierTag )
            // InternalSemver.g:4593:3: ruleQualifierTag
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
    // InternalSemver.g:4602:1: rule__Qualifier__BuildMetadataAssignment_0_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_0_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4606:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4607:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4607:2: ( ruleQualifierTag )
            // InternalSemver.g:4608:3: ruleQualifierTag
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
    // InternalSemver.g:4617:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4621:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4622:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4622:2: ( ruleQualifierTag )
            // InternalSemver.g:4623:3: ruleQualifierTag
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
    // InternalSemver.g:4632:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4636:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4637:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4637:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4638:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4647:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4651:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4652:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4652:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4653:3: ruleALPHA_NUMERIC_CHARS
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

    // $ANTLR start synpred4_InternalSemver
    public final void synpred4_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:800:2: ( ( ( ruleLocalPathVersionRequirement ) ) )
        // InternalSemver.g:800:2: ( ( ruleLocalPathVersionRequirement ) )
        {
        // InternalSemver.g:800:2: ( ( ruleLocalPathVersionRequirement ) )
        // InternalSemver.g:801:3: ( ruleLocalPathVersionRequirement )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
        }
        // InternalSemver.g:802:3: ( ruleLocalPathVersionRequirement )
        // InternalSemver.g:802:4: ruleLocalPathVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleLocalPathVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred4_InternalSemver

    // $ANTLR start synpred5_InternalSemver
    public final void synpred5_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:821:2: ( ( ( ruleURLVersionRequirement ) ) )
        // InternalSemver.g:821:2: ( ( ruleURLVersionRequirement ) )
        {
        // InternalSemver.g:821:2: ( ( ruleURLVersionRequirement ) )
        // InternalSemver.g:822:3: ( ruleURLVersionRequirement )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0()); 
        }
        // InternalSemver.g:823:3: ( ruleURLVersionRequirement )
        // InternalSemver.g:823:4: ruleURLVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleURLVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred5_InternalSemver

    // $ANTLR start synpred6_InternalSemver
    public final void synpred6_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:827:2: ( ( ruleGitHubVersionRequirement ) )
        // InternalSemver.g:827:2: ( ruleGitHubVersionRequirement )
        {
        // InternalSemver.g:827:2: ( ruleGitHubVersionRequirement )
        // InternalSemver.g:828:3: ruleGitHubVersionRequirement
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1()); 
        }
        pushFollow(FOLLOW_2);
        ruleGitHubVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred6_InternalSemver

    // $ANTLR start synpred7_InternalSemver
    public final void synpred7_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:848:2: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) )
        // InternalSemver.g:848:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        {
        // InternalSemver.g:848:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        // InternalSemver.g:849:3: ( rule__URLVersionSpecifier__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
        }
        // InternalSemver.g:850:3: ( rule__URLVersionSpecifier__Group_0__0 )
        // InternalSemver.g:850:4: rule__URLVersionSpecifier__Group_0__0
        {
        pushFollow(FOLLOW_2);
        rule__URLVersionSpecifier__Group_0__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred7_InternalSemver

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


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA7 dfa7 = new DFA7(this);
    static final String dfa_1s = "\30\uffff";
    static final String dfa_2s = "\3\uffff\4\2\21\uffff";
    static final String dfa_3s = "\1\10\1\4\1\uffff\4\4\20\0\1\uffff";
    static final String dfa_4s = "\1\47\1\51\1\uffff\3\51\1\61\20\0\1\uffff";
    static final String dfa_5s = "\2\uffff\1\2\24\uffff\1\1";
    static final String dfa_6s = "\7\uffff\1\15\1\10\1\5\1\14\1\17\1\12\1\0\1\3\1\16\1\11\1\13\1\6\1\7\1\1\1\4\1\2\1\uffff}>";
    static final String[] dfa_7s = {
            "\3\2\1\1\4\2\26\uffff\2\2",
            "\2\2\1\uffff\5\2\1\3\3\2\23\uffff\7\2",
            "",
            "\2\2\1\uffff\6\2\1\4\3\2\22\uffff\7\2",
            "\2\2\1\uffff\7\2\1\5\2\2\22\uffff\7\2",
            "\2\2\1\uffff\12\2\22\uffff\6\2\1\6",
            "\1\14\1\16\1\uffff\1\15\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\2\22\uffff\1\7\1\10\1\11\1\12\1\13\1\uffff\1\2\7\uffff\1\2",
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
            return "795:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ( rule__NPMVersionRequirement__Alternatives_1_0_1 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_13 = input.LA(1);

                         
                        int index4_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_13);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_20 = input.LA(1);

                         
                        int index4_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_20);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_22 = input.LA(1);

                         
                        int index4_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_22);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_14 = input.LA(1);

                         
                        int index4_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_14);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA4_21 = input.LA(1);

                         
                        int index4_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_21);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA4_9 = input.LA(1);

                         
                        int index4_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_9);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA4_18 = input.LA(1);

                         
                        int index4_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_18);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA4_19 = input.LA(1);

                         
                        int index4_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_19);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA4_16 = input.LA(1);

                         
                        int index4_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_16);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA4_12 = input.LA(1);

                         
                        int index4_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA4_17 = input.LA(1);

                         
                        int index4_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA4_10 = input.LA(1);

                         
                        int index4_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_10);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_7);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA4_15 = input.LA(1);

                         
                        int index4_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_15);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA4_11 = input.LA(1);

                         
                        int index4_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_11);
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
    static final String dfa_8s = "\102\uffff";
    static final String dfa_9s = "\12\uffff\12\44\1\uffff\2\44\1\11\14\44\1\uffff\12\44\17\11\4\uffff";
    static final String dfa_10s = "\1\10\10\4\1\uffff\12\4\1\uffff\17\4\1\uffff\31\4\4\0";
    static final String dfa_11s = "\1\47\10\51\1\uffff\12\51\1\uffff\2\51\1\61\14\51\1\uffff\12\51\17\61\4\0";
    static final String dfa_12s = "\11\uffff\1\2\12\uffff\1\1\17\uffff\1\3\35\uffff";
    static final String dfa_13s = "\76\uffff\1\3\1\0\1\1\1\2}>";
    static final String[] dfa_14s = {
            "\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\26\uffff\2\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "\1\26\1\13\1\uffff\1\12\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\23\uffff\3\11\1\25\1\11\1\24\1\11",
            "",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\2\11\1\uffff\12\11\22\uffff\1\57\4\11\1\uffff\1\11\7\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\3\11\1\30\1\11\1\24\1\27",
            "",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\3\11\1\30\1\11\1\uffff\1\11",
            "\2\11\1\uffff\12\11\22\uffff\1\60\4\11\1\uffff\1\11\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
            "\1\63\1\65\1\uffff\1\64\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\11\22\uffff\1\76\1\77\1\101\1\61\1\62\1\uffff\1\100\7\uffff\1\11",
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

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "816:1: rule__NPMVersionRequirement__Alternatives_1_0_1 : ( ( ( ruleURLVersionRequirement ) ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_63 = input.LA(1);

                         
                        int index5_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 20;}

                        else if ( (synpred6_InternalSemver()) ) {s = 9;}

                         
                        input.seek(index5_63);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_64 = input.LA(1);

                         
                        int index5_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 20;}

                        else if ( (synpred6_InternalSemver()) ) {s = 9;}

                         
                        input.seek(index5_64);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_65 = input.LA(1);

                         
                        int index5_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 20;}

                        else if ( (synpred6_InternalSemver()) ) {s = 9;}

                         
                        input.seek(index5_65);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_62 = input.LA(1);

                         
                        int index5_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 20;}

                        else if ( (synpred6_InternalSemver()) ) {s = 9;}

                         
                        input.seek(index5_62);
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
    static final String dfa_15s = "\17\uffff";
    static final String dfa_16s = "\1\uffff\1\6\1\uffff\1\6\3\uffff\1\6\3\uffff\4\6";
    static final String dfa_17s = "\2\4\1\uffff\1\4\1\0\1\4\1\uffff\1\4\2\0\1\uffff\4\4";
    static final String dfa_18s = "\1\60\1\46\1\uffff\1\46\1\0\1\46\1\uffff\1\46\2\0\1\uffff\3\46\1\51";
    static final String dfa_19s = "\2\uffff\1\1\3\uffff\1\3\3\uffff\1\2\4\uffff";
    static final String dfa_20s = "\4\uffff\1\3\1\2\2\uffff\1\1\1\0\5\uffff}>";
    static final String[] dfa_21s = {
            "\1\5\1\4\1\2\1\3\1\1\7\6\26\uffff\1\6\3\uffff\7\2",
            "\2\6\1\uffff\7\6\1\7\2\6\25\uffff\1\6",
            "",
            "\1\10\1\11\1\2\12\6\25\uffff\1\6",
            "\1\uffff",
            "\2\12\1\uffff\11\12\26\uffff\1\12",
            "",
            "\2\6\1\uffff\2\6\1\13\7\6\25\uffff\1\6",
            "\1\uffff",
            "\1\uffff",
            "",
            "\2\6\1\uffff\1\14\11\6\25\uffff\1\6",
            "\2\6\1\uffff\7\6\1\15\2\6\25\uffff\1\6",
            "\2\6\1\uffff\3\6\1\16\6\6\25\uffff\1\6",
            "\2\6\1\uffff\12\6\25\uffff\1\6\2\uffff\1\2"
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "843:1: rule__URLVersionSpecifier__Alternatives : ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_9 = input.LA(1);

                         
                        int index6_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index6_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA6_8 = input.LA(1);

                         
                        int index6_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index6_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA6_5 = input.LA(1);

                         
                        int index6_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_5>=RULE_DIGITS && LA6_5<=RULE_LETTER_X)||(LA6_5>=RULE_LETTER_V && LA6_5<=RULE_LETTER_OTHER)||LA6_5==38) ) {s = 10;}

                        else if ( (synpred7_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index6_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA6_4 = input.LA(1);

                         
                        int index6_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index6_4);
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
    static final String dfa_22s = "\u00a9\uffff";
    static final String dfa_23s = "\2\uffff\3\1\4\uffff\33\1\2\uffff\14\1\2\uffff\14\1\1\uffff\47\1\1\uffff\30\1\1\uffff\47\1";
    static final String dfa_24s = "\1\4\1\uffff\3\20\4\4\3\20\30\4\1\uffff\34\4\3\20\112\4\3\20\30\4";
    static final String dfa_25s = "\1\60\1\uffff\3\62\1\6\2\46\34\62\1\uffff\1\6\14\62\2\46\14\62\1\46\47\62\1\6\30\62\1\46\47\62";
    static final String dfa_26s = "\1\uffff\1\1\42\uffff\1\2\u0084\uffff";
    static final String dfa_27s = "\u00a9\uffff}>";
    static final String[] dfa_28s = {
            "\1\4\1\2\1\3\1\1\42\uffff\7\1",
            "",
            "\1\10\23\uffff\1\5\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\5\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\5\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\13\1\11\1\12",
            "\1\15\1\17\1\uffff\1\16\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\26\uffff\1\14",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\26\uffff\1\30",
            "\4\1\36\uffff\1\44\3\uffff\7\1\1\uffff\1\1",
            "\1\10\23\uffff\1\45\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\45\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\45\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "",
            "\1\103\1\101\1\102",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\47\1\51\1\uffff\1\50\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\10\23\uffff\1\62\1\uffff\1\46\1\uffff\1\63\11\uffff\1\1",
            "\1\105\1\107\1\uffff\1\106\1\110\1\111\1\112\1\113\1\114\1\115\1\116\1\117\26\uffff\1\104",
            "\1\121\1\123\1\uffff\1\122\1\124\1\125\1\126\1\127\1\130\1\131\1\132\1\133\26\uffff\1\120",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\65\1\67\1\uffff\1\66\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\10\23\uffff\1\100\1\uffff\1\64\13\uffff\1\1",
            "\1\135\1\137\1\uffff\1\136\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147\26\uffff\1\134",
            "\1\10\23\uffff\1\150\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\150\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\150\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0090\1\u008e\1\u008f",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\152\1\154\1\uffff\1\153\1\155\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\10\23\uffff\1\62\1\uffff\1\151\1\uffff\1\63\11\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\166\1\170\1\uffff\1\167\1\171\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\10\23\uffff\1\u0081\1\uffff\1\165\13\uffff\1\1",
            "\1\u0092\1\u0094\1\uffff\1\u0093\1\u0095\1\u0096\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\26\uffff\1\u0091",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\u0083\1\u0085\1\uffff\1\u0084\1\u0086\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\10\23\uffff\1\100\1\uffff\1\u0082\13\uffff\1\1",
            "\1\10\23\uffff\1\150\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\150\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\10\23\uffff\1\150\1\uffff\1\6\1\uffff\1\7\11\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1",
            "\1\u009e\1\u00a0\1\uffff\1\u009f\1\u00a1\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\10\23\uffff\1\u0081\1\uffff\1\u009d\13\uffff\1\1"
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
            return "870:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000F80000FFB2L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0001FC00000000F0L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000000F80000FFB0L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x000002F80000FFB0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0001FCF80000FFF0L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0001FC00000001F0L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0004000000010000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0004000000010002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0001FC00000100F0L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0001FC0000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000015000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x000001F80000FFB0L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x000001F80000FFB2L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x000002F80000FFB2L});

}