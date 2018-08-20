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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_DIGITS", "RULE_LETTER_X", "RULE_ASTERIX", "RULE_LETTER_V", "RULE_LETTER_S", "RULE_LETTER_M", "RULE_LETTER_R", "RULE_LETTER_F", "RULE_LETTER_I", "RULE_LETTER_L", "RULE_LETTER_E", "RULE_LETTER_OTHER", "RULE_WS", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'/'", "'.'", "'-'", "'+'", "':'", "'@'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'://'", "'#'", "'||'"
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
    // InternalSemver.g:520:1: rulePATH : ( ( rule__PATH__Group__0 ) ) ;
    public final void rulePATH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:524:2: ( ( ( rule__PATH__Group__0 ) ) )
            // InternalSemver.g:525:2: ( ( rule__PATH__Group__0 ) )
            {
            // InternalSemver.g:525:2: ( ( rule__PATH__Group__0 ) )
            // InternalSemver.g:526:3: ( rule__PATH__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getGroup()); 
            }
            // InternalSemver.g:527:3: ( rule__PATH__Group__0 )
            // InternalSemver.g:527:4: rule__PATH__Group__0
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
    // InternalSemver.g:536:1: entryRuleURL_PROTOCOL : ruleURL_PROTOCOL EOF ;
    public final void entryRuleURL_PROTOCOL() throws RecognitionException {
        try {
            // InternalSemver.g:537:1: ( ruleURL_PROTOCOL EOF )
            // InternalSemver.g:538:1: ruleURL_PROTOCOL EOF
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
    // InternalSemver.g:545:1: ruleURL_PROTOCOL : ( ( rule__URL_PROTOCOL__Group__0 ) ) ;
    public final void ruleURL_PROTOCOL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:549:2: ( ( ( rule__URL_PROTOCOL__Group__0 ) ) )
            // InternalSemver.g:550:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            {
            // InternalSemver.g:550:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            // InternalSemver.g:551:3: ( rule__URL_PROTOCOL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getGroup()); 
            }
            // InternalSemver.g:552:3: ( rule__URL_PROTOCOL__Group__0 )
            // InternalSemver.g:552:4: rule__URL_PROTOCOL__Group__0
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
    // InternalSemver.g:561:1: entryRuleURL : ruleURL EOF ;
    public final void entryRuleURL() throws RecognitionException {
        try {
            // InternalSemver.g:562:1: ( ruleURL EOF )
            // InternalSemver.g:563:1: ruleURL EOF
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
    // InternalSemver.g:570:1: ruleURL : ( ( rule__URL__Group__0 ) ) ;
    public final void ruleURL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:574:2: ( ( ( rule__URL__Group__0 ) ) )
            // InternalSemver.g:575:2: ( ( rule__URL__Group__0 ) )
            {
            // InternalSemver.g:575:2: ( ( rule__URL__Group__0 ) )
            // InternalSemver.g:576:3: ( rule__URL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup()); 
            }
            // InternalSemver.g:577:3: ( rule__URL__Group__0 )
            // InternalSemver.g:577:4: rule__URL__Group__0
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
    // InternalSemver.g:586:1: entryRuleTAG : ruleTAG EOF ;
    public final void entryRuleTAG() throws RecognitionException {
        try {
            // InternalSemver.g:587:1: ( ruleTAG EOF )
            // InternalSemver.g:588:1: ruleTAG EOF
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
    // InternalSemver.g:595:1: ruleTAG : ( ( rule__TAG__Group__0 ) ) ;
    public final void ruleTAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:599:2: ( ( ( rule__TAG__Group__0 ) ) )
            // InternalSemver.g:600:2: ( ( rule__TAG__Group__0 ) )
            {
            // InternalSemver.g:600:2: ( ( rule__TAG__Group__0 ) )
            // InternalSemver.g:601:3: ( rule__TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getGroup()); 
            }
            // InternalSemver.g:602:3: ( rule__TAG__Group__0 )
            // InternalSemver.g:602:4: rule__TAG__Group__0
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
    // InternalSemver.g:611:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSemver.g:612:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:613:1: ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:620:1: ruleALPHA_NUMERIC_CHARS : ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:624:2: ( ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) )
            // InternalSemver.g:625:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            {
            // InternalSemver.g:625:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            // InternalSemver.g:626:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            {
            // InternalSemver.g:626:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) )
            // InternalSemver.g:627:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:628:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            // InternalSemver.g:628:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
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

            // InternalSemver.g:631:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            // InternalSemver.g:632:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:633:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_DIGITS && LA1_0<=RULE_LETTER_X)||(LA1_0>=RULE_LETTER_V && LA1_0<=RULE_LETTER_OTHER)||LA1_0==37) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSemver.g:633:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS"
    // InternalSemver.g:643:1: entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        try {
            // InternalSemver.g:644:1: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF )
            // InternalSemver.g:645:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF
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
    // InternalSemver.g:652:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:656:2: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) )
            // InternalSemver.g:657:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            {
            // InternalSemver.g:657:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            // InternalSemver.g:658:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup()); 
            }
            // InternalSemver.g:659:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            // InternalSemver.g:659:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0
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
    // InternalSemver.g:668:1: entryRuleWILDCARD : ruleWILDCARD EOF ;
    public final void entryRuleWILDCARD() throws RecognitionException {
        try {
            // InternalSemver.g:669:1: ( ruleWILDCARD EOF )
            // InternalSemver.g:670:1: ruleWILDCARD EOF
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
    // InternalSemver.g:677:1: ruleWILDCARD : ( ( rule__WILDCARD__Alternatives ) ) ;
    public final void ruleWILDCARD() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:681:2: ( ( ( rule__WILDCARD__Alternatives ) ) )
            // InternalSemver.g:682:2: ( ( rule__WILDCARD__Alternatives ) )
            {
            // InternalSemver.g:682:2: ( ( rule__WILDCARD__Alternatives ) )
            // InternalSemver.g:683:3: ( rule__WILDCARD__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWILDCARDAccess().getAlternatives()); 
            }
            // InternalSemver.g:684:3: ( rule__WILDCARD__Alternatives )
            // InternalSemver.g:684:4: rule__WILDCARD__Alternatives
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
    // InternalSemver.g:694:1: ruleLETTER : ( ( rule__LETTER__Alternatives ) ) ;
    public final void ruleLETTER() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:698:2: ( ( ( rule__LETTER__Alternatives ) ) )
            // InternalSemver.g:699:2: ( ( rule__LETTER__Alternatives ) )
            {
            // InternalSemver.g:699:2: ( ( rule__LETTER__Alternatives ) )
            // InternalSemver.g:700:3: ( rule__LETTER__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTERAccess().getAlternatives()); 
            }
            // InternalSemver.g:701:3: ( rule__LETTER__Alternatives )
            // InternalSemver.g:701:4: rule__LETTER__Alternatives
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
    // InternalSemver.g:711:1: ruleLETTER_NO_VX : ( ( rule__LETTER_NO_VX__Alternatives ) ) ;
    public final void ruleLETTER_NO_VX() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:715:2: ( ( ( rule__LETTER_NO_VX__Alternatives ) ) )
            // InternalSemver.g:716:2: ( ( rule__LETTER_NO_VX__Alternatives ) )
            {
            // InternalSemver.g:716:2: ( ( rule__LETTER_NO_VX__Alternatives ) )
            // InternalSemver.g:717:3: ( rule__LETTER_NO_VX__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLETTER_NO_VXAccess().getAlternatives()); 
            }
            // InternalSemver.g:718:3: ( rule__LETTER_NO_VX__Alternatives )
            // InternalSemver.g:718:4: rule__LETTER_NO_VX__Alternatives
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
    // InternalSemver.g:727:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:731:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSemver.g:732:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSemver.g:732:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSemver.g:733:3: ( rule__VersionComparator__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            }
            // InternalSemver.g:734:3: ( rule__VersionComparator__Alternatives )
            // InternalSemver.g:734:4: rule__VersionComparator__Alternatives
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
    // InternalSemver.g:742:1: rule__NPMVersionRequirement__Alternatives : ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) );
    public final void rule__NPMVersionRequirement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:746:1: ( ( ( rule__NPMVersionRequirement__Group_0__0 ) ) | ( ( rule__NPMVersionRequirement__Group_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==EOF||(LA2_0>=RULE_DIGITS && LA2_0<=RULE_LETTER_V)||LA2_0==RULE_WS||(LA2_0>=41 && LA2_0<=47)) ) {
                alt2=1;
            }
            else if ( ((LA2_0>=RULE_LETTER_S && LA2_0<=RULE_LETTER_OTHER)) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalSemver.g:747:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    {
                    // InternalSemver.g:747:2: ( ( rule__NPMVersionRequirement__Group_0__0 ) )
                    // InternalSemver.g:748:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:749:3: ( rule__NPMVersionRequirement__Group_0__0 )
                    // InternalSemver.g:749:4: rule__NPMVersionRequirement__Group_0__0
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
                    // InternalSemver.g:753:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    {
                    // InternalSemver.g:753:2: ( ( rule__NPMVersionRequirement__Group_1__0 ) )
                    // InternalSemver.g:754:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:755:3: ( rule__NPMVersionRequirement__Group_1__0 )
                    // InternalSemver.g:755:4: rule__NPMVersionRequirement__Group_1__0
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
    // InternalSemver.g:763:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ruleURLVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );
    public final void rule__NPMVersionRequirement__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:767:1: ( ( ( ruleLocalPathVersionRequirement ) ) | ( ruleURLVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) )
            int alt3=4;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // InternalSemver.g:768:2: ( ( ruleLocalPathVersionRequirement ) )
                    {
                    // InternalSemver.g:768:2: ( ( ruleLocalPathVersionRequirement ) )
                    // InternalSemver.g:769:3: ( ruleLocalPathVersionRequirement )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
                    }
                    // InternalSemver.g:770:3: ( ruleLocalPathVersionRequirement )
                    // InternalSemver.g:770:4: ruleLocalPathVersionRequirement
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
                    // InternalSemver.g:774:2: ( ruleURLVersionRequirement )
                    {
                    // InternalSemver.g:774:2: ( ruleURLVersionRequirement )
                    // InternalSemver.g:775:3: ruleURLVersionRequirement
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
                    // InternalSemver.g:780:2: ( ruleGitHubVersionRequirement )
                    {
                    // InternalSemver.g:780:2: ( ruleGitHubVersionRequirement )
                    // InternalSemver.g:781:3: ruleGitHubVersionRequirement
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
                    // InternalSemver.g:786:2: ( ruleTagVersionRequirement )
                    {
                    // InternalSemver.g:786:2: ( ruleTagVersionRequirement )
                    // InternalSemver.g:787:3: ruleTagVersionRequirement
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
    // InternalSemver.g:796:1: rule__URLVersionSpecifier__Alternatives : ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) );
    public final void rule__URLVersionSpecifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:800:1: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) )
            int alt4=3;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // InternalSemver.g:801:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:801:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    // InternalSemver.g:802:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:803:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    // InternalSemver.g:803:4: rule__URLVersionSpecifier__Group_0__0
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
                    // InternalSemver.g:807:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:807:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    // InternalSemver.g:808:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:809:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    // InternalSemver.g:809:4: rule__URLVersionSpecifier__Group_1__0
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
                    // InternalSemver.g:813:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:813:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    // InternalSemver.g:814:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:815:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    // InternalSemver.g:815:4: rule__URLVersionSpecifier__Group_2__0
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
    // InternalSemver.g:823:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:827:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSemver.g:828:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSemver.g:828:2: ( ruleVersionRangeContraint )
                    // InternalSemver.g:829:3: ruleVersionRangeContraint
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
                    // InternalSemver.g:834:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSemver.g:834:2: ( ruleHyphenVersionRange )
                    // InternalSemver.g:835:3: ruleHyphenVersionRange
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
    // InternalSemver.g:844:1: rule__VersionPart__Alternatives : ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) );
    public final void rule__VersionPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:848:1: ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) )
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
                    // InternalSemver.g:849:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    {
                    // InternalSemver.g:849:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    // InternalSemver.g:850:3: ( rule__VersionPart__WildcardAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    }
                    // InternalSemver.g:851:3: ( rule__VersionPart__WildcardAssignment_0 )
                    // InternalSemver.g:851:4: rule__VersionPart__WildcardAssignment_0
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
                    // InternalSemver.g:855:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    {
                    // InternalSemver.g:855:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    // InternalSemver.g:856:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    }
                    // InternalSemver.g:857:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    // InternalSemver.g:857:4: rule__VersionPart__NumberRawAssignment_1
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
    // InternalSemver.g:865:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:869:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt7=3;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:870:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:870:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSemver.g:871:3: ( rule__Qualifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:872:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSemver.g:872:4: rule__Qualifier__Group_0__0
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
                    // InternalSemver.g:876:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:876:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSemver.g:877:3: ( rule__Qualifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:878:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSemver.g:878:4: rule__Qualifier__Group_1__0
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
                    // InternalSemver.g:882:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:882:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSemver.g:883:3: ( rule__Qualifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:884:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSemver.g:884:4: rule__Qualifier__Group_2__0
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
    // InternalSemver.g:892:1: rule__PATH__Alternatives_0 : ( ( ruleLETTER_NO_VX ) | ( '/' ) | ( '.' ) );
    public final void rule__PATH__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:896:1: ( ( ruleLETTER_NO_VX ) | ( '/' ) | ( '.' ) )
            int alt8=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_S:
            case RULE_LETTER_M:
            case RULE_LETTER_R:
            case RULE_LETTER_F:
            case RULE_LETTER_I:
            case RULE_LETTER_L:
            case RULE_LETTER_E:
            case RULE_LETTER_OTHER:
                {
                alt8=1;
                }
                break;
            case 35:
                {
                alt8=2;
                }
                break;
            case 36:
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
                    // InternalSemver.g:897:2: ( ruleLETTER_NO_VX )
                    {
                    // InternalSemver.g:897:2: ( ruleLETTER_NO_VX )
                    // InternalSemver.g:898:3: ruleLETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTER_NO_VXParserRuleCall_0_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER_NO_VX();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTER_NO_VXParserRuleCall_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:903:2: ( '/' )
                    {
                    // InternalSemver.g:903:2: ( '/' )
                    // InternalSemver.g:904:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_0_1()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:909:2: ( '.' )
                    {
                    // InternalSemver.g:909:2: ( '.' )
                    // InternalSemver.g:910:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_0_2()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:919:1: rule__PATH__Alternatives_1 : ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__PATH__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:923:1: ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt9=5;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt9=1;
                }
                break;
            case 36:
                {
                alt9=2;
                }
                break;
            case 37:
                {
                alt9=3;
                }
                break;
            case RULE_DIGITS:
                {
                alt9=4;
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
                alt9=5;
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
                    // InternalSemver.g:924:2: ( '/' )
                    {
                    // InternalSemver.g:924:2: ( '/' )
                    // InternalSemver.g:925:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_1_0()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:930:2: ( '.' )
                    {
                    // InternalSemver.g:930:2: ( '.' )
                    // InternalSemver.g:931:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_1_1()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:936:2: ( '-' )
                    {
                    // InternalSemver.g:936:2: ( '-' )
                    // InternalSemver.g:937:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getHyphenMinusKeyword_1_2()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getHyphenMinusKeyword_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:942:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:942:2: ( RULE_DIGITS )
                    // InternalSemver.g:943:3: RULE_DIGITS
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
                    // InternalSemver.g:948:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:948:2: ( ruleLETTER )
                    // InternalSemver.g:949:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getLETTERParserRuleCall_1_4()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getLETTERParserRuleCall_1_4()); 
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
    // InternalSemver.g:958:1: rule__URL_PROTOCOL__Alternatives_1 : ( ( ruleLETTER ) | ( '+' ) );
    public final void rule__URL_PROTOCOL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:962:1: ( ( ruleLETTER ) | ( '+' ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_LETTER_X||(LA10_0>=RULE_LETTER_V && LA10_0<=RULE_LETTER_OTHER)) ) {
                alt10=1;
            }
            else if ( (LA10_0==38) ) {
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
                    // InternalSemver.g:963:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:963:2: ( ruleLETTER )
                    // InternalSemver.g:964:3: ruleLETTER
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
                    // InternalSemver.g:969:2: ( '+' )
                    {
                    // InternalSemver.g:969:2: ( '+' )
                    // InternalSemver.g:970:3: '+'
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


    // $ANTLR start "rule__URL__Alternatives_1"
    // InternalSemver.g:979:1: rule__URL__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:983:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt11=3;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt11=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt11=2;
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
                alt11=3;
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
                    // InternalSemver.g:984:2: ( '-' )
                    {
                    // InternalSemver.g:984:2: ( '-' )
                    // InternalSemver.g:985:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:990:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:990:2: ( RULE_DIGITS )
                    // InternalSemver.g:991:3: RULE_DIGITS
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
                    // InternalSemver.g:996:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:996:2: ( ruleLETTER )
                    // InternalSemver.g:997:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTERParserRuleCall_1_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTERParserRuleCall_1_2()); 
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
    // InternalSemver.g:1006:1: rule__URL__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1010:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt12=4;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt12=1;
                }
                break;
            case 36:
                {
                alt12=2;
                }
                break;
            case 39:
                {
                alt12=3;
                }
                break;
            case 40:
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
                    // InternalSemver.g:1011:2: ( '/' )
                    {
                    // InternalSemver.g:1011:2: ( '/' )
                    // InternalSemver.g:1012:3: '/'
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
                    // InternalSemver.g:1017:2: ( '.' )
                    {
                    // InternalSemver.g:1017:2: ( '.' )
                    // InternalSemver.g:1018:3: '.'
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
                    // InternalSemver.g:1023:2: ( ':' )
                    {
                    // InternalSemver.g:1023:2: ( ':' )
                    // InternalSemver.g:1024:3: ':'
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
                    // InternalSemver.g:1029:2: ( '@' )
                    {
                    // InternalSemver.g:1029:2: ( '@' )
                    // InternalSemver.g:1030:3: '@'
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

            }
        }
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
    // InternalSemver.g:1039:1: rule__URL__Alternatives_3 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__URL__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1043:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt13=7;
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
            case 37:
                {
                alt13=5;
                }
                break;
            case RULE_DIGITS:
                {
                alt13=6;
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
                alt13=7;
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
                    // InternalSemver.g:1044:2: ( '/' )
                    {
                    // InternalSemver.g:1044:2: ( '/' )
                    // InternalSemver.g:1045:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_3_0()); 
                    }
                    match(input,35,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1050:2: ( '.' )
                    {
                    // InternalSemver.g:1050:2: ( '.' )
                    // InternalSemver.g:1051:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_3_1()); 
                    }
                    match(input,36,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_3_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1056:2: ( ':' )
                    {
                    // InternalSemver.g:1056:2: ( ':' )
                    // InternalSemver.g:1057:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_3_2()); 
                    }
                    match(input,39,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_3_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1062:2: ( '@' )
                    {
                    // InternalSemver.g:1062:2: ( '@' )
                    // InternalSemver.g:1063:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_3_3()); 
                    }
                    match(input,40,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_3_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1068:2: ( '-' )
                    {
                    // InternalSemver.g:1068:2: ( '-' )
                    // InternalSemver.g:1069:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_3_4()); 
                    }
                    match(input,37,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_3_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1074:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1074:2: ( RULE_DIGITS )
                    // InternalSemver.g:1075:3: RULE_DIGITS
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
                    // InternalSemver.g:1080:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1080:2: ( ruleLETTER )
                    // InternalSemver.g:1081:3: ruleLETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getLETTERParserRuleCall_3_6()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLETTER();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getLETTERParserRuleCall_3_6()); 
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


    // $ANTLR start "rule__TAG__Alternatives_1"
    // InternalSemver.g:1090:1: rule__TAG__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__TAG__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1094:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt14=3;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt14=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt14=2;
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
                alt14=3;
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
                    // InternalSemver.g:1095:2: ( '-' )
                    {
                    // InternalSemver.g:1095:2: ( '-' )
                    // InternalSemver.g:1096:3: '-'
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
                    // InternalSemver.g:1101:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1101:2: ( RULE_DIGITS )
                    // InternalSemver.g:1102:3: RULE_DIGITS
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
                    // InternalSemver.g:1107:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1107:2: ( ruleLETTER )
                    // InternalSemver.g:1108:3: ruleLETTER
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
    // InternalSemver.g:1117:1: rule__ALPHA_NUMERIC_CHARS__Alternatives : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__ALPHA_NUMERIC_CHARS__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1121:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
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
                    // InternalSemver.g:1122:2: ( '-' )
                    {
                    // InternalSemver.g:1122:2: ( '-' )
                    // InternalSemver.g:1123:3: '-'
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
                    // InternalSemver.g:1128:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1128:2: ( RULE_DIGITS )
                    // InternalSemver.g:1129:3: RULE_DIGITS
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
                    // InternalSemver.g:1134:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1134:2: ( ruleLETTER )
                    // InternalSemver.g:1135:3: ruleLETTER
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
    // InternalSemver.g:1144:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) );
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1148:1: ( ( '-' ) | ( RULE_DIGITS ) | ( ruleLETTER ) )
            int alt16=3;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt16=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt16=2;
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
                alt16=3;
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
                    // InternalSemver.g:1149:2: ( '-' )
                    {
                    // InternalSemver.g:1149:2: ( '-' )
                    // InternalSemver.g:1150:3: '-'
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
                    // InternalSemver.g:1155:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1155:2: ( RULE_DIGITS )
                    // InternalSemver.g:1156:3: RULE_DIGITS
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
                    // InternalSemver.g:1161:2: ( ruleLETTER )
                    {
                    // InternalSemver.g:1161:2: ( ruleLETTER )
                    // InternalSemver.g:1162:3: ruleLETTER
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
    // InternalSemver.g:1171:1: rule__WILDCARD__Alternatives : ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) );
    public final void rule__WILDCARD__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1175:1: ( ( RULE_LETTER_X ) | ( RULE_ASTERIX ) )
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
                    // InternalSemver.g:1176:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1176:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1177:3: RULE_LETTER_X
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
                    // InternalSemver.g:1182:2: ( RULE_ASTERIX )
                    {
                    // InternalSemver.g:1182:2: ( RULE_ASTERIX )
                    // InternalSemver.g:1183:3: RULE_ASTERIX
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
    // InternalSemver.g:1192:1: rule__LETTER__Alternatives : ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) );
    public final void rule__LETTER__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1196:1: ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( ruleLETTER_NO_VX ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_V:
                {
                alt18=1;
                }
                break;
            case RULE_LETTER_X:
                {
                alt18=2;
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
                    // InternalSemver.g:1197:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1197:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1198:3: RULE_LETTER_V
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
                    // InternalSemver.g:1203:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1203:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1204:3: RULE_LETTER_X
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
                    // InternalSemver.g:1209:2: ( ruleLETTER_NO_VX )
                    {
                    // InternalSemver.g:1209:2: ( ruleLETTER_NO_VX )
                    // InternalSemver.g:1210:3: ruleLETTER_NO_VX
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
    // InternalSemver.g:1219:1: rule__LETTER_NO_VX__Alternatives : ( ( RULE_LETTER_S ) | ( RULE_LETTER_M ) | ( RULE_LETTER_R ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_L ) | ( RULE_LETTER_E ) | ( RULE_LETTER_OTHER ) );
    public final void rule__LETTER_NO_VX__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1223:1: ( ( RULE_LETTER_S ) | ( RULE_LETTER_M ) | ( RULE_LETTER_R ) | ( RULE_LETTER_F ) | ( RULE_LETTER_I ) | ( RULE_LETTER_L ) | ( RULE_LETTER_E ) | ( RULE_LETTER_OTHER ) )
            int alt19=8;
            switch ( input.LA(1) ) {
            case RULE_LETTER_S:
                {
                alt19=1;
                }
                break;
            case RULE_LETTER_M:
                {
                alt19=2;
                }
                break;
            case RULE_LETTER_R:
                {
                alt19=3;
                }
                break;
            case RULE_LETTER_F:
                {
                alt19=4;
                }
                break;
            case RULE_LETTER_I:
                {
                alt19=5;
                }
                break;
            case RULE_LETTER_L:
                {
                alt19=6;
                }
                break;
            case RULE_LETTER_E:
                {
                alt19=7;
                }
                break;
            case RULE_LETTER_OTHER:
                {
                alt19=8;
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
                    // InternalSemver.g:1224:2: ( RULE_LETTER_S )
                    {
                    // InternalSemver.g:1224:2: ( RULE_LETTER_S )
                    // InternalSemver.g:1225:3: RULE_LETTER_S
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
                    // InternalSemver.g:1230:2: ( RULE_LETTER_M )
                    {
                    // InternalSemver.g:1230:2: ( RULE_LETTER_M )
                    // InternalSemver.g:1231:3: RULE_LETTER_M
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
                    // InternalSemver.g:1236:2: ( RULE_LETTER_R )
                    {
                    // InternalSemver.g:1236:2: ( RULE_LETTER_R )
                    // InternalSemver.g:1237:3: RULE_LETTER_R
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
                    // InternalSemver.g:1242:2: ( RULE_LETTER_F )
                    {
                    // InternalSemver.g:1242:2: ( RULE_LETTER_F )
                    // InternalSemver.g:1243:3: RULE_LETTER_F
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
                    // InternalSemver.g:1248:2: ( RULE_LETTER_I )
                    {
                    // InternalSemver.g:1248:2: ( RULE_LETTER_I )
                    // InternalSemver.g:1249:3: RULE_LETTER_I
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
                    // InternalSemver.g:1254:2: ( RULE_LETTER_L )
                    {
                    // InternalSemver.g:1254:2: ( RULE_LETTER_L )
                    // InternalSemver.g:1255:3: RULE_LETTER_L
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
                    // InternalSemver.g:1260:2: ( RULE_LETTER_E )
                    {
                    // InternalSemver.g:1260:2: ( RULE_LETTER_E )
                    // InternalSemver.g:1261:3: RULE_LETTER_E
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
                    // InternalSemver.g:1266:2: ( RULE_LETTER_OTHER )
                    {
                    // InternalSemver.g:1266:2: ( RULE_LETTER_OTHER )
                    // InternalSemver.g:1267:3: RULE_LETTER_OTHER
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
    // InternalSemver.g:1276:1: rule__VersionComparator__Alternatives : ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1280:1: ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt20=7;
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
            case 43:
                {
                alt20=3;
                }
                break;
            case 44:
                {
                alt20=4;
                }
                break;
            case 45:
                {
                alt20=5;
                }
                break;
            case 46:
                {
                alt20=6;
                }
                break;
            case 47:
                {
                alt20=7;
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
                    // InternalSemver.g:1281:2: ( ( '=' ) )
                    {
                    // InternalSemver.g:1281:2: ( ( '=' ) )
                    // InternalSemver.g:1282:3: ( '=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }
                    // InternalSemver.g:1283:3: ( '=' )
                    // InternalSemver.g:1283:4: '='
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
                    // InternalSemver.g:1287:2: ( ( '<' ) )
                    {
                    // InternalSemver.g:1287:2: ( ( '<' ) )
                    // InternalSemver.g:1288:3: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }
                    // InternalSemver.g:1289:3: ( '<' )
                    // InternalSemver.g:1289:4: '<'
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
                    // InternalSemver.g:1293:2: ( ( '~' ) )
                    {
                    // InternalSemver.g:1293:2: ( ( '~' ) )
                    // InternalSemver.g:1294:3: ( '~' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }
                    // InternalSemver.g:1295:3: ( '~' )
                    // InternalSemver.g:1295:4: '~'
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
                    // InternalSemver.g:1299:2: ( ( '^' ) )
                    {
                    // InternalSemver.g:1299:2: ( ( '^' ) )
                    // InternalSemver.g:1300:3: ( '^' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }
                    // InternalSemver.g:1301:3: ( '^' )
                    // InternalSemver.g:1301:4: '^'
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
                    // InternalSemver.g:1305:2: ( ( '<=' ) )
                    {
                    // InternalSemver.g:1305:2: ( ( '<=' ) )
                    // InternalSemver.g:1306:3: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }
                    // InternalSemver.g:1307:3: ( '<=' )
                    // InternalSemver.g:1307:4: '<='
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
                    // InternalSemver.g:1311:2: ( ( '>' ) )
                    {
                    // InternalSemver.g:1311:2: ( ( '>' ) )
                    // InternalSemver.g:1312:3: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }
                    // InternalSemver.g:1313:3: ( '>' )
                    // InternalSemver.g:1313:4: '>'
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
                    // InternalSemver.g:1317:2: ( ( '>=' ) )
                    {
                    // InternalSemver.g:1317:2: ( ( '>=' ) )
                    // InternalSemver.g:1318:3: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); 
                    }
                    // InternalSemver.g:1319:3: ( '>=' )
                    // InternalSemver.g:1319:4: '>='
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
    // InternalSemver.g:1327:1: rule__NPMVersionRequirement__Group_0__0 : rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 ;
    public final void rule__NPMVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1331:1: ( rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 )
            // InternalSemver.g:1332:2: rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1
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
    // InternalSemver.g:1339:1: rule__NPMVersionRequirement__Group_0__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1343:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1344:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1344:1: ( ( RULE_WS )* )
            // InternalSemver.g:1345:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }
            // InternalSemver.g:1346:2: ( RULE_WS )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_WS) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalSemver.g:1346:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop21;
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
    // InternalSemver.g:1354:1: rule__NPMVersionRequirement__Group_0__1 : rule__NPMVersionRequirement__Group_0__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1358:1: ( rule__NPMVersionRequirement__Group_0__1__Impl )
            // InternalSemver.g:1359:2: rule__NPMVersionRequirement__Group_0__1__Impl
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
    // InternalSemver.g:1365:1: rule__NPMVersionRequirement__Group_0__1__Impl : ( ruleVersionRangeSetRequirement ) ;
    public final void rule__NPMVersionRequirement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1369:1: ( ( ruleVersionRangeSetRequirement ) )
            // InternalSemver.g:1370:1: ( ruleVersionRangeSetRequirement )
            {
            // InternalSemver.g:1370:1: ( ruleVersionRangeSetRequirement )
            // InternalSemver.g:1371:2: ruleVersionRangeSetRequirement
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
    // InternalSemver.g:1381:1: rule__NPMVersionRequirement__Group_1__0 : rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 ;
    public final void rule__NPMVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1385:1: ( rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 )
            // InternalSemver.g:1386:2: rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1
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
    // InternalSemver.g:1393:1: rule__NPMVersionRequirement__Group_1__0__Impl : ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) ;
    public final void rule__NPMVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1397:1: ( ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) )
            // InternalSemver.g:1398:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            {
            // InternalSemver.g:1398:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            // InternalSemver.g:1399:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:1400:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            // InternalSemver.g:1400:3: rule__NPMVersionRequirement__Alternatives_1_0
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
    // InternalSemver.g:1408:1: rule__NPMVersionRequirement__Group_1__1 : rule__NPMVersionRequirement__Group_1__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1412:1: ( rule__NPMVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1413:2: rule__NPMVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1419:1: rule__NPMVersionRequirement__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1423:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1424:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1424:1: ( ( RULE_WS )* )
            // InternalSemver.g:1425:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:1426:2: ( RULE_WS )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==RULE_WS) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalSemver.g:1426:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop22;
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
    // InternalSemver.g:1435:1: rule__LocalPathVersionRequirement__Group__0 : rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 ;
    public final void rule__LocalPathVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1439:1: ( rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 )
            // InternalSemver.g:1440:2: rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1
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
    // InternalSemver.g:1447:1: rule__LocalPathVersionRequirement__Group__0__Impl : ( ( rule__LocalPathVersionRequirement__Group_0__0 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1451:1: ( ( ( rule__LocalPathVersionRequirement__Group_0__0 ) ) )
            // InternalSemver.g:1452:1: ( ( rule__LocalPathVersionRequirement__Group_0__0 ) )
            {
            // InternalSemver.g:1452:1: ( ( rule__LocalPathVersionRequirement__Group_0__0 ) )
            // InternalSemver.g:1453:2: ( rule__LocalPathVersionRequirement__Group_0__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getGroup_0()); 
            }
            // InternalSemver.g:1454:2: ( rule__LocalPathVersionRequirement__Group_0__0 )
            // InternalSemver.g:1454:3: rule__LocalPathVersionRequirement__Group_0__0
            {
            pushFollow(FOLLOW_2);
            rule__LocalPathVersionRequirement__Group_0__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementAccess().getGroup_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:1462:1: rule__LocalPathVersionRequirement__Group__1 : rule__LocalPathVersionRequirement__Group__1__Impl ;
    public final void rule__LocalPathVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1466:1: ( rule__LocalPathVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1467:2: rule__LocalPathVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1473:1: rule__LocalPathVersionRequirement__Group__1__Impl : ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1477:1: ( ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) )
            // InternalSemver.g:1478:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            {
            // InternalSemver.g:1478:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            // InternalSemver.g:1479:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }
            // InternalSemver.g:1480:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            // InternalSemver.g:1480:3: rule__LocalPathVersionRequirement__LocalPathAssignment_1
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


    // $ANTLR start "rule__LocalPathVersionRequirement__Group_0__0"
    // InternalSemver.g:1489:1: rule__LocalPathVersionRequirement__Group_0__0 : rule__LocalPathVersionRequirement__Group_0__0__Impl ;
    public final void rule__LocalPathVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1493:1: ( rule__LocalPathVersionRequirement__Group_0__0__Impl )
            // InternalSemver.g:1494:2: rule__LocalPathVersionRequirement__Group_0__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LocalPathVersionRequirement__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalPathVersionRequirement__Group_0__0"


    // $ANTLR start "rule__LocalPathVersionRequirement__Group_0__0__Impl"
    // InternalSemver.g:1500:1: rule__LocalPathVersionRequirement__Group_0__0__Impl : ( ruleFILE_TAG ) ;
    public final void rule__LocalPathVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1504:1: ( ( ruleFILE_TAG ) )
            // InternalSemver.g:1505:1: ( ruleFILE_TAG )
            {
            // InternalSemver.g:1505:1: ( ruleFILE_TAG )
            // InternalSemver.g:1506:2: ruleFILE_TAG
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleFILE_TAG();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalPathVersionRequirement__Group_0__0__Impl"


    // $ANTLR start "rule__URLVersionRequirement__Group__0"
    // InternalSemver.g:1516:1: rule__URLVersionRequirement__Group__0 : rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 ;
    public final void rule__URLVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1520:1: ( rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 )
            // InternalSemver.g:1521:2: rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1
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
    // InternalSemver.g:1528:1: rule__URLVersionRequirement__Group__0__Impl : ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) ;
    public final void rule__URLVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1532:1: ( ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) )
            // InternalSemver.g:1533:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            {
            // InternalSemver.g:1533:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            // InternalSemver.g:1534:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }
            // InternalSemver.g:1535:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            // InternalSemver.g:1535:3: rule__URLVersionRequirement__ProtocolAssignment_0
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
    // InternalSemver.g:1543:1: rule__URLVersionRequirement__Group__1 : rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 ;
    public final void rule__URLVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1547:1: ( rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 )
            // InternalSemver.g:1548:2: rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2
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
    // InternalSemver.g:1555:1: rule__URLVersionRequirement__Group__1__Impl : ( ( '://' ) ) ;
    public final void rule__URLVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1559:1: ( ( ( '://' ) ) )
            // InternalSemver.g:1560:1: ( ( '://' ) )
            {
            // InternalSemver.g:1560:1: ( ( '://' ) )
            // InternalSemver.g:1561:2: ( '://' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getColonSolidusSolidusKeyword_1()); 
            }
            // InternalSemver.g:1562:2: ( '://' )
            // InternalSemver.g:1562:3: '://'
            {
            match(input,48,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1570:1: rule__URLVersionRequirement__Group__2 : rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 ;
    public final void rule__URLVersionRequirement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1574:1: ( rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 )
            // InternalSemver.g:1575:2: rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3
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
    // InternalSemver.g:1582:1: rule__URLVersionRequirement__Group__2__Impl : ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) ;
    public final void rule__URLVersionRequirement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1586:1: ( ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) )
            // InternalSemver.g:1587:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            {
            // InternalSemver.g:1587:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            // InternalSemver.g:1588:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }
            // InternalSemver.g:1589:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            // InternalSemver.g:1589:3: rule__URLVersionRequirement__UrlAssignment_2
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
    // InternalSemver.g:1597:1: rule__URLVersionRequirement__Group__3 : rule__URLVersionRequirement__Group__3__Impl ;
    public final void rule__URLVersionRequirement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1601:1: ( rule__URLVersionRequirement__Group__3__Impl )
            // InternalSemver.g:1602:2: rule__URLVersionRequirement__Group__3__Impl
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
    // InternalSemver.g:1608:1: rule__URLVersionRequirement__Group__3__Impl : ( ( rule__URLVersionRequirement__Group_3__0 )? ) ;
    public final void rule__URLVersionRequirement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1612:1: ( ( ( rule__URLVersionRequirement__Group_3__0 )? ) )
            // InternalSemver.g:1613:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            {
            // InternalSemver.g:1613:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            // InternalSemver.g:1614:2: ( rule__URLVersionRequirement__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }
            // InternalSemver.g:1615:2: ( rule__URLVersionRequirement__Group_3__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==49) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSemver.g:1615:3: rule__URLVersionRequirement__Group_3__0
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
    // InternalSemver.g:1624:1: rule__URLVersionRequirement__Group_3__0 : rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 ;
    public final void rule__URLVersionRequirement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1628:1: ( rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 )
            // InternalSemver.g:1629:2: rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1
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
    // InternalSemver.g:1636:1: rule__URLVersionRequirement__Group_3__0__Impl : ( '#' ) ;
    public final void rule__URLVersionRequirement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1640:1: ( ( '#' ) )
            // InternalSemver.g:1641:1: ( '#' )
            {
            // InternalSemver.g:1641:1: ( '#' )
            // InternalSemver.g:1642:2: '#'
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
    // InternalSemver.g:1651:1: rule__URLVersionRequirement__Group_3__1 : rule__URLVersionRequirement__Group_3__1__Impl ;
    public final void rule__URLVersionRequirement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1655:1: ( rule__URLVersionRequirement__Group_3__1__Impl )
            // InternalSemver.g:1656:2: rule__URLVersionRequirement__Group_3__1__Impl
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
    // InternalSemver.g:1662:1: rule__URLVersionRequirement__Group_3__1__Impl : ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) ;
    public final void rule__URLVersionRequirement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1666:1: ( ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) )
            // InternalSemver.g:1667:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            {
            // InternalSemver.g:1667:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            // InternalSemver.g:1668:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }
            // InternalSemver.g:1669:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            // InternalSemver.g:1669:3: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
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
    // InternalSemver.g:1678:1: rule__URLVersionSpecifier__Group_0__0 : rule__URLVersionSpecifier__Group_0__0__Impl ;
    public final void rule__URLVersionSpecifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1682:1: ( rule__URLVersionSpecifier__Group_0__0__Impl )
            // InternalSemver.g:1683:2: rule__URLVersionSpecifier__Group_0__0__Impl
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
    // InternalSemver.g:1689:1: rule__URLVersionSpecifier__Group_0__0__Impl : ( ruleURLSemver ) ;
    public final void rule__URLVersionSpecifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1693:1: ( ( ruleURLSemver ) )
            // InternalSemver.g:1694:1: ( ruleURLSemver )
            {
            // InternalSemver.g:1694:1: ( ruleURLSemver )
            // InternalSemver.g:1695:2: ruleURLSemver
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
    // InternalSemver.g:1705:1: rule__URLVersionSpecifier__Group_1__0 : rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 ;
    public final void rule__URLVersionSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1709:1: ( rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 )
            // InternalSemver.g:1710:2: rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1
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
    // InternalSemver.g:1717:1: rule__URLVersionSpecifier__Group_1__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1721:1: ( ( () ) )
            // InternalSemver.g:1722:1: ( () )
            {
            // InternalSemver.g:1722:1: ( () )
            // InternalSemver.g:1723:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); 
            }
            // InternalSemver.g:1724:2: ()
            // InternalSemver.g:1724:3: 
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
    // InternalSemver.g:1732:1: rule__URLVersionSpecifier__Group_1__1 : rule__URLVersionSpecifier__Group_1__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1736:1: ( rule__URLVersionSpecifier__Group_1__1__Impl )
            // InternalSemver.g:1737:2: rule__URLVersionSpecifier__Group_1__1__Impl
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
    // InternalSemver.g:1743:1: rule__URLVersionSpecifier__Group_1__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1747:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1748:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1748:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1749:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1750:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            // InternalSemver.g:1750:3: rule__URLVersionSpecifier__CommitISHAssignment_1_1
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
    // InternalSemver.g:1759:1: rule__URLVersionSpecifier__Group_2__0 : rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 ;
    public final void rule__URLVersionSpecifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1763:1: ( rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 )
            // InternalSemver.g:1764:2: rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1
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
    // InternalSemver.g:1771:1: rule__URLVersionSpecifier__Group_2__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1775:1: ( ( () ) )
            // InternalSemver.g:1776:1: ( () )
            {
            // InternalSemver.g:1776:1: ( () )
            // InternalSemver.g:1777:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); 
            }
            // InternalSemver.g:1778:2: ()
            // InternalSemver.g:1778:3: 
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
    // InternalSemver.g:1786:1: rule__URLVersionSpecifier__Group_2__1 : rule__URLVersionSpecifier__Group_2__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1790:1: ( rule__URLVersionSpecifier__Group_2__1__Impl )
            // InternalSemver.g:1791:2: rule__URLVersionSpecifier__Group_2__1__Impl
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
    // InternalSemver.g:1797:1: rule__URLVersionSpecifier__Group_2__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1801:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) )
            // InternalSemver.g:1802:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            {
            // InternalSemver.g:1802:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            // InternalSemver.g:1803:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); 
            }
            // InternalSemver.g:1804:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            // InternalSemver.g:1804:3: rule__URLVersionSpecifier__CommitISHAssignment_2_1
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
    // InternalSemver.g:1813:1: rule__URLSemver__Group__0 : rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 ;
    public final void rule__URLSemver__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1817:1: ( rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 )
            // InternalSemver.g:1818:2: rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1
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
    // InternalSemver.g:1825:1: rule__URLSemver__Group__0__Impl : ( () ) ;
    public final void rule__URLSemver__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1829:1: ( ( () ) )
            // InternalSemver.g:1830:1: ( () )
            {
            // InternalSemver.g:1830:1: ( () )
            // InternalSemver.g:1831:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); 
            }
            // InternalSemver.g:1832:2: ()
            // InternalSemver.g:1832:3: 
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
    // InternalSemver.g:1840:1: rule__URLSemver__Group__1 : rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 ;
    public final void rule__URLSemver__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1844:1: ( rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 )
            // InternalSemver.g:1845:2: rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2
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
    // InternalSemver.g:1852:1: rule__URLSemver__Group__1__Impl : ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) ;
    public final void rule__URLSemver__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1856:1: ( ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) )
            // InternalSemver.g:1857:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            {
            // InternalSemver.g:1857:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            // InternalSemver.g:1858:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); 
            }
            // InternalSemver.g:1859:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_LETTER_S) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSemver.g:1859:3: rule__URLSemver__WithSemverTagAssignment_1
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
    // InternalSemver.g:1867:1: rule__URLSemver__Group__2 : rule__URLSemver__Group__2__Impl ;
    public final void rule__URLSemver__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1871:1: ( rule__URLSemver__Group__2__Impl )
            // InternalSemver.g:1872:2: rule__URLSemver__Group__2__Impl
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
    // InternalSemver.g:1878:1: rule__URLSemver__Group__2__Impl : ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) ;
    public final void rule__URLSemver__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1882:1: ( ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) )
            // InternalSemver.g:1883:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            {
            // InternalSemver.g:1883:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            // InternalSemver.g:1884:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); 
            }
            // InternalSemver.g:1885:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            // InternalSemver.g:1885:3: rule__URLSemver__SimpleVersionAssignment_2
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
    // InternalSemver.g:1894:1: rule__GitHubVersionRequirement__Group__0 : rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 ;
    public final void rule__GitHubVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1898:1: ( rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 )
            // InternalSemver.g:1899:2: rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1
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
    // InternalSemver.g:1906:1: rule__GitHubVersionRequirement__Group__0__Impl : ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) ;
    public final void rule__GitHubVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1910:1: ( ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) )
            // InternalSemver.g:1911:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            {
            // InternalSemver.g:1911:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            // InternalSemver.g:1912:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }
            // InternalSemver.g:1913:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            // InternalSemver.g:1913:3: rule__GitHubVersionRequirement__GithubUrlAssignment_0
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
    // InternalSemver.g:1921:1: rule__GitHubVersionRequirement__Group__1 : rule__GitHubVersionRequirement__Group__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1925:1: ( rule__GitHubVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1926:2: rule__GitHubVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1932:1: rule__GitHubVersionRequirement__Group__1__Impl : ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) ;
    public final void rule__GitHubVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1936:1: ( ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) )
            // InternalSemver.g:1937:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:1937:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            // InternalSemver.g:1938:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1939:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==49) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSemver.g:1939:3: rule__GitHubVersionRequirement__Group_1__0
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
    // InternalSemver.g:1948:1: rule__GitHubVersionRequirement__Group_1__0 : rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 ;
    public final void rule__GitHubVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1952:1: ( rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 )
            // InternalSemver.g:1953:2: rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1
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
    // InternalSemver.g:1960:1: rule__GitHubVersionRequirement__Group_1__0__Impl : ( '#' ) ;
    public final void rule__GitHubVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1964:1: ( ( '#' ) )
            // InternalSemver.g:1965:1: ( '#' )
            {
            // InternalSemver.g:1965:1: ( '#' )
            // InternalSemver.g:1966:2: '#'
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
    // InternalSemver.g:1975:1: rule__GitHubVersionRequirement__Group_1__1 : rule__GitHubVersionRequirement__Group_1__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1979:1: ( rule__GitHubVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1980:2: rule__GitHubVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1986:1: rule__GitHubVersionRequirement__Group_1__1__Impl : ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) ;
    public final void rule__GitHubVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1990:1: ( ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1991:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1991:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1992:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1993:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            // InternalSemver.g:1993:3: rule__GitHubVersionRequirement__CommitISHAssignment_1_1
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
    // InternalSemver.g:2002:1: rule__VersionRangeSetRequirement__Group__0 : rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 ;
    public final void rule__VersionRangeSetRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2006:1: ( rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 )
            // InternalSemver.g:2007:2: rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1
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
    // InternalSemver.g:2014:1: rule__VersionRangeSetRequirement__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSetRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2018:1: ( ( () ) )
            // InternalSemver.g:2019:1: ( () )
            {
            // InternalSemver.g:2019:1: ( () )
            // InternalSemver.g:2020:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }
            // InternalSemver.g:2021:2: ()
            // InternalSemver.g:2021:3: 
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
    // InternalSemver.g:2029:1: rule__VersionRangeSetRequirement__Group__1 : rule__VersionRangeSetRequirement__Group__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2033:1: ( rule__VersionRangeSetRequirement__Group__1__Impl )
            // InternalSemver.g:2034:2: rule__VersionRangeSetRequirement__Group__1__Impl
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
    // InternalSemver.g:2040:1: rule__VersionRangeSetRequirement__Group__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) ;
    public final void rule__VersionRangeSetRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2044:1: ( ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) )
            // InternalSemver.g:2045:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:2045:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            // InternalSemver.g:2046:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:2047:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>=RULE_DIGITS && LA26_0<=RULE_LETTER_V)||(LA26_0>=41 && LA26_0<=47)) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalSemver.g:2047:3: rule__VersionRangeSetRequirement__Group_1__0
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
    // InternalSemver.g:2056:1: rule__VersionRangeSetRequirement__Group_1__0 : rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2060:1: ( rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 )
            // InternalSemver.g:2061:2: rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1
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
    // InternalSemver.g:2068:1: rule__VersionRangeSetRequirement__Group_1__0__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2072:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) )
            // InternalSemver.g:2073:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            {
            // InternalSemver.g:2073:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            // InternalSemver.g:2074:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }
            // InternalSemver.g:2075:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            // InternalSemver.g:2075:3: rule__VersionRangeSetRequirement__RangesAssignment_1_0
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
    // InternalSemver.g:2083:1: rule__VersionRangeSetRequirement__Group_1__1 : rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2087:1: ( rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 )
            // InternalSemver.g:2088:2: rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2
            {
            pushFollow(FOLLOW_14);
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
    // InternalSemver.g:2095:1: rule__VersionRangeSetRequirement__Group_1__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2099:1: ( ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) )
            // InternalSemver.g:2100:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            {
            // InternalSemver.g:2100:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            // InternalSemver.g:2101:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }
            // InternalSemver.g:2102:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            loop27:
            do {
                int alt27=2;
                alt27 = dfa27.predict(input);
                switch (alt27) {
            	case 1 :
            	    // InternalSemver.g:2102:3: rule__VersionRangeSetRequirement__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_15);
            	    rule__VersionRangeSetRequirement__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop27;
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
    // InternalSemver.g:2110:1: rule__VersionRangeSetRequirement__Group_1__2 : rule__VersionRangeSetRequirement__Group_1__2__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2114:1: ( rule__VersionRangeSetRequirement__Group_1__2__Impl )
            // InternalSemver.g:2115:2: rule__VersionRangeSetRequirement__Group_1__2__Impl
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
    // InternalSemver.g:2121:1: rule__VersionRangeSetRequirement__Group_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2125:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2126:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2126:1: ( ( RULE_WS )* )
            // InternalSemver.g:2127:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); 
            }
            // InternalSemver.g:2128:2: ( RULE_WS )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==RULE_WS) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSemver.g:2128:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop28;
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
    // InternalSemver.g:2137:1: rule__VersionRangeSetRequirement__Group_1_1__0 : rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2141:1: ( rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 )
            // InternalSemver.g:2142:2: rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1
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
    // InternalSemver.g:2149:1: rule__VersionRangeSetRequirement__Group_1_1__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2153:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2154:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2154:1: ( ( RULE_WS )* )
            // InternalSemver.g:2155:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }
            // InternalSemver.g:2156:2: ( RULE_WS )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==RULE_WS) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSemver.g:2156:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop29;
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
    // InternalSemver.g:2164:1: rule__VersionRangeSetRequirement__Group_1_1__1 : rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2168:1: ( rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 )
            // InternalSemver.g:2169:2: rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2
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
    // InternalSemver.g:2176:1: rule__VersionRangeSetRequirement__Group_1_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2180:1: ( ( '||' ) )
            // InternalSemver.g:2181:1: ( '||' )
            {
            // InternalSemver.g:2181:1: ( '||' )
            // InternalSemver.g:2182:2: '||'
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
    // InternalSemver.g:2191:1: rule__VersionRangeSetRequirement__Group_1_1__2 : rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2195:1: ( rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 )
            // InternalSemver.g:2196:2: rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3
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
    // InternalSemver.g:2203:1: rule__VersionRangeSetRequirement__Group_1_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2207:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2208:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2208:1: ( ( RULE_WS )* )
            // InternalSemver.g:2209:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }
            // InternalSemver.g:2210:2: ( RULE_WS )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_WS) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSemver.g:2210:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop30;
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
    // InternalSemver.g:2218:1: rule__VersionRangeSetRequirement__Group_1_1__3 : rule__VersionRangeSetRequirement__Group_1_1__3__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2222:1: ( rule__VersionRangeSetRequirement__Group_1_1__3__Impl )
            // InternalSemver.g:2223:2: rule__VersionRangeSetRequirement__Group_1_1__3__Impl
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
    // InternalSemver.g:2229:1: rule__VersionRangeSetRequirement__Group_1_1__3__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2233:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) )
            // InternalSemver.g:2234:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            {
            // InternalSemver.g:2234:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            // InternalSemver.g:2235:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }
            // InternalSemver.g:2236:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            // InternalSemver.g:2236:3: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
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
    // InternalSemver.g:2245:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2249:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemver.g:2250:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
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
    // InternalSemver.g:2257:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2261:1: ( ( () ) )
            // InternalSemver.g:2262:1: ( () )
            {
            // InternalSemver.g:2262:1: ( () )
            // InternalSemver.g:2263:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }
            // InternalSemver.g:2264:2: ()
            // InternalSemver.g:2264:3: 
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
    // InternalSemver.g:2272:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2276:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemver.g:2277:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
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
    // InternalSemver.g:2284:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2288:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemver.g:2289:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemver.g:2289:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemver.g:2290:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }
            // InternalSemver.g:2291:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemver.g:2291:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSemver.g:2299:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2303:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemver.g:2304:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
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
    // InternalSemver.g:2311:1: rule__HyphenVersionRange__Group__2__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2315:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2316:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2316:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2317:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2317:2: ( ( RULE_WS ) )
            // InternalSemver.g:2318:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2319:3: ( RULE_WS )
            // InternalSemver.g:2319:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }

            }

            // InternalSemver.g:2322:2: ( ( RULE_WS )* )
            // InternalSemver.g:2323:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2324:3: ( RULE_WS )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==RULE_WS) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:2324:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop31;
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
    // InternalSemver.g:2333:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2337:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSemver.g:2338:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
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
    // InternalSemver.g:2345:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2349:1: ( ( '-' ) )
            // InternalSemver.g:2350:1: ( '-' )
            {
            // InternalSemver.g:2350:1: ( '-' )
            // InternalSemver.g:2351:2: '-'
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
    // InternalSemver.g:2360:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2364:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSemver.g:2365:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
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
    // InternalSemver.g:2372:1: rule__HyphenVersionRange__Group__4__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2376:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2377:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2377:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2378:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2378:2: ( ( RULE_WS ) )
            // InternalSemver.g:2379:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2380:3: ( RULE_WS )
            // InternalSemver.g:2380:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }

            }

            // InternalSemver.g:2383:2: ( ( RULE_WS )* )
            // InternalSemver.g:2384:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2385:3: ( RULE_WS )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_WS) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:2385:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop32;
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
    // InternalSemver.g:2394:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2398:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSemver.g:2399:2: rule__HyphenVersionRange__Group__5__Impl
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
    // InternalSemver.g:2405:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2409:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSemver.g:2410:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSemver.g:2410:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSemver.g:2411:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }
            // InternalSemver.g:2412:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSemver.g:2412:3: rule__HyphenVersionRange__ToAssignment_5
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
    // InternalSemver.g:2421:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2425:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSemver.g:2426:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
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
    // InternalSemver.g:2433:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2437:1: ( ( () ) )
            // InternalSemver.g:2438:1: ( () )
            {
            // InternalSemver.g:2438:1: ( () )
            // InternalSemver.g:2439:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }
            // InternalSemver.g:2440:2: ()
            // InternalSemver.g:2440:3: 
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
    // InternalSemver.g:2448:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2452:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSemver.g:2453:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
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
    // InternalSemver.g:2460:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2464:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSemver.g:2465:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSemver.g:2465:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSemver.g:2466:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }
            // InternalSemver.g:2467:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSemver.g:2467:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
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
    // InternalSemver.g:2475:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2479:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSemver.g:2480:2: rule__VersionRangeContraint__Group__2__Impl
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
    // InternalSemver.g:2486:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2490:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSemver.g:2491:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSemver.g:2491:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSemver.g:2492:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }
            // InternalSemver.g:2493:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop33:
            do {
                int alt33=2;
                alt33 = dfa33.predict(input);
                switch (alt33) {
            	case 1 :
            	    // InternalSemver.g:2493:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__VersionRangeContraint__Group_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop33;
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
    // InternalSemver.g:2502:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2506:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSemver.g:2507:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
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
    // InternalSemver.g:2514:1: rule__VersionRangeContraint__Group_2__0__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2518:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2519:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2519:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2520:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2520:2: ( ( RULE_WS ) )
            // InternalSemver.g:2521:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2522:3: ( RULE_WS )
            // InternalSemver.g:2522:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }

            }

            // InternalSemver.g:2525:2: ( ( RULE_WS )* )
            // InternalSemver.g:2526:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2527:3: ( RULE_WS )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==RULE_WS) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalSemver.g:2527:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop34;
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
    // InternalSemver.g:2536:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2540:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSemver.g:2541:2: rule__VersionRangeContraint__Group_2__1__Impl
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
    // InternalSemver.g:2547:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2551:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSemver.g:2552:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSemver.g:2552:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSemver.g:2553:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }
            // InternalSemver.g:2554:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSemver.g:2554:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
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
    // InternalSemver.g:2563:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2567:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemver.g:2568:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
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
    // InternalSemver.g:2575:1: rule__SimpleVersion__Group__0__Impl : ( ( rule__SimpleVersion__Group_0__0 )* ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2579:1: ( ( ( rule__SimpleVersion__Group_0__0 )* ) )
            // InternalSemver.g:2580:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            {
            // InternalSemver.g:2580:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            // InternalSemver.g:2581:2: ( rule__SimpleVersion__Group_0__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup_0()); 
            }
            // InternalSemver.g:2582:2: ( rule__SimpleVersion__Group_0__0 )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( ((LA35_0>=41 && LA35_0<=47)) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSemver.g:2582:3: rule__SimpleVersion__Group_0__0
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__SimpleVersion__Group_0__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop35;
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
    // InternalSemver.g:2590:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2594:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemver.g:2595:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
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
    // InternalSemver.g:2602:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2606:1: ( ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) )
            // InternalSemver.g:2607:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            {
            // InternalSemver.g:2607:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            // InternalSemver.g:2608:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); 
            }
            // InternalSemver.g:2609:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_LETTER_V) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalSemver.g:2609:3: rule__SimpleVersion__WithLetterVAssignment_1
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
    // InternalSemver.g:2617:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2621:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSemver.g:2622:2: rule__SimpleVersion__Group__2__Impl
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
    // InternalSemver.g:2628:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2632:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSemver.g:2633:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSemver.g:2633:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSemver.g:2634:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            }
            // InternalSemver.g:2635:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSemver.g:2635:3: rule__SimpleVersion__NumberAssignment_2
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
    // InternalSemver.g:2644:1: rule__SimpleVersion__Group_0__0 : rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 ;
    public final void rule__SimpleVersion__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2648:1: ( rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 )
            // InternalSemver.g:2649:2: rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1
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
    // InternalSemver.g:2656:1: rule__SimpleVersion__Group_0__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) ;
    public final void rule__SimpleVersion__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2660:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) )
            // InternalSemver.g:2661:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            {
            // InternalSemver.g:2661:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            // InternalSemver.g:2662:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); 
            }
            // InternalSemver.g:2663:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            // InternalSemver.g:2663:3: rule__SimpleVersion__ComparatorsAssignment_0_0
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
    // InternalSemver.g:2671:1: rule__SimpleVersion__Group_0__1 : rule__SimpleVersion__Group_0__1__Impl ;
    public final void rule__SimpleVersion__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2675:1: ( rule__SimpleVersion__Group_0__1__Impl )
            // InternalSemver.g:2676:2: rule__SimpleVersion__Group_0__1__Impl
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
    // InternalSemver.g:2682:1: rule__SimpleVersion__Group_0__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__SimpleVersion__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2686:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2687:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2687:1: ( ( RULE_WS )* )
            // InternalSemver.g:2688:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); 
            }
            // InternalSemver.g:2689:2: ( RULE_WS )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==RULE_WS) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalSemver.g:2689:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop37;
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
    // InternalSemver.g:2698:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2702:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemver.g:2703:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
            {
            pushFollow(FOLLOW_19);
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
    // InternalSemver.g:2710:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2714:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemver.g:2715:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemver.g:2715:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemver.g:2716:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }
            // InternalSemver.g:2717:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemver.g:2717:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSemver.g:2725:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2729:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSemver.g:2730:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
            {
            pushFollow(FOLLOW_19);
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
    // InternalSemver.g:2737:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2741:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemver.g:2742:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemver.g:2742:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemver.g:2743:2: ( rule__VersionNumber__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }
            // InternalSemver.g:2744:2: ( rule__VersionNumber__Group_1__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==36) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalSemver.g:2744:3: rule__VersionNumber__Group_1__0
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
    // InternalSemver.g:2752:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2756:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSemver.g:2757:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSemver.g:2763:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2767:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSemver.g:2768:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSemver.g:2768:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSemver.g:2769:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }
            // InternalSemver.g:2770:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( ((LA39_0>=37 && LA39_0<=38)) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalSemver.g:2770:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSemver.g:2779:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2783:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemver.g:2784:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
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
    // InternalSemver.g:2791:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2795:1: ( ( '.' ) )
            // InternalSemver.g:2796:1: ( '.' )
            {
            // InternalSemver.g:2796:1: ( '.' )
            // InternalSemver.g:2797:2: '.'
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
    // InternalSemver.g:2806:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2810:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemver.g:2811:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
            {
            pushFollow(FOLLOW_20);
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
    // InternalSemver.g:2818:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2822:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemver.g:2823:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemver.g:2823:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemver.g:2824:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }
            // InternalSemver.g:2825:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemver.g:2825:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSemver.g:2833:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2837:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemver.g:2838:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSemver.g:2844:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2848:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemver.g:2849:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemver.g:2849:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemver.g:2850:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }
            // InternalSemver.g:2851:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==36) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalSemver.g:2851:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSemver.g:2860:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2864:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemver.g:2865:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
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
    // InternalSemver.g:2872:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2876:1: ( ( '.' ) )
            // InternalSemver.g:2877:1: ( '.' )
            {
            // InternalSemver.g:2877:1: ( '.' )
            // InternalSemver.g:2878:2: '.'
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
    // InternalSemver.g:2887:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2891:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemver.g:2892:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
            {
            pushFollow(FOLLOW_20);
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
    // InternalSemver.g:2899:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2903:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSemver.g:2904:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSemver.g:2904:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSemver.g:2905:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }
            // InternalSemver.g:2906:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSemver.g:2906:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSemver.g:2914:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2918:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemver.g:2919:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSemver.g:2925:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2929:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSemver.g:2930:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSemver.g:2930:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSemver.g:2931:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }
            // InternalSemver.g:2932:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==36) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSemver.g:2932:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_21);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop41;
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
    // InternalSemver.g:2941:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2945:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSemver.g:2946:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
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
    // InternalSemver.g:2953:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2957:1: ( ( '.' ) )
            // InternalSemver.g:2958:1: ( '.' )
            {
            // InternalSemver.g:2958:1: ( '.' )
            // InternalSemver.g:2959:2: '.'
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
    // InternalSemver.g:2968:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2972:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSemver.g:2973:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSemver.g:2979:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2983:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSemver.g:2984:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSemver.g:2984:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSemver.g:2985:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }
            // InternalSemver.g:2986:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSemver.g:2986:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSemver.g:2995:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2999:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemver.g:3000:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
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
    // InternalSemver.g:3007:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3011:1: ( ( '-' ) )
            // InternalSemver.g:3012:1: ( '-' )
            {
            // InternalSemver.g:3012:1: ( '-' )
            // InternalSemver.g:3013:2: '-'
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
    // InternalSemver.g:3022:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3026:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSemver.g:3027:2: rule__Qualifier__Group_0__1__Impl
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
    // InternalSemver.g:3033:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3037:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemver.g:3038:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemver.g:3038:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemver.g:3039:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }
            // InternalSemver.g:3040:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemver.g:3040:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSemver.g:3049:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3053:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemver.g:3054:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
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
    // InternalSemver.g:3061:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3065:1: ( ( '+' ) )
            // InternalSemver.g:3066:1: ( '+' )
            {
            // InternalSemver.g:3066:1: ( '+' )
            // InternalSemver.g:3067:2: '+'
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
    // InternalSemver.g:3076:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3080:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemver.g:3081:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSemver.g:3087:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3091:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemver.g:3092:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemver.g:3092:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemver.g:3093:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }
            // InternalSemver.g:3094:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemver.g:3094:3: rule__Qualifier__BuildMetadataAssignment_1_1
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
    // InternalSemver.g:3103:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3107:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSemver.g:3108:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
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
    // InternalSemver.g:3115:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3119:1: ( ( '-' ) )
            // InternalSemver.g:3120:1: ( '-' )
            {
            // InternalSemver.g:3120:1: ( '-' )
            // InternalSemver.g:3121:2: '-'
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
    // InternalSemver.g:3130:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3134:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSemver.g:3135:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
            {
            pushFollow(FOLLOW_22);
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
    // InternalSemver.g:3142:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3146:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSemver.g:3147:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSemver.g:3147:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSemver.g:3148:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            }
            // InternalSemver.g:3149:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSemver.g:3149:3: rule__Qualifier__PreReleaseAssignment_2_1
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
    // InternalSemver.g:3157:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3161:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSemver.g:3162:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
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
    // InternalSemver.g:3169:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3173:1: ( ( '+' ) )
            // InternalSemver.g:3174:1: ( '+' )
            {
            // InternalSemver.g:3174:1: ( '+' )
            // InternalSemver.g:3175:2: '+'
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
    // InternalSemver.g:3184:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3188:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSemver.g:3189:2: rule__Qualifier__Group_2__3__Impl
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
    // InternalSemver.g:3195:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3199:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSemver.g:3200:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSemver.g:3200:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSemver.g:3201:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            }
            // InternalSemver.g:3202:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSemver.g:3202:3: rule__Qualifier__BuildMetadataAssignment_2_3
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
    // InternalSemver.g:3211:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3215:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSemver.g:3216:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
            {
            pushFollow(FOLLOW_20);
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
    // InternalSemver.g:3223:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3227:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSemver.g:3228:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSemver.g:3228:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSemver.g:3229:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }
            // InternalSemver.g:3230:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSemver.g:3230:3: rule__QualifierTag__PartsAssignment_0
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
    // InternalSemver.g:3238:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3242:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSemver.g:3243:2: rule__QualifierTag__Group__1__Impl
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
    // InternalSemver.g:3249:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3253:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSemver.g:3254:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSemver.g:3254:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSemver.g:3255:2: ( rule__QualifierTag__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }
            // InternalSemver.g:3256:2: ( rule__QualifierTag__Group_1__0 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==36) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSemver.g:3256:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_21);
            	    rule__QualifierTag__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop42;
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
    // InternalSemver.g:3265:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3269:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSemver.g:3270:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
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
    // InternalSemver.g:3277:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3281:1: ( ( '.' ) )
            // InternalSemver.g:3282:1: ( '.' )
            {
            // InternalSemver.g:3282:1: ( '.' )
            // InternalSemver.g:3283:2: '.'
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
    // InternalSemver.g:3292:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3296:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSemver.g:3297:2: rule__QualifierTag__Group_1__1__Impl
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
    // InternalSemver.g:3303:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3307:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSemver.g:3308:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSemver.g:3308:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSemver.g:3309:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }
            // InternalSemver.g:3310:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSemver.g:3310:3: rule__QualifierTag__PartsAssignment_1_1
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
    // InternalSemver.g:3319:1: rule__FILE_TAG__Group__0 : rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 ;
    public final void rule__FILE_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3323:1: ( rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1 )
            // InternalSemver.g:3324:2: rule__FILE_TAG__Group__0__Impl rule__FILE_TAG__Group__1
            {
            pushFollow(FOLLOW_23);
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
    // InternalSemver.g:3331:1: rule__FILE_TAG__Group__0__Impl : ( RULE_LETTER_F ) ;
    public final void rule__FILE_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3335:1: ( ( RULE_LETTER_F ) )
            // InternalSemver.g:3336:1: ( RULE_LETTER_F )
            {
            // InternalSemver.g:3336:1: ( RULE_LETTER_F )
            // InternalSemver.g:3337:2: RULE_LETTER_F
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
    // InternalSemver.g:3346:1: rule__FILE_TAG__Group__1 : rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 ;
    public final void rule__FILE_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3350:1: ( rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2 )
            // InternalSemver.g:3351:2: rule__FILE_TAG__Group__1__Impl rule__FILE_TAG__Group__2
            {
            pushFollow(FOLLOW_24);
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
    // InternalSemver.g:3358:1: rule__FILE_TAG__Group__1__Impl : ( RULE_LETTER_I ) ;
    public final void rule__FILE_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3362:1: ( ( RULE_LETTER_I ) )
            // InternalSemver.g:3363:1: ( RULE_LETTER_I )
            {
            // InternalSemver.g:3363:1: ( RULE_LETTER_I )
            // InternalSemver.g:3364:2: RULE_LETTER_I
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
    // InternalSemver.g:3373:1: rule__FILE_TAG__Group__2 : rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 ;
    public final void rule__FILE_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3377:1: ( rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3 )
            // InternalSemver.g:3378:2: rule__FILE_TAG__Group__2__Impl rule__FILE_TAG__Group__3
            {
            pushFollow(FOLLOW_25);
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
    // InternalSemver.g:3385:1: rule__FILE_TAG__Group__2__Impl : ( RULE_LETTER_L ) ;
    public final void rule__FILE_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3389:1: ( ( RULE_LETTER_L ) )
            // InternalSemver.g:3390:1: ( RULE_LETTER_L )
            {
            // InternalSemver.g:3390:1: ( RULE_LETTER_L )
            // InternalSemver.g:3391:2: RULE_LETTER_L
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
    // InternalSemver.g:3400:1: rule__FILE_TAG__Group__3 : rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 ;
    public final void rule__FILE_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3404:1: ( rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4 )
            // InternalSemver.g:3405:2: rule__FILE_TAG__Group__3__Impl rule__FILE_TAG__Group__4
            {
            pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3412:1: rule__FILE_TAG__Group__3__Impl : ( RULE_LETTER_E ) ;
    public final void rule__FILE_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3416:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3417:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3417:1: ( RULE_LETTER_E )
            // InternalSemver.g:3418:2: RULE_LETTER_E
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
    // InternalSemver.g:3427:1: rule__FILE_TAG__Group__4 : rule__FILE_TAG__Group__4__Impl ;
    public final void rule__FILE_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3431:1: ( rule__FILE_TAG__Group__4__Impl )
            // InternalSemver.g:3432:2: rule__FILE_TAG__Group__4__Impl
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
    // InternalSemver.g:3438:1: rule__FILE_TAG__Group__4__Impl : ( ':' ) ;
    public final void rule__FILE_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3442:1: ( ( ':' ) )
            // InternalSemver.g:3443:1: ( ':' )
            {
            // InternalSemver.g:3443:1: ( ':' )
            // InternalSemver.g:3444:2: ':'
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
    // InternalSemver.g:3454:1: rule__SEMVER_TAG__Group__0 : rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 ;
    public final void rule__SEMVER_TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3458:1: ( rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1 )
            // InternalSemver.g:3459:2: rule__SEMVER_TAG__Group__0__Impl rule__SEMVER_TAG__Group__1
            {
            pushFollow(FOLLOW_25);
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
    // InternalSemver.g:3466:1: rule__SEMVER_TAG__Group__0__Impl : ( RULE_LETTER_S ) ;
    public final void rule__SEMVER_TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3470:1: ( ( RULE_LETTER_S ) )
            // InternalSemver.g:3471:1: ( RULE_LETTER_S )
            {
            // InternalSemver.g:3471:1: ( RULE_LETTER_S )
            // InternalSemver.g:3472:2: RULE_LETTER_S
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
    // InternalSemver.g:3481:1: rule__SEMVER_TAG__Group__1 : rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 ;
    public final void rule__SEMVER_TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3485:1: ( rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2 )
            // InternalSemver.g:3486:2: rule__SEMVER_TAG__Group__1__Impl rule__SEMVER_TAG__Group__2
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
    // InternalSemver.g:3493:1: rule__SEMVER_TAG__Group__1__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3497:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3498:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3498:1: ( RULE_LETTER_E )
            // InternalSemver.g:3499:2: RULE_LETTER_E
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
    // InternalSemver.g:3508:1: rule__SEMVER_TAG__Group__2 : rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 ;
    public final void rule__SEMVER_TAG__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3512:1: ( rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3 )
            // InternalSemver.g:3513:2: rule__SEMVER_TAG__Group__2__Impl rule__SEMVER_TAG__Group__3
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
    // InternalSemver.g:3520:1: rule__SEMVER_TAG__Group__2__Impl : ( RULE_LETTER_M ) ;
    public final void rule__SEMVER_TAG__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3524:1: ( ( RULE_LETTER_M ) )
            // InternalSemver.g:3525:1: ( RULE_LETTER_M )
            {
            // InternalSemver.g:3525:1: ( RULE_LETTER_M )
            // InternalSemver.g:3526:2: RULE_LETTER_M
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
    // InternalSemver.g:3535:1: rule__SEMVER_TAG__Group__3 : rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 ;
    public final void rule__SEMVER_TAG__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3539:1: ( rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4 )
            // InternalSemver.g:3540:2: rule__SEMVER_TAG__Group__3__Impl rule__SEMVER_TAG__Group__4
            {
            pushFollow(FOLLOW_25);
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
    // InternalSemver.g:3547:1: rule__SEMVER_TAG__Group__3__Impl : ( RULE_LETTER_V ) ;
    public final void rule__SEMVER_TAG__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3551:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:3552:1: ( RULE_LETTER_V )
            {
            // InternalSemver.g:3552:1: ( RULE_LETTER_V )
            // InternalSemver.g:3553:2: RULE_LETTER_V
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
    // InternalSemver.g:3562:1: rule__SEMVER_TAG__Group__4 : rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 ;
    public final void rule__SEMVER_TAG__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3566:1: ( rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5 )
            // InternalSemver.g:3567:2: rule__SEMVER_TAG__Group__4__Impl rule__SEMVER_TAG__Group__5
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
    // InternalSemver.g:3574:1: rule__SEMVER_TAG__Group__4__Impl : ( RULE_LETTER_E ) ;
    public final void rule__SEMVER_TAG__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3578:1: ( ( RULE_LETTER_E ) )
            // InternalSemver.g:3579:1: ( RULE_LETTER_E )
            {
            // InternalSemver.g:3579:1: ( RULE_LETTER_E )
            // InternalSemver.g:3580:2: RULE_LETTER_E
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
    // InternalSemver.g:3589:1: rule__SEMVER_TAG__Group__5 : rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 ;
    public final void rule__SEMVER_TAG__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3593:1: ( rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6 )
            // InternalSemver.g:3594:2: rule__SEMVER_TAG__Group__5__Impl rule__SEMVER_TAG__Group__6
            {
            pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3601:1: rule__SEMVER_TAG__Group__5__Impl : ( RULE_LETTER_R ) ;
    public final void rule__SEMVER_TAG__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3605:1: ( ( RULE_LETTER_R ) )
            // InternalSemver.g:3606:1: ( RULE_LETTER_R )
            {
            // InternalSemver.g:3606:1: ( RULE_LETTER_R )
            // InternalSemver.g:3607:2: RULE_LETTER_R
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
    // InternalSemver.g:3616:1: rule__SEMVER_TAG__Group__6 : rule__SEMVER_TAG__Group__6__Impl ;
    public final void rule__SEMVER_TAG__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3620:1: ( rule__SEMVER_TAG__Group__6__Impl )
            // InternalSemver.g:3621:2: rule__SEMVER_TAG__Group__6__Impl
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
    // InternalSemver.g:3627:1: rule__SEMVER_TAG__Group__6__Impl : ( ':' ) ;
    public final void rule__SEMVER_TAG__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3631:1: ( ( ':' ) )
            // InternalSemver.g:3632:1: ( ':' )
            {
            // InternalSemver.g:3632:1: ( ':' )
            // InternalSemver.g:3633:2: ':'
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


    // $ANTLR start "rule__PATH__Group__0"
    // InternalSemver.g:3643:1: rule__PATH__Group__0 : rule__PATH__Group__0__Impl rule__PATH__Group__1 ;
    public final void rule__PATH__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3647:1: ( rule__PATH__Group__0__Impl rule__PATH__Group__1 )
            // InternalSemver.g:3648:2: rule__PATH__Group__0__Impl rule__PATH__Group__1
            {
            pushFollow(FOLLOW_7);
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
    // InternalSemver.g:3655:1: rule__PATH__Group__0__Impl : ( ( rule__PATH__Alternatives_0 ) ) ;
    public final void rule__PATH__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3659:1: ( ( ( rule__PATH__Alternatives_0 ) ) )
            // InternalSemver.g:3660:1: ( ( rule__PATH__Alternatives_0 ) )
            {
            // InternalSemver.g:3660:1: ( ( rule__PATH__Alternatives_0 ) )
            // InternalSemver.g:3661:2: ( rule__PATH__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:3662:2: ( rule__PATH__Alternatives_0 )
            // InternalSemver.g:3662:3: rule__PATH__Alternatives_0
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
    // InternalSemver.g:3670:1: rule__PATH__Group__1 : rule__PATH__Group__1__Impl ;
    public final void rule__PATH__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3674:1: ( rule__PATH__Group__1__Impl )
            // InternalSemver.g:3675:2: rule__PATH__Group__1__Impl
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
    // InternalSemver.g:3681:1: rule__PATH__Group__1__Impl : ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) ) ;
    public final void rule__PATH__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3685:1: ( ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) ) )
            // InternalSemver.g:3686:1: ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3686:1: ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) )
            // InternalSemver.g:3687:2: ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* )
            {
            // InternalSemver.g:3687:2: ( ( rule__PATH__Alternatives_1 ) )
            // InternalSemver.g:3688:3: ( rule__PATH__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3689:3: ( rule__PATH__Alternatives_1 )
            // InternalSemver.g:3689:4: rule__PATH__Alternatives_1
            {
            pushFollow(FOLLOW_30);
            rule__PATH__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3692:2: ( ( rule__PATH__Alternatives_1 )* )
            // InternalSemver.g:3693:3: ( rule__PATH__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3694:3: ( rule__PATH__Alternatives_1 )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=RULE_DIGITS && LA43_0<=RULE_LETTER_X)||(LA43_0>=RULE_LETTER_V && LA43_0<=RULE_LETTER_OTHER)||(LA43_0>=35 && LA43_0<=37)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSemver.g:3694:4: rule__PATH__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_30);
            	    rule__PATH__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop43;
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
    // InternalSemver.g:3704:1: rule__URL_PROTOCOL__Group__0 : rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 ;
    public final void rule__URL_PROTOCOL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3708:1: ( rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 )
            // InternalSemver.g:3709:2: rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1
            {
            pushFollow(FOLLOW_31);
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
    // InternalSemver.g:3716:1: rule__URL_PROTOCOL__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__URL_PROTOCOL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3720:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:3721:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:3721:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:3722:2: ruleLETTER_NO_VX
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
    // InternalSemver.g:3731:1: rule__URL_PROTOCOL__Group__1 : rule__URL_PROTOCOL__Group__1__Impl ;
    public final void rule__URL_PROTOCOL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3735:1: ( rule__URL_PROTOCOL__Group__1__Impl )
            // InternalSemver.g:3736:2: rule__URL_PROTOCOL__Group__1__Impl
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
    // InternalSemver.g:3742:1: rule__URL_PROTOCOL__Group__1__Impl : ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) ;
    public final void rule__URL_PROTOCOL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3746:1: ( ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) )
            // InternalSemver.g:3747:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3747:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            // InternalSemver.g:3748:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            {
            // InternalSemver.g:3748:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) )
            // InternalSemver.g:3749:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3750:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            // InternalSemver.g:3750:4: rule__URL_PROTOCOL__Alternatives_1
            {
            pushFollow(FOLLOW_32);
            rule__URL_PROTOCOL__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3753:2: ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            // InternalSemver.g:3754:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3755:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==RULE_LETTER_X||(LA44_0>=RULE_LETTER_V && LA44_0<=RULE_LETTER_OTHER)||LA44_0==38) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSemver.g:3755:4: rule__URL_PROTOCOL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_32);
            	    rule__URL_PROTOCOL__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop44;
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
    // InternalSemver.g:3765:1: rule__URL__Group__0 : rule__URL__Group__0__Impl rule__URL__Group__1 ;
    public final void rule__URL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3769:1: ( rule__URL__Group__0__Impl rule__URL__Group__1 )
            // InternalSemver.g:3770:2: rule__URL__Group__0__Impl rule__URL__Group__1
            {
            pushFollow(FOLLOW_33);
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
    // InternalSemver.g:3777:1: rule__URL__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__URL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3781:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:3782:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:3782:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:3783:2: ruleLETTER_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLETTER_NO_VX();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLAccess().getLETTER_NO_VXParserRuleCall_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3792:1: rule__URL__Group__1 : rule__URL__Group__1__Impl rule__URL__Group__2 ;
    public final void rule__URL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3796:1: ( rule__URL__Group__1__Impl rule__URL__Group__2 )
            // InternalSemver.g:3797:2: rule__URL__Group__1__Impl rule__URL__Group__2
            {
            pushFollow(FOLLOW_33);
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
    // InternalSemver.g:3804:1: rule__URL__Group__1__Impl : ( ( rule__URL__Alternatives_1 )* ) ;
    public final void rule__URL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3808:1: ( ( ( rule__URL__Alternatives_1 )* ) )
            // InternalSemver.g:3809:1: ( ( rule__URL__Alternatives_1 )* )
            {
            // InternalSemver.g:3809:1: ( ( rule__URL__Alternatives_1 )* )
            // InternalSemver.g:3810:2: ( rule__URL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3811:2: ( rule__URL__Alternatives_1 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=RULE_DIGITS && LA45_0<=RULE_LETTER_X)||(LA45_0>=RULE_LETTER_V && LA45_0<=RULE_LETTER_OTHER)||LA45_0==37) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSemver.g:3811:3: rule__URL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__URL__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop45;
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
    // InternalSemver.g:3819:1: rule__URL__Group__2 : rule__URL__Group__2__Impl rule__URL__Group__3 ;
    public final void rule__URL__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3823:1: ( rule__URL__Group__2__Impl rule__URL__Group__3 )
            // InternalSemver.g:3824:2: rule__URL__Group__2__Impl rule__URL__Group__3
            {
            pushFollow(FOLLOW_33);
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
    // InternalSemver.g:3831:1: rule__URL__Group__2__Impl : ( ( rule__URL__Alternatives_2 ) ) ;
    public final void rule__URL__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3835:1: ( ( ( rule__URL__Alternatives_2 ) ) )
            // InternalSemver.g:3836:1: ( ( rule__URL__Alternatives_2 ) )
            {
            // InternalSemver.g:3836:1: ( ( rule__URL__Alternatives_2 ) )
            // InternalSemver.g:3837:2: ( rule__URL__Alternatives_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:3838:2: ( rule__URL__Alternatives_2 )
            // InternalSemver.g:3838:3: rule__URL__Alternatives_2
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
    // InternalSemver.g:3846:1: rule__URL__Group__3 : rule__URL__Group__3__Impl ;
    public final void rule__URL__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3850:1: ( rule__URL__Group__3__Impl )
            // InternalSemver.g:3851:2: rule__URL__Group__3__Impl
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
    // InternalSemver.g:3857:1: rule__URL__Group__3__Impl : ( ( rule__URL__Alternatives_3 )* ) ;
    public final void rule__URL__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3861:1: ( ( ( rule__URL__Alternatives_3 )* ) )
            // InternalSemver.g:3862:1: ( ( rule__URL__Alternatives_3 )* )
            {
            // InternalSemver.g:3862:1: ( ( rule__URL__Alternatives_3 )* )
            // InternalSemver.g:3863:2: ( rule__URL__Alternatives_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_3()); 
            }
            // InternalSemver.g:3864:2: ( rule__URL__Alternatives_3 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=RULE_DIGITS && LA46_0<=RULE_LETTER_X)||(LA46_0>=RULE_LETTER_V && LA46_0<=RULE_LETTER_OTHER)||(LA46_0>=35 && LA46_0<=37)||(LA46_0>=39 && LA46_0<=40)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSemver.g:3864:3: rule__URL__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_34);
            	    rule__URL__Alternatives_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop46;
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
    // InternalSemver.g:3873:1: rule__TAG__Group__0 : rule__TAG__Group__0__Impl rule__TAG__Group__1 ;
    public final void rule__TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3877:1: ( rule__TAG__Group__0__Impl rule__TAG__Group__1 )
            // InternalSemver.g:3878:2: rule__TAG__Group__0__Impl rule__TAG__Group__1
            {
            pushFollow(FOLLOW_9);
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
    // InternalSemver.g:3885:1: rule__TAG__Group__0__Impl : ( ruleLETTER_NO_VX ) ;
    public final void rule__TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3889:1: ( ( ruleLETTER_NO_VX ) )
            // InternalSemver.g:3890:1: ( ruleLETTER_NO_VX )
            {
            // InternalSemver.g:3890:1: ( ruleLETTER_NO_VX )
            // InternalSemver.g:3891:2: ruleLETTER_NO_VX
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
    // InternalSemver.g:3900:1: rule__TAG__Group__1 : rule__TAG__Group__1__Impl ;
    public final void rule__TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3904:1: ( rule__TAG__Group__1__Impl )
            // InternalSemver.g:3905:2: rule__TAG__Group__1__Impl
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
    // InternalSemver.g:3911:1: rule__TAG__Group__1__Impl : ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) ;
    public final void rule__TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3915:1: ( ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) )
            // InternalSemver.g:3916:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3916:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            // InternalSemver.g:3917:2: ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* )
            {
            // InternalSemver.g:3917:2: ( ( rule__TAG__Alternatives_1 ) )
            // InternalSemver.g:3918:3: ( rule__TAG__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3919:3: ( rule__TAG__Alternatives_1 )
            // InternalSemver.g:3919:4: rule__TAG__Alternatives_1
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

            // InternalSemver.g:3922:2: ( ( rule__TAG__Alternatives_1 )* )
            // InternalSemver.g:3923:3: ( rule__TAG__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3924:3: ( rule__TAG__Alternatives_1 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( ((LA47_0>=RULE_DIGITS && LA47_0<=RULE_LETTER_X)||(LA47_0>=RULE_LETTER_V && LA47_0<=RULE_LETTER_OTHER)||LA47_0==37) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalSemver.g:3924:4: rule__TAG__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__TAG__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop47;
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
    // InternalSemver.g:3934:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3938:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 )
            // InternalSemver.g:3939:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1
            {
            pushFollow(FOLLOW_9);
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
    // InternalSemver.g:3946:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl : ( RULE_DIGITS ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3950:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:3951:1: ( RULE_DIGITS )
            {
            // InternalSemver.g:3951:1: ( RULE_DIGITS )
            // InternalSemver.g:3952:2: RULE_DIGITS
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
    // InternalSemver.g:3961:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3965:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl )
            // InternalSemver.g:3966:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl
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
    // InternalSemver.g:3972:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl : ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3976:1: ( ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) )
            // InternalSemver.g:3977:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3977:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            // InternalSemver.g:3978:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            {
            // InternalSemver.g:3978:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) )
            // InternalSemver.g:3979:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3980:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            // InternalSemver.g:3980:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
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

            // InternalSemver.g:3983:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            // InternalSemver.g:3984:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3985:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=RULE_DIGITS && LA48_0<=RULE_LETTER_X)||(LA48_0>=RULE_LETTER_V && LA48_0<=RULE_LETTER_OTHER)||LA48_0==37) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalSemver.g:3985:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop48;
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
    // InternalSemver.g:3995:1: rule__LocalPathVersionRequirement__LocalPathAssignment_1 : ( rulePATH ) ;
    public final void rule__LocalPathVersionRequirement__LocalPathAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3999:1: ( ( rulePATH ) )
            // InternalSemver.g:4000:2: ( rulePATH )
            {
            // InternalSemver.g:4000:2: ( rulePATH )
            // InternalSemver.g:4001:3: rulePATH
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
    // InternalSemver.g:4010:1: rule__URLVersionRequirement__ProtocolAssignment_0 : ( ruleURL_PROTOCOL ) ;
    public final void rule__URLVersionRequirement__ProtocolAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4014:1: ( ( ruleURL_PROTOCOL ) )
            // InternalSemver.g:4015:2: ( ruleURL_PROTOCOL )
            {
            // InternalSemver.g:4015:2: ( ruleURL_PROTOCOL )
            // InternalSemver.g:4016:3: ruleURL_PROTOCOL
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
    // InternalSemver.g:4025:1: rule__URLVersionRequirement__UrlAssignment_2 : ( ruleURL ) ;
    public final void rule__URLVersionRequirement__UrlAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4029:1: ( ( ruleURL ) )
            // InternalSemver.g:4030:2: ( ruleURL )
            {
            // InternalSemver.g:4030:2: ( ruleURL )
            // InternalSemver.g:4031:3: ruleURL
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
    // InternalSemver.g:4040:1: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 : ( ruleURLVersionSpecifier ) ;
    public final void rule__URLVersionRequirement__VersionSpecifierAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4044:1: ( ( ruleURLVersionSpecifier ) )
            // InternalSemver.g:4045:2: ( ruleURLVersionSpecifier )
            {
            // InternalSemver.g:4045:2: ( ruleURLVersionSpecifier )
            // InternalSemver.g:4046:3: ruleURLVersionSpecifier
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
    // InternalSemver.g:4055:1: rule__URLVersionSpecifier__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4059:1: ( ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
            // InternalSemver.g:4060:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            {
            // InternalSemver.g:4060:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            // InternalSemver.g:4061:3: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
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
    // InternalSemver.g:4070:1: rule__URLVersionSpecifier__CommitISHAssignment_2_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4074:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4075:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4075:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4076:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4085:1: rule__URLSemver__WithSemverTagAssignment_1 : ( ruleSEMVER_TAG ) ;
    public final void rule__URLSemver__WithSemverTagAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4089:1: ( ( ruleSEMVER_TAG ) )
            // InternalSemver.g:4090:2: ( ruleSEMVER_TAG )
            {
            // InternalSemver.g:4090:2: ( ruleSEMVER_TAG )
            // InternalSemver.g:4091:3: ruleSEMVER_TAG
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
    // InternalSemver.g:4100:1: rule__URLSemver__SimpleVersionAssignment_2 : ( ruleSimpleVersion ) ;
    public final void rule__URLSemver__SimpleVersionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4104:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4105:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4105:2: ( ruleSimpleVersion )
            // InternalSemver.g:4106:3: ruleSimpleVersion
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
    // InternalSemver.g:4115:1: rule__TagVersionRequirement__TagNameAssignment : ( ruleTAG ) ;
    public final void rule__TagVersionRequirement__TagNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4119:1: ( ( ruleTAG ) )
            // InternalSemver.g:4120:2: ( ruleTAG )
            {
            // InternalSemver.g:4120:2: ( ruleTAG )
            // InternalSemver.g:4121:3: ruleTAG
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
    // InternalSemver.g:4130:1: rule__GitHubVersionRequirement__GithubUrlAssignment_0 : ( ruleURL ) ;
    public final void rule__GitHubVersionRequirement__GithubUrlAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4134:1: ( ( ruleURL ) )
            // InternalSemver.g:4135:2: ( ruleURL )
            {
            // InternalSemver.g:4135:2: ( ruleURL )
            // InternalSemver.g:4136:3: ruleURL
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
    // InternalSemver.g:4145:1: rule__GitHubVersionRequirement__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__GitHubVersionRequirement__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4149:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4150:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4150:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4151:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4160:1: rule__VersionRangeSetRequirement__RangesAssignment_1_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4164:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4165:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4165:2: ( ruleVersionRange )
            // InternalSemver.g:4166:3: ruleVersionRange
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
    // InternalSemver.g:4175:1: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4179:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:4180:2: ( ruleVersionRange )
            {
            // InternalSemver.g:4180:2: ( ruleVersionRange )
            // InternalSemver.g:4181:3: ruleVersionRange
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
    // InternalSemver.g:4190:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4194:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4195:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4195:2: ( ruleVersionNumber )
            // InternalSemver.g:4196:3: ruleVersionNumber
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
    // InternalSemver.g:4205:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4209:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4210:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4210:2: ( ruleVersionNumber )
            // InternalSemver.g:4211:3: ruleVersionNumber
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
    // InternalSemver.g:4220:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4224:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4225:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4225:2: ( ruleSimpleVersion )
            // InternalSemver.g:4226:3: ruleSimpleVersion
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
    // InternalSemver.g:4235:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4239:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:4240:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:4240:2: ( ruleSimpleVersion )
            // InternalSemver.g:4241:3: ruleSimpleVersion
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
    // InternalSemver.g:4250:1: rule__SimpleVersion__ComparatorsAssignment_0_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4254:1: ( ( ruleVersionComparator ) )
            // InternalSemver.g:4255:2: ( ruleVersionComparator )
            {
            // InternalSemver.g:4255:2: ( ruleVersionComparator )
            // InternalSemver.g:4256:3: ruleVersionComparator
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
    // InternalSemver.g:4265:1: rule__SimpleVersion__WithLetterVAssignment_1 : ( RULE_LETTER_V ) ;
    public final void rule__SimpleVersion__WithLetterVAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4269:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:4270:2: ( RULE_LETTER_V )
            {
            // InternalSemver.g:4270:2: ( RULE_LETTER_V )
            // InternalSemver.g:4271:3: RULE_LETTER_V
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
    // InternalSemver.g:4280:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4284:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:4285:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:4285:2: ( ruleVersionNumber )
            // InternalSemver.g:4286:3: ruleVersionNumber
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
    // InternalSemver.g:4295:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4299:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4300:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4300:2: ( ruleVersionPart )
            // InternalSemver.g:4301:3: ruleVersionPart
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
    // InternalSemver.g:4310:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4314:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4315:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4315:2: ( ruleVersionPart )
            // InternalSemver.g:4316:3: ruleVersionPart
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
    // InternalSemver.g:4325:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4329:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4330:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4330:2: ( ruleVersionPart )
            // InternalSemver.g:4331:3: ruleVersionPart
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
    // InternalSemver.g:4340:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4344:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:4345:2: ( ruleVersionPart )
            {
            // InternalSemver.g:4345:2: ( ruleVersionPart )
            // InternalSemver.g:4346:3: ruleVersionPart
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
    // InternalSemver.g:4355:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4359:1: ( ( ruleQualifier ) )
            // InternalSemver.g:4360:2: ( ruleQualifier )
            {
            // InternalSemver.g:4360:2: ( ruleQualifier )
            // InternalSemver.g:4361:3: ruleQualifier
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
    // InternalSemver.g:4370:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4374:1: ( ( ruleWILDCARD ) )
            // InternalSemver.g:4375:2: ( ruleWILDCARD )
            {
            // InternalSemver.g:4375:2: ( ruleWILDCARD )
            // InternalSemver.g:4376:3: ruleWILDCARD
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
    // InternalSemver.g:4385:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4389:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:4390:2: ( RULE_DIGITS )
            {
            // InternalSemver.g:4390:2: ( RULE_DIGITS )
            // InternalSemver.g:4391:3: RULE_DIGITS
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
    // InternalSemver.g:4400:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4404:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4405:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4405:2: ( ruleQualifierTag )
            // InternalSemver.g:4406:3: ruleQualifierTag
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
    // InternalSemver.g:4415:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4419:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4420:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4420:2: ( ruleQualifierTag )
            // InternalSemver.g:4421:3: ruleQualifierTag
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
    // InternalSemver.g:4430:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4434:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4435:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4435:2: ( ruleQualifierTag )
            // InternalSemver.g:4436:3: ruleQualifierTag
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
    // InternalSemver.g:4445:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4449:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4450:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4450:2: ( ruleQualifierTag )
            // InternalSemver.g:4451:3: ruleQualifierTag
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
    // InternalSemver.g:4460:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4464:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4465:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4465:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4466:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4475:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4479:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4480:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4480:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4481:3: ruleALPHA_NUMERIC_CHARS
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

    // $ANTLR start synpred3_InternalSemver
    public final void synpred3_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:768:2: ( ( ( ruleLocalPathVersionRequirement ) ) )
        // InternalSemver.g:768:2: ( ( ruleLocalPathVersionRequirement ) )
        {
        // InternalSemver.g:768:2: ( ( ruleLocalPathVersionRequirement ) )
        // InternalSemver.g:769:3: ( ruleLocalPathVersionRequirement )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); 
        }
        // InternalSemver.g:770:3: ( ruleLocalPathVersionRequirement )
        // InternalSemver.g:770:4: ruleLocalPathVersionRequirement
        {
        pushFollow(FOLLOW_2);
        ruleLocalPathVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred3_InternalSemver

    // $ANTLR start synpred5_InternalSemver
    public final void synpred5_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:780:2: ( ( ruleGitHubVersionRequirement ) )
        // InternalSemver.g:780:2: ( ruleGitHubVersionRequirement )
        {
        // InternalSemver.g:780:2: ( ruleGitHubVersionRequirement )
        // InternalSemver.g:781:3: ruleGitHubVersionRequirement
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_2()); 
        }
        pushFollow(FOLLOW_2);
        ruleGitHubVersionRequirement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred5_InternalSemver

    // $ANTLR start synpred6_InternalSemver
    public final void synpred6_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:801:2: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) )
        // InternalSemver.g:801:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        {
        // InternalSemver.g:801:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        // InternalSemver.g:802:3: ( rule__URLVersionSpecifier__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
        }
        // InternalSemver.g:803:3: ( rule__URLVersionSpecifier__Group_0__0 )
        // InternalSemver.g:803:4: rule__URLVersionSpecifier__Group_0__0
        {
        pushFollow(FOLLOW_2);
        rule__URLVersionSpecifier__Group_0__0();

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


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA33 dfa33 = new DFA33(this);
    static final String dfa_1s = "\113\uffff";
    static final String dfa_2s = "\11\uffff\14\44\2\uffff\15\44\1\uffff\14\44\13\25\17\uffff";
    static final String dfa_3s = "\1\10\24\4\2\uffff\15\4\1\uffff\27\4\16\0\1\uffff";
    static final String dfa_4s = "\1\17\12\50\12\60\2\uffff\1\60\14\50\1\uffff\14\60\13\61\16\0\1\uffff";
    static final String dfa_5s = "\25\uffff\1\3\1\2\15\uffff\1\4\45\uffff\1\1";
    static final String dfa_6s = "\74\uffff\1\2\1\5\1\15\1\4\1\1\1\6\1\11\1\13\1\0\1\10\1\12\1\14\1\3\1\7\1\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\1\3\1\4\1\1\1\5\1\6\1\7\1\10",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\27\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\27\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\27\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\27\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\27\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\27\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\12\1\14\1\uffff\1\13\1\15\1\16\1\17\1\20\1\27\1\22\1\23\1\24\23\uffff\2\25\1\11\1\26\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\57\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "",
            "",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\22\uffff\2\25\1\30\1\uffff\2\25",
            "",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\60\1\56\1\44\22\uffff\2\25\1\30\1\26\2\25\7\uffff\1\26",
            "\1\31\1\46\1\uffff\1\45\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\44\22\uffff\2\25\1\30\1\26\1\61\1\25\7\uffff\1\26",
            "\2\25\1\uffff\1\25\1\64\1\65\1\66\1\67\1\70\1\71\1\72\1\73\1\25\22\uffff\1\62\1\63\1\25\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
            "\1\77\1\101\1\uffff\1\100\1\102\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\25\22\uffff\1\74\1\75\1\76\1\uffff\2\25\10\uffff\1\25",
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
            return "763:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ruleURLVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA3_68 = input.LA(1);

                         
                        int index3_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_68);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA3_64 = input.LA(1);

                         
                        int index3_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_64);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA3_60 = input.LA(1);

                         
                        int index3_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_60);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA3_72 = input.LA(1);

                         
                        int index3_72 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_72);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA3_63 = input.LA(1);

                         
                        int index3_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_63);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA3_61 = input.LA(1);

                         
                        int index3_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_61);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA3_65 = input.LA(1);

                         
                        int index3_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_65);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA3_73 = input.LA(1);

                         
                        int index3_73 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_73);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA3_69 = input.LA(1);

                         
                        int index3_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_69);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA3_66 = input.LA(1);

                         
                        int index3_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_66);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA3_70 = input.LA(1);

                         
                        int index3_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_70);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA3_67 = input.LA(1);

                         
                        int index3_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_67);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA3_71 = input.LA(1);

                         
                        int index3_71 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_71);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA3_62 = input.LA(1);

                         
                        int index3_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalSemver()) ) {s = 74;}

                        else if ( (synpred5_InternalSemver()) ) {s = 21;}

                         
                        input.seek(index3_62);
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
    static final String dfa_8s = "\17\uffff";
    static final String dfa_9s = "\1\uffff\1\6\1\uffff\1\6\3\uffff\1\6\3\uffff\4\6";
    static final String dfa_10s = "\2\4\1\uffff\1\4\1\0\1\4\1\uffff\1\4\2\0\1\uffff\4\4";
    static final String dfa_11s = "\1\57\1\45\1\uffff\1\45\1\0\1\45\1\uffff\1\45\2\0\1\uffff\3\45\1\47";
    static final String dfa_12s = "\2\uffff\1\1\3\uffff\1\3\3\uffff\1\2\4\uffff";
    static final String dfa_13s = "\4\uffff\1\3\1\0\2\uffff\1\1\1\2\5\uffff}>";
    static final String[] dfa_14s = {
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

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "796:1: rule__URLVersionSpecifier__Alternatives : ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) );";
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
                        if ( ((LA4_5>=RULE_DIGITS && LA4_5<=RULE_LETTER_X)||(LA4_5>=RULE_LETTER_V && LA4_5<=RULE_LETTER_OTHER)||LA4_5==37) ) {s = 10;}

                        else if ( (synpred6_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index4_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index4_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_9 = input.LA(1);

                         
                        int index4_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index4_9);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_4 = input.LA(1);

                         
                        int index4_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_InternalSemver()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index4_4);
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
    static final String dfa_15s = "\u00aa\uffff";
    static final String dfa_16s = "\2\uffff\3\1\3\uffff\35\1\2\uffff\14\1\2\uffff\14\1\1\uffff\47\1\1\uffff\30\1\1\uffff\47\1";
    static final String dfa_17s = "\1\4\1\uffff\3\20\4\4\3\20\31\4\1\uffff\34\4\3\20\112\4\3\20\30\4";
    static final String dfa_18s = "\1\57\1\uffff\3\62\1\6\2\45\35\62\1\uffff\1\6\14\62\2\45\14\62\1\45\47\62\1\6\30\62\1\45\47\62";
    static final String dfa_19s = "\1\uffff\1\1\43\uffff\1\2\u0084\uffff";
    static final String dfa_20s = "\u00aa\uffff}>";
    static final String[] dfa_21s = {
            "\1\4\1\2\1\3\1\1\41\uffff\7\1",
            "",
            "\1\10\23\uffff\1\5\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\5\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\5\1\6\1\7\13\uffff\1\1",
            "\1\13\1\11\1\12",
            "\1\15\1\17\1\uffff\1\16\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\25\uffff\1\14",
            "\1\31\1\33\1\uffff\1\32\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\25\uffff\1\30",
            "\4\1\10\uffff\1\44\24\uffff\1\45\3\uffff\7\1\2\uffff\1\1",
            "\1\10\23\uffff\1\46\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\46\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\46\1\6\1\7\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\4\1\10\uffff\1\44\24\uffff\1\45\3\uffff\7\1\2\uffff\1\1",
            "",
            "\1\104\1\102\1\103",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\50\1\52\1\uffff\1\51\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\10\23\uffff\1\63\1\47\1\64\13\uffff\1\1",
            "\1\106\1\110\1\uffff\1\107\1\111\1\112\1\113\1\114\1\115\1\116\1\117\1\120\25\uffff\1\105",
            "\1\122\1\124\1\uffff\1\123\1\125\1\126\1\127\1\130\1\131\1\132\1\133\1\134\25\uffff\1\121",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\66\1\70\1\uffff\1\67\1\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\10\23\uffff\1\101\1\65\14\uffff\1\1",
            "\1\136\1\140\1\uffff\1\137\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\25\uffff\1\135",
            "\1\10\23\uffff\1\151\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0091\1\u008f\1\u0090",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\153\1\155\1\uffff\1\154\1\156\1\157\1\160\1\161\1\162\1\163\1\164\1\165\1\10\23\uffff\1\63\1\152\1\64\13\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\167\1\171\1\uffff\1\170\1\172\1\173\1\174\1\175\1\176\1\177\1\u0080\1\u0081\1\10\23\uffff\1\u0082\1\166\14\uffff\1\1",
            "\1\u0093\1\u0095\1\uffff\1\u0094\1\u0096\1\u0097\1\u0098\1\u0099\1\u009a\1\u009b\1\u009c\1\u009d\25\uffff\1\u0092",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\u0084\1\u0086\1\uffff\1\u0085\1\u0087\1\u0088\1\u0089\1\u008a\1\u008b\1\u008c\1\u008d\1\u008e\1\10\23\uffff\1\101\1\u0083\14\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\13\uffff\1\1",
            "\1\10\23\uffff\1\151\1\6\1\7\13\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1",
            "\1\u009f\1\u00a1\1\uffff\1\u00a0\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\u00a6\1\u00a7\1\u00a8\1\u00a9\1\10\23\uffff\1\u0082\1\u009e\14\uffff\1\1"
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
            return "823:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
    static final String dfa_22s = "\66\uffff";
    static final String dfa_23s = "\3\uffff\30\35\3\uffff\30\35";
    static final String dfa_24s = "\1\45\1\4\1\uffff\31\4\2\uffff\30\4";
    static final String dfa_25s = "\1\46\1\45\1\uffff\30\62\1\45\2\uffff\30\62";
    static final String dfa_26s = "\2\uffff\1\2\31\uffff\1\3\1\1\30\uffff";
    static final String dfa_27s = "\66\uffff}>";
    static final String[] dfa_28s = {
            "\1\1\1\2",
            "\1\4\1\6\1\uffff\1\5\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\25\uffff\1\3",
            "",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\20\1\22\1\uffff\1\21\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\35\23\uffff\1\33\1\17\1\34\13\uffff\1\35",
            "\1\37\1\41\1\uffff\1\40\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\25\uffff\1\36",
            "",
            "",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35",
            "\1\53\1\55\1\uffff\1\54\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\35\23\uffff\1\33\1\52\1\34\13\uffff\1\35"
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
            return "865:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );";
        }
    }
    static final String dfa_29s = "\4\uffff";
    static final String dfa_30s = "\2\2\2\uffff";
    static final String dfa_31s = "\2\20\2\uffff";
    static final String dfa_32s = "\2\62\2\uffff";
    static final String dfa_33s = "\2\uffff\1\2\1\1";
    static final String dfa_34s = "\4\uffff}>";
    static final String[] dfa_35s = {
            "\1\1\41\uffff\1\3",
            "\1\1\41\uffff\1\3",
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

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "()* loopback of 2102:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*";
        }
    }
    static final String dfa_36s = "\5\uffff";
    static final String dfa_37s = "\1\1\1\uffff\2\1\1\uffff";
    static final String dfa_38s = "\1\20\1\uffff\2\4\1\uffff";
    static final String dfa_39s = "\1\62\1\uffff\2\62\1\uffff";
    static final String dfa_40s = "\1\uffff\1\2\2\uffff\1\1";
    static final String dfa_41s = "\5\uffff}>";
    static final String[] dfa_42s = {
            "\1\2\41\uffff\1\1",
            "",
            "\4\4\10\uffff\1\3\30\uffff\7\4\2\uffff\1\1",
            "\4\4\10\uffff\1\3\30\uffff\7\4\2\uffff\1\1",
            ""
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = dfa_36;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "()* loopback of 2493:2: ( rule__VersionRangeContraint__Group_2__0 )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000200000FFB2L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000FE00000000F0L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000380000FFB0L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x000000200000FFB0L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000FE200000FFF0L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000FE00000001F0L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0004000000010000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0004000000010002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000FE00000100F0L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000FE0000000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000007000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x000000380000FFB2L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x000000600000FFB0L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x000000600000FFB2L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x000001B80000FFB0L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x000001B80000FFB2L});

}