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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_DIGITS", "RULE_LETTER_X", "RULE_ASTERIX", "RULE_LETTER_V", "RULE_LETTER_S", "RULE_LETTER_M", "RULE_LETTER_R", "RULE_LETTER_F", "RULE_LETTER_I", "RULE_LETTER_L", "RULE_LETTER_E", "RULE_LETTER_OTHER", "RULE_WS", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'/'", "'.'", "'-'", "'+'", "':'", "'@'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'#'", "'||'"
    };
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

                if ( ((LA1_0>=RULE_DIGITS && LA1_0<=RULE_LETTER_X)||(LA1_0>=RULE_LETTER_V && LA1_0<=RULE_LETTER_OTHER)||(LA1_0>=35 && LA1_0<=37)) ) {
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

                if ( ((LA2_0>=RULE_DIGITS && LA2_0<=RULE_LETTER_X)||(LA2_0>=RULE_LETTER_V && LA2_0<=RULE_LETTER_OTHER)||LA2_0==37) ) {
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

            if ( (LA3_0==EOF||(LA3_0>=RULE_DIGITS && LA3_0<=RULE_LETTER_V)||LA3_0==RULE_WS||(LA3_0>=41 && LA3_0<=47)) ) {
                alt3=1;
            }
            else if ( ((LA3_0>=RULE_LETTER_S && LA3_0<=RULE_LETTER_OTHER)) ) {
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
    // InternalSemver.g:912:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:916:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt9=3;
            alt9 = dfa9.predict(input);
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
                case 3 :
                    // InternalSemver.g:929:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:929:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSemver.g:930:3: ( rule__Qualifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:931:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSemver.g:931:4: rule__Qualifier__Group_2__0
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


    // $ANTLR start "rule__PATH__Alternatives"
    // InternalSemver.g:939:1: rule__PATH__Alternatives : ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__PATH__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:943:1: ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt10=5;
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
            case RULE_DIGITS:
                {
                alt10=4;
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
                alt10=5;
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
                    // InternalSemver.g:944:2: ( '/' )
                    {
                    // InternalSemver.g:944:2: ( '/' )
                    // InternalSemver.g:945:3: '/'
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
                    // InternalSemver.g:950:2: ( '.' )
                    {
                    // InternalSemver.g:950:2: ( '.' )
                    // InternalSemver.g:951:3: '.'
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
                    // InternalSemver.g:956:2: ( '-' )
                    {
                    // InternalSemver.g:956:2: ( '-' )
                    // InternalSemver.g:957:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getHyphenMinusKeyword_2()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getHyphenMinusKeyword_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:962:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:962:2: ( RULE_DIGITS )
                    // InternalSemver.g:963:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_3()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:968:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:968:2: ( ruleLETTER )
                    // InternalSemver.g:969:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTERParserRuleCall_4()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTERParserRuleCall_4()); 
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
    // InternalSemver.g:978:1: rule__URL_PROTOCOL__Alternatives_1 : ( ( ruleLETTER ) | ( '+' ) );
    public final void rule__URL_PROTOCOL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:982:1: ( ( ruleLETTER ) | ( '+' ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_LETTER_X||(LA11_0>=RULE_LETTER_V && LA11_0<=RULE_LETTER_OTHER)) ) {
                alt11=1;
            }
            else if ( (LA11_0==38) ) {
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
                    // InternalSemver.g:983:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:983:2: ( ruleLETTER )
                    // InternalSemver.g:984:3: ruleLETTER
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
                    // InternalSemver.g:989:2: ( '+' )
                    {
                    // InternalSemver.g:989:2: ( '+' )
                    // InternalSemver.g:990:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1()); 
                    }
                    match(input,38,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:999:1: rule__URL__Alternatives_0 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1003:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt12=3;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt12=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt12=2;
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
                alt12=3;
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
                    // InternalSemver.g:1004:2: ( '-' )
                    {
                    // InternalSemver.g:1004:2: ( '-' )
                    // InternalSemver.g:1005:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1010:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1010:2: ( RULE_DIGITS )
                    // InternalSemver.g:1011:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_1()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1016:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1016:2: ( ruleLETTER )
                    // InternalSemver.g:1017:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_2()); 
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
    // InternalSemver.g:1026:1: rule__URL__Alternatives_1 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1030:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
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
            case 39:
                {
                alt13=3;
                }
                break;
            case 40:
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
                    // InternalSemver.g:1031:2: ( '/' )
                    {
                    // InternalSemver.g:1031:2: ( '/' )
                    // InternalSemver.g:1032:3: '/'
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
                    // InternalSemver.g:1037:2: ( '.' )
                    {
                    // InternalSemver.g:1037:2: ( '.' )
                    // InternalSemver.g:1038:3: '.'
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
                    // InternalSemver.g:1043:2: ( ':' )
                    {
                    // InternalSemver.g:1043:2: ( ':' )
                    // InternalSemver.g:1044:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_1_2()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1049:2: ( '@' )
                    {
                    // InternalSemver.g:1049:2: ( '@' )
                    // InternalSemver.g:1050:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_1_3()); 
                    }
                    match(input,40,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1059:1: rule__URL__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1063:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt14=7;
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
            case 39:
                {
                alt14=3;
                }
                break;
            case 40:
                {
                alt14=4;
                }
                break;
            case 37:
                {
                alt14=5;
                }
                break;
            case RULE_DIGITS:
                {
                alt14=6;
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
                alt14=7;
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
                    // InternalSemver.g:1064:2: ( '/' )
                    {
                    // InternalSemver.g:1064:2: ( '/' )
                    // InternalSemver.g:1065:3: '/'
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
                    // InternalSemver.g:1070:2: ( '.' )
                    {
                    // InternalSemver.g:1070:2: ( '.' )
                    // InternalSemver.g:1071:3: '.'
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
                    // InternalSemver.g:1076:2: ( ':' )
                    {
                    // InternalSemver.g:1076:2: ( ':' )
                    // InternalSemver.g:1077:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1082:2: ( '@' )
                    {
                    // InternalSemver.g:1082:2: ( '@' )
                    // InternalSemver.g:1083:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,40,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1088:2: ( '-' )
                    {
                    // InternalSemver.g:1088:2: ( '-' )
                    // InternalSemver.g:1089:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1094:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1094:2: ( RULE_DIGITS )
                    // InternalSemver.g:1095:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_5()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1100:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1100:2: ( ruleLETTER )
                    // InternalSemver.g:1101:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_6()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_6()); 
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


    // $ANTLR start "rule__URL_NO_VX__Alternatives_1"
    // InternalSemver.g:1110:1: rule__URL_NO_VX__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL_NO_VX__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1114:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt15=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt15=2;
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
                    // InternalSemver.g:1115:2: ( '-' )
                    {
                    // InternalSemver.g:1115:2: ( '-' )
                    // InternalSemver.g:1116:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1121:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1121:2: ( RULE_DIGITS )
                    // InternalSemver.g:1122:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1127:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1127:2: ( ruleLETTER )
                    // InternalSemver.g:1128:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_2()); 
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
    // InternalSemver.g:1137:1: rule__URL_NO_VX__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL_NO_VX__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1141:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt16=4;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt16=1;
                }
                break;
            case 36:
                {
                alt16=2;
                }
                break;
            case 39:
                {
                alt16=3;
                }
                break;
            case 40:
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
                    // InternalSemver.g:1142:2: ( '/' )
                    {
                    // InternalSemver.g:1142:2: ( '/' )
                    // InternalSemver.g:1143:3: '/'
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
                    // InternalSemver.g:1148:2: ( '.' )
                    {
                    // InternalSemver.g:1148:2: ( '.' )
                    // InternalSemver.g:1149:3: '.'
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
                    // InternalSemver.g:1154:2: ( ':' )
                    {
                    // InternalSemver.g:1154:2: ( ':' )
                    // InternalSemver.g:1155:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1160:2: ( '@' )
                    {
                    // InternalSemver.g:1160:2: ( '@' )
                    // InternalSemver.g:1161:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,40,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1170:1: rule__URL_NO_VX__Alternatives_3 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL_NO_VX__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1174:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt17=7;
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
            case 39:
                {
                alt17=3;
                }
                break;
            case 40:
                {
                alt17=4;
                }
                break;
            case 37:
                {
                alt17=5;
                }
                break;
            case RULE_DIGITS:
                {
                alt17=6;
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
                alt17=7;
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
                    // InternalSemver.g:1175:2: ( '/' )
                    {
                    // InternalSemver.g:1175:2: ( '/' )
                    // InternalSemver.g:1176:3: '/'
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
                    // InternalSemver.g:1181:2: ( '.' )
                    {
                    // InternalSemver.g:1181:2: ( '.' )
                    // InternalSemver.g:1182:3: '.'
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
                    // InternalSemver.g:1187:2: ( ':' )
                    {
                    // InternalSemver.g:1187:2: ( ':' )
                    // InternalSemver.g:1188:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1193:2: ( '@' )
                    {
                    // InternalSemver.g:1193:2: ( '@' )
                    // InternalSemver.g:1194:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); 
                    }
                    match(input,40,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1199:2: ( '-' )
                    {
                    // InternalSemver.g:1199:2: ( '-' )
                    // InternalSemver.g:1200:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1205:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1205:2: ( RULE_DIGITS )
                    // InternalSemver.g:1206:3: RULE_DIGITS
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_5()); 
                    }
                    match(input,RULE_DIGITS,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1211:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1211:2: ( ruleLETTER )
                    // InternalSemver.g:1212:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_6()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_6()); 
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
    // InternalSemver.g:1221:1: rule__TAG__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__TAG__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1225:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt18=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt18=2;
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
                    // InternalSemver.g:1226:2: ( '-' )
                    {
                    // InternalSemver.g:1226:2: ( '-' )
                    // InternalSemver.g:1227:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1232:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1232:2: ( RULE_DIGITS )
                    // InternalSemver.g:1233:3: RULE_DIGITS
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
                    // InternalSemver.g:1238:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1238:2: ( ruleLETTER )
                    // InternalSemver.g:1239:3: ruleLETTER
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
    // InternalSemver.g:1248:1: rule__ALPHA_NUMERIC_CHARS__Alternatives : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__ALPHA_NUMERIC_CHARS__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1252:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt19=3;
            switch ( input.LA(1) ) {
            case 37:
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
                    // InternalSemver.g:1253:2: ( '-' )
                    {
                    // InternalSemver.g:1253:2: ( '-' )
                    // InternalSemver.g:1254:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1259:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1259:2: ( RULE_DIGITS )
                    // InternalSemver.g:1260:3: RULE_DIGITS
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
                    // InternalSemver.g:1265:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1265:2: ( ruleLETTER )
                    // InternalSemver.g:1266:3: ruleLETTER
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
    // InternalSemver.g:1275:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1279:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt20=3;
            switch ( input.LA(1) ) {
            case 37:
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
                    // InternalSemver.g:1280:2: ( '-' )
                    {
                    // InternalSemver.g:1280:2: ( '-' )
                    // InternalSemver.g:1281:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1286:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1286:2: ( RULE_DIGITS )
                    // InternalSemver.g:1287:3: RULE_DIGITS
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
                    // InternalSemver.g:1292:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1292:2: ( ruleLETTER )
                    // InternalSemver.g:1293:3: ruleLETTER
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
    // InternalSemver.g:1302:1: rule__WILDCARD__Alternatives : ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) );
    public final void rule__WILDCARD__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1306:1: ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LETTER_X) ) {
                alt21=1;
            }
            else if ( (LA21_0==RULE_ASTERIX) ) {
                alt21=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalSemver.g:1307:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1307:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1308:3: RULE_LETTER_X
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
                    // InternalSemver.g:1313:2: ( RULE_ASTERIX )
                    {
                    // InternalSemver.g:1313:2: ( RULE_ASTERIX )
                    // InternalSemver.g:1314:3: RULE_ASTERIX
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
    // InternalSemver.g:1323:1: rule__LETTER__Alternatives : ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) );
    public final void rule__LETTER__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1327:1: ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) )
            int alt22=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_V:
                {
                alt22=1;
                }
                break;
            case RULE_LETTER_X:
                {
                alt22=2;
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
                alt22=3;
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
                    // InternalSemver.g:1328:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1328:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1329:3: RULE_LETTER_V
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
                    // InternalSemver.g:1334:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1334:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1335:3: RULE_LETTER_X
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
                    // InternalSemver.g:1340:2: ( ruleLETTER_NO_VX )
                    {
                    // InternalSemver.g:1340:2: ( ruleLETTER_NO_VX )
                    // InternalSemver.g:1341:3: ruleLETTER_NO_VX
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
    // InternalSemver.g:1350:1: rule__LETTER_NO_VX__Alternatives : ( ( RULE_LETTER_S ) | ( RULE_LETTER_M ) | ( RULE_LETTER_R ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_L ) | ( RULE_LETTER_E ) | ( RULE_LETTER_OTHER ) );
    public final void rule__LETTER_NO_VX__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1354:1: ( ( RULE_LETTER_S ) | ( RULE_LETTER_M ) | ( RULE_LETTER_R ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_L ) | ( RULE_LETTER_E ) | ( RULE_LETTER_OTHER ) )
            int alt23=8;
            switch ( input.LA(1) ) {
            case RULE_LETTER_S:
                {
                alt23=1;
                }
                break;
            case RULE_LETTER_M:
                {
                alt23=2;
                }
                break;
            case RULE_LETTER_R:
                {
                alt23=3;
                }
                break;
            case RULE_LETTER_F:
                {
                alt23=4;
                }
                break;
            case RULE_LETTER_I:
                {
                alt23=5;
                }
                break;
            case RULE_LETTER_L:
                {
                alt23=6;
                }
                break;
            case RULE_LETTER_E:
                {
                alt23=7;
                }
                break;
            case RULE_LETTER_OTHER:
                {
                alt23=8;
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
                    // InternalSemver.g:1355:2: ( RULE_LETTER_S )
                    {
                    // InternalSemver.g:1355:2: ( RULE_LETTER_S )
                    // InternalSemver.g:1356:3: RULE_LETTER_S
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
                    // InternalSemver.g:1361:2: ( RULE_LETTER_M )
                    {
                    // InternalSemver.g:1361:2: ( RULE_LETTER_M )
                    // InternalSemver.g:1362:3: RULE_LETTER_M
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
                    // InternalSemver.g:1367:2: ( RULE_LETTER_R )
                    {
                    // InternalSemver.g:1367:2: ( RULE_LETTER_R )
                    // InternalSemver.g:1368:3: RULE_LETTER_R
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
                    // InternalSemver.g:1373:2: ( RULE_LETTER_F )
                    {
                    // InternalSemver.g:1373:2: ( RULE_LETTER_F )
                    // InternalSemver.g:1374:3: RULE_LETTER_F
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
                    // InternalSemver.g:1379:2: ( RULE_LETTER_I )
                    {
                    // InternalSemver.g:1379:2: ( RULE_LETTER_I )
                    // InternalSemver.g:1380:3: RULE_LETTER_I
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
                    // InternalSemver.g:1385:2: ( RULE_LETTER_L )
                    {
                    // InternalSemver.g:1385:2: ( RULE_LETTER_L )
                    // InternalSemver.g:1386:3: RULE_LETTER_L
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
                    // InternalSemver.g:1391:2: ( RULE_LETTER_E )
                    {
                    // InternalSemver.g:1391:2: ( RULE_LETTER_E )
                    // InternalSemver.g:1392:3: RULE_LETTER_E
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
                    // InternalSemver.g:1397:2: ( RULE_LETTER_OTHER )
                    {
                    // InternalSemver.g:1397:2: ( RULE_LETTER_OTHER )
                    // InternalSemver.g:1398:3: RULE_LETTER_OTHER
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
    // InternalSemver.g:1407:1: rule__VersionComparator__Alternatives : ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1411:1: ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt24=7;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt24=1;
                }
                break;
            case 42:
                {
                alt24=2;
                }
                break;
            case 43:
                {
                alt24=3;
                }
                break;
            case 44:
                {
                alt24=4;
                }
                break;
            case 45:
                {
                alt24=5;
                }
                break;
            case 46:
                {
                alt24=6;
                }
                break;
            case 47:
                {
                alt24=7;
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
                    // InternalSemver.g:1412:2: ( ( '=' ) )
                    {
                    // InternalSemver.g:1412:2: ( ( '=' ) )
                    // InternalSemver.g:1413:3: ( '=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }
                    // InternalSemver.g:1414:3: ( '=' )
                    // InternalSemver.g:1414:4: '='
                    {
                    match(input,41,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1418:2: ( ( '<' ) )
                    {
                    // InternalSemver.g:1418:2: ( ( '<' ) )
                    // InternalSemver.g:1419:3: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }
                    // InternalSemver.g:1420:3: ( '<' )
                    // InternalSemver.g:1420:4: '<'
                    {
                    match(input,42,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1424:2: ( ( '~' ) )
                    {
                    // InternalSemver.g:1424:2: ( ( '~' ) )
                    // InternalSemver.g:1425:3: ( '~' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }
                    // InternalSemver.g:1426:3: ( '~' )
                    // InternalSemver.g:1426:4: '~'
                    {
                    match(input,43,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1430:2: ( ( '^' ) )
                    {
                    // InternalSemver.g:1430:2: ( ( '^' ) )
                    // InternalSemver.g:1431:3: ( '^' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }
                    // InternalSemver.g:1432:3: ( '^' )
                    // InternalSemver.g:1432:4: '^'
                    {
                    match(input,44,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1436:2: ( ( '<=' ) )
                    {
                    // InternalSemver.g:1436:2: ( ( '<=' ) )
                    // InternalSemver.g:1437:3: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }
                    // InternalSemver.g:1438:3: ( '<=' )
                    // InternalSemver.g:1438:4: '<='
                    {
                    match(input,45,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1442:2: ( ( '>' ) )
                    {
                    // InternalSemver.g:1442:2: ( ( '>' ) )
                    // InternalSemver.g:1443:3: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }
                    // InternalSemver.g:1444:3: ( '>' )
                    // InternalSemver.g:1444:4: '>'
                    {
                    match(input,46,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1448:2: ( ( '>=' ) )
                    {
                    // InternalSemver.g:1448:2: ( ( '>=' ) )
                    // InternalSemver.g:1449:3: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); 
                    }
                    // InternalSemver.g:1450:3: ( '>=' )
                    // InternalSemver.g:1450:4: '>='
                    {
                    match(input,47,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1458:1: rule__NPMVersionRequirement__Group_0__0 : rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 ;
    public final void rule__NPMVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1462:1: ( rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 )
            // InternalSemver.g:1463:2: rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1
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
    // InternalSemver.g:1470:1: rule__NPMVersionRequirement__Group_0__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1474:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1475:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1475:1: ( ( RULE_WS )* )
            // InternalSemver.g:1476:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }
            // InternalSemver.g:1477:2: ( RULE_WS )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==RULE_WS) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalSemver.g:1477:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop25;
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
    // InternalSemver.g:1485:1: rule__NPMVersionRequirement__Group_0__1 : rule__NPMVersionRequirement__Group_0__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1489:1: ( rule__NPMVersionRequirement__Group_0__1__Impl )
            // InternalSemver.g:1490:2: rule__NPMVersionRequirement__Group_0__1__Impl
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
    // InternalSemver.g:1496:1: rule__NPMVersionRequirement__Group_0__1__Impl : ( ruleVersionRangeSetRequirement ) ;
    public final void rule__NPMVersionRequirement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1500:1: ( ( ruleVersionRangeSetRequirement ) )
            // InternalSemver.g:1501:1: ( ruleVersionRangeSetRequirement )
            {
            // InternalSemver.g:1501:1: ( ruleVersionRangeSetRequirement )
            // InternalSemver.g:1502:2: ruleVersionRangeSetRequirement
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
    // InternalSemver.g:1512:1: rule__NPMVersionRequirement__Group_1__0 : rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 ;
    public final void rule__NPMVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1516:1: ( rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 )
            // InternalSemver.g:1517:2: rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1
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
    // InternalSemver.g:1524:1: rule__NPMVersionRequirement__Group_1__0__Impl : ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) ;
    public final void rule__NPMVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1528:1: ( ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) )
            // InternalSemver.g:1529:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            {
            // InternalSemver.g:1529:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            // InternalSemver.g:1530:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:1531:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            // InternalSemver.g:1531:3: rule__NPMVersionRequirement__Alternatives_1_0
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
    // InternalSemver.g:1539:1: rule__NPMVersionRequirement__Group_1__1 : rule__NPMVersionRequirement__Group_1__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1543:1: ( rule__NPMVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1544:2: rule__NPMVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1550:1: rule__NPMVersionRequirement__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1554:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1555:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1555:1: ( ( RULE_WS )* )
            // InternalSemver.g:1556:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:1557:2: ( RULE_WS )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_WS) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSemver.g:1557:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop26;
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
    // InternalSemver.g:1566:1: rule__LocalPathVersionRequirement__Group__0 : rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 ;
    public final void rule__LocalPathVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1570:1: ( rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 )
            // InternalSemver.g:1571:2: rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1
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
    // InternalSemver.g:1578:1: rule__LocalPathVersionRequirement__Group__0__Impl : ( ruleFILE_TAG ) ;
    public final void rule__LocalPathVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1582:1: ( ( ruleFILE_TAG ) )
            // InternalSemver.g:1583:1: ( ruleFILE_TAG )
            {
            // InternalSemver.g:1583:1: ( ruleFILE_TAG )
            // InternalSemver.g:1584:2: ruleFILE_TAG
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
    // InternalSemver.g:1593:1: rule__LocalPathVersionRequirement__Group__1 : rule__LocalPathVersionRequirement__Group__1__Impl ;
    public final void rule__LocalPathVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1597:1: ( rule__LocalPathVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1598:2: rule__LocalPathVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1604:1: rule__LocalPathVersionRequirement__Group__1__Impl : ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1608:1: ( ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) )
            // InternalSemver.g:1609:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            {
            // InternalSemver.g:1609:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            // InternalSemver.g:1610:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }
            // InternalSemver.g:1611:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            // InternalSemver.g:1611:3: rule__LocalPathVersionRequirement__LocalPathAssignment_1
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
    // InternalSemver.g:1620:1: rule__URLVersionRequirement__Group__0 : rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 ;
    public final void rule__URLVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1624:1: ( rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 )
            // InternalSemver.g:1625:2: rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1
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
    // InternalSemver.g:1632:1: rule__URLVersionRequirement__Group__0__Impl : ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) ;
    public final void rule__URLVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1636:1: ( ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) )
            // InternalSemver.g:1637:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            {
            // InternalSemver.g:1637:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            // InternalSemver.g:1638:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }
            // InternalSemver.g:1639:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            // InternalSemver.g:1639:3: rule__URLVersionRequirement__ProtocolAssignment_0
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
    // InternalSemver.g:1647:1: rule__URLVersionRequirement__Group__1 : rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 ;
    public final void rule__URLVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1651:1: ( rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 )
            // InternalSemver.g:1652:2: rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2
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
    // InternalSemver.g:1659:1: rule__URLVersionRequirement__Group__1__Impl : ( ( rule__URLVersionRequirement__Group_1__0 ) ) ;
    public final void rule__URLVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1663:1: ( ( ( rule__URLVersionRequirement__Group_1__0 ) ) )
            // InternalSemver.g:1664:1: ( ( rule__URLVersionRequirement__Group_1__0 ) )
            {
            // InternalSemver.g:1664:1: ( ( rule__URLVersionRequirement__Group_1__0 ) )
            // InternalSemver.g:1665:2: ( rule__URLVersionRequirement__Group_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1666:2: ( rule__URLVersionRequirement__Group_1__0 )
            // InternalSemver.g:1666:3: rule__URLVersionRequirement__Group_1__0
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
    // InternalSemver.g:1674:1: rule__URLVersionRequirement__Group__2 : rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 ;
    public final void rule__URLVersionRequirement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1678:1: ( rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 )
            // InternalSemver.g:1679:2: rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3
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
    // InternalSemver.g:1686:1: rule__URLVersionRequirement__Group__2__Impl : ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) ;
    public final void rule__URLVersionRequirement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1690:1: ( ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) )
            // InternalSemver.g:1691:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            {
            // InternalSemver.g:1691:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            // InternalSemver.g:1692:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }
            // InternalSemver.g:1693:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            // InternalSemver.g:1693:3: rule__URLVersionRequirement__UrlAssignment_2
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
    // InternalSemver.g:1701:1: rule__URLVersionRequirement__Group__3 : rule__URLVersionRequirement__Group__3__Impl ;
    public final void rule__URLVersionRequirement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1705:1: ( rule__URLVersionRequirement__Group__3__Impl )
            // InternalSemver.g:1706:2: rule__URLVersionRequirement__Group__3__Impl
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
    // InternalSemver.g:1712:1: rule__URLVersionRequirement__Group__3__Impl : ( ( rule__URLVersionRequirement__Group_3__0 )? ) ;
    public final void rule__URLVersionRequirement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1716:1: ( ( ( rule__URLVersionRequirement__Group_3__0 )? ) )
            // InternalSemver.g:1717:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            {
            // InternalSemver.g:1717:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            // InternalSemver.g:1718:2: ( rule__URLVersionRequirement__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }
            // InternalSemver.g:1719:2: ( rule__URLVersionRequirement__Group_3__0 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==48) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalSemver.g:1719:3: rule__URLVersionRequirement__Group_3__0
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
    // InternalSemver.g:1728:1: rule__URLVersionRequirement__Group_1__0 : rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1 ;
    public final void rule__URLVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1732:1: ( rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1 )
            // InternalSemver.g:1733:2: rule__URLVersionRequirement__Group_1__0__Impl rule__URLVersionRequirement__Group_1__1
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
    // InternalSemver.g:1740:1: rule__URLVersionRequirement__Group_1__0__Impl : ( ':' ) ;
    public final void rule__URLVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1744:1: ( ( ':' ) )
            // InternalSemver.g:1745:1: ( ':' )
            {
            // InternalSemver.g:1745:1: ( ':' )
            // InternalSemver.g:1746:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1755:1: rule__URLVersionRequirement__Group_1__1 : rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2 ;
    public final void rule__URLVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1759:1: ( rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2 )
            // InternalSemver.g:1760:2: rule__URLVersionRequirement__Group_1__1__Impl rule__URLVersionRequirement__Group_1__2
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
    // InternalSemver.g:1767:1: rule__URLVersionRequirement__Group_1__1__Impl : ( '/' ) ;
    public final void rule__URLVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1771:1: ( ( '/' ) )
            // InternalSemver.g:1772:1: ( '/' )
            {
            // InternalSemver.g:1772:1: ( '/' )
            // InternalSemver.g:1773:2: '/'
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
    // InternalSemver.g:1782:1: rule__URLVersionRequirement__Group_1__2 : rule__URLVersionRequirement__Group_1__2__Impl ;
    public final void rule__URLVersionRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1786:1: ( rule__URLVersionRequirement__Group_1__2__Impl )
            // InternalSemver.g:1787:2: rule__URLVersionRequirement__Group_1__2__Impl
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
    // InternalSemver.g:1793:1: rule__URLVersionRequirement__Group_1__2__Impl : ( '/' ) ;
    public final void rule__URLVersionRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1797:1: ( ( '/' ) )
            // InternalSemver.g:1798:1: ( '/' )
            {
            // InternalSemver.g:1798:1: ( '/' )
            // InternalSemver.g:1799:2: '/'
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
    // InternalSemver.g:1809:1: rule__URLVersionRequirement__Group_3__0 : rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 ;
    public final void rule__URLVersionRequirement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1813:1: ( rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 )
            // InternalSemver.g:1814:2: rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1
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
    // InternalSemver.g:1821:1: rule__URLVersionRequirement__Group_3__0__Impl : ( '#' ) ;
    public final void rule__URLVersionRequirement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1825:1: ( ( '#' ) )
            // InternalSemver.g:1826:1: ( '#' )
            {
            // InternalSemver.g:1826:1: ( '#' )
            // InternalSemver.g:1827:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); 
            }
            match(input,48,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:1836:1: rule__URLVersionRequirement__Group_3__1 : rule__URLVersionRequirement__Group_3__1__Impl ;
    public final void rule__URLVersionRequirement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1840:1: ( rule__URLVersionRequirement__Group_3__1__Impl )
            // InternalSemver.g:1841:2: rule__URLVersionRequirement__Group_3__1__Impl
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
    // InternalSemver.g:1847:1: rule__URLVersionRequirement__Group_3__1__Impl : ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) ;
    public final void rule__URLVersionRequirement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1851:1: ( ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) )
            // InternalSemver.g:1852:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            {
            // InternalSemver.g:1852:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            // InternalSemver.g:1853:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }
            // InternalSemver.g:1854:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            // InternalSemver.g:1854:3: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
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
    // InternalSemver.g:1863:1: rule__URLVersionSpecifier__Group_0__0 : rule__URLVersionSpecifier__Group_0__0__Impl ;
    public final void rule__URLVersionSpecifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1867:1: ( rule__URLVersionSpecifier__Group_0__0__Impl )
            // InternalSemver.g:1868:2: rule__URLVersionSpecifier__Group_0__0__Impl
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
    // InternalSemver.g:1874:1: rule__URLVersionSpecifier__Group_0__0__Impl : ( ruleURLSemver ) ;
    public final void rule__URLVersionSpecifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1878:1: ( ( ruleURLSemver ) )
            // InternalSemver.g:1879:1: ( ruleURLSemver )
            {
            // InternalSemver.g:1879:1: ( ruleURLSemver )
            // InternalSemver.g:1880:2: ruleURLSemver
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
    // InternalSemver.g:1890:1: rule__URLVersionSpecifier__Group_1__0 : rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 ;
    public final void rule__URLVersionSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1894:1: ( rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 )
            // InternalSemver.g:1895:2: rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1
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
    // InternalSemver.g:1902:1: rule__URLVersionSpecifier__Group_1__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1906:1: ( ( () ) )
            // InternalSemver.g:1907:1: ( () )
            {
            // InternalSemver.g:1907:1: ( () )
            // InternalSemver.g:1908:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); 
            }
            // InternalSemver.g:1909:2: ()
            // InternalSemver.g:1909:3: 
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
    // InternalSemver.g:1917:1: rule__URLVersionSpecifier__Group_1__1 : rule__URLVersionSpecifier__Group_1__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1921:1: ( rule__URLVersionSpecifier__Group_1__1__Impl )
            // InternalSemver.g:1922:2: rule__URLVersionSpecifier__Group_1__1__Impl
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
    // InternalSemver.g:1928:1: rule__URLVersionSpecifier__Group_1__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1932:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1933:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1933:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1934:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1935:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            // InternalSemver.g:1935:3: rule__URLVersionSpecifier__CommitISHAssignment_1_1
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
    // InternalSemver.g:1944:1: rule__URLVersionSpecifier__Group_2__0 : rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 ;
    public final void rule__URLVersionSpecifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1948:1: ( rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 )
            // InternalSemver.g:1949:2: rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1
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
    // InternalSemver.g:1956:1: rule__URLVersionSpecifier__Group_2__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1960:1: ( ( () ) )
            // InternalSemver.g:1961:1: ( () )
            {
            // InternalSemver.g:1961:1: ( () )
            // InternalSemver.g:1962:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); 
            }
            // InternalSemver.g:1963:2: ()
            // InternalSemver.g:1963:3: 
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
    // InternalSemver.g:1971:1: rule__URLVersionSpecifier__Group_2__1 : rule__URLVersionSpecifier__Group_2__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1975:1: ( rule__URLVersionSpecifier__Group_2__1__Impl )
            // InternalSemver.g:1976:2: rule__URLVersionSpecifier__Group_2__1__Impl
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
    // InternalSemver.g:1982:1: rule__URLVersionSpecifier__Group_2__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1986:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) )
            // InternalSemver.g:1987:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            {
            // InternalSemver.g:1987:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            // InternalSemver.g:1988:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); 
            }
            // InternalSemver.g:1989:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            // InternalSemver.g:1989:3: rule__URLVersionSpecifier__CommitISHAssignment_2_1
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
    // InternalSemver.g:1998:1: rule__URLSemver__Group__0 : rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 ;
    public final void rule__URLSemver__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2002:1: ( rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 )
            // InternalSemver.g:2003:2: rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1
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
    // InternalSemver.g:2010:1: rule__URLSemver__Group__0__Impl : ( () ) ;
    public final void rule__URLSemver__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2014:1: ( ( () ) )
            // InternalSemver.g:2015:1: ( () )
            {
            // InternalSemver.g:2015:1: ( () )
            // InternalSemver.g:2016:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); 
            }
            // InternalSemver.g:2017:2: ()
            // InternalSemver.g:2017:3: 
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
    // InternalSemver.g:2025:1: rule__URLSemver__Group__1 : rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 ;
    public final void rule__URLSemver__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2029:1: ( rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 )
            // InternalSemver.g:2030:2: rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2
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
    // InternalSemver.g:2037:1: rule__URLSemver__Group__1__Impl : ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) ;
    public final void rule__URLSemver__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2041:1: ( ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) )
            // InternalSemver.g:2042:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            {
            // InternalSemver.g:2042:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            // InternalSemver.g:2043:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); 
            }
            // InternalSemver.g:2044:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_LETTER_S) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalSemver.g:2044:3: rule__URLSemver__WithSemverTagAssignment_1
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
    // InternalSemver.g:2052:1: rule__URLSemver__Group__2 : rule__URLSemver__Group__2__Impl ;
    public final void rule__URLSemver__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2056:1: ( rule__URLSemver__Group__2__Impl )
            // InternalSemver.g:2057:2: rule__URLSemver__Group__2__Impl
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
    // InternalSemver.g:2063:1: rule__URLSemver__Group__2__Impl : ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) ;
    public final void rule__URLSemver__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2067:1: ( ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) )
            // InternalSemver.g:2068:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            {
            // InternalSemver.g:2068:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            // InternalSemver.g:2069:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); 
            }
            // InternalSemver.g:2070:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            // InternalSemver.g:2070:3: rule__URLSemver__SimpleVersionAssignment_2
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
    // InternalSemver.g:2079:1: rule__GitHubVersionRequirement__Group__0 : rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 ;
    public final void rule__GitHubVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2083:1: ( rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 )
            // InternalSemver.g:2084:2: rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1
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
    // InternalSemver.g:2091:1: rule__GitHubVersionRequirement__Group__0__Impl : ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) ;
    public final void rule__GitHubVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2095:1: ( ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) )
            // InternalSemver.g:2096:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            {
            // InternalSemver.g:2096:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            // InternalSemver.g:2097:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }
            // InternalSemver.g:2098:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            // InternalSemver.g:2098:3: rule__GitHubVersionRequirement__GithubUrlAssignment_0
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
    // InternalSemver.g:2106:1: rule__GitHubVersionRequirement__Group__1 : rule__GitHubVersionRequirement__Group__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2110:1: ( rule__GitHubVersionRequirement__Group__1__Impl )
            // InternalSemver.g:2111:2: rule__GitHubVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:2117:1: rule__GitHubVersionRequirement__Group__1__Impl : ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) ;
    public final void rule__GitHubVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2121:1: ( ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) )
            // InternalSemver.g:2122:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:2122:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            // InternalSemver.g:2123:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:2124:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==48) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalSemver.g:2124:3: rule__GitHubVersionRequirement__Group_1__0
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
    // InternalSemver.g:2133:1: rule__GitHubVersionRequirement__Group_1__0 : rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 ;
    public final void rule__GitHubVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2137:1: ( rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 )
            // InternalSemver.g:2138:2: rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1
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
    // InternalSemver.g:2145:1: rule__GitHubVersionRequirement__Group_1__0__Impl : ( '#' ) ;
    public final void rule__GitHubVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2149:1: ( ( '#' ) )
            // InternalSemver.g:2150:1: ( '#' )
            {
            // InternalSemver.g:2150:1: ( '#' )
            // InternalSemver.g:2151:2: '#'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); 
            }
            match(input,48,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2160:1: rule__GitHubVersionRequirement__Group_1__1 : rule__GitHubVersionRequirement__Group_1__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2164:1: ( rule__GitHubVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:2165:2: rule__GitHubVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:2171:1: rule__GitHubVersionRequirement__Group_1__1__Impl : ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) ;
    public final void rule__GitHubVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2175:1: ( ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:2176:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:2176:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:2177:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:2178:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            // InternalSemver.g:2178:3: rule__GitHubVersionRequirement__CommitISHAssignment_1_1
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
    // InternalSemver.g:2187:1: rule__VersionRangeSetRequirement__Group__0 : rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 ;
    public final void rule__VersionRangeSetRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2191:1: ( rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 )
            // InternalSemver.g:2192:2: rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1
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
    // InternalSemver.g:2199:1: rule__VersionRangeSetRequirement__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSetRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2203:1: ( ( () ) )
            // InternalSemver.g:2204:1: ( () )
            {
            // InternalSemver.g:2204:1: ( () )
            // InternalSemver.g:2205:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }
            // InternalSemver.g:2206:2: ()
            // InternalSemver.g:2206:3: 
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
    // InternalSemver.g:2214:1: rule__VersionRangeSetRequirement__Group__1 : rule__VersionRangeSetRequirement__Group__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2218:1: ( rule__VersionRangeSetRequirement__Group__1__Impl )
            // InternalSemver.g:2219:2: rule__VersionRangeSetRequirement__Group__1__Impl
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
    // InternalSemver.g:2225:1: rule__VersionRangeSetRequirement__Group__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) ;
    public final void rule__VersionRangeSetRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2229:1: ( ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) )
            // InternalSemver.g:2230:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:2230:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            // InternalSemver.g:2231:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:2232:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=RULE_DIGITS && LA30_0<=RULE_LETTER_V)||(LA30_0>=41 && LA30_0<=47)) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalSemver.g:2232:3: rule__VersionRangeSetRequirement__Group_1__0
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
    // InternalSemver.g:2241:1: rule__VersionRangeSetRequirement__Group_1__0 : rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2245:1: ( rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 )
            // InternalSemver.g:2246:2: rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1
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
    // InternalSemver.g:2253:1: rule__VersionRangeSetRequirement__Group_1__0__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2257:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) )
            // InternalSemver.g:2258:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            {
            // InternalSemver.g:2258:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            // InternalSemver.g:2259:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }
            // InternalSemver.g:2260:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            // InternalSemver.g:2260:3: rule__VersionRangeSetRequirement__RangesAssignment_1_0
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
    // InternalSemver.g:2268:1: rule__VersionRangeSetRequirement__Group_1__1 : rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2272:1: ( rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 )
            // InternalSemver.g:2273:2: rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2
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
    // InternalSemver.g:2280:1: rule__VersionRangeSetRequirement__Group_1__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2284:1: ( ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) )
            // InternalSemver.g:2285:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            {
            // InternalSemver.g:2285:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            // InternalSemver.g:2286:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }
            // InternalSemver.g:2287:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            loop31:
            do {
                int alt31=2;
                alt31 = dfa31.predict(input);
                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:2287:3: rule__VersionRangeSetRequirement__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_16);
            	    rule__VersionRangeSetRequirement__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop31;
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
    // InternalSemver.g:2295:1: rule__VersionRangeSetRequirement__Group_1__2 : rule__VersionRangeSetRequirement__Group_1__2__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2299:1: ( rule__VersionRangeSetRequirement__Group_1__2__Impl )
            // InternalSemver.g:2300:2: rule__VersionRangeSetRequirement__Group_1__2__Impl
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
    // InternalSemver.g:2306:1: rule__VersionRangeSetRequirement__Group_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2310:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2311:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2311:1: ( ( RULE_WS )* )
            // InternalSemver.g:2312:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); 
            }
            // InternalSemver.g:2313:2: ( RULE_WS )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_WS) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:2313:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop32;
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
    // InternalSemver.g:2322:1: rule__VersionRangeSetRequirement__Group_1_1__0 : rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2326:1: ( rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 )
            // InternalSemver.g:2327:2: rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1
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
    // InternalSemver.g:2334:1: rule__VersionRangeSetRequirement__Group_1_1__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2338:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2339:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2339:1: ( ( RULE_WS )* )
            // InternalSemver.g:2340:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }
            // InternalSemver.g:2341:2: ( RULE_WS )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==RULE_WS) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSemver.g:2341:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop33;
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
    // InternalSemver.g:2349:1: rule__VersionRangeSetRequirement__Group_1_1__1 : rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2353:1: ( rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 )
            // InternalSemver.g:2354:2: rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2
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
    // InternalSemver.g:2361:1: rule__VersionRangeSetRequirement__Group_1_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2365:1: ( ( '||' ) )
            // InternalSemver.g:2366:1: ( '||' )
            {
            // InternalSemver.g:2366:1: ( '||' )
            // InternalSemver.g:2367:2: '||'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); 
            }
            match(input,49,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2376:1: rule__VersionRangeSetRequirement__Group_1_1__2 : rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2380:1: ( rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 )
            // InternalSemver.g:2381:2: rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3
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
    // InternalSemver.g:2388:1: rule__VersionRangeSetRequirement__Group_1_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2392:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2393:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2393:1: ( ( RULE_WS )* )
            // InternalSemver.g:2394:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }
            // InternalSemver.g:2395:2: ( RULE_WS )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==RULE_WS) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSemver.g:2395:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop34;
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
    // InternalSemver.g:2403:1: rule__VersionRangeSetRequirement__Group_1_1__3 : rule__VersionRangeSetRequirement__Group_1_1__3__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2407:1: ( rule__VersionRangeSetRequirement__Group_1_1__3__Impl )
            // InternalSemver.g:2408:2: rule__VersionRangeSetRequirement__Group_1_1__3__Impl
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
    // InternalSemver.g:2414:1: rule__VersionRangeSetRequirement__Group_1_1__3__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2418:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) )
            // InternalSemver.g:2419:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            {
            // InternalSemver.g:2419:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            // InternalSemver.g:2420:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }
            // InternalSemver.g:2421:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            // InternalSemver.g:2421:3: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
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
    // InternalSemver.g:2430:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2434:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemver.g:2435:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
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
    // InternalSemver.g:2442:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2446:1: ( ( () ) )
            // InternalSemver.g:2447:1: ( () )
            {
            // InternalSemver.g:2447:1: ( () )
            // InternalSemver.g:2448:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }
            // InternalSemver.g:2449:2: ()
            // InternalSemver.g:2449:3: 
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
    // InternalSemver.g:2457:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2461:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemver.g:2462:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
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
    // InternalSemver.g:2469:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2473:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemver.g:2474:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemver.g:2474:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemver.g:2475:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }
            // InternalSemver.g:2476:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemver.g:2476:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSemver.g:2484:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2488:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemver.g:2489:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
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
    // InternalSemver.g:2496:1: rule__HyphenVersionRange__Group__2__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2500:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2501:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2501:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2502:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2502:2: ( ( RULE_WS ) )
            // InternalSemver.g:2503:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2504:3: ( RULE_WS )
            // InternalSemver.g:2504:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }

            }

            // InternalSemver.g:2507:2: ( ( RULE_WS )* )
            // InternalSemver.g:2508:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2509:3: ( RULE_WS )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_WS) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSemver.g:2509:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop35;
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
    // InternalSemver.g:2518:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2522:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSemver.g:2523:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
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
    // InternalSemver.g:2530:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2534:1: ( ( '-' ) )
            // InternalSemver.g:2535:1: ( '-' )
            {
            // InternalSemver.g:2535:1: ( '-' )
            // InternalSemver.g:2536:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 
            }
            match(input,37,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2545:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2549:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSemver.g:2550:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
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
    // InternalSemver.g:2557:1: rule__HyphenVersionRange__Group__4__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2561:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2562:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2562:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2563:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2563:2: ( ( RULE_WS ) )
            // InternalSemver.g:2564:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2565:3: ( RULE_WS )
            // InternalSemver.g:2565:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }

            }

            // InternalSemver.g:2568:2: ( ( RULE_WS )* )
            // InternalSemver.g:2569:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2570:3: ( RULE_WS )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==RULE_WS) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalSemver.g:2570:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop36;
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
    // InternalSemver.g:2579:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2583:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSemver.g:2584:2: rule__HyphenVersionRange__Group__5__Impl
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
    // InternalSemver.g:2590:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2594:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSemver.g:2595:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSemver.g:2595:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSemver.g:2596:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }
            // InternalSemver.g:2597:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSemver.g:2597:3: rule__HyphenVersionRange__ToAssignment_5
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
    // InternalSemver.g:2606:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2610:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSemver.g:2611:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
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
    // InternalSemver.g:2618:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2622:1: ( ( () ) )
            // InternalSemver.g:2623:1: ( () )
            {
            // InternalSemver.g:2623:1: ( () )
            // InternalSemver.g:2624:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }
            // InternalSemver.g:2625:2: ()
            // InternalSemver.g:2625:3: 
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
    // InternalSemver.g:2633:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2637:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSemver.g:2638:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
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
    // InternalSemver.g:2645:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2649:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSemver.g:2650:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSemver.g:2650:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSemver.g:2651:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }
            // InternalSemver.g:2652:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSemver.g:2652:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
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
    // InternalSemver.g:2660:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2664:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSemver.g:2665:2: rule__VersionRangeContraint__Group__2__Impl
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
    // InternalSemver.g:2671:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2675:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSemver.g:2676:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSemver.g:2676:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSemver.g:2677:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }
            // InternalSemver.g:2678:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop37:
            do {
                int alt37=2;
                alt37 = dfa37.predict(input);
                switch (alt37) {
            	case 1 :
            	    // InternalSemver.g:2678:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__VersionRangeContraint__Group_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop37;
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
    // InternalSemver.g:2687:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2691:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSemver.g:2692:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
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
    // InternalSemver.g:2699:1: rule__VersionRangeContraint__Group_2__0__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2703:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2704:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2704:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2705:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2705:2: ( ( RULE_WS ) )
            // InternalSemver.g:2706:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2707:3: ( RULE_WS )
            // InternalSemver.g:2707:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }

            }

            // InternalSemver.g:2710:2: ( ( RULE_WS )* )
            // InternalSemver.g:2711:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2712:3: ( RULE_WS )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_WS) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSemver.g:2712:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop38;
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
    // InternalSemver.g:2721:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2725:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSemver.g:2726:2: rule__VersionRangeContraint__Group_2__1__Impl
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
    // InternalSemver.g:2732:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2736:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSemver.g:2737:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSemver.g:2737:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSemver.g:2738:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }
            // InternalSemver.g:2739:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSemver.g:2739:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
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
    // InternalSemver.g:2748:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2752:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemver.g:2753:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
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
    // InternalSemver.g:2760:1: rule__SimpleVersion__Group__0__Impl : ( ( rule__SimpleVersion__Group_0__0 )* ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2764:1: ( ( ( rule__SimpleVersion__Group_0__0 )* ) )
            // InternalSemver.g:2765:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            {
            // InternalSemver.g:2765:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            // InternalSemver.g:2766:2: ( rule__SimpleVersion__Group_0__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup_0()); 
            }
            // InternalSemver.g:2767:2: ( rule__SimpleVersion__Group_0__0 )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( ((LA39_0>=41 && LA39_0<=47)) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSemver.g:2767:3: rule__SimpleVersion__Group_0__0
            	    {
            	    pushFollow(FOLLOW_19);
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
    // InternalSemver.g:2775:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2779:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemver.g:2780:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
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
    // InternalSemver.g:2787:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2791:1: ( ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) )
            // InternalSemver.g:2792:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            {
            // InternalSemver.g:2792:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            // InternalSemver.g:2793:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); 
            }
            // InternalSemver.g:2794:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==RULE_LETTER_V) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalSemver.g:2794:3: rule__SimpleVersion__WithLetterVAssignment_1
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
    // InternalSemver.g:2802:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2806:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSemver.g:2807:2: rule__SimpleVersion__Group__2__Impl
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
    // InternalSemver.g:2813:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2817:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSemver.g:2818:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSemver.g:2818:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSemver.g:2819:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            }
            // InternalSemver.g:2820:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSemver.g:2820:3: rule__SimpleVersion__NumberAssignment_2
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
    // InternalSemver.g:2829:1: rule__SimpleVersion__Group_0__0 : rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 ;
    public final void rule__SimpleVersion__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2833:1: ( rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 )
            // InternalSemver.g:2834:2: rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1
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
    // InternalSemver.g:2841:1: rule__SimpleVersion__Group_0__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) ;
    public final void rule__SimpleVersion__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2845:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) )
            // InternalSemver.g:2846:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            {
            // InternalSemver.g:2846:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            // InternalSemver.g:2847:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); 
            }
            // InternalSemver.g:2848:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            // InternalSemver.g:2848:3: rule__SimpleVersion__ComparatorsAssignment_0_0
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
    // InternalSemver.g:2856:1: rule__SimpleVersion__Group_0__1 : rule__SimpleVersion__Group_0__1__Impl ;
    public final void rule__SimpleVersion__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2860:1: ( rule__SimpleVersion__Group_0__1__Impl )
            // InternalSemver.g:2861:2: rule__SimpleVersion__Group_0__1__Impl
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
    // InternalSemver.g:2867:1: rule__SimpleVersion__Group_0__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__SimpleVersion__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2871:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2872:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2872:1: ( ( RULE_WS )* )
            // InternalSemver.g:2873:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); 
            }
            // InternalSemver.g:2874:2: ( RULE_WS )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==RULE_WS) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSemver.g:2874:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

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
    // InternalSemver.g:2883:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2887:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemver.g:2888:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
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
    // InternalSemver.g:2895:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2899:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemver.g:2900:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemver.g:2900:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemver.g:2901:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }
            // InternalSemver.g:2902:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemver.g:2902:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSemver.g:2910:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2914:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSemver.g:2915:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
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
    // InternalSemver.g:2922:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2926:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemver.g:2927:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemver.g:2927:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemver.g:2928:2: ( rule__VersionNumber__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }
            // InternalSemver.g:2929:2: ( rule__VersionNumber__Group_1__0 )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==36) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalSemver.g:2929:3: rule__VersionNumber__Group_1__0
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
    // InternalSemver.g:2937:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2941:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSemver.g:2942:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSemver.g:2948:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2952:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSemver.g:2953:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSemver.g:2953:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSemver.g:2954:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }
            // InternalSemver.g:2955:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( ((LA43_0>=37 && LA43_0<=38)) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalSemver.g:2955:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSemver.g:2964:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2968:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemver.g:2969:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
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
    // InternalSemver.g:2976:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2980:1: ( ( '.' ) )
            // InternalSemver.g:2981:1: ( '.' )
            {
            // InternalSemver.g:2981:1: ( '.' )
            // InternalSemver.g:2982:2: '.'
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
    // InternalSemver.g:2991:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2995:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemver.g:2996:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
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
    // InternalSemver.g:3003:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3007:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemver.g:3008:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemver.g:3008:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemver.g:3009:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }
            // InternalSemver.g:3010:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemver.g:3010:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSemver.g:3018:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3022:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemver.g:3023:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSemver.g:3029:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3033:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemver.g:3034:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemver.g:3034:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemver.g:3035:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }
            // InternalSemver.g:3036:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==36) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalSemver.g:3036:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSemver.g:3045:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3049:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemver.g:3050:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
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
    // InternalSemver.g:3057:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3061:1: ( ( '.' ) )
            // InternalSemver.g:3062:1: ( '.' )
            {
            // InternalSemver.g:3062:1: ( '.' )
            // InternalSemver.g:3063:2: '.'
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
    // InternalSemver.g:3072:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3076:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemver.g:3077:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
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
    // InternalSemver.g:3084:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3088:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSemver.g:3089:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSemver.g:3089:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSemver.g:3090:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }
            // InternalSemver.g:3091:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSemver.g:3091:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSemver.g:3099:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3103:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemver.g:3104:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSemver.g:3110:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3114:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSemver.g:3115:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSemver.g:3115:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSemver.g:3116:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }
            // InternalSemver.g:3117:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==36) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSemver.g:3117:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_22);
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
    // InternalSemver.g:3126:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3130:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSemver.g:3131:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
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
    // InternalSemver.g:3138:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3142:1: ( ( '.' ) )
            // InternalSemver.g:3143:1: ( '.' )
            {
            // InternalSemver.g:3143:1: ( '.' )
            // InternalSemver.g:3144:2: '.'
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
    // InternalSemver.g:3153:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3157:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSemver.g:3158:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSemver.g:3164:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3168:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSemver.g:3169:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSemver.g:3169:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSemver.g:3170:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }
            // InternalSemver.g:3171:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSemver.g:3171:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSemver.g:3180:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3184:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemver.g:3185:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
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
    // InternalSemver.g:3192:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3196:1: ( ( '-' ) )
            // InternalSemver.g:3197:1: ( '-' )
            {
            // InternalSemver.g:3197:1: ( '-' )
            // InternalSemver.g:3198:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            }
            match(input,37,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3207:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3211:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSemver.g:3212:2: rule__Qualifier__Group_0__1__Impl
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
    // InternalSemver.g:3218:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3222:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemver.g:3223:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemver.g:3223:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemver.g:3224:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }
            // InternalSemver.g:3225:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemver.g:3225:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSemver.g:3234:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3238:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemver.g:3239:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
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
    // InternalSemver.g:3246:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3250:1: ( ( '+' ) )
            // InternalSemver.g:3251:1: ( '+' )
            {
            // InternalSemver.g:3251:1: ( '+' )
            // InternalSemver.g:3252:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            }
            match(input,38,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3261:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3265:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemver.g:3266:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSemver.g:3272:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3276:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemver.g:3277:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemver.g:3277:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemver.g:3278:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }
            // InternalSemver.g:3279:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemver.g:3279:3: rule__Qualifier__BuildMetadataAssignment_1_1
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
    // InternalSemver.g:3288:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3292:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSemver.g:3293:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
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
    // InternalSemver.g:3300:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3304:1: ( ( '-' ) )
            // InternalSemver.g:3305:1: ( '-' )
            {
            // InternalSemver.g:3305:1: ( '-' )
            // InternalSemver.g:3306:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 
            }
            match(input,37,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3315:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3319:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSemver.g:3320:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
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
    // InternalSemver.g:3327:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3331:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSemver.g:3332:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSemver.g:3332:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSemver.g:3333:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            }
            // InternalSemver.g:3334:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSemver.g:3334:3: rule__Qualifier__PreReleaseAssignment_2_1
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
    // InternalSemver.g:3342:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3346:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSemver.g:3347:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
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
    // InternalSemver.g:3354:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3358:1: ( ( '+' ) )
            // InternalSemver.g:3359:1: ( '+' )
            {
            // InternalSemver.g:3359:1: ( '+' )
            // InternalSemver.g:3360:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 
            }
            match(input,38,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3369:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3373:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSemver.g:3374:2: rule__Qualifier__Group_2__3__Impl
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
    // InternalSemver.g:3380:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3384:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSemver.g:3385:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSemver.g:3385:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSemver.g:3386:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            }
            // InternalSemver.g:3387:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSemver.g:3387:3: rule__Qualifier__BuildMetadataAssignment_2_3
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
    // InternalSemver.g:3396:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3400:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSemver.g:3401:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
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
    // InternalSemver.g:3408:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3412:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSemver.g:3413:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSemver.g:3413:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSemver.g:3414:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }
            // InternalSemver.g:3415:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSemver.g:3415:3: rule__QualifierTag__PartsAssignment_0
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
    // InternalSemver.g:3423:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3427:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSemver.g:3428:2: rule__QualifierTag__Group__1__Impl
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
    // InternalSemver.g:3434:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3438:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSemver.g:3439:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSemver.g:3439:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSemver.g:3440:2: ( rule__QualifierTag__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }
            // InternalSemver.g:3441:2: ( rule__QualifierTag__Group_1__0 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==36) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSemver.g:3441:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__QualifierTag__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop46;
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
    // InternalSemver.g:3450:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3454:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSemver.g:3455:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
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
    // InternalSemver.g:3462:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3466:1: ( ( '.' ) )
            // InternalSemver.g:3467:1: ( '.' )
            {
            // InternalSemver.g:3467:1: ( '.' )
            // InternalSemver.g:3468:2: '.'
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
    // InternalSemver.g:3477:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3481:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSemver.g:3482:2: rule__QualifierTag__Group_1__1__Impl
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
    // InternalSemver.g:3488:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3492:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSemver.g:3493:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSemver.g:3493:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSemver.g:3494:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }
            // InternalSemver.g:3495:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSemver.g:3495:3: rule__QualifierTag__PartsAssignment_1_1
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
    // InternalSemver.g:3504:1: rule__FILE_TAG__Group__0 : rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 ;
    public final void rule__FILE_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3508:1: ( rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 )
            // InternalSemver.g:3509:2: rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1
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
    // InternalSemver.g:3516:1: rule__FILE_TAG__Group__0__Impl : ( RULE_LETTER_F ) ;
    public final void rule__FILE_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3520:1: ( ( RULE_LETTER_F ) )
            // InternalSemver.g:3521:1: ( RULE_LETTER_F )
            {
            // InternalSemver.g:3521:1: ( RULE_LETTER_F )
            // InternalSemver.g:3522:2: RULE_LETTER_F
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
    // InternalSemver.g:3531:1: rule__FILE_TAG__Group__1 : rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 ;
    public final void rule__FILE_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3535:1: ( rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 )
            // InternalSemver.g:3536:2: rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2
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
    // InternalSemver.g:3543:1: rule__FILE_TAG__Group__1__Impl : ( RULE_LETTER_I ) ;
    public final void rule__FILE_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3547:1: ( ( RULE_LETTER_I ) )
            // InternalSemver.g:3548:1: ( RULE_LETTER_I )
            {
            // InternalSemver.g:3548:1: ( RULE_LETTER_I )
            // InternalSemver.g:3549:2: RULE_LETTER_I
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
    // InternalSemver.g:3558:1: rule__FILE_TAG__Group__2 : rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 ;
    public final void rule__FILE_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3562:1: ( rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 )
            // InternalSemver.g:3563:2: rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3
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
    // InternalSemver.g:3570:1: rule__FILE_TAG__Group__2__Impl : ( RULE_LETTER_L ) ;
    public final void rule__FILE_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3574:1: ( ( RULE_LETTER_L ) )
            // InternalSemver.g:3575:1: ( RULE_LETTER_L )
            {
            // InternalSemver.g:3575:1: ( RULE_LETTER_L )
            // InternalSemver.g:3576:2: RULE_LETTER_L
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
    // InternalSemver.g:3585:1: rule__FILE_TAG__Group__3 : rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 ;
    public final void rule__FILE_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3589:1: ( rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 )
            // InternalSemver.g:3590:2: rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4
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
    // InternalSemver.g:3597:1: rule__FILE_TAG__Group__3__Impl : ( RULE_LETTER_E ) ;
    public final void rule__FILE_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3601:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3602:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3602:1: ( RULE_LETTER_E )
            // InternalSemver.g:3603:2: RULE_LETTER_E
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
    // InternalSemver.g:3612:1: rule__FILE_TAG__Group__4 : rule__FILE_TAG__Group__4__Impl ;
    public final void rule__FILE_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3616:1: ( rule__FILE_TAG__Group__4__Impl )
            // InternalSemver.g:3617:2: rule__FILE_TAG__Group__4__Impl
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
    // InternalSemver.g:3623:1: rule__FILE_TAG__Group__4__Impl : ( ':' ) ;
    public final void rule__FILE_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3627:1: ( ( ':' ) )
            // InternalSemver.g:3628:1: ( ':' )
            {
            // InternalSemver.g:3628:1: ( ':' )
            // InternalSemver.g:3629:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFILE_TAGAccess().getColonKeyword_4()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3639:1: rule__SEMVER_TAG__Group__0 : rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 ;
    public final void rule__SEMVER_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3643:1: ( rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 )
            // InternalSemver.g:3644:2: rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1
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
    // InternalSemver.g:3651:1: rule__SEMVER_TAG__Group__0__Impl : ( RULE_LETTER_S ) ;
    public final void rule__SEMVER_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3655:1: ( ( RULE_LETTER_S ) )
            // InternalSemver.g:3656:1: ( RULE_LETTER_S )
            {
            // InternalSemver.g:3656:1: ( RULE_LETTER_S )
            // InternalSemver.g:3657:2: RULE_LETTER_S
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
    // InternalSemver.g:3666:1: rule__SEMVER_TAG__Group__1 : rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 ;
    public final void rule__SEMVER_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3670:1: ( rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 )
            // InternalSemver.g:3671:2: rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2
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
    // InternalSemver.g:3678:1: rule__SEMVER_TAG__Group__1__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3682:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3683:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3683:1: ( RULE_LETTER_E )
            // InternalSemver.g:3684:2: RULE_LETTER_E
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
    // InternalSemver.g:3693:1: rule__SEMVER_TAG__Group__2 : rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 ;
    public final void rule__SEMVER_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3697:1: ( rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 )
            // InternalSemver.g:3698:2: rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3
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
    // InternalSemver.g:3705:1: rule__SEMVER_TAG__Group__2__Impl : ( RULE_LETTER_M ) ;
    public final void rule__SEMVER_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3709:1: ( ( RULE_LETTER_M ) )
            // InternalSemver.g:3710:1: ( RULE_LETTER_M )
            {
            // InternalSemver.g:3710:1: ( RULE_LETTER_M )
            // InternalSemver.g:3711:2: RULE_LETTER_M
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
    // InternalSemver.g:3720:1: rule__SEMVER_TAG__Group__3 : rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 ;
    public final void rule__SEMVER_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3724:1: ( rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 )
            // InternalSemver.g:3725:2: rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4
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
    // InternalSemver.g:3732:1: rule__SEMVER_TAG__Group__3__Impl : ( RULE_LETTER_V ) ;
    public final void rule__SEMVER_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3736:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:3737:1: ( RULE_LETTER_V )
            {
            // InternalSemver.g:3737:1: ( RULE_LETTER_V )
            // InternalSemver.g:3738:2: RULE_LETTER_V
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
    // InternalSemver.g:3747:1: rule__SEMVER_TAG__Group__4 : rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 ;
    public final void rule__SEMVER_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3751:1: ( rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 )
            // InternalSemver.g:3752:2: rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5
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
    // InternalSemver.g:3759:1: rule__SEMVER_TAG__Group__4__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3763:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3764:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3764:1: ( RULE_LETTER_E )
            // InternalSemver.g:3765:2: RULE_LETTER_E
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
    // InternalSemver.g:3774:1: rule__SEMVER_TAG__Group__5 : rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 ;
    public final void rule__SEMVER_TAG__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3778:1: ( rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 )
            // InternalSemver.g:3779:2: rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6
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
    // InternalSemver.g:3786:1: rule__SEMVER_TAG__Group__5__Impl : ( RULE_LETTER_R ) ;
    public final void rule__SEMVER_TAG__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3790:1: ( ( RULE_LETTER_R ) )
            // InternalSemver.g:3791:1: ( RULE_LETTER_R )
            {
            // InternalSemver.g:3791:1: ( RULE_LETTER_R )
            // InternalSemver.g:3792:2: RULE_LETTER_R
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
    // InternalSemver.g:3801:1: rule__SEMVER_TAG__Group__6 : rule__SEMVER_TAG__Group__6__Impl ;
    public final void rule__SEMVER_TAG__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3805:1: ( rule__SEMVER_TAG__Group__6__Impl )
            // InternalSemver.g:3806:2: rule__SEMVER_TAG__Group__6__Impl
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
    // InternalSemver.g:3812:1: rule__SEMVER_TAG__Group__6__Impl : ( ':' ) ;
    public final void rule__SEMVER_TAG__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3816:1: ( ( ':' ) )
            // InternalSemver.g:3817:1: ( ':' )
            {
            // InternalSemver.g:3817:1: ( ':' )
            // InternalSemver.g:3818:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3828:1: rule__URL_PROTOCOL__Group__0 : rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 ;
    public final void rule__URL_PROTOCOL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3832:1: ( rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 )
            // InternalSemver.g:3833:2: rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1
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
    // InternalSemver.g:3840:1: rule__URL_PROTOCOL__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__URL_PROTOCOL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3844:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:3845:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:3845:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:3846:2: ruleLETTER_NO_VX
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
    // InternalSemver.g:3855:1: rule__URL_PROTOCOL__Group__1 : rule__URL_PROTOCOL__Group__1__Impl ;
    public final void rule__URL_PROTOCOL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3859:1: ( rule__URL_PROTOCOL__Group__1__Impl )
            // InternalSemver.g:3860:2: rule__URL_PROTOCOL__Group__1__Impl
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
    // InternalSemver.g:3866:1: rule__URL_PROTOCOL__Group__1__Impl : ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) ;
    public final void rule__URL_PROTOCOL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3870:1: ( ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) )
            // InternalSemver.g:3871:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3871:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            // InternalSemver.g:3872:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            {
            // InternalSemver.g:3872:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) )
            // InternalSemver.g:3873:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3874:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            // InternalSemver.g:3874:4: rule__URL_PROTOCOL__Alternatives_1
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

            // InternalSemver.g:3877:2: ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            // InternalSemver.g:3878:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3879:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==RULE_LETTER_X||(LA47_0>=RULE_LETTER_V && LA47_0<=RULE_LETTER_OTHER)||LA47_0==38) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSemver.g:3879:4: rule__URL_PROTOCOL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_31);
            	    rule__URL_PROTOCOL__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop47;
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
    // InternalSemver.g:3889:1: rule__URL__Group__0 : rule__URL__Group__0__Impl rule__URL__Group__1 ;
    public final void rule__URL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3893:1: ( rule__URL__Group__0__Impl rule__URL__Group__1 )
            // InternalSemver.g:3894:2: rule__URL__Group__0__Impl rule__URL__Group__1
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
    // InternalSemver.g:3901:1: rule__URL__Group__0__Impl : ( ( rule__URL__Alternatives_0 )* ) ;
    public final void rule__URL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3905:1: ( ( ( rule__URL__Alternatives_0 )* ) )
            // InternalSemver.g:3906:1: ( ( rule__URL__Alternatives_0 )* )
            {
            // InternalSemver.g:3906:1: ( ( rule__URL__Alternatives_0 )* )
            // InternalSemver.g:3907:2: ( rule__URL__Alternatives_0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:3908:2: ( rule__URL__Alternatives_0 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=RULE_DIGITS && LA48_0<=RULE_LETTER_X)||(LA48_0>=RULE_LETTER_V && LA48_0<=RULE_LETTER_OTHER)||LA48_0==37) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalSemver.g:3908:3: rule__URL__Alternatives_0
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL__Alternatives_0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop48;
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
    // InternalSemver.g:3916:1: rule__URL__Group__1 : rule__URL__Group__1__Impl rule__URL__Group__2 ;
    public final void rule__URL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3920:1: ( rule__URL__Group__1__Impl rule__URL__Group__2 )
            // InternalSemver.g:3921:2: rule__URL__Group__1__Impl rule__URL__Group__2
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
    // InternalSemver.g:3928:1: rule__URL__Group__1__Impl : ( ( rule__URL__Alternatives_1 ) ) ;
    public final void rule__URL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3932:1: ( ( ( rule__URL__Alternatives_1 ) ) )
            // InternalSemver.g:3933:1: ( ( rule__URL__Alternatives_1 ) )
            {
            // InternalSemver.g:3933:1: ( ( rule__URL__Alternatives_1 ) )
            // InternalSemver.g:3934:2: ( rule__URL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3935:2: ( rule__URL__Alternatives_1 )
            // InternalSemver.g:3935:3: rule__URL__Alternatives_1
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
    // InternalSemver.g:3943:1: rule__URL__Group__2 : rule__URL__Group__2__Impl ;
    public final void rule__URL__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3947:1: ( rule__URL__Group__2__Impl )
            // InternalSemver.g:3948:2: rule__URL__Group__2__Impl
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
    // InternalSemver.g:3954:1: rule__URL__Group__2__Impl : ( ( rule__URL__Alternatives_2 )* ) ;
    public final void rule__URL__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3958:1: ( ( ( rule__URL__Alternatives_2 )* ) )
            // InternalSemver.g:3959:1: ( ( rule__URL__Alternatives_2 )* )
            {
            // InternalSemver.g:3959:1: ( ( rule__URL__Alternatives_2 )* )
            // InternalSemver.g:3960:2: ( rule__URL__Alternatives_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:3961:2: ( rule__URL__Alternatives_2 )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( ((LA49_0>=RULE_DIGITS && LA49_0<=RULE_LETTER_X)||(LA49_0>=RULE_LETTER_V && LA49_0<=RULE_LETTER_OTHER)||(LA49_0>=35 && LA49_0<=37)||(LA49_0>=39 && LA49_0<=40)) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalSemver.g:3961:3: rule__URL__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_32);
            	    rule__URL__Alternatives_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop49;
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
    // InternalSemver.g:3970:1: rule__URL_NO_VX__Group__0 : rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1 ;
    public final void rule__URL_NO_VX__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3974:1: ( rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1 )
            // InternalSemver.g:3975:2: rule__URL_NO_VX__Group__0__Impl rule__URL_NO_VX__Group__1
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
    // InternalSemver.g:3982:1: rule__URL_NO_VX__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__URL_NO_VX__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3986:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:3987:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:3987:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:3988:2: ruleLETTER_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3997:1: rule__URL_NO_VX__Group__1 : rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2 ;
    public final void rule__URL_NO_VX__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4001:1: ( rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2 )
            // InternalSemver.g:4002:2: rule__URL_NO_VX__Group__1__Impl rule__URL_NO_VX__Group__2
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
    // InternalSemver.g:4009:1: rule__URL_NO_VX__Group__1__Impl : ( ( rule__URL_NO_VX__Alternatives_1 )* ) ;
    public final void rule__URL_NO_VX__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4013:1: ( ( ( rule__URL_NO_VX__Alternatives_1 )* ) )
            // InternalSemver.g:4014:1: ( ( rule__URL_NO_VX__Alternatives_1 )* )
            {
            // InternalSemver.g:4014:1: ( ( rule__URL_NO_VX__Alternatives_1 )* )
            // InternalSemver.g:4015:2: ( rule__URL_NO_VX__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4016:2: ( rule__URL_NO_VX__Alternatives_1 )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=RULE_DIGITS && LA50_0<=RULE_LETTER_X)||(LA50_0>=RULE_LETTER_V && LA50_0<=RULE_LETTER_OTHER)||LA50_0==37) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalSemver.g:4016:3: rule__URL_NO_VX__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL_NO_VX__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop50;
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
    // InternalSemver.g:4024:1: rule__URL_NO_VX__Group__2 : rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3 ;
    public final void rule__URL_NO_VX__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4028:1: ( rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3 )
            // InternalSemver.g:4029:2: rule__URL_NO_VX__Group__2__Impl rule__URL_NO_VX__Group__3
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
    // InternalSemver.g:4036:1: rule__URL_NO_VX__Group__2__Impl : ( ( rule__URL_NO_VX__Alternatives_2 ) ) ;
    public final void rule__URL_NO_VX__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4040:1: ( ( ( rule__URL_NO_VX__Alternatives_2 ) ) )
            // InternalSemver.g:4041:1: ( ( rule__URL_NO_VX__Alternatives_2 ) )
            {
            // InternalSemver.g:4041:1: ( ( rule__URL_NO_VX__Alternatives_2 ) )
            // InternalSemver.g:4042:2: ( rule__URL_NO_VX__Alternatives_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:4043:2: ( rule__URL_NO_VX__Alternatives_2 )
            // InternalSemver.g:4043:3: rule__URL_NO_VX__Alternatives_2
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
    // InternalSemver.g:4051:1: rule__URL_NO_VX__Group__3 : rule__URL_NO_VX__Group__3__Impl ;
    public final void rule__URL_NO_VX__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4055:1: ( rule__URL_NO_VX__Group__3__Impl )
            // InternalSemver.g:4056:2: rule__URL_NO_VX__Group__3__Impl
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
    // InternalSemver.g:4062:1: rule__URL_NO_VX__Group__3__Impl : ( ( rule__URL_NO_VX__Alternatives_3 )* ) ;
    public final void rule__URL_NO_VX__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4066:1: ( ( ( rule__URL_NO_VX__Alternatives_3 )* ) )
            // InternalSemver.g:4067:1: ( ( rule__URL_NO_VX__Alternatives_3 )* )
            {
            // InternalSemver.g:4067:1: ( ( rule__URL_NO_VX__Alternatives_3 )* )
            // InternalSemver.g:4068:2: ( rule__URL_NO_VX__Alternatives_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_NO_VXAccess().getAlternatives_3()); 
            }
            // InternalSemver.g:4069:2: ( rule__URL_NO_VX__Alternatives_3 )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( ((LA51_0>=RULE_DIGITS && LA51_0<=RULE_LETTER_X)||(LA51_0>=RULE_LETTER_V && LA51_0<=RULE_LETTER_OTHER)||(LA51_0>=35 && LA51_0<=37)||(LA51_0>=39 && LA51_0<=40)) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalSemver.g:4069:3: rule__URL_NO_VX__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_32);
            	    rule__URL_NO_VX__Alternatives_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop51;
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
    // InternalSemver.g:4078:1: rule__TAG__Group__0 : rule__TAG__Group__0__Impl rule__TAG__Group__1 ;
    public final void rule__TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4082:1: ( rule__TAG__Group__0__Impl rule__TAG__Group__1 )
            // InternalSemver.g:4083:2: rule__TAG__Group__0__Impl rule__TAG__Group__1
            {
            pushFollow(FOLLOW_7);
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
    // InternalSemver.g:4090:1: rule__TAG__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4094:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:4095:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:4095:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:4096:2: ruleLETTER_NO_VX
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
    // InternalSemver.g:4105:1: rule__TAG__Group__1 : rule__TAG__Group__1__Impl ;
    public final void rule__TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4109:1: ( rule__TAG__Group__1__Impl )
            // InternalSemver.g:4110:2: rule__TAG__Group__1__Impl
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
    // InternalSemver.g:4116:1: rule__TAG__Group__1__Impl : ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) ;
    public final void rule__TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4120:1: ( ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) )
            // InternalSemver.g:4121:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            {
            // InternalSemver.g:4121:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            // InternalSemver.g:4122:2: ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* )
            {
            // InternalSemver.g:4122:2: ( ( rule__TAG__Alternatives_1 ) )
            // InternalSemver.g:4123:3: ( rule__TAG__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4124:3: ( rule__TAG__Alternatives_1 )
            // InternalSemver.g:4124:4: rule__TAG__Alternatives_1
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

            // InternalSemver.g:4127:2: ( ( rule__TAG__Alternatives_1 )* )
            // InternalSemver.g:4128:3: ( rule__TAG__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4129:3: ( rule__TAG__Alternatives_1 )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( ((LA52_0>=RULE_DIGITS && LA52_0<=RULE_LETTER_X)||(LA52_0>=RULE_LETTER_V && LA52_0<=RULE_LETTER_OTHER)||LA52_0==37) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalSemver.g:4129:4: rule__TAG__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__TAG__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop52;
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
    // InternalSemver.g:4139:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4143:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 )
            // InternalSemver.g:4144:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1
            {
            pushFollow(FOLLOW_7);
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
    // InternalSemver.g:4151:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl : ( RULE_DIGITS ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4155:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:4156:1: ( RULE_DIGITS )
            {
            // InternalSemver.g:4156:1: ( RULE_DIGITS )
            // InternalSemver.g:4157:2: RULE_DIGITS
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
    // InternalSemver.g:4166:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4170:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl )
            // InternalSemver.g:4171:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl
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
    // InternalSemver.g:4177:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl : ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4181:1: ( ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) )
            // InternalSemver.g:4182:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            {
            // InternalSemver.g:4182:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            // InternalSemver.g:4183:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            {
            // InternalSemver.g:4183:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) )
            // InternalSemver.g:4184:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4185:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            // InternalSemver.g:4185:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
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

            // InternalSemver.g:4188:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            // InternalSemver.g:4189:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:4190:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( ((LA53_0>=RULE_DIGITS && LA53_0<=RULE_LETTER_X)||(LA53_0>=RULE_LETTER_V && LA53_0<=RULE_LETTER_OTHER)||LA53_0==37) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalSemver.g:4190:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop53;
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
    // InternalSemver.g:4200:1: rule__LocalPathVersionRequirement__LocalPathAssignment_1 : ( rulePATH ) ;
    public final void rule__LocalPathVersionRequirement__LocalPathAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4204:1: ( ( rulePATH ) )
            // InternalSemver.g:4205:2: ( rulePATH )
            {
            // InternalSemver.g:4205:2: ( rulePATH )
            // InternalSemver.g:4206:3: rulePATH
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
    // InternalSemver.g:4215:1: rule__URLVersionRequirement__ProtocolAssignment_0 : ( ruleURL_PROTOCOL ) ;
    public final void rule__URLVersionRequirement__ProtocolAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4219:1: ( ( ruleURL_PROTOCOL ) )
            // InternalSemver.g:4220:2: ( ruleURL_PROTOCOL )
            {
            // InternalSemver.g:4220:2: ( ruleURL_PROTOCOL )
            // InternalSemver.g:4221:3: ruleURL_PROTOCOL
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
    // InternalSemver.g:4230:1: rule__URLVersionRequirement__UrlAssignment_2 : ( ruleURL ) ;
    public final void rule__URLVersionRequirement__UrlAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4234:1: ( ( ruleURL ) )
            // InternalSemver.g:4235:2: ( ruleURL )
            {
            // InternalSemver.g:4235:2: ( ruleURL )
            // InternalSemver.g:4236:3: ruleURL
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
    // InternalSemver.g:4245:1: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 : ( ruleURLVersionSpecifier ) ;
    public final void rule__URLVersionRequirement__VersionSpecifierAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4249:1: ( ( ruleURLVersionSpecifier ) )
            // InternalSemver.g:4250:2: ( ruleURLVersionSpecifier )
            {
            // InternalSemver.g:4250:2: ( ruleURLVersionSpecifier )
            // InternalSemver.g:4251:3: ruleURLVersionSpecifier
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
    // InternalSemver.g:4260:1: rule__URLVersionSpecifier__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4264:1: ( ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
            // InternalSemver.g:4265:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            {
            // InternalSemver.g:4265:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            // InternalSemver.g:4266:3: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
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
    // InternalSemver.g:4275:1: rule__URLVersionSpecifier__CommitISHAssignment_2_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4279:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4280:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4280:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4281:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4290:1: rule__URLSemver__WithSemverTagAssignment_1 : ( ruleSEMVER_TAG ) ;
    public final void rule__URLSemver__WithSemverTagAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4294:1: ( ( ruleSEMVER_TAG ) )
            // InternalSemver.g:4295:2: ( ruleSEMVER_TAG )
            {
            // InternalSemver.g:4295:2: ( ruleSEMVER_TAG )
            // InternalSemver.g:4296:3: ruleSEMVER_TAG
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
    // InternalSemver.g:4305:1: rule__URLSemver__SimpleVersionAssignment_2 : ( ruleSimpleVersion ) ;
    public final void rule__URLSemver__SimpleVersionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4309:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4310:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4310:2: ( ruleSimpleVersion )
            // InternalSemver.g:4311:3: ruleSimpleVersion
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
    // InternalSemver.g:4320:1: rule__TagVersionRequirement__TagNameAssignment : ( ruleTAG ) ;
    public final void rule__TagVersionRequirement__TagNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4324:1: ( ( ruleTAG ) )
            // InternalSemver.g:4325:2: ( ruleTAG )
            {
            // InternalSemver.g:4325:2: ( ruleTAG )
            // InternalSemver.g:4326:3: ruleTAG
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
    // InternalSemver.g:4335:1: rule__GitHubVersionRequirement__GithubUrlAssignment_0 : ( ruleURL_NO_VX ) ;
    public final void rule__GitHubVersionRequirement__GithubUrlAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4339:1: ( ( ruleURL_NO_VX ) )
            // InternalSemver.g:4340:2: ( ruleURL_NO_VX )
            {
            // InternalSemver.g:4340:2: ( ruleURL_NO_VX )
            // InternalSemver.g:4341:3: ruleURL_NO_VX
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
    // InternalSemver.g:4350:1: rule__GitHubVersionRequirement__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__GitHubVersionRequirement__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4354:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4355:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4355:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4356:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4365:1: rule__VersionRangeSetRequirement__RangesAssignment_1_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4369:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4370:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4370:2: ( ruleVersionRange )
            // InternalSemver.g:4371:3: ruleVersionRange
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
    // InternalSemver.g:4380:1: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4384:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4385:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4385:2: ( ruleVersionRange )
            // InternalSemver.g:4386:3: ruleVersionRange
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
    // InternalSemver.g:4395:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4399:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4400:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4400:2: ( ruleVersionNumber )
            // InternalSemver.g:4401:3: ruleVersionNumber
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
    // InternalSemver.g:4410:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4414:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4415:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4415:2: ( ruleVersionNumber )
            // InternalSemver.g:4416:3: ruleVersionNumber
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
    // InternalSemver.g:4425:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4429:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4430:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4430:2: ( ruleSimpleVersion )
            // InternalSemver.g:4431:3: ruleSimpleVersion
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
    // InternalSemver.g:4440:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4444:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4445:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4445:2: ( ruleSimpleVersion )
            // InternalSemver.g:4446:3: ruleSimpleVersion
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
    // InternalSemver.g:4455:1: rule__SimpleVersion__ComparatorsAssignment_0_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4459:1: ( ( ruleVersionComparator ) )
            // InternalSemver.g:4460:2: ( ruleVersionComparator )
            {
            // InternalSemver.g:4460:2: ( ruleVersionComparator )
            // InternalSemver.g:4461:3: ruleVersionComparator
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
    // InternalSemver.g:4470:1: rule__SimpleVersion__WithLetterVAssignment_1 : ( RULE_LETTER_V ) ;
    public final void rule__SimpleVersion__WithLetterVAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4474:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:4475:2: ( RULE_LETTER_V )
            {
            // InternalSemver.g:4475:2: ( RULE_LETTER_V )
            // InternalSemver.g:4476:3: RULE_LETTER_V
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
    // InternalSemver.g:4485:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4489:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4490:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4490:2: ( ruleVersionNumber )
            // InternalSemver.g:4491:3: ruleVersionNumber
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
    // InternalSemver.g:4500:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4504:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4505:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4505:2: ( ruleVersionPart )
            // InternalSemver.g:4506:3: ruleVersionPart
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
    // InternalSemver.g:4515:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4519:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4520:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4520:2: ( ruleVersionPart )
            // InternalSemver.g:4521:3: ruleVersionPart
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
    // InternalSemver.g:4530:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4534:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4535:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4535:2: ( ruleVersionPart )
            // InternalSemver.g:4536:3: ruleVersionPart
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
    // InternalSemver.g:4545:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4549:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4550:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4550:2: ( ruleVersionPart )
            // InternalSemver.g:4551:3: ruleVersionPart
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
    // InternalSemver.g:4560:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4564:1: ( ( ruleQualifier ) )
            // InternalSemver.g:4565:2: ( ruleQualifier )
            {
            // InternalSemver.g:4565:2: ( ruleQualifier )
            // InternalSemver.g:4566:3: ruleQualifier
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
    // InternalSemver.g:4575:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4579:1: ( ( ruleWILDCARD ) )
            // InternalSemver.g:4580:2: ( ruleWILDCARD )
            {
            // InternalSemver.g:4580:2: ( ruleWILDCARD )
            // InternalSemver.g:4581:3: ruleWILDCARD
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
    // InternalSemver.g:4590:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4594:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:4595:2: ( RULE_DIGITS )
            {
            // InternalSemver.g:4595:2: ( RULE_DIGITS )
            // InternalSemver.g:4596:3: RULE_DIGITS
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
    // InternalSemver.g:4605:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4609:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4610:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4610:2: ( ruleQualifierTag )
            // InternalSemver.g:4611:3: ruleQualifierTag
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
    // InternalSemver.g:4620:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4624:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4625:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4625:2: ( ruleQualifierTag )
            // InternalSemver.g:4626:3: ruleQualifierTag
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
    // InternalSemver.g:4635:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4639:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4640:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4640:2: ( ruleQualifierTag )
            // InternalSemver.g:4641:3: ruleQualifierTag
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
    // InternalSemver.g:4650:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4654:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4655:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4655:2: ( ruleQualifierTag )
            // InternalSemver.g:4656:3: ruleQualifierTag
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
    // InternalSemver.g:4665:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4669:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4670:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4670:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4671:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4680:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4684:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4685:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4685:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4686:3: ruleALPHA_NUMERIC_CHARS
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
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA31 dfa31 = new DFA31(this);
    protected DFA37 dfa37 = new DFA37(this);
    static final String dfa_1s = "\26\uffff";
    static final String dfa_2s = "\3\uffff\4\2\17\uffff";
    static final String dfa_3s = "\1\10\1\4\1\uffff\4\4\16\0\1\uffff";
    static final String dfa_4s = "\1\17\1\50\1\uffff\3\50\1\60\16\0\1\uffff";
    static final String dfa_5s = "\2\uffff\1\2\22\uffff\1\1";
    static final String dfa_6s = "\7\uffff\1\2\1\10\1\6\1\0\1\11\1\4\1\1\1\3\1\14\1\15\1\7\1\12\1\5\1\13\1\uffff}>";
    static final String[] dfa_7s = {
            "\3\2\1\1\4\2",
            "\2\2\1\uffff\5\2\1\3\3\2\23\uffff\6\2",
            "",
            "\2\2\1\uffff\6\2\1\4\3\2\22\uffff\6\2",
            "\2\2\1\uffff\7\2\1\5\2\2\22\uffff\6\2",
            "\2\2\1\uffff\12\2\22\uffff\4\2\1\6\1\2",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\2\22\uffff\1\7\1\10\1\11\1\uffff\2\2\7\uffff\1\2",
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
                        int LA4_10 = input.LA(1);

                         
                        int index4_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_10);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_13 = input.LA(1);

                         
                        int index4_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_13);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_14 = input.LA(1);

                         
                        int index4_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_14);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA4_12 = input.LA(1);

                         
                        int index4_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_12);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA4_19 = input.LA(1);

                         
                        int index4_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_19);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA4_9 = input.LA(1);

                         
                        int index4_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA4_17 = input.LA(1);

                         
                        int index4_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_17);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA4_11 = input.LA(1);

                         
                        int index4_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA4_18 = input.LA(1);

                         
                        int index4_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_18);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA4_20 = input.LA(1);

                         
                        int index4_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_20);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA4_15 = input.LA(1);

                         
                        int index4_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_15);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA4_16 = input.LA(1);

                         
                        int index4_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_InternalSemver()) ) {s = 21;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index4_16);
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
    static final String dfa_8s = "\101\uffff";
    static final String dfa_9s = "\11\uffff\12\44\1\uffff\2\44\1\uffff\1\26\14\44\1\uffff\12\44\16\26\4\uffff";
    static final String dfa_10s = "\1\10\22\4\1\uffff\2\4\1\uffff\15\4\1\uffff\30\4\4\0";
    static final String dfa_11s = "\1\17\22\50\1\uffff\2\50\1\uffff\1\60\14\50\1\uffff\12\50\16\60\4\0";
    static final String dfa_12s = "\23\uffff\1\1\2\uffff\1\2\15\uffff\1\3\34\uffff";
    static final String dfa_13s = "\75\uffff\1\1\1\2\1\3\1\0}>";
    static final String[] dfa_14s = {
            "\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\25\1\12\1\uffff\1\11\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\23\uffff\2\26\1\24\1\23\2\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "",
            "\2\26\1\uffff\12\26\22\uffff\1\57\2\26\1\uffff\2\26\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\26\1\30\1\23\1\27\1\26",
            "",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\26\1\30\1\uffff\2\26",
            "\2\26\1\uffff\12\26\22\uffff\1\60\2\26\1\uffff\2\26\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
            "\1\62\1\64\1\uffff\1\63\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\26\22\uffff\1\75\1\76\1\61\1\uffff\1\77\1\100\7\uffff\1\26",
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
                        int LA5_64 = input.LA(1);

                         
                        int index5_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 19;}

                        else if ( (synpred6_InternalSemver()) ) {s = 22;}

                         
                        input.seek(index5_64);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_61 = input.LA(1);

                         
                        int index5_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 19;}

                        else if ( (synpred6_InternalSemver()) ) {s = 22;}

                         
                        input.seek(index5_61);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_62 = input.LA(1);

                         
                        int index5_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 19;}

                        else if ( (synpred6_InternalSemver()) ) {s = 22;}

                         
                        input.seek(index5_62);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_63 = input.LA(1);

                         
                        int index5_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_InternalSemver()) ) {s = 19;}

                        else if ( (synpred6_InternalSemver()) ) {s = 22;}

                         
                        input.seek(index5_63);
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
    static final String dfa_18s = "\1\57\1\45\1\uffff\1\45\1\0\1\45\1\uffff\1\45\2\0\1\uffff\3\45\1\47";
    static final String dfa_19s = "\2\uffff\1\1\3\uffff\1\3\3\uffff\1\2\4\uffff";
    static final String dfa_20s = "\4\uffff\1\3\1\2\2\uffff\1\0\1\1\5\uffff}>";
    static final String[] dfa_21s = {
            "\1\5\1\4\1\2\1\3\1\1\7\6\25\uffff\1\6\3\uffff\7\2",
            "\2\6\1\uffff\7\6\1\7\2\6\24\uffff\1\6",
            "",
            "\1\11\1\10\1\2\12\6\24\uffff\1\6",
            "\1\uffff",
            "\2\12\1\uffff\11\12\25\uffff\1\12",
            "",
            "\2\6\1\uffff\2\6\1\13\7\6\24\uffff\1\6",
            "\1\uffff",
            "\1\uffff",
            "",
            "\2\6\1\uffff\1\14\11\6\24\uffff\1\6",
            "\2\6\1\uffff\7\6\1\15\2\6\24\uffff\1\6",
            "\2\6\1\uffff\3\6\1\16\6\6\24\uffff\1\6",
            "\2\6\1\uffff\12\6\24\uffff\1\6\1\uffff\1\2"
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
                        int LA6_8 = input.LA(1);

                         
                        int index6_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index6_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA6_9 = input.LA(1);

                         
                        int index6_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index6_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA6_5 = input.LA(1);

                         
                        int index6_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA6_5>=RULE_DIGITS && LA6_5<=RULE_LETTER_X)||(LA6_5>=RULE_LETTER_V && LA6_5<=RULE_LETTER_OTHER)||LA6_5==37) ) {s = 10;}

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
    static final String dfa_22s = "\u00aa\uffff";
    static final String dfa_23s = "\2\uffff\3\1\3\uffff\35\1\2\uffff\14\1\2\uffff\14\1\1\uffff\47\1\1\uffff\30\1\1\uffff\47\1";
    static final String dfa_24s = "\1\4\1\uffff\3\20\4\4\3\20\31\4\1\uffff\34\4\3\20\112\4\3\20\30\4";
    static final String dfa_25s = "\1\57\1\uffff\3\61\1\6\2\45\35\61\1\uffff\1\6\14\61\2\45\14\61\1\45\47\61\1\6\30\61\1\45\47\61";
    static final String dfa_26s = "\1\uffff\1\1\43\uffff\1\2\u0084\uffff";
    static final String dfa_27s = "\u00aa\uffff}>";
    static final String[] dfa_28s = {
            "\1\4\1\2\1\3\1\1\41\uffff\7\1",
            "",
            "\1\10\23\uffff\1\5\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\5\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\5\1\6\1\7\12\uffff\1\1",
            "\1\13\1\11\1\12",
            "\1\15\1\17\1\uffff\1\16\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\25\uffff\1\14",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\25\uffff\1\30",
            "\4\1\10\uffff\1\44\24\uffff\1\45\3\uffff\7\1\1\uffff\1\1",
            "\1\10\23\uffff\1\46\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\46\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\46\1\6\1\7\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\4\1\10\uffff\1\44\24\uffff\1\45\3\uffff\7\1\1\uffff\1\1",
            "",
            "\1\104\1\102\1\103",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\12\uffff\1\1",
            "\1\106\1\110\1\uffff\1\107\1\111\1\112\1\113\1\114\1\115\1\116\1\117\1\120\25\uffff\1\105",
            "\1\122\1\124\1\uffff\1\123\1\125\1\126\1\127\1\130\1\131\1\132\1\133\1\134\25\uffff\1\121",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\13\uffff\1\1",
            "\1\136\1\140\1\uffff\1\137\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\25\uffff\1\135",
            "\1\10\23\uffff\1\151\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0091\1\u008f\1\u0090",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\12\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\13\uffff\1\1",
            "\1\u0093\1\u0095\1\uffff\1\u0094\1\u0096\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\25\uffff\1\u0092",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\13\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\12\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\12\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\13\uffff\1\1"
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
    static final String dfa_29s = "\66\uffff";
    static final String dfa_30s = "\3\uffff\30\35\3\uffff\30\35";
    static final String dfa_31s = "\1\45\1\4\1\uffff\31\4\2\uffff\30\4";
    static final String dfa_32s = "\1\46\1\45\1\uffff\30\61\1\45\2\uffff\30\61";
    static final String dfa_33s = "\2\uffff\1\2\31\uffff\1\3\1\1\30\uffff";
    static final String dfa_34s = "\66\uffff}>";
    static final String[] dfa_35s = {
            "\1\1\1\2",
            "\1\4\1\6\1\uffff\1\5\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\25\uffff\1\3",
            "",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\12\uffff\1\35",
            "\1\37\1\41\1\uffff\1\40\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\25\uffff\1\36",
            "",
            "",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\12\uffff\1\35"
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
            return "912:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );";
        }
    }
    static final String dfa_36s = "\4\uffff";
    static final String dfa_37s = "\2\2\2\uffff";
    static final String dfa_38s = "\2\20\2\uffff";
    static final String dfa_39s = "\2\61\2\uffff";
    static final String dfa_40s = "\2\uffff\1\2\1\1";
    static final String dfa_41s = "\4\uffff}>";
    static final String[] dfa_42s = {
            "\1\1\40\uffff\1\3",
            "\1\1\40\uffff\1\3",
            "",
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
            return "()* loopback of 2287:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*";
        }
    }
    static final String dfa_43s = "\5\uffff";
    static final String dfa_44s = "\1\1\1\uffff\2\1\1\uffff";
    static final String dfa_45s = "\1\20\1\uffff\2\4\1\uffff";
    static final String dfa_46s = "\1\61\1\uffff\2\61\1\uffff";
    static final String dfa_47s = "\1\uffff\1\2\2\uffff\1\1";
    static final String dfa_48s = "\5\uffff}>";
    static final String[] dfa_49s = {
            "\1\2\40\uffff\1\1",
            "",
            "\4\4\10\uffff\1\3\30\uffff\7\4\1\uffff\1\1",
            "\4\4\10\uffff\1\3\30\uffff\7\4\1\uffff\1\1",
            ""
    };

    static final short[] dfa_43 = DFA.unpackEncodedString(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final char[] dfa_45 = DFA.unpackEncodedStringToUnsignedChars(dfa_45s);
    static final char[] dfa_46 = DFA.unpackEncodedStringToUnsignedChars(dfa_46s);
    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final short[][] dfa_49 = unpackEncodedStringArray(dfa_49s);

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_43;
            this.eof = dfa_44;
            this.min = dfa_45;
            this.max = dfa_46;
            this.accept = dfa_47;
            this.special = dfa_48;
            this.transition = dfa_49;
        }
        public String getDescription() {
            return "()* loopback of 2678:2: ( rule__VersionRangeContraint__Group_2__0 )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000380000FFB2L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000FE00000000F0L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000380000FFB0L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x000001B80000FFB0L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000FE380000FFF0L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000FE00000001F0L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0002000000010000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0002000000010002L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000FE00000100F0L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000FE0000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000007000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x000000780000FFB0L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x000000780000FFB2L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x000001B80000FFB2L});

}