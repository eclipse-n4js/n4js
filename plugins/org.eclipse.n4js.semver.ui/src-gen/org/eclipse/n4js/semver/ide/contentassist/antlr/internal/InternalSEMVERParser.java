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
import org.eclipse.n4js.semver.services.SEMVERGrammarAccess;



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
public class InternalSEMVERParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_DIGITS", "RULE_LETTERS", "RULE_WS", "RULE_LETTER", "RULE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'x'", "'X'", "'*'", "'v'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'||'", "'-'", "'.'", "'+'"
    };
    public static final int RULE_ML_COMMENT_FRAGMENT=21;
    public static final int RULE_DIGITS=4;
    public static final int RULE_ZWJ=15;
    public static final int RULE_SL_COMMENT_FRAGMENT=20;
    public static final int RULE_WHITESPACE_FRAGMENT=9;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=23;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=18;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=10;
    public static final int RULE_WS=6;
    public static final int RULE_EOL=11;
    public static final int RULE_BOM=17;
    public static final int RULE_DIGIT=8;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=22;
    public static final int RULE_ANY_OTHER=26;
    public static final int RULE_LETTERS=5;
    public static final int RULE_LETTER=7;
    public static final int RULE_ZWNJ=16;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=19;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=25;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=12;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int RULE_HEX_DIGIT=13;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=14;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=24;
    public static final int T__40=40;
    public static final int T__41=41;

    // delegates
    // delegators


        public InternalSEMVERParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSEMVERParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSEMVERParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSEMVER.g"; }


    	private SEMVERGrammarAccess grammarAccess;

    	public void setGrammarAccess(SEMVERGrammarAccess grammarAccess) {
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
    // InternalSEMVER.g:60:1: entryRuleVersionRangeSet : ruleVersionRangeSet EOF ;
    public final void entryRuleVersionRangeSet() throws RecognitionException {
        try {
            // InternalSEMVER.g:61:1: ( ruleVersionRangeSet EOF )
            // InternalSEMVER.g:62:1: ruleVersionRangeSet EOF
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
    // InternalSEMVER.g:69:1: ruleVersionRangeSet : ( ( rule__VersionRangeSet__Group__0 ) ) ;
    public final void ruleVersionRangeSet() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:73:2: ( ( ( rule__VersionRangeSet__Group__0 ) ) )
            // InternalSEMVER.g:74:2: ( ( rule__VersionRangeSet__Group__0 ) )
            {
            // InternalSEMVER.g:74:2: ( ( rule__VersionRangeSet__Group__0 ) )
            // InternalSEMVER.g:75:3: ( rule__VersionRangeSet__Group__0 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup()); 
            // InternalSEMVER.g:76:3: ( rule__VersionRangeSet__Group__0 )
            // InternalSEMVER.g:76:4: rule__VersionRangeSet__Group__0
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
    // InternalSEMVER.g:85:1: entryRuleVersionRange : ruleVersionRange EOF ;
    public final void entryRuleVersionRange() throws RecognitionException {
        try {
            // InternalSEMVER.g:86:1: ( ruleVersionRange EOF )
            // InternalSEMVER.g:87:1: ruleVersionRange EOF
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
    // InternalSEMVER.g:94:1: ruleVersionRange : ( ( rule__VersionRange__Alternatives ) ) ;
    public final void ruleVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:98:2: ( ( ( rule__VersionRange__Alternatives ) ) )
            // InternalSEMVER.g:99:2: ( ( rule__VersionRange__Alternatives ) )
            {
            // InternalSEMVER.g:99:2: ( ( rule__VersionRange__Alternatives ) )
            // InternalSEMVER.g:100:3: ( rule__VersionRange__Alternatives )
            {
             before(grammarAccess.getVersionRangeAccess().getAlternatives()); 
            // InternalSEMVER.g:101:3: ( rule__VersionRange__Alternatives )
            // InternalSEMVER.g:101:4: rule__VersionRange__Alternatives
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
    // InternalSEMVER.g:110:1: entryRuleHyphenVersionRange : ruleHyphenVersionRange EOF ;
    public final void entryRuleHyphenVersionRange() throws RecognitionException {
        try {
            // InternalSEMVER.g:111:1: ( ruleHyphenVersionRange EOF )
            // InternalSEMVER.g:112:1: ruleHyphenVersionRange EOF
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
    // InternalSEMVER.g:119:1: ruleHyphenVersionRange : ( ( rule__HyphenVersionRange__Group__0 ) ) ;
    public final void ruleHyphenVersionRange() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:123:2: ( ( ( rule__HyphenVersionRange__Group__0 ) ) )
            // InternalSEMVER.g:124:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            {
            // InternalSEMVER.g:124:2: ( ( rule__HyphenVersionRange__Group__0 ) )
            // InternalSEMVER.g:125:3: ( rule__HyphenVersionRange__Group__0 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getGroup()); 
            // InternalSEMVER.g:126:3: ( rule__HyphenVersionRange__Group__0 )
            // InternalSEMVER.g:126:4: rule__HyphenVersionRange__Group__0
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


    // $ANTLR start "entryRuleVersionRangeContraint"
    // InternalSEMVER.g:135:1: entryRuleVersionRangeContraint : ruleVersionRangeContraint EOF ;
    public final void entryRuleVersionRangeContraint() throws RecognitionException {
        try {
            // InternalSEMVER.g:136:1: ( ruleVersionRangeContraint EOF )
            // InternalSEMVER.g:137:1: ruleVersionRangeContraint EOF
            {
             before(grammarAccess.getVersionRangeContraintRule()); 
            pushFollow(FOLLOW_1);
            ruleVersionRangeContraint();

            state._fsp--;

             after(grammarAccess.getVersionRangeContraintRule()); 
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
    // $ANTLR end "entryRuleVersionRangeContraint"


    // $ANTLR start "ruleVersionRangeContraint"
    // InternalSEMVER.g:144:1: ruleVersionRangeContraint : ( ( rule__VersionRangeContraint__Group__0 ) ) ;
    public final void ruleVersionRangeContraint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:148:2: ( ( ( rule__VersionRangeContraint__Group__0 ) ) )
            // InternalSEMVER.g:149:2: ( ( rule__VersionRangeContraint__Group__0 ) )
            {
            // InternalSEMVER.g:149:2: ( ( rule__VersionRangeContraint__Group__0 ) )
            // InternalSEMVER.g:150:3: ( rule__VersionRangeContraint__Group__0 )
            {
             before(grammarAccess.getVersionRangeContraintAccess().getGroup()); 
            // InternalSEMVER.g:151:3: ( rule__VersionRangeContraint__Group__0 )
            // InternalSEMVER.g:151:4: rule__VersionRangeContraint__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeContraintAccess().getGroup()); 

            }


            }

        }
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
    // InternalSEMVER.g:160:1: entryRuleSimpleVersion : ruleSimpleVersion EOF ;
    public final void entryRuleSimpleVersion() throws RecognitionException {
        try {
            // InternalSEMVER.g:161:1: ( ruleSimpleVersion EOF )
            // InternalSEMVER.g:162:1: ruleSimpleVersion EOF
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
    // InternalSEMVER.g:169:1: ruleSimpleVersion : ( ( rule__SimpleVersion__Group__0 ) ) ;
    public final void ruleSimpleVersion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:173:2: ( ( ( rule__SimpleVersion__Group__0 ) ) )
            // InternalSEMVER.g:174:2: ( ( rule__SimpleVersion__Group__0 ) )
            {
            // InternalSEMVER.g:174:2: ( ( rule__SimpleVersion__Group__0 ) )
            // InternalSEMVER.g:175:3: ( rule__SimpleVersion__Group__0 )
            {
             before(grammarAccess.getSimpleVersionAccess().getGroup()); 
            // InternalSEMVER.g:176:3: ( rule__SimpleVersion__Group__0 )
            // InternalSEMVER.g:176:4: rule__SimpleVersion__Group__0
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
    // InternalSEMVER.g:185:1: entryRuleVersionNumber : ruleVersionNumber EOF ;
    public final void entryRuleVersionNumber() throws RecognitionException {
        try {
            // InternalSEMVER.g:186:1: ( ruleVersionNumber EOF )
            // InternalSEMVER.g:187:1: ruleVersionNumber EOF
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
    // InternalSEMVER.g:194:1: ruleVersionNumber : ( ( rule__VersionNumber__Group__0 ) ) ;
    public final void ruleVersionNumber() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:198:2: ( ( ( rule__VersionNumber__Group__0 ) ) )
            // InternalSEMVER.g:199:2: ( ( rule__VersionNumber__Group__0 ) )
            {
            // InternalSEMVER.g:199:2: ( ( rule__VersionNumber__Group__0 ) )
            // InternalSEMVER.g:200:3: ( rule__VersionNumber__Group__0 )
            {
             before(grammarAccess.getVersionNumberAccess().getGroup()); 
            // InternalSEMVER.g:201:3: ( rule__VersionNumber__Group__0 )
            // InternalSEMVER.g:201:4: rule__VersionNumber__Group__0
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


    // $ANTLR start "entryRuleVersionPart"
    // InternalSEMVER.g:210:1: entryRuleVersionPart : ruleVersionPart EOF ;
    public final void entryRuleVersionPart() throws RecognitionException {
        try {
            // InternalSEMVER.g:211:1: ( ruleVersionPart EOF )
            // InternalSEMVER.g:212:1: ruleVersionPart EOF
            {
             before(grammarAccess.getVersionPartRule()); 
            pushFollow(FOLLOW_1);
            ruleVersionPart();

            state._fsp--;

             after(grammarAccess.getVersionPartRule()); 
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
    // $ANTLR end "entryRuleVersionPart"


    // $ANTLR start "ruleVersionPart"
    // InternalSEMVER.g:219:1: ruleVersionPart : ( ( rule__VersionPart__Alternatives ) ) ;
    public final void ruleVersionPart() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:223:2: ( ( ( rule__VersionPart__Alternatives ) ) )
            // InternalSEMVER.g:224:2: ( ( rule__VersionPart__Alternatives ) )
            {
            // InternalSEMVER.g:224:2: ( ( rule__VersionPart__Alternatives ) )
            // InternalSEMVER.g:225:3: ( rule__VersionPart__Alternatives )
            {
             before(grammarAccess.getVersionPartAccess().getAlternatives()); 
            // InternalSEMVER.g:226:3: ( rule__VersionPart__Alternatives )
            // InternalSEMVER.g:226:4: rule__VersionPart__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VersionPart__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getVersionPartAccess().getAlternatives()); 

            }


            }

        }
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
    // InternalSEMVER.g:235:1: entryRuleQualifier : ruleQualifier EOF ;
    public final void entryRuleQualifier() throws RecognitionException {
        try {
            // InternalSEMVER.g:236:1: ( ruleQualifier EOF )
            // InternalSEMVER.g:237:1: ruleQualifier EOF
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
    // InternalSEMVER.g:244:1: ruleQualifier : ( ( rule__Qualifier__Alternatives ) ) ;
    public final void ruleQualifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:248:2: ( ( ( rule__Qualifier__Alternatives ) ) )
            // InternalSEMVER.g:249:2: ( ( rule__Qualifier__Alternatives ) )
            {
            // InternalSEMVER.g:249:2: ( ( rule__Qualifier__Alternatives ) )
            // InternalSEMVER.g:250:3: ( rule__Qualifier__Alternatives )
            {
             before(grammarAccess.getQualifierAccess().getAlternatives()); 
            // InternalSEMVER.g:251:3: ( rule__Qualifier__Alternatives )
            // InternalSEMVER.g:251:4: rule__Qualifier__Alternatives
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


    // $ANTLR start "entryRuleQualifierTag"
    // InternalSEMVER.g:260:1: entryRuleQualifierTag : ruleQualifierTag EOF ;
    public final void entryRuleQualifierTag() throws RecognitionException {
        try {
            // InternalSEMVER.g:261:1: ( ruleQualifierTag EOF )
            // InternalSEMVER.g:262:1: ruleQualifierTag EOF
            {
             before(grammarAccess.getQualifierTagRule()); 
            pushFollow(FOLLOW_1);
            ruleQualifierTag();

            state._fsp--;

             after(grammarAccess.getQualifierTagRule()); 
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
    // $ANTLR end "entryRuleQualifierTag"


    // $ANTLR start "ruleQualifierTag"
    // InternalSEMVER.g:269:1: ruleQualifierTag : ( ( rule__QualifierTag__Group__0 ) ) ;
    public final void ruleQualifierTag() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:273:2: ( ( ( rule__QualifierTag__Group__0 ) ) )
            // InternalSEMVER.g:274:2: ( ( rule__QualifierTag__Group__0 ) )
            {
            // InternalSEMVER.g:274:2: ( ( rule__QualifierTag__Group__0 ) )
            // InternalSEMVER.g:275:3: ( rule__QualifierTag__Group__0 )
            {
             before(grammarAccess.getQualifierTagAccess().getGroup()); 
            // InternalSEMVER.g:276:3: ( rule__QualifierTag__Group__0 )
            // InternalSEMVER.g:276:4: rule__QualifierTag__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getQualifierTagAccess().getGroup()); 

            }


            }

        }
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSEMVER.g:285:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSEMVER.g:286:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSEMVER.g:287:1: ruleALPHA_NUMERIC_CHARS EOF
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARSRule()); 
            pushFollow(FOLLOW_1);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             after(grammarAccess.getALPHA_NUMERIC_CHARSRule()); 
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
    // $ANTLR end "entryRuleALPHA_NUMERIC_CHARS"


    // $ANTLR start "ruleALPHA_NUMERIC_CHARS"
    // InternalSEMVER.g:294:1: ruleALPHA_NUMERIC_CHARS : ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:298:2: ( ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) )
            // InternalSEMVER.g:299:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            {
            // InternalSEMVER.g:299:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            // InternalSEMVER.g:300:3: ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* )
            {
            // InternalSEMVER.g:300:3: ( ( ruleALPHA_NUMERIC_CHAR ) )
            // InternalSEMVER.g:301:4: ( ruleALPHA_NUMERIC_CHAR )
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            // InternalSEMVER.g:302:4: ( ruleALPHA_NUMERIC_CHAR )
            // InternalSEMVER.g:302:5: ruleALPHA_NUMERIC_CHAR
            {
            pushFollow(FOLLOW_3);
            ruleALPHA_NUMERIC_CHAR();

            state._fsp--;


            }

             after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 

            }

            // InternalSEMVER.g:305:3: ( ( ruleALPHA_NUMERIC_CHAR )* )
            // InternalSEMVER.g:306:4: ( ruleALPHA_NUMERIC_CHAR )*
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            // InternalSEMVER.g:307:4: ( ruleALPHA_NUMERIC_CHAR )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_DIGITS && LA1_0<=RULE_LETTERS)||LA1_0==39) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSEMVER.g:307:5: ruleALPHA_NUMERIC_CHAR
            	    {
            	    pushFollow(FOLLOW_3);
            	    ruleALPHA_NUMERIC_CHAR();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 

            }


            }


            }

        }
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
    // InternalSEMVER.g:317:1: entryRuleWILDCARD : ruleWILDCARD EOF ;
    public final void entryRuleWILDCARD() throws RecognitionException {
        try {
            // InternalSEMVER.g:318:1: ( ruleWILDCARD EOF )
            // InternalSEMVER.g:319:1: ruleWILDCARD EOF
            {
             before(grammarAccess.getWILDCARDRule()); 
            pushFollow(FOLLOW_1);
            ruleWILDCARD();

            state._fsp--;

             after(grammarAccess.getWILDCARDRule()); 
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
    // $ANTLR end "entryRuleWILDCARD"


    // $ANTLR start "ruleWILDCARD"
    // InternalSEMVER.g:326:1: ruleWILDCARD : ( ( rule__WILDCARD__Alternatives ) ) ;
    public final void ruleWILDCARD() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:330:2: ( ( ( rule__WILDCARD__Alternatives ) ) )
            // InternalSEMVER.g:331:2: ( ( rule__WILDCARD__Alternatives ) )
            {
            // InternalSEMVER.g:331:2: ( ( rule__WILDCARD__Alternatives ) )
            // InternalSEMVER.g:332:3: ( rule__WILDCARD__Alternatives )
            {
             before(grammarAccess.getWILDCARDAccess().getAlternatives()); 
            // InternalSEMVER.g:333:3: ( rule__WILDCARD__Alternatives )
            // InternalSEMVER.g:333:4: rule__WILDCARD__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__WILDCARD__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getWILDCARDAccess().getAlternatives()); 

            }


            }

        }
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


    // $ANTLR start "ruleALPHA_NUMERIC_CHAR"
    // InternalSEMVER.g:343:1: ruleALPHA_NUMERIC_CHAR : ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) ) ;
    public final void ruleALPHA_NUMERIC_CHAR() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:347:2: ( ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) ) )
            // InternalSEMVER.g:348:2: ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) )
            {
            // InternalSEMVER.g:348:2: ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) )
            // InternalSEMVER.g:349:3: ( rule__ALPHA_NUMERIC_CHAR__Group__0 )
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getGroup()); 
            // InternalSEMVER.g:350:3: ( rule__ALPHA_NUMERIC_CHAR__Group__0 )
            // InternalSEMVER.g:350:4: rule__ALPHA_NUMERIC_CHAR__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getGroup()); 

            }


            }

        }
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


    // $ANTLR start "ruleVersionComparator"
    // InternalSEMVER.g:359:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:363:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSEMVER.g:364:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSEMVER.g:364:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSEMVER.g:365:3: ( rule__VersionComparator__Alternatives )
            {
             before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            // InternalSEMVER.g:366:3: ( rule__VersionComparator__Alternatives )
            // InternalSEMVER.g:366:4: rule__VersionComparator__Alternatives
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
    // InternalSEMVER.g:374:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:378:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt2=2;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // InternalSEMVER.g:379:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSEMVER.g:379:2: ( ruleVersionRangeContraint )
                    // InternalSEMVER.g:380:3: ruleVersionRangeContraint
                    {
                     before(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleVersionRangeContraint();

                    state._fsp--;

                     after(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:385:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSEMVER.g:385:2: ( ruleHyphenVersionRange )
                    // InternalSEMVER.g:386:3: ruleHyphenVersionRange
                    {
                     before(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleHyphenVersionRange();

                    state._fsp--;

                     after(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1()); 

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
    // InternalSEMVER.g:395:1: rule__VersionPart__Alternatives : ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) );
    public final void rule__VersionPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:399:1: ( ( ( rule__VersionPart__WildcardAssignment_0 ) ) | ( ( rule__VersionPart__NumberRawAssignment_1 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=27 && LA3_0<=29)) ) {
                alt3=1;
            }
            else if ( (LA3_0==RULE_DIGITS) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalSEMVER.g:400:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    {
                    // InternalSEMVER.g:400:2: ( ( rule__VersionPart__WildcardAssignment_0 ) )
                    // InternalSEMVER.g:401:3: ( rule__VersionPart__WildcardAssignment_0 )
                    {
                     before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 
                    // InternalSEMVER.g:402:3: ( rule__VersionPart__WildcardAssignment_0 )
                    // InternalSEMVER.g:402:4: rule__VersionPart__WildcardAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionPart__WildcardAssignment_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:406:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    {
                    // InternalSEMVER.g:406:2: ( ( rule__VersionPart__NumberRawAssignment_1 ) )
                    // InternalSEMVER.g:407:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    {
                     before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 
                    // InternalSEMVER.g:408:3: ( rule__VersionPart__NumberRawAssignment_1 )
                    // InternalSEMVER.g:408:4: rule__VersionPart__NumberRawAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionPart__NumberRawAssignment_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); 

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
    // InternalSEMVER.g:416:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:420:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt4=3;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // InternalSEMVER.g:421:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSEMVER.g:421:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSEMVER.g:422:3: ( rule__Qualifier__Group_0__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    // InternalSEMVER.g:423:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSEMVER.g:423:4: rule__Qualifier__Group_0__0
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
                    // InternalSEMVER.g:427:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSEMVER.g:427:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSEMVER.g:428:3: ( rule__Qualifier__Group_1__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    // InternalSEMVER.g:429:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSEMVER.g:429:4: rule__Qualifier__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getQualifierAccess().getGroup_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:433:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSEMVER.g:433:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSEMVER.g:434:3: ( rule__Qualifier__Group_2__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    // InternalSEMVER.g:435:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSEMVER.g:435:4: rule__Qualifier__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Qualifier__Group_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getQualifierAccess().getGroup_2()); 

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


    // $ANTLR start "rule__WILDCARD__Alternatives"
    // InternalSEMVER.g:443:1: rule__WILDCARD__Alternatives : ( ( 'x' ) | ( 'X' ) | ( '*' ) );
    public final void rule__WILDCARD__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:447:1: ( ( 'x' ) | ( 'X' ) | ( '*' ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 27:
                {
                alt5=1;
                }
                break;
            case 28:
                {
                alt5=2;
                }
                break;
            case 29:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalSEMVER.g:448:2: ( 'x' )
                    {
                    // InternalSEMVER.g:448:2: ( 'x' )
                    // InternalSEMVER.g:449:3: 'x'
                    {
                     before(grammarAccess.getWILDCARDAccess().getXKeyword_0()); 
                    match(input,27,FOLLOW_2); 
                     after(grammarAccess.getWILDCARDAccess().getXKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:454:2: ( 'X' )
                    {
                    // InternalSEMVER.g:454:2: ( 'X' )
                    // InternalSEMVER.g:455:3: 'X'
                    {
                     before(grammarAccess.getWILDCARDAccess().getXKeyword_1()); 
                    match(input,28,FOLLOW_2); 
                     after(grammarAccess.getWILDCARDAccess().getXKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:460:2: ( '*' )
                    {
                    // InternalSEMVER.g:460:2: ( '*' )
                    // InternalSEMVER.g:461:3: '*'
                    {
                     before(grammarAccess.getWILDCARDAccess().getAsteriskKeyword_2()); 
                    match(input,29,FOLLOW_2); 
                     after(grammarAccess.getWILDCARDAccess().getAsteriskKeyword_2()); 

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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Alternatives_1"
    // InternalSEMVER.g:470:1: rule__ALPHA_NUMERIC_CHAR__Alternatives_1 : ( ( RULE_DIGITS ) | ( RULE_LETTERS ) );
    public final void rule__ALPHA_NUMERIC_CHAR__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:474:1: ( ( RULE_DIGITS ) | ( RULE_LETTERS ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_DIGITS) ) {
                alt6=1;
            }
            else if ( (LA6_0==RULE_LETTERS) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalSEMVER.g:475:2: ( RULE_DIGITS )
                    {
                    // InternalSEMVER.g:475:2: ( RULE_DIGITS )
                    // InternalSEMVER.g:476:3: RULE_DIGITS
                    {
                     before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1_0()); 
                    match(input,RULE_DIGITS,FOLLOW_2); 
                     after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:481:2: ( RULE_LETTERS )
                    {
                    // InternalSEMVER.g:481:2: ( RULE_LETTERS )
                    // InternalSEMVER.g:482:3: RULE_LETTERS
                    {
                     before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSTerminalRuleCall_1_1()); 
                    match(input,RULE_LETTERS,FOLLOW_2); 
                     after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSTerminalRuleCall_1_1()); 

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


    // $ANTLR start "rule__VersionComparator__Alternatives"
    // InternalSEMVER.g:491:1: rule__VersionComparator__Alternatives : ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:495:1: ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt7=8;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt7=1;
                }
                break;
            case 31:
                {
                alt7=2;
                }
                break;
            case 32:
                {
                alt7=3;
                }
                break;
            case 33:
                {
                alt7=4;
                }
                break;
            case 34:
                {
                alt7=5;
                }
                break;
            case 35:
                {
                alt7=6;
                }
                break;
            case 36:
                {
                alt7=7;
                }
                break;
            case 37:
                {
                alt7=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalSEMVER.g:496:2: ( ( 'v' ) )
                    {
                    // InternalSEMVER.g:496:2: ( ( 'v' ) )
                    // InternalSEMVER.g:497:3: ( 'v' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 
                    // InternalSEMVER.g:498:3: ( 'v' )
                    // InternalSEMVER.g:498:4: 'v'
                    {
                    match(input,30,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:502:2: ( ( '=' ) )
                    {
                    // InternalSEMVER.g:502:2: ( ( '=' ) )
                    // InternalSEMVER.g:503:3: ( '=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 
                    // InternalSEMVER.g:504:3: ( '=' )
                    // InternalSEMVER.g:504:4: '='
                    {
                    match(input,31,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:508:2: ( ( '<' ) )
                    {
                    // InternalSEMVER.g:508:2: ( ( '<' ) )
                    // InternalSEMVER.g:509:3: ( '<' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 
                    // InternalSEMVER.g:510:3: ( '<' )
                    // InternalSEMVER.g:510:4: '<'
                    {
                    match(input,32,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:514:2: ( ( '~' ) )
                    {
                    // InternalSEMVER.g:514:2: ( ( '~' ) )
                    // InternalSEMVER.g:515:3: ( '~' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 
                    // InternalSEMVER.g:516:3: ( '~' )
                    // InternalSEMVER.g:516:4: '~'
                    {
                    match(input,33,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalSEMVER.g:520:2: ( ( '^' ) )
                    {
                    // InternalSEMVER.g:520:2: ( ( '^' ) )
                    // InternalSEMVER.g:521:3: ( '^' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 
                    // InternalSEMVER.g:522:3: ( '^' )
                    // InternalSEMVER.g:522:4: '^'
                    {
                    match(input,34,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalSEMVER.g:526:2: ( ( '<=' ) )
                    {
                    // InternalSEMVER.g:526:2: ( ( '<=' ) )
                    // InternalSEMVER.g:527:3: ( '<=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 
                    // InternalSEMVER.g:528:3: ( '<=' )
                    // InternalSEMVER.g:528:4: '<='
                    {
                    match(input,35,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalSEMVER.g:532:2: ( ( '>' ) )
                    {
                    // InternalSEMVER.g:532:2: ( ( '>' ) )
                    // InternalSEMVER.g:533:3: ( '>' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 
                    // InternalSEMVER.g:534:3: ( '>' )
                    // InternalSEMVER.g:534:4: '>'
                    {
                    match(input,36,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalSEMVER.g:538:2: ( ( '>=' ) )
                    {
                    // InternalSEMVER.g:538:2: ( ( '>=' ) )
                    // InternalSEMVER.g:539:3: ( '>=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7()); 
                    // InternalSEMVER.g:540:3: ( '>=' )
                    // InternalSEMVER.g:540:4: '>='
                    {
                    match(input,37,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7()); 

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
    // InternalSEMVER.g:548:1: rule__VersionRangeSet__Group__0 : rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 ;
    public final void rule__VersionRangeSet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:552:1: ( rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 )
            // InternalSEMVER.g:553:2: rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1
            {
            pushFollow(FOLLOW_4);
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
    // InternalSEMVER.g:560:1: rule__VersionRangeSet__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:564:1: ( ( () ) )
            // InternalSEMVER.g:565:1: ( () )
            {
            // InternalSEMVER.g:565:1: ( () )
            // InternalSEMVER.g:566:2: ()
            {
             before(grammarAccess.getVersionRangeSetAccess().getVersionRangeSetAction_0()); 
            // InternalSEMVER.g:567:2: ()
            // InternalSEMVER.g:567:3: 
            {
            }

             after(grammarAccess.getVersionRangeSetAccess().getVersionRangeSetAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group__0__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group__1"
    // InternalSEMVER.g:575:1: rule__VersionRangeSet__Group__1 : rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2 ;
    public final void rule__VersionRangeSet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:579:1: ( rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2 )
            // InternalSEMVER.g:580:2: rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__VersionRangeSet__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group__2();

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
    // InternalSEMVER.g:587:1: rule__VersionRangeSet__Group__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:591:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:592:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:592:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:593:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_1()); 
            // InternalSEMVER.g:594:2: ( RULE_WS )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_WS) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSEMVER.g:594:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

             after(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__VersionRangeSet__Group__2"
    // InternalSEMVER.g:602:1: rule__VersionRangeSet__Group__2 : rule__VersionRangeSet__Group__2__Impl ;
    public final void rule__VersionRangeSet__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:606:1: ( rule__VersionRangeSet__Group__2__Impl )
            // InternalSEMVER.g:607:2: rule__VersionRangeSet__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group__2__Impl();

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
    // $ANTLR end "rule__VersionRangeSet__Group__2"


    // $ANTLR start "rule__VersionRangeSet__Group__2__Impl"
    // InternalSEMVER.g:613:1: rule__VersionRangeSet__Group__2__Impl : ( ( rule__VersionRangeSet__Group_2__0 )? ) ;
    public final void rule__VersionRangeSet__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:617:1: ( ( ( rule__VersionRangeSet__Group_2__0 )? ) )
            // InternalSEMVER.g:618:1: ( ( rule__VersionRangeSet__Group_2__0 )? )
            {
            // InternalSEMVER.g:618:1: ( ( rule__VersionRangeSet__Group_2__0 )? )
            // InternalSEMVER.g:619:2: ( rule__VersionRangeSet__Group_2__0 )?
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup_2()); 
            // InternalSEMVER.g:620:2: ( rule__VersionRangeSet__Group_2__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_DIGITS||(LA9_0>=27 && LA9_0<=37)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSEMVER.g:620:3: rule__VersionRangeSet__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionRangeSet__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVersionRangeSetAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group__2__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_2__0"
    // InternalSEMVER.g:629:1: rule__VersionRangeSet__Group_2__0 : rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1 ;
    public final void rule__VersionRangeSet__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:633:1: ( rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1 )
            // InternalSEMVER.g:634:2: rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1
            {
            pushFollow(FOLLOW_6);
            rule__VersionRangeSet__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2__1();

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
    // $ANTLR end "rule__VersionRangeSet__Group_2__0"


    // $ANTLR start "rule__VersionRangeSet__Group_2__0__Impl"
    // InternalSEMVER.g:641:1: rule__VersionRangeSet__Group_2__0__Impl : ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) ) ;
    public final void rule__VersionRangeSet__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:645:1: ( ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) ) )
            // InternalSEMVER.g:646:1: ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) )
            {
            // InternalSEMVER.g:646:1: ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) )
            // InternalSEMVER.g:647:2: ( rule__VersionRangeSet__RangesAssignment_2_0 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_0()); 
            // InternalSEMVER.g:648:2: ( rule__VersionRangeSet__RangesAssignment_2_0 )
            // InternalSEMVER.g:648:3: rule__VersionRangeSet__RangesAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__RangesAssignment_2_0();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_2__0__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_2__1"
    // InternalSEMVER.g:656:1: rule__VersionRangeSet__Group_2__1 : rule__VersionRangeSet__Group_2__1__Impl rule__VersionRangeSet__Group_2__2 ;
    public final void rule__VersionRangeSet__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:660:1: ( rule__VersionRangeSet__Group_2__1__Impl rule__VersionRangeSet__Group_2__2 )
            // InternalSEMVER.g:661:2: rule__VersionRangeSet__Group_2__1__Impl rule__VersionRangeSet__Group_2__2
            {
            pushFollow(FOLLOW_6);
            rule__VersionRangeSet__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2__2();

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
    // $ANTLR end "rule__VersionRangeSet__Group_2__1"


    // $ANTLR start "rule__VersionRangeSet__Group_2__1__Impl"
    // InternalSEMVER.g:668:1: rule__VersionRangeSet__Group_2__1__Impl : ( ( rule__VersionRangeSet__Group_2_1__0 )* ) ;
    public final void rule__VersionRangeSet__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:672:1: ( ( ( rule__VersionRangeSet__Group_2_1__0 )* ) )
            // InternalSEMVER.g:673:1: ( ( rule__VersionRangeSet__Group_2_1__0 )* )
            {
            // InternalSEMVER.g:673:1: ( ( rule__VersionRangeSet__Group_2_1__0 )* )
            // InternalSEMVER.g:674:2: ( rule__VersionRangeSet__Group_2_1__0 )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup_2_1()); 
            // InternalSEMVER.g:675:2: ( rule__VersionRangeSet__Group_2_1__0 )*
            loop10:
            do {
                int alt10=2;
                alt10 = dfa10.predict(input);
                switch (alt10) {
            	case 1 :
            	    // InternalSEMVER.g:675:3: rule__VersionRangeSet__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__VersionRangeSet__Group_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getVersionRangeSetAccess().getGroup_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_2__1__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_2__2"
    // InternalSEMVER.g:683:1: rule__VersionRangeSet__Group_2__2 : rule__VersionRangeSet__Group_2__2__Impl ;
    public final void rule__VersionRangeSet__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:687:1: ( rule__VersionRangeSet__Group_2__2__Impl )
            // InternalSEMVER.g:688:2: rule__VersionRangeSet__Group_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2__2__Impl();

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
    // $ANTLR end "rule__VersionRangeSet__Group_2__2"


    // $ANTLR start "rule__VersionRangeSet__Group_2__2__Impl"
    // InternalSEMVER.g:694:1: rule__VersionRangeSet__Group_2__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:698:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:699:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:699:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:700:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_2()); 
            // InternalSEMVER.g:701:2: ( RULE_WS )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_WS) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalSEMVER.g:701:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_2__2__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__0"
    // InternalSEMVER.g:710:1: rule__VersionRangeSet__Group_2_1__0 : rule__VersionRangeSet__Group_2_1__0__Impl rule__VersionRangeSet__Group_2_1__1 ;
    public final void rule__VersionRangeSet__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:714:1: ( rule__VersionRangeSet__Group_2_1__0__Impl rule__VersionRangeSet__Group_2_1__1 )
            // InternalSEMVER.g:715:2: rule__VersionRangeSet__Group_2_1__0__Impl rule__VersionRangeSet__Group_2_1__1
            {
            pushFollow(FOLLOW_6);
            rule__VersionRangeSet__Group_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2_1__1();

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
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__0"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__0__Impl"
    // InternalSEMVER.g:722:1: rule__VersionRangeSet__Group_2_1__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:726:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:727:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:727:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:728:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_0()); 
            // InternalSEMVER.g:729:2: ( RULE_WS )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_WS) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSEMVER.g:729:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__0__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__1"
    // InternalSEMVER.g:737:1: rule__VersionRangeSet__Group_2_1__1 : rule__VersionRangeSet__Group_2_1__1__Impl rule__VersionRangeSet__Group_2_1__2 ;
    public final void rule__VersionRangeSet__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:741:1: ( rule__VersionRangeSet__Group_2_1__1__Impl rule__VersionRangeSet__Group_2_1__2 )
            // InternalSEMVER.g:742:2: rule__VersionRangeSet__Group_2_1__1__Impl rule__VersionRangeSet__Group_2_1__2
            {
            pushFollow(FOLLOW_4);
            rule__VersionRangeSet__Group_2_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2_1__2();

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
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__1"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__1__Impl"
    // InternalSEMVER.g:749:1: rule__VersionRangeSet__Group_2_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSet__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:753:1: ( ( '||' ) )
            // InternalSEMVER.g:754:1: ( '||' )
            {
            // InternalSEMVER.g:754:1: ( '||' )
            // InternalSEMVER.g:755:2: '||'
            {
             before(grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_2_1_1()); 
            match(input,38,FOLLOW_2); 
             after(grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__1__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__2"
    // InternalSEMVER.g:764:1: rule__VersionRangeSet__Group_2_1__2 : rule__VersionRangeSet__Group_2_1__2__Impl rule__VersionRangeSet__Group_2_1__3 ;
    public final void rule__VersionRangeSet__Group_2_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:768:1: ( rule__VersionRangeSet__Group_2_1__2__Impl rule__VersionRangeSet__Group_2_1__3 )
            // InternalSEMVER.g:769:2: rule__VersionRangeSet__Group_2_1__2__Impl rule__VersionRangeSet__Group_2_1__3
            {
            pushFollow(FOLLOW_4);
            rule__VersionRangeSet__Group_2_1__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2_1__3();

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
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__2"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__2__Impl"
    // InternalSEMVER.g:776:1: rule__VersionRangeSet__Group_2_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group_2_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:780:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:781:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:781:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:782:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_2()); 
            // InternalSEMVER.g:783:2: ( RULE_WS )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_WS) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalSEMVER.g:783:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__2__Impl"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__3"
    // InternalSEMVER.g:791:1: rule__VersionRangeSet__Group_2_1__3 : rule__VersionRangeSet__Group_2_1__3__Impl ;
    public final void rule__VersionRangeSet__Group_2_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:795:1: ( rule__VersionRangeSet__Group_2_1__3__Impl )
            // InternalSEMVER.g:796:2: rule__VersionRangeSet__Group_2_1__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2_1__3__Impl();

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
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__3"


    // $ANTLR start "rule__VersionRangeSet__Group_2_1__3__Impl"
    // InternalSEMVER.g:802:1: rule__VersionRangeSet__Group_2_1__3__Impl : ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) ) ;
    public final void rule__VersionRangeSet__Group_2_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:806:1: ( ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) ) )
            // InternalSEMVER.g:807:1: ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) )
            {
            // InternalSEMVER.g:807:1: ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) )
            // InternalSEMVER.g:808:2: ( rule__VersionRangeSet__RangesAssignment_2_1_3 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_1_3()); 
            // InternalSEMVER.g:809:2: ( rule__VersionRangeSet__RangesAssignment_2_1_3 )
            // InternalSEMVER.g:809:3: rule__VersionRangeSet__RangesAssignment_2_1_3
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__RangesAssignment_2_1_3();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_1_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__Group_2_1__3__Impl"


    // $ANTLR start "rule__HyphenVersionRange__Group__0"
    // InternalSEMVER.g:818:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:822:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSEMVER.g:823:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSEMVER.g:830:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:834:1: ( ( () ) )
            // InternalSEMVER.g:835:1: ( () )
            {
            // InternalSEMVER.g:835:1: ( () )
            // InternalSEMVER.g:836:2: ()
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            // InternalSEMVER.g:837:2: ()
            // InternalSEMVER.g:837:3: 
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
    // InternalSEMVER.g:845:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:849:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSEMVER.g:850:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
            {
            pushFollow(FOLLOW_9);
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
    // InternalSEMVER.g:857:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:861:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSEMVER.g:862:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSEMVER.g:862:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSEMVER.g:863:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            // InternalSEMVER.g:864:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSEMVER.g:864:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSEMVER.g:872:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:876:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSEMVER.g:877:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
            {
            pushFollow(FOLLOW_10);
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
    // InternalSEMVER.g:884:1: rule__HyphenVersionRange__Group__2__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:888:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSEMVER.g:889:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSEMVER.g:889:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSEMVER.g:890:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:890:2: ( ( RULE_WS ) )
            // InternalSEMVER.g:891:3: ( RULE_WS )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            // InternalSEMVER.g:892:3: ( RULE_WS )
            // InternalSEMVER.g:892:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); 

            }

             after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 

            }

            // InternalSEMVER.g:895:2: ( ( RULE_WS )* )
            // InternalSEMVER.g:896:3: ( RULE_WS )*
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            // InternalSEMVER.g:897:3: ( RULE_WS )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_WS) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSEMVER.g:897:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

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
    // InternalSEMVER.g:906:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:910:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSEMVER.g:911:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
            {
            pushFollow(FOLLOW_9);
            rule__HyphenVersionRange__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__4();

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
    // InternalSEMVER.g:918:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:922:1: ( ( '-' ) )
            // InternalSEMVER.g:923:1: ( '-' )
            {
            // InternalSEMVER.g:923:1: ( '-' )
            // InternalSEMVER.g:924:2: '-'
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 
            match(input,39,FOLLOW_2); 
             after(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); 

            }


            }

        }
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
    // InternalSEMVER.g:933:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:937:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSEMVER.g:938:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
            {
            pushFollow(FOLLOW_8);
            rule__HyphenVersionRange__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__5();

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
    // $ANTLR end "rule__HyphenVersionRange__Group__4"


    // $ANTLR start "rule__HyphenVersionRange__Group__4__Impl"
    // InternalSEMVER.g:945:1: rule__HyphenVersionRange__Group__4__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:949:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSEMVER.g:950:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSEMVER.g:950:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSEMVER.g:951:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:951:2: ( ( RULE_WS ) )
            // InternalSEMVER.g:952:3: ( RULE_WS )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            // InternalSEMVER.g:953:3: ( RULE_WS )
            // InternalSEMVER.g:953:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); 

            }

             after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 

            }

            // InternalSEMVER.g:956:2: ( ( RULE_WS )* )
            // InternalSEMVER.g:957:3: ( RULE_WS )*
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            // InternalSEMVER.g:958:3: ( RULE_WS )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_WS) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalSEMVER.g:958:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

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
    // InternalSEMVER.g:967:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:971:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSEMVER.g:972:2: rule__HyphenVersionRange__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__Group__5__Impl();

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
    // $ANTLR end "rule__HyphenVersionRange__Group__5"


    // $ANTLR start "rule__HyphenVersionRange__Group__5__Impl"
    // InternalSEMVER.g:978:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:982:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSEMVER.g:983:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSEMVER.g:983:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSEMVER.g:984:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            // InternalSEMVER.g:985:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSEMVER.g:985:3: rule__HyphenVersionRange__ToAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__HyphenVersionRange__ToAssignment_5();

            state._fsp--;


            }

             after(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 

            }


            }

        }
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
    // InternalSEMVER.g:994:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:998:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSEMVER.g:999:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__VersionRangeContraint__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__1();

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
    // $ANTLR end "rule__VersionRangeContraint__Group__0"


    // $ANTLR start "rule__VersionRangeContraint__Group__0__Impl"
    // InternalSEMVER.g:1006:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1010:1: ( ( () ) )
            // InternalSEMVER.g:1011:1: ( () )
            {
            // InternalSEMVER.g:1011:1: ( () )
            // InternalSEMVER.g:1012:2: ()
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            // InternalSEMVER.g:1013:2: ()
            // InternalSEMVER.g:1013:3: 
            {
            }

             after(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 

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
    // InternalSEMVER.g:1021:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1025:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSEMVER.g:1026:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
            {
            pushFollow(FOLLOW_9);
            rule__VersionRangeContraint__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__2();

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
    // $ANTLR end "rule__VersionRangeContraint__Group__1"


    // $ANTLR start "rule__VersionRangeContraint__Group__1__Impl"
    // InternalSEMVER.g:1033:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1037:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSEMVER.g:1038:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSEMVER.g:1038:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSEMVER.g:1039:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            // InternalSEMVER.g:1040:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSEMVER.g:1040:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__VersionConstraintsAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1048:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1052:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSEMVER.g:1053:2: rule__VersionRangeContraint__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group__2__Impl();

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
    // $ANTLR end "rule__VersionRangeContraint__Group__2"


    // $ANTLR start "rule__VersionRangeContraint__Group__2__Impl"
    // InternalSEMVER.g:1059:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1063:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSEMVER.g:1064:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSEMVER.g:1064:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSEMVER.g:1065:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
             before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            // InternalSEMVER.g:1066:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop16:
            do {
                int alt16=2;
                alt16 = dfa16.predict(input);
                switch (alt16) {
            	case 1 :
            	    // InternalSEMVER.g:1066:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__VersionRangeContraint__Group_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

             after(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 

            }


            }

        }
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
    // InternalSEMVER.g:1075:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1079:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSEMVER.g:1080:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
            {
            pushFollow(FOLLOW_8);
            rule__VersionRangeContraint__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group_2__1();

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
    // $ANTLR end "rule__VersionRangeContraint__Group_2__0"


    // $ANTLR start "rule__VersionRangeContraint__Group_2__0__Impl"
    // InternalSEMVER.g:1087:1: rule__VersionRangeContraint__Group_2__0__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1091:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSEMVER.g:1092:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSEMVER.g:1092:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSEMVER.g:1093:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:1093:2: ( ( RULE_WS ) )
            // InternalSEMVER.g:1094:3: ( RULE_WS )
            {
             before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            // InternalSEMVER.g:1095:3: ( RULE_WS )
            // InternalSEMVER.g:1095:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); 

            }

             after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 

            }

            // InternalSEMVER.g:1098:2: ( ( RULE_WS )* )
            // InternalSEMVER.g:1099:3: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            // InternalSEMVER.g:1100:3: ( RULE_WS )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==RULE_WS) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSEMVER.g:1100:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

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
    // InternalSEMVER.g:1109:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1113:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSEMVER.g:1114:2: rule__VersionRangeContraint__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__Group_2__1__Impl();

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
    // $ANTLR end "rule__VersionRangeContraint__Group_2__1"


    // $ANTLR start "rule__VersionRangeContraint__Group_2__1__Impl"
    // InternalSEMVER.g:1120:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1124:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSEMVER.g:1125:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSEMVER.g:1125:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSEMVER.g:1126:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            // InternalSEMVER.g:1127:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSEMVER.g:1127:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeContraint__VersionConstraintsAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1136:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1140:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSEMVER.g:1141:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSEMVER.g:1148:1: rule__SimpleVersion__Group__0__Impl : ( () ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1152:1: ( ( () ) )
            // InternalSEMVER.g:1153:1: ( () )
            {
            // InternalSEMVER.g:1153:1: ( () )
            // InternalSEMVER.g:1154:2: ()
            {
             before(grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0()); 
            // InternalSEMVER.g:1155:2: ()
            // InternalSEMVER.g:1155:3: 
            {
            }

             after(grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0()); 

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
    // InternalSEMVER.g:1163:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1167:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSEMVER.g:1168:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
            {
            pushFollow(FOLLOW_8);
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
    // InternalSEMVER.g:1175:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__Group_1__0 )* ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1179:1: ( ( ( rule__SimpleVersion__Group_1__0 )* ) )
            // InternalSEMVER.g:1180:1: ( ( rule__SimpleVersion__Group_1__0 )* )
            {
            // InternalSEMVER.g:1180:1: ( ( rule__SimpleVersion__Group_1__0 )* )
            // InternalSEMVER.g:1181:2: ( rule__SimpleVersion__Group_1__0 )*
            {
             before(grammarAccess.getSimpleVersionAccess().getGroup_1()); 
            // InternalSEMVER.g:1182:2: ( rule__SimpleVersion__Group_1__0 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=30 && LA18_0<=37)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalSEMVER.g:1182:3: rule__SimpleVersion__Group_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__SimpleVersion__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

             after(grammarAccess.getSimpleVersionAccess().getGroup_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1190:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1194:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSEMVER.g:1195:2: rule__SimpleVersion__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group__2__Impl();

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
    // InternalSEMVER.g:1201:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1205:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSEMVER.g:1206:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSEMVER.g:1206:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSEMVER.g:1207:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
             before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            // InternalSEMVER.g:1208:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSEMVER.g:1208:3: rule__SimpleVersion__NumberAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__NumberAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 

            }


            }

        }
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
    // InternalSEMVER.g:1217:1: rule__SimpleVersion__Group_1__0 : rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1 ;
    public final void rule__SimpleVersion__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1221:1: ( rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1 )
            // InternalSEMVER.g:1222:2: rule__SimpleVersion__Group_1__0__Impl rule__SimpleVersion__Group_1__1
            {
            pushFollow(FOLLOW_9);
            rule__SimpleVersion__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group_1__1();

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
    // $ANTLR end "rule__SimpleVersion__Group_1__0"


    // $ANTLR start "rule__SimpleVersion__Group_1__0__Impl"
    // InternalSEMVER.g:1229:1: rule__SimpleVersion__Group_1__0__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) ) ;
    public final void rule__SimpleVersion__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1233:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) ) )
            // InternalSEMVER.g:1234:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) )
            {
            // InternalSEMVER.g:1234:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1_0 ) )
            // InternalSEMVER.g:1235:2: ( rule__SimpleVersion__ComparatorsAssignment_1_0 )
            {
             before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1_0()); 
            // InternalSEMVER.g:1236:2: ( rule__SimpleVersion__ComparatorsAssignment_1_0 )
            // InternalSEMVER.g:1236:3: rule__SimpleVersion__ComparatorsAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__ComparatorsAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1244:1: rule__SimpleVersion__Group_1__1 : rule__SimpleVersion__Group_1__1__Impl ;
    public final void rule__SimpleVersion__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1248:1: ( rule__SimpleVersion__Group_1__1__Impl )
            // InternalSEMVER.g:1249:2: rule__SimpleVersion__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleVersion__Group_1__1__Impl();

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
    // $ANTLR end "rule__SimpleVersion__Group_1__1"


    // $ANTLR start "rule__SimpleVersion__Group_1__1__Impl"
    // InternalSEMVER.g:1255:1: rule__SimpleVersion__Group_1__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__SimpleVersion__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1259:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:1260:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:1260:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:1261:2: ( RULE_WS )*
            {
             before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_1_1()); 
            // InternalSEMVER.g:1262:2: ( RULE_WS )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_WS) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSEMVER.g:1262:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_1_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1271:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1275:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSEMVER.g:1276:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
            {
            pushFollow(FOLLOW_12);
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
    // InternalSEMVER.g:1283:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1287:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSEMVER.g:1288:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSEMVER.g:1288:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSEMVER.g:1289:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
             before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            // InternalSEMVER.g:1290:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSEMVER.g:1290:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSEMVER.g:1298:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1302:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSEMVER.g:1303:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
            {
            pushFollow(FOLLOW_12);
            rule__VersionNumber__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__2();

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
    // InternalSEMVER.g:1310:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1314:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSEMVER.g:1315:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSEMVER.g:1315:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSEMVER.g:1316:2: ( rule__VersionNumber__Group_1__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            // InternalSEMVER.g:1317:2: ( rule__VersionNumber__Group_1__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==40) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSEMVER.g:1317:3: rule__VersionNumber__Group_1__0
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


    // $ANTLR start "rule__VersionNumber__Group__2"
    // InternalSEMVER.g:1325:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1329:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSEMVER.g:1330:2: rule__VersionNumber__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group__2__Impl();

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
    // $ANTLR end "rule__VersionNumber__Group__2"


    // $ANTLR start "rule__VersionNumber__Group__2__Impl"
    // InternalSEMVER.g:1336:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1340:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSEMVER.g:1341:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSEMVER.g:1341:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSEMVER.g:1342:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
             before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            // InternalSEMVER.g:1343:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==39||LA21_0==41) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSEMVER.g:1343:3: rule__VersionNumber__QualifierAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionNumber__QualifierAssignment_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 

            }


            }

        }
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
    // InternalSEMVER.g:1352:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1356:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSEMVER.g:1357:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSEMVER.g:1364:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1368:1: ( ( '.' ) )
            // InternalSEMVER.g:1369:1: ( '.' )
            {
            // InternalSEMVER.g:1369:1: ( '.' )
            // InternalSEMVER.g:1370:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            match(input,40,FOLLOW_2); 
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
    // InternalSEMVER.g:1379:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1383:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSEMVER.g:1384:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
            {
            pushFollow(FOLLOW_13);
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
    // InternalSEMVER.g:1391:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1395:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSEMVER.g:1396:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSEMVER.g:1396:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSEMVER.g:1397:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            // InternalSEMVER.g:1398:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSEMVER.g:1398:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSEMVER.g:1406:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1410:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSEMVER.g:1411:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSEMVER.g:1417:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1421:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSEMVER.g:1422:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSEMVER.g:1422:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSEMVER.g:1423:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            // InternalSEMVER.g:1424:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==40) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSEMVER.g:1424:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSEMVER.g:1433:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1437:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSEMVER.g:1438:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSEMVER.g:1445:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1449:1: ( ( '.' ) )
            // InternalSEMVER.g:1450:1: ( '.' )
            {
            // InternalSEMVER.g:1450:1: ( '.' )
            // InternalSEMVER.g:1451:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            match(input,40,FOLLOW_2); 
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
    // InternalSEMVER.g:1460:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1464:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSEMVER.g:1465:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
            {
            pushFollow(FOLLOW_13);
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
    // InternalSEMVER.g:1472:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1476:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSEMVER.g:1477:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSEMVER.g:1477:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSEMVER.g:1478:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            // InternalSEMVER.g:1479:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSEMVER.g:1479:3: rule__VersionNumber__PatchAssignment_1_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__PatchAssignment_1_2_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1487:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1491:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSEMVER.g:1492:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSEMVER.g:1498:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1502:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSEMVER.g:1503:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSEMVER.g:1503:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSEMVER.g:1504:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            // InternalSEMVER.g:1505:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==40) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalSEMVER.g:1505:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_14);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

             after(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 

            }


            }

        }
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
    // InternalSEMVER.g:1514:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1518:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSEMVER.g:1519:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
            {
            pushFollow(FOLLOW_8);
            rule__VersionNumber__Group_1_2_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2_2__1();

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
    // $ANTLR end "rule__VersionNumber__Group_1_2_2__0"


    // $ANTLR start "rule__VersionNumber__Group_1_2_2__0__Impl"
    // InternalSEMVER.g:1526:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1530:1: ( ( '.' ) )
            // InternalSEMVER.g:1531:1: ( '.' )
            {
            // InternalSEMVER.g:1531:1: ( '.' )
            // InternalSEMVER.g:1532:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            match(input,40,FOLLOW_2); 
             after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1541:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1545:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSEMVER.g:1546:2: rule__VersionNumber__Group_1_2_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__Group_1_2_2__1__Impl();

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
    // $ANTLR end "rule__VersionNumber__Group_1_2_2__1"


    // $ANTLR start "rule__VersionNumber__Group_1_2_2__1__Impl"
    // InternalSEMVER.g:1552:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1556:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSEMVER.g:1557:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSEMVER.g:1557:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSEMVER.g:1558:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            // InternalSEMVER.g:1559:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSEMVER.g:1559:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionNumber__ExtendedAssignment_1_2_2_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1568:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1572:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSEMVER.g:1573:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
            {
            pushFollow(FOLLOW_15);
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
    // InternalSEMVER.g:1580:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1584:1: ( ( '-' ) )
            // InternalSEMVER.g:1585:1: ( '-' )
            {
            // InternalSEMVER.g:1585:1: ( '-' )
            // InternalSEMVER.g:1586:2: '-'
            {
             before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            match(input,39,FOLLOW_2); 
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
    // InternalSEMVER.g:1595:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1599:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSEMVER.g:1600:2: rule__Qualifier__Group_0__1__Impl
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
    // InternalSEMVER.g:1606:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1610:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSEMVER.g:1611:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSEMVER.g:1611:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSEMVER.g:1612:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            // InternalSEMVER.g:1613:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSEMVER.g:1613:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSEMVER.g:1622:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1626:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSEMVER.g:1627:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
            {
            pushFollow(FOLLOW_15);
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
    // InternalSEMVER.g:1634:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1638:1: ( ( '+' ) )
            // InternalSEMVER.g:1639:1: ( '+' )
            {
            // InternalSEMVER.g:1639:1: ( '+' )
            // InternalSEMVER.g:1640:2: '+'
            {
             before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            match(input,41,FOLLOW_2); 
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
    // InternalSEMVER.g:1649:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1653:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSEMVER.g:1654:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSEMVER.g:1660:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1664:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSEMVER.g:1665:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSEMVER.g:1665:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSEMVER.g:1666:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            // InternalSEMVER.g:1667:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSEMVER.g:1667:3: rule__Qualifier__BuildMetadataAssignment_1_1
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


    // $ANTLR start "rule__Qualifier__Group_2__0"
    // InternalSEMVER.g:1676:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1680:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSEMVER.g:1681:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
            {
            pushFollow(FOLLOW_15);
            rule__Qualifier__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__1();

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
    // $ANTLR end "rule__Qualifier__Group_2__0"


    // $ANTLR start "rule__Qualifier__Group_2__0__Impl"
    // InternalSEMVER.g:1688:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1692:1: ( ( '-' ) )
            // InternalSEMVER.g:1693:1: ( '-' )
            {
            // InternalSEMVER.g:1693:1: ( '-' )
            // InternalSEMVER.g:1694:2: '-'
            {
             before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 
            match(input,39,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1703:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1707:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSEMVER.g:1708:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
            {
            pushFollow(FOLLOW_16);
            rule__Qualifier__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__2();

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
    // $ANTLR end "rule__Qualifier__Group_2__1"


    // $ANTLR start "rule__Qualifier__Group_2__1__Impl"
    // InternalSEMVER.g:1715:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1719:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSEMVER.g:1720:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSEMVER.g:1720:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSEMVER.g:1721:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            // InternalSEMVER.g:1722:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSEMVER.g:1722:3: rule__Qualifier__PreReleaseAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__PreReleaseAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1730:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1734:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSEMVER.g:1735:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
            {
            pushFollow(FOLLOW_15);
            rule__Qualifier__Group_2__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__3();

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
    // $ANTLR end "rule__Qualifier__Group_2__2"


    // $ANTLR start "rule__Qualifier__Group_2__2__Impl"
    // InternalSEMVER.g:1742:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1746:1: ( ( '+' ) )
            // InternalSEMVER.g:1747:1: ( '+' )
            {
            // InternalSEMVER.g:1747:1: ( '+' )
            // InternalSEMVER.g:1748:2: '+'
            {
             before(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 
            match(input,41,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 

            }


            }

        }
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
    // InternalSEMVER.g:1757:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1761:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSEMVER.g:1762:2: rule__Qualifier__Group_2__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__Group_2__3__Impl();

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
    // $ANTLR end "rule__Qualifier__Group_2__3"


    // $ANTLR start "rule__Qualifier__Group_2__3__Impl"
    // InternalSEMVER.g:1768:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1772:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSEMVER.g:1773:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSEMVER.g:1773:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSEMVER.g:1774:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            // InternalSEMVER.g:1775:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSEMVER.g:1775:3: rule__Qualifier__BuildMetadataAssignment_2_3
            {
            pushFollow(FOLLOW_2);
            rule__Qualifier__BuildMetadataAssignment_2_3();

            state._fsp--;


            }

             after(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 

            }


            }

        }
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
    // InternalSEMVER.g:1784:1: rule__QualifierTag__Group__0 : rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 ;
    public final void rule__QualifierTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1788:1: ( rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1 )
            // InternalSEMVER.g:1789:2: rule__QualifierTag__Group__0__Impl rule__QualifierTag__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__QualifierTag__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group__1();

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
    // $ANTLR end "rule__QualifierTag__Group__0"


    // $ANTLR start "rule__QualifierTag__Group__0__Impl"
    // InternalSEMVER.g:1796:1: rule__QualifierTag__Group__0__Impl : ( ( rule__QualifierTag__PartsAssignment_0 ) ) ;
    public final void rule__QualifierTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1800:1: ( ( ( rule__QualifierTag__PartsAssignment_0 ) ) )
            // InternalSEMVER.g:1801:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            {
            // InternalSEMVER.g:1801:1: ( ( rule__QualifierTag__PartsAssignment_0 ) )
            // InternalSEMVER.g:1802:2: ( rule__QualifierTag__PartsAssignment_0 )
            {
             before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 
            // InternalSEMVER.g:1803:2: ( rule__QualifierTag__PartsAssignment_0 )
            // InternalSEMVER.g:1803:3: rule__QualifierTag__PartsAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__PartsAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1811:1: rule__QualifierTag__Group__1 : rule__QualifierTag__Group__1__Impl ;
    public final void rule__QualifierTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1815:1: ( rule__QualifierTag__Group__1__Impl )
            // InternalSEMVER.g:1816:2: rule__QualifierTag__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group__1__Impl();

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
    // $ANTLR end "rule__QualifierTag__Group__1"


    // $ANTLR start "rule__QualifierTag__Group__1__Impl"
    // InternalSEMVER.g:1822:1: rule__QualifierTag__Group__1__Impl : ( ( rule__QualifierTag__Group_1__0 )* ) ;
    public final void rule__QualifierTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1826:1: ( ( ( rule__QualifierTag__Group_1__0 )* ) )
            // InternalSEMVER.g:1827:1: ( ( rule__QualifierTag__Group_1__0 )* )
            {
            // InternalSEMVER.g:1827:1: ( ( rule__QualifierTag__Group_1__0 )* )
            // InternalSEMVER.g:1828:2: ( rule__QualifierTag__Group_1__0 )*
            {
             before(grammarAccess.getQualifierTagAccess().getGroup_1()); 
            // InternalSEMVER.g:1829:2: ( rule__QualifierTag__Group_1__0 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==40) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalSEMVER.g:1829:3: rule__QualifierTag__Group_1__0
            	    {
            	    pushFollow(FOLLOW_14);
            	    rule__QualifierTag__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

             after(grammarAccess.getQualifierTagAccess().getGroup_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1838:1: rule__QualifierTag__Group_1__0 : rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 ;
    public final void rule__QualifierTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1842:1: ( rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1 )
            // InternalSEMVER.g:1843:2: rule__QualifierTag__Group_1__0__Impl rule__QualifierTag__Group_1__1
            {
            pushFollow(FOLLOW_15);
            rule__QualifierTag__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group_1__1();

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
    // $ANTLR end "rule__QualifierTag__Group_1__0"


    // $ANTLR start "rule__QualifierTag__Group_1__0__Impl"
    // InternalSEMVER.g:1850:1: rule__QualifierTag__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifierTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1854:1: ( ( '.' ) )
            // InternalSEMVER.g:1855:1: ( '.' )
            {
            // InternalSEMVER.g:1855:1: ( '.' )
            // InternalSEMVER.g:1856:2: '.'
            {
             before(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 
            match(input,40,FOLLOW_2); 
             after(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1865:1: rule__QualifierTag__Group_1__1 : rule__QualifierTag__Group_1__1__Impl ;
    public final void rule__QualifierTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1869:1: ( rule__QualifierTag__Group_1__1__Impl )
            // InternalSEMVER.g:1870:2: rule__QualifierTag__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__Group_1__1__Impl();

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
    // $ANTLR end "rule__QualifierTag__Group_1__1"


    // $ANTLR start "rule__QualifierTag__Group_1__1__Impl"
    // InternalSEMVER.g:1876:1: rule__QualifierTag__Group_1__1__Impl : ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) ;
    public final void rule__QualifierTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1880:1: ( ( ( rule__QualifierTag__PartsAssignment_1_1 ) ) )
            // InternalSEMVER.g:1881:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            {
            // InternalSEMVER.g:1881:1: ( ( rule__QualifierTag__PartsAssignment_1_1 ) )
            // InternalSEMVER.g:1882:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            {
             before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 
            // InternalSEMVER.g:1883:2: ( rule__QualifierTag__PartsAssignment_1_1 )
            // InternalSEMVER.g:1883:3: rule__QualifierTag__PartsAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__QualifierTag__PartsAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__0"
    // InternalSEMVER.g:1892:1: rule__ALPHA_NUMERIC_CHAR__Group__0 : rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1896:1: ( rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1 )
            // InternalSEMVER.g:1897:2: rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1
            {
            pushFollow(FOLLOW_15);
            rule__ALPHA_NUMERIC_CHAR__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__1();

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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__0"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__0__Impl"
    // InternalSEMVER.g:1904:1: rule__ALPHA_NUMERIC_CHAR__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1908:1: ( ( ( '-' )? ) )
            // InternalSEMVER.g:1909:1: ( ( '-' )? )
            {
            // InternalSEMVER.g:1909:1: ( ( '-' )? )
            // InternalSEMVER.g:1910:2: ( '-' )?
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0()); 
            // InternalSEMVER.g:1911:2: ( '-' )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==39) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSEMVER.g:1911:3: '-'
                    {
                    match(input,39,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1919:1: rule__ALPHA_NUMERIC_CHAR__Group__1 : rule__ALPHA_NUMERIC_CHAR__Group__1__Impl ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1923:1: ( rule__ALPHA_NUMERIC_CHAR__Group__1__Impl )
            // InternalSEMVER.g:1924:2: rule__ALPHA_NUMERIC_CHAR__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__1__Impl();

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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__1"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__1__Impl"
    // InternalSEMVER.g:1930:1: rule__ALPHA_NUMERIC_CHAR__Group__1__Impl : ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) ) ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1934:1: ( ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) ) )
            // InternalSEMVER.g:1935:1: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) )
            {
            // InternalSEMVER.g:1935:1: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 ) )
            // InternalSEMVER.g:1936:2: ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 )
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives_1()); 
            // InternalSEMVER.g:1937:2: ( rule__ALPHA_NUMERIC_CHAR__Alternatives_1 )
            // InternalSEMVER.g:1937:3: rule__ALPHA_NUMERIC_CHAR__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Alternatives_1();

            state._fsp--;


            }

             after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__VersionRangeSet__RangesAssignment_2_0"
    // InternalSEMVER.g:1946:1: rule__VersionRangeSet__RangesAssignment_2_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1950:1: ( ( ruleVersionRange ) )
            // InternalSEMVER.g:1951:2: ( ruleVersionRange )
            {
            // InternalSEMVER.g:1951:2: ( ruleVersionRange )
            // InternalSEMVER.g:1952:3: ruleVersionRange
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;

             after(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__RangesAssignment_2_0"


    // $ANTLR start "rule__VersionRangeSet__RangesAssignment_2_1_3"
    // InternalSEMVER.g:1961:1: rule__VersionRangeSet__RangesAssignment_2_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_2_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1965:1: ( ( ruleVersionRange ) )
            // InternalSEMVER.g:1966:2: ( ruleVersionRange )
            {
            // InternalSEMVER.g:1966:2: ( ruleVersionRange )
            // InternalSEMVER.g:1967:3: ruleVersionRange
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_1_3_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;

             after(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_1_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__RangesAssignment_2_1_3"


    // $ANTLR start "rule__HyphenVersionRange__FromAssignment_1"
    // InternalSEMVER.g:1976:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1980:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1981:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1981:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1982:3: ruleVersionNumber
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


    // $ANTLR start "rule__HyphenVersionRange__ToAssignment_5"
    // InternalSEMVER.g:1991:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1995:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1996:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1996:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1997:3: ruleVersionNumber
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;

             after(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2006:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2010:1: ( ( ruleSimpleVersion ) )
            // InternalSEMVER.g:2011:2: ( ruleSimpleVersion )
            {
            // InternalSEMVER.g:2011:2: ( ruleSimpleVersion )
            // InternalSEMVER.g:2012:3: ruleSimpleVersion
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;

             after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2021:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2025:1: ( ( ruleSimpleVersion ) )
            // InternalSEMVER.g:2026:2: ( ruleSimpleVersion )
            {
            // InternalSEMVER.g:2026:2: ( ruleSimpleVersion )
            // InternalSEMVER.g:2027:3: ruleSimpleVersion
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleVersion();

            state._fsp--;

             after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2036:1: rule__SimpleVersion__ComparatorsAssignment_1_0 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2040:1: ( ( ruleVersionComparator ) )
            // InternalSEMVER.g:2041:2: ( ruleVersionComparator )
            {
            // InternalSEMVER.g:2041:2: ( ruleVersionComparator )
            // InternalSEMVER.g:2042:3: ruleVersionComparator
            {
             before(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionComparator();

            state._fsp--;

             after(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2051:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2055:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:2056:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:2056:2: ( ruleVersionNumber )
            // InternalSEMVER.g:2057:3: ruleVersionNumber
            {
             before(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionNumber();

            state._fsp--;

             after(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2066:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2070:1: ( ( ruleVersionPart ) )
            // InternalSEMVER.g:2071:2: ( ruleVersionPart )
            {
            // InternalSEMVER.g:2071:2: ( ruleVersionPart )
            // InternalSEMVER.g:2072:3: ruleVersionPart
            {
             before(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2081:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2085:1: ( ( ruleVersionPart ) )
            // InternalSEMVER.g:2086:2: ( ruleVersionPart )
            {
            // InternalSEMVER.g:2086:2: ( ruleVersionPart )
            // InternalSEMVER.g:2087:3: ruleVersionPart
            {
             before(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2096:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2100:1: ( ( ruleVersionPart ) )
            // InternalSEMVER.g:2101:2: ( ruleVersionPart )
            {
            // InternalSEMVER.g:2101:2: ( ruleVersionPart )
            // InternalSEMVER.g:2102:3: ruleVersionPart
            {
             before(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2111:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVersionPart ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2115:1: ( ( ruleVersionPart ) )
            // InternalSEMVER.g:2116:2: ( ruleVersionPart )
            {
            // InternalSEMVER.g:2116:2: ( ruleVersionPart )
            // InternalSEMVER.g:2117:3: ruleVersionPart
            {
             before(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionPart();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2126:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2130:1: ( ( ruleQualifier ) )
            // InternalSEMVER.g:2131:2: ( ruleQualifier )
            {
            // InternalSEMVER.g:2131:2: ( ruleQualifier )
            // InternalSEMVER.g:2132:3: ruleQualifier
            {
             before(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifier();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2141:1: rule__VersionPart__WildcardAssignment_0 : ( ruleWILDCARD ) ;
    public final void rule__VersionPart__WildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2145:1: ( ( ruleWILDCARD ) )
            // InternalSEMVER.g:2146:2: ( ruleWILDCARD )
            {
            // InternalSEMVER.g:2146:2: ( ruleWILDCARD )
            // InternalSEMVER.g:2147:3: ruleWILDCARD
            {
             before(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleWILDCARD();

            state._fsp--;

             after(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2156:1: rule__VersionPart__NumberRawAssignment_1 : ( RULE_DIGITS ) ;
    public final void rule__VersionPart__NumberRawAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2160:1: ( ( RULE_DIGITS ) )
            // InternalSEMVER.g:2161:2: ( RULE_DIGITS )
            {
            // InternalSEMVER.g:2161:2: ( RULE_DIGITS )
            // InternalSEMVER.g:2162:3: RULE_DIGITS
            {
             before(grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0()); 
            match(input,RULE_DIGITS,FOLLOW_2); 
             after(grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2171:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2175:1: ( ( ruleQualifierTag ) )
            // InternalSEMVER.g:2176:2: ( ruleQualifierTag )
            {
            // InternalSEMVER.g:2176:2: ( ruleQualifierTag )
            // InternalSEMVER.g:2177:3: ruleQualifierTag
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2186:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2190:1: ( ( ruleQualifierTag ) )
            // InternalSEMVER.g:2191:2: ( ruleQualifierTag )
            {
            // InternalSEMVER.g:2191:2: ( ruleQualifierTag )
            // InternalSEMVER.g:2192:3: ruleQualifierTag
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2201:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2205:1: ( ( ruleQualifierTag ) )
            // InternalSEMVER.g:2206:2: ( ruleQualifierTag )
            {
            // InternalSEMVER.g:2206:2: ( ruleQualifierTag )
            // InternalSEMVER.g:2207:3: ruleQualifierTag
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2216:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( ruleQualifierTag ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2220:1: ( ( ruleQualifierTag ) )
            // InternalSEMVER.g:2221:2: ( ruleQualifierTag )
            {
            // InternalSEMVER.g:2221:2: ( ruleQualifierTag )
            // InternalSEMVER.g:2222:3: ruleQualifierTag
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_2_3_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifierTag();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_2_3_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2231:1: rule__QualifierTag__PartsAssignment_0 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2235:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:2236:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:2236:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:2237:3: ruleALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             after(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:2246:1: rule__QualifierTag__PartsAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__QualifierTag__PartsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:2250:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:2251:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:2251:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:2252:3: ruleALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             after(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 

            }


            }

        }
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

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA16 dfa16 = new DFA16(this);
    static final String dfa_1s = "\102\uffff";
    static final String dfa_2s = "\2\uffff\4\1\3\uffff\5\1\1\uffff\2\1\1\uffff\3\1\3\uffff\2\1\3\uffff\2\1\1\uffff\4\1\1\uffff\2\1\1\uffff\2\1\1\uffff\2\1\2\uffff\2\1\1\uffff\2\1\2\uffff\6\1\1\uffff\2\1\1\uffff\2\1";
    static final String dfa_3s = "\1\4\1\uffff\4\6\4\4\4\6\7\4\1\uffff\12\4\4\6\24\4\4\6\6\4";
    static final String dfa_4s = "\1\45\1\uffff\4\51\1\35\3\47\4\51\1\5\2\51\1\5\2\50\1\47\1\uffff\1\35\1\5\2\51\2\47\1\5\2\50\1\47\4\51\1\5\2\51\1\5\2\50\1\5\2\50\1\35\1\5\2\51\1\5\2\50\1\47\1\5\2\50\4\51\1\5\2\50\1\5\2\50";
    static final String dfa_5s = "\1\uffff\1\1\23\uffff\1\2\54\uffff";
    static final String dfa_6s = "\102\uffff}>";
    static final String[] dfa_7s = {
            "\1\5\26\uffff\1\2\1\3\1\4\10\1",
            "",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\15\26\uffff\1\12\1\13\1\14",
            "\1\17\1\20\41\uffff\1\16",
            "\1\22\1\23\41\uffff\1\21",
            "\1\1\1\uffff\1\24\24\uffff\14\1\1\25",
            "\1\11\37\uffff\1\1\1\7\1\26\1\10",
            "\1\11\37\uffff\1\1\1\7\1\26\1\10",
            "\1\11\37\uffff\1\1\1\7\1\26\1\10",
            "\1\11\37\uffff\1\1\1\7\1\26\1\10",
            "\1\17\1\20",
            "\1\30\1\31\1\11\37\uffff\1\1\1\27\1\32\1\33",
            "\1\30\1\31\1\11\37\uffff\1\1\1\27\1\32\1\33",
            "\1\22\1\23",
            "\1\35\1\36\1\11\37\uffff\1\1\1\34\1\37",
            "\1\35\1\36\1\11\37\uffff\1\1\1\34\1\37",
            "\1\1\1\uffff\1\24\24\uffff\14\1\1\25",
            "",
            "\1\43\26\uffff\1\40\1\41\1\42",
            "\1\30\1\31",
            "\1\30\1\31\1\11\37\uffff\1\1\1\27\1\32\1\33",
            "\1\30\1\31\1\11\37\uffff\1\1\1\27\1\32\1\33",
            "\1\45\1\46\41\uffff\1\44",
            "\1\50\1\51\41\uffff\1\47",
            "\1\35\1\36",
            "\1\35\1\36\1\11\37\uffff\1\1\1\34\1\37",
            "\1\35\1\36\1\11\37\uffff\1\1\1\34\1\37",
            "\1\53\1\54\41\uffff\1\52",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\45\1\46",
            "\1\57\1\60\1\11\37\uffff\1\1\1\56\1\32\1\33",
            "\1\57\1\60\1\11\37\uffff\1\1\1\56\1\32\1\33",
            "\1\50\1\51",
            "\1\62\1\63\1\11\37\uffff\1\1\1\61\1\64",
            "\1\62\1\63\1\11\37\uffff\1\1\1\61\1\64",
            "\1\53\1\54",
            "\1\66\1\67\1\11\37\uffff\1\1\1\65\1\37",
            "\1\66\1\67\1\11\37\uffff\1\1\1\65\1\37",
            "\1\73\26\uffff\1\70\1\71\1\72",
            "\1\57\1\60",
            "\1\57\1\60\1\11\37\uffff\1\1\1\56\1\32\1\33",
            "\1\57\1\60\1\11\37\uffff\1\1\1\56\1\32\1\33",
            "\1\62\1\63",
            "\1\62\1\63\1\11\37\uffff\1\1\1\61\1\64",
            "\1\62\1\63\1\11\37\uffff\1\1\1\61\1\64",
            "\1\75\1\76\41\uffff\1\74",
            "\1\66\1\67",
            "\1\66\1\67\1\11\37\uffff\1\1\1\65\1\37",
            "\1\66\1\67\1\11\37\uffff\1\1\1\65\1\37",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\11\37\uffff\1\1\1\7\1\55\1\10",
            "\1\75\1\76",
            "\1\100\1\101\1\11\37\uffff\1\1\1\77\1\64",
            "\1\100\1\101\1\11\37\uffff\1\1\1\77\1\64",
            "\1\100\1\101",
            "\1\100\1\101\1\11\37\uffff\1\1\1\77\1\64",
            "\1\100\1\101\1\11\37\uffff\1\1\1\77\1\64"
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
            return "374:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
    static final String dfa_8s = "\22\uffff";
    static final String dfa_9s = "\4\uffff\2\13\1\uffff\2\13\4\uffff\2\13\1\uffff\2\13";
    static final String dfa_10s = "\1\47\1\4\1\uffff\7\4\2\uffff\6\4";
    static final String dfa_11s = "\1\51\1\47\1\uffff\1\5\2\51\1\5\2\51\1\47\2\uffff\1\5\2\51\1\5\2\51";
    static final String dfa_12s = "\2\uffff\1\2\7\uffff\1\3\1\1\6\uffff";
    static final String dfa_13s = "\22\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\1\uffff\1\2",
            "\1\4\1\5\41\uffff\1\3",
            "",
            "\1\4\1\5",
            "\1\7\1\10\1\13\37\uffff\1\13\1\6\1\11\1\12",
            "\1\7\1\10\1\13\37\uffff\1\13\1\6\1\11\1\12",
            "\1\7\1\10",
            "\1\7\1\10\1\13\37\uffff\1\13\1\6\1\11\1\12",
            "\1\7\1\10\1\13\37\uffff\1\13\1\6\1\11\1\12",
            "\1\15\1\16\41\uffff\1\14",
            "",
            "",
            "\1\15\1\16",
            "\1\20\1\21\1\13\37\uffff\1\13\1\17\1\11\1\12",
            "\1\20\1\21\1\13\37\uffff\1\13\1\17\1\11\1\12",
            "\1\20\1\21",
            "\1\20\1\21\1\13\37\uffff\1\13\1\17\1\11\1\12",
            "\1\20\1\21\1\13\37\uffff\1\13\1\17\1\11\1\12"
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
            return "416:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );";
        }
    }
    static final String dfa_15s = "\4\uffff";
    static final String dfa_16s = "\2\2\2\uffff";
    static final String dfa_17s = "\2\6\2\uffff";
    static final String dfa_18s = "\2\46\2\uffff";
    static final String dfa_19s = "\2\uffff\1\2\1\1";
    static final String dfa_20s = "\4\uffff}>";
    static final String[] dfa_21s = {
            "\1\1\37\uffff\1\3",
            "\1\1\37\uffff\1\3",
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

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "()* loopback of 675:2: ( rule__VersionRangeSet__Group_2_1__0 )*";
        }
    }
    static final String dfa_22s = "\5\uffff";
    static final String dfa_23s = "\1\1\1\uffff\2\1\1\uffff";
    static final String dfa_24s = "\1\6\1\uffff\2\4\1\uffff";
    static final String dfa_25s = "\1\46\1\uffff\2\46\1\uffff";
    static final String dfa_26s = "\1\uffff\1\2\2\uffff\1\1";
    static final String dfa_27s = "\5\uffff}>";
    static final String[] dfa_28s = {
            "\1\2\37\uffff\1\1",
            "",
            "\1\4\1\uffff\1\3\24\uffff\13\4\1\1",
            "\1\4\1\uffff\1\3\24\uffff\13\4\1\1",
            ""
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "()* loopback of 1066:2: ( rule__VersionRangeContraint__Group_2__0 )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000008000000032L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000003FF8000050L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000004000000040L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000004000000042L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000003FF8000010L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000003FC0000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000038000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000008000000030L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000020000000000L});

}