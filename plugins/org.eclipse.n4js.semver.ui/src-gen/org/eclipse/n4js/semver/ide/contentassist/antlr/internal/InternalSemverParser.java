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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LETTER_NO_VX", "RULE_DIGITS", "RULE_LETTER_V", "RULE_LETTER_X", "RULE_ASTERIX", "RULE_WS", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'/'", "'.'", "'-'", "'+'", "':'", "'@'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'file:'", "'://'", "'#'", "'||'", "'semver:'"
    };
    public static final int RULE_WHITESPACE_FRAGMENT=10;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=11;
    public static final int RULE_EOL=12;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=23;
    public static final int RULE_ZWNJ=17;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int RULE_ASTERIX=8;
    public static final int RULE_LETTER_NO_VX=4;
    public static final int RULE_ML_COMMENT_FRAGMENT=22;
    public static final int RULE_DIGITS=5;
    public static final int RULE_ZWJ=16;
    public static final int RULE_SL_COMMENT_FRAGMENT=21;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=24;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=19;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=9;
    public static final int RULE_BOM=18;
    public static final int RULE_LETTER_V=6;
    public static final int RULE_LETTER_X=7;
    public static final int RULE_ANY_OTHER=27;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=20;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=26;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=13;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_HEX_DIGIT=14;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=15;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=25;
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


    // $ANTLR start "entryRulePATH"
    // InternalSemver.g:461:1: entryRulePATH : rulePATH EOF ;
    public final void entryRulePATH() throws RecognitionException {
        try {
            // InternalSemver.g:462:1: ( rulePATH EOF )
            // InternalSemver.g:463:1: rulePATH EOF
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
    // InternalSemver.g:470:1: rulePATH : ( ( rule__PATH__Group__0 ) ) ;
    public final void rulePATH() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:474:2: ( ( ( rule__PATH__Group__0 ) ) )
            // InternalSemver.g:475:2: ( ( rule__PATH__Group__0 ) )
            {
            // InternalSemver.g:475:2: ( ( rule__PATH__Group__0 ) )
            // InternalSemver.g:476:3: ( rule__PATH__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getGroup()); 
            }
            // InternalSemver.g:477:3: ( rule__PATH__Group__0 )
            // InternalSemver.g:477:4: rule__PATH__Group__0
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
    // InternalSemver.g:495:1: ruleURL_PROTOCOL : ( ( rule__URL_PROTOCOL__Group__0 ) ) ;
    public final void ruleURL_PROTOCOL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:499:2: ( ( ( rule__URL_PROTOCOL__Group__0 ) ) )
            // InternalSemver.g:500:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            {
            // InternalSemver.g:500:2: ( ( rule__URL_PROTOCOL__Group__0 ) )
            // InternalSemver.g:501:3: ( rule__URL_PROTOCOL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getGroup()); 
            }
            // InternalSemver.g:502:3: ( rule__URL_PROTOCOL__Group__0 )
            // InternalSemver.g:502:4: rule__URL_PROTOCOL__Group__0
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
    // InternalSemver.g:511:1: entryRuleURL : ruleURL EOF ;
    public final void entryRuleURL() throws RecognitionException {
        try {
            // InternalSemver.g:512:1: ( ruleURL EOF )
            // InternalSemver.g:513:1: ruleURL EOF
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
    // InternalSemver.g:520:1: ruleURL : ( ( rule__URL__Group__0 ) ) ;
    public final void ruleURL() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:524:2: ( ( ( rule__URL__Group__0 ) ) )
            // InternalSemver.g:525:2: ( ( rule__URL__Group__0 ) )
            {
            // InternalSemver.g:525:2: ( ( rule__URL__Group__0 ) )
            // InternalSemver.g:526:3: ( rule__URL__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getGroup()); 
            }
            // InternalSemver.g:527:3: ( rule__URL__Group__0 )
            // InternalSemver.g:527:4: rule__URL__Group__0
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
    // InternalSemver.g:536:1: entryRuleTAG : ruleTAG EOF ;
    public final void entryRuleTAG() throws RecognitionException {
        try {
            // InternalSemver.g:537:1: ( ruleTAG EOF )
            // InternalSemver.g:538:1: ruleTAG EOF
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
    // InternalSemver.g:545:1: ruleTAG : ( ( rule__TAG__Group__0 ) ) ;
    public final void ruleTAG() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:549:2: ( ( ( rule__TAG__Group__0 ) ) )
            // InternalSemver.g:550:2: ( ( rule__TAG__Group__0 ) )
            {
            // InternalSemver.g:550:2: ( ( rule__TAG__Group__0 ) )
            // InternalSemver.g:551:3: ( rule__TAG__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getGroup()); 
            }
            // InternalSemver.g:552:3: ( rule__TAG__Group__0 )
            // InternalSemver.g:552:4: rule__TAG__Group__0
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
    // InternalSemver.g:561:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSemver.g:562:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSemver.g:563:1: ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSemver.g:570:1: ruleALPHA_NUMERIC_CHARS : ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:574:2: ( ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) ) )
            // InternalSemver.g:575:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            {
            // InternalSemver.g:575:2: ( ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* ) )
            // InternalSemver.g:576:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) ) ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            {
            // InternalSemver.g:576:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives ) )
            // InternalSemver.g:577:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:578:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )
            // InternalSemver.g:578:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
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

            // InternalSemver.g:581:3: ( ( rule__ALPHA_NUMERIC_CHARS__Alternatives )* )
            // InternalSemver.g:582:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); 
            }
            // InternalSemver.g:583:4: ( rule__ALPHA_NUMERIC_CHARS__Alternatives )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_LETTER_NO_VX && LA1_0<=RULE_LETTER_X)||LA1_0==30) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSemver.g:583:5: rule__ALPHA_NUMERIC_CHARS__Alternatives
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
    // InternalSemver.g:593:1: entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {
        try {
            // InternalSemver.g:594:1: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF )
            // InternalSemver.g:595:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS EOF
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
    // InternalSemver.g:602:1: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS : ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:606:2: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) ) )
            // InternalSemver.g:607:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            {
            // InternalSemver.g:607:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 ) )
            // InternalSemver.g:608:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup()); 
            }
            // InternalSemver.g:609:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 )
            // InternalSemver.g:609:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0
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
            int LA2_0 = input.LA(1);

            if ( (LA2_0==EOF||(LA2_0>=RULE_DIGITS && LA2_0<=RULE_WS)||(LA2_0>=34 && LA2_0<=40)) ) {
                alt2=1;
            }
            else if ( (LA2_0==RULE_LETTER_NO_VX||LA2_0==41) ) {
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
    // InternalSemver.g:712:1: rule__URLVersionSpecifier__Alternatives : ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) );
    public final void rule__URLVersionSpecifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:716:1: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) | ( ( rule__URLVersionSpecifier__Group_1__0 ) ) | ( ( rule__URLVersionSpecifier__Group_2__0 ) ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case RULE_ASTERIX:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 45:
                {
                alt4=1;
                }
                break;
            case RULE_LETTER_V:
                {
                switch ( input.LA(2) ) {
                case RULE_LETTER_X:
                    {
                    int LA4_6 = input.LA(3);

                    if ( (synpred6_InternalSemver()) ) {
                        alt4=1;
                    }
                    else if ( (true) ) {
                        alt4=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_ASTERIX:
                    {
                    alt4=1;
                    }
                    break;
                case RULE_DIGITS:
                    {
                    int LA4_7 = input.LA(3);

                    if ( (synpred6_InternalSemver()) ) {
                        alt4=1;
                    }
                    else if ( (true) ) {
                        alt4=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                case EOF:
                case RULE_LETTER_NO_VX:
                case RULE_LETTER_V:
                case RULE_WS:
                case 30:
                    {
                    alt4=3;
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
                    alt4=3;
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

                if ( ((LA4_4>=RULE_LETTER_NO_VX && LA4_4<=RULE_LETTER_X)||LA4_4==30) ) {
                    alt4=2;
                }
                else if ( (synpred6_InternalSemver()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=3;
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
            case 30:
                {
                alt4=3;
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
                    // InternalSemver.g:717:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:717:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
                    // InternalSemver.g:718:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:719:3: ( rule__URLVersionSpecifier__Group_0__0 )
                    // InternalSemver.g:719:4: rule__URLVersionSpecifier__Group_0__0
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
                    // InternalSemver.g:723:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:723:2: ( ( rule__URLVersionSpecifier__Group_1__0 ) )
                    // InternalSemver.g:724:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:725:3: ( rule__URLVersionSpecifier__Group_1__0 )
                    // InternalSemver.g:725:4: rule__URLVersionSpecifier__Group_1__0
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
                    // InternalSemver.g:729:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:729:2: ( ( rule__URLVersionSpecifier__Group_2__0 ) )
                    // InternalSemver.g:730:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLVersionSpecifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:731:3: ( rule__URLVersionSpecifier__Group_2__0 )
                    // InternalSemver.g:731:4: rule__URLVersionSpecifier__Group_2__0
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
    // InternalSemver.g:739:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:743:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSemver.g:744:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSemver.g:744:2: ( ruleVersionRangeContraint )
                    // InternalSemver.g:745:3: ruleVersionRangeContraint
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
                    // InternalSemver.g:750:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSemver.g:750:2: ( ruleHyphenVersionRange )
                    // InternalSemver.g:751:3: ruleHyphenVersionRange
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
    // InternalSemver.g:760:1: rule__VersionPart__Alternatives : ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) );
    public final void rule__VersionPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:764:1: ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) )
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
                    // InternalSemver.g:765:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    {
                    // InternalSemver.g:765:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    // InternalSemver.g:766:3: ( rule__VersionPart__WildcardAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    }
                    // InternalSemver.g:767:3: ( rule__VersionPart__WildcardAssignment_0 )
                    // InternalSemver.g:767:4: rule__VersionPart__WildcardAssignment_0
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
                    // InternalSemver.g:771:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    {
                    // InternalSemver.g:771:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    // InternalSemver.g:772:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    }
                    // InternalSemver.g:773:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    // InternalSemver.g:773:4: rule__VersionPart__NumberRawAssignment_1
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
    // InternalSemver.g:781:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:785:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt7=3;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSemver.g:786:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSemver.g:786:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSemver.g:787:3: ( rule__Qualifier__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    }
                    // InternalSemver.g:788:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSemver.g:788:4: rule__Qualifier__Group_0__0
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
                    // InternalSemver.g:792:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSemver.g:792:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSemver.g:793:3: ( rule__Qualifier__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    }
                    // InternalSemver.g:794:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSemver.g:794:4: rule__Qualifier__Group_1__0
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
                    // InternalSemver.g:798:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSemver.g:798:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSemver.g:799:3: ( rule__Qualifier__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    }
                    // InternalSemver.g:800:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSemver.g:800:4: rule__Qualifier__Group_2__0
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
    // InternalSemver.g:808:1: rule__PATH__Alternatives_0 : ( ( RULE_LETTER_NO_VX ) | ( '/' ) | ( '.' ) );
    public final void rule__PATH__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:812:1: ( ( RULE_LETTER_NO_VX ) | ( '/' ) | ( '.' ) )
            int alt8=3;
            switch ( input.LA(1) ) {
            case RULE_LETTER_NO_VX:
                {
                alt8=1;
                }
                break;
            case 28:
                {
                alt8=2;
                }
                break;
            case 29:
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
                    // InternalSemver.g:813:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:813:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:814:3: RULE_LETTER_NO_VX
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
                    // InternalSemver.g:819:2: ( '/' )
                    {
                    // InternalSemver.g:819:2: ( '/' )
                    // InternalSemver.g:820:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_0_1()); 
                    }
                    match(input,28,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:825:2: ( '.' )
                    {
                    // InternalSemver.g:825:2: ( '.' )
                    // InternalSemver.g:826:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_0_2()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:835:1: rule__PATH__Alternatives_1 : ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__PATH__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:839:1: ( ( '/' ) | ( '.' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt9=7;
            switch ( input.LA(1) ) {
            case 28:
                {
                alt9=1;
                }
                break;
            case 29:
                {
                alt9=2;
                }
                break;
            case 30:
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
                    // InternalSemver.g:840:2: ( '/' )
                    {
                    // InternalSemver.g:840:2: ( '/' )
                    // InternalSemver.g:841:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getSolidusKeyword_1_0()); 
                    }
                    match(input,28,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getSolidusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:846:2: ( '.' )
                    {
                    // InternalSemver.g:846:2: ( '.' )
                    // InternalSemver.g:847:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getFullStopKeyword_1_1()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getFullStopKeyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:852:2: ( '-' )
                    {
                    // InternalSemver.g:852:2: ( '-' )
                    // InternalSemver.g:853:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPATHAccess().getHyphenMinusKeyword_1_2()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPATHAccess().getHyphenMinusKeyword_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:858:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:858:2: ( RULE_DIGITS )
                    // InternalSemver.g:859:3: RULE_DIGITS
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
                    // InternalSemver.g:864:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:864:2: ( RULE_LETTER_V )
                    // InternalSemver.g:865:3: RULE_LETTER_V
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
                    // InternalSemver.g:870:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:870:2: ( RULE_LETTER_X )
                    // InternalSemver.g:871:3: RULE_LETTER_X
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
                    // InternalSemver.g:876:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:876:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:877:3: RULE_LETTER_NO_VX
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
    // InternalSemver.g:886:1: rule__URL_PROTOCOL__Alternatives_1 : ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) | ( '+' ) );
    public final void rule__URL_PROTOCOL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:890:1: ( ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) | ( '+' ) )
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
            case 31:
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
                    // InternalSemver.g:891:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:891:2: ( RULE_LETTER_V )
                    // InternalSemver.g:892:3: RULE_LETTER_V
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
                    // InternalSemver.g:897:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:897:2: ( RULE_LETTER_X )
                    // InternalSemver.g:898:3: RULE_LETTER_X
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
                    // InternalSemver.g:903:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:903:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:904:3: RULE_LETTER_NO_VX
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
                    // InternalSemver.g:909:2: ( '+' )
                    {
                    // InternalSemver.g:909:2: ( '+' )
                    // InternalSemver.g:910:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_3()); 
                    }
                    match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:919:1: rule__URL__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__URL__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:923:1: ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt11=5;
            switch ( input.LA(1) ) {
            case 30:
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
                    // InternalSemver.g:924:2: ( '-' )
                    {
                    // InternalSemver.g:924:2: ( '-' )
                    // InternalSemver.g:925:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:930:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:930:2: ( RULE_DIGITS )
                    // InternalSemver.g:931:3: RULE_DIGITS
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
                    // InternalSemver.g:936:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:936:2: ( RULE_LETTER_V )
                    // InternalSemver.g:937:3: RULE_LETTER_V
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
                    // InternalSemver.g:942:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:942:2: ( RULE_LETTER_X )
                    // InternalSemver.g:943:3: RULE_LETTER_X
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
                    // InternalSemver.g:948:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:948:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:949:3: RULE_LETTER_NO_VX
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
    // InternalSemver.g:958:1: rule__URL__Alternatives_2 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) );
    public final void rule__URL__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:962:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) )
            int alt12=4;
            switch ( input.LA(1) ) {
            case 28:
                {
                alt12=1;
                }
                break;
            case 29:
                {
                alt12=2;
                }
                break;
            case 32:
                {
                alt12=3;
                }
                break;
            case 33:
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
                    // InternalSemver.g:963:2: ( '/' )
                    {
                    // InternalSemver.g:963:2: ( '/' )
                    // InternalSemver.g:964:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }
                    match(input,28,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:969:2: ( '.' )
                    {
                    // InternalSemver.g:969:2: ( '.' )
                    // InternalSemver.g:970:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:975:2: ( ':' )
                    {
                    // InternalSemver.g:975:2: ( ':' )
                    // InternalSemver.g:976:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }
                    match(input,32,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_2_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:981:2: ( '@' )
                    {
                    // InternalSemver.g:981:2: ( '@' )
                    // InternalSemver.g:982:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); 
                    }
                    match(input,33,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:991:1: rule__URL__Alternatives_3 : ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__URL__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:995:1: ( ( '/' ) | ( '.' ) | ( ':' ) | ( '@' ) | ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt13=9;
            switch ( input.LA(1) ) {
            case 28:
                {
                alt13=1;
                }
                break;
            case 29:
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
            case 30:
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
                    // InternalSemver.g:996:2: ( '/' )
                    {
                    // InternalSemver.g:996:2: ( '/' )
                    // InternalSemver.g:997:3: '/'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getSolidusKeyword_3_0()); 
                    }
                    match(input,28,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getSolidusKeyword_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1002:2: ( '.' )
                    {
                    // InternalSemver.g:1002:2: ( '.' )
                    // InternalSemver.g:1003:3: '.'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getFullStopKeyword_3_1()); 
                    }
                    match(input,29,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getFullStopKeyword_3_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1008:2: ( ':' )
                    {
                    // InternalSemver.g:1008:2: ( ':' )
                    // InternalSemver.g:1009:3: ':'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getColonKeyword_3_2()); 
                    }
                    match(input,32,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getColonKeyword_3_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1014:2: ( '@' )
                    {
                    // InternalSemver.g:1014:2: ( '@' )
                    // InternalSemver.g:1015:3: '@'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getCommercialAtKeyword_3_3()); 
                    }
                    match(input,33,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getCommercialAtKeyword_3_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1020:2: ( '-' )
                    {
                    // InternalSemver.g:1020:2: ( '-' )
                    // InternalSemver.g:1021:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getURLAccess().getHyphenMinusKeyword_3_4()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getURLAccess().getHyphenMinusKeyword_3_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1026:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1026:2: ( RULE_DIGITS )
                    // InternalSemver.g:1027:3: RULE_DIGITS
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
                    // InternalSemver.g:1032:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1032:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1033:3: RULE_LETTER_V
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
                    // InternalSemver.g:1038:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1038:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1039:3: RULE_LETTER_X
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
                    // InternalSemver.g:1044:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1044:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1045:3: RULE_LETTER_NO_VX
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


    // $ANTLR start "rule__TAG__Alternatives_1"
    // InternalSemver.g:1054:1: rule__TAG__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__TAG__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1058:1: ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt14=5;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt14=1;
                }
                break;
            case RULE_DIGITS:
                {
                alt14=2;
                }
                break;
            case RULE_LETTER_V:
                {
                alt14=3;
                }
                break;
            case RULE_LETTER_X:
                {
                alt14=4;
                }
                break;
            case RULE_LETTER_NO_VX:
                {
                alt14=5;
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
                    // InternalSemver.g:1059:2: ( '-' )
                    {
                    // InternalSemver.g:1059:2: ( '-' )
                    // InternalSemver.g:1060:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1065:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1065:2: ( RULE_DIGITS )
                    // InternalSemver.g:1066:3: RULE_DIGITS
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
                    // InternalSemver.g:1071:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1071:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1072:3: RULE_LETTER_V
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
                    // InternalSemver.g:1077:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1077:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1078:3: RULE_LETTER_X
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
                    // InternalSemver.g:1083:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1083:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1084:3: RULE_LETTER_NO_VX
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
    // InternalSemver.g:1093:1: rule__ALPHA_NUMERIC_CHARS__Alternatives : ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__ALPHA_NUMERIC_CHARS__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1097:1: ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt15=5;
            switch ( input.LA(1) ) {
            case 30:
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
                    // InternalSemver.g:1098:2: ( '-' )
                    {
                    // InternalSemver.g:1098:2: ( '-' )
                    // InternalSemver.g:1099:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1104:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1104:2: ( RULE_DIGITS )
                    // InternalSemver.g:1105:3: RULE_DIGITS
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
                    // InternalSemver.g:1110:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1110:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1111:3: RULE_LETTER_V
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
                    // InternalSemver.g:1116:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1116:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1117:3: RULE_LETTER_X
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
                    // InternalSemver.g:1122:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1122:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1123:3: RULE_LETTER_NO_VX
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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1"
    // InternalSemver.g:1132:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 : ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) );
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1136:1: ( ( '-' ) | ( RULE_DIGITS ) | ( RULE_LETTER_V ) | ( RULE_LETTER_X ) | ( RULE_LETTER_NO_VX ) )
            int alt16=5;
            switch ( input.LA(1) ) {
            case 30:
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
                    // InternalSemver.g:1137:2: ( '-' )
                    {
                    // InternalSemver.g:1137:2: ( '-' )
                    // InternalSemver.g:1138:3: '-'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); 
                    }
                    match(input,30,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1143:2: ( RULE_DIGITS )
                    {
                    // InternalSemver.g:1143:2: ( RULE_DIGITS )
                    // InternalSemver.g:1144:3: RULE_DIGITS
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
                    // InternalSemver.g:1149:2: ( RULE_LETTER_V )
                    {
                    // InternalSemver.g:1149:2: ( RULE_LETTER_V )
                    // InternalSemver.g:1150:3: RULE_LETTER_V
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTER_VTerminalRuleCall_1_2()); 
                    }
                    match(input,RULE_LETTER_V,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTER_VTerminalRuleCall_1_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1155:2: ( RULE_LETTER_X )
                    {
                    // InternalSemver.g:1155:2: ( RULE_LETTER_X )
                    // InternalSemver.g:1156:3: RULE_LETTER_X
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTER_XTerminalRuleCall_1_3()); 
                    }
                    match(input,RULE_LETTER_X,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTER_XTerminalRuleCall_1_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1161:2: ( RULE_LETTER_NO_VX )
                    {
                    // InternalSemver.g:1161:2: ( RULE_LETTER_NO_VX )
                    // InternalSemver.g:1162:3: RULE_LETTER_NO_VX
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTER_NO_VXTerminalRuleCall_1_4()); 
                    }
                    match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTER_NO_VXTerminalRuleCall_1_4()); 
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


    // $ANTLR start "rule__VersionComparator__Alternatives"
    // InternalSemver.g:1192:1: rule__VersionComparator__Alternatives : ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1196:1: ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt18=7;
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
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalSemver.g:1197:2: ( ( '=' ) )
                    {
                    // InternalSemver.g:1197:2: ( ( '=' ) )
                    // InternalSemver.g:1198:3: ( '=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }
                    // InternalSemver.g:1199:3: ( '=' )
                    // InternalSemver.g:1199:4: '='
                    {
                    match(input,34,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalSemver.g:1203:2: ( ( '<' ) )
                    {
                    // InternalSemver.g:1203:2: ( ( '<' ) )
                    // InternalSemver.g:1204:3: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }
                    // InternalSemver.g:1205:3: ( '<' )
                    // InternalSemver.g:1205:4: '<'
                    {
                    match(input,35,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:1209:2: ( ( '~' ) )
                    {
                    // InternalSemver.g:1209:2: ( ( '~' ) )
                    // InternalSemver.g:1210:3: ( '~' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }
                    // InternalSemver.g:1211:3: ( '~' )
                    // InternalSemver.g:1211:4: '~'
                    {
                    match(input,36,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalSemver.g:1215:2: ( ( '^' ) )
                    {
                    // InternalSemver.g:1215:2: ( ( '^' ) )
                    // InternalSemver.g:1216:3: ( '^' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }
                    // InternalSemver.g:1217:3: ( '^' )
                    // InternalSemver.g:1217:4: '^'
                    {
                    match(input,37,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalSemver.g:1221:2: ( ( '<=' ) )
                    {
                    // InternalSemver.g:1221:2: ( ( '<=' ) )
                    // InternalSemver.g:1222:3: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }
                    // InternalSemver.g:1223:3: ( '<=' )
                    // InternalSemver.g:1223:4: '<='
                    {
                    match(input,38,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalSemver.g:1227:2: ( ( '>' ) )
                    {
                    // InternalSemver.g:1227:2: ( ( '>' ) )
                    // InternalSemver.g:1228:3: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }
                    // InternalSemver.g:1229:3: ( '>' )
                    // InternalSemver.g:1229:4: '>'
                    {
                    match(input,39,FOLLOW_2); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalSemver.g:1233:2: ( ( '>=' ) )
                    {
                    // InternalSemver.g:1233:2: ( ( '>=' ) )
                    // InternalSemver.g:1234:3: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); 
                    }
                    // InternalSemver.g:1235:3: ( '>=' )
                    // InternalSemver.g:1235:4: '>='
                    {
                    match(input,40,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1243:1: rule__NPMVersionRequirement__Group_0__0 : rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 ;
    public final void rule__NPMVersionRequirement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1247:1: ( rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1 )
            // InternalSemver.g:1248:2: rule__NPMVersionRequirement__Group_0__0__Impl rule__NPMVersionRequirement__Group_0__1
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
    // InternalSemver.g:1255:1: rule__NPMVersionRequirement__Group_0__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1259:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1260:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1260:1: ( ( RULE_WS )* )
            // InternalSemver.g:1261:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); 
            }
            // InternalSemver.g:1262:2: ( RULE_WS )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_WS) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSemver.g:1262:3: RULE_WS
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
    // InternalSemver.g:1270:1: rule__NPMVersionRequirement__Group_0__1 : rule__NPMVersionRequirement__Group_0__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1274:1: ( rule__NPMVersionRequirement__Group_0__1__Impl )
            // InternalSemver.g:1275:2: rule__NPMVersionRequirement__Group_0__1__Impl
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
    // InternalSemver.g:1281:1: rule__NPMVersionRequirement__Group_0__1__Impl : ( ruleVersionRangeSetRequirement ) ;
    public final void rule__NPMVersionRequirement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1285:1: ( ( ruleVersionRangeSetRequirement ) )
            // InternalSemver.g:1286:1: ( ruleVersionRangeSetRequirement )
            {
            // InternalSemver.g:1286:1: ( ruleVersionRangeSetRequirement )
            // InternalSemver.g:1287:2: ruleVersionRangeSetRequirement
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
    // InternalSemver.g:1297:1: rule__NPMVersionRequirement__Group_1__0 : rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 ;
    public final void rule__NPMVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1301:1: ( rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1 )
            // InternalSemver.g:1302:2: rule__NPMVersionRequirement__Group_1__0__Impl rule__NPMVersionRequirement__Group_1__1
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
    // InternalSemver.g:1309:1: rule__NPMVersionRequirement__Group_1__0__Impl : ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) ;
    public final void rule__NPMVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1313:1: ( ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) ) )
            // InternalSemver.g:1314:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            {
            // InternalSemver.g:1314:1: ( ( rule__NPMVersionRequirement__Alternatives_1_0 ) )
            // InternalSemver.g:1315:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); 
            }
            // InternalSemver.g:1316:2: ( rule__NPMVersionRequirement__Alternatives_1_0 )
            // InternalSemver.g:1316:3: rule__NPMVersionRequirement__Alternatives_1_0
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
    // InternalSemver.g:1324:1: rule__NPMVersionRequirement__Group_1__1 : rule__NPMVersionRequirement__Group_1__1__Impl ;
    public final void rule__NPMVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1328:1: ( rule__NPMVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1329:2: rule__NPMVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1335:1: rule__NPMVersionRequirement__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__NPMVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1339:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:1340:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:1340:1: ( ( RULE_WS )* )
            // InternalSemver.g:1341:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); 
            }
            // InternalSemver.g:1342:2: ( RULE_WS )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==RULE_WS) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSemver.g:1342:3: RULE_WS
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
    // InternalSemver.g:1351:1: rule__LocalPathVersionRequirement__Group__0 : rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 ;
    public final void rule__LocalPathVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1355:1: ( rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1 )
            // InternalSemver.g:1356:2: rule__LocalPathVersionRequirement__Group__0__Impl rule__LocalPathVersionRequirement__Group__1
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
    // InternalSemver.g:1363:1: rule__LocalPathVersionRequirement__Group__0__Impl : ( ( 'file:' ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1367:1: ( ( ( 'file:' ) ) )
            // InternalSemver.g:1368:1: ( ( 'file:' ) )
            {
            // InternalSemver.g:1368:1: ( ( 'file:' ) )
            // InternalSemver.g:1369:2: ( 'file:' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getFileKeyword_0()); 
            }
            // InternalSemver.g:1370:2: ( 'file:' )
            // InternalSemver.g:1370:3: 'file:'
            {
            match(input,41,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1378:1: rule__LocalPathVersionRequirement__Group__1 : rule__LocalPathVersionRequirement__Group__1__Impl ;
    public final void rule__LocalPathVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1382:1: ( rule__LocalPathVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1383:2: rule__LocalPathVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1389:1: rule__LocalPathVersionRequirement__Group__1__Impl : ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) ;
    public final void rule__LocalPathVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1393:1: ( ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) ) )
            // InternalSemver.g:1394:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            {
            // InternalSemver.g:1394:1: ( ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 ) )
            // InternalSemver.g:1395:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); 
            }
            // InternalSemver.g:1396:2: ( rule__LocalPathVersionRequirement__LocalPathAssignment_1 )
            // InternalSemver.g:1396:3: rule__LocalPathVersionRequirement__LocalPathAssignment_1
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
    // InternalSemver.g:1405:1: rule__URLVersionRequirement__Group__0 : rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 ;
    public final void rule__URLVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1409:1: ( rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1 )
            // InternalSemver.g:1410:2: rule__URLVersionRequirement__Group__0__Impl rule__URLVersionRequirement__Group__1
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
    // InternalSemver.g:1417:1: rule__URLVersionRequirement__Group__0__Impl : ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) ;
    public final void rule__URLVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1421:1: ( ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) ) )
            // InternalSemver.g:1422:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            {
            // InternalSemver.g:1422:1: ( ( rule__URLVersionRequirement__ProtocolAssignment_0 ) )
            // InternalSemver.g:1423:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); 
            }
            // InternalSemver.g:1424:2: ( rule__URLVersionRequirement__ProtocolAssignment_0 )
            // InternalSemver.g:1424:3: rule__URLVersionRequirement__ProtocolAssignment_0
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
    // InternalSemver.g:1432:1: rule__URLVersionRequirement__Group__1 : rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 ;
    public final void rule__URLVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1436:1: ( rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2 )
            // InternalSemver.g:1437:2: rule__URLVersionRequirement__Group__1__Impl rule__URLVersionRequirement__Group__2
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
    // InternalSemver.g:1444:1: rule__URLVersionRequirement__Group__1__Impl : ( ( '://' ) ) ;
    public final void rule__URLVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1448:1: ( ( ( '://' ) ) )
            // InternalSemver.g:1449:1: ( ( '://' ) )
            {
            // InternalSemver.g:1449:1: ( ( '://' ) )
            // InternalSemver.g:1450:2: ( '://' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getColonSolidusSolidusKeyword_1()); 
            }
            // InternalSemver.g:1451:2: ( '://' )
            // InternalSemver.g:1451:3: '://'
            {
            match(input,42,FOLLOW_2); if (state.failed) return ;

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
    // InternalSemver.g:1459:1: rule__URLVersionRequirement__Group__2 : rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 ;
    public final void rule__URLVersionRequirement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1463:1: ( rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3 )
            // InternalSemver.g:1464:2: rule__URLVersionRequirement__Group__2__Impl rule__URLVersionRequirement__Group__3
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
    // InternalSemver.g:1471:1: rule__URLVersionRequirement__Group__2__Impl : ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) ;
    public final void rule__URLVersionRequirement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1475:1: ( ( ( rule__URLVersionRequirement__UrlAssignment_2 ) ) )
            // InternalSemver.g:1476:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            {
            // InternalSemver.g:1476:1: ( ( rule__URLVersionRequirement__UrlAssignment_2 ) )
            // InternalSemver.g:1477:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); 
            }
            // InternalSemver.g:1478:2: ( rule__URLVersionRequirement__UrlAssignment_2 )
            // InternalSemver.g:1478:3: rule__URLVersionRequirement__UrlAssignment_2
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
    // InternalSemver.g:1486:1: rule__URLVersionRequirement__Group__3 : rule__URLVersionRequirement__Group__3__Impl ;
    public final void rule__URLVersionRequirement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1490:1: ( rule__URLVersionRequirement__Group__3__Impl )
            // InternalSemver.g:1491:2: rule__URLVersionRequirement__Group__3__Impl
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
    // InternalSemver.g:1497:1: rule__URLVersionRequirement__Group__3__Impl : ( ( rule__URLVersionRequirement__Group_3__0 )? ) ;
    public final void rule__URLVersionRequirement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1501:1: ( ( ( rule__URLVersionRequirement__Group_3__0 )? ) )
            // InternalSemver.g:1502:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            {
            // InternalSemver.g:1502:1: ( ( rule__URLVersionRequirement__Group_3__0 )? )
            // InternalSemver.g:1503:2: ( rule__URLVersionRequirement__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); 
            }
            // InternalSemver.g:1504:2: ( rule__URLVersionRequirement__Group_3__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==43) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSemver.g:1504:3: rule__URLVersionRequirement__Group_3__0
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
    // InternalSemver.g:1513:1: rule__URLVersionRequirement__Group_3__0 : rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 ;
    public final void rule__URLVersionRequirement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1517:1: ( rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1 )
            // InternalSemver.g:1518:2: rule__URLVersionRequirement__Group_3__0__Impl rule__URLVersionRequirement__Group_3__1
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
    // InternalSemver.g:1525:1: rule__URLVersionRequirement__Group_3__0__Impl : ( '#' ) ;
    public final void rule__URLVersionRequirement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1529:1: ( ( '#' ) )
            // InternalSemver.g:1530:1: ( '#' )
            {
            // InternalSemver.g:1530:1: ( '#' )
            // InternalSemver.g:1531:2: '#'
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
    // InternalSemver.g:1540:1: rule__URLVersionRequirement__Group_3__1 : rule__URLVersionRequirement__Group_3__1__Impl ;
    public final void rule__URLVersionRequirement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1544:1: ( rule__URLVersionRequirement__Group_3__1__Impl )
            // InternalSemver.g:1545:2: rule__URLVersionRequirement__Group_3__1__Impl
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
    // InternalSemver.g:1551:1: rule__URLVersionRequirement__Group_3__1__Impl : ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) ;
    public final void rule__URLVersionRequirement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1555:1: ( ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) ) )
            // InternalSemver.g:1556:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            {
            // InternalSemver.g:1556:1: ( ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 ) )
            // InternalSemver.g:1557:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); 
            }
            // InternalSemver.g:1558:2: ( rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 )
            // InternalSemver.g:1558:3: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
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
    // InternalSemver.g:1567:1: rule__URLVersionSpecifier__Group_0__0 : rule__URLVersionSpecifier__Group_0__0__Impl ;
    public final void rule__URLVersionSpecifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1571:1: ( rule__URLVersionSpecifier__Group_0__0__Impl )
            // InternalSemver.g:1572:2: rule__URLVersionSpecifier__Group_0__0__Impl
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
    // InternalSemver.g:1578:1: rule__URLVersionSpecifier__Group_0__0__Impl : ( ruleURLSemver ) ;
    public final void rule__URLVersionSpecifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1582:1: ( ( ruleURLSemver ) )
            // InternalSemver.g:1583:1: ( ruleURLSemver )
            {
            // InternalSemver.g:1583:1: ( ruleURLSemver )
            // InternalSemver.g:1584:2: ruleURLSemver
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
    // InternalSemver.g:1594:1: rule__URLVersionSpecifier__Group_1__0 : rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 ;
    public final void rule__URLVersionSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1598:1: ( rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1 )
            // InternalSemver.g:1599:2: rule__URLVersionSpecifier__Group_1__0__Impl rule__URLVersionSpecifier__Group_1__1
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
    // InternalSemver.g:1606:1: rule__URLVersionSpecifier__Group_1__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1610:1: ( ( () ) )
            // InternalSemver.g:1611:1: ( () )
            {
            // InternalSemver.g:1611:1: ( () )
            // InternalSemver.g:1612:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); 
            }
            // InternalSemver.g:1613:2: ()
            // InternalSemver.g:1613:3: 
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
    // InternalSemver.g:1621:1: rule__URLVersionSpecifier__Group_1__1 : rule__URLVersionSpecifier__Group_1__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1625:1: ( rule__URLVersionSpecifier__Group_1__1__Impl )
            // InternalSemver.g:1626:2: rule__URLVersionSpecifier__Group_1__1__Impl
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
    // InternalSemver.g:1632:1: rule__URLVersionSpecifier__Group_1__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1636:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1637:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1637:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1638:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1639:2: ( rule__URLVersionSpecifier__CommitISHAssignment_1_1 )
            // InternalSemver.g:1639:3: rule__URLVersionSpecifier__CommitISHAssignment_1_1
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
    // InternalSemver.g:1648:1: rule__URLVersionSpecifier__Group_2__0 : rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 ;
    public final void rule__URLVersionSpecifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1652:1: ( rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1 )
            // InternalSemver.g:1653:2: rule__URLVersionSpecifier__Group_2__0__Impl rule__URLVersionSpecifier__Group_2__1
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
    // InternalSemver.g:1660:1: rule__URLVersionSpecifier__Group_2__0__Impl : ( () ) ;
    public final void rule__URLVersionSpecifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1664:1: ( ( () ) )
            // InternalSemver.g:1665:1: ( () )
            {
            // InternalSemver.g:1665:1: ( () )
            // InternalSemver.g:1666:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); 
            }
            // InternalSemver.g:1667:2: ()
            // InternalSemver.g:1667:3: 
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
    // InternalSemver.g:1675:1: rule__URLVersionSpecifier__Group_2__1 : rule__URLVersionSpecifier__Group_2__1__Impl ;
    public final void rule__URLVersionSpecifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1679:1: ( rule__URLVersionSpecifier__Group_2__1__Impl )
            // InternalSemver.g:1680:2: rule__URLVersionSpecifier__Group_2__1__Impl
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
    // InternalSemver.g:1686:1: rule__URLVersionSpecifier__Group_2__1__Impl : ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) ;
    public final void rule__URLVersionSpecifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1690:1: ( ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) ) )
            // InternalSemver.g:1691:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            {
            // InternalSemver.g:1691:1: ( ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 ) )
            // InternalSemver.g:1692:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); 
            }
            // InternalSemver.g:1693:2: ( rule__URLVersionSpecifier__CommitISHAssignment_2_1 )
            // InternalSemver.g:1693:3: rule__URLVersionSpecifier__CommitISHAssignment_2_1
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
    // InternalSemver.g:1702:1: rule__URLSemver__Group__0 : rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 ;
    public final void rule__URLSemver__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1706:1: ( rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1 )
            // InternalSemver.g:1707:2: rule__URLSemver__Group__0__Impl rule__URLSemver__Group__1
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
    // InternalSemver.g:1714:1: rule__URLSemver__Group__0__Impl : ( () ) ;
    public final void rule__URLSemver__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1718:1: ( ( () ) )
            // InternalSemver.g:1719:1: ( () )
            {
            // InternalSemver.g:1719:1: ( () )
            // InternalSemver.g:1720:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); 
            }
            // InternalSemver.g:1721:2: ()
            // InternalSemver.g:1721:3: 
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
    // InternalSemver.g:1729:1: rule__URLSemver__Group__1 : rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 ;
    public final void rule__URLSemver__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1733:1: ( rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2 )
            // InternalSemver.g:1734:2: rule__URLSemver__Group__1__Impl rule__URLSemver__Group__2
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
    // InternalSemver.g:1741:1: rule__URLSemver__Group__1__Impl : ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) ;
    public final void rule__URLSemver__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1745:1: ( ( ( rule__URLSemver__WithSemverTagAssignment_1 )? ) )
            // InternalSemver.g:1746:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            {
            // InternalSemver.g:1746:1: ( ( rule__URLSemver__WithSemverTagAssignment_1 )? )
            // InternalSemver.g:1747:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); 
            }
            // InternalSemver.g:1748:2: ( rule__URLSemver__WithSemverTagAssignment_1 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==45) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSemver.g:1748:3: rule__URLSemver__WithSemverTagAssignment_1
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
    // InternalSemver.g:1756:1: rule__URLSemver__Group__2 : rule__URLSemver__Group__2__Impl ;
    public final void rule__URLSemver__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1760:1: ( rule__URLSemver__Group__2__Impl )
            // InternalSemver.g:1761:2: rule__URLSemver__Group__2__Impl
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
    // InternalSemver.g:1767:1: rule__URLSemver__Group__2__Impl : ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) ;
    public final void rule__URLSemver__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1771:1: ( ( ( rule__URLSemver__SimpleVersionAssignment_2 ) ) )
            // InternalSemver.g:1772:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            {
            // InternalSemver.g:1772:1: ( ( rule__URLSemver__SimpleVersionAssignment_2 ) )
            // InternalSemver.g:1773:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); 
            }
            // InternalSemver.g:1774:2: ( rule__URLSemver__SimpleVersionAssignment_2 )
            // InternalSemver.g:1774:3: rule__URLSemver__SimpleVersionAssignment_2
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
    // InternalSemver.g:1783:1: rule__GitHubVersionRequirement__Group__0 : rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 ;
    public final void rule__GitHubVersionRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1787:1: ( rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1 )
            // InternalSemver.g:1788:2: rule__GitHubVersionRequirement__Group__0__Impl rule__GitHubVersionRequirement__Group__1
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
    // InternalSemver.g:1795:1: rule__GitHubVersionRequirement__Group__0__Impl : ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) ;
    public final void rule__GitHubVersionRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1799:1: ( ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) ) )
            // InternalSemver.g:1800:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            {
            // InternalSemver.g:1800:1: ( ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 ) )
            // InternalSemver.g:1801:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); 
            }
            // InternalSemver.g:1802:2: ( rule__GitHubVersionRequirement__GithubUrlAssignment_0 )
            // InternalSemver.g:1802:3: rule__GitHubVersionRequirement__GithubUrlAssignment_0
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
    // InternalSemver.g:1810:1: rule__GitHubVersionRequirement__Group__1 : rule__GitHubVersionRequirement__Group__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1814:1: ( rule__GitHubVersionRequirement__Group__1__Impl )
            // InternalSemver.g:1815:2: rule__GitHubVersionRequirement__Group__1__Impl
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
    // InternalSemver.g:1821:1: rule__GitHubVersionRequirement__Group__1__Impl : ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) ;
    public final void rule__GitHubVersionRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1825:1: ( ( ( rule__GitHubVersionRequirement__Group_1__0 )? ) )
            // InternalSemver.g:1826:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:1826:1: ( ( rule__GitHubVersionRequirement__Group_1__0 )? )
            // InternalSemver.g:1827:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1828:2: ( rule__GitHubVersionRequirement__Group_1__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==43) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSemver.g:1828:3: rule__GitHubVersionRequirement__Group_1__0
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
    // InternalSemver.g:1837:1: rule__GitHubVersionRequirement__Group_1__0 : rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 ;
    public final void rule__GitHubVersionRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1841:1: ( rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1 )
            // InternalSemver.g:1842:2: rule__GitHubVersionRequirement__Group_1__0__Impl rule__GitHubVersionRequirement__Group_1__1
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
    // InternalSemver.g:1849:1: rule__GitHubVersionRequirement__Group_1__0__Impl : ( '#' ) ;
    public final void rule__GitHubVersionRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1853:1: ( ( '#' ) )
            // InternalSemver.g:1854:1: ( '#' )
            {
            // InternalSemver.g:1854:1: ( '#' )
            // InternalSemver.g:1855:2: '#'
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
    // InternalSemver.g:1864:1: rule__GitHubVersionRequirement__Group_1__1 : rule__GitHubVersionRequirement__Group_1__1__Impl ;
    public final void rule__GitHubVersionRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1868:1: ( rule__GitHubVersionRequirement__Group_1__1__Impl )
            // InternalSemver.g:1869:2: rule__GitHubVersionRequirement__Group_1__1__Impl
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
    // InternalSemver.g:1875:1: rule__GitHubVersionRequirement__Group_1__1__Impl : ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) ;
    public final void rule__GitHubVersionRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1879:1: ( ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) ) )
            // InternalSemver.g:1880:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            {
            // InternalSemver.g:1880:1: ( ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 ) )
            // InternalSemver.g:1881:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); 
            }
            // InternalSemver.g:1882:2: ( rule__GitHubVersionRequirement__CommitISHAssignment_1_1 )
            // InternalSemver.g:1882:3: rule__GitHubVersionRequirement__CommitISHAssignment_1_1
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
    // InternalSemver.g:1891:1: rule__VersionRangeSetRequirement__Group__0 : rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 ;
    public final void rule__VersionRangeSetRequirement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1895:1: ( rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1 )
            // InternalSemver.g:1896:2: rule__VersionRangeSetRequirement__Group__0__Impl rule__VersionRangeSetRequirement__Group__1
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
    // InternalSemver.g:1903:1: rule__VersionRangeSetRequirement__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSetRequirement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1907:1: ( ( () ) )
            // InternalSemver.g:1908:1: ( () )
            {
            // InternalSemver.g:1908:1: ( () )
            // InternalSemver.g:1909:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); 
            }
            // InternalSemver.g:1910:2: ()
            // InternalSemver.g:1910:3: 
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
    // InternalSemver.g:1918:1: rule__VersionRangeSetRequirement__Group__1 : rule__VersionRangeSetRequirement__Group__1__Impl ;
    public final void rule__VersionRangeSetRequirement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1922:1: ( rule__VersionRangeSetRequirement__Group__1__Impl )
            // InternalSemver.g:1923:2: rule__VersionRangeSetRequirement__Group__1__Impl
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
    // InternalSemver.g:1929:1: rule__VersionRangeSetRequirement__Group__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) ;
    public final void rule__VersionRangeSetRequirement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1933:1: ( ( ( rule__VersionRangeSetRequirement__Group_1__0 )? ) )
            // InternalSemver.g:1934:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            {
            // InternalSemver.g:1934:1: ( ( rule__VersionRangeSetRequirement__Group_1__0 )? )
            // InternalSemver.g:1935:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); 
            }
            // InternalSemver.g:1936:2: ( rule__VersionRangeSetRequirement__Group_1__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0>=RULE_DIGITS && LA24_0<=RULE_ASTERIX)||(LA24_0>=34 && LA24_0<=40)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalSemver.g:1936:3: rule__VersionRangeSetRequirement__Group_1__0
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
    // InternalSemver.g:1945:1: rule__VersionRangeSetRequirement__Group_1__0 : rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1949:1: ( rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1 )
            // InternalSemver.g:1950:2: rule__VersionRangeSetRequirement__Group_1__0__Impl rule__VersionRangeSetRequirement__Group_1__1
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
    // InternalSemver.g:1957:1: rule__VersionRangeSetRequirement__Group_1__0__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1961:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) ) )
            // InternalSemver.g:1962:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            {
            // InternalSemver.g:1962:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 ) )
            // InternalSemver.g:1963:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); 
            }
            // InternalSemver.g:1964:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_0 )
            // InternalSemver.g:1964:3: rule__VersionRangeSetRequirement__RangesAssignment_1_0
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
    // InternalSemver.g:1972:1: rule__VersionRangeSetRequirement__Group_1__1 : rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1976:1: ( rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2 )
            // InternalSemver.g:1977:2: rule__VersionRangeSetRequirement__Group_1__1__Impl rule__VersionRangeSetRequirement__Group_1__2
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
    // InternalSemver.g:1984:1: rule__VersionRangeSetRequirement__Group_1__1__Impl : ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:1988:1: ( ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* ) )
            // InternalSemver.g:1989:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            {
            // InternalSemver.g:1989:1: ( ( rule__VersionRangeSetRequirement__Group_1_1__0 )* )
            // InternalSemver.g:1990:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); 
            }
            // InternalSemver.g:1991:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*
            loop25:
            do {
                int alt25=2;
                alt25 = dfa25.predict(input);
                switch (alt25) {
            	case 1 :
            	    // InternalSemver.g:1991:3: rule__VersionRangeSetRequirement__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_15);
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
    // InternalSemver.g:1999:1: rule__VersionRangeSetRequirement__Group_1__2 : rule__VersionRangeSetRequirement__Group_1__2__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2003:1: ( rule__VersionRangeSetRequirement__Group_1__2__Impl )
            // InternalSemver.g:2004:2: rule__VersionRangeSetRequirement__Group_1__2__Impl
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
    // InternalSemver.g:2010:1: rule__VersionRangeSetRequirement__Group_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2014:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2015:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2015:1: ( ( RULE_WS )* )
            // InternalSemver.g:2016:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); 
            }
            // InternalSemver.g:2017:2: ( RULE_WS )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_WS) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalSemver.g:2017:3: RULE_WS
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
    // InternalSemver.g:2026:1: rule__VersionRangeSetRequirement__Group_1_1__0 : rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2030:1: ( rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1 )
            // InternalSemver.g:2031:2: rule__VersionRangeSetRequirement__Group_1_1__0__Impl rule__VersionRangeSetRequirement__Group_1_1__1
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
    // InternalSemver.g:2038:1: rule__VersionRangeSetRequirement__Group_1_1__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2042:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2043:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2043:1: ( ( RULE_WS )* )
            // InternalSemver.g:2044:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); 
            }
            // InternalSemver.g:2045:2: ( RULE_WS )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==RULE_WS) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalSemver.g:2045:3: RULE_WS
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
    // InternalSemver.g:2053:1: rule__VersionRangeSetRequirement__Group_1_1__1 : rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2057:1: ( rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2 )
            // InternalSemver.g:2058:2: rule__VersionRangeSetRequirement__Group_1_1__1__Impl rule__VersionRangeSetRequirement__Group_1_1__2
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
    // InternalSemver.g:2065:1: rule__VersionRangeSetRequirement__Group_1_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2069:1: ( ( '||' ) )
            // InternalSemver.g:2070:1: ( '||' )
            {
            // InternalSemver.g:2070:1: ( '||' )
            // InternalSemver.g:2071:2: '||'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); 
            }
            match(input,44,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2080:1: rule__VersionRangeSetRequirement__Group_1_1__2 : rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2084:1: ( rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3 )
            // InternalSemver.g:2085:2: rule__VersionRangeSetRequirement__Group_1_1__2__Impl rule__VersionRangeSetRequirement__Group_1_1__3
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
    // InternalSemver.g:2092:1: rule__VersionRangeSetRequirement__Group_1_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2096:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2097:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2097:1: ( ( RULE_WS )* )
            // InternalSemver.g:2098:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); 
            }
            // InternalSemver.g:2099:2: ( RULE_WS )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==RULE_WS) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalSemver.g:2099:3: RULE_WS
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
    // InternalSemver.g:2107:1: rule__VersionRangeSetRequirement__Group_1_1__3 : rule__VersionRangeSetRequirement__Group_1_1__3__Impl ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2111:1: ( rule__VersionRangeSetRequirement__Group_1_1__3__Impl )
            // InternalSemver.g:2112:2: rule__VersionRangeSetRequirement__Group_1_1__3__Impl
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
    // InternalSemver.g:2118:1: rule__VersionRangeSetRequirement__Group_1_1__3__Impl : ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) ;
    public final void rule__VersionRangeSetRequirement__Group_1_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2122:1: ( ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) ) )
            // InternalSemver.g:2123:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            {
            // InternalSemver.g:2123:1: ( ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 ) )
            // InternalSemver.g:2124:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); 
            }
            // InternalSemver.g:2125:2: ( rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 )
            // InternalSemver.g:2125:3: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
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
    // InternalSemver.g:2134:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2138:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemver.g:2139:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
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
    // InternalSemver.g:2146:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2150:1: ( ( () ) )
            // InternalSemver.g:2151:1: ( () )
            {
            // InternalSemver.g:2151:1: ( () )
            // InternalSemver.g:2152:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            }
            // InternalSemver.g:2153:2: ()
            // InternalSemver.g:2153:3: 
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
    // InternalSemver.g:2161:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2165:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemver.g:2166:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
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
    // InternalSemver.g:2173:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2177:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemver.g:2178:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemver.g:2178:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemver.g:2179:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            }
            // InternalSemver.g:2180:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemver.g:2180:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSemver.g:2188:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2192:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemver.g:2193:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
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
    // InternalSemver.g:2200:1: rule__HyphenVersionRange__Group__2__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2204:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2205:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2205:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2206:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2206:2: ( ( RULE_WS ) )
            // InternalSemver.g:2207:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2208:3: ( RULE_WS )
            // InternalSemver.g:2208:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }

            }

            // InternalSemver.g:2211:2: ( ( RULE_WS )* )
            // InternalSemver.g:2212:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            }
            // InternalSemver.g:2213:3: ( RULE_WS )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==RULE_WS) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSemver.g:2213:4: RULE_WS
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
    // InternalSemver.g:2222:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2226:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSemver.g:2227:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
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
    // InternalSemver.g:2234:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2238:1: ( ( '-' ) )
            // InternalSemver.g:2239:1: ( '-' )
            {
            // InternalSemver.g:2239:1: ( '-' )
            // InternalSemver.g:2240:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2249:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2253:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSemver.g:2254:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
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
    // InternalSemver.g:2261:1: rule__HyphenVersionRange__Group__4__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2265:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2266:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2266:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2267:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2267:2: ( ( RULE_WS ) )
            // InternalSemver.g:2268:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2269:3: ( RULE_WS )
            // InternalSemver.g:2269:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }

            }

            // InternalSemver.g:2272:2: ( ( RULE_WS )* )
            // InternalSemver.g:2273:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            }
            // InternalSemver.g:2274:3: ( RULE_WS )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_WS) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalSemver.g:2274:4: RULE_WS
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
    // InternalSemver.g:2283:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2287:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSemver.g:2288:2: rule__HyphenVersionRange__Group__5__Impl
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
    // InternalSemver.g:2294:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2298:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSemver.g:2299:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSemver.g:2299:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSemver.g:2300:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            }
            // InternalSemver.g:2301:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSemver.g:2301:3: rule__HyphenVersionRange__ToAssignment_5
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
    // InternalSemver.g:2310:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2314:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSemver.g:2315:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
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
    // InternalSemver.g:2322:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2326:1: ( ( () ) )
            // InternalSemver.g:2327:1: ( () )
            {
            // InternalSemver.g:2327:1: ( () )
            // InternalSemver.g:2328:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            }
            // InternalSemver.g:2329:2: ()
            // InternalSemver.g:2329:3: 
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
    // InternalSemver.g:2337:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2341:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSemver.g:2342:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
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
    // InternalSemver.g:2349:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2353:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSemver.g:2354:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSemver.g:2354:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSemver.g:2355:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            }
            // InternalSemver.g:2356:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSemver.g:2356:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
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
    // InternalSemver.g:2364:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2368:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSemver.g:2369:2: rule__VersionRangeContraint__Group__2__Impl
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
    // InternalSemver.g:2375:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2379:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSemver.g:2380:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSemver.g:2380:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSemver.g:2381:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            }
            // InternalSemver.g:2382:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop31:
            do {
                int alt31=2;
                alt31 = dfa31.predict(input);
                switch (alt31) {
            	case 1 :
            	    // InternalSemver.g:2382:3: rule__VersionRangeContraint__Group_2__0
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
    // InternalSemver.g:2391:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2395:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSemver.g:2396:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
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
    // InternalSemver.g:2403:1: rule__VersionRangeContraint__Group_2__0__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2407:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSemver.g:2408:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSemver.g:2408:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSemver.g:2409:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSemver.g:2409:2: ( ( RULE_WS ) )
            // InternalSemver.g:2410:3: ( RULE_WS )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2411:3: ( RULE_WS )
            // InternalSemver.g:2411:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }

            }

            // InternalSemver.g:2414:2: ( ( RULE_WS )* )
            // InternalSemver.g:2415:3: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            }
            // InternalSemver.g:2416:3: ( RULE_WS )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_WS) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalSemver.g:2416:4: RULE_WS
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
    // InternalSemver.g:2425:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2429:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSemver.g:2430:2: rule__VersionRangeContraint__Group_2__1__Impl
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
    // InternalSemver.g:2436:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2440:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSemver.g:2441:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSemver.g:2441:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSemver.g:2442:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            }
            // InternalSemver.g:2443:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSemver.g:2443:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
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
    // InternalSemver.g:2452:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2456:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemver.g:2457:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
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
    // InternalSemver.g:2464:1: rule__SimpleVersion__Group__0__Impl : ( ( rule__SimpleVersion__Group_0__0 )* ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2468:1: ( ( ( rule__SimpleVersion__Group_0__0 )* ) )
            // InternalSemver.g:2469:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            {
            // InternalSemver.g:2469:1: ( ( rule__SimpleVersion__Group_0__0 )* )
            // InternalSemver.g:2470:2: ( rule__SimpleVersion__Group_0__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getGroup_0()); 
            }
            // InternalSemver.g:2471:2: ( rule__SimpleVersion__Group_0__0 )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( ((LA33_0>=34 && LA33_0<=40)) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalSemver.g:2471:3: rule__SimpleVersion__Group_0__0
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__SimpleVersion__Group_0__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop33;
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
    // InternalSemver.g:2479:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2483:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemver.g:2484:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
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
    // InternalSemver.g:2491:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2495:1: ( ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? ) )
            // InternalSemver.g:2496:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            {
            // InternalSemver.g:2496:1: ( ( rule__SimpleVersion__WithLetterVAssignment_1 )? )
            // InternalSemver.g:2497:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); 
            }
            // InternalSemver.g:2498:2: ( rule__SimpleVersion__WithLetterVAssignment_1 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RULE_LETTER_V) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalSemver.g:2498:3: rule__SimpleVersion__WithLetterVAssignment_1
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
    // InternalSemver.g:2506:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2510:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSemver.g:2511:2: rule__SimpleVersion__Group__2__Impl
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
    // InternalSemver.g:2517:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2521:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSemver.g:2522:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSemver.g:2522:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSemver.g:2523:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            }
            // InternalSemver.g:2524:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSemver.g:2524:3: rule__SimpleVersion__NumberAssignment_2
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
    // InternalSemver.g:2533:1: rule__SimpleVersion__Group_0__0 : rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 ;
    public final void rule__SimpleVersion__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2537:1: ( rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1 )
            // InternalSemver.g:2538:2: rule__SimpleVersion__Group_0__0__Impl rule__SimpleVersion__Group_0__1
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
    // InternalSemver.g:2545:1: rule__SimpleVersion__Group_0__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) ;
    public final void rule__SimpleVersion__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2549:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) ) )
            // InternalSemver.g:2550:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            {
            // InternalSemver.g:2550:1: ( ( rule__SimpleVersion__ComparatorsAssignment_0_0 ) )
            // InternalSemver.g:2551:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); 
            }
            // InternalSemver.g:2552:2: ( rule__SimpleVersion__ComparatorsAssignment_0_0 )
            // InternalSemver.g:2552:3: rule__SimpleVersion__ComparatorsAssignment_0_0
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
    // InternalSemver.g:2560:1: rule__SimpleVersion__Group_0__1 : rule__SimpleVersion__Group_0__1__Impl ;
    public final void rule__SimpleVersion__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2564:1: ( rule__SimpleVersion__Group_0__1__Impl )
            // InternalSemver.g:2565:2: rule__SimpleVersion__Group_0__1__Impl
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
    // InternalSemver.g:2571:1: rule__SimpleVersion__Group_0__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__SimpleVersion__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2575:1: ( ( ( RULE_WS )* ) )
            // InternalSemver.g:2576:1: ( ( RULE_WS )* )
            {
            // InternalSemver.g:2576:1: ( ( RULE_WS )* )
            // InternalSemver.g:2577:2: ( RULE_WS )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); 
            }
            // InternalSemver.g:2578:2: ( RULE_WS )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_WS) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalSemver.g:2578:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop35;
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
    // InternalSemver.g:2587:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2591:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemver.g:2592:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
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
    // InternalSemver.g:2599:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2603:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemver.g:2604:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemver.g:2604:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemver.g:2605:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            }
            // InternalSemver.g:2606:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemver.g:2606:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSemver.g:2614:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2618:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSemver.g:2619:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
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
    // InternalSemver.g:2626:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2630:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemver.g:2631:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemver.g:2631:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemver.g:2632:2: ( rule__VersionNumber__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            }
            // InternalSemver.g:2633:2: ( rule__VersionNumber__Group_1__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==29) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalSemver.g:2633:3: rule__VersionNumber__Group_1__0
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
    // InternalSemver.g:2641:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2645:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSemver.g:2646:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSemver.g:2652:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2656:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSemver.g:2657:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSemver.g:2657:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSemver.g:2658:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            }
            // InternalSemver.g:2659:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( ((LA37_0>=30 && LA37_0<=31)) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalSemver.g:2659:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSemver.g:2668:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2672:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemver.g:2673:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
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
    // InternalSemver.g:2680:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2684:1: ( ( '.' ) )
            // InternalSemver.g:2685:1: ( '.' )
            {
            // InternalSemver.g:2685:1: ( '.' )
            // InternalSemver.g:2686:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2695:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2699:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemver.g:2700:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
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
    // InternalSemver.g:2707:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2711:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemver.g:2712:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemver.g:2712:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemver.g:2713:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            }
            // InternalSemver.g:2714:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemver.g:2714:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSemver.g:2722:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2726:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemver.g:2727:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSemver.g:2733:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2737:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemver.g:2738:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemver.g:2738:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemver.g:2739:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            }
            // InternalSemver.g:2740:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==29) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalSemver.g:2740:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSemver.g:2749:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2753:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemver.g:2754:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
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
    // InternalSemver.g:2761:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2765:1: ( ( '.' ) )
            // InternalSemver.g:2766:1: ( '.' )
            {
            // InternalSemver.g:2766:1: ( '.' )
            // InternalSemver.g:2767:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2776:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2780:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemver.g:2781:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
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
    // InternalSemver.g:2788:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2792:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSemver.g:2793:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSemver.g:2793:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSemver.g:2794:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            }
            // InternalSemver.g:2795:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSemver.g:2795:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSemver.g:2803:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2807:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemver.g:2808:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSemver.g:2814:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2818:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSemver.g:2819:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSemver.g:2819:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSemver.g:2820:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            }
            // InternalSemver.g:2821:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==29) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalSemver.g:2821:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_21);
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
    // InternalSemver.g:2830:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2834:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSemver.g:2835:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
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
    // InternalSemver.g:2842:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2846:1: ( ( '.' ) )
            // InternalSemver.g:2847:1: ( '.' )
            {
            // InternalSemver.g:2847:1: ( '.' )
            // InternalSemver.g:2848:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2857:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2861:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSemver.g:2862:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSemver.g:2868:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2872:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSemver.g:2873:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSemver.g:2873:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSemver.g:2874:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            }
            // InternalSemver.g:2875:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSemver.g:2875:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSemver.g:2884:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2888:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemver.g:2889:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
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
    // InternalSemver.g:2896:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2900:1: ( ( '-' ) )
            // InternalSemver.g:2901:1: ( '-' )
            {
            // InternalSemver.g:2901:1: ( '-' )
            // InternalSemver.g:2902:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2911:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2915:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSemver.g:2916:2: rule__Qualifier__Group_0__1__Impl
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
    // InternalSemver.g:2922:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2926:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemver.g:2927:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemver.g:2927:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemver.g:2928:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            }
            // InternalSemver.g:2929:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemver.g:2929:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSemver.g:2938:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2942:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemver.g:2943:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
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
    // InternalSemver.g:2950:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2954:1: ( ( '+' ) )
            // InternalSemver.g:2955:1: ( '+' )
            {
            // InternalSemver.g:2955:1: ( '+' )
            // InternalSemver.g:2956:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:2965:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2969:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemver.g:2970:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSemver.g:2976:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2980:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemver.g:2981:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemver.g:2981:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemver.g:2982:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            }
            // InternalSemver.g:2983:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemver.g:2983:3: rule__Qualifier__BuildMetadataAssignment_1_1
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
    // InternalSemver.g:2992:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:2996:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSemver.g:2997:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
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
    // InternalSemver.g:3004:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3008:1: ( ( '-' ) )
            // InternalSemver.g:3009:1: ( '-' )
            {
            // InternalSemver.g:3009:1: ( '-' )
            // InternalSemver.g:3010:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3019:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3023:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSemver.g:3024:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
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
    // InternalSemver.g:3031:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3035:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSemver.g:3036:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSemver.g:3036:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSemver.g:3037:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            }
            // InternalSemver.g:3038:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSemver.g:3038:3: rule__Qualifier__PreReleaseAssignment_2_1
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
    // InternalSemver.g:3046:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3050:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSemver.g:3051:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
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
    // InternalSemver.g:3058:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3062:1: ( ( '+' ) )
            // InternalSemver.g:3063:1: ( '+' )
            {
            // InternalSemver.g:3063:1: ( '+' )
            // InternalSemver.g:3064:2: '+'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3073:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3077:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSemver.g:3078:2: rule__Qualifier__Group_2__3__Impl
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
    // InternalSemver.g:3084:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3088:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSemver.g:3089:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSemver.g:3089:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSemver.g:3090:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            }
            // InternalSemver.g:3091:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSemver.g:3091:3: rule__Qualifier__BuildMetadataAssignment_2_3
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
    // InternalSemver.g:3100:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3104:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSemver.g:3105:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
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
    // InternalSemver.g:3112:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3116:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSemver.g:3117:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSemver.g:3117:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSemver.g:3118:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            }
            // InternalSemver.g:3119:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSemver.g:3119:3: rule__QualifierTag__PartsAssignment_0
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
    // InternalSemver.g:3127:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3131:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSemver.g:3132:2: rule__QualifierTag__Group__1__Impl
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
    // InternalSemver.g:3138:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3142:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSemver.g:3143:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSemver.g:3143:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSemver.g:3144:2: ( rule__QualifierTag__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            }
            // InternalSemver.g:3145:2: ( rule__QualifierTag__Group_1__0 )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==29) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalSemver.g:3145:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_21);
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
    // InternalSemver.g:3154:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3158:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSemver.g:3159:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
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
    // InternalSemver.g:3166:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3170:1: ( ( '.' ) )
            // InternalSemver.g:3171:1: ( '.' )
            {
            // InternalSemver.g:3171:1: ( '.' )
            // InternalSemver.g:3172:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
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
    // InternalSemver.g:3181:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3185:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSemver.g:3186:2: rule__QualifierTag__Group_1__1__Impl
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
    // InternalSemver.g:3192:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3196:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSemver.g:3197:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSemver.g:3197:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSemver.g:3198:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            }
            // InternalSemver.g:3199:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSemver.g:3199:3: rule__QualifierTag__PartsAssignment_1_1
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
    // InternalSemver.g:3208:1: rule__PATH__Group__0 : rule__PATH__Group__0__Impl rule__PATH__Group__1 ;
    public final void rule__PATH__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3212:1: ( rule__PATH__Group__0__Impl rule__PATH__Group__1 )
            // InternalSemver.g:3213:2: rule__PATH__Group__0__Impl rule__PATH__Group__1
            {
            pushFollow(FOLLOW_23);
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
    // InternalSemver.g:3220:1: rule__PATH__Group__0__Impl : ( ( rule__PATH__Alternatives_0 ) ) ;
    public final void rule__PATH__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3224:1: ( ( ( rule__PATH__Alternatives_0 ) ) )
            // InternalSemver.g:3225:1: ( ( rule__PATH__Alternatives_0 ) )
            {
            // InternalSemver.g:3225:1: ( ( rule__PATH__Alternatives_0 ) )
            // InternalSemver.g:3226:2: ( rule__PATH__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_0()); 
            }
            // InternalSemver.g:3227:2: ( rule__PATH__Alternatives_0 )
            // InternalSemver.g:3227:3: rule__PATH__Alternatives_0
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
    // InternalSemver.g:3235:1: rule__PATH__Group__1 : rule__PATH__Group__1__Impl ;
    public final void rule__PATH__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3239:1: ( rule__PATH__Group__1__Impl )
            // InternalSemver.g:3240:2: rule__PATH__Group__1__Impl
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
    // InternalSemver.g:3246:1: rule__PATH__Group__1__Impl : ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) ) ;
    public final void rule__PATH__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3250:1: ( ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) ) )
            // InternalSemver.g:3251:1: ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3251:1: ( ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* ) )
            // InternalSemver.g:3252:2: ( ( rule__PATH__Alternatives_1 ) ) ( ( rule__PATH__Alternatives_1 )* )
            {
            // InternalSemver.g:3252:2: ( ( rule__PATH__Alternatives_1 ) )
            // InternalSemver.g:3253:3: ( rule__PATH__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3254:3: ( rule__PATH__Alternatives_1 )
            // InternalSemver.g:3254:4: rule__PATH__Alternatives_1
            {
            pushFollow(FOLLOW_24);
            rule__PATH__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3257:2: ( ( rule__PATH__Alternatives_1 )* )
            // InternalSemver.g:3258:3: ( rule__PATH__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPATHAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3259:3: ( rule__PATH__Alternatives_1 )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( ((LA41_0>=RULE_LETTER_NO_VX && LA41_0<=RULE_LETTER_X)||(LA41_0>=28 && LA41_0<=30)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSemver.g:3259:4: rule__PATH__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_24);
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
    // InternalSemver.g:3269:1: rule__URL_PROTOCOL__Group__0 : rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 ;
    public final void rule__URL_PROTOCOL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3273:1: ( rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1 )
            // InternalSemver.g:3274:2: rule__URL_PROTOCOL__Group__0__Impl rule__URL_PROTOCOL__Group__1
            {
            pushFollow(FOLLOW_25);
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
    // InternalSemver.g:3281:1: rule__URL_PROTOCOL__Group__0__Impl : ( RULE_LETTER_NO_VX ) ;
    public final void rule__URL_PROTOCOL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3285:1: ( ( RULE_LETTER_NO_VX ) )
            // InternalSemver.g:3286:1: ( RULE_LETTER_NO_VX )
            {
            // InternalSemver.g:3286:1: ( RULE_LETTER_NO_VX )
            // InternalSemver.g:3287:2: RULE_LETTER_NO_VX
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
    // InternalSemver.g:3296:1: rule__URL_PROTOCOL__Group__1 : rule__URL_PROTOCOL__Group__1__Impl ;
    public final void rule__URL_PROTOCOL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3300:1: ( rule__URL_PROTOCOL__Group__1__Impl )
            // InternalSemver.g:3301:2: rule__URL_PROTOCOL__Group__1__Impl
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
    // InternalSemver.g:3307:1: rule__URL_PROTOCOL__Group__1__Impl : ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) ;
    public final void rule__URL_PROTOCOL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3311:1: ( ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) ) )
            // InternalSemver.g:3312:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3312:1: ( ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* ) )
            // InternalSemver.g:3313:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) ) ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            {
            // InternalSemver.g:3313:2: ( ( rule__URL_PROTOCOL__Alternatives_1 ) )
            // InternalSemver.g:3314:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3315:3: ( rule__URL_PROTOCOL__Alternatives_1 )
            // InternalSemver.g:3315:4: rule__URL_PROTOCOL__Alternatives_1
            {
            pushFollow(FOLLOW_26);
            rule__URL_PROTOCOL__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }

            }

            // InternalSemver.g:3318:2: ( ( rule__URL_PROTOCOL__Alternatives_1 )* )
            // InternalSemver.g:3319:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3320:3: ( rule__URL_PROTOCOL__Alternatives_1 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==RULE_LETTER_NO_VX||(LA42_0>=RULE_LETTER_V && LA42_0<=RULE_LETTER_X)||LA42_0==31) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalSemver.g:3320:4: rule__URL_PROTOCOL__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_26);
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
    // InternalSemver.g:3330:1: rule__URL__Group__0 : rule__URL__Group__0__Impl rule__URL__Group__1 ;
    public final void rule__URL__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3334:1: ( rule__URL__Group__0__Impl rule__URL__Group__1 )
            // InternalSemver.g:3335:2: rule__URL__Group__0__Impl rule__URL__Group__1
            {
            pushFollow(FOLLOW_27);
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
    // InternalSemver.g:3342:1: rule__URL__Group__0__Impl : ( RULE_LETTER_NO_VX ) ;
    public final void rule__URL__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3346:1: ( ( RULE_LETTER_NO_VX ) )
            // InternalSemver.g:3347:1: ( RULE_LETTER_NO_VX )
            {
            // InternalSemver.g:3347:1: ( RULE_LETTER_NO_VX )
            // InternalSemver.g:3348:2: RULE_LETTER_NO_VX
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
    // InternalSemver.g:3357:1: rule__URL__Group__1 : rule__URL__Group__1__Impl rule__URL__Group__2 ;
    public final void rule__URL__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3361:1: ( rule__URL__Group__1__Impl rule__URL__Group__2 )
            // InternalSemver.g:3362:2: rule__URL__Group__1__Impl rule__URL__Group__2
            {
            pushFollow(FOLLOW_27);
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
    // InternalSemver.g:3369:1: rule__URL__Group__1__Impl : ( ( rule__URL__Alternatives_1 )* ) ;
    public final void rule__URL__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3373:1: ( ( ( rule__URL__Alternatives_1 )* ) )
            // InternalSemver.g:3374:1: ( ( rule__URL__Alternatives_1 )* )
            {
            // InternalSemver.g:3374:1: ( ( rule__URL__Alternatives_1 )* )
            // InternalSemver.g:3375:2: ( rule__URL__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3376:2: ( rule__URL__Alternatives_1 )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=RULE_LETTER_NO_VX && LA43_0<=RULE_LETTER_X)||LA43_0==30) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSemver.g:3376:3: rule__URL__Alternatives_1
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
    // InternalSemver.g:3384:1: rule__URL__Group__2 : rule__URL__Group__2__Impl rule__URL__Group__3 ;
    public final void rule__URL__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3388:1: ( rule__URL__Group__2__Impl rule__URL__Group__3 )
            // InternalSemver.g:3389:2: rule__URL__Group__2__Impl rule__URL__Group__3
            {
            pushFollow(FOLLOW_27);
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
    // InternalSemver.g:3396:1: rule__URL__Group__2__Impl : ( ( rule__URL__Alternatives_2 ) ) ;
    public final void rule__URL__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3400:1: ( ( ( rule__URL__Alternatives_2 ) ) )
            // InternalSemver.g:3401:1: ( ( rule__URL__Alternatives_2 ) )
            {
            // InternalSemver.g:3401:1: ( ( rule__URL__Alternatives_2 ) )
            // InternalSemver.g:3402:2: ( rule__URL__Alternatives_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_2()); 
            }
            // InternalSemver.g:3403:2: ( rule__URL__Alternatives_2 )
            // InternalSemver.g:3403:3: rule__URL__Alternatives_2
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
    // InternalSemver.g:3411:1: rule__URL__Group__3 : rule__URL__Group__3__Impl ;
    public final void rule__URL__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3415:1: ( rule__URL__Group__3__Impl )
            // InternalSemver.g:3416:2: rule__URL__Group__3__Impl
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
    // InternalSemver.g:3422:1: rule__URL__Group__3__Impl : ( ( rule__URL__Alternatives_3 )* ) ;
    public final void rule__URL__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3426:1: ( ( ( rule__URL__Alternatives_3 )* ) )
            // InternalSemver.g:3427:1: ( ( rule__URL__Alternatives_3 )* )
            {
            // InternalSemver.g:3427:1: ( ( rule__URL__Alternatives_3 )* )
            // InternalSemver.g:3428:2: ( rule__URL__Alternatives_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLAccess().getAlternatives_3()); 
            }
            // InternalSemver.g:3429:2: ( rule__URL__Alternatives_3 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( ((LA44_0>=RULE_LETTER_NO_VX && LA44_0<=RULE_LETTER_X)||(LA44_0>=28 && LA44_0<=30)||(LA44_0>=32 && LA44_0<=33)) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalSemver.g:3429:3: rule__URL__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_28);
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
    // InternalSemver.g:3438:1: rule__TAG__Group__0 : rule__TAG__Group__0__Impl rule__TAG__Group__1 ;
    public final void rule__TAG__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3442:1: ( rule__TAG__Group__0__Impl rule__TAG__Group__1 )
            // InternalSemver.g:3443:2: rule__TAG__Group__0__Impl rule__TAG__Group__1
            {
            pushFollow(FOLLOW_29);
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
    // InternalSemver.g:3450:1: rule__TAG__Group__0__Impl : ( RULE_LETTER_NO_VX ) ;
    public final void rule__TAG__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3454:1: ( ( RULE_LETTER_NO_VX ) )
            // InternalSemver.g:3455:1: ( RULE_LETTER_NO_VX )
            {
            // InternalSemver.g:3455:1: ( RULE_LETTER_NO_VX )
            // InternalSemver.g:3456:2: RULE_LETTER_NO_VX
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_0()); 
            }
            match(input,RULE_LETTER_NO_VX,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTAGAccess().getLETTER_NO_VXTerminalRuleCall_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3465:1: rule__TAG__Group__1 : rule__TAG__Group__1__Impl ;
    public final void rule__TAG__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3469:1: ( rule__TAG__Group__1__Impl )
            // InternalSemver.g:3470:2: rule__TAG__Group__1__Impl
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
    // InternalSemver.g:3476:1: rule__TAG__Group__1__Impl : ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) ;
    public final void rule__TAG__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3480:1: ( ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) ) )
            // InternalSemver.g:3481:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3481:1: ( ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* ) )
            // InternalSemver.g:3482:2: ( ( rule__TAG__Alternatives_1 ) ) ( ( rule__TAG__Alternatives_1 )* )
            {
            // InternalSemver.g:3482:2: ( ( rule__TAG__Alternatives_1 ) )
            // InternalSemver.g:3483:3: ( rule__TAG__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3484:3: ( rule__TAG__Alternatives_1 )
            // InternalSemver.g:3484:4: rule__TAG__Alternatives_1
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

            // InternalSemver.g:3487:2: ( ( rule__TAG__Alternatives_1 )* )
            // InternalSemver.g:3488:3: ( rule__TAG__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTAGAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3489:3: ( rule__TAG__Alternatives_1 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=RULE_LETTER_NO_VX && LA45_0<=RULE_LETTER_X)||LA45_0==30) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalSemver.g:3489:4: rule__TAG__Alternatives_1
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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0"
    // InternalSemver.g:3499:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3503:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 )
            // InternalSemver.g:3504:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1
            {
            pushFollow(FOLLOW_29);
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
    // InternalSemver.g:3511:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl : ( RULE_DIGITS ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3515:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:3516:1: ( RULE_DIGITS )
            {
            // InternalSemver.g:3516:1: ( RULE_DIGITS )
            // InternalSemver.g:3517:2: RULE_DIGITS
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
    // InternalSemver.g:3526:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1 : rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3530:1: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl )
            // InternalSemver.g:3531:2: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl
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
    // InternalSemver.g:3537:1: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl : ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) ;
    public final void rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3541:1: ( ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) ) )
            // InternalSemver.g:3542:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            {
            // InternalSemver.g:3542:1: ( ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* ) )
            // InternalSemver.g:3543:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) ) ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            {
            // InternalSemver.g:3543:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 ) )
            // InternalSemver.g:3544:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3545:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )
            // InternalSemver.g:3545:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
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

            // InternalSemver.g:3548:2: ( ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )* )
            // InternalSemver.g:3549:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); 
            }
            // InternalSemver.g:3550:3: ( rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=RULE_LETTER_NO_VX && LA46_0<=RULE_LETTER_X)||LA46_0==30) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalSemver.g:3550:4: rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop46;
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
    // InternalSemver.g:3560:1: rule__LocalPathVersionRequirement__LocalPathAssignment_1 : ( rulePATH ) ;
    public final void rule__LocalPathVersionRequirement__LocalPathAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3564:1: ( ( rulePATH ) )
            // InternalSemver.g:3565:2: ( rulePATH )
            {
            // InternalSemver.g:3565:2: ( rulePATH )
            // InternalSemver.g:3566:3: rulePATH
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
    // InternalSemver.g:3575:1: rule__URLVersionRequirement__ProtocolAssignment_0 : ( ruleURL_PROTOCOL ) ;
    public final void rule__URLVersionRequirement__ProtocolAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3579:1: ( ( ruleURL_PROTOCOL ) )
            // InternalSemver.g:3580:2: ( ruleURL_PROTOCOL )
            {
            // InternalSemver.g:3580:2: ( ruleURL_PROTOCOL )
            // InternalSemver.g:3581:3: ruleURL_PROTOCOL
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
    // InternalSemver.g:3590:1: rule__URLVersionRequirement__UrlAssignment_2 : ( ruleURL ) ;
    public final void rule__URLVersionRequirement__UrlAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3594:1: ( ( ruleURL ) )
            // InternalSemver.g:3595:2: ( ruleURL )
            {
            // InternalSemver.g:3595:2: ( ruleURL )
            // InternalSemver.g:3596:3: ruleURL
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
    // InternalSemver.g:3605:1: rule__URLVersionRequirement__VersionSpecifierAssignment_3_1 : ( ruleURLVersionSpecifier ) ;
    public final void rule__URLVersionRequirement__VersionSpecifierAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3609:1: ( ( ruleURLVersionSpecifier ) )
            // InternalSemver.g:3610:2: ( ruleURLVersionSpecifier )
            {
            // InternalSemver.g:3610:2: ( ruleURLVersionSpecifier )
            // InternalSemver.g:3611:3: ruleURLVersionSpecifier
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
    // InternalSemver.g:3620:1: rule__URLVersionSpecifier__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3624:1: ( ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS ) )
            // InternalSemver.g:3625:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            {
            // InternalSemver.g:3625:2: ( ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS )
            // InternalSemver.g:3626:3: ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
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
    // InternalSemver.g:3635:1: rule__URLVersionSpecifier__CommitISHAssignment_2_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__URLVersionSpecifier__CommitISHAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3639:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3640:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3640:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3641:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:3650:1: rule__URLSemver__WithSemverTagAssignment_1 : ( ( 'semver:' ) ) ;
    public final void rule__URLSemver__WithSemverTagAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3654:1: ( ( ( 'semver:' ) ) )
            // InternalSemver.g:3655:2: ( ( 'semver:' ) )
            {
            // InternalSemver.g:3655:2: ( ( 'semver:' ) )
            // InternalSemver.g:3656:3: ( 'semver:' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagSemverKeyword_1_0()); 
            }
            // InternalSemver.g:3657:3: ( 'semver:' )
            // InternalSemver.g:3658:4: 'semver:'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getURLSemverAccess().getWithSemverTagSemverKeyword_1_0()); 
            }
            match(input,45,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getWithSemverTagSemverKeyword_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getURLSemverAccess().getWithSemverTagSemverKeyword_1_0()); 
            }

            }


            }

        }
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
    // InternalSemver.g:3669:1: rule__URLSemver__SimpleVersionAssignment_2 : ( ruleSimpleVersion ) ;
    public final void rule__URLSemver__SimpleVersionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3673:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3674:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3674:2: ( ruleSimpleVersion )
            // InternalSemver.g:3675:3: ruleSimpleVersion
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
    // InternalSemver.g:3684:1: rule__TagVersionRequirement__TagNameAssignment : ( ruleTAG ) ;
    public final void rule__TagVersionRequirement__TagNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3688:1: ( ( ruleTAG ) )
            // InternalSemver.g:3689:2: ( ruleTAG )
            {
            // InternalSemver.g:3689:2: ( ruleTAG )
            // InternalSemver.g:3690:3: ruleTAG
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
    // InternalSemver.g:3699:1: rule__GitHubVersionRequirement__GithubUrlAssignment_0 : ( ruleURL ) ;
    public final void rule__GitHubVersionRequirement__GithubUrlAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3703:1: ( ( ruleURL ) )
            // InternalSemver.g:3704:2: ( ruleURL )
            {
            // InternalSemver.g:3704:2: ( ruleURL )
            // InternalSemver.g:3705:3: ruleURL
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
    // InternalSemver.g:3714:1: rule__GitHubVersionRequirement__CommitISHAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__GitHubVersionRequirement__CommitISHAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3718:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:3719:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:3719:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:3720:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:3729:1: rule__VersionRangeSetRequirement__RangesAssignment_1_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3733:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:3734:2: ( ruleVersionRange )
            {
            // InternalSemver.g:3734:2: ( ruleVersionRange )
            // InternalSemver.g:3735:3: ruleVersionRange
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
    // InternalSemver.g:3744:1: rule__VersionRangeSetRequirement__RangesAssignment_1_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSetRequirement__RangesAssignment_1_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3748:1: ( ( ruleVersionRange ) )
            // InternalSemver.g:3749:2: ( ruleVersionRange )
            {
            // InternalSemver.g:3749:2: ( ruleVersionRange )
            // InternalSemver.g:3750:3: ruleVersionRange
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
    // InternalSemver.g:3759:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3763:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3764:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3764:2: ( ruleVersionNumber )
            // InternalSemver.g:3765:3: ruleVersionNumber
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
    // InternalSemver.g:3774:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3778:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3779:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3779:2: ( ruleVersionNumber )
            // InternalSemver.g:3780:3: ruleVersionNumber
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
    // InternalSemver.g:3789:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3793:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3794:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3794:2: ( ruleSimpleVersion )
            // InternalSemver.g:3795:3: ruleSimpleVersion
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
    // InternalSemver.g:3804:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3808:1: ( ( ruleSimpleVersion ) )
            // InternalSemver.g:3809:2: ( ruleSimpleVersion )
            {
            // InternalSemver.g:3809:2: ( ruleSimpleVersion )
            // InternalSemver.g:3810:3: ruleSimpleVersion
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
    // InternalSemver.g:3819:1: rule__SimpleVersion__ComparatorsAssignment_0_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3823:1: ( ( ruleVersionComparator ) )
            // InternalSemver.g:3824:2: ( ruleVersionComparator )
            {
            // InternalSemver.g:3824:2: ( ruleVersionComparator )
            // InternalSemver.g:3825:3: ruleVersionComparator
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
    // InternalSemver.g:3834:1: rule__SimpleVersion__WithLetterVAssignment_1 : ( RULE_LETTER_V ) ;
    public final void rule__SimpleVersion__WithLetterVAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3838:1: ( ( RULE_LETTER_V ) )
            // InternalSemver.g:3839:2: ( RULE_LETTER_V )
            {
            // InternalSemver.g:3839:2: ( RULE_LETTER_V )
            // InternalSemver.g:3840:3: RULE_LETTER_V
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
    // InternalSemver.g:3849:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3853:1: ( ( ruleVersionNumber ) )
            // InternalSemver.g:3854:2: ( ruleVersionNumber )
            {
            // InternalSemver.g:3854:2: ( ruleVersionNumber )
            // InternalSemver.g:3855:3: ruleVersionNumber
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
    // InternalSemver.g:3864:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3868:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3869:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3869:2: ( ruleVersionPart )
            // InternalSemver.g:3870:3: ruleVersionPart
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
    // InternalSemver.g:3879:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3883:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3884:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3884:2: ( ruleVersionPart )
            // InternalSemver.g:3885:3: ruleVersionPart
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
    // InternalSemver.g:3894:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3898:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3899:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3899:2: ( ruleVersionPart )
            // InternalSemver.g:3900:3: ruleVersionPart
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
    // InternalSemver.g:3909:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3913:1: ( ( ruleVersionPart ) )
            // InternalSemver.g:3914:2: ( ruleVersionPart )
            {
            // InternalSemver.g:3914:2: ( ruleVersionPart )
            // InternalSemver.g:3915:3: ruleVersionPart
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
    // InternalSemver.g:3924:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3928:1: ( ( ruleQualifier ) )
            // InternalSemver.g:3929:2: ( ruleQualifier )
            {
            // InternalSemver.g:3929:2: ( ruleQualifier )
            // InternalSemver.g:3930:3: ruleQualifier
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
    // InternalSemver.g:3939:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3943:1: ( ( ruleWILDCARD ) )
            // InternalSemver.g:3944:2: ( ruleWILDCARD )
            {
            // InternalSemver.g:3944:2: ( ruleWILDCARD )
            // InternalSemver.g:3945:3: ruleWILDCARD
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
    // InternalSemver.g:3954:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3958:1: ( ( RULE_DIGITS ) )
            // InternalSemver.g:3959:2: ( RULE_DIGITS )
            {
            // InternalSemver.g:3959:2: ( RULE_DIGITS )
            // InternalSemver.g:3960:3: RULE_DIGITS
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
    // InternalSemver.g:3969:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3973:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3974:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3974:2: ( ruleQualifierTag )
            // InternalSemver.g:3975:3: ruleQualifierTag
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
    // InternalSemver.g:3984:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:3988:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:3989:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:3989:2: ( ruleQualifierTag )
            // InternalSemver.g:3990:3: ruleQualifierTag
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
    // InternalSemver.g:3999:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4003:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4004:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4004:2: ( ruleQualifierTag )
            // InternalSemver.g:4005:3: ruleQualifierTag
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
    // InternalSemver.g:4014:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4018:1: ( ( ruleQualifierTag ) )
            // InternalSemver.g:4019:2: ( ruleQualifierTag )
            {
            // InternalSemver.g:4019:2: ( ruleQualifierTag )
            // InternalSemver.g:4020:3: ruleQualifierTag
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
    // InternalSemver.g:4029:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4033:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4034:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4034:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4035:3: ruleALPHA_NUMERIC_CHARS
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
    // InternalSemver.g:4044:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemver.g:4048:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSemver.g:4049:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSemver.g:4049:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSemver.g:4050:3: ruleALPHA_NUMERIC_CHARS
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

    // $ANTLR start synpred6_InternalSemver
    public final void synpred6_InternalSemver_fragment() throws RecognitionException {   
        // InternalSemver.g:717:2: ( ( ( rule__URLVersionSpecifier__Group_0__0 ) ) )
        // InternalSemver.g:717:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        {
        // InternalSemver.g:717:2: ( ( rule__URLVersionSpecifier__Group_0__0 ) )
        // InternalSemver.g:718:3: ( rule__URLVersionSpecifier__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); 
        }
        // InternalSemver.g:719:3: ( rule__URLVersionSpecifier__Group_0__0 )
        // InternalSemver.g:719:4: rule__URLVersionSpecifier__Group_0__0
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


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA31 dfa31 = new DFA31(this);
    static final String dfa_1s = "\23\uffff";
    static final String dfa_2s = "\3\uffff\5\17\2\uffff\5\17\1\uffff\3\17";
    static final String dfa_3s = "\1\4\1\uffff\6\4\2\uffff\5\4\1\uffff\3\4";
    static final String dfa_4s = "\1\51\1\uffff\3\41\3\52\2\uffff\5\41\1\uffff\3\52";
    static final String dfa_5s = "\1\uffff\1\1\6\uffff\1\3\1\2\5\uffff\1\4\3\uffff";
    static final String dfa_6s = "\23\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\44\uffff\1\1",
            "",
            "\1\7\1\4\1\5\1\6\24\uffff\2\10\1\3\1\11\2\10",
            "\1\16\1\13\1\14\1\15\1\uffff\1\17\22\uffff\2\10\1\12\1\uffff\2\10",
            "\1\16\1\13\1\14\1\15\1\uffff\1\17\22\uffff\2\10\1\12\1\uffff\2\10",
            "\1\22\1\13\1\20\1\21\1\uffff\1\17\22\uffff\2\10\1\12\1\11\2\10\10\uffff\1\11",
            "\1\22\1\13\1\20\1\21\1\uffff\1\17\22\uffff\2\10\1\12\1\11\2\10\10\uffff\1\11",
            "\1\22\1\13\1\20\1\21\1\uffff\1\17\22\uffff\2\10\1\12\1\11\2\10\10\uffff\1\11",
            "",
            "",
            "\1\16\1\13\1\14\1\15\1\uffff\1\17\22\uffff\2\10\1\12\1\uffff\2\10",
            "\1\16\1\13\1\14\1\15\1\uffff\1\17\22\uffff\2\10\1\12\1\uffff\2\10",
            "\1\16\1\13\1\14\1\15\1\uffff\1\17\22\uffff\2\10\1\12\1\uffff\2\10",
            "\1\16\1\13\1\14\1\15\1\uffff\1\17\22\uffff\2\10\1\12\1\uffff\2\10",
            "\1\16\1\13\1\14\1\15\1\uffff\1\17\22\uffff\2\10\1\12\1\uffff\2\10",
            "",
            "\1\22\1\13\1\20\1\21\1\uffff\1\17\22\uffff\2\10\1\12\1\11\2\10\10\uffff\1\11",
            "\1\22\1\13\1\20\1\21\1\uffff\1\17\22\uffff\2\10\1\12\1\11\2\10\10\uffff\1\11",
            "\1\22\1\13\1\20\1\21\1\uffff\1\17\22\uffff\2\10\1\12\1\11\2\10\10\uffff\1\11"
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
            return "679:1: rule__NPMVersionRequirement__Alternatives_1_0 : ( ( ( ruleLocalPathVersionRequirement ) ) | ( ruleURLVersionRequirement ) | ( ruleGitHubVersionRequirement ) | ( ruleTagVersionRequirement ) );";
        }
    }
    static final String dfa_8s = "\126\uffff";
    static final String dfa_9s = "\2\uffff\3\1\3\uffff\17\1\2\uffff\5\1\2\uffff\5\1\1\uffff\22\1\1\uffff\12\1\1\uffff\22\1";
    static final String dfa_10s = "\1\5\1\uffff\3\11\1\5\2\4\1\5\3\11\12\4\1\5\1\uffff\1\5\15\4\3\11\17\4\1\5\20\4\3\11\12\4";
    static final String dfa_11s = "\1\50\1\uffff\3\54\1\10\2\36\17\54\1\uffff\1\10\5\54\2\36\5\54\1\36\22\54\1\10\12\54\1\36\22\54";
    static final String dfa_12s = "\1\uffff\1\1\25\uffff\1\2\76\uffff";
    static final String dfa_13s = "\126\uffff}>";
    static final String[] dfa_14s = {
            "\1\4\1\1\1\2\1\3\31\uffff\7\1",
            "",
            "\1\10\23\uffff\1\5\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\5\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\5\1\6\1\7\14\uffff\1\1",
            "\1\13\1\uffff\1\11\1\12",
            "\1\20\1\15\1\16\1\17\26\uffff\1\14",
            "\1\25\1\22\1\23\1\24\26\uffff\1\21",
            "\4\1\1\26\24\uffff\1\27\3\uffff\7\1\3\uffff\1\1",
            "\1\10\23\uffff\1\30\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\30\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\30\1\6\1\7\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\4\1\1\26\24\uffff\1\27\3\uffff\7\1\3\uffff\1\1",
            "",
            "\1\50\1\uffff\1\46\1\47",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\35\1\32\1\33\1\34\1\uffff\1\10\23\uffff\1\36\1\31\1\37\14\uffff\1\1",
            "\1\55\1\52\1\53\1\54\26\uffff\1\51",
            "\1\62\1\57\1\60\1\61\26\uffff\1\56",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\44\1\41\1\42\1\43\1\uffff\1\10\23\uffff\1\45\1\40\15\uffff\1\1",
            "\1\67\1\64\1\65\1\66\26\uffff\1\63",
            "\1\10\23\uffff\1\70\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\70\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\70\1\6\1\7\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\113\1\uffff\1\111\1\112",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\75\1\72\1\73\1\74\1\uffff\1\10\23\uffff\1\36\1\71\1\37\14\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\102\1\77\1\100\1\101\1\uffff\1\10\23\uffff\1\103\1\76\15\uffff\1\1",
            "\1\120\1\115\1\116\1\117\26\uffff\1\114",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\110\1\105\1\106\1\107\1\uffff\1\10\23\uffff\1\45\1\104\15\uffff\1\1",
            "\1\10\23\uffff\1\70\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\70\1\6\1\7\14\uffff\1\1",
            "\1\10\23\uffff\1\70\1\6\1\7\14\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1",
            "\1\125\1\122\1\123\1\124\1\uffff\1\10\23\uffff\1\103\1\121\15\uffff\1\1"
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
            return "739:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
    static final String dfa_15s = "\32\uffff";
    static final String dfa_16s = "\3\uffff\12\17\3\uffff\12\17";
    static final String dfa_17s = "\1\36\1\4\1\uffff\13\4\2\uffff\12\4";
    static final String dfa_18s = "\1\37\1\36\1\uffff\12\54\1\36\2\uffff\12\54";
    static final String dfa_19s = "\2\uffff\1\2\13\uffff\1\3\1\1\12\uffff";
    static final String dfa_20s = "\32\uffff}>";
    static final String[] dfa_21s = {
            "\1\1\1\2",
            "\1\7\1\4\1\5\1\6\26\uffff\1\3",
            "",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\14\1\11\1\12\1\13\1\uffff\1\17\23\uffff\1\15\1\10\1\16\14\uffff\1\17",
            "\1\24\1\21\1\22\1\23\26\uffff\1\20",
            "",
            "",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17",
            "\1\31\1\26\1\27\1\30\1\uffff\1\17\23\uffff\1\15\1\25\1\16\14\uffff\1\17"
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
            return "781:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );";
        }
    }
    static final String dfa_22s = "\4\uffff";
    static final String dfa_23s = "\2\2\2\uffff";
    static final String dfa_24s = "\2\11\2\uffff";
    static final String dfa_25s = "\2\54\2\uffff";
    static final String dfa_26s = "\2\uffff\1\2\1\1";
    static final String dfa_27s = "\4\uffff}>";
    static final String[] dfa_28s = {
            "\1\1\42\uffff\1\3",
            "\1\1\42\uffff\1\3",
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
            return "()* loopback of 1991:2: ( rule__VersionRangeSetRequirement__Group_1_1__0 )*";
        }
    }
    static final String dfa_29s = "\5\uffff";
    static final String dfa_30s = "\1\1\1\uffff\2\1\1\uffff";
    static final String dfa_31s = "\1\11\1\uffff\2\5\1\uffff";
    static final String dfa_32s = "\1\54\1\uffff\2\54\1\uffff";
    static final String dfa_33s = "\1\uffff\1\2\2\uffff\1\1";
    static final String dfa_34s = "\5\uffff}>";
    static final String[] dfa_35s = {
            "\1\2\42\uffff\1\1",
            "",
            "\4\4\1\3\30\uffff\7\4\3\uffff\1\1",
            "\4\4\1\3\30\uffff\7\4\3\uffff\1\1",
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
            return "()* loopback of 2382:2: ( rule__VersionRangeContraint__Group_2__0 )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x00000000400000F2L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x000001FC000001E0L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000030000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x000021FC400001F0L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x000021FC000001E0L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000100000000200L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000100000000202L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x000001FC000003E0L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x000001FC00000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x00000000E0000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000000700000F0L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000000700000F2L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000000800000D0L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000000800000D2L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000003700000F0L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000003700000F2L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x00000000400000F0L});

}