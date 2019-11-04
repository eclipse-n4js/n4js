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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ExclamationMark", "DollarSign", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "EqualsSign", "QuestionMark", "LeftSquareBracket", "RightSquareBracket", "CircumflexAccent", "LeftCurlyBracket", "VerticalLine", "RightCurlyBracket", "RULE_WORD_BOUNDARY", "RULE_NOT_WORD_BOUNDARY", "RULE_CHARACTER_CLASS_ESCAPE", "RULE_CONTROL_ESCAPE", "RULE_CONTROL_LETTER_ESCAPE", "RULE_HEX_DIGIT", "RULE_HEX_ESCAPE", "RULE_UNICODE_ESCAPE", "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT", "RULE_DECIMAL_ESCAPE", "RULE_IDENTITY_ESCAPE", "RULE_UNICODE_DIGIT_FRAGMENT", "RULE_UNICODE_DIGIT", "RULE_UNICODE_LETTER_FRAGMENT", "RULE_UNICODE_LETTER", "RULE_PATTERN_CHARACTER_NO_DASH", "RULE_DECIMAL_DIGIT_FRAGMENT", "RULE_ZWJ", "RULE_ZWNJ", "RULE_BOM", "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT", "RULE_WHITESPACE_FRAGMENT", "RULE_LINE_TERMINATOR_FRAGMENT", "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT", "RULE_SL_COMMENT_FRAGMENT", "RULE_ML_COMMENT_FRAGMENT", "RULE_UNICODE_COMBINING_MARK_FRAGMENT", "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT", "RULE_ANY_OTHER"
    };
    public static final int RULE_WHITESPACE_FRAGMENT=44;
    public static final int LeftParenthesis=6;
    public static final int RULE_HEX_ESCAPE=29;
    public static final int RightSquareBracket=18;
    public static final int ExclamationMark=4;
    public static final int RULE_CONTROL_LETTER_ESCAPE=27;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=46;
    public static final int RightParenthesis=7;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=49;
    public static final int RULE_ZWNJ=41;
    public static final int VerticalLine=21;
    public static final int PlusSign=9;
    public static final int LeftSquareBracket=17;
    public static final int RULE_DECIMAL_ESCAPE=32;
    public static final int RULE_ML_COMMENT_FRAGMENT=48;
    public static final int RULE_PATTERN_CHARACTER_NO_DASH=38;
    public static final int RULE_WORD_BOUNDARY=23;
    public static final int RULE_UNICODE_ESCAPE=30;
    public static final int Comma=10;
    public static final int EqualsSign=15;
    public static final int RULE_ZWJ=40;
    public static final int RULE_SL_COMMENT_FRAGMENT=47;
    public static final int HyphenMinus=11;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=34;
    public static final int RULE_UNICODE_LETTER=37;
    public static final int RULE_CONTROL_ESCAPE=26;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=43;
    public static final int Solidus=13;
    public static final int Colon=14;
    public static final int RightCurlyBracket=22;
    public static final int RULE_CHARACTER_CLASS_ESCAPE=25;
    public static final int EOF=-1;
    public static final int Asterisk=8;
    public static final int FullStop=12;
    public static final int RULE_UNICODE_DIGIT=35;
    public static final int RULE_BOM=42;
    public static final int LeftCurlyBracket=20;
    public static final int RULE_ANY_OTHER=51;
    public static final int CircumflexAccent=19;
    public static final int RULE_NOT_WORD_BOUNDARY=24;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=45;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=36;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=39;
    public static final int QuestionMark=16;
    public static final int DollarSign=5;
    public static final int RULE_HEX_DIGIT=28;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=31;
    public static final int RULE_IDENTITY_ESCAPE=33;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=50;

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
    		tokenNameToValue.put("EqualsSign", "'='");
    		tokenNameToValue.put("QuestionMark", "'?'");
    		tokenNameToValue.put("LeftSquareBracket", "'['");
    		tokenNameToValue.put("RightSquareBracket", "']'");
    		tokenNameToValue.put("CircumflexAccent", "'^'");
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
    // InternalRegularExpressionParser.g:82:1: entryRuleRegularExpressionLiteral : ruleRegularExpressionLiteral EOF ;
    public final void entryRuleRegularExpressionLiteral() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:83:1: ( ruleRegularExpressionLiteral EOF )
            // InternalRegularExpressionParser.g:84:1: ruleRegularExpressionLiteral EOF
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
    // InternalRegularExpressionParser.g:91:1: ruleRegularExpressionLiteral : ( ( rule__RegularExpressionLiteral__Group__0 ) ) ;
    public final void ruleRegularExpressionLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:95:2: ( ( ( rule__RegularExpressionLiteral__Group__0 ) ) )
            // InternalRegularExpressionParser.g:96:2: ( ( rule__RegularExpressionLiteral__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:96:2: ( ( rule__RegularExpressionLiteral__Group__0 ) )
            // InternalRegularExpressionParser.g:97:3: ( rule__RegularExpressionLiteral__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:98:3: ( rule__RegularExpressionLiteral__Group__0 )
            // InternalRegularExpressionParser.g:98:4: rule__RegularExpressionLiteral__Group__0
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
    // InternalRegularExpressionParser.g:107:1: entryRuleRegularExpressionBody : ruleRegularExpressionBody EOF ;
    public final void entryRuleRegularExpressionBody() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:108:1: ( ruleRegularExpressionBody EOF )
            // InternalRegularExpressionParser.g:109:1: ruleRegularExpressionBody EOF
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
    // InternalRegularExpressionParser.g:116:1: ruleRegularExpressionBody : ( ( rule__RegularExpressionBody__PatternAssignment ) ) ;
    public final void ruleRegularExpressionBody() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:120:2: ( ( ( rule__RegularExpressionBody__PatternAssignment ) ) )
            // InternalRegularExpressionParser.g:121:2: ( ( rule__RegularExpressionBody__PatternAssignment ) )
            {
            // InternalRegularExpressionParser.g:121:2: ( ( rule__RegularExpressionBody__PatternAssignment ) )
            // InternalRegularExpressionParser.g:122:3: ( rule__RegularExpressionBody__PatternAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionBodyAccess().getPatternAssignment()); 
            }
            // InternalRegularExpressionParser.g:123:3: ( rule__RegularExpressionBody__PatternAssignment )
            // InternalRegularExpressionParser.g:123:4: rule__RegularExpressionBody__PatternAssignment
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
    // InternalRegularExpressionParser.g:132:1: entryRuleDisjunction : ruleDisjunction EOF ;
    public final void entryRuleDisjunction() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:133:1: ( ruleDisjunction EOF )
            // InternalRegularExpressionParser.g:134:1: ruleDisjunction EOF
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
    // InternalRegularExpressionParser.g:141:1: ruleDisjunction : ( ( rule__Disjunction__Alternatives ) ) ;
    public final void ruleDisjunction() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:145:2: ( ( ( rule__Disjunction__Alternatives ) ) )
            // InternalRegularExpressionParser.g:146:2: ( ( rule__Disjunction__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:146:2: ( ( rule__Disjunction__Alternatives ) )
            // InternalRegularExpressionParser.g:147:3: ( rule__Disjunction__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:148:3: ( rule__Disjunction__Alternatives )
            // InternalRegularExpressionParser.g:148:4: rule__Disjunction__Alternatives
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
    // InternalRegularExpressionParser.g:157:1: entryRuleAlternative : ruleAlternative EOF ;
    public final void entryRuleAlternative() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:158:1: ( ruleAlternative EOF )
            // InternalRegularExpressionParser.g:159:1: ruleAlternative EOF
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
    // InternalRegularExpressionParser.g:166:1: ruleAlternative : ( ( rule__Alternative__Group__0 ) ) ;
    public final void ruleAlternative() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:170:2: ( ( ( rule__Alternative__Group__0 ) ) )
            // InternalRegularExpressionParser.g:171:2: ( ( rule__Alternative__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:171:2: ( ( rule__Alternative__Group__0 ) )
            // InternalRegularExpressionParser.g:172:3: ( rule__Alternative__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:173:3: ( rule__Alternative__Group__0 )
            // InternalRegularExpressionParser.g:173:4: rule__Alternative__Group__0
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
    // InternalRegularExpressionParser.g:182:1: entryRuleTerm : ruleTerm EOF ;
    public final void entryRuleTerm() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:183:1: ( ruleTerm EOF )
            // InternalRegularExpressionParser.g:184:1: ruleTerm EOF
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
    // InternalRegularExpressionParser.g:191:1: ruleTerm : ( ( rule__Term__Alternatives ) ) ;
    public final void ruleTerm() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:195:2: ( ( ( rule__Term__Alternatives ) ) )
            // InternalRegularExpressionParser.g:196:2: ( ( rule__Term__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:196:2: ( ( rule__Term__Alternatives ) )
            // InternalRegularExpressionParser.g:197:3: ( rule__Term__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTermAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:198:3: ( rule__Term__Alternatives )
            // InternalRegularExpressionParser.g:198:4: rule__Term__Alternatives
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
    // InternalRegularExpressionParser.g:207:1: entryRuleAssertion : ruleAssertion EOF ;
    public final void entryRuleAssertion() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:208:1: ( ruleAssertion EOF )
            // InternalRegularExpressionParser.g:209:1: ruleAssertion EOF
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
    // InternalRegularExpressionParser.g:216:1: ruleAssertion : ( ( rule__Assertion__Alternatives ) ) ;
    public final void ruleAssertion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:220:2: ( ( ( rule__Assertion__Alternatives ) ) )
            // InternalRegularExpressionParser.g:221:2: ( ( rule__Assertion__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:221:2: ( ( rule__Assertion__Alternatives ) )
            // InternalRegularExpressionParser.g:222:3: ( rule__Assertion__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssertionAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:223:3: ( rule__Assertion__Alternatives )
            // InternalRegularExpressionParser.g:223:4: rule__Assertion__Alternatives
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
    // InternalRegularExpressionParser.g:232:1: entryRuleLineStart : ruleLineStart EOF ;
    public final void entryRuleLineStart() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:233:1: ( ruleLineStart EOF )
            // InternalRegularExpressionParser.g:234:1: ruleLineStart EOF
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
    // InternalRegularExpressionParser.g:241:1: ruleLineStart : ( ( rule__LineStart__Group__0 ) ) ;
    public final void ruleLineStart() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:245:2: ( ( ( rule__LineStart__Group__0 ) ) )
            // InternalRegularExpressionParser.g:246:2: ( ( rule__LineStart__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:246:2: ( ( rule__LineStart__Group__0 ) )
            // InternalRegularExpressionParser.g:247:3: ( rule__LineStart__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineStartAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:248:3: ( rule__LineStart__Group__0 )
            // InternalRegularExpressionParser.g:248:4: rule__LineStart__Group__0
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
    // InternalRegularExpressionParser.g:257:1: entryRuleLineEnd : ruleLineEnd EOF ;
    public final void entryRuleLineEnd() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:258:1: ( ruleLineEnd EOF )
            // InternalRegularExpressionParser.g:259:1: ruleLineEnd EOF
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
    // InternalRegularExpressionParser.g:266:1: ruleLineEnd : ( ( rule__LineEnd__Group__0 ) ) ;
    public final void ruleLineEnd() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:270:2: ( ( ( rule__LineEnd__Group__0 ) ) )
            // InternalRegularExpressionParser.g:271:2: ( ( rule__LineEnd__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:271:2: ( ( rule__LineEnd__Group__0 ) )
            // InternalRegularExpressionParser.g:272:3: ( rule__LineEnd__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineEndAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:273:3: ( rule__LineEnd__Group__0 )
            // InternalRegularExpressionParser.g:273:4: rule__LineEnd__Group__0
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
    // InternalRegularExpressionParser.g:282:1: entryRuleWordBoundary : ruleWordBoundary EOF ;
    public final void entryRuleWordBoundary() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:283:1: ( ruleWordBoundary EOF )
            // InternalRegularExpressionParser.g:284:1: ruleWordBoundary EOF
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
    // InternalRegularExpressionParser.g:291:1: ruleWordBoundary : ( ( rule__WordBoundary__Group__0 ) ) ;
    public final void ruleWordBoundary() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:295:2: ( ( ( rule__WordBoundary__Group__0 ) ) )
            // InternalRegularExpressionParser.g:296:2: ( ( rule__WordBoundary__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:296:2: ( ( rule__WordBoundary__Group__0 ) )
            // InternalRegularExpressionParser.g:297:3: ( rule__WordBoundary__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:298:3: ( rule__WordBoundary__Group__0 )
            // InternalRegularExpressionParser.g:298:4: rule__WordBoundary__Group__0
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
    // InternalRegularExpressionParser.g:307:1: entryRuleLookAhead : ruleLookAhead EOF ;
    public final void entryRuleLookAhead() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:308:1: ( ruleLookAhead EOF )
            // InternalRegularExpressionParser.g:309:1: ruleLookAhead EOF
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
    // InternalRegularExpressionParser.g:316:1: ruleLookAhead : ( ( rule__LookAhead__Group__0 ) ) ;
    public final void ruleLookAhead() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:320:2: ( ( ( rule__LookAhead__Group__0 ) ) )
            // InternalRegularExpressionParser.g:321:2: ( ( rule__LookAhead__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:321:2: ( ( rule__LookAhead__Group__0 ) )
            // InternalRegularExpressionParser.g:322:3: ( rule__LookAhead__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:323:3: ( rule__LookAhead__Group__0 )
            // InternalRegularExpressionParser.g:323:4: rule__LookAhead__Group__0
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
    // InternalRegularExpressionParser.g:332:1: entryRuleAtom : ruleAtom EOF ;
    public final void entryRuleAtom() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:333:1: ( ruleAtom EOF )
            // InternalRegularExpressionParser.g:334:1: ruleAtom EOF
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
    // InternalRegularExpressionParser.g:341:1: ruleAtom : ( ( rule__Atom__Alternatives ) ) ;
    public final void ruleAtom() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:345:2: ( ( ( rule__Atom__Alternatives ) ) )
            // InternalRegularExpressionParser.g:346:2: ( ( rule__Atom__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:346:2: ( ( rule__Atom__Alternatives ) )
            // InternalRegularExpressionParser.g:347:3: ( rule__Atom__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:348:3: ( rule__Atom__Alternatives )
            // InternalRegularExpressionParser.g:348:4: rule__Atom__Alternatives
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
    // InternalRegularExpressionParser.g:357:1: entryRulePatternCharacter : rulePatternCharacter EOF ;
    public final void entryRulePatternCharacter() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:358:1: ( rulePatternCharacter EOF )
            // InternalRegularExpressionParser.g:359:1: rulePatternCharacter EOF
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
    // InternalRegularExpressionParser.g:366:1: rulePatternCharacter : ( ( rule__PatternCharacter__ValueAssignment ) ) ;
    public final void rulePatternCharacter() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:370:2: ( ( ( rule__PatternCharacter__ValueAssignment ) ) )
            // InternalRegularExpressionParser.g:371:2: ( ( rule__PatternCharacter__ValueAssignment ) )
            {
            // InternalRegularExpressionParser.g:371:2: ( ( rule__PatternCharacter__ValueAssignment ) )
            // InternalRegularExpressionParser.g:372:3: ( rule__PatternCharacter__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPatternCharacterAccess().getValueAssignment()); 
            }
            // InternalRegularExpressionParser.g:373:3: ( rule__PatternCharacter__ValueAssignment )
            // InternalRegularExpressionParser.g:373:4: rule__PatternCharacter__ValueAssignment
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
    // InternalRegularExpressionParser.g:382:1: entryRuleWildcard : ruleWildcard EOF ;
    public final void entryRuleWildcard() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:383:1: ( ruleWildcard EOF )
            // InternalRegularExpressionParser.g:384:1: ruleWildcard EOF
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
    // InternalRegularExpressionParser.g:391:1: ruleWildcard : ( ( rule__Wildcard__Group__0 ) ) ;
    public final void ruleWildcard() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:395:2: ( ( ( rule__Wildcard__Group__0 ) ) )
            // InternalRegularExpressionParser.g:396:2: ( ( rule__Wildcard__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:396:2: ( ( rule__Wildcard__Group__0 ) )
            // InternalRegularExpressionParser.g:397:3: ( rule__Wildcard__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWildcardAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:398:3: ( rule__Wildcard__Group__0 )
            // InternalRegularExpressionParser.g:398:4: rule__Wildcard__Group__0
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
    // InternalRegularExpressionParser.g:407:1: entryRuleAtomEscape : ruleAtomEscape EOF ;
    public final void entryRuleAtomEscape() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:408:1: ( ruleAtomEscape EOF )
            // InternalRegularExpressionParser.g:409:1: ruleAtomEscape EOF
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
    // InternalRegularExpressionParser.g:416:1: ruleAtomEscape : ( ( rule__AtomEscape__Alternatives ) ) ;
    public final void ruleAtomEscape() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:420:2: ( ( ( rule__AtomEscape__Alternatives ) ) )
            // InternalRegularExpressionParser.g:421:2: ( ( rule__AtomEscape__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:421:2: ( ( rule__AtomEscape__Alternatives ) )
            // InternalRegularExpressionParser.g:422:3: ( rule__AtomEscape__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomEscapeAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:423:3: ( rule__AtomEscape__Alternatives )
            // InternalRegularExpressionParser.g:423:4: rule__AtomEscape__Alternatives
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
    // InternalRegularExpressionParser.g:432:1: entryRuleCharacterClassEscapeSequence : ruleCharacterClassEscapeSequence EOF ;
    public final void entryRuleCharacterClassEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:433:1: ( ruleCharacterClassEscapeSequence EOF )
            // InternalRegularExpressionParser.g:434:1: ruleCharacterClassEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:441:1: ruleCharacterClassEscapeSequence : ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleCharacterClassEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:445:2: ( ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:446:2: ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:446:2: ( ( rule__CharacterClassEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:447:3: ( rule__CharacterClassEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:448:3: ( rule__CharacterClassEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:448:4: rule__CharacterClassEscapeSequence__SequenceAssignment
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
    // InternalRegularExpressionParser.g:457:1: entryRuleCharacterEscapeSequence : ruleCharacterEscapeSequence EOF ;
    public final void entryRuleCharacterEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:458:1: ( ruleCharacterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:459:1: ruleCharacterEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:466:1: ruleCharacterEscapeSequence : ( ( rule__CharacterEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleCharacterEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:470:2: ( ( ( rule__CharacterEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:471:2: ( ( rule__CharacterEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:471:2: ( ( rule__CharacterEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:472:3: ( rule__CharacterEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:473:3: ( rule__CharacterEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:473:4: rule__CharacterEscapeSequence__SequenceAssignment
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
    // InternalRegularExpressionParser.g:482:1: entryRuleControlLetterEscapeSequence : ruleControlLetterEscapeSequence EOF ;
    public final void entryRuleControlLetterEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:483:1: ( ruleControlLetterEscapeSequence EOF )
            // InternalRegularExpressionParser.g:484:1: ruleControlLetterEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:491:1: ruleControlLetterEscapeSequence : ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleControlLetterEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:495:2: ( ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:496:2: ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:496:2: ( ( rule__ControlLetterEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:497:3: ( rule__ControlLetterEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:498:3: ( rule__ControlLetterEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:498:4: rule__ControlLetterEscapeSequence__SequenceAssignment
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
    // InternalRegularExpressionParser.g:507:1: entryRuleHexEscapeSequence : ruleHexEscapeSequence EOF ;
    public final void entryRuleHexEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:508:1: ( ruleHexEscapeSequence EOF )
            // InternalRegularExpressionParser.g:509:1: ruleHexEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:516:1: ruleHexEscapeSequence : ( ( rule__HexEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleHexEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:520:2: ( ( ( rule__HexEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:521:2: ( ( rule__HexEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:521:2: ( ( rule__HexEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:522:3: ( rule__HexEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getHexEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:523:3: ( rule__HexEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:523:4: rule__HexEscapeSequence__SequenceAssignment
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
    // InternalRegularExpressionParser.g:532:1: entryRuleUnicodeEscapeSequence : ruleUnicodeEscapeSequence EOF ;
    public final void entryRuleUnicodeEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:533:1: ( ruleUnicodeEscapeSequence EOF )
            // InternalRegularExpressionParser.g:534:1: ruleUnicodeEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:541:1: ruleUnicodeEscapeSequence : ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleUnicodeEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:545:2: ( ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:546:2: ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:546:2: ( ( rule__UnicodeEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:547:3: ( rule__UnicodeEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:548:3: ( rule__UnicodeEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:548:4: rule__UnicodeEscapeSequence__SequenceAssignment
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
    // InternalRegularExpressionParser.g:557:1: entryRuleIdentityEscapeSequence : ruleIdentityEscapeSequence EOF ;
    public final void entryRuleIdentityEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:558:1: ( ruleIdentityEscapeSequence EOF )
            // InternalRegularExpressionParser.g:559:1: ruleIdentityEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:566:1: ruleIdentityEscapeSequence : ( ( rule__IdentityEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleIdentityEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:570:2: ( ( ( rule__IdentityEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:571:2: ( ( rule__IdentityEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:571:2: ( ( rule__IdentityEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:572:3: ( rule__IdentityEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:573:3: ( rule__IdentityEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:573:4: rule__IdentityEscapeSequence__SequenceAssignment
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
    // InternalRegularExpressionParser.g:582:1: entryRuleDecimalEscapeSequence : ruleDecimalEscapeSequence EOF ;
    public final void entryRuleDecimalEscapeSequence() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:583:1: ( ruleDecimalEscapeSequence EOF )
            // InternalRegularExpressionParser.g:584:1: ruleDecimalEscapeSequence EOF
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
    // InternalRegularExpressionParser.g:591:1: ruleDecimalEscapeSequence : ( ( rule__DecimalEscapeSequence__SequenceAssignment ) ) ;
    public final void ruleDecimalEscapeSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:595:2: ( ( ( rule__DecimalEscapeSequence__SequenceAssignment ) ) )
            // InternalRegularExpressionParser.g:596:2: ( ( rule__DecimalEscapeSequence__SequenceAssignment ) )
            {
            // InternalRegularExpressionParser.g:596:2: ( ( rule__DecimalEscapeSequence__SequenceAssignment ) )
            // InternalRegularExpressionParser.g:597:3: ( rule__DecimalEscapeSequence__SequenceAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceAssignment()); 
            }
            // InternalRegularExpressionParser.g:598:3: ( rule__DecimalEscapeSequence__SequenceAssignment )
            // InternalRegularExpressionParser.g:598:4: rule__DecimalEscapeSequence__SequenceAssignment
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
    // InternalRegularExpressionParser.g:607:1: entryRuleCharacterClass : ruleCharacterClass EOF ;
    public final void entryRuleCharacterClass() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:608:1: ( ruleCharacterClass EOF )
            // InternalRegularExpressionParser.g:609:1: ruleCharacterClass EOF
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
    // InternalRegularExpressionParser.g:616:1: ruleCharacterClass : ( ( rule__CharacterClass__Group__0 ) ) ;
    public final void ruleCharacterClass() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:620:2: ( ( ( rule__CharacterClass__Group__0 ) ) )
            // InternalRegularExpressionParser.g:621:2: ( ( rule__CharacterClass__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:621:2: ( ( rule__CharacterClass__Group__0 ) )
            // InternalRegularExpressionParser.g:622:3: ( rule__CharacterClass__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:623:3: ( rule__CharacterClass__Group__0 )
            // InternalRegularExpressionParser.g:623:4: rule__CharacterClass__Group__0
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
    // InternalRegularExpressionParser.g:632:1: entryRuleCharacterClassElement : ruleCharacterClassElement EOF ;
    public final void entryRuleCharacterClassElement() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:633:1: ( ruleCharacterClassElement EOF )
            // InternalRegularExpressionParser.g:634:1: ruleCharacterClassElement EOF
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
    // InternalRegularExpressionParser.g:641:1: ruleCharacterClassElement : ( ( rule__CharacterClassElement__Group__0 ) ) ;
    public final void ruleCharacterClassElement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:645:2: ( ( ( rule__CharacterClassElement__Group__0 ) ) )
            // InternalRegularExpressionParser.g:646:2: ( ( rule__CharacterClassElement__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:646:2: ( ( rule__CharacterClassElement__Group__0 ) )
            // InternalRegularExpressionParser.g:647:3: ( rule__CharacterClassElement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:648:3: ( rule__CharacterClassElement__Group__0 )
            // InternalRegularExpressionParser.g:648:4: rule__CharacterClassElement__Group__0
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
    // InternalRegularExpressionParser.g:657:1: entryRuleCharacterClassAtom : ruleCharacterClassAtom EOF ;
    public final void entryRuleCharacterClassAtom() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:658:1: ( ruleCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:659:1: ruleCharacterClassAtom EOF
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
    // InternalRegularExpressionParser.g:666:1: ruleCharacterClassAtom : ( ( rule__CharacterClassAtom__Alternatives ) ) ;
    public final void ruleCharacterClassAtom() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:670:2: ( ( ( rule__CharacterClassAtom__Alternatives ) ) )
            // InternalRegularExpressionParser.g:671:2: ( ( rule__CharacterClassAtom__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:671:2: ( ( rule__CharacterClassAtom__Alternatives ) )
            // InternalRegularExpressionParser.g:672:3: ( rule__CharacterClassAtom__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAtomAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:673:3: ( rule__CharacterClassAtom__Alternatives )
            // InternalRegularExpressionParser.g:673:4: rule__CharacterClassAtom__Alternatives
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
    // InternalRegularExpressionParser.g:682:1: entryRuleEscapedCharacterClassAtom : ruleEscapedCharacterClassAtom EOF ;
    public final void entryRuleEscapedCharacterClassAtom() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:683:1: ( ruleEscapedCharacterClassAtom EOF )
            // InternalRegularExpressionParser.g:684:1: ruleEscapedCharacterClassAtom EOF
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
    // InternalRegularExpressionParser.g:691:1: ruleEscapedCharacterClassAtom : ( ( rule__EscapedCharacterClassAtom__Alternatives ) ) ;
    public final void ruleEscapedCharacterClassAtom() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:695:2: ( ( ( rule__EscapedCharacterClassAtom__Alternatives ) ) )
            // InternalRegularExpressionParser.g:696:2: ( ( rule__EscapedCharacterClassAtom__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:696:2: ( ( rule__EscapedCharacterClassAtom__Alternatives ) )
            // InternalRegularExpressionParser.g:697:3: ( rule__EscapedCharacterClassAtom__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getEscapedCharacterClassAtomAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:698:3: ( rule__EscapedCharacterClassAtom__Alternatives )
            // InternalRegularExpressionParser.g:698:4: rule__EscapedCharacterClassAtom__Alternatives
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
    // InternalRegularExpressionParser.g:707:1: entryRuleBackspace : ruleBackspace EOF ;
    public final void entryRuleBackspace() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:708:1: ( ruleBackspace EOF )
            // InternalRegularExpressionParser.g:709:1: ruleBackspace EOF
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
    // InternalRegularExpressionParser.g:716:1: ruleBackspace : ( ( rule__Backspace__Group__0 ) ) ;
    public final void ruleBackspace() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:720:2: ( ( ( rule__Backspace__Group__0 ) ) )
            // InternalRegularExpressionParser.g:721:2: ( ( rule__Backspace__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:721:2: ( ( rule__Backspace__Group__0 ) )
            // InternalRegularExpressionParser.g:722:3: ( rule__Backspace__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBackspaceAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:723:3: ( rule__Backspace__Group__0 )
            // InternalRegularExpressionParser.g:723:4: rule__Backspace__Group__0
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
    // InternalRegularExpressionParser.g:732:1: entryRuleGroup : ruleGroup EOF ;
    public final void entryRuleGroup() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:733:1: ( ruleGroup EOF )
            // InternalRegularExpressionParser.g:734:1: ruleGroup EOF
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
    // InternalRegularExpressionParser.g:741:1: ruleGroup : ( ( rule__Group__Group__0 ) ) ;
    public final void ruleGroup() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:745:2: ( ( ( rule__Group__Group__0 ) ) )
            // InternalRegularExpressionParser.g:746:2: ( ( rule__Group__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:746:2: ( ( rule__Group__Group__0 ) )
            // InternalRegularExpressionParser.g:747:3: ( rule__Group__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:748:3: ( rule__Group__Group__0 )
            // InternalRegularExpressionParser.g:748:4: rule__Group__Group__0
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


    // $ANTLR start "entryRuleQuantifier"
    // InternalRegularExpressionParser.g:757:1: entryRuleQuantifier : ruleQuantifier EOF ;
    public final void entryRuleQuantifier() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:758:1: ( ruleQuantifier EOF )
            // InternalRegularExpressionParser.g:759:1: ruleQuantifier EOF
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
    // InternalRegularExpressionParser.g:766:1: ruleQuantifier : ( ( rule__Quantifier__Alternatives ) ) ;
    public final void ruleQuantifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:770:2: ( ( ( rule__Quantifier__Alternatives ) ) )
            // InternalRegularExpressionParser.g:771:2: ( ( rule__Quantifier__Alternatives ) )
            {
            // InternalRegularExpressionParser.g:771:2: ( ( rule__Quantifier__Alternatives ) )
            // InternalRegularExpressionParser.g:772:3: ( rule__Quantifier__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQuantifierAccess().getAlternatives()); 
            }
            // InternalRegularExpressionParser.g:773:3: ( rule__Quantifier__Alternatives )
            // InternalRegularExpressionParser.g:773:4: rule__Quantifier__Alternatives
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
    // InternalRegularExpressionParser.g:782:1: entryRuleSimpleQuantifier : ruleSimpleQuantifier EOF ;
    public final void entryRuleSimpleQuantifier() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:783:1: ( ruleSimpleQuantifier EOF )
            // InternalRegularExpressionParser.g:784:1: ruleSimpleQuantifier EOF
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
    // InternalRegularExpressionParser.g:791:1: ruleSimpleQuantifier : ( ( rule__SimpleQuantifier__Group__0 ) ) ;
    public final void ruleSimpleQuantifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:795:2: ( ( ( rule__SimpleQuantifier__Group__0 ) ) )
            // InternalRegularExpressionParser.g:796:2: ( ( rule__SimpleQuantifier__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:796:2: ( ( rule__SimpleQuantifier__Group__0 ) )
            // InternalRegularExpressionParser.g:797:3: ( rule__SimpleQuantifier__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:798:3: ( rule__SimpleQuantifier__Group__0 )
            // InternalRegularExpressionParser.g:798:4: rule__SimpleQuantifier__Group__0
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
    // InternalRegularExpressionParser.g:807:1: entryRuleExactQuantifier : ruleExactQuantifier EOF ;
    public final void entryRuleExactQuantifier() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:808:1: ( ruleExactQuantifier EOF )
            // InternalRegularExpressionParser.g:809:1: ruleExactQuantifier EOF
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
    // InternalRegularExpressionParser.g:816:1: ruleExactQuantifier : ( ( rule__ExactQuantifier__Group__0 ) ) ;
    public final void ruleExactQuantifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:820:2: ( ( ( rule__ExactQuantifier__Group__0 ) ) )
            // InternalRegularExpressionParser.g:821:2: ( ( rule__ExactQuantifier__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:821:2: ( ( rule__ExactQuantifier__Group__0 ) )
            // InternalRegularExpressionParser.g:822:3: ( rule__ExactQuantifier__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:823:3: ( rule__ExactQuantifier__Group__0 )
            // InternalRegularExpressionParser.g:823:4: rule__ExactQuantifier__Group__0
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
    // InternalRegularExpressionParser.g:832:1: entryRuleRegularExpressionFlags : ruleRegularExpressionFlags EOF ;
    public final void entryRuleRegularExpressionFlags() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:833:1: ( ruleRegularExpressionFlags EOF )
            // InternalRegularExpressionParser.g:834:1: ruleRegularExpressionFlags EOF
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
    // InternalRegularExpressionParser.g:841:1: ruleRegularExpressionFlags : ( ( rule__RegularExpressionFlags__Group__0 ) ) ;
    public final void ruleRegularExpressionFlags() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:845:2: ( ( ( rule__RegularExpressionFlags__Group__0 ) ) )
            // InternalRegularExpressionParser.g:846:2: ( ( rule__RegularExpressionFlags__Group__0 ) )
            {
            // InternalRegularExpressionParser.g:846:2: ( ( rule__RegularExpressionFlags__Group__0 ) )
            // InternalRegularExpressionParser.g:847:3: ( rule__RegularExpressionFlags__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getGroup()); 
            }
            // InternalRegularExpressionParser.g:848:3: ( rule__RegularExpressionFlags__Group__0 )
            // InternalRegularExpressionParser.g:848:4: rule__RegularExpressionFlags__Group__0
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
    // InternalRegularExpressionParser.g:857:1: entryRuleINT : ruleINT EOF ;
    public final void entryRuleINT() throws RecognitionException {
        try {
            // InternalRegularExpressionParser.g:858:1: ( ruleINT EOF )
            // InternalRegularExpressionParser.g:859:1: ruleINT EOF
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
    // InternalRegularExpressionParser.g:866:1: ruleINT : ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) ) ;
    public final void ruleINT() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:870:2: ( ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) ) )
            // InternalRegularExpressionParser.g:871:2: ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) )
            {
            // InternalRegularExpressionParser.g:871:2: ( ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* ) )
            // InternalRegularExpressionParser.g:872:3: ( ( RULE_UNICODE_DIGIT ) ) ( ( RULE_UNICODE_DIGIT )* )
            {
            // InternalRegularExpressionParser.g:872:3: ( ( RULE_UNICODE_DIGIT ) )
            // InternalRegularExpressionParser.g:873:4: ( RULE_UNICODE_DIGIT )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); 
            }
            // InternalRegularExpressionParser.g:874:4: ( RULE_UNICODE_DIGIT )
            // InternalRegularExpressionParser.g:874:5: RULE_UNICODE_DIGIT
            {
            match(input,RULE_UNICODE_DIGIT,FOLLOW_3); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); 
            }

            }

            // InternalRegularExpressionParser.g:877:3: ( ( RULE_UNICODE_DIGIT )* )
            // InternalRegularExpressionParser.g:878:4: ( RULE_UNICODE_DIGIT )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); 
            }
            // InternalRegularExpressionParser.g:879:4: ( RULE_UNICODE_DIGIT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_UNICODE_DIGIT) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:879:5: RULE_UNICODE_DIGIT
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
    // InternalRegularExpressionParser.g:888:1: rule__Disjunction__Alternatives : ( ( ( rule__Disjunction__Group_0__0 ) ) | ( ( rule__Disjunction__Group_1__0 ) ) );
    public final void rule__Disjunction__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:892:1: ( ( ( rule__Disjunction__Group_0__0 ) ) | ( ( rule__Disjunction__Group_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=ExclamationMark && LA2_0<=LeftParenthesis)||(LA2_0>=Comma && LA2_0<=FullStop)||(LA2_0>=Colon && LA2_0<=EqualsSign)||(LA2_0>=LeftSquareBracket && LA2_0<=LeftCurlyBracket)||(LA2_0>=RightCurlyBracket && LA2_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA2_0>=RULE_HEX_ESCAPE && LA2_0<=RULE_UNICODE_ESCAPE)||(LA2_0>=RULE_DECIMAL_ESCAPE && LA2_0<=RULE_IDENTITY_ESCAPE)||LA2_0==RULE_UNICODE_DIGIT||(LA2_0>=RULE_UNICODE_LETTER && LA2_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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
                    // InternalRegularExpressionParser.g:893:2: ( ( rule__Disjunction__Group_0__0 ) )
                    {
                    // InternalRegularExpressionParser.g:893:2: ( ( rule__Disjunction__Group_0__0 ) )
                    // InternalRegularExpressionParser.g:894:3: ( rule__Disjunction__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDisjunctionAccess().getGroup_0()); 
                    }
                    // InternalRegularExpressionParser.g:895:3: ( rule__Disjunction__Group_0__0 )
                    // InternalRegularExpressionParser.g:895:4: rule__Disjunction__Group_0__0
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
                    // InternalRegularExpressionParser.g:899:2: ( ( rule__Disjunction__Group_1__0 ) )
                    {
                    // InternalRegularExpressionParser.g:899:2: ( ( rule__Disjunction__Group_1__0 ) )
                    // InternalRegularExpressionParser.g:900:3: ( rule__Disjunction__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDisjunctionAccess().getGroup_1()); 
                    }
                    // InternalRegularExpressionParser.g:901:3: ( rule__Disjunction__Group_1__0 )
                    // InternalRegularExpressionParser.g:901:4: rule__Disjunction__Group_1__0
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
    // InternalRegularExpressionParser.g:909:1: rule__Term__Alternatives : ( ( ruleAssertion ) | ( ( rule__Term__Group_1__0 ) ) );
    public final void rule__Term__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:913:1: ( ( ruleAssertion ) | ( ( rule__Term__Group_1__0 ) ) )
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
                    int LA3_4 = input.LA(3);

                    if ( (LA3_4==ExclamationMark||LA3_4==EqualsSign) ) {
                        alt3=1;
                    }
                    else if ( (LA3_4==Colon) ) {
                        alt3=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 4, input);

                        throw nvae;
                    }
                }
                else if ( ((LA3_2>=ExclamationMark && LA3_2<=RightParenthesis)||(LA3_2>=Comma && LA3_2<=FullStop)||(LA3_2>=Colon && LA3_2<=EqualsSign)||(LA3_2>=LeftSquareBracket && LA3_2<=RULE_CONTROL_LETTER_ESCAPE)||(LA3_2>=RULE_HEX_ESCAPE && LA3_2<=RULE_UNICODE_ESCAPE)||(LA3_2>=RULE_DECIMAL_ESCAPE && LA3_2<=RULE_IDENTITY_ESCAPE)||LA3_2==RULE_UNICODE_DIGIT||(LA3_2>=RULE_UNICODE_LETTER && LA3_2<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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
            case EqualsSign:
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
                    // InternalRegularExpressionParser.g:914:2: ( ruleAssertion )
                    {
                    // InternalRegularExpressionParser.g:914:2: ( ruleAssertion )
                    // InternalRegularExpressionParser.g:915:3: ruleAssertion
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
                    // InternalRegularExpressionParser.g:920:2: ( ( rule__Term__Group_1__0 ) )
                    {
                    // InternalRegularExpressionParser.g:920:2: ( ( rule__Term__Group_1__0 ) )
                    // InternalRegularExpressionParser.g:921:3: ( rule__Term__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getTermAccess().getGroup_1()); 
                    }
                    // InternalRegularExpressionParser.g:922:3: ( rule__Term__Group_1__0 )
                    // InternalRegularExpressionParser.g:922:4: rule__Term__Group_1__0
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
    // InternalRegularExpressionParser.g:930:1: rule__Assertion__Alternatives : ( ( ruleLineStart ) | ( ruleLineEnd ) | ( ruleWordBoundary ) | ( ruleLookAhead ) );
    public final void rule__Assertion__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:934:1: ( ( ruleLineStart ) | ( ruleLineEnd ) | ( ruleWordBoundary ) | ( ruleLookAhead ) )
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
                    // InternalRegularExpressionParser.g:935:2: ( ruleLineStart )
                    {
                    // InternalRegularExpressionParser.g:935:2: ( ruleLineStart )
                    // InternalRegularExpressionParser.g:936:3: ruleLineStart
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
                    // InternalRegularExpressionParser.g:941:2: ( ruleLineEnd )
                    {
                    // InternalRegularExpressionParser.g:941:2: ( ruleLineEnd )
                    // InternalRegularExpressionParser.g:942:3: ruleLineEnd
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
                    // InternalRegularExpressionParser.g:947:2: ( ruleWordBoundary )
                    {
                    // InternalRegularExpressionParser.g:947:2: ( ruleWordBoundary )
                    // InternalRegularExpressionParser.g:948:3: ruleWordBoundary
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
                    // InternalRegularExpressionParser.g:953:2: ( ruleLookAhead )
                    {
                    // InternalRegularExpressionParser.g:953:2: ( ruleLookAhead )
                    // InternalRegularExpressionParser.g:954:3: ruleLookAhead
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
    // InternalRegularExpressionParser.g:963:1: rule__WordBoundary__Alternatives_1 : ( ( RULE_WORD_BOUNDARY ) | ( ( rule__WordBoundary__NotAssignment_1_1 ) ) );
    public final void rule__WordBoundary__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:967:1: ( ( RULE_WORD_BOUNDARY ) | ( ( rule__WordBoundary__NotAssignment_1_1 ) ) )
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
                    // InternalRegularExpressionParser.g:968:2: ( RULE_WORD_BOUNDARY )
                    {
                    // InternalRegularExpressionParser.g:968:2: ( RULE_WORD_BOUNDARY )
                    // InternalRegularExpressionParser.g:969:3: RULE_WORD_BOUNDARY
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
                    // InternalRegularExpressionParser.g:974:2: ( ( rule__WordBoundary__NotAssignment_1_1 ) )
                    {
                    // InternalRegularExpressionParser.g:974:2: ( ( rule__WordBoundary__NotAssignment_1_1 ) )
                    // InternalRegularExpressionParser.g:975:3: ( rule__WordBoundary__NotAssignment_1_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getWordBoundaryAccess().getNotAssignment_1_1()); 
                    }
                    // InternalRegularExpressionParser.g:976:3: ( rule__WordBoundary__NotAssignment_1_1 )
                    // InternalRegularExpressionParser.g:976:4: rule__WordBoundary__NotAssignment_1_1
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


    // $ANTLR start "rule__LookAhead__Alternatives_3"
    // InternalRegularExpressionParser.g:984:1: rule__LookAhead__Alternatives_3 : ( ( EqualsSign ) | ( ( rule__LookAhead__NotAssignment_3_1 ) ) );
    public final void rule__LookAhead__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:988:1: ( ( EqualsSign ) | ( ( rule__LookAhead__NotAssignment_3_1 ) ) )
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
                    // InternalRegularExpressionParser.g:989:2: ( EqualsSign )
                    {
                    // InternalRegularExpressionParser.g:989:2: ( EqualsSign )
                    // InternalRegularExpressionParser.g:990:3: EqualsSign
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLookAheadAccess().getEqualsSignKeyword_3_0()); 
                    }
                    match(input,EqualsSign,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLookAheadAccess().getEqualsSignKeyword_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalRegularExpressionParser.g:995:2: ( ( rule__LookAhead__NotAssignment_3_1 ) )
                    {
                    // InternalRegularExpressionParser.g:995:2: ( ( rule__LookAhead__NotAssignment_3_1 ) )
                    // InternalRegularExpressionParser.g:996:3: ( rule__LookAhead__NotAssignment_3_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLookAheadAccess().getNotAssignment_3_1()); 
                    }
                    // InternalRegularExpressionParser.g:997:3: ( rule__LookAhead__NotAssignment_3_1 )
                    // InternalRegularExpressionParser.g:997:4: rule__LookAhead__NotAssignment_3_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__LookAhead__NotAssignment_3_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLookAheadAccess().getNotAssignment_3_1()); 
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
    // $ANTLR end "rule__LookAhead__Alternatives_3"


    // $ANTLR start "rule__Atom__Alternatives"
    // InternalRegularExpressionParser.g:1005:1: rule__Atom__Alternatives : ( ( rulePatternCharacter ) | ( ruleWildcard ) | ( ruleAtomEscape ) | ( ruleCharacterClass ) | ( ruleGroup ) );
    public final void rule__Atom__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1009:1: ( ( rulePatternCharacter ) | ( ruleWildcard ) | ( ruleAtomEscape ) | ( ruleCharacterClass ) | ( ruleGroup ) )
            int alt7=5;
            switch ( input.LA(1) ) {
            case ExclamationMark:
            case Comma:
            case HyphenMinus:
            case Colon:
            case EqualsSign:
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
                    // InternalRegularExpressionParser.g:1010:2: ( rulePatternCharacter )
                    {
                    // InternalRegularExpressionParser.g:1010:2: ( rulePatternCharacter )
                    // InternalRegularExpressionParser.g:1011:3: rulePatternCharacter
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
                    // InternalRegularExpressionParser.g:1016:2: ( ruleWildcard )
                    {
                    // InternalRegularExpressionParser.g:1016:2: ( ruleWildcard )
                    // InternalRegularExpressionParser.g:1017:3: ruleWildcard
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
                    // InternalRegularExpressionParser.g:1022:2: ( ruleAtomEscape )
                    {
                    // InternalRegularExpressionParser.g:1022:2: ( ruleAtomEscape )
                    // InternalRegularExpressionParser.g:1023:3: ruleAtomEscape
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
                    // InternalRegularExpressionParser.g:1028:2: ( ruleCharacterClass )
                    {
                    // InternalRegularExpressionParser.g:1028:2: ( ruleCharacterClass )
                    // InternalRegularExpressionParser.g:1029:3: ruleCharacterClass
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
                    // InternalRegularExpressionParser.g:1034:2: ( ruleGroup )
                    {
                    // InternalRegularExpressionParser.g:1034:2: ( ruleGroup )
                    // InternalRegularExpressionParser.g:1035:3: ruleGroup
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
    // InternalRegularExpressionParser.g:1044:1: rule__PatternCharacter__ValueAlternatives_0 : ( ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) | ( HyphenMinus ) | ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( RightSquareBracket ) );
    public final void rule__PatternCharacter__ValueAlternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1048:1: ( ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) | ( HyphenMinus ) | ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( RightSquareBracket ) )
            int alt8=11;
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
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalRegularExpressionParser.g:1049:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    {
                    // InternalRegularExpressionParser.g:1049:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    // InternalRegularExpressionParser.g:1050:3: RULE_PATTERN_CHARACTER_NO_DASH
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
                    // InternalRegularExpressionParser.g:1055:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1055:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1056:3: RULE_UNICODE_LETTER
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
                    // InternalRegularExpressionParser.g:1061:2: ( RULE_UNICODE_DIGIT )
                    {
                    // InternalRegularExpressionParser.g:1061:2: ( RULE_UNICODE_DIGIT )
                    // InternalRegularExpressionParser.g:1062:3: RULE_UNICODE_DIGIT
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
                    // InternalRegularExpressionParser.g:1067:2: ( HyphenMinus )
                    {
                    // InternalRegularExpressionParser.g:1067:2: ( HyphenMinus )
                    // InternalRegularExpressionParser.g:1068:3: HyphenMinus
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
                    // InternalRegularExpressionParser.g:1073:2: ( Comma )
                    {
                    // InternalRegularExpressionParser.g:1073:2: ( Comma )
                    // InternalRegularExpressionParser.g:1074:3: Comma
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
                    // InternalRegularExpressionParser.g:1079:2: ( EqualsSign )
                    {
                    // InternalRegularExpressionParser.g:1079:2: ( EqualsSign )
                    // InternalRegularExpressionParser.g:1080:3: EqualsSign
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
                    // InternalRegularExpressionParser.g:1085:2: ( Colon )
                    {
                    // InternalRegularExpressionParser.g:1085:2: ( Colon )
                    // InternalRegularExpressionParser.g:1086:3: Colon
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
                    // InternalRegularExpressionParser.g:1091:2: ( ExclamationMark )
                    {
                    // InternalRegularExpressionParser.g:1091:2: ( ExclamationMark )
                    // InternalRegularExpressionParser.g:1092:3: ExclamationMark
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
                    // InternalRegularExpressionParser.g:1097:2: ( LeftCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1097:2: ( LeftCurlyBracket )
                    // InternalRegularExpressionParser.g:1098:3: LeftCurlyBracket
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
                    // InternalRegularExpressionParser.g:1103:2: ( RightCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1103:2: ( RightCurlyBracket )
                    // InternalRegularExpressionParser.g:1104:3: RightCurlyBracket
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
                    // InternalRegularExpressionParser.g:1109:2: ( RightSquareBracket )
                    {
                    // InternalRegularExpressionParser.g:1109:2: ( RightSquareBracket )
                    // InternalRegularExpressionParser.g:1110:3: RightSquareBracket
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

            }
        }
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
    // InternalRegularExpressionParser.g:1119:1: rule__AtomEscape__Alternatives : ( ( ruleDecimalEscapeSequence ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) );
    public final void rule__AtomEscape__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1123:1: ( ( ruleDecimalEscapeSequence ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) )
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
                    // InternalRegularExpressionParser.g:1124:2: ( ruleDecimalEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1124:2: ( ruleDecimalEscapeSequence )
                    // InternalRegularExpressionParser.g:1125:3: ruleDecimalEscapeSequence
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
                    // InternalRegularExpressionParser.g:1130:2: ( ruleCharacterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1130:2: ( ruleCharacterEscapeSequence )
                    // InternalRegularExpressionParser.g:1131:3: ruleCharacterEscapeSequence
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
                    // InternalRegularExpressionParser.g:1136:2: ( ruleControlLetterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1136:2: ( ruleControlLetterEscapeSequence )
                    // InternalRegularExpressionParser.g:1137:3: ruleControlLetterEscapeSequence
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
                    // InternalRegularExpressionParser.g:1142:2: ( ruleHexEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1142:2: ( ruleHexEscapeSequence )
                    // InternalRegularExpressionParser.g:1143:3: ruleHexEscapeSequence
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
                    // InternalRegularExpressionParser.g:1148:2: ( ruleUnicodeEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1148:2: ( ruleUnicodeEscapeSequence )
                    // InternalRegularExpressionParser.g:1149:3: ruleUnicodeEscapeSequence
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
                    // InternalRegularExpressionParser.g:1154:2: ( ruleIdentityEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1154:2: ( ruleIdentityEscapeSequence )
                    // InternalRegularExpressionParser.g:1155:3: ruleIdentityEscapeSequence
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
                    // InternalRegularExpressionParser.g:1160:2: ( ruleCharacterClassEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1160:2: ( ruleCharacterClassEscapeSequence )
                    // InternalRegularExpressionParser.g:1161:3: ruleCharacterClassEscapeSequence
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
    // InternalRegularExpressionParser.g:1170:1: rule__CharacterClassAtom__Alternatives : ( ( ruleEscapedCharacterClassAtom ) | ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) ) );
    public final void rule__CharacterClassAtom__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1174:1: ( ( ruleEscapedCharacterClassAtom ) | ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_WORD_BOUNDARY||(LA10_0>=RULE_CHARACTER_CLASS_ESCAPE && LA10_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA10_0>=RULE_HEX_ESCAPE && LA10_0<=RULE_UNICODE_ESCAPE)||(LA10_0>=RULE_DECIMAL_ESCAPE && LA10_0<=RULE_IDENTITY_ESCAPE)) ) {
                alt10=1;
            }
            else if ( ((LA10_0>=ExclamationMark && LA10_0<=LeftSquareBracket)||(LA10_0>=CircumflexAccent && LA10_0<=RightCurlyBracket)||LA10_0==RULE_UNICODE_DIGIT||(LA10_0>=RULE_UNICODE_LETTER && LA10_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
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
                    // InternalRegularExpressionParser.g:1175:2: ( ruleEscapedCharacterClassAtom )
                    {
                    // InternalRegularExpressionParser.g:1175:2: ( ruleEscapedCharacterClassAtom )
                    // InternalRegularExpressionParser.g:1176:3: ruleEscapedCharacterClassAtom
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
                    // InternalRegularExpressionParser.g:1181:2: ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) )
                    {
                    // InternalRegularExpressionParser.g:1181:2: ( ( rule__CharacterClassAtom__CharacterAssignment_1 ) )
                    // InternalRegularExpressionParser.g:1182:3: ( rule__CharacterClassAtom__CharacterAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterAssignment_1()); 
                    }
                    // InternalRegularExpressionParser.g:1183:3: ( rule__CharacterClassAtom__CharacterAssignment_1 )
                    // InternalRegularExpressionParser.g:1183:4: rule__CharacterClassAtom__CharacterAssignment_1
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
    // InternalRegularExpressionParser.g:1191:1: rule__CharacterClassAtom__CharacterAlternatives_1_0 : ( ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( HyphenMinus ) | ( CircumflexAccent ) | ( DollarSign ) | ( FullStop ) | ( Asterisk ) | ( PlusSign ) | ( QuestionMark ) | ( LeftParenthesis ) | ( RightParenthesis ) | ( LeftSquareBracket ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( VerticalLine ) | ( Solidus ) | ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) );
    public final void rule__CharacterClassAtom__CharacterAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1195:1: ( ( Comma ) | ( EqualsSign ) | ( Colon ) | ( ExclamationMark ) | ( HyphenMinus ) | ( CircumflexAccent ) | ( DollarSign ) | ( FullStop ) | ( Asterisk ) | ( PlusSign ) | ( QuestionMark ) | ( LeftParenthesis ) | ( RightParenthesis ) | ( LeftSquareBracket ) | ( LeftCurlyBracket ) | ( RightCurlyBracket ) | ( VerticalLine ) | ( Solidus ) | ( RULE_PATTERN_CHARACTER_NO_DASH ) | ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_DIGIT ) )
            int alt11=21;
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
            case RULE_PATTERN_CHARACTER_NO_DASH:
                {
                alt11=19;
                }
                break;
            case RULE_UNICODE_LETTER:
                {
                alt11=20;
                }
                break;
            case RULE_UNICODE_DIGIT:
                {
                alt11=21;
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
                    // InternalRegularExpressionParser.g:1196:2: ( Comma )
                    {
                    // InternalRegularExpressionParser.g:1196:2: ( Comma )
                    // InternalRegularExpressionParser.g:1197:3: Comma
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
                    // InternalRegularExpressionParser.g:1202:2: ( EqualsSign )
                    {
                    // InternalRegularExpressionParser.g:1202:2: ( EqualsSign )
                    // InternalRegularExpressionParser.g:1203:3: EqualsSign
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
                    // InternalRegularExpressionParser.g:1208:2: ( Colon )
                    {
                    // InternalRegularExpressionParser.g:1208:2: ( Colon )
                    // InternalRegularExpressionParser.g:1209:3: Colon
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
                    // InternalRegularExpressionParser.g:1214:2: ( ExclamationMark )
                    {
                    // InternalRegularExpressionParser.g:1214:2: ( ExclamationMark )
                    // InternalRegularExpressionParser.g:1215:3: ExclamationMark
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
                    // InternalRegularExpressionParser.g:1220:2: ( HyphenMinus )
                    {
                    // InternalRegularExpressionParser.g:1220:2: ( HyphenMinus )
                    // InternalRegularExpressionParser.g:1221:3: HyphenMinus
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
                    // InternalRegularExpressionParser.g:1226:2: ( CircumflexAccent )
                    {
                    // InternalRegularExpressionParser.g:1226:2: ( CircumflexAccent )
                    // InternalRegularExpressionParser.g:1227:3: CircumflexAccent
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
                    // InternalRegularExpressionParser.g:1232:2: ( DollarSign )
                    {
                    // InternalRegularExpressionParser.g:1232:2: ( DollarSign )
                    // InternalRegularExpressionParser.g:1233:3: DollarSign
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
                    // InternalRegularExpressionParser.g:1238:2: ( FullStop )
                    {
                    // InternalRegularExpressionParser.g:1238:2: ( FullStop )
                    // InternalRegularExpressionParser.g:1239:3: FullStop
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
                    // InternalRegularExpressionParser.g:1244:2: ( Asterisk )
                    {
                    // InternalRegularExpressionParser.g:1244:2: ( Asterisk )
                    // InternalRegularExpressionParser.g:1245:3: Asterisk
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
                    // InternalRegularExpressionParser.g:1250:2: ( PlusSign )
                    {
                    // InternalRegularExpressionParser.g:1250:2: ( PlusSign )
                    // InternalRegularExpressionParser.g:1251:3: PlusSign
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
                    // InternalRegularExpressionParser.g:1256:2: ( QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:1256:2: ( QuestionMark )
                    // InternalRegularExpressionParser.g:1257:3: QuestionMark
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
                    // InternalRegularExpressionParser.g:1262:2: ( LeftParenthesis )
                    {
                    // InternalRegularExpressionParser.g:1262:2: ( LeftParenthesis )
                    // InternalRegularExpressionParser.g:1263:3: LeftParenthesis
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
                    // InternalRegularExpressionParser.g:1268:2: ( RightParenthesis )
                    {
                    // InternalRegularExpressionParser.g:1268:2: ( RightParenthesis )
                    // InternalRegularExpressionParser.g:1269:3: RightParenthesis
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
                    // InternalRegularExpressionParser.g:1274:2: ( LeftSquareBracket )
                    {
                    // InternalRegularExpressionParser.g:1274:2: ( LeftSquareBracket )
                    // InternalRegularExpressionParser.g:1275:3: LeftSquareBracket
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
                    // InternalRegularExpressionParser.g:1280:2: ( LeftCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1280:2: ( LeftCurlyBracket )
                    // InternalRegularExpressionParser.g:1281:3: LeftCurlyBracket
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
                    // InternalRegularExpressionParser.g:1286:2: ( RightCurlyBracket )
                    {
                    // InternalRegularExpressionParser.g:1286:2: ( RightCurlyBracket )
                    // InternalRegularExpressionParser.g:1287:3: RightCurlyBracket
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
                    // InternalRegularExpressionParser.g:1292:2: ( VerticalLine )
                    {
                    // InternalRegularExpressionParser.g:1292:2: ( VerticalLine )
                    // InternalRegularExpressionParser.g:1293:3: VerticalLine
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
                    // InternalRegularExpressionParser.g:1298:2: ( Solidus )
                    {
                    // InternalRegularExpressionParser.g:1298:2: ( Solidus )
                    // InternalRegularExpressionParser.g:1299:3: Solidus
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
                    // InternalRegularExpressionParser.g:1304:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    {
                    // InternalRegularExpressionParser.g:1304:2: ( RULE_PATTERN_CHARACTER_NO_DASH )
                    // InternalRegularExpressionParser.g:1305:3: RULE_PATTERN_CHARACTER_NO_DASH
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_18()); 
                    }
                    match(input,RULE_PATTERN_CHARACTER_NO_DASH,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_18()); 
                    }

                    }


                    }
                    break;
                case 20 :
                    // InternalRegularExpressionParser.g:1310:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1310:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1311:3: RULE_UNICODE_LETTER
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_LETTERTerminalRuleCall_1_0_19()); 
                    }
                    match(input,RULE_UNICODE_LETTER,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_LETTERTerminalRuleCall_1_0_19()); 
                    }

                    }


                    }
                    break;
                case 21 :
                    // InternalRegularExpressionParser.g:1316:2: ( RULE_UNICODE_DIGIT )
                    {
                    // InternalRegularExpressionParser.g:1316:2: ( RULE_UNICODE_DIGIT )
                    // InternalRegularExpressionParser.g:1317:3: RULE_UNICODE_DIGIT
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_DIGITTerminalRuleCall_1_0_20()); 
                    }
                    match(input,RULE_UNICODE_DIGIT,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_DIGITTerminalRuleCall_1_0_20()); 
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
    // InternalRegularExpressionParser.g:1326:1: rule__EscapedCharacterClassAtom__Alternatives : ( ( ruleDecimalEscapeSequence ) | ( ruleBackspace ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) );
    public final void rule__EscapedCharacterClassAtom__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1330:1: ( ( ruleDecimalEscapeSequence ) | ( ruleBackspace ) | ( ruleCharacterEscapeSequence ) | ( ruleControlLetterEscapeSequence ) | ( ruleHexEscapeSequence ) | ( ruleUnicodeEscapeSequence ) | ( ruleIdentityEscapeSequence ) | ( ruleCharacterClassEscapeSequence ) )
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
                    // InternalRegularExpressionParser.g:1331:2: ( ruleDecimalEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1331:2: ( ruleDecimalEscapeSequence )
                    // InternalRegularExpressionParser.g:1332:3: ruleDecimalEscapeSequence
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
                    // InternalRegularExpressionParser.g:1337:2: ( ruleBackspace )
                    {
                    // InternalRegularExpressionParser.g:1337:2: ( ruleBackspace )
                    // InternalRegularExpressionParser.g:1338:3: ruleBackspace
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
                    // InternalRegularExpressionParser.g:1343:2: ( ruleCharacterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1343:2: ( ruleCharacterEscapeSequence )
                    // InternalRegularExpressionParser.g:1344:3: ruleCharacterEscapeSequence
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
                    // InternalRegularExpressionParser.g:1349:2: ( ruleControlLetterEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1349:2: ( ruleControlLetterEscapeSequence )
                    // InternalRegularExpressionParser.g:1350:3: ruleControlLetterEscapeSequence
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
                    // InternalRegularExpressionParser.g:1355:2: ( ruleHexEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1355:2: ( ruleHexEscapeSequence )
                    // InternalRegularExpressionParser.g:1356:3: ruleHexEscapeSequence
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
                    // InternalRegularExpressionParser.g:1361:2: ( ruleUnicodeEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1361:2: ( ruleUnicodeEscapeSequence )
                    // InternalRegularExpressionParser.g:1362:3: ruleUnicodeEscapeSequence
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
                    // InternalRegularExpressionParser.g:1367:2: ( ruleIdentityEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1367:2: ( ruleIdentityEscapeSequence )
                    // InternalRegularExpressionParser.g:1368:3: ruleIdentityEscapeSequence
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
                    // InternalRegularExpressionParser.g:1373:2: ( ruleCharacterClassEscapeSequence )
                    {
                    // InternalRegularExpressionParser.g:1373:2: ( ruleCharacterClassEscapeSequence )
                    // InternalRegularExpressionParser.g:1374:3: ruleCharacterClassEscapeSequence
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


    // $ANTLR start "rule__Quantifier__Alternatives"
    // InternalRegularExpressionParser.g:1383:1: rule__Quantifier__Alternatives : ( ( ruleSimpleQuantifier ) | ( ruleExactQuantifier ) );
    public final void rule__Quantifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1387:1: ( ( ruleSimpleQuantifier ) | ( ruleExactQuantifier ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=Asterisk && LA13_0<=PlusSign)||LA13_0==QuestionMark) ) {
                alt13=1;
            }
            else if ( (LA13_0==LeftCurlyBracket) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalRegularExpressionParser.g:1388:2: ( ruleSimpleQuantifier )
                    {
                    // InternalRegularExpressionParser.g:1388:2: ( ruleSimpleQuantifier )
                    // InternalRegularExpressionParser.g:1389:3: ruleSimpleQuantifier
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
                    // InternalRegularExpressionParser.g:1394:2: ( ruleExactQuantifier )
                    {
                    // InternalRegularExpressionParser.g:1394:2: ( ruleExactQuantifier )
                    // InternalRegularExpressionParser.g:1395:3: ruleExactQuantifier
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
    // InternalRegularExpressionParser.g:1404:1: rule__SimpleQuantifier__QuantifierAlternatives_0_0 : ( ( PlusSign ) | ( Asterisk ) | ( QuestionMark ) );
    public final void rule__SimpleQuantifier__QuantifierAlternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1408:1: ( ( PlusSign ) | ( Asterisk ) | ( QuestionMark ) )
            int alt14=3;
            switch ( input.LA(1) ) {
            case PlusSign:
                {
                alt14=1;
                }
                break;
            case Asterisk:
                {
                alt14=2;
                }
                break;
            case QuestionMark:
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
                    // InternalRegularExpressionParser.g:1409:2: ( PlusSign )
                    {
                    // InternalRegularExpressionParser.g:1409:2: ( PlusSign )
                    // InternalRegularExpressionParser.g:1410:3: PlusSign
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
                    // InternalRegularExpressionParser.g:1415:2: ( Asterisk )
                    {
                    // InternalRegularExpressionParser.g:1415:2: ( Asterisk )
                    // InternalRegularExpressionParser.g:1416:3: Asterisk
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
                    // InternalRegularExpressionParser.g:1421:2: ( QuestionMark )
                    {
                    // InternalRegularExpressionParser.g:1421:2: ( QuestionMark )
                    // InternalRegularExpressionParser.g:1422:3: QuestionMark
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
    // InternalRegularExpressionParser.g:1431:1: rule__ExactQuantifier__Alternatives_3 : ( ( ( rule__ExactQuantifier__Group_3_0__0 ) ) | ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) ) );
    public final void rule__ExactQuantifier__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1435:1: ( ( ( rule__ExactQuantifier__Group_3_0__0 ) ) | ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==Comma) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==RULE_UNICODE_DIGIT) ) {
                    alt15=1;
                }
                else if ( (LA15_1==EOF||LA15_1==RightCurlyBracket) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalRegularExpressionParser.g:1436:2: ( ( rule__ExactQuantifier__Group_3_0__0 ) )
                    {
                    // InternalRegularExpressionParser.g:1436:2: ( ( rule__ExactQuantifier__Group_3_0__0 ) )
                    // InternalRegularExpressionParser.g:1437:3: ( rule__ExactQuantifier__Group_3_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getExactQuantifierAccess().getGroup_3_0()); 
                    }
                    // InternalRegularExpressionParser.g:1438:3: ( rule__ExactQuantifier__Group_3_0__0 )
                    // InternalRegularExpressionParser.g:1438:4: rule__ExactQuantifier__Group_3_0__0
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
                    // InternalRegularExpressionParser.g:1442:2: ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) )
                    {
                    // InternalRegularExpressionParser.g:1442:2: ( ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 ) )
                    // InternalRegularExpressionParser.g:1443:3: ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxAssignment_3_1()); 
                    }
                    // InternalRegularExpressionParser.g:1444:3: ( rule__ExactQuantifier__UnboundedMaxAssignment_3_1 )
                    // InternalRegularExpressionParser.g:1444:4: rule__ExactQuantifier__UnboundedMaxAssignment_3_1
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
    // InternalRegularExpressionParser.g:1452:1: rule__RegularExpressionFlags__FlagsAlternatives_1_0 : ( ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_ESCAPE ) );
    public final void rule__RegularExpressionFlags__FlagsAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1456:1: ( ( RULE_UNICODE_LETTER ) | ( RULE_UNICODE_ESCAPE ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_UNICODE_LETTER) ) {
                alt16=1;
            }
            else if ( (LA16_0==RULE_UNICODE_ESCAPE) ) {
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
                    // InternalRegularExpressionParser.g:1457:2: ( RULE_UNICODE_LETTER )
                    {
                    // InternalRegularExpressionParser.g:1457:2: ( RULE_UNICODE_LETTER )
                    // InternalRegularExpressionParser.g:1458:3: RULE_UNICODE_LETTER
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
                    // InternalRegularExpressionParser.g:1463:2: ( RULE_UNICODE_ESCAPE )
                    {
                    // InternalRegularExpressionParser.g:1463:2: ( RULE_UNICODE_ESCAPE )
                    // InternalRegularExpressionParser.g:1464:3: RULE_UNICODE_ESCAPE
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
    // InternalRegularExpressionParser.g:1473:1: rule__RegularExpressionLiteral__Group__0 : rule__RegularExpressionLiteral__Group__0__Impl rule__RegularExpressionLiteral__Group__1 ;
    public final void rule__RegularExpressionLiteral__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1477:1: ( rule__RegularExpressionLiteral__Group__0__Impl rule__RegularExpressionLiteral__Group__1 )
            // InternalRegularExpressionParser.g:1478:2: rule__RegularExpressionLiteral__Group__0__Impl rule__RegularExpressionLiteral__Group__1
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
    // InternalRegularExpressionParser.g:1485:1: rule__RegularExpressionLiteral__Group__0__Impl : ( Solidus ) ;
    public final void rule__RegularExpressionLiteral__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1489:1: ( ( Solidus ) )
            // InternalRegularExpressionParser.g:1490:1: ( Solidus )
            {
            // InternalRegularExpressionParser.g:1490:1: ( Solidus )
            // InternalRegularExpressionParser.g:1491:2: Solidus
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
    // InternalRegularExpressionParser.g:1500:1: rule__RegularExpressionLiteral__Group__1 : rule__RegularExpressionLiteral__Group__1__Impl rule__RegularExpressionLiteral__Group__2 ;
    public final void rule__RegularExpressionLiteral__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1504:1: ( rule__RegularExpressionLiteral__Group__1__Impl rule__RegularExpressionLiteral__Group__2 )
            // InternalRegularExpressionParser.g:1505:2: rule__RegularExpressionLiteral__Group__1__Impl rule__RegularExpressionLiteral__Group__2
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
    // InternalRegularExpressionParser.g:1512:1: rule__RegularExpressionLiteral__Group__1__Impl : ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) ) ;
    public final void rule__RegularExpressionLiteral__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1516:1: ( ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) ) )
            // InternalRegularExpressionParser.g:1517:1: ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) )
            {
            // InternalRegularExpressionParser.g:1517:1: ( ( rule__RegularExpressionLiteral__BodyAssignment_1 ) )
            // InternalRegularExpressionParser.g:1518:2: ( rule__RegularExpressionLiteral__BodyAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getBodyAssignment_1()); 
            }
            // InternalRegularExpressionParser.g:1519:2: ( rule__RegularExpressionLiteral__BodyAssignment_1 )
            // InternalRegularExpressionParser.g:1519:3: rule__RegularExpressionLiteral__BodyAssignment_1
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
    // InternalRegularExpressionParser.g:1527:1: rule__RegularExpressionLiteral__Group__2 : rule__RegularExpressionLiteral__Group__2__Impl rule__RegularExpressionLiteral__Group__3 ;
    public final void rule__RegularExpressionLiteral__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1531:1: ( rule__RegularExpressionLiteral__Group__2__Impl rule__RegularExpressionLiteral__Group__3 )
            // InternalRegularExpressionParser.g:1532:2: rule__RegularExpressionLiteral__Group__2__Impl rule__RegularExpressionLiteral__Group__3
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
    // InternalRegularExpressionParser.g:1539:1: rule__RegularExpressionLiteral__Group__2__Impl : ( Solidus ) ;
    public final void rule__RegularExpressionLiteral__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1543:1: ( ( Solidus ) )
            // InternalRegularExpressionParser.g:1544:1: ( Solidus )
            {
            // InternalRegularExpressionParser.g:1544:1: ( Solidus )
            // InternalRegularExpressionParser.g:1545:2: Solidus
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
    // InternalRegularExpressionParser.g:1554:1: rule__RegularExpressionLiteral__Group__3 : rule__RegularExpressionLiteral__Group__3__Impl ;
    public final void rule__RegularExpressionLiteral__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1558:1: ( rule__RegularExpressionLiteral__Group__3__Impl )
            // InternalRegularExpressionParser.g:1559:2: rule__RegularExpressionLiteral__Group__3__Impl
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
    // InternalRegularExpressionParser.g:1565:1: rule__RegularExpressionLiteral__Group__3__Impl : ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) ) ;
    public final void rule__RegularExpressionLiteral__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1569:1: ( ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) ) )
            // InternalRegularExpressionParser.g:1570:1: ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) )
            {
            // InternalRegularExpressionParser.g:1570:1: ( ( rule__RegularExpressionLiteral__FlagsAssignment_3 ) )
            // InternalRegularExpressionParser.g:1571:2: ( rule__RegularExpressionLiteral__FlagsAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionLiteralAccess().getFlagsAssignment_3()); 
            }
            // InternalRegularExpressionParser.g:1572:2: ( rule__RegularExpressionLiteral__FlagsAssignment_3 )
            // InternalRegularExpressionParser.g:1572:3: rule__RegularExpressionLiteral__FlagsAssignment_3
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
    // InternalRegularExpressionParser.g:1581:1: rule__Disjunction__Group_0__0 : rule__Disjunction__Group_0__0__Impl rule__Disjunction__Group_0__1 ;
    public final void rule__Disjunction__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1585:1: ( rule__Disjunction__Group_0__0__Impl rule__Disjunction__Group_0__1 )
            // InternalRegularExpressionParser.g:1586:2: rule__Disjunction__Group_0__0__Impl rule__Disjunction__Group_0__1
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
    // InternalRegularExpressionParser.g:1593:1: rule__Disjunction__Group_0__0__Impl : ( ruleAlternative ) ;
    public final void rule__Disjunction__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1597:1: ( ( ruleAlternative ) )
            // InternalRegularExpressionParser.g:1598:1: ( ruleAlternative )
            {
            // InternalRegularExpressionParser.g:1598:1: ( ruleAlternative )
            // InternalRegularExpressionParser.g:1599:2: ruleAlternative
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
    // InternalRegularExpressionParser.g:1608:1: rule__Disjunction__Group_0__1 : rule__Disjunction__Group_0__1__Impl ;
    public final void rule__Disjunction__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1612:1: ( rule__Disjunction__Group_0__1__Impl )
            // InternalRegularExpressionParser.g:1613:2: rule__Disjunction__Group_0__1__Impl
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
    // InternalRegularExpressionParser.g:1619:1: rule__Disjunction__Group_0__1__Impl : ( ( rule__Disjunction__Group_0_1__0 )? ) ;
    public final void rule__Disjunction__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1623:1: ( ( ( rule__Disjunction__Group_0_1__0 )? ) )
            // InternalRegularExpressionParser.g:1624:1: ( ( rule__Disjunction__Group_0_1__0 )? )
            {
            // InternalRegularExpressionParser.g:1624:1: ( ( rule__Disjunction__Group_0_1__0 )? )
            // InternalRegularExpressionParser.g:1625:2: ( rule__Disjunction__Group_0_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_0_1()); 
            }
            // InternalRegularExpressionParser.g:1626:2: ( rule__Disjunction__Group_0_1__0 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==VerticalLine) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalRegularExpressionParser.g:1626:3: rule__Disjunction__Group_0_1__0
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
    // InternalRegularExpressionParser.g:1635:1: rule__Disjunction__Group_0_1__0 : rule__Disjunction__Group_0_1__0__Impl rule__Disjunction__Group_0_1__1 ;
    public final void rule__Disjunction__Group_0_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1639:1: ( rule__Disjunction__Group_0_1__0__Impl rule__Disjunction__Group_0_1__1 )
            // InternalRegularExpressionParser.g:1640:2: rule__Disjunction__Group_0_1__0__Impl rule__Disjunction__Group_0_1__1
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
    // InternalRegularExpressionParser.g:1647:1: rule__Disjunction__Group_0_1__0__Impl : ( () ) ;
    public final void rule__Disjunction__Group_0_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1651:1: ( ( () ) )
            // InternalRegularExpressionParser.g:1652:1: ( () )
            {
            // InternalRegularExpressionParser.g:1652:1: ( () )
            // InternalRegularExpressionParser.g:1653:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()); 
            }
            // InternalRegularExpressionParser.g:1654:2: ()
            // InternalRegularExpressionParser.g:1654:3: 
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
    // InternalRegularExpressionParser.g:1662:1: rule__Disjunction__Group_0_1__1 : rule__Disjunction__Group_0_1__1__Impl ;
    public final void rule__Disjunction__Group_0_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1666:1: ( rule__Disjunction__Group_0_1__1__Impl )
            // InternalRegularExpressionParser.g:1667:2: rule__Disjunction__Group_0_1__1__Impl
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
    // InternalRegularExpressionParser.g:1673:1: rule__Disjunction__Group_0_1__1__Impl : ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) ) ;
    public final void rule__Disjunction__Group_0_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1677:1: ( ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) ) )
            // InternalRegularExpressionParser.g:1678:1: ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) )
            {
            // InternalRegularExpressionParser.g:1678:1: ( ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* ) )
            // InternalRegularExpressionParser.g:1679:2: ( ( rule__Disjunction__Group_0_1_1__0 ) ) ( ( rule__Disjunction__Group_0_1_1__0 )* )
            {
            // InternalRegularExpressionParser.g:1679:2: ( ( rule__Disjunction__Group_0_1_1__0 ) )
            // InternalRegularExpressionParser.g:1680:3: ( rule__Disjunction__Group_0_1_1__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); 
            }
            // InternalRegularExpressionParser.g:1681:3: ( rule__Disjunction__Group_0_1_1__0 )
            // InternalRegularExpressionParser.g:1681:4: rule__Disjunction__Group_0_1_1__0
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

            // InternalRegularExpressionParser.g:1684:2: ( ( rule__Disjunction__Group_0_1_1__0 )* )
            // InternalRegularExpressionParser.g:1685:3: ( rule__Disjunction__Group_0_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); 
            }
            // InternalRegularExpressionParser.g:1686:3: ( rule__Disjunction__Group_0_1_1__0 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==VerticalLine) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:1686:4: rule__Disjunction__Group_0_1_1__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__Disjunction__Group_0_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop18;
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
    // InternalRegularExpressionParser.g:1696:1: rule__Disjunction__Group_0_1_1__0 : rule__Disjunction__Group_0_1_1__0__Impl rule__Disjunction__Group_0_1_1__1 ;
    public final void rule__Disjunction__Group_0_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1700:1: ( rule__Disjunction__Group_0_1_1__0__Impl rule__Disjunction__Group_0_1_1__1 )
            // InternalRegularExpressionParser.g:1701:2: rule__Disjunction__Group_0_1_1__0__Impl rule__Disjunction__Group_0_1_1__1
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
    // InternalRegularExpressionParser.g:1708:1: rule__Disjunction__Group_0_1_1__0__Impl : ( VerticalLine ) ;
    public final void rule__Disjunction__Group_0_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1712:1: ( ( VerticalLine ) )
            // InternalRegularExpressionParser.g:1713:1: ( VerticalLine )
            {
            // InternalRegularExpressionParser.g:1713:1: ( VerticalLine )
            // InternalRegularExpressionParser.g:1714:2: VerticalLine
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
    // InternalRegularExpressionParser.g:1723:1: rule__Disjunction__Group_0_1_1__1 : rule__Disjunction__Group_0_1_1__1__Impl ;
    public final void rule__Disjunction__Group_0_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1727:1: ( rule__Disjunction__Group_0_1_1__1__Impl )
            // InternalRegularExpressionParser.g:1728:2: rule__Disjunction__Group_0_1_1__1__Impl
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
    // InternalRegularExpressionParser.g:1734:1: rule__Disjunction__Group_0_1_1__1__Impl : ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? ) ;
    public final void rule__Disjunction__Group_0_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1738:1: ( ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? ) )
            // InternalRegularExpressionParser.g:1739:1: ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? )
            {
            // InternalRegularExpressionParser.g:1739:1: ( ( rule__Disjunction__ElementsAssignment_0_1_1_1 )? )
            // InternalRegularExpressionParser.g:1740:2: ( rule__Disjunction__ElementsAssignment_0_1_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getElementsAssignment_0_1_1_1()); 
            }
            // InternalRegularExpressionParser.g:1741:2: ( rule__Disjunction__ElementsAssignment_0_1_1_1 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0>=ExclamationMark && LA19_0<=LeftParenthesis)||(LA19_0>=Comma && LA19_0<=FullStop)||(LA19_0>=Colon && LA19_0<=EqualsSign)||(LA19_0>=LeftSquareBracket && LA19_0<=LeftCurlyBracket)||(LA19_0>=RightCurlyBracket && LA19_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA19_0>=RULE_HEX_ESCAPE && LA19_0<=RULE_UNICODE_ESCAPE)||(LA19_0>=RULE_DECIMAL_ESCAPE && LA19_0<=RULE_IDENTITY_ESCAPE)||LA19_0==RULE_UNICODE_DIGIT||(LA19_0>=RULE_UNICODE_LETTER && LA19_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalRegularExpressionParser.g:1741:3: rule__Disjunction__ElementsAssignment_0_1_1_1
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
    // InternalRegularExpressionParser.g:1750:1: rule__Disjunction__Group_1__0 : rule__Disjunction__Group_1__0__Impl rule__Disjunction__Group_1__1 ;
    public final void rule__Disjunction__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1754:1: ( rule__Disjunction__Group_1__0__Impl rule__Disjunction__Group_1__1 )
            // InternalRegularExpressionParser.g:1755:2: rule__Disjunction__Group_1__0__Impl rule__Disjunction__Group_1__1
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
    // InternalRegularExpressionParser.g:1762:1: rule__Disjunction__Group_1__0__Impl : ( () ) ;
    public final void rule__Disjunction__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1766:1: ( ( () ) )
            // InternalRegularExpressionParser.g:1767:1: ( () )
            {
            // InternalRegularExpressionParser.g:1767:1: ( () )
            // InternalRegularExpressionParser.g:1768:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getDisjunctionAction_1_0()); 
            }
            // InternalRegularExpressionParser.g:1769:2: ()
            // InternalRegularExpressionParser.g:1769:3: 
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
    // InternalRegularExpressionParser.g:1777:1: rule__Disjunction__Group_1__1 : rule__Disjunction__Group_1__1__Impl ;
    public final void rule__Disjunction__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1781:1: ( rule__Disjunction__Group_1__1__Impl )
            // InternalRegularExpressionParser.g:1782:2: rule__Disjunction__Group_1__1__Impl
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
    // InternalRegularExpressionParser.g:1788:1: rule__Disjunction__Group_1__1__Impl : ( ( rule__Disjunction__Group_1_1__0 )* ) ;
    public final void rule__Disjunction__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1792:1: ( ( ( rule__Disjunction__Group_1_1__0 )* ) )
            // InternalRegularExpressionParser.g:1793:1: ( ( rule__Disjunction__Group_1_1__0 )* )
            {
            // InternalRegularExpressionParser.g:1793:1: ( ( rule__Disjunction__Group_1_1__0 )* )
            // InternalRegularExpressionParser.g:1794:2: ( rule__Disjunction__Group_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getGroup_1_1()); 
            }
            // InternalRegularExpressionParser.g:1795:2: ( rule__Disjunction__Group_1_1__0 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==VerticalLine) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:1795:3: rule__Disjunction__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__Disjunction__Group_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop20;
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
    // InternalRegularExpressionParser.g:1804:1: rule__Disjunction__Group_1_1__0 : rule__Disjunction__Group_1_1__0__Impl rule__Disjunction__Group_1_1__1 ;
    public final void rule__Disjunction__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1808:1: ( rule__Disjunction__Group_1_1__0__Impl rule__Disjunction__Group_1_1__1 )
            // InternalRegularExpressionParser.g:1809:2: rule__Disjunction__Group_1_1__0__Impl rule__Disjunction__Group_1_1__1
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
    // InternalRegularExpressionParser.g:1816:1: rule__Disjunction__Group_1_1__0__Impl : ( VerticalLine ) ;
    public final void rule__Disjunction__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1820:1: ( ( VerticalLine ) )
            // InternalRegularExpressionParser.g:1821:1: ( VerticalLine )
            {
            // InternalRegularExpressionParser.g:1821:1: ( VerticalLine )
            // InternalRegularExpressionParser.g:1822:2: VerticalLine
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
    // InternalRegularExpressionParser.g:1831:1: rule__Disjunction__Group_1_1__1 : rule__Disjunction__Group_1_1__1__Impl ;
    public final void rule__Disjunction__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1835:1: ( rule__Disjunction__Group_1_1__1__Impl )
            // InternalRegularExpressionParser.g:1836:2: rule__Disjunction__Group_1_1__1__Impl
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
    // InternalRegularExpressionParser.g:1842:1: rule__Disjunction__Group_1_1__1__Impl : ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? ) ;
    public final void rule__Disjunction__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1846:1: ( ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? ) )
            // InternalRegularExpressionParser.g:1847:1: ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? )
            {
            // InternalRegularExpressionParser.g:1847:1: ( ( rule__Disjunction__ElementsAssignment_1_1_1 )? )
            // InternalRegularExpressionParser.g:1848:2: ( rule__Disjunction__ElementsAssignment_1_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDisjunctionAccess().getElementsAssignment_1_1_1()); 
            }
            // InternalRegularExpressionParser.g:1849:2: ( rule__Disjunction__ElementsAssignment_1_1_1 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0>=ExclamationMark && LA21_0<=LeftParenthesis)||(LA21_0>=Comma && LA21_0<=FullStop)||(LA21_0>=Colon && LA21_0<=EqualsSign)||(LA21_0>=LeftSquareBracket && LA21_0<=LeftCurlyBracket)||(LA21_0>=RightCurlyBracket && LA21_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA21_0>=RULE_HEX_ESCAPE && LA21_0<=RULE_UNICODE_ESCAPE)||(LA21_0>=RULE_DECIMAL_ESCAPE && LA21_0<=RULE_IDENTITY_ESCAPE)||LA21_0==RULE_UNICODE_DIGIT||(LA21_0>=RULE_UNICODE_LETTER && LA21_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalRegularExpressionParser.g:1849:3: rule__Disjunction__ElementsAssignment_1_1_1
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
    // InternalRegularExpressionParser.g:1858:1: rule__Alternative__Group__0 : rule__Alternative__Group__0__Impl rule__Alternative__Group__1 ;
    public final void rule__Alternative__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1862:1: ( rule__Alternative__Group__0__Impl rule__Alternative__Group__1 )
            // InternalRegularExpressionParser.g:1863:2: rule__Alternative__Group__0__Impl rule__Alternative__Group__1
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
    // InternalRegularExpressionParser.g:1870:1: rule__Alternative__Group__0__Impl : ( ruleTerm ) ;
    public final void rule__Alternative__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1874:1: ( ( ruleTerm ) )
            // InternalRegularExpressionParser.g:1875:1: ( ruleTerm )
            {
            // InternalRegularExpressionParser.g:1875:1: ( ruleTerm )
            // InternalRegularExpressionParser.g:1876:2: ruleTerm
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
    // InternalRegularExpressionParser.g:1885:1: rule__Alternative__Group__1 : rule__Alternative__Group__1__Impl ;
    public final void rule__Alternative__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1889:1: ( rule__Alternative__Group__1__Impl )
            // InternalRegularExpressionParser.g:1890:2: rule__Alternative__Group__1__Impl
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
    // InternalRegularExpressionParser.g:1896:1: rule__Alternative__Group__1__Impl : ( ( rule__Alternative__Group_1__0 )? ) ;
    public final void rule__Alternative__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1900:1: ( ( ( rule__Alternative__Group_1__0 )? ) )
            // InternalRegularExpressionParser.g:1901:1: ( ( rule__Alternative__Group_1__0 )? )
            {
            // InternalRegularExpressionParser.g:1901:1: ( ( rule__Alternative__Group_1__0 )? )
            // InternalRegularExpressionParser.g:1902:2: ( rule__Alternative__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getGroup_1()); 
            }
            // InternalRegularExpressionParser.g:1903:2: ( rule__Alternative__Group_1__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=ExclamationMark && LA22_0<=LeftParenthesis)||(LA22_0>=Comma && LA22_0<=FullStop)||(LA22_0>=Colon && LA22_0<=EqualsSign)||(LA22_0>=LeftSquareBracket && LA22_0<=LeftCurlyBracket)||(LA22_0>=RightCurlyBracket && LA22_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA22_0>=RULE_HEX_ESCAPE && LA22_0<=RULE_UNICODE_ESCAPE)||(LA22_0>=RULE_DECIMAL_ESCAPE && LA22_0<=RULE_IDENTITY_ESCAPE)||LA22_0==RULE_UNICODE_DIGIT||(LA22_0>=RULE_UNICODE_LETTER && LA22_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalRegularExpressionParser.g:1903:3: rule__Alternative__Group_1__0
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
    // InternalRegularExpressionParser.g:1912:1: rule__Alternative__Group_1__0 : rule__Alternative__Group_1__0__Impl rule__Alternative__Group_1__1 ;
    public final void rule__Alternative__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1916:1: ( rule__Alternative__Group_1__0__Impl rule__Alternative__Group_1__1 )
            // InternalRegularExpressionParser.g:1917:2: rule__Alternative__Group_1__0__Impl rule__Alternative__Group_1__1
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
    // InternalRegularExpressionParser.g:1924:1: rule__Alternative__Group_1__0__Impl : ( () ) ;
    public final void rule__Alternative__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1928:1: ( ( () ) )
            // InternalRegularExpressionParser.g:1929:1: ( () )
            {
            // InternalRegularExpressionParser.g:1929:1: ( () )
            // InternalRegularExpressionParser.g:1930:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()); 
            }
            // InternalRegularExpressionParser.g:1931:2: ()
            // InternalRegularExpressionParser.g:1931:3: 
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
    // InternalRegularExpressionParser.g:1939:1: rule__Alternative__Group_1__1 : rule__Alternative__Group_1__1__Impl ;
    public final void rule__Alternative__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1943:1: ( rule__Alternative__Group_1__1__Impl )
            // InternalRegularExpressionParser.g:1944:2: rule__Alternative__Group_1__1__Impl
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
    // InternalRegularExpressionParser.g:1950:1: rule__Alternative__Group_1__1__Impl : ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) ) ;
    public final void rule__Alternative__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1954:1: ( ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) ) )
            // InternalRegularExpressionParser.g:1955:1: ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) )
            {
            // InternalRegularExpressionParser.g:1955:1: ( ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* ) )
            // InternalRegularExpressionParser.g:1956:2: ( ( rule__Alternative__ElementsAssignment_1_1 ) ) ( ( rule__Alternative__ElementsAssignment_1_1 )* )
            {
            // InternalRegularExpressionParser.g:1956:2: ( ( rule__Alternative__ElementsAssignment_1_1 ) )
            // InternalRegularExpressionParser.g:1957:3: ( rule__Alternative__ElementsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); 
            }
            // InternalRegularExpressionParser.g:1958:3: ( rule__Alternative__ElementsAssignment_1_1 )
            // InternalRegularExpressionParser.g:1958:4: rule__Alternative__ElementsAssignment_1_1
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

            // InternalRegularExpressionParser.g:1961:2: ( ( rule__Alternative__ElementsAssignment_1_1 )* )
            // InternalRegularExpressionParser.g:1962:3: ( rule__Alternative__ElementsAssignment_1_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); 
            }
            // InternalRegularExpressionParser.g:1963:3: ( rule__Alternative__ElementsAssignment_1_1 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( ((LA23_0>=ExclamationMark && LA23_0<=LeftParenthesis)||(LA23_0>=Comma && LA23_0<=FullStop)||(LA23_0>=Colon && LA23_0<=EqualsSign)||(LA23_0>=LeftSquareBracket && LA23_0<=LeftCurlyBracket)||(LA23_0>=RightCurlyBracket && LA23_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA23_0>=RULE_HEX_ESCAPE && LA23_0<=RULE_UNICODE_ESCAPE)||(LA23_0>=RULE_DECIMAL_ESCAPE && LA23_0<=RULE_IDENTITY_ESCAPE)||LA23_0==RULE_UNICODE_DIGIT||(LA23_0>=RULE_UNICODE_LETTER && LA23_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:1963:4: rule__Alternative__ElementsAssignment_1_1
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__Alternative__ElementsAssignment_1_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop23;
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
    // InternalRegularExpressionParser.g:1973:1: rule__Term__Group_1__0 : rule__Term__Group_1__0__Impl rule__Term__Group_1__1 ;
    public final void rule__Term__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1977:1: ( rule__Term__Group_1__0__Impl rule__Term__Group_1__1 )
            // InternalRegularExpressionParser.g:1978:2: rule__Term__Group_1__0__Impl rule__Term__Group_1__1
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
    // InternalRegularExpressionParser.g:1985:1: rule__Term__Group_1__0__Impl : ( ruleAtom ) ;
    public final void rule__Term__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:1989:1: ( ( ruleAtom ) )
            // InternalRegularExpressionParser.g:1990:1: ( ruleAtom )
            {
            // InternalRegularExpressionParser.g:1990:1: ( ruleAtom )
            // InternalRegularExpressionParser.g:1991:2: ruleAtom
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
    // InternalRegularExpressionParser.g:2000:1: rule__Term__Group_1__1 : rule__Term__Group_1__1__Impl ;
    public final void rule__Term__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2004:1: ( rule__Term__Group_1__1__Impl )
            // InternalRegularExpressionParser.g:2005:2: rule__Term__Group_1__1__Impl
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
    // InternalRegularExpressionParser.g:2011:1: rule__Term__Group_1__1__Impl : ( ( rule__Term__QuantifierAssignment_1_1 )? ) ;
    public final void rule__Term__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2015:1: ( ( ( rule__Term__QuantifierAssignment_1_1 )? ) )
            // InternalRegularExpressionParser.g:2016:1: ( ( rule__Term__QuantifierAssignment_1_1 )? )
            {
            // InternalRegularExpressionParser.g:2016:1: ( ( rule__Term__QuantifierAssignment_1_1 )? )
            // InternalRegularExpressionParser.g:2017:2: ( rule__Term__QuantifierAssignment_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTermAccess().getQuantifierAssignment_1_1()); 
            }
            // InternalRegularExpressionParser.g:2018:2: ( rule__Term__QuantifierAssignment_1_1 )?
            int alt24=2;
            alt24 = dfa24.predict(input);
            switch (alt24) {
                case 1 :
                    // InternalRegularExpressionParser.g:2018:3: rule__Term__QuantifierAssignment_1_1
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
    // InternalRegularExpressionParser.g:2027:1: rule__LineStart__Group__0 : rule__LineStart__Group__0__Impl rule__LineStart__Group__1 ;
    public final void rule__LineStart__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2031:1: ( rule__LineStart__Group__0__Impl rule__LineStart__Group__1 )
            // InternalRegularExpressionParser.g:2032:2: rule__LineStart__Group__0__Impl rule__LineStart__Group__1
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
    // InternalRegularExpressionParser.g:2039:1: rule__LineStart__Group__0__Impl : ( () ) ;
    public final void rule__LineStart__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2043:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2044:1: ( () )
            {
            // InternalRegularExpressionParser.g:2044:1: ( () )
            // InternalRegularExpressionParser.g:2045:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineStartAccess().getLineStartAction_0()); 
            }
            // InternalRegularExpressionParser.g:2046:2: ()
            // InternalRegularExpressionParser.g:2046:3: 
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
    // InternalRegularExpressionParser.g:2054:1: rule__LineStart__Group__1 : rule__LineStart__Group__1__Impl ;
    public final void rule__LineStart__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2058:1: ( rule__LineStart__Group__1__Impl )
            // InternalRegularExpressionParser.g:2059:2: rule__LineStart__Group__1__Impl
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
    // InternalRegularExpressionParser.g:2065:1: rule__LineStart__Group__1__Impl : ( CircumflexAccent ) ;
    public final void rule__LineStart__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2069:1: ( ( CircumflexAccent ) )
            // InternalRegularExpressionParser.g:2070:1: ( CircumflexAccent )
            {
            // InternalRegularExpressionParser.g:2070:1: ( CircumflexAccent )
            // InternalRegularExpressionParser.g:2071:2: CircumflexAccent
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
    // InternalRegularExpressionParser.g:2081:1: rule__LineEnd__Group__0 : rule__LineEnd__Group__0__Impl rule__LineEnd__Group__1 ;
    public final void rule__LineEnd__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2085:1: ( rule__LineEnd__Group__0__Impl rule__LineEnd__Group__1 )
            // InternalRegularExpressionParser.g:2086:2: rule__LineEnd__Group__0__Impl rule__LineEnd__Group__1
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
    // InternalRegularExpressionParser.g:2093:1: rule__LineEnd__Group__0__Impl : ( () ) ;
    public final void rule__LineEnd__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2097:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2098:1: ( () )
            {
            // InternalRegularExpressionParser.g:2098:1: ( () )
            // InternalRegularExpressionParser.g:2099:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLineEndAccess().getLineEndAction_0()); 
            }
            // InternalRegularExpressionParser.g:2100:2: ()
            // InternalRegularExpressionParser.g:2100:3: 
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
    // InternalRegularExpressionParser.g:2108:1: rule__LineEnd__Group__1 : rule__LineEnd__Group__1__Impl ;
    public final void rule__LineEnd__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2112:1: ( rule__LineEnd__Group__1__Impl )
            // InternalRegularExpressionParser.g:2113:2: rule__LineEnd__Group__1__Impl
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
    // InternalRegularExpressionParser.g:2119:1: rule__LineEnd__Group__1__Impl : ( DollarSign ) ;
    public final void rule__LineEnd__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2123:1: ( ( DollarSign ) )
            // InternalRegularExpressionParser.g:2124:1: ( DollarSign )
            {
            // InternalRegularExpressionParser.g:2124:1: ( DollarSign )
            // InternalRegularExpressionParser.g:2125:2: DollarSign
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
    // InternalRegularExpressionParser.g:2135:1: rule__WordBoundary__Group__0 : rule__WordBoundary__Group__0__Impl rule__WordBoundary__Group__1 ;
    public final void rule__WordBoundary__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2139:1: ( rule__WordBoundary__Group__0__Impl rule__WordBoundary__Group__1 )
            // InternalRegularExpressionParser.g:2140:2: rule__WordBoundary__Group__0__Impl rule__WordBoundary__Group__1
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
    // InternalRegularExpressionParser.g:2147:1: rule__WordBoundary__Group__0__Impl : ( () ) ;
    public final void rule__WordBoundary__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2151:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2152:1: ( () )
            {
            // InternalRegularExpressionParser.g:2152:1: ( () )
            // InternalRegularExpressionParser.g:2153:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryAccess().getWordBoundaryAction_0()); 
            }
            // InternalRegularExpressionParser.g:2154:2: ()
            // InternalRegularExpressionParser.g:2154:3: 
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
    // InternalRegularExpressionParser.g:2162:1: rule__WordBoundary__Group__1 : rule__WordBoundary__Group__1__Impl ;
    public final void rule__WordBoundary__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2166:1: ( rule__WordBoundary__Group__1__Impl )
            // InternalRegularExpressionParser.g:2167:2: rule__WordBoundary__Group__1__Impl
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
    // InternalRegularExpressionParser.g:2173:1: rule__WordBoundary__Group__1__Impl : ( ( rule__WordBoundary__Alternatives_1 ) ) ;
    public final void rule__WordBoundary__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2177:1: ( ( ( rule__WordBoundary__Alternatives_1 ) ) )
            // InternalRegularExpressionParser.g:2178:1: ( ( rule__WordBoundary__Alternatives_1 ) )
            {
            // InternalRegularExpressionParser.g:2178:1: ( ( rule__WordBoundary__Alternatives_1 ) )
            // InternalRegularExpressionParser.g:2179:2: ( rule__WordBoundary__Alternatives_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWordBoundaryAccess().getAlternatives_1()); 
            }
            // InternalRegularExpressionParser.g:2180:2: ( rule__WordBoundary__Alternatives_1 )
            // InternalRegularExpressionParser.g:2180:3: rule__WordBoundary__Alternatives_1
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
    // InternalRegularExpressionParser.g:2189:1: rule__LookAhead__Group__0 : rule__LookAhead__Group__0__Impl rule__LookAhead__Group__1 ;
    public final void rule__LookAhead__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2193:1: ( rule__LookAhead__Group__0__Impl rule__LookAhead__Group__1 )
            // InternalRegularExpressionParser.g:2194:2: rule__LookAhead__Group__0__Impl rule__LookAhead__Group__1
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
    // InternalRegularExpressionParser.g:2201:1: rule__LookAhead__Group__0__Impl : ( () ) ;
    public final void rule__LookAhead__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2205:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2206:1: ( () )
            {
            // InternalRegularExpressionParser.g:2206:1: ( () )
            // InternalRegularExpressionParser.g:2207:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getLookAheadAction_0()); 
            }
            // InternalRegularExpressionParser.g:2208:2: ()
            // InternalRegularExpressionParser.g:2208:3: 
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
    // InternalRegularExpressionParser.g:2216:1: rule__LookAhead__Group__1 : rule__LookAhead__Group__1__Impl rule__LookAhead__Group__2 ;
    public final void rule__LookAhead__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2220:1: ( rule__LookAhead__Group__1__Impl rule__LookAhead__Group__2 )
            // InternalRegularExpressionParser.g:2221:2: rule__LookAhead__Group__1__Impl rule__LookAhead__Group__2
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
    // InternalRegularExpressionParser.g:2228:1: rule__LookAhead__Group__1__Impl : ( LeftParenthesis ) ;
    public final void rule__LookAhead__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2232:1: ( ( LeftParenthesis ) )
            // InternalRegularExpressionParser.g:2233:1: ( LeftParenthesis )
            {
            // InternalRegularExpressionParser.g:2233:1: ( LeftParenthesis )
            // InternalRegularExpressionParser.g:2234:2: LeftParenthesis
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
    // InternalRegularExpressionParser.g:2243:1: rule__LookAhead__Group__2 : rule__LookAhead__Group__2__Impl rule__LookAhead__Group__3 ;
    public final void rule__LookAhead__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2247:1: ( rule__LookAhead__Group__2__Impl rule__LookAhead__Group__3 )
            // InternalRegularExpressionParser.g:2248:2: rule__LookAhead__Group__2__Impl rule__LookAhead__Group__3
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
    // InternalRegularExpressionParser.g:2255:1: rule__LookAhead__Group__2__Impl : ( QuestionMark ) ;
    public final void rule__LookAhead__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2259:1: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:2260:1: ( QuestionMark )
            {
            // InternalRegularExpressionParser.g:2260:1: ( QuestionMark )
            // InternalRegularExpressionParser.g:2261:2: QuestionMark
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
    // InternalRegularExpressionParser.g:2270:1: rule__LookAhead__Group__3 : rule__LookAhead__Group__3__Impl rule__LookAhead__Group__4 ;
    public final void rule__LookAhead__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2274:1: ( rule__LookAhead__Group__3__Impl rule__LookAhead__Group__4 )
            // InternalRegularExpressionParser.g:2275:2: rule__LookAhead__Group__3__Impl rule__LookAhead__Group__4
            {
            pushFollow(FOLLOW_4);
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
    // InternalRegularExpressionParser.g:2282:1: rule__LookAhead__Group__3__Impl : ( ( rule__LookAhead__Alternatives_3 ) ) ;
    public final void rule__LookAhead__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2286:1: ( ( ( rule__LookAhead__Alternatives_3 ) ) )
            // InternalRegularExpressionParser.g:2287:1: ( ( rule__LookAhead__Alternatives_3 ) )
            {
            // InternalRegularExpressionParser.g:2287:1: ( ( rule__LookAhead__Alternatives_3 ) )
            // InternalRegularExpressionParser.g:2288:2: ( rule__LookAhead__Alternatives_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getAlternatives_3()); 
            }
            // InternalRegularExpressionParser.g:2289:2: ( rule__LookAhead__Alternatives_3 )
            // InternalRegularExpressionParser.g:2289:3: rule__LookAhead__Alternatives_3
            {
            pushFollow(FOLLOW_2);
            rule__LookAhead__Alternatives_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getAlternatives_3()); 
            }

            }


            }

        }
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
    // InternalRegularExpressionParser.g:2297:1: rule__LookAhead__Group__4 : rule__LookAhead__Group__4__Impl rule__LookAhead__Group__5 ;
    public final void rule__LookAhead__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2301:1: ( rule__LookAhead__Group__4__Impl rule__LookAhead__Group__5 )
            // InternalRegularExpressionParser.g:2302:2: rule__LookAhead__Group__4__Impl rule__LookAhead__Group__5
            {
            pushFollow(FOLLOW_18);
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
    // InternalRegularExpressionParser.g:2309:1: rule__LookAhead__Group__4__Impl : ( ( rule__LookAhead__PatternAssignment_4 ) ) ;
    public final void rule__LookAhead__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2313:1: ( ( ( rule__LookAhead__PatternAssignment_4 ) ) )
            // InternalRegularExpressionParser.g:2314:1: ( ( rule__LookAhead__PatternAssignment_4 ) )
            {
            // InternalRegularExpressionParser.g:2314:1: ( ( rule__LookAhead__PatternAssignment_4 ) )
            // InternalRegularExpressionParser.g:2315:2: ( rule__LookAhead__PatternAssignment_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getPatternAssignment_4()); 
            }
            // InternalRegularExpressionParser.g:2316:2: ( rule__LookAhead__PatternAssignment_4 )
            // InternalRegularExpressionParser.g:2316:3: rule__LookAhead__PatternAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__LookAhead__PatternAssignment_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getPatternAssignment_4()); 
            }

            }


            }

        }
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
    // InternalRegularExpressionParser.g:2324:1: rule__LookAhead__Group__5 : rule__LookAhead__Group__5__Impl ;
    public final void rule__LookAhead__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2328:1: ( rule__LookAhead__Group__5__Impl )
            // InternalRegularExpressionParser.g:2329:2: rule__LookAhead__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LookAhead__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // InternalRegularExpressionParser.g:2335:1: rule__LookAhead__Group__5__Impl : ( RightParenthesis ) ;
    public final void rule__LookAhead__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2339:1: ( ( RightParenthesis ) )
            // InternalRegularExpressionParser.g:2340:1: ( RightParenthesis )
            {
            // InternalRegularExpressionParser.g:2340:1: ( RightParenthesis )
            // InternalRegularExpressionParser.g:2341:2: RightParenthesis
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getRightParenthesisKeyword_5()); 
            }
            match(input,RightParenthesis,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getRightParenthesisKeyword_5()); 
            }

            }


            }

        }
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


    // $ANTLR start "rule__Wildcard__Group__0"
    // InternalRegularExpressionParser.g:2351:1: rule__Wildcard__Group__0 : rule__Wildcard__Group__0__Impl rule__Wildcard__Group__1 ;
    public final void rule__Wildcard__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2355:1: ( rule__Wildcard__Group__0__Impl rule__Wildcard__Group__1 )
            // InternalRegularExpressionParser.g:2356:2: rule__Wildcard__Group__0__Impl rule__Wildcard__Group__1
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
    // InternalRegularExpressionParser.g:2363:1: rule__Wildcard__Group__0__Impl : ( () ) ;
    public final void rule__Wildcard__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2367:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2368:1: ( () )
            {
            // InternalRegularExpressionParser.g:2368:1: ( () )
            // InternalRegularExpressionParser.g:2369:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getWildcardAccess().getWildcardAction_0()); 
            }
            // InternalRegularExpressionParser.g:2370:2: ()
            // InternalRegularExpressionParser.g:2370:3: 
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
    // InternalRegularExpressionParser.g:2378:1: rule__Wildcard__Group__1 : rule__Wildcard__Group__1__Impl ;
    public final void rule__Wildcard__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2382:1: ( rule__Wildcard__Group__1__Impl )
            // InternalRegularExpressionParser.g:2383:2: rule__Wildcard__Group__1__Impl
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
    // InternalRegularExpressionParser.g:2389:1: rule__Wildcard__Group__1__Impl : ( FullStop ) ;
    public final void rule__Wildcard__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2393:1: ( ( FullStop ) )
            // InternalRegularExpressionParser.g:2394:1: ( FullStop )
            {
            // InternalRegularExpressionParser.g:2394:1: ( FullStop )
            // InternalRegularExpressionParser.g:2395:2: FullStop
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
    // InternalRegularExpressionParser.g:2405:1: rule__CharacterClass__Group__0 : rule__CharacterClass__Group__0__Impl rule__CharacterClass__Group__1 ;
    public final void rule__CharacterClass__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2409:1: ( rule__CharacterClass__Group__0__Impl rule__CharacterClass__Group__1 )
            // InternalRegularExpressionParser.g:2410:2: rule__CharacterClass__Group__0__Impl rule__CharacterClass__Group__1
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
    // InternalRegularExpressionParser.g:2417:1: rule__CharacterClass__Group__0__Impl : ( () ) ;
    public final void rule__CharacterClass__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2421:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2422:1: ( () )
            {
            // InternalRegularExpressionParser.g:2422:1: ( () )
            // InternalRegularExpressionParser.g:2423:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getCharacterClassAction_0()); 
            }
            // InternalRegularExpressionParser.g:2424:2: ()
            // InternalRegularExpressionParser.g:2424:3: 
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
    // InternalRegularExpressionParser.g:2432:1: rule__CharacterClass__Group__1 : rule__CharacterClass__Group__1__Impl rule__CharacterClass__Group__2 ;
    public final void rule__CharacterClass__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2436:1: ( rule__CharacterClass__Group__1__Impl rule__CharacterClass__Group__2 )
            // InternalRegularExpressionParser.g:2437:2: rule__CharacterClass__Group__1__Impl rule__CharacterClass__Group__2
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
    // InternalRegularExpressionParser.g:2444:1: rule__CharacterClass__Group__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__CharacterClass__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2448:1: ( ( LeftSquareBracket ) )
            // InternalRegularExpressionParser.g:2449:1: ( LeftSquareBracket )
            {
            // InternalRegularExpressionParser.g:2449:1: ( LeftSquareBracket )
            // InternalRegularExpressionParser.g:2450:2: LeftSquareBracket
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
    // InternalRegularExpressionParser.g:2459:1: rule__CharacterClass__Group__2 : rule__CharacterClass__Group__2__Impl rule__CharacterClass__Group__3 ;
    public final void rule__CharacterClass__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2463:1: ( rule__CharacterClass__Group__2__Impl rule__CharacterClass__Group__3 )
            // InternalRegularExpressionParser.g:2464:2: rule__CharacterClass__Group__2__Impl rule__CharacterClass__Group__3
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
    // InternalRegularExpressionParser.g:2471:1: rule__CharacterClass__Group__2__Impl : ( ( rule__CharacterClass__Group_2__0 )? ) ;
    public final void rule__CharacterClass__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2475:1: ( ( ( rule__CharacterClass__Group_2__0 )? ) )
            // InternalRegularExpressionParser.g:2476:1: ( ( rule__CharacterClass__Group_2__0 )? )
            {
            // InternalRegularExpressionParser.g:2476:1: ( ( rule__CharacterClass__Group_2__0 )? )
            // InternalRegularExpressionParser.g:2477:2: ( rule__CharacterClass__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getGroup_2()); 
            }
            // InternalRegularExpressionParser.g:2478:2: ( rule__CharacterClass__Group_2__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==CircumflexAccent) ) {
                int LA25_1 = input.LA(2);

                if ( (synpred70_InternalRegularExpressionParser()) ) {
                    alt25=1;
                }
            }
            switch (alt25) {
                case 1 :
                    // InternalRegularExpressionParser.g:2478:3: rule__CharacterClass__Group_2__0
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
    // InternalRegularExpressionParser.g:2486:1: rule__CharacterClass__Group__3 : rule__CharacterClass__Group__3__Impl rule__CharacterClass__Group__4 ;
    public final void rule__CharacterClass__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2490:1: ( rule__CharacterClass__Group__3__Impl rule__CharacterClass__Group__4 )
            // InternalRegularExpressionParser.g:2491:2: rule__CharacterClass__Group__3__Impl rule__CharacterClass__Group__4
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
    // InternalRegularExpressionParser.g:2498:1: rule__CharacterClass__Group__3__Impl : ( ( rule__CharacterClass__ElementsAssignment_3 )* ) ;
    public final void rule__CharacterClass__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2502:1: ( ( ( rule__CharacterClass__ElementsAssignment_3 )* ) )
            // InternalRegularExpressionParser.g:2503:1: ( ( rule__CharacterClass__ElementsAssignment_3 )* )
            {
            // InternalRegularExpressionParser.g:2503:1: ( ( rule__CharacterClass__ElementsAssignment_3 )* )
            // InternalRegularExpressionParser.g:2504:2: ( rule__CharacterClass__ElementsAssignment_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getElementsAssignment_3()); 
            }
            // InternalRegularExpressionParser.g:2505:2: ( rule__CharacterClass__ElementsAssignment_3 )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=ExclamationMark && LA26_0<=LeftSquareBracket)||(LA26_0>=CircumflexAccent && LA26_0<=RULE_WORD_BOUNDARY)||(LA26_0>=RULE_CHARACTER_CLASS_ESCAPE && LA26_0<=RULE_CONTROL_LETTER_ESCAPE)||(LA26_0>=RULE_HEX_ESCAPE && LA26_0<=RULE_UNICODE_ESCAPE)||(LA26_0>=RULE_DECIMAL_ESCAPE && LA26_0<=RULE_IDENTITY_ESCAPE)||LA26_0==RULE_UNICODE_DIGIT||(LA26_0>=RULE_UNICODE_LETTER && LA26_0<=RULE_PATTERN_CHARACTER_NO_DASH)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:2505:3: rule__CharacterClass__ElementsAssignment_3
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__CharacterClass__ElementsAssignment_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop26;
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
    // InternalRegularExpressionParser.g:2513:1: rule__CharacterClass__Group__4 : rule__CharacterClass__Group__4__Impl ;
    public final void rule__CharacterClass__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2517:1: ( rule__CharacterClass__Group__4__Impl )
            // InternalRegularExpressionParser.g:2518:2: rule__CharacterClass__Group__4__Impl
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
    // InternalRegularExpressionParser.g:2524:1: rule__CharacterClass__Group__4__Impl : ( RightSquareBracket ) ;
    public final void rule__CharacterClass__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2528:1: ( ( RightSquareBracket ) )
            // InternalRegularExpressionParser.g:2529:1: ( RightSquareBracket )
            {
            // InternalRegularExpressionParser.g:2529:1: ( RightSquareBracket )
            // InternalRegularExpressionParser.g:2530:2: RightSquareBracket
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
    // InternalRegularExpressionParser.g:2540:1: rule__CharacterClass__Group_2__0 : rule__CharacterClass__Group_2__0__Impl ;
    public final void rule__CharacterClass__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2544:1: ( rule__CharacterClass__Group_2__0__Impl )
            // InternalRegularExpressionParser.g:2545:2: rule__CharacterClass__Group_2__0__Impl
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
    // InternalRegularExpressionParser.g:2551:1: rule__CharacterClass__Group_2__0__Impl : ( ( rule__CharacterClass__NegatedAssignment_2_0 ) ) ;
    public final void rule__CharacterClass__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2555:1: ( ( ( rule__CharacterClass__NegatedAssignment_2_0 ) ) )
            // InternalRegularExpressionParser.g:2556:1: ( ( rule__CharacterClass__NegatedAssignment_2_0 ) )
            {
            // InternalRegularExpressionParser.g:2556:1: ( ( rule__CharacterClass__NegatedAssignment_2_0 ) )
            // InternalRegularExpressionParser.g:2557:2: ( rule__CharacterClass__NegatedAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getNegatedAssignment_2_0()); 
            }
            // InternalRegularExpressionParser.g:2558:2: ( rule__CharacterClass__NegatedAssignment_2_0 )
            // InternalRegularExpressionParser.g:2558:3: rule__CharacterClass__NegatedAssignment_2_0
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
    // InternalRegularExpressionParser.g:2567:1: rule__CharacterClassElement__Group__0 : rule__CharacterClassElement__Group__0__Impl rule__CharacterClassElement__Group__1 ;
    public final void rule__CharacterClassElement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2571:1: ( rule__CharacterClassElement__Group__0__Impl rule__CharacterClassElement__Group__1 )
            // InternalRegularExpressionParser.g:2572:2: rule__CharacterClassElement__Group__0__Impl rule__CharacterClassElement__Group__1
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
    // InternalRegularExpressionParser.g:2579:1: rule__CharacterClassElement__Group__0__Impl : ( ruleCharacterClassAtom ) ;
    public final void rule__CharacterClassElement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2583:1: ( ( ruleCharacterClassAtom ) )
            // InternalRegularExpressionParser.g:2584:1: ( ruleCharacterClassAtom )
            {
            // InternalRegularExpressionParser.g:2584:1: ( ruleCharacterClassAtom )
            // InternalRegularExpressionParser.g:2585:2: ruleCharacterClassAtom
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
    // InternalRegularExpressionParser.g:2594:1: rule__CharacterClassElement__Group__1 : rule__CharacterClassElement__Group__1__Impl ;
    public final void rule__CharacterClassElement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2598:1: ( rule__CharacterClassElement__Group__1__Impl )
            // InternalRegularExpressionParser.g:2599:2: rule__CharacterClassElement__Group__1__Impl
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
    // InternalRegularExpressionParser.g:2605:1: rule__CharacterClassElement__Group__1__Impl : ( ( rule__CharacterClassElement__Group_1__0 )? ) ;
    public final void rule__CharacterClassElement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2609:1: ( ( ( rule__CharacterClassElement__Group_1__0 )? ) )
            // InternalRegularExpressionParser.g:2610:1: ( ( rule__CharacterClassElement__Group_1__0 )? )
            {
            // InternalRegularExpressionParser.g:2610:1: ( ( rule__CharacterClassElement__Group_1__0 )? )
            // InternalRegularExpressionParser.g:2611:2: ( rule__CharacterClassElement__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getGroup_1()); 
            }
            // InternalRegularExpressionParser.g:2612:2: ( rule__CharacterClassElement__Group_1__0 )?
            int alt27=2;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalRegularExpressionParser.g:2612:3: rule__CharacterClassElement__Group_1__0
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
    // InternalRegularExpressionParser.g:2621:1: rule__CharacterClassElement__Group_1__0 : rule__CharacterClassElement__Group_1__0__Impl ;
    public final void rule__CharacterClassElement__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2625:1: ( rule__CharacterClassElement__Group_1__0__Impl )
            // InternalRegularExpressionParser.g:2626:2: rule__CharacterClassElement__Group_1__0__Impl
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
    // InternalRegularExpressionParser.g:2632:1: rule__CharacterClassElement__Group_1__0__Impl : ( ( rule__CharacterClassElement__Group_1_0__0 ) ) ;
    public final void rule__CharacterClassElement__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2636:1: ( ( ( rule__CharacterClassElement__Group_1_0__0 ) ) )
            // InternalRegularExpressionParser.g:2637:1: ( ( rule__CharacterClassElement__Group_1_0__0 ) )
            {
            // InternalRegularExpressionParser.g:2637:1: ( ( rule__CharacterClassElement__Group_1_0__0 ) )
            // InternalRegularExpressionParser.g:2638:2: ( rule__CharacterClassElement__Group_1_0__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getGroup_1_0()); 
            }
            // InternalRegularExpressionParser.g:2639:2: ( rule__CharacterClassElement__Group_1_0__0 )
            // InternalRegularExpressionParser.g:2639:3: rule__CharacterClassElement__Group_1_0__0
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
    // InternalRegularExpressionParser.g:2648:1: rule__CharacterClassElement__Group_1_0__0 : rule__CharacterClassElement__Group_1_0__0__Impl rule__CharacterClassElement__Group_1_0__1 ;
    public final void rule__CharacterClassElement__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2652:1: ( rule__CharacterClassElement__Group_1_0__0__Impl rule__CharacterClassElement__Group_1_0__1 )
            // InternalRegularExpressionParser.g:2653:2: rule__CharacterClassElement__Group_1_0__0__Impl rule__CharacterClassElement__Group_1_0__1
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
    // InternalRegularExpressionParser.g:2660:1: rule__CharacterClassElement__Group_1_0__0__Impl : ( () ) ;
    public final void rule__CharacterClassElement__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2664:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2665:1: ( () )
            {
            // InternalRegularExpressionParser.g:2665:1: ( () )
            // InternalRegularExpressionParser.g:2666:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()); 
            }
            // InternalRegularExpressionParser.g:2667:2: ()
            // InternalRegularExpressionParser.g:2667:3: 
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
    // InternalRegularExpressionParser.g:2675:1: rule__CharacterClassElement__Group_1_0__1 : rule__CharacterClassElement__Group_1_0__1__Impl rule__CharacterClassElement__Group_1_0__2 ;
    public final void rule__CharacterClassElement__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2679:1: ( rule__CharacterClassElement__Group_1_0__1__Impl rule__CharacterClassElement__Group_1_0__2 )
            // InternalRegularExpressionParser.g:2680:2: rule__CharacterClassElement__Group_1_0__1__Impl rule__CharacterClassElement__Group_1_0__2
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
    // InternalRegularExpressionParser.g:2687:1: rule__CharacterClassElement__Group_1_0__1__Impl : ( HyphenMinus ) ;
    public final void rule__CharacterClassElement__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2691:1: ( ( HyphenMinus ) )
            // InternalRegularExpressionParser.g:2692:1: ( HyphenMinus )
            {
            // InternalRegularExpressionParser.g:2692:1: ( HyphenMinus )
            // InternalRegularExpressionParser.g:2693:2: HyphenMinus
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
    // InternalRegularExpressionParser.g:2702:1: rule__CharacterClassElement__Group_1_0__2 : rule__CharacterClassElement__Group_1_0__2__Impl ;
    public final void rule__CharacterClassElement__Group_1_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2706:1: ( rule__CharacterClassElement__Group_1_0__2__Impl )
            // InternalRegularExpressionParser.g:2707:2: rule__CharacterClassElement__Group_1_0__2__Impl
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
    // InternalRegularExpressionParser.g:2713:1: rule__CharacterClassElement__Group_1_0__2__Impl : ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) ) ;
    public final void rule__CharacterClassElement__Group_1_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2717:1: ( ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) ) )
            // InternalRegularExpressionParser.g:2718:1: ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) )
            {
            // InternalRegularExpressionParser.g:2718:1: ( ( rule__CharacterClassElement__RightAssignment_1_0_2 ) )
            // InternalRegularExpressionParser.g:2719:2: ( rule__CharacterClassElement__RightAssignment_1_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassElementAccess().getRightAssignment_1_0_2()); 
            }
            // InternalRegularExpressionParser.g:2720:2: ( rule__CharacterClassElement__RightAssignment_1_0_2 )
            // InternalRegularExpressionParser.g:2720:3: rule__CharacterClassElement__RightAssignment_1_0_2
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
    // InternalRegularExpressionParser.g:2729:1: rule__Backspace__Group__0 : rule__Backspace__Group__0__Impl rule__Backspace__Group__1 ;
    public final void rule__Backspace__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2733:1: ( rule__Backspace__Group__0__Impl rule__Backspace__Group__1 )
            // InternalRegularExpressionParser.g:2734:2: rule__Backspace__Group__0__Impl rule__Backspace__Group__1
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
    // InternalRegularExpressionParser.g:2741:1: rule__Backspace__Group__0__Impl : ( () ) ;
    public final void rule__Backspace__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2745:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2746:1: ( () )
            {
            // InternalRegularExpressionParser.g:2746:1: ( () )
            // InternalRegularExpressionParser.g:2747:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBackspaceAccess().getBackspaceAction_0()); 
            }
            // InternalRegularExpressionParser.g:2748:2: ()
            // InternalRegularExpressionParser.g:2748:3: 
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
    // InternalRegularExpressionParser.g:2756:1: rule__Backspace__Group__1 : rule__Backspace__Group__1__Impl ;
    public final void rule__Backspace__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2760:1: ( rule__Backspace__Group__1__Impl )
            // InternalRegularExpressionParser.g:2761:2: rule__Backspace__Group__1__Impl
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
    // InternalRegularExpressionParser.g:2767:1: rule__Backspace__Group__1__Impl : ( RULE_WORD_BOUNDARY ) ;
    public final void rule__Backspace__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2771:1: ( ( RULE_WORD_BOUNDARY ) )
            // InternalRegularExpressionParser.g:2772:1: ( RULE_WORD_BOUNDARY )
            {
            // InternalRegularExpressionParser.g:2772:1: ( RULE_WORD_BOUNDARY )
            // InternalRegularExpressionParser.g:2773:2: RULE_WORD_BOUNDARY
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
    // InternalRegularExpressionParser.g:2783:1: rule__Group__Group__0 : rule__Group__Group__0__Impl rule__Group__Group__1 ;
    public final void rule__Group__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2787:1: ( rule__Group__Group__0__Impl rule__Group__Group__1 )
            // InternalRegularExpressionParser.g:2788:2: rule__Group__Group__0__Impl rule__Group__Group__1
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
    // InternalRegularExpressionParser.g:2795:1: rule__Group__Group__0__Impl : ( () ) ;
    public final void rule__Group__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2799:1: ( ( () ) )
            // InternalRegularExpressionParser.g:2800:1: ( () )
            {
            // InternalRegularExpressionParser.g:2800:1: ( () )
            // InternalRegularExpressionParser.g:2801:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getGroupAction_0()); 
            }
            // InternalRegularExpressionParser.g:2802:2: ()
            // InternalRegularExpressionParser.g:2802:3: 
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
    // InternalRegularExpressionParser.g:2810:1: rule__Group__Group__1 : rule__Group__Group__1__Impl rule__Group__Group__2 ;
    public final void rule__Group__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2814:1: ( rule__Group__Group__1__Impl rule__Group__Group__2 )
            // InternalRegularExpressionParser.g:2815:2: rule__Group__Group__1__Impl rule__Group__Group__2
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
    // InternalRegularExpressionParser.g:2822:1: rule__Group__Group__1__Impl : ( LeftParenthesis ) ;
    public final void rule__Group__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2826:1: ( ( LeftParenthesis ) )
            // InternalRegularExpressionParser.g:2827:1: ( LeftParenthesis )
            {
            // InternalRegularExpressionParser.g:2827:1: ( LeftParenthesis )
            // InternalRegularExpressionParser.g:2828:2: LeftParenthesis
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
    // InternalRegularExpressionParser.g:2837:1: rule__Group__Group__2 : rule__Group__Group__2__Impl rule__Group__Group__3 ;
    public final void rule__Group__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2841:1: ( rule__Group__Group__2__Impl rule__Group__Group__3 )
            // InternalRegularExpressionParser.g:2842:2: rule__Group__Group__2__Impl rule__Group__Group__3
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
    // InternalRegularExpressionParser.g:2849:1: rule__Group__Group__2__Impl : ( ( rule__Group__Group_2__0 )? ) ;
    public final void rule__Group__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2853:1: ( ( ( rule__Group__Group_2__0 )? ) )
            // InternalRegularExpressionParser.g:2854:1: ( ( rule__Group__Group_2__0 )? )
            {
            // InternalRegularExpressionParser.g:2854:1: ( ( rule__Group__Group_2__0 )? )
            // InternalRegularExpressionParser.g:2855:2: ( rule__Group__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getGroup_2()); 
            }
            // InternalRegularExpressionParser.g:2856:2: ( rule__Group__Group_2__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==QuestionMark) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalRegularExpressionParser.g:2856:3: rule__Group__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Group__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getGroup_2()); 
            }

            }


            }

        }
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
    // InternalRegularExpressionParser.g:2864:1: rule__Group__Group__3 : rule__Group__Group__3__Impl rule__Group__Group__4 ;
    public final void rule__Group__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2868:1: ( rule__Group__Group__3__Impl rule__Group__Group__4 )
            // InternalRegularExpressionParser.g:2869:2: rule__Group__Group__3__Impl rule__Group__Group__4
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
    // InternalRegularExpressionParser.g:2876:1: rule__Group__Group__3__Impl : ( ( rule__Group__PatternAssignment_3 ) ) ;
    public final void rule__Group__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2880:1: ( ( ( rule__Group__PatternAssignment_3 ) ) )
            // InternalRegularExpressionParser.g:2881:1: ( ( rule__Group__PatternAssignment_3 ) )
            {
            // InternalRegularExpressionParser.g:2881:1: ( ( rule__Group__PatternAssignment_3 ) )
            // InternalRegularExpressionParser.g:2882:2: ( rule__Group__PatternAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getPatternAssignment_3()); 
            }
            // InternalRegularExpressionParser.g:2883:2: ( rule__Group__PatternAssignment_3 )
            // InternalRegularExpressionParser.g:2883:3: rule__Group__PatternAssignment_3
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
    // InternalRegularExpressionParser.g:2891:1: rule__Group__Group__4 : rule__Group__Group__4__Impl ;
    public final void rule__Group__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2895:1: ( rule__Group__Group__4__Impl )
            // InternalRegularExpressionParser.g:2896:2: rule__Group__Group__4__Impl
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
    // InternalRegularExpressionParser.g:2902:1: rule__Group__Group__4__Impl : ( RightParenthesis ) ;
    public final void rule__Group__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2906:1: ( ( RightParenthesis ) )
            // InternalRegularExpressionParser.g:2907:1: ( RightParenthesis )
            {
            // InternalRegularExpressionParser.g:2907:1: ( RightParenthesis )
            // InternalRegularExpressionParser.g:2908:2: RightParenthesis
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


    // $ANTLR start "rule__Group__Group_2__0"
    // InternalRegularExpressionParser.g:2918:1: rule__Group__Group_2__0 : rule__Group__Group_2__0__Impl rule__Group__Group_2__1 ;
    public final void rule__Group__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2922:1: ( rule__Group__Group_2__0__Impl rule__Group__Group_2__1 )
            // InternalRegularExpressionParser.g:2923:2: rule__Group__Group_2__0__Impl rule__Group__Group_2__1
            {
            pushFollow(FOLLOW_27);
            rule__Group__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Group__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2__0"


    // $ANTLR start "rule__Group__Group_2__0__Impl"
    // InternalRegularExpressionParser.g:2930:1: rule__Group__Group_2__0__Impl : ( ( rule__Group__NonCapturingAssignment_2_0 ) ) ;
    public final void rule__Group__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2934:1: ( ( ( rule__Group__NonCapturingAssignment_2_0 ) ) )
            // InternalRegularExpressionParser.g:2935:1: ( ( rule__Group__NonCapturingAssignment_2_0 ) )
            {
            // InternalRegularExpressionParser.g:2935:1: ( ( rule__Group__NonCapturingAssignment_2_0 ) )
            // InternalRegularExpressionParser.g:2936:2: ( rule__Group__NonCapturingAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNonCapturingAssignment_2_0()); 
            }
            // InternalRegularExpressionParser.g:2937:2: ( rule__Group__NonCapturingAssignment_2_0 )
            // InternalRegularExpressionParser.g:2937:3: rule__Group__NonCapturingAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__Group__NonCapturingAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNonCapturingAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2__0__Impl"


    // $ANTLR start "rule__Group__Group_2__1"
    // InternalRegularExpressionParser.g:2945:1: rule__Group__Group_2__1 : rule__Group__Group_2__1__Impl ;
    public final void rule__Group__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2949:1: ( rule__Group__Group_2__1__Impl )
            // InternalRegularExpressionParser.g:2950:2: rule__Group__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Group__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2__1"


    // $ANTLR start "rule__Group__Group_2__1__Impl"
    // InternalRegularExpressionParser.g:2956:1: rule__Group__Group_2__1__Impl : ( Colon ) ;
    public final void rule__Group__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2960:1: ( ( Colon ) )
            // InternalRegularExpressionParser.g:2961:1: ( Colon )
            {
            // InternalRegularExpressionParser.g:2961:1: ( Colon )
            // InternalRegularExpressionParser.g:2962:2: Colon
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getColonKeyword_2_1()); 
            }
            match(input,Colon,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getColonKeyword_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__Group_2__1__Impl"


    // $ANTLR start "rule__SimpleQuantifier__Group__0"
    // InternalRegularExpressionParser.g:2972:1: rule__SimpleQuantifier__Group__0 : rule__SimpleQuantifier__Group__0__Impl rule__SimpleQuantifier__Group__1 ;
    public final void rule__SimpleQuantifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2976:1: ( rule__SimpleQuantifier__Group__0__Impl rule__SimpleQuantifier__Group__1 )
            // InternalRegularExpressionParser.g:2977:2: rule__SimpleQuantifier__Group__0__Impl rule__SimpleQuantifier__Group__1
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
    // InternalRegularExpressionParser.g:2984:1: rule__SimpleQuantifier__Group__0__Impl : ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) ) ;
    public final void rule__SimpleQuantifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:2988:1: ( ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) ) )
            // InternalRegularExpressionParser.g:2989:1: ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) )
            {
            // InternalRegularExpressionParser.g:2989:1: ( ( rule__SimpleQuantifier__QuantifierAssignment_0 ) )
            // InternalRegularExpressionParser.g:2990:2: ( rule__SimpleQuantifier__QuantifierAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAssignment_0()); 
            }
            // InternalRegularExpressionParser.g:2991:2: ( rule__SimpleQuantifier__QuantifierAssignment_0 )
            // InternalRegularExpressionParser.g:2991:3: rule__SimpleQuantifier__QuantifierAssignment_0
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
    // InternalRegularExpressionParser.g:2999:1: rule__SimpleQuantifier__Group__1 : rule__SimpleQuantifier__Group__1__Impl ;
    public final void rule__SimpleQuantifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3003:1: ( rule__SimpleQuantifier__Group__1__Impl )
            // InternalRegularExpressionParser.g:3004:2: rule__SimpleQuantifier__Group__1__Impl
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
    // InternalRegularExpressionParser.g:3010:1: rule__SimpleQuantifier__Group__1__Impl : ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? ) ;
    public final void rule__SimpleQuantifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3014:1: ( ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? ) )
            // InternalRegularExpressionParser.g:3015:1: ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? )
            {
            // InternalRegularExpressionParser.g:3015:1: ( ( rule__SimpleQuantifier__NonGreedyAssignment_1 )? )
            // InternalRegularExpressionParser.g:3016:2: ( rule__SimpleQuantifier__NonGreedyAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyAssignment_1()); 
            }
            // InternalRegularExpressionParser.g:3017:2: ( rule__SimpleQuantifier__NonGreedyAssignment_1 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==QuestionMark) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalRegularExpressionParser.g:3017:3: rule__SimpleQuantifier__NonGreedyAssignment_1
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
    // InternalRegularExpressionParser.g:3026:1: rule__ExactQuantifier__Group__0 : rule__ExactQuantifier__Group__0__Impl rule__ExactQuantifier__Group__1 ;
    public final void rule__ExactQuantifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3030:1: ( rule__ExactQuantifier__Group__0__Impl rule__ExactQuantifier__Group__1 )
            // InternalRegularExpressionParser.g:3031:2: rule__ExactQuantifier__Group__0__Impl rule__ExactQuantifier__Group__1
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
    // InternalRegularExpressionParser.g:3038:1: rule__ExactQuantifier__Group__0__Impl : ( () ) ;
    public final void rule__ExactQuantifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3042:1: ( ( () ) )
            // InternalRegularExpressionParser.g:3043:1: ( () )
            {
            // InternalRegularExpressionParser.g:3043:1: ( () )
            // InternalRegularExpressionParser.g:3044:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0()); 
            }
            // InternalRegularExpressionParser.g:3045:2: ()
            // InternalRegularExpressionParser.g:3045:3: 
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
    // InternalRegularExpressionParser.g:3053:1: rule__ExactQuantifier__Group__1 : rule__ExactQuantifier__Group__1__Impl rule__ExactQuantifier__Group__2 ;
    public final void rule__ExactQuantifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3057:1: ( rule__ExactQuantifier__Group__1__Impl rule__ExactQuantifier__Group__2 )
            // InternalRegularExpressionParser.g:3058:2: rule__ExactQuantifier__Group__1__Impl rule__ExactQuantifier__Group__2
            {
            pushFollow(FOLLOW_28);
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
    // InternalRegularExpressionParser.g:3065:1: rule__ExactQuantifier__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ExactQuantifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3069:1: ( ( LeftCurlyBracket ) )
            // InternalRegularExpressionParser.g:3070:1: ( LeftCurlyBracket )
            {
            // InternalRegularExpressionParser.g:3070:1: ( LeftCurlyBracket )
            // InternalRegularExpressionParser.g:3071:2: LeftCurlyBracket
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
    // InternalRegularExpressionParser.g:3080:1: rule__ExactQuantifier__Group__2 : rule__ExactQuantifier__Group__2__Impl rule__ExactQuantifier__Group__3 ;
    public final void rule__ExactQuantifier__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3084:1: ( rule__ExactQuantifier__Group__2__Impl rule__ExactQuantifier__Group__3 )
            // InternalRegularExpressionParser.g:3085:2: rule__ExactQuantifier__Group__2__Impl rule__ExactQuantifier__Group__3
            {
            pushFollow(FOLLOW_29);
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
    // InternalRegularExpressionParser.g:3092:1: rule__ExactQuantifier__Group__2__Impl : ( ( rule__ExactQuantifier__MinAssignment_2 ) ) ;
    public final void rule__ExactQuantifier__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3096:1: ( ( ( rule__ExactQuantifier__MinAssignment_2 ) ) )
            // InternalRegularExpressionParser.g:3097:1: ( ( rule__ExactQuantifier__MinAssignment_2 ) )
            {
            // InternalRegularExpressionParser.g:3097:1: ( ( rule__ExactQuantifier__MinAssignment_2 ) )
            // InternalRegularExpressionParser.g:3098:2: ( rule__ExactQuantifier__MinAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getMinAssignment_2()); 
            }
            // InternalRegularExpressionParser.g:3099:2: ( rule__ExactQuantifier__MinAssignment_2 )
            // InternalRegularExpressionParser.g:3099:3: rule__ExactQuantifier__MinAssignment_2
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
    // InternalRegularExpressionParser.g:3107:1: rule__ExactQuantifier__Group__3 : rule__ExactQuantifier__Group__3__Impl rule__ExactQuantifier__Group__4 ;
    public final void rule__ExactQuantifier__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3111:1: ( rule__ExactQuantifier__Group__3__Impl rule__ExactQuantifier__Group__4 )
            // InternalRegularExpressionParser.g:3112:2: rule__ExactQuantifier__Group__3__Impl rule__ExactQuantifier__Group__4
            {
            pushFollow(FOLLOW_29);
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
    // InternalRegularExpressionParser.g:3119:1: rule__ExactQuantifier__Group__3__Impl : ( ( rule__ExactQuantifier__Alternatives_3 )? ) ;
    public final void rule__ExactQuantifier__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3123:1: ( ( ( rule__ExactQuantifier__Alternatives_3 )? ) )
            // InternalRegularExpressionParser.g:3124:1: ( ( rule__ExactQuantifier__Alternatives_3 )? )
            {
            // InternalRegularExpressionParser.g:3124:1: ( ( rule__ExactQuantifier__Alternatives_3 )? )
            // InternalRegularExpressionParser.g:3125:2: ( rule__ExactQuantifier__Alternatives_3 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getAlternatives_3()); 
            }
            // InternalRegularExpressionParser.g:3126:2: ( rule__ExactQuantifier__Alternatives_3 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==Comma) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalRegularExpressionParser.g:3126:3: rule__ExactQuantifier__Alternatives_3
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
    // InternalRegularExpressionParser.g:3134:1: rule__ExactQuantifier__Group__4 : rule__ExactQuantifier__Group__4__Impl rule__ExactQuantifier__Group__5 ;
    public final void rule__ExactQuantifier__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3138:1: ( rule__ExactQuantifier__Group__4__Impl rule__ExactQuantifier__Group__5 )
            // InternalRegularExpressionParser.g:3139:2: rule__ExactQuantifier__Group__4__Impl rule__ExactQuantifier__Group__5
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
    // InternalRegularExpressionParser.g:3146:1: rule__ExactQuantifier__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ExactQuantifier__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3150:1: ( ( RightCurlyBracket ) )
            // InternalRegularExpressionParser.g:3151:1: ( RightCurlyBracket )
            {
            // InternalRegularExpressionParser.g:3151:1: ( RightCurlyBracket )
            // InternalRegularExpressionParser.g:3152:2: RightCurlyBracket
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
    // InternalRegularExpressionParser.g:3161:1: rule__ExactQuantifier__Group__5 : rule__ExactQuantifier__Group__5__Impl ;
    public final void rule__ExactQuantifier__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3165:1: ( rule__ExactQuantifier__Group__5__Impl )
            // InternalRegularExpressionParser.g:3166:2: rule__ExactQuantifier__Group__5__Impl
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
    // InternalRegularExpressionParser.g:3172:1: rule__ExactQuantifier__Group__5__Impl : ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? ) ;
    public final void rule__ExactQuantifier__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3176:1: ( ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? ) )
            // InternalRegularExpressionParser.g:3177:1: ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? )
            {
            // InternalRegularExpressionParser.g:3177:1: ( ( rule__ExactQuantifier__NonGreedyAssignment_5 )? )
            // InternalRegularExpressionParser.g:3178:2: ( rule__ExactQuantifier__NonGreedyAssignment_5 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getNonGreedyAssignment_5()); 
            }
            // InternalRegularExpressionParser.g:3179:2: ( rule__ExactQuantifier__NonGreedyAssignment_5 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==QuestionMark) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalRegularExpressionParser.g:3179:3: rule__ExactQuantifier__NonGreedyAssignment_5
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
    // InternalRegularExpressionParser.g:3188:1: rule__ExactQuantifier__Group_3_0__0 : rule__ExactQuantifier__Group_3_0__0__Impl rule__ExactQuantifier__Group_3_0__1 ;
    public final void rule__ExactQuantifier__Group_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3192:1: ( rule__ExactQuantifier__Group_3_0__0__Impl rule__ExactQuantifier__Group_3_0__1 )
            // InternalRegularExpressionParser.g:3193:2: rule__ExactQuantifier__Group_3_0__0__Impl rule__ExactQuantifier__Group_3_0__1
            {
            pushFollow(FOLLOW_28);
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
    // InternalRegularExpressionParser.g:3200:1: rule__ExactQuantifier__Group_3_0__0__Impl : ( Comma ) ;
    public final void rule__ExactQuantifier__Group_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3204:1: ( ( Comma ) )
            // InternalRegularExpressionParser.g:3205:1: ( Comma )
            {
            // InternalRegularExpressionParser.g:3205:1: ( Comma )
            // InternalRegularExpressionParser.g:3206:2: Comma
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
    // InternalRegularExpressionParser.g:3215:1: rule__ExactQuantifier__Group_3_0__1 : rule__ExactQuantifier__Group_3_0__1__Impl ;
    public final void rule__ExactQuantifier__Group_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3219:1: ( rule__ExactQuantifier__Group_3_0__1__Impl )
            // InternalRegularExpressionParser.g:3220:2: rule__ExactQuantifier__Group_3_0__1__Impl
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
    // InternalRegularExpressionParser.g:3226:1: rule__ExactQuantifier__Group_3_0__1__Impl : ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) ) ;
    public final void rule__ExactQuantifier__Group_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3230:1: ( ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) ) )
            // InternalRegularExpressionParser.g:3231:1: ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) )
            {
            // InternalRegularExpressionParser.g:3231:1: ( ( rule__ExactQuantifier__MaxAssignment_3_0_1 ) )
            // InternalRegularExpressionParser.g:3232:2: ( rule__ExactQuantifier__MaxAssignment_3_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getMaxAssignment_3_0_1()); 
            }
            // InternalRegularExpressionParser.g:3233:2: ( rule__ExactQuantifier__MaxAssignment_3_0_1 )
            // InternalRegularExpressionParser.g:3233:3: rule__ExactQuantifier__MaxAssignment_3_0_1
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
    // InternalRegularExpressionParser.g:3242:1: rule__RegularExpressionFlags__Group__0 : rule__RegularExpressionFlags__Group__0__Impl rule__RegularExpressionFlags__Group__1 ;
    public final void rule__RegularExpressionFlags__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3246:1: ( rule__RegularExpressionFlags__Group__0__Impl rule__RegularExpressionFlags__Group__1 )
            // InternalRegularExpressionParser.g:3247:2: rule__RegularExpressionFlags__Group__0__Impl rule__RegularExpressionFlags__Group__1
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
    // InternalRegularExpressionParser.g:3254:1: rule__RegularExpressionFlags__Group__0__Impl : ( () ) ;
    public final void rule__RegularExpressionFlags__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3258:1: ( ( () ) )
            // InternalRegularExpressionParser.g:3259:1: ( () )
            {
            // InternalRegularExpressionParser.g:3259:1: ( () )
            // InternalRegularExpressionParser.g:3260:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0()); 
            }
            // InternalRegularExpressionParser.g:3261:2: ()
            // InternalRegularExpressionParser.g:3261:3: 
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
    // InternalRegularExpressionParser.g:3269:1: rule__RegularExpressionFlags__Group__1 : rule__RegularExpressionFlags__Group__1__Impl ;
    public final void rule__RegularExpressionFlags__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3273:1: ( rule__RegularExpressionFlags__Group__1__Impl )
            // InternalRegularExpressionParser.g:3274:2: rule__RegularExpressionFlags__Group__1__Impl
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
    // InternalRegularExpressionParser.g:3280:1: rule__RegularExpressionFlags__Group__1__Impl : ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* ) ;
    public final void rule__RegularExpressionFlags__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3284:1: ( ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* ) )
            // InternalRegularExpressionParser.g:3285:1: ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* )
            {
            // InternalRegularExpressionParser.g:3285:1: ( ( rule__RegularExpressionFlags__FlagsAssignment_1 )* )
            // InternalRegularExpressionParser.g:3286:2: ( rule__RegularExpressionFlags__FlagsAssignment_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAssignment_1()); 
            }
            // InternalRegularExpressionParser.g:3287:2: ( rule__RegularExpressionFlags__FlagsAssignment_1 )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_UNICODE_ESCAPE||LA32_0==RULE_UNICODE_LETTER) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalRegularExpressionParser.g:3287:3: rule__RegularExpressionFlags__FlagsAssignment_1
            	    {
            	    pushFollow(FOLLOW_30);
            	    rule__RegularExpressionFlags__FlagsAssignment_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop32;
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
    // InternalRegularExpressionParser.g:3296:1: rule__RegularExpressionLiteral__BodyAssignment_1 : ( ruleRegularExpressionBody ) ;
    public final void rule__RegularExpressionLiteral__BodyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3300:1: ( ( ruleRegularExpressionBody ) )
            // InternalRegularExpressionParser.g:3301:2: ( ruleRegularExpressionBody )
            {
            // InternalRegularExpressionParser.g:3301:2: ( ruleRegularExpressionBody )
            // InternalRegularExpressionParser.g:3302:3: ruleRegularExpressionBody
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
    // InternalRegularExpressionParser.g:3311:1: rule__RegularExpressionLiteral__FlagsAssignment_3 : ( ruleRegularExpressionFlags ) ;
    public final void rule__RegularExpressionLiteral__FlagsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3315:1: ( ( ruleRegularExpressionFlags ) )
            // InternalRegularExpressionParser.g:3316:2: ( ruleRegularExpressionFlags )
            {
            // InternalRegularExpressionParser.g:3316:2: ( ruleRegularExpressionFlags )
            // InternalRegularExpressionParser.g:3317:3: ruleRegularExpressionFlags
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
    // InternalRegularExpressionParser.g:3326:1: rule__RegularExpressionBody__PatternAssignment : ( ruleDisjunction ) ;
    public final void rule__RegularExpressionBody__PatternAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3330:1: ( ( ruleDisjunction ) )
            // InternalRegularExpressionParser.g:3331:2: ( ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:3331:2: ( ruleDisjunction )
            // InternalRegularExpressionParser.g:3332:3: ruleDisjunction
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
    // InternalRegularExpressionParser.g:3341:1: rule__Disjunction__ElementsAssignment_0_1_1_1 : ( ruleAlternative ) ;
    public final void rule__Disjunction__ElementsAssignment_0_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3345:1: ( ( ruleAlternative ) )
            // InternalRegularExpressionParser.g:3346:2: ( ruleAlternative )
            {
            // InternalRegularExpressionParser.g:3346:2: ( ruleAlternative )
            // InternalRegularExpressionParser.g:3347:3: ruleAlternative
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
    // InternalRegularExpressionParser.g:3356:1: rule__Disjunction__ElementsAssignment_1_1_1 : ( ruleAlternative ) ;
    public final void rule__Disjunction__ElementsAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3360:1: ( ( ruleAlternative ) )
            // InternalRegularExpressionParser.g:3361:2: ( ruleAlternative )
            {
            // InternalRegularExpressionParser.g:3361:2: ( ruleAlternative )
            // InternalRegularExpressionParser.g:3362:3: ruleAlternative
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
    // InternalRegularExpressionParser.g:3371:1: rule__Alternative__ElementsAssignment_1_1 : ( ruleTerm ) ;
    public final void rule__Alternative__ElementsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3375:1: ( ( ruleTerm ) )
            // InternalRegularExpressionParser.g:3376:2: ( ruleTerm )
            {
            // InternalRegularExpressionParser.g:3376:2: ( ruleTerm )
            // InternalRegularExpressionParser.g:3377:3: ruleTerm
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
    // InternalRegularExpressionParser.g:3386:1: rule__Term__QuantifierAssignment_1_1 : ( ruleQuantifier ) ;
    public final void rule__Term__QuantifierAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3390:1: ( ( ruleQuantifier ) )
            // InternalRegularExpressionParser.g:3391:2: ( ruleQuantifier )
            {
            // InternalRegularExpressionParser.g:3391:2: ( ruleQuantifier )
            // InternalRegularExpressionParser.g:3392:3: ruleQuantifier
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
    // InternalRegularExpressionParser.g:3401:1: rule__WordBoundary__NotAssignment_1_1 : ( RULE_NOT_WORD_BOUNDARY ) ;
    public final void rule__WordBoundary__NotAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3405:1: ( ( RULE_NOT_WORD_BOUNDARY ) )
            // InternalRegularExpressionParser.g:3406:2: ( RULE_NOT_WORD_BOUNDARY )
            {
            // InternalRegularExpressionParser.g:3406:2: ( RULE_NOT_WORD_BOUNDARY )
            // InternalRegularExpressionParser.g:3407:3: RULE_NOT_WORD_BOUNDARY
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


    // $ANTLR start "rule__LookAhead__NotAssignment_3_1"
    // InternalRegularExpressionParser.g:3416:1: rule__LookAhead__NotAssignment_3_1 : ( ( ExclamationMark ) ) ;
    public final void rule__LookAhead__NotAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3420:1: ( ( ( ExclamationMark ) ) )
            // InternalRegularExpressionParser.g:3421:2: ( ( ExclamationMark ) )
            {
            // InternalRegularExpressionParser.g:3421:2: ( ( ExclamationMark ) )
            // InternalRegularExpressionParser.g:3422:3: ( ExclamationMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); 
            }
            // InternalRegularExpressionParser.g:3423:3: ( ExclamationMark )
            // InternalRegularExpressionParser.g:3424:4: ExclamationMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); 
            }
            match(input,ExclamationMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__NotAssignment_3_1"


    // $ANTLR start "rule__LookAhead__PatternAssignment_4"
    // InternalRegularExpressionParser.g:3435:1: rule__LookAhead__PatternAssignment_4 : ( ruleDisjunction ) ;
    public final void rule__LookAhead__PatternAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3439:1: ( ( ruleDisjunction ) )
            // InternalRegularExpressionParser.g:3440:2: ( ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:3440:2: ( ruleDisjunction )
            // InternalRegularExpressionParser.g:3441:3: ruleDisjunction
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLookAheadAccess().getPatternDisjunctionParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDisjunction();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLookAheadAccess().getPatternDisjunctionParserRuleCall_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LookAhead__PatternAssignment_4"


    // $ANTLR start "rule__PatternCharacter__ValueAssignment"
    // InternalRegularExpressionParser.g:3450:1: rule__PatternCharacter__ValueAssignment : ( ( rule__PatternCharacter__ValueAlternatives_0 ) ) ;
    public final void rule__PatternCharacter__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3454:1: ( ( ( rule__PatternCharacter__ValueAlternatives_0 ) ) )
            // InternalRegularExpressionParser.g:3455:2: ( ( rule__PatternCharacter__ValueAlternatives_0 ) )
            {
            // InternalRegularExpressionParser.g:3455:2: ( ( rule__PatternCharacter__ValueAlternatives_0 ) )
            // InternalRegularExpressionParser.g:3456:3: ( rule__PatternCharacter__ValueAlternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPatternCharacterAccess().getValueAlternatives_0()); 
            }
            // InternalRegularExpressionParser.g:3457:3: ( rule__PatternCharacter__ValueAlternatives_0 )
            // InternalRegularExpressionParser.g:3457:4: rule__PatternCharacter__ValueAlternatives_0
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
    // InternalRegularExpressionParser.g:3465:1: rule__CharacterClassEscapeSequence__SequenceAssignment : ( RULE_CHARACTER_CLASS_ESCAPE ) ;
    public final void rule__CharacterClassEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3469:1: ( ( RULE_CHARACTER_CLASS_ESCAPE ) )
            // InternalRegularExpressionParser.g:3470:2: ( RULE_CHARACTER_CLASS_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3470:2: ( RULE_CHARACTER_CLASS_ESCAPE )
            // InternalRegularExpressionParser.g:3471:3: RULE_CHARACTER_CLASS_ESCAPE
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
    // InternalRegularExpressionParser.g:3480:1: rule__CharacterEscapeSequence__SequenceAssignment : ( RULE_CONTROL_ESCAPE ) ;
    public final void rule__CharacterEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3484:1: ( ( RULE_CONTROL_ESCAPE ) )
            // InternalRegularExpressionParser.g:3485:2: ( RULE_CONTROL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3485:2: ( RULE_CONTROL_ESCAPE )
            // InternalRegularExpressionParser.g:3486:3: RULE_CONTROL_ESCAPE
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
    // InternalRegularExpressionParser.g:3495:1: rule__ControlLetterEscapeSequence__SequenceAssignment : ( RULE_CONTROL_LETTER_ESCAPE ) ;
    public final void rule__ControlLetterEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3499:1: ( ( RULE_CONTROL_LETTER_ESCAPE ) )
            // InternalRegularExpressionParser.g:3500:2: ( RULE_CONTROL_LETTER_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3500:2: ( RULE_CONTROL_LETTER_ESCAPE )
            // InternalRegularExpressionParser.g:3501:3: RULE_CONTROL_LETTER_ESCAPE
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
    // InternalRegularExpressionParser.g:3510:1: rule__HexEscapeSequence__SequenceAssignment : ( RULE_HEX_ESCAPE ) ;
    public final void rule__HexEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3514:1: ( ( RULE_HEX_ESCAPE ) )
            // InternalRegularExpressionParser.g:3515:2: ( RULE_HEX_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3515:2: ( RULE_HEX_ESCAPE )
            // InternalRegularExpressionParser.g:3516:3: RULE_HEX_ESCAPE
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
    // InternalRegularExpressionParser.g:3525:1: rule__UnicodeEscapeSequence__SequenceAssignment : ( RULE_UNICODE_ESCAPE ) ;
    public final void rule__UnicodeEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3529:1: ( ( RULE_UNICODE_ESCAPE ) )
            // InternalRegularExpressionParser.g:3530:2: ( RULE_UNICODE_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3530:2: ( RULE_UNICODE_ESCAPE )
            // InternalRegularExpressionParser.g:3531:3: RULE_UNICODE_ESCAPE
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
    // InternalRegularExpressionParser.g:3540:1: rule__IdentityEscapeSequence__SequenceAssignment : ( RULE_IDENTITY_ESCAPE ) ;
    public final void rule__IdentityEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3544:1: ( ( RULE_IDENTITY_ESCAPE ) )
            // InternalRegularExpressionParser.g:3545:2: ( RULE_IDENTITY_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3545:2: ( RULE_IDENTITY_ESCAPE )
            // InternalRegularExpressionParser.g:3546:3: RULE_IDENTITY_ESCAPE
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
    // InternalRegularExpressionParser.g:3555:1: rule__DecimalEscapeSequence__SequenceAssignment : ( RULE_DECIMAL_ESCAPE ) ;
    public final void rule__DecimalEscapeSequence__SequenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3559:1: ( ( RULE_DECIMAL_ESCAPE ) )
            // InternalRegularExpressionParser.g:3560:2: ( RULE_DECIMAL_ESCAPE )
            {
            // InternalRegularExpressionParser.g:3560:2: ( RULE_DECIMAL_ESCAPE )
            // InternalRegularExpressionParser.g:3561:3: RULE_DECIMAL_ESCAPE
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
    // InternalRegularExpressionParser.g:3570:1: rule__CharacterClass__NegatedAssignment_2_0 : ( ( CircumflexAccent ) ) ;
    public final void rule__CharacterClass__NegatedAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3574:1: ( ( ( CircumflexAccent ) ) )
            // InternalRegularExpressionParser.g:3575:2: ( ( CircumflexAccent ) )
            {
            // InternalRegularExpressionParser.g:3575:2: ( ( CircumflexAccent ) )
            // InternalRegularExpressionParser.g:3576:3: ( CircumflexAccent )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); 
            }
            // InternalRegularExpressionParser.g:3577:3: ( CircumflexAccent )
            // InternalRegularExpressionParser.g:3578:4: CircumflexAccent
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
    // InternalRegularExpressionParser.g:3589:1: rule__CharacterClass__ElementsAssignment_3 : ( ruleCharacterClassElement ) ;
    public final void rule__CharacterClass__ElementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3593:1: ( ( ruleCharacterClassElement ) )
            // InternalRegularExpressionParser.g:3594:2: ( ruleCharacterClassElement )
            {
            // InternalRegularExpressionParser.g:3594:2: ( ruleCharacterClassElement )
            // InternalRegularExpressionParser.g:3595:3: ruleCharacterClassElement
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
    // InternalRegularExpressionParser.g:3604:1: rule__CharacterClassElement__RightAssignment_1_0_2 : ( ruleCharacterClassAtom ) ;
    public final void rule__CharacterClassElement__RightAssignment_1_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3608:1: ( ( ruleCharacterClassAtom ) )
            // InternalRegularExpressionParser.g:3609:2: ( ruleCharacterClassAtom )
            {
            // InternalRegularExpressionParser.g:3609:2: ( ruleCharacterClassAtom )
            // InternalRegularExpressionParser.g:3610:3: ruleCharacterClassAtom
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
    // InternalRegularExpressionParser.g:3619:1: rule__CharacterClassAtom__CharacterAssignment_1 : ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) ) ;
    public final void rule__CharacterClassAtom__CharacterAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3623:1: ( ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) ) )
            // InternalRegularExpressionParser.g:3624:2: ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) )
            {
            // InternalRegularExpressionParser.g:3624:2: ( ( rule__CharacterClassAtom__CharacterAlternatives_1_0 ) )
            // InternalRegularExpressionParser.g:3625:3: ( rule__CharacterClassAtom__CharacterAlternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCharacterClassAtomAccess().getCharacterAlternatives_1_0()); 
            }
            // InternalRegularExpressionParser.g:3626:3: ( rule__CharacterClassAtom__CharacterAlternatives_1_0 )
            // InternalRegularExpressionParser.g:3626:4: rule__CharacterClassAtom__CharacterAlternatives_1_0
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


    // $ANTLR start "rule__Group__NonCapturingAssignment_2_0"
    // InternalRegularExpressionParser.g:3634:1: rule__Group__NonCapturingAssignment_2_0 : ( ( QuestionMark ) ) ;
    public final void rule__Group__NonCapturingAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3638:1: ( ( ( QuestionMark ) ) )
            // InternalRegularExpressionParser.g:3639:2: ( ( QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:3639:2: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:3640:3: ( QuestionMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); 
            }
            // InternalRegularExpressionParser.g:3641:3: ( QuestionMark )
            // InternalRegularExpressionParser.g:3642:4: QuestionMark
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); 
            }
            match(input,QuestionMark,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Group__NonCapturingAssignment_2_0"


    // $ANTLR start "rule__Group__PatternAssignment_3"
    // InternalRegularExpressionParser.g:3653:1: rule__Group__PatternAssignment_3 : ( ruleDisjunction ) ;
    public final void rule__Group__PatternAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3657:1: ( ( ruleDisjunction ) )
            // InternalRegularExpressionParser.g:3658:2: ( ruleDisjunction )
            {
            // InternalRegularExpressionParser.g:3658:2: ( ruleDisjunction )
            // InternalRegularExpressionParser.g:3659:3: ruleDisjunction
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
    // InternalRegularExpressionParser.g:3668:1: rule__SimpleQuantifier__QuantifierAssignment_0 : ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) ) ;
    public final void rule__SimpleQuantifier__QuantifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3672:1: ( ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) ) )
            // InternalRegularExpressionParser.g:3673:2: ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) )
            {
            // InternalRegularExpressionParser.g:3673:2: ( ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 ) )
            // InternalRegularExpressionParser.g:3674:3: ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAlternatives_0_0()); 
            }
            // InternalRegularExpressionParser.g:3675:3: ( rule__SimpleQuantifier__QuantifierAlternatives_0_0 )
            // InternalRegularExpressionParser.g:3675:4: rule__SimpleQuantifier__QuantifierAlternatives_0_0
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
    // InternalRegularExpressionParser.g:3683:1: rule__SimpleQuantifier__NonGreedyAssignment_1 : ( ( QuestionMark ) ) ;
    public final void rule__SimpleQuantifier__NonGreedyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3687:1: ( ( ( QuestionMark ) ) )
            // InternalRegularExpressionParser.g:3688:2: ( ( QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:3688:2: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:3689:3: ( QuestionMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); 
            }
            // InternalRegularExpressionParser.g:3690:3: ( QuestionMark )
            // InternalRegularExpressionParser.g:3691:4: QuestionMark
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
    // InternalRegularExpressionParser.g:3702:1: rule__ExactQuantifier__MinAssignment_2 : ( ruleINT ) ;
    public final void rule__ExactQuantifier__MinAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3706:1: ( ( ruleINT ) )
            // InternalRegularExpressionParser.g:3707:2: ( ruleINT )
            {
            // InternalRegularExpressionParser.g:3707:2: ( ruleINT )
            // InternalRegularExpressionParser.g:3708:3: ruleINT
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
    // InternalRegularExpressionParser.g:3717:1: rule__ExactQuantifier__MaxAssignment_3_0_1 : ( ruleINT ) ;
    public final void rule__ExactQuantifier__MaxAssignment_3_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3721:1: ( ( ruleINT ) )
            // InternalRegularExpressionParser.g:3722:2: ( ruleINT )
            {
            // InternalRegularExpressionParser.g:3722:2: ( ruleINT )
            // InternalRegularExpressionParser.g:3723:3: ruleINT
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
    // InternalRegularExpressionParser.g:3732:1: rule__ExactQuantifier__UnboundedMaxAssignment_3_1 : ( ( Comma ) ) ;
    public final void rule__ExactQuantifier__UnboundedMaxAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3736:1: ( ( ( Comma ) ) )
            // InternalRegularExpressionParser.g:3737:2: ( ( Comma ) )
            {
            // InternalRegularExpressionParser.g:3737:2: ( ( Comma ) )
            // InternalRegularExpressionParser.g:3738:3: ( Comma )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); 
            }
            // InternalRegularExpressionParser.g:3739:3: ( Comma )
            // InternalRegularExpressionParser.g:3740:4: Comma
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
    // InternalRegularExpressionParser.g:3751:1: rule__ExactQuantifier__NonGreedyAssignment_5 : ( ( QuestionMark ) ) ;
    public final void rule__ExactQuantifier__NonGreedyAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3755:1: ( ( ( QuestionMark ) ) )
            // InternalRegularExpressionParser.g:3756:2: ( ( QuestionMark ) )
            {
            // InternalRegularExpressionParser.g:3756:2: ( ( QuestionMark ) )
            // InternalRegularExpressionParser.g:3757:3: ( QuestionMark )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); 
            }
            // InternalRegularExpressionParser.g:3758:3: ( QuestionMark )
            // InternalRegularExpressionParser.g:3759:4: QuestionMark
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
    // InternalRegularExpressionParser.g:3770:1: rule__RegularExpressionFlags__FlagsAssignment_1 : ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) ) ;
    public final void rule__RegularExpressionFlags__FlagsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalRegularExpressionParser.g:3774:1: ( ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) ) )
            // InternalRegularExpressionParser.g:3775:2: ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) )
            {
            // InternalRegularExpressionParser.g:3775:2: ( ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 ) )
            // InternalRegularExpressionParser.g:3776:3: ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAlternatives_1_0()); 
            }
            // InternalRegularExpressionParser.g:3777:3: ( rule__RegularExpressionFlags__FlagsAlternatives_1_0 )
            // InternalRegularExpressionParser.g:3777:4: rule__RegularExpressionFlags__FlagsAlternatives_1_0
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

    // $ANTLR start synpred69_InternalRegularExpressionParser
    public final void synpred69_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:2018:3: ( rule__Term__QuantifierAssignment_1_1 )
        // InternalRegularExpressionParser.g:2018:3: rule__Term__QuantifierAssignment_1_1
        {
        pushFollow(FOLLOW_2);
        rule__Term__QuantifierAssignment_1_1();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred69_InternalRegularExpressionParser

    // $ANTLR start synpred70_InternalRegularExpressionParser
    public final void synpred70_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:2478:3: ( rule__CharacterClass__Group_2__0 )
        // InternalRegularExpressionParser.g:2478:3: rule__CharacterClass__Group_2__0
        {
        pushFollow(FOLLOW_2);
        rule__CharacterClass__Group_2__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred70_InternalRegularExpressionParser

    // $ANTLR start synpred72_InternalRegularExpressionParser
    public final void synpred72_InternalRegularExpressionParser_fragment() throws RecognitionException {   
        // InternalRegularExpressionParser.g:2612:3: ( rule__CharacterClassElement__Group_1__0 )
        // InternalRegularExpressionParser.g:2612:3: rule__CharacterClassElement__Group_1__0
        {
        pushFollow(FOLLOW_2);
        rule__CharacterClassElement__Group_1__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred72_InternalRegularExpressionParser

    // Delegated rules

    public final boolean synpred72_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred72_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred69_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred69_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred70_InternalRegularExpressionParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred70_InternalRegularExpressionParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA24 dfa24 = new DFA24(this);
    protected DFA27 dfa27 = new DFA27(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\1\3\1\uffff\1\3\1\uffff\3\3\1\uffff\2\3";
    static final String dfa_3s = "\1\4\1\uffff\1\4\1\uffff\3\4\1\0\2\4";
    static final String dfa_4s = "\1\46\1\uffff\1\46\1\uffff\3\46\1\0\2\46";
    static final String dfa_5s = "\1\uffff\1\1\1\uffff\1\2\6\uffff";
    static final String dfa_6s = "\7\uffff\1\0\2\uffff}>";
    static final String[] dfa_7s = {
            "\4\3\2\1\6\3\1\1\3\3\1\2\7\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\3\1\uffff\2\3",
            "",
            "\30\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\4\1\uffff\2\3",
            "",
            "\6\3\1\6\13\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\5\1\uffff\2\3",
            "\6\3\1\6\13\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\5\1\uffff\2\3",
            "\22\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\10\1\uffff\2\3",
            "\1\uffff",
            "\22\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\11\1\uffff\2\3",
            "\22\3\1\7\5\3\1\uffff\2\3\1\uffff\2\3\1\uffff\1\11\1\uffff\2\3"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "2018:2: ( rule__Term__QuantifierAssignment_1_1 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA24_7 = input.LA(1);

                         
                        int index24_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred69_InternalRegularExpressionParser()) ) {s = 1;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index24_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 24, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\41\uffff";
    static final String dfa_9s = "\1\2\40\uffff";
    static final String dfa_10s = "\2\4\1\uffff\35\0\1\uffff";
    static final String dfa_11s = "\2\46\1\uffff\35\0\1\uffff";
    static final String dfa_12s = "\2\uffff\1\2\35\uffff\1\1";
    static final String dfa_13s = "\3\uffff\1\24\1\6\1\15\1\30\1\11\1\7\1\22\1\2\1\25\1\12\1\31\1\16\1\3\1\1\1\21\1\5\1\10\1\27\1\14\1\34\1\0\1\20\1\4\1\23\1\26\1\13\1\32\1\17\1\33\1\uffff}>";
    static final String[] dfa_14s = {
            "\7\2\1\1\14\2\1\uffff\3\2\1\uffff\2\2\1\uffff\2\2\1\uffff\1\2\1\uffff\2\2",
            "\1\16\1\21\1\26\1\27\1\23\1\24\1\13\1\17\1\22\1\34\1\15\1\14\1\25\1\30\1\2\1\20\1\31\1\33\1\32\1\4\1\uffff\1\12\1\5\1\6\1\uffff\1\7\1\10\1\uffff\1\3\1\11\1\uffff\1\37\1\uffff\1\36\1\35",
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
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "2612:2: ( rule__CharacterClassElement__Group_1__0 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_23 = input.LA(1);

                         
                        int index27_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_23);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_16 = input.LA(1);

                         
                        int index27_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_16);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA27_10 = input.LA(1);

                         
                        int index27_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_10);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA27_15 = input.LA(1);

                         
                        int index27_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_15);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA27_25 = input.LA(1);

                         
                        int index27_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_25);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA27_18 = input.LA(1);

                         
                        int index27_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_18);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA27_4 = input.LA(1);

                         
                        int index27_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_4);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA27_8 = input.LA(1);

                         
                        int index27_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA27_19 = input.LA(1);

                         
                        int index27_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_19);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA27_7 = input.LA(1);

                         
                        int index27_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_7);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA27_12 = input.LA(1);

                         
                        int index27_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA27_28 = input.LA(1);

                         
                        int index27_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_28);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA27_21 = input.LA(1);

                         
                        int index27_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_21);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA27_5 = input.LA(1);

                         
                        int index27_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_5);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA27_14 = input.LA(1);

                         
                        int index27_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA27_30 = input.LA(1);

                         
                        int index27_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_30);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA27_24 = input.LA(1);

                         
                        int index27_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_24);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA27_17 = input.LA(1);

                         
                        int index27_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA27_9 = input.LA(1);

                         
                        int index27_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_9);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA27_26 = input.LA(1);

                         
                        int index27_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_26);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA27_3 = input.LA(1);

                         
                        int index27_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_3);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA27_11 = input.LA(1);

                         
                        int index27_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_11);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA27_27 = input.LA(1);

                         
                        int index27_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_27);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA27_20 = input.LA(1);

                         
                        int index27_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_20);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA27_6 = input.LA(1);

                         
                        int index27_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_6);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA27_13 = input.LA(1);

                         
                        int index27_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_13);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA27_29 = input.LA(1);

                         
                        int index27_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_29);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA27_31 = input.LA(1);

                         
                        int index27_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_31);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA27_22 = input.LA(1);

                         
                        int index27_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred72_InternalRegularExpressionParser()) ) {s = 32;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index27_22);
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
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000006B6FFEDC70L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000002040000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000006B6FDEDC70L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000006B6FDEDC72L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000110300L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000001800000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000001880060L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000006B6EFFFFF0L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000006B6EFBFFF2L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000006B6EFBFFF0L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000006B6FFFDC70L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000400400L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000002040000002L});

}