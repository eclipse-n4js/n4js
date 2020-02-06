package org.eclipse.n4js.regex.ide.contentassist.antlr.internal;
import java.util.Map;
import java.util.HashMap;

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
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;



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
public class InternalRegularExpressionParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ExclamationMark", "DollarSign", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "LessThanSign", "EqualsSign", "GreaterThanSign", "QuestionMark", "LeftSquareBracket", "RightSquareBracket", "CircumflexAccent", "KW__", "LeftCurlyBracket", "VerticalLine", "RightCurlyBracket", "RULE_WORD_BOUNDARY", "RULE_NOT_WORD_BOUNDARY", "RULE_CHARACTER_CLASS_ESCAPE", "RULE_CONTROL_ESCAPE", "RULE_CONTROL_LETTER_ESCAPE", "RULE_HEX_DIGIT", "RULE_HEX_ESCAPE", "RULE_UNICODE_ESCAPE", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_DECIMAL_ESCAPE", "RULE_IDENTITY_ESCAPE", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_DIGIT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_UNICODE_LETTER", "RULE_PATTERN_CHARACTER_NO_DASH", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_ANY_OTHER"
    };
    public static final int LessThanSign=15;
    public static final int RULE_WHITESPACE_FRAGMENT=47;
    public static final int LeftParenthesis=6;
    public static final int RULE_HEX_ESCAPE=32;
    public static final int RightSquareBracket=20;
    public static final int ExclamationMark=4;
    public static final int GreaterThanSign=17;
    public static final int RULE_CONTROL_LETTER_ESCAPE=30;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=49;
    public static final int RightParenthesis=7;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=52;
    public static final int RULE_ZWNJ=44;
    public static final int VerticalLine=24;
    public static final int PlusSign=9;
    public static final int LeftSquareBracket=19;
    public static final int RULE_DECIMAL_ESCAPE=35;
    public static final int RULE_ML_COMMENT_FRAGMENT=51;
    public static final int RULE_PATTERN_CHARACTER_NO_DASH=41;
    public static final int RULE_WORD_BOUNDARY=26;
    public static final int RULE_UNICODE_ESCAPE=33;
    public static final int Comma=10;
    public static final int EqualsSign=16;
    public static final int RULE_ZWJ=43;
    public static final int RULE_SL_COMMENT_FRAGMENT=50;
    public static final int HyphenMinus=11;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=37;
    public static final int RULE_UNICODE_LETTER=40;
    public static final int RULE_CONTROL_ESCAPE=29;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=46;
    public static final int Solidus=13;
    public static final int Colon=14;
    public static final int RightCurlyBracket=25;
    public static final int RULE_CHARACTER_CLASS_ESCAPE=28;
    public static final int EOF=-1;
    public static final int Asterisk=8;
    public static final int FullStop=12;
    public static final int RULE_UNICODE_DIGIT=38;
    public static final int RULE_BOM=45;
    public static final int LeftCurlyBracket=23;
    public static final int RULE_ANY_OTHER=54;
    public static final int CircumflexAccent=21;
    public static final int RULE_NOT_WORD_BOUNDARY=27;
    public static final int KW__=22;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=48;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=39;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=42;
    public static final int QuestionMark=18;
    public static final int DollarSign=5;
    public static final int RULE_HEX_DIGIT=31;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=34;
    public static final int RULE_IDENTITY_ESCAPE=36;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=53;

    // delegates
    // delegators


        public InternalRegularExpressionParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalRegularExpressionParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalRegularExpressionParser.tokenNames; }
    public String getGrammarFileName() { return "InternalRegularExpressionParser.g"; }


    	private RegularExpressionGrammarAccess grammarAccess;
    	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
    	
    	{
    		tokenNameToValue.put("ExclamationMark", "'!'");
    		tokenNameToValue.put("DollarSign", "'\\u0024'");
    		tokenNameToValue.put("LeftParenthesis", "'('");
    		tokenNameToValue.put("RightParenthesis", "')'");
    		tokenNameToValue.put("Asterisk", "'*'");
    		tokenNameToValue.put("PlusSign", "'+'");
    		tokenNameToValue.put("Comma", "','");
    		tokenNameToValue.put("HyphenMinus", "'-'");
    		tokenNameToValue.put("FullStop", "'.'");
    		tokenNameToValue.put("Solidus", "'/'");
    		tokenNameToValue.put("Colon", "':'");
    		tokenNameToValue.put("LessThanSign", "'<'");
    		tokenNameToValue.put("EqualsSign", "'='");
    		tokenNameToValue.put("GreaterThanSign", "'>'");
    		tokenNameToValue.put("QuestionMark", "'?'");
    		tokenNameToValue.put("LeftSquareBracket", "'['");
    		tokenNameToValue.put("RightSquareBracket", "']'");
    		tokenNameToValue.put("CircumflexAccent", "'^'");
    		tokenNameToValue.put("KW__", "'_'");
    		tokenNameToValue.put("LeftCurlyBracket", "'{'");
    		tokenNameToValue.put("VerticalLine", "'|'");
    		tokenNameToValue.put("RightCurlyBracket", "'}'");
    	}

    	public void setGrammarAccess(RegularExpressionGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		String result = tokenNameToValue.get(tokenName);
    		if (result == null)
    			result = tokenName;
    		return result;
    	}



    // $ANTLR start "entryRuleRegularExpressionLiteral"
    // InternalRegularExpressionParser.g:85:1: entryRuleRegularExpressionLiteral : ruleRegularExpressionLiteral EOF ;
    public final void entryRuleRegularExpressionLiteral() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:86:1: ( ruleRegularExpressionLiteral EOF )
            // InternalRegularExpressionParser.g:87:1: ruleRegularExpressionLiteral EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRegularExpressionLiteral();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralRule()); 
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
    // $ANTLR end "entryRuleRegularExpressionLiteral"


    // $ANTLR start "ruleRegularExpressionLiteral"
    // InternalRegularExpressionParser.g:94:1: ruleRegularExpressionLiteral : ( ( rule__RegularExpressionLiteral__Group__0 ) ) ;
    public final void ruleRegularExpressionLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:98:2: ( ( ( rule__RegularExpressionLiteral__Group__0 ) ) )
            // InternalRegularExpressionParser.g:99:2: ( ( rule__RegularExpressionLiteral__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:99:2: ( ( rule__RegularExpressionLiteral__Group__0 ) )
            // InternalRegularExpressionParser.g:100:3: ( rule__RegularExpressionLiteral__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:101:3: ( rule__RegularExpressionLiteral__Group__0 )
            // InternalRegularExpressionParser.g:101:4: rule__RegularExpressionLiteral__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionLiteral__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRegularExpressionLiteral"


    // $ANTLR start "entryRuleRegularExpressionBody"
    // InternalRegularExpressionParser.g:110:1: entryRuleRegularExpressionBody : ruleRegularExpressionBody EOF ;
    public final void entryRuleRegularExpressionBody() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:111:1: ( ruleRegularExpressionBody EOF )
            // InternalRegularExpressionParser.g:112:1: ruleRegularExpressionBody EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRegularExpressionBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionBodyRule()); 
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
    // $ANTLR end "entryRuleRegularExpressionBody"


    // $ANTLR start "ruleRegularExpressionBody"
    // InternalRegularExpressionParser.g:119:1: ruleRegularExpressionBody : ( ( rule__RegularExpressionBody__PatternAssignment ) ) ;
    public final void ruleRegularExpressionBody() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:123:2: ( ( ( rule__RegularExpressionBody__PatternAssignment ) ) )
            // InternalRegularExpressionParser.g:124:2: ( ( rule__RegularExpressionBody__PatternAssignment ) )
            {
            // InternalRegularExpressionParser.g:124:2: ( ( rule__RegularExpressionBody__PatternAssignment ) )
            // InternalRegularExpressionParser.g:125:3: ( rule__RegularExpressionBody__PatternAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionBodyAccess().getPatternAssignment()); 
            }
            // InternalRegularExpressionParser.g:126:3: ( rule__RegularExpressionBody__PatternAssignment )
            // InternalRegularExpressionParser.g:126:4: rule__RegularExpressionBody__PatternAssignment
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionBody__PatternAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionBodyAccess().getPatternAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRegularExpressionBody"


    // $ANTLR start "entryRuleDisjunction"
    // InternalRegularExpressionParser.g:135:1: entryRuleDisjunction : ruleDisjunction EOF ;
    public final void entryRuleDisjunction() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:136:1: ( ruleDisjunction EOF )
            // InternalRegularExpressionParser.g:137:1: ruleDisjunction EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleDisjunction();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionRule()); 
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
    // $ANTLR end "entryRuleDisjunction"


    // $ANTLR start "ruleDisjunction"
    // InternalRegularExpressionParser.g:144:1: ruleDisjunction : ( ( rule__Disjunction__Alternatives ) ) ;
    public final void ruleDisjunction() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:148:2: ( ( ( rule__Disjunction__Alternatives ) ) )
            // InternalRegularExpressionParser.g:149:2: ( ( rule__Disjunction__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:149:2: ( ( rule__Disjunction__Alternatives ) )
            // InternalRegularExpressionParser.g:150:3: ( rule__Disjunction__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:151:3: ( rule__Disjunction__Alternatives )
            // InternalRegularExpressionParser.g:151:4: rule__Disjunction__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Disjunction__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDisjunction"


    // $ANTLR start "entryRuleAlternative"
    // InternalRegularExpressionParser.g:160:1: entryRuleAlternative : ruleAlternative EOF ;
    public final void entryRuleAlternative() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:161:1: ( ruleAlternative EOF )
            // InternalRegularExpressionParser.g:162:1: ruleAlternative EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleAlternative();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeRule()); 
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
    // $ANTLR end "entryRuleAlternative"


    // $ANTLR start "ruleAlternative"
    // InternalRegularExpressionParser.g:169:1: ruleAlternative : ( ( rule__Alternative__Group__0 ) ) ;
    public final void ruleAlternative() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:173:2: ( ( ( rule__Alternative__Group__0 ) ) )
            // InternalRegularExpressionParser.g:174:2: ( ( rule__Alternative__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:174:2: ( ( rule__Alternative__Group__0 ) )
            // InternalRegularExpressionParser.g:175:3: ( rule__Alternative__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:176:3: ( rule__Alternative__Group__0 )
            // InternalRegularExpressionParser.g:176:4: rule__Alternative__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Alternative__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAlternative"


    // $ANTLR start "entryRuleTerm"
    // InternalRegularExpressionParser.g:185:1: entryRuleTerm : ruleTerm EOF ;
    public final void entryRuleTerm() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:186:1: ( ruleTerm EOF )
            // InternalRegularExpressionParser.g:187:1: ruleTerm EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTermRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleTerm();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTermRule()); 
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
    // $ANTLR end "entryRuleTerm"


    // $ANTLR start "ruleTerm"
    // InternalRegularExpressionParser.g:194:1: ruleTerm : ( ( rule__Term__Alternatives ) ) ;
    public final void ruleTerm() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:198:2: ( ( ( rule__Term__Alternatives ) ) )
            // InternalRegularExpressionParser.g:199:2: ( ( rule__Term__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:199:2: ( ( rule__Term__Alternatives ) )
            // InternalRegularExpressionParser.g:200:3: ( rule__Term__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTermAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:201:3: ( rule__Term__Alternatives )
            // InternalRegularExpressionParser.g:201:4: rule__Term__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Term__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTermAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTerm"


    // $ANTLR start "entryRuleAssertion"
    // InternalRegularExpressionParser.g:210:1: entryRuleAssertion : ruleAssertion EOF ;
    public final void entryRuleAssertion() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:211:1: ( ruleAssertion EOF )
            // InternalRegularExpressionParser.g:212:1: ruleAssertion EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssertionRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleAssertion();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssertionRule()); 
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
    // $ANTLR end "entryRuleAssertion"


    // $ANTLR start "ruleAssertion"
    // InternalRegularExpressionParser.g:219:1: ruleAssertion : ( ( rule__Assertion__Alternatives ) ) ;
    public final void ruleAssertion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:223:2: ( ( ( rule__Assertion__Alternatives ) ) )
            // InternalRegularExpressionParser.g:224:2: ( ( rule__Assertion__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:224:2: ( ( rule__Assertion__Alternatives ) )
            // InternalRegularExpressionParser.g:225:3: ( rule__Assertion__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssertionAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:226:3: ( rule__Assertion__Alternatives )
            // InternalRegularExpressionParser.g:226:4: rule__Assertion__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Assertion__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssertionAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAssertion"


    // $ANTLR start "entryRuleLineStart"
    // InternalRegularExpressionParser.g:235:1: entryRuleLineStart : ruleLineStart EOF ;
    public final void entryRuleLineStart() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:236:1: ( ruleLineStart EOF )
            // InternalRegularExpressionParser.g:237:1: ruleLineStart EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineStartRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLineStart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineStartRule()); 
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
    // $ANTLR end "entryRuleLineStart"


    // $ANTLR start "ruleLineStart"
    // InternalRegularExpressionParser.g:244:1: ruleLineStart : ( ( rule__LineStart__Group__0 ) ) ;
    public final void ruleLineStart() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:248:2: ( ( ( rule__LineStart__Group__0 ) ) )
            // InternalRegularExpressionParser.g:249:2: ( ( rule__LineStart__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:249:2: ( ( rule__LineStart__Group__0 ) )
            // InternalRegularExpressionParser.g:250:3: ( rule__LineStart__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineStartAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:251:3: ( rule__LineStart__Group__0 )
            // InternalRegularExpressionParser.g:251:4: rule__LineStart__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LineStart__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineStartAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLineStart"


    // $ANTLR start "entryRuleLineEnd"
    // InternalRegularExpressionParser.g:260:1: entryRuleLineEnd : ruleLineEnd EOF ;
    public final void entryRuleLineEnd() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:261:1: ( ruleLineEnd EOF )
            // InternalRegularExpressionParser.g:262:1: ruleLineEnd EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineEndRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLineEnd();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineEndRule()); 
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
    // $ANTLR end "entryRuleLineEnd"


    // $ANTLR start "ruleLineEnd"
    // InternalRegularExpressionParser.g:269:1: ruleLineEnd : ( ( rule__LineEnd__Group__0 ) ) ;
    public final void ruleLineEnd() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:273:2: ( ( ( rule__LineEnd__Group__0 ) ) )
            // InternalRegularExpressionParser.g:274:2: ( ( rule__LineEnd__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:274:2: ( ( rule__LineEnd__Group__0 ) )
            // InternalRegularExpressionParser.g:275:3: ( rule__LineEnd__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineEndAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:276:3: ( rule__LineEnd__Group__0 )
            // InternalRegularExpressionParser.g:276:4: rule__LineEnd__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LineEnd__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineEndAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLineEnd"


    // $ANTLR start "entryRuleWordBoundary"
    // InternalRegularExpressionParser.g:285:1: entryRuleWordBoundary : ruleWordBoundary EOF ;
    public final void entryRuleWordBoundary() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:286:1: ( ruleWordBoundary EOF )
            // InternalRegularExpressionParser.g:287:1: ruleWordBoundary EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleWordBoundary();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWordBoundaryRule()); 
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
    // $ANTLR end "entryRuleWordBoundary"


    // $ANTLR start "ruleWordBoundary"
    // InternalRegularExpressionParser.g:294:1: ruleWordBoundary : ( ( rule__WordBoundary__Group__0 ) ) ;
    public final void ruleWordBoundary() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:298:2: ( ( ( rule__WordBoundary__Group__0 ) ) )
            // InternalRegularExpressionParser.g:299:2: ( ( rule__WordBoundary__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:299:2: ( ( rule__WordBoundary__Group__0 ) )
            // InternalRegularExpressionParser.g:300:3: ( rule__WordBoundary__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:301:3: ( rule__WordBoundary__Group__0 )
            // InternalRegularExpressionParser.g:301:4: rule__WordBoundary__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__WordBoundary__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWordBoundaryAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWordBoundary"


    // $ANTLR start "entryRuleLookAhead"
    // InternalRegularExpressionParser.g:310:1: entryRuleLookAhead : ruleLookAhead EOF ;
    public final void entryRuleLookAhead() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:311:1: ( ruleLookAhead EOF )
            // InternalRegularExpressionParser.g:312:1: ruleLookAhead EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLookAhead();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadRule()); 
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
    // $ANTLR end "entryRuleLookAhead"


    // $ANTLR start "ruleLookAhead"
    // InternalRegularExpressionParser.g:319:1: ruleLookAhead : ( ( rule__LookAhead__Group__0 ) ) ;
    public final void ruleLookAhead() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:323:2: ( ( ( rule__LookAhead__Group__0 ) ) )
            // InternalRegularExpressionParser.g:324:2: ( ( rule__LookAhead__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:324:2: ( ( rule__LookAhead__Group__0 ) )
            // InternalRegularExpressionParser.g:325:3: ( rule__LookAhead__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:326:3: ( rule__LookAhead__Group__0 )
            // InternalRegularExpressionParser.g:326:4: rule__LookAhead__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLookAhead"


    // $ANTLR start "entryRuleAtom"
    // InternalRegularExpressionParser.g:335:1: entryRuleAtom : ruleAtom EOF ;
    public final void entryRuleAtom() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:336:1: ( ruleAtom EOF )
            // InternalRegularExpressionParser.g:337:1: ruleAtom EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleAtom();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAtomRule()); 
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
    // $ANTLR end "entryRuleAtom"


    // $ANTLR start "ruleAtom"
    // InternalRegularExpressionParser.g:344:1: ruleAtom : ( ( rule__Atom__Alternatives ) ) ;
    public final void ruleAtom() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:348:2: ( ( ( rule__Atom__Alternatives ) ) )
            // InternalRegularExpressionParser.g:349:2: ( ( rule__Atom__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:349:2: ( ( rule__Atom__Alternatives ) )
            // InternalRegularExpressionParser.g:350:3: ( rule__Atom__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:351:3: ( rule__Atom__Alternatives )
            // InternalRegularExpressionParser.g:351:4: rule__Atom__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Atom__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAtomAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAtom"


    // $ANTLR start "entryRulePatternCharacter"
    // InternalRegularExpressionParser.g:360:1: entryRulePatternCharacter : rulePatternCharacter EOF ;
    public final void entryRulePatternCharacter() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:361:1: ( rulePatternCharacter EOF )
            // InternalRegularExpressionParser.g:362:1: rulePatternCharacter EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPatternCharacterRule()); 
            }
            pushFollow(FOLLOW_1);
            rulePatternCharacter();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPatternCharacterRule()); 
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
    // $ANTLR end "entryRulePatternCharacter"


    // $ANTLR start "rulePatternCharacter"
    // InternalRegularExpressionParser.g:369:1: rulePatternCharacter : ( ( rule__PatternCharacter__ValueAssignment ) ) ;
    public final void rulePatternCharacter() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:373:2: ( ( ( rule__PatternCharacter__ValueAssignment ) ) )
            // InternalRegularExpressionParser.g:374:2: ( ( rule__PatternCharacter__ValueAssignment ) )
            {
            // InternalRegularExpressionParser.g:374:2: ( ( rule__PatternCharacter__ValueAssignment ) )
            // InternalRegularExpressionParser.g:375:3: ( rule__PatternCharacter__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPatternCharacterAccess().getValueAssignment()); 
            }
            // InternalRegularExpressionParser.g:376:3: ( rule__PatternCharacter__ValueAssignment )
            // InternalRegularExpressionParser.g:376:4: rule__PatternCharacter__ValueAssignment
            {
            pushFollow(FOLLOW_2);
            rule__PatternCharacter__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPatternCharacterAccess().getValueAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePatternCharacter"


    // $ANTLR start "entryRuleWildcard"
    // InternalRegularExpressionParser.g:385:1: entryRuleWildcard : ruleWildcard EOF ;
    public final void entryRuleWildcard() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:386:1: ( ruleWildcard EOF )
            // InternalRegularExpressionParser.g:387:1: ruleWildcard EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWildcardRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleWildcard();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWildcardRule()); 
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
    // $ANTLR end "entryRuleWildcard"


    // $ANTLR start "ruleWildcard"
    // InternalRegularExpressionParser.g:394:1: ruleWildcard : ( ( rule__Wildcard__Group__0 ) ) ;
    public final void ruleWildcard() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:398:2: ( ( ( rule__Wildcard__Group__0 ) ) )
            // InternalRegularExpressionParser.g:399:2: ( ( rule__Wildcard__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:399:2: ( ( rule__Wildcard__Group__0 ) )
            // InternalRegularExpressionParser.g:400:3: ( rule__Wildcard__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWildcardAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:401:3: ( rule__Wildcard__Group__0 )
            // InternalRegularExpressionParser.g:401:4: rule__Wildcard__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Wildcard__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWildcardAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWildcard"


    // $ANTLR start "entryRuleAtomEscape"
    // InternalRegularExpressionParser.g:410:1: entryRuleAtomEscape : ruleAtomEscape EOF ;
    public final void entryRuleAtomEscape() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:411:1: ( ruleAtomEscape EOF )
            // InternalRegularExpressionParser.g:412:1: ruleAtomEscape EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomEscapeRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleAtomEscape();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAtomEscapeRule()); 
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
    // $ANTLR end "entryRuleAtomEscape"


    // $ANTLR start "ruleAtomEscape"
    // InternalRegularExpressionParser.g:419:1: ruleAtomEscape : ( ( rule__AtomEscape__Alternatives ) ) ;
    public final void ruleAtomEscape() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:423:2: ( ( ( rule__AtomEscape__Alternatives ) ) )
            // InternalRegularExpressionParser.g:424:2: ( ( rule__AtomEscape__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:424:2: ( ( rule__AtomEscape__Alternatives ) )
            // InternalRegularExpressionParser.g:425:3: ( rule__AtomEscape__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomEscapeAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:426:3: ( rule__AtomEscape__Alternatives )
            // InternalRegularExpressionParser.g:426:4: rule__AtomEscape__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__AtomEscape__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAtomEscapeAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAtomEscape"


    // $ANTLR start "entryRuleCharacterClassEscapeSequence"
    // InternalRegularExpressionParser.g:435:1: entryRuleCharacterClassEscapeSequence : ruleCharacterClassEscapeSequence EOF ;
    public final void entryRuleCharacterClassEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:436:1: ( ruleCharacterClassEscapeSequence EOF )
            // InternalRegularExpressionParser.g:437:1: ruleCharacterClassEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleCharacterClassEscapeSequence();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassEscapeSequenceRule()); 
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
    // $ANTLR end "entryRuleCharacterClassEscapeSequence"


    // $ANTLR start "ruleCharacterClassEscapeSequence"
    // InternalRegularExpressionParser.g:444:1: ruleCharacterClassEscapeSequence : ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleCharacterClassEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:448:2: ( ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:449:2: ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:449:2: ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:450:3: ( rule__CharacterClassEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:451:3: ( rule__CharacterClassEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:451:4: rule__CharacterClassEscapeSequence__SequenceAssignment
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassEscapeSequence__SequenceAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterClassEscapeSequence"


    // $ANTLR start "entryRuleCharacterEscapeSequence"
    // InternalRegularExpressionParser.g:460:1: entryRuleCharacterEscapeSequence : ruleCharacterEscapeSequence EOF ;
    public final void entryRuleCharacterEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:461:1: ( ruleCharacterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:462:1: ruleCharacterEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleCharacterEscapeSequence();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterEscapeSequenceRule()); 
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
    // $ANTLR end "entryRuleCharacterEscapeSequence"


    // $ANTLR start "ruleCharacterEscapeSequence"
    // InternalRegularExpressionParser.g:469:1: ruleCharacterEscapeSequence : ( ( rule__CharacterEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleCharacterEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:473:2: ( ( ( rule__CharacterEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:474:2: ( ( rule__CharacterEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:474:2: ( ( rule__CharacterEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:475:3: ( rule__CharacterEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:476:3: ( rule__CharacterEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:476:4: rule__CharacterEscapeSequence__SequenceAssignment
            {
            pushFollow(FOLLOW_2);
            rule__CharacterEscapeSequence__SequenceAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterEscapeSequence"


    // $ANTLR start "entryRuleControlLetterEscapeSequence"
    // InternalRegularExpressionParser.g:485:1: entryRuleControlLetterEscapeSequence : ruleControlLetterEscapeSequence EOF ;
    public final void entryRuleControlLetterEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:486:1: ( ruleControlLetterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:487:1: ruleControlLetterEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getControlLetterEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleControlLetterEscapeSequence();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getControlLetterEscapeSequenceRule()); 
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
    // $ANTLR end "entryRuleControlLetterEscapeSequence"


    // $ANTLR start "ruleControlLetterEscapeSequence"
    // InternalRegularExpressionParser.g:494:1: ruleControlLetterEscapeSequence : ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleControlLetterEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:498:2: ( ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:499:2: ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:499:2: ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:500:3: ( rule__ControlLetterEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:501:3: ( rule__ControlLetterEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:501:4: rule__ControlLetterEscapeSequence__SequenceAssignment
            {
            pushFollow(FOLLOW_2);
            rule__ControlLetterEscapeSequence__SequenceAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleControlLetterEscapeSequence"


    // $ANTLR start "entryRuleHexEscapeSequence"
    // InternalRegularExpressionParser.g:510:1: entryRuleHexEscapeSequence : ruleHexEscapeSequence EOF ;
    public final void entryRuleHexEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:511:1: ( ruleHexEscapeSequence EOF )
            // InternalRegularExpressionParser.g:512:1: ruleHexEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHexEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleHexEscapeSequence();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHexEscapeSequenceRule()); 
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
    // $ANTLR end "entryRuleHexEscapeSequence"


    // $ANTLR start "ruleHexEscapeSequence"
    // InternalRegularExpressionParser.g:519:1: ruleHexEscapeSequence : ( ( rule__HexEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleHexEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:523:2: ( ( ( rule__HexEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:524:2: ( ( rule__HexEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:524:2: ( ( rule__HexEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:525:3: ( rule__HexEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHexEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:526:3: ( rule__HexEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:526:4: rule__HexEscapeSequence__SequenceAssignment
            {
            pushFollow(FOLLOW_2);
            rule__HexEscapeSequence__SequenceAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getHexEscapeSequenceAccess().getSequenceAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleHexEscapeSequence"


    // $ANTLR start "entryRuleUnicodeEscapeSequence"
    // InternalRegularExpressionParser.g:535:1: entryRuleUnicodeEscapeSequence : ruleUnicodeEscapeSequence EOF ;
    public final void entryRuleUnicodeEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:536:1: ( ruleUnicodeEscapeSequence EOF )
            // InternalRegularExpressionParser.g:537:1: ruleUnicodeEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnicodeEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleUnicodeEscapeSequence();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnicodeEscapeSequenceRule()); 
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
    // $ANTLR end "entryRuleUnicodeEscapeSequence"


    // $ANTLR start "ruleUnicodeEscapeSequence"
    // InternalRegularExpressionParser.g:544:1: ruleUnicodeEscapeSequence : ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleUnicodeEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:548:2: ( ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:549:2: ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:549:2: ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:550:3: ( rule__UnicodeEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:551:3: ( rule__UnicodeEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:551:4: rule__UnicodeEscapeSequence__SequenceAssignment
            {
            pushFollow(FOLLOW_2);
            rule__UnicodeEscapeSequence__SequenceAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleUnicodeEscapeSequence"


    // $ANTLR start "entryRuleIdentityEscapeSequence"
    // InternalRegularExpressionParser.g:560:1: entryRuleIdentityEscapeSequence : ruleIdentityEscapeSequence EOF ;
    public final void entryRuleIdentityEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:561:1: ( ruleIdentityEscapeSequence EOF )
            // InternalRegularExpressionParser.g:562:1: ruleIdentityEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIdentityEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleIdentityEscapeSequence();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIdentityEscapeSequenceRule()); 
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
    // $ANTLR end "entryRuleIdentityEscapeSequence"


    // $ANTLR start "ruleIdentityEscapeSequence"
    // InternalRegularExpressionParser.g:569:1: ruleIdentityEscapeSequence : ( ( rule__IdentityEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleIdentityEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:573:2: ( ( ( rule__IdentityEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:574:2: ( ( rule__IdentityEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:574:2: ( ( rule__IdentityEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:575:3: ( rule__IdentityEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:576:3: ( rule__IdentityEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:576:4: rule__IdentityEscapeSequence__SequenceAssignment
            {
            pushFollow(FOLLOW_2);
            rule__IdentityEscapeSequence__SequenceAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIdentityEscapeSequence"


    // $ANTLR start "entryRuleDecimalEscapeSequence"
    // InternalRegularExpressionParser.g:585:1: entryRuleDecimalEscapeSequence : ruleDecimalEscapeSequence EOF ;
    public final void entryRuleDecimalEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:586:1: ( ruleDecimalEscapeSequence EOF )
            // InternalRegularExpressionParser.g:587:1: ruleDecimalEscapeSequence EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDecimalEscapeSequenceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleDecimalEscapeSequence();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDecimalEscapeSequenceRule()); 
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
    // $ANTLR end "entryRuleDecimalEscapeSequence"


    // $ANTLR start "ruleDecimalEscapeSequence"
    // InternalRegularExpressionParser.g:594:1: ruleDecimalEscapeSequence : ( ( rule__DecimalEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleDecimalEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:598:2: ( ( ( rule__DecimalEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:599:2: ( ( rule__DecimalEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:599:2: ( ( rule__DecimalEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:600:3: ( rule__DecimalEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:601:3: ( rule__DecimalEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:601:4: rule__DecimalEscapeSequence__SequenceAssignment
            {
            pushFollow(FOLLOW_2);
            rule__DecimalEscapeSequence__SequenceAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceAssignment()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDecimalEscapeSequence"


    // $ANTLR start "entryRuleCharacterClass"
    // InternalRegularExpressionParser.g:610:1: entryRuleCharacterClass : ruleCharacterClass EOF ;
    public final void entryRuleCharacterClass() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:611:1: ( ruleCharacterClass EOF )
            // InternalRegularExpressionParser.g:612:1: ruleCharacterClass EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleCharacterClass();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassRule()); 
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
    // $ANTLR end "entryRuleCharacterClass"


    // $ANTLR start "ruleCharacterClass"
    // InternalRegularExpressionParser.g:619:1: ruleCharacterClass : ( ( rule__CharacterClass__Group__0 ) ) ;
    public final void ruleCharacterClass() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:623:2: ( ( ( rule__CharacterClass__Group__0 ) ) )
            // InternalRegularExpressionParser.g:624:2: ( ( rule__CharacterClass__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:624:2: ( ( rule__CharacterClass__Group__0 ) )
            // InternalRegularExpressionParser.g:625:3: ( rule__CharacterClass__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:626:3: ( rule__CharacterClass__Group__0 )
            // InternalRegularExpressionParser.g:626:4: rule__CharacterClass__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClass__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterClass"


    // $ANTLR start "entryRuleCharacterClassElement"
    // InternalRegularExpressionParser.g:635:1: entryRuleCharacterClassElement : ruleCharacterClassElement EOF ;
    public final void entryRuleCharacterClassElement() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:636:1: ( ruleCharacterClassElement EOF )
            // InternalRegularExpressionParser.g:637:1: ruleCharacterClassElement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleCharacterClassElement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementRule()); 
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
    // $ANTLR end "entryRuleCharacterClassElement"


    // $ANTLR start "ruleCharacterClassElement"
    // InternalRegularExpressionParser.g:644:1: ruleCharacterClassElement : ( ( rule__CharacterClassElement__Group__0 ) ) ;
    public final void ruleCharacterClassElement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:648:2: ( ( ( rule__CharacterClassElement__Group__0 ) ) )
            // InternalRegularExpressionParser.g:649:2: ( ( rule__CharacterClassElement__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:649:2: ( ( rule__CharacterClassElement__Group__0 ) )
            // InternalRegularExpressionParser.g:650:3: ( rule__CharacterClassElement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:651:3: ( rule__CharacterClassElement__Group__0 )
            // InternalRegularExpressionParser.g:651:4: rule__CharacterClassElement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterClassElement"


    // $ANTLR start "entryRuleCharacterClassAtom"
    // InternalRegularExpressionParser.g:660:1: entryRuleCharacterClassAtom : ruleCharacterClassAtom EOF ;
    public final void entryRuleCharacterClassAtom() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:661:1: ( ruleCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:662:1: ruleCharacterClassAtom EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAtomRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleCharacterClassAtom();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAtomRule()); 
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
    // $ANTLR end "entryRuleCharacterClassAtom"


    // $ANTLR start "ruleCharacterClassAtom"
    // InternalRegularExpressionParser.g:669:1: ruleCharacterClassAtom : ( ( rule__CharacterClassAtom__Alternatives ) ) ;
    public final void ruleCharacterClassAtom() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:673:2: ( ( ( rule__CharacterClassAtom__Alternatives ) ) )
            // InternalRegularExpressionParser.g:674:2: ( ( rule__CharacterClassAtom__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:674:2: ( ( rule__CharacterClassAtom__Alternatives ) )
            // InternalRegularExpressionParser.g:675:3: ( rule__CharacterClassAtom__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAtomAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:676:3: ( rule__CharacterClassAtom__Alternatives )
            // InternalRegularExpressionParser.g:676:4: rule__CharacterClassAtom__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassAtom__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAtomAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterClassAtom"


    // $ANTLR start "entryRuleEscapedCharacterClassAtom"
    // InternalRegularExpressionParser.g:685:1: entryRuleEscapedCharacterClassAtom : ruleEscapedCharacterClassAtom EOF ;
    public final void entryRuleEscapedCharacterClassAtom() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:686:1: ( ruleEscapedCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:687:1: ruleEscapedCharacterClassAtom EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getEscapedCharacterClassAtomRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleEscapedCharacterClassAtom();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getEscapedCharacterClassAtomRule()); 
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
    // $ANTLR end "entryRuleEscapedCharacterClassAtom"


    // $ANTLR start "ruleEscapedCharacterClassAtom"
    // InternalRegularExpressionParser.g:694:1: ruleEscapedCharacterClassAtom : ( ( rule__EscapedCharacterClassAtom__Alternatives ) ) ;
    public final void ruleEscapedCharacterClassAtom() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:698:2: ( ( ( rule__EscapedCharacterClassAtom__Alternatives ) ) )
            // InternalRegularExpressionParser.g:699:2: ( ( rule__EscapedCharacterClassAtom__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:699:2: ( ( rule__EscapedCharacterClassAtom__Alternatives ) )
            // InternalRegularExpressionParser.g:700:3: ( rule__EscapedCharacterClassAtom__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getEscapedCharacterClassAtomAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:701:3: ( rule__EscapedCharacterClassAtom__Alternatives )
            // InternalRegularExpressionParser.g:701:4: rule__EscapedCharacterClassAtom__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__EscapedCharacterClassAtom__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getEscapedCharacterClassAtomAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEscapedCharacterClassAtom"


    // $ANTLR start "entryRuleBackspace"
    // InternalRegularExpressionParser.g:710:1: entryRuleBackspace : ruleBackspace EOF ;
    public final void entryRuleBackspace() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:711:1: ( ruleBackspace EOF )
            // InternalRegularExpressionParser.g:712:1: ruleBackspace EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBackspaceRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleBackspace();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBackspaceRule()); 
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
    // $ANTLR end "entryRuleBackspace"


    // $ANTLR start "ruleBackspace"
    // InternalRegularExpressionParser.g:719:1: ruleBackspace : ( ( rule__Backspace__Group__0 ) ) ;
    public final void ruleBackspace() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:723:2: ( ( ( rule__Backspace__Group__0 ) ) )
            // InternalRegularExpressionParser.g:724:2: ( ( rule__Backspace__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:724:2: ( ( rule__Backspace__Group__0 ) )
            // InternalRegularExpressionParser.g:725:3: ( rule__Backspace__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBackspaceAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:726:3: ( rule__Backspace__Group__0 )
            // InternalRegularExpressionParser.g:726:4: rule__Backspace__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Backspace__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBackspaceAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBackspace"


    // $ANTLR start "entryRuleGroup"
    // InternalRegularExpressionParser.g:735:1: entryRuleGroup : ruleGroup EOF ;
    public final void entryRuleGroup() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:736:1: ( ruleGroup EOF )
            // InternalRegularExpressionParser.g:737:1: ruleGroup EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleGroup();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupRule()); 
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
    // $ANTLR end "entryRuleGroup"


    // $ANTLR start "ruleGroup"
    // InternalRegularExpressionParser.g:744:1: ruleGroup : ( ( rule__Group__Group__0 ) ) ;
    public final void ruleGroup() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:748:2: ( ( ( rule__Group__Group__0 ) ) )
            // InternalRegularExpressionParser.g:749:2: ( ( rule__Group__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:749:2: ( ( rule__Group__Group__0 ) )
            // InternalRegularExpressionParser.g:750:3: ( rule__Group__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:751:3: ( rule__Group__Group__0 )
            // InternalRegularExpressionParser.g:751:4: rule__Group__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Group__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGroup"


    // $ANTLR start "entryRuleRegExpIdentifierName"
    // InternalRegularExpressionParser.g:760:1: entryRuleRegExpIdentifierName : ruleRegExpIdentifierName EOF ;
    public final void entryRuleRegExpIdentifierName() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:761:1: ( ruleRegExpIdentifierName EOF )
            // InternalRegularExpressionParser.g:762:1: ruleRegExpIdentifierName EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierNameRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRegExpIdentifierName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierNameRule()); 
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
    // $ANTLR end "entryRuleRegExpIdentifierName"


    // $ANTLR start "ruleRegExpIdentifierName"
    // InternalRegularExpressionParser.g:769:1: ruleRegExpIdentifierName : ( ( rule__RegExpIdentifierName__Group__0 ) ) ;
    public final void ruleRegExpIdentifierName() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:773:2: ( ( ( rule__RegExpIdentifierName__Group__0 ) ) )
            // InternalRegularExpressionParser.g:774:2: ( ( rule__RegExpIdentifierName__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:774:2: ( ( rule__RegExpIdentifierName__Group__0 ) )
            // InternalRegularExpressionParser.g:775:3: ( rule__RegExpIdentifierName__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierNameAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:776:3: ( rule__RegExpIdentifierName__Group__0 )
            // InternalRegularExpressionParser.g:776:4: rule__RegExpIdentifierName__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RegExpIdentifierName__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierNameAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRegExpIdentifierName"


    // $ANTLR start "entryRuleRegExpIdentifierStart"
    // InternalRegularExpressionParser.g:785:1: entryRuleRegExpIdentifierStart : ruleRegExpIdentifierStart EOF ;
    public final void entryRuleRegExpIdentifierStart() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:786:1: ( ruleRegExpIdentifierStart EOF )
            // InternalRegularExpressionParser.g:787:1: ruleRegExpIdentifierStart EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierStartRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRegExpIdentifierStart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierStartRule()); 
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
    // $ANTLR end "entryRuleRegExpIdentifierStart"


    // $ANTLR start "ruleRegExpIdentifierStart"
    // InternalRegularExpressionParser.g:794:1: ruleRegExpIdentifierStart : ( ( rule__RegExpIdentifierStart__Alternatives ) ) ;
    public final void ruleRegExpIdentifierStart() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:798:2: ( ( ( rule__RegExpIdentifierStart__Alternatives ) ) )
            // InternalRegularExpressionParser.g:799:2: ( ( rule__RegExpIdentifierStart__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:799:2: ( ( rule__RegExpIdentifierStart__Alternatives ) )
            // InternalRegularExpressionParser.g:800:3: ( rule__RegExpIdentifierStart__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierStartAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:801:3: ( rule__RegExpIdentifierStart__Alternatives )
            // InternalRegularExpressionParser.g:801:4: rule__RegExpIdentifierStart__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__RegExpIdentifierStart__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierStartAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRegExpIdentifierStart"


    // $ANTLR start "entryRuleRegExpIdentifierPart"
    // InternalRegularExpressionParser.g:810:1: entryRuleRegExpIdentifierPart : ruleRegExpIdentifierPart EOF ;
    public final void entryRuleRegExpIdentifierPart() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:811:1: ( ruleRegExpIdentifierPart EOF )
            // InternalRegularExpressionParser.g:812:1: ruleRegExpIdentifierPart EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierPartRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRegExpIdentifierPart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierPartRule()); 
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
    // $ANTLR end "entryRuleRegExpIdentifierPart"


    // $ANTLR start "ruleRegExpIdentifierPart"
    // InternalRegularExpressionParser.g:819:1: ruleRegExpIdentifierPart : ( ( rule__RegExpIdentifierPart__Alternatives ) ) ;
    public final void ruleRegExpIdentifierPart() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:823:2: ( ( ( rule__RegExpIdentifierPart__Alternatives ) ) )
            // InternalRegularExpressionParser.g:824:2: ( ( rule__RegExpIdentifierPart__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:824:2: ( ( rule__RegExpIdentifierPart__Alternatives ) )
            // InternalRegularExpressionParser.g:825:3: ( rule__RegExpIdentifierPart__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierPartAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:826:3: ( rule__RegExpIdentifierPart__Alternatives )
            // InternalRegularExpressionParser.g:826:4: rule__RegExpIdentifierPart__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__RegExpIdentifierPart__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierPartAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRegExpIdentifierPart"


    // $ANTLR start "entryRuleQuantifier"
    // InternalRegularExpressionParser.g:835:1: entryRuleQuantifier : ruleQuantifier EOF ;
    public final void entryRuleQuantifier() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:836:1: ( ruleQuantifier EOF )
            // InternalRegularExpressionParser.g:837:1: ruleQuantifier EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQuantifierRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleQuantifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQuantifierRule()); 
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
    // $ANTLR end "entryRuleQuantifier"


    // $ANTLR start "ruleQuantifier"
    // InternalRegularExpressionParser.g:844:1: ruleQuantifier : ( ( rule__Quantifier__Alternatives ) ) ;
    public final void ruleQuantifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:848:2: ( ( ( rule__Quantifier__Alternatives ) ) )
            // InternalRegularExpressionParser.g:849:2: ( ( rule__Quantifier__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:849:2: ( ( rule__Quantifier__Alternatives ) )
            // InternalRegularExpressionParser.g:850:3: ( rule__Quantifier__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQuantifierAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:851:3: ( rule__Quantifier__Alternatives )
            // InternalRegularExpressionParser.g:851:4: rule__Quantifier__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Quantifier__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQuantifierAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQuantifier"


    // $ANTLR start "entryRuleSimpleQuantifier"
    // InternalRegularExpressionParser.g:860:1: entryRuleSimpleQuantifier : ruleSimpleQuantifier EOF ;
    public final void entryRuleSimpleQuantifier() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:861:1: ( ruleSimpleQuantifier EOF )
            // InternalRegularExpressionParser.g:862:1: ruleSimpleQuantifier EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleSimpleQuantifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleQuantifierRule()); 
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
    // $ANTLR end "entryRuleSimpleQuantifier"


    // $ANTLR start "ruleSimpleQuantifier"
    // InternalRegularExpressionParser.g:869:1: ruleSimpleQuantifier : ( ( rule__SimpleQuantifier__Group__0 ) ) ;
    public final void ruleSimpleQuantifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:873:2: ( ( ( rule__SimpleQuantifier__Group__0 ) ) )
            // InternalRegularExpressionParser.g:874:2: ( ( rule__SimpleQuantifier__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:874:2: ( ( rule__SimpleQuantifier__Group__0 ) )
            // InternalRegularExpressionParser.g:875:3: ( rule__SimpleQuantifier__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:876:3: ( rule__SimpleQuantifier__Group__0 )
            // InternalRegularExpressionParser.g:876:4: rule__SimpleQuantifier__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleQuantifier__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleQuantifierAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSimpleQuantifier"


    // $ANTLR start "entryRuleExactQuantifier"
    // InternalRegularExpressionParser.g:885:1: entryRuleExactQuantifier : ruleExactQuantifier EOF ;
    public final void entryRuleExactQuantifier() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:886:1: ( ruleExactQuantifier EOF )
            // InternalRegularExpressionParser.g:887:1: ruleExactQuantifier EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleExactQuantifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierRule()); 
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
    // $ANTLR end "entryRuleExactQuantifier"


    // $ANTLR start "ruleExactQuantifier"
    // InternalRegularExpressionParser.g:894:1: ruleExactQuantifier : ( ( rule__ExactQuantifier__Group__0 ) ) ;
    public final void ruleExactQuantifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:898:2: ( ( ( rule__ExactQuantifier__Group__0 ) ) )
            // InternalRegularExpressionParser.g:899:2: ( ( rule__ExactQuantifier__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:899:2: ( ( rule__ExactQuantifier__Group__0 ) )
            // InternalRegularExpressionParser.g:900:3: ( rule__ExactQuantifier__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:901:3: ( rule__ExactQuantifier__Group__0 )
            // InternalRegularExpressionParser.g:901:4: rule__ExactQuantifier__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExactQuantifier"


    // $ANTLR start "entryRuleRegularExpressionFlags"
    // InternalRegularExpressionParser.g:910:1: entryRuleRegularExpressionFlags : ruleRegularExpressionFlags EOF ;
    public final void entryRuleRegularExpressionFlags() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:911:1: ( ruleRegularExpressionFlags EOF )
            // InternalRegularExpressionParser.g:912:1: ruleRegularExpressionFlags EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleRegularExpressionFlags();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionFlagsRule()); 
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
    // $ANTLR end "entryRuleRegularExpressionFlags"


    // $ANTLR start "ruleRegularExpressionFlags"
    // InternalRegularExpressionParser.g:919:1: ruleRegularExpressionFlags : ( ( rule__RegularExpressionFlags__Group__0 ) ) ;
    public final void ruleRegularExpressionFlags() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:923:2: ( ( ( rule__RegularExpressionFlags__Group__0 ) ) )
            // InternalRegularExpressionParser.g:924:2: ( ( rule__RegularExpressionFlags__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:924:2: ( ( rule__RegularExpressionFlags__Group__0 ) )
            // InternalRegularExpressionParser.g:925:3: ( rule__RegularExpressionFlags__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:926:3: ( rule__RegularExpressionFlags__Group__0 )
            // InternalRegularExpressionParser.g:926:4: rule__RegularExpressionFlags__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionFlags__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionFlagsAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRegularExpressionFlags"


    // $ANTLR start "entryRuleINT"
    // InternalRegularExpressionParser.g:935:1: entryRuleINT : ruleINT EOF ;
    public final void entryRuleINT() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:936:1: ( ruleINT EOF )
            // InternalRegularExpressionParser.g:937:1: ruleINT EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getINTRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleINT();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getINTRule()); 
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
    // $ANTLR end "entryRuleINT"


    // $ANTLR start "ruleINT"
    // InternalRegularExpressionParser.g:944:1: ruleINT : ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) ) ;
    public final void ruleINT() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:948:2: ( ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) ) )
            // InternalRegularExpressionParser.g:949:2: ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) )
            {
            // InternalRegularExpressionParser.g:949:2: ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) )
            // InternalRegularExpressionParser.g:950:3: ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* )
            {
            // InternalRegularExpressionParser.g:950:3: ( ( RULE_UNICODE_DIGIT ) )
            // InternalRegularExpressionParser.g:951:4: ( RULE_UNICODE_DIGIT )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); 
            }
            // InternalRegularExpressionParser.g:952:4: ( RULE_UNICODE_DIGIT )
            // InternalRegularExpressionParser.g:952:5: RULE_UNICODE_DIGIT
            {
            match(input,RULE_UNICODE_DIGIT,FOLLOW_3); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); 
            }

            }

            // InternalRegularExpressionParser.g:955:3: ( ( RULE_UNICODE_DIGIT )* )
            // InternalRegularExpressionParser.g:956:4: ( RULE_UNICODE_DIGIT )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); 
            }
            // InternalRegularExpressionParser.g:957:4: ( RULE_UNICODE_DIGIT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_UNICODE_DIGIT) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:957:5: RULE_UNICODE_DIGIT
            	    {
            	    match(input,RULE_UNICODE_DIGIT,FOLLOW_3); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); 
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
    // $ANTLR end "ruleINT"


    // $ANTLR start "rule__Disjunction__Alternatives"
    // InternalRegularExpressionParser.g:966:1: rule__Disjunction__Alternatives : ( ( ( rule__Disjunction__Group_0__0 ) ) | ( ( rule__Disjunction__Group_1__0 ) ) );
    public final void rule__Disjunction__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:970:1: ( ( ( rule__Disjunction__Group_0__0 ) ) | ( ( rule__Disjunction__Group_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=ExclamationMark && LA2_0<=LeftParenthesis)||(LA2_0>=Comma && LA2_0<=FullStop)||(LA2_0>=Colon && LA2_0<=GreaterThanSign)||(LA2_0>=LeftSquareBracket && LA2_0<=CircumflexAccent)||LA2_0==LeftCurlyBracket||(LA2_0>=RightCurlyBracket && LA2_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA2_0>=RULE_HEX_ESCAPE && LA2_0<=RULE_UNICODE_ESCAPE)||(LA2_0>=RULE_DECIMAL_ESCAPE && LA2_0<=RULE_IDENTITY_ESCAPE)||LA2_0==RULE_UNICODE_DIGIT||(LA2_0>=RULE_UNICODE_LETTER && LA2_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt2=1;
            }
            else if ( (LA2_0==EOF||LA2_0==RightParenthesis||LA2_0==Solidus||LA2_0==VerticalLine) ) {
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
                    // InternalRegularExpressionParser.g:971:2: ( ( rule__Disjunction__Group_0__0 ) )
                    {
                    // InternalRegularExpressionParser.g:971:2: ( ( rule__Disjunction__Group_0__0 ) )
                    // InternalRegularExpressionParser.g:972:3: ( rule__Disjunction__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDisjunctionAccess().getGroup_0()); 
                    }
                    // InternalRegularExpressionParser.g:973:3: ( rule__Disjunction__Group_0__0 )
                    // InternalRegularExpressionParser.g:973:4: rule__Disjunction__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Disjunction__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDisjunctionAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:977:2: ( ( rule__Disjunction__Group_1__0 ) )
                    {
                    // InternalRegularExpressionParser.g:977:2: ( ( rule__Disjunction__Group_1__0 ) )
                    // InternalRegularExpressionParser.g:978:3: ( rule__Disjunction__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDisjunctionAccess().getGroup_1()); 
                    }
                    // InternalRegularExpressionParser.g:979:3: ( rule__Disjunction__Group_1__0 )
                    // InternalRegularExpressionParser.g:979:4: rule__Disjunction__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Disjunction__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDisjunctionAccess().getGroup_1()); 
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
    // $ANTLR end "rule__Disjunction__Alternatives"


    // $ANTLR start "rule__Term__Alternatives"
    // InternalRegularExpressionParser.g:987:1: rule__Term__Alternatives : ( ( ruleAssertion ) | ( ( rule__Term__Group_1__0 ) ) );
    public final void rule__Term__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:991:1: ( ( ruleAssertion ) | ( ( rule__Term__Group_1__0 ) ) )
            int alt3=2;
            switch ( input.LA(1) ) {
            case DollarSign:
            case CircumflexAccent:
            case RULE_WORD_BOUNDARY:
            case RULE_NOT_WORD_BOUNDARY:
                {
                alt3=1;
                }
                break;
            case LeftParenthesis:
                {
                int LA3_2 = input.LA(2);

                if ( (LA3_2==QuestionMark) ) {
                    switch ( input.LA(3) ) {
                    case LessThanSign:
                        {
                        int LA3_5 = input.LA(4);

                        if ( (LA3_5==ExclamationMark||LA3_5==EqualsSign) ) {
                            alt3=1;
                        }
                        else if ( (LA3_5==DollarSign||LA3_5==KW__||LA3_5==RULE_UNICODE_ESCAPE||LA3_5==RULE_UNICODE_LETTER) ) {
                            alt3=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 5, input);

                            throw nvae;
                        }
                        }
                        break;
                    case Colon:
                        {
                        alt3=2;
                        }
                        break;
                    case ExclamationMark:
                    case EqualsSign:
                        {
                        alt3=1;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 4, input);

                        throw nvae;
                    }

                }
                else if ( ((LA3_2>=ExclamationMark && LA3_2<=RightParenthesis)||(LA3_2>=Comma && LA3_2<=FullStop)||(LA3_2>=Colon && LA3_2<=GreaterThanSign)||(LA3_2>=LeftSquareBracket && LA3_2<=CircumflexAccent)||(LA3_2>=LeftCurlyBracket && LA3_2<=RULE_CONTROL_LETTER_ESCAPE)||(LA3_2>=RULE_HEX_ESCAPE && LA3_2<=RULE_UNICODE_ESCAPE)||(LA3_2>=RULE_DECIMAL_ESCAPE && LA3_2<=RULE_IDENTITY_ESCAPE)||LA3_2==RULE_UNICODE_DIGIT||(LA3_2>=RULE_UNICODE_LETTER && LA3_2<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt3=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;
                }
                }
                break;
            case ExclamationMark:
            case Comma:
            case HyphenMinus:
            case FullStop:
            case Colon:
            case LessThanSign:
            case EqualsSign:
            case GreaterThanSign:
            case LeftSquareBracket:
            case RightSquareBracket:
            case LeftCurlyBracket:
            case RightCurlyBracket:
            case RULE_CHARACTER_CLASS_ESCAPE:
            case RULE_CONTROL_ESCAPE:
            case RULE_CONTROL_LETTER_ESCAPE:
            case RULE_HEX_ESCAPE:
            case RULE_UNICODE_ESCAPE:
            case RULE_DECIMAL_ESCAPE:
            case RULE_IDENTITY_ESCAPE:
            case RULE_UNICODE_DIGIT:
            case RULE_UNICODE_LETTER:
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt3=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalRegularExpressionParser.g:992:2: ( ruleAssertion )
                    {
                    // InternalRegularExpressionParser.g:992:2: ( ruleAssertion )
                    // InternalRegularExpressionParser.g:993:3: ruleAssertion
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTermAccess().getAssertionParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleAssertion();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTermAccess().getAssertionParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:998:2: ( ( rule__Term__Group_1__0 ) )
                    {
                    // InternalRegularExpressionParser.g:998:2: ( ( rule__Term__Group_1__0 ) )
                    // InternalRegularExpressionParser.g:999:3: ( rule__Term__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTermAccess().getGroup_1()); 
                    }
                    // InternalRegularExpressionParser.g:1000:3: ( rule__Term__Group_1__0 )
                    // InternalRegularExpressionParser.g:1000:4: rule__Term__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Term__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getTermAccess().getGroup_1()); 
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
    // $ANTLR end "rule__Term__Alternatives"


    // $ANTLR start "rule__Assertion__Alternatives"
    // InternalRegularExpressionParser.g:1008:1: rule__Assertion__Alternatives : ( ( ruleLineStart ) | ( ruleLineEnd ) | ( ruleWordBoundary ) | ( ruleLookAhead ) );
    public final void rule__Assertion__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1012:1: ( ( ruleLineStart ) | ( ruleLineEnd ) | ( ruleWordBoundary ) | ( ruleLookAhead ) )
            int alt4=4;
            switch ( input.LA(1) ) {
            case CircumflexAccent:
                {
                alt4=1;
                }
                break;
            case DollarSign:
                {
                alt4=2;
                }
                break;
            case RULE_WORD_BOUNDARY:
            case RULE_NOT_WORD_BOUNDARY:
                {
                alt4=3;
                }
                break;
            case LeftParenthesis:
                {
                alt4=4;
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
                    // InternalRegularExpressionParser.g:1013:2: ( ruleLineStart )
                    {
                    // InternalRegularExpressionParser.g:1013:2: ( ruleLineStart )
                    // InternalRegularExpressionParser.g:1014:3: ruleLineStart
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAssertionAccess().getLineStartParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLineStart();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAssertionAccess().getLineStartParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1019:2: ( ruleLineEnd )
                    {
                    // InternalRegularExpressionParser.g:1019:2: ( ruleLineEnd )
                    // InternalRegularExpressionParser.g:1020:3: ruleLineEnd
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAssertionAccess().getLineEndParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLineEnd();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAssertionAccess().getLineEndParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1025:2: ( ruleWordBoundary )
                    {
                    // InternalRegularExpressionParser.g:1025:2: ( ruleWordBoundary )
                    // InternalRegularExpressionParser.g:1026:3: ruleWordBoundary
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAssertionAccess().getWordBoundaryParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleWordBoundary();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAssertionAccess().getWordBoundaryParserRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1031:2: ( ruleLookAhead )
                    {
                    // InternalRegularExpressionParser.g:1031:2: ( ruleLookAhead )
                    // InternalRegularExpressionParser.g:1032:3: ruleLookAhead
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAssertionAccess().getLookAheadParserRuleCall_3()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleLookAhead();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAssertionAccess().getLookAheadParserRuleCall_3()); 
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
    // $ANTLR end "rule__Assertion__Alternatives"


    // $ANTLR start "rule__WordBoundary__Alternatives_1"
    // InternalRegularExpressionParser.g:1041:1: rule__WordBoundary__Alternatives_1 : ( ( RULE_WORD_BOUNDARY ) | ( ( rule__WordBoundary__NotAssignment_1_1 ) ) );
    public final void rule__WordBoundary__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1045:1: ( ( RULE_WORD_BOUNDARY ) | ( ( rule__WordBoundary__NotAssignment_1_1 ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_WORD_BOUNDARY) ) {
                alt5=1;
            }
            else if ( (LA5_0==RULE_NOT_WORD_BOUNDARY) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalRegularExpressionParser.g:1046:2: ( RULE_WORD_BOUNDARY )
                    {
                    // InternalRegularExpressionParser.g:1046:2: ( RULE_WORD_BOUNDARY )
                    // InternalRegularExpressionParser.g:1047:3: RULE_WORD_BOUNDARY
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWordBoundaryAccess().getWORD_BOUNDARYTerminalRuleCall_1_0()); 
                    }
                    match(input,RULE_WORD_BOUNDARY,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWordBoundaryAccess().getWORD_BOUNDARYTerminalRuleCall_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1052:2: ( ( rule__WordBoundary__NotAssignment_1_1 ) )
                    {
                    // InternalRegularExpressionParser.g:1052:2: ( ( rule__WordBoundary__NotAssignment_1_1 ) )
                    // InternalRegularExpressionParser.g:1053:3: ( rule__WordBoundary__NotAssignment_1_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWordBoundaryAccess().getNotAssignment_1_1()); 
                    }
                    // InternalRegularExpressionParser.g:1054:3: ( rule__WordBoundary__NotAssignment_1_1 )
                    // InternalRegularExpressionParser.g:1054:4: rule__WordBoundary__NotAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__WordBoundary__NotAssignment_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getWordBoundaryAccess().getNotAssignment_1_1()); 
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
    // $ANTLR end "rule__WordBoundary__Alternatives_1"


    // $ANTLR start "rule__LookAhead__Alternatives_4"
    // InternalRegularExpressionParser.g:1062:1: rule__LookAhead__Alternatives_4 : ( ( EqualsSign ) | ( ( rule__LookAhead__NotAssignment_4_1 ) ) );
    public final void rule__LookAhead__Alternatives_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1066:1: ( ( EqualsSign ) | ( ( rule__LookAhead__NotAssignment_4_1 ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==EqualsSign) ) {
                alt6=1;
            }
            else if ( (LA6_0==ExclamationMark) ) {
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
                    // InternalRegularExpressionParser.g:1067:2: ( EqualsSign )
                    {
                    // InternalRegularExpressionParser.g:1067:2: ( EqualsSign )
                    // InternalRegularExpressionParser.g:1068:3: EqualsSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLookAheadAccess().getEqualsSignKeyword_4_0()); 
                    }
                    match(input,EqualsSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLookAheadAccess().getEqualsSignKeyword_4_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1073:2: ( ( rule__LookAhead__NotAssignment_4_1 ) )
                    {
                    // InternalRegularExpressionParser.g:1073:2: ( ( rule__LookAhead__NotAssignment_4_1 ) )
                    // InternalRegularExpressionParser.g:1074:3: ( rule__LookAhead__NotAssignment_4_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLookAheadAccess().getNotAssignment_4_1()); 
                    }
                    // InternalRegularExpressionParser.g:1075:3: ( rule__LookAhead__NotAssignment_4_1 )
                    // InternalRegularExpressionParser.g:1075:4: rule__LookAhead__NotAssignment_4_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__LookAhead__NotAssignment_4_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLookAheadAccess().getNotAssignment_4_1()); 
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
    // $ANTLR end "rule__LookAhead__Alternatives_4"


    // $ANTLR start "rule__Atom__Alternatives"
    // InternalRegularExpressionParser.g:1083:1: rule__Atom__Alternatives : ( ( rulePatternCharacter ) | ( ruleWildcard ) | ( ruleAtomEscape ) | ( ruleCharacterClass ) | ( ruleGroup ) );
    public final void rule__Atom__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1087:1: ( ( rulePatternCharacter ) | ( ruleWildcard ) | ( ruleAtomEscape ) | ( ruleCharacterClass ) | ( ruleGroup ) )
            int alt7=5;
            switch ( input.LA(1) ) {
            case ExclamationMark:
            case Comma:
            case HyphenMinus:
            case Colon:
            case LessThanSign:
            case EqualsSign:
            case GreaterThanSign:
            case RightSquareBracket:
            case LeftCurlyBracket:
            case RightCurlyBracket:
            case RULE_UNICODE_DIGIT:
            case RULE_UNICODE_LETTER:
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt7=1;
                }
                break;
            case FullStop:
                {
                alt7=2;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
            case RULE_CONTROL_ESCAPE:
            case RULE_CONTROL_LETTER_ESCAPE:
            case RULE_HEX_ESCAPE:
            case RULE_UNICODE_ESCAPE:
            case RULE_DECIMAL_ESCAPE:
            case RULE_IDENTITY_ESCAPE:
                {
                alt7=3;
                }
                break;
            case LeftSquareBracket:
                {
                alt7=4;
                }
                break;
            case LeftParenthesis:
                {
                alt7=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalRegularExpressionParser.g:1088:2: ( rulePatternCharacter )
                    {
                    // InternalRegularExpressionParser.g:1088:2: ( rulePatternCharacter )
                    // InternalRegularExpressionParser.g:1089:3: rulePatternCharacter
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getPatternCharacterParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    rulePatternCharacter();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getPatternCharacterParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1094:2: ( ruleWildcard )
                    {
                    // InternalRegularExpressionParser.g:1094:2: ( ruleWildcard )
                    // InternalRegularExpressionParser.g:1095:3: ruleWildcard
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getWildcardParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleWildcard();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getWildcardParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1100:2: ( ruleAtomEscape )
                    {
                    // InternalRegularExpressionParser.g:1100:2: ( ruleAtomEscape )
                    // InternalRegularExpressionParser.g:1101:3: ruleAtomEscape
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getAtomEscapeParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleAtomEscape();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getAtomEscapeParserRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1106:2: ( ruleCharacterClass )
                    {
                    // InternalRegularExpressionParser.g:1106:2: ( ruleCharacterClass )
                    // InternalRegularExpressionParser.g:1107:3: ruleCharacterClass
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getCharacterClassParserRuleCall_3()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleCharacterClass();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getCharacterClassParserRuleCall_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:1112:2: ( ruleGroup )
                    {
                    // InternalRegularExpressionParser.g:1112:2: ( ruleGroup )
                    // InternalRegularExpressionParser.g:1113:3: ruleGroup
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getGroupParserRuleCall_4()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleGroup();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getGroupParserRuleCall_4()); 
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
    // $ANTLR end "rule__Atom__Alternatives"


    // $ANTLR start "rule__PatternCharacter__ValueAlternatives_0"
    // InternalRegularExpressionParser.g:1122:1: rule__PatternCharacter__ValueAlternatives_0 : ( ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) | ( HyphenMinus ) | ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( RightSquareBracket ) | ( LessThanSign ) | ( GreaterThanSign ) );
    public final void rule__PatternCharacter__ValueAlternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1126:1: ( ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) | ( HyphenMinus ) | ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( RightSquareBracket ) | ( LessThanSign ) | ( GreaterThanSign ) )
            int alt8=13;
            switch ( input.LA(1) ) {
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt8=1;
                }
                break;
            case RULE_UNICODE_LETTER:
                {
                alt8=2;
                }
                break;
            case RULE_UNICODE_DIGIT:
                {
                alt8=3;
                }
                break;
            case HyphenMinus:
                {
                alt8=4;
                }
                break;
            case Comma:
                {
                alt8=5;
                }
                break;
            case EqualsSign:
                {
                alt8=6;
                }
                break;
            case Colon:
                {
                alt8=7;
                }
                break;
            case ExclamationMark:
                {
                alt8=8;
                }
                break;
            case LeftCurlyBracket:
                {
                alt8=9;
                }
                break;
            case RightCurlyBracket:
                {
                alt8=10;
                }
                break;
            case RightSquareBracket:
                {
                alt8=11;
                }
                break;
            case LessThanSign:
                {
                alt8=12;
                }
                break;
            case GreaterThanSign:
                {
                alt8=13;
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
                    // InternalRegularExpressionParser.g:1127:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    {
                    // InternalRegularExpressionParser.g:1127:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    // InternalRegularExpressionParser.g:1128:3: RULE_PATTERN_CHARACTER_NO_DASH
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0()); 
                    }
                    match(input,RULE_PATTERN_CHARACTER_NO_DASH,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1133:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1133:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1134:3: RULE_UNICODE_LETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueUNICODE_LETTERTerminalRuleCall_0_1()); 
                    }
                    match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueUNICODE_LETTERTerminalRuleCall_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1139:2: ( RULE_UNICODE_DIGIT )
                    {
                    // InternalRegularExpressionParser.g:1139:2: ( RULE_UNICODE_DIGIT )
                    // InternalRegularExpressionParser.g:1140:3: RULE_UNICODE_DIGIT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueUNICODE_DIGITTerminalRuleCall_0_2()); 
                    }
                    match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueUNICODE_DIGITTerminalRuleCall_0_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1145:2: ( HyphenMinus )
                    {
                    // InternalRegularExpressionParser.g:1145:2: ( HyphenMinus )
                    // InternalRegularExpressionParser.g:1146:3: HyphenMinus
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueHyphenMinusKeyword_0_3()); 
                    }
                    match(input,HyphenMinus,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueHyphenMinusKeyword_0_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:1151:2: ( Comma )
                    {
                    // InternalRegularExpressionParser.g:1151:2: ( Comma )
                    // InternalRegularExpressionParser.g:1152:3: Comma
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueCommaKeyword_0_4()); 
                    }
                    match(input,Comma,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueCommaKeyword_0_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalRegularExpressionParser.g:1157:2: ( EqualsSign )
                    {
                    // InternalRegularExpressionParser.g:1157:2: ( EqualsSign )
                    // InternalRegularExpressionParser.g:1158:3: EqualsSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueEqualsSignKeyword_0_5()); 
                    }
                    match(input,EqualsSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueEqualsSignKeyword_0_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalRegularExpressionParser.g:1163:2: ( Colon )
                    {
                    // InternalRegularExpressionParser.g:1163:2: ( Colon )
                    // InternalRegularExpressionParser.g:1164:3: Colon
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueColonKeyword_0_6()); 
                    }
                    match(input,Colon,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueColonKeyword_0_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalRegularExpressionParser.g:1169:2: ( ExclamationMark )
                    {
                    // InternalRegularExpressionParser.g:1169:2: ( ExclamationMark )
                    // InternalRegularExpressionParser.g:1170:3: ExclamationMark
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueExclamationMarkKeyword_0_7()); 
                    }
                    match(input,ExclamationMark,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueExclamationMarkKeyword_0_7()); 
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalRegularExpressionParser.g:1175:2: ( LeftCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1175:2: ( LeftCurlyBracket )
                    // InternalRegularExpressionParser.g:1176:3: LeftCurlyBracket
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueLeftCurlyBracketKeyword_0_8()); 
                    }
                    match(input,LeftCurlyBracket,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueLeftCurlyBracketKeyword_0_8()); 
                    }

                    }


                    }
                    break;
                case 10 :
                    // InternalRegularExpressionParser.g:1181:2: ( RightCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1181:2: ( RightCurlyBracket )
                    // InternalRegularExpressionParser.g:1182:3: RightCurlyBracket
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueRightCurlyBracketKeyword_0_9()); 
                    }
                    match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueRightCurlyBracketKeyword_0_9()); 
                    }

                    }


                    }
                    break;
                case 11 :
                    // InternalRegularExpressionParser.g:1187:2: ( RightSquareBracket )
                    {
                    // InternalRegularExpressionParser.g:1187:2: ( RightSquareBracket )
                    // InternalRegularExpressionParser.g:1188:3: RightSquareBracket
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueRightSquareBracketKeyword_0_10()); 
                    }
                    match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueRightSquareBracketKeyword_0_10()); 
                    }

                    }


                    }
                    break;
                case 12 :
                    // InternalRegularExpressionParser.g:1193:2: ( LessThanSign )
                    {
                    // InternalRegularExpressionParser.g:1193:2: ( LessThanSign )
                    // InternalRegularExpressionParser.g:1194:3: LessThanSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueLessThanSignKeyword_0_11()); 
                    }
                    match(input,LessThanSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueLessThanSignKeyword_0_11()); 
                    }

                    }


                    }
                    break;
                case 13 :
                    // InternalRegularExpressionParser.g:1199:2: ( GreaterThanSign )
                    {
                    // InternalRegularExpressionParser.g:1199:2: ( GreaterThanSign )
                    // InternalRegularExpressionParser.g:1200:3: GreaterThanSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPatternCharacterAccess().getValueGreaterThanSignKeyword_0_12()); 
                    }
                    match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPatternCharacterAccess().getValueGreaterThanSignKeyword_0_12()); 
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
    // $ANTLR end "rule__PatternCharacter__ValueAlternatives_0"


    // $ANTLR start "rule__AtomEscape__Alternatives"
    // InternalRegularExpressionParser.g:1209:1: rule__AtomEscape__Alternatives : ( ( ruleDecimalEscapeSequence ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) );
    public final void rule__AtomEscape__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1213:1: ( ( ruleDecimalEscapeSequence ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) )
            int alt9=7;
            switch ( input.LA(1) ) {
            case RULE_DECIMAL_ESCAPE:
                {
                alt9=1;
                }
                break;
            case RULE_CONTROL_ESCAPE:
                {
                alt9=2;
                }
                break;
            case RULE_CONTROL_LETTER_ESCAPE:
                {
                alt9=3;
                }
                break;
            case RULE_HEX_ESCAPE:
                {
                alt9=4;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt9=5;
                }
                break;
            case RULE_IDENTITY_ESCAPE:
                {
                alt9=6;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
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
                    // InternalRegularExpressionParser.g:1214:2: ( ruleDecimalEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1214:2: ( ruleDecimalEscapeSequence )
                    // InternalRegularExpressionParser.g:1215:3: ruleDecimalEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomEscapeAccess().getDecimalEscapeSequenceParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleDecimalEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomEscapeAccess().getDecimalEscapeSequenceParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1220:2: ( ruleCharacterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1220:2: ( ruleCharacterEscapeSequence )
                    // InternalRegularExpressionParser.g:1221:3: ruleCharacterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomEscapeAccess().getCharacterEscapeSequenceParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleCharacterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomEscapeAccess().getCharacterEscapeSequenceParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1226:2: ( ruleControlLetterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1226:2: ( ruleControlLetterEscapeSequence )
                    // InternalRegularExpressionParser.g:1227:3: ruleControlLetterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomEscapeAccess().getControlLetterEscapeSequenceParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleControlLetterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomEscapeAccess().getControlLetterEscapeSequenceParserRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1232:2: ( ruleHexEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1232:2: ( ruleHexEscapeSequence )
                    // InternalRegularExpressionParser.g:1233:3: ruleHexEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomEscapeAccess().getHexEscapeSequenceParserRuleCall_3()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleHexEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomEscapeAccess().getHexEscapeSequenceParserRuleCall_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:1238:2: ( ruleUnicodeEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1238:2: ( ruleUnicodeEscapeSequence )
                    // InternalRegularExpressionParser.g:1239:3: ruleUnicodeEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomEscapeAccess().getUnicodeEscapeSequenceParserRuleCall_4()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleUnicodeEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomEscapeAccess().getUnicodeEscapeSequenceParserRuleCall_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalRegularExpressionParser.g:1244:2: ( ruleIdentityEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1244:2: ( ruleIdentityEscapeSequence )
                    // InternalRegularExpressionParser.g:1245:3: ruleIdentityEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomEscapeAccess().getIdentityEscapeSequenceParserRuleCall_5()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleIdentityEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomEscapeAccess().getIdentityEscapeSequenceParserRuleCall_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalRegularExpressionParser.g:1250:2: ( ruleCharacterClassEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1250:2: ( ruleCharacterClassEscapeSequence )
                    // InternalRegularExpressionParser.g:1251:3: ruleCharacterClassEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomEscapeAccess().getCharacterClassEscapeSequenceParserRuleCall_6()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleCharacterClassEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomEscapeAccess().getCharacterClassEscapeSequenceParserRuleCall_6()); 
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
    // $ANTLR end "rule__AtomEscape__Alternatives"


    // $ANTLR start "rule__CharacterClassAtom__Alternatives"
    // InternalRegularExpressionParser.g:1260:1: rule__CharacterClassAtom__Alternatives : ( ( ruleEscapedCharacterClassAtom ) | ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) ) );
    public final void rule__CharacterClassAtom__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1264:1: ( ( ruleEscapedCharacterClassAtom ) | ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_WORD_BOUNDARY||(LA10_0>=RULE_CHARACTER_CLASS_ESCAPE && LA10_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA10_0>=RULE_HEX_ESCAPE && LA10_0<=RULE_UNICODE_ESCAPE)||(LA10_0>=RULE_DECIMAL_ESCAPE && LA10_0<=RULE_IDENTITY_ESCAPE)) ) {
                alt10=1;
            }
            else if ( ((LA10_0>=ExclamationMark && LA10_0<=LeftSquareBracket)||LA10_0==CircumflexAccent||(LA10_0>=LeftCurlyBracket && LA10_0<=RightCurlyBracket)||LA10_0==RULE_UNICODE_DIGIT||(LA10_0>=RULE_UNICODE_LETTER && LA10_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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
                    // InternalRegularExpressionParser.g:1265:2: ( ruleEscapedCharacterClassAtom )
                    {
                    // InternalRegularExpressionParser.g:1265:2: ( ruleEscapedCharacterClassAtom )
                    // InternalRegularExpressionParser.g:1266:3: ruleEscapedCharacterClassAtom
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getEscapedCharacterClassAtomParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleEscapedCharacterClassAtom();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getEscapedCharacterClassAtomParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1271:2: ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) )
                    {
                    // InternalRegularExpressionParser.g:1271:2: ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) )
                    // InternalRegularExpressionParser.g:1272:3: ( rule__CharacterClassAtom__CharacterAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterAssignment_1()); 
                    }
                    // InternalRegularExpressionParser.g:1273:3: ( rule__CharacterClassAtom__CharacterAssignment_1 )
                    // InternalRegularExpressionParser.g:1273:4: rule__CharacterClassAtom__CharacterAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__CharacterClassAtom__CharacterAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterAssignment_1()); 
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
    // $ANTLR end "rule__CharacterClassAtom__Alternatives"


    // $ANTLR start "rule__CharacterClassAtom__CharacterAlternatives_1_0"
    // InternalRegularExpressionParser.g:1281:1: rule__CharacterClassAtom__CharacterAlternatives_1_0 : ( ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( HyphenMinus ) | ( CircumflexAccent ) | ( DollarSign ) | ( FullStop ) | ( Asterisk ) | ( PlusSign ) | ( QuestionMark ) | ( LeftParenthesis ) | ( RightParenthesis ) | ( LeftSquareBracket ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( VerticalLine ) | ( Solidus ) | ( LessThanSign ) | ( GreaterThanSign ) | ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) );
    public final void rule__CharacterClassAtom__CharacterAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1285:1: ( ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( HyphenMinus ) | ( CircumflexAccent ) | ( DollarSign ) | ( FullStop ) | ( Asterisk ) | ( PlusSign ) | ( QuestionMark ) | ( LeftParenthesis ) | ( RightParenthesis ) | ( LeftSquareBracket ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( VerticalLine ) | ( Solidus ) | ( LessThanSign ) | ( GreaterThanSign ) | ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) )
            int alt11=23;
            switch ( input.LA(1) ) {
            case Comma:
                {
                alt11=1;
                }
                break;
            case EqualsSign:
                {
                alt11=2;
                }
                break;
            case Colon:
                {
                alt11=3;
                }
                break;
            case ExclamationMark:
                {
                alt11=4;
                }
                break;
            case HyphenMinus:
                {
                alt11=5;
                }
                break;
            case CircumflexAccent:
                {
                alt11=6;
                }
                break;
            case DollarSign:
                {
                alt11=7;
                }
                break;
            case FullStop:
                {
                alt11=8;
                }
                break;
            case Asterisk:
                {
                alt11=9;
                }
                break;
            case PlusSign:
                {
                alt11=10;
                }
                break;
            case QuestionMark:
                {
                alt11=11;
                }
                break;
            case LeftParenthesis:
                {
                alt11=12;
                }
                break;
            case RightParenthesis:
                {
                alt11=13;
                }
                break;
            case LeftSquareBracket:
                {
                alt11=14;
                }
                break;
            case LeftCurlyBracket:
                {
                alt11=15;
                }
                break;
            case RightCurlyBracket:
                {
                alt11=16;
                }
                break;
            case VerticalLine:
                {
                alt11=17;
                }
                break;
            case Solidus:
                {
                alt11=18;
                }
                break;
            case LessThanSign:
                {
                alt11=19;
                }
                break;
            case GreaterThanSign:
                {
                alt11=20;
                }
                break;
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt11=21;
                }
                break;
            case RULE_UNICODE_LETTER:
                {
                alt11=22;
                }
                break;
            case RULE_UNICODE_DIGIT:
                {
                alt11=23;
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
                    // InternalRegularExpressionParser.g:1286:2: ( Comma )
                    {
                    // InternalRegularExpressionParser.g:1286:2: ( Comma )
                    // InternalRegularExpressionParser.g:1287:3: Comma
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterCommaKeyword_1_0_0()); 
                    }
                    match(input,Comma,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterCommaKeyword_1_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1292:2: ( EqualsSign )
                    {
                    // InternalRegularExpressionParser.g:1292:2: ( EqualsSign )
                    // InternalRegularExpressionParser.g:1293:3: EqualsSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterEqualsSignKeyword_1_0_1()); 
                    }
                    match(input,EqualsSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterEqualsSignKeyword_1_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1298:2: ( Colon )
                    {
                    // InternalRegularExpressionParser.g:1298:2: ( Colon )
                    // InternalRegularExpressionParser.g:1299:3: Colon
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterColonKeyword_1_0_2()); 
                    }
                    match(input,Colon,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterColonKeyword_1_0_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1304:2: ( ExclamationMark )
                    {
                    // InternalRegularExpressionParser.g:1304:2: ( ExclamationMark )
                    // InternalRegularExpressionParser.g:1305:3: ExclamationMark
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterExclamationMarkKeyword_1_0_3()); 
                    }
                    match(input,ExclamationMark,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterExclamationMarkKeyword_1_0_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:1310:2: ( HyphenMinus )
                    {
                    // InternalRegularExpressionParser.g:1310:2: ( HyphenMinus )
                    // InternalRegularExpressionParser.g:1311:3: HyphenMinus
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterHyphenMinusKeyword_1_0_4()); 
                    }
                    match(input,HyphenMinus,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterHyphenMinusKeyword_1_0_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalRegularExpressionParser.g:1316:2: ( CircumflexAccent )
                    {
                    // InternalRegularExpressionParser.g:1316:2: ( CircumflexAccent )
                    // InternalRegularExpressionParser.g:1317:3: CircumflexAccent
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterCircumflexAccentKeyword_1_0_5()); 
                    }
                    match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterCircumflexAccentKeyword_1_0_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalRegularExpressionParser.g:1322:2: ( DollarSign )
                    {
                    // InternalRegularExpressionParser.g:1322:2: ( DollarSign )
                    // InternalRegularExpressionParser.g:1323:3: DollarSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterDollarSignKeyword_1_0_6()); 
                    }
                    match(input,DollarSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterDollarSignKeyword_1_0_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalRegularExpressionParser.g:1328:2: ( FullStop )
                    {
                    // InternalRegularExpressionParser.g:1328:2: ( FullStop )
                    // InternalRegularExpressionParser.g:1329:3: FullStop
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterFullStopKeyword_1_0_7()); 
                    }
                    match(input,FullStop,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterFullStopKeyword_1_0_7()); 
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalRegularExpressionParser.g:1334:2: ( Asterisk )
                    {
                    // InternalRegularExpressionParser.g:1334:2: ( Asterisk )
                    // InternalRegularExpressionParser.g:1335:3: Asterisk
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterAsteriskKeyword_1_0_8()); 
                    }
                    match(input,Asterisk,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterAsteriskKeyword_1_0_8()); 
                    }

                    }


                    }
                    break;
                case 10 :
                    // InternalRegularExpressionParser.g:1340:2: ( PlusSign )
                    {
                    // InternalRegularExpressionParser.g:1340:2: ( PlusSign )
                    // InternalRegularExpressionParser.g:1341:3: PlusSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterPlusSignKeyword_1_0_9()); 
                    }
                    match(input,PlusSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterPlusSignKeyword_1_0_9()); 
                    }

                    }


                    }
                    break;
                case 11 :
                    // InternalRegularExpressionParser.g:1346:2: ( QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:1346:2: ( QuestionMark )
                    // InternalRegularExpressionParser.g:1347:3: QuestionMark
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterQuestionMarkKeyword_1_0_10()); 
                    }
                    match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterQuestionMarkKeyword_1_0_10()); 
                    }

                    }


                    }
                    break;
                case 12 :
                    // InternalRegularExpressionParser.g:1352:2: ( LeftParenthesis )
                    {
                    // InternalRegularExpressionParser.g:1352:2: ( LeftParenthesis )
                    // InternalRegularExpressionParser.g:1353:3: LeftParenthesis
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftParenthesisKeyword_1_0_11()); 
                    }
                    match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftParenthesisKeyword_1_0_11()); 
                    }

                    }


                    }
                    break;
                case 13 :
                    // InternalRegularExpressionParser.g:1358:2: ( RightParenthesis )
                    {
                    // InternalRegularExpressionParser.g:1358:2: ( RightParenthesis )
                    // InternalRegularExpressionParser.g:1359:3: RightParenthesis
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterRightParenthesisKeyword_1_0_12()); 
                    }
                    match(input,RightParenthesis,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterRightParenthesisKeyword_1_0_12()); 
                    }

                    }


                    }
                    break;
                case 14 :
                    // InternalRegularExpressionParser.g:1364:2: ( LeftSquareBracket )
                    {
                    // InternalRegularExpressionParser.g:1364:2: ( LeftSquareBracket )
                    // InternalRegularExpressionParser.g:1365:3: LeftSquareBracket
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftSquareBracketKeyword_1_0_13()); 
                    }
                    match(input,LeftSquareBracket,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftSquareBracketKeyword_1_0_13()); 
                    }

                    }


                    }
                    break;
                case 15 :
                    // InternalRegularExpressionParser.g:1370:2: ( LeftCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1370:2: ( LeftCurlyBracket )
                    // InternalRegularExpressionParser.g:1371:3: LeftCurlyBracket
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftCurlyBracketKeyword_1_0_14()); 
                    }
                    match(input,LeftCurlyBracket,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftCurlyBracketKeyword_1_0_14()); 
                    }

                    }


                    }
                    break;
                case 16 :
                    // InternalRegularExpressionParser.g:1376:2: ( RightCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1376:2: ( RightCurlyBracket )
                    // InternalRegularExpressionParser.g:1377:3: RightCurlyBracket
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterRightCurlyBracketKeyword_1_0_15()); 
                    }
                    match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterRightCurlyBracketKeyword_1_0_15()); 
                    }

                    }


                    }
                    break;
                case 17 :
                    // InternalRegularExpressionParser.g:1382:2: ( VerticalLine )
                    {
                    // InternalRegularExpressionParser.g:1382:2: ( VerticalLine )
                    // InternalRegularExpressionParser.g:1383:3: VerticalLine
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterVerticalLineKeyword_1_0_16()); 
                    }
                    match(input,VerticalLine,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterVerticalLineKeyword_1_0_16()); 
                    }

                    }


                    }
                    break;
                case 18 :
                    // InternalRegularExpressionParser.g:1388:2: ( Solidus )
                    {
                    // InternalRegularExpressionParser.g:1388:2: ( Solidus )
                    // InternalRegularExpressionParser.g:1389:3: Solidus
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterSolidusKeyword_1_0_17()); 
                    }
                    match(input,Solidus,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterSolidusKeyword_1_0_17()); 
                    }

                    }


                    }
                    break;
                case 19 :
                    // InternalRegularExpressionParser.g:1394:2: ( LessThanSign )
                    {
                    // InternalRegularExpressionParser.g:1394:2: ( LessThanSign )
                    // InternalRegularExpressionParser.g:1395:3: LessThanSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterLessThanSignKeyword_1_0_18()); 
                    }
                    match(input,LessThanSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterLessThanSignKeyword_1_0_18()); 
                    }

                    }


                    }
                    break;
                case 20 :
                    // InternalRegularExpressionParser.g:1400:2: ( GreaterThanSign )
                    {
                    // InternalRegularExpressionParser.g:1400:2: ( GreaterThanSign )
                    // InternalRegularExpressionParser.g:1401:3: GreaterThanSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterGreaterThanSignKeyword_1_0_19()); 
                    }
                    match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterGreaterThanSignKeyword_1_0_19()); 
                    }

                    }


                    }
                    break;
                case 21 :
                    // InternalRegularExpressionParser.g:1406:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    {
                    // InternalRegularExpressionParser.g:1406:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    // InternalRegularExpressionParser.g:1407:3: RULE_PATTERN_CHARACTER_NO_DASH
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_20()); 
                    }
                    match(input,RULE_PATTERN_CHARACTER_NO_DASH,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_20()); 
                    }

                    }


                    }
                    break;
                case 22 :
                    // InternalRegularExpressionParser.g:1412:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1412:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1413:3: RULE_UNICODE_LETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_LETTERTerminalRuleCall_1_0_21()); 
                    }
                    match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_LETTERTerminalRuleCall_1_0_21()); 
                    }

                    }


                    }
                    break;
                case 23 :
                    // InternalRegularExpressionParser.g:1418:2: ( RULE_UNICODE_DIGIT )
                    {
                    // InternalRegularExpressionParser.g:1418:2: ( RULE_UNICODE_DIGIT )
                    // InternalRegularExpressionParser.g:1419:3: RULE_UNICODE_DIGIT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_DIGITTerminalRuleCall_1_0_22()); 
                    }
                    match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_DIGITTerminalRuleCall_1_0_22()); 
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
    // $ANTLR end "rule__CharacterClassAtom__CharacterAlternatives_1_0"


    // $ANTLR start "rule__EscapedCharacterClassAtom__Alternatives"
    // InternalRegularExpressionParser.g:1428:1: rule__EscapedCharacterClassAtom__Alternatives : ( ( ruleDecimalEscapeSequence ) | ( ruleBackspace ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) );
    public final void rule__EscapedCharacterClassAtom__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1432:1: ( ( ruleDecimalEscapeSequence ) | ( ruleBackspace ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) )
            int alt12=8;
            switch ( input.LA(1) ) {
            case RULE_DECIMAL_ESCAPE:
                {
                alt12=1;
                }
                break;
            case RULE_WORD_BOUNDARY:
                {
                alt12=2;
                }
                break;
            case RULE_CONTROL_ESCAPE:
                {
                alt12=3;
                }
                break;
            case RULE_CONTROL_LETTER_ESCAPE:
                {
                alt12=4;
                }
                break;
            case RULE_HEX_ESCAPE:
                {
                alt12=5;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt12=6;
                }
                break;
            case RULE_IDENTITY_ESCAPE:
                {
                alt12=7;
                }
                break;
            case RULE_CHARACTER_CLASS_ESCAPE:
                {
                alt12=8;
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
                    // InternalRegularExpressionParser.g:1433:2: ( ruleDecimalEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1433:2: ( ruleDecimalEscapeSequence )
                    // InternalRegularExpressionParser.g:1434:3: ruleDecimalEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getDecimalEscapeSequenceParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleDecimalEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getDecimalEscapeSequenceParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1439:2: ( ruleBackspace )
                    {
                    // InternalRegularExpressionParser.g:1439:2: ( ruleBackspace )
                    // InternalRegularExpressionParser.g:1440:3: ruleBackspace
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getBackspaceParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleBackspace();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getBackspaceParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1445:2: ( ruleCharacterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1445:2: ( ruleCharacterEscapeSequence )
                    // InternalRegularExpressionParser.g:1446:3: ruleCharacterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterEscapeSequenceParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleCharacterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterEscapeSequenceParserRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1451:2: ( ruleControlLetterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1451:2: ( ruleControlLetterEscapeSequence )
                    // InternalRegularExpressionParser.g:1452:3: ruleControlLetterEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getControlLetterEscapeSequenceParserRuleCall_3()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleControlLetterEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getControlLetterEscapeSequenceParserRuleCall_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:1457:2: ( ruleHexEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1457:2: ( ruleHexEscapeSequence )
                    // InternalRegularExpressionParser.g:1458:3: ruleHexEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getHexEscapeSequenceParserRuleCall_4()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleHexEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getHexEscapeSequenceParserRuleCall_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalRegularExpressionParser.g:1463:2: ( ruleUnicodeEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1463:2: ( ruleUnicodeEscapeSequence )
                    // InternalRegularExpressionParser.g:1464:3: ruleUnicodeEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getUnicodeEscapeSequenceParserRuleCall_5()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleUnicodeEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getUnicodeEscapeSequenceParserRuleCall_5()); 
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalRegularExpressionParser.g:1469:2: ( ruleIdentityEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1469:2: ( ruleIdentityEscapeSequence )
                    // InternalRegularExpressionParser.g:1470:3: ruleIdentityEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getIdentityEscapeSequenceParserRuleCall_6()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleIdentityEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getIdentityEscapeSequenceParserRuleCall_6()); 
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalRegularExpressionParser.g:1475:2: ( ruleCharacterClassEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1475:2: ( ruleCharacterClassEscapeSequence )
                    // InternalRegularExpressionParser.g:1476:3: ruleCharacterClassEscapeSequence
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterClassEscapeSequenceParserRuleCall_7()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleCharacterClassEscapeSequence();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterClassEscapeSequenceParserRuleCall_7()); 
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
    // $ANTLR end "rule__EscapedCharacterClassAtom__Alternatives"


    // $ANTLR start "rule__Group__Alternatives_2"
    // InternalRegularExpressionParser.g:1485:1: rule__Group__Alternatives_2 : ( ( ( rule__Group__Group_2_0__0 ) ) | ( ( rule__Group__Group_2_1__0 ) ) );
    public final void rule__Group__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1489:1: ( ( ( rule__Group__Group_2_0__0 ) ) | ( ( rule__Group__Group_2_1__0 ) ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==QuestionMark) ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==LessThanSign) ) {
                    alt13=1;
                }
                else if ( (LA13_1==Colon) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalRegularExpressionParser.g:1490:2: ( ( rule__Group__Group_2_0__0 ) )
                    {
                    // InternalRegularExpressionParser.g:1490:2: ( ( rule__Group__Group_2_0__0 ) )
                    // InternalRegularExpressionParser.g:1491:3: ( rule__Group__Group_2_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGroupAccess().getGroup_2_0()); 
                    }
                    // InternalRegularExpressionParser.g:1492:3: ( rule__Group__Group_2_0__0 )
                    // InternalRegularExpressionParser.g:1492:4: rule__Group__Group_2_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Group__Group_2_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGroupAccess().getGroup_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1496:2: ( ( rule__Group__Group_2_1__0 ) )
                    {
                    // InternalRegularExpressionParser.g:1496:2: ( ( rule__Group__Group_2_1__0 ) )
                    // InternalRegularExpressionParser.g:1497:3: ( rule__Group__Group_2_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getGroupAccess().getGroup_2_1()); 
                    }
                    // InternalRegularExpressionParser.g:1498:3: ( rule__Group__Group_2_1__0 )
                    // InternalRegularExpressionParser.g:1498:4: rule__Group__Group_2_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Group__Group_2_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getGroupAccess().getGroup_2_1()); 
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
    // $ANTLR end "rule__Group__Alternatives_2"


    // $ANTLR start "rule__RegExpIdentifierStart__Alternatives"
    // InternalRegularExpressionParser.g:1506:1: rule__RegExpIdentifierStart__Alternatives : ( ( RULE_UNICODE_LETTER ) | ( DollarSign ) | ( KW__ ) | ( RULE_UNICODE_ESCAPE ) );
    public final void rule__RegExpIdentifierStart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1510:1: ( ( RULE_UNICODE_LETTER ) | ( DollarSign ) | ( KW__ ) | ( RULE_UNICODE_ESCAPE ) )
            int alt14=4;
            switch ( input.LA(1) ) {
            case RULE_UNICODE_LETTER:
                {
                alt14=1;
                }
                break;
            case DollarSign:
                {
                alt14=2;
                }
                break;
            case KW__:
                {
                alt14=3;
                }
                break;
            case RULE_UNICODE_ESCAPE:
                {
                alt14=4;
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
                    // InternalRegularExpressionParser.g:1511:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1511:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1512:3: RULE_UNICODE_LETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_LETTERTerminalRuleCall_0()); 
                    }
                    match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_LETTERTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1517:2: ( DollarSign )
                    {
                    // InternalRegularExpressionParser.g:1517:2: ( DollarSign )
                    // InternalRegularExpressionParser.g:1518:3: DollarSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierStartAccess().getDollarSignKeyword_1()); 
                    }
                    match(input,DollarSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierStartAccess().getDollarSignKeyword_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1523:2: ( KW__ )
                    {
                    // InternalRegularExpressionParser.g:1523:2: ( KW__ )
                    // InternalRegularExpressionParser.g:1524:3: KW__
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierStartAccess().get_Keyword_2()); 
                    }
                    match(input,KW__,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierStartAccess().get_Keyword_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1529:2: ( RULE_UNICODE_ESCAPE )
                    {
                    // InternalRegularExpressionParser.g:1529:2: ( RULE_UNICODE_ESCAPE )
                    // InternalRegularExpressionParser.g:1530:3: RULE_UNICODE_ESCAPE
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_ESCAPETerminalRuleCall_3()); 
                    }
                    match(input,RULE_UNICODE_ESCAPE,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_ESCAPETerminalRuleCall_3()); 
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
    // $ANTLR end "rule__RegExpIdentifierStart__Alternatives"


    // $ANTLR start "rule__RegExpIdentifierPart__Alternatives"
    // InternalRegularExpressionParser.g:1539:1: rule__RegExpIdentifierPart__Alternatives : ( ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) | ( DollarSign ) | ( KW__ ) | ( RULE_UNICODE_ESCAPE ) );
    public final void rule__RegExpIdentifierPart__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1543:1: ( ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) | ( DollarSign ) | ( KW__ ) | ( RULE_UNICODE_ESCAPE ) )
            int alt15=5;
            switch ( input.LA(1) ) {
            case RULE_UNICODE_LETTER:
                {
                alt15=1;
                }
                break;
            case RULE_UNICODE_DIGIT:
                {
                alt15=2;
                }
                break;
            case DollarSign:
                {
                alt15=3;
                }
                break;
            case KW__:
                {
                alt15=4;
                }
                break;
            case RULE_UNICODE_ESCAPE:
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
                    // InternalRegularExpressionParser.g:1544:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1544:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1545:3: RULE_UNICODE_LETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_LETTERTerminalRuleCall_0()); 
                    }
                    match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_LETTERTerminalRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1550:2: ( RULE_UNICODE_DIGIT )
                    {
                    // InternalRegularExpressionParser.g:1550:2: ( RULE_UNICODE_DIGIT )
                    // InternalRegularExpressionParser.g:1551:3: RULE_UNICODE_DIGIT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_DIGITTerminalRuleCall_1()); 
                    }
                    match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_DIGITTerminalRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1556:2: ( DollarSign )
                    {
                    // InternalRegularExpressionParser.g:1556:2: ( DollarSign )
                    // InternalRegularExpressionParser.g:1557:3: DollarSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierPartAccess().getDollarSignKeyword_2()); 
                    }
                    match(input,DollarSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierPartAccess().getDollarSignKeyword_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalRegularExpressionParser.g:1562:2: ( KW__ )
                    {
                    // InternalRegularExpressionParser.g:1562:2: ( KW__ )
                    // InternalRegularExpressionParser.g:1563:3: KW__
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierPartAccess().get_Keyword_3()); 
                    }
                    match(input,KW__,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierPartAccess().get_Keyword_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalRegularExpressionParser.g:1568:2: ( RULE_UNICODE_ESCAPE )
                    {
                    // InternalRegularExpressionParser.g:1568:2: ( RULE_UNICODE_ESCAPE )
                    // InternalRegularExpressionParser.g:1569:3: RULE_UNICODE_ESCAPE
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_ESCAPETerminalRuleCall_4()); 
                    }
                    match(input,RULE_UNICODE_ESCAPE,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_ESCAPETerminalRuleCall_4()); 
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
    // $ANTLR end "rule__RegExpIdentifierPart__Alternatives"


    // $ANTLR start "rule__Quantifier__Alternatives"
    // InternalRegularExpressionParser.g:1578:1: rule__Quantifier__Alternatives : ( ( ruleSimpleQuantifier ) | ( ruleExactQuantifier ) );
    public final void rule__Quantifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1582:1: ( ( ruleSimpleQuantifier ) | ( ruleExactQuantifier ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=Asterisk && LA16_0<=PlusSign)||LA16_0==QuestionMark) ) {
                alt16=1;
            }
            else if ( (LA16_0==LeftCurlyBracket) ) {
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
                    // InternalRegularExpressionParser.g:1583:2: ( ruleSimpleQuantifier )
                    {
                    // InternalRegularExpressionParser.g:1583:2: ( ruleSimpleQuantifier )
                    // InternalRegularExpressionParser.g:1584:3: ruleSimpleQuantifier
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQuantifierAccess().getSimpleQuantifierParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleSimpleQuantifier();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getQuantifierAccess().getSimpleQuantifierParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1589:2: ( ruleExactQuantifier )
                    {
                    // InternalRegularExpressionParser.g:1589:2: ( ruleExactQuantifier )
                    // InternalRegularExpressionParser.g:1590:3: ruleExactQuantifier
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getQuantifierAccess().getExactQuantifierParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_2);
                    ruleExactQuantifier();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getQuantifierAccess().getExactQuantifierParserRuleCall_1()); 
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
    // $ANTLR end "rule__Quantifier__Alternatives"


    // $ANTLR start "rule__SimpleQuantifier__QuantifierAlternatives_0_0"
    // InternalRegularExpressionParser.g:1599:1: rule__SimpleQuantifier__QuantifierAlternatives_0_0 : ( ( PlusSign ) | ( Asterisk ) | ( QuestionMark ) );
    public final void rule__SimpleQuantifier__QuantifierAlternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1603:1: ( ( PlusSign ) | ( Asterisk ) | ( QuestionMark ) )
            int alt17=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                alt17=1;
                }
                break;
            case Asterisk:
                {
                alt17=2;
                }
                break;
            case QuestionMark:
                {
                alt17=3;
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
                    // InternalRegularExpressionParser.g:1604:2: ( PlusSign )
                    {
                    // InternalRegularExpressionParser.g:1604:2: ( PlusSign )
                    // InternalRegularExpressionParser.g:1605:3: PlusSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getSimpleQuantifierAccess().getQuantifierPlusSignKeyword_0_0_0()); 
                    }
                    match(input,PlusSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getSimpleQuantifierAccess().getQuantifierPlusSignKeyword_0_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1610:2: ( Asterisk )
                    {
                    // InternalRegularExpressionParser.g:1610:2: ( Asterisk )
                    // InternalRegularExpressionParser.g:1611:3: Asterisk
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAsteriskKeyword_0_0_1()); 
                    }
                    match(input,Asterisk,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getSimpleQuantifierAccess().getQuantifierAsteriskKeyword_0_0_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionParser.g:1616:2: ( QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:1616:2: ( QuestionMark )
                    // InternalRegularExpressionParser.g:1617:3: QuestionMark
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getSimpleQuantifierAccess().getQuantifierQuestionMarkKeyword_0_0_2()); 
                    }
                    match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getSimpleQuantifierAccess().getQuantifierQuestionMarkKeyword_0_0_2()); 
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
    // $ANTLR end "rule__SimpleQuantifier__QuantifierAlternatives_0_0"


    // $ANTLR start "rule__ExactQuantifier__Alternatives_3"
    // InternalRegularExpressionParser.g:1626:1: rule__ExactQuantifier__Alternatives_3 : ( ( ( rule__ExactQuantifier__Group_3_0__0 ) ) | ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) ) );
    public final void rule__ExactQuantifier__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1630:1: ( ( ( rule__ExactQuantifier__Group_3_0__0 ) ) | ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==Comma) ) {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==RULE_UNICODE_DIGIT) ) {
                    alt18=1;
                }
                else if ( (LA18_1==EOF||LA18_1==RightCurlyBracket) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalRegularExpressionParser.g:1631:2: ( ( rule__ExactQuantifier__Group_3_0__0 ) )
                    {
                    // InternalRegularExpressionParser.g:1631:2: ( ( rule__ExactQuantifier__Group_3_0__0 ) )
                    // InternalRegularExpressionParser.g:1632:3: ( rule__ExactQuantifier__Group_3_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getExactQuantifierAccess().getGroup_3_0()); 
                    }
                    // InternalRegularExpressionParser.g:1633:3: ( rule__ExactQuantifier__Group_3_0__0 )
                    // InternalRegularExpressionParser.g:1633:4: rule__ExactQuantifier__Group_3_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExactQuantifier__Group_3_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getExactQuantifierAccess().getGroup_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1637:2: ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) )
                    {
                    // InternalRegularExpressionParser.g:1637:2: ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) )
                    // InternalRegularExpressionParser.g:1638:3: ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxAssignment_3_1()); 
                    }
                    // InternalRegularExpressionParser.g:1639:3: ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 )
                    // InternalRegularExpressionParser.g:1639:4: rule__ExactQuantifier__UnboundedMaxAssignment_3_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExactQuantifier__UnboundedMaxAssignment_3_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getExactQuantifierAccess().getUnboundedMaxAssignment_3_1()); 
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
    // $ANTLR end "rule__ExactQuantifier__Alternatives_3"


    // $ANTLR start "rule__RegularExpressionFlags__FlagsAlternatives_1_0"
    // InternalRegularExpressionParser.g:1647:1: rule__RegularExpressionFlags__FlagsAlternatives_1_0 : ( ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_ESCAPE ) );
    public final void rule__RegularExpressionFlags__FlagsAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1651:1: ( ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_ESCAPE ) )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_UNICODE_LETTER) ) {
                alt19=1;
            }
            else if ( (LA19_0==RULE_UNICODE_ESCAPE) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalRegularExpressionParser.g:1652:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1652:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1653:3: RULE_UNICODE_LETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_LETTERTerminalRuleCall_1_0_0()); 
                    }
                    match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_LETTERTerminalRuleCall_1_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:1658:2: ( RULE_UNICODE_ESCAPE )
                    {
                    // InternalRegularExpressionParser.g:1658:2: ( RULE_UNICODE_ESCAPE )
                    // InternalRegularExpressionParser.g:1659:3: RULE_UNICODE_ESCAPE
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1()); 
                    }
                    match(input,RULE_UNICODE_ESCAPE,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1()); 
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
    // $ANTLR end "rule__RegularExpressionFlags__FlagsAlternatives_1_0"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__0"
    // InternalRegularExpressionParser.g:1668:1: rule__RegularExpressionLiteral__Group__0 : rule__RegularExpressionLiteral__Group__0__Impl rule__RegularExpressionLiteral__Group__1 ;
    public final void rule__RegularExpressionLiteral__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1672:1: ( rule__RegularExpressionLiteral__Group__0__Impl rule__RegularExpressionLiteral__Group__1 )
            // InternalRegularExpressionParser.g:1673:2: rule__RegularExpressionLiteral__Group__0__Impl rule__RegularExpressionLiteral__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__RegularExpressionLiteral__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RegularExpressionLiteral__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__0"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__0__Impl"
    // InternalRegularExpressionParser.g:1680:1: rule__RegularExpressionLiteral__Group__0__Impl : ( Solidus ) ;
    public final void rule__RegularExpressionLiteral__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1684:1: ( ( Solidus ) )
            // InternalRegularExpressionParser.g:1685:1: ( Solidus )
            {
            // InternalRegularExpressionParser.g:1685:1: ( Solidus )
            // InternalRegularExpressionParser.g:1686:2: Solidus
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_0()); 
            }
            match(input,Solidus,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__0__Impl"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__1"
    // InternalRegularExpressionParser.g:1695:1: rule__RegularExpressionLiteral__Group__1 : rule__RegularExpressionLiteral__Group__1__Impl rule__RegularExpressionLiteral__Group__2 ;
    public final void rule__RegularExpressionLiteral__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1699:1: ( rule__RegularExpressionLiteral__Group__1__Impl rule__RegularExpressionLiteral__Group__2 )
            // InternalRegularExpressionParser.g:1700:2: rule__RegularExpressionLiteral__Group__1__Impl rule__RegularExpressionLiteral__Group__2
            {
            pushFollow(FOLLOW_5);
            rule__RegularExpressionLiteral__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RegularExpressionLiteral__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__1"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__1__Impl"
    // InternalRegularExpressionParser.g:1707:1: rule__RegularExpressionLiteral__Group__1__Impl : ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) ) ;
    public final void rule__RegularExpressionLiteral__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1711:1: ( ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) ) )
            // InternalRegularExpressionParser.g:1712:1: ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) )
            {
            // InternalRegularExpressionParser.g:1712:1: ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) )
            // InternalRegularExpressionParser.g:1713:2: ( rule__RegularExpressionLiteral__BodyAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getBodyAssignment_1()); 
            }
            // InternalRegularExpressionParser.g:1714:2: ( rule__RegularExpressionLiteral__BodyAssignment_1 )
            // InternalRegularExpressionParser.g:1714:3: rule__RegularExpressionLiteral__BodyAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionLiteral__BodyAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralAccess().getBodyAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__1__Impl"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__2"
    // InternalRegularExpressionParser.g:1722:1: rule__RegularExpressionLiteral__Group__2 : rule__RegularExpressionLiteral__Group__2__Impl rule__RegularExpressionLiteral__Group__3 ;
    public final void rule__RegularExpressionLiteral__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1726:1: ( rule__RegularExpressionLiteral__Group__2__Impl rule__RegularExpressionLiteral__Group__3 )
            // InternalRegularExpressionParser.g:1727:2: rule__RegularExpressionLiteral__Group__2__Impl rule__RegularExpressionLiteral__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__RegularExpressionLiteral__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RegularExpressionLiteral__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__2"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__2__Impl"
    // InternalRegularExpressionParser.g:1734:1: rule__RegularExpressionLiteral__Group__2__Impl : ( Solidus ) ;
    public final void rule__RegularExpressionLiteral__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1738:1: ( ( Solidus ) )
            // InternalRegularExpressionParser.g:1739:1: ( Solidus )
            {
            // InternalRegularExpressionParser.g:1739:1: ( Solidus )
            // InternalRegularExpressionParser.g:1740:2: Solidus
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_2()); 
            }
            match(input,Solidus,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__2__Impl"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__3"
    // InternalRegularExpressionParser.g:1749:1: rule__RegularExpressionLiteral__Group__3 : rule__RegularExpressionLiteral__Group__3__Impl ;
    public final void rule__RegularExpressionLiteral__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1753:1: ( rule__RegularExpressionLiteral__Group__3__Impl )
            // InternalRegularExpressionParser.g:1754:2: rule__RegularExpressionLiteral__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionLiteral__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__3"


    // $ANTLR start "rule__RegularExpressionLiteral__Group__3__Impl"
    // InternalRegularExpressionParser.g:1760:1: rule__RegularExpressionLiteral__Group__3__Impl : ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) ) ;
    public final void rule__RegularExpressionLiteral__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1764:1: ( ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) ) )
            // InternalRegularExpressionParser.g:1765:1: ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) )
            {
            // InternalRegularExpressionParser.g:1765:1: ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) )
            // InternalRegularExpressionParser.g:1766:2: ( rule__RegularExpressionLiteral__FlagsAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getFlagsAssignment_3()); 
            }
            // InternalRegularExpressionParser.g:1767:2: ( rule__RegularExpressionLiteral__FlagsAssignment_3 )
            // InternalRegularExpressionParser.g:1767:3: rule__RegularExpressionLiteral__FlagsAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionLiteral__FlagsAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralAccess().getFlagsAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__Group__3__Impl"


    // $ANTLR start "rule__Disjunction__Group_0__0"
    // InternalRegularExpressionParser.g:1776:1: rule__Disjunction__Group_0__0 : rule__Disjunction__Group_0__0__Impl rule__Disjunction__Group_0__1 ;
    public final void rule__Disjunction__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1780:1: ( rule__Disjunction__Group_0__0__Impl rule__Disjunction__Group_0__1 )
            // InternalRegularExpressionParser.g:1781:2: rule__Disjunction__Group_0__0__Impl rule__Disjunction__Group_0__1
            {
            pushFollow(FOLLOW_7);
            rule__Disjunction__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0__0"


    // $ANTLR start "rule__Disjunction__Group_0__0__Impl"
    // InternalRegularExpressionParser.g:1788:1: rule__Disjunction__Group_0__0__Impl : ( ruleAlternative ) ;
    public final void rule__Disjunction__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1792:1: ( ( ruleAlternative ) )
            // InternalRegularExpressionParser.g:1793:1: ( ruleAlternative )
            {
            // InternalRegularExpressionParser.g:1793:1: ( ruleAlternative )
            // InternalRegularExpressionParser.g:1794:2: ruleAlternative
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getAlternativeParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleAlternative();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getAlternativeParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0__0__Impl"


    // $ANTLR start "rule__Disjunction__Group_0__1"
    // InternalRegularExpressionParser.g:1803:1: rule__Disjunction__Group_0__1 : rule__Disjunction__Group_0__1__Impl ;
    public final void rule__Disjunction__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1807:1: ( rule__Disjunction__Group_0__1__Impl )
            // InternalRegularExpressionParser.g:1808:2: rule__Disjunction__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0__1"


    // $ANTLR start "rule__Disjunction__Group_0__1__Impl"
    // InternalRegularExpressionParser.g:1814:1: rule__Disjunction__Group_0__1__Impl : ( ( rule__Disjunction__Group_0_1__0 )? ) ;
    public final void rule__Disjunction__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1818:1: ( ( ( rule__Disjunction__Group_0_1__0 )? ) )
            // InternalRegularExpressionParser.g:1819:1: ( ( rule__Disjunction__Group_0_1__0 )? )
            {
            // InternalRegularExpressionParser.g:1819:1: ( ( rule__Disjunction__Group_0_1__0 )? )
            // InternalRegularExpressionParser.g:1820:2: ( rule__Disjunction__Group_0_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_0_1()); 
            }
            // InternalRegularExpressionParser.g:1821:2: ( rule__Disjunction__Group_0_1__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==VerticalLine) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalRegularExpressionParser.g:1821:3: rule__Disjunction__Group_0_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Disjunction__Group_0_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getGroup_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0__1__Impl"


    // $ANTLR start "rule__Disjunction__Group_0_1__0"
    // InternalRegularExpressionParser.g:1830:1: rule__Disjunction__Group_0_1__0 : rule__Disjunction__Group_0_1__0__Impl rule__Disjunction__Group_0_1__1 ;
    public final void rule__Disjunction__Group_0_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1834:1: ( rule__Disjunction__Group_0_1__0__Impl rule__Disjunction__Group_0_1__1 )
            // InternalRegularExpressionParser.g:1835:2: rule__Disjunction__Group_0_1__0__Impl rule__Disjunction__Group_0_1__1
            {
            pushFollow(FOLLOW_7);
            rule__Disjunction__Group_0_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_0_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0_1__0"


    // $ANTLR start "rule__Disjunction__Group_0_1__0__Impl"
    // InternalRegularExpressionParser.g:1842:1: rule__Disjunction__Group_0_1__0__Impl : ( () ) ;
    public final void rule__Disjunction__Group_0_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1846:1: ( ( () ) )
            // InternalRegularExpressionParser.g:1847:1: ( () )
            {
            // InternalRegularExpressionParser.g:1847:1: ( () )
            // InternalRegularExpressionParser.g:1848:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()); 
            }
            // InternalRegularExpressionParser.g:1849:2: ()
            // InternalRegularExpressionParser.g:1849:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0_1__0__Impl"


    // $ANTLR start "rule__Disjunction__Group_0_1__1"
    // InternalRegularExpressionParser.g:1857:1: rule__Disjunction__Group_0_1__1 : rule__Disjunction__Group_0_1__1__Impl ;
    public final void rule__Disjunction__Group_0_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1861:1: ( rule__Disjunction__Group_0_1__1__Impl )
            // InternalRegularExpressionParser.g:1862:2: rule__Disjunction__Group_0_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_0_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0_1__1"


    // $ANTLR start "rule__Disjunction__Group_0_1__1__Impl"
    // InternalRegularExpressionParser.g:1868:1: rule__Disjunction__Group_0_1__1__Impl : ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) ) ;
    public final void rule__Disjunction__Group_0_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1872:1: ( ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) ) )
            // InternalRegularExpressionParser.g:1873:1: ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) )
            {
            // InternalRegularExpressionParser.g:1873:1: ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) )
            // InternalRegularExpressionParser.g:1874:2: ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* )
            {
            // InternalRegularExpressionParser.g:1874:2: ( ( rule__Disjunction__Group_0_1_1__0 ) )
            // InternalRegularExpressionParser.g:1875:3: ( rule__Disjunction__Group_0_1_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); 
            }
            // InternalRegularExpressionParser.g:1876:3: ( rule__Disjunction__Group_0_1_1__0 )
            // InternalRegularExpressionParser.g:1876:4: rule__Disjunction__Group_0_1_1__0
            {
            pushFollow(FOLLOW_8);
            rule__Disjunction__Group_0_1_1__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); 
            }

            }

            // InternalRegularExpressionParser.g:1879:2: ( ( rule__Disjunction__Group_0_1_1__0 )* )
            // InternalRegularExpressionParser.g:1880:3: ( rule__Disjunction__Group_0_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); 
            }
            // InternalRegularExpressionParser.g:1881:3: ( rule__Disjunction__Group_0_1_1__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==VerticalLine) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:1881:4: rule__Disjunction__Group_0_1_1__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__Disjunction__Group_0_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); 
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
    // $ANTLR end "rule__Disjunction__Group_0_1__1__Impl"


    // $ANTLR start "rule__Disjunction__Group_0_1_1__0"
    // InternalRegularExpressionParser.g:1891:1: rule__Disjunction__Group_0_1_1__0 : rule__Disjunction__Group_0_1_1__0__Impl rule__Disjunction__Group_0_1_1__1 ;
    public final void rule__Disjunction__Group_0_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1895:1: ( rule__Disjunction__Group_0_1_1__0__Impl rule__Disjunction__Group_0_1_1__1 )
            // InternalRegularExpressionParser.g:1896:2: rule__Disjunction__Group_0_1_1__0__Impl rule__Disjunction__Group_0_1_1__1
            {
            pushFollow(FOLLOW_9);
            rule__Disjunction__Group_0_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_0_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0_1_1__0"


    // $ANTLR start "rule__Disjunction__Group_0_1_1__0__Impl"
    // InternalRegularExpressionParser.g:1903:1: rule__Disjunction__Group_0_1_1__0__Impl : ( VerticalLine ) ;
    public final void rule__Disjunction__Group_0_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1907:1: ( ( VerticalLine ) )
            // InternalRegularExpressionParser.g:1908:1: ( VerticalLine )
            {
            // InternalRegularExpressionParser.g:1908:1: ( VerticalLine )
            // InternalRegularExpressionParser.g:1909:2: VerticalLine
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0()); 
            }
            match(input,VerticalLine,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0_1_1__0__Impl"


    // $ANTLR start "rule__Disjunction__Group_0_1_1__1"
    // InternalRegularExpressionParser.g:1918:1: rule__Disjunction__Group_0_1_1__1 : rule__Disjunction__Group_0_1_1__1__Impl ;
    public final void rule__Disjunction__Group_0_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1922:1: ( rule__Disjunction__Group_0_1_1__1__Impl )
            // InternalRegularExpressionParser.g:1923:2: rule__Disjunction__Group_0_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_0_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0_1_1__1"


    // $ANTLR start "rule__Disjunction__Group_0_1_1__1__Impl"
    // InternalRegularExpressionParser.g:1929:1: rule__Disjunction__Group_0_1_1__1__Impl : ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? ) ;
    public final void rule__Disjunction__Group_0_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1933:1: ( ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? ) )
            // InternalRegularExpressionParser.g:1934:1: ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? )
            {
            // InternalRegularExpressionParser.g:1934:1: ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? )
            // InternalRegularExpressionParser.g:1935:2: ( rule__Disjunction__ElementsAssignment_0_1_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getElementsAssignment_0_1_1_1()); 
            }
            // InternalRegularExpressionParser.g:1936:2: ( rule__Disjunction__ElementsAssignment_0_1_1_1 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=ExclamationMark && LA22_0<=LeftParenthesis)||(LA22_0>=Comma && LA22_0<=FullStop)||(LA22_0>=Colon && LA22_0<=GreaterThanSign)||(LA22_0>=LeftSquareBracket && LA22_0<=CircumflexAccent)||LA22_0==LeftCurlyBracket||(LA22_0>=RightCurlyBracket && LA22_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA22_0>=RULE_HEX_ESCAPE && LA22_0<=RULE_UNICODE_ESCAPE)||(LA22_0>=RULE_DECIMAL_ESCAPE && LA22_0<=RULE_IDENTITY_ESCAPE)||LA22_0==RULE_UNICODE_DIGIT||(LA22_0>=RULE_UNICODE_LETTER && LA22_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalRegularExpressionParser.g:1936:3: rule__Disjunction__ElementsAssignment_0_1_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Disjunction__ElementsAssignment_0_1_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getElementsAssignment_0_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_0_1_1__1__Impl"


    // $ANTLR start "rule__Disjunction__Group_1__0"
    // InternalRegularExpressionParser.g:1945:1: rule__Disjunction__Group_1__0 : rule__Disjunction__Group_1__0__Impl rule__Disjunction__Group_1__1 ;
    public final void rule__Disjunction__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1949:1: ( rule__Disjunction__Group_1__0__Impl rule__Disjunction__Group_1__1 )
            // InternalRegularExpressionParser.g:1950:2: rule__Disjunction__Group_1__0__Impl rule__Disjunction__Group_1__1
            {
            pushFollow(FOLLOW_4);
            rule__Disjunction__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1__0"


    // $ANTLR start "rule__Disjunction__Group_1__0__Impl"
    // InternalRegularExpressionParser.g:1957:1: rule__Disjunction__Group_1__0__Impl : ( () ) ;
    public final void rule__Disjunction__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1961:1: ( ( () ) )
            // InternalRegularExpressionParser.g:1962:1: ( () )
            {
            // InternalRegularExpressionParser.g:1962:1: ( () )
            // InternalRegularExpressionParser.g:1963:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getDisjunctionAction_1_0()); 
            }
            // InternalRegularExpressionParser.g:1964:2: ()
            // InternalRegularExpressionParser.g:1964:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getDisjunctionAction_1_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1__0__Impl"


    // $ANTLR start "rule__Disjunction__Group_1__1"
    // InternalRegularExpressionParser.g:1972:1: rule__Disjunction__Group_1__1 : rule__Disjunction__Group_1__1__Impl ;
    public final void rule__Disjunction__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1976:1: ( rule__Disjunction__Group_1__1__Impl )
            // InternalRegularExpressionParser.g:1977:2: rule__Disjunction__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1__1"


    // $ANTLR start "rule__Disjunction__Group_1__1__Impl"
    // InternalRegularExpressionParser.g:1983:1: rule__Disjunction__Group_1__1__Impl : ( ( rule__Disjunction__Group_1_1__0 )* ) ;
    public final void rule__Disjunction__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1987:1: ( ( ( rule__Disjunction__Group_1_1__0 )* ) )
            // InternalRegularExpressionParser.g:1988:1: ( ( rule__Disjunction__Group_1_1__0 )* )
            {
            // InternalRegularExpressionParser.g:1988:1: ( ( rule__Disjunction__Group_1_1__0 )* )
            // InternalRegularExpressionParser.g:1989:2: ( rule__Disjunction__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_1_1()); 
            }
            // InternalRegularExpressionParser.g:1990:2: ( rule__Disjunction__Group_1_1__0 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==VerticalLine) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:1990:3: rule__Disjunction__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__Disjunction__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getGroup_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1__1__Impl"


    // $ANTLR start "rule__Disjunction__Group_1_1__0"
    // InternalRegularExpressionParser.g:1999:1: rule__Disjunction__Group_1_1__0 : rule__Disjunction__Group_1_1__0__Impl rule__Disjunction__Group_1_1__1 ;
    public final void rule__Disjunction__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2003:1: ( rule__Disjunction__Group_1_1__0__Impl rule__Disjunction__Group_1_1__1 )
            // InternalRegularExpressionParser.g:2004:2: rule__Disjunction__Group_1_1__0__Impl rule__Disjunction__Group_1_1__1
            {
            pushFollow(FOLLOW_9);
            rule__Disjunction__Group_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1_1__0"


    // $ANTLR start "rule__Disjunction__Group_1_1__0__Impl"
    // InternalRegularExpressionParser.g:2011:1: rule__Disjunction__Group_1_1__0__Impl : ( VerticalLine ) ;
    public final void rule__Disjunction__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2015:1: ( ( VerticalLine ) )
            // InternalRegularExpressionParser.g:2016:1: ( VerticalLine )
            {
            // InternalRegularExpressionParser.g:2016:1: ( VerticalLine )
            // InternalRegularExpressionParser.g:2017:2: VerticalLine
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0()); 
            }
            match(input,VerticalLine,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1_1__0__Impl"


    // $ANTLR start "rule__Disjunction__Group_1_1__1"
    // InternalRegularExpressionParser.g:2026:1: rule__Disjunction__Group_1_1__1 : rule__Disjunction__Group_1_1__1__Impl ;
    public final void rule__Disjunction__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2030:1: ( rule__Disjunction__Group_1_1__1__Impl )
            // InternalRegularExpressionParser.g:2031:2: rule__Disjunction__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Disjunction__Group_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1_1__1"


    // $ANTLR start "rule__Disjunction__Group_1_1__1__Impl"
    // InternalRegularExpressionParser.g:2037:1: rule__Disjunction__Group_1_1__1__Impl : ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? ) ;
    public final void rule__Disjunction__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2041:1: ( ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? ) )
            // InternalRegularExpressionParser.g:2042:1: ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? )
            {
            // InternalRegularExpressionParser.g:2042:1: ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? )
            // InternalRegularExpressionParser.g:2043:2: ( rule__Disjunction__ElementsAssignment_1_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getElementsAssignment_1_1_1()); 
            }
            // InternalRegularExpressionParser.g:2044:2: ( rule__Disjunction__ElementsAssignment_1_1_1 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0>=ExclamationMark && LA24_0<=LeftParenthesis)||(LA24_0>=Comma && LA24_0<=FullStop)||(LA24_0>=Colon && LA24_0<=GreaterThanSign)||(LA24_0>=LeftSquareBracket && LA24_0<=CircumflexAccent)||LA24_0==LeftCurlyBracket||(LA24_0>=RightCurlyBracket && LA24_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA24_0>=RULE_HEX_ESCAPE && LA24_0<=RULE_UNICODE_ESCAPE)||(LA24_0>=RULE_DECIMAL_ESCAPE && LA24_0<=RULE_IDENTITY_ESCAPE)||LA24_0==RULE_UNICODE_DIGIT||(LA24_0>=RULE_UNICODE_LETTER && LA24_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalRegularExpressionParser.g:2044:3: rule__Disjunction__ElementsAssignment_1_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Disjunction__ElementsAssignment_1_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getElementsAssignment_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__Group_1_1__1__Impl"


    // $ANTLR start "rule__Alternative__Group__0"
    // InternalRegularExpressionParser.g:2053:1: rule__Alternative__Group__0 : rule__Alternative__Group__0__Impl rule__Alternative__Group__1 ;
    public final void rule__Alternative__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2057:1: ( rule__Alternative__Group__0__Impl rule__Alternative__Group__1 )
            // InternalRegularExpressionParser.g:2058:2: rule__Alternative__Group__0__Impl rule__Alternative__Group__1
            {
            pushFollow(FOLLOW_9);
            rule__Alternative__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Alternative__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__Group__0"


    // $ANTLR start "rule__Alternative__Group__0__Impl"
    // InternalRegularExpressionParser.g:2065:1: rule__Alternative__Group__0__Impl : ( ruleTerm ) ;
    public final void rule__Alternative__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2069:1: ( ( ruleTerm ) )
            // InternalRegularExpressionParser.g:2070:1: ( ruleTerm )
            {
            // InternalRegularExpressionParser.g:2070:1: ( ruleTerm )
            // InternalRegularExpressionParser.g:2071:2: ruleTerm
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getTermParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleTerm();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeAccess().getTermParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__Group__0__Impl"


    // $ANTLR start "rule__Alternative__Group__1"
    // InternalRegularExpressionParser.g:2080:1: rule__Alternative__Group__1 : rule__Alternative__Group__1__Impl ;
    public final void rule__Alternative__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2084:1: ( rule__Alternative__Group__1__Impl )
            // InternalRegularExpressionParser.g:2085:2: rule__Alternative__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Alternative__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__Group__1"


    // $ANTLR start "rule__Alternative__Group__1__Impl"
    // InternalRegularExpressionParser.g:2091:1: rule__Alternative__Group__1__Impl : ( ( rule__Alternative__Group_1__0 )? ) ;
    public final void rule__Alternative__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2095:1: ( ( ( rule__Alternative__Group_1__0 )? ) )
            // InternalRegularExpressionParser.g:2096:1: ( ( rule__Alternative__Group_1__0 )? )
            {
            // InternalRegularExpressionParser.g:2096:1: ( ( rule__Alternative__Group_1__0 )? )
            // InternalRegularExpressionParser.g:2097:2: ( rule__Alternative__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getGroup_1()); 
            }
            // InternalRegularExpressionParser.g:2098:2: ( rule__Alternative__Group_1__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=ExclamationMark && LA25_0<=LeftParenthesis)||(LA25_0>=Comma && LA25_0<=FullStop)||(LA25_0>=Colon && LA25_0<=GreaterThanSign)||(LA25_0>=LeftSquareBracket && LA25_0<=CircumflexAccent)||LA25_0==LeftCurlyBracket||(LA25_0>=RightCurlyBracket && LA25_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA25_0>=RULE_HEX_ESCAPE && LA25_0<=RULE_UNICODE_ESCAPE)||(LA25_0>=RULE_DECIMAL_ESCAPE && LA25_0<=RULE_IDENTITY_ESCAPE)||LA25_0==RULE_UNICODE_DIGIT||(LA25_0>=RULE_UNICODE_LETTER && LA25_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalRegularExpressionParser.g:2098:3: rule__Alternative__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Alternative__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__Group__1__Impl"


    // $ANTLR start "rule__Alternative__Group_1__0"
    // InternalRegularExpressionParser.g:2107:1: rule__Alternative__Group_1__0 : rule__Alternative__Group_1__0__Impl rule__Alternative__Group_1__1 ;
    public final void rule__Alternative__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2111:1: ( rule__Alternative__Group_1__0__Impl rule__Alternative__Group_1__1 )
            // InternalRegularExpressionParser.g:2112:2: rule__Alternative__Group_1__0__Impl rule__Alternative__Group_1__1
            {
            pushFollow(FOLLOW_9);
            rule__Alternative__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Alternative__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__Group_1__0"


    // $ANTLR start "rule__Alternative__Group_1__0__Impl"
    // InternalRegularExpressionParser.g:2119:1: rule__Alternative__Group_1__0__Impl : ( () ) ;
    public final void rule__Alternative__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2123:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2124:1: ( () )
            {
            // InternalRegularExpressionParser.g:2124:1: ( () )
            // InternalRegularExpressionParser.g:2125:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()); 
            }
            // InternalRegularExpressionParser.g:2126:2: ()
            // InternalRegularExpressionParser.g:2126:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__Group_1__0__Impl"


    // $ANTLR start "rule__Alternative__Group_1__1"
    // InternalRegularExpressionParser.g:2134:1: rule__Alternative__Group_1__1 : rule__Alternative__Group_1__1__Impl ;
    public final void rule__Alternative__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2138:1: ( rule__Alternative__Group_1__1__Impl )
            // InternalRegularExpressionParser.g:2139:2: rule__Alternative__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Alternative__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__Group_1__1"


    // $ANTLR start "rule__Alternative__Group_1__1__Impl"
    // InternalRegularExpressionParser.g:2145:1: rule__Alternative__Group_1__1__Impl : ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) ) ;
    public final void rule__Alternative__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2149:1: ( ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) ) )
            // InternalRegularExpressionParser.g:2150:1: ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) )
            {
            // InternalRegularExpressionParser.g:2150:1: ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) )
            // InternalRegularExpressionParser.g:2151:2: ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* )
            {
            // InternalRegularExpressionParser.g:2151:2: ( ( rule__Alternative__ElementsAssignment_1_1 ) )
            // InternalRegularExpressionParser.g:2152:3: ( rule__Alternative__ElementsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); 
            }
            // InternalRegularExpressionParser.g:2153:3: ( rule__Alternative__ElementsAssignment_1_1 )
            // InternalRegularExpressionParser.g:2153:4: rule__Alternative__ElementsAssignment_1_1
            {
            pushFollow(FOLLOW_10);
            rule__Alternative__ElementsAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); 
            }

            }

            // InternalRegularExpressionParser.g:2156:2: ( ( rule__Alternative__ElementsAssignment_1_1 )* )
            // InternalRegularExpressionParser.g:2157:3: ( rule__Alternative__ElementsAssignment_1_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); 
            }
            // InternalRegularExpressionParser.g:2158:3: ( rule__Alternative__ElementsAssignment_1_1 )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=ExclamationMark && LA26_0<=LeftParenthesis)||(LA26_0>=Comma && LA26_0<=FullStop)||(LA26_0>=Colon && LA26_0<=GreaterThanSign)||(LA26_0>=LeftSquareBracket && LA26_0<=CircumflexAccent)||LA26_0==LeftCurlyBracket||(LA26_0>=RightCurlyBracket && LA26_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA26_0>=RULE_HEX_ESCAPE && LA26_0<=RULE_UNICODE_ESCAPE)||(LA26_0>=RULE_DECIMAL_ESCAPE && LA26_0<=RULE_IDENTITY_ESCAPE)||LA26_0==RULE_UNICODE_DIGIT||(LA26_0>=RULE_UNICODE_LETTER && LA26_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2158:4: rule__Alternative__ElementsAssignment_1_1
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__Alternative__ElementsAssignment_1_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); 
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
    // $ANTLR end "rule__Alternative__Group_1__1__Impl"


    // $ANTLR start "rule__Term__Group_1__0"
    // InternalRegularExpressionParser.g:2168:1: rule__Term__Group_1__0 : rule__Term__Group_1__0__Impl rule__Term__Group_1__1 ;
    public final void rule__Term__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2172:1: ( rule__Term__Group_1__0__Impl rule__Term__Group_1__1 )
            // InternalRegularExpressionParser.g:2173:2: rule__Term__Group_1__0__Impl rule__Term__Group_1__1
            {
            pushFollow(FOLLOW_11);
            rule__Term__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Term__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__0"


    // $ANTLR start "rule__Term__Group_1__0__Impl"
    // InternalRegularExpressionParser.g:2180:1: rule__Term__Group_1__0__Impl : ( ruleAtom ) ;
    public final void rule__Term__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2184:1: ( ( ruleAtom ) )
            // InternalRegularExpressionParser.g:2185:1: ( ruleAtom )
            {
            // InternalRegularExpressionParser.g:2185:1: ( ruleAtom )
            // InternalRegularExpressionParser.g:2186:2: ruleAtom
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTermAccess().getAtomParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleAtom();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTermAccess().getAtomParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__0__Impl"


    // $ANTLR start "rule__Term__Group_1__1"
    // InternalRegularExpressionParser.g:2195:1: rule__Term__Group_1__1 : rule__Term__Group_1__1__Impl ;
    public final void rule__Term__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2199:1: ( rule__Term__Group_1__1__Impl )
            // InternalRegularExpressionParser.g:2200:2: rule__Term__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Term__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__1"


    // $ANTLR start "rule__Term__Group_1__1__Impl"
    // InternalRegularExpressionParser.g:2206:1: rule__Term__Group_1__1__Impl : ( ( rule__Term__QuantifierAssignment_1_1 )? ) ;
    public final void rule__Term__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2210:1: ( ( ( rule__Term__QuantifierAssignment_1_1 )? ) )
            // InternalRegularExpressionParser.g:2211:1: ( ( rule__Term__QuantifierAssignment_1_1 )? )
            {
            // InternalRegularExpressionParser.g:2211:1: ( ( rule__Term__QuantifierAssignment_1_1 )? )
            // InternalRegularExpressionParser.g:2212:2: ( rule__Term__QuantifierAssignment_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTermAccess().getQuantifierAssignment_1_1()); 
            }
            // InternalRegularExpressionParser.g:2213:2: ( rule__Term__QuantifierAssignment_1_1 )?
            int alt27=2;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalRegularExpressionParser.g:2213:3: rule__Term__QuantifierAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Term__QuantifierAssignment_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTermAccess().getQuantifierAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__1__Impl"


    // $ANTLR start "rule__LineStart__Group__0"
    // InternalRegularExpressionParser.g:2222:1: rule__LineStart__Group__0 : rule__LineStart__Group__0__Impl rule__LineStart__Group__1 ;
    public final void rule__LineStart__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2226:1: ( rule__LineStart__Group__0__Impl rule__LineStart__Group__1 )
            // InternalRegularExpressionParser.g:2227:2: rule__LineStart__Group__0__Impl rule__LineStart__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__LineStart__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LineStart__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineStart__Group__0"


    // $ANTLR start "rule__LineStart__Group__0__Impl"
    // InternalRegularExpressionParser.g:2234:1: rule__LineStart__Group__0__Impl : ( () ) ;
    public final void rule__LineStart__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2238:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2239:1: ( () )
            {
            // InternalRegularExpressionParser.g:2239:1: ( () )
            // InternalRegularExpressionParser.g:2240:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineStartAccess().getLineStartAction_0()); 
            }
            // InternalRegularExpressionParser.g:2241:2: ()
            // InternalRegularExpressionParser.g:2241:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineStartAccess().getLineStartAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineStart__Group__0__Impl"


    // $ANTLR start "rule__LineStart__Group__1"
    // InternalRegularExpressionParser.g:2249:1: rule__LineStart__Group__1 : rule__LineStart__Group__1__Impl ;
    public final void rule__LineStart__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2253:1: ( rule__LineStart__Group__1__Impl )
            // InternalRegularExpressionParser.g:2254:2: rule__LineStart__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LineStart__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineStart__Group__1"


    // $ANTLR start "rule__LineStart__Group__1__Impl"
    // InternalRegularExpressionParser.g:2260:1: rule__LineStart__Group__1__Impl : ( CircumflexAccent ) ;
    public final void rule__LineStart__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2264:1: ( ( CircumflexAccent ) )
            // InternalRegularExpressionParser.g:2265:1: ( CircumflexAccent )
            {
            // InternalRegularExpressionParser.g:2265:1: ( CircumflexAccent )
            // InternalRegularExpressionParser.g:2266:2: CircumflexAccent
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineStartAccess().getCircumflexAccentKeyword_1()); 
            }
            match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineStartAccess().getCircumflexAccentKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineStart__Group__1__Impl"


    // $ANTLR start "rule__LineEnd__Group__0"
    // InternalRegularExpressionParser.g:2276:1: rule__LineEnd__Group__0 : rule__LineEnd__Group__0__Impl rule__LineEnd__Group__1 ;
    public final void rule__LineEnd__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2280:1: ( rule__LineEnd__Group__0__Impl rule__LineEnd__Group__1 )
            // InternalRegularExpressionParser.g:2281:2: rule__LineEnd__Group__0__Impl rule__LineEnd__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__LineEnd__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LineEnd__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineEnd__Group__0"


    // $ANTLR start "rule__LineEnd__Group__0__Impl"
    // InternalRegularExpressionParser.g:2288:1: rule__LineEnd__Group__0__Impl : ( () ) ;
    public final void rule__LineEnd__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2292:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2293:1: ( () )
            {
            // InternalRegularExpressionParser.g:2293:1: ( () )
            // InternalRegularExpressionParser.g:2294:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineEndAccess().getLineEndAction_0()); 
            }
            // InternalRegularExpressionParser.g:2295:2: ()
            // InternalRegularExpressionParser.g:2295:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineEndAccess().getLineEndAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineEnd__Group__0__Impl"


    // $ANTLR start "rule__LineEnd__Group__1"
    // InternalRegularExpressionParser.g:2303:1: rule__LineEnd__Group__1 : rule__LineEnd__Group__1__Impl ;
    public final void rule__LineEnd__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2307:1: ( rule__LineEnd__Group__1__Impl )
            // InternalRegularExpressionParser.g:2308:2: rule__LineEnd__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LineEnd__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineEnd__Group__1"


    // $ANTLR start "rule__LineEnd__Group__1__Impl"
    // InternalRegularExpressionParser.g:2314:1: rule__LineEnd__Group__1__Impl : ( DollarSign ) ;
    public final void rule__LineEnd__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2318:1: ( ( DollarSign ) )
            // InternalRegularExpressionParser.g:2319:1: ( DollarSign )
            {
            // InternalRegularExpressionParser.g:2319:1: ( DollarSign )
            // InternalRegularExpressionParser.g:2320:2: DollarSign
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineEndAccess().getDollarSignKeyword_1()); 
            }
            match(input,DollarSign,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLineEndAccess().getDollarSignKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LineEnd__Group__1__Impl"


    // $ANTLR start "rule__WordBoundary__Group__0"
    // InternalRegularExpressionParser.g:2330:1: rule__WordBoundary__Group__0 : rule__WordBoundary__Group__0__Impl rule__WordBoundary__Group__1 ;
    public final void rule__WordBoundary__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2334:1: ( rule__WordBoundary__Group__0__Impl rule__WordBoundary__Group__1 )
            // InternalRegularExpressionParser.g:2335:2: rule__WordBoundary__Group__0__Impl rule__WordBoundary__Group__1
            {
            pushFollow(FOLLOW_14);
            rule__WordBoundary__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__WordBoundary__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WordBoundary__Group__0"


    // $ANTLR start "rule__WordBoundary__Group__0__Impl"
    // InternalRegularExpressionParser.g:2342:1: rule__WordBoundary__Group__0__Impl : ( () ) ;
    public final void rule__WordBoundary__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2346:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2347:1: ( () )
            {
            // InternalRegularExpressionParser.g:2347:1: ( () )
            // InternalRegularExpressionParser.g:2348:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryAccess().getWordBoundaryAction_0()); 
            }
            // InternalRegularExpressionParser.g:2349:2: ()
            // InternalRegularExpressionParser.g:2349:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWordBoundaryAccess().getWordBoundaryAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WordBoundary__Group__0__Impl"


    // $ANTLR start "rule__WordBoundary__Group__1"
    // InternalRegularExpressionParser.g:2357:1: rule__WordBoundary__Group__1 : rule__WordBoundary__Group__1__Impl ;
    public final void rule__WordBoundary__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2361:1: ( rule__WordBoundary__Group__1__Impl )
            // InternalRegularExpressionParser.g:2362:2: rule__WordBoundary__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__WordBoundary__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WordBoundary__Group__1"


    // $ANTLR start "rule__WordBoundary__Group__1__Impl"
    // InternalRegularExpressionParser.g:2368:1: rule__WordBoundary__Group__1__Impl : ( ( rule__WordBoundary__Alternatives_1 ) ) ;
    public final void rule__WordBoundary__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2372:1: ( ( ( rule__WordBoundary__Alternatives_1 ) ) )
            // InternalRegularExpressionParser.g:2373:1: ( ( rule__WordBoundary__Alternatives_1 ) )
            {
            // InternalRegularExpressionParser.g:2373:1: ( ( rule__WordBoundary__Alternatives_1 ) )
            // InternalRegularExpressionParser.g:2374:2: ( rule__WordBoundary__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryAccess().getAlternatives_1()); 
            }
            // InternalRegularExpressionParser.g:2375:2: ( rule__WordBoundary__Alternatives_1 )
            // InternalRegularExpressionParser.g:2375:3: rule__WordBoundary__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__WordBoundary__Alternatives_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWordBoundaryAccess().getAlternatives_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WordBoundary__Group__1__Impl"


    // $ANTLR start "rule__LookAhead__Group__0"
    // InternalRegularExpressionParser.g:2384:1: rule__LookAhead__Group__0 : rule__LookAhead__Group__0__Impl rule__LookAhead__Group__1 ;
    public final void rule__LookAhead__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2388:1: ( rule__LookAhead__Group__0__Impl rule__LookAhead__Group__1 )
            // InternalRegularExpressionParser.g:2389:2: rule__LookAhead__Group__0__Impl rule__LookAhead__Group__1
            {
            pushFollow(FOLLOW_15);
            rule__LookAhead__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__0"


    // $ANTLR start "rule__LookAhead__Group__0__Impl"
    // InternalRegularExpressionParser.g:2396:1: rule__LookAhead__Group__0__Impl : ( () ) ;
    public final void rule__LookAhead__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2400:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2401:1: ( () )
            {
            // InternalRegularExpressionParser.g:2401:1: ( () )
            // InternalRegularExpressionParser.g:2402:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getLookAheadAction_0()); 
            }
            // InternalRegularExpressionParser.g:2403:2: ()
            // InternalRegularExpressionParser.g:2403:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getLookAheadAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__0__Impl"


    // $ANTLR start "rule__LookAhead__Group__1"
    // InternalRegularExpressionParser.g:2411:1: rule__LookAhead__Group__1 : rule__LookAhead__Group__1__Impl rule__LookAhead__Group__2 ;
    public final void rule__LookAhead__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2415:1: ( rule__LookAhead__Group__1__Impl rule__LookAhead__Group__2 )
            // InternalRegularExpressionParser.g:2416:2: rule__LookAhead__Group__1__Impl rule__LookAhead__Group__2
            {
            pushFollow(FOLLOW_16);
            rule__LookAhead__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__1"


    // $ANTLR start "rule__LookAhead__Group__1__Impl"
    // InternalRegularExpressionParser.g:2423:1: rule__LookAhead__Group__1__Impl : ( LeftParenthesis ) ;
    public final void rule__LookAhead__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2427:1: ( ( LeftParenthesis ) )
            // InternalRegularExpressionParser.g:2428:1: ( LeftParenthesis )
            {
            // InternalRegularExpressionParser.g:2428:1: ( LeftParenthesis )
            // InternalRegularExpressionParser.g:2429:2: LeftParenthesis
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getLeftParenthesisKeyword_1()); 
            }
            match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getLeftParenthesisKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__1__Impl"


    // $ANTLR start "rule__LookAhead__Group__2"
    // InternalRegularExpressionParser.g:2438:1: rule__LookAhead__Group__2 : rule__LookAhead__Group__2__Impl rule__LookAhead__Group__3 ;
    public final void rule__LookAhead__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2442:1: ( rule__LookAhead__Group__2__Impl rule__LookAhead__Group__3 )
            // InternalRegularExpressionParser.g:2443:2: rule__LookAhead__Group__2__Impl rule__LookAhead__Group__3
            {
            pushFollow(FOLLOW_17);
            rule__LookAhead__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__2"


    // $ANTLR start "rule__LookAhead__Group__2__Impl"
    // InternalRegularExpressionParser.g:2450:1: rule__LookAhead__Group__2__Impl : ( QuestionMark ) ;
    public final void rule__LookAhead__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2454:1: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:2455:1: ( QuestionMark )
            {
            // InternalRegularExpressionParser.g:2455:1: ( QuestionMark )
            // InternalRegularExpressionParser.g:2456:2: QuestionMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getQuestionMarkKeyword_2()); 
            }
            match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getQuestionMarkKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__2__Impl"


    // $ANTLR start "rule__LookAhead__Group__3"
    // InternalRegularExpressionParser.g:2465:1: rule__LookAhead__Group__3 : rule__LookAhead__Group__3__Impl rule__LookAhead__Group__4 ;
    public final void rule__LookAhead__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2469:1: ( rule__LookAhead__Group__3__Impl rule__LookAhead__Group__4 )
            // InternalRegularExpressionParser.g:2470:2: rule__LookAhead__Group__3__Impl rule__LookAhead__Group__4
            {
            pushFollow(FOLLOW_17);
            rule__LookAhead__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__3"


    // $ANTLR start "rule__LookAhead__Group__3__Impl"
    // InternalRegularExpressionParser.g:2477:1: rule__LookAhead__Group__3__Impl : ( ( rule__LookAhead__BackwardsAssignment_3 )? ) ;
    public final void rule__LookAhead__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2481:1: ( ( ( rule__LookAhead__BackwardsAssignment_3 )? ) )
            // InternalRegularExpressionParser.g:2482:1: ( ( rule__LookAhead__BackwardsAssignment_3 )? )
            {
            // InternalRegularExpressionParser.g:2482:1: ( ( rule__LookAhead__BackwardsAssignment_3 )? )
            // InternalRegularExpressionParser.g:2483:2: ( rule__LookAhead__BackwardsAssignment_3 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getBackwardsAssignment_3()); 
            }
            // InternalRegularExpressionParser.g:2484:2: ( rule__LookAhead__BackwardsAssignment_3 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==LessThanSign) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalRegularExpressionParser.g:2484:3: rule__LookAhead__BackwardsAssignment_3
                    {
                    pushFollow(FOLLOW_2);
                    rule__LookAhead__BackwardsAssignment_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getBackwardsAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__3__Impl"


    // $ANTLR start "rule__LookAhead__Group__4"
    // InternalRegularExpressionParser.g:2492:1: rule__LookAhead__Group__4 : rule__LookAhead__Group__4__Impl rule__LookAhead__Group__5 ;
    public final void rule__LookAhead__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2496:1: ( rule__LookAhead__Group__4__Impl rule__LookAhead__Group__5 )
            // InternalRegularExpressionParser.g:2497:2: rule__LookAhead__Group__4__Impl rule__LookAhead__Group__5
            {
            pushFollow(FOLLOW_4);
            rule__LookAhead__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__4"


    // $ANTLR start "rule__LookAhead__Group__4__Impl"
    // InternalRegularExpressionParser.g:2504:1: rule__LookAhead__Group__4__Impl : ( ( rule__LookAhead__Alternatives_4 ) ) ;
    public final void rule__LookAhead__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2508:1: ( ( ( rule__LookAhead__Alternatives_4 ) ) )
            // InternalRegularExpressionParser.g:2509:1: ( ( rule__LookAhead__Alternatives_4 ) )
            {
            // InternalRegularExpressionParser.g:2509:1: ( ( rule__LookAhead__Alternatives_4 ) )
            // InternalRegularExpressionParser.g:2510:2: ( rule__LookAhead__Alternatives_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getAlternatives_4()); 
            }
            // InternalRegularExpressionParser.g:2511:2: ( rule__LookAhead__Alternatives_4 )
            // InternalRegularExpressionParser.g:2511:3: rule__LookAhead__Alternatives_4
            {
            pushFollow(FOLLOW_2);
            rule__LookAhead__Alternatives_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getAlternatives_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__4__Impl"


    // $ANTLR start "rule__LookAhead__Group__5"
    // InternalRegularExpressionParser.g:2519:1: rule__LookAhead__Group__5 : rule__LookAhead__Group__5__Impl rule__LookAhead__Group__6 ;
    public final void rule__LookAhead__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2523:1: ( rule__LookAhead__Group__5__Impl rule__LookAhead__Group__6 )
            // InternalRegularExpressionParser.g:2524:2: rule__LookAhead__Group__5__Impl rule__LookAhead__Group__6
            {
            pushFollow(FOLLOW_18);
            rule__LookAhead__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__5"


    // $ANTLR start "rule__LookAhead__Group__5__Impl"
    // InternalRegularExpressionParser.g:2531:1: rule__LookAhead__Group__5__Impl : ( ( rule__LookAhead__PatternAssignment_5 ) ) ;
    public final void rule__LookAhead__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2535:1: ( ( ( rule__LookAhead__PatternAssignment_5 ) ) )
            // InternalRegularExpressionParser.g:2536:1: ( ( rule__LookAhead__PatternAssignment_5 ) )
            {
            // InternalRegularExpressionParser.g:2536:1: ( ( rule__LookAhead__PatternAssignment_5 ) )
            // InternalRegularExpressionParser.g:2537:2: ( rule__LookAhead__PatternAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getPatternAssignment_5()); 
            }
            // InternalRegularExpressionParser.g:2538:2: ( rule__LookAhead__PatternAssignment_5 )
            // InternalRegularExpressionParser.g:2538:3: rule__LookAhead__PatternAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__LookAhead__PatternAssignment_5();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getPatternAssignment_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__5__Impl"


    // $ANTLR start "rule__LookAhead__Group__6"
    // InternalRegularExpressionParser.g:2546:1: rule__LookAhead__Group__6 : rule__LookAhead__Group__6__Impl ;
    public final void rule__LookAhead__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2550:1: ( rule__LookAhead__Group__6__Impl )
            // InternalRegularExpressionParser.g:2551:2: rule__LookAhead__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__6"


    // $ANTLR start "rule__LookAhead__Group__6__Impl"
    // InternalRegularExpressionParser.g:2557:1: rule__LookAhead__Group__6__Impl : ( RightParenthesis ) ;
    public final void rule__LookAhead__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2561:1: ( ( RightParenthesis ) )
            // InternalRegularExpressionParser.g:2562:1: ( RightParenthesis )
            {
            // InternalRegularExpressionParser.g:2562:1: ( RightParenthesis )
            // InternalRegularExpressionParser.g:2563:2: RightParenthesis
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getRightParenthesisKeyword_6()); 
            }
            match(input,RightParenthesis,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getRightParenthesisKeyword_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__Group__6__Impl"


    // $ANTLR start "rule__Wildcard__Group__0"
    // InternalRegularExpressionParser.g:2573:1: rule__Wildcard__Group__0 : rule__Wildcard__Group__0__Impl rule__Wildcard__Group__1 ;
    public final void rule__Wildcard__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2577:1: ( rule__Wildcard__Group__0__Impl rule__Wildcard__Group__1 )
            // InternalRegularExpressionParser.g:2578:2: rule__Wildcard__Group__0__Impl rule__Wildcard__Group__1
            {
            pushFollow(FOLLOW_19);
            rule__Wildcard__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Wildcard__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Wildcard__Group__0"


    // $ANTLR start "rule__Wildcard__Group__0__Impl"
    // InternalRegularExpressionParser.g:2585:1: rule__Wildcard__Group__0__Impl : ( () ) ;
    public final void rule__Wildcard__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2589:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2590:1: ( () )
            {
            // InternalRegularExpressionParser.g:2590:1: ( () )
            // InternalRegularExpressionParser.g:2591:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWildcardAccess().getWildcardAction_0()); 
            }
            // InternalRegularExpressionParser.g:2592:2: ()
            // InternalRegularExpressionParser.g:2592:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getWildcardAccess().getWildcardAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Wildcard__Group__0__Impl"


    // $ANTLR start "rule__Wildcard__Group__1"
    // InternalRegularExpressionParser.g:2600:1: rule__Wildcard__Group__1 : rule__Wildcard__Group__1__Impl ;
    public final void rule__Wildcard__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2604:1: ( rule__Wildcard__Group__1__Impl )
            // InternalRegularExpressionParser.g:2605:2: rule__Wildcard__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Wildcard__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Wildcard__Group__1"


    // $ANTLR start "rule__Wildcard__Group__1__Impl"
    // InternalRegularExpressionParser.g:2611:1: rule__Wildcard__Group__1__Impl : ( FullStop ) ;
    public final void rule__Wildcard__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2615:1: ( ( FullStop ) )
            // InternalRegularExpressionParser.g:2616:1: ( FullStop )
            {
            // InternalRegularExpressionParser.g:2616:1: ( FullStop )
            // InternalRegularExpressionParser.g:2617:2: FullStop
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWildcardAccess().getFullStopKeyword_1()); 
            }
            match(input,FullStop,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWildcardAccess().getFullStopKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Wildcard__Group__1__Impl"


    // $ANTLR start "rule__CharacterClass__Group__0"
    // InternalRegularExpressionParser.g:2627:1: rule__CharacterClass__Group__0 : rule__CharacterClass__Group__0__Impl rule__CharacterClass__Group__1 ;
    public final void rule__CharacterClass__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2631:1: ( rule__CharacterClass__Group__0__Impl rule__CharacterClass__Group__1 )
            // InternalRegularExpressionParser.g:2632:2: rule__CharacterClass__Group__0__Impl rule__CharacterClass__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__CharacterClass__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__CharacterClass__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__0"


    // $ANTLR start "rule__CharacterClass__Group__0__Impl"
    // InternalRegularExpressionParser.g:2639:1: rule__CharacterClass__Group__0__Impl : ( () ) ;
    public final void rule__CharacterClass__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2643:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2644:1: ( () )
            {
            // InternalRegularExpressionParser.g:2644:1: ( () )
            // InternalRegularExpressionParser.g:2645:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getCharacterClassAction_0()); 
            }
            // InternalRegularExpressionParser.g:2646:2: ()
            // InternalRegularExpressionParser.g:2646:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getCharacterClassAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__0__Impl"


    // $ANTLR start "rule__CharacterClass__Group__1"
    // InternalRegularExpressionParser.g:2654:1: rule__CharacterClass__Group__1 : rule__CharacterClass__Group__1__Impl rule__CharacterClass__Group__2 ;
    public final void rule__CharacterClass__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2658:1: ( rule__CharacterClass__Group__1__Impl rule__CharacterClass__Group__2 )
            // InternalRegularExpressionParser.g:2659:2: rule__CharacterClass__Group__1__Impl rule__CharacterClass__Group__2
            {
            pushFollow(FOLLOW_21);
            rule__CharacterClass__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__CharacterClass__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__1"


    // $ANTLR start "rule__CharacterClass__Group__1__Impl"
    // InternalRegularExpressionParser.g:2666:1: rule__CharacterClass__Group__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__CharacterClass__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2670:1: ( ( LeftSquareBracket ) )
            // InternalRegularExpressionParser.g:2671:1: ( LeftSquareBracket )
            {
            // InternalRegularExpressionParser.g:2671:1: ( LeftSquareBracket )
            // InternalRegularExpressionParser.g:2672:2: LeftSquareBracket
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getLeftSquareBracketKeyword_1()); 
            }
            match(input,LeftSquareBracket,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getLeftSquareBracketKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__1__Impl"


    // $ANTLR start "rule__CharacterClass__Group__2"
    // InternalRegularExpressionParser.g:2681:1: rule__CharacterClass__Group__2 : rule__CharacterClass__Group__2__Impl rule__CharacterClass__Group__3 ;
    public final void rule__CharacterClass__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2685:1: ( rule__CharacterClass__Group__2__Impl rule__CharacterClass__Group__3 )
            // InternalRegularExpressionParser.g:2686:2: rule__CharacterClass__Group__2__Impl rule__CharacterClass__Group__3
            {
            pushFollow(FOLLOW_21);
            rule__CharacterClass__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__CharacterClass__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__2"


    // $ANTLR start "rule__CharacterClass__Group__2__Impl"
    // InternalRegularExpressionParser.g:2693:1: rule__CharacterClass__Group__2__Impl : ( ( rule__CharacterClass__Group_2__0 )? ) ;
    public final void rule__CharacterClass__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2697:1: ( ( ( rule__CharacterClass__Group_2__0 )? ) )
            // InternalRegularExpressionParser.g:2698:1: ( ( rule__CharacterClass__Group_2__0 )? )
            {
            // InternalRegularExpressionParser.g:2698:1: ( ( rule__CharacterClass__Group_2__0 )? )
            // InternalRegularExpressionParser.g:2699:2: ( rule__CharacterClass__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getGroup_2()); 
            }
            // InternalRegularExpressionParser.g:2700:2: ( rule__CharacterClass__Group_2__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==CircumflexAccent) ) {
                int LA29_1 = input.LA(2);

                if ( (synpred83_InternalRegularExpressionParser()) ) {
                    alt29=1;
                }
            }
            switch (alt29) {
                case 1 :
                    // InternalRegularExpressionParser.g:2700:3: rule__CharacterClass__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__CharacterClass__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__2__Impl"


    // $ANTLR start "rule__CharacterClass__Group__3"
    // InternalRegularExpressionParser.g:2708:1: rule__CharacterClass__Group__3 : rule__CharacterClass__Group__3__Impl rule__CharacterClass__Group__4 ;
    public final void rule__CharacterClass__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2712:1: ( rule__CharacterClass__Group__3__Impl rule__CharacterClass__Group__4 )
            // InternalRegularExpressionParser.g:2713:2: rule__CharacterClass__Group__3__Impl rule__CharacterClass__Group__4
            {
            pushFollow(FOLLOW_21);
            rule__CharacterClass__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__CharacterClass__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__3"


    // $ANTLR start "rule__CharacterClass__Group__3__Impl"
    // InternalRegularExpressionParser.g:2720:1: rule__CharacterClass__Group__3__Impl : ( ( rule__CharacterClass__ElementsAssignment_3 )* ) ;
    public final void rule__CharacterClass__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2724:1: ( ( ( rule__CharacterClass__ElementsAssignment_3 )* ) )
            // InternalRegularExpressionParser.g:2725:1: ( ( rule__CharacterClass__ElementsAssignment_3 )* )
            {
            // InternalRegularExpressionParser.g:2725:1: ( ( rule__CharacterClass__ElementsAssignment_3 )* )
            // InternalRegularExpressionParser.g:2726:2: ( rule__CharacterClass__ElementsAssignment_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getElementsAssignment_3()); 
            }
            // InternalRegularExpressionParser.g:2727:2: ( rule__CharacterClass__ElementsAssignment_3 )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>=ExclamationMark && LA30_0<=LeftSquareBracket)||LA30_0==CircumflexAccent||(LA30_0>=LeftCurlyBracket && LA30_0<=RULE_WORD_BOUNDARY)||(LA30_0>=RULE_CHARACTER_CLASS_ESCAPE && LA30_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA30_0>=RULE_HEX_ESCAPE && LA30_0<=RULE_UNICODE_ESCAPE)||(LA30_0>=RULE_DECIMAL_ESCAPE && LA30_0<=RULE_IDENTITY_ESCAPE)||LA30_0==RULE_UNICODE_DIGIT||(LA30_0>=RULE_UNICODE_LETTER && LA30_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2727:3: rule__CharacterClass__ElementsAssignment_3
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__CharacterClass__ElementsAssignment_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getElementsAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__3__Impl"


    // $ANTLR start "rule__CharacterClass__Group__4"
    // InternalRegularExpressionParser.g:2735:1: rule__CharacterClass__Group__4 : rule__CharacterClass__Group__4__Impl ;
    public final void rule__CharacterClass__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2739:1: ( rule__CharacterClass__Group__4__Impl )
            // InternalRegularExpressionParser.g:2740:2: rule__CharacterClass__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClass__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__4"


    // $ANTLR start "rule__CharacterClass__Group__4__Impl"
    // InternalRegularExpressionParser.g:2746:1: rule__CharacterClass__Group__4__Impl : ( RightSquareBracket ) ;
    public final void rule__CharacterClass__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2750:1: ( ( RightSquareBracket ) )
            // InternalRegularExpressionParser.g:2751:1: ( RightSquareBracket )
            {
            // InternalRegularExpressionParser.g:2751:1: ( RightSquareBracket )
            // InternalRegularExpressionParser.g:2752:2: RightSquareBracket
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getRightSquareBracketKeyword_4()); 
            }
            match(input,RightSquareBracket,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getRightSquareBracketKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group__4__Impl"


    // $ANTLR start "rule__CharacterClass__Group_2__0"
    // InternalRegularExpressionParser.g:2762:1: rule__CharacterClass__Group_2__0 : rule__CharacterClass__Group_2__0__Impl ;
    public final void rule__CharacterClass__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2766:1: ( rule__CharacterClass__Group_2__0__Impl )
            // InternalRegularExpressionParser.g:2767:2: rule__CharacterClass__Group_2__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClass__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group_2__0"


    // $ANTLR start "rule__CharacterClass__Group_2__0__Impl"
    // InternalRegularExpressionParser.g:2773:1: rule__CharacterClass__Group_2__0__Impl : ( ( rule__CharacterClass__NegatedAssignment_2_0 ) ) ;
    public final void rule__CharacterClass__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2777:1: ( ( ( rule__CharacterClass__NegatedAssignment_2_0 ) ) )
            // InternalRegularExpressionParser.g:2778:1: ( ( rule__CharacterClass__NegatedAssignment_2_0 ) )
            {
            // InternalRegularExpressionParser.g:2778:1: ( ( rule__CharacterClass__NegatedAssignment_2_0 ) )
            // InternalRegularExpressionParser.g:2779:2: ( rule__CharacterClass__NegatedAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getNegatedAssignment_2_0()); 
            }
            // InternalRegularExpressionParser.g:2780:2: ( rule__CharacterClass__NegatedAssignment_2_0 )
            // InternalRegularExpressionParser.g:2780:3: rule__CharacterClass__NegatedAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClass__NegatedAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getNegatedAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__Group_2__0__Impl"


    // $ANTLR start "rule__CharacterClassElement__Group__0"
    // InternalRegularExpressionParser.g:2789:1: rule__CharacterClassElement__Group__0 : rule__CharacterClassElement__Group__0__Impl rule__CharacterClassElement__Group__1 ;
    public final void rule__CharacterClassElement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2793:1: ( rule__CharacterClassElement__Group__0__Impl rule__CharacterClassElement__Group__1 )
            // InternalRegularExpressionParser.g:2794:2: rule__CharacterClassElement__Group__0__Impl rule__CharacterClassElement__Group__1
            {
            pushFollow(FOLLOW_23);
            rule__CharacterClassElement__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group__0"


    // $ANTLR start "rule__CharacterClassElement__Group__0__Impl"
    // InternalRegularExpressionParser.g:2801:1: rule__CharacterClassElement__Group__0__Impl : ( ruleCharacterClassAtom ) ;
    public final void rule__CharacterClassElement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2805:1: ( ( ruleCharacterClassAtom ) )
            // InternalRegularExpressionParser.g:2806:1: ( ruleCharacterClassAtom )
            {
            // InternalRegularExpressionParser.g:2806:1: ( ruleCharacterClassAtom )
            // InternalRegularExpressionParser.g:2807:2: ruleCharacterClassAtom
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getCharacterClassAtomParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCharacterClassAtom();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getCharacterClassAtomParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group__0__Impl"


    // $ANTLR start "rule__CharacterClassElement__Group__1"
    // InternalRegularExpressionParser.g:2816:1: rule__CharacterClassElement__Group__1 : rule__CharacterClassElement__Group__1__Impl ;
    public final void rule__CharacterClassElement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2820:1: ( rule__CharacterClassElement__Group__1__Impl )
            // InternalRegularExpressionParser.g:2821:2: rule__CharacterClassElement__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group__1"


    // $ANTLR start "rule__CharacterClassElement__Group__1__Impl"
    // InternalRegularExpressionParser.g:2827:1: rule__CharacterClassElement__Group__1__Impl : ( ( rule__CharacterClassElement__Group_1__0 )? ) ;
    public final void rule__CharacterClassElement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2831:1: ( ( ( rule__CharacterClassElement__Group_1__0 )? ) )
            // InternalRegularExpressionParser.g:2832:1: ( ( rule__CharacterClassElement__Group_1__0 )? )
            {
            // InternalRegularExpressionParser.g:2832:1: ( ( rule__CharacterClassElement__Group_1__0 )? )
            // InternalRegularExpressionParser.g:2833:2: ( rule__CharacterClassElement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getGroup_1()); 
            }
            // InternalRegularExpressionParser.g:2834:2: ( rule__CharacterClassElement__Group_1__0 )?
            int alt31=2;
            alt31 = dfa31.predict(input);
            switch (alt31) {
                case 1 :
                    // InternalRegularExpressionParser.g:2834:3: rule__CharacterClassElement__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__CharacterClassElement__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group__1__Impl"


    // $ANTLR start "rule__CharacterClassElement__Group_1__0"
    // InternalRegularExpressionParser.g:2843:1: rule__CharacterClassElement__Group_1__0 : rule__CharacterClassElement__Group_1__0__Impl ;
    public final void rule__CharacterClassElement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2847:1: ( rule__CharacterClassElement__Group_1__0__Impl )
            // InternalRegularExpressionParser.g:2848:2: rule__CharacterClassElement__Group_1__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1__0"


    // $ANTLR start "rule__CharacterClassElement__Group_1__0__Impl"
    // InternalRegularExpressionParser.g:2854:1: rule__CharacterClassElement__Group_1__0__Impl : ( ( rule__CharacterClassElement__Group_1_0__0 ) ) ;
    public final void rule__CharacterClassElement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2858:1: ( ( ( rule__CharacterClassElement__Group_1_0__0 ) ) )
            // InternalRegularExpressionParser.g:2859:1: ( ( rule__CharacterClassElement__Group_1_0__0 ) )
            {
            // InternalRegularExpressionParser.g:2859:1: ( ( rule__CharacterClassElement__Group_1_0__0 ) )
            // InternalRegularExpressionParser.g:2860:2: ( rule__CharacterClassElement__Group_1_0__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getGroup_1_0()); 
            }
            // InternalRegularExpressionParser.g:2861:2: ( rule__CharacterClassElement__Group_1_0__0 )
            // InternalRegularExpressionParser.g:2861:3: rule__CharacterClassElement__Group_1_0__0
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group_1_0__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getGroup_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1__0__Impl"


    // $ANTLR start "rule__CharacterClassElement__Group_1_0__0"
    // InternalRegularExpressionParser.g:2870:1: rule__CharacterClassElement__Group_1_0__0 : rule__CharacterClassElement__Group_1_0__0__Impl rule__CharacterClassElement__Group_1_0__1 ;
    public final void rule__CharacterClassElement__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2874:1: ( rule__CharacterClassElement__Group_1_0__0__Impl rule__CharacterClassElement__Group_1_0__1 )
            // InternalRegularExpressionParser.g:2875:2: rule__CharacterClassElement__Group_1_0__0__Impl rule__CharacterClassElement__Group_1_0__1
            {
            pushFollow(FOLLOW_23);
            rule__CharacterClassElement__Group_1_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group_1_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1_0__0"


    // $ANTLR start "rule__CharacterClassElement__Group_1_0__0__Impl"
    // InternalRegularExpressionParser.g:2882:1: rule__CharacterClassElement__Group_1_0__0__Impl : ( () ) ;
    public final void rule__CharacterClassElement__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2886:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2887:1: ( () )
            {
            // InternalRegularExpressionParser.g:2887:1: ( () )
            // InternalRegularExpressionParser.g:2888:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()); 
            }
            // InternalRegularExpressionParser.g:2889:2: ()
            // InternalRegularExpressionParser.g:2889:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1_0__0__Impl"


    // $ANTLR start "rule__CharacterClassElement__Group_1_0__1"
    // InternalRegularExpressionParser.g:2897:1: rule__CharacterClassElement__Group_1_0__1 : rule__CharacterClassElement__Group_1_0__1__Impl rule__CharacterClassElement__Group_1_0__2 ;
    public final void rule__CharacterClassElement__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2901:1: ( rule__CharacterClassElement__Group_1_0__1__Impl rule__CharacterClassElement__Group_1_0__2 )
            // InternalRegularExpressionParser.g:2902:2: rule__CharacterClassElement__Group_1_0__1__Impl rule__CharacterClassElement__Group_1_0__2
            {
            pushFollow(FOLLOW_24);
            rule__CharacterClassElement__Group_1_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group_1_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1_0__1"


    // $ANTLR start "rule__CharacterClassElement__Group_1_0__1__Impl"
    // InternalRegularExpressionParser.g:2909:1: rule__CharacterClassElement__Group_1_0__1__Impl : ( HyphenMinus ) ;
    public final void rule__CharacterClassElement__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2913:1: ( ( HyphenMinus ) )
            // InternalRegularExpressionParser.g:2914:1: ( HyphenMinus )
            {
            // InternalRegularExpressionParser.g:2914:1: ( HyphenMinus )
            // InternalRegularExpressionParser.g:2915:2: HyphenMinus
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getHyphenMinusKeyword_1_0_1()); 
            }
            match(input,HyphenMinus,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getHyphenMinusKeyword_1_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1_0__1__Impl"


    // $ANTLR start "rule__CharacterClassElement__Group_1_0__2"
    // InternalRegularExpressionParser.g:2924:1: rule__CharacterClassElement__Group_1_0__2 : rule__CharacterClassElement__Group_1_0__2__Impl ;
    public final void rule__CharacterClassElement__Group_1_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2928:1: ( rule__CharacterClassElement__Group_1_0__2__Impl )
            // InternalRegularExpressionParser.g:2929:2: rule__CharacterClassElement__Group_1_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__Group_1_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1_0__2"


    // $ANTLR start "rule__CharacterClassElement__Group_1_0__2__Impl"
    // InternalRegularExpressionParser.g:2935:1: rule__CharacterClassElement__Group_1_0__2__Impl : ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) ) ;
    public final void rule__CharacterClassElement__Group_1_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2939:1: ( ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) ) )
            // InternalRegularExpressionParser.g:2940:1: ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) )
            {
            // InternalRegularExpressionParser.g:2940:1: ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) )
            // InternalRegularExpressionParser.g:2941:2: ( rule__CharacterClassElement__RightAssignment_1_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getRightAssignment_1_0_2()); 
            }
            // InternalRegularExpressionParser.g:2942:2: ( rule__CharacterClassElement__RightAssignment_1_0_2 )
            // InternalRegularExpressionParser.g:2942:3: rule__CharacterClassElement__RightAssignment_1_0_2
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassElement__RightAssignment_1_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getRightAssignment_1_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__Group_1_0__2__Impl"


    // $ANTLR start "rule__Backspace__Group__0"
    // InternalRegularExpressionParser.g:2951:1: rule__Backspace__Group__0 : rule__Backspace__Group__0__Impl rule__Backspace__Group__1 ;
    public final void rule__Backspace__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2955:1: ( rule__Backspace__Group__0__Impl rule__Backspace__Group__1 )
            // InternalRegularExpressionParser.g:2956:2: rule__Backspace__Group__0__Impl rule__Backspace__Group__1
            {
            pushFollow(FOLLOW_25);
            rule__Backspace__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Backspace__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Backspace__Group__0"


    // $ANTLR start "rule__Backspace__Group__0__Impl"
    // InternalRegularExpressionParser.g:2963:1: rule__Backspace__Group__0__Impl : ( () ) ;
    public final void rule__Backspace__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2967:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2968:1: ( () )
            {
            // InternalRegularExpressionParser.g:2968:1: ( () )
            // InternalRegularExpressionParser.g:2969:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBackspaceAccess().getBackspaceAction_0()); 
            }
            // InternalRegularExpressionParser.g:2970:2: ()
            // InternalRegularExpressionParser.g:2970:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBackspaceAccess().getBackspaceAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Backspace__Group__0__Impl"


    // $ANTLR start "rule__Backspace__Group__1"
    // InternalRegularExpressionParser.g:2978:1: rule__Backspace__Group__1 : rule__Backspace__Group__1__Impl ;
    public final void rule__Backspace__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2982:1: ( rule__Backspace__Group__1__Impl )
            // InternalRegularExpressionParser.g:2983:2: rule__Backspace__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Backspace__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Backspace__Group__1"


    // $ANTLR start "rule__Backspace__Group__1__Impl"
    // InternalRegularExpressionParser.g:2989:1: rule__Backspace__Group__1__Impl : ( RULE_WORD_BOUNDARY ) ;
    public final void rule__Backspace__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2993:1: ( ( RULE_WORD_BOUNDARY ) )
            // InternalRegularExpressionParser.g:2994:1: ( RULE_WORD_BOUNDARY )
            {
            // InternalRegularExpressionParser.g:2994:1: ( RULE_WORD_BOUNDARY )
            // InternalRegularExpressionParser.g:2995:2: RULE_WORD_BOUNDARY
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBackspaceAccess().getWORD_BOUNDARYTerminalRuleCall_1()); 
            }
            match(input,RULE_WORD_BOUNDARY,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBackspaceAccess().getWORD_BOUNDARYTerminalRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Backspace__Group__1__Impl"


    // $ANTLR start "rule__Group__Group__0"
    // InternalRegularExpressionParser.g:3005:1: rule__Group__Group__0 : rule__Group__Group__0__Impl rule__Group__Group__1 ;
    public final void rule__Group__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3009:1: ( rule__Group__Group__0__Impl rule__Group__Group__1 )
            // InternalRegularExpressionParser.g:3010:2: rule__Group__Group__0__Impl rule__Group__Group__1
            {
            pushFollow(FOLLOW_9);
            rule__Group__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__0"


    // $ANTLR start "rule__Group__Group__0__Impl"
    // InternalRegularExpressionParser.g:3017:1: rule__Group__Group__0__Impl : ( () ) ;
    public final void rule__Group__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3021:1: ( ( () ) )
            // InternalRegularExpressionParser.g:3022:1: ( () )
            {
            // InternalRegularExpressionParser.g:3022:1: ( () )
            // InternalRegularExpressionParser.g:3023:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getGroupAction_0()); 
            }
            // InternalRegularExpressionParser.g:3024:2: ()
            // InternalRegularExpressionParser.g:3024:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getGroupAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__0__Impl"


    // $ANTLR start "rule__Group__Group__1"
    // InternalRegularExpressionParser.g:3032:1: rule__Group__Group__1 : rule__Group__Group__1__Impl rule__Group__Group__2 ;
    public final void rule__Group__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3036:1: ( rule__Group__Group__1__Impl rule__Group__Group__2 )
            // InternalRegularExpressionParser.g:3037:2: rule__Group__Group__1__Impl rule__Group__Group__2
            {
            pushFollow(FOLLOW_26);
            rule__Group__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__1"


    // $ANTLR start "rule__Group__Group__1__Impl"
    // InternalRegularExpressionParser.g:3044:1: rule__Group__Group__1__Impl : ( LeftParenthesis ) ;
    public final void rule__Group__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3048:1: ( ( LeftParenthesis ) )
            // InternalRegularExpressionParser.g:3049:1: ( LeftParenthesis )
            {
            // InternalRegularExpressionParser.g:3049:1: ( LeftParenthesis )
            // InternalRegularExpressionParser.g:3050:2: LeftParenthesis
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1()); 
            }
            match(input,LeftParenthesis,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__1__Impl"


    // $ANTLR start "rule__Group__Group__2"
    // InternalRegularExpressionParser.g:3059:1: rule__Group__Group__2 : rule__Group__Group__2__Impl rule__Group__Group__3 ;
    public final void rule__Group__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3063:1: ( rule__Group__Group__2__Impl rule__Group__Group__3 )
            // InternalRegularExpressionParser.g:3064:2: rule__Group__Group__2__Impl rule__Group__Group__3
            {
            pushFollow(FOLLOW_26);
            rule__Group__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__2"


    // $ANTLR start "rule__Group__Group__2__Impl"
    // InternalRegularExpressionParser.g:3071:1: rule__Group__Group__2__Impl : ( ( rule__Group__Alternatives_2 )? ) ;
    public final void rule__Group__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3075:1: ( ( ( rule__Group__Alternatives_2 )? ) )
            // InternalRegularExpressionParser.g:3076:1: ( ( rule__Group__Alternatives_2 )? )
            {
            // InternalRegularExpressionParser.g:3076:1: ( ( rule__Group__Alternatives_2 )? )
            // InternalRegularExpressionParser.g:3077:2: ( rule__Group__Alternatives_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getAlternatives_2()); 
            }
            // InternalRegularExpressionParser.g:3078:2: ( rule__Group__Alternatives_2 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==QuestionMark) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalRegularExpressionParser.g:3078:3: rule__Group__Alternatives_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Group__Alternatives_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getAlternatives_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__2__Impl"


    // $ANTLR start "rule__Group__Group__3"
    // InternalRegularExpressionParser.g:3086:1: rule__Group__Group__3 : rule__Group__Group__3__Impl rule__Group__Group__4 ;
    public final void rule__Group__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3090:1: ( rule__Group__Group__3__Impl rule__Group__Group__4 )
            // InternalRegularExpressionParser.g:3091:2: rule__Group__Group__3__Impl rule__Group__Group__4
            {
            pushFollow(FOLLOW_18);
            rule__Group__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__3"


    // $ANTLR start "rule__Group__Group__3__Impl"
    // InternalRegularExpressionParser.g:3098:1: rule__Group__Group__3__Impl : ( ( rule__Group__PatternAssignment_3 ) ) ;
    public final void rule__Group__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3102:1: ( ( ( rule__Group__PatternAssignment_3 ) ) )
            // InternalRegularExpressionParser.g:3103:1: ( ( rule__Group__PatternAssignment_3 ) )
            {
            // InternalRegularExpressionParser.g:3103:1: ( ( rule__Group__PatternAssignment_3 ) )
            // InternalRegularExpressionParser.g:3104:2: ( rule__Group__PatternAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getPatternAssignment_3()); 
            }
            // InternalRegularExpressionParser.g:3105:2: ( rule__Group__PatternAssignment_3 )
            // InternalRegularExpressionParser.g:3105:3: rule__Group__PatternAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Group__PatternAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getPatternAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__3__Impl"


    // $ANTLR start "rule__Group__Group__4"
    // InternalRegularExpressionParser.g:3113:1: rule__Group__Group__4 : rule__Group__Group__4__Impl ;
    public final void rule__Group__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3117:1: ( rule__Group__Group__4__Impl )
            // InternalRegularExpressionParser.g:3118:2: rule__Group__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Group__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__4"


    // $ANTLR start "rule__Group__Group__4__Impl"
    // InternalRegularExpressionParser.g:3124:1: rule__Group__Group__4__Impl : ( RightParenthesis ) ;
    public final void rule__Group__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3128:1: ( ( RightParenthesis ) )
            // InternalRegularExpressionParser.g:3129:1: ( RightParenthesis )
            {
            // InternalRegularExpressionParser.g:3129:1: ( RightParenthesis )
            // InternalRegularExpressionParser.g:3130:2: RightParenthesis
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getRightParenthesisKeyword_4()); 
            }
            match(input,RightParenthesis,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getRightParenthesisKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group__4__Impl"


    // $ANTLR start "rule__Group__Group_2_0__0"
    // InternalRegularExpressionParser.g:3140:1: rule__Group__Group_2_0__0 : rule__Group__Group_2_0__0__Impl rule__Group__Group_2_0__1 ;
    public final void rule__Group__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3144:1: ( rule__Group__Group_2_0__0__Impl rule__Group__Group_2_0__1 )
            // InternalRegularExpressionParser.g:3145:2: rule__Group__Group_2_0__0__Impl rule__Group__Group_2_0__1
            {
            pushFollow(FOLLOW_27);
            rule__Group__Group_2_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group_2_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__0"


    // $ANTLR start "rule__Group__Group_2_0__0__Impl"
    // InternalRegularExpressionParser.g:3152:1: rule__Group__Group_2_0__0__Impl : ( ( rule__Group__NamedAssignment_2_0_0 ) ) ;
    public final void rule__Group__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3156:1: ( ( ( rule__Group__NamedAssignment_2_0_0 ) ) )
            // InternalRegularExpressionParser.g:3157:1: ( ( rule__Group__NamedAssignment_2_0_0 ) )
            {
            // InternalRegularExpressionParser.g:3157:1: ( ( rule__Group__NamedAssignment_2_0_0 ) )
            // InternalRegularExpressionParser.g:3158:2: ( rule__Group__NamedAssignment_2_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNamedAssignment_2_0_0()); 
            }
            // InternalRegularExpressionParser.g:3159:2: ( rule__Group__NamedAssignment_2_0_0 )
            // InternalRegularExpressionParser.g:3159:3: rule__Group__NamedAssignment_2_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Group__NamedAssignment_2_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNamedAssignment_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__0__Impl"


    // $ANTLR start "rule__Group__Group_2_0__1"
    // InternalRegularExpressionParser.g:3167:1: rule__Group__Group_2_0__1 : rule__Group__Group_2_0__1__Impl rule__Group__Group_2_0__2 ;
    public final void rule__Group__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3171:1: ( rule__Group__Group_2_0__1__Impl rule__Group__Group_2_0__2 )
            // InternalRegularExpressionParser.g:3172:2: rule__Group__Group_2_0__1__Impl rule__Group__Group_2_0__2
            {
            pushFollow(FOLLOW_28);
            rule__Group__Group_2_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group_2_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__1"


    // $ANTLR start "rule__Group__Group_2_0__1__Impl"
    // InternalRegularExpressionParser.g:3179:1: rule__Group__Group_2_0__1__Impl : ( LessThanSign ) ;
    public final void rule__Group__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3183:1: ( ( LessThanSign ) )
            // InternalRegularExpressionParser.g:3184:1: ( LessThanSign )
            {
            // InternalRegularExpressionParser.g:3184:1: ( LessThanSign )
            // InternalRegularExpressionParser.g:3185:2: LessThanSign
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getLessThanSignKeyword_2_0_1()); 
            }
            match(input,LessThanSign,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getLessThanSignKeyword_2_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__1__Impl"


    // $ANTLR start "rule__Group__Group_2_0__2"
    // InternalRegularExpressionParser.g:3194:1: rule__Group__Group_2_0__2 : rule__Group__Group_2_0__2__Impl rule__Group__Group_2_0__3 ;
    public final void rule__Group__Group_2_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3198:1: ( rule__Group__Group_2_0__2__Impl rule__Group__Group_2_0__3 )
            // InternalRegularExpressionParser.g:3199:2: rule__Group__Group_2_0__2__Impl rule__Group__Group_2_0__3
            {
            pushFollow(FOLLOW_29);
            rule__Group__Group_2_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group_2_0__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__2"


    // $ANTLR start "rule__Group__Group_2_0__2__Impl"
    // InternalRegularExpressionParser.g:3206:1: rule__Group__Group_2_0__2__Impl : ( ( rule__Group__NameAssignment_2_0_2 ) ) ;
    public final void rule__Group__Group_2_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3210:1: ( ( ( rule__Group__NameAssignment_2_0_2 ) ) )
            // InternalRegularExpressionParser.g:3211:1: ( ( rule__Group__NameAssignment_2_0_2 ) )
            {
            // InternalRegularExpressionParser.g:3211:1: ( ( rule__Group__NameAssignment_2_0_2 ) )
            // InternalRegularExpressionParser.g:3212:2: ( rule__Group__NameAssignment_2_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNameAssignment_2_0_2()); 
            }
            // InternalRegularExpressionParser.g:3213:2: ( rule__Group__NameAssignment_2_0_2 )
            // InternalRegularExpressionParser.g:3213:3: rule__Group__NameAssignment_2_0_2
            {
            pushFollow(FOLLOW_2);
            rule__Group__NameAssignment_2_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNameAssignment_2_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__2__Impl"


    // $ANTLR start "rule__Group__Group_2_0__3"
    // InternalRegularExpressionParser.g:3221:1: rule__Group__Group_2_0__3 : rule__Group__Group_2_0__3__Impl ;
    public final void rule__Group__Group_2_0__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3225:1: ( rule__Group__Group_2_0__3__Impl )
            // InternalRegularExpressionParser.g:3226:2: rule__Group__Group_2_0__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Group__Group_2_0__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__3"


    // $ANTLR start "rule__Group__Group_2_0__3__Impl"
    // InternalRegularExpressionParser.g:3232:1: rule__Group__Group_2_0__3__Impl : ( GreaterThanSign ) ;
    public final void rule__Group__Group_2_0__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3236:1: ( ( GreaterThanSign ) )
            // InternalRegularExpressionParser.g:3237:1: ( GreaterThanSign )
            {
            // InternalRegularExpressionParser.g:3237:1: ( GreaterThanSign )
            // InternalRegularExpressionParser.g:3238:2: GreaterThanSign
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getGreaterThanSignKeyword_2_0_3()); 
            }
            match(input,GreaterThanSign,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getGreaterThanSignKeyword_2_0_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_0__3__Impl"


    // $ANTLR start "rule__Group__Group_2_1__0"
    // InternalRegularExpressionParser.g:3248:1: rule__Group__Group_2_1__0 : rule__Group__Group_2_1__0__Impl rule__Group__Group_2_1__1 ;
    public final void rule__Group__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3252:1: ( rule__Group__Group_2_1__0__Impl rule__Group__Group_2_1__1 )
            // InternalRegularExpressionParser.g:3253:2: rule__Group__Group_2_1__0__Impl rule__Group__Group_2_1__1
            {
            pushFollow(FOLLOW_30);
            rule__Group__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_1__0"


    // $ANTLR start "rule__Group__Group_2_1__0__Impl"
    // InternalRegularExpressionParser.g:3260:1: rule__Group__Group_2_1__0__Impl : ( ( rule__Group__NonCapturingAssignment_2_1_0 ) ) ;
    public final void rule__Group__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3264:1: ( ( ( rule__Group__NonCapturingAssignment_2_1_0 ) ) )
            // InternalRegularExpressionParser.g:3265:1: ( ( rule__Group__NonCapturingAssignment_2_1_0 ) )
            {
            // InternalRegularExpressionParser.g:3265:1: ( ( rule__Group__NonCapturingAssignment_2_1_0 ) )
            // InternalRegularExpressionParser.g:3266:2: ( rule__Group__NonCapturingAssignment_2_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNonCapturingAssignment_2_1_0()); 
            }
            // InternalRegularExpressionParser.g:3267:2: ( rule__Group__NonCapturingAssignment_2_1_0 )
            // InternalRegularExpressionParser.g:3267:3: rule__Group__NonCapturingAssignment_2_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Group__NonCapturingAssignment_2_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNonCapturingAssignment_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_1__0__Impl"


    // $ANTLR start "rule__Group__Group_2_1__1"
    // InternalRegularExpressionParser.g:3275:1: rule__Group__Group_2_1__1 : rule__Group__Group_2_1__1__Impl ;
    public final void rule__Group__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3279:1: ( rule__Group__Group_2_1__1__Impl )
            // InternalRegularExpressionParser.g:3280:2: rule__Group__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Group__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_1__1"


    // $ANTLR start "rule__Group__Group_2_1__1__Impl"
    // InternalRegularExpressionParser.g:3286:1: rule__Group__Group_2_1__1__Impl : ( Colon ) ;
    public final void rule__Group__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3290:1: ( ( Colon ) )
            // InternalRegularExpressionParser.g:3291:1: ( Colon )
            {
            // InternalRegularExpressionParser.g:3291:1: ( Colon )
            // InternalRegularExpressionParser.g:3292:2: Colon
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getColonKeyword_2_1_1()); 
            }
            match(input,Colon,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getColonKeyword_2_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2_1__1__Impl"


    // $ANTLR start "rule__RegExpIdentifierName__Group__0"
    // InternalRegularExpressionParser.g:3302:1: rule__RegExpIdentifierName__Group__0 : rule__RegExpIdentifierName__Group__0__Impl rule__RegExpIdentifierName__Group__1 ;
    public final void rule__RegExpIdentifierName__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3306:1: ( rule__RegExpIdentifierName__Group__0__Impl rule__RegExpIdentifierName__Group__1 )
            // InternalRegularExpressionParser.g:3307:2: rule__RegExpIdentifierName__Group__0__Impl rule__RegExpIdentifierName__Group__1
            {
            pushFollow(FOLLOW_31);
            rule__RegExpIdentifierName__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RegExpIdentifierName__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegExpIdentifierName__Group__0"


    // $ANTLR start "rule__RegExpIdentifierName__Group__0__Impl"
    // InternalRegularExpressionParser.g:3314:1: rule__RegExpIdentifierName__Group__0__Impl : ( ruleRegExpIdentifierStart ) ;
    public final void rule__RegExpIdentifierName__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3318:1: ( ( ruleRegExpIdentifierStart ) )
            // InternalRegularExpressionParser.g:3319:1: ( ruleRegExpIdentifierStart )
            {
            // InternalRegularExpressionParser.g:3319:1: ( ruleRegExpIdentifierStart )
            // InternalRegularExpressionParser.g:3320:2: ruleRegExpIdentifierStart
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierStartParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleRegExpIdentifierStart();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierStartParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegExpIdentifierName__Group__0__Impl"


    // $ANTLR start "rule__RegExpIdentifierName__Group__1"
    // InternalRegularExpressionParser.g:3329:1: rule__RegExpIdentifierName__Group__1 : rule__RegExpIdentifierName__Group__1__Impl ;
    public final void rule__RegExpIdentifierName__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3333:1: ( rule__RegExpIdentifierName__Group__1__Impl )
            // InternalRegularExpressionParser.g:3334:2: rule__RegExpIdentifierName__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RegExpIdentifierName__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegExpIdentifierName__Group__1"


    // $ANTLR start "rule__RegExpIdentifierName__Group__1__Impl"
    // InternalRegularExpressionParser.g:3340:1: rule__RegExpIdentifierName__Group__1__Impl : ( ( ruleRegExpIdentifierPart )* ) ;
    public final void rule__RegExpIdentifierName__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3344:1: ( ( ( ruleRegExpIdentifierPart )* ) )
            // InternalRegularExpressionParser.g:3345:1: ( ( ruleRegExpIdentifierPart )* )
            {
            // InternalRegularExpressionParser.g:3345:1: ( ( ruleRegExpIdentifierPart )* )
            // InternalRegularExpressionParser.g:3346:2: ( ruleRegExpIdentifierPart )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierPartParserRuleCall_1()); 
            }
            // InternalRegularExpressionParser.g:3347:2: ( ruleRegExpIdentifierPart )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==DollarSign||LA33_0==KW__||LA33_0==RULE_UNICODE_ESCAPE||LA33_0==RULE_UNICODE_DIGIT||LA33_0==RULE_UNICODE_LETTER) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:3347:3: ruleRegExpIdentifierPart
            	    {
            	    pushFollow(FOLLOW_32);
            	    ruleRegExpIdentifierPart();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierPartParserRuleCall_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegExpIdentifierName__Group__1__Impl"


    // $ANTLR start "rule__SimpleQuantifier__Group__0"
    // InternalRegularExpressionParser.g:3356:1: rule__SimpleQuantifier__Group__0 : rule__SimpleQuantifier__Group__0__Impl rule__SimpleQuantifier__Group__1 ;
    public final void rule__SimpleQuantifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3360:1: ( rule__SimpleQuantifier__Group__0__Impl rule__SimpleQuantifier__Group__1 )
            // InternalRegularExpressionParser.g:3361:2: rule__SimpleQuantifier__Group__0__Impl rule__SimpleQuantifier__Group__1
            {
            pushFollow(FOLLOW_16);
            rule__SimpleQuantifier__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__SimpleQuantifier__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleQuantifier__Group__0"


    // $ANTLR start "rule__SimpleQuantifier__Group__0__Impl"
    // InternalRegularExpressionParser.g:3368:1: rule__SimpleQuantifier__Group__0__Impl : ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) ) ;
    public final void rule__SimpleQuantifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3372:1: ( ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) ) )
            // InternalRegularExpressionParser.g:3373:1: ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) )
            {
            // InternalRegularExpressionParser.g:3373:1: ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) )
            // InternalRegularExpressionParser.g:3374:2: ( rule__SimpleQuantifier__QuantifierAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAssignment_0()); 
            }
            // InternalRegularExpressionParser.g:3375:2: ( rule__SimpleQuantifier__QuantifierAssignment_0 )
            // InternalRegularExpressionParser.g:3375:3: rule__SimpleQuantifier__QuantifierAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleQuantifier__QuantifierAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleQuantifierAccess().getQuantifierAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleQuantifier__Group__0__Impl"


    // $ANTLR start "rule__SimpleQuantifier__Group__1"
    // InternalRegularExpressionParser.g:3383:1: rule__SimpleQuantifier__Group__1 : rule__SimpleQuantifier__Group__1__Impl ;
    public final void rule__SimpleQuantifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3387:1: ( rule__SimpleQuantifier__Group__1__Impl )
            // InternalRegularExpressionParser.g:3388:2: rule__SimpleQuantifier__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleQuantifier__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleQuantifier__Group__1"


    // $ANTLR start "rule__SimpleQuantifier__Group__1__Impl"
    // InternalRegularExpressionParser.g:3394:1: rule__SimpleQuantifier__Group__1__Impl : ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? ) ;
    public final void rule__SimpleQuantifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3398:1: ( ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? ) )
            // InternalRegularExpressionParser.g:3399:1: ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? )
            {
            // InternalRegularExpressionParser.g:3399:1: ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? )
            // InternalRegularExpressionParser.g:3400:2: ( rule__SimpleQuantifier__NonGreedyAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyAssignment_1()); 
            }
            // InternalRegularExpressionParser.g:3401:2: ( rule__SimpleQuantifier__NonGreedyAssignment_1 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==QuestionMark) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalRegularExpressionParser.g:3401:3: rule__SimpleQuantifier__NonGreedyAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__SimpleQuantifier__NonGreedyAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleQuantifierAccess().getNonGreedyAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleQuantifier__Group__1__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group__0"
    // InternalRegularExpressionParser.g:3410:1: rule__ExactQuantifier__Group__0 : rule__ExactQuantifier__Group__0__Impl rule__ExactQuantifier__Group__1 ;
    public final void rule__ExactQuantifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3414:1: ( rule__ExactQuantifier__Group__0__Impl rule__ExactQuantifier__Group__1 )
            // InternalRegularExpressionParser.g:3415:2: rule__ExactQuantifier__Group__0__Impl rule__ExactQuantifier__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__ExactQuantifier__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__0"


    // $ANTLR start "rule__ExactQuantifier__Group__0__Impl"
    // InternalRegularExpressionParser.g:3422:1: rule__ExactQuantifier__Group__0__Impl : ( () ) ;
    public final void rule__ExactQuantifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3426:1: ( ( () ) )
            // InternalRegularExpressionParser.g:3427:1: ( () )
            {
            // InternalRegularExpressionParser.g:3427:1: ( () )
            // InternalRegularExpressionParser.g:3428:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0()); 
            }
            // InternalRegularExpressionParser.g:3429:2: ()
            // InternalRegularExpressionParser.g:3429:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__0__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group__1"
    // InternalRegularExpressionParser.g:3437:1: rule__ExactQuantifier__Group__1 : rule__ExactQuantifier__Group__1__Impl rule__ExactQuantifier__Group__2 ;
    public final void rule__ExactQuantifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3441:1: ( rule__ExactQuantifier__Group__1__Impl rule__ExactQuantifier__Group__2 )
            // InternalRegularExpressionParser.g:3442:2: rule__ExactQuantifier__Group__1__Impl rule__ExactQuantifier__Group__2
            {
            pushFollow(FOLLOW_33);
            rule__ExactQuantifier__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__1"


    // $ANTLR start "rule__ExactQuantifier__Group__1__Impl"
    // InternalRegularExpressionParser.g:3449:1: rule__ExactQuantifier__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ExactQuantifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3453:1: ( ( LeftCurlyBracket ) )
            // InternalRegularExpressionParser.g:3454:1: ( LeftCurlyBracket )
            {
            // InternalRegularExpressionParser.g:3454:1: ( LeftCurlyBracket )
            // InternalRegularExpressionParser.g:3455:2: LeftCurlyBracket
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getLeftCurlyBracketKeyword_1()); 
            }
            match(input,LeftCurlyBracket,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getLeftCurlyBracketKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__1__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group__2"
    // InternalRegularExpressionParser.g:3464:1: rule__ExactQuantifier__Group__2 : rule__ExactQuantifier__Group__2__Impl rule__ExactQuantifier__Group__3 ;
    public final void rule__ExactQuantifier__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3468:1: ( rule__ExactQuantifier__Group__2__Impl rule__ExactQuantifier__Group__3 )
            // InternalRegularExpressionParser.g:3469:2: rule__ExactQuantifier__Group__2__Impl rule__ExactQuantifier__Group__3
            {
            pushFollow(FOLLOW_34);
            rule__ExactQuantifier__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__2"


    // $ANTLR start "rule__ExactQuantifier__Group__2__Impl"
    // InternalRegularExpressionParser.g:3476:1: rule__ExactQuantifier__Group__2__Impl : ( ( rule__ExactQuantifier__MinAssignment_2 ) ) ;
    public final void rule__ExactQuantifier__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3480:1: ( ( ( rule__ExactQuantifier__MinAssignment_2 ) ) )
            // InternalRegularExpressionParser.g:3481:1: ( ( rule__ExactQuantifier__MinAssignment_2 ) )
            {
            // InternalRegularExpressionParser.g:3481:1: ( ( rule__ExactQuantifier__MinAssignment_2 ) )
            // InternalRegularExpressionParser.g:3482:2: ( rule__ExactQuantifier__MinAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getMinAssignment_2()); 
            }
            // InternalRegularExpressionParser.g:3483:2: ( rule__ExactQuantifier__MinAssignment_2 )
            // InternalRegularExpressionParser.g:3483:3: rule__ExactQuantifier__MinAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__MinAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getMinAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__2__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group__3"
    // InternalRegularExpressionParser.g:3491:1: rule__ExactQuantifier__Group__3 : rule__ExactQuantifier__Group__3__Impl rule__ExactQuantifier__Group__4 ;
    public final void rule__ExactQuantifier__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3495:1: ( rule__ExactQuantifier__Group__3__Impl rule__ExactQuantifier__Group__4 )
            // InternalRegularExpressionParser.g:3496:2: rule__ExactQuantifier__Group__3__Impl rule__ExactQuantifier__Group__4
            {
            pushFollow(FOLLOW_34);
            rule__ExactQuantifier__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__3"


    // $ANTLR start "rule__ExactQuantifier__Group__3__Impl"
    // InternalRegularExpressionParser.g:3503:1: rule__ExactQuantifier__Group__3__Impl : ( ( rule__ExactQuantifier__Alternatives_3 )? ) ;
    public final void rule__ExactQuantifier__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3507:1: ( ( ( rule__ExactQuantifier__Alternatives_3 )? ) )
            // InternalRegularExpressionParser.g:3508:1: ( ( rule__ExactQuantifier__Alternatives_3 )? )
            {
            // InternalRegularExpressionParser.g:3508:1: ( ( rule__ExactQuantifier__Alternatives_3 )? )
            // InternalRegularExpressionParser.g:3509:2: ( rule__ExactQuantifier__Alternatives_3 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getAlternatives_3()); 
            }
            // InternalRegularExpressionParser.g:3510:2: ( rule__ExactQuantifier__Alternatives_3 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==Comma) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalRegularExpressionParser.g:3510:3: rule__ExactQuantifier__Alternatives_3
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExactQuantifier__Alternatives_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getAlternatives_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__3__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group__4"
    // InternalRegularExpressionParser.g:3518:1: rule__ExactQuantifier__Group__4 : rule__ExactQuantifier__Group__4__Impl rule__ExactQuantifier__Group__5 ;
    public final void rule__ExactQuantifier__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3522:1: ( rule__ExactQuantifier__Group__4__Impl rule__ExactQuantifier__Group__5 )
            // InternalRegularExpressionParser.g:3523:2: rule__ExactQuantifier__Group__4__Impl rule__ExactQuantifier__Group__5
            {
            pushFollow(FOLLOW_16);
            rule__ExactQuantifier__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__4"


    // $ANTLR start "rule__ExactQuantifier__Group__4__Impl"
    // InternalRegularExpressionParser.g:3530:1: rule__ExactQuantifier__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ExactQuantifier__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3534:1: ( ( RightCurlyBracket ) )
            // InternalRegularExpressionParser.g:3535:1: ( RightCurlyBracket )
            {
            // InternalRegularExpressionParser.g:3535:1: ( RightCurlyBracket )
            // InternalRegularExpressionParser.g:3536:2: RightCurlyBracket
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getRightCurlyBracketKeyword_4()); 
            }
            match(input,RightCurlyBracket,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getRightCurlyBracketKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__4__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group__5"
    // InternalRegularExpressionParser.g:3545:1: rule__ExactQuantifier__Group__5 : rule__ExactQuantifier__Group__5__Impl ;
    public final void rule__ExactQuantifier__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3549:1: ( rule__ExactQuantifier__Group__5__Impl )
            // InternalRegularExpressionParser.g:3550:2: rule__ExactQuantifier__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__5"


    // $ANTLR start "rule__ExactQuantifier__Group__5__Impl"
    // InternalRegularExpressionParser.g:3556:1: rule__ExactQuantifier__Group__5__Impl : ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? ) ;
    public final void rule__ExactQuantifier__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3560:1: ( ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? ) )
            // InternalRegularExpressionParser.g:3561:1: ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? )
            {
            // InternalRegularExpressionParser.g:3561:1: ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? )
            // InternalRegularExpressionParser.g:3562:2: ( rule__ExactQuantifier__NonGreedyAssignment_5 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getNonGreedyAssignment_5()); 
            }
            // InternalRegularExpressionParser.g:3563:2: ( rule__ExactQuantifier__NonGreedyAssignment_5 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==QuestionMark) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalRegularExpressionParser.g:3563:3: rule__ExactQuantifier__NonGreedyAssignment_5
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExactQuantifier__NonGreedyAssignment_5();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getNonGreedyAssignment_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group__5__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group_3_0__0"
    // InternalRegularExpressionParser.g:3572:1: rule__ExactQuantifier__Group_3_0__0 : rule__ExactQuantifier__Group_3_0__0__Impl rule__ExactQuantifier__Group_3_0__1 ;
    public final void rule__ExactQuantifier__Group_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3576:1: ( rule__ExactQuantifier__Group_3_0__0__Impl rule__ExactQuantifier__Group_3_0__1 )
            // InternalRegularExpressionParser.g:3577:2: rule__ExactQuantifier__Group_3_0__0__Impl rule__ExactQuantifier__Group_3_0__1
            {
            pushFollow(FOLLOW_33);
            rule__ExactQuantifier__Group_3_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group_3_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group_3_0__0"


    // $ANTLR start "rule__ExactQuantifier__Group_3_0__0__Impl"
    // InternalRegularExpressionParser.g:3584:1: rule__ExactQuantifier__Group_3_0__0__Impl : ( Comma ) ;
    public final void rule__ExactQuantifier__Group_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3588:1: ( ( Comma ) )
            // InternalRegularExpressionParser.g:3589:1: ( Comma )
            {
            // InternalRegularExpressionParser.g:3589:1: ( Comma )
            // InternalRegularExpressionParser.g:3590:2: Comma
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getCommaKeyword_3_0_0()); 
            }
            match(input,Comma,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getCommaKeyword_3_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group_3_0__0__Impl"


    // $ANTLR start "rule__ExactQuantifier__Group_3_0__1"
    // InternalRegularExpressionParser.g:3599:1: rule__ExactQuantifier__Group_3_0__1 : rule__ExactQuantifier__Group_3_0__1__Impl ;
    public final void rule__ExactQuantifier__Group_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3603:1: ( rule__ExactQuantifier__Group_3_0__1__Impl )
            // InternalRegularExpressionParser.g:3604:2: rule__ExactQuantifier__Group_3_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__Group_3_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group_3_0__1"


    // $ANTLR start "rule__ExactQuantifier__Group_3_0__1__Impl"
    // InternalRegularExpressionParser.g:3610:1: rule__ExactQuantifier__Group_3_0__1__Impl : ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) ) ;
    public final void rule__ExactQuantifier__Group_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3614:1: ( ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) ) )
            // InternalRegularExpressionParser.g:3615:1: ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) )
            {
            // InternalRegularExpressionParser.g:3615:1: ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) )
            // InternalRegularExpressionParser.g:3616:2: ( rule__ExactQuantifier__MaxAssignment_3_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getMaxAssignment_3_0_1()); 
            }
            // InternalRegularExpressionParser.g:3617:2: ( rule__ExactQuantifier__MaxAssignment_3_0_1 )
            // InternalRegularExpressionParser.g:3617:3: rule__ExactQuantifier__MaxAssignment_3_0_1
            {
            pushFollow(FOLLOW_2);
            rule__ExactQuantifier__MaxAssignment_3_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getMaxAssignment_3_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__Group_3_0__1__Impl"


    // $ANTLR start "rule__RegularExpressionFlags__Group__0"
    // InternalRegularExpressionParser.g:3626:1: rule__RegularExpressionFlags__Group__0 : rule__RegularExpressionFlags__Group__0__Impl rule__RegularExpressionFlags__Group__1 ;
    public final void rule__RegularExpressionFlags__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3630:1: ( rule__RegularExpressionFlags__Group__0__Impl rule__RegularExpressionFlags__Group__1 )
            // InternalRegularExpressionParser.g:3631:2: rule__RegularExpressionFlags__Group__0__Impl rule__RegularExpressionFlags__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__RegularExpressionFlags__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__RegularExpressionFlags__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionFlags__Group__0"


    // $ANTLR start "rule__RegularExpressionFlags__Group__0__Impl"
    // InternalRegularExpressionParser.g:3638:1: rule__RegularExpressionFlags__Group__0__Impl : ( () ) ;
    public final void rule__RegularExpressionFlags__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3642:1: ( ( () ) )
            // InternalRegularExpressionParser.g:3643:1: ( () )
            {
            // InternalRegularExpressionParser.g:3643:1: ( () )
            // InternalRegularExpressionParser.g:3644:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0()); 
            }
            // InternalRegularExpressionParser.g:3645:2: ()
            // InternalRegularExpressionParser.g:3645:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionFlags__Group__0__Impl"


    // $ANTLR start "rule__RegularExpressionFlags__Group__1"
    // InternalRegularExpressionParser.g:3653:1: rule__RegularExpressionFlags__Group__1 : rule__RegularExpressionFlags__Group__1__Impl ;
    public final void rule__RegularExpressionFlags__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3657:1: ( rule__RegularExpressionFlags__Group__1__Impl )
            // InternalRegularExpressionParser.g:3658:2: rule__RegularExpressionFlags__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionFlags__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionFlags__Group__1"


    // $ANTLR start "rule__RegularExpressionFlags__Group__1__Impl"
    // InternalRegularExpressionParser.g:3664:1: rule__RegularExpressionFlags__Group__1__Impl : ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* ) ;
    public final void rule__RegularExpressionFlags__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3668:1: ( ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* ) )
            // InternalRegularExpressionParser.g:3669:1: ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* )
            {
            // InternalRegularExpressionParser.g:3669:1: ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* )
            // InternalRegularExpressionParser.g:3670:2: ( rule__RegularExpressionFlags__FlagsAssignment_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAssignment_1()); 
            }
            // InternalRegularExpressionParser.g:3671:2: ( rule__RegularExpressionFlags__FlagsAssignment_1 )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==RULE_UNICODE_ESCAPE||LA37_0==RULE_UNICODE_LETTER) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:3671:3: rule__RegularExpressionFlags__FlagsAssignment_1
            	    {
            	    pushFollow(FOLLOW_35);
            	    rule__RegularExpressionFlags__FlagsAssignment_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionFlags__Group__1__Impl"


    // $ANTLR start "rule__RegularExpressionLiteral__BodyAssignment_1"
    // InternalRegularExpressionParser.g:3680:1: rule__RegularExpressionLiteral__BodyAssignment_1 : ( ruleRegularExpressionBody ) ;
    public final void rule__RegularExpressionLiteral__BodyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3684:1: ( ( ruleRegularExpressionBody ) )
            // InternalRegularExpressionParser.g:3685:2: ( ruleRegularExpressionBody )
            {
            // InternalRegularExpressionParser.g:3685:2: ( ruleRegularExpressionBody )
            // InternalRegularExpressionParser.g:3686:3: ruleRegularExpressionBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getBodyRegularExpressionBodyParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleRegularExpressionBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralAccess().getBodyRegularExpressionBodyParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__BodyAssignment_1"


    // $ANTLR start "rule__RegularExpressionLiteral__FlagsAssignment_3"
    // InternalRegularExpressionParser.g:3695:1: rule__RegularExpressionLiteral__FlagsAssignment_3 : ( ruleRegularExpressionFlags ) ;
    public final void rule__RegularExpressionLiteral__FlagsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3699:1: ( ( ruleRegularExpressionFlags ) )
            // InternalRegularExpressionParser.g:3700:2: ( ruleRegularExpressionFlags )
            {
            // InternalRegularExpressionParser.g:3700:2: ( ruleRegularExpressionFlags )
            // InternalRegularExpressionParser.g:3701:3: ruleRegularExpressionFlags
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getFlagsRegularExpressionFlagsParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleRegularExpressionFlags();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionLiteralAccess().getFlagsRegularExpressionFlagsParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionLiteral__FlagsAssignment_3"


    // $ANTLR start "rule__RegularExpressionBody__PatternAssignment"
    // InternalRegularExpressionParser.g:3710:1: rule__RegularExpressionBody__PatternAssignment : ( ruleDisjunction ) ;
    public final void rule__RegularExpressionBody__PatternAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3714:1: ( ( ruleDisjunction ) )
            // InternalRegularExpressionParser.g:3715:2: ( ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:3715:2: ( ruleDisjunction )
            // InternalRegularExpressionParser.g:3716:3: ruleDisjunction
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionBodyAccess().getPatternDisjunctionParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDisjunction();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionBodyAccess().getPatternDisjunctionParserRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionBody__PatternAssignment"


    // $ANTLR start "rule__Disjunction__ElementsAssignment_0_1_1_1"
    // InternalRegularExpressionParser.g:3725:1: rule__Disjunction__ElementsAssignment_0_1_1_1 : ( ruleAlternative ) ;
    public final void rule__Disjunction__ElementsAssignment_0_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3729:1: ( ( ruleAlternative ) )
            // InternalRegularExpressionParser.g:3730:2: ( ruleAlternative )
            {
            // InternalRegularExpressionParser.g:3730:2: ( ruleAlternative )
            // InternalRegularExpressionParser.g:3731:3: ruleAlternative
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_0_1_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleAlternative();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_0_1_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__ElementsAssignment_0_1_1_1"


    // $ANTLR start "rule__Disjunction__ElementsAssignment_1_1_1"
    // InternalRegularExpressionParser.g:3740:1: rule__Disjunction__ElementsAssignment_1_1_1 : ( ruleAlternative ) ;
    public final void rule__Disjunction__ElementsAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3744:1: ( ( ruleAlternative ) )
            // InternalRegularExpressionParser.g:3745:2: ( ruleAlternative )
            {
            // InternalRegularExpressionParser.g:3745:2: ( ruleAlternative )
            // InternalRegularExpressionParser.g:3746:3: ruleAlternative
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_1_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleAlternative();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_1_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Disjunction__ElementsAssignment_1_1_1"


    // $ANTLR start "rule__Alternative__ElementsAssignment_1_1"
    // InternalRegularExpressionParser.g:3755:1: rule__Alternative__ElementsAssignment_1_1 : ( ruleTerm ) ;
    public final void rule__Alternative__ElementsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3759:1: ( ( ruleTerm ) )
            // InternalRegularExpressionParser.g:3760:2: ( ruleTerm )
            {
            // InternalRegularExpressionParser.g:3760:2: ( ruleTerm )
            // InternalRegularExpressionParser.g:3761:3: ruleTerm
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getElementsTermParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleTerm();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAlternativeAccess().getElementsTermParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Alternative__ElementsAssignment_1_1"


    // $ANTLR start "rule__Term__QuantifierAssignment_1_1"
    // InternalRegularExpressionParser.g:3770:1: rule__Term__QuantifierAssignment_1_1 : ( ruleQuantifier ) ;
    public final void rule__Term__QuantifierAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3774:1: ( ( ruleQuantifier ) )
            // InternalRegularExpressionParser.g:3775:2: ( ruleQuantifier )
            {
            // InternalRegularExpressionParser.g:3775:2: ( ruleQuantifier )
            // InternalRegularExpressionParser.g:3776:3: ruleQuantifier
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTermAccess().getQuantifierQuantifierParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleQuantifier();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTermAccess().getQuantifierQuantifierParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__QuantifierAssignment_1_1"


    // $ANTLR start "rule__WordBoundary__NotAssignment_1_1"
    // InternalRegularExpressionParser.g:3785:1: rule__WordBoundary__NotAssignment_1_1 : ( RULE_NOT_WORD_BOUNDARY ) ;
    public final void rule__WordBoundary__NotAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3789:1: ( ( RULE_NOT_WORD_BOUNDARY ) )
            // InternalRegularExpressionParser.g:3790:2: ( RULE_NOT_WORD_BOUNDARY )
            {
            // InternalRegularExpressionParser.g:3790:2: ( RULE_NOT_WORD_BOUNDARY )
            // InternalRegularExpressionParser.g:3791:3: RULE_NOT_WORD_BOUNDARY
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryAccess().getNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0()); 
            }
            match(input,RULE_NOT_WORD_BOUNDARY,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getWordBoundaryAccess().getNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WordBoundary__NotAssignment_1_1"


    // $ANTLR start "rule__LookAhead__BackwardsAssignment_3"
    // InternalRegularExpressionParser.g:3800:1: rule__LookAhead__BackwardsAssignment_3 : ( ( LessThanSign ) ) ;
    public final void rule__LookAhead__BackwardsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3804:1: ( ( ( LessThanSign ) ) )
            // InternalRegularExpressionParser.g:3805:2: ( ( LessThanSign ) )
            {
            // InternalRegularExpressionParser.g:3805:2: ( ( LessThanSign ) )
            // InternalRegularExpressionParser.g:3806:3: ( LessThanSign )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getBackwardsLessThanSignKeyword_3_0()); 
            }
            // InternalRegularExpressionParser.g:3807:3: ( LessThanSign )
            // InternalRegularExpressionParser.g:3808:4: LessThanSign
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getBackwardsLessThanSignKeyword_3_0()); 
            }
            match(input,LessThanSign,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getBackwardsLessThanSignKeyword_3_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getBackwardsLessThanSignKeyword_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__BackwardsAssignment_3"


    // $ANTLR start "rule__LookAhead__NotAssignment_4_1"
    // InternalRegularExpressionParser.g:3819:1: rule__LookAhead__NotAssignment_4_1 : ( ( ExclamationMark ) ) ;
    public final void rule__LookAhead__NotAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3823:1: ( ( ( ExclamationMark ) ) )
            // InternalRegularExpressionParser.g:3824:2: ( ( ExclamationMark ) )
            {
            // InternalRegularExpressionParser.g:3824:2: ( ( ExclamationMark ) )
            // InternalRegularExpressionParser.g:3825:3: ( ExclamationMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_4_1_0()); 
            }
            // InternalRegularExpressionParser.g:3826:3: ( ExclamationMark )
            // InternalRegularExpressionParser.g:3827:4: ExclamationMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_4_1_0()); 
            }
            match(input,ExclamationMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_4_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_4_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__NotAssignment_4_1"


    // $ANTLR start "rule__LookAhead__PatternAssignment_5"
    // InternalRegularExpressionParser.g:3838:1: rule__LookAhead__PatternAssignment_5 : ( ruleDisjunction ) ;
    public final void rule__LookAhead__PatternAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3842:1: ( ( ruleDisjunction ) )
            // InternalRegularExpressionParser.g:3843:2: ( ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:3843:2: ( ruleDisjunction )
            // InternalRegularExpressionParser.g:3844:3: ruleDisjunction
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getPatternDisjunctionParserRuleCall_5_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDisjunction();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getPatternDisjunctionParserRuleCall_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__PatternAssignment_5"


    // $ANTLR start "rule__PatternCharacter__ValueAssignment"
    // InternalRegularExpressionParser.g:3853:1: rule__PatternCharacter__ValueAssignment : ( ( rule__PatternCharacter__ValueAlternatives_0 ) ) ;
    public final void rule__PatternCharacter__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3857:1: ( ( ( rule__PatternCharacter__ValueAlternatives_0 ) ) )
            // InternalRegularExpressionParser.g:3858:2: ( ( rule__PatternCharacter__ValueAlternatives_0 ) )
            {
            // InternalRegularExpressionParser.g:3858:2: ( ( rule__PatternCharacter__ValueAlternatives_0 ) )
            // InternalRegularExpressionParser.g:3859:3: ( rule__PatternCharacter__ValueAlternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPatternCharacterAccess().getValueAlternatives_0()); 
            }
            // InternalRegularExpressionParser.g:3860:3: ( rule__PatternCharacter__ValueAlternatives_0 )
            // InternalRegularExpressionParser.g:3860:4: rule__PatternCharacter__ValueAlternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__PatternCharacter__ValueAlternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPatternCharacterAccess().getValueAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatternCharacter__ValueAssignment"


    // $ANTLR start "rule__CharacterClassEscapeSequence__SequenceAssignment"
    // InternalRegularExpressionParser.g:3868:1: rule__CharacterClassEscapeSequence__SequenceAssignment : ( RULE_CHARACTER_CLASS_ESCAPE ) ;
    public final void rule__CharacterClassEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3872:1: ( ( RULE_CHARACTER_CLASS_ESCAPE ) )
            // InternalRegularExpressionParser.g:3873:2: ( RULE_CHARACTER_CLASS_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3873:2: ( RULE_CHARACTER_CLASS_ESCAPE )
            // InternalRegularExpressionParser.g:3874:3: RULE_CHARACTER_CLASS_ESCAPE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0()); 
            }
            match(input,RULE_CHARACTER_CLASS_ESCAPE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassEscapeSequence__SequenceAssignment"


    // $ANTLR start "rule__CharacterEscapeSequence__SequenceAssignment"
    // InternalRegularExpressionParser.g:3883:1: rule__CharacterEscapeSequence__SequenceAssignment : ( RULE_CONTROL_ESCAPE ) ;
    public final void rule__CharacterEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3887:1: ( ( RULE_CONTROL_ESCAPE ) )
            // InternalRegularExpressionParser.g:3888:2: ( RULE_CONTROL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3888:2: ( RULE_CONTROL_ESCAPE )
            // InternalRegularExpressionParser.g:3889:3: RULE_CONTROL_ESCAPE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceCONTROL_ESCAPETerminalRuleCall_0()); 
            }
            match(input,RULE_CONTROL_ESCAPE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceCONTROL_ESCAPETerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterEscapeSequence__SequenceAssignment"


    // $ANTLR start "rule__ControlLetterEscapeSequence__SequenceAssignment"
    // InternalRegularExpressionParser.g:3898:1: rule__ControlLetterEscapeSequence__SequenceAssignment : ( RULE_CONTROL_LETTER_ESCAPE ) ;
    public final void rule__ControlLetterEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3902:1: ( ( RULE_CONTROL_LETTER_ESCAPE ) )
            // InternalRegularExpressionParser.g:3903:2: ( RULE_CONTROL_LETTER_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3903:2: ( RULE_CONTROL_LETTER_ESCAPE )
            // InternalRegularExpressionParser.g:3904:3: RULE_CONTROL_LETTER_ESCAPE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0()); 
            }
            match(input,RULE_CONTROL_LETTER_ESCAPE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ControlLetterEscapeSequence__SequenceAssignment"


    // $ANTLR start "rule__HexEscapeSequence__SequenceAssignment"
    // InternalRegularExpressionParser.g:3913:1: rule__HexEscapeSequence__SequenceAssignment : ( RULE_HEX_ESCAPE ) ;
    public final void rule__HexEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3917:1: ( ( RULE_HEX_ESCAPE ) )
            // InternalRegularExpressionParser.g:3918:2: ( RULE_HEX_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3918:2: ( RULE_HEX_ESCAPE )
            // InternalRegularExpressionParser.g:3919:3: RULE_HEX_ESCAPE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHexEscapeSequenceAccess().getSequenceHEX_ESCAPETerminalRuleCall_0()); 
            }
            match(input,RULE_HEX_ESCAPE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getHexEscapeSequenceAccess().getSequenceHEX_ESCAPETerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HexEscapeSequence__SequenceAssignment"


    // $ANTLR start "rule__UnicodeEscapeSequence__SequenceAssignment"
    // InternalRegularExpressionParser.g:3928:1: rule__UnicodeEscapeSequence__SequenceAssignment : ( RULE_UNICODE_ESCAPE ) ;
    public final void rule__UnicodeEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3932:1: ( ( RULE_UNICODE_ESCAPE ) )
            // InternalRegularExpressionParser.g:3933:2: ( RULE_UNICODE_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3933:2: ( RULE_UNICODE_ESCAPE )
            // InternalRegularExpressionParser.g:3934:3: RULE_UNICODE_ESCAPE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceUNICODE_ESCAPETerminalRuleCall_0()); 
            }
            match(input,RULE_UNICODE_ESCAPE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceUNICODE_ESCAPETerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnicodeEscapeSequence__SequenceAssignment"


    // $ANTLR start "rule__IdentityEscapeSequence__SequenceAssignment"
    // InternalRegularExpressionParser.g:3943:1: rule__IdentityEscapeSequence__SequenceAssignment : ( RULE_IDENTITY_ESCAPE ) ;
    public final void rule__IdentityEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3947:1: ( ( RULE_IDENTITY_ESCAPE ) )
            // InternalRegularExpressionParser.g:3948:2: ( RULE_IDENTITY_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3948:2: ( RULE_IDENTITY_ESCAPE )
            // InternalRegularExpressionParser.g:3949:3: RULE_IDENTITY_ESCAPE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceIDENTITY_ESCAPETerminalRuleCall_0()); 
            }
            match(input,RULE_IDENTITY_ESCAPE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceIDENTITY_ESCAPETerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IdentityEscapeSequence__SequenceAssignment"


    // $ANTLR start "rule__DecimalEscapeSequence__SequenceAssignment"
    // InternalRegularExpressionParser.g:3958:1: rule__DecimalEscapeSequence__SequenceAssignment : ( RULE_DECIMAL_ESCAPE ) ;
    public final void rule__DecimalEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3962:1: ( ( RULE_DECIMAL_ESCAPE ) )
            // InternalRegularExpressionParser.g:3963:2: ( RULE_DECIMAL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3963:2: ( RULE_DECIMAL_ESCAPE )
            // InternalRegularExpressionParser.g:3964:3: RULE_DECIMAL_ESCAPE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceDECIMAL_ESCAPETerminalRuleCall_0()); 
            }
            match(input,RULE_DECIMAL_ESCAPE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceDECIMAL_ESCAPETerminalRuleCall_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DecimalEscapeSequence__SequenceAssignment"


    // $ANTLR start "rule__CharacterClass__NegatedAssignment_2_0"
    // InternalRegularExpressionParser.g:3973:1: rule__CharacterClass__NegatedAssignment_2_0 : ( ( CircumflexAccent ) ) ;
    public final void rule__CharacterClass__NegatedAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3977:1: ( ( ( CircumflexAccent ) ) )
            // InternalRegularExpressionParser.g:3978:2: ( ( CircumflexAccent ) )
            {
            // InternalRegularExpressionParser.g:3978:2: ( ( CircumflexAccent ) )
            // InternalRegularExpressionParser.g:3979:3: ( CircumflexAccent )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); 
            }
            // InternalRegularExpressionParser.g:3980:3: ( CircumflexAccent )
            // InternalRegularExpressionParser.g:3981:4: CircumflexAccent
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); 
            }
            match(input,CircumflexAccent,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__NegatedAssignment_2_0"


    // $ANTLR start "rule__CharacterClass__ElementsAssignment_3"
    // InternalRegularExpressionParser.g:3992:1: rule__CharacterClass__ElementsAssignment_3 : ( ruleCharacterClassElement ) ;
    public final void rule__CharacterClass__ElementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3996:1: ( ( ruleCharacterClassElement ) )
            // InternalRegularExpressionParser.g:3997:2: ( ruleCharacterClassElement )
            {
            // InternalRegularExpressionParser.g:3997:2: ( ruleCharacterClassElement )
            // InternalRegularExpressionParser.g:3998:3: ruleCharacterClassElement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getElementsCharacterClassElementParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCharacterClassElement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAccess().getElementsCharacterClassElementParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClass__ElementsAssignment_3"


    // $ANTLR start "rule__CharacterClassElement__RightAssignment_1_0_2"
    // InternalRegularExpressionParser.g:4007:1: rule__CharacterClassElement__RightAssignment_1_0_2 : ( ruleCharacterClassAtom ) ;
    public final void rule__CharacterClassElement__RightAssignment_1_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4011:1: ( ( ruleCharacterClassAtom ) )
            // InternalRegularExpressionParser.g:4012:2: ( ruleCharacterClassAtom )
            {
            // InternalRegularExpressionParser.g:4012:2: ( ruleCharacterClassAtom )
            // InternalRegularExpressionParser.g:4013:3: ruleCharacterClassAtom
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getRightCharacterClassAtomParserRuleCall_1_0_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCharacterClassAtom();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassElementAccess().getRightCharacterClassAtomParserRuleCall_1_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassElement__RightAssignment_1_0_2"


    // $ANTLR start "rule__CharacterClassAtom__CharacterAssignment_1"
    // InternalRegularExpressionParser.g:4022:1: rule__CharacterClassAtom__CharacterAssignment_1 : ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) ) ;
    public final void rule__CharacterClassAtom__CharacterAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4026:1: ( ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) ) )
            // InternalRegularExpressionParser.g:4027:2: ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) )
            {
            // InternalRegularExpressionParser.g:4027:2: ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) )
            // InternalRegularExpressionParser.g:4028:3: ( rule__CharacterClassAtom__CharacterAlternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAtomAccess().getCharacterAlternatives_1_0()); 
            }
            // InternalRegularExpressionParser.g:4029:3: ( rule__CharacterClassAtom__CharacterAlternatives_1_0 )
            // InternalRegularExpressionParser.g:4029:4: rule__CharacterClassAtom__CharacterAlternatives_1_0
            {
            pushFollow(FOLLOW_2);
            rule__CharacterClassAtom__CharacterAlternatives_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCharacterClassAtomAccess().getCharacterAlternatives_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterClassAtom__CharacterAssignment_1"


    // $ANTLR start "rule__Group__NamedAssignment_2_0_0"
    // InternalRegularExpressionParser.g:4037:1: rule__Group__NamedAssignment_2_0_0 : ( ( QuestionMark ) ) ;
    public final void rule__Group__NamedAssignment_2_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4041:1: ( ( ( QuestionMark ) ) )
            // InternalRegularExpressionParser.g:4042:2: ( ( QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:4042:2: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:4043:3: ( QuestionMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNamedQuestionMarkKeyword_2_0_0_0()); 
            }
            // InternalRegularExpressionParser.g:4044:3: ( QuestionMark )
            // InternalRegularExpressionParser.g:4045:4: QuestionMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNamedQuestionMarkKeyword_2_0_0_0()); 
            }
            match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNamedQuestionMarkKeyword_2_0_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNamedQuestionMarkKeyword_2_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__NamedAssignment_2_0_0"


    // $ANTLR start "rule__Group__NameAssignment_2_0_2"
    // InternalRegularExpressionParser.g:4056:1: rule__Group__NameAssignment_2_0_2 : ( ruleRegExpIdentifierName ) ;
    public final void rule__Group__NameAssignment_2_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4060:1: ( ( ruleRegExpIdentifierName ) )
            // InternalRegularExpressionParser.g:4061:2: ( ruleRegExpIdentifierName )
            {
            // InternalRegularExpressionParser.g:4061:2: ( ruleRegExpIdentifierName )
            // InternalRegularExpressionParser.g:4062:3: ruleRegExpIdentifierName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNameRegExpIdentifierNameParserRuleCall_2_0_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleRegExpIdentifierName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNameRegExpIdentifierNameParserRuleCall_2_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__NameAssignment_2_0_2"


    // $ANTLR start "rule__Group__NonCapturingAssignment_2_1_0"
    // InternalRegularExpressionParser.g:4071:1: rule__Group__NonCapturingAssignment_2_1_0 : ( ( QuestionMark ) ) ;
    public final void rule__Group__NonCapturingAssignment_2_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4075:1: ( ( ( QuestionMark ) ) )
            // InternalRegularExpressionParser.g:4076:2: ( ( QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:4076:2: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:4077:3: ( QuestionMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_1_0_0()); 
            }
            // InternalRegularExpressionParser.g:4078:3: ( QuestionMark )
            // InternalRegularExpressionParser.g:4079:4: QuestionMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_1_0_0()); 
            }
            match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_1_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__NonCapturingAssignment_2_1_0"


    // $ANTLR start "rule__Group__PatternAssignment_3"
    // InternalRegularExpressionParser.g:4090:1: rule__Group__PatternAssignment_3 : ( ruleDisjunction ) ;
    public final void rule__Group__PatternAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4094:1: ( ( ruleDisjunction ) )
            // InternalRegularExpressionParser.g:4095:2: ( ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:4095:2: ( ruleDisjunction )
            // InternalRegularExpressionParser.g:4096:3: ruleDisjunction
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getPatternDisjunctionParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDisjunction();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getPatternDisjunctionParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__PatternAssignment_3"


    // $ANTLR start "rule__SimpleQuantifier__QuantifierAssignment_0"
    // InternalRegularExpressionParser.g:4105:1: rule__SimpleQuantifier__QuantifierAssignment_0 : ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) ) ;
    public final void rule__SimpleQuantifier__QuantifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4109:1: ( ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) ) )
            // InternalRegularExpressionParser.g:4110:2: ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) )
            {
            // InternalRegularExpressionParser.g:4110:2: ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) )
            // InternalRegularExpressionParser.g:4111:3: ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAlternatives_0_0()); 
            }
            // InternalRegularExpressionParser.g:4112:3: ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 )
            // InternalRegularExpressionParser.g:4112:4: rule__SimpleQuantifier__QuantifierAlternatives_0_0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleQuantifier__QuantifierAlternatives_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleQuantifierAccess().getQuantifierAlternatives_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleQuantifier__QuantifierAssignment_0"


    // $ANTLR start "rule__SimpleQuantifier__NonGreedyAssignment_1"
    // InternalRegularExpressionParser.g:4120:1: rule__SimpleQuantifier__NonGreedyAssignment_1 : ( ( QuestionMark ) ) ;
    public final void rule__SimpleQuantifier__NonGreedyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4124:1: ( ( ( QuestionMark ) ) )
            // InternalRegularExpressionParser.g:4125:2: ( ( QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:4125:2: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:4126:3: ( QuestionMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); 
            }
            // InternalRegularExpressionParser.g:4127:3: ( QuestionMark )
            // InternalRegularExpressionParser.g:4128:4: QuestionMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); 
            }
            match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleQuantifier__NonGreedyAssignment_1"


    // $ANTLR start "rule__ExactQuantifier__MinAssignment_2"
    // InternalRegularExpressionParser.g:4139:1: rule__ExactQuantifier__MinAssignment_2 : ( ruleINT ) ;
    public final void rule__ExactQuantifier__MinAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4143:1: ( ( ruleINT ) )
            // InternalRegularExpressionParser.g:4144:2: ( ruleINT )
            {
            // InternalRegularExpressionParser.g:4144:2: ( ruleINT )
            // InternalRegularExpressionParser.g:4145:3: ruleINT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getMinINTParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleINT();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getMinINTParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__MinAssignment_2"


    // $ANTLR start "rule__ExactQuantifier__MaxAssignment_3_0_1"
    // InternalRegularExpressionParser.g:4154:1: rule__ExactQuantifier__MaxAssignment_3_0_1 : ( ruleINT ) ;
    public final void rule__ExactQuantifier__MaxAssignment_3_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4158:1: ( ( ruleINT ) )
            // InternalRegularExpressionParser.g:4159:2: ( ruleINT )
            {
            // InternalRegularExpressionParser.g:4159:2: ( ruleINT )
            // InternalRegularExpressionParser.g:4160:3: ruleINT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getMaxINTParserRuleCall_3_0_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleINT();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getMaxINTParserRuleCall_3_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__MaxAssignment_3_0_1"


    // $ANTLR start "rule__ExactQuantifier__UnboundedMaxAssignment_3_1"
    // InternalRegularExpressionParser.g:4169:1: rule__ExactQuantifier__UnboundedMaxAssignment_3_1 : ( ( Comma ) ) ;
    public final void rule__ExactQuantifier__UnboundedMaxAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4173:1: ( ( ( Comma ) ) )
            // InternalRegularExpressionParser.g:4174:2: ( ( Comma ) )
            {
            // InternalRegularExpressionParser.g:4174:2: ( ( Comma ) )
            // InternalRegularExpressionParser.g:4175:3: ( Comma )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); 
            }
            // InternalRegularExpressionParser.g:4176:3: ( Comma )
            // InternalRegularExpressionParser.g:4177:4: Comma
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); 
            }
            match(input,Comma,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__UnboundedMaxAssignment_3_1"


    // $ANTLR start "rule__ExactQuantifier__NonGreedyAssignment_5"
    // InternalRegularExpressionParser.g:4188:1: rule__ExactQuantifier__NonGreedyAssignment_5 : ( ( QuestionMark ) ) ;
    public final void rule__ExactQuantifier__NonGreedyAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4192:1: ( ( ( QuestionMark ) ) )
            // InternalRegularExpressionParser.g:4193:2: ( ( QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:4193:2: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:4194:3: ( QuestionMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); 
            }
            // InternalRegularExpressionParser.g:4195:3: ( QuestionMark )
            // InternalRegularExpressionParser.g:4196:4: QuestionMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); 
            }
            match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExactQuantifier__NonGreedyAssignment_5"


    // $ANTLR start "rule__RegularExpressionFlags__FlagsAssignment_1"
    // InternalRegularExpressionParser.g:4207:1: rule__RegularExpressionFlags__FlagsAssignment_1 : ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) ) ;
    public final void rule__RegularExpressionFlags__FlagsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:4211:1: ( ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) ) )
            // InternalRegularExpressionParser.g:4212:2: ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) )
            {
            // InternalRegularExpressionParser.g:4212:2: ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) )
            // InternalRegularExpressionParser.g:4213:3: ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAlternatives_1_0()); 
            }
            // InternalRegularExpressionParser.g:4214:3: ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 )
            // InternalRegularExpressionParser.g:4214:4: rule__RegularExpressionFlags__FlagsAlternatives_1_0
            {
            pushFollow(FOLLOW_2);
            rule__RegularExpressionFlags__FlagsAlternatives_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAlternatives_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RegularExpressionFlags__FlagsAssignment_1"

    // $ANTLR start synpred81_InternalRegularExpressionParser
    public final void synpred81_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:2213:3: ( rule__Term__QuantifierAssignment_1_1 )
        // InternalRegularExpressionParser.g:2213:3: rule__Term__QuantifierAssignment_1_1
        {
        pushFollow(FOLLOW_2);
        rule__Term__QuantifierAssignment_1_1();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred81_InternalRegularExpressionParser

    // $ANTLR start synpred83_InternalRegularExpressionParser
    public final void synpred83_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:2700:3: ( rule__CharacterClass__Group_2__0 )
        // InternalRegularExpressionParser.g:2700:3: rule__CharacterClass__Group_2__0
        {
        pushFollow(FOLLOW_2);
        rule__CharacterClass__Group_2__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred83_InternalRegularExpressionParser

    // $ANTLR start synpred85_InternalRegularExpressionParser
    public final void synpred85_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:2834:3: ( rule__CharacterClassElement__Group_1__0 )
        // InternalRegularExpressionParser.g:2834:3: rule__CharacterClassElement__Group_1__0
        {
        pushFollow(FOLLOW_2);
        rule__CharacterClassElement__Group_1__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred85_InternalRegularExpressionParser

    // Delegated rules

    public final boolean synpred85_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred85_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred83_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred83_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred81_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred81_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA27 dfa27 = new DFA27(this);
    protected DFA31 dfa31 = new DFA31(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\1\3\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3";
    static final String dfa_3s = "\1\4\1\uffff\1\4\1\uffff\3\4\1\0\2\4";
    static final String dfa_4s = "\1\51\1\uffff\1\51\1\uffff\3\51\1\0\2\51";
    static final String dfa_5s = "\1\uffff\1\1\1\uffff\1\2\6\uffff";
    static final String dfa_6s = "\7\uffff\1\0\2\uffff}>";
    static final String[] dfa_7s = {
            "\4\3\2\1\10\3\1\1\3\3\1\uffff\1\2\7\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\3\1\uffff\2\3",
            "",
            "\22\3\1\uffff\10\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\4\1\uffff\2\3",
            "",
            "\6\3\1\6\13\3\1\uffff\2\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\5\1\uffff\2\3",
            "\6\3\1\6\13\3\1\uffff\2\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\5\1\uffff\2\3",
            "\22\3\1\uffff\2\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\10\1\uffff\2\3",
            "\1\uffff",
            "\22\3\1\uffff\2\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\11\1\uffff\2\3",
            "\22\3\1\uffff\2\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\11\1\uffff\2\3"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "2213:2: ( rule__Term__QuantifierAssignment_1_1 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_7 = input.LA(1);

                         
                        int index27_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred81_InternalRegularExpressionParser()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index27_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 27, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\43\uffff";
    static final String dfa_9s = "\1\2\42\uffff";
    static final String dfa_10s = "\2\4\1\uffff\37\0\1\uffff";
    static final String dfa_11s = "\2\51\1\uffff\37\0\1\uffff";
    static final String dfa_12s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_13s = "\3\uffff\1\24\1\34\1\12\1\30\1\26\1\5\1\21\1\1\1\10\1\32\1\14\1\16\1\36\1\22\1\6\1\7\1\31\1\13\1\35\1\0\1\20\1\4\1\25\1\27\1\11\1\33\1\15\1\17\1\2\1\23\1\3\1\uffff}>";
    static final String[] dfa_14s = {
            "\7\2\1\1\12\2\1\uffff\4\2\1\uffff\3\2\1\uffff\2\2\1\uffff\2\2\1\uffff\1\2\1\uffff\2\2",
            "\1\16\1\21\1\26\1\27\1\23\1\24\1\13\1\17\1\22\1\34\1\15\1\35\1\14\1\36\1\25\1\30\1\2\1\20\1\uffff\1\31\1\33\1\32\1\4\1\uffff\1\12\1\5\1\6\1\uffff\1\7\1\10\1\uffff\1\3\1\11\1\uffff\1\41\1\uffff\1\40\1\37",
            "",
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

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "2834:2: ( rule__CharacterClassElement__Group_1__0 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA31_22 = input.LA(1);

                         
                        int index31_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_22);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA31_10 = input.LA(1);

                         
                        int index31_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_10);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA31_31 = input.LA(1);

                         
                        int index31_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_31);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA31_33 = input.LA(1);

                         
                        int index31_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_33);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA31_24 = input.LA(1);

                         
                        int index31_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_24);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA31_8 = input.LA(1);

                         
                        int index31_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA31_17 = input.LA(1);

                         
                        int index31_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_17);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA31_18 = input.LA(1);

                         
                        int index31_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_18);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA31_11 = input.LA(1);

                         
                        int index31_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA31_27 = input.LA(1);

                         
                        int index31_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_27);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA31_5 = input.LA(1);

                         
                        int index31_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_5);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA31_20 = input.LA(1);

                         
                        int index31_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_20);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA31_13 = input.LA(1);

                         
                        int index31_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA31_29 = input.LA(1);

                         
                        int index31_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_29);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA31_14 = input.LA(1);

                         
                        int index31_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA31_30 = input.LA(1);

                         
                        int index31_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_30);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA31_23 = input.LA(1);

                         
                        int index31_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_23);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA31_9 = input.LA(1);

                         
                        int index31_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_9);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA31_16 = input.LA(1);

                         
                        int index31_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_16);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA31_32 = input.LA(1);

                         
                        int index31_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_32);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA31_3 = input.LA(1);

                         
                        int index31_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_3);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA31_25 = input.LA(1);

                         
                        int index31_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_25);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA31_7 = input.LA(1);

                         
                        int index31_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_7);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA31_26 = input.LA(1);

                         
                        int index31_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_26);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA31_6 = input.LA(1);

                         
                        int index31_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_6);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA31_19 = input.LA(1);

                         
                        int index31_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_19);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA31_12 = input.LA(1);

                         
                        int index31_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_12);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA31_28 = input.LA(1);

                         
                        int index31_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_28);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA31_4 = input.LA(1);

                         
                        int index31_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_4);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA31_21 = input.LA(1);

                         
                        int index31_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_21);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA31_15 = input.LA(1);

                         
                        int index31_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred85_InternalRegularExpressionParser()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index31_15);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 31, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000035B7FBBDC70L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000010200000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000035B7EBBDC70L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000035B7EBBDC72L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000840300L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x000000000C200060L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000018010L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000035B77BFFFF0L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000035B77AFFFF2L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000035B77AFFFF0L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000035B7FBFDC70L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000010200400020L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000014200400020L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000014200400022L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000002000400L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000010200000002L});

}