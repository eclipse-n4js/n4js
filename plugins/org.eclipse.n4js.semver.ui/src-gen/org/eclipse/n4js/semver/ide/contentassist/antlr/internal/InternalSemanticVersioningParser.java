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
import org.eclipse.n4js.semver.services.SemanticVersioningGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

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
public class InternalSemanticVersioningParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_PART", "RULE_WHITESPACE_FRAGMENT", "RULE_WS", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'x'", "'X'", "'*'", "'='", "'<'", "'<='", "'>'", "'>='", "'||'", "'-'", "'.'", "'+'", "'~'", "'^'"
    };
    public static final int RULE_ML_COMMENT_FRAGMENT=18;
    public static final int RULE_ZWJ=12;
    public static final int RULE_SL_COMMENT_FRAGMENT=17;
    public static final int RULE_WHITESPACE_FRAGMENT=5;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=20;
    public static final int T__37=37;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=15;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=7;
    public static final int RULE_WS=6;
    public static final int RULE_EOL=8;
    public static final int RULE_BOM=14;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=19;
    public static final int RULE_ANY_OTHER=23;
    public static final int RULE_ZWNJ=13;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=16;
    public static final int RULE_PART=4;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=22;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=9;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int RULE_HEX_DIGIT=10;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=11;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=21;
    public static final int T__24=24;
    public static final int T__25=25;

    // delegates
    // delegators


        public InternalSemanticVersioningParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSemanticVersioningParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSemanticVersioningParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSemanticVersioning.g"; }


    	private SemanticVersioningGrammarAccess grammarAccess;

    	public void setGrammarAccess(SemanticVersioningGrammarAccess grammarAccess) {
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



    // $ANTLR start "entryRuleVersionRangeSet"
    // InternalSemanticVersioning.g:60:1: entryRuleVersionRangeSet : ruleVersionRangeSet EOF ;
    public final void entryRuleVersionRangeSet() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:61:1: ( ruleVersionRangeSet EOF )
            // InternalSemanticVersioning.g:62:1: ruleVersionRangeSet EOF
            {
             before(grammarAccess.getVersionRangeSetRule()); 
            pushFollow(FOLLOW_1);
            ruleVersionRangeSet();

            state._fsp--;

             after(grammarAccess.getVersionRangeSetRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // $ANTLR end "entryRuleVersionRangeSet"


    // $ANTLR start "ruleVersionRangeSet"
    // InternalSemanticVersioning.g:69:1: ruleVersionRangeSet : ( ( rule__VersionRangeSet__Group__0 ) ) ;
    public final void ruleVersionRangeSet() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:73:2: ( ( ( rule__VersionRangeSet__Group__0 ) ) )
            // InternalSemanticVersioning.g:74:2: ( ( rule__VersionRangeSet__Group__0 ) )
            {
            // InternalSemanticVersioning.g:74:2: ( ( rule__VersionRangeSet__Group__0 ) )
            // InternalSemanticVersioning.g:75:3: ( rule__VersionRangeSet__Group__0 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup()); 
            // InternalSemanticVersioning.g:76:3: ( rule__VersionRangeSet__Group__0 )
            // InternalSemanticVersioning.g:76:4: rule__VersionRangeSet__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeSetAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionRangeSet"


    // $ANTLR start "entryRuleVersionRange"
    // InternalSemanticVersioning.g:85:1: entryRuleVersionRange : ruleVersionRange EOF ;
    public final void entryRuleVersionRange() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:86:1: ( ruleVersionRange EOF )
            // InternalSemanticVersioning.g:87:1: ruleVersionRange EOF
            {
             before(grammarAccess.getVersionRangeRule()); 
            pushFollow(FOLLOW_1);
            ruleVersionRange();

            state._fsp--;

             after(grammarAccess.getVersionRangeRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSemanticVersioning.g:94:1: ruleVersionRange : ( ( rule__VersionRange__Alternatives ) ) ;
    public final void ruleVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:98:2: ( ( ( rule__VersionRange__Alternatives ) ) )
            // InternalSemanticVersioning.g:99:2: ( ( rule__VersionRange__Alternatives ) )
            {
            // InternalSemanticVersioning.g:99:2: ( ( rule__VersionRange__Alternatives ) )
            // InternalSemanticVersioning.g:100:3: ( rule__VersionRange__Alternatives )
            {
             before(grammarAccess.getVersionRangeAccess().getAlternatives()); 
            // InternalSemanticVersioning.g:101:3: ( rule__VersionRange__Alternatives )
            // InternalSemanticVersioning.g:101:4: rule__VersionRange__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VersionRange__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeAccess().getAlternatives()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:110:1: entryRuleHyphenVersionRange : ruleHyphenVersionRange EOF ;
    public final void entryRuleHyphenVersionRange() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:111:1: ( ruleHyphenVersionRange EOF )
            // InternalSemanticVersioning.g:112:1: ruleHyphenVersionRange EOF
            {
             before(grammarAccess.getHyphenVersionRangeRule()); 
            pushFollow(FOLLOW_1);
            ruleHyphenVersionRange();

            state._fsp--;

             after(grammarAccess.getHyphenVersionRangeRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSemanticVersioning.g:119:1: ruleHyphenVersionRange : ( ( rule__HyphenVersionRange__Group__0 ) ) ;
    public final void ruleHyphenVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:123:2: ( ( ( rule__HyphenVersionRange__Group__0 ) ) )
            // InternalSemanticVersioning.g:124:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            {
            // InternalSemanticVersioning.g:124:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            // InternalSemanticVersioning.g:125:3: ( rule__HyphenVersionRange__Group__0 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getGroup()); 
            // InternalSemanticVersioning.g:126:3: ( rule__HyphenVersionRange__Group__0 )
            // InternalSemanticVersioning.g:126:4: rule__HyphenVersionRange__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getHyphenVersionRangeAccess().getGroup()); 

            }


            }

        }
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


    // $ANTLR start "entryRuleEnumeratedVersionRange"
    // InternalSemanticVersioning.g:135:1: entryRuleEnumeratedVersionRange : ruleEnumeratedVersionRange EOF ;
    public final void entryRuleEnumeratedVersionRange() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:136:1: ( ruleEnumeratedVersionRange EOF )
            // InternalSemanticVersioning.g:137:1: ruleEnumeratedVersionRange EOF
            {
             before(grammarAccess.getEnumeratedVersionRangeRule()); 
            pushFollow(FOLLOW_1);
            ruleEnumeratedVersionRange();

            state._fsp--;

             after(grammarAccess.getEnumeratedVersionRangeRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // $ANTLR end "entryRuleEnumeratedVersionRange"


    // $ANTLR start "ruleEnumeratedVersionRange"
    // InternalSemanticVersioning.g:144:1: ruleEnumeratedVersionRange : ( ( rule__EnumeratedVersionRange__Group__0 ) ) ;
    public final void ruleEnumeratedVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:148:2: ( ( ( rule__EnumeratedVersionRange__Group__0 ) ) )
            // InternalSemanticVersioning.g:149:2: ( ( rule__EnumeratedVersionRange__Group__0 ) )
            {
            // InternalSemanticVersioning.g:149:2: ( ( rule__EnumeratedVersionRange__Group__0 ) )
            // InternalSemanticVersioning.g:150:3: ( rule__EnumeratedVersionRange__Group__0 )
            {
             before(grammarAccess.getEnumeratedVersionRangeAccess().getGroup()); 
            // InternalSemanticVersioning.g:151:3: ( rule__EnumeratedVersionRange__Group__0 )
            // InternalSemanticVersioning.g:151:4: rule__EnumeratedVersionRange__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__EnumeratedVersionRange__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEnumeratedVersionRangeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEnumeratedVersionRange"


    // $ANTLR start "entryRuleSimpleVersion"
    // InternalSemanticVersioning.g:160:1: entryRuleSimpleVersion : ruleSimpleVersion EOF ;
    public final void entryRuleSimpleVersion() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:161:1: ( ruleSimpleVersion EOF )
            // InternalSemanticVersioning.g:162:1: ruleSimpleVersion EOF
            {
             before(grammarAccess.getSimpleVersionRule()); 
            pushFollow(FOLLOW_1);
            ruleSimpleVersion();

            state._fsp--;

             after(grammarAccess.getSimpleVersionRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSemanticVersioning.g:169:1: ruleSimpleVersion : ( ( rule__SimpleVersion__Group__0 ) ) ;
    public final void ruleSimpleVersion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:173:2: ( ( ( rule__SimpleVersion__Group__0 ) ) )
            // InternalSemanticVersioning.g:174:2: ( ( rule__SimpleVersion__Group__0 ) )
            {
            // InternalSemanticVersioning.g:174:2: ( ( rule__SimpleVersion__Group__0 ) )
            // InternalSemanticVersioning.g:175:3: ( rule__SimpleVersion__Group__0 )
            {
             before(grammarAccess.getSimpleVersionAccess().getGroup()); 
            // InternalSemanticVersioning.g:176:3: ( rule__SimpleVersion__Group__0 )
            // InternalSemanticVersioning.g:176:4: rule__SimpleVersion__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSimpleVersionAccess().getGroup()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:185:1: entryRuleVersionNumber : ruleVersionNumber EOF ;
    public final void entryRuleVersionNumber() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:186:1: ( ruleVersionNumber EOF )
            // InternalSemanticVersioning.g:187:1: ruleVersionNumber EOF
            {
             before(grammarAccess.getVersionNumberRule()); 
            pushFollow(FOLLOW_1);
            ruleVersionNumber();

            state._fsp--;

             after(grammarAccess.getVersionNumberRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSemanticVersioning.g:194:1: ruleVersionNumber : ( ( rule__VersionNumber__Group__0 ) ) ;
    public final void ruleVersionNumber() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:198:2: ( ( ( rule__VersionNumber__Group__0 ) ) )
            // InternalSemanticVersioning.g:199:2: ( ( rule__VersionNumber__Group__0 ) )
            {
            // InternalSemanticVersioning.g:199:2: ( ( rule__VersionNumber__Group__0 ) )
            // InternalSemanticVersioning.g:200:3: ( rule__VersionNumber__Group__0 )
            {
             before(grammarAccess.getVersionNumberAccess().getGroup()); 
            // InternalSemanticVersioning.g:201:3: ( rule__VersionNumber__Group__0 )
            // InternalSemanticVersioning.g:201:4: rule__VersionNumber__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVersionNumberAccess().getGroup()); 

            }


            }

        }
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


    // $ANTLR start "entryRuleXr"
    // InternalSemanticVersioning.g:210:1: entryRuleXr : ruleXr EOF ;
    public final void entryRuleXr() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:211:1: ( ruleXr EOF )
            // InternalSemanticVersioning.g:212:1: ruleXr EOF
            {
             before(grammarAccess.getXrRule()); 
            pushFollow(FOLLOW_1);
            ruleXr();

            state._fsp--;

             after(grammarAccess.getXrRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // $ANTLR end "entryRuleXr"


    // $ANTLR start "ruleXr"
    // InternalSemanticVersioning.g:219:1: ruleXr : ( ( rule__Xr__Alternatives ) ) ;
    public final void ruleXr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:223:2: ( ( ( rule__Xr__Alternatives ) ) )
            // InternalSemanticVersioning.g:224:2: ( ( rule__Xr__Alternatives ) )
            {
            // InternalSemanticVersioning.g:224:2: ( ( rule__Xr__Alternatives ) )
            // InternalSemanticVersioning.g:225:3: ( rule__Xr__Alternatives )
            {
             before(grammarAccess.getXrAccess().getAlternatives()); 
            // InternalSemanticVersioning.g:226:3: ( rule__Xr__Alternatives )
            // InternalSemanticVersioning.g:226:4: rule__Xr__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Xr__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getXrAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleXr"


    // $ANTLR start "entryRuleQualifier"
    // InternalSemanticVersioning.g:235:1: entryRuleQualifier : ruleQualifier EOF ;
    public final void entryRuleQualifier() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:236:1: ( ruleQualifier EOF )
            // InternalSemanticVersioning.g:237:1: ruleQualifier EOF
            {
             before(grammarAccess.getQualifierRule()); 
            pushFollow(FOLLOW_1);
            ruleQualifier();

            state._fsp--;

             after(grammarAccess.getQualifierRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSemanticVersioning.g:244:1: ruleQualifier : ( ( rule__Qualifier__Alternatives ) ) ;
    public final void ruleQualifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:248:2: ( ( ( rule__Qualifier__Alternatives ) ) )
            // InternalSemanticVersioning.g:249:2: ( ( rule__Qualifier__Alternatives ) )
            {
            // InternalSemanticVersioning.g:249:2: ( ( rule__Qualifier__Alternatives ) )
            // InternalSemanticVersioning.g:250:3: ( rule__Qualifier__Alternatives )
            {
             before(grammarAccess.getQualifierAccess().getAlternatives()); 
            // InternalSemanticVersioning.g:251:3: ( rule__Qualifier__Alternatives )
            // InternalSemanticVersioning.g:251:4: rule__Qualifier__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getQualifierAccess().getAlternatives()); 

            }


            }

        }
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


    // $ANTLR start "entryRuleParts"
    // InternalSemanticVersioning.g:260:1: entryRuleParts : ruleParts EOF ;
    public final void entryRuleParts() throws RecognitionException {
        try {
            // InternalSemanticVersioning.g:261:1: ( ruleParts EOF )
            // InternalSemanticVersioning.g:262:1: ruleParts EOF
            {
             before(grammarAccess.getPartsRule()); 
            pushFollow(FOLLOW_1);
            ruleParts();

            state._fsp--;

             after(grammarAccess.getPartsRule()); 
            match(input,EOF,FOLLOW_2); 

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
    // $ANTLR end "entryRuleParts"


    // $ANTLR start "ruleParts"
    // InternalSemanticVersioning.g:269:1: ruleParts : ( ( rule__Parts__Group__0 ) ) ;
    public final void ruleParts() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:273:2: ( ( ( rule__Parts__Group__0 ) ) )
            // InternalSemanticVersioning.g:274:2: ( ( rule__Parts__Group__0 ) )
            {
            // InternalSemanticVersioning.g:274:2: ( ( rule__Parts__Group__0 ) )
            // InternalSemanticVersioning.g:275:3: ( rule__Parts__Group__0 )
            {
             before(grammarAccess.getPartsAccess().getGroup()); 
            // InternalSemanticVersioning.g:276:3: ( rule__Parts__Group__0 )
            // InternalSemanticVersioning.g:276:4: rule__Parts__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Parts__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPartsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleParts"


    // $ANTLR start "ruleVersionComparator"
    // InternalSemanticVersioning.g:285:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:289:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSemanticVersioning.g:290:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSemanticVersioning.g:290:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSemanticVersioning.g:291:3: ( rule__VersionComparator__Alternatives )
            {
             before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            // InternalSemanticVersioning.g:292:3: ( rule__VersionComparator__Alternatives )
            // InternalSemanticVersioning.g:292:4: rule__VersionComparator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VersionComparator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getVersionComparatorAccess().getAlternatives()); 

            }


            }

        }
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


    // $ANTLR start "rule__VersionRange__Alternatives"
    // InternalSemanticVersioning.g:300:1: rule__VersionRange__Alternatives : ( ( ruleHyphenVersionRange ) | ( ruleEnumeratedVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:304:1: ( ( ruleHyphenVersionRange ) | ( ruleEnumeratedVersionRange ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>=24 && LA1_0<=26)) ) {
                alt1=1;
            }
            else if ( ((LA1_0>=27 && LA1_0<=31)||LA1_0==36) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalSemanticVersioning.g:305:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSemanticVersioning.g:305:2: ( ruleHyphenVersionRange )
                    // InternalSemanticVersioning.g:306:3: ruleHyphenVersionRange
                    {
                     before(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleHyphenVersionRange();

                    state._fsp--;

                     after(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:311:2: ( ruleEnumeratedVersionRange )
                    {
                    // InternalSemanticVersioning.g:311:2: ( ruleEnumeratedVersionRange )
                    // InternalSemanticVersioning.g:312:3: ruleEnumeratedVersionRange
                    {
                     before(grammarAccess.getVersionRangeAccess().getEnumeratedVersionRangeParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleEnumeratedVersionRange();

                    state._fsp--;

                     after(grammarAccess.getVersionRangeAccess().getEnumeratedVersionRangeParserRuleCall_1()); 

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


    // $ANTLR start "rule__Xr__Alternatives"
    // InternalSemanticVersioning.g:321:1: rule__Xr__Alternatives : ( ( 'x' ) | ( 'X' ) | ( '*' ) );
    public final void rule__Xr__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:325:1: ( ( 'x' ) | ( 'X' ) | ( '*' ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case 24:
                {
                alt2=1;
                }
                break;
            case 25:
                {
                alt2=2;
                }
                break;
            case 26:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalSemanticVersioning.g:326:2: ( 'x' )
                    {
                    // InternalSemanticVersioning.g:326:2: ( 'x' )
                    // InternalSemanticVersioning.g:327:3: 'x'
                    {
                     before(grammarAccess.getXrAccess().getXKeyword_0()); 
                    match(input,24,FOLLOW_2); 
                     after(grammarAccess.getXrAccess().getXKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:332:2: ( 'X' )
                    {
                    // InternalSemanticVersioning.g:332:2: ( 'X' )
                    // InternalSemanticVersioning.g:333:3: 'X'
                    {
                     before(grammarAccess.getXrAccess().getXKeyword_1()); 
                    match(input,25,FOLLOW_2); 
                     after(grammarAccess.getXrAccess().getXKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSemanticVersioning.g:338:2: ( '*' )
                    {
                    // InternalSemanticVersioning.g:338:2: ( '*' )
                    // InternalSemanticVersioning.g:339:3: '*'
                    {
                     before(grammarAccess.getXrAccess().getAsteriskKeyword_2()); 
                    match(input,26,FOLLOW_2); 
                     after(grammarAccess.getXrAccess().getAsteriskKeyword_2()); 

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
    // $ANTLR end "rule__Xr__Alternatives"


    // $ANTLR start "rule__Qualifier__Alternatives"
    // InternalSemanticVersioning.g:348:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:352:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==33) ) {
                alt3=1;
            }
            else if ( (LA3_0==35) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalSemanticVersioning.g:353:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSemanticVersioning.g:353:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSemanticVersioning.g:354:3: ( rule__Qualifier__Group_0__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    // InternalSemanticVersioning.g:355:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSemanticVersioning.g:355:4: rule__Qualifier__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getQualifierAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:359:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSemanticVersioning.g:359:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSemanticVersioning.g:360:3: ( rule__Qualifier__Group_1__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    // InternalSemanticVersioning.g:361:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSemanticVersioning.g:361:4: rule__Qualifier__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getQualifierAccess().getGroup_1()); 

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


    // $ANTLR start "rule__VersionComparator__Alternatives"
    // InternalSemanticVersioning.g:369:1: rule__VersionComparator__Alternatives : ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:373:1: ( ( ( '=' ) ) | ( ( '<' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt4=5;
            switch ( input.LA(1) ) {
            case 27:
                {
                alt4=1;
                }
                break;
            case 28:
                {
                alt4=2;
                }
                break;
            case 29:
                {
                alt4=3;
                }
                break;
            case 30:
                {
                alt4=4;
                }
                break;
            case 31:
                {
                alt4=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalSemanticVersioning.g:374:2: ( ( '=' ) )
                    {
                    // InternalSemanticVersioning.g:374:2: ( ( '=' ) )
                    // InternalSemanticVersioning.g:375:3: ( '=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 
                    // InternalSemanticVersioning.g:376:3: ( '=' )
                    // InternalSemanticVersioning.g:376:4: '='
                    {
                    match(input,27,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSemanticVersioning.g:380:2: ( ( '<' ) )
                    {
                    // InternalSemanticVersioning.g:380:2: ( ( '<' ) )
                    // InternalSemanticVersioning.g:381:3: ( '<' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 
                    // InternalSemanticVersioning.g:382:3: ( '<' )
                    // InternalSemanticVersioning.g:382:4: '<'
                    {
                    match(input,28,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSemanticVersioning.g:386:2: ( ( '<=' ) )
                    {
                    // InternalSemanticVersioning.g:386:2: ( ( '<=' ) )
                    // InternalSemanticVersioning.g:387:3: ( '<=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_2()); 
                    // InternalSemanticVersioning.g:388:3: ( '<=' )
                    // InternalSemanticVersioning.g:388:4: '<='
                    {
                    match(input,29,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSemanticVersioning.g:392:2: ( ( '>' ) )
                    {
                    // InternalSemanticVersioning.g:392:2: ( ( '>' ) )
                    // InternalSemanticVersioning.g:393:3: ( '>' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_3()); 
                    // InternalSemanticVersioning.g:394:3: ( '>' )
                    // InternalSemanticVersioning.g:394:4: '>'
                    {
                    match(input,30,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalSemanticVersioning.g:398:2: ( ( '>=' ) )
                    {
                    // InternalSemanticVersioning.g:398:2: ( ( '>=' ) )
                    // InternalSemanticVersioning.g:399:3: ( '>=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_4()); 
                    // InternalSemanticVersioning.g:400:3: ( '>=' )
                    // InternalSemanticVersioning.g:400:4: '>='
                    {
                    match(input,31,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_4()); 

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


    // $ANTLR start "rule__VersionRangeSet__Group__0"
    // InternalSemanticVersioning.g:408:1: rule__VersionRangeSet__Group__0 : rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 ;
    public final void rule__VersionRangeSet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:412:1: ( rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 )
            // InternalSemanticVersioning.g:413:2: rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__VersionRangeSet__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group__0"


    // $ANTLR start "rule__VersionRangeSet__Group__0__Impl"
    // InternalSemanticVersioning.g:420:1: rule__VersionRangeSet__Group__0__Impl : ( ( rule__VersionRangeSet__RangesAssignment_0 )? ) ;
    public final void rule__VersionRangeSet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:424:1: ( ( ( rule__VersionRangeSet__RangesAssignment_0 )? ) )
            // InternalSemanticVersioning.g:425:1: ( ( rule__VersionRangeSet__RangesAssignment_0 )? )
            {
            // InternalSemanticVersioning.g:425:1: ( ( rule__VersionRangeSet__RangesAssignment_0 )? )
            // InternalSemanticVersioning.g:426:2: ( rule__VersionRangeSet__RangesAssignment_0 )?
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_0()); 
            // InternalSemanticVersioning.g:427:2: ( rule__VersionRangeSet__RangesAssignment_0 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=24 && LA5_0<=31)||LA5_0==36) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSemanticVersioning.g:427:3: rule__VersionRangeSet__RangesAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionRangeSet__RangesAssignment_0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group__0__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group__1"
    // InternalSemanticVersioning.g:435:1: rule__VersionRangeSet__Group__1 : rule__VersionRangeSet__Group__1__Impl ;
    public final void rule__VersionRangeSet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:439:1: ( rule__VersionRangeSet__Group__1__Impl )
            // InternalSemanticVersioning.g:440:2: rule__VersionRangeSet__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group__1"


    // $ANTLR start "rule__VersionRangeSet__Group__1__Impl"
    // InternalSemanticVersioning.g:446:1: rule__VersionRangeSet__Group__1__Impl : ( ( rule__VersionRangeSet__Group_1__0 )* ) ;
    public final void rule__VersionRangeSet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:450:1: ( ( ( rule__VersionRangeSet__Group_1__0 )* ) )
            // InternalSemanticVersioning.g:451:1: ( ( rule__VersionRangeSet__Group_1__0 )* )
            {
            // InternalSemanticVersioning.g:451:1: ( ( rule__VersionRangeSet__Group_1__0 )* )
            // InternalSemanticVersioning.g:452:2: ( rule__VersionRangeSet__Group_1__0 )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup_1()); 
            // InternalSemanticVersioning.g:453:2: ( rule__VersionRangeSet__Group_1__0 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==32) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSemanticVersioning.g:453:3: rule__VersionRangeSet__Group_1__0
            	    {
            	    pushFollow(FOLLOW_4);
            	    rule__VersionRangeSet__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

             after(grammarAccess.getVersionRangeSetAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group__1__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_1__0"
    // InternalSemanticVersioning.g:462:1: rule__VersionRangeSet__Group_1__0 : rule__VersionRangeSet__Group_1__0__Impl rule__VersionRangeSet__Group_1__1 ;
    public final void rule__VersionRangeSet__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:466:1: ( rule__VersionRangeSet__Group_1__0__Impl rule__VersionRangeSet__Group_1__1 )
            // InternalSemanticVersioning.g:467:2: rule__VersionRangeSet__Group_1__0__Impl rule__VersionRangeSet__Group_1__1
            {
            pushFollow(FOLLOW_5);
            rule__VersionRangeSet__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_1__0"


    // $ANTLR start "rule__VersionRangeSet__Group_1__0__Impl"
    // InternalSemanticVersioning.g:474:1: rule__VersionRangeSet__Group_1__0__Impl : ( '||' ) ;
    public final void rule__VersionRangeSet__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:478:1: ( ( '||' ) )
            // InternalSemanticVersioning.g:479:1: ( '||' )
            {
            // InternalSemanticVersioning.g:479:1: ( '||' )
            // InternalSemanticVersioning.g:480:2: '||'
            {
             before(grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_1_0()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_1__0__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_1__1"
    // InternalSemanticVersioning.g:489:1: rule__VersionRangeSet__Group_1__1 : rule__VersionRangeSet__Group_1__1__Impl ;
    public final void rule__VersionRangeSet__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:493:1: ( rule__VersionRangeSet__Group_1__1__Impl )
            // InternalSemanticVersioning.g:494:2: rule__VersionRangeSet__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_1__1"


    // $ANTLR start "rule__VersionRangeSet__Group_1__1__Impl"
    // InternalSemanticVersioning.g:500:1: rule__VersionRangeSet__Group_1__1__Impl : ( ( rule__VersionRangeSet__RangesAssignment_1_1 ) ) ;
    public final void rule__VersionRangeSet__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:504:1: ( ( ( rule__VersionRangeSet__RangesAssignment_1_1 ) ) )
            // InternalSemanticVersioning.g:505:1: ( ( rule__VersionRangeSet__RangesAssignment_1_1 ) )
            {
            // InternalSemanticVersioning.g:505:1: ( ( rule__VersionRangeSet__RangesAssignment_1_1 ) )
            // InternalSemanticVersioning.g:506:2: ( rule__VersionRangeSet__RangesAssignment_1_1 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_1_1()); 
            // InternalSemanticVersioning.g:507:2: ( rule__VersionRangeSet__RangesAssignment_1_1 )
            // InternalSemanticVersioning.g:507:3: rule__VersionRangeSet__RangesAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__RangesAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_1__1__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__0"
    // InternalSemanticVersioning.g:516:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:520:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSemanticVersioning.g:521:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__HyphenVersionRange__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__1();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:528:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:532:1: ( ( () ) )
            // InternalSemanticVersioning.g:533:1: ( () )
            {
            // InternalSemanticVersioning.g:533:1: ( () )
            // InternalSemanticVersioning.g:534:2: ()
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            // InternalSemanticVersioning.g:535:2: ()
            // InternalSemanticVersioning.g:535:3: 
            {
            }

             after(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 

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
    // InternalSemanticVersioning.g:543:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:547:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSemanticVersioning.g:548:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__HyphenVersionRange__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__2();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:555:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:559:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSemanticVersioning.g:560:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSemanticVersioning.g:560:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSemanticVersioning.g:561:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            // InternalSemanticVersioning.g:562:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSemanticVersioning.g:562:3: rule__HyphenVersionRange__FromAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__FromAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:570:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:574:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSemanticVersioning.g:575:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__HyphenVersionRange__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__3();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:582:1: rule__HyphenVersionRange__Group__2__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:586:1: ( ( '-' ) )
            // InternalSemanticVersioning.g:587:1: ( '-' )
            {
            // InternalSemanticVersioning.g:587:1: ( '-' )
            // InternalSemanticVersioning.g:588:2: '-'
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_2()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_2()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:597:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:601:1: ( rule__HyphenVersionRange__Group__3__Impl )
            // InternalSemanticVersioning.g:602:2: rule__HyphenVersionRange__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__3__Impl();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:608:1: rule__HyphenVersionRange__Group__3__Impl : ( ( rule__HyphenVersionRange__ToAssignment_3 ) ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:612:1: ( ( ( rule__HyphenVersionRange__ToAssignment_3 ) ) )
            // InternalSemanticVersioning.g:613:1: ( ( rule__HyphenVersionRange__ToAssignment_3 ) )
            {
            // InternalSemanticVersioning.g:613:1: ( ( rule__HyphenVersionRange__ToAssignment_3 ) )
            // InternalSemanticVersioning.g:614:2: ( rule__HyphenVersionRange__ToAssignment_3 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_3()); 
            // InternalSemanticVersioning.g:615:2: ( rule__HyphenVersionRange__ToAssignment_3 )
            // InternalSemanticVersioning.g:615:3: rule__HyphenVersionRange__ToAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__ToAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_3()); 

            }


            }

        }
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


    // $ANTLR start "rule__EnumeratedVersionRange__Group__0"
    // InternalSemanticVersioning.g:624:1: rule__EnumeratedVersionRange__Group__0 : rule__EnumeratedVersionRange__Group__0__Impl rule__EnumeratedVersionRange__Group__1 ;
    public final void rule__EnumeratedVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:628:1: ( rule__EnumeratedVersionRange__Group__0__Impl rule__EnumeratedVersionRange__Group__1 )
            // InternalSemanticVersioning.g:629:2: rule__EnumeratedVersionRange__Group__0__Impl rule__EnumeratedVersionRange__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__EnumeratedVersionRange__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EnumeratedVersionRange__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnumeratedVersionRange__Group__0"


    // $ANTLR start "rule__EnumeratedVersionRange__Group__0__Impl"
    // InternalSemanticVersioning.g:636:1: rule__EnumeratedVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__EnumeratedVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:640:1: ( ( () ) )
            // InternalSemanticVersioning.g:641:1: ( () )
            {
            // InternalSemanticVersioning.g:641:1: ( () )
            // InternalSemanticVersioning.g:642:2: ()
            {
             before(grammarAccess.getEnumeratedVersionRangeAccess().getEnumeratedVersionRangeAction_0()); 
            // InternalSemanticVersioning.g:643:2: ()
            // InternalSemanticVersioning.g:643:3: 
            {
            }

             after(grammarAccess.getEnumeratedVersionRangeAccess().getEnumeratedVersionRangeAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnumeratedVersionRange__Group__0__Impl"


    // $ANTLR start "rule__EnumeratedVersionRange__Group__1"
    // InternalSemanticVersioning.g:651:1: rule__EnumeratedVersionRange__Group__1 : rule__EnumeratedVersionRange__Group__1__Impl ;
    public final void rule__EnumeratedVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:655:1: ( rule__EnumeratedVersionRange__Group__1__Impl )
            // InternalSemanticVersioning.g:656:2: rule__EnumeratedVersionRange__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EnumeratedVersionRange__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnumeratedVersionRange__Group__1"


    // $ANTLR start "rule__EnumeratedVersionRange__Group__1__Impl"
    // InternalSemanticVersioning.g:662:1: rule__EnumeratedVersionRange__Group__1__Impl : ( ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 ) ) ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )* ) ) ;
    public final void rule__EnumeratedVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:666:1: ( ( ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 ) ) ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )* ) ) )
            // InternalSemanticVersioning.g:667:1: ( ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 ) ) ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )* ) )
            {
            // InternalSemanticVersioning.g:667:1: ( ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 ) ) ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )* ) )
            // InternalSemanticVersioning.g:668:2: ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 ) ) ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )* )
            {
            // InternalSemanticVersioning.g:668:2: ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 ) )
            // InternalSemanticVersioning.g:669:3: ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )
            {
             before(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsAssignment_1()); 
            // InternalSemanticVersioning.g:670:3: ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )
            // InternalSemanticVersioning.g:670:4: rule__EnumeratedVersionRange__SimpleVersionsAssignment_1
            {
            pushFollow(FOLLOW_8);
            rule__EnumeratedVersionRange__SimpleVersionsAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsAssignment_1()); 

            }

            // InternalSemanticVersioning.g:673:2: ( ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )* )
            // InternalSemanticVersioning.g:674:3: ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )*
            {
             before(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsAssignment_1()); 
            // InternalSemanticVersioning.g:675:3: ( rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=27 && LA7_0<=31)||LA7_0==36) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSemanticVersioning.g:675:4: rule__EnumeratedVersionRange__SimpleVersionsAssignment_1
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__EnumeratedVersionRange__SimpleVersionsAssignment_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

             after(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsAssignment_1()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnumeratedVersionRange__Group__1__Impl"


    // $ANTLR start "rule__SimpleVersion__Group__0"
    // InternalSemanticVersioning.g:685:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:689:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSemanticVersioning.g:690:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__SimpleVersion__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__1();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:697:1: rule__SimpleVersion__Group__0__Impl : ( ( rule__SimpleVersion__ComparatorAssignment_0 )? ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:701:1: ( ( ( rule__SimpleVersion__ComparatorAssignment_0 )? ) )
            // InternalSemanticVersioning.g:702:1: ( ( rule__SimpleVersion__ComparatorAssignment_0 )? )
            {
            // InternalSemanticVersioning.g:702:1: ( ( rule__SimpleVersion__ComparatorAssignment_0 )? )
            // InternalSemanticVersioning.g:703:2: ( rule__SimpleVersion__ComparatorAssignment_0 )?
            {
             before(grammarAccess.getSimpleVersionAccess().getComparatorAssignment_0()); 
            // InternalSemanticVersioning.g:704:2: ( rule__SimpleVersion__ComparatorAssignment_0 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=27 && LA8_0<=31)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSemanticVersioning.g:704:3: rule__SimpleVersion__ComparatorAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__SimpleVersion__ComparatorAssignment_0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getSimpleVersionAccess().getComparatorAssignment_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:712:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:716:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSemanticVersioning.g:717:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
            {
            pushFollow(FOLLOW_9);
            rule__SimpleVersion__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__2();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:724:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__HasTildeAssignment_1 ) ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:728:1: ( ( ( rule__SimpleVersion__HasTildeAssignment_1 ) ) )
            // InternalSemanticVersioning.g:729:1: ( ( rule__SimpleVersion__HasTildeAssignment_1 ) )
            {
            // InternalSemanticVersioning.g:729:1: ( ( rule__SimpleVersion__HasTildeAssignment_1 ) )
            // InternalSemanticVersioning.g:730:2: ( rule__SimpleVersion__HasTildeAssignment_1 )
            {
             before(grammarAccess.getSimpleVersionAccess().getHasTildeAssignment_1()); 
            // InternalSemanticVersioning.g:731:2: ( rule__SimpleVersion__HasTildeAssignment_1 )
            // InternalSemanticVersioning.g:731:3: rule__SimpleVersion__HasTildeAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__HasTildeAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getSimpleVersionAccess().getHasTildeAssignment_1()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:739:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl rule__SimpleVersion__Group__3 ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:743:1: ( rule__SimpleVersion__Group__2__Impl rule__SimpleVersion__Group__3 )
            // InternalSemanticVersioning.g:744:2: rule__SimpleVersion__Group__2__Impl rule__SimpleVersion__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__SimpleVersion__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__3();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:751:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__HasCaretAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:755:1: ( ( ( rule__SimpleVersion__HasCaretAssignment_2 ) ) )
            // InternalSemanticVersioning.g:756:1: ( ( rule__SimpleVersion__HasCaretAssignment_2 ) )
            {
            // InternalSemanticVersioning.g:756:1: ( ( rule__SimpleVersion__HasCaretAssignment_2 ) )
            // InternalSemanticVersioning.g:757:2: ( rule__SimpleVersion__HasCaretAssignment_2 )
            {
             before(grammarAccess.getSimpleVersionAccess().getHasCaretAssignment_2()); 
            // InternalSemanticVersioning.g:758:2: ( rule__SimpleVersion__HasCaretAssignment_2 )
            // InternalSemanticVersioning.g:758:3: rule__SimpleVersion__HasCaretAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__HasCaretAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getSimpleVersionAccess().getHasCaretAssignment_2()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:766:1: rule__SimpleVersion__Group__3 : rule__SimpleVersion__Group__3__Impl ;
    public final void rule__SimpleVersion__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:770:1: ( rule__SimpleVersion__Group__3__Impl )
            // InternalSemanticVersioning.g:771:2: rule__SimpleVersion__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__3__Impl();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:777:1: rule__SimpleVersion__Group__3__Impl : ( ( rule__SimpleVersion__NumberAssignment_3 ) ) ;
    public final void rule__SimpleVersion__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:781:1: ( ( ( rule__SimpleVersion__NumberAssignment_3 ) ) )
            // InternalSemanticVersioning.g:782:1: ( ( rule__SimpleVersion__NumberAssignment_3 ) )
            {
            // InternalSemanticVersioning.g:782:1: ( ( rule__SimpleVersion__NumberAssignment_3 ) )
            // InternalSemanticVersioning.g:783:2: ( rule__SimpleVersion__NumberAssignment_3 )
            {
             before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_3()); 
            // InternalSemanticVersioning.g:784:2: ( rule__SimpleVersion__NumberAssignment_3 )
            // InternalSemanticVersioning.g:784:3: rule__SimpleVersion__NumberAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__NumberAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getSimpleVersionAccess().getNumberAssignment_3()); 

            }


            }

        }
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


    // $ANTLR start "rule__VersionNumber__Group__0"
    // InternalSemanticVersioning.g:793:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:797:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSemanticVersioning.g:798:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__VersionNumber__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__1();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:805:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:809:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSemanticVersioning.g:810:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSemanticVersioning.g:810:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSemanticVersioning.g:811:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
             before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            // InternalSemanticVersioning.g:812:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSemanticVersioning.g:812:3: rule__VersionNumber__MajorAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__MajorAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:820:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:824:1: ( rule__VersionNumber__Group__1__Impl )
            // InternalSemanticVersioning.g:825:2: rule__VersionNumber__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__1__Impl();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:831:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:835:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSemanticVersioning.g:836:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSemanticVersioning.g:836:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSemanticVersioning.g:837:2: ( rule__VersionNumber__Group_1__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            // InternalSemanticVersioning.g:838:2: ( rule__VersionNumber__Group_1__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==34) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSemanticVersioning.g:838:3: rule__VersionNumber__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionNumber__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVersionNumberAccess().getGroup_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__VersionNumber__Group_1__0"
    // InternalSemanticVersioning.g:847:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:851:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSemanticVersioning.g:852:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
            {
            pushFollow(FOLLOW_6);
            rule__VersionNumber__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1__1();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:859:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:863:1: ( ( '.' ) )
            // InternalSemanticVersioning.g:864:1: ( '.' )
            {
            // InternalSemanticVersioning.g:864:1: ( '.' )
            // InternalSemanticVersioning.g:865:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:874:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:878:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSemanticVersioning.g:879:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
            {
            pushFollow(FOLLOW_10);
            rule__VersionNumber__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1__2();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:886:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:890:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSemanticVersioning.g:891:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSemanticVersioning.g:891:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSemanticVersioning.g:892:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            // InternalSemanticVersioning.g:893:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSemanticVersioning.g:893:3: rule__VersionNumber__MinorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__MinorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:901:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:905:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSemanticVersioning.g:906:2: rule__VersionNumber__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1__2__Impl();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:912:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:916:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSemanticVersioning.g:917:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSemanticVersioning.g:917:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSemanticVersioning.g:918:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            // InternalSemanticVersioning.g:919:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==34) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalSemanticVersioning.g:919:3: rule__VersionNumber__Group_1_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionNumber__Group_1_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:928:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:932:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSemanticVersioning.g:933:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
            {
            pushFollow(FOLLOW_6);
            rule__VersionNumber__Group_1_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2__1();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:940:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:944:1: ( ( '.' ) )
            // InternalSemanticVersioning.g:945:1: ( '.' )
            {
            // InternalSemanticVersioning.g:945:1: ( '.' )
            // InternalSemanticVersioning.g:946:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:955:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:959:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSemanticVersioning.g:960:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
            {
            pushFollow(FOLLOW_11);
            rule__VersionNumber__Group_1_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2__2();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:967:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PathAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:971:1: ( ( ( rule__VersionNumber__PathAssignment_1_2_1 ) ) )
            // InternalSemanticVersioning.g:972:1: ( ( rule__VersionNumber__PathAssignment_1_2_1 ) )
            {
            // InternalSemanticVersioning.g:972:1: ( ( rule__VersionNumber__PathAssignment_1_2_1 ) )
            // InternalSemanticVersioning.g:973:2: ( rule__VersionNumber__PathAssignment_1_2_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getPathAssignment_1_2_1()); 
            // InternalSemanticVersioning.g:974:2: ( rule__VersionNumber__PathAssignment_1_2_1 )
            // InternalSemanticVersioning.g:974:3: rule__VersionNumber__PathAssignment_1_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__PathAssignment_1_2_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionNumberAccess().getPathAssignment_1_2_1()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:982:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:986:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSemanticVersioning.g:987:2: rule__VersionNumber__Group_1_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2__2__Impl();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:993:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_1_2_2 )? ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:997:1: ( ( ( rule__VersionNumber__QualifierAssignment_1_2_2 )? ) )
            // InternalSemanticVersioning.g:998:1: ( ( rule__VersionNumber__QualifierAssignment_1_2_2 )? )
            {
            // InternalSemanticVersioning.g:998:1: ( ( rule__VersionNumber__QualifierAssignment_1_2_2 )? )
            // InternalSemanticVersioning.g:999:2: ( rule__VersionNumber__QualifierAssignment_1_2_2 )?
            {
             before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_1_2_2()); 
            // InternalSemanticVersioning.g:1000:2: ( rule__VersionNumber__QualifierAssignment_1_2_2 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==33) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==RULE_PART) ) {
                    alt11=1;
                }
            }
            else if ( (LA11_0==35) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSemanticVersioning.g:1000:3: rule__VersionNumber__QualifierAssignment_1_2_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionNumber__QualifierAssignment_1_2_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVersionNumberAccess().getQualifierAssignment_1_2_2()); 

            }


            }

        }
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


    // $ANTLR start "rule__Qualifier__Group_0__0"
    // InternalSemanticVersioning.g:1009:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1013:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSemanticVersioning.g:1014:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
            {
            pushFollow(FOLLOW_12);
            rule__Qualifier__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0__1();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:1021:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1025:1: ( ( '-' ) )
            // InternalSemanticVersioning.g:1026:1: ( '-' )
            {
            // InternalSemanticVersioning.g:1026:1: ( '-' )
            // InternalSemanticVersioning.g:1027:2: '-'
            {
             before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:1036:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1040:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSemanticVersioning.g:1041:2: rule__Qualifier__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_0__1__Impl();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:1047:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1051:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSemanticVersioning.g:1052:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSemanticVersioning.g:1052:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSemanticVersioning.g:1053:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            // InternalSemanticVersioning.g:1054:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSemanticVersioning.g:1054:3: rule__Qualifier__PreReleaseAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__PreReleaseAssignment_0_1();

            state._fsp--;


            }

             after(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:1063:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1067:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSemanticVersioning.g:1068:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__Qualifier__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_1__1();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:1075:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1079:1: ( ( '+' ) )
            // InternalSemanticVersioning.g:1080:1: ( '+' )
            {
            // InternalSemanticVersioning.g:1080:1: ( '+' )
            // InternalSemanticVersioning.g:1081:2: '+'
            {
             before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:1090:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1094:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSemanticVersioning.g:1095:2: rule__Qualifier__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_1__1__Impl();

            state._fsp--;


            }

        }
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
    // InternalSemanticVersioning.g:1101:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1105:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSemanticVersioning.g:1106:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSemanticVersioning.g:1106:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSemanticVersioning.g:1107:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            // InternalSemanticVersioning.g:1108:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSemanticVersioning.g:1108:3: rule__Qualifier__BuildMetadataAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__BuildMetadataAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__Parts__Group__0"
    // InternalSemanticVersioning.g:1117:1: rule__Parts__Group__0 : rule__Parts__Group__0__Impl rule__Parts__Group__1 ;
    public final void rule__Parts__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1121:1: ( rule__Parts__Group__0__Impl rule__Parts__Group__1 )
            // InternalSemanticVersioning.g:1122:2: rule__Parts__Group__0__Impl rule__Parts__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__Parts__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Parts__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group__0"


    // $ANTLR start "rule__Parts__Group__0__Impl"
    // InternalSemanticVersioning.g:1129:1: rule__Parts__Group__0__Impl : ( RULE_PART ) ;
    public final void rule__Parts__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1133:1: ( ( RULE_PART ) )
            // InternalSemanticVersioning.g:1134:1: ( RULE_PART )
            {
            // InternalSemanticVersioning.g:1134:1: ( RULE_PART )
            // InternalSemanticVersioning.g:1135:2: RULE_PART
            {
             before(grammarAccess.getPartsAccess().getPARTTerminalRuleCall_0()); 
            match(input,RULE_PART,FOLLOW_2); 
             after(grammarAccess.getPartsAccess().getPARTTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group__0__Impl"


    // $ANTLR start "rule__Parts__Group__1"
    // InternalSemanticVersioning.g:1144:1: rule__Parts__Group__1 : rule__Parts__Group__1__Impl ;
    public final void rule__Parts__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1148:1: ( rule__Parts__Group__1__Impl )
            // InternalSemanticVersioning.g:1149:2: rule__Parts__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Parts__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group__1"


    // $ANTLR start "rule__Parts__Group__1__Impl"
    // InternalSemanticVersioning.g:1155:1: rule__Parts__Group__1__Impl : ( ( rule__Parts__Group_1__0 )* ) ;
    public final void rule__Parts__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1159:1: ( ( ( rule__Parts__Group_1__0 )* ) )
            // InternalSemanticVersioning.g:1160:1: ( ( rule__Parts__Group_1__0 )* )
            {
            // InternalSemanticVersioning.g:1160:1: ( ( rule__Parts__Group_1__0 )* )
            // InternalSemanticVersioning.g:1161:2: ( rule__Parts__Group_1__0 )*
            {
             before(grammarAccess.getPartsAccess().getGroup_1()); 
            // InternalSemanticVersioning.g:1162:2: ( rule__Parts__Group_1__0 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==34) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSemanticVersioning.g:1162:3: rule__Parts__Group_1__0
            	    {
            	    pushFollow(FOLLOW_13);
            	    rule__Parts__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getPartsAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group__1__Impl"


    // $ANTLR start "rule__Parts__Group_1__0"
    // InternalSemanticVersioning.g:1171:1: rule__Parts__Group_1__0 : rule__Parts__Group_1__0__Impl rule__Parts__Group_1__1 ;
    public final void rule__Parts__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1175:1: ( rule__Parts__Group_1__0__Impl rule__Parts__Group_1__1 )
            // InternalSemanticVersioning.g:1176:2: rule__Parts__Group_1__0__Impl rule__Parts__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__Parts__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Parts__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group_1__0"


    // $ANTLR start "rule__Parts__Group_1__0__Impl"
    // InternalSemanticVersioning.g:1183:1: rule__Parts__Group_1__0__Impl : ( '.' ) ;
    public final void rule__Parts__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1187:1: ( ( '.' ) )
            // InternalSemanticVersioning.g:1188:1: ( '.' )
            {
            // InternalSemanticVersioning.g:1188:1: ( '.' )
            // InternalSemanticVersioning.g:1189:2: '.'
            {
             before(grammarAccess.getPartsAccess().getFullStopKeyword_1_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getPartsAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group_1__0__Impl"


    // $ANTLR start "rule__Parts__Group_1__1"
    // InternalSemanticVersioning.g:1198:1: rule__Parts__Group_1__1 : rule__Parts__Group_1__1__Impl ;
    public final void rule__Parts__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1202:1: ( rule__Parts__Group_1__1__Impl )
            // InternalSemanticVersioning.g:1203:2: rule__Parts__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Parts__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group_1__1"


    // $ANTLR start "rule__Parts__Group_1__1__Impl"
    // InternalSemanticVersioning.g:1209:1: rule__Parts__Group_1__1__Impl : ( RULE_PART ) ;
    public final void rule__Parts__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1213:1: ( ( RULE_PART ) )
            // InternalSemanticVersioning.g:1214:1: ( RULE_PART )
            {
            // InternalSemanticVersioning.g:1214:1: ( RULE_PART )
            // InternalSemanticVersioning.g:1215:2: RULE_PART
            {
             before(grammarAccess.getPartsAccess().getPARTTerminalRuleCall_1_1()); 
            match(input,RULE_PART,FOLLOW_2); 
             after(grammarAccess.getPartsAccess().getPARTTerminalRuleCall_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parts__Group_1__1__Impl"


    // $ANTLR start "rule__VersionRangeSet__RangesAssignment_0"
    // InternalSemanticVersioning.g:1225:1: rule__VersionRangeSet__RangesAssignment_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1229:1: ( ( ruleVersionRange ) )
            // InternalSemanticVersioning.g:1230:2: ( ruleVersionRange )
            {
            // InternalSemanticVersioning.g:1230:2: ( ruleVersionRange )
            // InternalSemanticVersioning.g:1231:3: ruleVersionRange
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;

             after(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__RangesAssignment_0"


    // $ANTLR start "rule__VersionRangeSet__RangesAssignment_1_1"
    // InternalSemanticVersioning.g:1240:1: rule__VersionRangeSet__RangesAssignment_1_1 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1244:1: ( ( ruleVersionRange ) )
            // InternalSemanticVersioning.g:1245:2: ( ruleVersionRange )
            {
            // InternalSemanticVersioning.g:1245:2: ( ruleVersionRange )
            // InternalSemanticVersioning.g:1246:3: ruleVersionRange
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;

             after(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__RangesAssignment_1_1"


    // $ANTLR start "rule__HyphenVersionRange__FromAssignment_1"
    // InternalSemanticVersioning.g:1255:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1259:1: ( ( ruleVersionNumber ) )
            // InternalSemanticVersioning.g:1260:2: ( ruleVersionNumber )
            {
            // InternalSemanticVersioning.g:1260:2: ( ruleVersionNumber )
            // InternalSemanticVersioning.g:1261:3: ruleVersionNumber
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;

             after(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0()); 

            }


            }

        }
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


    // $ANTLR start "rule__HyphenVersionRange__ToAssignment_3"
    // InternalSemanticVersioning.g:1270:1: rule__HyphenVersionRange__ToAssignment_3 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1274:1: ( ( ruleVersionNumber ) )
            // InternalSemanticVersioning.g:1275:2: ( ruleVersionNumber )
            {
            // InternalSemanticVersioning.g:1275:2: ( ruleVersionNumber )
            // InternalSemanticVersioning.g:1276:3: ruleVersionNumber
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;

             after(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HyphenVersionRange__ToAssignment_3"


    // $ANTLR start "rule__EnumeratedVersionRange__SimpleVersionsAssignment_1"
    // InternalSemanticVersioning.g:1285:1: rule__EnumeratedVersionRange__SimpleVersionsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__EnumeratedVersionRange__SimpleVersionsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1289:1: ( ( ruleSimpleVersion ) )
            // InternalSemanticVersioning.g:1290:2: ( ruleSimpleVersion )
            {
            // InternalSemanticVersioning.g:1290:2: ( ruleSimpleVersion )
            // InternalSemanticVersioning.g:1291:3: ruleSimpleVersion
            {
             before(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsSimpleVersionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;

             after(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsSimpleVersionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnumeratedVersionRange__SimpleVersionsAssignment_1"


    // $ANTLR start "rule__SimpleVersion__ComparatorAssignment_0"
    // InternalSemanticVersioning.g:1300:1: rule__SimpleVersion__ComparatorAssignment_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1304:1: ( ( ruleVersionComparator ) )
            // InternalSemanticVersioning.g:1305:2: ( ruleVersionComparator )
            {
            // InternalSemanticVersioning.g:1305:2: ( ruleVersionComparator )
            // InternalSemanticVersioning.g:1306:3: ruleVersionComparator
            {
             before(grammarAccess.getSimpleVersionAccess().getComparatorVersionComparatorEnumRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionComparator();

            state._fsp--;

             after(grammarAccess.getSimpleVersionAccess().getComparatorVersionComparatorEnumRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__ComparatorAssignment_0"


    // $ANTLR start "rule__SimpleVersion__HasTildeAssignment_1"
    // InternalSemanticVersioning.g:1315:1: rule__SimpleVersion__HasTildeAssignment_1 : ( ( '~' ) ) ;
    public final void rule__SimpleVersion__HasTildeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1319:1: ( ( ( '~' ) ) )
            // InternalSemanticVersioning.g:1320:2: ( ( '~' ) )
            {
            // InternalSemanticVersioning.g:1320:2: ( ( '~' ) )
            // InternalSemanticVersioning.g:1321:3: ( '~' )
            {
             before(grammarAccess.getSimpleVersionAccess().getHasTildeTildeKeyword_1_0()); 
            // InternalSemanticVersioning.g:1322:3: ( '~' )
            // InternalSemanticVersioning.g:1323:4: '~'
            {
             before(grammarAccess.getSimpleVersionAccess().getHasTildeTildeKeyword_1_0()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getSimpleVersionAccess().getHasTildeTildeKeyword_1_0()); 

            }

             after(grammarAccess.getSimpleVersionAccess().getHasTildeTildeKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__HasTildeAssignment_1"


    // $ANTLR start "rule__SimpleVersion__HasCaretAssignment_2"
    // InternalSemanticVersioning.g:1334:1: rule__SimpleVersion__HasCaretAssignment_2 : ( ( '^' ) ) ;
    public final void rule__SimpleVersion__HasCaretAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1338:1: ( ( ( '^' ) ) )
            // InternalSemanticVersioning.g:1339:2: ( ( '^' ) )
            {
            // InternalSemanticVersioning.g:1339:2: ( ( '^' ) )
            // InternalSemanticVersioning.g:1340:3: ( '^' )
            {
             before(grammarAccess.getSimpleVersionAccess().getHasCaretCircumflexAccentKeyword_2_0()); 
            // InternalSemanticVersioning.g:1341:3: ( '^' )
            // InternalSemanticVersioning.g:1342:4: '^'
            {
             before(grammarAccess.getSimpleVersionAccess().getHasCaretCircumflexAccentKeyword_2_0()); 
            match(input,37,FOLLOW_2); 
             after(grammarAccess.getSimpleVersionAccess().getHasCaretCircumflexAccentKeyword_2_0()); 

            }

             after(grammarAccess.getSimpleVersionAccess().getHasCaretCircumflexAccentKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__HasCaretAssignment_2"


    // $ANTLR start "rule__SimpleVersion__NumberAssignment_3"
    // InternalSemanticVersioning.g:1353:1: rule__SimpleVersion__NumberAssignment_3 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1357:1: ( ( ruleVersionNumber ) )
            // InternalSemanticVersioning.g:1358:2: ( ruleVersionNumber )
            {
            // InternalSemanticVersioning.g:1358:2: ( ruleVersionNumber )
            // InternalSemanticVersioning.g:1359:3: ruleVersionNumber
            {
             before(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;

             after(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_3_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:1368:1: rule__VersionNumber__MajorAssignment_0 : ( ruleXr ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1372:1: ( ( ruleXr ) )
            // InternalSemanticVersioning.g:1373:2: ( ruleXr )
            {
            // InternalSemanticVersioning.g:1373:2: ( ruleXr )
            // InternalSemanticVersioning.g:1374:3: ruleXr
            {
             before(grammarAccess.getVersionNumberAccess().getMajorXrParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleXr();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getMajorXrParserRuleCall_0_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:1383:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleXr ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1387:1: ( ( ruleXr ) )
            // InternalSemanticVersioning.g:1388:2: ( ruleXr )
            {
            // InternalSemanticVersioning.g:1388:2: ( ruleXr )
            // InternalSemanticVersioning.g:1389:3: ruleXr
            {
             before(grammarAccess.getVersionNumberAccess().getMinorXrParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleXr();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getMinorXrParserRuleCall_1_1_0()); 

            }


            }

        }
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


    // $ANTLR start "rule__VersionNumber__PathAssignment_1_2_1"
    // InternalSemanticVersioning.g:1398:1: rule__VersionNumber__PathAssignment_1_2_1 : ( ruleXr ) ;
    public final void rule__VersionNumber__PathAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1402:1: ( ( ruleXr ) )
            // InternalSemanticVersioning.g:1403:2: ( ruleXr )
            {
            // InternalSemanticVersioning.g:1403:2: ( ruleXr )
            // InternalSemanticVersioning.g:1404:3: ruleXr
            {
             before(grammarAccess.getVersionNumberAccess().getPathXrParserRuleCall_1_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleXr();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getPathXrParserRuleCall_1_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__PathAssignment_1_2_1"


    // $ANTLR start "rule__VersionNumber__QualifierAssignment_1_2_2"
    // InternalSemanticVersioning.g:1413:1: rule__VersionNumber__QualifierAssignment_1_2_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_1_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1417:1: ( ( ruleQualifier ) )
            // InternalSemanticVersioning.g:1418:2: ( ruleQualifier )
            {
            // InternalSemanticVersioning.g:1418:2: ( ruleQualifier )
            // InternalSemanticVersioning.g:1419:3: ruleQualifier
            {
             before(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_1_2_2_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifier();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_1_2_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionNumber__QualifierAssignment_1_2_2"


    // $ANTLR start "rule__Qualifier__PreReleaseAssignment_0_1"
    // InternalSemanticVersioning.g:1428:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleParts ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1432:1: ( ( ruleParts ) )
            // InternalSemanticVersioning.g:1433:2: ( ruleParts )
            {
            // InternalSemanticVersioning.g:1433:2: ( ruleParts )
            // InternalSemanticVersioning.g:1434:3: ruleParts
            {
             before(grammarAccess.getQualifierAccess().getPreReleasePartsParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleParts();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getPreReleasePartsParserRuleCall_0_1_0()); 

            }


            }

        }
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
    // InternalSemanticVersioning.g:1443:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleParts ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSemanticVersioning.g:1447:1: ( ( ruleParts ) )
            // InternalSemanticVersioning.g:1448:2: ( ruleParts )
            {
            // InternalSemanticVersioning.g:1448:2: ( ruleParts )
            // InternalSemanticVersioning.g:1449:3: ruleParts
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataPartsParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleParts();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getBuildMetadataPartsParserRuleCall_1_1_0()); 

            }


            }

        }
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

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000010FF000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000007000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000010FF000002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000A00000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000400000002L});

}