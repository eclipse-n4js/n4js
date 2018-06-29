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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_VERSION_PART", "RULE_ALPHA_NUMERIC_CHARS", "RULE_NUMERIC_ID", "RULE_ALPHA_NUMERIC_CHAR", "RULE_DIGIT", "RULE_NON_DIGIT", "RULE_LETTER", "RULE_POSITIVE_DIGIT", "RULE_WHITESPACE_FRAGMENT", "RULE_WS", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_EOL", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_HEX_DIGIT", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_ANY_OTHER", "'v'", "'='", "'<'", "'~'", "'^'", "'<='", "'>'", "'>='", "'||'", "'-'", "'.'", "'+'"
    };
    public static final int RULE_ML_COMMENT_FRAGMENT=25;
    public static final int RULE_ZWJ=19;
    public static final int RULE_SL_COMMENT_FRAGMENT=24;
    public static final int RULE_WHITESPACE_FRAGMENT=12;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=27;
    public static final int T__37=37;
    public static final int RULE_ALPHA_NUMERIC_CHARS=5;
    public static final int T__38=38;
    public static final int RULE_NUMERIC_ID=6;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=22;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_VERSION_PART=4;
    public static final int EOF=-1;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_NON_DIGIT=9;
    public static final int RULE_ALPHA_NUMERIC_CHAR=7;
    public static final int RULE_POSITIVE_DIGIT=11;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=14;
    public static final int RULE_WS=13;
    public static final int RULE_EOL=15;
    public static final int RULE_BOM=21;
    public static final int RULE_DIGIT=8;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=26;
    public static final int RULE_ANY_OTHER=30;
    public static final int RULE_LETTER=10;
    public static final int RULE_ZWNJ=20;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=23;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=29;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=16;
    public static final int RULE_HEX_DIGIT=17;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=18;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=28;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;

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


    // $ANTLR start "entryRuleSimpleVersion"
    // InternalSEMVER.g:135:1: entryRuleSimpleVersion : ruleSimpleVersion EOF ;
    public final void entryRuleSimpleVersion() throws RecognitionException {
        try {
            // InternalSEMVER.g:136:1: ( ruleSimpleVersion EOF )
            // InternalSEMVER.g:137:1: ruleSimpleVersion EOF
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
    // InternalSEMVER.g:144:1: ruleSimpleVersion : ( ( rule__SimpleVersion__Group__0 ) ) ;
    public final void ruleSimpleVersion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:148:2: ( ( ( rule__SimpleVersion__Group__0 ) ) )
            // InternalSEMVER.g:149:2: ( ( rule__SimpleVersion__Group__0 ) )
            {
            // InternalSEMVER.g:149:2: ( ( rule__SimpleVersion__Group__0 ) )
            // InternalSEMVER.g:150:3: ( rule__SimpleVersion__Group__0 )
            {
             before(grammarAccess.getSimpleVersionAccess().getGroup()); 
            // InternalSEMVER.g:151:3: ( rule__SimpleVersion__Group__0 )
            // InternalSEMVER.g:151:4: rule__SimpleVersion__Group__0
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
    // InternalSEMVER.g:160:1: entryRuleVersionNumber : ruleVersionNumber EOF ;
    public final void entryRuleVersionNumber() throws RecognitionException {
        try {
            // InternalSEMVER.g:161:1: ( ruleVersionNumber EOF )
            // InternalSEMVER.g:162:1: ruleVersionNumber EOF
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
    // InternalSEMVER.g:169:1: ruleVersionNumber : ( ( rule__VersionNumber__Group__0 ) ) ;
    public final void ruleVersionNumber() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:173:2: ( ( ( rule__VersionNumber__Group__0 ) ) )
            // InternalSEMVER.g:174:2: ( ( rule__VersionNumber__Group__0 ) )
            {
            // InternalSEMVER.g:174:2: ( ( rule__VersionNumber__Group__0 ) )
            // InternalSEMVER.g:175:3: ( rule__VersionNumber__Group__0 )
            {
             before(grammarAccess.getVersionNumberAccess().getGroup()); 
            // InternalSEMVER.g:176:3: ( rule__VersionNumber__Group__0 )
            // InternalSEMVER.g:176:4: rule__VersionNumber__Group__0
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
    // InternalSEMVER.g:185:1: entryRuleQualifier : ruleQualifier EOF ;
    public final void entryRuleQualifier() throws RecognitionException {
        try {
            // InternalSEMVER.g:186:1: ( ruleQualifier EOF )
            // InternalSEMVER.g:187:1: ruleQualifier EOF
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
    // InternalSEMVER.g:194:1: ruleQualifier : ( ( rule__Qualifier__Alternatives ) ) ;
    public final void ruleQualifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:198:2: ( ( ( rule__Qualifier__Alternatives ) ) )
            // InternalSEMVER.g:199:2: ( ( rule__Qualifier__Alternatives ) )
            {
            // InternalSEMVER.g:199:2: ( ( rule__Qualifier__Alternatives ) )
            // InternalSEMVER.g:200:3: ( rule__Qualifier__Alternatives )
            {
             before(grammarAccess.getQualifierAccess().getAlternatives()); 
            // InternalSEMVER.g:201:3: ( rule__Qualifier__Alternatives )
            // InternalSEMVER.g:201:4: rule__Qualifier__Alternatives
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


    // $ANTLR start "ruleVersionComparator"
    // InternalSEMVER.g:210:1: ruleVersionComparator : ( ( rule__VersionComparator__Alternatives ) ) ;
    public final void ruleVersionComparator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:214:1: ( ( ( rule__VersionComparator__Alternatives ) ) )
            // InternalSEMVER.g:215:2: ( ( rule__VersionComparator__Alternatives ) )
            {
            // InternalSEMVER.g:215:2: ( ( rule__VersionComparator__Alternatives ) )
            // InternalSEMVER.g:216:3: ( rule__VersionComparator__Alternatives )
            {
             before(grammarAccess.getVersionComparatorAccess().getAlternatives()); 
            // InternalSEMVER.g:217:3: ( rule__VersionComparator__Alternatives )
            // InternalSEMVER.g:217:4: rule__VersionComparator__Alternatives
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
    // InternalSEMVER.g:225:1: rule__VersionRange__Alternatives : ( ( ruleSimpleVersion ) | ( ruleHyphenVersionRange ) );
    public final void rule__VersionRange__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:229:1: ( ( ruleSimpleVersion ) | ( ruleHyphenVersionRange ) )
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // InternalSEMVER.g:230:2: ( ruleSimpleVersion )
                    {
                    // InternalSEMVER.g:230:2: ( ruleSimpleVersion )
                    // InternalSEMVER.g:231:3: ruleSimpleVersion
                    {
                     before(grammarAccess.getVersionRangeAccess().getSimpleVersionParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleSimpleVersion();

                    state._fsp--;

                     after(grammarAccess.getVersionRangeAccess().getSimpleVersionParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:236:2: ( ruleHyphenVersionRange )
                    {
                    // InternalSEMVER.g:236:2: ( ruleHyphenVersionRange )
                    // InternalSEMVER.g:237:3: ruleHyphenVersionRange
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
    // InternalSEMVER.g:246:1: rule__Qualifier__Alternatives : ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) );
    public final void rule__Qualifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:250:1: ( ( ( rule__Qualifier__Group_0__0 ) ) | ( ( rule__Qualifier__Group_1__0 ) ) | ( ( rule__Qualifier__Group_2__0 ) ) )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==40) ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==RULE_ALPHA_NUMERIC_CHARS) ) {
                    int LA2_3 = input.LA(3);

                    if ( (LA2_3==42) ) {
                        alt2=3;
                    }
                    else if ( (LA2_3==EOF||(LA2_3>=39 && LA2_3<=40)) ) {
                        alt2=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA2_0==42) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalSEMVER.g:251:2: ( ( rule__Qualifier__Group_0__0 ) )
                    {
                    // InternalSEMVER.g:251:2: ( ( rule__Qualifier__Group_0__0 ) )
                    // InternalSEMVER.g:252:3: ( rule__Qualifier__Group_0__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_0()); 
                    // InternalSEMVER.g:253:3: ( rule__Qualifier__Group_0__0 )
                    // InternalSEMVER.g:253:4: rule__Qualifier__Group_0__0
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
                    // InternalSEMVER.g:257:2: ( ( rule__Qualifier__Group_1__0 ) )
                    {
                    // InternalSEMVER.g:257:2: ( ( rule__Qualifier__Group_1__0 ) )
                    // InternalSEMVER.g:258:3: ( rule__Qualifier__Group_1__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_1()); 
                    // InternalSEMVER.g:259:3: ( rule__Qualifier__Group_1__0 )
                    // InternalSEMVER.g:259:4: rule__Qualifier__Group_1__0
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
                    // InternalSEMVER.g:263:2: ( ( rule__Qualifier__Group_2__0 ) )
                    {
                    // InternalSEMVER.g:263:2: ( ( rule__Qualifier__Group_2__0 ) )
                    // InternalSEMVER.g:264:3: ( rule__Qualifier__Group_2__0 )
                    {
                     before(grammarAccess.getQualifierAccess().getGroup_2()); 
                    // InternalSEMVER.g:265:3: ( rule__Qualifier__Group_2__0 )
                    // InternalSEMVER.g:265:4: rule__Qualifier__Group_2__0
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


    // $ANTLR start "rule__VersionComparator__Alternatives"
    // InternalSEMVER.g:273:1: rule__VersionComparator__Alternatives : ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) );
    public final void rule__VersionComparator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:277:1: ( ( ( 'v' ) ) | ( ( '=' ) ) | ( ( '<' ) ) | ( ( '~' ) ) | ( ( '^' ) ) | ( ( '<=' ) ) | ( ( '>' ) ) | ( ( '>=' ) ) )
            int alt3=8;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt3=1;
                }
                break;
            case 32:
                {
                alt3=2;
                }
                break;
            case 33:
                {
                alt3=3;
                }
                break;
            case 34:
                {
                alt3=4;
                }
                break;
            case 35:
                {
                alt3=5;
                }
                break;
            case 36:
                {
                alt3=6;
                }
                break;
            case 37:
                {
                alt3=7;
                }
                break;
            case 38:
                {
                alt3=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalSEMVER.g:278:2: ( ( 'v' ) )
                    {
                    // InternalSEMVER.g:278:2: ( ( 'v' ) )
                    // InternalSEMVER.g:279:3: ( 'v' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 
                    // InternalSEMVER.g:280:3: ( 'v' )
                    // InternalSEMVER.g:280:4: 'v'
                    {
                    match(input,31,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getVersionEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSEMVER.g:284:2: ( ( '=' ) )
                    {
                    // InternalSEMVER.g:284:2: ( ( '=' ) )
                    // InternalSEMVER.g:285:3: ( '=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 
                    // InternalSEMVER.g:286:3: ( '=' )
                    // InternalSEMVER.g:286:4: '='
                    {
                    match(input,32,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSEMVER.g:290:2: ( ( '<' ) )
                    {
                    // InternalSEMVER.g:290:2: ( ( '<' ) )
                    // InternalSEMVER.g:291:3: ( '<' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 
                    // InternalSEMVER.g:292:3: ( '<' )
                    // InternalSEMVER.g:292:4: '<'
                    {
                    match(input,33,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSEMVER.g:296:2: ( ( '~' ) )
                    {
                    // InternalSEMVER.g:296:2: ( ( '~' ) )
                    // InternalSEMVER.g:297:3: ( '~' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 
                    // InternalSEMVER.g:298:3: ( '~' )
                    // InternalSEMVER.g:298:4: '~'
                    {
                    match(input,34,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalSEMVER.g:302:2: ( ( '^' ) )
                    {
                    // InternalSEMVER.g:302:2: ( ( '^' ) )
                    // InternalSEMVER.g:303:3: ( '^' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 
                    // InternalSEMVER.g:304:3: ( '^' )
                    // InternalSEMVER.g:304:4: '^'
                    {
                    match(input,35,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalSEMVER.g:308:2: ( ( '<=' ) )
                    {
                    // InternalSEMVER.g:308:2: ( ( '<=' ) )
                    // InternalSEMVER.g:309:3: ( '<=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 
                    // InternalSEMVER.g:310:3: ( '<=' )
                    // InternalSEMVER.g:310:4: '<='
                    {
                    match(input,36,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalSEMVER.g:314:2: ( ( '>' ) )
                    {
                    // InternalSEMVER.g:314:2: ( ( '>' ) )
                    // InternalSEMVER.g:315:3: ( '>' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 
                    // InternalSEMVER.g:316:3: ( '>' )
                    // InternalSEMVER.g:316:4: '>'
                    {
                    match(input,37,FOLLOW_2); 

                    }

                     after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalSEMVER.g:320:2: ( ( '>=' ) )
                    {
                    // InternalSEMVER.g:320:2: ( ( '>=' ) )
                    // InternalSEMVER.g:321:3: ( '>=' )
                    {
                     before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_7()); 
                    // InternalSEMVER.g:322:3: ( '>=' )
                    // InternalSEMVER.g:322:4: '>='
                    {
                    match(input,38,FOLLOW_2); 

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
    // InternalSEMVER.g:330:1: rule__VersionRangeSet__Group__0 : rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 ;
    public final void rule__VersionRangeSet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:334:1: ( rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1 )
            // InternalSEMVER.g:335:2: rule__VersionRangeSet__Group__0__Impl rule__VersionRangeSet__Group__1
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
    // InternalSEMVER.g:342:1: rule__VersionRangeSet__Group__0__Impl : ( () ) ;
    public final void rule__VersionRangeSet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:346:1: ( ( () ) )
            // InternalSEMVER.g:347:1: ( () )
            {
            // InternalSEMVER.g:347:1: ( () )
            // InternalSEMVER.g:348:2: ()
            {
             before(grammarAccess.getVersionRangeSetAccess().getVersionRangeSetAction_0()); 
            // InternalSEMVER.g:349:2: ()
            // InternalSEMVER.g:349:3: 
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
    // InternalSEMVER.g:357:1: rule__VersionRangeSet__Group__1 : rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2 ;
    public final void rule__VersionRangeSet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:361:1: ( rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2 )
            // InternalSEMVER.g:362:2: rule__VersionRangeSet__Group__1__Impl rule__VersionRangeSet__Group__2
            {
            pushFollow(FOLLOW_3);
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
    // InternalSEMVER.g:369:1: rule__VersionRangeSet__Group__1__Impl : ( ( rule__VersionRangeSet__RangesAssignment_1 )? ) ;
    public final void rule__VersionRangeSet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:373:1: ( ( ( rule__VersionRangeSet__RangesAssignment_1 )? ) )
            // InternalSEMVER.g:374:1: ( ( rule__VersionRangeSet__RangesAssignment_1 )? )
            {
            // InternalSEMVER.g:374:1: ( ( rule__VersionRangeSet__RangesAssignment_1 )? )
            // InternalSEMVER.g:375:2: ( rule__VersionRangeSet__RangesAssignment_1 )?
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_1()); 
            // InternalSEMVER.g:376:2: ( rule__VersionRangeSet__RangesAssignment_1 )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_VERSION_PART||(LA4_0>=31 && LA4_0<=38)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalSEMVER.g:376:3: rule__VersionRangeSet__RangesAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionRangeSet__RangesAssignment_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_1()); 

            }


            }

        }
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
    // InternalSEMVER.g:384:1: rule__VersionRangeSet__Group__2 : rule__VersionRangeSet__Group__2__Impl ;
    public final void rule__VersionRangeSet__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:388:1: ( rule__VersionRangeSet__Group__2__Impl )
            // InternalSEMVER.g:389:2: rule__VersionRangeSet__Group__2__Impl
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
    // InternalSEMVER.g:395:1: rule__VersionRangeSet__Group__2__Impl : ( ( rule__VersionRangeSet__Group_2__0 )* ) ;
    public final void rule__VersionRangeSet__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:399:1: ( ( ( rule__VersionRangeSet__Group_2__0 )* ) )
            // InternalSEMVER.g:400:1: ( ( rule__VersionRangeSet__Group_2__0 )* )
            {
            // InternalSEMVER.g:400:1: ( ( rule__VersionRangeSet__Group_2__0 )* )
            // InternalSEMVER.g:401:2: ( rule__VersionRangeSet__Group_2__0 )*
            {
             before(grammarAccess.getVersionRangeSetAccess().getGroup_2()); 
            // InternalSEMVER.g:402:2: ( rule__VersionRangeSet__Group_2__0 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==39) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSEMVER.g:402:3: rule__VersionRangeSet__Group_2__0
            	    {
            	    pushFollow(FOLLOW_4);
            	    rule__VersionRangeSet__Group_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

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
    // InternalSEMVER.g:411:1: rule__VersionRangeSet__Group_2__0 : rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1 ;
    public final void rule__VersionRangeSet__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:415:1: ( rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1 )
            // InternalSEMVER.g:416:2: rule__VersionRangeSet__Group_2__0__Impl rule__VersionRangeSet__Group_2__1
            {
            pushFollow(FOLLOW_5);
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
    // InternalSEMVER.g:423:1: rule__VersionRangeSet__Group_2__0__Impl : ( '||' ) ;
    public final void rule__VersionRangeSet__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:427:1: ( ( '||' ) )
            // InternalSEMVER.g:428:1: ( '||' )
            {
            // InternalSEMVER.g:428:1: ( '||' )
            // InternalSEMVER.g:429:2: '||'
            {
             before(grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_2_0()); 
            match(input,39,FOLLOW_2); 
             after(grammarAccess.getVersionRangeSetAccess().getVerticalLineVerticalLineKeyword_2_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:438:1: rule__VersionRangeSet__Group_2__1 : rule__VersionRangeSet__Group_2__1__Impl ;
    public final void rule__VersionRangeSet__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:442:1: ( rule__VersionRangeSet__Group_2__1__Impl )
            // InternalSEMVER.g:443:2: rule__VersionRangeSet__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__Group_2__1__Impl();

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
    // InternalSEMVER.g:449:1: rule__VersionRangeSet__Group_2__1__Impl : ( ( rule__VersionRangeSet__RangesAssignment_2_1 ) ) ;
    public final void rule__VersionRangeSet__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:453:1: ( ( ( rule__VersionRangeSet__RangesAssignment_2_1 ) ) )
            // InternalSEMVER.g:454:1: ( ( rule__VersionRangeSet__RangesAssignment_2_1 ) )
            {
            // InternalSEMVER.g:454:1: ( ( rule__VersionRangeSet__RangesAssignment_2_1 ) )
            // InternalSEMVER.g:455:2: ( rule__VersionRangeSet__RangesAssignment_2_1 )
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_1()); 
            // InternalSEMVER.g:456:2: ( rule__VersionRangeSet__RangesAssignment_2_1 )
            // InternalSEMVER.g:456:3: rule__VersionRangeSet__RangesAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionRangeSet__RangesAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__HyphenVersionRange__Group__0"
    // InternalSEMVER.g:465:1: rule__HyphenVersionRange__Group__0 : rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 ;
    public final void rule__HyphenVersionRange__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:469:1: ( rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1 )
            // InternalSEMVER.g:470:2: rule__HyphenVersionRange__Group__0__Impl rule__HyphenVersionRange__Group__1
            {
            pushFollow(FOLLOW_5);
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
    // InternalSEMVER.g:477:1: rule__HyphenVersionRange__Group__0__Impl : ( () ) ;
    public final void rule__HyphenVersionRange__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:481:1: ( ( () ) )
            // InternalSEMVER.g:482:1: ( () )
            {
            // InternalSEMVER.g:482:1: ( () )
            // InternalSEMVER.g:483:2: ()
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); 
            // InternalSEMVER.g:484:2: ()
            // InternalSEMVER.g:484:3: 
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
    // InternalSEMVER.g:492:1: rule__HyphenVersionRange__Group__1 : rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 ;
    public final void rule__HyphenVersionRange__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:496:1: ( rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2 )
            // InternalSEMVER.g:497:2: rule__HyphenVersionRange__Group__1__Impl rule__HyphenVersionRange__Group__2
            {
            pushFollow(FOLLOW_6);
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
    // InternalSEMVER.g:504:1: rule__HyphenVersionRange__Group__1__Impl : ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) ;
    public final void rule__HyphenVersionRange__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:508:1: ( ( ( rule__HyphenVersionRange__FromAssignment_1 ) ) )
            // InternalSEMVER.g:509:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            {
            // InternalSEMVER.g:509:1: ( ( rule__HyphenVersionRange__FromAssignment_1 ) )
            // InternalSEMVER.g:510:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); 
            // InternalSEMVER.g:511:2: ( rule__HyphenVersionRange__FromAssignment_1 )
            // InternalSEMVER.g:511:3: rule__HyphenVersionRange__FromAssignment_1
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
    // InternalSEMVER.g:519:1: rule__HyphenVersionRange__Group__2 : rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 ;
    public final void rule__HyphenVersionRange__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:523:1: ( rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3 )
            // InternalSEMVER.g:524:2: rule__HyphenVersionRange__Group__2__Impl rule__HyphenVersionRange__Group__3
            {
            pushFollow(FOLLOW_5);
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
    // InternalSEMVER.g:531:1: rule__HyphenVersionRange__Group__2__Impl : ( '-' ) ;
    public final void rule__HyphenVersionRange__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:535:1: ( ( '-' ) )
            // InternalSEMVER.g:536:1: ( '-' )
            {
            // InternalSEMVER.g:536:1: ( '-' )
            // InternalSEMVER.g:537:2: '-'
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_2()); 
            match(input,40,FOLLOW_2); 
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
    // InternalSEMVER.g:546:1: rule__HyphenVersionRange__Group__3 : rule__HyphenVersionRange__Group__3__Impl ;
    public final void rule__HyphenVersionRange__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:550:1: ( rule__HyphenVersionRange__Group__3__Impl )
            // InternalSEMVER.g:551:2: rule__HyphenVersionRange__Group__3__Impl
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
    // InternalSEMVER.g:557:1: rule__HyphenVersionRange__Group__3__Impl : ( ( rule__HyphenVersionRange__ToAssignment_3 ) ) ;
    public final void rule__HyphenVersionRange__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:561:1: ( ( ( rule__HyphenVersionRange__ToAssignment_3 ) ) )
            // InternalSEMVER.g:562:1: ( ( rule__HyphenVersionRange__ToAssignment_3 ) )
            {
            // InternalSEMVER.g:562:1: ( ( rule__HyphenVersionRange__ToAssignment_3 ) )
            // InternalSEMVER.g:563:2: ( rule__HyphenVersionRange__ToAssignment_3 )
            {
             before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_3()); 
            // InternalSEMVER.g:564:2: ( rule__HyphenVersionRange__ToAssignment_3 )
            // InternalSEMVER.g:564:3: rule__HyphenVersionRange__ToAssignment_3
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


    // $ANTLR start "rule__SimpleVersion__Group__0"
    // InternalSEMVER.g:573:1: rule__SimpleVersion__Group__0 : rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 ;
    public final void rule__SimpleVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:577:1: ( rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1 )
            // InternalSEMVER.g:578:2: rule__SimpleVersion__Group__0__Impl rule__SimpleVersion__Group__1
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
    // InternalSEMVER.g:585:1: rule__SimpleVersion__Group__0__Impl : ( () ) ;
    public final void rule__SimpleVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:589:1: ( ( () ) )
            // InternalSEMVER.g:590:1: ( () )
            {
            // InternalSEMVER.g:590:1: ( () )
            // InternalSEMVER.g:591:2: ()
            {
             before(grammarAccess.getSimpleVersionAccess().getSimpleVersionAction_0()); 
            // InternalSEMVER.g:592:2: ()
            // InternalSEMVER.g:592:3: 
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
    // InternalSEMVER.g:600:1: rule__SimpleVersion__Group__1 : rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 ;
    public final void rule__SimpleVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:604:1: ( rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2 )
            // InternalSEMVER.g:605:2: rule__SimpleVersion__Group__1__Impl rule__SimpleVersion__Group__2
            {
            pushFollow(FOLLOW_5);
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
    // InternalSEMVER.g:612:1: rule__SimpleVersion__Group__1__Impl : ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* ) ;
    public final void rule__SimpleVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:616:1: ( ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* ) )
            // InternalSEMVER.g:617:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* )
            {
            // InternalSEMVER.g:617:1: ( ( rule__SimpleVersion__ComparatorsAssignment_1 )* )
            // InternalSEMVER.g:618:2: ( rule__SimpleVersion__ComparatorsAssignment_1 )*
            {
             before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1()); 
            // InternalSEMVER.g:619:2: ( rule__SimpleVersion__ComparatorsAssignment_1 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>=31 && LA6_0<=38)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSEMVER.g:619:3: rule__SimpleVersion__ComparatorsAssignment_1
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__SimpleVersion__ComparatorsAssignment_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
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
    // InternalSEMVER.g:627:1: rule__SimpleVersion__Group__2 : rule__SimpleVersion__Group__2__Impl ;
    public final void rule__SimpleVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:631:1: ( rule__SimpleVersion__Group__2__Impl )
            // InternalSEMVER.g:632:2: rule__SimpleVersion__Group__2__Impl
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
    // InternalSEMVER.g:638:1: rule__SimpleVersion__Group__2__Impl : ( ( rule__SimpleVersion__NumberAssignment_2 ) ) ;
    public final void rule__SimpleVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:642:1: ( ( ( rule__SimpleVersion__NumberAssignment_2 ) ) )
            // InternalSEMVER.g:643:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            {
            // InternalSEMVER.g:643:1: ( ( rule__SimpleVersion__NumberAssignment_2 ) )
            // InternalSEMVER.g:644:2: ( rule__SimpleVersion__NumberAssignment_2 )
            {
             before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); 
            // InternalSEMVER.g:645:2: ( rule__SimpleVersion__NumberAssignment_2 )
            // InternalSEMVER.g:645:3: rule__SimpleVersion__NumberAssignment_2
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
    // InternalSEMVER.g:654:1: rule__VersionNumber__Group__0 : rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 ;
    public final void rule__VersionNumber__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:658:1: ( rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1 )
            // InternalSEMVER.g:659:2: rule__VersionNumber__Group__0__Impl rule__VersionNumber__Group__1
            {
            pushFollow(FOLLOW_8);
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
    // InternalSEMVER.g:666:1: rule__VersionNumber__Group__0__Impl : ( ( rule__VersionNumber__MajorAssignment_0 ) ) ;
    public final void rule__VersionNumber__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:670:1: ( ( ( rule__VersionNumber__MajorAssignment_0 ) ) )
            // InternalSEMVER.g:671:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            {
            // InternalSEMVER.g:671:1: ( ( rule__VersionNumber__MajorAssignment_0 ) )
            // InternalSEMVER.g:672:2: ( rule__VersionNumber__MajorAssignment_0 )
            {
             before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); 
            // InternalSEMVER.g:673:2: ( rule__VersionNumber__MajorAssignment_0 )
            // InternalSEMVER.g:673:3: rule__VersionNumber__MajorAssignment_0
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
    // InternalSEMVER.g:681:1: rule__VersionNumber__Group__1 : rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 ;
    public final void rule__VersionNumber__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:685:1: ( rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2 )
            // InternalSEMVER.g:686:2: rule__VersionNumber__Group__1__Impl rule__VersionNumber__Group__2
            {
            pushFollow(FOLLOW_8);
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
    // InternalSEMVER.g:693:1: rule__VersionNumber__Group__1__Impl : ( ( rule__VersionNumber__Group_1__0 )? ) ;
    public final void rule__VersionNumber__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:697:1: ( ( ( rule__VersionNumber__Group_1__0 )? ) )
            // InternalSEMVER.g:698:1: ( ( rule__VersionNumber__Group_1__0 )? )
            {
            // InternalSEMVER.g:698:1: ( ( rule__VersionNumber__Group_1__0 )? )
            // InternalSEMVER.g:699:2: ( rule__VersionNumber__Group_1__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1()); 
            // InternalSEMVER.g:700:2: ( rule__VersionNumber__Group_1__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==41) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalSEMVER.g:700:3: rule__VersionNumber__Group_1__0
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
    // InternalSEMVER.g:708:1: rule__VersionNumber__Group__2 : rule__VersionNumber__Group__2__Impl ;
    public final void rule__VersionNumber__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:712:1: ( rule__VersionNumber__Group__2__Impl )
            // InternalSEMVER.g:713:2: rule__VersionNumber__Group__2__Impl
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
    // InternalSEMVER.g:719:1: rule__VersionNumber__Group__2__Impl : ( ( rule__VersionNumber__QualifierAssignment_2 )? ) ;
    public final void rule__VersionNumber__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:723:1: ( ( ( rule__VersionNumber__QualifierAssignment_2 )? ) )
            // InternalSEMVER.g:724:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            {
            // InternalSEMVER.g:724:1: ( ( rule__VersionNumber__QualifierAssignment_2 )? )
            // InternalSEMVER.g:725:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            {
             before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); 
            // InternalSEMVER.g:726:2: ( rule__VersionNumber__QualifierAssignment_2 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==40) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==RULE_ALPHA_NUMERIC_CHARS) ) {
                    alt8=1;
                }
            }
            else if ( (LA8_0==42) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSEMVER.g:726:3: rule__VersionNumber__QualifierAssignment_2
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
    // InternalSEMVER.g:735:1: rule__VersionNumber__Group_1__0 : rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 ;
    public final void rule__VersionNumber__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:739:1: ( rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1 )
            // InternalSEMVER.g:740:2: rule__VersionNumber__Group_1__0__Impl rule__VersionNumber__Group_1__1
            {
            pushFollow(FOLLOW_9);
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
    // InternalSEMVER.g:747:1: rule__VersionNumber__Group_1__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:751:1: ( ( '.' ) )
            // InternalSEMVER.g:752:1: ( '.' )
            {
            // InternalSEMVER.g:752:1: ( '.' )
            // InternalSEMVER.g:753:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); 
            match(input,41,FOLLOW_2); 
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
    // InternalSEMVER.g:762:1: rule__VersionNumber__Group_1__1 : rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 ;
    public final void rule__VersionNumber__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:766:1: ( rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2 )
            // InternalSEMVER.g:767:2: rule__VersionNumber__Group_1__1__Impl rule__VersionNumber__Group_1__2
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
    // InternalSEMVER.g:774:1: rule__VersionNumber__Group_1__1__Impl : ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) ;
    public final void rule__VersionNumber__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:778:1: ( ( ( rule__VersionNumber__MinorAssignment_1_1 ) ) )
            // InternalSEMVER.g:779:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            {
            // InternalSEMVER.g:779:1: ( ( rule__VersionNumber__MinorAssignment_1_1 ) )
            // InternalSEMVER.g:780:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); 
            // InternalSEMVER.g:781:2: ( rule__VersionNumber__MinorAssignment_1_1 )
            // InternalSEMVER.g:781:3: rule__VersionNumber__MinorAssignment_1_1
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
    // InternalSEMVER.g:789:1: rule__VersionNumber__Group_1__2 : rule__VersionNumber__Group_1__2__Impl ;
    public final void rule__VersionNumber__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:793:1: ( rule__VersionNumber__Group_1__2__Impl )
            // InternalSEMVER.g:794:2: rule__VersionNumber__Group_1__2__Impl
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
    // InternalSEMVER.g:800:1: rule__VersionNumber__Group_1__2__Impl : ( ( rule__VersionNumber__Group_1_2__0 )? ) ;
    public final void rule__VersionNumber__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:804:1: ( ( ( rule__VersionNumber__Group_1_2__0 )? ) )
            // InternalSEMVER.g:805:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            {
            // InternalSEMVER.g:805:1: ( ( rule__VersionNumber__Group_1_2__0 )? )
            // InternalSEMVER.g:806:2: ( rule__VersionNumber__Group_1_2__0 )?
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); 
            // InternalSEMVER.g:807:2: ( rule__VersionNumber__Group_1_2__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==41) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSEMVER.g:807:3: rule__VersionNumber__Group_1_2__0
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
    // InternalSEMVER.g:816:1: rule__VersionNumber__Group_1_2__0 : rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 ;
    public final void rule__VersionNumber__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:820:1: ( rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1 )
            // InternalSEMVER.g:821:2: rule__VersionNumber__Group_1_2__0__Impl rule__VersionNumber__Group_1_2__1
            {
            pushFollow(FOLLOW_9);
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
    // InternalSEMVER.g:828:1: rule__VersionNumber__Group_1_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:832:1: ( ( '.' ) )
            // InternalSEMVER.g:833:1: ( '.' )
            {
            // InternalSEMVER.g:833:1: ( '.' )
            // InternalSEMVER.g:834:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); 
            match(input,41,FOLLOW_2); 
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
    // InternalSEMVER.g:843:1: rule__VersionNumber__Group_1_2__1 : rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 ;
    public final void rule__VersionNumber__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:847:1: ( rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2 )
            // InternalSEMVER.g:848:2: rule__VersionNumber__Group_1_2__1__Impl rule__VersionNumber__Group_1_2__2
            {
            pushFollow(FOLLOW_10);
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
    // InternalSEMVER.g:855:1: rule__VersionNumber__Group_1_2__1__Impl : ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:859:1: ( ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) ) )
            // InternalSEMVER.g:860:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            {
            // InternalSEMVER.g:860:1: ( ( rule__VersionNumber__PatchAssignment_1_2_1 ) )
            // InternalSEMVER.g:861:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); 
            // InternalSEMVER.g:862:2: ( rule__VersionNumber__PatchAssignment_1_2_1 )
            // InternalSEMVER.g:862:3: rule__VersionNumber__PatchAssignment_1_2_1
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
    // InternalSEMVER.g:870:1: rule__VersionNumber__Group_1_2__2 : rule__VersionNumber__Group_1_2__2__Impl ;
    public final void rule__VersionNumber__Group_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:874:1: ( rule__VersionNumber__Group_1_2__2__Impl )
            // InternalSEMVER.g:875:2: rule__VersionNumber__Group_1_2__2__Impl
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
    // InternalSEMVER.g:881:1: rule__VersionNumber__Group_1_2__2__Impl : ( ( rule__VersionNumber__Group_1_2_2__0 )* ) ;
    public final void rule__VersionNumber__Group_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:885:1: ( ( ( rule__VersionNumber__Group_1_2_2__0 )* ) )
            // InternalSEMVER.g:886:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            {
            // InternalSEMVER.g:886:1: ( ( rule__VersionNumber__Group_1_2_2__0 )* )
            // InternalSEMVER.g:887:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            {
             before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); 
            // InternalSEMVER.g:888:2: ( rule__VersionNumber__Group_1_2_2__0 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==41) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSEMVER.g:888:3: rule__VersionNumber__Group_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__VersionNumber__Group_1_2_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
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
    // InternalSEMVER.g:897:1: rule__VersionNumber__Group_1_2_2__0 : rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 ;
    public final void rule__VersionNumber__Group_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:901:1: ( rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1 )
            // InternalSEMVER.g:902:2: rule__VersionNumber__Group_1_2_2__0__Impl rule__VersionNumber__Group_1_2_2__1
            {
            pushFollow(FOLLOW_9);
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
    // InternalSEMVER.g:909:1: rule__VersionNumber__Group_1_2_2__0__Impl : ( '.' ) ;
    public final void rule__VersionNumber__Group_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:913:1: ( ( '.' ) )
            // InternalSEMVER.g:914:1: ( '.' )
            {
            // InternalSEMVER.g:914:1: ( '.' )
            // InternalSEMVER.g:915:2: '.'
            {
             before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); 
            match(input,41,FOLLOW_2); 
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
    // InternalSEMVER.g:924:1: rule__VersionNumber__Group_1_2_2__1 : rule__VersionNumber__Group_1_2_2__1__Impl ;
    public final void rule__VersionNumber__Group_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:928:1: ( rule__VersionNumber__Group_1_2_2__1__Impl )
            // InternalSEMVER.g:929:2: rule__VersionNumber__Group_1_2_2__1__Impl
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
    // InternalSEMVER.g:935:1: rule__VersionNumber__Group_1_2_2__1__Impl : ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) ;
    public final void rule__VersionNumber__Group_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:939:1: ( ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) ) )
            // InternalSEMVER.g:940:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            {
            // InternalSEMVER.g:940:1: ( ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 ) )
            // InternalSEMVER.g:941:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            {
             before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); 
            // InternalSEMVER.g:942:2: ( rule__VersionNumber__ExtendedAssignment_1_2_2_1 )
            // InternalSEMVER.g:942:3: rule__VersionNumber__ExtendedAssignment_1_2_2_1
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
    // InternalSEMVER.g:951:1: rule__Qualifier__Group_0__0 : rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 ;
    public final void rule__Qualifier__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:955:1: ( rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1 )
            // InternalSEMVER.g:956:2: rule__Qualifier__Group_0__0__Impl rule__Qualifier__Group_0__1
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
    // InternalSEMVER.g:963:1: rule__Qualifier__Group_0__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:967:1: ( ( '-' ) )
            // InternalSEMVER.g:968:1: ( '-' )
            {
            // InternalSEMVER.g:968:1: ( '-' )
            // InternalSEMVER.g:969:2: '-'
            {
             before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); 
            match(input,40,FOLLOW_2); 
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
    // InternalSEMVER.g:978:1: rule__Qualifier__Group_0__1 : rule__Qualifier__Group_0__1__Impl ;
    public final void rule__Qualifier__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:982:1: ( rule__Qualifier__Group_0__1__Impl )
            // InternalSEMVER.g:983:2: rule__Qualifier__Group_0__1__Impl
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
    // InternalSEMVER.g:989:1: rule__Qualifier__Group_0__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) ;
    public final void rule__Qualifier__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:993:1: ( ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) ) )
            // InternalSEMVER.g:994:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            {
            // InternalSEMVER.g:994:1: ( ( rule__Qualifier__PreReleaseAssignment_0_1 ) )
            // InternalSEMVER.g:995:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); 
            // InternalSEMVER.g:996:2: ( rule__Qualifier__PreReleaseAssignment_0_1 )
            // InternalSEMVER.g:996:3: rule__Qualifier__PreReleaseAssignment_0_1
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
    // InternalSEMVER.g:1005:1: rule__Qualifier__Group_1__0 : rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 ;
    public final void rule__Qualifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1009:1: ( rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1 )
            // InternalSEMVER.g:1010:2: rule__Qualifier__Group_1__0__Impl rule__Qualifier__Group_1__1
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
    // InternalSEMVER.g:1017:1: rule__Qualifier__Group_1__0__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1021:1: ( ( '+' ) )
            // InternalSEMVER.g:1022:1: ( '+' )
            {
            // InternalSEMVER.g:1022:1: ( '+' )
            // InternalSEMVER.g:1023:2: '+'
            {
             before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); 
            match(input,42,FOLLOW_2); 
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
    // InternalSEMVER.g:1032:1: rule__Qualifier__Group_1__1 : rule__Qualifier__Group_1__1__Impl ;
    public final void rule__Qualifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1036:1: ( rule__Qualifier__Group_1__1__Impl )
            // InternalSEMVER.g:1037:2: rule__Qualifier__Group_1__1__Impl
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
    // InternalSEMVER.g:1043:1: rule__Qualifier__Group_1__1__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) ;
    public final void rule__Qualifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1047:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) ) )
            // InternalSEMVER.g:1048:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            {
            // InternalSEMVER.g:1048:1: ( ( rule__Qualifier__BuildMetadataAssignment_1_1 ) )
            // InternalSEMVER.g:1049:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); 
            // InternalSEMVER.g:1050:2: ( rule__Qualifier__BuildMetadataAssignment_1_1 )
            // InternalSEMVER.g:1050:3: rule__Qualifier__BuildMetadataAssignment_1_1
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
    // InternalSEMVER.g:1059:1: rule__Qualifier__Group_2__0 : rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 ;
    public final void rule__Qualifier__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1063:1: ( rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1 )
            // InternalSEMVER.g:1064:2: rule__Qualifier__Group_2__0__Impl rule__Qualifier__Group_2__1
            {
            pushFollow(FOLLOW_12);
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
    // InternalSEMVER.g:1071:1: rule__Qualifier__Group_2__0__Impl : ( '-' ) ;
    public final void rule__Qualifier__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1075:1: ( ( '-' ) )
            // InternalSEMVER.g:1076:1: ( '-' )
            {
            // InternalSEMVER.g:1076:1: ( '-' )
            // InternalSEMVER.g:1077:2: '-'
            {
             before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); 
            match(input,40,FOLLOW_2); 
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
    // InternalSEMVER.g:1086:1: rule__Qualifier__Group_2__1 : rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 ;
    public final void rule__Qualifier__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1090:1: ( rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2 )
            // InternalSEMVER.g:1091:2: rule__Qualifier__Group_2__1__Impl rule__Qualifier__Group_2__2
            {
            pushFollow(FOLLOW_13);
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
    // InternalSEMVER.g:1098:1: rule__Qualifier__Group_2__1__Impl : ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) ;
    public final void rule__Qualifier__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1102:1: ( ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) ) )
            // InternalSEMVER.g:1103:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            {
            // InternalSEMVER.g:1103:1: ( ( rule__Qualifier__PreReleaseAssignment_2_1 ) )
            // InternalSEMVER.g:1104:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); 
            // InternalSEMVER.g:1105:2: ( rule__Qualifier__PreReleaseAssignment_2_1 )
            // InternalSEMVER.g:1105:3: rule__Qualifier__PreReleaseAssignment_2_1
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
    // InternalSEMVER.g:1113:1: rule__Qualifier__Group_2__2 : rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 ;
    public final void rule__Qualifier__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1117:1: ( rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3 )
            // InternalSEMVER.g:1118:2: rule__Qualifier__Group_2__2__Impl rule__Qualifier__Group_2__3
            {
            pushFollow(FOLLOW_12);
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
    // InternalSEMVER.g:1125:1: rule__Qualifier__Group_2__2__Impl : ( '+' ) ;
    public final void rule__Qualifier__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1129:1: ( ( '+' ) )
            // InternalSEMVER.g:1130:1: ( '+' )
            {
            // InternalSEMVER.g:1130:1: ( '+' )
            // InternalSEMVER.g:1131:2: '+'
            {
             before(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); 
            match(input,42,FOLLOW_2); 
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
    // InternalSEMVER.g:1140:1: rule__Qualifier__Group_2__3 : rule__Qualifier__Group_2__3__Impl ;
    public final void rule__Qualifier__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1144:1: ( rule__Qualifier__Group_2__3__Impl )
            // InternalSEMVER.g:1145:2: rule__Qualifier__Group_2__3__Impl
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
    // InternalSEMVER.g:1151:1: rule__Qualifier__Group_2__3__Impl : ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) ;
    public final void rule__Qualifier__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1155:1: ( ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) ) )
            // InternalSEMVER.g:1156:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            {
            // InternalSEMVER.g:1156:1: ( ( rule__Qualifier__BuildMetadataAssignment_2_3 ) )
            // InternalSEMVER.g:1157:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); 
            // InternalSEMVER.g:1158:2: ( rule__Qualifier__BuildMetadataAssignment_2_3 )
            // InternalSEMVER.g:1158:3: rule__Qualifier__BuildMetadataAssignment_2_3
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


    // $ANTLR start "rule__VersionRangeSet__RangesAssignment_1"
    // InternalSEMVER.g:1167:1: rule__VersionRangeSet__RangesAssignment_1 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1171:1: ( ( ruleVersionRange ) )
            // InternalSEMVER.g:1172:2: ( ruleVersionRange )
            {
            // InternalSEMVER.g:1172:2: ( ruleVersionRange )
            // InternalSEMVER.g:1173:3: ruleVersionRange
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;

             after(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__RangesAssignment_1"


    // $ANTLR start "rule__VersionRangeSet__RangesAssignment_2_1"
    // InternalSEMVER.g:1182:1: rule__VersionRangeSet__RangesAssignment_2_1 : ( ruleVersionRange ) ;
    public final void rule__VersionRangeSet__RangesAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1186:1: ( ( ruleVersionRange ) )
            // InternalSEMVER.g:1187:2: ( ruleVersionRange )
            {
            // InternalSEMVER.g:1187:2: ( ruleVersionRange )
            // InternalSEMVER.g:1188:3: ruleVersionRange
            {
             before(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionRange();

            state._fsp--;

             after(grammarAccess.getVersionRangeSetAccess().getRangesVersionRangeParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionRangeSet__RangesAssignment_2_1"


    // $ANTLR start "rule__HyphenVersionRange__FromAssignment_1"
    // InternalSEMVER.g:1197:1: rule__HyphenVersionRange__FromAssignment_1 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__FromAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1201:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1202:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1202:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1203:3: ruleVersionNumber
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
    // InternalSEMVER.g:1212:1: rule__HyphenVersionRange__ToAssignment_3 : ( ruleVersionNumber ) ;
    public final void rule__HyphenVersionRange__ToAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1216:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1217:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1217:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1218:3: ruleVersionNumber
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


    // $ANTLR start "rule__SimpleVersion__ComparatorsAssignment_1"
    // InternalSEMVER.g:1227:1: rule__SimpleVersion__ComparatorsAssignment_1 : ( ruleVersionComparator ) ;
    public final void rule__SimpleVersion__ComparatorsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1231:1: ( ( ruleVersionComparator ) )
            // InternalSEMVER.g:1232:2: ( ruleVersionComparator )
            {
            // InternalSEMVER.g:1232:2: ( ruleVersionComparator )
            // InternalSEMVER.g:1233:3: ruleVersionComparator
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
    // InternalSEMVER.g:1242:1: rule__SimpleVersion__NumberAssignment_2 : ( ruleVersionNumber ) ;
    public final void rule__SimpleVersion__NumberAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1246:1: ( ( ruleVersionNumber ) )
            // InternalSEMVER.g:1247:2: ( ruleVersionNumber )
            {
            // InternalSEMVER.g:1247:2: ( ruleVersionNumber )
            // InternalSEMVER.g:1248:3: ruleVersionNumber
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
    // InternalSEMVER.g:1257:1: rule__VersionNumber__MajorAssignment_0 : ( RULE_VERSION_PART ) ;
    public final void rule__VersionNumber__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1261:1: ( ( RULE_VERSION_PART ) )
            // InternalSEMVER.g:1262:2: ( RULE_VERSION_PART )
            {
            // InternalSEMVER.g:1262:2: ( RULE_VERSION_PART )
            // InternalSEMVER.g:1263:3: RULE_VERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getMajorVERSION_PARTTerminalRuleCall_0_0()); 
            match(input,RULE_VERSION_PART,FOLLOW_2); 
             after(grammarAccess.getVersionNumberAccess().getMajorVERSION_PARTTerminalRuleCall_0_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1272:1: rule__VersionNumber__MinorAssignment_1_1 : ( RULE_VERSION_PART ) ;
    public final void rule__VersionNumber__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1276:1: ( ( RULE_VERSION_PART ) )
            // InternalSEMVER.g:1277:2: ( RULE_VERSION_PART )
            {
            // InternalSEMVER.g:1277:2: ( RULE_VERSION_PART )
            // InternalSEMVER.g:1278:3: RULE_VERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getMinorVERSION_PARTTerminalRuleCall_1_1_0()); 
            match(input,RULE_VERSION_PART,FOLLOW_2); 
             after(grammarAccess.getVersionNumberAccess().getMinorVERSION_PARTTerminalRuleCall_1_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1287:1: rule__VersionNumber__PatchAssignment_1_2_1 : ( RULE_VERSION_PART ) ;
    public final void rule__VersionNumber__PatchAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1291:1: ( ( RULE_VERSION_PART ) )
            // InternalSEMVER.g:1292:2: ( RULE_VERSION_PART )
            {
            // InternalSEMVER.g:1292:2: ( RULE_VERSION_PART )
            // InternalSEMVER.g:1293:3: RULE_VERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getPatchVERSION_PARTTerminalRuleCall_1_2_1_0()); 
            match(input,RULE_VERSION_PART,FOLLOW_2); 
             after(grammarAccess.getVersionNumberAccess().getPatchVERSION_PARTTerminalRuleCall_1_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1302:1: rule__VersionNumber__ExtendedAssignment_1_2_2_1 : ( RULE_VERSION_PART ) ;
    public final void rule__VersionNumber__ExtendedAssignment_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1306:1: ( ( RULE_VERSION_PART ) )
            // InternalSEMVER.g:1307:2: ( RULE_VERSION_PART )
            {
            // InternalSEMVER.g:1307:2: ( RULE_VERSION_PART )
            // InternalSEMVER.g:1308:3: RULE_VERSION_PART
            {
             before(grammarAccess.getVersionNumberAccess().getExtendedVERSION_PARTTerminalRuleCall_1_2_2_1_0()); 
            match(input,RULE_VERSION_PART,FOLLOW_2); 
             after(grammarAccess.getVersionNumberAccess().getExtendedVERSION_PARTTerminalRuleCall_1_2_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1317:1: rule__VersionNumber__QualifierAssignment_2 : ( ruleQualifier ) ;
    public final void rule__VersionNumber__QualifierAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1321:1: ( ( ruleQualifier ) )
            // InternalSEMVER.g:1322:2: ( ruleQualifier )
            {
            // InternalSEMVER.g:1322:2: ( ruleQualifier )
            // InternalSEMVER.g:1323:3: ruleQualifier
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
    // InternalSEMVER.g:1332:1: rule__Qualifier__PreReleaseAssignment_0_1 : ( RULE_ALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__PreReleaseAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1336:1: ( ( RULE_ALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1337:2: ( RULE_ALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1337:2: ( RULE_ALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1338:3: RULE_ALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSTerminalRuleCall_0_1_0()); 
            match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSTerminalRuleCall_0_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1347:1: rule__Qualifier__BuildMetadataAssignment_1_1 : ( RULE_ALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1351:1: ( ( RULE_ALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1352:2: ( RULE_ALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1352:2: ( RULE_ALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1353:3: RULE_ALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSTerminalRuleCall_1_1_0()); 
            match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSTerminalRuleCall_1_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1362:1: rule__Qualifier__PreReleaseAssignment_2_1 : ( RULE_ALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__PreReleaseAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1366:1: ( ( RULE_ALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1367:2: ( RULE_ALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1367:2: ( RULE_ALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1368:3: RULE_ALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSTerminalRuleCall_2_1_0()); 
            match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getPreReleaseALPHA_NUMERIC_CHARSTerminalRuleCall_2_1_0()); 

            }


            }

        }
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
    // InternalSEMVER.g:1377:1: rule__Qualifier__BuildMetadataAssignment_2_3 : ( RULE_ALPHA_NUMERIC_CHARS ) ;
    public final void rule__Qualifier__BuildMetadataAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSEMVER.g:1381:1: ( ( RULE_ALPHA_NUMERIC_CHARS ) )
            // InternalSEMVER.g:1382:2: ( RULE_ALPHA_NUMERIC_CHARS )
            {
            // InternalSEMVER.g:1382:2: ( RULE_ALPHA_NUMERIC_CHARS )
            // InternalSEMVER.g:1383:3: RULE_ALPHA_NUMERIC_CHARS
            {
             before(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSTerminalRuleCall_2_3_0()); 
            match(input,RULE_ALPHA_NUMERIC_CHARS,FOLLOW_2); 
             after(grammarAccess.getQualifierAccess().getBuildMetadataALPHA_NUMERIC_CHARSTerminalRuleCall_2_3_0()); 

            }


            }

        }
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


    protected DFA1 dfa1 = new DFA1(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\2\uffff\1\1\3\uffff\2\1\1\uffff\1\1\2\uffff\2\1\1\uffff\1\1";
    static final String dfa_3s = "\1\4\1\uffff\1\47\2\4\1\5\2\47\1\uffff\1\47\1\4\1\5\2\47\1\4\1\47";
    static final String dfa_4s = "\1\46\1\uffff\1\52\1\4\2\5\2\52\1\uffff\1\50\1\4\1\5\1\52\1\50\1\4\1\52";
    static final String dfa_5s = "\1\uffff\1\1\6\uffff\1\2\7\uffff";
    static final String dfa_6s = "\20\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\32\uffff\10\1",
            "",
            "\1\1\1\4\1\3\1\5",
            "\1\6",
            "\1\10\1\7",
            "\1\11",
            "\1\1\1\4\1\12\1\5",
            "\1\1\1\10\1\uffff\1\13",
            "",
            "\1\1\1\10",
            "\1\14",
            "\1\15",
            "\1\1\1\4\1\16\1\5",
            "\1\1\1\10",
            "\1\17",
            "\1\1\1\4\1\16\1\5"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "225:1: rule__VersionRange__Alternatives : ( ( ruleSimpleVersion ) | ( ruleHyphenVersionRange ) );";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000FF80000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000007F80000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000007F80000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000070000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000040000000000L});

}