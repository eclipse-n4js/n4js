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


    // $ANTLR start "entryRuleQualifier"
    // InternalSEMVER.g:210:1: entryRuleQualifier : ruleQualifier EOF ;
    public final void entryRuleQualifier() throws RecognitionException {
        try {
            // InternalSEMVER.g:211:1: ( ruleQualifier EOF )
            // InternalSEMVER.g:212:1: ruleQualifier EOF
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
    // InternalSEMVER.g:219:1: ruleQualifier : ( ( rule__Qualifier__Alternatives ) ) ;
    public final void ruleQualifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:223:2: ( ( ( rule__Qualifier__Alternatives ) ) )
            // InternalSEMVER.g:224:2: ( ( rule__Qualifier__Alternatives ) )
            {
            // InternalSEMVER.g:224:2: ( ( rule__Qualifier__Alternatives ) )
            // InternalSEMVER.g:225:3: ( rule__Qualifier__Alternatives )
            {
             before(grammarAccess.getQualifierAccess().getAlternatives()); 
            // InternalSEMVER.g:226:3: ( rule__Qualifier__Alternatives )
            // InternalSEMVER.g:226:4: rule__Qualifier__Alternatives
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


    // $ANTLR start "entryRuleALPHA_NUMERIC_CHARS"
    // InternalSEMVER.g:235:1: entryRuleALPHA_NUMERIC_CHARS : ruleALPHA_NUMERIC_CHARS EOF ;
    public final void entryRuleALPHA_NUMERIC_CHARS() throws RecognitionException {
        try {
            // InternalSEMVER.g:236:1: ( ruleALPHA_NUMERIC_CHARS EOF )
            // InternalSEMVER.g:237:1: ruleALPHA_NUMERIC_CHARS EOF
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
    // InternalSEMVER.g:244:1: ruleALPHA_NUMERIC_CHARS : ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) ;
    public final void ruleALPHA_NUMERIC_CHARS() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:248:2: ( ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) ) )
            // InternalSEMVER.g:249:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            {
            // InternalSEMVER.g:249:2: ( ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* ) )
            // InternalSEMVER.g:250:3: ( ( ruleALPHA_NUMERIC_CHAR ) ) ( ( ruleALPHA_NUMERIC_CHAR )* )
            {
            // InternalSEMVER.g:250:3: ( ( ruleALPHA_NUMERIC_CHAR ) )
            // InternalSEMVER.g:251:4: ( ruleALPHA_NUMERIC_CHAR )
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            // InternalSEMVER.g:252:4: ( ruleALPHA_NUMERIC_CHAR )
            // InternalSEMVER.g:252:5: ruleALPHA_NUMERIC_CHAR
            {
            pushFollow(FOLLOW_3);
            ruleALPHA_NUMERIC_CHAR();

            state._fsp--;


            }

             after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 

            }

            // InternalSEMVER.g:255:3: ( ( ruleALPHA_NUMERIC_CHAR )* )
            // InternalSEMVER.g:256:4: ( ruleALPHA_NUMERIC_CHAR )*
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getALPHA_NUMERIC_CHARParserRuleCall()); 
            // InternalSEMVER.g:257:4: ( ruleALPHA_NUMERIC_CHAR )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_DIGITS && LA1_0<=RULE_LETTERS)||(LA1_0>=39 && LA1_0<=40)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSEMVER.g:257:5: ruleALPHA_NUMERIC_CHAR
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


    // $ANTLR start "entryRuleVERSION_PART"
    // InternalSEMVER.g:267:1: entryRuleVERSION_PART : ruleVERSION_PART EOF ;
    public final void entryRuleVERSION_PART() throws RecognitionException {
        try {
            // InternalSEMVER.g:268:1: ( ruleVERSION_PART EOF )
            // InternalSEMVER.g:269:1: ruleVERSION_PART EOF
            {
             before(grammarAccess.getVERSION_PARTRule()); 
            pushFollow(FOLLOW_1);
            ruleVERSION_PART();

            state._fsp--;

             after(grammarAccess.getVERSION_PARTRule()); 
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
    // $ANTLR end "entryRuleVERSION_PART"


    // $ANTLR start "ruleVERSION_PART"
    // InternalSEMVER.g:276:1: ruleVERSION_PART : ( ( rule__VERSION_PART__Alternatives ) ) ;
    public final void ruleVERSION_PART() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:280:2: ( ( ( rule__VERSION_PART__Alternatives ) ) )
            // InternalSEMVER.g:281:2: ( ( rule__VERSION_PART__Alternatives ) )
            {
            // InternalSEMVER.g:281:2: ( ( rule__VERSION_PART__Alternatives ) )
            // InternalSEMVER.g:282:3: ( rule__VERSION_PART__Alternatives )
            {
             before(grammarAccess.getVERSION_PARTAccess().getAlternatives()); 
            // InternalSEMVER.g:283:3: ( rule__VERSION_PART__Alternatives )
            // InternalSEMVER.g:283:4: rule__VERSION_PART__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VERSION_PART__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getVERSION_PARTAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVERSION_PART"


    // $ANTLR start "ruleALPHA_NUMERIC_CHAR"
    // InternalSEMVER.g:293:1: ruleALPHA_NUMERIC_CHAR : ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) ) ;
    public final void ruleALPHA_NUMERIC_CHAR() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:297:2: ( ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) ) )
            // InternalSEMVER.g:298:2: ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) )
            {
            // InternalSEMVER.g:298:2: ( ( rule__ALPHA_NUMERIC_CHAR__Group__0 ) )
            // InternalSEMVER.g:299:3: ( rule__ALPHA_NUMERIC_CHAR__Group__0 )
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getGroup()); 
            // InternalSEMVER.g:300:3: ( rule__ALPHA_NUMERIC_CHAR__Group__0 )
            // InternalSEMVER.g:300:4: rule__ALPHA_NUMERIC_CHAR__Group__0
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
    // InternalSEMVER.g:309:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:313:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSEMVER.g:314:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSEMVER.g:314:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSEMVER.g:315:3: ( rule__VersionComparator__Alternatives )
            {
             before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            // InternalSEMVER.g:316:3: ( rule__VersionComparator__Alternatives )
            // InternalSEMVER.g:316:4: rule__VersionComparator__Alternatives
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
    // InternalSEMVER.g:324:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:328:1: ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) )
            int alt2=2;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // InternalSEMVER.g:329:2: ( ruleVersionRangeContraint )
                    {
                    // InternalSEMVER.g:329:2: ( ruleVersionRangeContraint )
                    // InternalSEMVER.g:330:3: ruleVersionRangeContraint
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
                    // InternalSEMVER.g:335:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSEMVER.g:335:2: ( ruleHyphenVersionRange )
                    // InternalSEMVER.g:336:3: ruleHyphenVersionRange
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


    // $ANTLR start "rule__Qualifier__Alternatives"
    // InternalSEMVER.g:345:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:349:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt3=3;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // InternalSEMVER.g:350:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSEMVER.g:350:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSEMVER.g:351:3: ( rule__Qualifier__Group_0__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    // InternalSEMVER.g:352:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSEMVER.g:352:4: rule__Qualifier__Group_0__0
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
                    // InternalSEMVER.g:356:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSEMVER.g:356:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSEMVER.g:357:3: ( rule__Qualifier__Group_1__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    // InternalSEMVER.g:358:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSEMVER.g:358:4: rule__Qualifier__Group_1__0
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
                    // InternalSEMVER.g:362:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSEMVER.g:362:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSEMVER.g:363:3: ( rule__Qualifier__Group_2__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    // InternalSEMVER.g:364:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSEMVER.g:364:4: rule__Qualifier__Group_2__0
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


    // $ANTLR start "rule__VERSION_PART__Alternatives"
    // InternalSEMVER.g:372:1: rule__VERSION_PART__Alternatives : ( ( 'x' ) | ( 'X' ) | ( '*' ) | ( RULE_DIGITS ) );
    public final void rule__VERSION_PART__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:376:1: ( ( 'x' ) | ( 'X' ) | ( '*' ) | ( RULE_DIGITS ) )
            int alt4=4;
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
            case RULE_DIGITS:
                {
                alt4=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalSEMVER.g:377:2: ( 'x' )
                    {
                    // InternalSEMVER.g:377:2: ( 'x' )
                    // InternalSEMVER.g:378:3: 'x'
                    {
                     before(grammarAccess.getVERSION_PARTAccess().getXKeyword_0()); 
                    match(input,27,FOLLOW_2); 
                     after(grammarAccess.getVERSION_PARTAccess().getXKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:383:2: ( 'X' )
                    {
                    // InternalSEMVER.g:383:2: ( 'X' )
                    // InternalSEMVER.g:384:3: 'X'
                    {
                     before(grammarAccess.getVERSION_PARTAccess().getXKeyword_1()); 
                    match(input,28,FOLLOW_2); 
                     after(grammarAccess.getVERSION_PARTAccess().getXKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:389:2: ( '*' )
                    {
                    // InternalSEMVER.g:389:2: ( '*' )
                    // InternalSEMVER.g:390:3: '*'
                    {
                     before(grammarAccess.getVERSION_PARTAccess().getAsteriskKeyword_2()); 
                    match(input,29,FOLLOW_2); 
                     after(grammarAccess.getVERSION_PARTAccess().getAsteriskKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:395:2: ( RULE_DIGITS )
                    {
                    // InternalSEMVER.g:395:2: ( RULE_DIGITS )
                    // InternalSEMVER.g:396:3: RULE_DIGITS
                    {
                     before(grammarAccess.getVERSION_PARTAccess().getDIGITSTerminalRuleCall_3()); 
                    match(input,RULE_DIGITS,FOLLOW_2); 
                     after(grammarAccess.getVERSION_PARTAccess().getDIGITSTerminalRuleCall_3()); 

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
    // $ANTLR end "rule__VERSION_PART__Alternatives"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Alternatives_2"
    // InternalSEMVER.g:405:1: rule__ALPHA_NUMERIC_CHAR__Alternatives_2 : ( ( RULE_DIGITS ) | ( RULE_LETTERS ) );
    public final void rule__ALPHA_NUMERIC_CHAR__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:409:1: ( ( RULE_DIGITS ) | ( RULE_LETTERS ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_DIGITS) ) {
                alt5=1;
            }
            else if ( (LA5_0==RULE_LETTERS) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalSEMVER.g:410:2: ( RULE_DIGITS )
                    {
                    // InternalSEMVER.g:410:2: ( RULE_DIGITS )
                    // InternalSEMVER.g:411:3: RULE_DIGITS
                    {
                     before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_2_0()); 
                    match(input,RULE_DIGITS,FOLLOW_2); 
                     after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getDIGITSTerminalRuleCall_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:416:2: ( RULE_LETTERS )
                    {
                    // InternalSEMVER.g:416:2: ( RULE_LETTERS )
                    // InternalSEMVER.g:417:3: RULE_LETTERS
                    {
                     before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSTerminalRuleCall_2_1()); 
                    match(input,RULE_LETTERS,FOLLOW_2); 
                     after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getLETTERSTerminalRuleCall_2_1()); 

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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Alternatives_2"


    // $ANTLR start "rule__VersionComparator__Alternatives"
    // InternalSEMVER.g:426:1: rule__VersionComparator__Alternatives : ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:430:1: ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt6=8;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt6=1;
                }
                break;
            case 31:
                {
                alt6=2;
                }
                break;
            case 32:
                {
                alt6=3;
                }
                break;
            case 33:
                {
                alt6=4;
                }
                break;
            case 34:
                {
                alt6=5;
                }
                break;
            case 35:
                {
                alt6=6;
                }
                break;
            case 36:
                {
                alt6=7;
                }
                break;
            case 37:
                {
                alt6=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalSEMVER.g:431:2: ( ( 'v' ) )
                    {
                    // InternalSEMVER.g:431:2: ( ( 'v' ) )
                    // InternalSEMVER.g:432:3: ( 'v' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 
                    // InternalSEMVER.g:433:3: ( 'v' )
                    // InternalSEMVER.g:433:4: 'v'
                    {
                    match(input,30,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:437:2: ( ( '=' ) )
                    {
                    // InternalSEMVER.g:437:2: ( ( '=' ) )
                    // InternalSEMVER.g:438:3: ( '=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 
                    // InternalSEMVER.g:439:3: ( '=' )
                    // InternalSEMVER.g:439:4: '='
                    {
                    match(input,31,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:443:2: ( ( '<' ) )
                    {
                    // InternalSEMVER.g:443:2: ( ( '<' ) )
                    // InternalSEMVER.g:444:3: ( '<' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 
                    // InternalSEMVER.g:445:3: ( '<' )
                    // InternalSEMVER.g:445:4: '<'
                    {
                    match(input,32,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:449:2: ( ( '~' ) )
                    {
                    // InternalSEMVER.g:449:2: ( ( '~' ) )
                    // InternalSEMVER.g:450:3: ( '~' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 
                    // InternalSEMVER.g:451:3: ( '~' )
                    // InternalSEMVER.g:451:4: '~'
                    {
                    match(input,33,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalSEMVER.g:455:2: ( ( '^' ) )
                    {
                    // InternalSEMVER.g:455:2: ( ( '^' ) )
                    // InternalSEMVER.g:456:3: ( '^' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 
                    // InternalSEMVER.g:457:3: ( '^' )
                    // InternalSEMVER.g:457:4: '^'
                    {
                    match(input,34,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalSEMVER.g:461:2: ( ( '<=' ) )
                    {
                    // InternalSEMVER.g:461:2: ( ( '<=' ) )
                    // InternalSEMVER.g:462:3: ( '<=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 
                    // InternalSEMVER.g:463:3: ( '<=' )
                    // InternalSEMVER.g:463:4: '<='
                    {
                    match(input,35,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalSEMVER.g:467:2: ( ( '>' ) )
                    {
                    // InternalSEMVER.g:467:2: ( ( '>' ) )
                    // InternalSEMVER.g:468:3: ( '>' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 
                    // InternalSEMVER.g:469:3: ( '>' )
                    // InternalSEMVER.g:469:4: '>'
                    {
                    match(input,36,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalSEMVER.g:473:2: ( ( '>=' ) )
                    {
                    // InternalSEMVER.g:473:2: ( ( '>=' ) )
                    // InternalSEMVER.g:474:3: ( '>=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7()); 
                    // InternalSEMVER.g:475:3: ( '>=' )
                    // InternalSEMVER.g:475:4: '>='
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
    // InternalSEMVER.g:483:1: rule__VersionRangeSet__Group__0 : rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 ;
    public final void rule__VersionRangeSet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:487:1: ( rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 )
            // InternalSEMVER.g:488:2: rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1
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
    // InternalSEMVER.g:495:1: rule__VersionRangeSet__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:499:1: ( ( () ) )
            // InternalSEMVER.g:500:1: ( () )
            {
            // InternalSEMVER.g:500:1: ( () )
            // InternalSEMVER.g:501:2: ()
            {
             before(grammarAccess.getVersionRangeSetAccess().getVersionRangeSetAction_0()); 
            // InternalSEMVER.g:502:2: ()
            // InternalSEMVER.g:502:3: 
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
    // InternalSEMVER.g:510:1: rule__VersionRangeSet__Group__1 : rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2 ;
    public final void rule__VersionRangeSet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:514:1: ( rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2 )
            // InternalSEMVER.g:515:2: rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2
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
    // InternalSEMVER.g:522:1: rule__VersionRangeSet__Group__1__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:526:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:527:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:527:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:528:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_1()); 
            // InternalSEMVER.g:529:2: ( RULE_WS )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_WS) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSEMVER.g:529:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop7;
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
    // InternalSEMVER.g:537:1: rule__VersionRangeSet__Group__2 : rule__VersionRangeSet__Group__2__Impl ;
    public final void rule__VersionRangeSet__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:541:1: ( rule__VersionRangeSet__Group__2__Impl )
            // InternalSEMVER.g:542:2: rule__VersionRangeSet__Group__2__Impl
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
    // InternalSEMVER.g:548:1: rule__VersionRangeSet__Group__2__Impl : ( ( rule__VersionRangeSet__Group_2__0 )? ) ;
    public final void rule__VersionRangeSet__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:552:1: ( ( ( rule__VersionRangeSet__Group_2__0 )? ) )
            // InternalSEMVER.g:553:1: ( ( rule__VersionRangeSet__Group_2__0 )? )
            {
            // InternalSEMVER.g:553:1: ( ( rule__VersionRangeSet__Group_2__0 )? )
            // InternalSEMVER.g:554:2: ( rule__VersionRangeSet__Group_2__0 )?
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup_2()); 
            // InternalSEMVER.g:555:2: ( rule__VersionRangeSet__Group_2__0 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_DIGITS||(LA8_0>=27 && LA8_0<=37)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSEMVER.g:555:3: rule__VersionRangeSet__Group_2__0
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
    // InternalSEMVER.g:564:1: rule__VersionRangeSet__Group_2__0 : rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1 ;
    public final void rule__VersionRangeSet__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:568:1: ( rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1 )
            // InternalSEMVER.g:569:2: rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1
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
    // InternalSEMVER.g:576:1: rule__VersionRangeSet__Group_2__0__Impl : ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) ) ;
    public final void rule__VersionRangeSet__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:580:1: ( ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) ) )
            // InternalSEMVER.g:581:1: ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) )
            {
            // InternalSEMVER.g:581:1: ( ( rule__VersionRangeSet__RangesAssignment_2_0 ) )
            // InternalSEMVER.g:582:2: ( rule__VersionRangeSet__RangesAssignment_2_0 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_0()); 
            // InternalSEMVER.g:583:2: ( rule__VersionRangeSet__RangesAssignment_2_0 )
            // InternalSEMVER.g:583:3: rule__VersionRangeSet__RangesAssignment_2_0
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
    // InternalSEMVER.g:591:1: rule__VersionRangeSet__Group_2__1 : rule__VersionRangeSet__Group_2__1__Impl rule__VersionRangeSet__Group_2__2 ;
    public final void rule__VersionRangeSet__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:595:1: ( rule__VersionRangeSet__Group_2__1__Impl rule__VersionRangeSet__Group_2__2 )
            // InternalSEMVER.g:596:2: rule__VersionRangeSet__Group_2__1__Impl rule__VersionRangeSet__Group_2__2
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
    // InternalSEMVER.g:603:1: rule__VersionRangeSet__Group_2__1__Impl : ( ( rule__VersionRangeSet__Group_2_1__0 )* ) ;
    public final void rule__VersionRangeSet__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:607:1: ( ( ( rule__VersionRangeSet__Group_2_1__0 )* ) )
            // InternalSEMVER.g:608:1: ( ( rule__VersionRangeSet__Group_2_1__0 )* )
            {
            // InternalSEMVER.g:608:1: ( ( rule__VersionRangeSet__Group_2_1__0 )* )
            // InternalSEMVER.g:609:2: ( rule__VersionRangeSet__Group_2_1__0 )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup_2_1()); 
            // InternalSEMVER.g:610:2: ( rule__VersionRangeSet__Group_2_1__0 )*
            loop9:
            do {
                int alt9=2;
                alt9 = dfa9.predict(input);
                switch (alt9) {
            	case 1 :
            	    // InternalSEMVER.g:610:3: rule__VersionRangeSet__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__VersionRangeSet__Group_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
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
    // InternalSEMVER.g:618:1: rule__VersionRangeSet__Group_2__2 : rule__VersionRangeSet__Group_2__2__Impl ;
    public final void rule__VersionRangeSet__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:622:1: ( rule__VersionRangeSet__Group_2__2__Impl )
            // InternalSEMVER.g:623:2: rule__VersionRangeSet__Group_2__2__Impl
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
    // InternalSEMVER.g:629:1: rule__VersionRangeSet__Group_2__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:633:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:634:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:634:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:635:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_2()); 
            // InternalSEMVER.g:636:2: ( RULE_WS )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_WS) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSEMVER.g:636:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop10;
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
    // InternalSEMVER.g:645:1: rule__VersionRangeSet__Group_2_1__0 : rule__VersionRangeSet__Group_2_1__0__Impl rule__VersionRangeSet__Group_2_1__1 ;
    public final void rule__VersionRangeSet__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:649:1: ( rule__VersionRangeSet__Group_2_1__0__Impl rule__VersionRangeSet__Group_2_1__1 )
            // InternalSEMVER.g:650:2: rule__VersionRangeSet__Group_2_1__0__Impl rule__VersionRangeSet__Group_2_1__1
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
    // InternalSEMVER.g:657:1: rule__VersionRangeSet__Group_2_1__0__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:661:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:662:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:662:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:663:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_0()); 
            // InternalSEMVER.g:664:2: ( RULE_WS )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_WS) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalSEMVER.g:664:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop11;
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
    // InternalSEMVER.g:672:1: rule__VersionRangeSet__Group_2_1__1 : rule__VersionRangeSet__Group_2_1__1__Impl rule__VersionRangeSet__Group_2_1__2 ;
    public final void rule__VersionRangeSet__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:676:1: ( rule__VersionRangeSet__Group_2_1__1__Impl rule__VersionRangeSet__Group_2_1__2 )
            // InternalSEMVER.g:677:2: rule__VersionRangeSet__Group_2_1__1__Impl rule__VersionRangeSet__Group_2_1__2
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
    // InternalSEMVER.g:684:1: rule__VersionRangeSet__Group_2_1__1__Impl : ( '||' ) ;
    public final void rule__VersionRangeSet__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:688:1: ( ( '||' ) )
            // InternalSEMVER.g:689:1: ( '||' )
            {
            // InternalSEMVER.g:689:1: ( '||' )
            // InternalSEMVER.g:690:2: '||'
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
    // InternalSEMVER.g:699:1: rule__VersionRangeSet__Group_2_1__2 : rule__VersionRangeSet__Group_2_1__2__Impl rule__VersionRangeSet__Group_2_1__3 ;
    public final void rule__VersionRangeSet__Group_2_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:703:1: ( rule__VersionRangeSet__Group_2_1__2__Impl rule__VersionRangeSet__Group_2_1__3 )
            // InternalSEMVER.g:704:2: rule__VersionRangeSet__Group_2_1__2__Impl rule__VersionRangeSet__Group_2_1__3
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
    // InternalSEMVER.g:711:1: rule__VersionRangeSet__Group_2_1__2__Impl : ( ( RULE_WS )* ) ;
    public final void rule__VersionRangeSet__Group_2_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:715:1: ( ( ( RULE_WS )* ) )
            // InternalSEMVER.g:716:1: ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:716:1: ( ( RULE_WS )* )
            // InternalSEMVER.g:717:2: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getWSTerminalRuleCall_2_1_2()); 
            // InternalSEMVER.g:718:2: ( RULE_WS )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_WS) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSEMVER.g:718:3: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop12;
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
    // InternalSEMVER.g:726:1: rule__VersionRangeSet__Group_2_1__3 : rule__VersionRangeSet__Group_2_1__3__Impl ;
    public final void rule__VersionRangeSet__Group_2_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:730:1: ( rule__VersionRangeSet__Group_2_1__3__Impl )
            // InternalSEMVER.g:731:2: rule__VersionRangeSet__Group_2_1__3__Impl
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
    // InternalSEMVER.g:737:1: rule__VersionRangeSet__Group_2_1__3__Impl : ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) ) ;
    public final void rule__VersionRangeSet__Group_2_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:741:1: ( ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) ) )
            // InternalSEMVER.g:742:1: ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) )
            {
            // InternalSEMVER.g:742:1: ( ( rule__VersionRangeSet__RangesAssignment_2_1_3 ) )
            // InternalSEMVER.g:743:2: ( rule__VersionRangeSet__RangesAssignment_2_1_3 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_1_3()); 
            // InternalSEMVER.g:744:2: ( rule__VersionRangeSet__RangesAssignment_2_1_3 )
            // InternalSEMVER.g:744:3: rule__VersionRangeSet__RangesAssignment_2_1_3
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
    // InternalSEMVER.g:753:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:757:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSEMVER.g:758:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
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
    // InternalSEMVER.g:765:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:769:1: ( ( () ) )
            // InternalSEMVER.g:770:1: ( () )
            {
            // InternalSEMVER.g:770:1: ( () )
            // InternalSEMVER.g:771:2: ()
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            // InternalSEMVER.g:772:2: ()
            // InternalSEMVER.g:772:3: 
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
    // InternalSEMVER.g:780:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:784:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSEMVER.g:785:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
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
    // InternalSEMVER.g:792:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:796:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSEMVER.g:797:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSEMVER.g:797:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSEMVER.g:798:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            // InternalSEMVER.g:799:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSEMVER.g:799:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSEMVER.g:807:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:811:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSEMVER.g:812:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
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
    // InternalSEMVER.g:819:1: rule__HyphenVersionRange__Group__2__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:823:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSEMVER.g:824:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSEMVER.g:824:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSEMVER.g:825:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:825:2: ( ( RULE_WS ) )
            // InternalSEMVER.g:826:3: ( RULE_WS )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            // InternalSEMVER.g:827:3: ( RULE_WS )
            // InternalSEMVER.g:827:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); 

            }

             after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 

            }

            // InternalSEMVER.g:830:2: ( ( RULE_WS )* )
            // InternalSEMVER.g:831:3: ( RULE_WS )*
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); 
            // InternalSEMVER.g:832:3: ( RULE_WS )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_WS) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalSEMVER.g:832:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop13;
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
    // InternalSEMVER.g:841:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:845:1: ( rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4 )
            // InternalSEMVER.g:846:2: rule__HyphenVersionRange__Group__3__Impl rule__HyphenVersionRange__Group__4
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
    // InternalSEMVER.g:853:1: rule__HyphenVersionRange__Group__3__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:857:1: ( ( '-' ) )
            // InternalSEMVER.g:858:1: ( '-' )
            {
            // InternalSEMVER.g:858:1: ( '-' )
            // InternalSEMVER.g:859:2: '-'
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
    // InternalSEMVER.g:868:1: rule__HyphenVersionRange__Group__4 : rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 ;
    public final void rule__HyphenVersionRange__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:872:1: ( rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5 )
            // InternalSEMVER.g:873:2: rule__HyphenVersionRange__Group__4__Impl rule__HyphenVersionRange__Group__5
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
    // InternalSEMVER.g:880:1: rule__HyphenVersionRange__Group__4__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__HyphenVersionRange__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:884:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSEMVER.g:885:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSEMVER.g:885:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSEMVER.g:886:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:886:2: ( ( RULE_WS ) )
            // InternalSEMVER.g:887:3: ( RULE_WS )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            // InternalSEMVER.g:888:3: ( RULE_WS )
            // InternalSEMVER.g:888:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); 

            }

             after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 

            }

            // InternalSEMVER.g:891:2: ( ( RULE_WS )* )
            // InternalSEMVER.g:892:3: ( RULE_WS )*
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); 
            // InternalSEMVER.g:893:3: ( RULE_WS )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_WS) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSEMVER.g:893:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop14;
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
    // InternalSEMVER.g:902:1: rule__HyphenVersionRange__Group__5 : rule__HyphenVersionRange__Group__5__Impl ;
    public final void rule__HyphenVersionRange__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:906:1: ( rule__HyphenVersionRange__Group__5__Impl )
            // InternalSEMVER.g:907:2: rule__HyphenVersionRange__Group__5__Impl
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
    // InternalSEMVER.g:913:1: rule__HyphenVersionRange__Group__5__Impl : ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) ;
    public final void rule__HyphenVersionRange__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:917:1: ( ( ( rule__HyphenVersionRange__ToAssignment_5 ) ) )
            // InternalSEMVER.g:918:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            {
            // InternalSEMVER.g:918:1: ( ( rule__HyphenVersionRange__ToAssignment_5 ) )
            // InternalSEMVER.g:919:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); 
            // InternalSEMVER.g:920:2: ( rule__HyphenVersionRange__ToAssignment_5 )
            // InternalSEMVER.g:920:3: rule__HyphenVersionRange__ToAssignment_5
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
    // InternalSEMVER.g:929:1: rule__VersionRangeContraint__Group__0 : rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 ;
    public final void rule__VersionRangeContraint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:933:1: ( rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1 )
            // InternalSEMVER.g:934:2: rule__VersionRangeContraint__Group__0__Impl rule__VersionRangeContraint__Group__1
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
    // InternalSEMVER.g:941:1: rule__VersionRangeContraint__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeContraint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:945:1: ( ( () ) )
            // InternalSEMVER.g:946:1: ( () )
            {
            // InternalSEMVER.g:946:1: ( () )
            // InternalSEMVER.g:947:2: ()
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); 
            // InternalSEMVER.g:948:2: ()
            // InternalSEMVER.g:948:3: 
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
    // InternalSEMVER.g:956:1: rule__VersionRangeContraint__Group__1 : rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 ;
    public final void rule__VersionRangeContraint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:960:1: ( rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2 )
            // InternalSEMVER.g:961:2: rule__VersionRangeContraint__Group__1__Impl rule__VersionRangeContraint__Group__2
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
    // InternalSEMVER.g:968:1: rule__VersionRangeContraint__Group__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) ;
    public final void rule__VersionRangeContraint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:972:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) ) )
            // InternalSEMVER.g:973:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            {
            // InternalSEMVER.g:973:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 ) )
            // InternalSEMVER.g:974:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); 
            // InternalSEMVER.g:975:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_1 )
            // InternalSEMVER.g:975:3: rule__VersionRangeContraint__VersionConstraintsAssignment_1
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
    // InternalSEMVER.g:983:1: rule__VersionRangeContraint__Group__2 : rule__VersionRangeContraint__Group__2__Impl ;
    public final void rule__VersionRangeContraint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:987:1: ( rule__VersionRangeContraint__Group__2__Impl )
            // InternalSEMVER.g:988:2: rule__VersionRangeContraint__Group__2__Impl
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
    // InternalSEMVER.g:994:1: rule__VersionRangeContraint__Group__2__Impl : ( ( rule__VersionRangeContraint__Group_2__0 )* ) ;
    public final void rule__VersionRangeContraint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:998:1: ( ( ( rule__VersionRangeContraint__Group_2__0 )* ) )
            // InternalSEMVER.g:999:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            {
            // InternalSEMVER.g:999:1: ( ( rule__VersionRangeContraint__Group_2__0 )* )
            // InternalSEMVER.g:1000:2: ( rule__VersionRangeContraint__Group_2__0 )*
            {
             before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); 
            // InternalSEMVER.g:1001:2: ( rule__VersionRangeContraint__Group_2__0 )*
            loop15:
            do {
                int alt15=2;
                alt15 = dfa15.predict(input);
                switch (alt15) {
            	case 1 :
            	    // InternalSEMVER.g:1001:3: rule__VersionRangeContraint__Group_2__0
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__VersionRangeContraint__Group_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
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
    // InternalSEMVER.g:1010:1: rule__VersionRangeContraint__Group_2__0 : rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 ;
    public final void rule__VersionRangeContraint__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1014:1: ( rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1 )
            // InternalSEMVER.g:1015:2: rule__VersionRangeContraint__Group_2__0__Impl rule__VersionRangeContraint__Group_2__1
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
    // InternalSEMVER.g:1022:1: rule__VersionRangeContraint__Group_2__0__Impl : ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) ;
    public final void rule__VersionRangeContraint__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1026:1: ( ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) ) )
            // InternalSEMVER.g:1027:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            {
            // InternalSEMVER.g:1027:1: ( ( ( RULE_WS ) ) ( ( RULE_WS )* ) )
            // InternalSEMVER.g:1028:2: ( ( RULE_WS ) ) ( ( RULE_WS )* )
            {
            // InternalSEMVER.g:1028:2: ( ( RULE_WS ) )
            // InternalSEMVER.g:1029:3: ( RULE_WS )
            {
             before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            // InternalSEMVER.g:1030:3: ( RULE_WS )
            // InternalSEMVER.g:1030:4: RULE_WS
            {
            match(input,RULE_WS,FOLLOW_5); 

            }

             after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 

            }

            // InternalSEMVER.g:1033:2: ( ( RULE_WS )* )
            // InternalSEMVER.g:1034:3: ( RULE_WS )*
            {
             before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); 
            // InternalSEMVER.g:1035:3: ( RULE_WS )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_WS) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalSEMVER.g:1035:4: RULE_WS
            	    {
            	    match(input,RULE_WS,FOLLOW_5); 

            	    }
            	    break;

            	default :
            	    break loop16;
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
    // InternalSEMVER.g:1044:1: rule__VersionRangeContraint__Group_2__1 : rule__VersionRangeContraint__Group_2__1__Impl ;
    public final void rule__VersionRangeContraint__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1048:1: ( rule__VersionRangeContraint__Group_2__1__Impl )
            // InternalSEMVER.g:1049:2: rule__VersionRangeContraint__Group_2__1__Impl
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
    // InternalSEMVER.g:1055:1: rule__VersionRangeContraint__Group_2__1__Impl : ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) ;
    public final void rule__VersionRangeContraint__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1059:1: ( ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) ) )
            // InternalSEMVER.g:1060:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            {
            // InternalSEMVER.g:1060:1: ( ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 ) )
            // InternalSEMVER.g:1061:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            {
             before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); 
            // InternalSEMVER.g:1062:2: ( rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 )
            // InternalSEMVER.g:1062:3: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
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
    // InternalSEMVER.g:1071:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1075:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSEMVER.g:1076:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
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
    // InternalSEMVER.g:1083:1: rule__SimpleVersion__Group__0__Impl : ( () ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1087:1: ( ( () ) )
            // InternalSEMVER.g:1088:1: ( () )
            {
            // InternalSEMVER.g:1088:1: ( () )
            // InternalSEMVER.g:1089:2: ()
            {
             before(grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0()); 
            // InternalSEMVER.g:1090:2: ()
            // InternalSEMVER.g:1090:3: 
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
    // InternalSEMVER.g:1098:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1102:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSEMVER.g:1103:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
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
    // InternalSEMVER.g:1110:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1114:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* ) )
            // InternalSEMVER.g:1115:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* )
            {
            // InternalSEMVER.g:1115:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* )
            // InternalSEMVER.g:1116:2: ( rule__SimpleVersion__ComparatorsAssignment_1 )*
            {
             before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1()); 
            // InternalSEMVER.g:1117:2: ( rule__SimpleVersion__ComparatorsAssignment_1 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=30 && LA17_0<=37)) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSEMVER.g:1117:3: rule__SimpleVersion__ComparatorsAssignment_1
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__SimpleVersion__ComparatorsAssignment_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:1125:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1129:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSEMVER.g:1130:2: rule__SimpleVersion__Group__2__Impl
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
    // InternalSEMVER.g:1136:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1140:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSEMVER.g:1141:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSEMVER.g:1141:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSEMVER.g:1142:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
             before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            // InternalSEMVER.g:1143:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSEMVER.g:1143:3: rule__SimpleVersion__NumberAssignment_2
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


    // $ANTLR start "rule__VersionNumber__Group__0"
    // InternalSEMVER.g:1152:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1156:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSEMVER.g:1157:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
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
    // InternalSEMVER.g:1164:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1168:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSEMVER.g:1169:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSEMVER.g:1169:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSEMVER.g:1170:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
             before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            // InternalSEMVER.g:1171:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSEMVER.g:1171:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSEMVER.g:1179:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1183:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSEMVER.g:1184:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
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
    // InternalSEMVER.g:1191:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1195:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSEMVER.g:1196:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSEMVER.g:1196:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSEMVER.g:1197:2: ( rule__VersionNumber__Group_1__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            // InternalSEMVER.g:1198:2: ( rule__VersionNumber__Group_1__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==40) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalSEMVER.g:1198:3: rule__VersionNumber__Group_1__0
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
    // InternalSEMVER.g:1206:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1210:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSEMVER.g:1211:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSEMVER.g:1217:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1221:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSEMVER.g:1222:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSEMVER.g:1222:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSEMVER.g:1223:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
             before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            // InternalSEMVER.g:1224:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==39||LA19_0==41) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSEMVER.g:1224:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSEMVER.g:1233:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1237:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSEMVER.g:1238:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
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
    // InternalSEMVER.g:1245:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1249:1: ( ( '.' ) )
            // InternalSEMVER.g:1250:1: ( '.' )
            {
            // InternalSEMVER.g:1250:1: ( '.' )
            // InternalSEMVER.g:1251:2: '.'
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
    // InternalSEMVER.g:1260:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1264:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSEMVER.g:1265:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
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
    // InternalSEMVER.g:1272:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1276:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSEMVER.g:1277:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSEMVER.g:1277:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSEMVER.g:1278:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            // InternalSEMVER.g:1279:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSEMVER.g:1279:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSEMVER.g:1287:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1291:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSEMVER.g:1292:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSEMVER.g:1298:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1302:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSEMVER.g:1303:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSEMVER.g:1303:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSEMVER.g:1304:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            // InternalSEMVER.g:1305:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==40) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSEMVER.g:1305:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSEMVER.g:1314:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1318:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSEMVER.g:1319:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
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
    // InternalSEMVER.g:1326:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1330:1: ( ( '.' ) )
            // InternalSEMVER.g:1331:1: ( '.' )
            {
            // InternalSEMVER.g:1331:1: ( '.' )
            // InternalSEMVER.g:1332:2: '.'
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
    // InternalSEMVER.g:1341:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1345:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSEMVER.g:1346:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
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
    // InternalSEMVER.g:1353:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1357:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSEMVER.g:1358:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSEMVER.g:1358:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSEMVER.g:1359:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            // InternalSEMVER.g:1360:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSEMVER.g:1360:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSEMVER.g:1368:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1372:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSEMVER.g:1373:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSEMVER.g:1379:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1383:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSEMVER.g:1384:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSEMVER.g:1384:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSEMVER.g:1385:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            // InternalSEMVER.g:1386:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==40) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalSEMVER.g:1386:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_14);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
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
    // InternalSEMVER.g:1395:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1399:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSEMVER.g:1400:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
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
    // InternalSEMVER.g:1407:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1411:1: ( ( '.' ) )
            // InternalSEMVER.g:1412:1: ( '.' )
            {
            // InternalSEMVER.g:1412:1: ( '.' )
            // InternalSEMVER.g:1413:2: '.'
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
    // InternalSEMVER.g:1422:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1426:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSEMVER.g:1427:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSEMVER.g:1433:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1437:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSEMVER.g:1438:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSEMVER.g:1438:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSEMVER.g:1439:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            // InternalSEMVER.g:1440:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSEMVER.g:1440:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSEMVER.g:1449:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1453:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSEMVER.g:1454:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
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
    // InternalSEMVER.g:1461:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1465:1: ( ( '-' ) )
            // InternalSEMVER.g:1466:1: ( '-' )
            {
            // InternalSEMVER.g:1466:1: ( '-' )
            // InternalSEMVER.g:1467:2: '-'
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
    // InternalSEMVER.g:1476:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1480:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSEMVER.g:1481:2: rule__Qualifier__Group_0__1__Impl
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
    // InternalSEMVER.g:1487:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1491:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSEMVER.g:1492:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSEMVER.g:1492:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSEMVER.g:1493:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            // InternalSEMVER.g:1494:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSEMVER.g:1494:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSEMVER.g:1503:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1507:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSEMVER.g:1508:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
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
    // InternalSEMVER.g:1515:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1519:1: ( ( '+' ) )
            // InternalSEMVER.g:1520:1: ( '+' )
            {
            // InternalSEMVER.g:1520:1: ( '+' )
            // InternalSEMVER.g:1521:2: '+'
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
    // InternalSEMVER.g:1530:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1534:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSEMVER.g:1535:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSEMVER.g:1541:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1545:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSEMVER.g:1546:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSEMVER.g:1546:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSEMVER.g:1547:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            // InternalSEMVER.g:1548:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSEMVER.g:1548:3: rule__Qualifier__BuildMetadataAssignment_1_1
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
    // InternalSEMVER.g:1557:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1561:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSEMVER.g:1562:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
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
    // InternalSEMVER.g:1569:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1573:1: ( ( '-' ) )
            // InternalSEMVER.g:1574:1: ( '-' )
            {
            // InternalSEMVER.g:1574:1: ( '-' )
            // InternalSEMVER.g:1575:2: '-'
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
    // InternalSEMVER.g:1584:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1588:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSEMVER.g:1589:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
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
    // InternalSEMVER.g:1596:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1600:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSEMVER.g:1601:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSEMVER.g:1601:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSEMVER.g:1602:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            // InternalSEMVER.g:1603:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSEMVER.g:1603:3: rule__Qualifier__PreReleaseAssignment_2_1
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
    // InternalSEMVER.g:1611:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1615:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSEMVER.g:1616:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
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
    // InternalSEMVER.g:1623:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1627:1: ( ( '+' ) )
            // InternalSEMVER.g:1628:1: ( '+' )
            {
            // InternalSEMVER.g:1628:1: ( '+' )
            // InternalSEMVER.g:1629:2: '+'
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
    // InternalSEMVER.g:1638:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1642:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSEMVER.g:1643:2: rule__Qualifier__Group_2__3__Impl
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
    // InternalSEMVER.g:1649:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1653:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSEMVER.g:1654:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSEMVER.g:1654:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSEMVER.g:1655:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            // InternalSEMVER.g:1656:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSEMVER.g:1656:3: rule__Qualifier__BuildMetadataAssignment_2_3
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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__0"
    // InternalSEMVER.g:1665:1: rule__ALPHA_NUMERIC_CHAR__Group__0 : rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1 ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1669:1: ( rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1 )
            // InternalSEMVER.g:1670:2: rule__ALPHA_NUMERIC_CHAR__Group__0__Impl rule__ALPHA_NUMERIC_CHAR__Group__1
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
    // InternalSEMVER.g:1677:1: rule__ALPHA_NUMERIC_CHAR__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1681:1: ( ( ( '-' )? ) )
            // InternalSEMVER.g:1682:1: ( ( '-' )? )
            {
            // InternalSEMVER.g:1682:1: ( ( '-' )? )
            // InternalSEMVER.g:1683:2: ( '-' )?
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getHyphenMinusKeyword_0()); 
            // InternalSEMVER.g:1684:2: ( '-' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==39) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSEMVER.g:1684:3: '-'
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
    // InternalSEMVER.g:1692:1: rule__ALPHA_NUMERIC_CHAR__Group__1 : rule__ALPHA_NUMERIC_CHAR__Group__1__Impl rule__ALPHA_NUMERIC_CHAR__Group__2 ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1696:1: ( rule__ALPHA_NUMERIC_CHAR__Group__1__Impl rule__ALPHA_NUMERIC_CHAR__Group__2 )
            // InternalSEMVER.g:1697:2: rule__ALPHA_NUMERIC_CHAR__Group__1__Impl rule__ALPHA_NUMERIC_CHAR__Group__2
            {
            pushFollow(FOLLOW_15);
            rule__ALPHA_NUMERIC_CHAR__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__2();

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
    // InternalSEMVER.g:1704:1: rule__ALPHA_NUMERIC_CHAR__Group__1__Impl : ( ( '.' )? ) ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1708:1: ( ( ( '.' )? ) )
            // InternalSEMVER.g:1709:1: ( ( '.' )? )
            {
            // InternalSEMVER.g:1709:1: ( ( '.' )? )
            // InternalSEMVER.g:1710:2: ( '.' )?
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getFullStopKeyword_1()); 
            // InternalSEMVER.g:1711:2: ( '.' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==40) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSEMVER.g:1711:3: '.'
                    {
                    match(input,40,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getFullStopKeyword_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__2"
    // InternalSEMVER.g:1719:1: rule__ALPHA_NUMERIC_CHAR__Group__2 : rule__ALPHA_NUMERIC_CHAR__Group__2__Impl ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1723:1: ( rule__ALPHA_NUMERIC_CHAR__Group__2__Impl )
            // InternalSEMVER.g:1724:2: rule__ALPHA_NUMERIC_CHAR__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Group__2__Impl();

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
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__2"


    // $ANTLR start "rule__ALPHA_NUMERIC_CHAR__Group__2__Impl"
    // InternalSEMVER.g:1730:1: rule__ALPHA_NUMERIC_CHAR__Group__2__Impl : ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_2 ) ) ;
    public final void rule__ALPHA_NUMERIC_CHAR__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1734:1: ( ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_2 ) ) )
            // InternalSEMVER.g:1735:1: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_2 ) )
            {
            // InternalSEMVER.g:1735:1: ( ( rule__ALPHA_NUMERIC_CHAR__Alternatives_2 ) )
            // InternalSEMVER.g:1736:2: ( rule__ALPHA_NUMERIC_CHAR__Alternatives_2 )
            {
             before(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives_2()); 
            // InternalSEMVER.g:1737:2: ( rule__ALPHA_NUMERIC_CHAR__Alternatives_2 )
            // InternalSEMVER.g:1737:3: rule__ALPHA_NUMERIC_CHAR__Alternatives_2
            {
            pushFollow(FOLLOW_2);
            rule__ALPHA_NUMERIC_CHAR__Alternatives_2();

            state._fsp--;


            }

             after(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ALPHA_NUMERIC_CHAR__Group__2__Impl"


    // $ANTLR start "rule__VersionRangeSet__RangesAssignment_2_0"
    // InternalSEMVER.g:1746:1: rule__VersionRangeSet__RangesAssignment_2_0 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1750:1: ( ( ruleVersionRange ) )
            // InternalSEMVER.g:1751:2: ( ruleVersionRange )
            {
            // InternalSEMVER.g:1751:2: ( ruleVersionRange )
            // InternalSEMVER.g:1752:3: ruleVersionRange
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
    // InternalSEMVER.g:1761:1: rule__VersionRangeSet__RangesAssignment_2_1_3 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_2_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1765:1: ( ( ruleVersionRange ) )
            // InternalSEMVER.g:1766:2: ( ruleVersionRange )
            {
            // InternalSEMVER.g:1766:2: ( ruleVersionRange )
            // InternalSEMVER.g:1767:3: ruleVersionRange
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
    // InternalSEMVER.g:1776:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1780:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1781:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1781:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1782:3: ruleVersionNumber
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
    // InternalSEMVER.g:1791:1: rule__HyphenVersionRange__ToAssignment_5 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1795:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1796:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1796:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1797:3: ruleVersionNumber
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
    // InternalSEMVER.g:1806:1: rule__VersionRangeContraint__VersionConstraintsAssignment_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1810:1: ( ( ruleSimpleVersion ) )
            // InternalSEMVER.g:1811:2: ( ruleSimpleVersion )
            {
            // InternalSEMVER.g:1811:2: ( ruleSimpleVersion )
            // InternalSEMVER.g:1812:3: ruleSimpleVersion
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
    // InternalSEMVER.g:1821:1: rule__VersionRangeContraint__VersionConstraintsAssignment_2_1 : ( ruleSimpleVersion ) ;
    public final void rule__VersionRangeContraint__VersionConstraintsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1825:1: ( ( ruleSimpleVersion ) )
            // InternalSEMVER.g:1826:2: ( ruleSimpleVersion )
            {
            // InternalSEMVER.g:1826:2: ( ruleSimpleVersion )
            // InternalSEMVER.g:1827:3: ruleSimpleVersion
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


    // $ANTLR start "rule__SimpleVersion__ComparatorsAssignment_1"
    // InternalSEMVER.g:1836:1: rule__SimpleVersion__ComparatorsAssignment_1 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1840:1: ( ( ruleVersionComparator ) )
            // InternalSEMVER.g:1841:2: ( ruleVersionComparator )
            {
            // InternalSEMVER.g:1841:2: ( ruleVersionComparator )
            // InternalSEMVER.g:1842:3: ruleVersionComparator
            {
             before(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionComparator();

            state._fsp--;

             after(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleVersion__ComparatorsAssignment_1"


    // $ANTLR start "rule__SimpleVersion__NumberAssignment_2"
    // InternalSEMVER.g:1851:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1855:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1856:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1856:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1857:3: ruleVersionNumber
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
    // InternalSEMVER.g:1866:1: rule__VersionNumber__MajorAssignment_0 : ( ruleVERSION_PART ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1870:1: ( ( ruleVERSION_PART ) )
            // InternalSEMVER.g:1871:2: ( ruleVERSION_PART )
            {
            // InternalSEMVER.g:1871:2: ( ruleVERSION_PART )
            // InternalSEMVER.g:1872:3: ruleVERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getMajorVERSION_PARTParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVERSION_PART();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getMajorVERSION_PARTParserRuleCall_0_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1881:1: rule__VersionNumber__MinorAssignment_1_1 : ( ruleVERSION_PART ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1885:1: ( ( ruleVERSION_PART ) )
            // InternalSEMVER.g:1886:2: ( ruleVERSION_PART )
            {
            // InternalSEMVER.g:1886:2: ( ruleVERSION_PART )
            // InternalSEMVER.g:1887:3: ruleVERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getMinorVERSION_PARTParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVERSION_PART();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getMinorVERSION_PARTParserRuleCall_1_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1896:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( ruleVERSION_PART ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1900:1: ( ( ruleVERSION_PART ) )
            // InternalSEMVER.g:1901:2: ( ruleVERSION_PART )
            {
            // InternalSEMVER.g:1901:2: ( ruleVERSION_PART )
            // InternalSEMVER.g:1902:3: ruleVERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getPatchVERSION_PARTParserRuleCall_1_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVERSION_PART();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getPatchVERSION_PARTParserRuleCall_1_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1911:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( ruleVERSION_PART ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1915:1: ( ( ruleVERSION_PART ) )
            // InternalSEMVER.g:1916:2: ( ruleVERSION_PART )
            {
            // InternalSEMVER.g:1916:2: ( ruleVERSION_PART )
            // InternalSEMVER.g:1917:3: ruleVERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getExtendedVERSION_PARTParserRuleCall_1_2_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVERSION_PART();

            state._fsp--;

             after(grammarAccess.getVersionNumberAccess().getExtendedVERSION_PARTParserRuleCall_1_2_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1926:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1930:1: ( ( ruleQualifier ) )
            // InternalSEMVER.g:1931:2: ( ruleQualifier )
            {
            // InternalSEMVER.g:1931:2: ( ruleQualifier )
            // InternalSEMVER.g:1932:3: ruleQualifier
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


    // $ANTLR start "rule__Qualifier__PreReleaseAssignment_0_1"
    // InternalSEMVER.g:1941:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1945:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1946:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1946:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1947:3: ruleALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSParserRuleCall_0_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1956:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1960:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1961:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1961:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1962:3: ruleALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1971:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1975:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1976:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1976:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1977:3: ruleALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSParserRuleCall_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1986:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( ruleALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1990:1: ( ( ruleALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1991:2: ( ruleALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1991:2: ( ruleALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1992:3: ruleALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSParserRuleCall_2_3_0()); 
            pushFollow(FOLLOW_2);
            ruleALPHA_NUMERIC_CHARS();

            state._fsp--;

             after(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSParserRuleCall_2_3_0()); 

            }


            }

        }
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

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA3 dfa3 = new DFA3(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA15 dfa15 = new DFA15(this);
    static final String dfa_1s = "\63\uffff";
    static final String dfa_2s = "\2\uffff\4\1\3\uffff\5\1\2\uffff\2\1\2\uffff\3\1\4\uffff\2\1\3\uffff\6\1\2\uffff\2\1\3\uffff\6\1";
    static final String dfa_3s = "\1\4\1\uffff\4\6\4\4\4\6\11\4\1\uffff\12\4\4\6\11\4\4\6";
    static final String dfa_4s = "\1\45\1\uffff\4\51\1\35\2\50\1\47\4\51\1\50\1\5\2\51\1\50\1\5\2\50\1\47\1\uffff\1\35\1\50\1\5\2\51\2\50\1\5\2\50\4\51\1\50\1\5\2\50\1\35\1\50\1\5\2\50\4\51";
    static final String dfa_5s = "\1\uffff\1\1\25\uffff\1\2\33\uffff";
    static final String dfa_6s = "\63\uffff}>";
    static final String[] dfa_7s = {
            "\1\5\26\uffff\1\2\1\3\1\4\10\1",
            "",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\11\37\uffff\1\1\1\7\1\6\1\10",
            "\1\15\26\uffff\1\12\1\13\1\14",
            "\1\20\1\21\41\uffff\1\16\1\17",
            "\1\24\1\25\41\uffff\1\22\1\23",
            "\1\1\1\uffff\1\26\24\uffff\14\1\1\27",
            "\1\11\37\uffff\1\1\1\7\1\30\1\10",
            "\1\11\37\uffff\1\1\1\7\1\30\1\10",
            "\1\11\37\uffff\1\1\1\7\1\30\1\10",
            "\1\11\37\uffff\1\1\1\7\1\30\1\10",
            "\1\20\1\21\42\uffff\1\17",
            "\1\20\1\21",
            "\1\33\1\34\1\11\37\uffff\1\1\1\31\1\32\1\35",
            "\1\33\1\34\1\11\37\uffff\1\1\1\31\1\32\1\35",
            "\1\24\1\25\42\uffff\1\23",
            "\1\24\1\25",
            "\1\40\1\41\1\11\37\uffff\1\1\1\36\1\37",
            "\1\40\1\41\1\11\37\uffff\1\1\1\36\1\37",
            "\1\1\1\uffff\1\26\24\uffff\14\1\1\27",
            "",
            "\1\45\26\uffff\1\42\1\43\1\44",
            "\1\33\1\34\42\uffff\1\32",
            "\1\33\1\34",
            "\1\33\1\34\1\11\37\uffff\1\1\1\31\1\32\1\35",
            "\1\33\1\34\1\11\37\uffff\1\1\1\31\1\32\1\35",
            "\1\50\1\51\41\uffff\1\46\1\47",
            "\1\40\1\41\42\uffff\1\37",
            "\1\40\1\41",
            "\1\40\1\41\1\11\37\uffff\1\1\1\36\1\37",
            "\1\40\1\41\1\11\37\uffff\1\1\1\36\1\37",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10",
            "\1\50\1\51\42\uffff\1\47",
            "\1\50\1\51",
            "\1\55\1\56\1\11\37\uffff\1\1\1\53\1\54",
            "\1\55\1\56\1\11\37\uffff\1\1\1\53\1\54",
            "\1\62\26\uffff\1\57\1\60\1\61",
            "\1\55\1\56\42\uffff\1\54",
            "\1\55\1\56",
            "\1\55\1\56\1\11\37\uffff\1\1\1\53\1\54",
            "\1\55\1\56\1\11\37\uffff\1\1\1\53\1\54",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10",
            "\1\11\37\uffff\1\1\1\7\1\52\1\10"
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
            return "324:1: rule__VersionRange__Alternatives : ( ( ruleVersionRangeContraint ) | ( ruleHyphenVersionRange ) );";
        }
    }
    static final String dfa_8s = "\15\uffff";
    static final String dfa_9s = "\5\uffff\2\14\2\uffff\2\14\2\uffff";
    static final String dfa_10s = "\1\47\1\4\1\uffff\10\4\2\uffff";
    static final String dfa_11s = "\1\51\1\50\1\uffff\1\50\1\5\2\51\1\50\1\5\2\51\2\uffff";
    static final String dfa_12s = "\2\uffff\1\2\10\uffff\1\3\1\1";
    static final String dfa_13s = "\15\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\1\uffff\1\2",
            "\1\5\1\6\41\uffff\1\3\1\4",
            "",
            "\1\5\1\6\42\uffff\1\4",
            "\1\5\1\6",
            "\1\11\1\12\1\14\37\uffff\1\14\1\7\1\10\1\13",
            "\1\11\1\12\1\14\37\uffff\1\14\1\7\1\10\1\13",
            "\1\11\1\12\42\uffff\1\10",
            "\1\11\1\12",
            "\1\11\1\12\1\14\37\uffff\1\14\1\7\1\10\1\13",
            "\1\11\1\12\1\14\37\uffff\1\14\1\7\1\10\1\13",
            "",
            ""
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
            return "345:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );";
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
            return "()* loopback of 610:2: ( rule__VersionRangeSet__Group_2_1__0 )*";
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

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "()* loopback of 1001:2: ( rule__VersionRangeContraint__Group_2__0 )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000018000000032L});
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
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000018000000030L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000020000000000L});

}